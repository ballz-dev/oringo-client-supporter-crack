package com.jagrosh.discordipc.entities.pipe;

public enum PipeStatus {
  CONNECTING, CLOSED, UNINITIALIZED, CONNECTED, CLOSING, DISCONNECTED;
  
  static {
    CONNECTING = new PipeStatus("CONNECTING", 1);
    CONNECTED = new PipeStatus("CONNECTED", 2);
    CLOSING = new PipeStatus("CLOSING", 3);
    CLOSED = new PipeStatus("CLOSED", 4);
    DISCONNECTED = new PipeStatus("DISCONNECTED", 5);
    $VALUES = new PipeStatus[] { UNINITIALIZED, CONNECTING, CONNECTED, CLOSING, CLOSED, DISCONNECTED };
  }
}
