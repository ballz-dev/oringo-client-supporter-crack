package org.spongepowered.asm.mixin.transformer;

import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.lib.tree.AnnotationNode;
import org.spongepowered.asm.lib.tree.ClassNode;
import org.spongepowered.asm.lib.tree.FieldNode;
import org.spongepowered.asm.lib.tree.MethodNode;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.injection.struct.Target;
import org.spongepowered.asm.mixin.struct.SourceMap;
import org.spongepowered.asm.mixin.transformer.ext.Extensions;
import org.spongepowered.asm.mixin.transformer.ext.ITargetClassContext;
import org.spongepowered.asm.util.Annotations;
import org.spongepowered.asm.util.Bytecode;
import org.spongepowered.asm.util.ClassSignature;

class TargetClassContext extends ClassContext implements ITargetClassContext {
  private int nextUniqueMethodIndex;
  
  private boolean applied;
  
  private final SortedSet<MixinInfo> mixins;
  
  private final ClassSignature signature;
  
  private static final Logger logger = LogManager.getLogger("mixin");
  
  private final String className;
  
  private final MixinEnvironment env;
  
  private final String sessionId;
  
  private int nextUniqueFieldIndex;
  
  private final Map<String, Target> targetMethods = new HashMap<String, Target>();
  
  private final ClassNode classNode;
  
  private final ClassInfo classInfo;
  
  private final SourceMap sourceMap;
  
  private final Extensions extensions;
  
  private boolean forceExport;
  
  private final Set<MethodNode> mixinMethods = new HashSet<MethodNode>();
  
  TargetClassContext(MixinEnvironment paramMixinEnvironment, Extensions paramExtensions, String paramString1, String paramString2, ClassNode paramClassNode, SortedSet<MixinInfo> paramSortedSet) {
    this.env = paramMixinEnvironment;
    this.extensions = paramExtensions;
    this.sessionId = paramString1;
    this.className = paramString2;
    this.classNode = paramClassNode;
    this.classInfo = ClassInfo.fromClassNode(paramClassNode);
    this.signature = this.classInfo.getSignature();
    this.mixins = paramSortedSet;
    this.sourceMap = new SourceMap(paramClassNode.sourceFile);
    this.sourceMap.addFile(this.classNode);
  }
  
  public String toString() {
    return this.className;
  }
  
  boolean isApplied() {
    return this.applied;
  }
  
  boolean isExportForced() {
    return this.forceExport;
  }
  
  Extensions getExtensions() {
    return this.extensions;
  }
  
  String getSessionId() {
    return this.sessionId;
  }
  
  String getClassRef() {
    return this.classNode.name;
  }
  
  String getClassName() {
    return this.className;
  }
  
  public ClassNode getClassNode() {
    return this.classNode;
  }
  
  List<MethodNode> getMethods() {
    return this.classNode.methods;
  }
  
  List<FieldNode> getFields() {
    return this.classNode.fields;
  }
  
  public ClassInfo getClassInfo() {
    return this.classInfo;
  }
  
  SortedSet<MixinInfo> getMixins() {
    return this.mixins;
  }
  
  SourceMap getSourceMap() {
    return this.sourceMap;
  }
  
  void mergeSignature(ClassSignature paramClassSignature) {
    this.signature.merge(paramClassSignature);
  }
  
  void addMixinMethod(MethodNode paramMethodNode) {
    this.mixinMethods.add(paramMethodNode);
  }
  
  void methodMerged(MethodNode paramMethodNode) {
    if (!this.mixinMethods.remove(paramMethodNode))
      logger.debug("Unexpected: Merged unregistered method {}{} in {}", new Object[] { paramMethodNode.name, paramMethodNode.desc, this }); 
  }
  
  MethodNode findMethod(Deque<String> paramDeque, String paramString) {
    return findAliasedMethod(paramDeque, paramString, true);
  }
  
  MethodNode findAliasedMethod(Deque<String> paramDeque, String paramString) {
    return findAliasedMethod(paramDeque, paramString, false);
  }
  
  private MethodNode findAliasedMethod(Deque<String> paramDeque, String paramString, boolean paramBoolean) {
    String str = paramDeque.poll();
    if (str == null)
      return null; 
    for (MethodNode methodNode : this.classNode.methods) {
      if (methodNode.name.equals(str) && methodNode.desc.equals(paramString))
        return methodNode; 
    } 
    if (paramBoolean)
      for (MethodNode methodNode : this.mixinMethods) {
        if (methodNode.name.equals(str) && methodNode.desc.equals(paramString))
          return methodNode; 
      }  
    return findAliasedMethod(paramDeque, paramString);
  }
  
  FieldNode findAliasedField(Deque<String> paramDeque, String paramString) {
    String str = paramDeque.poll();
    if (str == null)
      return null; 
    for (FieldNode fieldNode : this.classNode.fields) {
      if (fieldNode.name.equals(str) && fieldNode.desc.equals(paramString))
        return fieldNode; 
    } 
    return findAliasedField(paramDeque, paramString);
  }
  
  Target getTargetMethod(MethodNode paramMethodNode) {
    if (!this.classNode.methods.contains(paramMethodNode))
      throw new IllegalArgumentException("Invalid target method supplied to getTargetMethod()"); 
    String str = paramMethodNode.name + paramMethodNode.desc;
    Target target = this.targetMethods.get(str);
    if (target == null) {
      target = new Target(this.classNode, paramMethodNode);
      this.targetMethods.put(str, target);
    } 
    return target;
  }
  
  String getUniqueName(MethodNode paramMethodNode, boolean paramBoolean) {
    String str1 = Integer.toHexString(this.nextUniqueMethodIndex++);
    String str2 = paramBoolean ? "%2$s_$md$%1$s$%3$s" : "md%s$%s$%s";
    return String.format(str2, new Object[] { this.sessionId.substring(30), paramMethodNode.name, str1 });
  }
  
  String getUniqueName(FieldNode paramFieldNode) {
    String str = Integer.toHexString(this.nextUniqueFieldIndex++);
    return String.format("fd%s$%s$%s", new Object[] { this.sessionId.substring(30), paramFieldNode.name, str });
  }
  
  void applyMixins() {
    if (this.applied)
      throw new IllegalStateException("Mixins already applied to target class " + this.className); 
    this.applied = true;
    MixinApplicatorStandard mixinApplicatorStandard = createApplicator();
    mixinApplicatorStandard.apply(this.mixins);
    applySignature();
    upgradeMethods();
    checkMerges();
  }
  
  private MixinApplicatorStandard createApplicator() {
    if (this.classInfo.isInterface())
      return new MixinApplicatorInterface(this); 
    return new MixinApplicatorStandard(this);
  }
  
  private void applySignature() {
    (getClassNode()).signature = this.signature.toString();
  }
  
  private void checkMerges() {
    for (MethodNode methodNode : this.mixinMethods) {
      if (!methodNode.name.startsWith("<"))
        logger.debug("Unexpected: Registered method {}{} in {} was not merged", new Object[] { methodNode.name, methodNode.desc, this }); 
    } 
  }
  
  void processDebugTasks() {
    if (!this.env.getOption(MixinEnvironment.Option.DEBUG_VERBOSE))
      return; 
    AnnotationNode annotationNode = Annotations.getVisible(this.classNode, Debug.class);
    if (annotationNode != null) {
      this.forceExport = Boolean.TRUE.equals(Annotations.getValue(annotationNode, "export"));
      if (Boolean.TRUE.equals(Annotations.getValue(annotationNode, "print")))
        Bytecode.textify(this.classNode, System.err); 
    } 
    for (MethodNode methodNode : this.classNode.methods) {
      AnnotationNode annotationNode1 = Annotations.getVisible(methodNode, Debug.class);
      if (annotationNode1 != null && Boolean.TRUE.equals(Annotations.getValue(annotationNode1, "print")))
        Bytecode.textify(methodNode, System.err); 
    } 
  }
}
