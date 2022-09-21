package org.spongepowered.asm.mixin.injection.invoke.arg;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import java.util.HashMap;
import java.util.Map;
import org.spongepowered.asm.lib.ClassVisitor;
import org.spongepowered.asm.lib.ClassWriter;
import org.spongepowered.asm.lib.Label;
import org.spongepowered.asm.lib.MethodVisitor;
import org.spongepowered.asm.lib.Type;
import org.spongepowered.asm.lib.util.CheckClassAdapter;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.transformer.ext.IClassGenerator;
import org.spongepowered.asm.util.Bytecode;
import org.spongepowered.asm.util.SignaturePrinter;
import org.spongepowered.asm.util.asm.MethodVisitorEx;

public final class ArgsClassGenerator implements IClassGenerator {
  public static final String ARGS_NAME = Args.class.getName();
  
  static {
    ARGS_REF = ARGS_NAME.replace('.', '/');
  }
  
  private int nextIndex = 1;
  
  private final BiMap<String, String> classNames = (BiMap<String, String>)HashBiMap.create();
  
  private final Map<String, byte[]> classBytes = (Map)new HashMap<String, byte>();
  
  public static final String GETTER_PREFIX = "$";
  
  private static final String NPE_CTOR_DESC = "(Ljava/lang/String;)V";
  
  private static final String ACE_CTOR_DESC = "(IILjava/lang/String;)V";
  
  private static final String VALUES_FIELD = "values";
  
  private static final String AIOOBE_CTOR_DESC = "(I)V";
  
  private static final String CLASS_NAME_BASE = "org.spongepowered.asm.synthetic.args.Args$";
  
  private static final String NPE = "java/lang/NullPointerException";
  
  private static final String CTOR_DESC = "([Ljava/lang/Object;)V";
  
  private static final String ACE = "org/spongepowered/asm/mixin/injection/invoke/arg/ArgumentCountException";
  
  public static final String ARGS_REF;
  
  private static final String SET_DESC = "(ILjava/lang/Object;)V";
  
  private static final String OBJECT = "java/lang/Object";
  
  private static final String OBJECT_ARRAY = "[Ljava/lang/Object;";
  
  private static final String AIOOBE = "org/spongepowered/asm/mixin/injection/invoke/arg/ArgumentIndexOutOfBoundsException";
  
  private static final String SETALL_DESC = "([Ljava/lang/Object;)V";
  
  private static final String SETALL = "setAll";
  
  private static final String SET = "set";
  
  public String getClassName(String paramString) {
    String str1 = Bytecode.changeDescriptorReturnType(paramString, "V");
    String str2 = (String)this.classNames.get(str1);
    if (str2 == null) {
      str2 = String.format("%s%d", new Object[] { "org.spongepowered.asm.synthetic.args.Args$", Integer.valueOf(this.nextIndex++) });
      this.classNames.put(str1, str2);
    } 
    return str2;
  }
  
  public String getClassRef(String paramString) {
    return getClassName(paramString).replace('.', '/');
  }
  
  public byte[] generate(String paramString) {
    return getBytes(paramString);
  }
  
  public byte[] getBytes(String paramString) {
    byte[] arrayOfByte = this.classBytes.get(paramString);
    if (arrayOfByte == null) {
      String str = (String)this.classNames.inverse().get(paramString);
      if (str == null)
        return null; 
      arrayOfByte = generateClass(paramString, str);
      this.classBytes.put(paramString, arrayOfByte);
    } 
    return arrayOfByte;
  }
  
  private byte[] generateClass(String paramString1, String paramString2) {
    CheckClassAdapter checkClassAdapter;
    String str = paramString1.replace('.', '/');
    Type[] arrayOfType = Type.getArgumentTypes(paramString2);
    ClassWriter classWriter1 = new ClassWriter(2);
    ClassWriter classWriter2 = classWriter1;
    if (MixinEnvironment.getCurrentEnvironment().getOption(MixinEnvironment.Option.DEBUG_VERIFY))
      checkClassAdapter = new CheckClassAdapter((ClassVisitor)classWriter1); 
    checkClassAdapter.visit(50, 4129, str, null, ARGS_REF, null);
    checkClassAdapter.visitSource(paramString1.substring(paramString1.lastIndexOf('.') + 1) + ".java", null);
    generateCtor(str, paramString2, arrayOfType, (ClassVisitor)checkClassAdapter);
    generateToString(str, paramString2, arrayOfType, (ClassVisitor)checkClassAdapter);
    generateFactory(str, paramString2, arrayOfType, (ClassVisitor)checkClassAdapter);
    generateSetters(str, paramString2, arrayOfType, (ClassVisitor)checkClassAdapter);
    generateGetters(str, paramString2, arrayOfType, (ClassVisitor)checkClassAdapter);
    checkClassAdapter.visitEnd();
    return classWriter1.toByteArray();
  }
  
  private void generateCtor(String paramString1, String paramString2, Type[] paramArrayOfType, ClassVisitor paramClassVisitor) {
    MethodVisitor methodVisitor = paramClassVisitor.visitMethod(2, "<init>", "([Ljava/lang/Object;)V", null, null);
    methodVisitor.visitCode();
    methodVisitor.visitVarInsn(25, 0);
    methodVisitor.visitVarInsn(25, 1);
    methodVisitor.visitMethodInsn(183, ARGS_REF, "<init>", "([Ljava/lang/Object;)V", false);
    methodVisitor.visitInsn(177);
    methodVisitor.visitMaxs(2, 2);
    methodVisitor.visitEnd();
  }
  
  private void generateToString(String paramString1, String paramString2, Type[] paramArrayOfType, ClassVisitor paramClassVisitor) {
    MethodVisitor methodVisitor = paramClassVisitor.visitMethod(1, "toString", "()Ljava/lang/String;", null, null);
    methodVisitor.visitCode();
    methodVisitor.visitLdcInsn("Args" + getSignature(paramArrayOfType));
    methodVisitor.visitInsn(176);
    methodVisitor.visitMaxs(1, 1);
    methodVisitor.visitEnd();
  }
  
  private void generateFactory(String paramString1, String paramString2, Type[] paramArrayOfType, ClassVisitor paramClassVisitor) {
    String str = Bytecode.changeDescriptorReturnType(paramString2, "L" + paramString1 + ";");
    MethodVisitorEx methodVisitorEx = new MethodVisitorEx(paramClassVisitor.visitMethod(9, "of", str, null, null));
    methodVisitorEx.visitCode();
    methodVisitorEx.visitTypeInsn(187, paramString1);
    methodVisitorEx.visitInsn(89);
    methodVisitorEx.visitConstant((byte)paramArrayOfType.length);
    methodVisitorEx.visitTypeInsn(189, "java/lang/Object");
    byte b = 0;
    for (Type type : paramArrayOfType) {
      methodVisitorEx.visitInsn(89);
      methodVisitorEx.visitConstant(b);
      methodVisitorEx.visitVarInsn(type.getOpcode(21), b);
      box((MethodVisitor)methodVisitorEx, type);
      methodVisitorEx.visitInsn(83);
      b = (byte)(b + type.getSize());
    } 
    methodVisitorEx.visitMethodInsn(183, paramString1, "<init>", "([Ljava/lang/Object;)V", false);
    methodVisitorEx.visitInsn(176);
    methodVisitorEx.visitMaxs(6, Bytecode.getArgsSize(paramArrayOfType));
    methodVisitorEx.visitEnd();
  }
  
  private void generateGetters(String paramString1, String paramString2, Type[] paramArrayOfType, ClassVisitor paramClassVisitor) {
    byte b = 0;
    for (Type type : paramArrayOfType) {
      String str1 = "$" + b;
      String str2 = "()" + type.getDescriptor();
      MethodVisitorEx methodVisitorEx = new MethodVisitorEx(paramClassVisitor.visitMethod(1, str1, str2, null, null));
      methodVisitorEx.visitCode();
      methodVisitorEx.visitVarInsn(25, 0);
      methodVisitorEx.visitFieldInsn(180, paramString1, "values", "[Ljava/lang/Object;");
      methodVisitorEx.visitConstant(b);
      methodVisitorEx.visitInsn(50);
      unbox((MethodVisitor)methodVisitorEx, type);
      methodVisitorEx.visitInsn(type.getOpcode(172));
      methodVisitorEx.visitMaxs(2, 1);
      methodVisitorEx.visitEnd();
      b = (byte)(b + 1);
    } 
  }
  
  private void generateSetters(String paramString1, String paramString2, Type[] paramArrayOfType, ClassVisitor paramClassVisitor) {
    generateIndexedSetter(paramString1, paramString2, paramArrayOfType, paramClassVisitor);
    generateMultiSetter(paramString1, paramString2, paramArrayOfType, paramClassVisitor);
  }
  
  private void generateIndexedSetter(String paramString1, String paramString2, Type[] paramArrayOfType, ClassVisitor paramClassVisitor) {
    MethodVisitorEx methodVisitorEx = new MethodVisitorEx(paramClassVisitor.visitMethod(1, "set", "(ILjava/lang/Object;)V", null, null));
    methodVisitorEx.visitCode();
    Label label1 = new Label(), label2 = new Label();
    Label[] arrayOfLabel = new Label[paramArrayOfType.length];
    byte b;
    for (b = 0; b < arrayOfLabel.length; b++)
      arrayOfLabel[b] = new Label(); 
    methodVisitorEx.visitVarInsn(25, 0);
    methodVisitorEx.visitFieldInsn(180, paramString1, "values", "[Ljava/lang/Object;");
    for (b = 0; b < paramArrayOfType.length; b = (byte)(b + 1)) {
      methodVisitorEx.visitVarInsn(21, 1);
      methodVisitorEx.visitConstant(b);
      methodVisitorEx.visitJumpInsn(159, arrayOfLabel[b]);
    } 
    throwAIOOBE(methodVisitorEx, 1);
    for (b = 0; b < paramArrayOfType.length; b++) {
      String str = Bytecode.getBoxingType(paramArrayOfType[b]);
      methodVisitorEx.visitLabel(arrayOfLabel[b]);
      methodVisitorEx.visitVarInsn(21, 1);
      methodVisitorEx.visitVarInsn(25, 2);
      methodVisitorEx.visitTypeInsn(192, (str != null) ? str : paramArrayOfType[b].getInternalName());
      methodVisitorEx.visitJumpInsn(167, (str != null) ? label2 : label1);
    } 
    methodVisitorEx.visitLabel(label2);
    methodVisitorEx.visitInsn(89);
    methodVisitorEx.visitJumpInsn(199, label1);
    throwNPE(methodVisitorEx, "Argument with primitive type cannot be set to NULL");
    methodVisitorEx.visitLabel(label1);
    methodVisitorEx.visitInsn(83);
    methodVisitorEx.visitInsn(177);
    methodVisitorEx.visitMaxs(6, 3);
    methodVisitorEx.visitEnd();
  }
  
  private void generateMultiSetter(String paramString1, String paramString2, Type[] paramArrayOfType, ClassVisitor paramClassVisitor) {
    MethodVisitorEx methodVisitorEx = new MethodVisitorEx(paramClassVisitor.visitMethod(1, "setAll", "([Ljava/lang/Object;)V", null, null));
    methodVisitorEx.visitCode();
    Label label1 = new Label(), label2 = new Label();
    byte b = 6;
    methodVisitorEx.visitVarInsn(25, 1);
    methodVisitorEx.visitInsn(190);
    methodVisitorEx.visitInsn(89);
    methodVisitorEx.visitConstant((byte)paramArrayOfType.length);
    methodVisitorEx.visitJumpInsn(159, label1);
    methodVisitorEx.visitTypeInsn(187, "org/spongepowered/asm/mixin/injection/invoke/arg/ArgumentCountException");
    methodVisitorEx.visitInsn(89);
    methodVisitorEx.visitInsn(93);
    methodVisitorEx.visitInsn(88);
    methodVisitorEx.visitConstant((byte)paramArrayOfType.length);
    methodVisitorEx.visitLdcInsn(getSignature(paramArrayOfType));
    methodVisitorEx.visitMethodInsn(183, "org/spongepowered/asm/mixin/injection/invoke/arg/ArgumentCountException", "<init>", "(IILjava/lang/String;)V", false);
    methodVisitorEx.visitInsn(191);
    methodVisitorEx.visitLabel(label1);
    methodVisitorEx.visitInsn(87);
    methodVisitorEx.visitVarInsn(25, 0);
    methodVisitorEx.visitFieldInsn(180, paramString1, "values", "[Ljava/lang/Object;");
    byte b1;
    for (b1 = 0; b1 < paramArrayOfType.length; b1 = (byte)(b1 + 1)) {
      methodVisitorEx.visitInsn(89);
      methodVisitorEx.visitConstant(b1);
      methodVisitorEx.visitVarInsn(25, 1);
      methodVisitorEx.visitConstant(b1);
      methodVisitorEx.visitInsn(50);
      String str = Bytecode.getBoxingType(paramArrayOfType[b1]);
      methodVisitorEx.visitTypeInsn(192, (str != null) ? str : paramArrayOfType[b1].getInternalName());
      if (str != null) {
        methodVisitorEx.visitInsn(89);
        methodVisitorEx.visitJumpInsn(198, label2);
        b = 7;
      } 
      methodVisitorEx.visitInsn(83);
    } 
    methodVisitorEx.visitInsn(177);
    methodVisitorEx.visitLabel(label2);
    throwNPE(methodVisitorEx, "Argument with primitive type cannot be set to NULL");
    methodVisitorEx.visitInsn(177);
    methodVisitorEx.visitMaxs(b, 2);
    methodVisitorEx.visitEnd();
  }
  
  private static void throwNPE(MethodVisitorEx paramMethodVisitorEx, String paramString) {
    paramMethodVisitorEx.visitTypeInsn(187, "java/lang/NullPointerException");
    paramMethodVisitorEx.visitInsn(89);
    paramMethodVisitorEx.visitLdcInsn(paramString);
    paramMethodVisitorEx.visitMethodInsn(183, "java/lang/NullPointerException", "<init>", "(Ljava/lang/String;)V", false);
    paramMethodVisitorEx.visitInsn(191);
  }
  
  private static void throwAIOOBE(MethodVisitorEx paramMethodVisitorEx, int paramInt) {
    paramMethodVisitorEx.visitTypeInsn(187, "org/spongepowered/asm/mixin/injection/invoke/arg/ArgumentIndexOutOfBoundsException");
    paramMethodVisitorEx.visitInsn(89);
    paramMethodVisitorEx.visitVarInsn(21, paramInt);
    paramMethodVisitorEx.visitMethodInsn(183, "org/spongepowered/asm/mixin/injection/invoke/arg/ArgumentIndexOutOfBoundsException", "<init>", "(I)V", false);
    paramMethodVisitorEx.visitInsn(191);
  }
  
  private static void box(MethodVisitor paramMethodVisitor, Type paramType) {
    String str = Bytecode.getBoxingType(paramType);
    if (str != null) {
      String str1 = String.format("(%s)L%s;", new Object[] { paramType.getDescriptor(), str });
      paramMethodVisitor.visitMethodInsn(184, str, "valueOf", str1, false);
    } 
  }
  
  private static void unbox(MethodVisitor paramMethodVisitor, Type paramType) {
    String str = Bytecode.getBoxingType(paramType);
    if (str != null) {
      String str1 = Bytecode.getUnboxingMethod(paramType);
      String str2 = "()" + paramType.getDescriptor();
      paramMethodVisitor.visitTypeInsn(192, str);
      paramMethodVisitor.visitMethodInsn(182, str, str1, str2, false);
    } else {
      paramMethodVisitor.visitTypeInsn(192, paramType.getInternalName());
    } 
  }
  
  private static String getSignature(Type[] paramArrayOfType) {
    return (new SignaturePrinter("", null, paramArrayOfType)).setFullyQualified(true).getFormattedArgs();
  }
}
