package org.spongepowered.asm.lib.commons;

import org.spongepowered.asm.lib.Handle;
import org.spongepowered.asm.lib.Type;
import org.spongepowered.asm.lib.signature.SignatureReader;
import org.spongepowered.asm.lib.signature.SignatureVisitor;
import org.spongepowered.asm.lib.signature.SignatureWriter;

public abstract class Remapper {
  public String mapDesc(String paramString) {
    String str1;
    byte b;
    String str2;
    Type type = Type.getType(paramString);
    switch (type.getSort()) {
      case 9:
        str1 = mapDesc(type.getElementType().getDescriptor());
        for (b = 0; b < type.getDimensions(); b++)
          str1 = '[' + str1; 
        return str1;
      case 10:
        str2 = map(type.getInternalName());
        if (str2 != null)
          return 'L' + str2 + ';'; 
        break;
    } 
    return paramString;
  }
  
  private Type mapType(Type paramType) {
    String str;
    byte b;
    switch (paramType.getSort()) {
      case 9:
        str = mapDesc(paramType.getElementType().getDescriptor());
        for (b = 0; b < paramType.getDimensions(); b++)
          str = '[' + str; 
        return Type.getType(str);
      case 10:
        str = map(paramType.getInternalName());
        return (str != null) ? Type.getObjectType(str) : paramType;
      case 11:
        return Type.getMethodType(mapMethodDesc(paramType.getDescriptor()));
    } 
    return paramType;
  }
  
  public String mapType(String paramString) {
    if (paramString == null)
      return null; 
    return mapType(Type.getObjectType(paramString)).getInternalName();
  }
  
  public String[] mapTypes(String[] paramArrayOfString) {
    String[] arrayOfString = null;
    boolean bool = false;
    for (byte b = 0; b < paramArrayOfString.length; b++) {
      String str1 = paramArrayOfString[b];
      String str2 = map(str1);
      if (str2 != null && arrayOfString == null) {
        arrayOfString = new String[paramArrayOfString.length];
        if (b > 0)
          System.arraycopy(paramArrayOfString, 0, arrayOfString, 0, b); 
        bool = true;
      } 
      if (bool)
        arrayOfString[b] = (str2 == null) ? str1 : str2; 
    } 
    return bool ? arrayOfString : paramArrayOfString;
  }
  
  public String mapMethodDesc(String paramString) {
    if ("()V".equals(paramString))
      return paramString; 
    Type[] arrayOfType = Type.getArgumentTypes(paramString);
    StringBuilder stringBuilder = new StringBuilder("(");
    for (byte b = 0; b < arrayOfType.length; b++)
      stringBuilder.append(mapDesc(arrayOfType[b].getDescriptor())); 
    Type type = Type.getReturnType(paramString);
    if (type == Type.VOID_TYPE) {
      stringBuilder.append(")V");
      return stringBuilder.toString();
    } 
    stringBuilder.append(')').append(mapDesc(type.getDescriptor()));
    return stringBuilder.toString();
  }
  
  public Object mapValue(Object paramObject) {
    if (paramObject instanceof Type)
      return mapType((Type)paramObject); 
    if (paramObject instanceof Handle) {
      Handle handle = (Handle)paramObject;
      return new Handle(handle.getTag(), mapType(handle.getOwner()), mapMethodName(handle
            .getOwner(), handle.getName(), handle.getDesc()), 
          mapMethodDesc(handle.getDesc()), handle.isInterface());
    } 
    return paramObject;
  }
  
  public String mapSignature(String paramString, boolean paramBoolean) {
    if (paramString == null)
      return null; 
    SignatureReader signatureReader = new SignatureReader(paramString);
    SignatureWriter signatureWriter = new SignatureWriter();
    SignatureVisitor signatureVisitor = createSignatureRemapper((SignatureVisitor)signatureWriter);
    if (paramBoolean) {
      signatureReader.acceptType(signatureVisitor);
    } else {
      signatureReader.accept(signatureVisitor);
    } 
    return signatureWriter.toString();
  }
  
  @Deprecated
  protected SignatureVisitor createRemappingSignatureAdapter(SignatureVisitor paramSignatureVisitor) {
    return new SignatureRemapper(paramSignatureVisitor, this);
  }
  
  protected SignatureVisitor createSignatureRemapper(SignatureVisitor paramSignatureVisitor) {
    return createRemappingSignatureAdapter(paramSignatureVisitor);
  }
  
  public String mapMethodName(String paramString1, String paramString2, String paramString3) {
    return paramString2;
  }
  
  public String mapInvokeDynamicMethodName(String paramString1, String paramString2) {
    return paramString1;
  }
  
  public String mapFieldName(String paramString1, String paramString2, String paramString3) {
    return paramString2;
  }
  
  public String map(String paramString) {
    return paramString;
  }
}
