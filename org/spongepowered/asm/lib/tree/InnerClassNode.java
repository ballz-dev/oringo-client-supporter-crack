package org.spongepowered.asm.lib.tree;

import org.spongepowered.asm.lib.ClassVisitor;

public class InnerClassNode {
  public String innerName;
  
  public String outerName;
  
  public int access;
  
  public String name;
  
  public InnerClassNode(String paramString1, String paramString2, String paramString3, int paramInt) {
    this.name = paramString1;
    this.outerName = paramString2;
    this.innerName = paramString3;
    this.access = paramInt;
  }
  
  public void accept(ClassVisitor paramClassVisitor) {
    paramClassVisitor.visitInnerClass(this.name, this.outerName, this.innerName, this.access);
  }
}
