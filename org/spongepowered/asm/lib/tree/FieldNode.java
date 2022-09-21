package org.spongepowered.asm.lib.tree;

import java.util.ArrayList;
import java.util.List;
import org.spongepowered.asm.lib.AnnotationVisitor;
import org.spongepowered.asm.lib.Attribute;
import org.spongepowered.asm.lib.ClassVisitor;
import org.spongepowered.asm.lib.FieldVisitor;
import org.spongepowered.asm.lib.TypePath;

public class FieldNode extends FieldVisitor {
  public String name;
  
  public List<Attribute> attrs;
  
  public List<AnnotationNode> visibleAnnotations;
  
  public List<TypeAnnotationNode> invisibleTypeAnnotations;
  
  public Object value;
  
  public int access;
  
  public String desc;
  
  public String signature;
  
  public List<TypeAnnotationNode> visibleTypeAnnotations;
  
  public List<AnnotationNode> invisibleAnnotations;
  
  public FieldNode(int paramInt, String paramString1, String paramString2, String paramString3, Object paramObject) {
    this(327680, paramInt, paramString1, paramString2, paramString3, paramObject);
    if (getClass() != FieldNode.class)
      throw new IllegalStateException(); 
  }
  
  public FieldNode(int paramInt1, int paramInt2, String paramString1, String paramString2, String paramString3, Object paramObject) {
    super(paramInt1);
    this.access = paramInt2;
    this.name = paramString1;
    this.desc = paramString2;
    this.signature = paramString3;
    this.value = paramObject;
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
  
  public void visitAttribute(Attribute paramAttribute) {
    if (this.attrs == null)
      this.attrs = new ArrayList<Attribute>(1); 
    this.attrs.add(paramAttribute);
  }
  
  public void visitEnd() {}
  
  public void check(int paramInt) {
    if (paramInt == 262144) {
      if (this.visibleTypeAnnotations != null && this.visibleTypeAnnotations
        .size() > 0)
        throw new RuntimeException(); 
      if (this.invisibleTypeAnnotations != null && this.invisibleTypeAnnotations
        .size() > 0)
        throw new RuntimeException(); 
    } 
  }
  
  public void accept(ClassVisitor paramClassVisitor) {
    FieldVisitor fieldVisitor = paramClassVisitor.visitField(this.access, this.name, this.desc, this.signature, this.value);
    if (fieldVisitor == null)
      return; 
    byte b1 = (this.visibleAnnotations == null) ? 0 : this.visibleAnnotations.size();
    byte b2;
    for (b2 = 0; b2 < b1; b2++) {
      AnnotationNode annotationNode = this.visibleAnnotations.get(b2);
      annotationNode.accept(fieldVisitor.visitAnnotation(annotationNode.desc, true));
    } 
    b1 = (this.invisibleAnnotations == null) ? 0 : this.invisibleAnnotations.size();
    for (b2 = 0; b2 < b1; b2++) {
      AnnotationNode annotationNode = this.invisibleAnnotations.get(b2);
      annotationNode.accept(fieldVisitor.visitAnnotation(annotationNode.desc, false));
    } 
    b1 = (this.visibleTypeAnnotations == null) ? 0 : this.visibleTypeAnnotations.size();
    for (b2 = 0; b2 < b1; b2++) {
      TypeAnnotationNode typeAnnotationNode = this.visibleTypeAnnotations.get(b2);
      typeAnnotationNode.accept(fieldVisitor.visitTypeAnnotation(typeAnnotationNode.typeRef, typeAnnotationNode.typePath, typeAnnotationNode.desc, true));
    } 
    b1 = (this.invisibleTypeAnnotations == null) ? 0 : this.invisibleTypeAnnotations.size();
    for (b2 = 0; b2 < b1; b2++) {
      TypeAnnotationNode typeAnnotationNode = this.invisibleTypeAnnotations.get(b2);
      typeAnnotationNode.accept(fieldVisitor.visitTypeAnnotation(typeAnnotationNode.typeRef, typeAnnotationNode.typePath, typeAnnotationNode.desc, false));
    } 
    b1 = (this.attrs == null) ? 0 : this.attrs.size();
    for (b2 = 0; b2 < b1; b2++)
      fieldVisitor.visitAttribute(this.attrs.get(b2)); 
    fieldVisitor.visitEnd();
  }
}
