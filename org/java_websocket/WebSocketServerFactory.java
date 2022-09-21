package org.java_websocket;

import java.io.IOException;
import java.nio.channels.ByteChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.List;
import org.java_websocket.drafts.Draft;

public interface WebSocketServerFactory extends WebSocketFactory {
  WebSocketImpl createWebSocket(WebSocketAdapter paramWebSocketAdapter, List<Draft> paramList);
  
  WebSocketImpl createWebSocket(WebSocketAdapter paramWebSocketAdapter, Draft paramDraft);
  
  void close();
  
  ByteChannel wrapChannel(SocketChannel paramSocketChannel, SelectionKey paramSelectionKey) throws IOException;
}
