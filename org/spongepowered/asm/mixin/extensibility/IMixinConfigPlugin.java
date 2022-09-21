package org.spongepowered.asm.mixin.extensibility;

import java.util.List;
import java.util.Set;
import org.spongepowered.asm.lib.tree.ClassNode;

public interface IMixinConfigPlugin {
  void preApply(String paramString1, ClassNode paramClassNode, String paramString2, IMixinInfo paramIMixinInfo);
  
  void acceptTargets(Set<String> paramSet1, Set<String> paramSet2);
  
  void onLoad(String paramString);
  
  boolean shouldApplyMixin(String paramString1, String paramString2);
  
  List<String> getMixins();
  
  void postApply(String paramString1, ClassNode paramClassNode, String paramString2, IMixinInfo paramIMixinInfo);
  
  String getRefMapperConfig();
}
