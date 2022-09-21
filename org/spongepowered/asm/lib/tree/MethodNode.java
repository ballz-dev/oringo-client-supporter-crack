package org.spongepowered.asm.lib.tree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.spongepowered.asm.lib.AnnotationVisitor;
import org.spongepowered.asm.lib.Attribute;
import org.spongepowered.asm.lib.ClassVisitor;
import org.spongepowered.asm.lib.Handle;
import org.spongepowered.asm.lib.Label;
import org.spongepowered.asm.lib.MethodVisitor;
import org.spongepowered.asm.lib.Type;
import org.spongepowered.asm.lib.TypePath;

public class MethodNode extends MethodVisitor {
  public List<AnnotationNode>[] visibleParameterAnnotations;
  
  public List<LocalVariableNode> localVariables;
  
  public List<AnnotationNode> invisibleAnnotations;
  
  public List<LocalVariableAnnotationNode> invisibleLocalVariableAnnotations;
  
  public InsnList instructions;
  
  public List<TypeAnnotationNode> visibleTypeAnnotations;
  
  public String desc;
  
  public String signature;
  
  public List<TypeAnnotationNode> invisibleTypeAnnotations;
  
  public List<Attribute> attrs;
  
  public List<AnnotationNode>[] invisibleParameterAnnotations;
  
  public Object annotationDefault;
  
  public List<ParameterNode> parameters;
  
  public int access;
  
  public List<LocalVariableAnnotationNode> visibleLocalVariableAnnotations;
  
  public int maxLocals;
  
  private boolean visited;
  
  public int maxStack;
  
  public String name;
  
  public List<AnnotationNode> visibleAnnotations;
  
  public List<String> exceptions;
  
  public List<TryCatchBlockNode> tryCatchBlocks;
  
  public MethodNode() {
    this(327680);
    if (getClass() != MethodNode.class)
      throw new IllegalStateException(); 
  }
  
  public MethodNode(int paramInt) {
    super(paramInt);
    this.instructions = new InsnList();
  }
  
  public MethodNode(int paramInt, String paramString1, String paramString2, String paramString3, String[] paramArrayOfString) {
    this(327680, paramInt, paramString1, paramString2, paramString3, paramArrayOfString);
    if (getClass() != MethodNode.class)
      throw new IllegalStateException(); 
  }
  
  public MethodNode(int paramInt1, int paramInt2, String paramString1, String paramString2, String paramString3, String[] paramArrayOfString) {
    super(paramInt1);
    this.access = paramInt2;
    this.name = paramString1;
    this.desc = paramString2;
    this.signature = paramString3;
    this.exceptions = new ArrayList<String>((paramArrayOfString == null) ? 0 : paramArrayOfString.length);
    boolean bool = ((paramInt2 & 0x400) != 0) ? true : false;
    if (!bool)
      this.localVariables = new ArrayList<LocalVariableNode>(5); 
    this.tryCatchBlocks = new ArrayList<TryCatchBlockNode>();
    if (paramArrayOfString != null)
      this.exceptions.addAll(Arrays.asList(paramArrayOfString)); 
    this.instructions = new InsnList();
  }
  
  public void visitParameter(String paramString, int paramInt) {
    if (this.parameters == null)
      this.parameters = new ArrayList<ParameterNode>(5); 
    this.parameters.add(new ParameterNode(paramString, paramInt));
  }
  
  public AnnotationVisitor visitAnnotationDefault() {
    return new AnnotationNode(new ArrayList(0) {
          public boolean add(Object param1Object) {
            MethodNode.this.annotationDefault = param1Object;
            return super.add(param1Object);
          }
        });
  }
  
  public AnnotationVisitor visitAnnotation(String paramString, boolean paramBoolean) {
    AnnotationNode annotationNode = new AnnotationNode(paramString);
    if (paramBoolean) {
      if (this.visibleAnnotations == null)
        this.visibleAnnotations = new ArrayList<AnnotationNode>(1); 
      this.visibleAnnotations.add(annotationNode);
    } else {
      if (this.invisibleAnnotations == null)
        this.invisibleAnnotations = new ArrayList<AnnotationNode>(1); 
      this.invisibleAnnotations.add(annotationNode);
    } 
    return annotationNode;
  }
  
  public AnnotationVisitor visitTypeAnnotation(int paramInt, TypePath paramTypePath, String paramString, boolean paramBoolean) {
    TypeAnnotationNode typeAnnotationNode = new TypeAnnotationNode(paramInt, paramTypePath, paramString);
    if (paramBoolean) {
      if (this.visibleTypeAnnotations == null)
        this.visibleTypeAnnotations = new ArrayList<TypeAnnotationNode>(1); 
      this.visibleTypeAnnotations.add(typeAnnotationNode);
    } else {
      if (this.invisibleTypeAnnotations == null)
        this.invisibleTypeAnnotations = new ArrayList<TypeAnnotationNode>(1); 
      this.invisibleTypeAnnotations.add(typeAnnotationNode);
    } 
    return typeAnnotationNode;
  }
  
  public AnnotationVisitor visitParameterAnnotation(int paramInt, String paramString, boolean paramBoolean) {
    AnnotationNode annotationNode = new AnnotationNode(paramString);
    if (paramBoolean) {
      if (this.visibleParameterAnnotations == null) {
        int i = (Type.getArgumentTypes(this.desc)).length;
        this.visibleParameterAnnotations = (List<AnnotationNode>[])new List[i];
      } 
      if (this.visibleParameterAnnotations[paramInt] == null)
        this.visibleParameterAnnotations[paramInt] = new ArrayList<AnnotationNode>(1); 
      this.visibleParameterAnnotations[paramInt].add(annotationNode);
    } else {
      if (this.invisibleParameterAnnotations == null) {
        int i = (Type.getArgumentTypes(this.desc)).length;
        this.invisibleParameterAnnotations = (List<AnnotationNode>[])new List[i];
      } 
      if (this.invisibleParameterAnnotations[paramInt] == null)
        this.invisibleParameterAnnotations[paramInt] = new ArrayList<AnnotationNode>(1); 
      this.invisibleParameterAnnotations[paramInt].add(annotationNode);
    } 
    return annotationNode;
  }
  
  public void visitAttribute(Attribute paramAttribute) {
    if (this.attrs == null)
      this.attrs = new ArrayList<Attribute>(1); 
    this.attrs.add(paramAttribute);
  }
  
  public void visitCode() {}
  
  public void visitFrame(int paramInt1, int paramInt2, Object[] paramArrayOfObject1, int paramInt3, Object[] paramArrayOfObject2) {
    this.instructions.add(new FrameNode(paramInt1, paramInt2, (paramArrayOfObject1 == null) ? null : 
          getLabelNodes(paramArrayOfObject1), paramInt3, (paramArrayOfObject2 == null) ? null : 
          getLabelNodes(paramArrayOfObject2)));
  }
  
  public void visitInsn(int paramInt) {
    this.instructions.add(new InsnNode(paramInt));
  }
  
  public void visitIntInsn(int paramInt1, int paramInt2) {
    this.instructions.add(new IntInsnNode(paramInt1, paramInt2));
  }
  
  public void visitVarInsn(int paramInt1, int paramInt2) {
    this.instructions.add(new VarInsnNode(paramInt1, paramInt2));
  }
  
  public void visitTypeInsn(int paramInt, String paramString) {
    this.instructions.add(new TypeInsnNode(paramInt, paramString));
  }
  
  public void visitFieldInsn(int paramInt, String paramString1, String paramString2, String paramString3) {
    this.instructions.add(new FieldInsnNode(paramInt, paramString1, paramString2, paramString3));
  }
  
  @Deprecated
  public void visitMethodInsn(int paramInt, String paramString1, String paramString2, String paramString3) {
    if (this.api >= 327680) {
      super.visitMethodInsn(paramInt, paramString1, paramString2, paramString3);
      return;
    } 
    this.instructions.add(new MethodInsnNode(paramInt, paramString1, paramString2, paramString3));
  }
  
  public void visitMethodInsn(int paramInt, String paramString1, String paramString2, String paramString3, boolean paramBoolean) {
    if (this.api < 327680) {
      super.visitMethodInsn(paramInt, paramString1, paramString2, paramString3, paramBoolean);
      return;
    } 
    this.instructions.add(new MethodInsnNode(paramInt, paramString1, paramString2, paramString3, paramBoolean));
  }
  
  public void visitInvokeDynamicInsn(String paramString1, String paramString2, Handle paramHandle, Object... paramVarArgs) {
    this.instructions.add(new InvokeDynamicInsnNode(paramString1, paramString2, paramHandle, paramVarArgs));
  }
  
  public void visitJumpInsn(int paramInt, Label paramLabel) {
    this.instructions.add(new JumpInsnNode(paramInt, getLabelNode(paramLabel)));
  }
  
  public void visitLabel(Label paramLabel) {
    this.instructions.add(getLabelNode(paramLabel));
  }
  
  public void visitLdcInsn(Object paramObject) {
    this.instructions.add(new LdcInsnNode(paramObject));
  }
  
  public void visitIincInsn(int paramInt1, int paramInt2) {
    this.instructions.add(new IincInsnNode(paramInt1, paramInt2));
  }
  
  public void visitTableSwitchInsn(int paramInt1, int paramInt2, Label paramLabel, Label... paramVarArgs) {
    this.instructions.add(new TableSwitchInsnNode(paramInt1, paramInt2, getLabelNode(paramLabel), 
          getLabelNodes(paramVarArgs)));
  }
  
  public void visitLookupSwitchInsn(Label paramLabel, int[] paramArrayOfint, Label[] paramArrayOfLabel) {
    this.instructions.add(new LookupSwitchInsnNode(getLabelNode(paramLabel), paramArrayOfint, 
          getLabelNodes(paramArrayOfLabel)));
  }
  
  public void visitMultiANewArrayInsn(String paramString, int paramInt) {
    this.instructions.add(new MultiANewArrayInsnNode(paramString, paramInt));
  }
  
  public AnnotationVisitor visitInsnAnnotation(int paramInt, TypePath paramTypePath, String paramString, boolean paramBoolean) {
    AbstractInsnNode abstractInsnNode = this.instructions.getLast();
    while (abstractInsnNode.getOpcode() == -1)
      abstractInsnNode = abstractInsnNode.getPrevious(); 
    TypeAnnotationNode typeAnnotationNode = new TypeAnnotationNode(paramInt, paramTypePath, paramString);
    if (paramBoolean) {
      if (abstractInsnNode.visibleTypeAnnotations == null)
        abstractInsnNode.visibleTypeAnnotations = new ArrayList<TypeAnnotationNode>(1); 
      abstractInsnNode.visibleTypeAnnotations.add(typeAnnotationNode);
    } else {
      if (abstractInsnNode.invisibleTypeAnnotations == null)
        abstractInsnNode.invisibleTypeAnnotations = new ArrayList<TypeAnnotationNode>(1); 
      abstractInsnNode.invisibleTypeAnnotations.add(typeAnnotationNode);
    } 
    return typeAnnotationNode;
  }
  
  public void visitTryCatchBlock(Label paramLabel1, Label paramLabel2, Label paramLabel3, String paramString) {
    this.tryCatchBlocks.add(new TryCatchBlockNode(getLabelNode(paramLabel1), 
          getLabelNode(paramLabel2), getLabelNode(paramLabel3), paramString));
  }
  
  public AnnotationVisitor visitTryCatchAnnotation(int paramInt, TypePath paramTypePath, String paramString, boolean paramBoolean) {
    TryCatchBlockNode tryCatchBlockNode = this.tryCatchBlocks.get((paramInt & 0xFFFF00) >> 8);
    TypeAnnotationNode typeAnnotationNode = new TypeAnnotationNode(paramInt, paramTypePath, paramString);
    if (paramBoolean) {
      if (tryCatchBlockNode.visibleTypeAnnotations == null)
        tryCatchBlockNode.visibleTypeAnnotations = new ArrayList<TypeAnnotationNode>(1); 
      tryCatchBlockNode.visibleTypeAnnotations.add(typeAnnotationNode);
    } else {
      if (tryCatchBlockNode.invisibleTypeAnnotations == null)
        tryCatchBlockNode.invisibleTypeAnnotations = new ArrayList<TypeAnnotationNode>(1); 
      tryCatchBlockNode.invisibleTypeAnnotations.add(typeAnnotationNode);
    } 
    return typeAnnotationNode;
  }
  
  public void visitLocalVariable(String paramString1, String paramString2, String paramString3, Label paramLabel1, Label paramLabel2, int paramInt) {
    this.localVariables.add(new LocalVariableNode(paramString1, paramString2, paramString3, 
          getLabelNode(paramLabel1), getLabelNode(paramLabel2), paramInt));
  }
  
  public AnnotationVisitor visitLocalVariableAnnotation(int paramInt, TypePath paramTypePath, Label[] paramArrayOfLabel1, Label[] paramArrayOfLabel2, int[] paramArrayOfint, String paramString, boolean paramBoolean) {
    LocalVariableAnnotationNode localVariableAnnotationNode = new LocalVariableAnnotationNode(paramInt, paramTypePath, getLabelNodes(paramArrayOfLabel1), getLabelNodes(paramArrayOfLabel2), paramArrayOfint, paramString);
    if (paramBoolean) {
      if (this.visibleLocalVariableAnnotations == null)
        this.visibleLocalVariableAnnotations = new ArrayList<LocalVariableAnnotationNode>(1); 
      this.visibleLocalVariableAnnotations.add(localVariableAnnotationNode);
    } else {
      if (this.invisibleLocalVariableAnnotations == null)
        this.invisibleLocalVariableAnnotations = new ArrayList<LocalVariableAnnotationNode>(1); 
      this.invisibleLocalVariableAnnotations.add(localVariableAnnotationNode);
    } 
    return localVariableAnnotationNode;
  }
  
  public void visitLineNumber(int paramInt, Label paramLabel) {
    this.instructions.add(new LineNumberNode(paramInt, getLabelNode(paramLabel)));
  }
  
  public void visitMaxs(int paramInt1, int paramInt2) {
    this.maxStack = paramInt1;
    this.maxLocals = paramInt2;
  }
  
  public void visitEnd() {}
  
  protected LabelNode getLabelNode(Label paramLabel) {
    if (!(paramLabel.info instanceof LabelNode))
      paramLabel.info = new LabelNode(); 
    return (LabelNode)paramLabel.info;
  }
  
  private LabelNode[] getLabelNodes(Label[] paramArrayOfLabel) {
    LabelNode[] arrayOfLabelNode = new LabelNode[paramArrayOfLabel.length];
    for (byte b = 0; b < paramArrayOfLabel.length; b++)
      arrayOfLabelNode[b] = getLabelNode(paramArrayOfLabel[b]); 
    return arrayOfLabelNode;
  }
  
  private Object[] getLabelNodes(Object[] paramArrayOfObject) {
    Object[] arrayOfObject = new Object[paramArrayOfObject.length];
    for (byte b = 0; b < paramArrayOfObject.length; b++) {
      Object object = paramArrayOfObject[b];
      if (object instanceof Label)
        object = getLabelNode((Label)object); 
      arrayOfObject[b] = object;
    } 
    return arrayOfObject;
  }
  
  public void check(int paramInt) {
    if (paramInt == 262144) {
      if (this.visibleTypeAnnotations != null && this.visibleTypeAnnotations
        .size() > 0)
        throw new RuntimeException(); 
      if (this.invisibleTypeAnnotations != null && this.invisibleTypeAnnotations
        .size() > 0)
        throw new RuntimeException(); 
      byte b1 = (this.tryCatchBlocks == null) ? 0 : this.tryCatchBlocks.size();
      byte b2;
      for (b2 = 0; b2 < b1; b2++) {
        TryCatchBlockNode tryCatchBlockNode = this.tryCatchBlocks.get(b2);
        if (tryCatchBlockNode.visibleTypeAnnotations != null && tryCatchBlockNode.visibleTypeAnnotations
          .size() > 0)
          throw new RuntimeException(); 
        if (tryCatchBlockNode.invisibleTypeAnnotations != null && tryCatchBlockNode.invisibleTypeAnnotations
          .size() > 0)
          throw new RuntimeException(); 
      } 
      for (b2 = 0; b2 < this.instructions.size(); b2++) {
        AbstractInsnNode abstractInsnNode = this.instructions.get(b2);
        if (abstractInsnNode.visibleTypeAnnotations != null && abstractInsnNode.visibleTypeAnnotations
          .size() > 0)
          throw new RuntimeException(); 
        if (abstractInsnNode.invisibleTypeAnnotations != null && abstractInsnNode.invisibleTypeAnnotations
          .size() > 0)
          throw new RuntimeException(); 
        if (abstractInsnNode instanceof MethodInsnNode) {
          boolean bool = ((MethodInsnNode)abstractInsnNode).itf;
          if (bool != ((abstractInsnNode.opcode == 185)))
            throw new RuntimeException(); 
        } 
      } 
      if (this.visibleLocalVariableAnnotations != null && this.visibleLocalVariableAnnotations
        .size() > 0)
        throw new RuntimeException(); 
      if (this.invisibleLocalVariableAnnotations != null && this.invisibleLocalVariableAnnotations
        .size() > 0)
        throw new RuntimeException(); 
    } 
  }
  
  public void accept(ClassVisitor paramClassVisitor) {
    String[] arrayOfString = new String[this.exceptions.size()];
    this.exceptions.toArray(arrayOfString);
    MethodVisitor methodVisitor = paramClassVisitor.visitMethod(this.access, this.name, this.desc, this.signature, arrayOfString);
    if (methodVisitor != null)
      accept(methodVisitor); 
  }
  
  public void accept(MethodVisitor paramMethodVisitor) {
    byte b1 = (this.parameters == null) ? 0 : this.parameters.size();
    byte b2;
    for (b2 = 0; b2 < b1; b2++) {
      ParameterNode parameterNode = this.parameters.get(b2);
      paramMethodVisitor.visitParameter(parameterNode.name, parameterNode.access);
    } 
    if (this.annotationDefault != null) {
      AnnotationVisitor annotationVisitor = paramMethodVisitor.visitAnnotationDefault();
      AnnotationNode.accept(annotationVisitor, null, this.annotationDefault);
      if (annotationVisitor != null)
        annotationVisitor.visitEnd(); 
    } 
    b1 = (this.visibleAnnotations == null) ? 0 : this.visibleAnnotations.size();
    for (b2 = 0; b2 < b1; b2++) {
      AnnotationNode annotationNode = this.visibleAnnotations.get(b2);
      annotationNode.accept(paramMethodVisitor.visitAnnotation(annotationNode.desc, true));
    } 
    b1 = (this.invisibleAnnotations == null) ? 0 : this.invisibleAnnotations.size();
    for (b2 = 0; b2 < b1; b2++) {
      AnnotationNode annotationNode = this.invisibleAnnotations.get(b2);
      annotationNode.accept(paramMethodVisitor.visitAnnotation(annotationNode.desc, false));
    } 
    b1 = (this.visibleTypeAnnotations == null) ? 0 : this.visibleTypeAnnotations.size();
    for (b2 = 0; b2 < b1; b2++) {
      TypeAnnotationNode typeAnnotationNode = this.visibleTypeAnnotations.get(b2);
      typeAnnotationNode.accept(paramMethodVisitor.visitTypeAnnotation(typeAnnotationNode.typeRef, typeAnnotationNode.typePath, typeAnnotationNode.desc, true));
    } 
    b1 = (this.invisibleTypeAnnotations == null) ? 0 : this.invisibleTypeAnnotations.size();
    for (b2 = 0; b2 < b1; b2++) {
      TypeAnnotationNode typeAnnotationNode = this.invisibleTypeAnnotations.get(b2);
      typeAnnotationNode.accept(paramMethodVisitor.visitTypeAnnotation(typeAnnotationNode.typeRef, typeAnnotationNode.typePath, typeAnnotationNode.desc, false));
    } 
    b1 = (this.visibleParameterAnnotations == null) ? 0 : this.visibleParameterAnnotations.length;
    for (b2 = 0; b2 < b1; b2++) {
      List<AnnotationNode> list = this.visibleParameterAnnotations[b2];
      if (list != null)
        for (byte b = 0; b < list.size(); b++) {
          AnnotationNode annotationNode = list.get(b);
          annotationNode.accept(paramMethodVisitor.visitParameterAnnotation(b2, annotationNode.desc, true));
        }  
    } 
    b1 = (this.invisibleParameterAnnotations == null) ? 0 : this.invisibleParameterAnnotations.length;
    for (b2 = 0; b2 < b1; b2++) {
      List<AnnotationNode> list = this.invisibleParameterAnnotations[b2];
      if (list != null)
        for (byte b = 0; b < list.size(); b++) {
          AnnotationNode annotationNode = list.get(b);
          annotationNode.accept(paramMethodVisitor.visitParameterAnnotation(b2, annotationNode.desc, false));
        }  
    } 
    if (this.visited)
      this.instructions.resetLabels(); 
    b1 = (this.attrs == null) ? 0 : this.attrs.size();
    for (b2 = 0; b2 < b1; b2++)
      paramMethodVisitor.visitAttribute(this.attrs.get(b2)); 
    if (this.instructions.size() > 0) {
      paramMethodVisitor.visitCode();
      b1 = (this.tryCatchBlocks == null) ? 0 : this.tryCatchBlocks.size();
      for (b2 = 0; b2 < b1; b2++) {
        ((TryCatchBlockNode)this.tryCatchBlocks.get(b2)).updateIndex(b2);
        ((TryCatchBlockNode)this.tryCatchBlocks.get(b2)).accept(paramMethodVisitor);
      } 
      this.instructions.accept(paramMethodVisitor);
      b1 = (this.localVariables == null) ? 0 : this.localVariables.size();
      for (b2 = 0; b2 < b1; b2++)
        ((LocalVariableNode)this.localVariables.get(b2)).accept(paramMethodVisitor); 
      b1 = (this.visibleLocalVariableAnnotations == null) ? 0 : this.visibleLocalVariableAnnotations.size();
      for (b2 = 0; b2 < b1; b2++)
        ((LocalVariableAnnotationNode)this.visibleLocalVariableAnnotations.get(b2)).accept(paramMethodVisitor, true); 
      b1 = (this.invisibleLocalVariableAnnotations == null) ? 0 : this.invisibleLocalVariableAnnotations.size();
      for (b2 = 0; b2 < b1; b2++)
        ((LocalVariableAnnotationNode)this.invisibleLocalVariableAnnotations.get(b2)).accept(paramMethodVisitor, false); 
      paramMethodVisitor.visitMaxs(this.maxStack, this.maxLocals);
      this.visited = true;
    } 
    paramMethodVisitor.visitEnd();
  }
}
