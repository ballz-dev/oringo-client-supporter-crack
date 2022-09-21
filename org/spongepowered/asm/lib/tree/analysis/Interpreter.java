package org.spongepowered.asm.lib.tree.analysis;

import java.util.List;
import org.spongepowered.asm.lib.Type;
import org.spongepowered.asm.lib.tree.AbstractInsnNode;

public abstract class Interpreter<V extends Value> {
  protected final int api;
  
  public abstract V binaryOperation(AbstractInsnNode paramAbstractInsnNode, V paramV1, V paramV2) throws AnalyzerException;
  
  protected Interpreter(int paramInt) {
    this.api = paramInt;
  }
  
  public abstract V naryOperation(AbstractInsnNode paramAbstractInsnNode, List<? extends V> paramList) throws AnalyzerException;
  
  public abstract void returnOperation(AbstractInsnNode paramAbstractInsnNode, V paramV1, V paramV2) throws AnalyzerException;
  
  public abstract V ternaryOperation(AbstractInsnNode paramAbstractInsnNode, V paramV1, V paramV2, V paramV3) throws AnalyzerException;
  
  public abstract V unaryOperation(AbstractInsnNode paramAbstractInsnNode, V paramV) throws AnalyzerException;
  
  public abstract V newOperation(AbstractInsnNode paramAbstractInsnNode) throws AnalyzerException;
  
  public abstract V copyOperation(AbstractInsnNode paramAbstractInsnNode, V paramV) throws AnalyzerException;
  
  public abstract V newValue(Type paramType);
  
  public abstract V merge(V paramV1, V paramV2);
}
