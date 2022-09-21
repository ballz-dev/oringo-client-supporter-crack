package org.spongepowered.asm.mixin.injection.invoke;

import com.google.common.base.Joiner;
import com.google.common.collect.ObjectArrays;
import com.google.common.primitives.Ints;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.spongepowered.asm.lib.Type;
import org.spongepowered.asm.lib.tree.AbstractInsnNode;
import org.spongepowered.asm.lib.tree.AnnotationNode;
import org.spongepowered.asm.lib.tree.FieldInsnNode;
import org.spongepowered.asm.lib.tree.InsnList;
import org.spongepowered.asm.lib.tree.InsnNode;
import org.spongepowered.asm.lib.tree.JumpInsnNode;
import org.spongepowered.asm.lib.tree.LabelNode;
import org.spongepowered.asm.lib.tree.MethodInsnNode;
import org.spongepowered.asm.lib.tree.TypeInsnNode;
import org.spongepowered.asm.lib.tree.VarInsnNode;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.injection.Coerce;
import org.spongepowered.asm.mixin.injection.InjectionPoint;
import org.spongepowered.asm.mixin.injection.code.Injector;
import org.spongepowered.asm.mixin.injection.points.BeforeFieldAccess;
import org.spongepowered.asm.mixin.injection.points.BeforeNew;
import org.spongepowered.asm.mixin.injection.struct.InjectionInfo;
import org.spongepowered.asm.mixin.injection.struct.InjectionNodes;
import org.spongepowered.asm.mixin.injection.struct.Target;
import org.spongepowered.asm.mixin.injection.throwables.InvalidInjectionException;
import org.spongepowered.asm.util.Annotations;
import org.spongepowered.asm.util.Bytecode;

public class RedirectInjector extends InvokeInjector {
  class Meta {
    public static final String KEY = "redirector";
    
    final String name;
    
    final String desc;
    
    final boolean isFinal;
    
    final int priority;
    
    public Meta(int param1Int, boolean param1Boolean, String param1String1, String param1String2) {
      this.priority = param1Int;
      this.isFinal = param1Boolean;
      this.name = param1String1;
      this.desc = param1String2;
    }
    
    RedirectInjector getOwner() {
      return RedirectInjector.this;
    }
  }
  
  static class ConstructorRedirectData {
    public static final String KEY = "ctor";
    
    public boolean wildcard = false;
    
    public int injected = 0;
  }
  
  static class RedirectedInvoke {
    final Type returnType;
    
    final MethodInsnNode node;
    
    final Target target;
    
    boolean captureTargetArgs = false;
    
    final Type[] args;
    
    final Type[] locals;
    
    RedirectedInvoke(Target param1Target, MethodInsnNode param1MethodInsnNode) {
      this.target = param1Target;
      this.node = param1MethodInsnNode;
      this.returnType = Type.getReturnType(param1MethodInsnNode.desc);
      this.args = Type.getArgumentTypes(param1MethodInsnNode.desc);
      this
        
        .locals = (param1MethodInsnNode.getOpcode() == 184) ? this.args : (Type[])ObjectArrays.concat(Type.getType("L" + param1MethodInsnNode.owner + ";"), (Object[])this.args);
    }
  }
  
  private Map<BeforeNew, ConstructorRedirectData> ctorRedirectors = new HashMap<BeforeNew, ConstructorRedirectData>();
  
  private static final String KEY_FUZZ = "fuzz";
  
  private static final String KEY_OPCODE = "opcode";
  
  private static final String KEY_NOMINATORS = "nominators";
  
  protected Meta meta;
  
  public RedirectInjector(InjectionInfo paramInjectionInfo) {
    this(paramInjectionInfo, "@Redirect");
  }
  
  protected RedirectInjector(InjectionInfo paramInjectionInfo, String paramString) {
    super(paramInjectionInfo, paramString);
    int i = paramInjectionInfo.getContext().getPriority();
    boolean bool = (Annotations.getVisible(this.methodNode, Final.class) != null) ? true : false;
    this.meta = new Meta(i, bool, this.info.toString(), this.methodNode.desc);
  }
  
  protected void checkTarget(Target paramTarget) {}
  
  protected void addTargetNode(Target paramTarget, List<InjectionNodes.InjectionNode> paramList, AbstractInsnNode paramAbstractInsnNode, Set<InjectionPoint> paramSet) {
    InjectionNodes.InjectionNode injectionNode1 = paramTarget.getInjectionNode(paramAbstractInsnNode);
    ConstructorRedirectData constructorRedirectData = null;
    int i = 8;
    int j = 0;
    if (injectionNode1 != null) {
      Meta meta = (Meta)injectionNode1.getDecoration("redirector");
      if (meta != null && meta.getOwner() != this) {
        if (meta.priority >= this.meta.priority) {
          Injector.logger.warn("{} conflict. Skipping {} with priority {}, already redirected by {} with priority {}", new Object[] { this.annotationType, this.info, 
                Integer.valueOf(this.meta.priority), meta.name, Integer.valueOf(meta.priority) });
          return;
        } 
        if (meta.isFinal)
          throw new InvalidInjectionException(this.info, String.format("%s conflict: %s failed because target was already remapped by %s", new Object[] { this.annotationType, this, meta.name })); 
      } 
    } 
    for (InjectionPoint injectionPoint : paramSet) {
      if (injectionPoint instanceof BeforeNew) {
        constructorRedirectData = getCtorRedirect((BeforeNew)injectionPoint);
        constructorRedirectData.wildcard = !((BeforeNew)injectionPoint).hasDescriptor();
        continue;
      } 
      if (injectionPoint instanceof BeforeFieldAccess) {
        BeforeFieldAccess beforeFieldAccess = (BeforeFieldAccess)injectionPoint;
        i = beforeFieldAccess.getFuzzFactor();
        j = beforeFieldAccess.getArrayOpcode();
      } 
    } 
    InjectionNodes.InjectionNode injectionNode2 = paramTarget.addInjectionNode(paramAbstractInsnNode);
    injectionNode2.decorate("redirector", this.meta);
    injectionNode2.decorate("nominators", paramSet);
    if (paramAbstractInsnNode instanceof TypeInsnNode && paramAbstractInsnNode.getOpcode() == 187) {
      injectionNode2.decorate("ctor", constructorRedirectData);
    } else {
      injectionNode2.decorate("fuzz", Integer.valueOf(i));
      injectionNode2.decorate("opcode", Integer.valueOf(j));
    } 
    paramList.add(injectionNode2);
  }
  
  private ConstructorRedirectData getCtorRedirect(BeforeNew paramBeforeNew) {
    ConstructorRedirectData constructorRedirectData = this.ctorRedirectors.get(paramBeforeNew);
    if (constructorRedirectData == null) {
      constructorRedirectData = new ConstructorRedirectData();
      this.ctorRedirectors.put(paramBeforeNew, constructorRedirectData);
    } 
    return constructorRedirectData;
  }
  
  protected void inject(Target paramTarget, InjectionNodes.InjectionNode paramInjectionNode) {
    if (!preInject(paramInjectionNode))
      return; 
    if (paramInjectionNode.isReplaced())
      throw new UnsupportedOperationException("Redirector target failure for " + this.info); 
    if (paramInjectionNode.getCurrentTarget() instanceof MethodInsnNode) {
      checkTargetForNode(paramTarget, paramInjectionNode);
      injectAtInvoke(paramTarget, paramInjectionNode);
      return;
    } 
    if (paramInjectionNode.getCurrentTarget() instanceof FieldInsnNode) {
      checkTargetForNode(paramTarget, paramInjectionNode);
      injectAtFieldAccess(paramTarget, paramInjectionNode);
      return;
    } 
    if (paramInjectionNode.getCurrentTarget() instanceof TypeInsnNode && paramInjectionNode.getCurrentTarget().getOpcode() == 187) {
      if (!this.isStatic && paramTarget.isStatic)
        throw new InvalidInjectionException(this.info, String.format("non-static callback method %s has a static target which is not supported", new Object[] { this })); 
      injectAtConstructor(paramTarget, paramInjectionNode);
      return;
    } 
    throw new InvalidInjectionException(this.info, String.format("%s annotation on is targetting an invalid insn in %s in %s", new Object[] { this.annotationType, paramTarget, this }));
  }
  
  protected boolean preInject(InjectionNodes.InjectionNode paramInjectionNode) {
    Meta meta = (Meta)paramInjectionNode.getDecoration("redirector");
    if (meta.getOwner() != this) {
      Injector.logger.warn("{} conflict. Skipping {} with priority {}, already redirected by {} with priority {}", new Object[] { this.annotationType, this.info, 
            Integer.valueOf(this.meta.priority), meta.name, Integer.valueOf(meta.priority) });
      return false;
    } 
    return true;
  }
  
  protected void postInject(Target paramTarget, InjectionNodes.InjectionNode paramInjectionNode) {
    super.postInject(paramTarget, paramInjectionNode);
    if (paramInjectionNode.getOriginalTarget() instanceof TypeInsnNode && paramInjectionNode.getOriginalTarget().getOpcode() == 187) {
      ConstructorRedirectData constructorRedirectData = (ConstructorRedirectData)paramInjectionNode.getDecoration("ctor");
      if (constructorRedirectData.wildcard && constructorRedirectData.injected == 0)
        throw new InvalidInjectionException(this.info, String.format("%s ctor invocation was not found in %s", new Object[] { this.annotationType, paramTarget })); 
    } 
  }
  
  protected void injectAtInvoke(Target paramTarget, InjectionNodes.InjectionNode paramInjectionNode) {
    RedirectedInvoke redirectedInvoke = new RedirectedInvoke(paramTarget, (MethodInsnNode)paramInjectionNode.getCurrentTarget());
    validateParams(redirectedInvoke);
    InsnList insnList = new InsnList();
    int i = Bytecode.getArgsSize(redirectedInvoke.locals) + 1;
    int j = 1;
    int[] arrayOfInt = storeArgs(paramTarget, redirectedInvoke.locals, insnList, 0);
    if (redirectedInvoke.captureTargetArgs) {
      int k = Bytecode.getArgsSize(paramTarget.arguments);
      i += k;
      j += k;
      arrayOfInt = Ints.concat(new int[][] { arrayOfInt, paramTarget.getArgIndices() });
    } 
    AbstractInsnNode abstractInsnNode = invokeHandlerWithArgs(this.methodArgs, insnList, arrayOfInt);
    paramTarget.replaceNode((AbstractInsnNode)redirectedInvoke.node, abstractInsnNode, insnList);
    paramTarget.addToLocals(i);
    paramTarget.addToStack(j);
  }
  
  protected void validateParams(RedirectedInvoke paramRedirectedInvoke) {
    int i = this.methodArgs.length;
    String str = String.format("%s handler method %s", new Object[] { this.annotationType, this });
    if (!paramRedirectedInvoke.returnType.equals(this.returnType))
      throw new InvalidInjectionException(this.info, String.format("%s has an invalid signature. Expected return type %s found %s", new Object[] { str, this.returnType, paramRedirectedInvoke.returnType })); 
    for (byte b = 0; b < i; b++) {
      Type type1 = null;
      if (b >= this.methodArgs.length)
        throw new InvalidInjectionException(this.info, String.format("%s has an invalid signature. Not enough arguments found for capture of target method args, expected %d but found %d", new Object[] { str, 
                
                Integer.valueOf(i), Integer.valueOf(this.methodArgs.length) })); 
      Type type2 = this.methodArgs[b];
      if (b < paramRedirectedInvoke.locals.length) {
        type1 = paramRedirectedInvoke.locals[b];
      } else {
        paramRedirectedInvoke.captureTargetArgs = true;
        i = Math.max(i, paramRedirectedInvoke.locals.length + paramRedirectedInvoke.target.arguments.length);
        int j = b - paramRedirectedInvoke.locals.length;
        if (j >= paramRedirectedInvoke.target.arguments.length)
          throw new InvalidInjectionException(this.info, String.format("%s has an invalid signature. Found unexpected additional target argument with type %s at index %d", new Object[] { str, type2, 
                  
                  Integer.valueOf(b) })); 
        type1 = paramRedirectedInvoke.target.arguments[j];
      } 
      AnnotationNode annotationNode = Annotations.getInvisibleParameter(this.methodNode, Coerce.class, b);
      if (type2.equals(type1)) {
        if (annotationNode != null && this.info.getContext().getOption(MixinEnvironment.Option.DEBUG_VERBOSE))
          Injector.logger.warn("Redundant @Coerce on {} argument {}, {} is identical to {}", new Object[] { str, Integer.valueOf(b), type1, type2 }); 
      } else {
        boolean bool = Injector.canCoerce(type2, type1);
        if (annotationNode == null)
          throw new InvalidInjectionException(this.info, String.format("%s has an invalid signature. Found unexpected argument type %s at index %d, expected %s", new Object[] { str, type2, 
                  
                  Integer.valueOf(b), type1 })); 
        if (!bool)
          throw new InvalidInjectionException(this.info, String.format("%s has an invalid signature. Cannot @Coerce argument type %s at index %d to %s", new Object[] { str, type1, 
                  
                  Integer.valueOf(b), type2 })); 
      } 
    } 
  }
  
  private void injectAtFieldAccess(Target paramTarget, InjectionNodes.InjectionNode paramInjectionNode) {
    FieldInsnNode fieldInsnNode = (FieldInsnNode)paramInjectionNode.getCurrentTarget();
    int i = fieldInsnNode.getOpcode();
    Type type1 = Type.getType("L" + fieldInsnNode.owner + ";");
    Type type2 = Type.getType(fieldInsnNode.desc);
    byte b1 = (type2.getSort() == 9) ? type2.getDimensions() : 0;
    byte b2 = (this.returnType.getSort() == 9) ? this.returnType.getDimensions() : 0;
    if (b2 > b1)
      throw new InvalidInjectionException(this.info, "Dimensionality of handler method is greater than target array on " + this); 
    if (b2 == 0 && b1 > 0) {
      int j = ((Integer)paramInjectionNode.getDecoration("fuzz")).intValue();
      int k = ((Integer)paramInjectionNode.getDecoration("opcode")).intValue();
      injectAtArrayField(paramTarget, fieldInsnNode, i, type1, type2, j, k);
    } else {
      injectAtScalarField(paramTarget, fieldInsnNode, i, type1, type2);
    } 
  }
  
  private void injectAtArrayField(Target paramTarget, FieldInsnNode paramFieldInsnNode, int paramInt1, Type paramType1, Type paramType2, int paramInt2, int paramInt3) {
    Type type = paramType2.getElementType();
    if (paramInt1 != 178 && paramInt1 != 180)
      throw new InvalidInjectionException(this.info, String.format("Unspported opcode %s for array access %s", new Object[] { Bytecode.getOpcodeName(paramInt1), this.info })); 
    if (this.returnType.getSort() != 0) {
      if (paramInt3 != 190)
        paramInt3 = type.getOpcode(46); 
      AbstractInsnNode abstractInsnNode = BeforeFieldAccess.findArrayNode(paramTarget.insns, paramFieldInsnNode, paramInt3, paramInt2);
      injectAtGetArray(paramTarget, paramFieldInsnNode, abstractInsnNode, paramType1, paramType2);
    } else {
      AbstractInsnNode abstractInsnNode = BeforeFieldAccess.findArrayNode(paramTarget.insns, paramFieldInsnNode, type.getOpcode(79), paramInt2);
      injectAtSetArray(paramTarget, paramFieldInsnNode, abstractInsnNode, paramType1, paramType2);
    } 
  }
  
  private void injectAtGetArray(Target paramTarget, FieldInsnNode paramFieldInsnNode, AbstractInsnNode paramAbstractInsnNode, Type paramType1, Type paramType2) {
    String str = getGetArrayHandlerDescriptor(paramAbstractInsnNode, this.returnType, paramType2);
    boolean bool = checkDescriptor(str, paramTarget, "array getter");
    injectArrayRedirect(paramTarget, paramFieldInsnNode, paramAbstractInsnNode, bool, "array getter");
  }
  
  private void injectAtSetArray(Target paramTarget, FieldInsnNode paramFieldInsnNode, AbstractInsnNode paramAbstractInsnNode, Type paramType1, Type paramType2) {
    String str = Bytecode.generateDescriptor(null, (Object[])getArrayArgs(paramType2, 1, new Type[] { paramType2.getElementType() }));
    boolean bool = checkDescriptor(str, paramTarget, "array setter");
    injectArrayRedirect(paramTarget, paramFieldInsnNode, paramAbstractInsnNode, bool, "array setter");
  }
  
  public void injectArrayRedirect(Target paramTarget, FieldInsnNode paramFieldInsnNode, AbstractInsnNode paramAbstractInsnNode, boolean paramBoolean, String paramString) {
    if (paramAbstractInsnNode == null) {
      String str = "";
      throw new InvalidInjectionException(this.info, String.format("Array element %s on %s could not locate a matching %s instruction in %s. %s", new Object[] { this.annotationType, this, paramString, paramTarget, str }));
    } 
    if (!this.isStatic) {
      paramTarget.insns.insertBefore((AbstractInsnNode)paramFieldInsnNode, (AbstractInsnNode)new VarInsnNode(25, 0));
      paramTarget.addToStack(1);
    } 
    InsnList insnList = new InsnList();
    if (paramBoolean) {
      pushArgs(paramTarget.arguments, insnList, paramTarget.getArgIndices(), 0, paramTarget.arguments.length);
      paramTarget.addToStack(Bytecode.getArgsSize(paramTarget.arguments));
    } 
    paramTarget.replaceNode(paramAbstractInsnNode, invokeHandler(insnList), insnList);
  }
  
  public void injectAtScalarField(Target paramTarget, FieldInsnNode paramFieldInsnNode, int paramInt, Type paramType1, Type paramType2) {
    AbstractInsnNode abstractInsnNode = null;
    InsnList insnList = new InsnList();
    if (paramInt == 178 || paramInt == 180) {
      abstractInsnNode = injectAtGetField(insnList, paramTarget, paramFieldInsnNode, (paramInt == 178), paramType1, paramType2);
    } else if (paramInt == 179 || paramInt == 181) {
      abstractInsnNode = injectAtPutField(insnList, paramTarget, paramFieldInsnNode, (paramInt == 179), paramType1, paramType2);
    } else {
      throw new InvalidInjectionException(this.info, String.format("Unspported opcode %s for %s", new Object[] { Bytecode.getOpcodeName(paramInt), this.info }));
    } 
    paramTarget.replaceNode((AbstractInsnNode)paramFieldInsnNode, abstractInsnNode, insnList);
  }
  
  private AbstractInsnNode injectAtGetField(InsnList paramInsnList, Target paramTarget, FieldInsnNode paramFieldInsnNode, boolean paramBoolean, Type paramType1, Type paramType2) {
    String str = paramBoolean ? Bytecode.generateDescriptor(paramType2, new Object[0]) : Bytecode.generateDescriptor(paramType2, new Object[] { paramType1 });
    boolean bool = checkDescriptor(str, paramTarget, "getter");
    if (!this.isStatic) {
      paramInsnList.add((AbstractInsnNode)new VarInsnNode(25, 0));
      if (!paramBoolean)
        paramInsnList.add((AbstractInsnNode)new InsnNode(95)); 
    } 
    if (bool) {
      pushArgs(paramTarget.arguments, paramInsnList, paramTarget.getArgIndices(), 0, paramTarget.arguments.length);
      paramTarget.addToStack(Bytecode.getArgsSize(paramTarget.arguments));
    } 
    paramTarget.addToStack(this.isStatic ? 0 : 1);
    return invokeHandler(paramInsnList);
  }
  
  private AbstractInsnNode injectAtPutField(InsnList paramInsnList, Target paramTarget, FieldInsnNode paramFieldInsnNode, boolean paramBoolean, Type paramType1, Type paramType2) {
    String str = paramBoolean ? Bytecode.generateDescriptor(null, new Object[] { paramType2 }) : Bytecode.generateDescriptor(null, new Object[] { paramType1, paramType2 });
    boolean bool = checkDescriptor(str, paramTarget, "setter");
    if (!this.isStatic)
      if (paramBoolean) {
        paramInsnList.add((AbstractInsnNode)new VarInsnNode(25, 0));
        paramInsnList.add((AbstractInsnNode)new InsnNode(95));
      } else {
        int i = paramTarget.allocateLocals(paramType2.getSize());
        paramInsnList.add((AbstractInsnNode)new VarInsnNode(paramType2.getOpcode(54), i));
        paramInsnList.add((AbstractInsnNode)new VarInsnNode(25, 0));
        paramInsnList.add((AbstractInsnNode)new InsnNode(95));
        paramInsnList.add((AbstractInsnNode)new VarInsnNode(paramType2.getOpcode(21), i));
      }  
    if (bool) {
      pushArgs(paramTarget.arguments, paramInsnList, paramTarget.getArgIndices(), 0, paramTarget.arguments.length);
      paramTarget.addToStack(Bytecode.getArgsSize(paramTarget.arguments));
    } 
    paramTarget.addToStack((!this.isStatic && !paramBoolean) ? 1 : 0);
    return invokeHandler(paramInsnList);
  }
  
  protected boolean checkDescriptor(String paramString1, Target paramTarget, String paramString2) {
    if (this.methodNode.desc.equals(paramString1))
      return false; 
    int i = paramString1.indexOf(')');
    String str = String.format("%s%s%s", new Object[] { paramString1.substring(0, i), Joiner.on("").join((Object[])paramTarget.arguments), paramString1.substring(i) });
    if (this.methodNode.desc.equals(str))
      return true; 
    throw new InvalidInjectionException(this.info, String.format("%s method %s %s has an invalid signature. Expected %s but found %s", new Object[] { this.annotationType, paramString2, this, paramString1, this.methodNode.desc }));
  }
  
  protected void injectAtConstructor(Target paramTarget, InjectionNodes.InjectionNode paramInjectionNode) {
    ConstructorRedirectData constructorRedirectData = (ConstructorRedirectData)paramInjectionNode.getDecoration("ctor");
    if (constructorRedirectData == null)
      throw new InvalidInjectionException(this.info, String.format("%s ctor redirector has no metadata, the injector failed a preprocessing phase", new Object[] { this.annotationType })); 
    TypeInsnNode typeInsnNode = (TypeInsnNode)paramInjectionNode.getCurrentTarget();
    AbstractInsnNode abstractInsnNode = paramTarget.get(paramTarget.indexOf((AbstractInsnNode)typeInsnNode) + 1);
    MethodInsnNode methodInsnNode = paramTarget.findInitNodeFor(typeInsnNode);
    if (methodInsnNode == null) {
      if (!constructorRedirectData.wildcard)
        throw new InvalidInjectionException(this.info, String.format("%s ctor invocation was not found in %s", new Object[] { this.annotationType, paramTarget })); 
      return;
    } 
    boolean bool = (abstractInsnNode.getOpcode() == 89) ? true : false;
    String str = methodInsnNode.desc.replace(")V", ")L" + typeInsnNode.desc + ";");
    boolean bool1 = false;
    try {
      bool1 = checkDescriptor(str, paramTarget, "constructor");
    } catch (InvalidInjectionException invalidInjectionException) {
      if (!constructorRedirectData.wildcard)
        throw invalidInjectionException; 
      return;
    } 
    if (bool)
      paramTarget.removeNode(abstractInsnNode); 
    if (this.isStatic) {
      paramTarget.removeNode((AbstractInsnNode)typeInsnNode);
    } else {
      paramTarget.replaceNode((AbstractInsnNode)typeInsnNode, (AbstractInsnNode)new VarInsnNode(25, 0));
    } 
    InsnList insnList = new InsnList();
    if (bool1) {
      pushArgs(paramTarget.arguments, insnList, paramTarget.getArgIndices(), 0, paramTarget.arguments.length);
      paramTarget.addToStack(Bytecode.getArgsSize(paramTarget.arguments));
    } 
    invokeHandler(insnList);
    if (bool) {
      LabelNode labelNode = new LabelNode();
      insnList.add((AbstractInsnNode)new InsnNode(89));
      insnList.add((AbstractInsnNode)new JumpInsnNode(199, labelNode));
      throwException(insnList, "java/lang/NullPointerException", String.format("%s constructor handler %s returned null for %s", new Object[] { this.annotationType, this, typeInsnNode.desc
              .replace('/', '.') }));
      insnList.add((AbstractInsnNode)labelNode);
      paramTarget.addToStack(1);
    } else {
      insnList.add((AbstractInsnNode)new InsnNode(87));
    } 
    paramTarget.replaceNode((AbstractInsnNode)methodInsnNode, insnList);
    constructorRedirectData.injected++;
  }
  
  private static String getGetArrayHandlerDescriptor(AbstractInsnNode paramAbstractInsnNode, Type paramType1, Type paramType2) {
    if (paramAbstractInsnNode != null && paramAbstractInsnNode.getOpcode() == 190)
      return Bytecode.generateDescriptor(Type.INT_TYPE, (Object[])getArrayArgs(paramType2, 0, new Type[0])); 
    return Bytecode.generateDescriptor(paramType1, (Object[])getArrayArgs(paramType2, 1, new Type[0]));
  }
  
  private static Type[] getArrayArgs(Type paramType, int paramInt, Type... paramVarArgs) {
    int i = paramType.getDimensions() + paramInt;
    Type[] arrayOfType = new Type[i + paramVarArgs.length];
    for (byte b = 0; b < arrayOfType.length; b++)
      arrayOfType[b] = (b == 0) ? paramType : ((b < i) ? Type.INT_TYPE : paramVarArgs[i - b]); 
    return arrayOfType;
  }
}
