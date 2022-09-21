package org.spongepowered.asm.mixin.injection.modify;

import java.util.Collection;
import java.util.List;
import org.spongepowered.asm.lib.Type;
import org.spongepowered.asm.lib.tree.AbstractInsnNode;
import org.spongepowered.asm.lib.tree.InsnList;
import org.spongepowered.asm.lib.tree.MethodNode;
import org.spongepowered.asm.lib.tree.VarInsnNode;
import org.spongepowered.asm.mixin.injection.InjectionPoint;
import org.spongepowered.asm.mixin.injection.code.Injector;
import org.spongepowered.asm.mixin.injection.struct.InjectionInfo;
import org.spongepowered.asm.mixin.injection.struct.InjectionNodes;
import org.spongepowered.asm.mixin.injection.struct.Target;
import org.spongepowered.asm.mixin.injection.throwables.InvalidInjectionException;
import org.spongepowered.asm.mixin.refmap.IMixinContext;
import org.spongepowered.asm.util.Bytecode;
import org.spongepowered.asm.util.PrettyPrinter;
import org.spongepowered.asm.util.SignaturePrinter;

public class ModifyVariableInjector extends Injector {
  private final LocalVariableDiscriminator discriminator;
  
  static class Context extends LocalVariableDiscriminator.Context {
    final InsnList insns = new InsnList();
    
    public Context(Type param1Type, boolean param1Boolean, Target param1Target, AbstractInsnNode param1AbstractInsnNode) {
      super(param1Type, param1Boolean, param1Target, param1AbstractInsnNode);
    }
  }
  
  static abstract class ContextualInjectionPoint extends InjectionPoint {
    protected final IMixinContext context;
    
    ContextualInjectionPoint(IMixinContext param1IMixinContext) {
      this.context = param1IMixinContext;
    }
    
    public boolean find(String param1String, InsnList param1InsnList, Collection<AbstractInsnNode> param1Collection) {
      throw new InvalidInjectionException(this.context, getAtCode() + " injection point must be used in conjunction with @ModifyVariable");
    }
    
    abstract boolean find(Target param1Target, Collection<AbstractInsnNode> param1Collection);
  }
  
  public ModifyVariableInjector(InjectionInfo paramInjectionInfo, LocalVariableDiscriminator paramLocalVariableDiscriminator) {
    super(paramInjectionInfo);
    this.discriminator = paramLocalVariableDiscriminator;
  }
  
  protected boolean findTargetNodes(MethodNode paramMethodNode, InjectionPoint paramInjectionPoint, InsnList paramInsnList, Collection<AbstractInsnNode> paramCollection) {
    if (paramInjectionPoint instanceof ContextualInjectionPoint) {
      Target target = this.info.getContext().getTargetMethod(paramMethodNode);
      return ((ContextualInjectionPoint)paramInjectionPoint).find(target, paramCollection);
    } 
    return paramInjectionPoint.find(paramMethodNode.desc, paramInsnList, paramCollection);
  }
  
  protected void sanityCheck(Target paramTarget, List<InjectionPoint> paramList) {
    super.sanityCheck(paramTarget, paramList);
    if (paramTarget.isStatic != this.isStatic)
      throw new InvalidInjectionException(this.info, "'static' of variable modifier method does not match target in " + this); 
    int i = this.discriminator.getOrdinal();
    if (i < -1)
      throw new InvalidInjectionException(this.info, "Invalid ordinal " + i + " specified in " + this); 
    if (this.discriminator.getIndex() == 0 && !this.isStatic)
      throw new InvalidInjectionException(this.info, "Invalid index 0 specified in non-static variable modifier " + this); 
  }
  
  protected void inject(Target paramTarget, InjectionNodes.InjectionNode paramInjectionNode) {
    if (paramInjectionNode.isReplaced())
      throw new InvalidInjectionException(this.info, "Variable modifier target for " + this + " was removed by another injector"); 
    Context context = new Context(this.returnType, this.discriminator.isArgsOnly(), paramTarget, paramInjectionNode.getCurrentTarget());
    if (this.discriminator.printLVT())
      printLocals(context); 
    String str = Bytecode.getDescriptor(new Type[] { this.returnType }, this.returnType);
    if (!str.equals(this.methodNode.desc))
      throw new InvalidInjectionException(this.info, "Variable modifier " + this + " has an invalid signature, expected " + str + " but found " + this.methodNode.desc); 
    try {
      int i = this.discriminator.findLocal(context);
      if (i > -1)
        inject(context, i); 
    } catch (InvalidImplicitDiscriminatorException invalidImplicitDiscriminatorException) {
      if (this.discriminator.printLVT()) {
        this.info.addCallbackInvocation(this.methodNode);
        return;
      } 
      throw new InvalidInjectionException(this.info, "Implicit variable modifier injection failed in " + this, invalidImplicitDiscriminatorException);
    } 
    paramTarget.insns.insertBefore(context.node, context.insns);
    paramTarget.addToStack(this.isStatic ? 1 : 2);
  }
  
  private void printLocals(Context paramContext) {
    SignaturePrinter signaturePrinter = new SignaturePrinter(this.methodNode.name, this.returnType, this.methodArgs, new String[] { "var" });
    signaturePrinter.setModifiers(this.methodNode);
    (new PrettyPrinter())
      .kvWidth(20)
      .kv("Target Class", this.classNode.name.replace('/', '.'))
      .kv("Target Method", paramContext.target.method.name)
      .kv("Callback Name", this.methodNode.name)
      .kv("Capture Type", SignaturePrinter.getTypeName(this.returnType, false))
      .kv("Instruction", "%s %s", new Object[] { paramContext.node.getClass().getSimpleName(), Bytecode.getOpcodeName(paramContext.node.getOpcode()) }).hr()
      .kv("Match mode", this.discriminator.isImplicit(paramContext) ? "IMPLICIT (match single)" : "EXPLICIT (match by criteria)")
      .kv("Match ordinal", (this.discriminator.getOrdinal() < 0) ? "any" : Integer.valueOf(this.discriminator.getOrdinal()))
      .kv("Match index", (this.discriminator.getIndex() < paramContext.baseArgIndex) ? "any" : Integer.valueOf(this.discriminator.getIndex()))
      .kv("Match name(s)", this.discriminator.hasNames() ? this.discriminator.getNames() : "any")
      .kv("Args only", Boolean.valueOf(this.discriminator.isArgsOnly())).hr()
      .add(paramContext)
      .print(System.err);
  }
  
  private void inject(Context paramContext, int paramInt) {
    if (!this.isStatic)
      paramContext.insns.add((AbstractInsnNode)new VarInsnNode(25, 0)); 
    paramContext.insns.add((AbstractInsnNode)new VarInsnNode(this.returnType.getOpcode(21), paramInt));
    invokeHandler(paramContext.insns);
    paramContext.insns.add((AbstractInsnNode)new VarInsnNode(this.returnType.getOpcode(54), paramInt));
  }
}
