package org.java_websocket.server;

import java.io.IOException;
import java.nio.channels.ByteChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLParameters;
import org.java_websocket.SSLSocketChannel2;

public class SSLParametersWebSocketServerFactory extends DefaultSSLWebSocketServerFactory {
  private final SSLParameters sslParameters;
  
  public SSLParametersWebSocketServerFactory(SSLContext paramSSLContext, SSLParameters paramSSLParameters) {
    this(paramSSLContext, Executors.newSingleThreadScheduledExecutor(), paramSSLParameters);
  }
  
  public SSLParametersWebSocketServerFactory(SSLContext paramSSLContext, ExecutorService paramExecutorService, SSLParameters paramSSLParameters) {
    super(paramSSLContext, paramExecutorService);
    if (paramSSLParameters == null)
      throw new IllegalArgumentException(); 
    this.sslParameters = paramSSLParameters;
  }
  
  public ByteChannel wrapChannel(SocketChannel paramSocketChannel, SelectionKey paramSelectionKey) throws IOException {
    SSLEngine sSLEngine = this.sslcontext.createSSLEngine();
    sSLEngine.setUseClientMode(false);
    sSLEngine.setSSLParameters(this.sslParameters);
    return (ByteChannel)new SSLSocketChannel2(paramSocketChannel, sSLEngine, this.exec, paramSelectionKey);
  }
}
