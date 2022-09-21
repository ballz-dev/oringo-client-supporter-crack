package org.spongepowered.asm.lib.tree;

import java.util.Map;
import org.spongepowered.asm.lib.Handle;
import org.spongepowered.asm.lib.MethodVisitor;

public class InvokeDynamicInsnNode extends AbstractInsnNode {
  public String name;
  
  public Handle bsm;
  
  public Object[] bsmArgs;
  
  public String desc;
  
  public InvokeDynamicInsnNode(String paramString1, String paramString2, Handle paramHandle, Object... paramVarArgs) {
    super(186);
    this.name = paramString1;
    this.desc = paramString2;
    this.bsm = paramHandle;
    this.bsmArgs = paramVarArgs;
  }
  
  public int getType() {
    return 6;
  }
  
  public void accept(MethodVisitor paramMethodVisitor) {
    paramMethodVisitor.visitInvokeDynamicInsn(this.name, this.desc, this.bsm, this.bsmArgs);
    acceptAnnotations(paramMethodVisitor);
  }
  
  public AbstractInsnNode clone(Map<LabelNode, LabelNode> paramMap) {
    return (new InvokeDynamicInsnNode(this.name, this.desc, this.bsm, this.bsmArgs))
      .cloneAnnotations(this);
  }
}
