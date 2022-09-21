package org.spongepowered.asm.lib.tree;

import java.util.Map;
import org.spongepowered.asm.lib.MethodVisitor;

public class FieldInsnNode extends AbstractInsnNode {
  public String desc;
  
  public String owner;
  
  public String name;
  
  public FieldInsnNode(int paramInt, String paramString1, String paramString2, String paramString3) {
    super(paramInt);
    this.owner = paramString1;
    this.name = paramString2;
    this.desc = paramString3;
  }
  
  public void setOpcode(int paramInt) {
    this.opcode = paramInt;
  }
  
  public int getType() {
    return 4;
  }
  
  public void accept(MethodVisitor paramMethodVisitor) {
    paramMethodVisitor.visitFieldInsn(this.opcode, this.owner, this.name, this.desc);
    acceptAnnotations(paramMethodVisitor);
  }
  
  public AbstractInsnNode clone(Map<LabelNode, LabelNode> paramMap) {
    return (new FieldInsnNode(this.opcode, this.owner, this.name, this.desc))
      .cloneAnnotations(this);
  }
}
