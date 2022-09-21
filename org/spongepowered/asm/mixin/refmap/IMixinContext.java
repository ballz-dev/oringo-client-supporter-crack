package org.spongepowered.asm.mixin.refmap;

import org.spongepowered.asm.lib.tree.MethodNode;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;
import org.spongepowered.asm.mixin.injection.struct.Target;
import org.spongepowered.asm.mixin.transformer.ext.Extensions;

public interface IMixinContext {
  String getClassRef();
  
  int getPriority();
  
  IMixinInfo getMixin();
  
  Extensions getExtensions();
  
  boolean getOption(MixinEnvironment.Option paramOption);
  
  Target getTargetMethod(MethodNode paramMethodNode);
  
  String getTargetClassRef();
  
  String getClassName();
  
  IReferenceMapper getReferenceMapper();
}
