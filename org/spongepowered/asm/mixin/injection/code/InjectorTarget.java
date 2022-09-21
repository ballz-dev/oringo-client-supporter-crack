package org.spongepowered.asm.mixin.injection.code;

import java.util.HashMap;
import java.util.Map;
import org.spongepowered.asm.lib.tree.AnnotationNode;
import org.spongepowered.asm.lib.tree.InsnList;
import org.spongepowered.asm.lib.tree.MethodNode;
import org.spongepowered.asm.mixin.injection.InjectionPoint;
import org.spongepowered.asm.mixin.injection.struct.Target;
import org.spongepowered.asm.mixin.transformer.meta.MixinMerged;
import org.spongepowered.asm.util.Annotations;

public class InjectorTarget {
  private final Map<String, ReadOnlyInsnList> cache = new HashMap<String, ReadOnlyInsnList>();
  
  private final ISliceContext context;
  
  private final String mergedBy;
  
  private final Target target;
  
  private final int mergedPriority;
  
  public InjectorTarget(ISliceContext paramISliceContext, Target paramTarget) {
    this.context = paramISliceContext;
    this.target = paramTarget;
    AnnotationNode annotationNode = Annotations.getVisible(paramTarget.method, MixinMerged.class);
    this.mergedBy = (String)Annotations.getValue(annotationNode, "mixin");
    this.mergedPriority = ((Integer)Annotations.getValue(annotationNode, "priority", Integer.valueOf(1000))).intValue();
  }
  
  public String toString() {
    return this.target.toString();
  }
  
  public Target getTarget() {
    return this.target;
  }
  
  public MethodNode getMethod() {
    return this.target.method;
  }
  
  public boolean isMerged() {
    return (this.mergedBy != null);
  }
  
  public String getMergedBy() {
    return this.mergedBy;
  }
  
  public int getMergedPriority() {
    return this.mergedPriority;
  }
  
  public InsnList getSlice(String paramString) {
    ReadOnlyInsnList readOnlyInsnList = this.cache.get(paramString);
    if (readOnlyInsnList == null) {
      MethodSlice methodSlice = this.context.getSlice(paramString);
      if (methodSlice != null) {
        readOnlyInsnList = methodSlice.getSlice(this.target.method);
      } else {
        readOnlyInsnList = new ReadOnlyInsnList(this.target.method.instructions);
      } 
      this.cache.put(paramString, readOnlyInsnList);
    } 
    return readOnlyInsnList;
  }
  
  public InsnList getSlice(InjectionPoint paramInjectionPoint) {
    return getSlice(paramInjectionPoint.getSlice());
  }
  
  public void dispose() {
    for (ReadOnlyInsnList readOnlyInsnList : this.cache.values())
      readOnlyInsnList.dispose(); 
    this.cache.clear();
  }
}
