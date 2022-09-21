package org.spongepowered.asm.mixin.transformer;

import java.util.Map;
import org.spongepowered.asm.lib.tree.FieldNode;
import org.spongepowered.asm.lib.tree.MethodNode;
import org.spongepowered.asm.mixin.injection.struct.InjectionInfo;
import org.spongepowered.asm.mixin.injection.throwables.InvalidInjectionException;
import org.spongepowered.asm.mixin.transformer.throwables.InvalidInterfaceMixinException;

class MixinApplicatorInterface extends MixinApplicatorStandard {
  MixinApplicatorInterface(TargetClassContext paramTargetClassContext) {
    super(paramTargetClassContext);
  }
  
  protected void applyInterfaces(MixinTargetContext paramMixinTargetContext) {
    for (String str : paramMixinTargetContext.getInterfaces()) {
      if (!this.targetClass.name.equals(str) && !this.targetClass.interfaces.contains(str)) {
        this.targetClass.interfaces.add(str);
        paramMixinTargetContext.getTargetClassInfo().addInterface(str);
      } 
    } 
  }
  
  protected void applyFields(MixinTargetContext paramMixinTargetContext) {
    for (Map.Entry<FieldNode, ClassInfo.Field> entry : paramMixinTargetContext.getShadowFields()) {
      FieldNode fieldNode = (FieldNode)entry.getKey();
      this.logger.error("Ignoring redundant @Shadow field {}:{} in {}", new Object[] { fieldNode.name, fieldNode.desc, paramMixinTargetContext });
    } 
    mergeNewFields(paramMixinTargetContext);
  }
  
  protected void applyInitialisers(MixinTargetContext paramMixinTargetContext) {}
  
  protected void prepareInjections(MixinTargetContext paramMixinTargetContext) {
    for (MethodNode methodNode : this.targetClass.methods) {
      try {
        InjectionInfo injectionInfo = InjectionInfo.parse(paramMixinTargetContext, methodNode);
        if (injectionInfo != null)
          throw new InvalidInterfaceMixinException(paramMixinTargetContext, injectionInfo + " is not supported on interface mixin method " + methodNode.name); 
      } catch (InvalidInjectionException invalidInjectionException) {
        String str = (invalidInjectionException.getInjectionInfo() != null) ? invalidInjectionException.getInjectionInfo().toString() : "Injection";
        throw new InvalidInterfaceMixinException(paramMixinTargetContext, str + " is not supported in interface mixin");
      } 
    } 
  }
  
  protected void applyInjections(MixinTargetContext paramMixinTargetContext) {}
}
