package org.spongepowered.tools.obfuscation;

import com.google.common.base.Strings;
import javax.annotation.processing.Messager;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.tools.Diagnostic;
import org.spongepowered.asm.lib.tree.MethodNode;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;
import org.spongepowered.asm.mixin.gen.AccessorInfo;
import org.spongepowered.asm.mixin.injection.struct.MemberInfo;
import org.spongepowered.asm.mixin.injection.struct.Target;
import org.spongepowered.asm.mixin.refmap.IMixinContext;
import org.spongepowered.asm.mixin.refmap.IReferenceMapper;
import org.spongepowered.asm.mixin.refmap.ReferenceMapper;
import org.spongepowered.asm.mixin.transformer.ext.Extensions;
import org.spongepowered.asm.obfuscation.mapping.IMapping;
import org.spongepowered.tools.obfuscation.interfaces.IMixinAnnotationProcessor;
import org.spongepowered.tools.obfuscation.mirror.AnnotationHandle;
import org.spongepowered.tools.obfuscation.mirror.FieldHandle;
import org.spongepowered.tools.obfuscation.mirror.MethodHandle;
import org.spongepowered.tools.obfuscation.mirror.TypeHandle;
import org.spongepowered.tools.obfuscation.mirror.TypeUtils;

public class AnnotatedMixinElementHandlerAccessor extends AnnotatedMixinElementHandler implements IMixinContext {
  static class AnnotatedElementAccessor extends AnnotatedMixinElementHandler.AnnotatedElement<ExecutableElement> {
    private final TypeMirror returnType;
    
    private String targetName;
    
    private final boolean shouldRemap;
    
    public AnnotatedElementAccessor(ExecutableElement param1ExecutableElement, AnnotationHandle param1AnnotationHandle, boolean param1Boolean) {
      super(param1ExecutableElement, param1AnnotationHandle);
      this.shouldRemap = param1Boolean;
      this.returnType = getElement().getReturnType();
    }
    
    public boolean shouldRemap() {
      return this.shouldRemap;
    }
    
    public String getAnnotationValue() {
      return (String)getAnnotation().getValue();
    }
    
    public TypeMirror getTargetType() {
      switch (getAccessorType()) {
        case FIELD_GETTER:
          return this.returnType;
        case FIELD_SETTER:
          return ((VariableElement)getElement().getParameters().get(0)).asType();
      } 
      return null;
    }
    
    public String getTargetTypeName() {
      return TypeUtils.getTypeName(getTargetType());
    }
    
    public String getAccessorDesc() {
      return TypeUtils.getInternalName(getTargetType());
    }
    
    public MemberInfo getContext() {
      return new MemberInfo(getTargetName(), null, getAccessorDesc());
    }
    
    public AccessorInfo.AccessorType getAccessorType() {
      return (this.returnType.getKind() == TypeKind.VOID) ? AccessorInfo.AccessorType.FIELD_SETTER : AccessorInfo.AccessorType.FIELD_GETTER;
    }
    
    public void setTargetName(String param1String) {
      this.targetName = param1String;
    }
    
    public String getTargetName() {
      return this.targetName;
    }
    
    public String toString() {
      return (this.targetName != null) ? this.targetName : "<invalid>";
    }
  }
  
  static class AnnotatedElementInvoker extends AnnotatedElementAccessor {
    public AnnotatedElementInvoker(ExecutableElement param1ExecutableElement, AnnotationHandle param1AnnotationHandle, boolean param1Boolean) {
      super(param1ExecutableElement, param1AnnotationHandle, param1Boolean);
    }
    
    public String getAccessorDesc() {
      return TypeUtils.getDescriptor(getElement());
    }
    
    public AccessorInfo.AccessorType getAccessorType() {
      return AccessorInfo.AccessorType.METHOD_PROXY;
    }
    
    public String getTargetTypeName() {
      return TypeUtils.getJavaSignature(getElement());
    }
  }
  
  public AnnotatedMixinElementHandlerAccessor(IMixinAnnotationProcessor paramIMixinAnnotationProcessor, AnnotatedMixin paramAnnotatedMixin) {
    super(paramIMixinAnnotationProcessor, paramAnnotatedMixin);
  }
  
  public ReferenceMapper getReferenceMapper() {
    return null;
  }
  
  public String getClassName() {
    return this.mixin.getClassRef().replace('/', '.');
  }
  
  public String getClassRef() {
    return this.mixin.getClassRef();
  }
  
  public String getTargetClassRef() {
    throw new UnsupportedOperationException("Target class not available at compile time");
  }
  
  public IMixinInfo getMixin() {
    throw new UnsupportedOperationException("MixinInfo not available at compile time");
  }
  
  public Extensions getExtensions() {
    throw new UnsupportedOperationException("Mixin Extensions not available at compile time");
  }
  
  public boolean getOption(MixinEnvironment.Option paramOption) {
    throw new UnsupportedOperationException("Options not available at compile time");
  }
  
  public int getPriority() {
    throw new UnsupportedOperationException("Priority not available at compile time");
  }
  
  public Target getTargetMethod(MethodNode paramMethodNode) {
    throw new UnsupportedOperationException("Target not available at compile time");
  }
  
  public void registerAccessor(AnnotatedElementAccessor paramAnnotatedElementAccessor) {
    if (paramAnnotatedElementAccessor.getAccessorType() == null) {
      paramAnnotatedElementAccessor.printMessage((Messager)this.ap, Diagnostic.Kind.WARNING, "Unsupported accessor type");
      return;
    } 
    String str = getAccessorTargetName(paramAnnotatedElementAccessor);
    if (str == null) {
      paramAnnotatedElementAccessor.printMessage((Messager)this.ap, Diagnostic.Kind.WARNING, "Cannot inflect accessor target name");
      return;
    } 
    paramAnnotatedElementAccessor.setTargetName(str);
    for (TypeHandle typeHandle : this.mixin.getTargets()) {
      if (paramAnnotatedElementAccessor.getAccessorType() == AccessorInfo.AccessorType.METHOD_PROXY) {
        registerInvokerForTarget((AnnotatedElementInvoker)paramAnnotatedElementAccessor, typeHandle);
        continue;
      } 
      registerAccessorForTarget(paramAnnotatedElementAccessor, typeHandle);
    } 
  }
  
  private void registerAccessorForTarget(AnnotatedElementAccessor paramAnnotatedElementAccessor, TypeHandle paramTypeHandle) {
    FieldHandle fieldHandle = paramTypeHandle.findField(paramAnnotatedElementAccessor.getTargetName(), paramAnnotatedElementAccessor.getTargetTypeName(), false);
    if (fieldHandle == null) {
      if (!paramTypeHandle.isImaginary()) {
        paramAnnotatedElementAccessor.printMessage((Messager)this.ap, Diagnostic.Kind.ERROR, "Could not locate @Accessor target " + paramAnnotatedElementAccessor + " in target " + paramTypeHandle);
        return;
      } 
      fieldHandle = new FieldHandle(paramTypeHandle.getName(), paramAnnotatedElementAccessor.getTargetName(), paramAnnotatedElementAccessor.getDesc());
    } 
    if (!paramAnnotatedElementAccessor.shouldRemap())
      return; 
    ObfuscationData<IMapping> obfuscationData = this.obf.getDataProvider().getObfField(fieldHandle.asMapping(false).move(paramTypeHandle.getName()));
    if (obfuscationData.isEmpty()) {
      String str = this.mixin.isMultiTarget() ? (" in target " + paramTypeHandle) : "";
      paramAnnotatedElementAccessor.printMessage((Messager)this.ap, Diagnostic.Kind.WARNING, "Unable to locate obfuscation mapping" + str + " for @Accessor target " + paramAnnotatedElementAccessor);
      return;
    } 
    obfuscationData = AnnotatedMixinElementHandler.stripOwnerData(obfuscationData);
    try {
      this.obf.getReferenceManager().addFieldMapping(this.mixin.getClassRef(), paramAnnotatedElementAccessor.getTargetName(), paramAnnotatedElementAccessor.getContext(), obfuscationData);
    } catch (ReferenceConflictException referenceConflictException) {
      paramAnnotatedElementAccessor.printMessage((Messager)this.ap, Diagnostic.Kind.ERROR, "Mapping conflict for @Accessor target " + paramAnnotatedElementAccessor + ": " + referenceConflictException.getNew() + " for target " + paramTypeHandle + " conflicts with existing mapping " + referenceConflictException
          .getOld());
    } 
  }
  
  private void registerInvokerForTarget(AnnotatedElementInvoker paramAnnotatedElementInvoker, TypeHandle paramTypeHandle) {
    MethodHandle methodHandle = paramTypeHandle.findMethod(paramAnnotatedElementInvoker.getTargetName(), paramAnnotatedElementInvoker.getTargetTypeName(), false);
    if (methodHandle == null) {
      if (!paramTypeHandle.isImaginary()) {
        paramAnnotatedElementInvoker.printMessage((Messager)this.ap, Diagnostic.Kind.ERROR, "Could not locate @Invoker target " + paramAnnotatedElementInvoker + " in target " + paramTypeHandle);
        return;
      } 
      methodHandle = new MethodHandle(paramTypeHandle, paramAnnotatedElementInvoker.getTargetName(), paramAnnotatedElementInvoker.getDesc());
    } 
    if (!paramAnnotatedElementInvoker.shouldRemap())
      return; 
    ObfuscationData<IMapping> obfuscationData = this.obf.getDataProvider().getObfMethod(methodHandle.asMapping(false).move(paramTypeHandle.getName()));
    if (obfuscationData.isEmpty()) {
      String str = this.mixin.isMultiTarget() ? (" in target " + paramTypeHandle) : "";
      paramAnnotatedElementInvoker.printMessage((Messager)this.ap, Diagnostic.Kind.WARNING, "Unable to locate obfuscation mapping" + str + " for @Accessor target " + paramAnnotatedElementInvoker);
      return;
    } 
    obfuscationData = AnnotatedMixinElementHandler.stripOwnerData(obfuscationData);
    try {
      this.obf.getReferenceManager().addMethodMapping(this.mixin.getClassRef(), paramAnnotatedElementInvoker.getTargetName(), paramAnnotatedElementInvoker.getContext(), obfuscationData);
    } catch (ReferenceConflictException referenceConflictException) {
      paramAnnotatedElementInvoker.printMessage((Messager)this.ap, Diagnostic.Kind.ERROR, "Mapping conflict for @Invoker target " + paramAnnotatedElementInvoker + ": " + referenceConflictException.getNew() + " for target " + paramTypeHandle + " conflicts with existing mapping " + referenceConflictException
          .getOld());
    } 
  }
  
  private String getAccessorTargetName(AnnotatedElementAccessor paramAnnotatedElementAccessor) {
    String str = paramAnnotatedElementAccessor.getAnnotationValue();
    if (Strings.isNullOrEmpty(str))
      return inflectAccessorTarget(paramAnnotatedElementAccessor); 
    return str;
  }
  
  private String inflectAccessorTarget(AnnotatedElementAccessor paramAnnotatedElementAccessor) {
    return AccessorInfo.inflectTarget(paramAnnotatedElementAccessor.getSimpleName(), paramAnnotatedElementAccessor.getAccessorType(), "", this, false);
  }
}
