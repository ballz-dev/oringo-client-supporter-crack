package org.spongepowered.asm.mixin.transformer;

import com.google.common.base.Strings;
import java.lang.annotation.Annotation;
import java.util.Iterator;
import java.util.ListIterator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.lib.Type;
import org.spongepowered.asm.lib.tree.AbstractInsnNode;
import org.spongepowered.asm.lib.tree.AnnotationNode;
import org.spongepowered.asm.lib.tree.FieldInsnNode;
import org.spongepowered.asm.lib.tree.FieldNode;
import org.spongepowered.asm.lib.tree.MethodInsnNode;
import org.spongepowered.asm.lib.tree.MethodNode;
import org.spongepowered.asm.mixin.Dynamic;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.gen.throwables.InvalidAccessorException;
import org.spongepowered.asm.mixin.transformer.meta.MixinRenamed;
import org.spongepowered.asm.mixin.transformer.throwables.InvalidMixinException;
import org.spongepowered.asm.util.Annotations;
import org.spongepowered.asm.util.Bytecode;
import org.spongepowered.asm.util.perf.Profiler;
import org.spongepowered.asm.util.throwables.SyntheticBridgeException;

class MixinPreProcessorStandard {
  private boolean attached;
  
  private boolean prepared;
  
  protected final MixinInfo.MixinClassNode classNode;
  
  enum SpecialMethod {
    SHADOW,
    MERGE(true),
    OVERWRITE(true, Overwrite.class),
    INVOKER(true, Overwrite.class),
    ACCESSOR(true, Overwrite.class);
    
    final String description;
    
    final boolean isOverwrite;
    
    final Class<? extends Annotation> annotation;
    
    static {
      INVOKER = new SpecialMethod("INVOKER", 4, false, (Class)Invoker.class);
      $VALUES = new SpecialMethod[] { MERGE, OVERWRITE, SHADOW, ACCESSOR, INVOKER };
    }
    
    SpecialMethod(boolean param1Boolean, Class<? extends Annotation> param1Class) {
      this.isOverwrite = param1Boolean;
      this.annotation = param1Class;
      this.description = "@" + Bytecode.getSimpleName(param1Class);
    }
    
    SpecialMethod(boolean param1Boolean) {
      this.isOverwrite = param1Boolean;
      this.annotation = null;
      this.description = "overwrite";
    }
    
    public String toString() {
      return this.description;
    }
  }
  
  private static final Logger logger = LogManager.getLogger("mixin");
  
  private final boolean verboseLogging;
  
  private final boolean strictUnique;
  
  protected final MixinEnvironment env;
  
  protected final Profiler profiler = MixinEnvironment.getProfiler();
  
  protected final MixinInfo mixin;
  
  MixinPreProcessorStandard(MixinInfo paramMixinInfo, MixinInfo.MixinClassNode paramMixinClassNode) {
    this.mixin = paramMixinInfo;
    this.classNode = paramMixinClassNode;
    this.env = paramMixinInfo.getParent().getEnvironment();
    this.verboseLogging = this.env.getOption(MixinEnvironment.Option.DEBUG_VERBOSE);
    this.strictUnique = this.env.getOption(MixinEnvironment.Option.DEBUG_UNIQUE);
  }
  
  final MixinPreProcessorStandard prepare() {
    if (this.prepared)
      return this; 
    this.prepared = true;
    Profiler.Section section = this.profiler.begin("prepare");
    for (MixinInfo.MixinMethodNode mixinMethodNode : this.classNode.mixinMethods) {
      ClassInfo.Method method = this.mixin.getClassInfo().findMethod(mixinMethodNode);
      prepareMethod(mixinMethodNode, method);
    } 
    for (FieldNode fieldNode : this.classNode.fields)
      prepareField(fieldNode); 
    section.end();
    return this;
  }
  
  protected void prepareMethod(MixinInfo.MixinMethodNode paramMixinMethodNode, ClassInfo.Method paramMethod) {
    prepareShadow(paramMixinMethodNode, paramMethod);
    prepareSoftImplements(paramMixinMethodNode, paramMethod);
  }
  
  protected void prepareShadow(MixinInfo.MixinMethodNode paramMixinMethodNode, ClassInfo.Method paramMethod) {
    AnnotationNode annotationNode = Annotations.getVisible(paramMixinMethodNode, Shadow.class);
    if (annotationNode == null)
      return; 
    String str = (String)Annotations.getValue(annotationNode, "prefix", Shadow.class);
    if (paramMixinMethodNode.name.startsWith(str)) {
      Annotations.setVisible(paramMixinMethodNode, MixinRenamed.class, new Object[] { "originalName", paramMixinMethodNode.name });
      String str1 = paramMixinMethodNode.name.substring(str.length());
      paramMixinMethodNode.name = paramMethod.renameTo(str1);
    } 
  }
  
  protected void prepareSoftImplements(MixinInfo.MixinMethodNode paramMixinMethodNode, ClassInfo.Method paramMethod) {
    for (InterfaceInfo interfaceInfo : this.mixin.getSoftImplements()) {
      if (interfaceInfo.renameMethod(paramMixinMethodNode))
        paramMethod.renameTo(paramMixinMethodNode.name); 
    } 
  }
  
  protected void prepareField(FieldNode paramFieldNode) {}
  
  final MixinPreProcessorStandard conform(TargetClassContext paramTargetClassContext) {
    return conform(paramTargetClassContext.getClassInfo());
  }
  
  final MixinPreProcessorStandard conform(ClassInfo paramClassInfo) {
    Profiler.Section section = this.profiler.begin("conform");
    for (MixinInfo.MixinMethodNode mixinMethodNode : this.classNode.mixinMethods) {
      if (mixinMethodNode.isInjector()) {
        ClassInfo.Method method = this.mixin.getClassInfo().findMethod(mixinMethodNode, 10);
        conformInjector(paramClassInfo, mixinMethodNode, method);
      } 
    } 
    section.end();
    return this;
  }
  
  private void conformInjector(ClassInfo paramClassInfo, MixinInfo.MixinMethodNode paramMixinMethodNode, ClassInfo.Method paramMethod) {
    MethodMapper methodMapper = paramClassInfo.getMethodMapper();
    methodMapper.remapHandlerMethod(this.mixin, paramMixinMethodNode, paramMethod);
  }
  
  MixinTargetContext createContextFor(TargetClassContext paramTargetClassContext) {
    MixinTargetContext mixinTargetContext = new MixinTargetContext(this.mixin, this.classNode, paramTargetClassContext);
    conform(paramTargetClassContext);
    attach(mixinTargetContext);
    return mixinTargetContext;
  }
  
  final MixinPreProcessorStandard attach(MixinTargetContext paramMixinTargetContext) {
    if (this.attached)
      throw new IllegalStateException("Preprocessor was already attached"); 
    this.attached = true;
    Profiler.Section section1 = this.profiler.begin("attach");
    Profiler.Section section2 = this.profiler.begin("methods");
    attachMethods(paramMixinTargetContext);
    section2 = section2.next("fields");
    attachFields(paramMixinTargetContext);
    section2 = section2.next("transform");
    transform(paramMixinTargetContext);
    section2.end();
    section1.end();
    return this;
  }
  
  protected void attachMethods(MixinTargetContext paramMixinTargetContext) {
    for (Iterator<MixinInfo.MixinMethodNode> iterator = this.classNode.mixinMethods.iterator(); iterator.hasNext(); ) {
      MixinInfo.MixinMethodNode mixinMethodNode = iterator.next();
      if (!validateMethod(paramMixinTargetContext, mixinMethodNode)) {
        iterator.remove();
        continue;
      } 
      if (attachInjectorMethod(paramMixinTargetContext, mixinMethodNode)) {
        paramMixinTargetContext.addMixinMethod(mixinMethodNode);
        continue;
      } 
      if (attachAccessorMethod(paramMixinTargetContext, mixinMethodNode)) {
        iterator.remove();
        continue;
      } 
      if (attachShadowMethod(paramMixinTargetContext, mixinMethodNode)) {
        paramMixinTargetContext.addShadowMethod(mixinMethodNode);
        iterator.remove();
        continue;
      } 
      if (attachOverwriteMethod(paramMixinTargetContext, mixinMethodNode)) {
        paramMixinTargetContext.addMixinMethod(mixinMethodNode);
        continue;
      } 
      if (attachUniqueMethod(paramMixinTargetContext, mixinMethodNode)) {
        iterator.remove();
        continue;
      } 
      attachMethod(paramMixinTargetContext, mixinMethodNode);
      paramMixinTargetContext.addMixinMethod(mixinMethodNode);
    } 
  }
  
  protected boolean validateMethod(MixinTargetContext paramMixinTargetContext, MixinInfo.MixinMethodNode paramMixinMethodNode) {
    return true;
  }
  
  protected boolean attachInjectorMethod(MixinTargetContext paramMixinTargetContext, MixinInfo.MixinMethodNode paramMixinMethodNode) {
    return paramMixinMethodNode.isInjector();
  }
  
  protected boolean attachAccessorMethod(MixinTargetContext paramMixinTargetContext, MixinInfo.MixinMethodNode paramMixinMethodNode) {
    return (attachAccessorMethod(paramMixinTargetContext, paramMixinMethodNode, SpecialMethod.ACCESSOR) || 
      attachAccessorMethod(paramMixinTargetContext, paramMixinMethodNode, SpecialMethod.INVOKER));
  }
  
  protected boolean attachAccessorMethod(MixinTargetContext paramMixinTargetContext, MixinInfo.MixinMethodNode paramMixinMethodNode, SpecialMethod paramSpecialMethod) {
    AnnotationNode annotationNode = paramMixinMethodNode.getVisibleAnnotation(paramSpecialMethod.annotation);
    if (annotationNode == null)
      return false; 
    String str = paramSpecialMethod + " method " + paramMixinMethodNode.name;
    ClassInfo.Method method = getSpecialMethod(paramMixinMethodNode, paramSpecialMethod);
    if (MixinEnvironment.getCompatibilityLevel().isAtLeast(MixinEnvironment.CompatibilityLevel.JAVA_8) && method.isStatic()) {
      if (this.mixin.getTargets().size() > 1)
        throw new InvalidAccessorException(paramMixinTargetContext, str + " in multi-target mixin is invalid. Mixin must have exactly 1 target."); 
      String str1 = paramMixinTargetContext.getUniqueName(paramMixinMethodNode, true);
      logger.log(this.mixin.getLoggingLevel(), "Renaming @Unique method {}{} to {} in {}", new Object[] { paramMixinMethodNode.name, paramMixinMethodNode.desc, str1, this.mixin });
      paramMixinMethodNode.name = method.renameTo(str1);
    } else {
      if (!method.isAbstract())
        throw new InvalidAccessorException(paramMixinTargetContext, str + " is not abstract"); 
      if (method.isStatic())
        throw new InvalidAccessorException(paramMixinTargetContext, str + " cannot be static"); 
    } 
    paramMixinTargetContext.addAccessorMethod(paramMixinMethodNode, paramSpecialMethod.annotation);
    return true;
  }
  
  protected boolean attachShadowMethod(MixinTargetContext paramMixinTargetContext, MixinInfo.MixinMethodNode paramMixinMethodNode) {
    return attachSpecialMethod(paramMixinTargetContext, paramMixinMethodNode, SpecialMethod.SHADOW);
  }
  
  protected boolean attachOverwriteMethod(MixinTargetContext paramMixinTargetContext, MixinInfo.MixinMethodNode paramMixinMethodNode) {
    return attachSpecialMethod(paramMixinTargetContext, paramMixinMethodNode, SpecialMethod.OVERWRITE);
  }
  
  protected boolean attachSpecialMethod(MixinTargetContext paramMixinTargetContext, MixinInfo.MixinMethodNode paramMixinMethodNode, SpecialMethod paramSpecialMethod) {
    AnnotationNode annotationNode = paramMixinMethodNode.getVisibleAnnotation(paramSpecialMethod.annotation);
    if (annotationNode == null)
      return false; 
    if (paramSpecialMethod.isOverwrite)
      checkMixinNotUnique(paramMixinMethodNode, paramSpecialMethod); 
    ClassInfo.Method method = getSpecialMethod(paramMixinMethodNode, paramSpecialMethod);
    MethodNode methodNode = paramMixinTargetContext.findMethod(paramMixinMethodNode, annotationNode);
    if (methodNode == null) {
      if (paramSpecialMethod.isOverwrite)
        return false; 
      methodNode = paramMixinTargetContext.findRemappedMethod(paramMixinMethodNode);
      if (methodNode == null)
        throw new InvalidMixinException(this.mixin, 
            String.format("%s method %s in %s was not located in the target class %s. %s%s", new Object[] { paramSpecialMethod, paramMixinMethodNode.name, this.mixin, paramMixinTargetContext.getTarget(), paramMixinTargetContext.getReferenceMapper().getStatus(), 
                getDynamicInfo(paramMixinMethodNode) })); 
      paramMixinMethodNode.name = method.renameTo(methodNode.name);
    } 
    if ("<init>".equals(methodNode.name))
      throw new InvalidMixinException(this.mixin, String.format("Nice try! %s in %s cannot alias a constructor", new Object[] { paramMixinMethodNode.name, this.mixin })); 
    if (!Bytecode.compareFlags(paramMixinMethodNode, methodNode, 8))
      throw new InvalidMixinException(this.mixin, String.format("STATIC modifier of %s method %s in %s does not match the target", new Object[] { paramSpecialMethod, paramMixinMethodNode.name, this.mixin })); 
    conformVisibility(paramMixinTargetContext, paramMixinMethodNode, paramSpecialMethod, methodNode);
    if (!methodNode.name.equals(paramMixinMethodNode.name)) {
      if (paramSpecialMethod.isOverwrite && (methodNode.access & 0x2) == 0)
        throw new InvalidMixinException(this.mixin, "Non-private method cannot be aliased. Found " + methodNode.name); 
      paramMixinMethodNode.name = method.renameTo(methodNode.name);
    } 
    return true;
  }
  
  private void conformVisibility(MixinTargetContext paramMixinTargetContext, MixinInfo.MixinMethodNode paramMixinMethodNode, SpecialMethod paramSpecialMethod, MethodNode paramMethodNode) {
    Bytecode.Visibility visibility1 = Bytecode.getVisibility(paramMethodNode);
    Bytecode.Visibility visibility2 = Bytecode.getVisibility(paramMixinMethodNode);
    if (visibility2.ordinal() >= visibility1.ordinal()) {
      if (visibility1 == Bytecode.Visibility.PRIVATE && visibility2.ordinal() > Bytecode.Visibility.PRIVATE.ordinal())
        paramMixinTargetContext.getTarget().addUpgradedMethod(paramMethodNode); 
      return;
    } 
    String str = String.format("%s %s method %s in %s cannot reduce visibiliy of %s target method", new Object[] { visibility2, paramSpecialMethod, paramMixinMethodNode.name, this.mixin, visibility1 });
    if (paramSpecialMethod.isOverwrite && !this.mixin.getParent().conformOverwriteVisibility())
      throw new InvalidMixinException(this.mixin, str); 
    if (visibility2 == Bytecode.Visibility.PRIVATE) {
      if (paramSpecialMethod.isOverwrite)
        logger.warn("Static binding violation: {}, visibility will be upgraded.", new Object[] { str }); 
      paramMixinTargetContext.addUpgradedMethod(paramMixinMethodNode);
      Bytecode.setVisibility(paramMixinMethodNode, visibility1);
    } 
  }
  
  protected ClassInfo.Method getSpecialMethod(MixinInfo.MixinMethodNode paramMixinMethodNode, SpecialMethod paramSpecialMethod) {
    ClassInfo.Method method = this.mixin.getClassInfo().findMethod(paramMixinMethodNode, 10);
    checkMethodNotUnique(method, paramSpecialMethod);
    return method;
  }
  
  protected void checkMethodNotUnique(ClassInfo.Method paramMethod, SpecialMethod paramSpecialMethod) {
    if (paramMethod.isUnique())
      throw new InvalidMixinException(this.mixin, String.format("%s method %s in %s cannot be @Unique", new Object[] { paramSpecialMethod, paramMethod.getName(), this.mixin })); 
  }
  
  protected void checkMixinNotUnique(MixinInfo.MixinMethodNode paramMixinMethodNode, SpecialMethod paramSpecialMethod) {
    if (this.mixin.isUnique())
      throw new InvalidMixinException(this.mixin, String.format("%s method %s found in a @Unique mixin %s", new Object[] { paramSpecialMethod, paramMixinMethodNode.name, this.mixin })); 
  }
  
  protected boolean attachUniqueMethod(MixinTargetContext paramMixinTargetContext, MixinInfo.MixinMethodNode paramMixinMethodNode) {
    ClassInfo.Method method = this.mixin.getClassInfo().findMethod(paramMixinMethodNode, 10);
    if (method == null || (!method.isUnique() && !this.mixin.isUnique() && !method.isSynthetic()))
      return false; 
    if (method.isSynthetic()) {
      paramMixinTargetContext.transformDescriptor(paramMixinMethodNode);
      method.remapTo(paramMixinMethodNode.desc);
    } 
    MethodNode methodNode = paramMixinTargetContext.findMethod(paramMixinMethodNode, (AnnotationNode)null);
    if (methodNode == null)
      return false; 
    String str = method.isSynthetic() ? "synthetic" : "@Unique";
    if (Bytecode.getVisibility(paramMixinMethodNode).ordinal() < Bytecode.Visibility.PUBLIC.ordinal()) {
      String str1 = paramMixinTargetContext.getUniqueName(paramMixinMethodNode, false);
      logger.log(this.mixin.getLoggingLevel(), "Renaming {} method {}{} to {} in {}", new Object[] { str, paramMixinMethodNode.name, paramMixinMethodNode.desc, str1, this.mixin });
      paramMixinMethodNode.name = method.renameTo(str1);
      return false;
    } 
    if (this.strictUnique)
      throw new InvalidMixinException(this.mixin, String.format("Method conflict, %s method %s in %s cannot overwrite %s%s in %s", new Object[] { str, paramMixinMethodNode.name, this.mixin, methodNode.name, methodNode.desc, paramMixinTargetContext
              .getTarget() })); 
    AnnotationNode annotationNode = Annotations.getVisible(paramMixinMethodNode, Unique.class);
    if (annotationNode == null || !((Boolean)Annotations.getValue(annotationNode, "silent", Boolean.FALSE)).booleanValue()) {
      if (Bytecode.hasFlag(paramMixinMethodNode, 64))
        try {
          Bytecode.compareBridgeMethods(methodNode, paramMixinMethodNode);
          logger.debug("Discarding sythetic bridge method {} in {} because existing method in {} is compatible", new Object[] { str, paramMixinMethodNode.name, this.mixin, paramMixinTargetContext
                .getTarget() });
          return true;
        } catch (SyntheticBridgeException syntheticBridgeException) {
          if (this.verboseLogging || this.env.getOption(MixinEnvironment.Option.DEBUG_VERIFY))
            syntheticBridgeException.printAnalysis(paramMixinTargetContext, methodNode, paramMixinMethodNode); 
          throw new InvalidMixinException(this.mixin, syntheticBridgeException.getMessage());
        }  
      logger.warn("Discarding {} public method {} in {} because it already exists in {}", new Object[] { str, paramMixinMethodNode.name, this.mixin, paramMixinTargetContext
            .getTarget() });
      return true;
    } 
    paramMixinTargetContext.addMixinMethod(paramMixinMethodNode);
    return true;
  }
  
  protected void attachMethod(MixinTargetContext paramMixinTargetContext, MixinInfo.MixinMethodNode paramMixinMethodNode) {
    ClassInfo.Method method1 = this.mixin.getClassInfo().findMethod(paramMixinMethodNode);
    if (method1 == null)
      return; 
    ClassInfo.Method method2 = this.mixin.getClassInfo().findMethodInHierarchy(paramMixinMethodNode, ClassInfo.SearchType.SUPER_CLASSES_ONLY);
    if (method2 != null && method2.isRenamed())
      paramMixinMethodNode.name = method1.renameTo(method2.getName()); 
    MethodNode methodNode = paramMixinTargetContext.findMethod(paramMixinMethodNode, (AnnotationNode)null);
    if (methodNode != null)
      conformVisibility(paramMixinTargetContext, paramMixinMethodNode, SpecialMethod.MERGE, methodNode); 
  }
  
  protected void attachFields(MixinTargetContext paramMixinTargetContext) {
    for (Iterator<FieldNode> iterator = this.classNode.fields.iterator(); iterator.hasNext(); ) {
      FieldNode fieldNode1 = iterator.next();
      AnnotationNode annotationNode = Annotations.getVisible(fieldNode1, Shadow.class);
      boolean bool = (annotationNode != null) ? true : false;
      if (!validateField(paramMixinTargetContext, fieldNode1, annotationNode)) {
        iterator.remove();
        continue;
      } 
      ClassInfo.Field field = this.mixin.getClassInfo().findField(fieldNode1);
      paramMixinTargetContext.transformDescriptor(fieldNode1);
      field.remapTo(fieldNode1.desc);
      if (field.isUnique() && bool)
        throw new InvalidMixinException(this.mixin, String.format("@Shadow field %s cannot be @Unique", new Object[] { fieldNode1.name })); 
      FieldNode fieldNode2 = paramMixinTargetContext.findField(fieldNode1, annotationNode);
      if (fieldNode2 == null) {
        if (annotationNode == null)
          continue; 
        fieldNode2 = paramMixinTargetContext.findRemappedField(fieldNode1);
        if (fieldNode2 == null)
          throw new InvalidMixinException(this.mixin, String.format("Shadow field %s was not located in the target class %s. %s%s", new Object[] { fieldNode1.name, paramMixinTargetContext
                  .getTarget(), paramMixinTargetContext.getReferenceMapper().getStatus(), 
                  getDynamicInfo(fieldNode1) })); 
        fieldNode1.name = field.renameTo(fieldNode2.name);
      } 
      if (!Bytecode.compareFlags(fieldNode1, fieldNode2, 8))
        throw new InvalidMixinException(this.mixin, String.format("STATIC modifier of @Shadow field %s in %s does not match the target", new Object[] { fieldNode1.name, this.mixin })); 
      if (field.isUnique()) {
        if ((fieldNode1.access & 0x6) != 0) {
          String str = paramMixinTargetContext.getUniqueName(fieldNode1);
          logger.log(this.mixin.getLoggingLevel(), "Renaming @Unique field {}{} to {} in {}", new Object[] { fieldNode1.name, fieldNode1.desc, str, this.mixin });
          fieldNode1.name = field.renameTo(str);
          continue;
        } 
        if (this.strictUnique)
          throw new InvalidMixinException(this.mixin, String.format("Field conflict, @Unique field %s in %s cannot overwrite %s%s in %s", new Object[] { fieldNode1.name, this.mixin, fieldNode2.name, fieldNode2.desc, paramMixinTargetContext
                  .getTarget() })); 
        logger.warn("Discarding @Unique public field {} in {} because it already exists in {}. Note that declared FIELD INITIALISERS will NOT be removed!", new Object[] { fieldNode1.name, this.mixin, paramMixinTargetContext
              .getTarget() });
        iterator.remove();
        continue;
      } 
      if (!fieldNode2.desc.equals(fieldNode1.desc))
        throw new InvalidMixinException(this.mixin, String.format("The field %s in the target class has a conflicting signature", new Object[] { fieldNode1.name })); 
      if (!fieldNode2.name.equals(fieldNode1.name)) {
        if ((fieldNode2.access & 0x2) == 0 && (fieldNode2.access & 0x1000) == 0)
          throw new InvalidMixinException(this.mixin, "Non-private field cannot be aliased. Found " + fieldNode2.name); 
        fieldNode1.name = field.renameTo(fieldNode2.name);
      } 
      iterator.remove();
      if (bool) {
        boolean bool1 = field.isDecoratedFinal();
        if (this.verboseLogging && Bytecode.hasFlag(fieldNode2, 16) != bool1) {
          String str = bool1 ? "@Shadow field {}::{} is decorated with @Final but target is not final" : "@Shadow target {}::{} is final but shadow is not decorated with @Final";
          logger.warn(str, new Object[] { this.mixin, fieldNode1.name });
        } 
        paramMixinTargetContext.addShadowField(fieldNode1, field);
      } 
    } 
  }
  
  protected boolean validateField(MixinTargetContext paramMixinTargetContext, FieldNode paramFieldNode, AnnotationNode paramAnnotationNode) {
    if (Bytecode.hasFlag(paramFieldNode, 8) && 
      !Bytecode.hasFlag(paramFieldNode, 2) && 
      !Bytecode.hasFlag(paramFieldNode, 4096) && paramAnnotationNode == null)
      throw new InvalidMixinException(paramMixinTargetContext, String.format("Mixin %s contains non-private static field %s:%s", new Object[] { paramMixinTargetContext, paramFieldNode.name, paramFieldNode.desc })); 
    String str = (String)Annotations.getValue(paramAnnotationNode, "prefix", Shadow.class);
    if (paramFieldNode.name.startsWith(str))
      throw new InvalidMixinException(paramMixinTargetContext, String.format("@Shadow field %s.%s has a shadow prefix. This is not allowed.", new Object[] { paramMixinTargetContext, paramFieldNode.name })); 
    if ("super$".equals(paramFieldNode.name)) {
      if (paramFieldNode.access != 2)
        throw new InvalidMixinException(this.mixin, String.format("Imaginary super field %s.%s must be private and non-final", new Object[] { paramMixinTargetContext, paramFieldNode.name })); 
      if (!paramFieldNode.desc.equals("L" + this.mixin.getClassRef() + ";"))
        throw new InvalidMixinException(this.mixin, 
            String.format("Imaginary super field %s.%s must have the same type as the parent mixin (%s)", new Object[] { paramMixinTargetContext, paramFieldNode.name, this.mixin.getClassName() })); 
      return false;
    } 
    return true;
  }
  
  protected void transform(MixinTargetContext paramMixinTargetContext) {
    for (MethodNode methodNode : this.classNode.methods) {
      for (ListIterator<AbstractInsnNode> listIterator = methodNode.instructions.iterator(); listIterator.hasNext(); ) {
        AbstractInsnNode abstractInsnNode = listIterator.next();
        if (abstractInsnNode instanceof MethodInsnNode) {
          transformMethod((MethodInsnNode)abstractInsnNode);
          continue;
        } 
        if (abstractInsnNode instanceof FieldInsnNode)
          transformField((FieldInsnNode)abstractInsnNode); 
      } 
    } 
  }
  
  protected void transformMethod(MethodInsnNode paramMethodInsnNode) {
    Profiler.Section section = this.profiler.begin("meta");
    ClassInfo classInfo = ClassInfo.forName(paramMethodInsnNode.owner);
    if (classInfo == null)
      throw new RuntimeException(new ClassNotFoundException(paramMethodInsnNode.owner.replace('/', '.'))); 
    ClassInfo.Method method = classInfo.findMethodInHierarchy(paramMethodInsnNode, ClassInfo.SearchType.ALL_CLASSES, 2);
    section.end();
    if (method != null && method.isRenamed())
      paramMethodInsnNode.name = method.getName(); 
  }
  
  protected void transformField(FieldInsnNode paramFieldInsnNode) {
    Profiler.Section section = this.profiler.begin("meta");
    ClassInfo classInfo = ClassInfo.forName(paramFieldInsnNode.owner);
    if (classInfo == null)
      throw new RuntimeException(new ClassNotFoundException(paramFieldInsnNode.owner.replace('/', '.'))); 
    ClassInfo.Field field = classInfo.findField(paramFieldInsnNode, 2);
    section.end();
    if (field != null && field.isRenamed())
      paramFieldInsnNode.name = field.getName(); 
  }
  
  protected static String getDynamicInfo(MethodNode paramMethodNode) {
    return getDynamicInfo("Method", Annotations.getInvisible(paramMethodNode, Dynamic.class));
  }
  
  protected static String getDynamicInfo(FieldNode paramFieldNode) {
    return getDynamicInfo("Field", Annotations.getInvisible(paramFieldNode, Dynamic.class));
  }
  
  private static String getDynamicInfo(String paramString, AnnotationNode paramAnnotationNode) {
    String str = Strings.nullToEmpty((String)Annotations.getValue(paramAnnotationNode));
    Type type = (Type)Annotations.getValue(paramAnnotationNode, "mixin");
    if (type != null)
      str = String.format("{%s} %s", new Object[] { type.getClassName(), str }).trim(); 
    return (str.length() > 0) ? String.format(" %s is @Dynamic(%s)", new Object[] { paramString, str }) : "";
  }
}
