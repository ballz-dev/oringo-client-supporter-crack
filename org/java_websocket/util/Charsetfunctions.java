package org.java_websocket.util;

import java.nio.ByteBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CodingErrorAction;
import java.nio.charset.StandardCharsets;
import org.java_websocket.exceptions.InvalidDataException;

public class Charsetfunctions {
  private static final CodingErrorAction codingErrorAction = CodingErrorAction.REPORT;
  
  public static byte[] utf8Bytes(String paramString) {
    return paramString.getBytes(StandardCharsets.UTF_8);
  }
  
  public static byte[] asciiBytes(String paramString) {
    return paramString.getBytes(StandardCharsets.US_ASCII);
  }
  
  public static String stringAscii(byte[] paramArrayOfbyte) {
    return stringAscii(paramArrayOfbyte, 0, paramArrayOfbyte.length);
  }
  
  public static String stringAscii(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) {
    return new String(paramArrayOfbyte, paramInt1, paramInt2, StandardCharsets.US_ASCII);
  }
  
  public static String stringUtf8(byte[] paramArrayOfbyte) throws InvalidDataException {
    return stringUtf8(ByteBuffer.wrap(paramArrayOfbyte));
  }
  
  public static String stringUtf8(ByteBuffer paramByteBuffer) throws InvalidDataException {
    String str;
    CharsetDecoder charsetDecoder = StandardCharsets.UTF_8.newDecoder();
    charsetDecoder.onMalformedInput(codingErrorAction);
    charsetDecoder.onUnmappableCharacter(codingErrorAction);
    try {
      paramByteBuffer.mark();
      str = charsetDecoder.decode(paramByteBuffer).toString();
      paramByteBuffer.reset();
    } catch (CharacterCodingException characterCodingException) {
      throw new InvalidDataException(1007, characterCodingException);
    } 
    return str;
  }
  
  private static final int[] utf8d = new int[] { 
      0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 
      0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 
      0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 
      0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 
      0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 
      0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 
      0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 
      0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 
      0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 
      0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 
      0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 
      0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 
      0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 
      1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 
      1, 1, 1, 1, 9, 9, 9, 9, 9, 9, 
      9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 
      7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 
      7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 
      7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 
      7, 7, 8, 8, 2, 2, 2, 2, 2, 2, 
      2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 
      2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 
      2, 2, 2, 2, 10, 3, 3, 3, 3, 3, 
      3, 3, 3, 3, 3, 3, 3, 4, 3, 3, 
      11, 6, 6, 6, 5, 8, 8, 8, 8, 8, 
      8, 8, 8, 8, 8, 8, 0, 1, 2, 3, 
      5, 8, 7, 1, 1, 1, 4, 6, 1, 1, 
      1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 
      1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 
      1, 1, 1, 1, 1, 0, 1, 0, 1, 1, 
      1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 
      1, 2, 1, 2, 1, 1, 1, 1, 1, 1, 
      1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 
      1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 
      1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 
      1, 1, 1, 1, 1, 1, 1, 1, 1, 3, 
      1, 3, 1, 1, 1, 1, 1, 1, 1, 3, 
      1, 1, 1, 1, 1, 3, 1, 3, 1, 1, 
      1, 1, 1, 1, 1, 3, 1, 1, 1, 1, 
      1, 1, 1, 1, 1, 1, 1, 1, 1, 1 };
  
  public static boolean isValidUTF8(ByteBuffer paramByteBuffer, int paramInt) {
    int i = paramByteBuffer.remaining();
    if (i < paramInt)
      return false; 
    int j = 0;
    for (int k = paramInt; k < i; k++) {
      j = utf8d[256 + (j << 4) + utf8d[0xFF & paramByteBuffer.get(k)]];
      if (j == 1)
        return false; 
    } 
    return true;
  }
  
  public static boolean isValidUTF8(ByteBuffer paramByteBuffer) {
    return isValidUTF8(paramByteBuffer, 0);
  }
}
