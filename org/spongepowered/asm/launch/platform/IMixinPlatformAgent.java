package org.spongepowered.asm.launch.platform;

public interface IMixinPlatformAgent {
  String getPhaseProvider();
  
  void initPrimaryContainer();
  
  String getLaunchTarget();
  
  void inject();
  
  void prepare();
}
