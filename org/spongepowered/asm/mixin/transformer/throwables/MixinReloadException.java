package org.spongepowered.asm.mixin.transformer.throwables;

import org.spongepowered.asm.mixin.extensibility.IMixinInfo;
import org.spongepowered.asm.mixin.throwables.MixinException;

public class MixinReloadException extends MixinException {
  private final IMixinInfo mixinInfo;
  
  private static final long serialVersionUID = 2L;
  
  public MixinReloadException(IMixinInfo paramIMixinInfo, String paramString) {
    super(paramString);
    this.mixinInfo = paramIMixinInfo;
  }
  
  public IMixinInfo getMixinInfo() {
    return this.mixinInfo;
  }
}
