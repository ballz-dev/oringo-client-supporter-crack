package org.java_websocket.client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Socket;
import java.net.URI;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import javax.net.SocketFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLParameters;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import org.java_websocket.AbstractWebSocket;
import org.java_websocket.WebSocket;
import org.java_websocket.WebSocketImpl;
import org.java_websocket.WebSocketListener;
import org.java_websocket.drafts.Draft;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.enums.Opcode;
import org.java_websocket.enums.ReadyState;
import org.java_websocket.exceptions.InvalidHandshakeException;
import org.java_websocket.framing.Framedata;
import org.java_websocket.handshake.ClientHandshakeBuilder;
import org.java_websocket.handshake.HandshakeImpl1Client;
import org.java_websocket.handshake.Handshakedata;
import org.java_websocket.handshake.ServerHandshake;
import org.java_websocket.protocols.IProtocol;

public abstract class WebSocketClient extends AbstractWebSocket implements Runnable, WebSocket {
  protected URI uri = null;
  
  private WebSocketImpl engine = null;
  
  private Socket socket = null;
  
  private SocketFactory socketFactory = null;
  
  private Proxy proxy = Proxy.NO_PROXY;
  
  private CountDownLatch connectLatch = new CountDownLatch(1);
  
  private CountDownLatch closeLatch = new CountDownLatch(1);
  
  private int connectTimeout = 0;
  
  private DnsResolver dnsResolver = null;
  
  private Thread writeThread;
  
  private Map<String, String> headers;
  
  private Thread connectReadThread;
  
  private Draft draft;
  
  private OutputStream ostream;
  
  public WebSocketClient(URI paramURI) {
    this(paramURI, (Draft)new Draft_6455());
  }
  
  public WebSocketClient(URI paramURI, Draft paramDraft) {
    this(paramURI, paramDraft, (Map<String, String>)null, 0);
  }
  
  public WebSocketClient(URI paramURI, Map<String, String> paramMap) {
    this(paramURI, (Draft)new Draft_6455(), paramMap);
  }
  
  public WebSocketClient(URI paramURI, Draft paramDraft, Map<String, String> paramMap) {
    this(paramURI, paramDraft, paramMap, 0);
  }
  
  public WebSocketClient(URI paramURI, Draft paramDraft, Map<String, String> paramMap, int paramInt) {
    if (paramURI == null)
      throw new IllegalArgumentException(); 
    if (paramDraft == null)
      throw new IllegalArgumentException("null as draft is permitted for `WebSocketServer` only!"); 
    this.uri = paramURI;
    this.draft = paramDraft;
    this.dnsResolver = new DnsResolver() {
        public InetAddress resolve(URI param1URI) throws UnknownHostException {
          return InetAddress.getByName(param1URI.getHost());
        }
      };
    if (paramMap != null) {
      this.headers = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
      this.headers.putAll(paramMap);
    } 
    this.connectTimeout = paramInt;
    setTcpNoDelay(false);
    setReuseAddr(false);
    this.engine = new WebSocketImpl((WebSocketListener)this, paramDraft);
  }
  
  public URI getURI() {
    return this.uri;
  }
  
  public Draft getDraft() {
    return this.draft;
  }
  
  public Socket getSocket() {
    return this.socket;
  }
  
  public void addHeader(String paramString1, String paramString2) {
    if (this.headers == null)
      this.headers = new TreeMap<>(String.CASE_INSENSITIVE_ORDER); 
    this.headers.put(paramString1, paramString2);
  }
  
  public String removeHeader(String paramString) {
    if (this.headers == null)
      return null; 
    return this.headers.remove(paramString);
  }
  
  public void clearHeaders() {
    this.headers = null;
  }
  
  public void setDnsResolver(DnsResolver paramDnsResolver) {
    this.dnsResolver = paramDnsResolver;
  }
  
  public void reconnect() {
    reset();
    connect();
  }
  
  public boolean reconnectBlocking() throws InterruptedException {
    reset();
    return connectBlocking();
  }
  
  private void reset() {
    Thread thread = Thread.currentThread();
    if (thread == this.writeThread || thread == this.connectReadThread)
      throw new IllegalStateException("You cannot initialize a reconnect out of the websocket thread. Use reconnect in another thread to ensure a successful cleanup."); 
    try {
      closeBlocking();
      if (this.writeThread != null) {
        this.writeThread.interrupt();
        this.writeThread = null;
      } 
      if (this.connectReadThread != null) {
        this.connectReadThread.interrupt();
        this.connectReadThread = null;
      } 
      this.draft.reset();
      if (this.socket != null) {
        this.socket.close();
        this.socket = null;
      } 
    } catch (Exception exception) {
      onError(exception);
      this.engine.closeConnection(1006, exception.getMessage());
      return;
    } 
    this.connectLatch = new CountDownLatch(1);
    this.closeLatch = new CountDownLatch(1);
    this.engine = new WebSocketImpl((WebSocketListener)this, this.draft);
  }
  
  public void connect() {
    if (this.connectReadThread != null)
      throw new IllegalStateException("WebSocketClient objects are not reuseable"); 
    this.connectReadThread = new Thread(this);
    this.connectReadThread.setName("WebSocketConnectReadThread-" + this.connectReadThread.getId());
    this.connectReadThread.start();
  }
  
  public boolean connectBlocking() throws InterruptedException {
    connect();
    this.connectLatch.await();
    return this.engine.isOpen();
  }
  
  public boolean connectBlocking(long paramLong, TimeUnit paramTimeUnit) throws InterruptedException {
    connect();
    return (this.connectLatch.await(paramLong, paramTimeUnit) && this.engine.isOpen());
  }
  
  public void close() {
    if (this.writeThread != null)
      this.engine.close(1000); 
  }
  
  public void closeBlocking() throws InterruptedException {
    close();
    this.closeLatch.await();
  }
  
  public void send(String paramString) {
    this.engine.send(paramString);
  }
  
  public void send(byte[] paramArrayOfbyte) {
    this.engine.send(paramArrayOfbyte);
  }
  
  public <T> T getAttachment() {
    return (T)this.engine.getAttachment();
  }
  
  public <T> void setAttachment(T paramT) {
    this.engine.setAttachment(paramT);
  }
  
  protected Collection<WebSocket> getConnections() {
    return (Collection)Collections.singletonList(this.engine);
  }
  
  public void sendPing() {
    this.engine.sendPing();
  }
  
  public void run() {
    InputStream inputStream;
    try {
      boolean bool = prepareSocket();
      this.socket.setTcpNoDelay(isTcpNoDelay());
      this.socket.setReuseAddress(isReuseAddr());
      if (!this.socket.isConnected()) {
        InetSocketAddress inetSocketAddress = (this.dnsResolver == null) ? InetSocketAddress.createUnresolved(this.uri.getHost(), getPort()) : new InetSocketAddress(this.dnsResolver.resolve(this.uri), getPort());
        this.socket.connect(inetSocketAddress, this.connectTimeout);
      } 
      if (bool && "wss".equals(this.uri.getScheme()))
        upgradeSocketToSSL(); 
      if (this.socket instanceof SSLSocket) {
        SSLSocket sSLSocket = (SSLSocket)this.socket;
        SSLParameters sSLParameters = sSLSocket.getSSLParameters();
        onSetSSLParameters(sSLParameters);
        sSLSocket.setSSLParameters(sSLParameters);
      } 
      inputStream = this.socket.getInputStream();
      this.ostream = this.socket.getOutputStream();
      sendHandshake();
    } catch (Exception exception) {
      onWebsocketError((WebSocket)this.engine, exception);
      this.engine.closeConnection(-1, exception.getMessage());
      return;
    } catch (InternalError internalError) {
      if (internalError.getCause() instanceof java.lang.reflect.InvocationTargetException && internalError.getCause()
        .getCause() instanceof IOException) {
        IOException iOException = (IOException)internalError.getCause().getCause();
        onWebsocketError((WebSocket)this.engine, iOException);
        this.engine.closeConnection(-1, iOException.getMessage());
        return;
      } 
      throw internalError;
    } 
    this.writeThread = new Thread(new WebsocketWriteThread(this));
    this.writeThread.start();
    byte[] arrayOfByte = new byte[16384];
    try {
      int i;
      while (!isClosing() && !isClosed() && (i = inputStream.read(arrayOfByte)) != -1)
        this.engine.decode(ByteBuffer.wrap(arrayOfByte, 0, i)); 
      this.engine.eot();
    } catch (IOException iOException) {
      handleIOException(iOException);
    } catch (RuntimeException runtimeException) {
      onError(runtimeException);
      this.engine.closeConnection(1006, runtimeException.getMessage());
    } 
    this.connectReadThread = null;
  }
  
  private void upgradeSocketToSSL() throws NoSuchAlgorithmException, KeyManagementException, IOException {
    SSLSocketFactory sSLSocketFactory;
    if (this.socketFactory instanceof SSLSocketFactory) {
      sSLSocketFactory = (SSLSocketFactory)this.socketFactory;
    } else {
      SSLContext sSLContext = SSLContext.getInstance("TLSv1.2");
      sSLContext.init(null, null, null);
      sSLSocketFactory = sSLContext.getSocketFactory();
    } 
    this.socket = sSLSocketFactory.createSocket(this.socket, this.uri.getHost(), getPort(), true);
  }
  
  private boolean prepareSocket() throws IOException {
    boolean bool = false;
    if (this.proxy != Proxy.NO_PROXY) {
      this.socket = new Socket(this.proxy);
      bool = true;
    } else if (this.socketFactory != null) {
      this.socket = this.socketFactory.createSocket();
    } else if (this.socket == null) {
      this.socket = new Socket(this.proxy);
      bool = true;
    } else if (this.socket.isClosed()) {
      throw new IOException();
    } 
    return bool;
  }
  
  protected void onSetSSLParameters(SSLParameters paramSSLParameters) {
    paramSSLParameters.setEndpointIdentificationAlgorithm("HTTPS");
  }
  
  private int getPort() {
    int i = this.uri.getPort();
    String str = this.uri.getScheme();
    if ("wss".equals(str))
      return (i == -1) ? 443 : i; 
    if ("ws".equals(str))
      return (i == -1) ? 80 : i; 
    throw new IllegalArgumentException("unknown scheme: " + str);
  }
  
  private void sendHandshake() throws InvalidHandshakeException {
    String str1, str2 = this.uri.getRawPath();
    String str3 = this.uri.getRawQuery();
    if (str2 == null || str2.length() == 0) {
      str1 = "/";
    } else {
      str1 = str2;
    } 
    if (str3 != null)
      str1 = str1 + '?' + str3; 
    int i = getPort();
    String str4 = this.uri.getHost() + ((i != 80 && i != 443) ? (":" + i) : "");
    HandshakeImpl1Client handshakeImpl1Client = new HandshakeImpl1Client();
    handshakeImpl1Client.setResourceDescriptor(str1);
    handshakeImpl1Client.put("Host", str4);
    if (this.headers != null)
      for (Map.Entry<String, String> entry : this.headers.entrySet())
        handshakeImpl1Client.put((String)entry.getKey(), (String)entry.getValue());  
    this.engine.startHandshake((ClientHandshakeBuilder)handshakeImpl1Client);
  }
  
  public ReadyState getReadyState() {
    return this.engine.getReadyState();
  }
  
  public final void onWebsocketMessage(WebSocket paramWebSocket, String paramString) {
    onMessage(paramString);
  }
  
  public final void onWebsocketMessage(WebSocket paramWebSocket, ByteBuffer paramByteBuffer) {
    onMessage(paramByteBuffer);
  }
  
  public final void onWebsocketOpen(WebSocket paramWebSocket, Handshakedata paramHandshakedata) {
    startConnectionLostTimer();
    onOpen((ServerHandshake)paramHandshakedata);
    this.connectLatch.countDown();
  }
  
  public final void onWebsocketClose(WebSocket paramWebSocket, int paramInt, String paramString, boolean paramBoolean) {
    stopConnectionLostTimer();
    if (this.writeThread != null)
      this.writeThread.interrupt(); 
    onClose(paramInt, paramString, paramBoolean);
    this.connectLatch.countDown();
    this.closeLatch.countDown();
  }
  
  public final void onWebsocketError(WebSocket paramWebSocket, Exception paramException) {
    onError(paramException);
  }
  
  public final void onWriteDemand(WebSocket paramWebSocket) {}
  
  public void onWebsocketCloseInitiated(WebSocket paramWebSocket, int paramInt, String paramString) {
    onCloseInitiated(paramInt, paramString);
  }
  
  public void onWebsocketClosing(WebSocket paramWebSocket, int paramInt, String paramString, boolean paramBoolean) {
    onClosing(paramInt, paramString, paramBoolean);
  }
  
  public void onCloseInitiated(int paramInt, String paramString) {}
  
  public void onClosing(int paramInt, String paramString, boolean paramBoolean) {}
  
  public WebSocket getConnection() {
    return (WebSocket)this.engine;
  }
  
  public InetSocketAddress getLocalSocketAddress(WebSocket paramWebSocket) {
    if (this.socket != null)
      return (InetSocketAddress)this.socket.getLocalSocketAddress(); 
    return null;
  }
  
  public InetSocketAddress getRemoteSocketAddress(WebSocket paramWebSocket) {
    if (this.socket != null)
      return (InetSocketAddress)this.socket.getRemoteSocketAddress(); 
    return null;
  }
  
  public void onMessage(ByteBuffer paramByteBuffer) {}
  
  private class WebsocketWriteThread implements Runnable {
    private final WebSocketClient webSocketClient;
    
    WebsocketWriteThread(WebSocketClient param1WebSocketClient1) {
      this.webSocketClient = param1WebSocketClient1;
    }
    
    public void run() {
      Thread.currentThread().setName("WebSocketWriteThread-" + Thread.currentThread().getId());
      try {
        runWriteData();
      } catch (IOException iOException) {
        WebSocketClient.this.handleIOException(iOException);
      } finally {
        closeSocket();
        WebSocketClient.this.writeThread = null;
      } 
    }
    
    private void runWriteData() throws IOException {
      try {
        while (!Thread.interrupted()) {
          ByteBuffer byteBuffer = WebSocketClient.this.engine.outQueue.take();
          WebSocketClient.this.ostream.write(byteBuffer.array(), 0, byteBuffer.limit());
          WebSocketClient.this.ostream.flush();
        } 
      } catch (InterruptedException interruptedException) {
        for (ByteBuffer byteBuffer : WebSocketClient.this.engine.outQueue) {
          WebSocketClient.this.ostream.write(byteBuffer.array(), 0, byteBuffer.limit());
          WebSocketClient.this.ostream.flush();
        } 
        Thread.currentThread().interrupt();
      } 
    }
    
    private void closeSocket() {
      try {
        if (WebSocketClient.this.socket != null)
          WebSocketClient.this.socket.close(); 
      } catch (IOException iOException) {
        WebSocketClient.this.onWebsocketError(this.webSocketClient, iOException);
      } 
    }
  }
  
  public void setProxy(Proxy paramProxy) {
    if (paramProxy == null)
      throw new IllegalArgumentException(); 
    this.proxy = paramProxy;
  }
  
  @Deprecated
  public void setSocket(Socket paramSocket) {
    if (this.socket != null)
      throw new IllegalStateException("socket has already been set"); 
    this.socket = paramSocket;
  }
  
  public void setSocketFactory(SocketFactory paramSocketFactory) {
    this.socketFactory = paramSocketFactory;
  }
  
  public void sendFragmentedFrame(Opcode paramOpcode, ByteBuffer paramByteBuffer, boolean paramBoolean) {
    this.engine.sendFragmentedFrame(paramOpcode, paramByteBuffer, paramBoolean);
  }
  
  public boolean isOpen() {
    return this.engine.isOpen();
  }
  
  public boolean isFlushAndClose() {
    return this.engine.isFlushAndClose();
  }
  
  public boolean isClosed() {
    return this.engine.isClosed();
  }
  
  public boolean isClosing() {
    return this.engine.isClosing();
  }
  
  public boolean hasBufferedData() {
    return this.engine.hasBufferedData();
  }
  
  public void close(int paramInt) {
    this.engine.close(paramInt);
  }
  
  public void close(int paramInt, String paramString) {
    this.engine.close(paramInt, paramString);
  }
  
  public void closeConnection(int paramInt, String paramString) {
    this.engine.closeConnection(paramInt, paramString);
  }
  
  public void send(ByteBuffer paramByteBuffer) {
    this.engine.send(paramByteBuffer);
  }
  
  public void sendFrame(Framedata paramFramedata) {
    this.engine.sendFrame(paramFramedata);
  }
  
  public void sendFrame(Collection<Framedata> paramCollection) {
    this.engine.sendFrame(paramCollection);
  }
  
  public InetSocketAddress getLocalSocketAddress() {
    return this.engine.getLocalSocketAddress();
  }
  
  public InetSocketAddress getRemoteSocketAddress() {
    return this.engine.getRemoteSocketAddress();
  }
  
  public String getResourceDescriptor() {
    return this.uri.getPath();
  }
  
  public boolean hasSSLSupport() {
    return this.socket instanceof SSLSocket;
  }
  
  public SSLSession getSSLSession() {
    if (!hasSSLSupport())
      throw new IllegalArgumentException("This websocket uses ws instead of wss. No SSLSession available."); 
    return ((SSLSocket)this.socket).getSession();
  }
  
  public IProtocol getProtocol() {
    return this.engine.getProtocol();
  }
  
  private void handleIOException(IOException paramIOException) {
    if (paramIOException instanceof javax.net.ssl.SSLException)
      onError(paramIOException); 
    this.engine.eot();
  }
  
  public abstract void onClose(int paramInt, String paramString, boolean paramBoolean);
  
  public abstract void onOpen(ServerHandshake paramServerHandshake);
  
  public abstract void onError(Exception paramException);
  
  public abstract void onMessage(String paramString);
}
