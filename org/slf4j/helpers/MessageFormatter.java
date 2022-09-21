package org.slf4j.helpers;

import java.util.HashMap;
import java.util.Map;

public final class MessageFormatter {
  static final char DELIM_STOP = '}';
  
  private static final char ESCAPE_CHAR = '\\';
  
  static final char DELIM_START = '{';
  
  static final String DELIM_STR = "{}";
  
  public static final FormattingTuple format(String paramString, Object paramObject) {
    return arrayFormat(paramString, new Object[] { paramObject });
  }
  
  public static final FormattingTuple format(String paramString, Object paramObject1, Object paramObject2) {
    return arrayFormat(paramString, new Object[] { paramObject1, paramObject2 });
  }
  
  static final Throwable getThrowableCandidate(Object[] paramArrayOfObject) {
    if (paramArrayOfObject == null || paramArrayOfObject.length == 0)
      return null; 
    Object object = paramArrayOfObject[paramArrayOfObject.length - 1];
    if (object instanceof Throwable)
      return (Throwable)object; 
    return null;
  }
  
  public static final FormattingTuple arrayFormat(String paramString, Object[] paramArrayOfObject) {
    Throwable throwable = getThrowableCandidate(paramArrayOfObject);
    Object[] arrayOfObject = paramArrayOfObject;
    if (throwable != null)
      arrayOfObject = trimmedCopy(paramArrayOfObject); 
    return arrayFormat(paramString, arrayOfObject, throwable);
  }
  
  private static Object[] trimmedCopy(Object[] paramArrayOfObject) {
    if (paramArrayOfObject == null || paramArrayOfObject.length == 0)
      throw new IllegalStateException("non-sensical empty or null argument array"); 
    int i = paramArrayOfObject.length - 1;
    Object[] arrayOfObject = new Object[i];
    System.arraycopy(paramArrayOfObject, 0, arrayOfObject, 0, i);
    return arrayOfObject;
  }
  
  public static final FormattingTuple arrayFormat(String paramString, Object[] paramArrayOfObject, Throwable paramThrowable) {
    if (paramString == null)
      return new FormattingTuple(null, paramArrayOfObject, paramThrowable); 
    if (paramArrayOfObject == null)
      return new FormattingTuple(paramString); 
    int i = 0;
    StringBuilder stringBuilder = new StringBuilder(paramString.length() + 50);
    for (byte b = 0; b < paramArrayOfObject.length; b++) {
      int j = paramString.indexOf("{}", i);
      if (j == -1) {
        if (!i)
          return new FormattingTuple(paramString, paramArrayOfObject, paramThrowable); 
        stringBuilder.append(paramString, i, paramString.length());
        return new FormattingTuple(stringBuilder.toString(), paramArrayOfObject, paramThrowable);
      } 
      if (isEscapedDelimeter(paramString, j)) {
        if (!isDoubleEscaped(paramString, j)) {
          b--;
          stringBuilder.append(paramString, i, j - 1);
          stringBuilder.append('{');
          i = j + 1;
        } else {
          stringBuilder.append(paramString, i, j - 1);
          deeplyAppendParameter(stringBuilder, paramArrayOfObject[b], (Map)new HashMap<Object, Object>());
          i = j + 2;
        } 
      } else {
        stringBuilder.append(paramString, i, j);
        deeplyAppendParameter(stringBuilder, paramArrayOfObject[b], (Map)new HashMap<Object, Object>());
        i = j + 2;
      } 
    } 
    stringBuilder.append(paramString, i, paramString.length());
    return new FormattingTuple(stringBuilder.toString(), paramArrayOfObject, paramThrowable);
  }
  
  static final boolean isEscapedDelimeter(String paramString, int paramInt) {
    if (paramInt == 0)
      return false; 
    char c = paramString.charAt(paramInt - 1);
    if (c == '\\')
      return true; 
    return false;
  }
  
  static final boolean isDoubleEscaped(String paramString, int paramInt) {
    if (paramInt >= 2 && paramString.charAt(paramInt - 2) == '\\')
      return true; 
    return false;
  }
  
  private static void deeplyAppendParameter(StringBuilder paramStringBuilder, Object paramObject, Map<Object[], Object> paramMap) {
    if (paramObject == null) {
      paramStringBuilder.append("null");
      return;
    } 
    if (!paramObject.getClass().isArray()) {
      safeObjectAppend(paramStringBuilder, paramObject);
    } else if (paramObject instanceof boolean[]) {
      booleanArrayAppend(paramStringBuilder, (boolean[])paramObject);
    } else if (paramObject instanceof byte[]) {
      byteArrayAppend(paramStringBuilder, (byte[])paramObject);
    } else if (paramObject instanceof char[]) {
      charArrayAppend(paramStringBuilder, (char[])paramObject);
    } else if (paramObject instanceof short[]) {
      shortArrayAppend(paramStringBuilder, (short[])paramObject);
    } else if (paramObject instanceof int[]) {
      intArrayAppend(paramStringBuilder, (int[])paramObject);
    } else if (paramObject instanceof long[]) {
      longArrayAppend(paramStringBuilder, (long[])paramObject);
    } else if (paramObject instanceof float[]) {
      floatArrayAppend(paramStringBuilder, (float[])paramObject);
    } else if (paramObject instanceof double[]) {
      doubleArrayAppend(paramStringBuilder, (double[])paramObject);
    } else {
      objectArrayAppend(paramStringBuilder, (Object[])paramObject, paramMap);
    } 
  }
  
  private static void safeObjectAppend(StringBuilder paramStringBuilder, Object paramObject) {
    try {
      String str = paramObject.toString();
      paramStringBuilder.append(str);
    } catch (Throwable throwable) {
      Util.report("SLF4J: Failed toString() invocation on an object of type [" + paramObject.getClass().getName() + "]", throwable);
      paramStringBuilder.append("[FAILED toString()]");
    } 
  }
  
  private static void objectArrayAppend(StringBuilder paramStringBuilder, Object[] paramArrayOfObject, Map<Object[], Object> paramMap) {
    paramStringBuilder.append('[');
    if (!paramMap.containsKey(paramArrayOfObject)) {
      paramMap.put(paramArrayOfObject, null);
      int i = paramArrayOfObject.length;
      for (byte b = 0; b < i; b++) {
        deeplyAppendParameter(paramStringBuilder, paramArrayOfObject[b], paramMap);
        if (b != i - 1)
          paramStringBuilder.append(", "); 
      } 
      paramMap.remove(paramArrayOfObject);
    } else {
      paramStringBuilder.append("...");
    } 
    paramStringBuilder.append(']');
  }
  
  private static void booleanArrayAppend(StringBuilder paramStringBuilder, boolean[] paramArrayOfboolean) {
    paramStringBuilder.append('[');
    int i = paramArrayOfboolean.length;
    for (byte b = 0; b < i; b++) {
      paramStringBuilder.append(paramArrayOfboolean[b]);
      if (b != i - 1)
        paramStringBuilder.append(", "); 
    } 
    paramStringBuilder.append(']');
  }
  
  private static void byteArrayAppend(StringBuilder paramStringBuilder, byte[] paramArrayOfbyte) {
    paramStringBuilder.append('[');
    int i = paramArrayOfbyte.length;
    for (byte b = 0; b < i; b++) {
      paramStringBuilder.append(paramArrayOfbyte[b]);
      if (b != i - 1)
        paramStringBuilder.append(", "); 
    } 
    paramStringBuilder.append(']');
  }
  
  private static void charArrayAppend(StringBuilder paramStringBuilder, char[] paramArrayOfchar) {
    paramStringBuilder.append('[');
    int i = paramArrayOfchar.length;
    for (byte b = 0; b < i; b++) {
      paramStringBuilder.append(paramArrayOfchar[b]);
      if (b != i - 1)
        paramStringBuilder.append(", "); 
    } 
    paramStringBuilder.append(']');
  }
  
  private static void shortArrayAppend(StringBuilder paramStringBuilder, short[] paramArrayOfshort) {
    paramStringBuilder.append('[');
    int i = paramArrayOfshort.length;
    for (byte b = 0; b < i; b++) {
      paramStringBuilder.append(paramArrayOfshort[b]);
      if (b != i - 1)
        paramStringBuilder.append(", "); 
    } 
    paramStringBuilder.append(']');
  }
  
  private static void intArrayAppend(StringBuilder paramStringBuilder, int[] paramArrayOfint) {
    paramStringBuilder.append('[');
    int i = paramArrayOfint.length;
    for (byte b = 0; b < i; b++) {
      paramStringBuilder.append(paramArrayOfint[b]);
      if (b != i - 1)
        paramStringBuilder.append(", "); 
    } 
    paramStringBuilder.append(']');
  }
  
  private static void longArrayAppend(StringBuilder paramStringBuilder, long[] paramArrayOflong) {
    paramStringBuilder.append('[');
    int i = paramArrayOflong.length;
    for (byte b = 0; b < i; b++) {
      paramStringBuilder.append(paramArrayOflong[b]);
      if (b != i - 1)
        paramStringBuilder.append(", "); 
    } 
    paramStringBuilder.append(']');
  }
  
  private static void floatArrayAppend(StringBuilder paramStringBuilder, float[] paramArrayOffloat) {
    paramStringBuilder.append('[');
    int i = paramArrayOffloat.length;
    for (byte b = 0; b < i; b++) {
      paramStringBuilder.append(paramArrayOffloat[b]);
      if (b != i - 1)
        paramStringBuilder.append(", "); 
    } 
    paramStringBuilder.append(']');
  }
  
  private static void doubleArrayAppend(StringBuilder paramStringBuilder, double[] paramArrayOfdouble) {
    paramStringBuilder.append('[');
    int i = paramArrayOfdouble.length;
    for (byte b = 0; b < i; b++) {
      paramStringBuilder.append(paramArrayOfdouble[b]);
      if (b != i - 1)
        paramStringBuilder.append(", "); 
    } 
    paramStringBuilder.append(']');
  }
}
