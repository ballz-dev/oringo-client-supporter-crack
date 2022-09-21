package org.spongepowered.asm.mixin.injection.callback;

import org.spongepowered.asm.lib.Type;

public class CallbackInfo implements Cancellable {
  private boolean cancelled;
  
  private final boolean cancellable;
  
  private final String name;
  
  public CallbackInfo(String paramString, boolean paramBoolean) {
    this.name = paramString;
    this.cancellable = paramBoolean;
  }
  
  public String getId() {
    return this.name;
  }
  
  public String toString() {
    return String.format("CallbackInfo[TYPE=%s,NAME=%s,CANCELLABLE=%s]", new Object[] { getClass().getSimpleName(), this.name, Boolean.valueOf(this.cancellable) });
  }
  
  public final boolean isCancellable() {
    return this.cancellable;
  }
  
  public final boolean isCancelled() {
    return this.cancelled;
  }
  
  public void cancel() throws CancellationException {
    if (!this.cancellable)
      throw new CancellationException(String.format("The call %s is not cancellable.", new Object[] { this.name })); 
    this.cancelled = true;
  }
  
  static String getCallInfoClassName() {
    return CallbackInfo.class.getName();
  }
  
  public static String getCallInfoClassName(Type paramType) {
    return (paramType.equals(Type.VOID_TYPE) ? CallbackInfo.class.getName() : CallbackInfoReturnable.class.getName()).replace('.', '/');
  }
  
  static String getConstructorDescriptor(Type paramType) {
    if (paramType.equals(Type.VOID_TYPE))
      return getConstructorDescriptor(); 
    if (paramType.getSort() == 10 || paramType.getSort() == 9)
      return String.format("(%sZ%s)V", new Object[] { "Ljava/lang/String;", "Ljava/lang/Object;" }); 
    return String.format("(%sZ%s)V", new Object[] { "Ljava/lang/String;", paramType.getDescriptor() });
  }
  
  static String getConstructorDescriptor() {
    return String.format("(%sZ)V", new Object[] { "Ljava/lang/String;" });
  }
  
  static String getIsCancelledMethodName() {
    return "isCancelled";
  }
  
  static String getIsCancelledMethodSig() {
    return "()Z";
  }
}
