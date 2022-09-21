package org.spongepowered.asm.lib;

final class Item {
  Item next;
  
  int index;
  
  String strVal3;
  
  int hashCode;
  
  String strVal2;
  
  String strVal1;
  
  int type;
  
  long longVal;
  
  int intVal;
  
  Item() {}
  
  Item(int paramInt) {
    this.index = paramInt;
  }
  
  Item(int paramInt, Item paramItem) {
    this.index = paramInt;
    this.type = paramItem.type;
    this.intVal = paramItem.intVal;
    this.longVal = paramItem.longVal;
    this.strVal1 = paramItem.strVal1;
    this.strVal2 = paramItem.strVal2;
    this.strVal3 = paramItem.strVal3;
    this.hashCode = paramItem.hashCode;
  }
  
  void set(int paramInt) {
    this.type = 3;
    this.intVal = paramInt;
    this.hashCode = Integer.MAX_VALUE & this.type + paramInt;
  }
  
  void set(long paramLong) {
    this.type = 5;
    this.longVal = paramLong;
    this.hashCode = Integer.MAX_VALUE & this.type + (int)paramLong;
  }
  
  void set(float paramFloat) {
    this.type = 4;
    this.intVal = Float.floatToRawIntBits(paramFloat);
    this.hashCode = Integer.MAX_VALUE & this.type + (int)paramFloat;
  }
  
  void set(double paramDouble) {
    this.type = 6;
    this.longVal = Double.doubleToRawLongBits(paramDouble);
    this.hashCode = Integer.MAX_VALUE & this.type + (int)paramDouble;
  }
  
  void set(int paramInt, String paramString1, String paramString2, String paramString3) {
    this.type = paramInt;
    this.strVal1 = paramString1;
    this.strVal2 = paramString2;
    this.strVal3 = paramString3;
    switch (paramInt) {
      case 7:
        this.intVal = 0;
      case 1:
      case 8:
      case 16:
      case 30:
        this.hashCode = Integer.MAX_VALUE & paramInt + paramString1.hashCode();
        return;
      case 12:
        this
          .hashCode = Integer.MAX_VALUE & paramInt + paramString1.hashCode() * paramString2.hashCode();
        return;
    } 
    this
      .hashCode = Integer.MAX_VALUE & paramInt + paramString1.hashCode() * paramString2.hashCode() * paramString3.hashCode();
  }
  
  void set(String paramString1, String paramString2, int paramInt) {
    this.type = 18;
    this.longVal = paramInt;
    this.strVal1 = paramString1;
    this.strVal2 = paramString2;
    this
      .hashCode = Integer.MAX_VALUE & 18 + paramInt * this.strVal1.hashCode() * this.strVal2.hashCode();
  }
  
  void set(int paramInt1, int paramInt2) {
    this.type = 33;
    this.intVal = paramInt1;
    this.hashCode = paramInt2;
  }
  
  boolean isEqualTo(Item paramItem) {
    switch (this.type) {
      case 1:
      case 7:
      case 8:
      case 16:
      case 30:
        return paramItem.strVal1.equals(this.strVal1);
      case 5:
      case 6:
      case 32:
        return (paramItem.longVal == this.longVal);
      case 3:
      case 4:
        return (paramItem.intVal == this.intVal);
      case 31:
        return (paramItem.intVal == this.intVal && paramItem.strVal1.equals(this.strVal1));
      case 12:
        return (paramItem.strVal1.equals(this.strVal1) && paramItem.strVal2.equals(this.strVal2));
      case 18:
        return (paramItem.longVal == this.longVal && paramItem.strVal1.equals(this.strVal1) && paramItem.strVal2
          .equals(this.strVal2));
    } 
    return (paramItem.strVal1.equals(this.strVal1) && paramItem.strVal2.equals(this.strVal2) && paramItem.strVal3
      .equals(this.strVal3));
  }
}
