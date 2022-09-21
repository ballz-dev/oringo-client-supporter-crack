package org.spongepowered.asm.lib.tree;

import org.spongepowered.asm.lib.MethodVisitor;

public class ParameterNode {
  public int access;
  
  public String name;
  
  public ParameterNode(String paramString, int paramInt) {
    this.name = paramString;
    this.access = paramInt;
  }
  
  public void accept(MethodVisitor paramMethodVisitor) {
    paramMethodVisitor.visitParameter(this.name, this.access);
  }
}
