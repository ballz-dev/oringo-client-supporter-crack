package org.spongepowered.asm.lib.tree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import org.spongepowered.asm.lib.Label;
import org.spongepowered.asm.lib.MethodVisitor;

public class LookupSwitchInsnNode extends AbstractInsnNode {
  public List<LabelNode> labels;
  
  public LabelNode dflt;
  
  public List<Integer> keys;
  
  public LookupSwitchInsnNode(LabelNode paramLabelNode, int[] paramArrayOfint, LabelNode[] paramArrayOfLabelNode) {
    super(171);
    this.dflt = paramLabelNode;
    this.keys = new ArrayList<Integer>((paramArrayOfint == null) ? 0 : paramArrayOfint.length);
    this.labels = new ArrayList<LabelNode>((paramArrayOfLabelNode == null) ? 0 : paramArrayOfLabelNode.length);
    if (paramArrayOfint != null)
      for (byte b = 0; b < paramArrayOfint.length; b++)
        this.keys.add(Integer.valueOf(paramArrayOfint[b]));  
    if (paramArrayOfLabelNode != null)
      this.labels.addAll(Arrays.asList(paramArrayOfLabelNode)); 
  }
  
  public int getType() {
    return 12;
  }
  
  public void accept(MethodVisitor paramMethodVisitor) {
    int[] arrayOfInt = new int[this.keys.size()];
    for (byte b1 = 0; b1 < arrayOfInt.length; b1++)
      arrayOfInt[b1] = ((Integer)this.keys.get(b1)).intValue(); 
    Label[] arrayOfLabel = new Label[this.labels.size()];
    for (byte b2 = 0; b2 < arrayOfLabel.length; b2++)
      arrayOfLabel[b2] = ((LabelNode)this.labels.get(b2)).getLabel(); 
    paramMethodVisitor.visitLookupSwitchInsn(this.dflt.getLabel(), arrayOfInt, arrayOfLabel);
    acceptAnnotations(paramMethodVisitor);
  }
  
  public AbstractInsnNode clone(Map<LabelNode, LabelNode> paramMap) {
    LookupSwitchInsnNode lookupSwitchInsnNode = new LookupSwitchInsnNode(clone(this.dflt, paramMap), null, clone(this.labels, paramMap));
    lookupSwitchInsnNode.keys.addAll(this.keys);
    return lookupSwitchInsnNode.cloneAnnotations(this);
  }
}
