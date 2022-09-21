package org.spongepowered.tools.obfuscation;

import javax.annotation.processing.Messager;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.VariableElement;
import javax.tools.Diagnostic;
import org.spongepowered.asm.obfuscation.mapping.IMapping;
import org.spongepowered.asm.obfuscation.mapping.common.MappingField;
import org.spongepowered.asm.obfuscation.mapping.common.MappingMethod;
import org.spongepowered.tools.obfuscation.interfaces.IMixinAnnotationProcessor;
import org.spongepowered.tools.obfuscation.interfaces.IObfuscationDataProvider;
import org.spongepowered.tools.obfuscation.mirror.AnnotationHandle;
import org.spongepowered.tools.obfuscation.mirror.TypeHandle;

class AnnotatedMixinElementHandlerShadow extends AnnotatedMixinElementHandler {
  static abstract class AnnotatedElementShadow<E extends Element, M extends IMapping<M>> extends AnnotatedMixinElementHandler.AnnotatedElement<E> {
    private final AnnotatedMixinElementHandler.ShadowElementName name;
    
    private final boolean shouldRemap;
    
    private final IMapping.Type type;
    
    protected AnnotatedElementShadow(E param1E, AnnotationHandle param1AnnotationHandle, boolean param1Boolean, IMapping.Type param1Type) {
      super(param1E, param1AnnotationHandle);
      this.shouldRemap = param1Boolean;
      this.name = new AnnotatedMixinElementHandler.ShadowElementName((Element)param1E, param1AnnotationHandle);
      this.type = param1Type;
    }
    
    public boolean shouldRemap() {
      return this.shouldRemap;
    }
    
    public AnnotatedMixinElementHandler.ShadowElementName getName() {
      return this.name;
    }
    
    public IMapping.Type getElementType() {
      return this.type;
    }
    
    public String toString() {
      return getElementType().name().toLowerCase();
    }
    
    public AnnotatedMixinElementHandler.ShadowElementName setObfuscatedName(IMapping<?> param1IMapping) {
      return setObfuscatedName(param1IMapping.getSimpleName());
    }
    
    public AnnotatedMixinElementHandler.ShadowElementName setObfuscatedName(String param1String) {
      return getName().setObfuscatedName(param1String);
    }
    
    public ObfuscationData<M> getObfuscationData(IObfuscationDataProvider param1IObfuscationDataProvider, TypeHandle param1TypeHandle) {
      return param1IObfuscationDataProvider.getObfEntry((IMapping)getMapping(param1TypeHandle, getName().toString(), getDesc()));
    }
    
    public abstract void addMapping(ObfuscationType param1ObfuscationType, IMapping<?> param1IMapping);
    
    public abstract M getMapping(TypeHandle param1TypeHandle, String param1String1, String param1String2);
  }
  
  class AnnotatedElementShadowField extends AnnotatedElementShadow<VariableElement, MappingField> {
    public AnnotatedElementShadowField(VariableElement param1VariableElement, AnnotationHandle param1AnnotationHandle, boolean param1Boolean) {
      super(param1VariableElement, param1AnnotationHandle, param1Boolean, IMapping.Type.FIELD);
    }
    
    public MappingField getMapping(TypeHandle param1TypeHandle, String param1String1, String param1String2) {
      return new MappingField(param1TypeHandle.getName(), param1String1, param1String2);
    }
    
    public void addMapping(ObfuscationType param1ObfuscationType, IMapping<?> param1IMapping) {
      AnnotatedMixinElementHandlerShadow.this.addFieldMapping(param1ObfuscationType, setObfuscatedName(param1IMapping), getDesc(), param1IMapping.getDesc());
    }
  }
  
  class AnnotatedElementShadowMethod extends AnnotatedElementShadow<ExecutableElement, MappingMethod> {
    public AnnotatedElementShadowMethod(ExecutableElement param1ExecutableElement, AnnotationHandle param1AnnotationHandle, boolean param1Boolean) {
      super(param1ExecutableElement, param1AnnotationHandle, param1Boolean, IMapping.Type.METHOD);
    }
    
    public MappingMethod getMapping(TypeHandle param1TypeHandle, String param1String1, String param1String2) {
      return param1TypeHandle.getMappingMethod(param1String1, param1String2);
    }
    
    public void addMapping(ObfuscationType param1ObfuscationType, IMapping<?> param1IMapping) {
      AnnotatedMixinElementHandlerShadow.this.addMethodMapping(param1ObfuscationType, setObfuscatedName(param1IMapping), getDesc(), param1IMapping.getDesc());
    }
  }
  
  AnnotatedMixinElementHandlerShadow(IMixinAnnotationProcessor paramIMixinAnnotationProcessor, AnnotatedMixin paramAnnotatedMixin) {
    super(paramIMixinAnnotationProcessor, paramAnnotatedMixin);
  }
  
  public void registerShadow(AnnotatedElementShadow<?, ?> paramAnnotatedElementShadow) {
    validateTarget((Element)paramAnnotatedElementShadow.getElement(), paramAnnotatedElementShadow.getAnnotation(), paramAnnotatedElementShadow.getName(), "@Shadow");
    if (!paramAnnotatedElementShadow.shouldRemap())
      return; 
    for (TypeHandle typeHandle : this.mixin.getTargets())
      registerShadowForTarget(paramAnnotatedElementShadow, typeHandle); 
  }
  
  private void registerShadowForTarget(AnnotatedElementShadow<?, ?> paramAnnotatedElementShadow, TypeHandle paramTypeHandle) {
    ObfuscationData<?> obfuscationData = paramAnnotatedElementShadow.getObfuscationData(this.obf.getDataProvider(), paramTypeHandle);
    if (obfuscationData.isEmpty()) {
      String str = this.mixin.isMultiTarget() ? (" in target " + paramTypeHandle) : "";
      if (paramTypeHandle.isSimulated()) {
        paramAnnotatedElementShadow.printMessage((Messager)this.ap, Diagnostic.Kind.WARNING, "Unable to locate obfuscation mapping" + str + " for @Shadow " + paramAnnotatedElementShadow);
      } else {
        paramAnnotatedElementShadow.printMessage((Messager)this.ap, Diagnostic.Kind.WARNING, "Unable to locate obfuscation mapping" + str + " for @Shadow " + paramAnnotatedElementShadow);
      } 
      return;
    } 
    for (ObfuscationType obfuscationType : obfuscationData) {
      try {
        paramAnnotatedElementShadow.addMapping(obfuscationType, (IMapping)obfuscationData.get(obfuscationType));
      } catch (MappingConflictException mappingConflictException) {
        paramAnnotatedElementShadow.printMessage((Messager)this.ap, Diagnostic.Kind.ERROR, "Mapping conflict for @Shadow " + paramAnnotatedElementShadow + ": " + mappingConflictException.getNew().getSimpleName() + " for target " + paramTypeHandle + " conflicts with existing mapping " + mappingConflictException
            .getOld().getSimpleName());
      } 
    } 
  }
}
