package org.spongepowered.asm.lib.tree.analysis;

import java.util.Set;
import org.spongepowered.asm.lib.tree.AbstractInsnNode;

public class SourceValue implements Value {
  public final Set<AbstractInsnNode> insns;
  
  public final int size;
  
  public SourceValue(int paramInt) {
    this(paramInt, SmallSet.emptySet());
  }
  
  public SourceValue(int paramInt, AbstractInsnNode paramAbstractInsnNode) {
    this.size = paramInt;
    this.insns = new SmallSet<AbstractInsnNode>(paramAbstractInsnNode, null);
  }
  
  public SourceValue(int paramInt, Set<AbstractInsnNode> paramSet) {
    this.size = paramInt;
    this.insns = paramSet;
  }
  
  public int getSize() {
    return this.size;
  }
  
  public boolean equals(Object paramObject) {
    if (!(paramObject instanceof SourceValue))
      return false; 
    SourceValue sourceValue = (SourceValue)paramObject;
    return (this.size == sourceValue.size && this.insns.equals(sourceValue.insns));
  }
  
  public int hashCode() {
    return this.insns.hashCode();
  }
}
