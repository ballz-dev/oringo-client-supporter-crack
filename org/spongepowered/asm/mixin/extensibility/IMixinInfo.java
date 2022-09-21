package org.spongepowered.asm.mixin.extensibility;

import java.util.List;
import org.spongepowered.asm.lib.tree.ClassNode;
import org.spongepowered.asm.mixin.MixinEnvironment;

public interface IMixinInfo {
  boolean isDetachedSuper();
  
  String getClassRef();
  
  IMixinConfig getConfig();
  
  int getPriority();
  
  String getName();
  
  ClassNode getClassNode(int paramInt);
  
  String getClassName();
  
  List<String> getTargetClasses();
  
  byte[] getClassBytes();
  
  MixinEnvironment.Phase getPhase();
}
