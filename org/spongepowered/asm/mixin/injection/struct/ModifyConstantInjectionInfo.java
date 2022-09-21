package org.spongepowered.asm.mixin.injection.struct;

import com.google.common.base.Strings;
import com.google.common.collect.ImmutableList;
import java.util.List;
import org.spongepowered.asm.lib.Type;
import org.spongepowered.asm.lib.tree.AnnotationNode;
import org.spongepowered.asm.lib.tree.MethodNode;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.code.Injector;
import org.spongepowered.asm.mixin.injection.invoke.ModifyConstantInjector;
import org.spongepowered.asm.mixin.injection.points.BeforeConstant;
import org.spongepowered.asm.mixin.transformer.MixinTargetContext;

public class ModifyConstantInjectionInfo extends InjectionInfo {
  private static final String CONSTANT_ANNOTATION_CLASS = Constant.class.getName().replace('.', '/');
  
  public ModifyConstantInjectionInfo(MixinTargetContext paramMixinTargetContext, MethodNode paramMethodNode, AnnotationNode paramAnnotationNode) {
    super(paramMixinTargetContext, paramMethodNode, paramAnnotationNode, "constant");
  }
  
  protected List<AnnotationNode> readInjectionPoints(String paramString) {
    ImmutableList immutableList;
    List<AnnotationNode> list = super.readInjectionPoints(paramString);
    if (list.isEmpty()) {
      AnnotationNode annotationNode = new AnnotationNode(CONSTANT_ANNOTATION_CLASS);
      annotationNode.visit("log", Boolean.TRUE);
      immutableList = ImmutableList.of(annotationNode);
    } 
    return (List<AnnotationNode>)immutableList;
  }
  
  protected void parseInjectionPoints(List<AnnotationNode> paramList) {
    Type type = Type.getReturnType(this.method.desc);
    for (AnnotationNode annotationNode : paramList)
      this.injectionPoints.add(new BeforeConstant(getContext(), annotationNode, type.getDescriptor())); 
  }
  
  protected Injector parseInjector(AnnotationNode paramAnnotationNode) {
    return (Injector)new ModifyConstantInjector(this);
  }
  
  protected String getDescription() {
    return "Constant modifier method";
  }
  
  public String getSliceId(String paramString) {
    return Strings.nullToEmpty(paramString);
  }
}
