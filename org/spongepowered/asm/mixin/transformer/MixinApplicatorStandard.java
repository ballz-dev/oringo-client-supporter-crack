package org.spongepowered.asm.mixin.transformer;

import com.google.common.collect.ImmutableList;
import java.lang.annotation.Annotation;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.SortedSet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.lib.Label;
import org.spongepowered.asm.lib.Type;
import org.spongepowered.asm.lib.signature.SignatureReader;
import org.spongepowered.asm.lib.signature.SignatureVisitor;
import org.spongepowered.asm.lib.tree.AbstractInsnNode;
import org.spongepowered.asm.lib.tree.AnnotationNode;
import org.spongepowered.asm.lib.tree.ClassNode;
import org.spongepowered.asm.lib.tree.FieldInsnNode;
import org.spongepowered.asm.lib.tree.FieldNode;
import org.spongepowered.asm.lib.tree.LabelNode;
import org.spongepowered.asm.lib.tree.LineNumberNode;
import org.spongepowered.asm.lib.tree.MethodInsnNode;
import org.spongepowered.asm.lib.tree.MethodNode;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Intrinsic;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.transformer.ext.extensions.ExtensionClassExporter;
import org.spongepowered.asm.mixin.transformer.meta.MixinMerged;
import org.spongepowered.asm.mixin.transformer.meta.MixinRenamed;
import org.spongepowered.asm.mixin.transformer.throwables.InvalidMixinException;
import org.spongepowered.asm.util.Annotations;
import org.spongepowered.asm.util.Bytecode;
import org.spongepowered.asm.util.ConstraintParser;
import org.spongepowered.asm.util.ITokenProvider;
import org.spongepowered.asm.util.perf.Profiler;
import org.spongepowered.asm.util.throwables.ConstraintViolationException;
import org.spongepowered.asm.util.throwables.InvalidConstraintException;

class MixinApplicatorStandard {
  protected static final List<Class<? extends Annotation>> CONSTRAINED_ANNOTATIONS = (List<Class<? extends Annotation>>)ImmutableList.of(Overwrite.class, Inject.class, ModifyArg.class, ModifyArgs.class, Redirect.class, ModifyVariable.class, ModifyConstant.class);
  
  enum ApplicatorPass {
    MAIN, INJECT, PREINJECT;
    
    static {
      INJECT = new ApplicatorPass("INJECT", 2);
      $VALUES = new ApplicatorPass[] { MAIN, PREINJECT, INJECT };
    }
  }
  
  enum InitialiserInjectionMode {
    DEFAULT, SAFE;
  }
  
  class Range {
    final int start;
    
    final int marker;
    
    final int end;
    
    Range(int param1Int1, int param1Int2, int param1Int3) {
      this.start = param1Int1;
      this.end = param1Int2;
      this.marker = param1Int3;
    }
    
    boolean isValid() {
      return (this.start != 0 && this.end != 0 && this.end >= this.start);
    }
    
    boolean contains(int param1Int) {
      return (param1Int >= this.start && param1Int <= this.end);
    }
    
    boolean excludes(int param1Int) {
      return (param1Int < this.start || param1Int > this.end);
    }
    
    public String toString() {
      return String.format("Range[%d-%d,%d,valid=%s)", new Object[] { Integer.valueOf(this.start), Integer.valueOf(this.end), Integer.valueOf(this.marker), Boolean.valueOf(isValid()) });
    }
  }
  
  static {
    INITIALISER_OPCODE_BLACKLIST = new int[] { 
        177, 21, 22, 23, 24, 46, 47, 48, 49, 50, 
        51, 52, 53, 54, 55, 56, 57, 58, 79, 80, 
        81, 82, 83, 84, 85, 86 };
  }
  
  protected final Logger logger = LogManager.getLogger("mixin");
  
  protected final Profiler profiler = MixinEnvironment.getProfiler();
  
  protected static final int[] INITIALISER_OPCODE_BLACKLIST;
  
  protected final TargetClassContext context;
  
  protected final String targetName;
  
  protected final ClassNode targetClass;
  
  protected final boolean mergeSignatures;
  
  MixinApplicatorStandard(TargetClassContext paramTargetClassContext) {
    this.context = paramTargetClassContext;
    this.targetName = paramTargetClassContext.getClassName();
    this.targetClass = paramTargetClassContext.getClassNode();
    ExtensionClassExporter extensionClassExporter = (ExtensionClassExporter)paramTargetClassContext.getExtensions().getExtension(ExtensionClassExporter.class);
    this
      .mergeSignatures = (extensionClassExporter.isDecompilerActive() && MixinEnvironment.getCurrentEnvironment().getOption(MixinEnvironment.Option.DEBUG_EXPORT_DECOMPILE_MERGESIGNATURES));
  }
  
  void apply(SortedSet<MixinInfo> paramSortedSet) {
    ArrayList<MixinTargetContext> arrayList = new ArrayList();
    for (MixinInfo mixinInfo : paramSortedSet) {
      this.logger.log(mixinInfo.getLoggingLevel(), "Mixing {} from {} into {}", new Object[] { mixinInfo.getName(), mixinInfo.getParent(), this.targetName });
      arrayList.add(mixinInfo.createContextFor(this.context));
    } 
    MixinTargetContext mixinTargetContext = null;
    try {
      for (MixinTargetContext mixinTargetContext1 : arrayList)
        (mixinTargetContext = mixinTargetContext1).preApply(this.targetName, this.targetClass); 
      for (ApplicatorPass applicatorPass : ApplicatorPass.values()) {
        Profiler.Section section = this.profiler.begin(new String[] { "pass", applicatorPass.name().toLowerCase() });
        for (MixinTargetContext mixinTargetContext1 : arrayList)
          applyMixin(mixinTargetContext = mixinTargetContext1, applicatorPass); 
        section.end();
      } 
      for (MixinTargetContext mixinTargetContext1 : arrayList)
        (mixinTargetContext = mixinTargetContext1).postApply(this.targetName, this.targetClass); 
    } catch (InvalidMixinException invalidMixinException) {
      throw invalidMixinException;
    } catch (Exception exception) {
      throw new InvalidMixinException(mixinTargetContext, "Unexpecteded " + exception.getClass().getSimpleName() + " whilst applying the mixin class: " + exception
          .getMessage(), exception);
    } 
    applySourceMap(this.context);
    this.context.processDebugTasks();
  }
  
  protected final void applyMixin(MixinTargetContext paramMixinTargetContext, ApplicatorPass paramApplicatorPass) {
    switch (paramApplicatorPass) {
      case MAIN:
        applySignature(paramMixinTargetContext);
        applyInterfaces(paramMixinTargetContext);
        applyAttributes(paramMixinTargetContext);
        applyAnnotations(paramMixinTargetContext);
        applyFields(paramMixinTargetContext);
        applyMethods(paramMixinTargetContext);
        applyInitialisers(paramMixinTargetContext);
        return;
      case PREINJECT:
        prepareInjections(paramMixinTargetContext);
        return;
      case INJECT:
        applyAccessors(paramMixinTargetContext);
        applyInjections(paramMixinTargetContext);
        return;
    } 
    throw new IllegalStateException("Invalid pass specified " + paramApplicatorPass);
  }
  
  protected void applySignature(MixinTargetContext paramMixinTargetContext) {
    if (this.mergeSignatures)
      this.context.mergeSignature(paramMixinTargetContext.getSignature()); 
  }
  
  protected void applyInterfaces(MixinTargetContext paramMixinTargetContext) {
    for (String str : paramMixinTargetContext.getInterfaces()) {
      if (!this.targetClass.interfaces.contains(str)) {
        this.targetClass.interfaces.add(str);
        paramMixinTargetContext.getTargetClassInfo().addInterface(str);
      } 
    } 
  }
  
  protected void applyAttributes(MixinTargetContext paramMixinTargetContext) {
    if (paramMixinTargetContext.shouldSetSourceFile())
      this.targetClass.sourceFile = paramMixinTargetContext.getSourceFile(); 
    this.targetClass.version = Math.max(this.targetClass.version, paramMixinTargetContext.getMinRequiredClassVersion());
  }
  
  protected void applyAnnotations(MixinTargetContext paramMixinTargetContext) {
    ClassNode classNode = paramMixinTargetContext.getClassNode();
    Bytecode.mergeAnnotations(classNode, this.targetClass);
  }
  
  protected void applyFields(MixinTargetContext paramMixinTargetContext) {
    mergeShadowFields(paramMixinTargetContext);
    mergeNewFields(paramMixinTargetContext);
  }
  
  protected void mergeShadowFields(MixinTargetContext paramMixinTargetContext) {
    for (Map.Entry<FieldNode, ClassInfo.Field> entry : paramMixinTargetContext.getShadowFields()) {
      FieldNode fieldNode1 = (FieldNode)entry.getKey();
      FieldNode fieldNode2 = findTargetField(fieldNode1);
      if (fieldNode2 != null) {
        Bytecode.mergeAnnotations(fieldNode1, fieldNode2);
        if (((ClassInfo.Field)entry.getValue()).isDecoratedMutable() && !Bytecode.hasFlag(fieldNode2, 2))
          fieldNode2.access &= 0xFFFFFFEF; 
      } 
    } 
  }
  
  protected void mergeNewFields(MixinTargetContext paramMixinTargetContext) {
    for (FieldNode fieldNode1 : paramMixinTargetContext.getFields()) {
      FieldNode fieldNode2 = findTargetField(fieldNode1);
      if (fieldNode2 == null) {
        this.targetClass.fields.add(fieldNode1);
        if (fieldNode1.signature != null) {
          if (this.mergeSignatures) {
            SignatureVisitor signatureVisitor = paramMixinTargetContext.getSignature().getRemapper();
            (new SignatureReader(fieldNode1.signature)).accept(signatureVisitor);
            fieldNode1.signature = signatureVisitor.toString();
            continue;
          } 
          fieldNode1.signature = null;
        } 
      } 
    } 
  }
  
  protected void applyMethods(MixinTargetContext paramMixinTargetContext) {
    for (MethodNode methodNode : paramMixinTargetContext.getShadowMethods())
      applyShadowMethod(paramMixinTargetContext, methodNode); 
    for (MethodNode methodNode : paramMixinTargetContext.getMethods())
      applyNormalMethod(paramMixinTargetContext, methodNode); 
  }
  
  protected void applyShadowMethod(MixinTargetContext paramMixinTargetContext, MethodNode paramMethodNode) {
    MethodNode methodNode = findTargetMethod(paramMethodNode);
    if (methodNode != null)
      Bytecode.mergeAnnotations(paramMethodNode, methodNode); 
  }
  
  protected void applyNormalMethod(MixinTargetContext paramMixinTargetContext, MethodNode paramMethodNode) {
    paramMixinTargetContext.transformMethod(paramMethodNode);
    if (!paramMethodNode.name.startsWith("<")) {
      checkMethodVisibility(paramMixinTargetContext, paramMethodNode);
      checkMethodConstraints(paramMixinTargetContext, paramMethodNode);
      mergeMethod(paramMixinTargetContext, paramMethodNode);
    } else if ("<clinit>".equals(paramMethodNode.name)) {
      appendInsns(paramMixinTargetContext, paramMethodNode);
    } 
  }
  
  protected void mergeMethod(MixinTargetContext paramMixinTargetContext, MethodNode paramMethodNode) {
    boolean bool = (Annotations.getVisible(paramMethodNode, Overwrite.class) != null) ? true : false;
    MethodNode methodNode = findTargetMethod(paramMethodNode);
    if (methodNode != null) {
      if (isAlreadyMerged(paramMixinTargetContext, paramMethodNode, bool, methodNode))
        return; 
      AnnotationNode annotationNode = Annotations.getInvisible(paramMethodNode, Intrinsic.class);
      if (annotationNode != null) {
        if (mergeIntrinsic(paramMixinTargetContext, paramMethodNode, bool, methodNode, annotationNode)) {
          paramMixinTargetContext.getTarget().methodMerged(paramMethodNode);
          return;
        } 
      } else {
        if (paramMixinTargetContext.requireOverwriteAnnotations() && !bool)
          throw new InvalidMixinException(paramMixinTargetContext, 
              String.format("%s%s in %s cannot overwrite method in %s because @Overwrite is required by the parent configuration", new Object[] { paramMethodNode.name, paramMethodNode.desc, paramMixinTargetContext, paramMixinTargetContext.getTarget().getClassName() })); 
        this.targetClass.methods.remove(methodNode);
      } 
    } else if (bool) {
      throw new InvalidMixinException(paramMixinTargetContext, String.format("Overwrite target \"%s\" was not located in target class %s", new Object[] { paramMethodNode.name, paramMixinTargetContext
              .getTargetClassRef() }));
    } 
    this.targetClass.methods.add(paramMethodNode);
    paramMixinTargetContext.methodMerged(paramMethodNode);
    if (paramMethodNode.signature != null)
      if (this.mergeSignatures) {
        SignatureVisitor signatureVisitor = paramMixinTargetContext.getSignature().getRemapper();
        (new SignatureReader(paramMethodNode.signature)).accept(signatureVisitor);
        paramMethodNode.signature = signatureVisitor.toString();
      } else {
        paramMethodNode.signature = null;
      }  
  }
  
  protected boolean isAlreadyMerged(MixinTargetContext paramMixinTargetContext, MethodNode paramMethodNode1, boolean paramBoolean, MethodNode paramMethodNode2) {
    AnnotationNode annotationNode = Annotations.getVisible(paramMethodNode2, MixinMerged.class);
    if (annotationNode == null) {
      if (Annotations.getVisible(paramMethodNode2, Final.class) != null) {
        this.logger.warn("Overwrite prohibited for @Final method {} in {}. Skipping method.", new Object[] { paramMethodNode1.name, paramMixinTargetContext });
        return true;
      } 
      return false;
    } 
    String str1 = (String)Annotations.getValue(annotationNode, "sessionId");
    if (!this.context.getSessionId().equals(str1))
      throw new ClassFormatError("Invalid @MixinMerged annotation found in" + paramMixinTargetContext + " at " + paramMethodNode1.name + " in " + this.targetClass.name); 
    if (Bytecode.hasFlag(paramMethodNode2, 4160) && 
      Bytecode.hasFlag(paramMethodNode1, 4160)) {
      if (paramMixinTargetContext.getEnvironment().getOption(MixinEnvironment.Option.DEBUG_VERBOSE))
        this.logger.warn("Synthetic bridge method clash for {} in {}", new Object[] { paramMethodNode1.name, paramMixinTargetContext }); 
      return true;
    } 
    String str2 = (String)Annotations.getValue(annotationNode, "mixin");
    int i = ((Integer)Annotations.getValue(annotationNode, "priority")).intValue();
    if (i >= paramMixinTargetContext.getPriority() && !str2.equals(paramMixinTargetContext.getClassName())) {
      this.logger.warn("Method overwrite conflict for {} in {}, previously written by {}. Skipping method.", new Object[] { paramMethodNode1.name, paramMixinTargetContext, str2 });
      return true;
    } 
    if (Annotations.getVisible(paramMethodNode2, Final.class) != null) {
      this.logger.warn("Method overwrite conflict for @Final method {} in {} declared by {}. Skipping method.", new Object[] { paramMethodNode1.name, paramMixinTargetContext, str2 });
      return true;
    } 
    return false;
  }
  
  protected boolean mergeIntrinsic(MixinTargetContext paramMixinTargetContext, MethodNode paramMethodNode1, boolean paramBoolean, MethodNode paramMethodNode2, AnnotationNode paramAnnotationNode) {
    if (paramBoolean)
      throw new InvalidMixinException(paramMixinTargetContext, "@Intrinsic is not compatible with @Overwrite, remove one of these annotations on " + paramMethodNode1.name + " in " + paramMixinTargetContext); 
    String str = paramMethodNode1.name + paramMethodNode1.desc;
    if (Bytecode.hasFlag(paramMethodNode1, 8))
      throw new InvalidMixinException(paramMixinTargetContext, "@Intrinsic method cannot be static, found " + str + " in " + paramMixinTargetContext); 
    if (!Bytecode.hasFlag(paramMethodNode1, 4096)) {
      AnnotationNode annotationNode = Annotations.getVisible(paramMethodNode1, MixinRenamed.class);
      if (annotationNode == null || !((Boolean)Annotations.getValue(annotationNode, "isInterfaceMember", Boolean.FALSE)).booleanValue())
        throw new InvalidMixinException(paramMixinTargetContext, "@Intrinsic method must be prefixed interface method, no rename encountered on " + str + " in " + paramMixinTargetContext); 
    } 
    if (!((Boolean)Annotations.getValue(paramAnnotationNode, "displace", Boolean.FALSE)).booleanValue()) {
      this.logger.log(paramMixinTargetContext.getLoggingLevel(), "Skipping Intrinsic mixin method {} for {}", new Object[] { str, paramMixinTargetContext.getTargetClassRef() });
      return true;
    } 
    displaceIntrinsic(paramMixinTargetContext, paramMethodNode1, paramMethodNode2);
    return false;
  }
  
  protected void displaceIntrinsic(MixinTargetContext paramMixinTargetContext, MethodNode paramMethodNode1, MethodNode paramMethodNode2) {
    String str = "proxy+" + paramMethodNode2.name;
    for (ListIterator<AbstractInsnNode> listIterator = paramMethodNode1.instructions.iterator(); listIterator.hasNext(); ) {
      AbstractInsnNode abstractInsnNode = listIterator.next();
      if (abstractInsnNode instanceof MethodInsnNode && abstractInsnNode.getOpcode() != 184) {
        MethodInsnNode methodInsnNode = (MethodInsnNode)abstractInsnNode;
        if (methodInsnNode.owner.equals(this.targetClass.name) && methodInsnNode.name.equals(paramMethodNode2.name) && methodInsnNode.desc.equals(paramMethodNode2.desc))
          methodInsnNode.name = str; 
      } 
    } 
    paramMethodNode2.name = str;
  }
  
  protected final void appendInsns(MixinTargetContext paramMixinTargetContext, MethodNode paramMethodNode) {
    if (Type.getReturnType(paramMethodNode.desc) != Type.VOID_TYPE)
      throw new IllegalArgumentException("Attempted to merge insns from a method which does not return void"); 
    MethodNode methodNode = findTargetMethod(paramMethodNode);
    if (methodNode != null) {
      AbstractInsnNode abstractInsnNode = Bytecode.findInsn(methodNode, 177);
      if (abstractInsnNode != null) {
        ListIterator<AbstractInsnNode> listIterator = paramMethodNode.instructions.iterator();
        while (listIterator.hasNext()) {
          AbstractInsnNode abstractInsnNode1 = listIterator.next();
          if (!(abstractInsnNode1 instanceof LineNumberNode) && abstractInsnNode1.getOpcode() != 177)
            methodNode.instructions.insertBefore(abstractInsnNode, abstractInsnNode1); 
        } 
        methodNode.maxLocals = Math.max(methodNode.maxLocals, paramMethodNode.maxLocals);
        methodNode.maxStack = Math.max(methodNode.maxStack, paramMethodNode.maxStack);
      } 
      return;
    } 
    this.targetClass.methods.add(paramMethodNode);
  }
  
  protected void applyInitialisers(MixinTargetContext paramMixinTargetContext) {
    MethodNode methodNode = getConstructor(paramMixinTargetContext);
    if (methodNode == null)
      return; 
    Deque<AbstractInsnNode> deque = getInitialiser(paramMixinTargetContext, methodNode);
    if (deque == null || deque.size() == 0)
      return; 
    for (MethodNode methodNode1 : this.targetClass.methods) {
      if ("<init>".equals(methodNode1.name)) {
        methodNode1.maxStack = Math.max(methodNode1.maxStack, methodNode.maxStack);
        injectInitialiser(paramMixinTargetContext, methodNode1, deque);
      } 
    } 
  }
  
  protected MethodNode getConstructor(MixinTargetContext paramMixinTargetContext) {
    MethodNode methodNode = null;
    for (MethodNode methodNode1 : paramMixinTargetContext.getMethods()) {
      if ("<init>".equals(methodNode1.name) && Bytecode.methodHasLineNumbers(methodNode1)) {
        if (methodNode == null) {
          methodNode = methodNode1;
          continue;
        } 
        this.logger.warn(String.format("Mixin %s has multiple constructors, %s was selected\n", new Object[] { paramMixinTargetContext, methodNode.desc }));
      } 
    } 
    return methodNode;
  }
  
  private Range getConstructorRange(MethodNode paramMethodNode) {
    boolean bool = false;
    AbstractInsnNode abstractInsnNode = null;
    int i = 0, j = 0, k = 0, m = -1;
    for (ListIterator<AbstractInsnNode> listIterator = paramMethodNode.instructions.iterator(); listIterator.hasNext(); ) {
      AbstractInsnNode abstractInsnNode1 = listIterator.next();
      if (abstractInsnNode1 instanceof LineNumberNode) {
        i = ((LineNumberNode)abstractInsnNode1).line;
        bool = true;
        continue;
      } 
      if (abstractInsnNode1 instanceof MethodInsnNode) {
        if (abstractInsnNode1.getOpcode() == 183 && "<init>".equals(((MethodInsnNode)abstractInsnNode1).name) && m == -1) {
          m = paramMethodNode.instructions.indexOf(abstractInsnNode1);
          j = i;
        } 
        continue;
      } 
      if (abstractInsnNode1.getOpcode() == 181) {
        bool = false;
        continue;
      } 
      if (abstractInsnNode1.getOpcode() == 177) {
        if (bool) {
          k = i;
          continue;
        } 
        k = j;
        abstractInsnNode = abstractInsnNode1;
      } 
    } 
    if (abstractInsnNode != null) {
      LabelNode labelNode = new LabelNode(new Label());
      paramMethodNode.instructions.insertBefore(abstractInsnNode, (AbstractInsnNode)labelNode);
      paramMethodNode.instructions.insertBefore(abstractInsnNode, (AbstractInsnNode)new LineNumberNode(j, labelNode));
    } 
    return new Range(j, k, m);
  }
  
  protected final Deque<AbstractInsnNode> getInitialiser(MixinTargetContext paramMixinTargetContext, MethodNode paramMethodNode) {
    Range range = getConstructorRange(paramMethodNode);
    if (!range.isValid())
      return null; 
    int i = 0;
    ArrayDeque<AbstractInsnNode> arrayDeque = new ArrayDeque();
    boolean bool = false;
    short s = -1;
    LabelNode labelNode = null;
    for (ListIterator<AbstractInsnNode> listIterator = paramMethodNode.instructions.iterator(range.marker); listIterator.hasNext(); ) {
      AbstractInsnNode abstractInsnNode1 = listIterator.next();
      if (abstractInsnNode1 instanceof LineNumberNode) {
        i = ((LineNumberNode)abstractInsnNode1).line;
        AbstractInsnNode abstractInsnNode2 = paramMethodNode.instructions.get(paramMethodNode.instructions.indexOf(abstractInsnNode1) + 1);
        if (i == range.end && abstractInsnNode2.getOpcode() != 177) {
          bool = true;
          s = 177;
          continue;
        } 
        bool = range.excludes(i);
        s = -1;
        continue;
      } 
      if (bool) {
        if (labelNode != null) {
          arrayDeque.add(labelNode);
          labelNode = null;
        } 
        if (abstractInsnNode1 instanceof LabelNode) {
          labelNode = (LabelNode)abstractInsnNode1;
          continue;
        } 
        int j = abstractInsnNode1.getOpcode();
        if (j == s) {
          s = -1;
          continue;
        } 
        for (int k : INITIALISER_OPCODE_BLACKLIST) {
          if (j == k)
            throw new InvalidMixinException(paramMixinTargetContext, "Cannot handle " + Bytecode.getOpcodeName(j) + " opcode (0x" + 
                Integer.toHexString(j).toUpperCase() + ") in class initialiser"); 
        } 
        arrayDeque.add(abstractInsnNode1);
      } 
    } 
    AbstractInsnNode abstractInsnNode = arrayDeque.peekLast();
    if (abstractInsnNode != null && 
      abstractInsnNode.getOpcode() != 181)
      throw new InvalidMixinException(paramMixinTargetContext, "Could not parse initialiser, expected 0xB5, found 0x" + 
          Integer.toHexString(abstractInsnNode.getOpcode()) + " in " + paramMixinTargetContext); 
    return arrayDeque;
  }
  
  protected final void injectInitialiser(MixinTargetContext paramMixinTargetContext, MethodNode paramMethodNode, Deque<AbstractInsnNode> paramDeque) {
    Map map = Bytecode.cloneLabels(paramMethodNode.instructions);
    AbstractInsnNode abstractInsnNode = findInitialiserInjectionPoint(paramMixinTargetContext, paramMethodNode, paramDeque);
    if (abstractInsnNode == null) {
      this.logger.warn("Failed to locate initialiser injection point in <init>{}, initialiser was not mixed in.", new Object[] { paramMethodNode.desc });
      return;
    } 
    for (AbstractInsnNode abstractInsnNode1 : paramDeque) {
      if (abstractInsnNode1 instanceof LabelNode)
        continue; 
      if (abstractInsnNode1 instanceof org.spongepowered.asm.lib.tree.JumpInsnNode)
        throw new InvalidMixinException(paramMixinTargetContext, "Unsupported JUMP opcode in initialiser in " + paramMixinTargetContext); 
      AbstractInsnNode abstractInsnNode2 = abstractInsnNode1.clone(map);
      paramMethodNode.instructions.insert(abstractInsnNode, abstractInsnNode2);
      abstractInsnNode = abstractInsnNode2;
    } 
  }
  
  protected AbstractInsnNode findInitialiserInjectionPoint(MixinTargetContext paramMixinTargetContext, MethodNode paramMethodNode, Deque<AbstractInsnNode> paramDeque) {
    HashSet<String> hashSet = new HashSet();
    for (AbstractInsnNode abstractInsnNode1 : paramDeque) {
      if (abstractInsnNode1.getOpcode() == 181)
        hashSet.add(fieldKey((FieldInsnNode)abstractInsnNode1)); 
    } 
    InitialiserInjectionMode initialiserInjectionMode = getInitialiserInjectionMode(paramMixinTargetContext.getEnvironment());
    String str1 = paramMixinTargetContext.getTargetClassInfo().getName();
    String str2 = paramMixinTargetContext.getTargetClassInfo().getSuperName();
    AbstractInsnNode abstractInsnNode = null;
    for (ListIterator<AbstractInsnNode> listIterator = paramMethodNode.instructions.iterator(); listIterator.hasNext(); ) {
      AbstractInsnNode abstractInsnNode1 = listIterator.next();
      if (abstractInsnNode1.getOpcode() == 183 && "<init>".equals(((MethodInsnNode)abstractInsnNode1).name)) {
        String str = ((MethodInsnNode)abstractInsnNode1).owner;
        if (str.equals(str1) || str.equals(str2)) {
          abstractInsnNode = abstractInsnNode1;
          if (initialiserInjectionMode == InitialiserInjectionMode.SAFE)
            break; 
        } 
        continue;
      } 
      if (abstractInsnNode1.getOpcode() == 181 && initialiserInjectionMode == InitialiserInjectionMode.DEFAULT) {
        String str = fieldKey((FieldInsnNode)abstractInsnNode1);
        if (hashSet.contains(str))
          abstractInsnNode = abstractInsnNode1; 
      } 
    } 
    return abstractInsnNode;
  }
  
  private InitialiserInjectionMode getInitialiserInjectionMode(MixinEnvironment paramMixinEnvironment) {
    String str = paramMixinEnvironment.getOptionValue(MixinEnvironment.Option.INITIALISER_INJECTION_MODE);
    if (str == null)
      return InitialiserInjectionMode.DEFAULT; 
    try {
      return InitialiserInjectionMode.valueOf(str.toUpperCase());
    } catch (Exception exception) {
      this.logger.warn("Could not parse unexpected value \"{}\" for mixin.initialiserInjectionMode, reverting to DEFAULT", new Object[] { str });
      return InitialiserInjectionMode.DEFAULT;
    } 
  }
  
  private static String fieldKey(FieldInsnNode paramFieldInsnNode) {
    return String.format("%s:%s", new Object[] { paramFieldInsnNode.desc, paramFieldInsnNode.name });
  }
  
  protected void prepareInjections(MixinTargetContext paramMixinTargetContext) {
    paramMixinTargetContext.prepareInjections();
  }
  
  protected void applyInjections(MixinTargetContext paramMixinTargetContext) {
    paramMixinTargetContext.applyInjections();
  }
  
  protected void applyAccessors(MixinTargetContext paramMixinTargetContext) {
    List<MethodNode> list = paramMixinTargetContext.generateAccessors();
    for (MethodNode methodNode : list) {
      if (!methodNode.name.startsWith("<"))
        mergeMethod(paramMixinTargetContext, methodNode); 
    } 
  }
  
  protected void checkMethodVisibility(MixinTargetContext paramMixinTargetContext, MethodNode paramMethodNode) {
    if (Bytecode.hasFlag(paramMethodNode, 8) && 
      !Bytecode.hasFlag(paramMethodNode, 2) && 
      !Bytecode.hasFlag(paramMethodNode, 4096) && 
      Annotations.getVisible(paramMethodNode, Overwrite.class) == null)
      throw new InvalidMixinException(paramMixinTargetContext, 
          String.format("Mixin %s contains non-private static method %s", new Object[] { paramMixinTargetContext, paramMethodNode })); 
  }
  
  protected void applySourceMap(TargetClassContext paramTargetClassContext) {
    this.targetClass.sourceDebug = paramTargetClassContext.getSourceMap().toString();
  }
  
  protected void checkMethodConstraints(MixinTargetContext paramMixinTargetContext, MethodNode paramMethodNode) {
    for (Class<? extends Annotation> clazz : CONSTRAINED_ANNOTATIONS) {
      AnnotationNode annotationNode = Annotations.getVisible(paramMethodNode, clazz);
      if (annotationNode != null)
        checkConstraints(paramMixinTargetContext, paramMethodNode, annotationNode); 
    } 
  }
  
  protected final void checkConstraints(MixinTargetContext paramMixinTargetContext, MethodNode paramMethodNode, AnnotationNode paramAnnotationNode) {
    try {
      ConstraintParser.Constraint constraint = ConstraintParser.parse(paramAnnotationNode);
      try {
        constraint.check((ITokenProvider)paramMixinTargetContext.getEnvironment());
      } catch (ConstraintViolationException constraintViolationException) {
        String str = String.format("Constraint violation: %s on %s in %s", new Object[] { constraintViolationException.getMessage(), paramMethodNode, paramMixinTargetContext });
        this.logger.warn(str);
        if (!paramMixinTargetContext.getEnvironment().getOption(MixinEnvironment.Option.IGNORE_CONSTRAINTS))
          throw new InvalidMixinException(paramMixinTargetContext, str, constraintViolationException); 
      } 
    } catch (InvalidConstraintException invalidConstraintException) {
      throw new InvalidMixinException(paramMixinTargetContext, invalidConstraintException.getMessage());
    } 
  }
  
  protected final MethodNode findTargetMethod(MethodNode paramMethodNode) {
    for (MethodNode methodNode : this.targetClass.methods) {
      if (methodNode.name.equals(paramMethodNode.name) && methodNode.desc.equals(paramMethodNode.desc))
        return methodNode; 
    } 
    return null;
  }
  
  protected final FieldNode findTargetField(FieldNode paramFieldNode) {
    for (FieldNode fieldNode : this.targetClass.fields) {
      if (fieldNode.name.equals(paramFieldNode.name))
        return fieldNode; 
    } 
    return null;
  }
}
