package org.java_websocket.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.CancelledKeyException;
import java.nio.channels.ClosedByInterruptException;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import org.java_websocket.AbstractWebSocket;
import org.java_websocket.SocketChannelIOHelper;
import org.java_websocket.WebSocket;
import org.java_websocket.WebSocketAdapter;
import org.java_websocket.WebSocketFactory;
import org.java_websocket.WebSocketImpl;
import org.java_websocket.WebSocketServerFactory;
import org.java_websocket.WrappedByteChannel;
import org.java_websocket.drafts.Draft;
import org.java_websocket.exceptions.WebsocketNotConnectedException;
import org.java_websocket.exceptions.WrappedIOException;
import org.java_websocket.framing.Framedata;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.handshake.Handshakedata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class WebSocketServer extends AbstractWebSocket implements Runnable {
  private static final int AVAILABLE_PROCESSORS = Runtime.getRuntime().availableProcessors();
  
  private final Logger log = LoggerFactory.getLogger(WebSocketServer.class);
  
  private final AtomicBoolean isclosed = new AtomicBoolean(false);
  
  private int queueinvokes = 0;
  
  private final AtomicInteger queuesize = new AtomicInteger(0);
  
  private WebSocketServerFactory wsf = new DefaultWebSocketServerFactory();
  
  private int maxPendingConnections = -1;
  
  private final InetSocketAddress address;
  
  private Thread selectorthread;
  
  private List<WebSocketImpl> iqueue;
  
  private ServerSocketChannel server;
  
  protected List<WebSocketWorker> decoders;
  
  private Selector selector;
  
  private final Collection<WebSocket> connections;
  
  private List<Draft> drafts;
  
  private BlockingQueue<ByteBuffer> buffers;
  
  public WebSocketServer() {
    this(new InetSocketAddress(80), AVAILABLE_PROCESSORS, (List<Draft>)null);
  }
  
  public WebSocketServer(InetSocketAddress paramInetSocketAddress) {
    this(paramInetSocketAddress, AVAILABLE_PROCESSORS, (List<Draft>)null);
  }
  
  public WebSocketServer(InetSocketAddress paramInetSocketAddress, int paramInt) {
    this(paramInetSocketAddress, paramInt, (List<Draft>)null);
  }
  
  public WebSocketServer(InetSocketAddress paramInetSocketAddress, List<Draft> paramList) {
    this(paramInetSocketAddress, AVAILABLE_PROCESSORS, paramList);
  }
  
  public WebSocketServer(InetSocketAddress paramInetSocketAddress, int paramInt, List<Draft> paramList) {
    this(paramInetSocketAddress, paramInt, paramList, new HashSet<>());
  }
  
  public WebSocketServer(InetSocketAddress paramInetSocketAddress, int paramInt, List<Draft> paramList, Collection<WebSocket> paramCollection) {
    if (paramInetSocketAddress == null || paramInt < 1 || paramCollection == null)
      throw new IllegalArgumentException("address and connectionscontainer must not be null and you need at least 1 decoder"); 
    if (paramList == null) {
      this.drafts = Collections.emptyList();
    } else {
      this.drafts = paramList;
    } 
    this.address = paramInetSocketAddress;
    this.connections = paramCollection;
    setTcpNoDelay(false);
    setReuseAddr(false);
    this.iqueue = new LinkedList<>();
    this.decoders = new ArrayList<>(paramInt);
    this.buffers = new LinkedBlockingQueue<>();
    for (byte b = 0; b < paramInt; b++) {
      WebSocketWorker webSocketWorker = new WebSocketWorker();
      this.decoders.add(webSocketWorker);
    } 
  }
  
  public void start() {
    if (this.selectorthread != null)
      throw new IllegalStateException(getClass().getName() + " can only be started once."); 
    (new Thread(this)).start();
  }
  
  public void stop(int paramInt) throws InterruptedException {
    stop(paramInt, "");
  }
  
  public void stop(int paramInt, String paramString) throws InterruptedException {
    ArrayList<WebSocket> arrayList;
    if (!this.isclosed.compareAndSet(false, true))
      return; 
    synchronized (this.connections) {
      arrayList = new ArrayList<>(this.connections);
    } 
    for (WebSocket webSocket : arrayList)
      webSocket.close(1001, paramString); 
    this.wsf.close();
    synchronized (this) {
      if (this.selectorthread != null && this.selector != null) {
        this.selector.wakeup();
        this.selectorthread.join(paramInt);
      } 
    } 
  }
  
  public void stop() throws InterruptedException {
    stop(0);
  }
  
  public Collection<WebSocket> getConnections() {
    synchronized (this.connections) {
      return Collections.unmodifiableCollection(new ArrayList<>(this.connections));
    } 
  }
  
  public InetSocketAddress getAddress() {
    return this.address;
  }
  
  public int getPort() {
    int i = getAddress().getPort();
    if (i == 0 && this.server != null)
      i = this.server.socket().getLocalPort(); 
    return i;
  }
  
  public List<Draft> getDraft() {
    return Collections.unmodifiableList(this.drafts);
  }
  
  public void setMaxPendingConnections(int paramInt) {
    this.maxPendingConnections = paramInt;
  }
  
  public int getMaxPendingConnections() {
    return this.maxPendingConnections;
  }
  
  public void run() {
    if (!doEnsureSingleThread())
      return; 
    if (!doSetupSelectorAndServerThread())
      return; 
    try {
      byte b1 = 5;
      byte b2 = 0;
      while (!this.selectorthread.isInterrupted() && b1 != 0) {
        SelectionKey selectionKey = null;
        try {
          if (this.isclosed.get())
            b2 = 5; 
          int i = this.selector.select(b2);
          if (i == 0 && this.isclosed.get())
            b1--; 
          Set<SelectionKey> set = this.selector.selectedKeys();
          Iterator<SelectionKey> iterator = set.iterator();
          while (iterator.hasNext()) {
            selectionKey = iterator.next();
            if (!selectionKey.isValid())
              continue; 
            if (selectionKey.isAcceptable()) {
              doAccept(selectionKey, iterator);
              continue;
            } 
            if (selectionKey.isReadable() && !doRead(selectionKey, iterator))
              continue; 
            if (selectionKey.isWritable())
              doWrite(selectionKey); 
          } 
          doAdditionalRead();
        } catch (CancelledKeyException cancelledKeyException) {
        
        } catch (ClosedByInterruptException closedByInterruptException) {
          return;
        } catch (WrappedIOException wrappedIOException) {
          handleIOException(selectionKey, wrappedIOException.getConnection(), wrappedIOException.getIOException());
        } catch (IOException iOException) {
          handleIOException(selectionKey, (WebSocket)null, iOException);
        } catch (InterruptedException interruptedException) {
          Thread.currentThread().interrupt();
        } 
      } 
    } catch (RuntimeException runtimeException) {
      handleFatal((WebSocket)null, runtimeException);
    } finally {
      doServerShutdown();
    } 
  }
  
  private void doAdditionalRead() throws InterruptedException, IOException {
    while (!this.iqueue.isEmpty()) {
      WebSocketImpl webSocketImpl = this.iqueue.remove(0);
      WrappedByteChannel wrappedByteChannel = (WrappedByteChannel)webSocketImpl.getChannel();
      ByteBuffer byteBuffer = takeBuffer();
      try {
        if (SocketChannelIOHelper.readMore(byteBuffer, webSocketImpl, wrappedByteChannel))
          this.iqueue.add(webSocketImpl); 
        if (byteBuffer.hasRemaining()) {
          webSocketImpl.inQueue.put(byteBuffer);
          queue(webSocketImpl);
          continue;
        } 
        pushBuffer(byteBuffer);
      } catch (IOException iOException) {
        pushBuffer(byteBuffer);
        throw iOException;
      } 
    } 
  }
  
  private void doAccept(SelectionKey paramSelectionKey, Iterator<SelectionKey> paramIterator) throws IOException, InterruptedException {
    if (!onConnect(paramSelectionKey)) {
      paramSelectionKey.cancel();
      return;
    } 
    SocketChannel socketChannel = this.server.accept();
    if (socketChannel == null)
      return; 
    socketChannel.configureBlocking(false);
    Socket socket = socketChannel.socket();
    socket.setTcpNoDelay(isTcpNoDelay());
    socket.setKeepAlive(true);
    WebSocketImpl webSocketImpl = this.wsf.createWebSocket((WebSocketAdapter)this, this.drafts);
    webSocketImpl.setSelectionKey(socketChannel.register(this.selector, 1, webSocketImpl));
    try {
      webSocketImpl.setChannel(this.wsf.wrapChannel(socketChannel, webSocketImpl.getSelectionKey()));
      paramIterator.remove();
      allocateBuffers((WebSocket)webSocketImpl);
    } catch (IOException iOException) {
      if (webSocketImpl.getSelectionKey() != null)
        webSocketImpl.getSelectionKey().cancel(); 
      handleIOException(webSocketImpl.getSelectionKey(), (WebSocket)null, iOException);
    } 
  }
  
  private boolean doRead(SelectionKey paramSelectionKey, Iterator<SelectionKey> paramIterator) throws InterruptedException, WrappedIOException {
    WebSocketImpl webSocketImpl = (WebSocketImpl)paramSelectionKey.attachment();
    ByteBuffer byteBuffer = takeBuffer();
    if (webSocketImpl.getChannel() == null) {
      paramSelectionKey.cancel();
      handleIOException(paramSelectionKey, (WebSocket)webSocketImpl, new IOException());
      return false;
    } 
    try {
      if (SocketChannelIOHelper.read(byteBuffer, webSocketImpl, webSocketImpl.getChannel())) {
        if (byteBuffer.hasRemaining()) {
          webSocketImpl.inQueue.put(byteBuffer);
          queue(webSocketImpl);
          paramIterator.remove();
          if (webSocketImpl.getChannel() instanceof WrappedByteChannel && ((WrappedByteChannel)webSocketImpl
            .getChannel()).isNeedRead())
            this.iqueue.add(webSocketImpl); 
        } else {
          pushBuffer(byteBuffer);
        } 
      } else {
        pushBuffer(byteBuffer);
      } 
    } catch (IOException iOException) {
      pushBuffer(byteBuffer);
      throw new WrappedIOException(webSocketImpl, iOException);
    } 
    return true;
  }
  
  private void doWrite(SelectionKey paramSelectionKey) throws WrappedIOException {
    WebSocketImpl webSocketImpl = (WebSocketImpl)paramSelectionKey.attachment();
    try {
      if (SocketChannelIOHelper.batch(webSocketImpl, webSocketImpl.getChannel()) && paramSelectionKey.isValid())
        paramSelectionKey.interestOps(1); 
    } catch (IOException iOException) {
      throw new WrappedIOException(webSocketImpl, iOException);
    } 
  }
  
  private boolean doSetupSelectorAndServerThread() {
    this.selectorthread.setName("WebSocketSelector-" + this.selectorthread.getId());
    try {
      this.server = ServerSocketChannel.open();
      this.server.configureBlocking(false);
      ServerSocket serverSocket = this.server.socket();
      serverSocket.setReceiveBufferSize(16384);
      serverSocket.setReuseAddress(isReuseAddr());
      serverSocket.bind(this.address, getMaxPendingConnections());
      this.selector = Selector.open();
      this.server.register(this.selector, this.server.validOps());
      startConnectionLostTimer();
      for (WebSocketWorker webSocketWorker : this.decoders)
        webSocketWorker.start(); 
      onStart();
    } catch (IOException iOException) {
      handleFatal((WebSocket)null, iOException);
      return false;
    } 
    return true;
  }
  
  private boolean doEnsureSingleThread() {
    synchronized (this) {
      if (this.selectorthread != null)
        throw new IllegalStateException(getClass().getName() + " can only be started once."); 
      this.selectorthread = Thread.currentThread();
      if (this.isclosed.get())
        return false; 
    } 
    return true;
  }
  
  private void doServerShutdown() {
    stopConnectionLostTimer();
    if (this.decoders != null)
      for (WebSocketWorker webSocketWorker : this.decoders)
        webSocketWorker.interrupt();  
    if (this.selector != null)
      try {
        this.selector.close();
      } catch (IOException iOException) {
        this.log.error("IOException during selector.close", iOException);
        onError((WebSocket)null, iOException);
      }  
    if (this.server != null)
      try {
        this.server.close();
      } catch (IOException iOException) {
        this.log.error("IOException during server.close", iOException);
        onError((WebSocket)null, iOException);
      }  
  }
  
  protected void allocateBuffers(WebSocket paramWebSocket) throws InterruptedException {
    if (this.queuesize.get() >= 2 * this.decoders.size() + 1)
      return; 
    this.queuesize.incrementAndGet();
    this.buffers.put(createBuffer());
  }
  
  protected void releaseBuffers(WebSocket paramWebSocket) throws InterruptedException {}
  
  public ByteBuffer createBuffer() {
    return ByteBuffer.allocate(16384);
  }
  
  protected void queue(WebSocketImpl paramWebSocketImpl) throws InterruptedException {
    if (paramWebSocketImpl.getWorkerThread() == null) {
      paramWebSocketImpl.setWorkerThread(this.decoders.get(this.queueinvokes % this.decoders.size()));
      this.queueinvokes++;
    } 
    paramWebSocketImpl.getWorkerThread().put(paramWebSocketImpl);
  }
  
  private ByteBuffer takeBuffer() throws InterruptedException {
    return this.buffers.take();
  }
  
  private void pushBuffer(ByteBuffer paramByteBuffer) throws InterruptedException {
    if (this.buffers.size() > this.queuesize.intValue())
      return; 
    this.buffers.put(paramByteBuffer);
  }
  
  private void handleIOException(SelectionKey paramSelectionKey, WebSocket paramWebSocket, IOException paramIOException) {
    if (paramSelectionKey != null)
      paramSelectionKey.cancel(); 
    if (paramWebSocket != null) {
      paramWebSocket.closeConnection(1006, paramIOException.getMessage());
    } else if (paramSelectionKey != null) {
      SelectableChannel selectableChannel = paramSelectionKey.channel();
      if (selectableChannel != null && selectableChannel
        .isOpen()) {
        try {
          selectableChannel.close();
        } catch (IOException iOException) {}
        this.log.trace("Connection closed because of exception", paramIOException);
      } 
    } 
  }
  
  private void handleFatal(WebSocket paramWebSocket, Exception paramException) {
    this.log.error("Shutdown due to fatal error", paramException);
    onError(paramWebSocket, paramException);
    String str1 = (paramException.getCause() != null) ? (" caused by " + paramException.getCause().getClass().getName()) : "";
    String str2 = "Got error on server side: " + paramException.getClass().getName() + str1;
    try {
      stop(0, str2);
    } catch (InterruptedException interruptedException) {
      Thread.currentThread().interrupt();
      this.log.error("Interrupt during stop", paramException);
      onError((WebSocket)null, interruptedException);
    } 
    if (this.decoders != null)
      for (WebSocketWorker webSocketWorker : this.decoders)
        webSocketWorker.interrupt();  
    if (this.selectorthread != null)
      this.selectorthread.interrupt(); 
  }
  
  public final void onWebsocketMessage(WebSocket paramWebSocket, String paramString) {
    onMessage(paramWebSocket, paramString);
  }
  
  public final void onWebsocketMessage(WebSocket paramWebSocket, ByteBuffer paramByteBuffer) {
    onMessage(paramWebSocket, paramByteBuffer);
  }
  
  public final void onWebsocketOpen(WebSocket paramWebSocket, Handshakedata paramHandshakedata) {
    if (addConnection(paramWebSocket))
      onOpen(paramWebSocket, (ClientHandshake)paramHandshakedata); 
  }
  
  public final void onWebsocketClose(WebSocket paramWebSocket, int paramInt, String paramString, boolean paramBoolean) {
    this.selector.wakeup();
    try {
      if (removeConnection(paramWebSocket))
        onClose(paramWebSocket, paramInt, paramString, paramBoolean); 
    } finally {
      try {
        releaseBuffers(paramWebSocket);
      } catch (InterruptedException interruptedException) {
        Thread.currentThread().interrupt();
      } 
    } 
  }
  
  protected boolean removeConnection(WebSocket paramWebSocket) {
    boolean bool = false;
    synchronized (this.connections) {
      if (this.connections.contains(paramWebSocket)) {
        bool = this.connections.remove(paramWebSocket);
      } else {
        this.log.trace("Removing connection which is not in the connections collection! Possible no handshake received! {}", paramWebSocket);
      } 
    } 
    if (this.isclosed.get() && this.connections.isEmpty())
      this.selectorthread.interrupt(); 
    return bool;
  }
  
  protected boolean addConnection(WebSocket paramWebSocket) {
    if (!this.isclosed.get())
      synchronized (this.connections) {
        return this.connections.add(paramWebSocket);
      }  
    paramWebSocket.close(1001);
    return true;
  }
  
  public final void onWebsocketError(WebSocket paramWebSocket, Exception paramException) {
    onError(paramWebSocket, paramException);
  }
  
  public final void onWriteDemand(WebSocket paramWebSocket) {
    WebSocketImpl webSocketImpl = (WebSocketImpl)paramWebSocket;
    try {
      webSocketImpl.getSelectionKey().interestOps(5);
    } catch (CancelledKeyException cancelledKeyException) {
      webSocketImpl.outQueue.clear();
    } 
    this.selector.wakeup();
  }
  
  public void onWebsocketCloseInitiated(WebSocket paramWebSocket, int paramInt, String paramString) {
    onCloseInitiated(paramWebSocket, paramInt, paramString);
  }
  
  public void onWebsocketClosing(WebSocket paramWebSocket, int paramInt, String paramString, boolean paramBoolean) {
    onClosing(paramWebSocket, paramInt, paramString, paramBoolean);
  }
  
  public void onCloseInitiated(WebSocket paramWebSocket, int paramInt, String paramString) {}
  
  public void onClosing(WebSocket paramWebSocket, int paramInt, String paramString, boolean paramBoolean) {}
  
  public final void setWebSocketFactory(WebSocketServerFactory paramWebSocketServerFactory) {
    if (this.wsf != null)
      this.wsf.close(); 
    this.wsf = paramWebSocketServerFactory;
  }
  
  public final WebSocketFactory getWebSocketFactory() {
    return (WebSocketFactory)this.wsf;
  }
  
  protected boolean onConnect(SelectionKey paramSelectionKey) {
    return true;
  }
  
  private Socket getSocket(WebSocket paramWebSocket) {
    WebSocketImpl webSocketImpl = (WebSocketImpl)paramWebSocket;
    return ((SocketChannel)webSocketImpl.getSelectionKey().channel()).socket();
  }
  
  public InetSocketAddress getLocalSocketAddress(WebSocket paramWebSocket) {
    return (InetSocketAddress)getSocket(paramWebSocket).getLocalSocketAddress();
  }
  
  public InetSocketAddress getRemoteSocketAddress(WebSocket paramWebSocket) {
    return (InetSocketAddress)getSocket(paramWebSocket).getRemoteSocketAddress();
  }
  
  public void onMessage(WebSocket paramWebSocket, ByteBuffer paramByteBuffer) {}
  
  public void broadcast(String paramString) {
    broadcast(paramString, this.connections);
  }
  
  public void broadcast(byte[] paramArrayOfbyte) {
    broadcast(paramArrayOfbyte, this.connections);
  }
  
  public void broadcast(ByteBuffer paramByteBuffer) {
    broadcast(paramByteBuffer, this.connections);
  }
  
  public void broadcast(byte[] paramArrayOfbyte, Collection<WebSocket> paramCollection) {
    if (paramArrayOfbyte == null || paramCollection == null)
      throw new IllegalArgumentException(); 
    broadcast(ByteBuffer.wrap(paramArrayOfbyte), paramCollection);
  }
  
  public void broadcast(ByteBuffer paramByteBuffer, Collection<WebSocket> paramCollection) {
    if (paramByteBuffer == null || paramCollection == null)
      throw new IllegalArgumentException(); 
    doBroadcast(paramByteBuffer, paramCollection);
  }
  
  public void broadcast(String paramString, Collection<WebSocket> paramCollection) {
    if (paramString == null || paramCollection == null)
      throw new IllegalArgumentException(); 
    doBroadcast(paramString, paramCollection);
  }
  
  private void doBroadcast(Object paramObject, Collection<WebSocket> paramCollection) {
    ArrayList<WebSocket> arrayList;
    String str = null;
    if (paramObject instanceof String)
      str = (String)paramObject; 
    ByteBuffer byteBuffer = null;
    if (paramObject instanceof ByteBuffer)
      byteBuffer = (ByteBuffer)paramObject; 
    if (str == null && byteBuffer == null)
      return; 
    HashMap<Object, Object> hashMap = new HashMap<>();
    synchronized (paramCollection) {
      arrayList = new ArrayList<>(paramCollection);
    } 
    for (WebSocket webSocket : arrayList) {
      if (webSocket != null) {
        Draft draft = webSocket.getDraft();
        fillFrames(draft, (Map)hashMap, str, byteBuffer);
        try {
          webSocket.sendFrame((Collection)hashMap.get(draft));
        } catch (WebsocketNotConnectedException websocketNotConnectedException) {}
      } 
    } 
  }
  
  private void fillFrames(Draft paramDraft, Map<Draft, List<Framedata>> paramMap, String paramString, ByteBuffer paramByteBuffer) {
    if (!paramMap.containsKey(paramDraft)) {
      List<Framedata> list = null;
      if (paramString != null)
        list = paramDraft.createFrames(paramString, false); 
      if (paramByteBuffer != null)
        list = paramDraft.createFrames(paramByteBuffer, false); 
      if (list != null)
        paramMap.put(paramDraft, list); 
    } 
  }
  
  public abstract void onOpen(WebSocket paramWebSocket, ClientHandshake paramClientHandshake);
  
  public abstract void onClose(WebSocket paramWebSocket, int paramInt, String paramString, boolean paramBoolean);
  
  public abstract void onStart();
  
  public abstract void onMessage(WebSocket paramWebSocket, String paramString);
  
  public abstract void onError(WebSocket paramWebSocket, Exception paramException);
  
  public class WebSocketWorker extends Thread {
    private BlockingQueue<WebSocketImpl> iqueue;
    
    public WebSocketWorker() {
      this.iqueue = new LinkedBlockingQueue<>();
      setName("WebSocketWorker-" + getId());
      setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            public void uncaughtException(Thread param2Thread, Throwable param2Throwable) {
              WebSocketServer.this.log.error("Uncaught exception in thread {}: {}", param2Thread.getName(), param2Throwable);
            }
          });
    }
    
    public void put(WebSocketImpl param1WebSocketImpl) throws InterruptedException {
      this.iqueue.put(param1WebSocketImpl);
    }
    
    public void run() {
      WebSocketImpl webSocketImpl = null;
      try {
        while (true) {
          webSocketImpl = this.iqueue.take();
          ByteBuffer byteBuffer = webSocketImpl.inQueue.poll();
          assert byteBuffer != null;
          doDecode(webSocketImpl, byteBuffer);
          webSocketImpl = null;
        } 
      } catch (InterruptedException interruptedException) {
        Thread.currentThread().interrupt();
      } catch (VirtualMachineError|ThreadDeath|LinkageError virtualMachineError) {
        WebSocketServer.this.log.error("Got fatal error in worker thread {}", getName());
        Exception exception = new Exception(virtualMachineError);
        WebSocketServer.this.handleFatal((WebSocket)webSocketImpl, exception);
      } catch (Throwable throwable) {
        WebSocketServer.this.log.error("Uncaught exception in thread {}: {}", getName(), throwable);
        if (webSocketImpl != null) {
          Exception exception = new Exception(throwable);
          WebSocketServer.this.onWebsocketError((WebSocket)webSocketImpl, exception);
          webSocketImpl.close();
        } 
      } 
    }
    
    private void doDecode(WebSocketImpl param1WebSocketImpl, ByteBuffer param1ByteBuffer) throws InterruptedException {
      try {
        param1WebSocketImpl.decode(param1ByteBuffer);
      } catch (Exception exception) {
        WebSocketServer.this.log.error("Error while reading from remote connection", exception);
      } finally {
        WebSocketServer.this.pushBuffer(param1ByteBuffer);
      } 
    }
  }
}
