package com.jagrosh.discordipc.entities;

import java.nio.ByteBuffer;
import org.json.JSONObject;

public class Packet {
  private final JSONObject data;
  
  private final OpCode op;
  
  public byte[] toBytes() {
    byte[] arrayOfByte = this.data.toString().getBytes();
    ByteBuffer byteBuffer = ByteBuffer.allocate(arrayOfByte.length + 8);
    byteBuffer.putInt(Integer.reverseBytes(this.op.ordinal()));
    byteBuffer.putInt(Integer.reverseBytes(arrayOfByte.length));
    byteBuffer.put(arrayOfByte);
    return byteBuffer.array();
  }
  
  public String toString() {
    return String.valueOf((new StringBuilder()).append("Pkt:").append(getOp()).append(getJson().toString()));
  }
  
  public OpCode getOp() {
    return this.op;
  }
  
  public Packet(OpCode paramOpCode, JSONObject paramJSONObject) {
    this.op = paramOpCode;
    this.data = paramJSONObject;
  }
  
  public JSONObject getJson() {
    return this.data;
  }
  
  public enum OpCode {
    FRAME, CLOSE, PONG, PING, HANDSHAKE;
    
    static {
      PONG = new OpCode("PONG", 4);
      $VALUES = new OpCode[] { HANDSHAKE, FRAME, CLOSE, PING, PONG };
    }
  }
}
