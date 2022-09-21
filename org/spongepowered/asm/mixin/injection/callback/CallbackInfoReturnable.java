package org.spongepowered.asm.mixin.injection.callback;

import org.spongepowered.asm.lib.Type;

public class CallbackInfoReturnable<R> extends CallbackInfo {
  private R returnValue;
  
  public CallbackInfoReturnable(String paramString, boolean paramBoolean) {
    super(paramString, paramBoolean);
    this.returnValue = null;
  }
  
  public CallbackInfoReturnable(String paramString, boolean paramBoolean, R paramR) {
    super(paramString, paramBoolean);
    this.returnValue = paramR;
  }
  
  public CallbackInfoReturnable(String paramString, boolean paramBoolean, byte paramByte) {
    super(paramString, paramBoolean);
    this.returnValue = (R)Byte.valueOf(paramByte);
  }
  
  public CallbackInfoReturnable(String paramString, boolean paramBoolean, char paramChar) {
    super(paramString, paramBoolean);
    this.returnValue = (R)Character.valueOf(paramChar);
  }
  
  public CallbackInfoReturnable(String paramString, boolean paramBoolean, double paramDouble) {
    super(paramString, paramBoolean);
    this.returnValue = (R)Double.valueOf(paramDouble);
  }
  
  public CallbackInfoReturnable(String paramString, boolean paramBoolean, float paramFloat) {
    super(paramString, paramBoolean);
    this.returnValue = (R)Float.valueOf(paramFloat);
  }
  
  public CallbackInfoReturnable(String paramString, boolean paramBoolean, int paramInt) {
    super(paramString, paramBoolean);
    this.returnValue = (R)Integer.valueOf(paramInt);
  }
  
  public CallbackInfoReturnable(String paramString, boolean paramBoolean, long paramLong) {
    super(paramString, paramBoolean);
    this.returnValue = (R)Long.valueOf(paramLong);
  }
  
  public CallbackInfoReturnable(String paramString, boolean paramBoolean, short paramShort) {
    super(paramString, paramBoolean);
    this.returnValue = (R)Short.valueOf(paramShort);
  }
  
  public CallbackInfoReturnable(String paramString, boolean paramBoolean1, boolean paramBoolean2) {
    super(paramString, paramBoolean1);
    this.returnValue = (R)Boolean.valueOf(paramBoolean2);
  }
  
  public void setReturnValue(R paramR) throws CancellationException {
    cancel();
    this.returnValue = paramR;
  }
  
  public R getReturnValue() {
    return this.returnValue;
  }
  
  public byte getReturnValueB() {
    return (this.returnValue == null) ? 0 : ((Byte)this.returnValue).byteValue();
  }
  
  public char getReturnValueC() {
    return (this.returnValue == null) ? Character.MIN_VALUE : ((Character)this.returnValue).charValue();
  }
  
  public double getReturnValueD() {
    return (this.returnValue == null) ? 0.0D : ((Double)this.returnValue).doubleValue();
  }
  
  public float getReturnValueF() {
    return (this.returnValue == null) ? 0.0F : ((Float)this.returnValue).floatValue();
  }
  
  public int getReturnValueI() {
    return (this.returnValue == null) ? 0 : ((Integer)this.returnValue).intValue();
  }
  
  public long getReturnValueJ() {
    return (this.returnValue == null) ? 0L : ((Long)this.returnValue).longValue();
  }
  
  public short getReturnValueS() {
    return (this.returnValue == null) ? 0 : ((Short)this.returnValue).shortValue();
  }
  
  public boolean getReturnValueZ() {
    return (this.returnValue == null) ? false : ((Boolean)this.returnValue).booleanValue();
  }
  
  static String getReturnAccessor(Type paramType) {
    if (paramType.getSort() == 10 || paramType.getSort() == 9)
      return "getReturnValue"; 
    return String.format("getReturnValue%s", new Object[] { paramType.getDescriptor() });
  }
  
  static String getReturnDescriptor(Type paramType) {
    if (paramType.getSort() == 10 || paramType.getSort() == 9)
      return String.format("()%s", new Object[] { "Ljava/lang/Object;" }); 
    return String.format("()%s", new Object[] { paramType.getDescriptor() });
  }
}
