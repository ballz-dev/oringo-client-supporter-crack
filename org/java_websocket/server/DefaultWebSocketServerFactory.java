package org.java_websocket.server;

import java.io.IOException;
import java.nio.channels.ByteChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.List;
import org.java_websocket.WebSocket;
import org.java_websocket.WebSocketAdapter;
import org.java_websocket.WebSocketImpl;
import org.java_websocket.WebSocketListener;
import org.java_websocket.WebSocketServerFactory;
import org.java_websocket.drafts.Draft;

public class DefaultWebSocketServerFactory implements WebSocketServerFactory {
  public WebSocketImpl createWebSocket(WebSocketAdapter paramWebSocketAdapter, Draft paramDraft) {
    return new WebSocketImpl((WebSocketListener)paramWebSocketAdapter, paramDraft);
  }
  
  public WebSocketImpl createWebSocket(WebSocketAdapter paramWebSocketAdapter, List<Draft> paramList) {
    return new WebSocketImpl((WebSocketListener)paramWebSocketAdapter, paramList);
  }
  
  public SocketChannel wrapChannel(SocketChannel paramSocketChannel, SelectionKey paramSelectionKey) {
    return paramSocketChannel;
  }
  
  public void close() {}
}
