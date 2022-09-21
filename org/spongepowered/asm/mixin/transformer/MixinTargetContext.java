package org.spongepowered.asm.mixin.transformer;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.lib.Handle;
import org.spongepowered.asm.lib.Type;
import org.spongepowered.asm.lib.tree.AbstractInsnNode;
import org.spongepowered.asm.lib.tree.AnnotationNode;
import org.spongepowered.asm.lib.tree.ClassNode;
import org.spongepowered.asm.lib.tree.FieldInsnNode;
import org.spongepowered.asm.lib.tree.FieldNode;
import org.spongepowered.asm.lib.tree.InvokeDynamicInsnNode;
import org.spongepowered.asm.lib.tree.LdcInsnNode;
import org.spongepowered.asm.lib.tree.LocalVariableNode;
import org.spongepowered.asm.lib.tree.MethodInsnNode;
import org.spongepowered.asm.lib.tree.MethodNode;
import org.spongepowered.asm.lib.tree.TypeInsnNode;
import org.spongepowered.asm.lib.tree.VarInsnNode;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.SoftOverride;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;
import org.spongepowered.asm.mixin.gen.AccessorInfo;
import org.spongepowered.asm.mixin.injection.struct.InjectionInfo;
import org.spongepowered.asm.mixin.injection.struct.InjectorGroupInfo;
import org.spongepowered.asm.mixin.injection.struct.Target;
import org.spongepowered.asm.mixin.injection.throwables.InjectionError;
import org.spongepowered.asm.mixin.injection.throwables.InjectionValidationException;
import org.spongepowered.asm.mixin.refmap.IMixinContext;
import org.spongepowered.asm.mixin.refmap.IReferenceMapper;
import org.spongepowered.asm.mixin.struct.MemberRef;
import org.spongepowered.asm.mixin.struct.SourceMap;
import org.spongepowered.asm.mixin.transformer.ext.Extensions;
import org.spongepowered.asm.mixin.transformer.meta.MixinMerged;
import org.spongepowered.asm.mixin.transformer.throwables.InvalidMixinException;
import org.spongepowered.asm.mixin.transformer.throwables.MixinTransformerError;
import org.spongepowered.asm.obfuscation.RemapperChain;
import org.spongepowered.asm.util.Annotations;
import org.spongepowered.asm.util.Bytecode;
import org.spongepowered.asm.util.ClassSignature;

public class MixinTargetContext extends ClassContext implements IMixinContext {
  private static final Logger logger = LogManager.getLogger("mixin");
  
  private final BiMap<String, String> innerClasses = (BiMap<String, String>)HashBiMap.create();
  
  private final List<MethodNode> shadowMethods = new ArrayList<MethodNode>();
  
  private final Map<FieldNode, ClassInfo.Field> shadowFields = new LinkedHashMap<FieldNode, ClassInfo.Field>();
  
  private final List<MethodNode> mergedMethods = new ArrayList<MethodNode>();
  
  private final InjectorGroupInfo.Map injectorGroups = new InjectorGroupInfo.Map();
  
  private final List<InjectionInfo> injectors = new ArrayList<InjectionInfo>();
  
  private final List<AccessorInfo> accessors = new ArrayList<AccessorInfo>();
  
  private int minRequiredClassVersion = MixinEnvironment.CompatibilityLevel.JAVA_6.classVersion();
  
  private final MixinInfo mixin;
  
  private final boolean inheritsFromMixin;
  
  private final SourceMap.File stratum;
  
  private final ClassNode classNode;
  
  private final ClassInfo targetClassInfo;
  
  private final boolean detachedSuper;
  
  private final String sessionId;
  
  private final TargetClassContext targetClass;
  
  MixinTargetContext(MixinInfo paramMixinInfo, ClassNode paramClassNode, TargetClassContext paramTargetClassContext) {
    this.mixin = paramMixinInfo;
    this.classNode = paramClassNode;
    this.targetClass = paramTargetClassContext;
    this.targetClassInfo = ClassInfo.forName(getTarget().getClassRef());
    this.stratum = paramTargetClassContext.getSourceMap().addFile(this.classNode);
    this.inheritsFromMixin = (paramMixinInfo.getClassInfo().hasMixinInHierarchy() || this.targetClassInfo.hasMixinTargetInHierarchy());
    this.detachedSuper = !this.classNode.superName.equals((getTarget().getClassNode()).superName);
    this.sessionId = paramTargetClassContext.getSessionId();
    requireVersion(paramClassNode.version);
    InnerClassGenerator innerClassGenerator = (InnerClassGenerator)paramTargetClassContext.getExtensions().getGenerator(InnerClassGenerator.class);
    for (String str : this.mixin.getInnerClasses())
      this.innerClasses.put(str, innerClassGenerator.registerInnerClass(this.mixin, str, this)); 
  }
  
  void addShadowMethod(MethodNode paramMethodNode) {
    this.shadowMethods.add(paramMethodNode);
  }
  
  void addShadowField(FieldNode paramFieldNode, ClassInfo.Field paramField) {
    this.shadowFields.put(paramFieldNode, paramField);
  }
  
  void addAccessorMethod(MethodNode paramMethodNode, Class<? extends Annotation> paramClass) {
    this.accessors.add(AccessorInfo.of(this, paramMethodNode, paramClass));
  }
  
  void addMixinMethod(MethodNode paramMethodNode) {
    Annotations.setVisible(paramMethodNode, MixinMerged.class, new Object[] { "mixin", getClassName() });
    getTarget().addMixinMethod(paramMethodNode);
  }
  
  void methodMerged(MethodNode paramMethodNode) {
    this.mergedMethods.add(paramMethodNode);
    this.targetClassInfo.addMethod(paramMethodNode);
    getTarget().methodMerged(paramMethodNode);
    Annotations.setVisible(paramMethodNode, MixinMerged.class, new Object[] { "mixin", 
          getClassName(), "priority", 
          Integer.valueOf(getPriority()), "sessionId", this.sessionId });
  }
  
  public String toString() {
    return this.mixin.toString();
  }
  
  public MixinEnvironment getEnvironment() {
    return this.mixin.getParent().getEnvironment();
  }
  
  public boolean getOption(MixinEnvironment.Option paramOption) {
    return getEnvironment().getOption(paramOption);
  }
  
  public ClassNode getClassNode() {
    return this.classNode;
  }
  
  public String getClassName() {
    return this.mixin.getClassName();
  }
  
  public String getClassRef() {
    return this.mixin.getClassRef();
  }
  
  public TargetClassContext getTarget() {
    return this.targetClass;
  }
  
  public String getTargetClassRef() {
    return getTarget().getClassRef();
  }
  
  public ClassNode getTargetClassNode() {
    return getTarget().getClassNode();
  }
  
  public ClassInfo getTargetClassInfo() {
    return this.targetClassInfo;
  }
  
  protected ClassInfo getClassInfo() {
    return this.mixin.getClassInfo();
  }
  
  public ClassSignature getSignature() {
    return getClassInfo().getSignature();
  }
  
  public SourceMap.File getStratum() {
    return this.stratum;
  }
  
  public int getMinRequiredClassVersion() {
    return this.minRequiredClassVersion;
  }
  
  public int getDefaultRequiredInjections() {
    return this.mixin.getParent().getDefaultRequiredInjections();
  }
  
  public String getDefaultInjectorGroup() {
    return this.mixin.getParent().getDefaultInjectorGroup();
  }
  
  public int getMaxShiftByValue() {
    return this.mixin.getParent().getMaxShiftByValue();
  }
  
  public InjectorGroupInfo.Map getInjectorGroups() {
    return this.injectorGroups;
  }
  
  public boolean requireOverwriteAnnotations() {
    return this.mixin.getParent().requireOverwriteAnnotations();
  }
  
  public ClassInfo findRealType(ClassInfo paramClassInfo) {
    if (paramClassInfo == getClassInfo())
      return this.targetClassInfo; 
    ClassInfo classInfo = this.targetClassInfo.findCorrespondingType(paramClassInfo);
    if (classInfo == null)
      throw new InvalidMixinException(this, "Resolution error: unable to find corresponding type for " + paramClassInfo + " in hierarchy of " + this.targetClassInfo); 
    return classInfo;
  }
  
  public void transformMethod(MethodNode paramMethodNode) {
    validateMethod(paramMethodNode);
    transformDescriptor(paramMethodNode);
    transformLVT(paramMethodNode);
    this.stratum.applyOffset(paramMethodNode);
    AbstractInsnNode abstractInsnNode = null;
    for (ListIterator<AbstractInsnNode> listIterator = paramMethodNode.instructions.iterator(); listIterator.hasNext(); ) {
      AbstractInsnNode abstractInsnNode1 = listIterator.next();
      if (abstractInsnNode1 instanceof MethodInsnNode) {
        transformMethodRef(paramMethodNode, listIterator, (MemberRef)new MemberRef.Method((MethodInsnNode)abstractInsnNode1));
      } else if (abstractInsnNode1 instanceof FieldInsnNode) {
        transformFieldRef(paramMethodNode, listIterator, (MemberRef)new MemberRef.Field((FieldInsnNode)abstractInsnNode1));
        checkFinal(paramMethodNode, listIterator, (FieldInsnNode)abstractInsnNode1);
      } else if (abstractInsnNode1 instanceof TypeInsnNode) {
        transformTypeNode(paramMethodNode, listIterator, (TypeInsnNode)abstractInsnNode1, abstractInsnNode);
      } else if (abstractInsnNode1 instanceof LdcInsnNode) {
        transformConstantNode(paramMethodNode, listIterator, (LdcInsnNode)abstractInsnNode1);
      } else if (abstractInsnNode1 instanceof InvokeDynamicInsnNode) {
        transformInvokeDynamicNode(paramMethodNode, listIterator, (InvokeDynamicInsnNode)abstractInsnNode1);
      } 
      abstractInsnNode = abstractInsnNode1;
    } 
  }
  
  private void validateMethod(MethodNode paramMethodNode) {
    if (Annotations.getInvisible(paramMethodNode, SoftOverride.class) != null) {
      ClassInfo.Method method = this.targetClassInfo.findMethodInHierarchy(paramMethodNode.name, paramMethodNode.desc, ClassInfo.SearchType.SUPER_CLASSES_ONLY, ClassInfo.Traversal.SUPER);
      if (method == null || !method.isInjected())
        throw new InvalidMixinException(this, "Mixin method " + paramMethodNode.name + paramMethodNode.desc + " is tagged with @SoftOverride but no valid method was found in superclasses of " + 
            getTarget().getClassName()); 
    } 
  }
  
  private void transformLVT(MethodNode paramMethodNode) {
    if (paramMethodNode.localVariables == null)
      return; 
    for (LocalVariableNode localVariableNode : paramMethodNode.localVariables) {
      if (localVariableNode == null || localVariableNode.desc == null)
        continue; 
      localVariableNode.desc = transformSingleDescriptor(Type.getType(localVariableNode.desc));
    } 
  }
  
  private void transformMethodRef(MethodNode paramMethodNode, Iterator<AbstractInsnNode> paramIterator, MemberRef paramMemberRef) {
    transformDescriptor(paramMemberRef);
    if (paramMemberRef.getOwner().equals(getClassRef())) {
      paramMemberRef.setOwner(getTarget().getClassRef());
      ClassInfo.Method method = getClassInfo().findMethod(paramMemberRef.getName(), paramMemberRef.getDesc(), 10);
      if (method != null && method.isRenamed() && method.getOriginalName().equals(paramMemberRef.getName()) && method.isSynthetic())
        paramMemberRef.setName(method.getName()); 
      upgradeMethodRef(paramMethodNode, paramMemberRef, method);
    } else if (this.innerClasses.containsKey(paramMemberRef.getOwner())) {
      paramMemberRef.setOwner((String)this.innerClasses.get(paramMemberRef.getOwner()));
      paramMemberRef.setDesc(transformMethodDescriptor(paramMemberRef.getDesc()));
    } else if (this.detachedSuper || this.inheritsFromMixin) {
      if (paramMemberRef.getOpcode() == 183) {
        updateStaticBinding(paramMethodNode, paramMemberRef);
      } else if (paramMemberRef.getOpcode() == 182 && ClassInfo.forName(paramMemberRef.getOwner()).isMixin()) {
        updateDynamicBinding(paramMethodNode, paramMemberRef);
      } 
    } 
  }
  
  private void transformFieldRef(MethodNode paramMethodNode, Iterator<AbstractInsnNode> paramIterator, MemberRef paramMemberRef) {
    if ("super$".equals(paramMemberRef.getName()))
      if (paramMemberRef instanceof MemberRef.Field) {
        processImaginarySuper(paramMethodNode, ((MemberRef.Field)paramMemberRef).insn);
        paramIterator.remove();
      } else {
        throw new InvalidMixinException(this.mixin, "Cannot call imaginary super from method handle.");
      }  
    transformDescriptor(paramMemberRef);
    if (paramMemberRef.getOwner().equals(getClassRef())) {
      paramMemberRef.setOwner(getTarget().getClassRef());
      ClassInfo.Field field = getClassInfo().findField(paramMemberRef.getName(), paramMemberRef.getDesc(), 10);
      if (field != null && field.isRenamed() && field.getOriginalName().equals(paramMemberRef.getName()) && field.isStatic())
        paramMemberRef.setName(field.getName()); 
    } else {
      ClassInfo classInfo = ClassInfo.forName(paramMemberRef.getOwner());
      if (classInfo.isMixin()) {
        ClassInfo classInfo1 = this.targetClassInfo.findCorrespondingType(classInfo);
        paramMemberRef.setOwner((classInfo1 != null) ? classInfo1.getName() : getTarget().getClassRef());
      } 
    } 
  }
  
  private void checkFinal(MethodNode paramMethodNode, Iterator<AbstractInsnNode> paramIterator, FieldInsnNode paramFieldInsnNode) {
    if (!paramFieldInsnNode.owner.equals(getTarget().getClassRef()))
      return; 
    int i = paramFieldInsnNode.getOpcode();
    if (i == 180 || i == 178)
      return; 
    for (Map.Entry<FieldNode, ClassInfo.Field> entry : this.shadowFields.entrySet()) {
      FieldNode fieldNode = (FieldNode)entry.getKey();
      if (!fieldNode.desc.equals(paramFieldInsnNode.desc) || !fieldNode.name.equals(paramFieldInsnNode.name))
        continue; 
      ClassInfo.Field field = (ClassInfo.Field)entry.getValue();
      if (field.isDecoratedFinal())
        if (field.isDecoratedMutable()) {
          if (this.mixin.getParent().getEnvironment().getOption(MixinEnvironment.Option.DEBUG_VERBOSE))
            logger.warn("Write access to @Mutable @Final field {} in {}::{}", new Object[] { field, this.mixin, paramMethodNode.name }); 
        } else if ("<init>".equals(paramMethodNode.name) || "<clinit>".equals(paramMethodNode.name)) {
          logger.warn("@Final field {} in {} should be final", new Object[] { field, this.mixin });
        } else {
          logger.error("Write access detected to @Final field {} in {}::{}", new Object[] { field, this.mixin, paramMethodNode.name });
          if (this.mixin.getParent().getEnvironment().getOption(MixinEnvironment.Option.DEBUG_VERIFY))
            throw new InvalidMixinException(this.mixin, "Write access detected to @Final field " + field + " in " + this.mixin + "::" + paramMethodNode.name); 
        }  
      return;
    } 
  }
  
  private void transformTypeNode(MethodNode paramMethodNode, Iterator<AbstractInsnNode> paramIterator, TypeInsnNode paramTypeInsnNode, AbstractInsnNode paramAbstractInsnNode) {
    if (paramTypeInsnNode.getOpcode() == 192 && paramTypeInsnNode.desc
      .equals(getTarget().getClassRef()) && paramAbstractInsnNode
      .getOpcode() == 25 && ((VarInsnNode)paramAbstractInsnNode).var == 0) {
      paramIterator.remove();
      return;
    } 
    if (paramTypeInsnNode.desc.equals(getClassRef())) {
      paramTypeInsnNode.desc = getTarget().getClassRef();
    } else {
      String str = (String)this.innerClasses.get(paramTypeInsnNode.desc);
      if (str != null)
        paramTypeInsnNode.desc = str; 
    } 
    transformDescriptor(paramTypeInsnNode);
  }
  
  private void transformConstantNode(MethodNode paramMethodNode, Iterator<AbstractInsnNode> paramIterator, LdcInsnNode paramLdcInsnNode) {
    paramLdcInsnNode.cst = transformConstant(paramMethodNode, paramIterator, paramLdcInsnNode.cst);
  }
  
  private void transformInvokeDynamicNode(MethodNode paramMethodNode, Iterator<AbstractInsnNode> paramIterator, InvokeDynamicInsnNode paramInvokeDynamicInsnNode) {
    requireVersion(51);
    paramInvokeDynamicInsnNode.desc = transformMethodDescriptor(paramInvokeDynamicInsnNode.desc);
    paramInvokeDynamicInsnNode.bsm = transformHandle(paramMethodNode, paramIterator, paramInvokeDynamicInsnNode.bsm);
    for (byte b = 0; b < paramInvokeDynamicInsnNode.bsmArgs.length; b++)
      paramInvokeDynamicInsnNode.bsmArgs[b] = transformConstant(paramMethodNode, paramIterator, paramInvokeDynamicInsnNode.bsmArgs[b]); 
  }
  
  private Object transformConstant(MethodNode paramMethodNode, Iterator<AbstractInsnNode> paramIterator, Object paramObject) {
    if (paramObject instanceof Type) {
      Type type = (Type)paramObject;
      String str = transformDescriptor(type);
      if (!type.toString().equals(str))
        return Type.getType(str); 
      return paramObject;
    } 
    if (paramObject instanceof Handle)
      return transformHandle(paramMethodNode, paramIterator, (Handle)paramObject); 
    return paramObject;
  }
  
  private Handle transformHandle(MethodNode paramMethodNode, Iterator<AbstractInsnNode> paramIterator, Handle paramHandle) {
    MemberRef.Handle handle = new MemberRef.Handle(paramHandle);
    if (handle.isField()) {
      transformFieldRef(paramMethodNode, paramIterator, (MemberRef)handle);
    } else {
      transformMethodRef(paramMethodNode, paramIterator, (MemberRef)handle);
    } 
    return handle.getMethodHandle();
  }
  
  private void processImaginarySuper(MethodNode paramMethodNode, FieldInsnNode paramFieldInsnNode) {
    if (paramFieldInsnNode.getOpcode() != 180) {
      if ("<init>".equals(paramMethodNode.name))
        throw new InvalidMixinException(this, "Illegal imaginary super declaration: field " + paramFieldInsnNode.name + " must not specify an initialiser"); 
      throw new InvalidMixinException(this, "Illegal imaginary super access: found " + Bytecode.getOpcodeName(paramFieldInsnNode.getOpcode()) + " opcode in " + paramMethodNode.name + paramMethodNode.desc);
    } 
    if ((paramMethodNode.access & 0x2) != 0 || (paramMethodNode.access & 0x8) != 0)
      throw new InvalidMixinException(this, "Illegal imaginary super access: method " + paramMethodNode.name + paramMethodNode.desc + " is private or static"); 
    if (Annotations.getInvisible(paramMethodNode, SoftOverride.class) == null)
      throw new InvalidMixinException(this, "Illegal imaginary super access: method " + paramMethodNode.name + paramMethodNode.desc + " is not decorated with @SoftOverride"); 
    for (ListIterator<AbstractInsnNode> listIterator = paramMethodNode.instructions.iterator(paramMethodNode.instructions.indexOf((AbstractInsnNode)paramFieldInsnNode)); listIterator.hasNext(); ) {
      AbstractInsnNode abstractInsnNode = listIterator.next();
      if (abstractInsnNode instanceof MethodInsnNode) {
        MethodInsnNode methodInsnNode = (MethodInsnNode)abstractInsnNode;
        if (methodInsnNode.owner.equals(getClassRef()) && methodInsnNode.name.equals(paramMethodNode.name) && methodInsnNode.desc.equals(paramMethodNode.desc)) {
          methodInsnNode.setOpcode(183);
          updateStaticBinding(paramMethodNode, (MemberRef)new MemberRef.Method(methodInsnNode));
          return;
        } 
      } 
    } 
    throw new InvalidMixinException(this, "Illegal imaginary super access: could not find INVOKE for " + paramMethodNode.name + paramMethodNode.desc);
  }
  
  private void updateStaticBinding(MethodNode paramMethodNode, MemberRef paramMemberRef) {
    updateBinding(paramMethodNode, paramMemberRef, ClassInfo.Traversal.SUPER);
  }
  
  private void updateDynamicBinding(MethodNode paramMethodNode, MemberRef paramMemberRef) {
    updateBinding(paramMethodNode, paramMemberRef, ClassInfo.Traversal.ALL);
  }
  
  private void updateBinding(MethodNode paramMethodNode, MemberRef paramMemberRef, ClassInfo.Traversal paramTraversal) {
    if ("<init>".equals(paramMethodNode.name) || paramMemberRef
      .getOwner().equals(getTarget().getClassRef()) || 
      getTarget().getClassRef().startsWith("<"))
      return; 
    ClassInfo.Method method = this.targetClassInfo.findMethodInHierarchy(paramMemberRef.getName(), paramMemberRef.getDesc(), paramTraversal
        .getSearchType(), paramTraversal);
    if (method != null) {
      if (method.getOwner().isMixin())
        throw new InvalidMixinException(this, "Invalid " + paramMemberRef + " in " + this + " resolved " + method.getOwner() + " but is mixin."); 
      paramMemberRef.setOwner(method.getImplementor().getName());
    } else if (ClassInfo.forName(paramMemberRef.getOwner()).isMixin()) {
      throw new MixinTransformerError("Error resolving " + paramMemberRef + " in " + this);
    } 
  }
  
  public void transformDescriptor(FieldNode paramFieldNode) {
    if (!this.inheritsFromMixin && this.innerClasses.size() == 0)
      return; 
    paramFieldNode.desc = transformSingleDescriptor(paramFieldNode.desc, false);
  }
  
  public void transformDescriptor(MethodNode paramMethodNode) {
    if (!this.inheritsFromMixin && this.innerClasses.size() == 0)
      return; 
    paramMethodNode.desc = transformMethodDescriptor(paramMethodNode.desc);
  }
  
  public void transformDescriptor(MemberRef paramMemberRef) {
    if (!this.inheritsFromMixin && this.innerClasses.size() == 0)
      return; 
    if (paramMemberRef.isField()) {
      paramMemberRef.setDesc(transformSingleDescriptor(paramMemberRef.getDesc(), false));
    } else {
      paramMemberRef.setDesc(transformMethodDescriptor(paramMemberRef.getDesc()));
    } 
  }
  
  public void transformDescriptor(TypeInsnNode paramTypeInsnNode) {
    if (!this.inheritsFromMixin && this.innerClasses.size() == 0)
      return; 
    paramTypeInsnNode.desc = transformSingleDescriptor(paramTypeInsnNode.desc, true);
  }
  
  private String transformDescriptor(Type paramType) {
    if (paramType.getSort() == 11)
      return transformMethodDescriptor(paramType.getDescriptor()); 
    return transformSingleDescriptor(paramType);
  }
  
  private String transformSingleDescriptor(Type paramType) {
    if (paramType.getSort() < 9)
      return paramType.toString(); 
    return transformSingleDescriptor(paramType.toString(), false);
  }
  
  private String transformSingleDescriptor(String paramString, boolean paramBoolean) {
    String str1 = paramString;
    while (str1.startsWith("[") || str1.startsWith("L")) {
      if (str1.startsWith("[")) {
        str1 = str1.substring(1);
        continue;
      } 
      str1 = str1.substring(1, str1.indexOf(";"));
      paramBoolean = true;
    } 
    if (!paramBoolean)
      return paramString; 
    String str2 = (String)this.innerClasses.get(str1);
    if (str2 != null)
      return paramString.replace(str1, str2); 
    if (this.innerClasses.inverse().containsKey(str1))
      return paramString; 
    ClassInfo classInfo = ClassInfo.forName(str1);
    if (!classInfo.isMixin())
      return paramString; 
    return paramString.replace(str1, findRealType(classInfo).toString());
  }
  
  private String transformMethodDescriptor(String paramString) {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append('(');
    for (Type type : Type.getArgumentTypes(paramString))
      stringBuilder.append(transformSingleDescriptor(type)); 
    return stringBuilder.append(')').append(transformSingleDescriptor(Type.getReturnType(paramString))).toString();
  }
  
  public Target getTargetMethod(MethodNode paramMethodNode) {
    return getTarget().getTargetMethod(paramMethodNode);
  }
  
  MethodNode findMethod(MethodNode paramMethodNode, AnnotationNode paramAnnotationNode) {
    LinkedList<String> linkedList = new LinkedList();
    linkedList.add(paramMethodNode.name);
    if (paramAnnotationNode != null) {
      List<? extends String> list = (List)Annotations.getValue(paramAnnotationNode, "aliases");
      if (list != null)
        linkedList.addAll(list); 
    } 
    return getTarget().findMethod(linkedList, paramMethodNode.desc);
  }
  
  MethodNode findRemappedMethod(MethodNode paramMethodNode) {
    RemapperChain remapperChain = getEnvironment().getRemappers();
    String str = remapperChain.mapMethodName(getTarget().getClassRef(), paramMethodNode.name, paramMethodNode.desc);
    if (str.equals(paramMethodNode.name))
      return null; 
    LinkedList<String> linkedList = new LinkedList();
    linkedList.add(str);
    return getTarget().findAliasedMethod(linkedList, paramMethodNode.desc);
  }
  
  FieldNode findField(FieldNode paramFieldNode, AnnotationNode paramAnnotationNode) {
    LinkedList<String> linkedList = new LinkedList();
    linkedList.add(paramFieldNode.name);
    if (paramAnnotationNode != null) {
      List<? extends String> list = (List)Annotations.getValue(paramAnnotationNode, "aliases");
      if (list != null)
        linkedList.addAll(list); 
    } 
    return getTarget().findAliasedField(linkedList, paramFieldNode.desc);
  }
  
  FieldNode findRemappedField(FieldNode paramFieldNode) {
    RemapperChain remapperChain = getEnvironment().getRemappers();
    String str = remapperChain.mapFieldName(getTarget().getClassRef(), paramFieldNode.name, paramFieldNode.desc);
    if (str.equals(paramFieldNode.name))
      return null; 
    LinkedList<String> linkedList = new LinkedList();
    linkedList.add(str);
    return getTarget().findAliasedField(linkedList, paramFieldNode.desc);
  }
  
  protected void requireVersion(int paramInt) {
    this.minRequiredClassVersion = Math.max(this.minRequiredClassVersion, paramInt);
    if (paramInt > MixinEnvironment.getCompatibilityLevel().classVersion())
      throw new InvalidMixinException(this, "Unsupported mixin class version " + paramInt); 
  }
  
  public Extensions getExtensions() {
    return this.targetClass.getExtensions();
  }
  
  public IMixinInfo getMixin() {
    return this.mixin;
  }
  
  MixinInfo getInfo() {
    return this.mixin;
  }
  
  public int getPriority() {
    return this.mixin.getPriority();
  }
  
  public Set<String> getInterfaces() {
    return this.mixin.getInterfaces();
  }
  
  public Collection<MethodNode> getShadowMethods() {
    return this.shadowMethods;
  }
  
  public List<MethodNode> getMethods() {
    return this.classNode.methods;
  }
  
  public Set<Map.Entry<FieldNode, ClassInfo.Field>> getShadowFields() {
    return this.shadowFields.entrySet();
  }
  
  public List<FieldNode> getFields() {
    return this.classNode.fields;
  }
  
  public Level getLoggingLevel() {
    return this.mixin.getLoggingLevel();
  }
  
  public boolean shouldSetSourceFile() {
    return this.mixin.getParent().shouldSetSourceFile();
  }
  
  public String getSourceFile() {
    return this.classNode.sourceFile;
  }
  
  public IReferenceMapper getReferenceMapper() {
    return this.mixin.getParent().getReferenceMapper();
  }
  
  public void preApply(String paramString, ClassNode paramClassNode) {
    this.mixin.preApply(paramString, paramClassNode);
  }
  
  public void postApply(String paramString, ClassNode paramClassNode) {
    try {
      this.injectorGroups.validateAll();
    } catch (InjectionValidationException injectionValidationException) {
      InjectorGroupInfo injectorGroupInfo = injectionValidationException.getGroup();
      throw new InjectionError(
          String.format("Critical injection failure: Callback group %s in %s failed injection check: %s", new Object[] { injectorGroupInfo, this.mixin, injectionValidationException.getMessage() }));
    } 
    this.mixin.postApply(paramString, paramClassNode);
  }
  
  public String getUniqueName(MethodNode paramMethodNode, boolean paramBoolean) {
    return getTarget().getUniqueName(paramMethodNode, paramBoolean);
  }
  
  public String getUniqueName(FieldNode paramFieldNode) {
    return getTarget().getUniqueName(paramFieldNode);
  }
  
  public void prepareInjections() {
    this.injectors.clear();
    for (MethodNode methodNode : this.mergedMethods) {
      InjectionInfo injectionInfo = InjectionInfo.parse(this, methodNode);
      if (injectionInfo == null)
        continue; 
      if (injectionInfo.isValid()) {
        injectionInfo.prepare();
        this.injectors.add(injectionInfo);
      } 
      methodNode.visibleAnnotations.remove(injectionInfo.getAnnotation());
    } 
  }
  
  public void applyInjections() {
    for (InjectionInfo injectionInfo : this.injectors)
      injectionInfo.inject(); 
    for (InjectionInfo injectionInfo : this.injectors)
      injectionInfo.postInject(); 
    this.injectors.clear();
  }
  
  public List<MethodNode> generateAccessors() {
    for (AccessorInfo accessorInfo : this.accessors)
      accessorInfo.locate(); 
    ArrayList<MethodNode> arrayList = new ArrayList();
    for (AccessorInfo accessorInfo : this.accessors) {
      MethodNode methodNode = accessorInfo.generate();
      getTarget().addMixinMethod(methodNode);
      arrayList.add(methodNode);
    } 
    return arrayList;
  }
}
