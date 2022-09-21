package org.java_websocket.framing;

import java.nio.ByteBuffer;
import org.java_websocket.enums.Opcode;

public interface Framedata {
  boolean getTransfereMasked();
  
  void append(Framedata paramFramedata);
  
  Opcode getOpcode();
  
  boolean isRSV1();
  
  boolean isFin();
  
  boolean isRSV3();
  
  ByteBuffer getPayloadData();
  
  boolean isRSV2();
}
