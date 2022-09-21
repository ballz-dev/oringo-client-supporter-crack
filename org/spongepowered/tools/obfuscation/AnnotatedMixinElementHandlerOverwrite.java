package org.spongepowered.tools.obfuscation;

import java.lang.reflect.Method;
import javax.annotation.processing.Messager;
import javax.lang.model.element.ExecutableElement;
import javax.tools.Diagnostic;
import org.spongepowered.asm.obfuscation.mapping.common.MappingMethod;
import org.spongepowered.tools.obfuscation.interfaces.IMixinAnnotationProcessor;
import org.spongepowered.tools.obfuscation.mirror.AnnotationHandle;
import org.spongepowered.tools.obfuscation.mirror.TypeHandle;

class AnnotatedMixinElementHandlerOverwrite extends AnnotatedMixinElementHandler {
  static class AnnotatedElementOverwrite extends AnnotatedMixinElementHandler.AnnotatedElement<ExecutableElement> {
    private final boolean shouldRemap;
    
    public AnnotatedElementOverwrite(ExecutableElement param1ExecutableElement, AnnotationHandle param1AnnotationHandle, boolean param1Boolean) {
      super(param1ExecutableElement, param1AnnotationHandle);
      this.shouldRemap = param1Boolean;
    }
    
    public boolean shouldRemap() {
      return this.shouldRemap;
    }
  }
  
  AnnotatedMixinElementHandlerOverwrite(IMixinAnnotationProcessor paramIMixinAnnotationProcessor, AnnotatedMixin paramAnnotatedMixin) {
    super(paramIMixinAnnotationProcessor, paramAnnotatedMixin);
  }
  
  public void registerMerge(ExecutableElement paramExecutableElement) {
    validateTargetMethod(paramExecutableElement, null, new AnnotatedMixinElementHandler.AliasedElementName(paramExecutableElement, AnnotationHandle.MISSING), "overwrite", true, true);
  }
  
  public void registerOverwrite(AnnotatedElementOverwrite paramAnnotatedElementOverwrite) {
    AnnotatedMixinElementHandler.AliasedElementName aliasedElementName = new AnnotatedMixinElementHandler.AliasedElementName(paramAnnotatedElementOverwrite.getElement(), paramAnnotatedElementOverwrite.getAnnotation());
    validateTargetMethod(paramAnnotatedElementOverwrite.getElement(), paramAnnotatedElementOverwrite.getAnnotation(), aliasedElementName, "@Overwrite", true, false);
    checkConstraints(paramAnnotatedElementOverwrite.getElement(), paramAnnotatedElementOverwrite.getAnnotation());
    if (paramAnnotatedElementOverwrite.shouldRemap())
      for (TypeHandle typeHandle : this.mixin.getTargets()) {
        if (!registerOverwriteForTarget(paramAnnotatedElementOverwrite, typeHandle))
          return; 
      }  
    if (!"true".equalsIgnoreCase(this.ap.getOption("disableOverwriteChecker"))) {
      Diagnostic.Kind kind = "error".equalsIgnoreCase(this.ap.getOption("overwriteErrorLevel")) ? Diagnostic.Kind.ERROR : Diagnostic.Kind.WARNING;
      String str = this.ap.getJavadocProvider().getJavadoc(paramAnnotatedElementOverwrite.getElement());
      if (str == null) {
        this.ap.printMessage(kind, "@Overwrite is missing javadoc comment", paramAnnotatedElementOverwrite.getElement());
        return;
      } 
      if (!str.toLowerCase().contains("@author"))
        this.ap.printMessage(kind, "@Overwrite is missing an @author tag", paramAnnotatedElementOverwrite.getElement()); 
      if (!str.toLowerCase().contains("@reason"))
        this.ap.printMessage(kind, "@Overwrite is missing an @reason tag", paramAnnotatedElementOverwrite.getElement()); 
    } 
  }
  
  private boolean registerOverwriteForTarget(AnnotatedElementOverwrite paramAnnotatedElementOverwrite, TypeHandle paramTypeHandle) {
    MappingMethod mappingMethod = paramTypeHandle.getMappingMethod(paramAnnotatedElementOverwrite.getSimpleName(), paramAnnotatedElementOverwrite.getDesc());
    ObfuscationData<MappingMethod> obfuscationData = this.obf.getDataProvider().getObfMethod(mappingMethod);
    if (obfuscationData.isEmpty()) {
      Diagnostic.Kind kind = Diagnostic.Kind.ERROR;
      try {
        Method method = paramAnnotatedElementOverwrite.getElement().getClass().getMethod("isStatic", new Class[0]);
        if (((Boolean)method.invoke(paramAnnotatedElementOverwrite.getElement(), new Object[0])).booleanValue())
          kind = Diagnostic.Kind.WARNING; 
      } catch (Exception exception) {}
      this.ap.printMessage(kind, "No obfuscation mapping for @Overwrite method", paramAnnotatedElementOverwrite.getElement());
      return false;
    } 
    try {
      addMethodMappings(paramAnnotatedElementOverwrite.getSimpleName(), paramAnnotatedElementOverwrite.getDesc(), obfuscationData);
    } catch (MappingConflictException mappingConflictException) {
      paramAnnotatedElementOverwrite.printMessage((Messager)this.ap, Diagnostic.Kind.ERROR, "Mapping conflict for @Overwrite method: " + mappingConflictException.getNew().getSimpleName() + " for target " + paramTypeHandle + " conflicts with existing mapping " + mappingConflictException
          .getOld().getSimpleName());
      return false;
    } 
    return true;
  }
}
