package org.spongepowered.asm.util;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class VersionNumber implements Comparable<VersionNumber>, Serializable {
  private final String suffix;
  
  private static final long serialVersionUID = 1L;
  
  private static final Pattern PATTERN;
  
  private final long value;
  
  public static final VersionNumber NONE = new VersionNumber();
  
  static {
    PATTERN = Pattern.compile("^(\\d{1,5})(?:\\.(\\d{1,5})(?:\\.(\\d{1,5})(?:\\.(\\d{1,5}))?)?)?(-[a-zA-Z0-9_\\-]+)?$");
  }
  
  private VersionNumber() {
    this.value = 0L;
    this.suffix = "";
  }
  
  private VersionNumber(short[] paramArrayOfshort) {
    this(paramArrayOfshort, null);
  }
  
  private VersionNumber(short[] paramArrayOfshort, String paramString) {
    this.value = pack(paramArrayOfshort);
    this.suffix = (paramString != null) ? paramString : "";
  }
  
  private VersionNumber(short paramShort1, short paramShort2, short paramShort3, short paramShort4) {
    this(paramShort1, paramShort2, paramShort3, paramShort4, null);
  }
  
  private VersionNumber(short paramShort1, short paramShort2, short paramShort3, short paramShort4, String paramString) {
    this.value = pack(new short[] { paramShort1, paramShort2, paramShort3, paramShort4 });
    this.suffix = (paramString != null) ? paramString : "";
  }
  
  public String toString() {
    short[] arrayOfShort = unpack(this.value);
    return String.format("%d.%d%3$s%4$s%5$s", new Object[] { Short.valueOf(arrayOfShort[0]), 
          Short.valueOf(arrayOfShort[1]), ((this.value & 0x7FFFFFFFL) > 0L) ? 
          String.format(".%d", new Object[] { Short.valueOf(arrayOfShort[2]) }) : "", ((this.value & 0x7FFFL) > 0L) ? 
          String.format(".%d", new Object[] { Short.valueOf(arrayOfShort[3]) }) : "", this.suffix });
  }
  
  public int compareTo(VersionNumber paramVersionNumber) {
    if (paramVersionNumber == null)
      return 1; 
    long l = this.value - paramVersionNumber.value;
    return (l > 0L) ? 1 : ((l < 0L) ? -1 : 0);
  }
  
  public boolean equals(Object paramObject) {
    if (!(paramObject instanceof VersionNumber))
      return false; 
    return (((VersionNumber)paramObject).value == this.value);
  }
  
  public int hashCode() {
    return (int)(this.value >> 32L) ^ (int)(this.value & 0xFFFFFFFFL);
  }
  
  private static long pack(short... paramVarArgs) {
    return paramVarArgs[0] << 48L | paramVarArgs[1] << 32L | (paramVarArgs[2] << 16) | paramVarArgs[3];
  }
  
  private static short[] unpack(long paramLong) {
    return new short[] { (short)(int)(paramLong >> 48L), (short)(int)(paramLong >> 32L & 0x7FFFL), (short)(int)(paramLong >> 16L & 0x7FFFL), (short)(int)(paramLong & 0x7FFFL) };
  }
  
  public static VersionNumber parse(String paramString) {
    return parse(paramString, NONE);
  }
  
  public static VersionNumber parse(String paramString1, String paramString2) {
    return parse(paramString1, parse(paramString2));
  }
  
  private static VersionNumber parse(String paramString, VersionNumber paramVersionNumber) {
    if (paramString == null)
      return paramVersionNumber; 
    Matcher matcher = PATTERN.matcher(paramString);
    if (!matcher.matches())
      return paramVersionNumber; 
    short[] arrayOfShort = new short[4];
    for (byte b = 0; b < 4; b++) {
      String str = matcher.group(b + 1);
      if (str != null) {
        int i = Integer.parseInt(str);
        if (i > 32767)
          throw new IllegalArgumentException("Version parts cannot exceed 32767, found " + i); 
        arrayOfShort[b] = (short)i;
      } 
    } 
    return new VersionNumber(arrayOfShort, matcher.group(5));
  }
}
