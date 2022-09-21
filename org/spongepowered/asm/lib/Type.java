package org.spongepowered.asm.lib;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class Type {
  public static final int BYTE = 3;
  
  private final int off;
  
  public static final Type FLOAT_TYPE;
  
  public static final Type DOUBLE_TYPE;
  
  private final int sort;
  
  public static final int OBJECT = 10;
  
  private final int len;
  
  public static final int DOUBLE = 8;
  
  public static final Type VOID_TYPE = new Type(0, null, 1443168256, 1);
  
  public static final int VOID = 0;
  
  public static final int ARRAY = 9;
  
  public static final int FLOAT = 6;
  
  public static final int SHORT = 4;
  
  public static final int METHOD = 11;
  
  public static final int CHAR = 2;
  
  public static final Type BOOLEAN_TYPE = new Type(1, null, 1509950721, 1);
  
  public static final int INT = 5;
  
  public static final int BOOLEAN = 1;
  
  private final char[] buf;
  
  public static final Type LONG_TYPE;
  
  public static final Type CHAR_TYPE = new Type(2, null, 1124075009, 1);
  
  public static final Type BYTE_TYPE = new Type(3, null, 1107297537, 1);
  
  public static final Type SHORT_TYPE = new Type(4, null, 1392510721, 1);
  
  public static final int LONG = 7;
  
  public static final Type INT_TYPE = new Type(5, null, 1224736769, 1);
  
  static {
    FLOAT_TYPE = new Type(6, null, 1174536705, 1);
    LONG_TYPE = new Type(7, null, 1241579778, 1);
    DOUBLE_TYPE = new Type(8, null, 1141048066, 1);
  }
  
  private Type(int paramInt1, char[] paramArrayOfchar, int paramInt2, int paramInt3) {
    this.sort = paramInt1;
    this.buf = paramArrayOfchar;
    this.off = paramInt2;
    this.len = paramInt3;
  }
  
  public static Type getType(String paramString) {
    return getType(paramString.toCharArray(), 0);
  }
  
  public static Type getObjectType(String paramString) {
    char[] arrayOfChar = paramString.toCharArray();
    return new Type((arrayOfChar[0] == '[') ? 9 : 10, arrayOfChar, 0, arrayOfChar.length);
  }
  
  public static Type getMethodType(String paramString) {
    return getType(paramString.toCharArray(), 0);
  }
  
  public static Type getMethodType(Type paramType, Type... paramVarArgs) {
    return getType(getMethodDescriptor(paramType, paramVarArgs));
  }
  
  public static Type getType(Class<?> paramClass) {
    if (paramClass.isPrimitive()) {
      if (paramClass == int.class)
        return INT_TYPE; 
      if (paramClass == void.class)
        return VOID_TYPE; 
      if (paramClass == boolean.class)
        return BOOLEAN_TYPE; 
      if (paramClass == byte.class)
        return BYTE_TYPE; 
      if (paramClass == char.class)
        return CHAR_TYPE; 
      if (paramClass == short.class)
        return SHORT_TYPE; 
      if (paramClass == double.class)
        return DOUBLE_TYPE; 
      if (paramClass == float.class)
        return FLOAT_TYPE; 
      return LONG_TYPE;
    } 
    return getType(getDescriptor(paramClass));
  }
  
  public static Type getType(Constructor<?> paramConstructor) {
    return getType(getConstructorDescriptor(paramConstructor));
  }
  
  public static Type getType(Method paramMethod) {
    return getType(getMethodDescriptor(paramMethod));
  }
  
  public static Type[] getArgumentTypes(String paramString) {
    char[] arrayOfChar = paramString.toCharArray();
    int i = 1;
    byte b = 0;
    while (true) {
      char c = arrayOfChar[i++];
      if (c == ')')
        break; 
      if (c == 'L') {
        while (arrayOfChar[i++] != ';');
        b++;
        continue;
      } 
      if (c != '[')
        b++; 
    } 
    Type[] arrayOfType = new Type[b];
    i = 1;
    b = 0;
    while (arrayOfChar[i] != ')') {
      arrayOfType[b] = getType(arrayOfChar, i);
      i += (arrayOfType[b]).len + (((arrayOfType[b]).sort == 10) ? 2 : 0);
      b++;
    } 
    return arrayOfType;
  }
  
  public static Type[] getArgumentTypes(Method paramMethod) {
    Class[] arrayOfClass = paramMethod.getParameterTypes();
    Type[] arrayOfType = new Type[arrayOfClass.length];
    for (int i = arrayOfClass.length - 1; i >= 0; i--)
      arrayOfType[i] = getType(arrayOfClass[i]); 
    return arrayOfType;
  }
  
  public static Type getReturnType(String paramString) {
    char[] arrayOfChar = paramString.toCharArray();
    byte b = 1;
    while (true) {
      char c = arrayOfChar[b++];
      if (c == ')')
        return getType(arrayOfChar, b); 
      if (c == 'L')
        while (arrayOfChar[b++] != ';'); 
    } 
  }
  
  public static Type getReturnType(Method paramMethod) {
    return getType(paramMethod.getReturnType());
  }
  
  public static int getArgumentsAndReturnSizes(String paramString) {
    byte b1 = 1;
    byte b2 = 1;
    while (true) {
      char c = paramString.charAt(b2++);
      if (c == ')') {
        c = paramString.charAt(b2);
        return b1 << 2 | ((c == 'V') ? 0 : ((c == 'D' || c == 'J') ? 2 : 1));
      } 
      if (c == 'L') {
        while (paramString.charAt(b2++) != ';');
        b1++;
        continue;
      } 
      if (c == '[') {
        while ((c = paramString.charAt(b2)) == '[')
          b2++; 
        if (c == 'D' || c == 'J')
          b1--; 
        continue;
      } 
      if (c == 'D' || c == 'J') {
        b1 += 2;
        continue;
      } 
      b1++;
    } 
  }
  
  private static Type getType(char[] paramArrayOfchar, int paramInt) {
    byte b;
    switch (paramArrayOfchar[paramInt]) {
      case 'V':
        return VOID_TYPE;
      case 'Z':
        return BOOLEAN_TYPE;
      case 'C':
        return CHAR_TYPE;
      case 'B':
        return BYTE_TYPE;
      case 'S':
        return SHORT_TYPE;
      case 'I':
        return INT_TYPE;
      case 'F':
        return FLOAT_TYPE;
      case 'J':
        return LONG_TYPE;
      case 'D':
        return DOUBLE_TYPE;
      case '[':
        b = 1;
        while (paramArrayOfchar[paramInt + b] == '[')
          b++; 
        if (paramArrayOfchar[paramInt + b] == 'L') {
          b++;
          while (paramArrayOfchar[paramInt + b] != ';')
            b++; 
        } 
        return new Type(9, paramArrayOfchar, paramInt, b + 1);
      case 'L':
        b = 1;
        while (paramArrayOfchar[paramInt + b] != ';')
          b++; 
        return new Type(10, paramArrayOfchar, paramInt + 1, b - 1);
    } 
    return new Type(11, paramArrayOfchar, paramInt, paramArrayOfchar.length - paramInt);
  }
  
  public int getSort() {
    return this.sort;
  }
  
  public int getDimensions() {
    byte b = 1;
    while (this.buf[this.off + b] == '[')
      b++; 
    return b;
  }
  
  public Type getElementType() {
    return getType(this.buf, this.off + getDimensions());
  }
  
  public String getClassName() {
    StringBuilder stringBuilder;
    int i;
    switch (this.sort) {
      case 0:
        return "void";
      case 1:
        return "boolean";
      case 2:
        return "char";
      case 3:
        return "byte";
      case 4:
        return "short";
      case 5:
        return "int";
      case 6:
        return "float";
      case 7:
        return "long";
      case 8:
        return "double";
      case 9:
        stringBuilder = new StringBuilder(getElementType().getClassName());
        for (i = getDimensions(); i > 0; i--)
          stringBuilder.append("[]"); 
        return stringBuilder.toString();
      case 10:
        return (new String(this.buf, this.off, this.len)).replace('/', '.');
    } 
    return null;
  }
  
  public String getInternalName() {
    return new String(this.buf, this.off, this.len);
  }
  
  public Type[] getArgumentTypes() {
    return getArgumentTypes(getDescriptor());
  }
  
  public Type getReturnType() {
    return getReturnType(getDescriptor());
  }
  
  public int getArgumentsAndReturnSizes() {
    return getArgumentsAndReturnSizes(getDescriptor());
  }
  
  public String getDescriptor() {
    StringBuilder stringBuilder = new StringBuilder();
    getDescriptor(stringBuilder);
    return stringBuilder.toString();
  }
  
  public static String getMethodDescriptor(Type paramType, Type... paramVarArgs) {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append('(');
    for (byte b = 0; b < paramVarArgs.length; b++)
      paramVarArgs[b].getDescriptor(stringBuilder); 
    stringBuilder.append(')');
    paramType.getDescriptor(stringBuilder);
    return stringBuilder.toString();
  }
  
  private void getDescriptor(StringBuilder paramStringBuilder) {
    if (this.buf == null) {
      paramStringBuilder.append((char)((this.off & 0xFF000000) >>> 24));
    } else if (this.sort == 10) {
      paramStringBuilder.append('L');
      paramStringBuilder.append(this.buf, this.off, this.len);
      paramStringBuilder.append(';');
    } else {
      paramStringBuilder.append(this.buf, this.off, this.len);
    } 
  }
  
  public static String getInternalName(Class<?> paramClass) {
    return paramClass.getName().replace('.', '/');
  }
  
  public static String getDescriptor(Class<?> paramClass) {
    StringBuilder stringBuilder = new StringBuilder();
    getDescriptor(stringBuilder, paramClass);
    return stringBuilder.toString();
  }
  
  public static String getConstructorDescriptor(Constructor<?> paramConstructor) {
    Class[] arrayOfClass = paramConstructor.getParameterTypes();
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append('(');
    for (byte b = 0; b < arrayOfClass.length; b++)
      getDescriptor(stringBuilder, arrayOfClass[b]); 
    return stringBuilder.append(")V").toString();
  }
  
  public static String getMethodDescriptor(Method paramMethod) {
    Class[] arrayOfClass = paramMethod.getParameterTypes();
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append('(');
    for (byte b = 0; b < arrayOfClass.length; b++)
      getDescriptor(stringBuilder, arrayOfClass[b]); 
    stringBuilder.append(')');
    getDescriptor(stringBuilder, paramMethod.getReturnType());
    return stringBuilder.toString();
  }
  
  private static void getDescriptor(StringBuilder paramStringBuilder, Class<?> paramClass) {
    Class<?> clazz = paramClass;
    while (true) {
      if (clazz.isPrimitive()) {
        byte b1;
        if (clazz == int.class) {
          b1 = 73;
        } else if (clazz == void.class) {
          b1 = 86;
        } else if (clazz == boolean.class) {
          b1 = 90;
        } else if (clazz == byte.class) {
          b1 = 66;
        } else if (clazz == char.class) {
          b1 = 67;
        } else if (clazz == short.class) {
          b1 = 83;
        } else if (clazz == double.class) {
          b1 = 68;
        } else if (clazz == float.class) {
          b1 = 70;
        } else {
          b1 = 74;
        } 
        paramStringBuilder.append(b1);
        return;
      } 
      if (clazz.isArray()) {
        paramStringBuilder.append('[');
        clazz = clazz.getComponentType();
        continue;
      } 
      break;
    } 
    paramStringBuilder.append('L');
    String str = clazz.getName();
    int i = str.length();
    for (byte b = 0; b < i; b++) {
      char c = str.charAt(b);
      paramStringBuilder.append((c == '.') ? 47 : c);
    } 
    paramStringBuilder.append(';');
  }
  
  public int getSize() {
    return (this.buf == null) ? (this.off & 0xFF) : 1;
  }
  
  public int getOpcode(int paramInt) {
    if (paramInt == 46 || paramInt == 79)
      return paramInt + ((this.buf == null) ? ((this.off & 0xFF00) >> 8) : 4); 
    return paramInt + ((this.buf == null) ? ((this.off & 0xFF0000) >> 16) : 4);
  }
  
  public boolean equals(Object paramObject) {
    if (this == paramObject)
      return true; 
    if (!(paramObject instanceof Type))
      return false; 
    Type type = (Type)paramObject;
    if (this.sort != type.sort)
      return false; 
    if (this.sort >= 9) {
      if (this.len != type.len)
        return false; 
      for (int i = this.off, j = type.off, k = i + this.len; i < k; i++, j++) {
        if (this.buf[i] != type.buf[j])
          return false; 
      } 
    } 
    return true;
  }
  
  public int hashCode() {
    int i = 13 * this.sort;
    if (this.sort >= 9)
      for (int j = this.off, k = j + this.len; j < k; j++)
        i = 17 * (i + this.buf[j]);  
    return i;
  }
  
  public String toString() {
    return getDescriptor();
  }
}
