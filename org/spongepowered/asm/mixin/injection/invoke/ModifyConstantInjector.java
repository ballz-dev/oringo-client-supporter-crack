package org.spongepowered.asm.mixin.injection.invoke;

import org.apache.logging.log4j.Level;
import org.spongepowered.asm.lib.Type;
import org.spongepowered.asm.lib.tree.AbstractInsnNode;
import org.spongepowered.asm.lib.tree.FieldInsnNode;
import org.spongepowered.asm.lib.tree.InsnList;
import org.spongepowered.asm.lib.tree.InsnNode;
import org.spongepowered.asm.lib.tree.JumpInsnNode;
import org.spongepowered.asm.lib.tree.LocalVariableNode;
import org.spongepowered.asm.lib.tree.VarInsnNode;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.injection.code.Injector;
import org.spongepowered.asm.mixin.injection.invoke.util.InsnFinder;
import org.spongepowered.asm.mixin.injection.struct.InjectionInfo;
import org.spongepowered.asm.mixin.injection.struct.InjectionNodes;
import org.spongepowered.asm.mixin.injection.struct.Target;
import org.spongepowered.asm.mixin.injection.throwables.InvalidInjectionException;
import org.spongepowered.asm.util.Bytecode;
import org.spongepowered.asm.util.Locals;
import org.spongepowered.asm.util.SignaturePrinter;

public class ModifyConstantInjector extends RedirectInjector {
  private static final int OPCODE_OFFSET = 6;
  
  public ModifyConstantInjector(InjectionInfo paramInjectionInfo) {
    super(paramInjectionInfo, "@ModifyConstant");
  }
  
  protected void inject(Target paramTarget, InjectionNodes.InjectionNode paramInjectionNode) {
    if (!preInject(paramInjectionNode))
      return; 
    if (paramInjectionNode.isReplaced())
      throw new UnsupportedOperationException("Target failure for " + this.info); 
    AbstractInsnNode abstractInsnNode = paramInjectionNode.getCurrentTarget();
    if (abstractInsnNode instanceof JumpInsnNode) {
      checkTargetModifiers(paramTarget, false);
      injectExpandedConstantModifier(paramTarget, (JumpInsnNode)abstractInsnNode);
      return;
    } 
    if (Bytecode.isConstant(abstractInsnNode)) {
      checkTargetModifiers(paramTarget, false);
      injectConstantModifier(paramTarget, abstractInsnNode);
      return;
    } 
    throw new InvalidInjectionException(this.info, this.annotationType + " annotation is targetting an invalid insn in " + paramTarget + " in " + this);
  }
  
  private void injectExpandedConstantModifier(Target paramTarget, JumpInsnNode paramJumpInsnNode) {
    int i = paramJumpInsnNode.getOpcode();
    if (i < 155 || i > 158)
      throw new InvalidInjectionException(this.info, this.annotationType + " annotation selected an invalid opcode " + 
          Bytecode.getOpcodeName(i) + " in " + paramTarget + " in " + this); 
    InsnList insnList = new InsnList();
    insnList.add((AbstractInsnNode)new InsnNode(3));
    AbstractInsnNode abstractInsnNode = invokeConstantHandler(Type.getType("I"), paramTarget, insnList, insnList);
    insnList.add((AbstractInsnNode)new JumpInsnNode(i + 6, paramJumpInsnNode.label));
    paramTarget.replaceNode((AbstractInsnNode)paramJumpInsnNode, abstractInsnNode, insnList);
    paramTarget.addToStack(1);
  }
  
  private void injectConstantModifier(Target paramTarget, AbstractInsnNode paramAbstractInsnNode) {
    Type type = Bytecode.getConstantType(paramAbstractInsnNode);
    if (type.getSort() <= 5 && this.info.getContext().getOption(MixinEnvironment.Option.DEBUG_VERBOSE))
      checkNarrowing(paramTarget, paramAbstractInsnNode, type); 
    InsnList insnList1 = new InsnList();
    InsnList insnList2 = new InsnList();
    AbstractInsnNode abstractInsnNode = invokeConstantHandler(type, paramTarget, insnList1, insnList2);
    paramTarget.wrapNode(paramAbstractInsnNode, abstractInsnNode, insnList1, insnList2);
  }
  
  private AbstractInsnNode invokeConstantHandler(Type paramType, Target paramTarget, InsnList paramInsnList1, InsnList paramInsnList2) {
    String str = Bytecode.generateDescriptor(paramType, new Object[] { paramType });
    boolean bool = checkDescriptor(str, paramTarget, "getter");
    if (!this.isStatic) {
      paramInsnList1.insert((AbstractInsnNode)new VarInsnNode(25, 0));
      paramTarget.addToStack(1);
    } 
    if (bool) {
      pushArgs(paramTarget.arguments, paramInsnList2, paramTarget.getArgIndices(), 0, paramTarget.arguments.length);
      paramTarget.addToStack(Bytecode.getArgsSize(paramTarget.arguments));
    } 
    return invokeHandler(paramInsnList2);
  }
  
  private void checkNarrowing(Target paramTarget, AbstractInsnNode paramAbstractInsnNode, Type paramType) {
    AbstractInsnNode abstractInsnNode = (new InsnFinder()).findPopInsn(paramTarget, paramAbstractInsnNode);
    if (abstractInsnNode == null)
      return; 
    if (abstractInsnNode instanceof FieldInsnNode) {
      FieldInsnNode fieldInsnNode = (FieldInsnNode)abstractInsnNode;
      Type type = Type.getType(fieldInsnNode.desc);
      checkNarrowing(paramTarget, paramAbstractInsnNode, paramType, type, paramTarget.indexOf(abstractInsnNode), String.format("%s %s %s.%s", new Object[] { Bytecode.getOpcodeName(abstractInsnNode), SignaturePrinter.getTypeName(type, false), fieldInsnNode.owner.replace('/', '.'), fieldInsnNode.name }));
    } else if (abstractInsnNode.getOpcode() == 172) {
      checkNarrowing(paramTarget, paramAbstractInsnNode, paramType, paramTarget.returnType, paramTarget.indexOf(abstractInsnNode), "RETURN " + 
          SignaturePrinter.getTypeName(paramTarget.returnType, false));
    } else if (abstractInsnNode.getOpcode() == 54) {
      int i = ((VarInsnNode)abstractInsnNode).var;
      LocalVariableNode localVariableNode = Locals.getLocalVariableAt(paramTarget.classNode, paramTarget.method, abstractInsnNode, i);
      if (localVariableNode != null && localVariableNode.desc != null) {
        String str = (localVariableNode.name != null) ? localVariableNode.name : "unnamed";
        Type type = Type.getType(localVariableNode.desc);
        checkNarrowing(paramTarget, paramAbstractInsnNode, paramType, type, paramTarget.indexOf(abstractInsnNode), String.format("ISTORE[var=%d] %s %s", new Object[] { Integer.valueOf(i), 
                SignaturePrinter.getTypeName(type, false), str }));
      } 
    } 
  }
  
  private void checkNarrowing(Target paramTarget, AbstractInsnNode paramAbstractInsnNode, Type paramType1, Type paramType2, int paramInt, String paramString) {
    int i = paramType1.getSort();
    int j = paramType2.getSort();
    if (j < i) {
      String str1 = SignaturePrinter.getTypeName(paramType1, false);
      String str2 = SignaturePrinter.getTypeName(paramType2, false);
      String str3 = (j == 1) ? ". Implicit conversion to <boolean> can cause nondeterministic (JVM-specific) behaviour!" : "";
      Level level = (j == 1) ? Level.ERROR : Level.WARN;
      Injector.logger.log(level, "Narrowing conversion of <{}> to <{}> in {} target {} at opcode {} ({}){}", new Object[] { str1, str2, this.info, paramTarget, 
            Integer.valueOf(paramInt), paramString, str3 });
    } 
  }
}
