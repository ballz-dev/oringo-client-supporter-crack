package org.spongepowered.asm.lib.tree.analysis;

import org.spongepowered.asm.lib.Type;

public class BasicValue implements Value {
  public static final BasicValue INT_VALUE;
  
  public static final BasicValue UNINITIALIZED_VALUE = new BasicValue(null);
  
  public static final BasicValue LONG_VALUE;
  
  private final Type type;
  
  public static final BasicValue RETURNADDRESS_VALUE;
  
  public static final BasicValue FLOAT_VALUE;
  
  public static final BasicValue DOUBLE_VALUE;
  
  public static final BasicValue REFERENCE_VALUE;
  
  static {
    INT_VALUE = new BasicValue(Type.INT_TYPE);
    FLOAT_VALUE = new BasicValue(Type.FLOAT_TYPE);
    LONG_VALUE = new BasicValue(Type.LONG_TYPE);
    DOUBLE_VALUE = new BasicValue(Type.DOUBLE_TYPE);
    REFERENCE_VALUE = new BasicValue(Type.getObjectType("java/lang/Object"));
    RETURNADDRESS_VALUE = new BasicValue(Type.VOID_TYPE);
  }
  
  public BasicValue(Type paramType) {
    this.type = paramType;
  }
  
  public Type getType() {
    return this.type;
  }
  
  public int getSize() {
    return (this.type == Type.LONG_TYPE || this.type == Type.DOUBLE_TYPE) ? 2 : 1;
  }
  
  public boolean isReference() {
    return (this.type != null && (this.type
      .getSort() == 10 || this.type.getSort() == 9));
  }
  
  public boolean equals(Object paramObject) {
    if (paramObject == this)
      return true; 
    if (paramObject instanceof BasicValue) {
      if (this.type == null)
        return (((BasicValue)paramObject).type == null); 
      return this.type.equals(((BasicValue)paramObject).type);
    } 
    return false;
  }
  
  public int hashCode() {
    return (this.type == null) ? 0 : this.type.hashCode();
  }
  
  public String toString() {
    if (this == UNINITIALIZED_VALUE)
      return "."; 
    if (this == RETURNADDRESS_VALUE)
      return "A"; 
    if (this == REFERENCE_VALUE)
      return "R"; 
    return this.type.getDescriptor();
  }
}
