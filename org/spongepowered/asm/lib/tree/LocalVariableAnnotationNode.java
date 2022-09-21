package org.spongepowered.asm.lib.tree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.spongepowered.asm.lib.Label;
import org.spongepowered.asm.lib.MethodVisitor;
import org.spongepowered.asm.lib.TypePath;

public class LocalVariableAnnotationNode extends TypeAnnotationNode {
  public List<Integer> index;
  
  public List<LabelNode> start;
  
  public List<LabelNode> end;
  
  public LocalVariableAnnotationNode(int paramInt, TypePath paramTypePath, LabelNode[] paramArrayOfLabelNode1, LabelNode[] paramArrayOfLabelNode2, int[] paramArrayOfint, String paramString) {
    this(327680, paramInt, paramTypePath, paramArrayOfLabelNode1, paramArrayOfLabelNode2, paramArrayOfint, paramString);
  }
  
  public LocalVariableAnnotationNode(int paramInt1, int paramInt2, TypePath paramTypePath, LabelNode[] paramArrayOfLabelNode1, LabelNode[] paramArrayOfLabelNode2, int[] paramArrayOfint, String paramString) {
    super(paramInt1, paramInt2, paramTypePath, paramString);
    this.start = new ArrayList<LabelNode>(paramArrayOfLabelNode1.length);
    this.start.addAll(Arrays.asList(paramArrayOfLabelNode1));
    this.end = new ArrayList<LabelNode>(paramArrayOfLabelNode2.length);
    this.end.addAll(Arrays.asList(paramArrayOfLabelNode2));
    this.index = new ArrayList<Integer>(paramArrayOfint.length);
    for (int i : paramArrayOfint)
      this.index.add(Integer.valueOf(i)); 
  }
  
  public void accept(MethodVisitor paramMethodVisitor, boolean paramBoolean) {
    Label[] arrayOfLabel1 = new Label[this.start.size()];
    Label[] arrayOfLabel2 = new Label[this.end.size()];
    int[] arrayOfInt = new int[this.index.size()];
    for (byte b = 0; b < arrayOfLabel1.length; b++) {
      arrayOfLabel1[b] = ((LabelNode)this.start.get(b)).getLabel();
      arrayOfLabel2[b] = ((LabelNode)this.end.get(b)).getLabel();
      arrayOfInt[b] = ((Integer)this.index.get(b)).intValue();
    } 
    accept(paramMethodVisitor.visitLocalVariableAnnotation(this.typeRef, this.typePath, arrayOfLabel1, arrayOfLabel2, arrayOfInt, this.desc, true));
  }
}
