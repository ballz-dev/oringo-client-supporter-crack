package org.java_websocket.util;

import java.nio.ByteBuffer;

public class ByteBufferUtils {
  public static int transferByteBuffer(ByteBuffer paramByteBuffer1, ByteBuffer paramByteBuffer2) {
    if (paramByteBuffer1 == null || paramByteBuffer2 == null)
      throw new IllegalArgumentException(); 
    int i = paramByteBuffer1.remaining();
    int j = paramByteBuffer2.remaining();
    if (i > j) {
      int k = Math.min(i, j);
      paramByteBuffer1.limit(k);
      paramByteBuffer2.put(paramByteBuffer1);
      return k;
    } 
    paramByteBuffer2.put(paramByteBuffer1);
    return i;
  }
  
  public static ByteBuffer getEmptyByteBuffer() {
    return ByteBuffer.allocate(0);
  }
}
