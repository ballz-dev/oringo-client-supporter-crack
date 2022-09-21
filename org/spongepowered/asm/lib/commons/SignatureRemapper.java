package org.spongepowered.asm.lib.commons;

import java.util.Stack;
import org.spongepowered.asm.lib.signature.SignatureVisitor;

public class SignatureRemapper extends SignatureVisitor {
  private Stack<String> classNames = new Stack<String>();
  
  private final SignatureVisitor v;
  
  private final Remapper remapper;
  
  public SignatureRemapper(SignatureVisitor paramSignatureVisitor, Remapper paramRemapper) {
    this(327680, paramSignatureVisitor, paramRemapper);
  }
  
  protected SignatureRemapper(int paramInt, SignatureVisitor paramSignatureVisitor, Remapper paramRemapper) {
    super(paramInt);
    this.v = paramSignatureVisitor;
    this.remapper = paramRemapper;
  }
  
  public void visitClassType(String paramString) {
    this.classNames.push(paramString);
    this.v.visitClassType(this.remapper.mapType(paramString));
  }
  
  public void visitInnerClassType(String paramString) {
    String str1 = this.classNames.pop();
    String str2 = str1 + '$' + paramString;
    this.classNames.push(str2);
    String str3 = this.remapper.mapType(str1) + '$';
    String str4 = this.remapper.mapType(str2);
    int i = str4.startsWith(str3) ? str3.length() : (str4.lastIndexOf('$') + 1);
    this.v.visitInnerClassType(str4.substring(i));
  }
  
  public void visitFormalTypeParameter(String paramString) {
    this.v.visitFormalTypeParameter(paramString);
  }
  
  public void visitTypeVariable(String paramString) {
    this.v.visitTypeVariable(paramString);
  }
  
  public SignatureVisitor visitArrayType() {
    this.v.visitArrayType();
    return this;
  }
  
  public void visitBaseType(char paramChar) {
    this.v.visitBaseType(paramChar);
  }
  
  public SignatureVisitor visitClassBound() {
    this.v.visitClassBound();
    return this;
  }
  
  public SignatureVisitor visitExceptionType() {
    this.v.visitExceptionType();
    return this;
  }
  
  public SignatureVisitor visitInterface() {
    this.v.visitInterface();
    return this;
  }
  
  public SignatureVisitor visitInterfaceBound() {
    this.v.visitInterfaceBound();
    return this;
  }
  
  public SignatureVisitor visitParameterType() {
    this.v.visitParameterType();
    return this;
  }
  
  public SignatureVisitor visitReturnType() {
    this.v.visitReturnType();
    return this;
  }
  
  public SignatureVisitor visitSuperclass() {
    this.v.visitSuperclass();
    return this;
  }
  
  public void visitTypeArgument() {
    this.v.visitTypeArgument();
  }
  
  public SignatureVisitor visitTypeArgument(char paramChar) {
    this.v.visitTypeArgument(paramChar);
    return this;
  }
  
  public void visitEnd() {
    this.v.visitEnd();
    this.classNames.pop();
  }
}
