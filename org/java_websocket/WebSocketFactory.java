package org.java_websocket;

import java.util.List;
import org.java_websocket.drafts.Draft;

public interface WebSocketFactory {
  WebSocket createWebSocket(WebSocketAdapter paramWebSocketAdapter, List<Draft> paramList);
  
  WebSocket createWebSocket(WebSocketAdapter paramWebSocketAdapter, Draft paramDraft);
}
