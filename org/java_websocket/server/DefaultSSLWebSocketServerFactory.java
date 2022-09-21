package org.java_websocket.server;

import java.io.IOException;
import java.nio.channels.ByteChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;
import org.java_websocket.SSLSocketChannel2;
import org.java_websocket.WebSocket;
import org.java_websocket.WebSocketAdapter;
import org.java_websocket.WebSocketImpl;
import org.java_websocket.WebSocketListener;
import org.java_websocket.WebSocketServerFactory;
import org.java_websocket.drafts.Draft;

public class DefaultSSLWebSocketServerFactory implements WebSocketServerFactory {
  protected ExecutorService exec;
  
  protected SSLContext sslcontext;
  
  public DefaultSSLWebSocketServerFactory(SSLContext paramSSLContext) {
    this(paramSSLContext, Executors.newSingleThreadScheduledExecutor());
  }
  
  public DefaultSSLWebSocketServerFactory(SSLContext paramSSLContext, ExecutorService paramExecutorService) {
    if (paramSSLContext == null || paramExecutorService == null)
      throw new IllegalArgumentException(); 
    this.sslcontext = paramSSLContext;
    this.exec = paramExecutorService;
  }
  
  public ByteChannel wrapChannel(SocketChannel paramSocketChannel, SelectionKey paramSelectionKey) throws IOException {
    SSLEngine sSLEngine = this.sslcontext.createSSLEngine();
    ArrayList arrayList = new ArrayList(Arrays.asList((Object[])sSLEngine.getEnabledCipherSuites()));
    arrayList.remove("TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256");
    sSLEngine.setEnabledCipherSuites((String[])arrayList.toArray((Object[])new String[arrayList.size()]));
    sSLEngine.setUseClientMode(false);
    return (ByteChannel)new SSLSocketChannel2(paramSocketChannel, sSLEngine, this.exec, paramSelectionKey);
  }
  
  public WebSocketImpl createWebSocket(WebSocketAdapter paramWebSocketAdapter, Draft paramDraft) {
    return new WebSocketImpl((WebSocketListener)paramWebSocketAdapter, paramDraft);
  }
  
  public WebSocketImpl createWebSocket(WebSocketAdapter paramWebSocketAdapter, List<Draft> paramList) {
    return new WebSocketImpl((WebSocketListener)paramWebSocketAdapter, paramList);
  }
  
  public void close() {
    this.exec.shutdown();
  }
}
