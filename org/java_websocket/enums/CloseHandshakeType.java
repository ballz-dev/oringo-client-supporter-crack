package org.java_websocket.enums;

public enum CloseHandshakeType {
  TWOWAY, NONE, ONEWAY;
  
  static {
    TWOWAY = new CloseHandshakeType("TWOWAY", 2);
    $VALUES = new CloseHandshakeType[] { NONE, ONEWAY, TWOWAY };
  }
}
