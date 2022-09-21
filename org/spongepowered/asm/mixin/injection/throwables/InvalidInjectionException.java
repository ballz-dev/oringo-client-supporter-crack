package org.spongepowered.asm.mixin.injection.throwables;

import org.spongepowered.asm.mixin.injection.struct.InjectionInfo;
import org.spongepowered.asm.mixin.refmap.IMixinContext;
import org.spongepowered.asm.mixin.transformer.throwables.InvalidMixinException;

public class InvalidInjectionException extends InvalidMixinException {
  private static final long serialVersionUID = 2L;
  
  private final InjectionInfo info;
  
  public InvalidInjectionException(IMixinContext paramIMixinContext, String paramString) {
    super(paramIMixinContext, paramString);
    this.info = null;
  }
  
  public InvalidInjectionException(InjectionInfo paramInjectionInfo, String paramString) {
    super(paramInjectionInfo.getContext(), paramString);
    this.info = paramInjectionInfo;
  }
  
  public InvalidInjectionException(IMixinContext paramIMixinContext, Throwable paramThrowable) {
    super(paramIMixinContext, paramThrowable);
    this.info = null;
  }
  
  public InvalidInjectionException(InjectionInfo paramInjectionInfo, Throwable paramThrowable) {
    super(paramInjectionInfo.getContext(), paramThrowable);
    this.info = paramInjectionInfo;
  }
  
  public InvalidInjectionException(IMixinContext paramIMixinContext, String paramString, Throwable paramThrowable) {
    super(paramIMixinContext, paramString, paramThrowable);
    this.info = null;
  }
  
  public InvalidInjectionException(InjectionInfo paramInjectionInfo, String paramString, Throwable paramThrowable) {
    super(paramInjectionInfo.getContext(), paramString, paramThrowable);
    this.info = paramInjectionInfo;
  }
  
  public InjectionInfo getInjectionInfo() {
    return this.info;
  }
}
