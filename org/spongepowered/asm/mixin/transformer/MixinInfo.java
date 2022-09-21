package org.spongepowered.asm.mixin.transformer;

import com.google.common.base.Function;
import com.google.common.base.Functions;
import com.google.common.collect.Lists;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.lib.ClassReader;
import org.spongepowered.asm.lib.ClassVisitor;
import org.spongepowered.asm.lib.MethodVisitor;
import org.spongepowered.asm.lib.Type;
import org.spongepowered.asm.lib.tree.AnnotationNode;
import org.spongepowered.asm.lib.tree.ClassNode;
import org.spongepowered.asm.lib.tree.FieldNode;
import org.spongepowered.asm.lib.tree.InnerClassNode;
import org.spongepowered.asm.lib.tree.MethodNode;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.extensibility.IMixinConfig;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;
import org.spongepowered.asm.mixin.injection.Surrogate;
import org.spongepowered.asm.mixin.injection.struct.InjectionInfo;
import org.spongepowered.asm.mixin.transformer.throwables.InvalidMixinException;
import org.spongepowered.asm.mixin.transformer.throwables.MixinReloadException;
import org.spongepowered.asm.mixin.transformer.throwables.MixinTargetAlreadyLoadedException;
import org.spongepowered.asm.service.IMixinService;
import org.spongepowered.asm.service.MixinService;
import org.spongepowered.asm.util.Annotations;
import org.spongepowered.asm.util.Bytecode;
import org.spongepowered.asm.util.perf.Profiler;

class MixinInfo implements Comparable<MixinInfo>, IMixinInfo {
  class MixinMethodNode extends MethodNode {
    private final String originalName;
    
    public MixinMethodNode(int param1Int, String param1String1, String param1String2, String param1String3, String[] param1ArrayOfString) {
      super(327680, param1Int, param1String1, param1String2, param1String3, param1ArrayOfString);
      this.originalName = param1String1;
    }
    
    public String toString() {
      return String.format("%s%s", new Object[] { this.originalName, this.desc });
    }
    
    public String getOriginalName() {
      return this.originalName;
    }
    
    public boolean isInjector() {
      return (getInjectorAnnotation() != null || isSurrogate());
    }
    
    public boolean isSurrogate() {
      return (getVisibleAnnotation((Class)Surrogate.class) != null);
    }
    
    public boolean isSynthetic() {
      return Bytecode.hasFlag(this, 4096);
    }
    
    public AnnotationNode getVisibleAnnotation(Class<? extends Annotation> param1Class) {
      return Annotations.getVisible(this, param1Class);
    }
    
    public AnnotationNode getInjectorAnnotation() {
      return InjectionInfo.getInjectorAnnotation(MixinInfo.this, this);
    }
    
    public IMixinInfo getOwner() {
      return MixinInfo.this;
    }
  }
  
  class MixinClassNode extends ClassNode {
    public final List<MixinInfo.MixinMethodNode> mixinMethods;
    
    public MixinClassNode(MixinInfo param1MixinInfo1) {
      this(327680);
    }
    
    public MixinClassNode(int param1Int) {
      super(param1Int);
      this.mixinMethods = this.methods;
    }
    
    public MixinInfo getMixin() {
      return MixinInfo.this;
    }
    
    public MethodVisitor visitMethod(int param1Int, String param1String1, String param1String2, String param1String3, String[] param1ArrayOfString) {
      MixinInfo.MixinMethodNode mixinMethodNode = new MixinInfo.MixinMethodNode(param1Int, param1String1, param1String2, param1String3, param1ArrayOfString);
      this.methods.add(mixinMethodNode);
      return (MethodVisitor)mixinMethodNode;
    }
  }
  
  class State {
    protected final Set<String> interfaces = new HashSet<String>();
    
    protected final List<InterfaceInfo> softImplements = new ArrayList<InterfaceInfo>();
    
    protected final Set<String> syntheticInnerClasses = new HashSet<String>();
    
    protected final Set<String> innerClasses = new HashSet<String>();
    
    private byte[] mixinBytes;
    
    protected MixinInfo.MixinClassNode classNode;
    
    private boolean detachedSuper;
    
    private final ClassInfo classInfo;
    
    private boolean unique;
    
    State(byte[] param1ArrayOfbyte) {
      this(param1ArrayOfbyte, null);
    }
    
    State(byte[] param1ArrayOfbyte, ClassInfo param1ClassInfo) {
      this.mixinBytes = param1ArrayOfbyte;
      connect();
      this.classInfo = (param1ClassInfo != null) ? param1ClassInfo : ClassInfo.fromClassNode(getClassNode());
    }
    
    private void connect() {
      this.classNode = createClassNode(0);
    }
    
    private void complete() {
      this.classNode = null;
    }
    
    ClassInfo getClassInfo() {
      return this.classInfo;
    }
    
    byte[] getClassBytes() {
      return this.mixinBytes;
    }
    
    MixinInfo.MixinClassNode getClassNode() {
      return this.classNode;
    }
    
    boolean isDetachedSuper() {
      return this.detachedSuper;
    }
    
    boolean isUnique() {
      return this.unique;
    }
    
    List<? extends InterfaceInfo> getSoftImplements() {
      return this.softImplements;
    }
    
    Set<String> getSyntheticInnerClasses() {
      return this.syntheticInnerClasses;
    }
    
    Set<String> getInnerClasses() {
      return this.innerClasses;
    }
    
    Set<String> getInterfaces() {
      return this.interfaces;
    }
    
    MixinInfo.MixinClassNode createClassNode(int param1Int) {
      MixinInfo.MixinClassNode mixinClassNode = new MixinInfo.MixinClassNode(MixinInfo.this);
      ClassReader classReader = new ClassReader(this.mixinBytes);
      classReader.accept((ClassVisitor)mixinClassNode, param1Int);
      return mixinClassNode;
    }
    
    void validate(MixinInfo.SubType param1SubType, List<ClassInfo> param1List) {
      MixinPreProcessorStandard mixinPreProcessorStandard = param1SubType.createPreProcessor(getClassNode()).prepare();
      for (ClassInfo classInfo : param1List)
        mixinPreProcessorStandard.conform(classInfo); 
      param1SubType.validate(this, param1List);
      this.detachedSuper = param1SubType.isDetachedSuper();
      this.unique = (Annotations.getVisible(getClassNode(), Unique.class) != null);
      validateInner();
      validateClassVersion();
      validateRemappables(param1List);
      readImplementations(param1SubType);
      readInnerClasses();
      validateChanges(param1SubType, param1List);
      complete();
    }
    
    private void validateInner() {
      if (!this.classInfo.isProbablyStatic())
        throw new InvalidMixinException(MixinInfo.this, "Inner class mixin must be declared static"); 
    }
    
    private void validateClassVersion() {
      if (this.classNode.version > MixinEnvironment.getCompatibilityLevel().classVersion()) {
        String str = ".";
        for (MixinEnvironment.CompatibilityLevel compatibilityLevel : MixinEnvironment.CompatibilityLevel.values()) {
          if (compatibilityLevel.classVersion() >= this.classNode.version)
            str = String.format(". Mixin requires compatibility level %s or above.", new Object[] { compatibilityLevel.name() }); 
        } 
        throw new InvalidMixinException(MixinInfo.this, "Unsupported mixin class version " + this.classNode.version + str);
      } 
    }
    
    private void validateRemappables(List<ClassInfo> param1List) {
      if (param1List.size() > 1) {
        for (FieldNode fieldNode : this.classNode.fields)
          validateRemappable(Shadow.class, fieldNode.name, Annotations.getVisible(fieldNode, Shadow.class)); 
        for (MethodNode methodNode : this.classNode.methods) {
          validateRemappable(Shadow.class, methodNode.name, Annotations.getVisible(methodNode, Shadow.class));
          AnnotationNode annotationNode = Annotations.getVisible(methodNode, Overwrite.class);
          if (annotationNode != null && ((methodNode.access & 0x8) == 0 || (methodNode.access & 0x1) == 0))
            throw new InvalidMixinException(MixinInfo.this, "Found @Overwrite annotation on " + methodNode.name + " in " + MixinInfo.this); 
        } 
      } 
    }
    
    private void validateRemappable(Class<Shadow> param1Class, String param1String, AnnotationNode param1AnnotationNode) {
      if (param1AnnotationNode != null && ((Boolean)Annotations.getValue(param1AnnotationNode, "remap", Boolean.TRUE)).booleanValue())
        throw new InvalidMixinException(MixinInfo.this, "Found a remappable @" + param1Class.getSimpleName() + " annotation on " + param1String + " in " + this); 
    }
    
    void readImplementations(MixinInfo.SubType param1SubType) {
      this.interfaces.addAll(this.classNode.interfaces);
      this.interfaces.addAll(param1SubType.getInterfaces());
      AnnotationNode annotationNode = Annotations.getInvisible(this.classNode, Implements.class);
      if (annotationNode == null)
        return; 
      List list = (List)Annotations.getValue(annotationNode);
      if (list == null)
        return; 
      for (AnnotationNode annotationNode1 : list) {
        InterfaceInfo interfaceInfo = InterfaceInfo.fromAnnotation(MixinInfo.this, annotationNode1);
        this.softImplements.add(interfaceInfo);
        this.interfaces.add(interfaceInfo.getInternalName());
        if (!(this instanceof MixinInfo.Reloaded))
          this.classInfo.addInterface(interfaceInfo.getInternalName()); 
      } 
    }
    
    void readInnerClasses() {
      for (InnerClassNode innerClassNode : this.classNode.innerClasses) {
        ClassInfo classInfo = ClassInfo.forName(innerClassNode.name);
        if ((innerClassNode.outerName != null && innerClassNode.outerName.equals(this.classInfo.getName())) || innerClassNode.name
          .startsWith(this.classNode.name + "$")) {
          if (classInfo.isProbablyStatic() && classInfo.isSynthetic()) {
            this.syntheticInnerClasses.add(innerClassNode.name);
            continue;
          } 
          this.innerClasses.add(innerClassNode.name);
        } 
      } 
    }
    
    protected void validateChanges(MixinInfo.SubType param1SubType, List<ClassInfo> param1List) {
      param1SubType.createPreProcessor(this.classNode).prepare();
    }
  }
  
  class Reloaded extends State {
    private final MixinInfo.State previous;
    
    Reloaded(MixinInfo.State param1State, byte[] param1ArrayOfbyte) {
      super(param1ArrayOfbyte, param1State.getClassInfo());
      this.previous = param1State;
    }
    
    protected void validateChanges(MixinInfo.SubType param1SubType, List<ClassInfo> param1List) {
      if (!this.syntheticInnerClasses.equals(this.previous.syntheticInnerClasses))
        throw new MixinReloadException(MixinInfo.this, "Cannot change inner classes"); 
      if (!this.interfaces.equals(this.previous.interfaces))
        throw new MixinReloadException(MixinInfo.this, "Cannot change interfaces"); 
      if (!(new HashSet(this.softImplements)).equals(new HashSet<InterfaceInfo>(this.previous.softImplements)))
        throw new MixinReloadException(MixinInfo.this, "Cannot change soft interfaces"); 
      List<ClassInfo> list = MixinInfo.this.readTargetClasses(this.classNode, true);
      if (!(new HashSet(list)).equals(new HashSet<ClassInfo>(param1List)))
        throw new MixinReloadException(MixinInfo.this, "Cannot change target classes"); 
      int i = MixinInfo.this.readPriority(this.classNode);
      if (i != MixinInfo.this.getPriority())
        throw new MixinReloadException(MixinInfo.this, "Cannot change mixin priority"); 
    }
  }
  
  static abstract class SubType {
    protected final MixinInfo mixin;
    
    protected final String annotationType;
    
    protected boolean detached;
    
    protected final boolean targetMustBeInterface;
    
    SubType(MixinInfo param1MixinInfo, String param1String, boolean param1Boolean) {
      this.mixin = param1MixinInfo;
      this.annotationType = param1String;
      this.targetMustBeInterface = param1Boolean;
    }
    
    Collection<String> getInterfaces() {
      return Collections.emptyList();
    }
    
    boolean isDetachedSuper() {
      return this.detached;
    }
    
    boolean isLoadable() {
      return false;
    }
    
    void validateTarget(String param1String, ClassInfo param1ClassInfo) {
      boolean bool = param1ClassInfo.isInterface();
      if (bool != this.targetMustBeInterface) {
        String str = bool ? "" : "not ";
        throw new InvalidMixinException(this.mixin, this.annotationType + " target type mismatch: " + param1String + " is " + str + "an interface in " + this);
      } 
    }
    
    static class Standard extends SubType {
      Standard(MixinInfo param2MixinInfo) {
        super(param2MixinInfo, "@Mixin", false);
      }
      
      void validate(MixinInfo.State param2State, List<ClassInfo> param2List) {
        MixinInfo.MixinClassNode mixinClassNode = param2State.getClassNode();
        for (ClassInfo classInfo : param2List) {
          if (mixinClassNode.superName.equals(classInfo.getSuperName()))
            continue; 
          if (!classInfo.hasSuperClass(mixinClassNode.superName, ClassInfo.Traversal.SUPER)) {
            ClassInfo classInfo1 = ClassInfo.forName(mixinClassNode.superName);
            if (classInfo1.isMixin())
              for (ClassInfo classInfo2 : classInfo1.getTargets()) {
                if (param2List.contains(classInfo2))
                  throw new InvalidMixinException(this.mixin, "Illegal hierarchy detected. Derived mixin " + this + " targets the same class " + classInfo2
                      .getClassName() + " as its superclass " + classInfo1
                      .getClassName()); 
              }  
            throw new InvalidMixinException(this.mixin, "Super class '" + mixinClassNode.superName.replace('/', '.') + "' of " + this.mixin
                .getName() + " was not found in the hierarchy of target class '" + classInfo + "'");
          } 
          this.detached = true;
        } 
      }
      
      MixinPreProcessorStandard createPreProcessor(MixinInfo.MixinClassNode param2MixinClassNode) {
        return new MixinPreProcessorStandard(this.mixin, param2MixinClassNode);
      }
    }
    
    static class Interface extends SubType {
      Interface(MixinInfo param2MixinInfo) {
        super(param2MixinInfo, "@Mixin", true);
      }
      
      void validate(MixinInfo.State param2State, List<ClassInfo> param2List) {
        if (!MixinEnvironment.getCompatibilityLevel().supportsMethodsInInterfaces())
          throw new InvalidMixinException(this.mixin, "Interface mixin not supported in current enviromnment"); 
        MixinInfo.MixinClassNode mixinClassNode = param2State.getClassNode();
        if (!"java/lang/Object".equals(mixinClassNode.superName))
          throw new InvalidMixinException(this.mixin, "Super class of " + this + " is invalid, found " + mixinClassNode.superName
              .replace('/', '.')); 
      }
      
      MixinPreProcessorStandard createPreProcessor(MixinInfo.MixinClassNode param2MixinClassNode) {
        return new MixinPreProcessorInterface(this.mixin, param2MixinClassNode);
      }
    }
    
    static class Accessor extends SubType {
      private final Collection<String> interfaces = new ArrayList<String>();
      
      Accessor(MixinInfo param2MixinInfo) {
        super(param2MixinInfo, "@Mixin", false);
        this.interfaces.add(param2MixinInfo.getClassRef());
      }
      
      boolean isLoadable() {
        return true;
      }
      
      Collection<String> getInterfaces() {
        return this.interfaces;
      }
      
      void validateTarget(String param2String, ClassInfo param2ClassInfo) {
        boolean bool = param2ClassInfo.isInterface();
        if (bool && !MixinEnvironment.getCompatibilityLevel().supportsMethodsInInterfaces())
          throw new InvalidMixinException(this.mixin, "Accessor mixin targetting an interface is not supported in current enviromnment"); 
      }
      
      void validate(MixinInfo.State param2State, List<ClassInfo> param2List) {
        MixinInfo.MixinClassNode mixinClassNode = param2State.getClassNode();
        if (!"java/lang/Object".equals(mixinClassNode.superName))
          throw new InvalidMixinException(this.mixin, "Super class of " + this + " is invalid, found " + mixinClassNode.superName
              .replace('/', '.')); 
      }
      
      MixinPreProcessorStandard createPreProcessor(MixinInfo.MixinClassNode param2MixinClassNode) {
        return new MixinPreProcessorAccessor(this.mixin, param2MixinClassNode);
      }
    }
    
    static SubType getTypeFor(MixinInfo param1MixinInfo) {
      if (!param1MixinInfo.getClassInfo().isInterface())
        return new Standard(param1MixinInfo); 
      int i = 0;
      for (ClassInfo.Method method : param1MixinInfo.getClassInfo().getMethods())
        i |= !method.isAccessor() ? 1 : 0; 
      if (i != 0)
        return new Interface(param1MixinInfo); 
      return new Accessor(param1MixinInfo);
    }
    
    abstract void validate(MixinInfo.State param1State, List<ClassInfo> param1List);
    
    abstract MixinPreProcessorStandard createPreProcessor(MixinInfo.MixinClassNode param1MixinClassNode);
  }
  
  static class Accessor extends SubType {
    private final Collection<String> interfaces = new ArrayList<String>();
    
    Accessor(MixinInfo param1MixinInfo) {
      super(param1MixinInfo, "@Mixin", false);
      this.interfaces.add(param1MixinInfo.getClassRef());
    }
    
    boolean isLoadable() {
      return true;
    }
    
    Collection<String> getInterfaces() {
      return this.interfaces;
    }
    
    void validateTarget(String param1String, ClassInfo param1ClassInfo) {
      boolean bool = param1ClassInfo.isInterface();
      if (bool && !MixinEnvironment.getCompatibilityLevel().supportsMethodsInInterfaces())
        throw new InvalidMixinException(this.mixin, "Accessor mixin targetting an interface is not supported in current enviromnment"); 
    }
    
    void validate(MixinInfo.State param1State, List<ClassInfo> param1List) {
      MixinInfo.MixinClassNode mixinClassNode = param1State.getClassNode();
      if (!"java/lang/Object".equals(mixinClassNode.superName))
        throw new InvalidMixinException(this.mixin, "Super class of " + this + " is invalid, found " + mixinClassNode.superName.replace('/', '.')); 
    }
    
    MixinPreProcessorStandard createPreProcessor(MixinInfo.MixinClassNode param1MixinClassNode) {
      return new MixinPreProcessorAccessor(this.mixin, param1MixinClassNode);
    }
  }
  
  private static final IMixinService classLoaderUtil = MixinService.getService();
  
  static int mixinOrder = 0;
  
  private final transient Logger logger = LogManager.getLogger("mixin");
  
  private final transient Profiler profiler = MixinEnvironment.getProfiler();
  
  private final transient int order = mixinOrder++;
  
  private final transient SubType type;
  
  private final transient IMixinService service;
  
  private transient State state;
  
  private final List<ClassInfo> targetClasses;
  
  private final List<String> targetClassNames;
  
  private final String name;
  
  private final String className;
  
  private final transient ClassInfo info;
  
  private final int priority;
  
  private final transient MixinConfig parent;
  
  private final transient MixinEnvironment.Phase phase;
  
  private transient State pendingState;
  
  private final transient boolean strict;
  
  private final boolean virtual;
  
  private final transient IMixinConfigPlugin plugin;
  
  MixinInfo(IMixinService paramIMixinService, MixinConfig paramMixinConfig, String paramString, boolean paramBoolean1, IMixinConfigPlugin paramIMixinConfigPlugin, boolean paramBoolean2) {
    this.service = paramIMixinService;
    this.parent = paramMixinConfig;
    this.name = paramString;
    this.className = paramMixinConfig.getMixinPackage() + paramString;
    this.plugin = paramIMixinConfigPlugin;
    this.phase = paramMixinConfig.getEnvironment().getPhase();
    this.strict = paramMixinConfig.getEnvironment().getOption(MixinEnvironment.Option.DEBUG_TARGETS);
    try {
      byte[] arrayOfByte = loadMixinClass(this.className, paramBoolean1);
      this.pendingState = new State(arrayOfByte);
      this.info = this.pendingState.getClassInfo();
      this.type = SubType.getTypeFor(this);
    } catch (InvalidMixinException invalidMixinException) {
      throw invalidMixinException;
    } catch (Exception exception) {
      throw new InvalidMixinException(this, exception);
    } 
    if (!this.type.isLoadable())
      classLoaderUtil.registerInvalidClass(this.className); 
    try {
      this.priority = readPriority(this.pendingState.getClassNode());
      this.virtual = readPseudo(this.pendingState.getClassNode());
      this.targetClasses = readTargetClasses(this.pendingState.getClassNode(), paramBoolean2);
      this.targetClassNames = Collections.unmodifiableList(Lists.transform(this.targetClasses, Functions.toStringFunction()));
    } catch (InvalidMixinException invalidMixinException) {
      throw invalidMixinException;
    } catch (Exception exception) {
      throw new InvalidMixinException(this, exception);
    } 
  }
  
  void validate() {
    if (this.pendingState == null)
      throw new IllegalStateException("No pending validation state for " + this); 
    try {
      this.pendingState.validate(this.type, this.targetClasses);
      this.state = this.pendingState;
    } finally {
      this.pendingState = null;
    } 
  }
  
  protected List<ClassInfo> readTargetClasses(MixinClassNode paramMixinClassNode, boolean paramBoolean) {
    if (paramMixinClassNode == null)
      return Collections.emptyList(); 
    AnnotationNode annotationNode = Annotations.getInvisible(paramMixinClassNode, Mixin.class);
    if (annotationNode == null)
      throw new InvalidMixinException(this, String.format("The mixin '%s' is missing an @Mixin annotation", new Object[] { this.className })); 
    ArrayList<ClassInfo> arrayList = new ArrayList();
    List list1 = (List)Annotations.getValue(annotationNode, "value");
    List list2 = (List)Annotations.getValue(annotationNode, "targets");
    if (list1 != null)
      readTargets(arrayList, Lists.transform(list1, new Function<Type, String>() {
              public String apply(Type param1Type) {
                return param1Type.getClassName();
              }
            }), paramBoolean, false); 
    if (list2 != null)
      readTargets(arrayList, Lists.transform(list2, new Function<String, String>() {
              public String apply(String param1String) {
                return MixinInfo.this.getParent().remapClassName(MixinInfo.this.getClassRef(), param1String);
              }
            }), paramBoolean, true); 
    return arrayList;
  }
  
  private void readTargets(Collection<ClassInfo> paramCollection, Collection<String> paramCollection1, boolean paramBoolean1, boolean paramBoolean2) {
    for (String str1 : paramCollection1) {
      String str2 = str1.replace('/', '.');
      if (classLoaderUtil.isClassLoaded(str2) && !isReloading()) {
        String str = String.format("Critical problem: %s target %s was already transformed.", new Object[] { this, str2 });
        if (this.parent.isRequired())
          throw new MixinTargetAlreadyLoadedException(this, str, str2); 
        this.logger.error(str);
      } 
      if (shouldApplyMixin(paramBoolean1, str2)) {
        ClassInfo classInfo = getTarget(str2, paramBoolean2);
        if (classInfo != null && !paramCollection.contains(classInfo)) {
          paramCollection.add(classInfo);
          classInfo.addMixin(this);
        } 
      } 
    } 
  }
  
  private boolean shouldApplyMixin(boolean paramBoolean, String paramString) {
    Profiler.Section section = this.profiler.begin("plugin");
    boolean bool = (this.plugin == null || paramBoolean || this.plugin.shouldApplyMixin(paramString, this.className)) ? true : false;
    section.end();
    return bool;
  }
  
  private ClassInfo getTarget(String paramString, boolean paramBoolean) throws InvalidMixinException {
    ClassInfo classInfo = ClassInfo.forName(paramString);
    if (classInfo == null) {
      if (isVirtual()) {
        this.logger.debug("Skipping virtual target {} for {}", new Object[] { paramString, this });
      } else {
        handleTargetError(String.format("@Mixin target %s was not found %s", new Object[] { paramString, this }));
      } 
      return null;
    } 
    this.type.validateTarget(paramString, classInfo);
    if (paramBoolean && classInfo.isPublic() && !isVirtual())
      handleTargetError(String.format("@Mixin target %s is public in %s and should be specified in value", new Object[] { paramString, this })); 
    return classInfo;
  }
  
  private void handleTargetError(String paramString) {
    if (this.strict) {
      this.logger.error(paramString);
      throw new InvalidMixinException(this, paramString);
    } 
    this.logger.warn(paramString);
  }
  
  protected int readPriority(ClassNode paramClassNode) {
    if (paramClassNode == null)
      return this.parent.getDefaultMixinPriority(); 
    AnnotationNode annotationNode = Annotations.getInvisible(paramClassNode, Mixin.class);
    if (annotationNode == null)
      throw new InvalidMixinException(this, String.format("The mixin '%s' is missing an @Mixin annotation", new Object[] { this.className })); 
    Integer integer = (Integer)Annotations.getValue(annotationNode, "priority");
    return (integer == null) ? this.parent.getDefaultMixinPriority() : integer.intValue();
  }
  
  protected boolean readPseudo(ClassNode paramClassNode) {
    return (Annotations.getInvisible(paramClassNode, Pseudo.class) != null);
  }
  
  private boolean isReloading() {
    return this.pendingState instanceof Reloaded;
  }
  
  private State getState() {
    return (this.state != null) ? this.state : this.pendingState;
  }
  
  ClassInfo getClassInfo() {
    return this.info;
  }
  
  public IMixinConfig getConfig() {
    return this.parent;
  }
  
  MixinConfig getParent() {
    return this.parent;
  }
  
  public int getPriority() {
    return this.priority;
  }
  
  public String getName() {
    return this.name;
  }
  
  public String getClassName() {
    return this.className;
  }
  
  public String getClassRef() {
    return getClassInfo().getName();
  }
  
  public byte[] getClassBytes() {
    return getState().getClassBytes();
  }
  
  public boolean isDetachedSuper() {
    return getState().isDetachedSuper();
  }
  
  public boolean isUnique() {
    return getState().isUnique();
  }
  
  public boolean isVirtual() {
    return this.virtual;
  }
  
  public boolean isAccessor() {
    return this.type instanceof SubType.Accessor;
  }
  
  public boolean isLoadable() {
    return this.type.isLoadable();
  }
  
  public Level getLoggingLevel() {
    return this.parent.getLoggingLevel();
  }
  
  public MixinEnvironment.Phase getPhase() {
    return this.phase;
  }
  
  public MixinClassNode getClassNode(int paramInt) {
    return getState().createClassNode(paramInt);
  }
  
  public List<String> getTargetClasses() {
    return this.targetClassNames;
  }
  
  List<InterfaceInfo> getSoftImplements() {
    return Collections.unmodifiableList(getState().getSoftImplements());
  }
  
  Set<String> getSyntheticInnerClasses() {
    return Collections.unmodifiableSet(getState().getSyntheticInnerClasses());
  }
  
  Set<String> getInnerClasses() {
    return Collections.unmodifiableSet(getState().getInnerClasses());
  }
  
  List<ClassInfo> getTargets() {
    return Collections.unmodifiableList(this.targetClasses);
  }
  
  Set<String> getInterfaces() {
    return getState().getInterfaces();
  }
  
  MixinTargetContext createContextFor(TargetClassContext paramTargetClassContext) {
    MixinClassNode mixinClassNode = getClassNode(8);
    Profiler.Section section = this.profiler.begin("pre");
    MixinTargetContext mixinTargetContext = this.type.createPreProcessor(mixinClassNode).prepare().createContextFor(paramTargetClassContext);
    section.end();
    return mixinTargetContext;
  }
  
  private byte[] loadMixinClass(String paramString, boolean paramBoolean) throws ClassNotFoundException {
    byte[] arrayOfByte = null;
    try {
      if (paramBoolean) {
        String str = this.service.getClassRestrictions(paramString);
        if (str.length() > 0)
          this.logger.error("Classloader restrictions [{}] encountered loading {}, name: {}", new Object[] { str, this, paramString }); 
      } 
      arrayOfByte = this.service.getBytecodeProvider().getClassBytes(paramString, paramBoolean);
    } catch (ClassNotFoundException classNotFoundException) {
      throw new ClassNotFoundException(String.format("The specified mixin '%s' was not found", new Object[] { paramString }));
    } catch (IOException iOException) {
      this.logger.warn("Failed to load mixin {}, the specified mixin will not be applied", new Object[] { paramString });
      throw new InvalidMixinException(this, "An error was encountered whilst loading the mixin class", iOException);
    } 
    return arrayOfByte;
  }
  
  void reloadMixin(byte[] paramArrayOfbyte) {
    if (this.pendingState != null)
      throw new IllegalStateException("Cannot reload mixin while it is initialising"); 
    this.pendingState = new Reloaded(this.state, paramArrayOfbyte);
    validate();
  }
  
  public int compareTo(MixinInfo paramMixinInfo) {
    if (paramMixinInfo == null)
      return 0; 
    if (paramMixinInfo.priority == this.priority)
      return this.order - paramMixinInfo.order; 
    return this.priority - paramMixinInfo.priority;
  }
  
  public void preApply(String paramString, ClassNode paramClassNode) {
    if (this.plugin != null) {
      Profiler.Section section = this.profiler.begin("plugin");
      this.plugin.preApply(paramString, paramClassNode, this.className, this);
      section.end();
    } 
  }
  
  public void postApply(String paramString, ClassNode paramClassNode) {
    if (this.plugin != null) {
      Profiler.Section section = this.profiler.begin("plugin");
      this.plugin.postApply(paramString, paramClassNode, this.className, this);
      section.end();
    } 
    this.parent.postApply(paramString, paramClassNode);
  }
  
  public String toString() {
    return String.format("%s:%s", new Object[] { this.parent.getName(), this.name });
  }
}
