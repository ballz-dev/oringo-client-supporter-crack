package org.spongepowered.tools.obfuscation;

import java.util.Iterator;
import java.util.List;
import javax.annotation.processing.Messager;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.VariableElement;
import javax.tools.Diagnostic;
import org.spongepowered.asm.mixin.injection.struct.MemberInfo;
import org.spongepowered.asm.obfuscation.mapping.IMapping;
import org.spongepowered.asm.obfuscation.mapping.common.MappingField;
import org.spongepowered.asm.obfuscation.mapping.common.MappingMethod;
import org.spongepowered.asm.util.ConstraintParser;
import org.spongepowered.asm.util.throwables.ConstraintViolationException;
import org.spongepowered.asm.util.throwables.InvalidConstraintException;
import org.spongepowered.tools.obfuscation.interfaces.IMixinAnnotationProcessor;
import org.spongepowered.tools.obfuscation.interfaces.IObfuscationManager;
import org.spongepowered.tools.obfuscation.mapping.IMappingConsumer;
import org.spongepowered.tools.obfuscation.mirror.AnnotationHandle;
import org.spongepowered.tools.obfuscation.mirror.FieldHandle;
import org.spongepowered.tools.obfuscation.mirror.MethodHandle;
import org.spongepowered.tools.obfuscation.mirror.TypeHandle;
import org.spongepowered.tools.obfuscation.mirror.TypeUtils;
import org.spongepowered.tools.obfuscation.mirror.Visibility;

abstract class AnnotatedMixinElementHandler {
  protected final String classRef;
  
  protected final IObfuscationManager obf;
  
  protected final IMixinAnnotationProcessor ap;
  
  private IMappingConsumer mappings;
  
  protected final AnnotatedMixin mixin;
  
  static abstract class AnnotatedElement<E extends Element> {
    private final String desc;
    
    protected final E element;
    
    protected final AnnotationHandle annotation;
    
    public AnnotatedElement(E param1E, AnnotationHandle param1AnnotationHandle) {
      this.element = param1E;
      this.annotation = param1AnnotationHandle;
      this.desc = TypeUtils.getDescriptor((Element)param1E);
    }
    
    public E getElement() {
      return this.element;
    }
    
    public AnnotationHandle getAnnotation() {
      return this.annotation;
    }
    
    public String getSimpleName() {
      return getElement().getSimpleName().toString();
    }
    
    public String getDesc() {
      return this.desc;
    }
    
    public final void printMessage(Messager param1Messager, Diagnostic.Kind param1Kind, CharSequence param1CharSequence) {
      param1Messager.printMessage(param1Kind, param1CharSequence, (Element)this.element, this.annotation.asMirror());
    }
  }
  
  static class AliasedElementName {
    private final List<String> aliases;
    
    protected final String originalName;
    
    private boolean caseSensitive;
    
    public AliasedElementName(Element param1Element, AnnotationHandle param1AnnotationHandle) {
      this.originalName = param1Element.getSimpleName().toString();
      this.aliases = param1AnnotationHandle.getList("aliases");
    }
    
    public AliasedElementName setCaseSensitive(boolean param1Boolean) {
      this.caseSensitive = param1Boolean;
      return this;
    }
    
    public boolean isCaseSensitive() {
      return this.caseSensitive;
    }
    
    public boolean hasAliases() {
      return (this.aliases.size() > 0);
    }
    
    public List<String> getAliases() {
      return this.aliases;
    }
    
    public String elementName() {
      return this.originalName;
    }
    
    public String baseName() {
      return this.originalName;
    }
    
    public boolean hasPrefix() {
      return false;
    }
  }
  
  static class ShadowElementName extends AliasedElementName {
    private final boolean hasPrefix;
    
    private final String prefix;
    
    private String obfuscated;
    
    private final String baseName;
    
    ShadowElementName(Element param1Element, AnnotationHandle param1AnnotationHandle) {
      super(param1Element, param1AnnotationHandle);
      this.prefix = (String)param1AnnotationHandle.getValue("prefix", "shadow$");
      boolean bool = false;
      String str = this.originalName;
      if (str.startsWith(this.prefix)) {
        bool = true;
        str = str.substring(this.prefix.length());
      } 
      this.hasPrefix = bool;
      this.obfuscated = this.baseName = str;
    }
    
    public String toString() {
      return this.baseName;
    }
    
    public String baseName() {
      return this.baseName;
    }
    
    public ShadowElementName setObfuscatedName(IMapping<?> param1IMapping) {
      this.obfuscated = param1IMapping.getName();
      return this;
    }
    
    public ShadowElementName setObfuscatedName(String param1String) {
      this.obfuscated = param1String;
      return this;
    }
    
    public boolean hasPrefix() {
      return this.hasPrefix;
    }
    
    public String prefix() {
      return this.hasPrefix ? this.prefix : "";
    }
    
    public String name() {
      return prefix(this.baseName);
    }
    
    public String obfuscated() {
      return prefix(this.obfuscated);
    }
    
    public String prefix(String param1String) {
      return this.hasPrefix ? (this.prefix + param1String) : param1String;
    }
  }
  
  AnnotatedMixinElementHandler(IMixinAnnotationProcessor paramIMixinAnnotationProcessor, AnnotatedMixin paramAnnotatedMixin) {
    this.ap = paramIMixinAnnotationProcessor;
    this.mixin = paramAnnotatedMixin;
    this.classRef = paramAnnotatedMixin.getClassRef();
    this.obf = paramIMixinAnnotationProcessor.getObfuscationManager();
  }
  
  private IMappingConsumer getMappings() {
    if (this.mappings == null) {
      IMappingConsumer iMappingConsumer = this.mixin.getMappings();
      if (iMappingConsumer instanceof Mappings) {
        this.mappings = ((Mappings)iMappingConsumer).asUnique();
      } else {
        this.mappings = iMappingConsumer;
      } 
    } 
    return this.mappings;
  }
  
  protected final void addFieldMappings(String paramString1, String paramString2, ObfuscationData<MappingField> paramObfuscationData) {
    for (ObfuscationType obfuscationType : paramObfuscationData) {
      MappingField mappingField = paramObfuscationData.get(obfuscationType);
      addFieldMapping(obfuscationType, paramString1, mappingField.getSimpleName(), paramString2, mappingField.getDesc());
    } 
  }
  
  protected final void addFieldMapping(ObfuscationType paramObfuscationType, ShadowElementName paramShadowElementName, String paramString1, String paramString2) {
    addFieldMapping(paramObfuscationType, paramShadowElementName.name(), paramShadowElementName.obfuscated(), paramString1, paramString2);
  }
  
  protected final void addFieldMapping(ObfuscationType paramObfuscationType, String paramString1, String paramString2, String paramString3, String paramString4) {
    MappingField mappingField1 = new MappingField(this.classRef, paramString1, paramString3);
    MappingField mappingField2 = new MappingField(this.classRef, paramString2, paramString4);
    getMappings().addFieldMapping(paramObfuscationType, mappingField1, mappingField2);
  }
  
  protected final void addMethodMappings(String paramString1, String paramString2, ObfuscationData<MappingMethod> paramObfuscationData) {
    for (ObfuscationType obfuscationType : paramObfuscationData) {
      MappingMethod mappingMethod = paramObfuscationData.get(obfuscationType);
      addMethodMapping(obfuscationType, paramString1, mappingMethod.getSimpleName(), paramString2, mappingMethod.getDesc());
    } 
  }
  
  protected final void addMethodMapping(ObfuscationType paramObfuscationType, ShadowElementName paramShadowElementName, String paramString1, String paramString2) {
    addMethodMapping(paramObfuscationType, paramShadowElementName.name(), paramShadowElementName.obfuscated(), paramString1, paramString2);
  }
  
  protected final void addMethodMapping(ObfuscationType paramObfuscationType, String paramString1, String paramString2, String paramString3, String paramString4) {
    MappingMethod mappingMethod1 = new MappingMethod(this.classRef, paramString1, paramString3);
    MappingMethod mappingMethod2 = new MappingMethod(this.classRef, paramString2, paramString4);
    getMappings().addMethodMapping(paramObfuscationType, mappingMethod1, mappingMethod2);
  }
  
  protected final void checkConstraints(ExecutableElement paramExecutableElement, AnnotationHandle paramAnnotationHandle) {
    try {
      ConstraintParser.Constraint constraint = ConstraintParser.parse((String)paramAnnotationHandle.getValue("constraints"));
      try {
        constraint.check(this.ap.getTokenProvider());
      } catch (ConstraintViolationException constraintViolationException) {
        this.ap.printMessage(Diagnostic.Kind.ERROR, constraintViolationException.getMessage(), paramExecutableElement, paramAnnotationHandle.asMirror());
      } 
    } catch (InvalidConstraintException invalidConstraintException) {
      this.ap.printMessage(Diagnostic.Kind.WARNING, invalidConstraintException.getMessage(), paramExecutableElement, paramAnnotationHandle.asMirror());
    } 
  }
  
  protected final void validateTarget(Element paramElement, AnnotationHandle paramAnnotationHandle, AliasedElementName paramAliasedElementName, String paramString) {
    if (paramElement instanceof ExecutableElement) {
      validateTargetMethod((ExecutableElement)paramElement, paramAnnotationHandle, paramAliasedElementName, paramString, false, false);
    } else if (paramElement instanceof VariableElement) {
      validateTargetField((VariableElement)paramElement, paramAnnotationHandle, paramAliasedElementName, paramString);
    } 
  }
  
  protected final void validateTargetMethod(ExecutableElement paramExecutableElement, AnnotationHandle paramAnnotationHandle, AliasedElementName paramAliasedElementName, String paramString, boolean paramBoolean1, boolean paramBoolean2) {
    String str = TypeUtils.getJavaSignature(paramExecutableElement);
    for (TypeHandle typeHandle : this.mixin.getTargets()) {
      if (typeHandle.isImaginary())
        continue; 
      MethodHandle methodHandle = typeHandle.findMethod(paramExecutableElement);
      if (methodHandle == null && paramAliasedElementName.hasPrefix())
        methodHandle = typeHandle.findMethod(paramAliasedElementName.baseName(), str); 
      if (methodHandle == null && paramAliasedElementName.hasAliases()) {
        String str1;
        Iterator<String> iterator = paramAliasedElementName.getAliases().iterator();
        do {
          str1 = iterator.next();
        } while (iterator.hasNext() && (
          methodHandle = typeHandle.findMethod(str1, str)) == null);
      } 
      if (methodHandle != null) {
        if (paramBoolean1)
          validateMethodVisibility(paramExecutableElement, paramAnnotationHandle, paramString, typeHandle, methodHandle); 
        continue;
      } 
      if (!paramBoolean2)
        printMessage(Diagnostic.Kind.WARNING, "Cannot find target for " + paramString + " method in " + typeHandle, paramExecutableElement, paramAnnotationHandle); 
    } 
  }
  
  private void validateMethodVisibility(ExecutableElement paramExecutableElement, AnnotationHandle paramAnnotationHandle, String paramString, TypeHandle paramTypeHandle, MethodHandle paramMethodHandle) {
    Visibility visibility1 = paramMethodHandle.getVisibility();
    if (visibility1 == null)
      return; 
    Visibility visibility2 = TypeUtils.getVisibility(paramExecutableElement);
    String str = "visibility of " + visibility1 + " method in " + paramTypeHandle;
    if (visibility1.ordinal() > visibility2.ordinal()) {
      printMessage(Diagnostic.Kind.WARNING, visibility2 + " " + paramString + " method cannot reduce " + str, paramExecutableElement, paramAnnotationHandle);
    } else if (visibility1 == Visibility.PRIVATE && visibility2.ordinal() > visibility1.ordinal()) {
      printMessage(Diagnostic.Kind.WARNING, visibility2 + " " + paramString + " method will upgrade " + str, paramExecutableElement, paramAnnotationHandle);
    } 
  }
  
  protected final void validateTargetField(VariableElement paramVariableElement, AnnotationHandle paramAnnotationHandle, AliasedElementName paramAliasedElementName, String paramString) {
    String str = paramVariableElement.asType().toString();
    for (TypeHandle typeHandle : this.mixin.getTargets()) {
      String str1;
      if (typeHandle.isImaginary())
        continue; 
      FieldHandle fieldHandle = typeHandle.findField(paramVariableElement);
      if (fieldHandle != null)
        continue; 
      List<String> list = paramAliasedElementName.getAliases();
      Iterator<String> iterator = list.iterator();
      do {
        str1 = iterator.next();
      } while (iterator.hasNext() && (
        fieldHandle = typeHandle.findField(str1, str)) == null);
      if (fieldHandle == null)
        this.ap.printMessage(Diagnostic.Kind.WARNING, "Cannot find target for " + paramString + " field in " + typeHandle, paramVariableElement, paramAnnotationHandle.asMirror()); 
    } 
  }
  
  protected final void validateReferencedTarget(ExecutableElement paramExecutableElement, AnnotationHandle paramAnnotationHandle, MemberInfo paramMemberInfo, String paramString) {
    String str = paramMemberInfo.toDescriptor();
    for (TypeHandle typeHandle : this.mixin.getTargets()) {
      if (typeHandle.isImaginary())
        continue; 
      MethodHandle methodHandle = typeHandle.findMethod(paramMemberInfo.name, str);
      if (methodHandle == null)
        this.ap.printMessage(Diagnostic.Kind.WARNING, "Cannot find target method for " + paramString + " in " + typeHandle, paramExecutableElement, paramAnnotationHandle.asMirror()); 
    } 
  }
  
  private void printMessage(Diagnostic.Kind paramKind, String paramString, Element paramElement, AnnotationHandle paramAnnotationHandle) {
    if (paramAnnotationHandle == null) {
      this.ap.printMessage(paramKind, paramString, paramElement);
    } else {
      this.ap.printMessage(paramKind, paramString, paramElement, paramAnnotationHandle.asMirror());
    } 
  }
  
  protected static <T extends IMapping<T>> ObfuscationData<T> stripOwnerData(ObfuscationData<T> paramObfuscationData) {
    ObfuscationData<Object> obfuscationData = new ObfuscationData();
    for (ObfuscationType obfuscationType : paramObfuscationData) {
      IMapping iMapping = (IMapping)paramObfuscationData.get(obfuscationType);
      obfuscationData.put(obfuscationType, iMapping.move(null));
    } 
    return (ObfuscationData)obfuscationData;
  }
  
  protected static <T extends IMapping<T>> ObfuscationData<T> stripDescriptors(ObfuscationData<T> paramObfuscationData) {
    ObfuscationData<Object> obfuscationData = new ObfuscationData();
    for (ObfuscationType obfuscationType : paramObfuscationData) {
      IMapping iMapping = (IMapping)paramObfuscationData.get(obfuscationType);
      obfuscationData.put(obfuscationType, iMapping.transform(null));
    } 
    return (ObfuscationData)obfuscationData;
  }
}
