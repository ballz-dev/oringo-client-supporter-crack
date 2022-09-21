package org.spongepowered.tools.obfuscation;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.annotation.processing.Messager;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.VariableElement;
import javax.tools.Diagnostic;
import org.spongepowered.asm.mixin.injection.Coerce;
import org.spongepowered.asm.mixin.injection.struct.InjectionPointData;
import org.spongepowered.asm.mixin.injection.struct.InvalidMemberDescriptorException;
import org.spongepowered.asm.mixin.injection.struct.MemberInfo;
import org.spongepowered.asm.obfuscation.mapping.IMapping;
import org.spongepowered.asm.obfuscation.mapping.common.MappingMethod;
import org.spongepowered.tools.obfuscation.interfaces.IMixinAnnotationProcessor;
import org.spongepowered.tools.obfuscation.interfaces.IReferenceManager;
import org.spongepowered.tools.obfuscation.mirror.AnnotationHandle;
import org.spongepowered.tools.obfuscation.mirror.TypeHandle;
import org.spongepowered.tools.obfuscation.struct.InjectorRemap;

class AnnotatedMixinElementHandlerInjector extends AnnotatedMixinElementHandler {
  static class AnnotatedElementInjector extends AnnotatedMixinElementHandler.AnnotatedElement<ExecutableElement> {
    private final InjectorRemap state;
    
    public AnnotatedElementInjector(ExecutableElement param1ExecutableElement, AnnotationHandle param1AnnotationHandle, InjectorRemap param1InjectorRemap) {
      super(param1ExecutableElement, param1AnnotationHandle);
      this.state = param1InjectorRemap;
    }
    
    public boolean shouldRemap() {
      return this.state.shouldRemap();
    }
    
    public boolean hasCoerceArgument() {
      if (!this.annotation.toString().equals("@Inject"))
        return false; 
      Iterator<? extends VariableElement> iterator = this.element.getParameters().iterator();
      if (iterator.hasNext()) {
        VariableElement variableElement = iterator.next();
        return AnnotationHandle.of(variableElement, Coerce.class).exists();
      } 
      return false;
    }
    
    public void addMessage(Diagnostic.Kind param1Kind, CharSequence param1CharSequence, Element param1Element, AnnotationHandle param1AnnotationHandle) {
      this.state.addMessage(param1Kind, param1CharSequence, param1Element, param1AnnotationHandle);
    }
    
    public String toString() {
      return getAnnotation().toString();
    }
  }
  
  static class AnnotatedElementInjectionPoint extends AnnotatedMixinElementHandler.AnnotatedElement<ExecutableElement> {
    private final AnnotationHandle at;
    
    private Map<String, String> args;
    
    private final InjectorRemap state;
    
    public AnnotatedElementInjectionPoint(ExecutableElement param1ExecutableElement, AnnotationHandle param1AnnotationHandle1, AnnotationHandle param1AnnotationHandle2, InjectorRemap param1InjectorRemap) {
      super(param1ExecutableElement, param1AnnotationHandle1);
      this.at = param1AnnotationHandle2;
      this.state = param1InjectorRemap;
    }
    
    public boolean shouldRemap() {
      return this.at.getBoolean("remap", this.state.shouldRemap());
    }
    
    public AnnotationHandle getAt() {
      return this.at;
    }
    
    public String getAtArg(String param1String) {
      if (this.args == null) {
        this.args = new HashMap<String, String>();
        for (String str : this.at.getList("args")) {
          if (str == null)
            continue; 
          int i = str.indexOf('=');
          if (i > -1) {
            this.args.put(str.substring(0, i), str.substring(i + 1));
            continue;
          } 
          this.args.put(str, "");
        } 
      } 
      return this.args.get(param1String);
    }
    
    public void notifyRemapped() {
      this.state.notifyRemapped();
    }
  }
  
  AnnotatedMixinElementHandlerInjector(IMixinAnnotationProcessor paramIMixinAnnotationProcessor, AnnotatedMixin paramAnnotatedMixin) {
    super(paramIMixinAnnotationProcessor, paramAnnotatedMixin);
  }
  
  public void registerInjector(AnnotatedElementInjector paramAnnotatedElementInjector) {
    if (this.mixin.isInterface())
      this.ap.printMessage(Diagnostic.Kind.ERROR, "Injector in interface is unsupported", paramAnnotatedElementInjector.getElement()); 
    for (String str : paramAnnotatedElementInjector.getAnnotation().getList("method")) {
      MemberInfo memberInfo = MemberInfo.parse(str);
      if (memberInfo.name == null)
        continue; 
      try {
        memberInfo.validate();
      } catch (InvalidMemberDescriptorException invalidMemberDescriptorException) {
        paramAnnotatedElementInjector.printMessage((Messager)this.ap, Diagnostic.Kind.ERROR, invalidMemberDescriptorException.getMessage());
      } 
      if (memberInfo.desc != null)
        validateReferencedTarget(paramAnnotatedElementInjector.getElement(), paramAnnotatedElementInjector.getAnnotation(), memberInfo, paramAnnotatedElementInjector.toString()); 
      if (!paramAnnotatedElementInjector.shouldRemap())
        continue; 
      for (TypeHandle typeHandle : this.mixin.getTargets()) {
        if (!registerInjector(paramAnnotatedElementInjector, str, memberInfo, typeHandle))
          break; 
      } 
    } 
  }
  
  private boolean registerInjector(AnnotatedElementInjector paramAnnotatedElementInjector, String paramString, MemberInfo paramMemberInfo, TypeHandle paramTypeHandle) {
    String str1 = paramTypeHandle.findDescriptor(paramMemberInfo);
    if (str1 == null) {
      Diagnostic.Kind kind = this.mixin.isMultiTarget() ? Diagnostic.Kind.ERROR : Diagnostic.Kind.WARNING;
      if (paramTypeHandle.isSimulated()) {
        paramAnnotatedElementInjector.printMessage((Messager)this.ap, Diagnostic.Kind.NOTE, paramAnnotatedElementInjector + " target '" + paramString + "' in @Pseudo mixin will not be obfuscated");
      } else if (paramTypeHandle.isImaginary()) {
        paramAnnotatedElementInjector.printMessage((Messager)this.ap, kind, paramAnnotatedElementInjector + " target requires method signature because enclosing type information for " + paramTypeHandle + " is unavailable");
      } else if (!paramMemberInfo.isInitialiser()) {
        paramAnnotatedElementInjector.printMessage((Messager)this.ap, kind, "Unable to determine signature for " + paramAnnotatedElementInjector + " target method");
      } 
      return true;
    } 
    String str2 = paramAnnotatedElementInjector + " target " + paramMemberInfo.name;
    MappingMethod mappingMethod = paramTypeHandle.getMappingMethod(paramMemberInfo.name, str1);
    ObfuscationData<IMapping> obfuscationData = this.obf.getDataProvider().getObfMethod(mappingMethod);
    if (obfuscationData.isEmpty())
      if (paramTypeHandle.isSimulated()) {
        obfuscationData = this.obf.getDataProvider().getRemappedMethod(mappingMethod);
      } else {
        if (paramMemberInfo.isClassInitialiser())
          return true; 
        Diagnostic.Kind kind = paramMemberInfo.isConstructor() ? Diagnostic.Kind.WARNING : Diagnostic.Kind.ERROR;
        paramAnnotatedElementInjector.addMessage(kind, "No obfuscation mapping for " + str2, paramAnnotatedElementInjector.getElement(), paramAnnotatedElementInjector.getAnnotation());
        return false;
      }  
    IReferenceManager iReferenceManager = this.obf.getReferenceManager();
    try {
      if ((paramMemberInfo.owner == null && this.mixin.isMultiTarget()) || paramTypeHandle.isSimulated())
        obfuscationData = AnnotatedMixinElementHandler.stripOwnerData(obfuscationData); 
      iReferenceManager.addMethodMapping(this.classRef, paramString, obfuscationData);
    } catch (ReferenceConflictException referenceConflictException) {
      String str = this.mixin.isMultiTarget() ? "Multi-target" : "Target";
      if (paramAnnotatedElementInjector.hasCoerceArgument() && paramMemberInfo.owner == null && paramMemberInfo.desc == null) {
        MemberInfo memberInfo1 = MemberInfo.parse(referenceConflictException.getOld());
        MemberInfo memberInfo2 = MemberInfo.parse(referenceConflictException.getNew());
        if (memberInfo1.name.equals(memberInfo2.name)) {
          obfuscationData = AnnotatedMixinElementHandler.stripDescriptors(obfuscationData);
          iReferenceManager.setAllowConflicts(true);
          iReferenceManager.addMethodMapping(this.classRef, paramString, obfuscationData);
          iReferenceManager.setAllowConflicts(false);
          paramAnnotatedElementInjector.printMessage((Messager)this.ap, Diagnostic.Kind.WARNING, "Coerced " + str + " reference has conflicting descriptors for " + str2 + ": Storing bare references " + obfuscationData
              .values() + " in refMap");
          return true;
        } 
      } 
      paramAnnotatedElementInjector.printMessage((Messager)this.ap, Diagnostic.Kind.ERROR, str + " reference conflict for " + str2 + ": " + paramString + " -> " + referenceConflictException
          .getNew() + " previously defined as " + referenceConflictException.getOld());
    } 
    return true;
  }
  
  public void registerInjectionPoint(AnnotatedElementInjectionPoint paramAnnotatedElementInjectionPoint, String paramString) {
    if (this.mixin.isInterface())
      this.ap.printMessage(Diagnostic.Kind.ERROR, "Injector in interface is unsupported", paramAnnotatedElementInjectionPoint.getElement()); 
    if (!paramAnnotatedElementInjectionPoint.shouldRemap())
      return; 
    String str1 = InjectionPointData.parseType((String)paramAnnotatedElementInjectionPoint.getAt().getValue("value"));
    String str2 = (String)paramAnnotatedElementInjectionPoint.getAt().getValue("target");
    if ("NEW".equals(str1)) {
      remapNewTarget(String.format(paramString, new Object[] { str1 + ".<target>" }), str2, paramAnnotatedElementInjectionPoint);
      remapNewTarget(String.format(paramString, new Object[] { str1 + ".args[class]" }), paramAnnotatedElementInjectionPoint.getAtArg("class"), paramAnnotatedElementInjectionPoint);
    } else {
      remapReference(String.format(paramString, new Object[] { str1 + ".<target>" }), str2, paramAnnotatedElementInjectionPoint);
    } 
  }
  
  protected final void remapNewTarget(String paramString1, String paramString2, AnnotatedElementInjectionPoint paramAnnotatedElementInjectionPoint) {
    if (paramString2 == null)
      return; 
    MemberInfo memberInfo = MemberInfo.parse(paramString2);
    String str = memberInfo.toCtorType();
    if (str != null) {
      String str1 = memberInfo.toCtorDesc();
      MappingMethod mappingMethod = new MappingMethod(str, ".", (str1 != null) ? str1 : "()V");
      ObfuscationData<MappingMethod> obfuscationData = this.obf.getDataProvider().getRemappedMethod(mappingMethod);
      if (obfuscationData.isEmpty()) {
        this.ap.printMessage(Diagnostic.Kind.WARNING, "Cannot find class mapping for " + paramString1 + " '" + str + "'", paramAnnotatedElementInjectionPoint.getElement(), paramAnnotatedElementInjectionPoint
            .getAnnotation().asMirror());
        return;
      } 
      ObfuscationData<String> obfuscationData1 = new ObfuscationData();
      for (ObfuscationType obfuscationType : obfuscationData) {
        MappingMethod mappingMethod1 = obfuscationData.get(obfuscationType);
        if (str1 == null) {
          obfuscationData1.put(obfuscationType, mappingMethod1.getOwner());
          continue;
        } 
        obfuscationData1.put(obfuscationType, mappingMethod1.getDesc().replace(")V", ")L" + mappingMethod1.getOwner() + ";"));
      } 
      this.obf.getReferenceManager().addClassMapping(this.classRef, paramString2, obfuscationData1);
    } 
    paramAnnotatedElementInjectionPoint.notifyRemapped();
  }
  
  protected final void remapReference(String paramString1, String paramString2, AnnotatedElementInjectionPoint paramAnnotatedElementInjectionPoint) {
    if (paramString2 == null)
      return; 
    AnnotationMirror annotationMirror = ((this.ap.getCompilerEnvironment() == IMixinAnnotationProcessor.CompilerEnvironment.JDT) ? paramAnnotatedElementInjectionPoint.getAt() : paramAnnotatedElementInjectionPoint.getAnnotation()).asMirror();
    MemberInfo memberInfo = MemberInfo.parse(paramString2);
    if (!memberInfo.isFullyQualified()) {
      String str = (memberInfo.owner == null) ? ((memberInfo.desc == null) ? "owner and signature" : "owner") : "signature";
      this.ap.printMessage(Diagnostic.Kind.ERROR, paramString1 + " is not fully qualified, missing " + str, paramAnnotatedElementInjectionPoint.getElement(), annotationMirror);
      return;
    } 
    try {
      memberInfo.validate();
    } catch (InvalidMemberDescriptorException invalidMemberDescriptorException) {
      this.ap.printMessage(Diagnostic.Kind.ERROR, invalidMemberDescriptorException.getMessage(), paramAnnotatedElementInjectionPoint.getElement(), annotationMirror);
    } 
    try {
      if (memberInfo.isField()) {
        ObfuscationData obfuscationData = this.obf.getDataProvider().getObfFieldRecursive(memberInfo);
        if (obfuscationData.isEmpty()) {
          this.ap.printMessage(Diagnostic.Kind.WARNING, "Cannot find field mapping for " + paramString1 + " '" + paramString2 + "'", paramAnnotatedElementInjectionPoint.getElement(), annotationMirror);
          return;
        } 
        this.obf.getReferenceManager().addFieldMapping(this.classRef, paramString2, memberInfo, obfuscationData);
      } else {
        ObfuscationData obfuscationData = this.obf.getDataProvider().getObfMethodRecursive(memberInfo);
        if (obfuscationData.isEmpty() && (
          memberInfo.owner == null || !memberInfo.owner.startsWith("java/lang/"))) {
          this.ap.printMessage(Diagnostic.Kind.WARNING, "Cannot find method mapping for " + paramString1 + " '" + paramString2 + "'", paramAnnotatedElementInjectionPoint.getElement(), annotationMirror);
          return;
        } 
        this.obf.getReferenceManager().addMethodMapping(this.classRef, paramString2, memberInfo, obfuscationData);
      } 
    } catch (ReferenceConflictException referenceConflictException) {
      this.ap.printMessage(Diagnostic.Kind.ERROR, "Unexpected reference conflict for " + paramString1 + ": " + paramString2 + " -> " + referenceConflictException
          .getNew() + " previously defined as " + referenceConflictException.getOld(), paramAnnotatedElementInjectionPoint.getElement(), annotationMirror);
      return;
    } 
    paramAnnotatedElementInjectionPoint.notifyRemapped();
  }
}
