package org.spongepowered.asm.lib.util;

import org.spongepowered.asm.lib.AnnotationVisitor;

public final class TraceAnnotationVisitor extends AnnotationVisitor {
  private final Printer p;
  
  public TraceAnnotationVisitor(Printer paramPrinter) {
    this(null, paramPrinter);
  }
  
  public TraceAnnotationVisitor(AnnotationVisitor paramAnnotationVisitor, Printer paramPrinter) {
    super(327680, paramAnnotationVisitor);
    this.p = paramPrinter;
  }
  
  public void visit(String paramString, Object paramObject) {
    this.p.visit(paramString, paramObject);
    super.visit(paramString, paramObject);
  }
  
  public void visitEnum(String paramString1, String paramString2, String paramString3) {
    this.p.visitEnum(paramString1, paramString2, paramString3);
    super.visitEnum(paramString1, paramString2, paramString3);
  }
  
  public AnnotationVisitor visitAnnotation(String paramString1, String paramString2) {
    Printer printer = this.p.visitAnnotation(paramString1, paramString2);
    AnnotationVisitor annotationVisitor = (this.av == null) ? null : this.av.visitAnnotation(paramString1, paramString2);
    return new TraceAnnotationVisitor(annotationVisitor, printer);
  }
  
  public AnnotationVisitor visitArray(String paramString) {
    Printer printer = this.p.visitArray(paramString);
    AnnotationVisitor annotationVisitor = (this.av == null) ? null : this.av.visitArray(paramString);
    return new TraceAnnotationVisitor(annotationVisitor, printer);
  }
  
  public void visitEnd() {
    this.p.visitAnnotationEnd();
    super.visitEnd();
  }
}
