package org.java_websocket.enums;

public enum Opcode {
  PONG, PING, CLOSING, TEXT, BINARY, CONTINUOUS;
  
  static {
    TEXT = new Opcode("TEXT", 1);
    BINARY = new Opcode("BINARY", 2);
    PING = new Opcode("PING", 3);
    PONG = new Opcode("PONG", 4);
    CLOSING = new Opcode("CLOSING", 5);
    $VALUES = new Opcode[] { CONTINUOUS, TEXT, BINARY, PING, PONG, CLOSING };
  }
}
