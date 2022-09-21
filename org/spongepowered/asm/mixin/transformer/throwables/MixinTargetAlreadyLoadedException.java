package org.spongepowered.asm.mixin.transformer.throwables;

import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

public class MixinTargetAlreadyLoadedException extends InvalidMixinException {
  private final String target;
  
  private static final long serialVersionUID = 1L;
  
  public MixinTargetAlreadyLoadedException(IMixinInfo paramIMixinInfo, String paramString1, String paramString2) {
    super(paramIMixinInfo, paramString1);
    this.target = paramString2;
  }
  
  public MixinTargetAlreadyLoadedException(IMixinInfo paramIMixinInfo, String paramString1, String paramString2, Throwable paramThrowable) {
    super(paramIMixinInfo, paramString1, paramThrowable);
    this.target = paramString2;
  }
  
  public String getTarget() {
    return this.target;
  }
}
