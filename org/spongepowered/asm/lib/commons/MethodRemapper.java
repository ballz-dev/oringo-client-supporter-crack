package org.spongepowered.asm.lib.commons;

import org.spongepowered.asm.lib.AnnotationVisitor;
import org.spongepowered.asm.lib.Handle;
import org.spongepowered.asm.lib.Label;
import org.spongepowered.asm.lib.MethodVisitor;
import org.spongepowered.asm.lib.TypePath;

public class MethodRemapper extends MethodVisitor {
  protected final Remapper remapper;
  
  public MethodRemapper(MethodVisitor paramMethodVisitor, Remapper paramRemapper) {
    this(327680, paramMethodVisitor, paramRemapper);
  }
  
  protected MethodRemapper(int paramInt, MethodVisitor paramMethodVisitor, Remapper paramRemapper) {
    super(paramInt, paramMethodVisitor);
    this.remapper = paramRemapper;
  }
  
  public AnnotationVisitor visitAnnotationDefault() {
    AnnotationVisitor annotationVisitor = super.visitAnnotationDefault();
    return (annotationVisitor == null) ? annotationVisitor : new AnnotationRemapper(annotationVisitor, this.remapper);
  }
  
  public AnnotationVisitor visitAnnotation(String paramString, boolean paramBoolean) {
    AnnotationVisitor annotationVisitor = super.visitAnnotation(this.remapper.mapDesc(paramString), paramBoolean);
    return (annotationVisitor == null) ? annotationVisitor : new AnnotationRemapper(annotationVisitor, this.remapper);
  }
  
  public AnnotationVisitor visitTypeAnnotation(int paramInt, TypePath paramTypePath, String paramString, boolean paramBoolean) {
    AnnotationVisitor annotationVisitor = super.visitTypeAnnotation(paramInt, paramTypePath, this.remapper
        .mapDesc(paramString), paramBoolean);
    return (annotationVisitor == null) ? annotationVisitor : new AnnotationRemapper(annotationVisitor, this.remapper);
  }
  
  public AnnotationVisitor visitParameterAnnotation(int paramInt, String paramString, boolean paramBoolean) {
    AnnotationVisitor annotationVisitor = super.visitParameterAnnotation(paramInt, this.remapper
        .mapDesc(paramString), paramBoolean);
    return (annotationVisitor == null) ? annotationVisitor : new AnnotationRemapper(annotationVisitor, this.remapper);
  }
  
  public void visitFrame(int paramInt1, int paramInt2, Object[] paramArrayOfObject1, int paramInt3, Object[] paramArrayOfObject2) {
    super.visitFrame(paramInt1, paramInt2, remapEntries(paramInt2, paramArrayOfObject1), paramInt3, 
        remapEntries(paramInt3, paramArrayOfObject2));
  }
  
  private Object[] remapEntries(int paramInt, Object[] paramArrayOfObject) {
    for (byte b = 0; b < paramInt; b++) {
      if (paramArrayOfObject[b] instanceof String) {
        Object[] arrayOfObject = new Object[paramInt];
        if (b > 0)
          System.arraycopy(paramArrayOfObject, 0, arrayOfObject, 0, b); 
        while (true) {
          Object object = paramArrayOfObject[b];
          arrayOfObject[b++] = (object instanceof String) ? this.remapper
            .mapType((String)object) : object;
          if (b >= paramInt)
            return arrayOfObject; 
        } 
      } 
    } 
    return paramArrayOfObject;
  }
  
  public void visitFieldInsn(int paramInt, String paramString1, String paramString2, String paramString3) {
    super.visitFieldInsn(paramInt, this.remapper.mapType(paramString1), this.remapper
        .mapFieldName(paramString1, paramString2, paramString3), this.remapper
        .mapDesc(paramString3));
  }
  
  @Deprecated
  public void visitMethodInsn(int paramInt, String paramString1, String paramString2, String paramString3) {
    if (this.api >= 327680) {
      super.visitMethodInsn(paramInt, paramString1, paramString2, paramString3);
      return;
    } 
    doVisitMethodInsn(paramInt, paramString1, paramString2, paramString3, (paramInt == 185));
  }
  
  public void visitMethodInsn(int paramInt, String paramString1, String paramString2, String paramString3, boolean paramBoolean) {
    if (this.api < 327680) {
      super.visitMethodInsn(paramInt, paramString1, paramString2, paramString3, paramBoolean);
      return;
    } 
    doVisitMethodInsn(paramInt, paramString1, paramString2, paramString3, paramBoolean);
  }
  
  private void doVisitMethodInsn(int paramInt, String paramString1, String paramString2, String paramString3, boolean paramBoolean) {
    if (this.mv != null)
      this.mv.visitMethodInsn(paramInt, this.remapper.mapType(paramString1), this.remapper
          .mapMethodName(paramString1, paramString2, paramString3), this.remapper
          .mapMethodDesc(paramString3), paramBoolean); 
  }
  
  public void visitInvokeDynamicInsn(String paramString1, String paramString2, Handle paramHandle, Object... paramVarArgs) {
    for (byte b = 0; b < paramVarArgs.length; b++)
      paramVarArgs[b] = this.remapper.mapValue(paramVarArgs[b]); 
    super.visitInvokeDynamicInsn(this.remapper
        .mapInvokeDynamicMethodName(paramString1, paramString2), this.remapper
        .mapMethodDesc(paramString2), (Handle)this.remapper.mapValue(paramHandle), paramVarArgs);
  }
  
  public void visitTypeInsn(int paramInt, String paramString) {
    super.visitTypeInsn(paramInt, this.remapper.mapType(paramString));
  }
  
  public void visitLdcInsn(Object paramObject) {
    super.visitLdcInsn(this.remapper.mapValue(paramObject));
  }
  
  public void visitMultiANewArrayInsn(String paramString, int paramInt) {
    super.visitMultiANewArrayInsn(this.remapper.mapDesc(paramString), paramInt);
  }
  
  public AnnotationVisitor visitInsnAnnotation(int paramInt, TypePath paramTypePath, String paramString, boolean paramBoolean) {
    AnnotationVisitor annotationVisitor = super.visitInsnAnnotation(paramInt, paramTypePath, this.remapper
        .mapDesc(paramString), paramBoolean);
    return (annotationVisitor == null) ? annotationVisitor : new AnnotationRemapper(annotationVisitor, this.remapper);
  }
  
  public void visitTryCatchBlock(Label paramLabel1, Label paramLabel2, Label paramLabel3, String paramString) {
    super.visitTryCatchBlock(paramLabel1, paramLabel2, paramLabel3, (paramString == null) ? null : this.remapper
        .mapType(paramString));
  }
  
  public AnnotationVisitor visitTryCatchAnnotation(int paramInt, TypePath paramTypePath, String paramString, boolean paramBoolean) {
    AnnotationVisitor annotationVisitor = super.visitTryCatchAnnotation(paramInt, paramTypePath, this.remapper
        .mapDesc(paramString), paramBoolean);
    return (annotationVisitor == null) ? annotationVisitor : new AnnotationRemapper(annotationVisitor, this.remapper);
  }
  
  public void visitLocalVariable(String paramString1, String paramString2, String paramString3, Label paramLabel1, Label paramLabel2, int paramInt) {
    super.visitLocalVariable(paramString1, this.remapper.mapDesc(paramString2), this.remapper
        .mapSignature(paramString3, true), paramLabel1, paramLabel2, paramInt);
  }
  
  public AnnotationVisitor visitLocalVariableAnnotation(int paramInt, TypePath paramTypePath, Label[] paramArrayOfLabel1, Label[] paramArrayOfLabel2, int[] paramArrayOfint, String paramString, boolean paramBoolean) {
    AnnotationVisitor annotationVisitor = super.visitLocalVariableAnnotation(paramInt, paramTypePath, paramArrayOfLabel1, paramArrayOfLabel2, paramArrayOfint, this.remapper
        .mapDesc(paramString), paramBoolean);
    return (annotationVisitor == null) ? annotationVisitor : new AnnotationRemapper(annotationVisitor, this.remapper);
  }
}
