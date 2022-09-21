package org.spongepowered.asm.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import org.spongepowered.asm.lib.Opcodes;
import org.spongepowered.asm.lib.Type;
import org.spongepowered.asm.lib.tree.AbstractInsnNode;
import org.spongepowered.asm.lib.tree.ClassNode;
import org.spongepowered.asm.lib.tree.FrameNode;
import org.spongepowered.asm.lib.tree.InsnList;
import org.spongepowered.asm.lib.tree.LabelNode;
import org.spongepowered.asm.lib.tree.LocalVariableNode;
import org.spongepowered.asm.lib.tree.MethodNode;
import org.spongepowered.asm.lib.tree.VarInsnNode;
import org.spongepowered.asm.lib.tree.analysis.Analyzer;
import org.spongepowered.asm.lib.tree.analysis.AnalyzerException;
import org.spongepowered.asm.lib.tree.analysis.BasicValue;
import org.spongepowered.asm.lib.tree.analysis.Frame;
import org.spongepowered.asm.lib.tree.analysis.Interpreter;
import org.spongepowered.asm.mixin.transformer.ClassInfo;
import org.spongepowered.asm.util.asm.MixinVerifier;
import org.spongepowered.asm.util.throwables.LVTGeneratorException;

public final class Locals {
  private static final Map<String, List<LocalVariableNode>> calculatedLocalVariables = new HashMap<String, List<LocalVariableNode>>();
  
  public static void loadLocals(Type[] paramArrayOfType, InsnList paramInsnList, int paramInt1, int paramInt2) {
    for (; paramInt1 < paramArrayOfType.length && paramInt2 > 0; paramInt1++) {
      if (paramArrayOfType[paramInt1] != null) {
        paramInsnList.add((AbstractInsnNode)new VarInsnNode(paramArrayOfType[paramInt1].getOpcode(21), paramInt1));
        paramInt2--;
      } 
    } 
  }
  
  public static LocalVariableNode[] getLocalsAt(ClassNode paramClassNode, MethodNode paramMethodNode, AbstractInsnNode paramAbstractInsnNode) {
    for (byte b1 = 0; b1 < 3 && (paramAbstractInsnNode instanceof LabelNode || paramAbstractInsnNode instanceof org.spongepowered.asm.lib.tree.LineNumberNode); b1++)
      paramAbstractInsnNode = nextNode(paramMethodNode.instructions, paramAbstractInsnNode); 
    ClassInfo classInfo = ClassInfo.forName(paramClassNode.name);
    if (classInfo == null)
      throw new LVTGeneratorException("Could not load class metadata for " + paramClassNode.name + " generating LVT for " + paramMethodNode.name); 
    ClassInfo.Method method = classInfo.findMethod(paramMethodNode);
    if (method == null)
      throw new LVTGeneratorException("Could not locate method metadata for " + paramMethodNode.name + " generating LVT in " + paramClassNode.name); 
    List<ClassInfo.FrameData> list = method.getFrames();
    LocalVariableNode[] arrayOfLocalVariableNode = new LocalVariableNode[paramMethodNode.maxLocals];
    int i = 0;
    byte b2 = 0;
    if ((paramMethodNode.access & 0x8) == 0)
      arrayOfLocalVariableNode[i++] = new LocalVariableNode("this", paramClassNode.name, null, null, null, 0); 
    for (Type type : Type.getArgumentTypes(paramMethodNode.desc)) {
      arrayOfLocalVariableNode[i] = new LocalVariableNode("arg" + b2++, type.toString(), null, null, null, i);
      i += type.getSize();
    } 
    int j = i;
    byte b = -1;
    int k = 0;
    for (ListIterator<AbstractInsnNode> listIterator = paramMethodNode.instructions.iterator(); listIterator.hasNext(); ) {
      AbstractInsnNode abstractInsnNode = listIterator.next();
      if (abstractInsnNode instanceof FrameNode) {
        b++;
        FrameNode frameNode = (FrameNode)abstractInsnNode;
        ClassInfo.FrameData frameData = (b < list.size()) ? list.get(b) : null;
        k = (frameData != null && frameData.type == 0) ? Math.min(k, frameData.locals) : frameNode.local.size();
        for (byte b4 = 0, b5 = 0; b5 < arrayOfLocalVariableNode.length; b5++, b4++) {
          E e = (b4 < frameNode.local.size()) ? frameNode.local.get(b4) : null;
          if (e instanceof String) {
            arrayOfLocalVariableNode[b5] = getLocalVariableAt(paramClassNode, paramMethodNode, paramAbstractInsnNode, b5);
          } else if (e instanceof Integer) {
            boolean bool1 = (e == Opcodes.UNINITIALIZED_THIS || e == Opcodes.NULL) ? true : false;
            boolean bool2 = (e == Opcodes.INTEGER || e == Opcodes.FLOAT) ? true : false;
            boolean bool3 = (e == Opcodes.DOUBLE || e == Opcodes.LONG) ? true : false;
            if (e != Opcodes.TOP)
              if (bool1) {
                arrayOfLocalVariableNode[b5] = null;
              } else if (bool2 || bool3) {
                arrayOfLocalVariableNode[b5] = getLocalVariableAt(paramClassNode, paramMethodNode, paramAbstractInsnNode, b5);
                if (bool3) {
                  b5++;
                  arrayOfLocalVariableNode[b5] = null;
                } 
              } else {
                throw new LVTGeneratorException("Unrecognised locals opcode " + e + " in locals array at position " + b4 + " in " + paramClassNode.name + "." + paramMethodNode.name + paramMethodNode.desc);
              }  
          } else if (e == null) {
            if (b5 >= j && b5 >= k && k > 0)
              arrayOfLocalVariableNode[b5] = null; 
          } else {
            throw new LVTGeneratorException("Invalid value " + e + " in locals array at position " + b4 + " in " + paramClassNode.name + "." + paramMethodNode.name + paramMethodNode.desc);
          } 
        } 
      } else if (abstractInsnNode instanceof VarInsnNode) {
        VarInsnNode varInsnNode = (VarInsnNode)abstractInsnNode;
        arrayOfLocalVariableNode[varInsnNode.var] = getLocalVariableAt(paramClassNode, paramMethodNode, paramAbstractInsnNode, varInsnNode.var);
      } 
      if (abstractInsnNode == paramAbstractInsnNode)
        break; 
    } 
    for (byte b3 = 0; b3 < arrayOfLocalVariableNode.length; b3++) {
      if (arrayOfLocalVariableNode[b3] != null && (arrayOfLocalVariableNode[b3]).desc == null)
        arrayOfLocalVariableNode[b3] = null; 
    } 
    return arrayOfLocalVariableNode;
  }
  
  public static LocalVariableNode getLocalVariableAt(ClassNode paramClassNode, MethodNode paramMethodNode, AbstractInsnNode paramAbstractInsnNode, int paramInt) {
    return getLocalVariableAt(paramClassNode, paramMethodNode, paramMethodNode.instructions.indexOf(paramAbstractInsnNode), paramInt);
  }
  
  private static LocalVariableNode getLocalVariableAt(ClassNode paramClassNode, MethodNode paramMethodNode, int paramInt1, int paramInt2) {
    LocalVariableNode localVariableNode1 = null;
    LocalVariableNode localVariableNode2 = null;
    for (LocalVariableNode localVariableNode : getLocalVariableTable(paramClassNode, paramMethodNode)) {
      if (localVariableNode.index != paramInt2)
        continue; 
      if (isOpcodeInRange(paramMethodNode.instructions, localVariableNode, paramInt1)) {
        localVariableNode1 = localVariableNode;
        continue;
      } 
      if (localVariableNode1 == null)
        localVariableNode2 = localVariableNode; 
    } 
    if (localVariableNode1 == null && !paramMethodNode.localVariables.isEmpty())
      for (LocalVariableNode localVariableNode : getGeneratedLocalVariableTable(paramClassNode, paramMethodNode)) {
        if (localVariableNode.index == paramInt2 && isOpcodeInRange(paramMethodNode.instructions, localVariableNode, paramInt1))
          localVariableNode1 = localVariableNode; 
      }  
    return (localVariableNode1 != null) ? localVariableNode1 : localVariableNode2;
  }
  
  private static boolean isOpcodeInRange(InsnList paramInsnList, LocalVariableNode paramLocalVariableNode, int paramInt) {
    return (paramInsnList.indexOf((AbstractInsnNode)paramLocalVariableNode.start) < paramInt && paramInsnList.indexOf((AbstractInsnNode)paramLocalVariableNode.end) > paramInt);
  }
  
  public static List<LocalVariableNode> getLocalVariableTable(ClassNode paramClassNode, MethodNode paramMethodNode) {
    if (paramMethodNode.localVariables.isEmpty())
      return getGeneratedLocalVariableTable(paramClassNode, paramMethodNode); 
    return paramMethodNode.localVariables;
  }
  
  public static List<LocalVariableNode> getGeneratedLocalVariableTable(ClassNode paramClassNode, MethodNode paramMethodNode) {
    String str = String.format("%s.%s%s", new Object[] { paramClassNode.name, paramMethodNode.name, paramMethodNode.desc });
    List<LocalVariableNode> list = calculatedLocalVariables.get(str);
    if (list != null)
      return list; 
    list = generateLocalVariableTable(paramClassNode, paramMethodNode);
    calculatedLocalVariables.put(str, list);
    return list;
  }
  
  public static List<LocalVariableNode> generateLocalVariableTable(ClassNode paramClassNode, MethodNode paramMethodNode) {
    ArrayList<Type> arrayList = null;
    if (paramClassNode.interfaces != null) {
      arrayList = new ArrayList();
      for (String str : paramClassNode.interfaces)
        arrayList.add(Type.getObjectType(str)); 
    } 
    Type type = null;
    if (paramClassNode.superName != null)
      type = Type.getObjectType(paramClassNode.superName); 
    Analyzer analyzer = new Analyzer((Interpreter)new MixinVerifier(Type.getObjectType(paramClassNode.name), type, arrayList, false));
    try {
      analyzer.analyze(paramClassNode.name, paramMethodNode);
    } catch (AnalyzerException analyzerException) {
      analyzerException.printStackTrace();
    } 
    Frame[] arrayOfFrame = analyzer.getFrames();
    int i = paramMethodNode.instructions.size();
    ArrayList<LocalVariableNode> arrayList1 = new ArrayList();
    LocalVariableNode[] arrayOfLocalVariableNode = new LocalVariableNode[paramMethodNode.maxLocals];
    BasicValue[] arrayOfBasicValue = new BasicValue[paramMethodNode.maxLocals];
    LabelNode[] arrayOfLabelNode = new LabelNode[i];
    String[] arrayOfString = new String[paramMethodNode.maxLocals];
    for (byte b = 0; b < i; b++) {
      Frame frame = arrayOfFrame[b];
      if (frame != null) {
        LabelNode labelNode1 = null;
        for (byte b1 = 0; b1 < frame.getLocals(); b1++) {
          BasicValue basicValue = (BasicValue)frame.getLocal(b1);
          if (basicValue != null || arrayOfBasicValue[b1] != null)
            if (basicValue == null || !basicValue.equals(arrayOfBasicValue[b1])) {
              if (labelNode1 == null) {
                AbstractInsnNode abstractInsnNode = paramMethodNode.instructions.get(b);
                if (abstractInsnNode instanceof LabelNode) {
                  labelNode1 = (LabelNode)abstractInsnNode;
                } else {
                  arrayOfLabelNode[b] = labelNode1 = new LabelNode();
                } 
              } 
              if (basicValue == null && arrayOfBasicValue[b1] != null) {
                arrayList1.add(arrayOfLocalVariableNode[b1]);
                (arrayOfLocalVariableNode[b1]).end = labelNode1;
                arrayOfLocalVariableNode[b1] = null;
              } else if (basicValue != null) {
                if (arrayOfBasicValue[b1] != null) {
                  arrayList1.add(arrayOfLocalVariableNode[b1]);
                  (arrayOfLocalVariableNode[b1]).end = labelNode1;
                  arrayOfLocalVariableNode[b1] = null;
                } 
                String str = (basicValue.getType() != null) ? basicValue.getType().getDescriptor() : arrayOfString[b1];
                arrayOfLocalVariableNode[b1] = new LocalVariableNode("var" + b1, str, null, labelNode1, null, b1);
                if (str != null)
                  arrayOfString[b1] = str; 
              } 
              arrayOfBasicValue[b1] = basicValue;
            }  
        } 
      } 
    } 
    LabelNode labelNode = null;
    int j;
    for (j = 0; j < arrayOfLocalVariableNode.length; j++) {
      if (arrayOfLocalVariableNode[j] != null) {
        if (labelNode == null) {
          labelNode = new LabelNode();
          paramMethodNode.instructions.add((AbstractInsnNode)labelNode);
        } 
        (arrayOfLocalVariableNode[j]).end = labelNode;
        arrayList1.add(arrayOfLocalVariableNode[j]);
      } 
    } 
    for (j = i - 1; j >= 0; j--) {
      if (arrayOfLabelNode[j] != null)
        paramMethodNode.instructions.insert(paramMethodNode.instructions.get(j), (AbstractInsnNode)arrayOfLabelNode[j]); 
    } 
    return arrayList1;
  }
  
  private static AbstractInsnNode nextNode(InsnList paramInsnList, AbstractInsnNode paramAbstractInsnNode) {
    int i = paramInsnList.indexOf(paramAbstractInsnNode) + 1;
    if (i > 0 && i < paramInsnList.size())
      return paramInsnList.get(i); 
    return paramAbstractInsnNode;
  }
}
