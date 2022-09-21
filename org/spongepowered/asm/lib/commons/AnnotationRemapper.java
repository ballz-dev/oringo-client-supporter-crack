package org.spongepowered.asm.lib.commons;

import org.spongepowered.asm.lib.AnnotationVisitor;

public class AnnotationRemapper extends AnnotationVisitor {
  protected final Remapper remapper;
  
  public AnnotationRemapper(AnnotationVisitor paramAnnotationVisitor, Remapper paramRemapper) {
    this(327680, paramAnnotationVisitor, paramRemapper);
  }
  
  protected AnnotationRemapper(int paramInt, AnnotationVisitor paramAnnotationVisitor, Remapper paramRemapper) {
    super(paramInt, paramAnnotationVisitor);
    this.remapper = paramRemapper;
  }
  
  public void visit(String paramString, Object paramObject) {
    this.av.visit(paramString, this.remapper.mapValue(paramObject));
  }
  
  public void visitEnum(String paramString1, String paramString2, String paramString3) {
    this.av.visitEnum(paramString1, this.remapper.mapDesc(paramString2), paramString3);
  }
  
  public AnnotationVisitor visitAnnotation(String paramString1, String paramString2) {
    AnnotationVisitor annotationVisitor = this.av.visitAnnotation(paramString1, this.remapper.mapDesc(paramString2));
    return (annotationVisitor == null) ? null : ((annotationVisitor == this.av) ? this : new AnnotationRemapper(annotationVisitor, this.remapper));
  }
  
  public AnnotationVisitor visitArray(String paramString) {
    AnnotationVisitor annotationVisitor = this.av.visitArray(paramString);
    return (annotationVisitor == null) ? null : ((annotationVisitor == this.av) ? this : new AnnotationRemapper(annotationVisitor, this.remapper));
  }
}
