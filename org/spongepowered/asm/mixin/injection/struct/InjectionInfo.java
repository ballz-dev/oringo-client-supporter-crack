package org.spongepowered.asm.mixin.injection.struct;

import com.google.common.base.Strings;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Deque;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.spongepowered.asm.lib.Type;
import org.spongepowered.asm.lib.tree.AnnotationNode;
import org.spongepowered.asm.lib.tree.MethodNode;
import org.spongepowered.asm.mixin.Dynamic;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.InjectionPoint;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.code.ISliceContext;
import org.spongepowered.asm.mixin.injection.code.Injector;
import org.spongepowered.asm.mixin.injection.code.InjectorTarget;
import org.spongepowered.asm.mixin.injection.code.MethodSlice;
import org.spongepowered.asm.mixin.injection.code.MethodSlices;
import org.spongepowered.asm.mixin.injection.throwables.InjectionError;
import org.spongepowered.asm.mixin.injection.throwables.InvalidInjectionException;
import org.spongepowered.asm.mixin.refmap.IMixinContext;
import org.spongepowered.asm.mixin.struct.SpecialMethodInfo;
import org.spongepowered.asm.mixin.transformer.MixinTargetContext;
import org.spongepowered.asm.mixin.transformer.meta.MixinMerged;
import org.spongepowered.asm.mixin.transformer.throwables.InvalidMixinException;
import org.spongepowered.asm.util.Annotations;
import org.spongepowered.asm.util.Bytecode;

public abstract class InjectionInfo extends SpecialMethodInfo implements ISliceContext {
  protected final Deque<MethodNode> targets = new ArrayDeque<MethodNode>();
  
  protected final List<InjectionPoint> injectionPoints = new ArrayList<InjectionPoint>();
  
  protected final Map<Target, List<InjectionNodes.InjectionNode>> targetNodes = new LinkedHashMap<Target, List<InjectionNodes.InjectionNode>>();
  
  private final List<MethodNode> injectedMethods = new ArrayList<MethodNode>(0);
  
  private int expectedCallbackCount = 1;
  
  private int requiredCallbackCount = 0;
  
  private int maxCallbackCount = Integer.MAX_VALUE;
  
  private int injectedCallbackCount = 0;
  
  protected final boolean isStatic;
  
  protected InjectorGroupInfo group;
  
  protected final MethodSlices slices;
  
  protected Injector injector;
  
  protected final String atKey;
  
  protected InjectionInfo(MixinTargetContext paramMixinTargetContext, MethodNode paramMethodNode, AnnotationNode paramAnnotationNode) {
    this(paramMixinTargetContext, paramMethodNode, paramAnnotationNode, "at");
  }
  
  protected InjectionInfo(MixinTargetContext paramMixinTargetContext, MethodNode paramMethodNode, AnnotationNode paramAnnotationNode, String paramString) {
    super(paramMixinTargetContext, paramMethodNode, paramAnnotationNode);
    this.isStatic = Bytecode.methodIsStatic(paramMethodNode);
    this.slices = MethodSlices.parse(this);
    this.atKey = paramString;
    readAnnotation();
  }
  
  protected void readAnnotation() {
    if (this.annotation == null)
      return; 
    String str = "@" + Bytecode.getSimpleName(this.annotation);
    List<AnnotationNode> list = readInjectionPoints(str);
    findMethods(parseTargets(str), str);
    parseInjectionPoints(list);
    parseRequirements();
    this.injector = parseInjector(this.annotation);
  }
  
  protected Set<MemberInfo> parseTargets(String paramString) {
    List list = Annotations.getValue(this.annotation, "method", false);
    if (list == null)
      throw new InvalidInjectionException(this, String.format("%s annotation on %s is missing method name", new Object[] { paramString, this.method.name })); 
    LinkedHashSet<MemberInfo> linkedHashSet = new LinkedHashSet();
    for (String str : list) {
      try {
        MemberInfo memberInfo = MemberInfo.parseAndValidate(str, (IMixinContext)this.mixin);
        if (memberInfo.owner != null && !memberInfo.owner.equals(this.mixin.getTargetClassRef()))
          throw new InvalidInjectionException(this, 
              String.format("%s annotation on %s specifies a target class '%s', which is not supported", new Object[] { paramString, this.method.name, memberInfo.owner })); 
        linkedHashSet.add(memberInfo);
      } catch (InvalidMemberDescriptorException invalidMemberDescriptorException) {
        throw new InvalidInjectionException(this, String.format("%s annotation on %s, has invalid target descriptor: \"%s\". %s", new Object[] { paramString, this.method.name, str, this.mixin
                .getReferenceMapper().getStatus() }));
      } 
    } 
    return linkedHashSet;
  }
  
  protected List<AnnotationNode> readInjectionPoints(String paramString) {
    List<AnnotationNode> list = Annotations.getValue(this.annotation, this.atKey, false);
    if (list == null)
      throw new InvalidInjectionException(this, String.format("%s annotation on %s is missing '%s' value(s)", new Object[] { paramString, this.method.name, this.atKey })); 
    return list;
  }
  
  protected void parseInjectionPoints(List<AnnotationNode> paramList) {
    this.injectionPoints.addAll(InjectionPoint.parse((IMixinContext)this.mixin, this.method, this.annotation, paramList));
  }
  
  protected void parseRequirements() {
    this.group = this.mixin.getInjectorGroups().parseGroup(this.method, this.mixin.getDefaultInjectorGroup()).add(this);
    Integer integer1 = (Integer)Annotations.getValue(this.annotation, "expect");
    if (integer1 != null)
      this.expectedCallbackCount = integer1.intValue(); 
    Integer integer2 = (Integer)Annotations.getValue(this.annotation, "require");
    if (integer2 != null && integer2.intValue() > -1) {
      this.requiredCallbackCount = integer2.intValue();
    } else if (this.group.isDefault()) {
      this.requiredCallbackCount = this.mixin.getDefaultRequiredInjections();
    } 
    Integer integer3 = (Integer)Annotations.getValue(this.annotation, "allow");
    if (integer3 != null)
      this.maxCallbackCount = Math.max(Math.max(this.requiredCallbackCount, 1), integer3.intValue()); 
  }
  
  public boolean isValid() {
    return (this.targets.size() > 0 && this.injectionPoints.size() > 0);
  }
  
  public void prepare() {
    this.targetNodes.clear();
    for (MethodNode methodNode : this.targets) {
      Target target = this.mixin.getTargetMethod(methodNode);
      InjectorTarget injectorTarget = new InjectorTarget(this, target);
      this.targetNodes.put(target, this.injector.find(injectorTarget, this.injectionPoints));
      injectorTarget.dispose();
    } 
  }
  
  public void inject() {
    for (Map.Entry<Target, List<InjectionNodes.InjectionNode>> entry : this.targetNodes.entrySet())
      this.injector.inject((Target)entry.getKey(), (List)entry.getValue()); 
    this.targets.clear();
  }
  
  public void postInject() {
    for (MethodNode methodNode : this.injectedMethods)
      this.classNode.methods.add(methodNode); 
    String str1 = getDescription();
    String str2 = this.mixin.getReferenceMapper().getStatus();
    String str3 = getDynamicInfo();
    if (this.mixin.getEnvironment().getOption(MixinEnvironment.Option.DEBUG_INJECTORS) && this.injectedCallbackCount < this.expectedCallbackCount)
      throw new InvalidInjectionException(this, 
          String.format("Injection validation failed: %s %s%s in %s expected %d invocation(s) but %d succeeded. %s%s", new Object[] { str1, this.method.name, this.method.desc, this.mixin, Integer.valueOf(this.expectedCallbackCount), Integer.valueOf(this.injectedCallbackCount), str2, str3 })); 
    if (this.injectedCallbackCount < this.requiredCallbackCount)
      throw new InjectionError(
          String.format("Critical injection failure: %s %s%s in %s failed injection check, (%d/%d) succeeded. %s%s", new Object[] { str1, this.method.name, this.method.desc, this.mixin, Integer.valueOf(this.injectedCallbackCount), Integer.valueOf(this.requiredCallbackCount), str2, str3 })); 
    if (this.injectedCallbackCount > this.maxCallbackCount)
      throw new InjectionError(
          String.format("Critical injection failure: %s %s%s in %s failed injection check, %d succeeded of %d allowed.%s", new Object[] { str1, this.method.name, this.method.desc, this.mixin, Integer.valueOf(this.injectedCallbackCount), Integer.valueOf(this.maxCallbackCount), str3 })); 
  }
  
  public void notifyInjected(Target paramTarget) {}
  
  protected String getDescription() {
    return "Callback method";
  }
  
  public String toString() {
    return describeInjector((IMixinContext)this.mixin, this.annotation, this.method);
  }
  
  public Collection<MethodNode> getTargets() {
    return this.targets;
  }
  
  public MethodSlice getSlice(String paramString) {
    return this.slices.get(getSliceId(paramString));
  }
  
  public String getSliceId(String paramString) {
    return "";
  }
  
  public int getInjectedCallbackCount() {
    return this.injectedCallbackCount;
  }
  
  public MethodNode addMethod(int paramInt, String paramString1, String paramString2) {
    MethodNode methodNode = new MethodNode(327680, paramInt | 0x1000, paramString1, paramString2, null, null);
    this.injectedMethods.add(methodNode);
    return methodNode;
  }
  
  public void addCallbackInvocation(MethodNode paramMethodNode) {
    this.injectedCallbackCount++;
  }
  
  private void findMethods(Set<MemberInfo> paramSet, String paramString) {
    this.targets.clear();
    byte b = this.mixin.getEnvironment().getOption(MixinEnvironment.Option.REFMAP_REMAP) ? 2 : 1;
    for (MemberInfo memberInfo : paramSet) {
      for (byte b1 = 0, b2 = 0; b2 < b && b1 < 1; b2++) {
        byte b3 = 0;
        for (MethodNode methodNode : this.classNode.methods) {
          if (memberInfo.matches(methodNode.name, methodNode.desc, b3)) {
            boolean bool = (Annotations.getVisible(methodNode, MixinMerged.class) != null) ? true : false;
            if (memberInfo.matchAll && (Bytecode.methodIsStatic(methodNode) != this.isStatic || methodNode == this.method || bool))
              continue; 
            checkTarget(methodNode);
            this.targets.add(methodNode);
            b3++;
            b1++;
          } 
        } 
        memberInfo = memberInfo.transform(null);
      } 
    } 
    if (this.targets.size() == 0)
      throw new InvalidInjectionException(this, 
          String.format("%s annotation on %s could not find any targets matching %s in the target class %s. %s%s", new Object[] { paramString, this.method.name, namesOf(paramSet), this.mixin.getTarget(), this.mixin
              .getReferenceMapper().getStatus(), getDynamicInfo() })); 
  }
  
  private void checkTarget(MethodNode paramMethodNode) {
    AnnotationNode annotationNode = Annotations.getVisible(paramMethodNode, MixinMerged.class);
    if (annotationNode == null)
      return; 
    if (Annotations.getVisible(paramMethodNode, Final.class) != null)
      throw new InvalidInjectionException(this, String.format("%s cannot inject into @Final method %s::%s%s merged by %s", new Object[] { this, this.classNode.name, paramMethodNode.name, paramMethodNode.desc, 
              Annotations.getValue(annotationNode, "mixin") })); 
  }
  
  protected String getDynamicInfo() {
    AnnotationNode annotationNode = Annotations.getInvisible(this.method, Dynamic.class);
    String str = Strings.nullToEmpty((String)Annotations.getValue(annotationNode));
    Type type = (Type)Annotations.getValue(annotationNode, "mixin");
    if (type != null)
      str = String.format("{%s} %s", new Object[] { type.getClassName(), str }).trim(); 
    return (str.length() > 0) ? String.format(" Method is @Dynamic(%s)", new Object[] { str }) : "";
  }
  
  public static InjectionInfo parse(MixinTargetContext paramMixinTargetContext, MethodNode paramMethodNode) {
    AnnotationNode annotationNode = getInjectorAnnotation(paramMixinTargetContext.getMixin(), paramMethodNode);
    if (annotationNode == null)
      return null; 
    if (annotationNode.desc.endsWith(Inject.class.getSimpleName() + ";"))
      return new CallbackInjectionInfo(paramMixinTargetContext, paramMethodNode, annotationNode); 
    if (annotationNode.desc.endsWith(ModifyArg.class.getSimpleName() + ";"))
      return new ModifyArgInjectionInfo(paramMixinTargetContext, paramMethodNode, annotationNode); 
    if (annotationNode.desc.endsWith(ModifyArgs.class.getSimpleName() + ";"))
      return new ModifyArgsInjectionInfo(paramMixinTargetContext, paramMethodNode, annotationNode); 
    if (annotationNode.desc.endsWith(Redirect.class.getSimpleName() + ";"))
      return new RedirectInjectionInfo(paramMixinTargetContext, paramMethodNode, annotationNode); 
    if (annotationNode.desc.endsWith(ModifyVariable.class.getSimpleName() + ";"))
      return new ModifyVariableInjectionInfo(paramMixinTargetContext, paramMethodNode, annotationNode); 
    if (annotationNode.desc.endsWith(ModifyConstant.class.getSimpleName() + ";"))
      return new ModifyConstantInjectionInfo(paramMixinTargetContext, paramMethodNode, annotationNode); 
    return null;
  }
  
  public static AnnotationNode getInjectorAnnotation(IMixinInfo paramIMixinInfo, MethodNode paramMethodNode) {
    AnnotationNode annotationNode = null;
    try {
      annotationNode = Annotations.getSingleVisible(paramMethodNode, new Class[] { Inject.class, ModifyArg.class, ModifyArgs.class, Redirect.class, ModifyVariable.class, ModifyConstant.class });
    } catch (IllegalArgumentException illegalArgumentException) {
      throw new InvalidMixinException(paramIMixinInfo, String.format("Error parsing annotations on %s in %s: %s", new Object[] { paramMethodNode.name, paramIMixinInfo.getClassName(), illegalArgumentException
              .getMessage() }));
    } 
    return annotationNode;
  }
  
  public static String getInjectorPrefix(AnnotationNode paramAnnotationNode) {
    if (paramAnnotationNode != null) {
      if (paramAnnotationNode.desc.endsWith(ModifyArg.class.getSimpleName() + ";"))
        return "modify"; 
      if (paramAnnotationNode.desc.endsWith(ModifyArgs.class.getSimpleName() + ";"))
        return "args"; 
      if (paramAnnotationNode.desc.endsWith(Redirect.class.getSimpleName() + ";"))
        return "redirect"; 
      if (paramAnnotationNode.desc.endsWith(ModifyVariable.class.getSimpleName() + ";"))
        return "localvar"; 
      if (paramAnnotationNode.desc.endsWith(ModifyConstant.class.getSimpleName() + ";"))
        return "constant"; 
    } 
    return "handler";
  }
  
  static String describeInjector(IMixinContext paramIMixinContext, AnnotationNode paramAnnotationNode, MethodNode paramMethodNode) {
    return String.format("%s->@%s::%s%s", new Object[] { paramIMixinContext.toString(), Bytecode.getSimpleName(paramAnnotationNode), paramMethodNode.name, paramMethodNode.desc });
  }
  
  private static String namesOf(Collection<MemberInfo> paramCollection) {
    byte b = 0;
    int i = paramCollection.size();
    StringBuilder stringBuilder = new StringBuilder();
    for (MemberInfo memberInfo : paramCollection) {
      if (b)
        if (b == i - 1) {
          stringBuilder.append(" or ");
        } else {
          stringBuilder.append(", ");
        }  
      stringBuilder.append('\'').append(memberInfo.name).append('\'');
      b++;
    } 
    return stringBuilder.toString();
  }
  
  protected abstract Injector parseInjector(AnnotationNode paramAnnotationNode);
}
