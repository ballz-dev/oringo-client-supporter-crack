package org.java_websocket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ByteChannel;
import java.nio.channels.SelectionKey;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import javax.net.ssl.SSLSession;
import org.java_websocket.drafts.Draft;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.enums.CloseHandshakeType;
import org.java_websocket.enums.HandshakeState;
import org.java_websocket.enums.Opcode;
import org.java_websocket.enums.ReadyState;
import org.java_websocket.enums.Role;
import org.java_websocket.exceptions.IncompleteHandshakeException;
import org.java_websocket.exceptions.InvalidDataException;
import org.java_websocket.exceptions.InvalidHandshakeException;
import org.java_websocket.exceptions.LimitExceededException;
import org.java_websocket.exceptions.WebsocketNotConnectedException;
import org.java_websocket.framing.CloseFrame;
import org.java_websocket.framing.Framedata;
import org.java_websocket.framing.PingFrame;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.handshake.ClientHandshakeBuilder;
import org.java_websocket.handshake.Handshakedata;
import org.java_websocket.handshake.ServerHandshake;
import org.java_websocket.handshake.ServerHandshakeBuilder;
import org.java_websocket.interfaces.ISSLChannel;
import org.java_websocket.protocols.IProtocol;
import org.java_websocket.server.WebSocketServer;
import org.java_websocket.util.Charsetfunctions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebSocketImpl implements WebSocket {
  private final Logger log = LoggerFactory.getLogger(WebSocketImpl.class);
  
  private boolean flushandclosestate = false;
  
  private volatile ReadyState readyState = ReadyState.NOT_YET_CONNECTED;
  
  private Draft draft = null;
  
  private ByteBuffer tmpHandshakeBytes = ByteBuffer.allocate(0);
  
  private ClientHandshake handshakerequest = null;
  
  private String closemessage = null;
  
  private Integer closecode = null;
  
  private Boolean closedremotely = null;
  
  private String resourceDescriptor = null;
  
  private long lastPong = System.nanoTime();
  
  private final Object synchronizeWriteObject = new Object();
  
  public static final int RCVBUF = 16384;
  
  private WebSocketServer.WebSocketWorker workerThread;
  
  private final WebSocketListener wsl;
  
  private SelectionKey key;
  
  public final BlockingQueue<ByteBuffer> outQueue;
  
  public static final int DEFAULT_PORT = 80;
  
  private Object attachment;
  
  public static final int DEFAULT_WSS_PORT = 443;
  
  public final BlockingQueue<ByteBuffer> inQueue;
  
  private List<Draft> knownDrafts;
  
  private Role role;
  
  private ByteChannel channel;
  
  public WebSocketImpl(WebSocketListener paramWebSocketListener, List<Draft> paramList) {
    this(paramWebSocketListener, (Draft)null);
    this.role = Role.SERVER;
    if (paramList == null || paramList.isEmpty()) {
      this.knownDrafts = new ArrayList<>();
      this.knownDrafts.add(new Draft_6455());
    } else {
      this.knownDrafts = paramList;
    } 
  }
  
  public WebSocketImpl(WebSocketListener paramWebSocketListener, Draft paramDraft) {
    if (paramWebSocketListener == null || (paramDraft == null && this.role == Role.SERVER))
      throw new IllegalArgumentException("parameters must not be null"); 
    this.outQueue = new LinkedBlockingQueue<>();
    this.inQueue = new LinkedBlockingQueue<>();
    this.wsl = paramWebSocketListener;
    this.role = Role.CLIENT;
    if (paramDraft != null)
      this.draft = paramDraft.copyInstance(); 
  }
  
  public void decode(ByteBuffer paramByteBuffer) {
    assert paramByteBuffer.hasRemaining();
    this.log.trace("process({}): ({})", Integer.valueOf(paramByteBuffer.remaining()), 
        (paramByteBuffer.remaining() > 1000) ? "too big to display" : new String(paramByteBuffer
          .array(), paramByteBuffer.position(), paramByteBuffer.remaining()));
    if (this.readyState != ReadyState.NOT_YET_CONNECTED) {
      if (this.readyState == ReadyState.OPEN)
        decodeFrames(paramByteBuffer); 
    } else if (decodeHandshake(paramByteBuffer) && !isClosing() && !isClosed()) {
      assert this.tmpHandshakeBytes.hasRemaining() != paramByteBuffer.hasRemaining() || 
        !paramByteBuffer.hasRemaining();
      if (paramByteBuffer.hasRemaining()) {
        decodeFrames(paramByteBuffer);
      } else if (this.tmpHandshakeBytes.hasRemaining()) {
        decodeFrames(this.tmpHandshakeBytes);
      } 
    } 
  }
  
  private boolean decodeHandshake(ByteBuffer paramByteBuffer) {
    ByteBuffer byteBuffer;
    if (this.tmpHandshakeBytes.capacity() == 0) {
      byteBuffer = paramByteBuffer;
    } else {
      if (this.tmpHandshakeBytes.remaining() < paramByteBuffer.remaining()) {
        ByteBuffer byteBuffer1 = ByteBuffer.allocate(this.tmpHandshakeBytes.capacity() + paramByteBuffer.remaining());
        this.tmpHandshakeBytes.flip();
        byteBuffer1.put(this.tmpHandshakeBytes);
        this.tmpHandshakeBytes = byteBuffer1;
      } 
      this.tmpHandshakeBytes.put(paramByteBuffer);
      this.tmpHandshakeBytes.flip();
      byteBuffer = this.tmpHandshakeBytes;
    } 
    byteBuffer.mark();
    try {
      if (this.role == Role.SERVER) {
        if (this.draft == null) {
          for (Draft draft : this.knownDrafts) {
            draft = draft.copyInstance();
            try {
              draft.setParseMode(this.role);
              byteBuffer.reset();
              Handshakedata handshakedata1 = draft.translateHandshake(byteBuffer);
              if (!(handshakedata1 instanceof ClientHandshake)) {
                this.log.trace("Closing due to wrong handshake");
                closeConnectionDueToWrongHandshake(new InvalidDataException(1002, "wrong http function"));
                return false;
              } 
              ClientHandshake clientHandshake1 = (ClientHandshake)handshakedata1;
              HandshakeState handshakeState1 = draft.acceptHandshakeAsServer(clientHandshake1);
              if (handshakeState1 == HandshakeState.MATCHED) {
                ServerHandshakeBuilder serverHandshakeBuilder;
                this.resourceDescriptor = clientHandshake1.getResourceDescriptor();
                try {
                  serverHandshakeBuilder = this.wsl.onWebsocketHandshakeReceivedAsServer(this, draft, clientHandshake1);
                } catch (InvalidDataException invalidDataException) {
                  this.log.trace("Closing due to wrong handshake. Possible handshake rejection", (Throwable)invalidDataException);
                  closeConnectionDueToWrongHandshake(invalidDataException);
                  return false;
                } catch (RuntimeException runtimeException) {
                  this.log.error("Closing due to internal server error", runtimeException);
                  this.wsl.onWebsocketError(this, runtimeException);
                  closeConnectionDueToInternalServerError(runtimeException);
                  return false;
                } 
                write(draft.createHandshake((Handshakedata)draft
                      .postProcessHandshakeResponseAsServer(clientHandshake1, serverHandshakeBuilder)));
                this.draft = draft;
                open((Handshakedata)clientHandshake1);
                return true;
              } 
            } catch (InvalidHandshakeException invalidHandshakeException) {}
          } 
          if (this.draft == null) {
            this.log.trace("Closing due to protocol error: no draft matches");
            closeConnectionDueToWrongHandshake(new InvalidDataException(1002, "no draft matches"));
          } 
          return false;
        } 
        Handshakedata handshakedata = this.draft.translateHandshake(byteBuffer);
        if (!(handshakedata instanceof ClientHandshake)) {
          this.log.trace("Closing due to protocol error: wrong http function");
          flushAndClose(1002, "wrong http function", false);
          return false;
        } 
        ClientHandshake clientHandshake = (ClientHandshake)handshakedata;
        HandshakeState handshakeState = this.draft.acceptHandshakeAsServer(clientHandshake);
        if (handshakeState == HandshakeState.MATCHED) {
          open((Handshakedata)clientHandshake);
          return true;
        } 
        this.log.trace("Closing due to protocol error: the handshake did finally not match");
        close(1002, "the handshake did finally not match");
        return false;
      } 
      if (this.role == Role.CLIENT) {
        this.draft.setParseMode(this.role);
        Handshakedata handshakedata = this.draft.translateHandshake(byteBuffer);
        if (!(handshakedata instanceof ServerHandshake)) {
          this.log.trace("Closing due to protocol error: wrong http function");
          flushAndClose(1002, "wrong http function", false);
          return false;
        } 
        ServerHandshake serverHandshake = (ServerHandshake)handshakedata;
        HandshakeState handshakeState = this.draft.acceptHandshakeAsClient(this.handshakerequest, serverHandshake);
        if (handshakeState == HandshakeState.MATCHED) {
          try {
            this.wsl.onWebsocketHandshakeReceivedAsClient(this, this.handshakerequest, serverHandshake);
          } catch (InvalidDataException invalidDataException) {
            this.log.trace("Closing due to invalid data exception. Possible handshake rejection", (Throwable)invalidDataException);
            flushAndClose(invalidDataException.getCloseCode(), invalidDataException.getMessage(), false);
            return false;
          } catch (RuntimeException runtimeException) {
            this.log.error("Closing since client was never connected", runtimeException);
            this.wsl.onWebsocketError(this, runtimeException);
            flushAndClose(-1, runtimeException.getMessage(), false);
            return false;
          } 
          open((Handshakedata)serverHandshake);
          return true;
        } 
        this.log.trace("Closing due to protocol error: draft {} refuses handshake", this.draft);
        close(1002, "draft " + this.draft + " refuses handshake");
      } 
    } catch (InvalidHandshakeException invalidHandshakeException) {
      this.log.trace("Closing due to invalid handshake", (Throwable)invalidHandshakeException);
      close((InvalidDataException)invalidHandshakeException);
    } catch (IncompleteHandshakeException incompleteHandshakeException) {
      if (this.tmpHandshakeBytes.capacity() == 0) {
        byteBuffer.reset();
        int i = incompleteHandshakeException.getPreferredSize();
        if (i == 0) {
          i = byteBuffer.capacity() + 16;
        } else {
          assert incompleteHandshakeException.getPreferredSize() >= byteBuffer.remaining();
        } 
        this.tmpHandshakeBytes = ByteBuffer.allocate(i);
        this.tmpHandshakeBytes.put(paramByteBuffer);
      } else {
        this.tmpHandshakeBytes.position(this.tmpHandshakeBytes.limit());
        this.tmpHandshakeBytes.limit(this.tmpHandshakeBytes.capacity());
      } 
    } 
    return false;
  }
  
  private void decodeFrames(ByteBuffer paramByteBuffer) {
    try {
      List list = this.draft.translateFrame(paramByteBuffer);
      for (Framedata framedata : list) {
        this.log.trace("matched frame: {}", framedata);
        this.draft.processFrame(this, framedata);
      } 
    } catch (LimitExceededException limitExceededException) {
      if (limitExceededException.getLimit() == Integer.MAX_VALUE) {
        this.log.error("Closing due to invalid size of frame", (Throwable)limitExceededException);
        this.wsl.onWebsocketError(this, (Exception)limitExceededException);
      } 
      close((InvalidDataException)limitExceededException);
    } catch (InvalidDataException invalidDataException) {
      this.log.error("Closing due to invalid data in frame", (Throwable)invalidDataException);
      this.wsl.onWebsocketError(this, (Exception)invalidDataException);
      close(invalidDataException);
    } catch (VirtualMachineError|ThreadDeath|LinkageError virtualMachineError) {
      this.log.error("Got fatal error during frame processing");
      throw virtualMachineError;
    } catch (Error error) {
      this.log.error("Closing web socket due to an error during frame processing");
      Exception exception = new Exception(error);
      this.wsl.onWebsocketError(this, exception);
      String str = "Got error " + error.getClass().getName();
      close(1011, str);
    } 
  }
  
  private void closeConnectionDueToWrongHandshake(InvalidDataException paramInvalidDataException) {
    write(generateHttpResponseDueToError(404));
    flushAndClose(paramInvalidDataException.getCloseCode(), paramInvalidDataException.getMessage(), false);
  }
  
  private void closeConnectionDueToInternalServerError(RuntimeException paramRuntimeException) {
    write(generateHttpResponseDueToError(500));
    flushAndClose(-1, paramRuntimeException.getMessage(), false);
  }
  
  private ByteBuffer generateHttpResponseDueToError(int paramInt) {
    String str;
    switch (paramInt) {
      case 404:
        str = "404 WebSocket Upgrade Failure";
        break;
      default:
        str = "500 Internal Server Error";
        break;
    } 
    return ByteBuffer.wrap(Charsetfunctions.asciiBytes("HTTP/1.1 " + str + "\r\nContent-Type: text/html\r\nServer: TooTallNate Java-WebSocket\r\nContent-Length: " + (48 + str
          
          .length()) + "\r\n\r\n<html><head></head><body><h1>" + str + "</h1></body></html>"));
  }
  
  public synchronized void close(int paramInt, String paramString, boolean paramBoolean) {
    if (this.readyState != ReadyState.CLOSING && this.readyState != ReadyState.CLOSED) {
      if (this.readyState == ReadyState.OPEN) {
        if (paramInt == 1006) {
          assert !paramBoolean;
          this.readyState = ReadyState.CLOSING;
          flushAndClose(paramInt, paramString, false);
          return;
        } 
        if (this.draft.getCloseHandshakeType() != CloseHandshakeType.NONE)
          try {
            if (!paramBoolean)
              try {
                this.wsl.onWebsocketCloseInitiated(this, paramInt, paramString);
              } catch (RuntimeException runtimeException) {
                this.wsl.onWebsocketError(this, runtimeException);
              }  
            if (isOpen()) {
              CloseFrame closeFrame = new CloseFrame();
              closeFrame.setReason(paramString);
              closeFrame.setCode(paramInt);
              closeFrame.isValid();
              sendFrame((Framedata)closeFrame);
            } 
          } catch (InvalidDataException invalidDataException) {
            this.log.error("generated frame is invalid", (Throwable)invalidDataException);
            this.wsl.onWebsocketError(this, (Exception)invalidDataException);
            flushAndClose(1006, "generated frame is invalid", false);
          }  
        flushAndClose(paramInt, paramString, paramBoolean);
      } else if (paramInt == -3) {
        assert paramBoolean;
        flushAndClose(-3, paramString, true);
      } else if (paramInt == 1002) {
        flushAndClose(paramInt, paramString, paramBoolean);
      } else {
        flushAndClose(-1, paramString, false);
      } 
      this.readyState = ReadyState.CLOSING;
      this.tmpHandshakeBytes = null;
      return;
    } 
  }
  
  public void close(int paramInt, String paramString) {
    close(paramInt, paramString, false);
  }
  
  public synchronized void closeConnection(int paramInt, String paramString, boolean paramBoolean) {
    if (this.readyState == ReadyState.CLOSED)
      return; 
    if (this.readyState == ReadyState.OPEN && 
      paramInt == 1006)
      this.readyState = ReadyState.CLOSING; 
    if (this.key != null)
      this.key.cancel(); 
    if (this.channel != null)
      try {
        this.channel.close();
      } catch (IOException iOException) {
        if (iOException.getMessage() != null && iOException.getMessage().equals("Broken pipe")) {
          this.log.trace("Caught IOException: Broken pipe during closeConnection()", iOException);
        } else {
          this.log.error("Exception during channel.close()", iOException);
          this.wsl.onWebsocketError(this, iOException);
        } 
      }  
    try {
      this.wsl.onWebsocketClose(this, paramInt, paramString, paramBoolean);
    } catch (RuntimeException runtimeException) {
      this.wsl.onWebsocketError(this, runtimeException);
    } 
    if (this.draft != null)
      this.draft.reset(); 
    this.handshakerequest = null;
    this.readyState = ReadyState.CLOSED;
  }
  
  protected void closeConnection(int paramInt, boolean paramBoolean) {
    closeConnection(paramInt, "", paramBoolean);
  }
  
  public void closeConnection() {
    if (this.closedremotely == null)
      throw new IllegalStateException("this method must be used in conjunction with flushAndClose"); 
    closeConnection(this.closecode.intValue(), this.closemessage, this.closedremotely.booleanValue());
  }
  
  public void closeConnection(int paramInt, String paramString) {
    closeConnection(paramInt, paramString, false);
  }
  
  public synchronized void flushAndClose(int paramInt, String paramString, boolean paramBoolean) {
    if (this.flushandclosestate)
      return; 
    this.closecode = Integer.valueOf(paramInt);
    this.closemessage = paramString;
    this.closedremotely = Boolean.valueOf(paramBoolean);
    this.flushandclosestate = true;
    this.wsl.onWriteDemand(this);
    try {
      this.wsl.onWebsocketClosing(this, paramInt, paramString, paramBoolean);
    } catch (RuntimeException runtimeException) {
      this.log.error("Exception in onWebsocketClosing", runtimeException);
      this.wsl.onWebsocketError(this, runtimeException);
    } 
    if (this.draft != null)
      this.draft.reset(); 
    this.handshakerequest = null;
  }
  
  public void eot() {
    if (this.readyState == ReadyState.NOT_YET_CONNECTED) {
      closeConnection(-1, true);
    } else if (this.flushandclosestate) {
      closeConnection(this.closecode.intValue(), this.closemessage, this.closedremotely.booleanValue());
    } else if (this.draft.getCloseHandshakeType() == CloseHandshakeType.NONE) {
      closeConnection(1000, true);
    } else if (this.draft.getCloseHandshakeType() == CloseHandshakeType.ONEWAY) {
      if (this.role == Role.SERVER) {
        closeConnection(1006, true);
      } else {
        closeConnection(1000, true);
      } 
    } else {
      closeConnection(1006, true);
    } 
  }
  
  public void close(int paramInt) {
    close(paramInt, "", false);
  }
  
  public void close(InvalidDataException paramInvalidDataException) {
    close(paramInvalidDataException.getCloseCode(), paramInvalidDataException.getMessage(), false);
  }
  
  public void send(String paramString) {
    if (paramString == null)
      throw new IllegalArgumentException("Cannot send 'null' data to a WebSocketImpl."); 
    send(this.draft.createFrames(paramString, (this.role == Role.CLIENT)));
  }
  
  public void send(ByteBuffer paramByteBuffer) {
    if (paramByteBuffer == null)
      throw new IllegalArgumentException("Cannot send 'null' data to a WebSocketImpl."); 
    send(this.draft.createFrames(paramByteBuffer, (this.role == Role.CLIENT)));
  }
  
  public void send(byte[] paramArrayOfbyte) {
    send(ByteBuffer.wrap(paramArrayOfbyte));
  }
  
  private void send(Collection<Framedata> paramCollection) {
    if (!isOpen())
      throw new WebsocketNotConnectedException(); 
    if (paramCollection == null)
      throw new IllegalArgumentException(); 
    ArrayList<ByteBuffer> arrayList = new ArrayList();
    for (Framedata framedata : paramCollection) {
      this.log.trace("send frame: {}", framedata);
      arrayList.add(this.draft.createBinaryFrame(framedata));
    } 
    write(arrayList);
  }
  
  public void sendFragmentedFrame(Opcode paramOpcode, ByteBuffer paramByteBuffer, boolean paramBoolean) {
    send(this.draft.continuousFrame(paramOpcode, paramByteBuffer, paramBoolean));
  }
  
  public void sendFrame(Collection<Framedata> paramCollection) {
    send(paramCollection);
  }
  
  public void sendFrame(Framedata paramFramedata) {
    send(Collections.singletonList(paramFramedata));
  }
  
  public void sendPing() throws NullPointerException {
    PingFrame pingFrame = this.wsl.onPreparePing(this);
    if (pingFrame == null)
      throw new NullPointerException("onPreparePing(WebSocket) returned null. PingFrame to sent can't be null."); 
    sendFrame((Framedata)pingFrame);
  }
  
  public boolean hasBufferedData() {
    return !this.outQueue.isEmpty();
  }
  
  public void startHandshake(ClientHandshakeBuilder paramClientHandshakeBuilder) throws InvalidHandshakeException {
    this.handshakerequest = (ClientHandshake)this.draft.postProcessHandshakeRequestAsClient(paramClientHandshakeBuilder);
    this.resourceDescriptor = paramClientHandshakeBuilder.getResourceDescriptor();
    assert this.resourceDescriptor != null;
    try {
      this.wsl.onWebsocketHandshakeSentAsClient(this, this.handshakerequest);
    } catch (InvalidDataException invalidDataException) {
      throw new InvalidHandshakeException("Handshake data rejected by client.");
    } catch (RuntimeException runtimeException) {
      this.log.error("Exception in startHandshake", runtimeException);
      this.wsl.onWebsocketError(this, runtimeException);
      throw new InvalidHandshakeException("rejected because of " + runtimeException);
    } 
    write(this.draft.createHandshake((Handshakedata)this.handshakerequest));
  }
  
  private void write(ByteBuffer paramByteBuffer) {
    this.log.trace("write({}): {}", Integer.valueOf(paramByteBuffer.remaining()), 
        (paramByteBuffer.remaining() > 1000) ? "too big to display" : new String(paramByteBuffer.array()));
    this.outQueue.add(paramByteBuffer);
    this.wsl.onWriteDemand(this);
  }
  
  private void write(List<ByteBuffer> paramList) {
    synchronized (this.synchronizeWriteObject) {
      for (ByteBuffer byteBuffer : paramList)
        write(byteBuffer); 
    } 
  }
  
  private void open(Handshakedata paramHandshakedata) {
    this.log.trace("open using draft: {}", this.draft);
    this.readyState = ReadyState.OPEN;
    updateLastPong();
    try {
      this.wsl.onWebsocketOpen(this, paramHandshakedata);
    } catch (RuntimeException runtimeException) {
      this.wsl.onWebsocketError(this, runtimeException);
    } 
  }
  
  public boolean isOpen() {
    return (this.readyState == ReadyState.OPEN);
  }
  
  public boolean isClosing() {
    return (this.readyState == ReadyState.CLOSING);
  }
  
  public boolean isFlushAndClose() {
    return this.flushandclosestate;
  }
  
  public boolean isClosed() {
    return (this.readyState == ReadyState.CLOSED);
  }
  
  public ReadyState getReadyState() {
    return this.readyState;
  }
  
  public void setSelectionKey(SelectionKey paramSelectionKey) {
    this.key = paramSelectionKey;
  }
  
  public SelectionKey getSelectionKey() {
    return this.key;
  }
  
  public String toString() {
    return super.toString();
  }
  
  public InetSocketAddress getRemoteSocketAddress() {
    return this.wsl.getRemoteSocketAddress(this);
  }
  
  public InetSocketAddress getLocalSocketAddress() {
    return this.wsl.getLocalSocketAddress(this);
  }
  
  public Draft getDraft() {
    return this.draft;
  }
  
  public void close() {
    close(1000);
  }
  
  public String getResourceDescriptor() {
    return this.resourceDescriptor;
  }
  
  long getLastPong() {
    return this.lastPong;
  }
  
  public void updateLastPong() {
    this.lastPong = System.nanoTime();
  }
  
  public WebSocketListener getWebSocketListener() {
    return this.wsl;
  }
  
  public <T> T getAttachment() {
    return (T)this.attachment;
  }
  
  public boolean hasSSLSupport() {
    return this.channel instanceof ISSLChannel;
  }
  
  public SSLSession getSSLSession() {
    if (!hasSSLSupport())
      throw new IllegalArgumentException("This websocket uses ws instead of wss. No SSLSession available."); 
    return ((ISSLChannel)this.channel).getSSLEngine().getSession();
  }
  
  public IProtocol getProtocol() {
    if (this.draft == null)
      return null; 
    if (!(this.draft instanceof Draft_6455))
      throw new IllegalArgumentException("This draft does not support Sec-WebSocket-Protocol"); 
    return ((Draft_6455)this.draft).getProtocol();
  }
  
  public <T> void setAttachment(T paramT) {
    this.attachment = paramT;
  }
  
  public ByteChannel getChannel() {
    return this.channel;
  }
  
  public void setChannel(ByteChannel paramByteChannel) {
    this.channel = paramByteChannel;
  }
  
  public WebSocketServer.WebSocketWorker getWorkerThread() {
    return this.workerThread;
  }
  
  public void setWorkerThread(WebSocketServer.WebSocketWorker paramWebSocketWorker) {
    this.workerThread = paramWebSocketWorker;
  }
}
