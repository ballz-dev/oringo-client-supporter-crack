package org.java_websocket;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import org.java_websocket.util.NamedThreadFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractWebSocket extends WebSocketAdapter {
  private final Logger log = LoggerFactory.getLogger(AbstractWebSocket.class);
  
  private long connectionLostTimeout = TimeUnit.SECONDS.toNanos(60L);
  
  private boolean websocketRunning = false;
  
  private final Object syncConnectionLost = new Object();
  
  private boolean tcpNoDelay;
  
  private boolean reuseAddr;
  
  private ScheduledExecutorService connectionLostCheckerService;
  
  private ScheduledFuture<?> connectionLostCheckerFuture;
  
  public int getConnectionLostTimeout() {
    synchronized (this.syncConnectionLost) {
      return (int)TimeUnit.NANOSECONDS.toSeconds(this.connectionLostTimeout);
    } 
  }
  
  public void setConnectionLostTimeout(int paramInt) {
    synchronized (this.syncConnectionLost) {
      this.connectionLostTimeout = TimeUnit.SECONDS.toNanos(paramInt);
      if (this.connectionLostTimeout <= 0L) {
        this.log.trace("Connection lost timer stopped");
        cancelConnectionLostTimer();
        return;
      } 
      if (this.websocketRunning) {
        this.log.trace("Connection lost timer restarted");
        try {
          ArrayList<WebSocket> arrayList = new ArrayList<>(getConnections());
          for (WebSocket webSocket : arrayList) {
            if (webSocket instanceof WebSocketImpl) {
              WebSocketImpl webSocketImpl = (WebSocketImpl)webSocket;
              webSocketImpl.updateLastPong();
            } 
          } 
        } catch (Exception exception) {
          this.log.error("Exception during connection lost restart", exception);
        } 
        restartConnectionLostTimer();
      } 
    } 
  }
  
  protected void stopConnectionLostTimer() {
    synchronized (this.syncConnectionLost) {
      if (this.connectionLostCheckerService != null || this.connectionLostCheckerFuture != null) {
        this.websocketRunning = false;
        this.log.trace("Connection lost timer stopped");
        cancelConnectionLostTimer();
      } 
    } 
  }
  
  protected void startConnectionLostTimer() {
    synchronized (this.syncConnectionLost) {
      if (this.connectionLostTimeout <= 0L) {
        this.log.trace("Connection lost timer deactivated");
        return;
      } 
      this.log.trace("Connection lost timer started");
      this.websocketRunning = true;
      restartConnectionLostTimer();
    } 
  }
  
  private void restartConnectionLostTimer() {
    cancelConnectionLostTimer();
    this
      .connectionLostCheckerService = Executors.newSingleThreadScheduledExecutor((ThreadFactory)new NamedThreadFactory("connectionLostChecker"));
    Runnable runnable = new Runnable() {
        private ArrayList<WebSocket> connections = new ArrayList<>();
        
        public void run() {
          this.connections.clear();
          try {
            long l;
            this.connections.addAll(AbstractWebSocket.this.getConnections());
            synchronized (AbstractWebSocket.this.syncConnectionLost) {
              l = (long)(System.nanoTime() - AbstractWebSocket.this.connectionLostTimeout * 1.5D);
            } 
            for (WebSocket webSocket : this.connections)
              AbstractWebSocket.this.executeConnectionLostDetection(webSocket, l); 
          } catch (Exception exception) {}
          this.connections.clear();
        }
      };
    this
      .connectionLostCheckerFuture = this.connectionLostCheckerService.scheduleAtFixedRate(runnable, this.connectionLostTimeout, this.connectionLostTimeout, TimeUnit.NANOSECONDS);
  }
  
  private void executeConnectionLostDetection(WebSocket paramWebSocket, long paramLong) {
    if (!(paramWebSocket instanceof WebSocketImpl))
      return; 
    WebSocketImpl webSocketImpl = (WebSocketImpl)paramWebSocket;
    if (webSocketImpl.getLastPong() < paramLong) {
      this.log.trace("Closing connection due to no pong received: {}", webSocketImpl);
      webSocketImpl.closeConnection(1006, "The connection was closed because the other endpoint did not respond with a pong in time. For more information check: https://github.com/TooTallNate/Java-WebSocket/wiki/Lost-connection-detection");
    } else if (webSocketImpl.isOpen()) {
      webSocketImpl.sendPing();
    } else {
      this.log.trace("Trying to ping a non open connection: {}", webSocketImpl);
    } 
  }
  
  private void cancelConnectionLostTimer() {
    if (this.connectionLostCheckerService != null) {
      this.connectionLostCheckerService.shutdownNow();
      this.connectionLostCheckerService = null;
    } 
    if (this.connectionLostCheckerFuture != null) {
      this.connectionLostCheckerFuture.cancel(false);
      this.connectionLostCheckerFuture = null;
    } 
  }
  
  public boolean isTcpNoDelay() {
    return this.tcpNoDelay;
  }
  
  public void setTcpNoDelay(boolean paramBoolean) {
    this.tcpNoDelay = paramBoolean;
  }
  
  public boolean isReuseAddr() {
    return this.reuseAddr;
  }
  
  public void setReuseAddr(boolean paramBoolean) {
    this.reuseAddr = paramBoolean;
  }
  
  protected abstract Collection<WebSocket> getConnections();
}
