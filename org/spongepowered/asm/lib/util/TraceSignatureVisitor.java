package org.spongepowered.asm.lib.util;

import org.spongepowered.asm.lib.signature.SignatureVisitor;

public final class TraceSignatureVisitor extends SignatureVisitor {
  private String separator = "";
  
  private boolean seenFormalParameter;
  
  private int argumentStack;
  
  private boolean seenInterface;
  
  private final StringBuilder declaration;
  
  private StringBuilder returnType;
  
  private boolean seenParameter;
  
  private int arrayStack;
  
  private boolean isInterface;
  
  private boolean seenInterfaceBound;
  
  private StringBuilder exceptions;
  
  public TraceSignatureVisitor(int paramInt) {
    super(327680);
    this.isInterface = ((paramInt & 0x200) != 0);
    this.declaration = new StringBuilder();
  }
  
  private TraceSignatureVisitor(StringBuilder paramStringBuilder) {
    super(327680);
    this.declaration = paramStringBuilder;
  }
  
  public void visitFormalTypeParameter(String paramString) {
    this.declaration.append(this.seenFormalParameter ? ", " : "<").append(paramString);
    this.seenFormalParameter = true;
    this.seenInterfaceBound = false;
  }
  
  public SignatureVisitor visitClassBound() {
    this.separator = " extends ";
    startType();
    return this;
  }
  
  public SignatureVisitor visitInterfaceBound() {
    this.separator = this.seenInterfaceBound ? ", " : " extends ";
    this.seenInterfaceBound = true;
    startType();
    return this;
  }
  
  public SignatureVisitor visitSuperclass() {
    endFormals();
    this.separator = " extends ";
    startType();
    return this;
  }
  
  public SignatureVisitor visitInterface() {
    this.separator = this.seenInterface ? ", " : (this.isInterface ? " extends " : " implements ");
    this.seenInterface = true;
    startType();
    return this;
  }
  
  public SignatureVisitor visitParameterType() {
    endFormals();
    if (this.seenParameter) {
      this.declaration.append(", ");
    } else {
      this.seenParameter = true;
      this.declaration.append('(');
    } 
    startType();
    return this;
  }
  
  public SignatureVisitor visitReturnType() {
    endFormals();
    if (this.seenParameter) {
      this.seenParameter = false;
    } else {
      this.declaration.append('(');
    } 
    this.declaration.append(')');
    this.returnType = new StringBuilder();
    return new TraceSignatureVisitor(this.returnType);
  }
  
  public SignatureVisitor visitExceptionType() {
    if (this.exceptions == null) {
      this.exceptions = new StringBuilder();
    } else {
      this.exceptions.append(", ");
    } 
    return new TraceSignatureVisitor(this.exceptions);
  }
  
  public void visitBaseType(char paramChar) {
    switch (paramChar) {
      case 'V':
        this.declaration.append("void");
        break;
      case 'B':
        this.declaration.append("byte");
        break;
      case 'J':
        this.declaration.append("long");
        break;
      case 'Z':
        this.declaration.append("boolean");
        break;
      case 'I':
        this.declaration.append("int");
        break;
      case 'S':
        this.declaration.append("short");
        break;
      case 'C':
        this.declaration.append("char");
        break;
      case 'F':
        this.declaration.append("float");
        break;
      default:
        this.declaration.append("double");
        break;
    } 
    endType();
  }
  
  public void visitTypeVariable(String paramString) {
    this.declaration.append(paramString);
    endType();
  }
  
  public SignatureVisitor visitArrayType() {
    startType();
    this.arrayStack |= 0x1;
    return this;
  }
  
  public void visitClassType(String paramString) {
    if ("java/lang/Object".equals(paramString)) {
      boolean bool = (this.argumentStack % 2 != 0 || this.seenParameter) ? true : false;
      if (bool)
        this.declaration.append(this.separator).append(paramString.replace('/', '.')); 
    } else {
      this.declaration.append(this.separator).append(paramString.replace('/', '.'));
    } 
    this.separator = "";
    this.argumentStack *= 2;
  }
  
  public void visitInnerClassType(String paramString) {
    if (this.argumentStack % 2 != 0)
      this.declaration.append('>'); 
    this.argumentStack /= 2;
    this.declaration.append('.');
    this.declaration.append(this.separator).append(paramString.replace('/', '.'));
    this.separator = "";
    this.argumentStack *= 2;
  }
  
  public void visitTypeArgument() {
    if (this.argumentStack % 2 == 0) {
      this.argumentStack++;
      this.declaration.append('<');
    } else {
      this.declaration.append(", ");
    } 
    this.declaration.append('?');
  }
  
  public SignatureVisitor visitTypeArgument(char paramChar) {
    if (this.argumentStack % 2 == 0) {
      this.argumentStack++;
      this.declaration.append('<');
    } else {
      this.declaration.append(", ");
    } 
    if (paramChar == '+') {
      this.declaration.append("? extends ");
    } else if (paramChar == '-') {
      this.declaration.append("? super ");
    } 
    startType();
    return this;
  }
  
  public void visitEnd() {
    if (this.argumentStack % 2 != 0)
      this.declaration.append('>'); 
    this.argumentStack /= 2;
    endType();
  }
  
  public String getDeclaration() {
    return this.declaration.toString();
  }
  
  public String getReturnType() {
    return (this.returnType == null) ? null : this.returnType.toString();
  }
  
  public String getExceptions() {
    return (this.exceptions == null) ? null : this.exceptions.toString();
  }
  
  private void endFormals() {
    if (this.seenFormalParameter) {
      this.declaration.append('>');
      this.seenFormalParameter = false;
    } 
  }
  
  private void startType() {
    this.arrayStack *= 2;
  }
  
  private void endType() {
    if (this.arrayStack % 2 == 0) {
      this.arrayStack /= 2;
    } else {
      while (this.arrayStack % 2 != 0) {
        this.arrayStack /= 2;
        this.declaration.append("[]");
      } 
    } 
  }
}
