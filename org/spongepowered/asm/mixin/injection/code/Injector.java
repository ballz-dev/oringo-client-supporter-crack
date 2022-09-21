package org.spongepowered.asm.mixin.injection.code;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.lib.Type;
import org.spongepowered.asm.lib.tree.AbstractInsnNode;
import org.spongepowered.asm.lib.tree.ClassNode;
import org.spongepowered.asm.lib.tree.InsnList;
import org.spongepowered.asm.lib.tree.InsnNode;
import org.spongepowered.asm.lib.tree.LdcInsnNode;
import org.spongepowered.asm.lib.tree.MethodInsnNode;
import org.spongepowered.asm.lib.tree.MethodNode;
import org.spongepowered.asm.lib.tree.TypeInsnNode;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.injection.InjectionPoint;
import org.spongepowered.asm.mixin.injection.struct.InjectionInfo;
import org.spongepowered.asm.mixin.injection.struct.InjectionNodes;
import org.spongepowered.asm.mixin.injection.struct.Target;
import org.spongepowered.asm.mixin.injection.throwables.InvalidInjectionException;
import org.spongepowered.asm.mixin.refmap.IMixinContext;
import org.spongepowered.asm.mixin.transformer.ClassInfo;
import org.spongepowered.asm.util.Bytecode;

public abstract class Injector {
  protected final Type returnType;
  
  protected final boolean isStatic;
  
  protected InjectionInfo info;
  
  protected final MethodNode methodNode;
  
  protected final Type[] methodArgs;
  
  protected final ClassNode classNode;
  
  public static final class TargetNode {
    final AbstractInsnNode insn;
    
    final Set<InjectionPoint> nominators = new HashSet<InjectionPoint>();
    
    TargetNode(AbstractInsnNode param1AbstractInsnNode) {
      this.insn = param1AbstractInsnNode;
    }
    
    public AbstractInsnNode getNode() {
      return this.insn;
    }
    
    public Set<InjectionPoint> getNominators() {
      return Collections.unmodifiableSet(this.nominators);
    }
    
    public boolean equals(Object param1Object) {
      if (param1Object == null || param1Object.getClass() != TargetNode.class)
        return false; 
      return (((TargetNode)param1Object).insn == this.insn);
    }
    
    public int hashCode() {
      return this.insn.hashCode();
    }
  }
  
  protected static final Logger logger = LogManager.getLogger("mixin");
  
  public Injector(InjectionInfo paramInjectionInfo) {
    this(paramInjectionInfo.getClassNode(), paramInjectionInfo.getMethod());
    this.info = paramInjectionInfo;
  }
  
  private Injector(ClassNode paramClassNode, MethodNode paramMethodNode) {
    this.classNode = paramClassNode;
    this.methodNode = paramMethodNode;
    this.methodArgs = Type.getArgumentTypes(this.methodNode.desc);
    this.returnType = Type.getReturnType(this.methodNode.desc);
    this.isStatic = Bytecode.methodIsStatic(this.methodNode);
  }
  
  public String toString() {
    return String.format("%s::%s", new Object[] { this.classNode.name, this.methodNode.name });
  }
  
  public final List<InjectionNodes.InjectionNode> find(InjectorTarget paramInjectorTarget, List<InjectionPoint> paramList) {
    sanityCheck(paramInjectorTarget.getTarget(), paramList);
    ArrayList<InjectionNodes.InjectionNode> arrayList = new ArrayList();
    for (TargetNode targetNode : findTargetNodes(paramInjectorTarget, paramList))
      addTargetNode(paramInjectorTarget.getTarget(), arrayList, targetNode.insn, targetNode.nominators); 
    return arrayList;
  }
  
  protected void addTargetNode(Target paramTarget, List<InjectionNodes.InjectionNode> paramList, AbstractInsnNode paramAbstractInsnNode, Set<InjectionPoint> paramSet) {
    paramList.add(paramTarget.addInjectionNode(paramAbstractInsnNode));
  }
  
  public final void inject(Target paramTarget, List<InjectionNodes.InjectionNode> paramList) {
    for (InjectionNodes.InjectionNode injectionNode : paramList) {
      if (injectionNode.isRemoved()) {
        if (this.info.getContext().getOption(MixinEnvironment.Option.DEBUG_VERBOSE))
          logger.warn("Target node for {} was removed by a previous injector in {}", new Object[] { this.info, paramTarget }); 
        continue;
      } 
      inject(paramTarget, injectionNode);
    } 
    for (InjectionNodes.InjectionNode injectionNode : paramList)
      postInject(paramTarget, injectionNode); 
  }
  
  private Collection<TargetNode> findTargetNodes(InjectorTarget paramInjectorTarget, List<InjectionPoint> paramList) {
    IMixinContext iMixinContext = this.info.getContext();
    MethodNode methodNode = paramInjectorTarget.getMethod();
    TreeMap<Object, Object> treeMap = new TreeMap<Object, Object>();
    ArrayList<AbstractInsnNode> arrayList = new ArrayList(32);
    for (InjectionPoint injectionPoint : paramList) {
      arrayList.clear();
      if (paramInjectorTarget.isMerged() && 
        !iMixinContext.getClassName().equals(paramInjectorTarget.getMergedBy()) && 
        !injectionPoint.checkPriority(paramInjectorTarget.getMergedPriority(), iMixinContext.getPriority()))
        throw new InvalidInjectionException(this.info, String.format("%s on %s with priority %d cannot inject into %s merged by %s with priority %d", new Object[] { injectionPoint, this, 
                Integer.valueOf(iMixinContext.getPriority()), paramInjectorTarget, paramInjectorTarget
                .getMergedBy(), Integer.valueOf(paramInjectorTarget.getMergedPriority()) })); 
      if (findTargetNodes(methodNode, injectionPoint, paramInjectorTarget.getSlice(injectionPoint), arrayList))
        for (AbstractInsnNode abstractInsnNode : arrayList) {
          Integer integer = Integer.valueOf(methodNode.instructions.indexOf(abstractInsnNode));
          TargetNode targetNode = (TargetNode)treeMap.get(integer);
          if (targetNode == null) {
            targetNode = new TargetNode(abstractInsnNode);
            treeMap.put(integer, targetNode);
          } 
          targetNode.nominators.add(injectionPoint);
        }  
    } 
    return treeMap.values();
  }
  
  protected boolean findTargetNodes(MethodNode paramMethodNode, InjectionPoint paramInjectionPoint, InsnList paramInsnList, Collection<AbstractInsnNode> paramCollection) {
    return paramInjectionPoint.find(paramMethodNode.desc, paramInsnList, paramCollection);
  }
  
  protected void sanityCheck(Target paramTarget, List<InjectionPoint> paramList) {
    if (paramTarget.classNode != this.classNode)
      throw new InvalidInjectionException(this.info, "Target class does not match injector class in " + this); 
  }
  
  protected void postInject(Target paramTarget, InjectionNodes.InjectionNode paramInjectionNode) {}
  
  protected AbstractInsnNode invokeHandler(InsnList paramInsnList) {
    return invokeHandler(paramInsnList, this.methodNode);
  }
  
  protected AbstractInsnNode invokeHandler(InsnList paramInsnList, MethodNode paramMethodNode) {
    boolean bool = ((paramMethodNode.access & 0x2) != 0) ? true : false;
    char c = this.isStatic ? '¸' : (bool ? '·' : '¶');
    MethodInsnNode methodInsnNode = new MethodInsnNode(c, this.classNode.name, paramMethodNode.name, paramMethodNode.desc, false);
    paramInsnList.add((AbstractInsnNode)methodInsnNode);
    this.info.addCallbackInvocation(paramMethodNode);
    return (AbstractInsnNode)methodInsnNode;
  }
  
  protected void throwException(InsnList paramInsnList, String paramString1, String paramString2) {
    paramInsnList.add((AbstractInsnNode)new TypeInsnNode(187, paramString1));
    paramInsnList.add((AbstractInsnNode)new InsnNode(89));
    paramInsnList.add((AbstractInsnNode)new LdcInsnNode(paramString2));
    paramInsnList.add((AbstractInsnNode)new MethodInsnNode(183, paramString1, "<init>", "(Ljava/lang/String;)V", false));
    paramInsnList.add((AbstractInsnNode)new InsnNode(191));
  }
  
  public static boolean canCoerce(Type paramType1, Type paramType2) {
    if (paramType1.getSort() == 10 && paramType2.getSort() == 10)
      return canCoerce(ClassInfo.forType(paramType1), ClassInfo.forType(paramType2)); 
    return canCoerce(paramType1.getDescriptor(), paramType2.getDescriptor());
  }
  
  public static boolean canCoerce(String paramString1, String paramString2) {
    if (paramString1.length() > 1 || paramString2.length() > 1)
      return false; 
    return canCoerce(paramString1.charAt(0), paramString2.charAt(0));
  }
  
  public static boolean canCoerce(char paramChar1, char paramChar2) {
    return (paramChar2 == 'I' && "IBSCZ".indexOf(paramChar1) > -1);
  }
  
  private static boolean canCoerce(ClassInfo paramClassInfo1, ClassInfo paramClassInfo2) {
    return (paramClassInfo1 != null && paramClassInfo2 != null && (paramClassInfo2 == paramClassInfo1 || paramClassInfo2.hasSuperClass(paramClassInfo1)));
  }
  
  protected abstract void inject(Target paramTarget, InjectionNodes.InjectionNode paramInjectionNode);
}
