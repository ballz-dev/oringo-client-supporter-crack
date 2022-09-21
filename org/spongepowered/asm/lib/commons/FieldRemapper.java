package org.spongepowered.asm.lib.commons;

import org.spongepowered.asm.lib.AnnotationVisitor;
import org.spongepowered.asm.lib.FieldVisitor;
import org.spongepowered.asm.lib.TypePath;

public class FieldRemapper extends FieldVisitor {
  private final Remapper remapper;
  
  public FieldRemapper(FieldVisitor paramFieldVisitor, Remapper paramRemapper) {
    this(327680, paramFieldVisitor, paramRemapper);
  }
  
  protected FieldRemapper(int paramInt, FieldVisitor paramFieldVisitor, Remapper paramRemapper) {
    super(paramInt, paramFieldVisitor);
    this.remapper = paramRemapper;
  }
  
  public AnnotationVisitor visitAnnotation(String paramString, boolean paramBoolean) {
    AnnotationVisitor annotationVisitor = this.fv.visitAnnotation(this.remapper.mapDesc(paramString), paramBoolean);
    return (annotationVisitor == null) ? null : new AnnotationRemapper(annotationVisitor, this.remapper);
  }
  
  public AnnotationVisitor visitTypeAnnotation(int paramInt, TypePath paramTypePath, String paramString, boolean paramBoolean) {
    AnnotationVisitor annotationVisitor = super.visitTypeAnnotation(paramInt, paramTypePath, this.remapper
        .mapDesc(paramString), paramBoolean);
    return (annotationVisitor == null) ? null : new AnnotationRemapper(annotationVisitor, this.remapper);
  }
}
