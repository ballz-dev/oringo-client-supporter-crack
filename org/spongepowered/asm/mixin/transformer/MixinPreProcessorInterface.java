package org.spongepowered.asm.mixin.transformer;

import org.spongepowered.asm.lib.tree.AnnotationNode;
import org.spongepowered.asm.lib.tree.FieldNode;
import org.spongepowered.asm.mixin.transformer.throwables.InvalidInterfaceMixinException;
import org.spongepowered.asm.util.Bytecode;

class MixinPreProcessorInterface extends MixinPreProcessorStandard {
  MixinPreProcessorInterface(MixinInfo paramMixinInfo, MixinInfo.MixinClassNode paramMixinClassNode) {
    super(paramMixinInfo, paramMixinClassNode);
  }
  
  protected void prepareMethod(MixinInfo.MixinMethodNode paramMixinMethodNode, ClassInfo.Method paramMethod) {
    if (!Bytecode.hasFlag(paramMixinMethodNode, 1) && !Bytecode.hasFlag(paramMixinMethodNode, 4096))
      throw new InvalidInterfaceMixinException(this.mixin, "Interface mixin contains a non-public method! Found " + paramMethod + " in " + this.mixin); 
    super.prepareMethod(paramMixinMethodNode, paramMethod);
  }
  
  protected boolean validateField(MixinTargetContext paramMixinTargetContext, FieldNode paramFieldNode, AnnotationNode paramAnnotationNode) {
    if (!Bytecode.hasFlag(paramFieldNode, 8))
      throw new InvalidInterfaceMixinException(this.mixin, "Interface mixin contains an instance field! Found " + paramFieldNode.name + " in " + this.mixin); 
    return super.validateField(paramMixinTargetContext, paramFieldNode, paramAnnotationNode);
  }
}
