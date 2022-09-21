package org.spongepowered.asm.mixin.extensibility;

import java.util.Set;
import org.spongepowered.asm.mixin.MixinEnvironment;

public interface IMixinConfig {
  public static final int DEFAULT_PRIORITY = 1000;
  
  String getMixinPackage();
  
  int getPriority();
  
  IMixinConfigPlugin getPlugin();
  
  String getName();
  
  Set<String> getTargets();
  
  MixinEnvironment getEnvironment();
  
  boolean isRequired();
}
