package org.java_websocket.handshake;

public interface ServerHandshakeBuilder extends HandshakeBuilder, ServerHandshake {
  void setHttpStatusMessage(String paramString);
  
  void setHttpStatus(short paramShort);
}
