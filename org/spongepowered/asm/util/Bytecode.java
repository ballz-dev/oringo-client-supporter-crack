package org.spongepowered.asm.util;

import com.google.common.base.Joiner;
import com.google.common.primitives.Ints;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.regex.Pattern;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.lib.ClassReader;
import org.spongepowered.asm.lib.ClassVisitor;
import org.spongepowered.asm.lib.ClassWriter;
import org.spongepowered.asm.lib.MethodVisitor;
import org.spongepowered.asm.lib.Opcodes;
import org.spongepowered.asm.lib.Type;
import org.spongepowered.asm.lib.tree.AbstractInsnNode;
import org.spongepowered.asm.lib.tree.AnnotationNode;
import org.spongepowered.asm.lib.tree.ClassNode;
import org.spongepowered.asm.lib.tree.FieldInsnNode;
import org.spongepowered.asm.lib.tree.FieldNode;
import org.spongepowered.asm.lib.tree.FrameNode;
import org.spongepowered.asm.lib.tree.InsnList;
import org.spongepowered.asm.lib.tree.IntInsnNode;
import org.spongepowered.asm.lib.tree.JumpInsnNode;
import org.spongepowered.asm.lib.tree.LabelNode;
import org.spongepowered.asm.lib.tree.LdcInsnNode;
import org.spongepowered.asm.lib.tree.LineNumberNode;
import org.spongepowered.asm.lib.tree.MethodInsnNode;
import org.spongepowered.asm.lib.tree.MethodNode;
import org.spongepowered.asm.lib.tree.TypeInsnNode;
import org.spongepowered.asm.lib.tree.VarInsnNode;
import org.spongepowered.asm.lib.util.CheckClassAdapter;
import org.spongepowered.asm.lib.util.TraceClassVisitor;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Intrinsic;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.util.throwables.SyntheticBridgeException;

public final class Bytecode {
  private static final String[] UNBOXING_METHODS;
  
  private static final String[] BOXING_TYPES;
  
  private static final Object[] CONSTANTS_VALUES;
  
  private static final Class<?>[] MERGEABLE_MIXIN_ANNOTATIONS;
  
  public static final int[] CONSTANTS_DOUBLE;
  
  public static final int[] CONSTANTS_FLOAT;
  
  public static final int[] CONSTANTS_ALL;
  
  private static final Logger logger;
  
  private static Pattern mergeableAnnotationPattern;
  
  public static final int[] CONSTANTS_LONG;
  
  public enum Visibility {
    PRIVATE(2),
    PACKAGE(2),
    PUBLIC(2),
    PROTECTED(4);
    
    final int access;
    
    static final int MASK = 7;
    
    static {
      $VALUES = new Visibility[] { PRIVATE, PROTECTED, PACKAGE, PUBLIC };
    }
    
    Visibility(int param1Int1) {
      this.access = param1Int1;
    }
  }
  
  public static final int[] CONSTANTS_INT = new int[] { 2, 3, 4, 5, 6, 7, 8 };
  
  private static final String[] CONSTANTS_TYPES;
  
  static {
    CONSTANTS_FLOAT = new int[] { 11, 12, 13 };
    CONSTANTS_DOUBLE = new int[] { 14, 15 };
    CONSTANTS_LONG = new int[] { 9, 10 };
    CONSTANTS_ALL = new int[] { 
        1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 
        11, 12, 13, 14, 15, 16, 17, 18 };
    CONSTANTS_VALUES = new Object[] { 
        null, Integer.valueOf(-1), Integer.valueOf(0), Integer.valueOf(1), Integer.valueOf(2), Integer.valueOf(3), Integer.valueOf(4), Integer.valueOf(5), Long.valueOf(0L), Long.valueOf(1L), 
        Float.valueOf(0.0F), Float.valueOf(1.0F), Float.valueOf(2.0F), Double.valueOf(0.0D), Double.valueOf(1.0D) };
    CONSTANTS_TYPES = new String[] { 
        null, "I", "I", "I", "I", "I", "I", "I", "J", "J", 
        "F", "F", "F", "D", "D", "I", "I" };
    BOXING_TYPES = new String[] { 
        null, "java/lang/Boolean", "java/lang/Character", "java/lang/Byte", "java/lang/Short", "java/lang/Integer", "java/lang/Float", "java/lang/Long", "java/lang/Double", null, 
        null, null };
    UNBOXING_METHODS = new String[] { 
        null, "booleanValue", "charValue", "byteValue", "shortValue", "intValue", "floatValue", "longValue", "doubleValue", null, 
        null, null };
    MERGEABLE_MIXIN_ANNOTATIONS = new Class[] { Overwrite.class, Intrinsic.class, Final.class, Debug.class };
    mergeableAnnotationPattern = getMergeableAnnotationPattern();
    logger = LogManager.getLogger("mixin");
  }
  
  public static MethodNode findMethod(ClassNode paramClassNode, String paramString1, String paramString2) {
    for (MethodNode methodNode : paramClassNode.methods) {
      if (methodNode.name.equals(paramString1) && methodNode.desc.equals(paramString2))
        return methodNode; 
    } 
    return null;
  }
  
  public static AbstractInsnNode findInsn(MethodNode paramMethodNode, int paramInt) {
    ListIterator<AbstractInsnNode> listIterator = paramMethodNode.instructions.iterator();
    while (listIterator.hasNext()) {
      AbstractInsnNode abstractInsnNode = listIterator.next();
      if (abstractInsnNode.getOpcode() == paramInt)
        return abstractInsnNode; 
    } 
    return null;
  }
  
  public static MethodInsnNode findSuperInit(MethodNode paramMethodNode, String paramString) {
    if (!"<init>".equals(paramMethodNode.name))
      return null; 
    byte b = 0;
    for (ListIterator<AbstractInsnNode> listIterator = paramMethodNode.instructions.iterator(); listIterator.hasNext(); ) {
      AbstractInsnNode abstractInsnNode = listIterator.next();
      if (abstractInsnNode instanceof TypeInsnNode && abstractInsnNode.getOpcode() == 187) {
        b++;
        continue;
      } 
      if (abstractInsnNode instanceof MethodInsnNode && abstractInsnNode.getOpcode() == 183) {
        MethodInsnNode methodInsnNode = (MethodInsnNode)abstractInsnNode;
        if ("<init>".equals(methodInsnNode.name)) {
          if (b > 0) {
            b--;
            continue;
          } 
          if (methodInsnNode.owner.equals(paramString))
            return methodInsnNode; 
        } 
      } 
    } 
    return null;
  }
  
  public static void textify(ClassNode paramClassNode, OutputStream paramOutputStream) {
    paramClassNode.accept((ClassVisitor)new TraceClassVisitor(new PrintWriter(paramOutputStream)));
  }
  
  public static void textify(MethodNode paramMethodNode, OutputStream paramOutputStream) {
    TraceClassVisitor traceClassVisitor = new TraceClassVisitor(new PrintWriter(paramOutputStream));
    MethodVisitor methodVisitor = traceClassVisitor.visitMethod(paramMethodNode.access, paramMethodNode.name, paramMethodNode.desc, paramMethodNode.signature, (String[])paramMethodNode.exceptions
        .toArray((Object[])new String[0]));
    paramMethodNode.accept(methodVisitor);
    traceClassVisitor.visitEnd();
  }
  
  public static void dumpClass(ClassNode paramClassNode) {
    ClassWriter classWriter = new ClassWriter(3);
    paramClassNode.accept((ClassVisitor)classWriter);
    dumpClass(classWriter.toByteArray());
  }
  
  public static void dumpClass(byte[] paramArrayOfbyte) {
    ClassReader classReader = new ClassReader(paramArrayOfbyte);
    CheckClassAdapter.verify(classReader, true, new PrintWriter(System.out));
  }
  
  public static void printMethodWithOpcodeIndices(MethodNode paramMethodNode) {
    System.err.printf("%s%s\n", new Object[] { paramMethodNode.name, paramMethodNode.desc });
    byte b = 0;
    for (ListIterator<AbstractInsnNode> listIterator = paramMethodNode.instructions.iterator(); listIterator.hasNext();) {
      System.err.printf("[%4d] %s\n", new Object[] { Integer.valueOf(b++), describeNode(listIterator.next()) });
    } 
  }
  
  public static void printMethod(MethodNode paramMethodNode) {
    System.err.printf("%s%s\n", new Object[] { paramMethodNode.name, paramMethodNode.desc });
    for (ListIterator<AbstractInsnNode> listIterator = paramMethodNode.instructions.iterator(); listIterator.hasNext(); ) {
      System.err.print("  ");
      printNode(listIterator.next());
    } 
  }
  
  public static void printNode(AbstractInsnNode paramAbstractInsnNode) {
    System.err.printf("%s\n", new Object[] { describeNode(paramAbstractInsnNode) });
  }
  
  public static String describeNode(AbstractInsnNode paramAbstractInsnNode) {
    if (paramAbstractInsnNode == null)
      return String.format("   %-14s ", new Object[] { "null" }); 
    if (paramAbstractInsnNode instanceof LabelNode)
      return String.format("[%s]", new Object[] { ((LabelNode)paramAbstractInsnNode).getLabel() }); 
    String str = String.format("   %-14s ", new Object[] { paramAbstractInsnNode.getClass().getSimpleName().replace("Node", "") });
    if (paramAbstractInsnNode instanceof JumpInsnNode) {
      str = str + String.format("[%s] [%s]", new Object[] { getOpcodeName(paramAbstractInsnNode), ((JumpInsnNode)paramAbstractInsnNode).label.getLabel() });
    } else if (paramAbstractInsnNode instanceof VarInsnNode) {
      str = str + String.format("[%s] %d", new Object[] { getOpcodeName(paramAbstractInsnNode), Integer.valueOf(((VarInsnNode)paramAbstractInsnNode).var) });
    } else if (paramAbstractInsnNode instanceof MethodInsnNode) {
      MethodInsnNode methodInsnNode = (MethodInsnNode)paramAbstractInsnNode;
      str = str + String.format("[%s] %s %s %s", new Object[] { getOpcodeName(paramAbstractInsnNode), methodInsnNode.owner, methodInsnNode.name, methodInsnNode.desc });
    } else if (paramAbstractInsnNode instanceof FieldInsnNode) {
      FieldInsnNode fieldInsnNode = (FieldInsnNode)paramAbstractInsnNode;
      str = str + String.format("[%s] %s %s %s", new Object[] { getOpcodeName(paramAbstractInsnNode), fieldInsnNode.owner, fieldInsnNode.name, fieldInsnNode.desc });
    } else if (paramAbstractInsnNode instanceof LineNumberNode) {
      LineNumberNode lineNumberNode = (LineNumberNode)paramAbstractInsnNode;
      str = str + String.format("LINE=[%d] LABEL=[%s]", new Object[] { Integer.valueOf(lineNumberNode.line), lineNumberNode.start.getLabel() });
    } else if (paramAbstractInsnNode instanceof LdcInsnNode) {
      str = str + ((LdcInsnNode)paramAbstractInsnNode).cst;
    } else if (paramAbstractInsnNode instanceof IntInsnNode) {
      str = str + ((IntInsnNode)paramAbstractInsnNode).operand;
    } else if (paramAbstractInsnNode instanceof FrameNode) {
      str = str + String.format("[%s] ", new Object[] { getOpcodeName(((FrameNode)paramAbstractInsnNode).type, "H_INVOKEINTERFACE", -1) });
    } else {
      str = str + String.format("[%s] ", new Object[] { getOpcodeName(paramAbstractInsnNode) });
    } 
    return str;
  }
  
  public static String getOpcodeName(AbstractInsnNode paramAbstractInsnNode) {
    return (paramAbstractInsnNode != null) ? getOpcodeName(paramAbstractInsnNode.getOpcode()) : "";
  }
  
  public static String getOpcodeName(int paramInt) {
    return getOpcodeName(paramInt, "UNINITIALIZED_THIS", 1);
  }
  
  private static String getOpcodeName(int paramInt1, String paramString, int paramInt2) {
    if (paramInt1 >= paramInt2) {
      boolean bool = false;
      try {
        for (Field field : Opcodes.class.getDeclaredFields()) {
          if (bool || field.getName().equals(paramString)) {
            bool = true;
            if (field.getType() == int.class && field.getInt(null) == paramInt1)
              return field.getName(); 
          } 
        } 
      } catch (Exception exception) {}
    } 
    return (paramInt1 >= 0) ? String.valueOf(paramInt1) : "UNKNOWN";
  }
  
  public static boolean methodHasLineNumbers(MethodNode paramMethodNode) {
    for (ListIterator listIterator = paramMethodNode.instructions.iterator(); listIterator.hasNext();) {
      if (listIterator.next() instanceof LineNumberNode)
        return true; 
    } 
    return false;
  }
  
  public static boolean methodIsStatic(MethodNode paramMethodNode) {
    return ((paramMethodNode.access & 0x8) == 8);
  }
  
  public static boolean fieldIsStatic(FieldNode paramFieldNode) {
    return ((paramFieldNode.access & 0x8) == 8);
  }
  
  public static int getFirstNonArgLocalIndex(MethodNode paramMethodNode) {
    return getFirstNonArgLocalIndex(Type.getArgumentTypes(paramMethodNode.desc), ((paramMethodNode.access & 0x8) == 0));
  }
  
  public static int getFirstNonArgLocalIndex(Type[] paramArrayOfType, boolean paramBoolean) {
    return getArgsSize(paramArrayOfType) + (paramBoolean ? 1 : 0);
  }
  
  public static int getArgsSize(Type[] paramArrayOfType) {
    int i = 0;
    for (Type type : paramArrayOfType)
      i += type.getSize(); 
    return i;
  }
  
  public static void loadArgs(Type[] paramArrayOfType, InsnList paramInsnList, int paramInt) {
    loadArgs(paramArrayOfType, paramInsnList, paramInt, -1);
  }
  
  public static void loadArgs(Type[] paramArrayOfType, InsnList paramInsnList, int paramInt1, int paramInt2) {
    loadArgs(paramArrayOfType, paramInsnList, paramInt1, paramInt2, null);
  }
  
  public static void loadArgs(Type[] paramArrayOfType1, InsnList paramInsnList, int paramInt1, int paramInt2, Type[] paramArrayOfType2) {
    int i = paramInt1;
    byte b = 0;
    for (Type type : paramArrayOfType1) {
      paramInsnList.add((AbstractInsnNode)new VarInsnNode(type.getOpcode(21), i));
      if (paramArrayOfType2 != null && b < paramArrayOfType2.length && paramArrayOfType2[b] != null)
        paramInsnList.add((AbstractInsnNode)new TypeInsnNode(192, paramArrayOfType2[b].getInternalName())); 
      i += type.getSize();
      if (paramInt2 >= paramInt1 && i >= paramInt2)
        return; 
      b++;
    } 
  }
  
  public static Map<LabelNode, LabelNode> cloneLabels(InsnList paramInsnList) {
    HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
    for (ListIterator<AbstractInsnNode> listIterator = paramInsnList.iterator(); listIterator.hasNext(); ) {
      AbstractInsnNode abstractInsnNode = listIterator.next();
      if (abstractInsnNode instanceof LabelNode)
        hashMap.put(abstractInsnNode, new LabelNode(((LabelNode)abstractInsnNode).getLabel())); 
    } 
    return (Map)hashMap;
  }
  
  public static String generateDescriptor(Object paramObject, Object... paramVarArgs) {
    StringBuilder stringBuilder = (new StringBuilder()).append('(');
    for (Object object : paramVarArgs)
      stringBuilder.append(toDescriptor(object)); 
    return stringBuilder.append(')').append((paramObject != null) ? toDescriptor(paramObject) : "V").toString();
  }
  
  private static String toDescriptor(Object paramObject) {
    if (paramObject instanceof String)
      return (String)paramObject; 
    if (paramObject instanceof Type)
      return paramObject.toString(); 
    if (paramObject instanceof Class)
      return Type.getDescriptor((Class)paramObject); 
    return (paramObject == null) ? "" : paramObject.toString();
  }
  
  public static String getDescriptor(Type[] paramArrayOfType) {
    return "(" + Joiner.on("").join((Object[])paramArrayOfType) + ")";
  }
  
  public static String getDescriptor(Type[] paramArrayOfType, Type paramType) {
    return getDescriptor(paramArrayOfType) + paramType.toString();
  }
  
  public static String changeDescriptorReturnType(String paramString1, String paramString2) {
    if (paramString1 == null)
      return null; 
    if (paramString2 == null)
      return paramString1; 
    return paramString1.substring(0, paramString1.lastIndexOf(')') + 1) + paramString2;
  }
  
  public static String getSimpleName(Class<? extends Annotation> paramClass) {
    return paramClass.getSimpleName();
  }
  
  public static String getSimpleName(AnnotationNode paramAnnotationNode) {
    return getSimpleName(paramAnnotationNode.desc);
  }
  
  public static String getSimpleName(String paramString) {
    int i = Math.max(paramString.lastIndexOf('/'), 0);
    return paramString.substring(i + 1).replace(";", "");
  }
  
  public static boolean isConstant(AbstractInsnNode paramAbstractInsnNode) {
    if (paramAbstractInsnNode == null)
      return false; 
    return Ints.contains(CONSTANTS_ALL, paramAbstractInsnNode.getOpcode());
  }
  
  public static Object getConstant(AbstractInsnNode paramAbstractInsnNode) {
    if (paramAbstractInsnNode == null)
      return null; 
    if (paramAbstractInsnNode instanceof LdcInsnNode)
      return ((LdcInsnNode)paramAbstractInsnNode).cst; 
    if (paramAbstractInsnNode instanceof IntInsnNode) {
      int j = ((IntInsnNode)paramAbstractInsnNode).operand;
      if (paramAbstractInsnNode.getOpcode() == 16 || paramAbstractInsnNode.getOpcode() == 17)
        return Integer.valueOf(j); 
      throw new IllegalArgumentException("IntInsnNode with invalid opcode " + paramAbstractInsnNode.getOpcode() + " in getConstant");
    } 
    int i = Ints.indexOf(CONSTANTS_ALL, paramAbstractInsnNode.getOpcode());
    return (i < 0) ? null : CONSTANTS_VALUES[i];
  }
  
  public static Type getConstantType(AbstractInsnNode paramAbstractInsnNode) {
    if (paramAbstractInsnNode == null)
      return null; 
    if (paramAbstractInsnNode instanceof LdcInsnNode) {
      Object object = ((LdcInsnNode)paramAbstractInsnNode).cst;
      if (object instanceof Integer)
        return Type.getType("I"); 
      if (object instanceof Float)
        return Type.getType("F"); 
      if (object instanceof Long)
        return Type.getType("J"); 
      if (object instanceof Double)
        return Type.getType("D"); 
      if (object instanceof String)
        return Type.getType("Ljava/lang/String;"); 
      if (object instanceof Type)
        return Type.getType("Ljava/lang/Class;"); 
      throw new IllegalArgumentException("LdcInsnNode with invalid payload type " + object.getClass() + " in getConstant");
    } 
    int i = Ints.indexOf(CONSTANTS_ALL, paramAbstractInsnNode.getOpcode());
    return (i < 0) ? null : Type.getType(CONSTANTS_TYPES[i]);
  }
  
  public static boolean hasFlag(ClassNode paramClassNode, int paramInt) {
    return ((paramClassNode.access & paramInt) == paramInt);
  }
  
  public static boolean hasFlag(MethodNode paramMethodNode, int paramInt) {
    return ((paramMethodNode.access & paramInt) == paramInt);
  }
  
  public static boolean hasFlag(FieldNode paramFieldNode, int paramInt) {
    return ((paramFieldNode.access & paramInt) == paramInt);
  }
  
  public static boolean compareFlags(MethodNode paramMethodNode1, MethodNode paramMethodNode2, int paramInt) {
    return (hasFlag(paramMethodNode1, paramInt) == hasFlag(paramMethodNode2, paramInt));
  }
  
  public static boolean compareFlags(FieldNode paramFieldNode1, FieldNode paramFieldNode2, int paramInt) {
    return (hasFlag(paramFieldNode1, paramInt) == hasFlag(paramFieldNode2, paramInt));
  }
  
  public static Visibility getVisibility(MethodNode paramMethodNode) {
    return getVisibility(paramMethodNode.access & 0x7);
  }
  
  public static Visibility getVisibility(FieldNode paramFieldNode) {
    return getVisibility(paramFieldNode.access & 0x7);
  }
  
  private static Visibility getVisibility(int paramInt) {
    if ((paramInt & 0x4) != 0)
      return Visibility.PROTECTED; 
    if ((paramInt & 0x2) != 0)
      return Visibility.PRIVATE; 
    if ((paramInt & 0x1) != 0)
      return Visibility.PUBLIC; 
    return Visibility.PACKAGE;
  }
  
  public static void setVisibility(MethodNode paramMethodNode, Visibility paramVisibility) {
    paramMethodNode.access = setVisibility(paramMethodNode.access, paramVisibility.access);
  }
  
  public static void setVisibility(FieldNode paramFieldNode, Visibility paramVisibility) {
    paramFieldNode.access = setVisibility(paramFieldNode.access, paramVisibility.access);
  }
  
  public static void setVisibility(MethodNode paramMethodNode, int paramInt) {
    paramMethodNode.access = setVisibility(paramMethodNode.access, paramInt);
  }
  
  public static void setVisibility(FieldNode paramFieldNode, int paramInt) {
    paramFieldNode.access = setVisibility(paramFieldNode.access, paramInt);
  }
  
  private static int setVisibility(int paramInt1, int paramInt2) {
    return paramInt1 & 0xFFFFFFF8 | paramInt2 & 0x7;
  }
  
  public static int getMaxLineNumber(ClassNode paramClassNode, int paramInt1, int paramInt2) {
    int i = 0;
    for (MethodNode methodNode : paramClassNode.methods) {
      for (ListIterator<AbstractInsnNode> listIterator = methodNode.instructions.iterator(); listIterator.hasNext(); ) {
        AbstractInsnNode abstractInsnNode = listIterator.next();
        if (abstractInsnNode instanceof LineNumberNode)
          i = Math.max(i, ((LineNumberNode)abstractInsnNode).line); 
      } 
    } 
    return Math.max(paramInt1, i + paramInt2);
  }
  
  public static String getBoxingType(Type paramType) {
    return (paramType == null) ? null : BOXING_TYPES[paramType.getSort()];
  }
  
  public static String getUnboxingMethod(Type paramType) {
    return (paramType == null) ? null : UNBOXING_METHODS[paramType.getSort()];
  }
  
  public static void mergeAnnotations(ClassNode paramClassNode1, ClassNode paramClassNode2) {
    paramClassNode2.visibleAnnotations = mergeAnnotations(paramClassNode1.visibleAnnotations, paramClassNode2.visibleAnnotations, "class", paramClassNode1.name);
    paramClassNode2.invisibleAnnotations = mergeAnnotations(paramClassNode1.invisibleAnnotations, paramClassNode2.invisibleAnnotations, "class", paramClassNode1.name);
  }
  
  public static void mergeAnnotations(MethodNode paramMethodNode1, MethodNode paramMethodNode2) {
    paramMethodNode2.visibleAnnotations = mergeAnnotations(paramMethodNode1.visibleAnnotations, paramMethodNode2.visibleAnnotations, "method", paramMethodNode1.name);
    paramMethodNode2.invisibleAnnotations = mergeAnnotations(paramMethodNode1.invisibleAnnotations, paramMethodNode2.invisibleAnnotations, "method", paramMethodNode1.name);
  }
  
  public static void mergeAnnotations(FieldNode paramFieldNode1, FieldNode paramFieldNode2) {
    paramFieldNode2.visibleAnnotations = mergeAnnotations(paramFieldNode1.visibleAnnotations, paramFieldNode2.visibleAnnotations, "field", paramFieldNode1.name);
    paramFieldNode2.invisibleAnnotations = mergeAnnotations(paramFieldNode1.invisibleAnnotations, paramFieldNode2.invisibleAnnotations, "field", paramFieldNode1.name);
  }
  
  private static List<AnnotationNode> mergeAnnotations(List<AnnotationNode> paramList1, List<AnnotationNode> paramList2, String paramString1, String paramString2) {
    try {
      if (paramList1 == null)
        return paramList2; 
      if (paramList2 == null)
        paramList2 = new ArrayList<AnnotationNode>(); 
      for (AnnotationNode annotationNode : paramList1) {
        if (!isMergeableAnnotation(annotationNode))
          continue; 
        for (Iterator<AnnotationNode> iterator = paramList2.iterator(); iterator.hasNext();) {
          if (((AnnotationNode)iterator.next()).desc.equals(annotationNode.desc)) {
            iterator.remove();
            break;
          } 
        } 
        paramList2.add(annotationNode);
      } 
    } catch (Exception exception) {
      logger.warn("Exception encountered whilst merging annotations for {} {}", new Object[] { paramString1, paramString2 });
    } 
    return paramList2;
  }
  
  private static boolean isMergeableAnnotation(AnnotationNode paramAnnotationNode) {
    if (paramAnnotationNode.desc.startsWith("L" + Constants.MIXIN_PACKAGE_REF))
      return mergeableAnnotationPattern.matcher(paramAnnotationNode.desc).matches(); 
    return true;
  }
  
  private static Pattern getMergeableAnnotationPattern() {
    StringBuilder stringBuilder = new StringBuilder("^L(");
    for (byte b = 0; b < MERGEABLE_MIXIN_ANNOTATIONS.length; b++) {
      if (b > 0)
        stringBuilder.append('|'); 
      stringBuilder.append(MERGEABLE_MIXIN_ANNOTATIONS[b].getName().replace('.', '/'));
    } 
    return Pattern.compile(stringBuilder.append(");$").toString());
  }
  
  public static void compareBridgeMethods(MethodNode paramMethodNode1, MethodNode paramMethodNode2) {
    ListIterator<AbstractInsnNode> listIterator1 = paramMethodNode1.instructions.iterator();
    ListIterator<AbstractInsnNode> listIterator2 = paramMethodNode2.instructions.iterator();
    byte b = 0;
    for (; listIterator1.hasNext() && listIterator2.hasNext(); b++) {
      AbstractInsnNode abstractInsnNode1 = listIterator1.next();
      AbstractInsnNode abstractInsnNode2 = listIterator2.next();
      if (!(abstractInsnNode1 instanceof LabelNode))
        if (abstractInsnNode1 instanceof MethodInsnNode) {
          MethodInsnNode methodInsnNode1 = (MethodInsnNode)abstractInsnNode1;
          MethodInsnNode methodInsnNode2 = (MethodInsnNode)abstractInsnNode2;
          if (!methodInsnNode1.name.equals(methodInsnNode2.name))
            throw new SyntheticBridgeException(SyntheticBridgeException.Problem.BAD_INVOKE_NAME, paramMethodNode1.name, paramMethodNode1.desc, b, abstractInsnNode1, abstractInsnNode2); 
          if (!methodInsnNode1.desc.equals(methodInsnNode2.desc))
            throw new SyntheticBridgeException(SyntheticBridgeException.Problem.BAD_INVOKE_DESC, paramMethodNode1.name, paramMethodNode1.desc, b, abstractInsnNode1, abstractInsnNode2); 
        } else {
          if (abstractInsnNode1.getOpcode() != abstractInsnNode2.getOpcode())
            throw new SyntheticBridgeException(SyntheticBridgeException.Problem.BAD_INSN, paramMethodNode1.name, paramMethodNode1.desc, b, abstractInsnNode1, abstractInsnNode2); 
          if (abstractInsnNode1 instanceof VarInsnNode) {
            VarInsnNode varInsnNode1 = (VarInsnNode)abstractInsnNode1;
            VarInsnNode varInsnNode2 = (VarInsnNode)abstractInsnNode2;
            if (varInsnNode1.var != varInsnNode2.var)
              throw new SyntheticBridgeException(SyntheticBridgeException.Problem.BAD_LOAD, paramMethodNode1.name, paramMethodNode1.desc, b, abstractInsnNode1, abstractInsnNode2); 
          } else if (abstractInsnNode1 instanceof TypeInsnNode) {
            TypeInsnNode typeInsnNode1 = (TypeInsnNode)abstractInsnNode1;
            TypeInsnNode typeInsnNode2 = (TypeInsnNode)abstractInsnNode2;
            if (typeInsnNode1.getOpcode() == 192 && !typeInsnNode1.desc.equals(typeInsnNode2.desc))
              throw new SyntheticBridgeException(SyntheticBridgeException.Problem.BAD_CAST, paramMethodNode1.name, paramMethodNode1.desc, b, abstractInsnNode1, abstractInsnNode2); 
          } 
        }  
    } 
    if (listIterator1.hasNext() || listIterator2.hasNext())
      throw new SyntheticBridgeException(SyntheticBridgeException.Problem.BAD_LENGTH, paramMethodNode1.name, paramMethodNode1.desc, b, null, null); 
  }
}
