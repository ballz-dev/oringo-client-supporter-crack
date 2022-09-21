package org.spongepowered.tools.obfuscation;

import com.google.common.collect.ImmutableList;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic;
import javax.tools.FileObject;
import javax.tools.StandardLocation;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.util.ITokenProvider;
import org.spongepowered.tools.obfuscation.interfaces.IJavadocProvider;
import org.spongepowered.tools.obfuscation.interfaces.IMixinAnnotationProcessor;
import org.spongepowered.tools.obfuscation.interfaces.IMixinValidator;
import org.spongepowered.tools.obfuscation.interfaces.IObfuscationManager;
import org.spongepowered.tools.obfuscation.interfaces.ITypeHandleProvider;
import org.spongepowered.tools.obfuscation.mirror.AnnotationHandle;
import org.spongepowered.tools.obfuscation.mirror.TypeHandle;
import org.spongepowered.tools.obfuscation.mirror.TypeHandleSimulated;
import org.spongepowered.tools.obfuscation.mirror.TypeReference;
import org.spongepowered.tools.obfuscation.struct.InjectorRemap;
import org.spongepowered.tools.obfuscation.validation.ParentValidator;
import org.spongepowered.tools.obfuscation.validation.TargetValidator;

final class AnnotatedMixins implements IMixinAnnotationProcessor, ITokenProvider, ITypeHandleProvider, IJavadocProvider {
  private static Map<ProcessingEnvironment, AnnotatedMixins> instances = new HashMap<ProcessingEnvironment, AnnotatedMixins>();
  
  private final Map<String, AnnotatedMixin> mixins = new HashMap<String, AnnotatedMixin>();
  
  private final List<AnnotatedMixin> mixinsForPass = new ArrayList<AnnotatedMixin>();
  
  private final Map<String, Integer> tokenCache = new HashMap<String, Integer>();
  
  private final TargetMap targets;
  
  private final ProcessingEnvironment processingEnv;
  
  private Properties properties;
  
  private final IObfuscationManager obf;
  
  private final List<IMixinValidator> validators;
  
  private final IMixinAnnotationProcessor.CompilerEnvironment env;
  
  private static final String MAPID_SYSTEM_PROPERTY = "mixin.target.mapid";
  
  private AnnotatedMixins(ProcessingEnvironment paramProcessingEnvironment) {
    this.env = detectEnvironment(paramProcessingEnvironment);
    this.processingEnv = paramProcessingEnvironment;
    printMessage(Diagnostic.Kind.NOTE, "SpongePowered MIXIN Annotation Processor Version=0.7.11");
    this.targets = initTargetMap();
    this.obf = new ObfuscationManager(this);
    this.obf.init();
    this.validators = (List<IMixinValidator>)ImmutableList.of(new ParentValidator(this), new TargetValidator(this));
    initTokenCache(getOption("tokens"));
  }
  
  protected TargetMap initTargetMap() {
    TargetMap targetMap = TargetMap.create(System.getProperty("mixin.target.mapid"));
    System.setProperty("mixin.target.mapid", targetMap.getSessionId());
    String str = getOption("dependencyTargetsFile");
    if (str != null)
      try {
        targetMap.readImports(new File(str));
      } catch (IOException iOException) {
        printMessage(Diagnostic.Kind.WARNING, "Could not read from specified imports file: " + str);
      }  
    return targetMap;
  }
  
  private void initTokenCache(String paramString) {
    if (paramString != null) {
      Pattern pattern = Pattern.compile("^([A-Z0-9\\-_\\.]+)=([0-9]+)$");
      String[] arrayOfString = paramString.replaceAll("\\s", "").toUpperCase().split("[;,]");
      for (String str : arrayOfString) {
        Matcher matcher = pattern.matcher(str);
        if (matcher.matches())
          this.tokenCache.put(matcher.group(1), Integer.valueOf(Integer.parseInt(matcher.group(2)))); 
      } 
    } 
  }
  
  public ITypeHandleProvider getTypeProvider() {
    return this;
  }
  
  public ITokenProvider getTokenProvider() {
    return this;
  }
  
  public IObfuscationManager getObfuscationManager() {
    return this.obf;
  }
  
  public IJavadocProvider getJavadocProvider() {
    return this;
  }
  
  public ProcessingEnvironment getProcessingEnvironment() {
    return this.processingEnv;
  }
  
  public IMixinAnnotationProcessor.CompilerEnvironment getCompilerEnvironment() {
    return this.env;
  }
  
  public Integer getToken(String paramString) {
    if (this.tokenCache.containsKey(paramString))
      return this.tokenCache.get(paramString); 
    String str = getOption(paramString);
    Integer integer = null;
    try {
      integer = Integer.valueOf(Integer.parseInt(str));
    } catch (Exception exception) {}
    this.tokenCache.put(paramString, integer);
    return integer;
  }
  
  public String getOption(String paramString) {
    if (paramString == null)
      return null; 
    String str = this.processingEnv.getOptions().get(paramString);
    if (str != null)
      return str; 
    return getProperties().getProperty(paramString);
  }
  
  public Properties getProperties() {
    if (this.properties == null) {
      this.properties = new Properties();
      try {
        Filer filer = this.processingEnv.getFiler();
        FileObject fileObject = filer.getResource(StandardLocation.SOURCE_PATH, "", "mixin.properties");
        if (fileObject != null) {
          InputStream inputStream = fileObject.openInputStream();
          this.properties.load(inputStream);
          inputStream.close();
        } 
      } catch (Exception exception) {}
    } 
    return this.properties;
  }
  
  private IMixinAnnotationProcessor.CompilerEnvironment detectEnvironment(ProcessingEnvironment paramProcessingEnvironment) {
    if (paramProcessingEnvironment.getClass().getName().contains("jdt"))
      return IMixinAnnotationProcessor.CompilerEnvironment.JDT; 
    return IMixinAnnotationProcessor.CompilerEnvironment.JAVAC;
  }
  
  public void writeMappings() {
    this.obf.writeMappings();
  }
  
  public void writeReferences() {
    this.obf.writeReferences();
  }
  
  public void clear() {
    this.mixins.clear();
  }
  
  public void registerMixin(TypeElement paramTypeElement) {
    String str = paramTypeElement.getQualifiedName().toString();
    if (!this.mixins.containsKey(str)) {
      AnnotatedMixin annotatedMixin = new AnnotatedMixin(this, paramTypeElement);
      this.targets.registerTargets(annotatedMixin);
      annotatedMixin.runValidators(IMixinValidator.ValidationPass.EARLY, this.validators);
      this.mixins.put(str, annotatedMixin);
      this.mixinsForPass.add(annotatedMixin);
    } 
  }
  
  public AnnotatedMixin getMixin(TypeElement paramTypeElement) {
    return getMixin(paramTypeElement.getQualifiedName().toString());
  }
  
  public AnnotatedMixin getMixin(String paramString) {
    return this.mixins.get(paramString);
  }
  
  public Collection<TypeMirror> getMixinsTargeting(TypeMirror paramTypeMirror) {
    return getMixinsTargeting((TypeElement)((DeclaredType)paramTypeMirror).asElement());
  }
  
  public Collection<TypeMirror> getMixinsTargeting(TypeElement paramTypeElement) {
    ArrayList<TypeMirror> arrayList = new ArrayList();
    for (TypeReference typeReference : this.targets.getMixinsTargeting(paramTypeElement)) {
      TypeHandle typeHandle = typeReference.getHandle(this.processingEnv);
      if (typeHandle != null)
        arrayList.add(typeHandle.getType()); 
    } 
    return arrayList;
  }
  
  public void registerAccessor(TypeElement paramTypeElement, ExecutableElement paramExecutableElement) {
    AnnotatedMixin annotatedMixin = getMixin(paramTypeElement);
    if (annotatedMixin == null) {
      printMessage(Diagnostic.Kind.ERROR, "Found @Accessor annotation on a non-mixin method", paramExecutableElement);
      return;
    } 
    AnnotationHandle annotationHandle = AnnotationHandle.of(paramExecutableElement, Accessor.class);
    annotatedMixin.registerAccessor(paramExecutableElement, annotationHandle, shouldRemap(annotatedMixin, annotationHandle));
  }
  
  public void registerInvoker(TypeElement paramTypeElement, ExecutableElement paramExecutableElement) {
    AnnotatedMixin annotatedMixin = getMixin(paramTypeElement);
    if (annotatedMixin == null) {
      printMessage(Diagnostic.Kind.ERROR, "Found @Accessor annotation on a non-mixin method", paramExecutableElement);
      return;
    } 
    AnnotationHandle annotationHandle = AnnotationHandle.of(paramExecutableElement, Invoker.class);
    annotatedMixin.registerInvoker(paramExecutableElement, annotationHandle, shouldRemap(annotatedMixin, annotationHandle));
  }
  
  public void registerOverwrite(TypeElement paramTypeElement, ExecutableElement paramExecutableElement) {
    AnnotatedMixin annotatedMixin = getMixin(paramTypeElement);
    if (annotatedMixin == null) {
      printMessage(Diagnostic.Kind.ERROR, "Found @Overwrite annotation on a non-mixin method", paramExecutableElement);
      return;
    } 
    AnnotationHandle annotationHandle = AnnotationHandle.of(paramExecutableElement, Overwrite.class);
    annotatedMixin.registerOverwrite(paramExecutableElement, annotationHandle, shouldRemap(annotatedMixin, annotationHandle));
  }
  
  public void registerShadow(TypeElement paramTypeElement, VariableElement paramVariableElement, AnnotationHandle paramAnnotationHandle) {
    AnnotatedMixin annotatedMixin = getMixin(paramTypeElement);
    if (annotatedMixin == null) {
      printMessage(Diagnostic.Kind.ERROR, "Found @Shadow annotation on a non-mixin field", paramVariableElement);
      return;
    } 
    annotatedMixin.registerShadow(paramVariableElement, paramAnnotationHandle, shouldRemap(annotatedMixin, paramAnnotationHandle));
  }
  
  public void registerShadow(TypeElement paramTypeElement, ExecutableElement paramExecutableElement, AnnotationHandle paramAnnotationHandle) {
    AnnotatedMixin annotatedMixin = getMixin(paramTypeElement);
    if (annotatedMixin == null) {
      printMessage(Diagnostic.Kind.ERROR, "Found @Shadow annotation on a non-mixin method", paramExecutableElement);
      return;
    } 
    annotatedMixin.registerShadow(paramExecutableElement, paramAnnotationHandle, shouldRemap(annotatedMixin, paramAnnotationHandle));
  }
  
  public void registerInjector(TypeElement paramTypeElement, ExecutableElement paramExecutableElement, AnnotationHandle paramAnnotationHandle) {
    AnnotatedMixin annotatedMixin = getMixin(paramTypeElement);
    if (annotatedMixin == null) {
      printMessage(Diagnostic.Kind.ERROR, "Found " + paramAnnotationHandle + " annotation on a non-mixin method", paramExecutableElement);
      return;
    } 
    InjectorRemap injectorRemap = new InjectorRemap(shouldRemap(annotatedMixin, paramAnnotationHandle));
    annotatedMixin.registerInjector(paramExecutableElement, paramAnnotationHandle, injectorRemap);
    injectorRemap.dispatchPendingMessages((Messager)this);
  }
  
  public void registerSoftImplements(TypeElement paramTypeElement, AnnotationHandle paramAnnotationHandle) {
    AnnotatedMixin annotatedMixin = getMixin(paramTypeElement);
    if (annotatedMixin == null) {
      printMessage(Diagnostic.Kind.ERROR, "Found @Implements annotation on a non-mixin class");
      return;
    } 
    annotatedMixin.registerSoftImplements(paramAnnotationHandle);
  }
  
  public void onPassStarted() {
    this.mixinsForPass.clear();
  }
  
  public void onPassCompleted(RoundEnvironment paramRoundEnvironment) {
    if (!"true".equalsIgnoreCase(getOption("disableTargetExport")))
      this.targets.write(true); 
    for (AnnotatedMixin annotatedMixin : paramRoundEnvironment.processingOver() ? (Object<?>)this.mixins.values() : (Object<?>)this.mixinsForPass)
      annotatedMixin.runValidators(paramRoundEnvironment.processingOver() ? IMixinValidator.ValidationPass.FINAL : IMixinValidator.ValidationPass.LATE, this.validators); 
  }
  
  private boolean shouldRemap(AnnotatedMixin paramAnnotatedMixin, AnnotationHandle paramAnnotationHandle) {
    return paramAnnotationHandle.getBoolean("remap", paramAnnotatedMixin.remap());
  }
  
  public void printMessage(Diagnostic.Kind paramKind, CharSequence paramCharSequence) {
    if (this.env == IMixinAnnotationProcessor.CompilerEnvironment.JAVAC || paramKind != Diagnostic.Kind.NOTE)
      this.processingEnv.getMessager().printMessage(paramKind, paramCharSequence); 
  }
  
  public void printMessage(Diagnostic.Kind paramKind, CharSequence paramCharSequence, Element paramElement) {
    this.processingEnv.getMessager().printMessage(paramKind, paramCharSequence, paramElement);
  }
  
  public void printMessage(Diagnostic.Kind paramKind, CharSequence paramCharSequence, Element paramElement, AnnotationMirror paramAnnotationMirror) {
    this.processingEnv.getMessager().printMessage(paramKind, paramCharSequence, paramElement, paramAnnotationMirror);
  }
  
  public void printMessage(Diagnostic.Kind paramKind, CharSequence paramCharSequence, Element paramElement, AnnotationMirror paramAnnotationMirror, AnnotationValue paramAnnotationValue) {
    this.processingEnv.getMessager().printMessage(paramKind, paramCharSequence, paramElement, paramAnnotationMirror, paramAnnotationValue);
  }
  
  public TypeHandle getTypeHandle(String paramString) {
    paramString = paramString.replace('/', '.');
    Elements elements = this.processingEnv.getElementUtils();
    TypeElement typeElement = elements.getTypeElement(paramString);
    if (typeElement != null)
      try {
        return new TypeHandle(typeElement);
      } catch (NullPointerException nullPointerException) {} 
    int i = paramString.lastIndexOf('.');
    if (i > -1) {
      String str = paramString.substring(0, i);
      PackageElement packageElement = elements.getPackageElement(str);
      if (packageElement != null)
        return new TypeHandle(packageElement, paramString); 
    } 
    return null;
  }
  
  public TypeHandle getSimulatedHandle(String paramString, TypeMirror paramTypeMirror) {
    paramString = paramString.replace('/', '.');
    int i = paramString.lastIndexOf('.');
    if (i > -1) {
      String str = paramString.substring(0, i);
      PackageElement packageElement = this.processingEnv.getElementUtils().getPackageElement(str);
      if (packageElement != null)
        return (TypeHandle)new TypeHandleSimulated(packageElement, paramString, paramTypeMirror); 
    } 
    return (TypeHandle)new TypeHandleSimulated(paramString, paramTypeMirror);
  }
  
  public String getJavadoc(Element paramElement) {
    Elements elements = this.processingEnv.getElementUtils();
    return elements.getDocComment(paramElement);
  }
  
  public static AnnotatedMixins getMixinsForEnvironment(ProcessingEnvironment paramProcessingEnvironment) {
    AnnotatedMixins annotatedMixins = instances.get(paramProcessingEnvironment);
    if (annotatedMixins == null) {
      annotatedMixins = new AnnotatedMixins(paramProcessingEnvironment);
      instances.put(paramProcessingEnvironment, annotatedMixins);
    } 
    return annotatedMixins;
  }
}
