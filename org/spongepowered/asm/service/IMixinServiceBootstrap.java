package org.spongepowered.asm.service;

public interface IMixinServiceBootstrap {
  String getName();
  
  void bootstrap();
  
  String getServiceClassName();
}
