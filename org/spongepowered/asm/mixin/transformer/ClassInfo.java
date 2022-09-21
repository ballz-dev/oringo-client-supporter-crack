package org.spongepowered.asm.mixin.transformer;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.lib.tree.AbstractInsnNode;
import org.spongepowered.asm.lib.tree.ClassNode;
import org.spongepowered.asm.lib.tree.FieldInsnNode;
import org.spongepowered.asm.lib.tree.FieldNode;
import org.spongepowered.asm.lib.tree.FrameNode;
import org.spongepowered.asm.lib.tree.MethodInsnNode;
import org.spongepowered.asm.lib.tree.MethodNode;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.service.MixinService;
import org.spongepowered.asm.util.Annotations;
import org.spongepowered.asm.util.ClassSignature;
import org.spongepowered.asm.util.perf.Profiler;

public final class ClassInfo {
  public enum SearchType {
    SUPER_CLASSES_ONLY(null, false, (ClassInfo.Traversal)SearchType.SUPER_CLASSES_ONLY),
    ALL_CLASSES;
    
    static {
      $VALUES = new SearchType[] { ALL_CLASSES, SUPER_CLASSES_ONLY };
    }
  }
  
  public enum Traversal {
    NONE(null, false, (Traversal)ClassInfo.SearchType.SUPER_CLASSES_ONLY),
    ALL,
    IMMEDIATE,
    SUPER;
    
    private final ClassInfo.SearchType searchType;
    
    private final Traversal next;
    
    private final boolean traverse;
    
    static {
    
    }
    
    Traversal(Traversal param1Traversal, boolean param1Boolean, ClassInfo.SearchType param1SearchType) {
      this.next = (param1Traversal != null) ? param1Traversal : this;
      this.traverse = param1Boolean;
      this.searchType = param1SearchType;
    }
    
    public Traversal next() {
      return this.next;
    }
    
    public boolean canTraverse() {
      return this.traverse;
    }
    
    public ClassInfo.SearchType getSearchType() {
      return this.searchType;
    }
  }
  
  public static class FrameData {
    private static final String[] FRAMETYPES = new String[] { "NEW", "FULL", "APPEND", "CHOP", "SAME", "SAME1" };
    
    public final int index;
    
    public final int locals;
    
    public final int type;
    
    FrameData(int param1Int1, int param1Int2, int param1Int3) {
      this.index = param1Int1;
      this.type = param1Int2;
      this.locals = param1Int3;
    }
    
    FrameData(int param1Int, FrameNode param1FrameNode) {
      this.index = param1Int;
      this.type = param1FrameNode.type;
      this.locals = (param1FrameNode.local != null) ? param1FrameNode.local.size() : 0;
    }
    
    public String toString() {
      return String.format("FrameData[index=%d, type=%s, locals=%d]", new Object[] { Integer.valueOf(this.index), FRAMETYPES[this.type + 1], Integer.valueOf(this.locals) });
    }
  }
  
  static abstract class Member {
    private final Type type;
    
    private String currentDesc;
    
    private boolean decoratedMutable;
    
    private String currentName;
    
    private boolean unique;
    
    private final boolean isInjected;
    
    private boolean decoratedFinal;
    
    private final String memberDesc;
    
    private final String memberName;
    
    private final int modifiers;
    
    enum Type {
      METHOD, FIELD;
      
      static {
      
      }
    }
    
    protected Member(Member param1Member) {
      this(param1Member.type, param1Member.memberName, param1Member.memberDesc, param1Member.modifiers, param1Member.isInjected);
      this.currentName = param1Member.currentName;
      this.currentDesc = param1Member.currentDesc;
      this.unique = param1Member.unique;
    }
    
    protected Member(Type param1Type, String param1String1, String param1String2, int param1Int) {
      this(param1Type, param1String1, param1String2, param1Int, false);
    }
    
    protected Member(Type param1Type, String param1String1, String param1String2, int param1Int, boolean param1Boolean) {
      this.type = param1Type;
      this.memberName = param1String1;
      this.memberDesc = param1String2;
      this.isInjected = param1Boolean;
      this.currentName = param1String1;
      this.currentDesc = param1String2;
      this.modifiers = param1Int;
    }
    
    public String getOriginalName() {
      return this.memberName;
    }
    
    public String getName() {
      return this.currentName;
    }
    
    public String getOriginalDesc() {
      return this.memberDesc;
    }
    
    public String getDesc() {
      return this.currentDesc;
    }
    
    public boolean isInjected() {
      return this.isInjected;
    }
    
    public boolean isRenamed() {
      return !this.currentName.equals(this.memberName);
    }
    
    public boolean isRemapped() {
      return !this.currentDesc.equals(this.memberDesc);
    }
    
    public boolean isPrivate() {
      return ((this.modifiers & 0x2) != 0);
    }
    
    public boolean isStatic() {
      return ((this.modifiers & 0x8) != 0);
    }
    
    public boolean isAbstract() {
      return ((this.modifiers & 0x400) != 0);
    }
    
    public boolean isFinal() {
      return ((this.modifiers & 0x10) != 0);
    }
    
    public boolean isSynthetic() {
      return ((this.modifiers & 0x1000) != 0);
    }
    
    public boolean isUnique() {
      return this.unique;
    }
    
    public void setUnique(boolean param1Boolean) {
      this.unique = param1Boolean;
    }
    
    public boolean isDecoratedFinal() {
      return this.decoratedFinal;
    }
    
    public boolean isDecoratedMutable() {
      return this.decoratedMutable;
    }
    
    public void setDecoratedFinal(boolean param1Boolean1, boolean param1Boolean2) {
      this.decoratedFinal = param1Boolean1;
      this.decoratedMutable = param1Boolean2;
    }
    
    public boolean matchesFlags(int param1Int) {
      return (((this.modifiers ^ 0xFFFFFFFF | param1Int & 0x2) & 0x2) != 0 && ((this.modifiers ^ 0xFFFFFFFF | param1Int & 0x8) & 0x8) != 0);
    }
    
    public ClassInfo getImplementor() {
      return getOwner();
    }
    
    public int getAccess() {
      return this.modifiers;
    }
    
    public String renameTo(String param1String) {
      this.currentName = param1String;
      return param1String;
    }
    
    public String remapTo(String param1String) {
      this.currentDesc = param1String;
      return param1String;
    }
    
    public boolean equals(String param1String1, String param1String2) {
      return ((this.memberName.equals(param1String1) || this.currentName.equals(param1String1)) && (this.memberDesc
        .equals(param1String2) || this.currentDesc.equals(param1String2)));
    }
    
    public boolean equals(Object param1Object) {
      if (!(param1Object instanceof Member))
        return false; 
      Member member = (Member)param1Object;
      return ((member.memberName.equals(this.memberName) || member.currentName.equals(this.currentName)) && (member.memberDesc
        .equals(this.memberDesc) || member.currentDesc.equals(this.currentDesc)));
    }
    
    public int hashCode() {
      return toString().hashCode();
    }
    
    public String toString() {
      return String.format(getDisplayFormat(), new Object[] { this.memberName, this.memberDesc });
    }
    
    protected String getDisplayFormat() {
      return "%s%s";
    }
    
    public abstract ClassInfo getOwner();
  }
  
  enum Type {
    METHOD, FIELD;
    
    static {
    
    }
  }
  
  public class Method extends Member {
    private boolean isAccessor;
    
    private final List<ClassInfo.FrameData> frames;
    
    public Method(ClassInfo.Member param1Member) {
      super(param1Member);
      this.frames = (param1Member instanceof Method) ? ((Method)param1Member).frames : null;
    }
    
    public Method(MethodNode param1MethodNode) {
      this(param1MethodNode, false);
      setUnique((Annotations.getVisible(param1MethodNode, Unique.class) != null));
      this.isAccessor = (Annotations.getSingleVisible(param1MethodNode, new Class[] { Accessor.class, Invoker.class }) != null);
    }
    
    public Method(MethodNode param1MethodNode, boolean param1Boolean) {
      super(ClassInfo.Member.Type.METHOD, param1MethodNode.name, param1MethodNode.desc, param1MethodNode.access, param1Boolean);
      this.frames = gatherFrames(param1MethodNode);
      setUnique((Annotations.getVisible(param1MethodNode, Unique.class) != null));
      this.isAccessor = (Annotations.getSingleVisible(param1MethodNode, new Class[] { Accessor.class, Invoker.class }) != null);
    }
    
    public Method(String param1String1, String param1String2) {
      super(ClassInfo.Member.Type.METHOD, param1String1, param1String2, 1, false);
      this.frames = null;
    }
    
    public Method(String param1String1, String param1String2, int param1Int) {
      super(ClassInfo.Member.Type.METHOD, param1String1, param1String2, param1Int, false);
      this.frames = null;
    }
    
    public Method(String param1String1, String param1String2, int param1Int, boolean param1Boolean) {
      super(ClassInfo.Member.Type.METHOD, param1String1, param1String2, param1Int, param1Boolean);
      this.frames = null;
    }
    
    private List<ClassInfo.FrameData> gatherFrames(MethodNode param1MethodNode) {
      ArrayList<ClassInfo.FrameData> arrayList = new ArrayList();
      for (ListIterator<AbstractInsnNode> listIterator = param1MethodNode.instructions.iterator(); listIterator.hasNext(); ) {
        AbstractInsnNode abstractInsnNode = listIterator.next();
        if (abstractInsnNode instanceof FrameNode)
          arrayList.add(new ClassInfo.FrameData(param1MethodNode.instructions.indexOf(abstractInsnNode), (FrameNode)abstractInsnNode)); 
      } 
      return arrayList;
    }
    
    public List<ClassInfo.FrameData> getFrames() {
      return this.frames;
    }
    
    public ClassInfo getOwner() {
      return ClassInfo.this;
    }
    
    public boolean isAccessor() {
      return this.isAccessor;
    }
    
    public boolean equals(Object param1Object) {
      if (!(param1Object instanceof Method))
        return false; 
      return super.equals(param1Object);
    }
  }
  
  public class InterfaceMethod extends Method {
    private final ClassInfo owner;
    
    public InterfaceMethod(ClassInfo.Member param1Member) {
      super(param1Member);
      this.owner = param1Member.getOwner();
    }
    
    public ClassInfo getOwner() {
      return this.owner;
    }
    
    public ClassInfo getImplementor() {
      return ClassInfo.this;
    }
  }
  
  class Field extends Member {
    public Field(ClassInfo.Member param1Member) {
      super(param1Member);
    }
    
    public Field(FieldNode param1FieldNode) {
      this(param1FieldNode, false);
    }
    
    public Field(FieldNode param1FieldNode, boolean param1Boolean) {
      super(ClassInfo.Member.Type.FIELD, param1FieldNode.name, param1FieldNode.desc, param1FieldNode.access, param1Boolean);
      setUnique((Annotations.getVisible(param1FieldNode, Unique.class) != null));
      if (Annotations.getVisible(param1FieldNode, Shadow.class) != null) {
        boolean bool1 = (Annotations.getVisible(param1FieldNode, Final.class) != null) ? true : false;
        boolean bool2 = (Annotations.getVisible(param1FieldNode, Mutable.class) != null) ? true : false;
        setDecoratedFinal(bool1, bool2);
      } 
    }
    
    public Field(String param1String1, String param1String2, int param1Int) {
      super(ClassInfo.Member.Type.FIELD, param1String1, param1String2, param1Int, false);
    }
    
    public Field(String param1String1, String param1String2, int param1Int, boolean param1Boolean) {
      super(ClassInfo.Member.Type.FIELD, param1String1, param1String2, param1Int, param1Boolean);
    }
    
    public ClassInfo getOwner() {
      return ClassInfo.this;
    }
    
    public boolean equals(Object param1Object) {
      if (!(param1Object instanceof Field))
        return false; 
      return super.equals(param1Object);
    }
    
    protected String getDisplayFormat() {
      return "%s:%s";
    }
  }
  
  private static final Logger logger = LogManager.getLogger("mixin");
  
  static {
    profiler = MixinEnvironment.getProfiler();
    cache = new HashMap<String, ClassInfo>();
    OBJECT = new ClassInfo();
    cache.put("java/lang/Object", OBJECT);
  }
  
  private final Set<MixinInfo> mixins = new HashSet<MixinInfo>();
  
  private final Map<ClassInfo, ClassInfo> correspondingTypes = new HashMap<ClassInfo, ClassInfo>();
  
  private final boolean isInterface;
  
  public static final int INCLUDE_STATIC = 8;
  
  private final String outerName;
  
  private final boolean isMixin;
  
  private final Set<Field> fields;
  
  private final MixinInfo mixin;
  
  private static final Profiler profiler;
  
  public static final int INCLUDE_ALL = 10;
  
  private static final String JAVA_LANG_OBJECT = "java/lang/Object";
  
  private static final ClassInfo OBJECT;
  
  private final String superName;
  
  private static final Map<String, ClassInfo> cache;
  
  private final int access;
  
  private final Set<Method> methods;
  
  private final MethodMapper methodMapper;
  
  private final boolean isProbablyStatic;
  
  private ClassInfo outerClass;
  
  private ClassSignature signature;
  
  private final String name;
  
  private ClassInfo superClass;
  
  public static final int INCLUDE_PRIVATE = 2;
  
  private final Set<String> interfaces;
  
  private ClassInfo() {
    this.name = "java/lang/Object";
    this.superName = null;
    this.outerName = null;
    this.isProbablyStatic = true;
    this.methods = (Set<Method>)ImmutableSet.of(new Method("getClass", "()Ljava/lang/Class;"), new Method("hashCode", "()I"), new Method("equals", "(Ljava/lang/Object;)Z"), new Method("clone", "()Ljava/lang/Object;"), new Method("toString", "()Ljava/lang/String;"), new Method("notify", "()V"), (Object[])new Method[] { new Method("notifyAll", "()V"), new Method("wait", "(J)V"), new Method("wait", "(JI)V"), new Method("wait", "()V"), new Method("finalize", "()V") });
    this.fields = Collections.emptySet();
    this.isInterface = false;
    this.interfaces = Collections.emptySet();
    this.access = 1;
    this.isMixin = false;
    this.mixin = null;
    this.methodMapper = null;
  }
  
  private ClassInfo(ClassNode paramClassNode) {
    Profiler.Section section = profiler.begin(1, "class.meta");
    try {
      this.name = paramClassNode.name;
      this.superName = (paramClassNode.superName != null) ? paramClassNode.superName : "java/lang/Object";
      this.methods = new HashSet<Method>();
      this.fields = new HashSet<Field>();
      this.isInterface = ((paramClassNode.access & 0x200) != 0);
      this.interfaces = new HashSet<String>();
      this.access = paramClassNode.access;
      this.isMixin = paramClassNode instanceof MixinInfo.MixinClassNode;
      this.mixin = this.isMixin ? ((MixinInfo.MixinClassNode)paramClassNode).getMixin() : null;
      this.interfaces.addAll(paramClassNode.interfaces);
      for (MethodNode methodNode : paramClassNode.methods)
        addMethod(methodNode, this.isMixin); 
      boolean bool = true;
      String str = paramClassNode.outerClass;
      for (FieldNode fieldNode : paramClassNode.fields) {
        if ((fieldNode.access & 0x1000) != 0 && 
          fieldNode.name.startsWith("this$")) {
          bool = false;
          if (str == null) {
            str = fieldNode.desc;
            if (str != null && str.startsWith("L"))
              str = str.substring(1, str.length() - 1); 
          } 
        } 
        this.fields.add(new Field(fieldNode, this.isMixin));
      } 
      this.isProbablyStatic = bool;
      this.outerName = str;
      this.methodMapper = new MethodMapper(MixinEnvironment.getCurrentEnvironment(), this);
      this.signature = ClassSignature.ofLazy(paramClassNode);
    } finally {
      section.end();
    } 
  }
  
  void addInterface(String paramString) {
    this.interfaces.add(paramString);
    getSignature().addInterface(paramString);
  }
  
  void addMethod(MethodNode paramMethodNode) {
    addMethod(paramMethodNode, true);
  }
  
  private void addMethod(MethodNode paramMethodNode, boolean paramBoolean) {
    if (!paramMethodNode.name.startsWith("<"))
      this.methods.add(new Method(paramMethodNode, paramBoolean)); 
  }
  
  void addMixin(MixinInfo paramMixinInfo) {
    if (this.isMixin)
      throw new IllegalArgumentException("Cannot add target " + this.name + " for " + paramMixinInfo.getClassName() + " because the target is a mixin"); 
    this.mixins.add(paramMixinInfo);
  }
  
  public Set<MixinInfo> getMixins() {
    return Collections.unmodifiableSet(this.mixins);
  }
  
  public boolean isMixin() {
    return this.isMixin;
  }
  
  public boolean isPublic() {
    return ((this.access & 0x1) != 0);
  }
  
  public boolean isAbstract() {
    return ((this.access & 0x400) != 0);
  }
  
  public boolean isSynthetic() {
    return ((this.access & 0x1000) != 0);
  }
  
  public boolean isProbablyStatic() {
    return this.isProbablyStatic;
  }
  
  public boolean isInner() {
    return (this.outerName != null);
  }
  
  public boolean isInterface() {
    return this.isInterface;
  }
  
  public Set<String> getInterfaces() {
    return Collections.unmodifiableSet(this.interfaces);
  }
  
  public String toString() {
    return this.name;
  }
  
  public MethodMapper getMethodMapper() {
    return this.methodMapper;
  }
  
  public int getAccess() {
    return this.access;
  }
  
  public String getName() {
    return this.name;
  }
  
  public String getClassName() {
    return this.name.replace('/', '.');
  }
  
  public String getSuperName() {
    return this.superName;
  }
  
  public ClassInfo getSuperClass() {
    if (this.superClass == null && this.superName != null)
      this.superClass = forName(this.superName); 
    return this.superClass;
  }
  
  public String getOuterName() {
    return this.outerName;
  }
  
  public ClassInfo getOuterClass() {
    if (this.outerClass == null && this.outerName != null)
      this.outerClass = forName(this.outerName); 
    return this.outerClass;
  }
  
  public ClassSignature getSignature() {
    return this.signature.wake();
  }
  
  List<ClassInfo> getTargets() {
    if (this.mixin != null) {
      ArrayList<ClassInfo> arrayList = new ArrayList();
      arrayList.add(this);
      arrayList.addAll(this.mixin.getTargets());
      return arrayList;
    } 
    return (List<ClassInfo>)ImmutableList.of(this);
  }
  
  public Set<Method> getMethods() {
    return Collections.unmodifiableSet(this.methods);
  }
  
  public Set<Method> getInterfaceMethods(boolean paramBoolean) {
    HashSet<Method> hashSet = new HashSet();
    ClassInfo classInfo = addMethodsRecursive(hashSet, paramBoolean);
    if (!this.isInterface)
      while (classInfo != null && classInfo != OBJECT)
        classInfo = classInfo.addMethodsRecursive(hashSet, paramBoolean);  
    for (Iterator<Method> iterator = hashSet.iterator(); iterator.hasNext();) {
      if (!((Method)iterator.next()).isAbstract())
        iterator.remove(); 
    } 
    return Collections.unmodifiableSet(hashSet);
  }
  
  private ClassInfo addMethodsRecursive(Set<Method> paramSet, boolean paramBoolean) {
    if (this.isInterface) {
      for (Method method : this.methods) {
        if (!method.isAbstract())
          paramSet.remove(method); 
        paramSet.add(method);
      } 
    } else if (!this.isMixin && paramBoolean) {
      for (MixinInfo mixinInfo : this.mixins)
        mixinInfo.getClassInfo().addMethodsRecursive(paramSet, paramBoolean); 
    } 
    for (String str : this.interfaces)
      forName(str).addMethodsRecursive(paramSet, paramBoolean); 
    return getSuperClass();
  }
  
  public boolean hasSuperClass(String paramString) {
    return hasSuperClass(paramString, Traversal.NONE);
  }
  
  public boolean hasSuperClass(String paramString, Traversal paramTraversal) {
    if ("java/lang/Object".equals(paramString))
      return true; 
    return (findSuperClass(paramString, paramTraversal) != null);
  }
  
  public boolean hasSuperClass(ClassInfo paramClassInfo) {
    return hasSuperClass(paramClassInfo, Traversal.NONE, false);
  }
  
  public boolean hasSuperClass(ClassInfo paramClassInfo, Traversal paramTraversal) {
    return hasSuperClass(paramClassInfo, paramTraversal, false);
  }
  
  public boolean hasSuperClass(ClassInfo paramClassInfo, Traversal paramTraversal, boolean paramBoolean) {
    if (OBJECT == paramClassInfo)
      return true; 
    return (findSuperClass(paramClassInfo.name, paramTraversal, paramBoolean) != null);
  }
  
  public ClassInfo findSuperClass(String paramString) {
    return findSuperClass(paramString, Traversal.NONE);
  }
  
  public ClassInfo findSuperClass(String paramString, Traversal paramTraversal) {
    return findSuperClass(paramString, paramTraversal, false, new HashSet<String>());
  }
  
  public ClassInfo findSuperClass(String paramString, Traversal paramTraversal, boolean paramBoolean) {
    if (OBJECT.name.equals(paramString))
      return null; 
    return findSuperClass(paramString, paramTraversal, paramBoolean, new HashSet<String>());
  }
  
  private ClassInfo findSuperClass(String paramString, Traversal paramTraversal, boolean paramBoolean, Set<String> paramSet) {
    ClassInfo classInfo = getSuperClass();
    if (classInfo != null)
      for (ClassInfo classInfo1 : classInfo.getTargets()) {
        if (paramString.equals(classInfo1.getName()))
          return classInfo; 
        ClassInfo classInfo2 = classInfo1.findSuperClass(paramString, paramTraversal.next(), paramBoolean, paramSet);
        if (classInfo2 != null)
          return classInfo2; 
      }  
    if (paramBoolean) {
      ClassInfo classInfo1 = findInterface(paramString);
      if (classInfo1 != null)
        return classInfo1; 
    } 
    if (paramTraversal.canTraverse())
      for (MixinInfo mixinInfo : this.mixins) {
        String str = mixinInfo.getClassName();
        if (paramSet.contains(str))
          continue; 
        paramSet.add(str);
        ClassInfo classInfo1 = mixinInfo.getClassInfo();
        if (paramString.equals(classInfo1.getName()))
          return classInfo1; 
        ClassInfo classInfo2 = classInfo1.findSuperClass(paramString, Traversal.ALL, paramBoolean, paramSet);
        if (classInfo2 != null)
          return classInfo2; 
      }  
    return null;
  }
  
  private ClassInfo findInterface(String paramString) {
    for (String str : getInterfaces()) {
      ClassInfo classInfo1 = forName(str);
      if (paramString.equals(str))
        return classInfo1; 
      ClassInfo classInfo2 = classInfo1.findInterface(paramString);
      if (classInfo2 != null)
        return classInfo2; 
    } 
    return null;
  }
  
  ClassInfo findCorrespondingType(ClassInfo paramClassInfo) {
    if (paramClassInfo == null || !paramClassInfo.isMixin || this.isMixin)
      return null; 
    ClassInfo classInfo = this.correspondingTypes.get(paramClassInfo);
    if (classInfo == null) {
      classInfo = findSuperTypeForMixin(paramClassInfo);
      this.correspondingTypes.put(paramClassInfo, classInfo);
    } 
    return classInfo;
  }
  
  private ClassInfo findSuperTypeForMixin(ClassInfo paramClassInfo) {
    ClassInfo classInfo = this;
    while (classInfo != null && classInfo != OBJECT) {
      for (MixinInfo mixinInfo : classInfo.mixins) {
        if (mixinInfo.getClassInfo().equals(paramClassInfo))
          return classInfo; 
      } 
      classInfo = classInfo.getSuperClass();
    } 
    return null;
  }
  
  public boolean hasMixinInHierarchy() {
    if (!this.isMixin)
      return false; 
    ClassInfo classInfo = getSuperClass();
    while (classInfo != null && classInfo != OBJECT) {
      if (classInfo.isMixin)
        return true; 
      classInfo = classInfo.getSuperClass();
    } 
    return false;
  }
  
  public boolean hasMixinTargetInHierarchy() {
    if (this.isMixin)
      return false; 
    ClassInfo classInfo = getSuperClass();
    while (classInfo != null && classInfo != OBJECT) {
      if (classInfo.mixins.size() > 0)
        return true; 
      classInfo = classInfo.getSuperClass();
    } 
    return false;
  }
  
  public Method findMethodInHierarchy(MethodNode paramMethodNode, SearchType paramSearchType) {
    return findMethodInHierarchy(paramMethodNode.name, paramMethodNode.desc, paramSearchType, Traversal.NONE);
  }
  
  public Method findMethodInHierarchy(MethodNode paramMethodNode, SearchType paramSearchType, int paramInt) {
    return findMethodInHierarchy(paramMethodNode.name, paramMethodNode.desc, paramSearchType, Traversal.NONE, paramInt);
  }
  
  public Method findMethodInHierarchy(MethodInsnNode paramMethodInsnNode, SearchType paramSearchType) {
    return findMethodInHierarchy(paramMethodInsnNode.name, paramMethodInsnNode.desc, paramSearchType, Traversal.NONE);
  }
  
  public Method findMethodInHierarchy(MethodInsnNode paramMethodInsnNode, SearchType paramSearchType, int paramInt) {
    return findMethodInHierarchy(paramMethodInsnNode.name, paramMethodInsnNode.desc, paramSearchType, Traversal.NONE, paramInt);
  }
  
  public Method findMethodInHierarchy(String paramString1, String paramString2, SearchType paramSearchType) {
    return findMethodInHierarchy(paramString1, paramString2, paramSearchType, Traversal.NONE);
  }
  
  public Method findMethodInHierarchy(String paramString1, String paramString2, SearchType paramSearchType, Traversal paramTraversal) {
    return findMethodInHierarchy(paramString1, paramString2, paramSearchType, paramTraversal, 0);
  }
  
  public Method findMethodInHierarchy(String paramString1, String paramString2, SearchType paramSearchType, Traversal paramTraversal, int paramInt) {
    return findInHierarchy(paramString1, paramString2, paramSearchType, paramTraversal, paramInt, Member.Type.METHOD);
  }
  
  public Field findFieldInHierarchy(FieldNode paramFieldNode, SearchType paramSearchType) {
    return findFieldInHierarchy(paramFieldNode.name, paramFieldNode.desc, paramSearchType, Traversal.NONE);
  }
  
  public Field findFieldInHierarchy(FieldNode paramFieldNode, SearchType paramSearchType, int paramInt) {
    return findFieldInHierarchy(paramFieldNode.name, paramFieldNode.desc, paramSearchType, Traversal.NONE, paramInt);
  }
  
  public Field findFieldInHierarchy(FieldInsnNode paramFieldInsnNode, SearchType paramSearchType) {
    return findFieldInHierarchy(paramFieldInsnNode.name, paramFieldInsnNode.desc, paramSearchType, Traversal.NONE);
  }
  
  public Field findFieldInHierarchy(FieldInsnNode paramFieldInsnNode, SearchType paramSearchType, int paramInt) {
    return findFieldInHierarchy(paramFieldInsnNode.name, paramFieldInsnNode.desc, paramSearchType, Traversal.NONE, paramInt);
  }
  
  public Field findFieldInHierarchy(String paramString1, String paramString2, SearchType paramSearchType) {
    return findFieldInHierarchy(paramString1, paramString2, paramSearchType, Traversal.NONE);
  }
  
  public Field findFieldInHierarchy(String paramString1, String paramString2, SearchType paramSearchType, Traversal paramTraversal) {
    return findFieldInHierarchy(paramString1, paramString2, paramSearchType, paramTraversal, 0);
  }
  
  public Field findFieldInHierarchy(String paramString1, String paramString2, SearchType paramSearchType, Traversal paramTraversal, int paramInt) {
    return findInHierarchy(paramString1, paramString2, paramSearchType, paramTraversal, paramInt, Member.Type.FIELD);
  }
  
  private <M extends Member> M findInHierarchy(String paramString1, String paramString2, SearchType paramSearchType, Traversal paramTraversal, int paramInt, Member.Type paramType) {
    if (paramSearchType == SearchType.ALL_CLASSES) {
      M m = (M)findMember(paramString1, paramString2, paramInt, paramType);
      if (m != null)
        return m; 
      if (paramTraversal.canTraverse())
        for (MixinInfo mixinInfo : this.mixins) {
          M m1 = (M)mixinInfo.getClassInfo().findMember(paramString1, paramString2, paramInt, paramType);
          if (m1 != null)
            return cloneMember(m1); 
        }  
    } 
    ClassInfo classInfo = getSuperClass();
    if (classInfo != null)
      for (ClassInfo classInfo1 : classInfo.getTargets()) {
        M m = (M)classInfo1.findInHierarchy(paramString1, paramString2, SearchType.ALL_CLASSES, paramTraversal.next(), paramInt & 0xFFFFFFFD, paramType);
        if (m != null)
          return m; 
      }  
    if (paramType == Member.Type.METHOD && (this.isInterface || MixinEnvironment.getCompatibilityLevel().supportsMethodsInInterfaces()))
      for (String str : this.interfaces) {
        ClassInfo classInfo1 = forName(str);
        if (classInfo1 == null) {
          logger.debug("Failed to resolve declared interface {} on {}", new Object[] { str, this.name });
          continue;
        } 
        M m = (M)classInfo1.findInHierarchy(paramString1, paramString2, SearchType.ALL_CLASSES, paramTraversal.next(), paramInt & 0xFFFFFFFD, paramType);
        if (m != null)
          return this.isInterface ? m : (M)new InterfaceMethod((Member)m); 
      }  
    return null;
  }
  
  private <M extends Member> M cloneMember(M paramM) {
    if (paramM instanceof Method)
      return (M)new Method((Member)paramM); 
    return (M)new Field((Member)paramM);
  }
  
  public Method findMethod(MethodNode paramMethodNode) {
    return findMethod(paramMethodNode.name, paramMethodNode.desc, paramMethodNode.access);
  }
  
  public Method findMethod(MethodNode paramMethodNode, int paramInt) {
    return findMethod(paramMethodNode.name, paramMethodNode.desc, paramInt);
  }
  
  public Method findMethod(MethodInsnNode paramMethodInsnNode) {
    return findMethod(paramMethodInsnNode.name, paramMethodInsnNode.desc, 0);
  }
  
  public Method findMethod(MethodInsnNode paramMethodInsnNode, int paramInt) {
    return findMethod(paramMethodInsnNode.name, paramMethodInsnNode.desc, paramInt);
  }
  
  public Method findMethod(String paramString1, String paramString2, int paramInt) {
    return findMember(paramString1, paramString2, paramInt, Member.Type.METHOD);
  }
  
  public Field findField(FieldNode paramFieldNode) {
    return findField(paramFieldNode.name, paramFieldNode.desc, paramFieldNode.access);
  }
  
  public Field findField(FieldInsnNode paramFieldInsnNode, int paramInt) {
    return findField(paramFieldInsnNode.name, paramFieldInsnNode.desc, paramInt);
  }
  
  public Field findField(String paramString1, String paramString2, int paramInt) {
    return findMember(paramString1, paramString2, paramInt, Member.Type.FIELD);
  }
  
  private <M extends Member> M findMember(String paramString1, String paramString2, int paramInt, Member.Type paramType) {
    Set set = (Set)((paramType == Member.Type.METHOD) ? this.methods : this.fields);
    for (Member member : set) {
      if (member.equals(paramString1, paramString2) && member.matchesFlags(paramInt))
        return (M)member; 
    } 
    return null;
  }
  
  public boolean equals(Object paramObject) {
    if (!(paramObject instanceof ClassInfo))
      return false; 
    return ((ClassInfo)paramObject).name.equals(this.name);
  }
  
  public int hashCode() {
    return this.name.hashCode();
  }
  
  static ClassInfo fromClassNode(ClassNode paramClassNode) {
    ClassInfo classInfo = cache.get(paramClassNode.name);
    if (classInfo == null) {
      classInfo = new ClassInfo(paramClassNode);
      cache.put(paramClassNode.name, classInfo);
    } 
    return classInfo;
  }
  
  public static ClassInfo forName(String paramString) {
    paramString = paramString.replace('.', '/');
    ClassInfo classInfo = cache.get(paramString);
    if (classInfo == null) {
      try {
        ClassNode classNode = MixinService.getService().getBytecodeProvider().getClassNode(paramString);
        classInfo = new ClassInfo(classNode);
      } catch (Exception exception) {
        logger.catching(Level.TRACE, exception);
        logger.warn("Error loading class: {} ({}: {})", new Object[] { paramString, exception.getClass().getName(), exception.getMessage() });
      } 
      cache.put(paramString, classInfo);
      logger.trace("Added class metadata for {} to metadata cache", new Object[] { paramString });
    } 
    return classInfo;
  }
  
  public static ClassInfo forType(org.spongepowered.asm.lib.Type paramType) {
    if (paramType.getSort() == 9)
      return forType(paramType.getElementType()); 
    if (paramType.getSort() < 9)
      return null; 
    return forName(paramType.getClassName().replace('.', '/'));
  }
  
  public static ClassInfo getCommonSuperClass(String paramString1, String paramString2) {
    if (paramString1 == null || paramString2 == null)
      return OBJECT; 
    return getCommonSuperClass(forName(paramString1), forName(paramString2));
  }
  
  public static ClassInfo getCommonSuperClass(org.spongepowered.asm.lib.Type paramType1, org.spongepowered.asm.lib.Type paramType2) {
    if (paramType1 == null || paramType2 == null || paramType1
      .getSort() != 10 || paramType2.getSort() != 10)
      return OBJECT; 
    return getCommonSuperClass(forType(paramType1), forType(paramType2));
  }
  
  private static ClassInfo getCommonSuperClass(ClassInfo paramClassInfo1, ClassInfo paramClassInfo2) {
    return getCommonSuperClass(paramClassInfo1, paramClassInfo2, false);
  }
  
  public static ClassInfo getCommonSuperClassOrInterface(String paramString1, String paramString2) {
    if (paramString1 == null || paramString2 == null)
      return OBJECT; 
    return getCommonSuperClassOrInterface(forName(paramString1), forName(paramString2));
  }
  
  public static ClassInfo getCommonSuperClassOrInterface(org.spongepowered.asm.lib.Type paramType1, org.spongepowered.asm.lib.Type paramType2) {
    if (paramType1 == null || paramType2 == null || paramType1
      .getSort() != 10 || paramType2.getSort() != 10)
      return OBJECT; 
    return getCommonSuperClassOrInterface(forType(paramType1), forType(paramType2));
  }
  
  public static ClassInfo getCommonSuperClassOrInterface(ClassInfo paramClassInfo1, ClassInfo paramClassInfo2) {
    return getCommonSuperClass(paramClassInfo1, paramClassInfo2, true);
  }
  
  private static ClassInfo getCommonSuperClass(ClassInfo paramClassInfo1, ClassInfo paramClassInfo2, boolean paramBoolean) {
    if (paramClassInfo1.hasSuperClass(paramClassInfo2, Traversal.NONE, paramBoolean))
      return paramClassInfo2; 
    if (paramClassInfo2.hasSuperClass(paramClassInfo1, Traversal.NONE, paramBoolean))
      return paramClassInfo1; 
    if (paramClassInfo1.isInterface() || paramClassInfo2.isInterface())
      return OBJECT; 
    do {
      paramClassInfo1 = paramClassInfo1.getSuperClass();
      if (paramClassInfo1 == null)
        return OBJECT; 
    } while (!paramClassInfo2.hasSuperClass(paramClassInfo1, Traversal.NONE, paramBoolean));
    return paramClassInfo1;
  }
}
