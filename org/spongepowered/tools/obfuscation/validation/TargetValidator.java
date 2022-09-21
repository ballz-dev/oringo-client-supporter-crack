package org.spongepowered.tools.obfuscation.validation;

import java.util.Collection;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.tools.obfuscation.MixinValidator;
import org.spongepowered.tools.obfuscation.interfaces.IMixinAnnotationProcessor;
import org.spongepowered.tools.obfuscation.interfaces.IMixinValidator;
import org.spongepowered.tools.obfuscation.mirror.AnnotationHandle;
import org.spongepowered.tools.obfuscation.mirror.TypeHandle;
import org.spongepowered.tools.obfuscation.mirror.TypeUtils;

public class TargetValidator extends MixinValidator {
  public TargetValidator(IMixinAnnotationProcessor paramIMixinAnnotationProcessor) {
    super(paramIMixinAnnotationProcessor, IMixinValidator.ValidationPass.LATE);
  }
  
  public boolean validate(TypeElement paramTypeElement, AnnotationHandle paramAnnotationHandle, Collection<TypeHandle> paramCollection) {
    if ("true".equalsIgnoreCase(this.options.getOption("disableTargetValidator")))
      return true; 
    if (paramTypeElement.getKind() == ElementKind.INTERFACE) {
      validateInterfaceMixin(paramTypeElement, paramCollection);
    } else {
      validateClassMixin(paramTypeElement, paramCollection);
    } 
    return true;
  }
  
  private void validateInterfaceMixin(TypeElement paramTypeElement, Collection<TypeHandle> paramCollection) {
    int i = 0;
    for (Element element : paramTypeElement.getEnclosedElements()) {
      if (element.getKind() == ElementKind.METHOD) {
        boolean bool1 = AnnotationHandle.of(element, Accessor.class).exists();
        boolean bool2 = AnnotationHandle.of(element, Invoker.class).exists();
        i |= (!bool1 && !bool2) ? 1 : 0;
      } 
    } 
    if (i == 0)
      return; 
    for (TypeHandle typeHandle : paramCollection) {
      TypeElement typeElement = typeHandle.getElement();
      if (typeElement != null && typeElement.getKind() != ElementKind.INTERFACE)
        error("Targetted type '" + typeHandle + " of " + paramTypeElement + " is not an interface", paramTypeElement); 
    } 
  }
  
  private void validateClassMixin(TypeElement paramTypeElement, Collection<TypeHandle> paramCollection) {
    TypeMirror typeMirror = paramTypeElement.getSuperclass();
    for (TypeHandle typeHandle : paramCollection) {
      TypeMirror typeMirror1 = typeHandle.getType();
      if (typeMirror1 != null && !validateSuperClass(typeMirror1, typeMirror))
        error("Superclass " + typeMirror + " of " + paramTypeElement + " was not found in the hierarchy of target class " + typeMirror1, paramTypeElement); 
    } 
  }
  
  private boolean validateSuperClass(TypeMirror paramTypeMirror1, TypeMirror paramTypeMirror2) {
    if (TypeUtils.isAssignable(this.processingEnv, paramTypeMirror1, paramTypeMirror2))
      return true; 
    return validateSuperClassRecursive(paramTypeMirror1, paramTypeMirror2);
  }
  
  private boolean validateSuperClassRecursive(TypeMirror paramTypeMirror1, TypeMirror paramTypeMirror2) {
    if (!(paramTypeMirror1 instanceof DeclaredType))
      return false; 
    if (TypeUtils.isAssignable(this.processingEnv, paramTypeMirror1, paramTypeMirror2))
      return true; 
    TypeElement typeElement = (TypeElement)((DeclaredType)paramTypeMirror1).asElement();
    TypeMirror typeMirror = typeElement.getSuperclass();
    if (typeMirror.getKind() == TypeKind.NONE)
      return false; 
    if (checkMixinsFor(typeMirror, paramTypeMirror2))
      return true; 
    return validateSuperClassRecursive(typeMirror, paramTypeMirror2);
  }
  
  private boolean checkMixinsFor(TypeMirror paramTypeMirror1, TypeMirror paramTypeMirror2) {
    for (TypeMirror typeMirror : getMixinsTargeting(paramTypeMirror1)) {
      if (TypeUtils.isAssignable(this.processingEnv, typeMirror, paramTypeMirror2))
        return true; 
    } 
    return false;
  }
}
