package org.spongepowered.asm.lib.tree;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.spongepowered.asm.lib.MethodVisitor;

public abstract class AbstractInsnNode {
  AbstractInsnNode prev;
  
  public static final int MULTIANEWARRAY_INSN = 13;
  
  public List<TypeAnnotationNode> visibleTypeAnnotations;
  
  public static final int IINC_INSN = 10;
  
  public static final int JUMP_INSN = 7;
  
  protected int opcode;
  
  public static final int LOOKUPSWITCH_INSN = 12;
  
  AbstractInsnNode next;
  
  public static final int LDC_INSN = 9;
  
  public List<TypeAnnotationNode> invisibleTypeAnnotations;
  
  public static final int TYPE_INSN = 3;
  
  public static final int INVOKE_DYNAMIC_INSN = 6;
  
  public static final int LABEL = 8;
  
  public static final int LINE = 15;
  
  public static final int FIELD_INSN = 4;
  
  public static final int VAR_INSN = 2;
  
  public static final int TABLESWITCH_INSN = 11;
  
  public static final int METHOD_INSN = 5;
  
  int index;
  
  public static final int INT_INSN = 1;
  
  public static final int FRAME = 14;
  
  public static final int INSN = 0;
  
  protected AbstractInsnNode(int paramInt) {
    this.opcode = paramInt;
    this.index = -1;
  }
  
  public int getOpcode() {
    return this.opcode;
  }
  
  public AbstractInsnNode getPrevious() {
    return this.prev;
  }
  
  public AbstractInsnNode getNext() {
    return this.next;
  }
  
  protected final void acceptAnnotations(MethodVisitor paramMethodVisitor) {
    byte b1 = (this.visibleTypeAnnotations == null) ? 0 : this.visibleTypeAnnotations.size();
    byte b2;
    for (b2 = 0; b2 < b1; b2++) {
      TypeAnnotationNode typeAnnotationNode = this.visibleTypeAnnotations.get(b2);
      typeAnnotationNode.accept(paramMethodVisitor.visitInsnAnnotation(typeAnnotationNode.typeRef, typeAnnotationNode.typePath, typeAnnotationNode.desc, true));
    } 
    b1 = (this.invisibleTypeAnnotations == null) ? 0 : this.invisibleTypeAnnotations.size();
    for (b2 = 0; b2 < b1; b2++) {
      TypeAnnotationNode typeAnnotationNode = this.invisibleTypeAnnotations.get(b2);
      typeAnnotationNode.accept(paramMethodVisitor.visitInsnAnnotation(typeAnnotationNode.typeRef, typeAnnotationNode.typePath, typeAnnotationNode.desc, false));
    } 
  }
  
  static LabelNode clone(LabelNode paramLabelNode, Map<LabelNode, LabelNode> paramMap) {
    return paramMap.get(paramLabelNode);
  }
  
  static LabelNode[] clone(List<LabelNode> paramList, Map<LabelNode, LabelNode> paramMap) {
    LabelNode[] arrayOfLabelNode = new LabelNode[paramList.size()];
    for (byte b = 0; b < arrayOfLabelNode.length; b++)
      arrayOfLabelNode[b] = paramMap.get(paramList.get(b)); 
    return arrayOfLabelNode;
  }
  
  protected final AbstractInsnNode cloneAnnotations(AbstractInsnNode paramAbstractInsnNode) {
    if (paramAbstractInsnNode.visibleTypeAnnotations != null) {
      this.visibleTypeAnnotations = new ArrayList<TypeAnnotationNode>();
      for (byte b = 0; b < paramAbstractInsnNode.visibleTypeAnnotations.size(); b++) {
        TypeAnnotationNode typeAnnotationNode1 = paramAbstractInsnNode.visibleTypeAnnotations.get(b);
        TypeAnnotationNode typeAnnotationNode2 = new TypeAnnotationNode(typeAnnotationNode1.typeRef, typeAnnotationNode1.typePath, typeAnnotationNode1.desc);
        typeAnnotationNode1.accept(typeAnnotationNode2);
        this.visibleTypeAnnotations.add(typeAnnotationNode2);
      } 
    } 
    if (paramAbstractInsnNode.invisibleTypeAnnotations != null) {
      this.invisibleTypeAnnotations = new ArrayList<TypeAnnotationNode>();
      for (byte b = 0; b < paramAbstractInsnNode.invisibleTypeAnnotations.size(); b++) {
        TypeAnnotationNode typeAnnotationNode1 = paramAbstractInsnNode.invisibleTypeAnnotations.get(b);
        TypeAnnotationNode typeAnnotationNode2 = new TypeAnnotationNode(typeAnnotationNode1.typeRef, typeAnnotationNode1.typePath, typeAnnotationNode1.desc);
        typeAnnotationNode1.accept(typeAnnotationNode2);
        this.invisibleTypeAnnotations.add(typeAnnotationNode2);
      } 
    } 
    return this;
  }
  
  public abstract AbstractInsnNode clone(Map<LabelNode, LabelNode> paramMap);
  
  public abstract int getType();
  
  public abstract void accept(MethodVisitor paramMethodVisitor);
}
