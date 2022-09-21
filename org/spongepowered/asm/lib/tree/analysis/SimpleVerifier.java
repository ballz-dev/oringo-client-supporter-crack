package org.spongepowered.asm.lib.tree.analysis;

import java.util.List;
import org.spongepowered.asm.lib.Type;

public class SimpleVerifier extends BasicVerifier {
  private final Type currentClass;
  
  private final boolean isInterface;
  
  private final Type currentSuperClass;
  
  private ClassLoader loader = getClass().getClassLoader();
  
  private final List<Type> currentClassInterfaces;
  
  public SimpleVerifier() {
    this(null, null, false);
  }
  
  public SimpleVerifier(Type paramType1, Type paramType2, boolean paramBoolean) {
    this(paramType1, paramType2, null, paramBoolean);
  }
  
  public SimpleVerifier(Type paramType1, Type paramType2, List<Type> paramList, boolean paramBoolean) {
    this(327680, paramType1, paramType2, paramList, paramBoolean);
  }
  
  protected SimpleVerifier(int paramInt, Type paramType1, Type paramType2, List<Type> paramList, boolean paramBoolean) {
    super(paramInt);
    this.currentClass = paramType1;
    this.currentSuperClass = paramType2;
    this.currentClassInterfaces = paramList;
    this.isInterface = paramBoolean;
  }
  
  public void setClassLoader(ClassLoader paramClassLoader) {
    this.loader = paramClassLoader;
  }
  
  public BasicValue newValue(Type paramType) {
    if (paramType == null)
      return BasicValue.UNINITIALIZED_VALUE; 
    boolean bool = (paramType.getSort() == 9) ? true : false;
    if (bool)
      switch (paramType.getElementType().getSort()) {
        case 1:
        case 2:
        case 3:
        case 4:
          return new BasicValue(paramType);
      }  
    BasicValue basicValue = super.newValue(paramType);
    if (BasicValue.REFERENCE_VALUE.equals(basicValue))
      if (bool) {
        basicValue = newValue(paramType.getElementType());
        String str = basicValue.getType().getDescriptor();
        for (byte b = 0; b < paramType.getDimensions(); b++)
          str = '[' + str; 
        basicValue = new BasicValue(Type.getType(str));
      } else {
        basicValue = new BasicValue(paramType);
      }  
    return basicValue;
  }
  
  protected boolean isArrayValue(BasicValue paramBasicValue) {
    Type type = paramBasicValue.getType();
    return (type != null && ("Lnull;"
      .equals(type.getDescriptor()) || type.getSort() == 9));
  }
  
  protected BasicValue getElementValue(BasicValue paramBasicValue) throws AnalyzerException {
    Type type = paramBasicValue.getType();
    if (type != null) {
      if (type.getSort() == 9)
        return newValue(Type.getType(type.getDescriptor()
              .substring(1))); 
      if ("Lnull;".equals(type.getDescriptor()))
        return paramBasicValue; 
    } 
    throw new Error("Internal error");
  }
  
  protected boolean isSubTypeOf(BasicValue paramBasicValue1, BasicValue paramBasicValue2) {
    Type type1 = paramBasicValue2.getType();
    Type type2 = paramBasicValue1.getType();
    switch (type1.getSort()) {
      case 5:
      case 6:
      case 7:
      case 8:
        return type2.equals(type1);
      case 9:
      case 10:
        if ("Lnull;".equals(type2.getDescriptor()))
          return true; 
        if (type2.getSort() == 10 || type2
          .getSort() == 9)
          return isAssignableFrom(type1, type2); 
        return false;
    } 
    throw new Error("Internal error");
  }
  
  public BasicValue merge(BasicValue paramBasicValue1, BasicValue paramBasicValue2) {
    if (!paramBasicValue1.equals(paramBasicValue2)) {
      Type type1 = paramBasicValue1.getType();
      Type type2 = paramBasicValue2.getType();
      if (type1 != null && (type1
        .getSort() == 10 || type1.getSort() == 9) && 
        type2 != null && (type2
        .getSort() == 10 || type2.getSort() == 9)) {
        if ("Lnull;".equals(type1.getDescriptor()))
          return paramBasicValue2; 
        if ("Lnull;".equals(type2.getDescriptor()))
          return paramBasicValue1; 
        if (isAssignableFrom(type1, type2))
          return paramBasicValue1; 
        if (isAssignableFrom(type2, type1))
          return paramBasicValue2; 
        while (true) {
          if (type1 == null || isInterface(type1))
            return BasicValue.REFERENCE_VALUE; 
          type1 = getSuperClass(type1);
          if (isAssignableFrom(type1, type2))
            return newValue(type1); 
        } 
      } 
      return BasicValue.UNINITIALIZED_VALUE;
    } 
    return paramBasicValue1;
  }
  
  protected boolean isInterface(Type paramType) {
    if (this.currentClass != null && paramType.equals(this.currentClass))
      return this.isInterface; 
    return getClass(paramType).isInterface();
  }
  
  protected Type getSuperClass(Type paramType) {
    if (this.currentClass != null && paramType.equals(this.currentClass))
      return this.currentSuperClass; 
    Class<?> clazz = getClass(paramType).getSuperclass();
    return (clazz == null) ? null : Type.getType(clazz);
  }
  
  protected boolean isAssignableFrom(Type paramType1, Type paramType2) {
    if (paramType1.equals(paramType2))
      return true; 
    if (this.currentClass != null && paramType1.equals(this.currentClass)) {
      if (getSuperClass(paramType2) == null)
        return false; 
      if (this.isInterface)
        return (paramType2.getSort() == 10 || paramType2
          .getSort() == 9); 
      return isAssignableFrom(paramType1, getSuperClass(paramType2));
    } 
    if (this.currentClass != null && paramType2.equals(this.currentClass)) {
      if (isAssignableFrom(paramType1, this.currentSuperClass))
        return true; 
      if (this.currentClassInterfaces != null)
        for (byte b = 0; b < this.currentClassInterfaces.size(); b++) {
          Type type = this.currentClassInterfaces.get(b);
          if (isAssignableFrom(paramType1, type))
            return true; 
        }  
      return false;
    } 
    Class<?> clazz = getClass(paramType1);
    if (clazz.isInterface())
      clazz = Object.class; 
    return clazz.isAssignableFrom(getClass(paramType2));
  }
  
  protected Class<?> getClass(Type paramType) {
    try {
      if (paramType.getSort() == 9)
        return Class.forName(paramType.getDescriptor().replace('/', '.'), false, this.loader); 
      return Class.forName(paramType.getClassName(), false, this.loader);
    } catch (ClassNotFoundException classNotFoundException) {
      throw new RuntimeException(classNotFoundException.toString());
    } 
  }
}
