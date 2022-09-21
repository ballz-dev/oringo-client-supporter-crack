package org.java_websocket.framing;

import java.nio.ByteBuffer;
import org.java_websocket.enums.Opcode;
import org.java_websocket.exceptions.InvalidDataException;
import org.java_websocket.util.ByteBufferUtils;

public abstract class FramedataImpl1 implements Framedata {
  private Opcode optcode;
  
  private boolean rsv1;
  
  private boolean rsv2;
  
  private boolean transferemasked;
  
  private ByteBuffer unmaskedpayload;
  
  private boolean fin;
  
  private boolean rsv3;
  
  public FramedataImpl1(Opcode paramOpcode) {
    this.optcode = paramOpcode;
    this.unmaskedpayload = ByteBufferUtils.getEmptyByteBuffer();
    this.fin = true;
    this.transferemasked = false;
    this.rsv1 = false;
    this.rsv2 = false;
    this.rsv3 = false;
  }
  
  public boolean isRSV1() {
    return this.rsv1;
  }
  
  public boolean isRSV2() {
    return this.rsv2;
  }
  
  public boolean isRSV3() {
    return this.rsv3;
  }
  
  public boolean isFin() {
    return this.fin;
  }
  
  public Opcode getOpcode() {
    return this.optcode;
  }
  
  public boolean getTransfereMasked() {
    return this.transferemasked;
  }
  
  public ByteBuffer getPayloadData() {
    return this.unmaskedpayload;
  }
  
  public void append(Framedata paramFramedata) {
    ByteBuffer byteBuffer = paramFramedata.getPayloadData();
    if (this.unmaskedpayload == null) {
      this.unmaskedpayload = ByteBuffer.allocate(byteBuffer.remaining());
      byteBuffer.mark();
      this.unmaskedpayload.put(byteBuffer);
      byteBuffer.reset();
    } else {
      byteBuffer.mark();
      this.unmaskedpayload.position(this.unmaskedpayload.limit());
      this.unmaskedpayload.limit(this.unmaskedpayload.capacity());
      if (byteBuffer.remaining() > this.unmaskedpayload.remaining()) {
        ByteBuffer byteBuffer1 = ByteBuffer.allocate(byteBuffer.remaining() + this.unmaskedpayload.capacity());
        this.unmaskedpayload.flip();
        byteBuffer1.put(this.unmaskedpayload);
        byteBuffer1.put(byteBuffer);
        this.unmaskedpayload = byteBuffer1;
      } else {
        this.unmaskedpayload.put(byteBuffer);
      } 
      this.unmaskedpayload.rewind();
      byteBuffer.reset();
    } 
    this.fin = paramFramedata.isFin();
  }
  
  public String toString() {
    return "Framedata{ opcode:" + getOpcode() + ", fin:" + isFin() + ", rsv1:" + isRSV1() + ", rsv2:" + 
      isRSV2() + ", rsv3:" + isRSV3() + ", payload length:[pos:" + this.unmaskedpayload
      .position() + ", len:" + this.unmaskedpayload.remaining() + "], payload:" + (
      (this.unmaskedpayload.remaining() > 1000) ? "(too big to display)" : new String(this.unmaskedpayload
        .array())) + '}';
  }
  
  public void setPayload(ByteBuffer paramByteBuffer) {
    this.unmaskedpayload = paramByteBuffer;
  }
  
  public void setFin(boolean paramBoolean) {
    this.fin = paramBoolean;
  }
  
  public void setRSV1(boolean paramBoolean) {
    this.rsv1 = paramBoolean;
  }
  
  public void setRSV2(boolean paramBoolean) {
    this.rsv2 = paramBoolean;
  }
  
  public void setRSV3(boolean paramBoolean) {
    this.rsv3 = paramBoolean;
  }
  
  public void setTransferemasked(boolean paramBoolean) {
    this.transferemasked = paramBoolean;
  }
  
  public static FramedataImpl1 get(Opcode paramOpcode) {
    if (paramOpcode == null)
      throw new IllegalArgumentException("Supplied opcode cannot be null"); 
    switch (paramOpcode) {
      case PING:
        return new PingFrame();
      case PONG:
        return new PongFrame();
      case TEXT:
        return new TextFrame();
      case BINARY:
        return new BinaryFrame();
      case CLOSING:
        return new CloseFrame();
      case CONTINUOUS:
        return new ContinuousFrame();
    } 
    throw new IllegalArgumentException("Supplied opcode is invalid");
  }
  
  public boolean equals(Object paramObject) {
    if (this == paramObject)
      return true; 
    if (paramObject == null || getClass() != paramObject.getClass())
      return false; 
    FramedataImpl1 framedataImpl1 = (FramedataImpl1)paramObject;
    if (this.fin != framedataImpl1.fin)
      return false; 
    if (this.transferemasked != framedataImpl1.transferemasked)
      return false; 
    if (this.rsv1 != framedataImpl1.rsv1)
      return false; 
    if (this.rsv2 != framedataImpl1.rsv2)
      return false; 
    if (this.rsv3 != framedataImpl1.rsv3)
      return false; 
    if (this.optcode != framedataImpl1.optcode)
      return false; 
    return (this.unmaskedpayload != null) ? this.unmaskedpayload.equals(framedataImpl1.unmaskedpayload) : ((framedataImpl1.unmaskedpayload == null));
  }
  
  public int hashCode() {
    int i = this.fin ? 1 : 0;
    i = 31 * i + this.optcode.hashCode();
    i = 31 * i + ((this.unmaskedpayload != null) ? this.unmaskedpayload.hashCode() : 0);
    i = 31 * i + (this.transferemasked ? 1 : 0);
    i = 31 * i + (this.rsv1 ? 1 : 0);
    i = 31 * i + (this.rsv2 ? 1 : 0);
    i = 31 * i + (this.rsv3 ? 1 : 0);
    return i;
  }
  
  public abstract void isValid() throws InvalidDataException;
}
