package org.spongepowered.asm.util;

public abstract class ObfuscationUtil {
  public static String mapDescriptor(String paramString, IClassRemapper paramIClassRemapper) {
    return remapDescriptor(paramString, paramIClassRemapper, false);
  }
  
  public static String unmapDescriptor(String paramString, IClassRemapper paramIClassRemapper) {
    return remapDescriptor(paramString, paramIClassRemapper, true);
  }
  
  private static String remapDescriptor(String paramString, IClassRemapper paramIClassRemapper, boolean paramBoolean) {
    StringBuilder stringBuilder1 = new StringBuilder();
    StringBuilder stringBuilder2 = null;
    for (byte b = 0; b < paramString.length(); b++) {
      char c = paramString.charAt(b);
      if (stringBuilder2 != null) {
        if (c == ';') {
          stringBuilder1.append('L').append(remap(stringBuilder2.toString(), paramIClassRemapper, paramBoolean)).append(';');
          stringBuilder2 = null;
        } else {
          stringBuilder2.append(c);
        } 
      } else if (c == 'L') {
        stringBuilder2 = new StringBuilder();
      } else {
        stringBuilder1.append(c);
      } 
    } 
    if (stringBuilder2 != null)
      throw new IllegalArgumentException("Invalid descriptor '" + paramString + "', missing ';'"); 
    return stringBuilder1.toString();
  }
  
  private static Object remap(String paramString, IClassRemapper paramIClassRemapper, boolean paramBoolean) {
    String str = paramBoolean ? paramIClassRemapper.unmap(paramString) : paramIClassRemapper.map(paramString);
    return (str != null) ? str : paramString;
  }
  
  public static interface IClassRemapper {
    String map(String param1String);
    
    String unmap(String param1String);
  }
}
