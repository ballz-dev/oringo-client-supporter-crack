package org.java_websocket.framing;

import java.nio.ByteBuffer;
import org.java_websocket.enums.Opcode;
import org.java_websocket.exceptions.InvalidDataException;
import org.java_websocket.exceptions.InvalidFrameException;
import org.java_websocket.util.ByteBufferUtils;
import org.java_websocket.util.Charsetfunctions;

public class CloseFrame extends ControlFrame {
  public static final int PROTOCOL_ERROR = 1002;
  
  public static final int GOING_AWAY = 1001;
  
  public static final int ABNORMAL_CLOSE = 1006;
  
  private String reason;
  
  public static final int REFUSE = 1003;
  
  public static final int TOOBIG = 1009;
  
  public static final int NEVER_CONNECTED = -1;
  
  public static final int POLICY_VALIDATION = 1008;
  
  public static final int NO_UTF8 = 1007;
  
  public static final int TRY_AGAIN_LATER = 1013;
  
  private int code;
  
  public static final int BAD_GATEWAY = 1014;
  
  public static final int NOCODE = 1005;
  
  public static final int NORMAL = 1000;
  
  public static final int BUGGYCLOSE = -2;
  
  public static final int TLS_ERROR = 1015;
  
  public static final int FLASHPOLICY = -3;
  
  public static final int SERVICE_RESTART = 1012;
  
  public static final int EXTENSION = 1010;
  
  public static final int UNEXPECTED_CONDITION = 1011;
  
  public CloseFrame() {
    super(Opcode.CLOSING);
    setReason("");
    setCode(1000);
  }
  
  public void setCode(int paramInt) {
    this.code = paramInt;
    if (paramInt == 1015) {
      this.code = 1005;
      this.reason = "";
    } 
    updatePayload();
  }
  
  public void setReason(String paramString) {
    if (paramString == null)
      paramString = ""; 
    this.reason = paramString;
    updatePayload();
  }
  
  public int getCloseCode() {
    return this.code;
  }
  
  public String getMessage() {
    return this.reason;
  }
  
  public String toString() {
    return super.toString() + "code: " + this.code;
  }
  
  public void isValid() throws InvalidDataException {
    super.isValid();
    if (this.code == 1007 && this.reason.isEmpty())
      throw new InvalidDataException(1007, "Received text is no valid utf8 string!"); 
    if (this.code == 1005 && 0 < this.reason.length())
      throw new InvalidDataException(1002, "A close frame must have a closecode if it has a reason"); 
    if (this.code > 1015 && this.code < 3000)
      throw new InvalidDataException(1002, "Trying to send an illegal close code!"); 
    if (this.code == 1006 || this.code == 1015 || this.code == 1005 || this.code > 4999 || this.code < 1000 || this.code == 1004)
      throw new InvalidFrameException("closecode must not be sent over the wire: " + this.code); 
  }
  
  public void setPayload(ByteBuffer paramByteBuffer) {
    this.code = 1005;
    this.reason = "";
    paramByteBuffer.mark();
    if (paramByteBuffer.remaining() == 0) {
      this.code = 1000;
    } else if (paramByteBuffer.remaining() == 1) {
      this.code = 1002;
    } else {
      if (paramByteBuffer.remaining() >= 2) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(4);
        byteBuffer.position(2);
        byteBuffer.putShort(paramByteBuffer.getShort());
        byteBuffer.position(0);
        this.code = byteBuffer.getInt();
      } 
      paramByteBuffer.reset();
      try {
        int i = paramByteBuffer.position();
        validateUtf8(paramByteBuffer, i);
      } catch (InvalidDataException invalidDataException) {
        this.code = 1007;
        this.reason = null;
      } 
    } 
  }
  
  private void validateUtf8(ByteBuffer paramByteBuffer, int paramInt) throws InvalidDataException {
    try {
      paramByteBuffer.position(paramByteBuffer.position() + 2);
      this.reason = Charsetfunctions.stringUtf8(paramByteBuffer);
    } catch (IllegalArgumentException illegalArgumentException) {
      throw new InvalidDataException(1007);
    } finally {
      paramByteBuffer.position(paramInt);
    } 
  }
  
  private void updatePayload() {
    byte[] arrayOfByte = Charsetfunctions.utf8Bytes(this.reason);
    ByteBuffer byteBuffer1 = ByteBuffer.allocate(4);
    byteBuffer1.putInt(this.code);
    byteBuffer1.position(2);
    ByteBuffer byteBuffer2 = ByteBuffer.allocate(2 + arrayOfByte.length);
    byteBuffer2.put(byteBuffer1);
    byteBuffer2.put(arrayOfByte);
    byteBuffer2.rewind();
    super.setPayload(byteBuffer2);
  }
  
  public ByteBuffer getPayloadData() {
    if (this.code == 1005)
      return ByteBufferUtils.getEmptyByteBuffer(); 
    return super.getPayloadData();
  }
  
  public boolean equals(Object paramObject) {
    if (this == paramObject)
      return true; 
    if (paramObject == null || getClass() != paramObject.getClass())
      return false; 
    if (!super.equals(paramObject))
      return false; 
    CloseFrame closeFrame = (CloseFrame)paramObject;
    if (this.code != closeFrame.code)
      return false; 
    return (this.reason != null) ? this.reason.equals(closeFrame.reason) : ((closeFrame.reason == null));
  }
  
  public int hashCode() {
    int i = super.hashCode();
    i = 31 * i + this.code;
    i = 31 * i + ((this.reason != null) ? this.reason.hashCode() : 0);
    return i;
  }
}
