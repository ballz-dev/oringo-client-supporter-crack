package org.spongepowered.asm.lib.tree.analysis;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.spongepowered.asm.lib.Opcodes;
import org.spongepowered.asm.lib.Type;
import org.spongepowered.asm.lib.tree.AbstractInsnNode;
import org.spongepowered.asm.lib.tree.FieldInsnNode;
import org.spongepowered.asm.lib.tree.InvokeDynamicInsnNode;
import org.spongepowered.asm.lib.tree.LdcInsnNode;
import org.spongepowered.asm.lib.tree.MethodInsnNode;

public class SourceInterpreter extends Interpreter<SourceValue> implements Opcodes {
  public SourceInterpreter() {
    super(327680);
  }
  
  protected SourceInterpreter(int paramInt) {
    super(paramInt);
  }
  
  public SourceValue newValue(Type paramType) {
    if (paramType == Type.VOID_TYPE)
      return null; 
    return new SourceValue((paramType == null) ? 1 : paramType.getSize());
  }
  
  public SourceValue newOperation(AbstractInsnNode paramAbstractInsnNode) {
    Object object;
    switch (paramAbstractInsnNode.getOpcode()) {
      case 9:
      case 10:
      case 14:
      case 15:
        i = 2;
        return new SourceValue(i, paramAbstractInsnNode);
      case 18:
        object = ((LdcInsnNode)paramAbstractInsnNode).cst;
        i = (object instanceof Long || object instanceof Double) ? 2 : 1;
        return new SourceValue(i, paramAbstractInsnNode);
      case 178:
        i = Type.getType(((FieldInsnNode)paramAbstractInsnNode).desc).getSize();
        return new SourceValue(i, paramAbstractInsnNode);
    } 
    int i = 1;
    return new SourceValue(i, paramAbstractInsnNode);
  }
  
  public SourceValue copyOperation(AbstractInsnNode paramAbstractInsnNode, SourceValue paramSourceValue) {
    return new SourceValue(paramSourceValue.getSize(), paramAbstractInsnNode);
  }
  
  public SourceValue unaryOperation(AbstractInsnNode paramAbstractInsnNode, SourceValue paramSourceValue) {
    switch (paramAbstractInsnNode.getOpcode()) {
      case 117:
      case 119:
      case 133:
      case 135:
      case 138:
      case 140:
      case 141:
      case 143:
        i = 2;
        return new SourceValue(i, paramAbstractInsnNode);
      case 180:
        i = Type.getType(((FieldInsnNode)paramAbstractInsnNode).desc).getSize();
        return new SourceValue(i, paramAbstractInsnNode);
    } 
    int i = 1;
    return new SourceValue(i, paramAbstractInsnNode);
  }
  
  public SourceValue binaryOperation(AbstractInsnNode paramAbstractInsnNode, SourceValue paramSourceValue1, SourceValue paramSourceValue2) {
    switch (paramAbstractInsnNode.getOpcode()) {
      case 47:
      case 49:
      case 97:
      case 99:
      case 101:
      case 103:
      case 105:
      case 107:
      case 109:
      case 111:
      case 113:
      case 115:
      case 121:
      case 123:
      case 125:
      case 127:
      case 129:
      case 131:
        b = 2;
        return new SourceValue(b, paramAbstractInsnNode);
    } 
    byte b = 1;
    return new SourceValue(b, paramAbstractInsnNode);
  }
  
  public SourceValue ternaryOperation(AbstractInsnNode paramAbstractInsnNode, SourceValue paramSourceValue1, SourceValue paramSourceValue2, SourceValue paramSourceValue3) {
    return new SourceValue(1, paramAbstractInsnNode);
  }
  
  public SourceValue naryOperation(AbstractInsnNode paramAbstractInsnNode, List<? extends SourceValue> paramList) {
    int j, i = paramAbstractInsnNode.getOpcode();
    if (i == 197) {
      j = 1;
    } else {
      String str = (i == 186) ? ((InvokeDynamicInsnNode)paramAbstractInsnNode).desc : ((MethodInsnNode)paramAbstractInsnNode).desc;
      j = Type.getReturnType(str).getSize();
    } 
    return new SourceValue(j, paramAbstractInsnNode);
  }
  
  public void returnOperation(AbstractInsnNode paramAbstractInsnNode, SourceValue paramSourceValue1, SourceValue paramSourceValue2) {}
  
  public SourceValue merge(SourceValue paramSourceValue1, SourceValue paramSourceValue2) {
    if (paramSourceValue1.insns instanceof SmallSet && paramSourceValue2.insns instanceof SmallSet) {
      Set<AbstractInsnNode> set = ((SmallSet)paramSourceValue1.insns).union((SmallSet)paramSourceValue2.insns);
      if (set == paramSourceValue1.insns && paramSourceValue1.size == paramSourceValue2.size)
        return paramSourceValue1; 
      return new SourceValue(Math.min(paramSourceValue1.size, paramSourceValue2.size), set);
    } 
    if (paramSourceValue1.size != paramSourceValue2.size || !paramSourceValue1.insns.containsAll(paramSourceValue2.insns)) {
      HashSet<AbstractInsnNode> hashSet = new HashSet();
      hashSet.addAll(paramSourceValue1.insns);
      hashSet.addAll(paramSourceValue2.insns);
      return new SourceValue(Math.min(paramSourceValue1.size, paramSourceValue2.size), hashSet);
    } 
    return paramSourceValue1;
  }
}
