package org.spongepowered.asm.lib.tree;

import java.util.Map;
import org.spongepowered.asm.lib.MethodVisitor;

public class IincInsnNode extends AbstractInsnNode {
  public int incr;
  
  public int var;
  
  public IincInsnNode(int paramInt1, int paramInt2) {
    super(132);
    this.var = paramInt1;
    this.incr = paramInt2;
  }
  
  public int getType() {
    return 10;
  }
  
  public void accept(MethodVisitor paramMethodVisitor) {
    paramMethodVisitor.visitIincInsn(this.var, this.incr);
    acceptAnnotations(paramMethodVisitor);
  }
  
  public AbstractInsnNode clone(Map<LabelNode, LabelNode> paramMap) {
    return (new IincInsnNode(this.var, this.incr)).cloneAnnotations(this);
  }
}
