package org.spongepowered.asm.mixin.gen.throwables;

import org.spongepowered.asm.mixin.gen.AccessorInfo;
import org.spongepowered.asm.mixin.refmap.IMixinContext;
import org.spongepowered.asm.mixin.transformer.throwables.InvalidMixinException;

public class InvalidAccessorException extends InvalidMixinException {
  private final AccessorInfo info;
  
  private static final long serialVersionUID = 2L;
  
  public InvalidAccessorException(IMixinContext paramIMixinContext, String paramString) {
    super(paramIMixinContext, paramString);
    this.info = null;
  }
  
  public InvalidAccessorException(AccessorInfo paramAccessorInfo, String paramString) {
    super(paramAccessorInfo.getContext(), paramString);
    this.info = paramAccessorInfo;
  }
  
  public InvalidAccessorException(IMixinContext paramIMixinContext, Throwable paramThrowable) {
    super(paramIMixinContext, paramThrowable);
    this.info = null;
  }
  
  public InvalidAccessorException(AccessorInfo paramAccessorInfo, Throwable paramThrowable) {
    super(paramAccessorInfo.getContext(), paramThrowable);
    this.info = paramAccessorInfo;
  }
  
  public InvalidAccessorException(IMixinContext paramIMixinContext, String paramString, Throwable paramThrowable) {
    super(paramIMixinContext, paramString, paramThrowable);
    this.info = null;
  }
  
  public InvalidAccessorException(AccessorInfo paramAccessorInfo, String paramString, Throwable paramThrowable) {
    super(paramAccessorInfo.getContext(), paramString, paramThrowable);
    this.info = paramAccessorInfo;
  }
  
  public AccessorInfo getAccessorInfo() {
    return this.info;
  }
}
