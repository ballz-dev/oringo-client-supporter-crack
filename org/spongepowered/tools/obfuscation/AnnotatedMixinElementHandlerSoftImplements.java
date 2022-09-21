package org.spongepowered.tools.obfuscation;

import java.util.List;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.type.DeclaredType;
import javax.tools.Diagnostic;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.obfuscation.mapping.common.MappingMethod;
import org.spongepowered.tools.obfuscation.interfaces.IMixinAnnotationProcessor;
import org.spongepowered.tools.obfuscation.mirror.AnnotationHandle;
import org.spongepowered.tools.obfuscation.mirror.MethodHandle;
import org.spongepowered.tools.obfuscation.mirror.TypeHandle;
import org.spongepowered.tools.obfuscation.mirror.TypeUtils;

public class AnnotatedMixinElementHandlerSoftImplements extends AnnotatedMixinElementHandler {
  AnnotatedMixinElementHandlerSoftImplements(IMixinAnnotationProcessor paramIMixinAnnotationProcessor, AnnotatedMixin paramAnnotatedMixin) {
    super(paramIMixinAnnotationProcessor, paramAnnotatedMixin);
  }
  
  public void process(AnnotationHandle paramAnnotationHandle) {
    if (!this.mixin.remap())
      return; 
    List list = paramAnnotationHandle.getAnnotationList("value");
    if (list.size() < 1) {
      this.ap.printMessage(Diagnostic.Kind.WARNING, "Empty @Implements annotation", this.mixin.getMixin(), paramAnnotationHandle.asMirror());
      return;
    } 
    for (AnnotationHandle annotationHandle : list) {
      Interface.Remap remap = (Interface.Remap)annotationHandle.getValue("remap", Interface.Remap.ALL);
      if (remap == Interface.Remap.NONE)
        continue; 
      try {
        TypeHandle typeHandle = new TypeHandle((DeclaredType)annotationHandle.getValue("iface"));
        String str = (String)annotationHandle.getValue("prefix");
        processSoftImplements(remap, typeHandle, str);
      } catch (Exception exception) {
        this.ap.printMessage(Diagnostic.Kind.ERROR, "Unexpected error: " + exception.getClass().getName() + ": " + exception.getMessage(), this.mixin.getMixin(), annotationHandle
            .asMirror());
      } 
    } 
  }
  
  private void processSoftImplements(Interface.Remap paramRemap, TypeHandle paramTypeHandle, String paramString) {
    for (ExecutableElement executableElement : paramTypeHandle.getEnclosedElements(new ElementKind[] { ElementKind.METHOD }))
      processMethod(paramRemap, paramTypeHandle, paramString, executableElement); 
    for (TypeHandle typeHandle : paramTypeHandle.getInterfaces())
      processSoftImplements(paramRemap, typeHandle, paramString); 
  }
  
  private void processMethod(Interface.Remap paramRemap, TypeHandle paramTypeHandle, String paramString, ExecutableElement paramExecutableElement) {
    String str1 = paramExecutableElement.getSimpleName().toString();
    String str2 = TypeUtils.getJavaSignature(paramExecutableElement);
    String str3 = TypeUtils.getDescriptor(paramExecutableElement);
    if (paramRemap != Interface.Remap.ONLY_PREFIXED) {
      MethodHandle methodHandle = this.mixin.getHandle().findMethod(str1, str2);
      if (methodHandle != null)
        addInterfaceMethodMapping(paramRemap, paramTypeHandle, (String)null, methodHandle, str1, str3); 
    } 
    if (paramString != null) {
      MethodHandle methodHandle = this.mixin.getHandle().findMethod(paramString + str1, str2);
      if (methodHandle != null)
        addInterfaceMethodMapping(paramRemap, paramTypeHandle, paramString, methodHandle, str1, str3); 
    } 
  }
  
  private void addInterfaceMethodMapping(Interface.Remap paramRemap, TypeHandle paramTypeHandle, String paramString1, MethodHandle paramMethodHandle, String paramString2, String paramString3) {
    MappingMethod mappingMethod = new MappingMethod(paramTypeHandle.getName(), paramString2, paramString3);
    ObfuscationData<MappingMethod> obfuscationData = this.obf.getDataProvider().getObfMethod(mappingMethod);
    if (obfuscationData.isEmpty()) {
      if (paramRemap.forceRemap())
        this.ap.printMessage(Diagnostic.Kind.ERROR, "No obfuscation mapping for soft-implementing method", paramMethodHandle.getElement()); 
      return;
    } 
    addMethodMappings(paramMethodHandle.getName(), paramString3, applyPrefix(obfuscationData, paramString1));
  }
  
  private ObfuscationData<MappingMethod> applyPrefix(ObfuscationData<MappingMethod> paramObfuscationData, String paramString) {
    if (paramString == null)
      return paramObfuscationData; 
    ObfuscationData<MappingMethod> obfuscationData = new ObfuscationData();
    for (ObfuscationType obfuscationType : paramObfuscationData) {
      MappingMethod mappingMethod = paramObfuscationData.get(obfuscationType);
      obfuscationData.put(obfuscationType, mappingMethod.addPrefix(paramString));
    } 
    return obfuscationData;
  }
}
