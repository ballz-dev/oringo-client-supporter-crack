package org.java_websocket.handshake;

public interface ServerHandshake extends Handshakedata {
  String getHttpStatusMessage();
  
  short getHttpStatus();
}
