package org.java_websocket.enums;

public enum ReadyState {
  CLOSING, OPEN, CLOSED, NOT_YET_CONNECTED;
  
  static {
    OPEN = new ReadyState("OPEN", 1);
    CLOSING = new ReadyState("CLOSING", 2);
    CLOSED = new ReadyState("CLOSED", 3);
    $VALUES = new ReadyState[] { NOT_YET_CONNECTED, OPEN, CLOSING, CLOSED };
  }
}
