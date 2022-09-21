package org.spongepowered.tools.obfuscation;

import java.util.Collection;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import javax.tools.Diagnostic;
import org.spongepowered.tools.obfuscation.interfaces.IMixinAnnotationProcessor;
import org.spongepowered.tools.obfuscation.interfaces.IMixinValidator;
import org.spongepowered.tools.obfuscation.interfaces.IOptionProvider;
import org.spongepowered.tools.obfuscation.mirror.AnnotationHandle;
import org.spongepowered.tools.obfuscation.mirror.TypeHandle;

public abstract class MixinValidator implements IMixinValidator {
  protected final IOptionProvider options;
  
  protected final ProcessingEnvironment processingEnv;
  
  protected final Messager messager;
  
  protected final IMixinValidator.ValidationPass pass;
  
  public MixinValidator(IMixinAnnotationProcessor paramIMixinAnnotationProcessor, IMixinValidator.ValidationPass paramValidationPass) {
    this.processingEnv = paramIMixinAnnotationProcessor.getProcessingEnvironment();
    this.messager = (Messager)paramIMixinAnnotationProcessor;
    this.options = (IOptionProvider)paramIMixinAnnotationProcessor;
    this.pass = paramValidationPass;
  }
  
  public final boolean validate(IMixinValidator.ValidationPass paramValidationPass, TypeElement paramTypeElement, AnnotationHandle paramAnnotationHandle, Collection<TypeHandle> paramCollection) {
    if (paramValidationPass != this.pass)
      return true; 
    return validate(paramTypeElement, paramAnnotationHandle, paramCollection);
  }
  
  protected final void note(String paramString, Element paramElement) {
    this.messager.printMessage(Diagnostic.Kind.NOTE, paramString, paramElement);
  }
  
  protected final void warning(String paramString, Element paramElement) {
    this.messager.printMessage(Diagnostic.Kind.WARNING, paramString, paramElement);
  }
  
  protected final void error(String paramString, Element paramElement) {
    this.messager.printMessage(Diagnostic.Kind.ERROR, paramString, paramElement);
  }
  
  protected final Collection<TypeMirror> getMixinsTargeting(TypeMirror paramTypeMirror) {
    return AnnotatedMixins.getMixinsForEnvironment(this.processingEnv).getMixinsTargeting(paramTypeMirror);
  }
  
  protected abstract boolean validate(TypeElement paramTypeElement, AnnotationHandle paramAnnotationHandle, Collection<TypeHandle> paramCollection);
}
