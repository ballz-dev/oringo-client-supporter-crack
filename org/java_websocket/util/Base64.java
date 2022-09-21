package org.java_websocket.util;

import java.io.ByteArrayOutputStream;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.zip.GZIPOutputStream;

public class Base64 {
  private static final String PREFERRED_ENCODING = "US-ASCII";
  
  private static final byte EQUALS_SIGN = 61;
  
  private static final byte NEW_LINE = 10;
  
  private static final byte[] _STANDARD_DECODABET;
  
  public static final int GZIP = 2;
  
  private static final byte WHITE_SPACE_ENC = -5;
  
  public static final int DO_BREAK_LINES = 8;
  
  private static final int MAX_LINE_LENGTH = 76;
  
  private static final byte[] _ORDERED_ALPHABET;
  
  public static final int NO_OPTIONS = 0;
  
  private static final byte[] _STANDARD_ALPHABET = new byte[] { 
      65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 
      75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 
      85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 
      101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 
      111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 
      121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 
      56, 57, 43, 47 };
  
  public static final int ORDERED = 32;
  
  public static final int URL_SAFE = 16;
  
  private static final byte[] _URL_SAFE_DECODABET;
  
  public static final int ENCODE = 1;
  
  private static final byte[] _URL_SAFE_ALPHABET;
  
  private static final byte[] _ORDERED_DECODABET;
  
  static {
    _STANDARD_DECODABET = new byte[] { 
        -9, -9, -9, -9, -9, -9, -9, -9, -9, -5, 
        -5, -9, -9, -5, -9, -9, -9, -9, -9, -9, 
        -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, 
        -9, -9, -5, -9, -9, -9, -9, -9, -9, -9, 
        -9, -9, -9, 62, -9, -9, -9, 63, 52, 53, 
        54, 55, 56, 57, 58, 59, 60, 61, -9, -9, 
        -9, -1, -9, -9, -9, 0, 1, 2, 3, 4, 
        5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 
        15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 
        25, -9, -9, -9, -9, -9, -9, 26, 27, 28, 
        29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 
        39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 
        49, 50, 51, -9, -9, -9, -9, -9, -9, -9, 
        -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, 
        -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, 
        -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, 
        -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, 
        -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, 
        -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, 
        -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, 
        -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, 
        -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, 
        -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, 
        -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, 
        -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, 
        -9, -9, -9, -9, -9, -9 };
    _URL_SAFE_ALPHABET = new byte[] { 
        65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 
        75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 
        85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 
        101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 
        111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 
        121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 
        56, 57, 45, 95 };
    _URL_SAFE_DECODABET = new byte[] { 
        -9, -9, -9, -9, -9, -9, -9, -9, -9, -5, 
        -5, -9, -9, -5, -9, -9, -9, -9, -9, -9, 
        -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, 
        -9, -9, -5, -9, -9, -9, -9, -9, -9, -9, 
        -9, -9, -9, -9, -9, 62, -9, -9, 52, 53, 
        54, 55, 56, 57, 58, 59, 60, 61, -9, -9, 
        -9, -1, -9, -9, -9, 0, 1, 2, 3, 4, 
        5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 
        15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 
        25, -9, -9, -9, -9, 63, -9, 26, 27, 28, 
        29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 
        39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 
        49, 50, 51, -9, -9, -9, -9, -9, -9, -9, 
        -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, 
        -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, 
        -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, 
        -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, 
        -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, 
        -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, 
        -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, 
        -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, 
        -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, 
        -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, 
        -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, 
        -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, 
        -9, -9, -9, -9, -9, -9 };
    _ORDERED_ALPHABET = new byte[] { 
        45, 48, 49, 50, 51, 52, 53, 54, 55, 56, 
        57, 65, 66, 67, 68, 69, 70, 71, 72, 73, 
        74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 
        84, 85, 86, 87, 88, 89, 90, 95, 97, 98, 
        99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 
        109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 
        119, 120, 121, 122 };
    _ORDERED_DECODABET = new byte[] { 
        -9, -9, -9, -9, -9, -9, -9, -9, -9, -5, 
        -5, -9, -9, -5, -9, -9, -9, -9, -9, -9, 
        -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, 
        -9, -9, -5, -9, -9, -9, -9, -9, -9, -9, 
        -9, -9, -9, -9, -9, 0, -9, -9, 1, 2, 
        3, 4, 5, 6, 7, 8, 9, 10, -9, -9, 
        -9, -1, -9, -9, -9, 11, 12, 13, 14, 15, 
        16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 
        26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 
        36, -9, -9, -9, -9, 37, -9, 38, 39, 40, 
        41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 
        51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 
        61, 62, 63, -9, -9, -9, -9, -9, -9, -9, 
        -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, 
        -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, 
        -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, 
        -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, 
        -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, 
        -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, 
        -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, 
        -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, 
        -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, 
        -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, 
        -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, 
        -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, 
        -9, -9, -9, -9, -9, -9, -9 };
  }
  
  private static final byte[] getAlphabet(int paramInt) {
    if ((paramInt & 0x10) == 16)
      return _URL_SAFE_ALPHABET; 
    if ((paramInt & 0x20) == 32)
      return _ORDERED_ALPHABET; 
    return _STANDARD_ALPHABET;
  }
  
  private static final byte[] getDecodabet(int paramInt) {
    if ((paramInt & 0x10) == 16)
      return _URL_SAFE_DECODABET; 
    if ((paramInt & 0x20) == 32)
      return _ORDERED_DECODABET; 
    return _STANDARD_DECODABET;
  }
  
  private static byte[] encode3to4(byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2, int paramInt1, int paramInt2) {
    encode3to4(paramArrayOfbyte2, 0, paramInt1, paramArrayOfbyte1, 0, paramInt2);
    return paramArrayOfbyte1;
  }
  
  private static byte[] encode3to4(byte[] paramArrayOfbyte1, int paramInt1, int paramInt2, byte[] paramArrayOfbyte2, int paramInt3, int paramInt4) {
    byte[] arrayOfByte = getAlphabet(paramInt4);
    int i = ((paramInt2 > 0) ? (paramArrayOfbyte1[paramInt1] << 24 >>> 8) : 0) | ((paramInt2 > 1) ? (paramArrayOfbyte1[paramInt1 + 1] << 24 >>> 16) : 0) | ((paramInt2 > 2) ? (paramArrayOfbyte1[paramInt1 + 2] << 24 >>> 24) : 0);
    switch (paramInt2) {
      case 3:
        paramArrayOfbyte2[paramInt3] = arrayOfByte[i >>> 18];
        paramArrayOfbyte2[paramInt3 + 1] = arrayOfByte[i >>> 12 & 0x3F];
        paramArrayOfbyte2[paramInt3 + 2] = arrayOfByte[i >>> 6 & 0x3F];
        paramArrayOfbyte2[paramInt3 + 3] = arrayOfByte[i & 0x3F];
        return paramArrayOfbyte2;
      case 2:
        paramArrayOfbyte2[paramInt3] = arrayOfByte[i >>> 18];
        paramArrayOfbyte2[paramInt3 + 1] = arrayOfByte[i >>> 12 & 0x3F];
        paramArrayOfbyte2[paramInt3 + 2] = arrayOfByte[i >>> 6 & 0x3F];
        paramArrayOfbyte2[paramInt3 + 3] = 61;
        return paramArrayOfbyte2;
      case 1:
        paramArrayOfbyte2[paramInt3] = arrayOfByte[i >>> 18];
        paramArrayOfbyte2[paramInt3 + 1] = arrayOfByte[i >>> 12 & 0x3F];
        paramArrayOfbyte2[paramInt3 + 2] = 61;
        paramArrayOfbyte2[paramInt3 + 3] = 61;
        return paramArrayOfbyte2;
    } 
    return paramArrayOfbyte2;
  }
  
  public static String encodeBytes(byte[] paramArrayOfbyte) {
    String str = null;
    try {
      str = encodeBytes(paramArrayOfbyte, 0, paramArrayOfbyte.length, 0);
    } catch (IOException iOException) {
      assert false : iOException.getMessage();
    } 
    assert str != null;
    return str;
  }
  
  public static String encodeBytes(byte[] paramArrayOfbyte, int paramInt1, int paramInt2, int paramInt3) throws IOException {
    byte[] arrayOfByte = encodeBytesToBytes(paramArrayOfbyte, paramInt1, paramInt2, paramInt3);
    try {
      return new String(arrayOfByte, "US-ASCII");
    } catch (UnsupportedEncodingException unsupportedEncodingException) {
      return new String(arrayOfByte);
    } 
  }
  
  public static byte[] encodeBytesToBytes(byte[] paramArrayOfbyte, int paramInt1, int paramInt2, int paramInt3) throws IOException {
    if (paramArrayOfbyte == null)
      throw new IllegalArgumentException("Cannot serialize a null array."); 
    if (paramInt1 < 0)
      throw new IllegalArgumentException("Cannot have negative offset: " + paramInt1); 
    if (paramInt2 < 0)
      throw new IllegalArgumentException("Cannot have length offset: " + paramInt2); 
    if (paramInt1 + paramInt2 > paramArrayOfbyte.length)
      throw new IllegalArgumentException(
          
          String.format("Cannot have offset of %d and length of %d with array of length %d", new Object[] { Integer.valueOf(paramInt1), Integer.valueOf(paramInt2), 
              Integer.valueOf(paramArrayOfbyte.length) })); 
    if ((paramInt3 & 0x2) != 0) {
      ByteArrayOutputStream byteArrayOutputStream = null;
      GZIPOutputStream gZIPOutputStream = null;
      OutputStream outputStream = null;
      try {
        byteArrayOutputStream = new ByteArrayOutputStream();
        outputStream = new OutputStream(byteArrayOutputStream, 0x1 | paramInt3);
        gZIPOutputStream = new GZIPOutputStream(outputStream);
        gZIPOutputStream.write(paramArrayOfbyte, paramInt1, paramInt2);
        gZIPOutputStream.close();
      } catch (IOException iOException) {
        throw iOException;
      } finally {
        try {
          if (gZIPOutputStream != null)
            gZIPOutputStream.close(); 
        } catch (Exception exception) {}
        try {
          if (outputStream != null)
            outputStream.close(); 
        } catch (Exception exception) {}
        try {
          if (byteArrayOutputStream != null)
            byteArrayOutputStream.close(); 
        } catch (Exception exception) {}
      } 
      return byteArrayOutputStream.toByteArray();
    } 
    boolean bool = ((paramInt3 & 0x8) != 0) ? true : false;
    int i = paramInt2 / 3 * 4 + ((paramInt2 % 3 > 0) ? 4 : 0);
    if (bool)
      i += i / 76; 
    byte[] arrayOfByte = new byte[i];
    byte b1 = 0;
    byte b2 = 0;
    int j = paramInt2 - 2;
    byte b3 = 0;
    for (; b1 < j; b1 += 3, b2 += 4) {
      encode3to4(paramArrayOfbyte, b1 + paramInt1, 3, arrayOfByte, b2, paramInt3);
      b3 += true;
      if (bool && b3 >= 76) {
        arrayOfByte[b2 + 4] = 10;
        b2++;
        b3 = 0;
      } 
    } 
    if (b1 < paramInt2) {
      encode3to4(paramArrayOfbyte, b1 + paramInt1, paramInt2 - b1, arrayOfByte, b2, paramInt3);
      b2 += 4;
    } 
    if (b2 <= arrayOfByte.length - 1) {
      byte[] arrayOfByte1 = new byte[b2];
      System.arraycopy(arrayOfByte, 0, arrayOfByte1, 0, b2);
      return arrayOfByte1;
    } 
    return arrayOfByte;
  }
  
  private static int decode4to3(byte[] paramArrayOfbyte1, int paramInt1, byte[] paramArrayOfbyte2, int paramInt2, int paramInt3) {
    if (paramArrayOfbyte1 == null)
      throw new IllegalArgumentException("Source array was null."); 
    if (paramArrayOfbyte2 == null)
      throw new IllegalArgumentException("Destination array was null."); 
    if (paramInt1 < 0 || paramInt1 + 3 >= paramArrayOfbyte1.length)
      throw new IllegalArgumentException(String.format("Source array with length %d cannot have offset of %d and still process four bytes.", new Object[] { Integer.valueOf(paramArrayOfbyte1.length), Integer.valueOf(paramInt1) })); 
    if (paramInt2 < 0 || paramInt2 + 2 >= paramArrayOfbyte2.length)
      throw new IllegalArgumentException(String.format("Destination array with length %d cannot have offset of %d and still store three bytes.", new Object[] { Integer.valueOf(paramArrayOfbyte2.length), Integer.valueOf(paramInt2) })); 
    byte[] arrayOfByte = getDecodabet(paramInt3);
    if (paramArrayOfbyte1[paramInt1 + 2] == 61) {
      int j = (arrayOfByte[paramArrayOfbyte1[paramInt1]] & 0xFF) << 18 | (arrayOfByte[paramArrayOfbyte1[paramInt1 + 1]] & 0xFF) << 12;
      paramArrayOfbyte2[paramInt2] = (byte)(j >>> 16);
      return 1;
    } 
    if (paramArrayOfbyte1[paramInt1 + 3] == 61) {
      int j = (arrayOfByte[paramArrayOfbyte1[paramInt1]] & 0xFF) << 18 | (arrayOfByte[paramArrayOfbyte1[paramInt1 + 1]] & 0xFF) << 12 | (arrayOfByte[paramArrayOfbyte1[paramInt1 + 2]] & 0xFF) << 6;
      paramArrayOfbyte2[paramInt2] = (byte)(j >>> 16);
      paramArrayOfbyte2[paramInt2 + 1] = (byte)(j >>> 8);
      return 2;
    } 
    int i = (arrayOfByte[paramArrayOfbyte1[paramInt1]] & 0xFF) << 18 | (arrayOfByte[paramArrayOfbyte1[paramInt1 + 1]] & 0xFF) << 12 | (arrayOfByte[paramArrayOfbyte1[paramInt1 + 2]] & 0xFF) << 6 | arrayOfByte[paramArrayOfbyte1[paramInt1 + 3]] & 0xFF;
    paramArrayOfbyte2[paramInt2] = (byte)(i >> 16);
    paramArrayOfbyte2[paramInt2 + 1] = (byte)(i >> 8);
    paramArrayOfbyte2[paramInt2 + 2] = (byte)i;
    return 3;
  }
  
  public static class OutputStream extends FilterOutputStream {
    private int position;
    
    private int options;
    
    private boolean suspendEncoding;
    
    private byte[] buffer;
    
    private boolean encode;
    
    private byte[] b4;
    
    private int lineLength;
    
    private int bufferLength;
    
    private boolean breakLines;
    
    private byte[] decodabet;
    
    public OutputStream(java.io.OutputStream param1OutputStream) {
      this(param1OutputStream, 1);
    }
    
    public OutputStream(java.io.OutputStream param1OutputStream, int param1Int) {
      super(param1OutputStream);
      this.breakLines = ((param1Int & 0x8) != 0);
      this.encode = ((param1Int & 0x1) != 0);
      this.bufferLength = this.encode ? 3 : 4;
      this.buffer = new byte[this.bufferLength];
      this.position = 0;
      this.lineLength = 0;
      this.suspendEncoding = false;
      this.b4 = new byte[4];
      this.options = param1Int;
      this.decodabet = Base64.getDecodabet(param1Int);
    }
    
    public void write(int param1Int) throws IOException {
      if (this.suspendEncoding) {
        this.out.write(param1Int);
        return;
      } 
      if (this.encode) {
        this.buffer[this.position++] = (byte)param1Int;
        if (this.position >= this.bufferLength) {
          this.out.write(Base64.encode3to4(this.b4, this.buffer, this.bufferLength, this.options));
          this.lineLength += 4;
          if (this.breakLines && this.lineLength >= 76) {
            this.out.write(10);
            this.lineLength = 0;
          } 
          this.position = 0;
        } 
      } else if (this.decodabet[param1Int & 0x7F] > -5) {
        this.buffer[this.position++] = (byte)param1Int;
        if (this.position >= this.bufferLength) {
          int i = Base64.decode4to3(this.buffer, 0, this.b4, 0, this.options);
          this.out.write(this.b4, 0, i);
          this.position = 0;
        } 
      } else if (this.decodabet[param1Int & 0x7F] != -5) {
        throw new IOException("Invalid character in Base64 data.");
      } 
    }
    
    public void write(byte[] param1ArrayOfbyte, int param1Int1, int param1Int2) throws IOException {
      if (this.suspendEncoding) {
        this.out.write(param1ArrayOfbyte, param1Int1, param1Int2);
        return;
      } 
      for (byte b = 0; b < param1Int2; b++)
        write(param1ArrayOfbyte[param1Int1 + b]); 
    }
    
    public void flushBase64() throws IOException {
      if (this.position > 0)
        if (this.encode) {
          this.out.write(Base64.encode3to4(this.b4, this.buffer, this.position, this.options));
          this.position = 0;
        } else {
          throw new IOException("Base64 input not properly padded.");
        }  
    }
    
    public void close() throws IOException {
      flushBase64();
      super.close();
      this.buffer = null;
      this.out = null;
    }
  }
}
