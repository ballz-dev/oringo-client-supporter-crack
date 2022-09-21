package org.spongepowered.asm.lib.tree;

import org.spongepowered.asm.lib.TypePath;

public class TypeAnnotationNode extends AnnotationNode {
  public TypePath typePath;
  
  public int typeRef;
  
  public TypeAnnotationNode(int paramInt, TypePath paramTypePath, String paramString) {
    this(327680, paramInt, paramTypePath, paramString);
    if (getClass() != TypeAnnotationNode.class)
      throw new IllegalStateException(); 
  }
  
  public TypeAnnotationNode(int paramInt1, int paramInt2, TypePath paramTypePath, String paramString) {
    super(paramInt1, paramString);
    this.typeRef = paramInt2;
    this.typePath = paramTypePath;
  }
}
