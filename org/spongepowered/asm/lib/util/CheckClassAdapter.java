package org.spongepowered.asm.lib.util;

import java.io.FileInputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.spongepowered.asm.lib.AnnotationVisitor;
import org.spongepowered.asm.lib.Attribute;
import org.spongepowered.asm.lib.ClassReader;
import org.spongepowered.asm.lib.ClassVisitor;
import org.spongepowered.asm.lib.FieldVisitor;
import org.spongepowered.asm.lib.Label;
import org.spongepowered.asm.lib.MethodVisitor;
import org.spongepowered.asm.lib.Type;
import org.spongepowered.asm.lib.TypePath;
import org.spongepowered.asm.lib.tree.ClassNode;
import org.spongepowered.asm.lib.tree.MethodNode;
import org.spongepowered.asm.lib.tree.TryCatchBlockNode;
import org.spongepowered.asm.lib.tree.analysis.Analyzer;
import org.spongepowered.asm.lib.tree.analysis.BasicValue;
import org.spongepowered.asm.lib.tree.analysis.Frame;
import org.spongepowered.asm.lib.tree.analysis.Interpreter;
import org.spongepowered.asm.lib.tree.analysis.SimpleVerifier;

public class CheckClassAdapter extends ClassVisitor {
  private boolean checkDataFlow;
  
  private int version;
  
  private boolean start;
  
  private boolean outer;
  
  private boolean source;
  
  private boolean end;
  
  private Map<Label, Integer> labels;
  
  public static void main(String[] paramArrayOfString) throws Exception {
    ClassReader classReader;
    if (paramArrayOfString.length != 1) {
      System.err.println("Verifies the given class.");
      System.err.println("Usage: CheckClassAdapter <fully qualified class name or class file name>");
      return;
    } 
    if (paramArrayOfString[0].endsWith(".class")) {
      classReader = new ClassReader(new FileInputStream(paramArrayOfString[0]));
    } else {
      classReader = new ClassReader(paramArrayOfString[0]);
    } 
    verify(classReader, false, new PrintWriter(System.err));
  }
  
  public static void verify(ClassReader paramClassReader, ClassLoader paramClassLoader, boolean paramBoolean, PrintWriter paramPrintWriter) {
    ClassNode classNode = new ClassNode();
    paramClassReader.accept(new CheckClassAdapter((ClassVisitor)classNode, false), 2);
    Type type = (classNode.superName == null) ? null : Type.getObjectType(classNode.superName);
    List<MethodNode> list = classNode.methods;
    ArrayList<Type> arrayList = new ArrayList();
    for (Iterator<String> iterator = classNode.interfaces.iterator(); iterator.hasNext();)
      arrayList.add(Type.getObjectType(iterator.next())); 
    for (byte b = 0; b < list.size(); b++) {
      MethodNode methodNode = list.get(b);
      SimpleVerifier simpleVerifier = new SimpleVerifier(Type.getObjectType(classNode.name), type, arrayList, ((classNode.access & 0x200) != 0));
      Analyzer<BasicValue> analyzer = new Analyzer((Interpreter)simpleVerifier);
      if (paramClassLoader != null)
        simpleVerifier.setClassLoader(paramClassLoader); 
      try {
        analyzer.analyze(classNode.name, methodNode);
        if (!paramBoolean)
          continue; 
      } catch (Exception exception) {
        exception.printStackTrace(paramPrintWriter);
      } 
      printAnalyzerResult(methodNode, analyzer, paramPrintWriter);
      continue;
    } 
    paramPrintWriter.flush();
  }
  
  public static void verify(ClassReader paramClassReader, boolean paramBoolean, PrintWriter paramPrintWriter) {
    verify(paramClassReader, null, paramBoolean, paramPrintWriter);
  }
  
  static void printAnalyzerResult(MethodNode paramMethodNode, Analyzer<BasicValue> paramAnalyzer, PrintWriter paramPrintWriter) {
    Frame[] arrayOfFrame = paramAnalyzer.getFrames();
    Textifier textifier = new Textifier();
    TraceMethodVisitor traceMethodVisitor = new TraceMethodVisitor(textifier);
    paramPrintWriter.println(paramMethodNode.name + paramMethodNode.desc);
    byte b;
    for (b = 0; b < paramMethodNode.instructions.size(); b++) {
      paramMethodNode.instructions.get(b).accept(traceMethodVisitor);
      StringBuilder stringBuilder = new StringBuilder();
      Frame frame = arrayOfFrame[b];
      if (frame == null) {
        stringBuilder.append('?');
      } else {
        byte b1;
        for (b1 = 0; b1 < frame.getLocals(); b1++)
          stringBuilder.append(getShortName(((BasicValue)frame.getLocal(b1)).toString()))
            .append(' '); 
        stringBuilder.append(" : ");
        for (b1 = 0; b1 < frame.getStackSize(); b1++)
          stringBuilder.append(getShortName(((BasicValue)frame.getStack(b1)).toString()))
            .append(' '); 
      } 
      while (stringBuilder.length() < paramMethodNode.maxStack + paramMethodNode.maxLocals + 1)
        stringBuilder.append(' '); 
      paramPrintWriter.print(Integer.toString(b + 100000).substring(1));
      paramPrintWriter.print(" " + stringBuilder + " : " + textifier.text.get(textifier.text.size() - 1));
    } 
    for (b = 0; b < paramMethodNode.tryCatchBlocks.size(); b++) {
      ((TryCatchBlockNode)paramMethodNode.tryCatchBlocks.get(b)).accept(traceMethodVisitor);
      paramPrintWriter.print(" " + textifier.text.get(textifier.text.size() - 1));
    } 
    paramPrintWriter.println();
  }
  
  private static String getShortName(String paramString) {
    int i = paramString.lastIndexOf('/');
    int j = paramString.length();
    if (paramString.charAt(j - 1) == ';')
      j--; 
    return (i == -1) ? paramString : paramString.substring(i + 1, j);
  }
  
  public CheckClassAdapter(ClassVisitor paramClassVisitor) {
    this(paramClassVisitor, true);
  }
  
  public CheckClassAdapter(ClassVisitor paramClassVisitor, boolean paramBoolean) {
    this(327680, paramClassVisitor, paramBoolean);
    if (getClass() != CheckClassAdapter.class)
      throw new IllegalStateException(); 
  }
  
  protected CheckClassAdapter(int paramInt, ClassVisitor paramClassVisitor, boolean paramBoolean) {
    super(paramInt, paramClassVisitor);
    this.labels = new HashMap<Label, Integer>();
    this.checkDataFlow = paramBoolean;
  }
  
  public void visit(int paramInt1, int paramInt2, String paramString1, String paramString2, String paramString3, String[] paramArrayOfString) {
    if (this.start)
      throw new IllegalStateException("visit must be called only once"); 
    this.start = true;
    checkState();
    checkAccess(paramInt2, 423473);
    if (paramString1 == null || !paramString1.endsWith("package-info"))
      CheckMethodAdapter.checkInternalName(paramString1, "class name"); 
    if ("java/lang/Object".equals(paramString1)) {
      if (paramString3 != null)
        throw new IllegalArgumentException("The super class name of the Object class must be 'null'"); 
    } else {
      CheckMethodAdapter.checkInternalName(paramString3, "super class name");
    } 
    if (paramString2 != null)
      checkClassSignature(paramString2); 
    if ((paramInt2 & 0x200) != 0 && 
      !"java/lang/Object".equals(paramString3))
      throw new IllegalArgumentException("The super class name of interfaces must be 'java/lang/Object'"); 
    if (paramArrayOfString != null)
      for (byte b = 0; b < paramArrayOfString.length; b++)
        CheckMethodAdapter.checkInternalName(paramArrayOfString[b], "interface name at index " + b);  
    this.version = paramInt1;
    super.visit(paramInt1, paramInt2, paramString1, paramString2, paramString3, paramArrayOfString);
  }
  
  public void visitSource(String paramString1, String paramString2) {
    checkState();
    if (this.source)
      throw new IllegalStateException("visitSource can be called only once."); 
    this.source = true;
    super.visitSource(paramString1, paramString2);
  }
  
  public void visitOuterClass(String paramString1, String paramString2, String paramString3) {
    checkState();
    if (this.outer)
      throw new IllegalStateException("visitOuterClass can be called only once."); 
    this.outer = true;
    if (paramString1 == null)
      throw new IllegalArgumentException("Illegal outer class owner"); 
    if (paramString3 != null)
      CheckMethodAdapter.checkMethodDesc(paramString3); 
    super.visitOuterClass(paramString1, paramString2, paramString3);
  }
  
  public void visitInnerClass(String paramString1, String paramString2, String paramString3, int paramInt) {
    checkState();
    CheckMethodAdapter.checkInternalName(paramString1, "class name");
    if (paramString2 != null)
      CheckMethodAdapter.checkInternalName(paramString2, "outer class name"); 
    if (paramString3 != null) {
      byte b = 0;
      while (b < paramString3.length() && 
        Character.isDigit(paramString3.charAt(b)))
        b++; 
      if (b == 0 || b < paramString3.length())
        CheckMethodAdapter.checkIdentifier(paramString3, b, -1, "inner class name"); 
    } 
    checkAccess(paramInt, 30239);
    super.visitInnerClass(paramString1, paramString2, paramString3, paramInt);
  }
  
  public FieldVisitor visitField(int paramInt, String paramString1, String paramString2, String paramString3, Object paramObject) {
    checkState();
    checkAccess(paramInt, 413919);
    CheckMethodAdapter.checkUnqualifiedName(this.version, paramString1, "field name");
    CheckMethodAdapter.checkDesc(paramString2, false);
    if (paramString3 != null)
      checkFieldSignature(paramString3); 
    if (paramObject != null)
      CheckMethodAdapter.checkConstant(paramObject); 
    FieldVisitor fieldVisitor = super.visitField(paramInt, paramString1, paramString2, paramString3, paramObject);
    return new CheckFieldAdapter(fieldVisitor);
  }
  
  public MethodVisitor visitMethod(int paramInt, String paramString1, String paramString2, String paramString3, String[] paramArrayOfString) {
    CheckMethodAdapter checkMethodAdapter;
    checkState();
    checkAccess(paramInt, 400895);
    if (!"<init>".equals(paramString1) && !"<clinit>".equals(paramString1))
      CheckMethodAdapter.checkMethodIdentifier(this.version, paramString1, "method name"); 
    CheckMethodAdapter.checkMethodDesc(paramString2);
    if (paramString3 != null)
      checkMethodSignature(paramString3); 
    if (paramArrayOfString != null)
      for (byte b = 0; b < paramArrayOfString.length; b++)
        CheckMethodAdapter.checkInternalName(paramArrayOfString[b], "exception name at index " + b);  
    if (this.checkDataFlow) {
      checkMethodAdapter = new CheckMethodAdapter(paramInt, paramString1, paramString2, super.visitMethod(paramInt, paramString1, paramString2, paramString3, paramArrayOfString), this.labels);
    } else {
      checkMethodAdapter = new CheckMethodAdapter(super.visitMethod(paramInt, paramString1, paramString2, paramString3, paramArrayOfString), this.labels);
    } 
    checkMethodAdapter.version = this.version;
    return checkMethodAdapter;
  }
  
  public AnnotationVisitor visitAnnotation(String paramString, boolean paramBoolean) {
    checkState();
    CheckMethodAdapter.checkDesc(paramString, false);
    return new CheckAnnotationAdapter(super.visitAnnotation(paramString, paramBoolean));
  }
  
  public AnnotationVisitor visitTypeAnnotation(int paramInt, TypePath paramTypePath, String paramString, boolean paramBoolean) {
    checkState();
    int i = paramInt >>> 24;
    if (i != 0 && i != 17 && i != 16)
      throw new IllegalArgumentException("Invalid type reference sort 0x" + 
          Integer.toHexString(i)); 
    checkTypeRefAndPath(paramInt, paramTypePath);
    CheckMethodAdapter.checkDesc(paramString, false);
    return new CheckAnnotationAdapter(super.visitTypeAnnotation(paramInt, paramTypePath, paramString, paramBoolean));
  }
  
  public void visitAttribute(Attribute paramAttribute) {
    checkState();
    if (paramAttribute == null)
      throw new IllegalArgumentException("Invalid attribute (must not be null)"); 
    super.visitAttribute(paramAttribute);
  }
  
  public void visitEnd() {
    checkState();
    this.end = true;
    super.visitEnd();
  }
  
  private void checkState() {
    if (!this.start)
      throw new IllegalStateException("Cannot visit member before visit has been called."); 
    if (this.end)
      throw new IllegalStateException("Cannot visit member after visitEnd has been called."); 
  }
  
  static void checkAccess(int paramInt1, int paramInt2) {
    if ((paramInt1 & (paramInt2 ^ 0xFFFFFFFF)) != 0)
      throw new IllegalArgumentException("Invalid access flags: " + paramInt1); 
    byte b1 = ((paramInt1 & 0x1) == 0) ? 0 : 1;
    byte b2 = ((paramInt1 & 0x2) == 0) ? 0 : 1;
    byte b3 = ((paramInt1 & 0x4) == 0) ? 0 : 1;
    if (b1 + b2 + b3 > 1)
      throw new IllegalArgumentException("public private and protected are mutually exclusive: " + paramInt1); 
    byte b4 = ((paramInt1 & 0x10) == 0) ? 0 : 1;
    byte b5 = ((paramInt1 & 0x400) == 0) ? 0 : 1;
    if (b4 + b5 > 1)
      throw new IllegalArgumentException("final and abstract are mutually exclusive: " + paramInt1); 
  }
  
  public static void checkClassSignature(String paramString) {
    int i = 0;
    if (getChar(paramString, 0) == '<')
      i = checkFormalTypeParameters(paramString, i); 
    i = checkClassTypeSignature(paramString, i);
    while (getChar(paramString, i) == 'L')
      i = checkClassTypeSignature(paramString, i); 
    if (i != paramString.length())
      throw new IllegalArgumentException(paramString + ": error at index " + i); 
  }
  
  public static void checkMethodSignature(String paramString) {
    int i = 0;
    if (getChar(paramString, 0) == '<')
      i = checkFormalTypeParameters(paramString, i); 
    i = checkChar('(', paramString, i);
    while ("ZCBSIFJDL[T".indexOf(getChar(paramString, i)) != -1)
      i = checkTypeSignature(paramString, i); 
    i = checkChar(')', paramString, i);
    if (getChar(paramString, i) == 'V') {
      i++;
    } else {
      i = checkTypeSignature(paramString, i);
    } 
    while (getChar(paramString, i) == '^') {
      i++;
      if (getChar(paramString, i) == 'L') {
        i = checkClassTypeSignature(paramString, i);
        continue;
      } 
      i = checkTypeVariableSignature(paramString, i);
    } 
    if (i != paramString.length())
      throw new IllegalArgumentException(paramString + ": error at index " + i); 
  }
  
  public static void checkFieldSignature(String paramString) {
    int i = checkFieldTypeSignature(paramString, 0);
    if (i != paramString.length())
      throw new IllegalArgumentException(paramString + ": error at index " + i); 
  }
  
  static void checkTypeRefAndPath(int paramInt, TypePath paramTypePath) {
    int i = 0;
    switch (paramInt >>> 24) {
      case 0:
      case 1:
      case 22:
        i = -65536;
        break;
      case 19:
      case 20:
      case 21:
      case 64:
      case 65:
      case 67:
      case 68:
      case 69:
      case 70:
        i = -16777216;
        break;
      case 16:
      case 17:
      case 18:
      case 23:
      case 66:
        i = -256;
        break;
      case 71:
      case 72:
      case 73:
      case 74:
      case 75:
        i = -16776961;
        break;
      default:
        throw new IllegalArgumentException("Invalid type reference sort 0x" + 
            Integer.toHexString(paramInt >>> 24));
    } 
    if ((paramInt & (i ^ 0xFFFFFFFF)) != 0)
      throw new IllegalArgumentException("Invalid type reference 0x" + 
          Integer.toHexString(paramInt)); 
    if (paramTypePath != null)
      for (byte b = 0; b < paramTypePath.getLength(); b++) {
        int j = paramTypePath.getStep(b);
        if (j != 0 && j != 1 && j != 3 && j != 2)
          throw new IllegalArgumentException("Invalid type path step " + b + " in " + paramTypePath); 
        if (j != 3 && paramTypePath
          .getStepArgument(b) != 0)
          throw new IllegalArgumentException("Invalid type path step argument for step " + b + " in " + paramTypePath); 
      }  
  }
  
  private static int checkFormalTypeParameters(String paramString, int paramInt) {
    paramInt = checkChar('<', paramString, paramInt);
    paramInt = checkFormalTypeParameter(paramString, paramInt);
    while (getChar(paramString, paramInt) != '>')
      paramInt = checkFormalTypeParameter(paramString, paramInt); 
    return paramInt + 1;
  }
  
  private static int checkFormalTypeParameter(String paramString, int paramInt) {
    paramInt = checkIdentifier(paramString, paramInt);
    paramInt = checkChar(':', paramString, paramInt);
    if ("L[T".indexOf(getChar(paramString, paramInt)) != -1)
      paramInt = checkFieldTypeSignature(paramString, paramInt); 
    while (getChar(paramString, paramInt) == ':')
      paramInt = checkFieldTypeSignature(paramString, paramInt + 1); 
    return paramInt;
  }
  
  private static int checkFieldTypeSignature(String paramString, int paramInt) {
    switch (getChar(paramString, paramInt)) {
      case 'L':
        return checkClassTypeSignature(paramString, paramInt);
      case '[':
        return checkTypeSignature(paramString, paramInt + 1);
    } 
    return checkTypeVariableSignature(paramString, paramInt);
  }
  
  private static int checkClassTypeSignature(String paramString, int paramInt) {
    paramInt = checkChar('L', paramString, paramInt);
    paramInt = checkIdentifier(paramString, paramInt);
    while (getChar(paramString, paramInt) == '/')
      paramInt = checkIdentifier(paramString, paramInt + 1); 
    if (getChar(paramString, paramInt) == '<')
      paramInt = checkTypeArguments(paramString, paramInt); 
    while (getChar(paramString, paramInt) == '.') {
      paramInt = checkIdentifier(paramString, paramInt + 1);
      if (getChar(paramString, paramInt) == '<')
        paramInt = checkTypeArguments(paramString, paramInt); 
    } 
    return checkChar(';', paramString, paramInt);
  }
  
  private static int checkTypeArguments(String paramString, int paramInt) {
    paramInt = checkChar('<', paramString, paramInt);
    paramInt = checkTypeArgument(paramString, paramInt);
    while (getChar(paramString, paramInt) != '>')
      paramInt = checkTypeArgument(paramString, paramInt); 
    return paramInt + 1;
  }
  
  private static int checkTypeArgument(String paramString, int paramInt) {
    char c = getChar(paramString, paramInt);
    if (c == '*')
      return paramInt + 1; 
    if (c == '+' || c == '-')
      paramInt++; 
    return checkFieldTypeSignature(paramString, paramInt);
  }
  
  private static int checkTypeVariableSignature(String paramString, int paramInt) {
    paramInt = checkChar('T', paramString, paramInt);
    paramInt = checkIdentifier(paramString, paramInt);
    return checkChar(';', paramString, paramInt);
  }
  
  private static int checkTypeSignature(String paramString, int paramInt) {
    switch (getChar(paramString, paramInt)) {
      case 'B':
      case 'C':
      case 'D':
      case 'F':
      case 'I':
      case 'J':
      case 'S':
      case 'Z':
        return paramInt + 1;
    } 
    return checkFieldTypeSignature(paramString, paramInt);
  }
  
  private static int checkIdentifier(String paramString, int paramInt) {
    if (!Character.isJavaIdentifierStart(getChar(paramString, paramInt)))
      throw new IllegalArgumentException(paramString + ": identifier expected at index " + paramInt); 
    paramInt++;
    while (Character.isJavaIdentifierPart(getChar(paramString, paramInt)))
      paramInt++; 
    return paramInt;
  }
  
  private static int checkChar(char paramChar, String paramString, int paramInt) {
    if (getChar(paramString, paramInt) == paramChar)
      return paramInt + 1; 
    throw new IllegalArgumentException(paramString + ": '" + paramChar + "' expected at index " + paramInt);
  }
  
  private static char getChar(String paramString, int paramInt) {
    return (paramInt < paramString.length()) ? paramString.charAt(paramInt) : Character.MIN_VALUE;
  }
}
