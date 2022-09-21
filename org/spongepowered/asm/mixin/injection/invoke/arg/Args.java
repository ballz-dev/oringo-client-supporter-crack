package org.spongepowered.asm.mixin.injection.invoke.arg;

public abstract class Args {
  protected final Object[] values;
  
  protected Args(Object[] paramArrayOfObject) {
    this.values = paramArrayOfObject;
  }
  
  public int size() {
    return this.values.length;
  }
  
  public <T> T get(int paramInt) {
    return (T)this.values[paramInt];
  }
  
  public abstract <T> void set(int paramInt, T paramT);
  
  public abstract void setAll(Object... paramVarArgs);
}
