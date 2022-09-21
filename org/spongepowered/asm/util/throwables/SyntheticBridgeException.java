package org.spongepowered.asm.util.throwables;

import java.util.ListIterator;
import org.spongepowered.asm.lib.Type;
import org.spongepowered.asm.lib.tree.AbstractInsnNode;
import org.spongepowered.asm.lib.tree.MethodInsnNode;
import org.spongepowered.asm.lib.tree.MethodNode;
import org.spongepowered.asm.lib.tree.TypeInsnNode;
import org.spongepowered.asm.lib.tree.VarInsnNode;
import org.spongepowered.asm.mixin.refmap.IMixinContext;
import org.spongepowered.asm.mixin.throwables.MixinException;
import org.spongepowered.asm.mixin.transformer.ClassInfo;
import org.spongepowered.asm.mixin.transformer.meta.MixinMerged;
import org.spongepowered.asm.util.Annotations;
import org.spongepowered.asm.util.Bytecode;
import org.spongepowered.asm.util.PrettyPrinter;

public class SyntheticBridgeException extends MixinException {
  private final String desc;
  
  private final AbstractInsnNode a;
  
  private final int index;
  
  private static final long serialVersionUID = 1L;
  
  private final Problem problem;
  
  private final String name;
  
  private final AbstractInsnNode b;
  
  public enum Problem {
    BAD_INVOKE_DESC,
    BAD_LENGTH,
    BAD_INSN("Conflicting opcodes %4$s and %5$s at offset %3$d in synthetic bridge method %1$s%2$s"),
    BAD_INVOKE_NAME("Conflicting opcodes %4$s and %5$s at offset %3$d in synthetic bridge method %1$s%2$s"),
    BAD_LOAD("Conflicting variable access at offset %3$d in synthetic bridge method %1$s%2$s"),
    BAD_CAST("Conflicting type cast at offset %3$d in synthetic bridge method %1$s%2$s");
    
    private final String message;
    
    static {
      BAD_INVOKE_DESC = new Problem("BAD_INVOKE_DESC", 4, "Conflicting synthetic bridge target method descriptor in synthetic bridge method %1$s%2$s Existing:%8$s Incoming:%9$s");
      BAD_LENGTH = new Problem("BAD_LENGTH", 5, "Mismatched bridge method length for synthetic bridge method %1$s%2$s unexpected extra opcode at offset %3$d");
      $VALUES = new Problem[] { BAD_INSN, BAD_LOAD, BAD_CAST, BAD_INVOKE_NAME, BAD_INVOKE_DESC, BAD_LENGTH };
    }
    
    Problem(String param1String1) {
      this.message = param1String1;
    }
    
    String getMessage(String param1String1, String param1String2, int param1Int, AbstractInsnNode param1AbstractInsnNode1, AbstractInsnNode param1AbstractInsnNode2) {
      return String.format(this.message, new Object[] { param1String1, param1String2, Integer.valueOf(param1Int), Bytecode.getOpcodeName(param1AbstractInsnNode1), Bytecode.getOpcodeName(param1AbstractInsnNode1), 
            getInsnName(param1AbstractInsnNode1), getInsnName(param1AbstractInsnNode2), getInsnDesc(param1AbstractInsnNode1), getInsnDesc(param1AbstractInsnNode2) });
    }
    
    private static String getInsnName(AbstractInsnNode param1AbstractInsnNode) {
      return (param1AbstractInsnNode instanceof MethodInsnNode) ? ((MethodInsnNode)param1AbstractInsnNode).name : "";
    }
    
    private static String getInsnDesc(AbstractInsnNode param1AbstractInsnNode) {
      return (param1AbstractInsnNode instanceof MethodInsnNode) ? ((MethodInsnNode)param1AbstractInsnNode).desc : "";
    }
  }
  
  public SyntheticBridgeException(Problem paramProblem, String paramString1, String paramString2, int paramInt, AbstractInsnNode paramAbstractInsnNode1, AbstractInsnNode paramAbstractInsnNode2) {
    super(paramProblem.getMessage(paramString1, paramString2, paramInt, paramAbstractInsnNode1, paramAbstractInsnNode2));
    this.problem = paramProblem;
    this.name = paramString1;
    this.desc = paramString2;
    this.index = paramInt;
    this.a = paramAbstractInsnNode1;
    this.b = paramAbstractInsnNode2;
  }
  
  public void printAnalysis(IMixinContext paramIMixinContext, MethodNode paramMethodNode1, MethodNode paramMethodNode2) {
    PrettyPrinter prettyPrinter = new PrettyPrinter();
    prettyPrinter.addWrapped(100, getMessage(), new Object[0]).hr();
    prettyPrinter.add().kv("Method", this.name + this.desc).kv("Problem Type", this.problem).add().hr();
    String str1 = (String)Annotations.getValue(Annotations.getVisible(paramMethodNode1, MixinMerged.class), "mixin");
    String str2 = (str1 != null) ? str1 : paramIMixinContext.getTargetClassRef().replace('/', '.');
    printMethod(prettyPrinter.add("Existing method").add().kv("Owner", str2).add(), paramMethodNode1).hr();
    printMethod(prettyPrinter.add("Incoming method").add().kv("Owner", paramIMixinContext.getClassRef().replace('/', '.')).add(), paramMethodNode2).hr();
    printProblem(prettyPrinter, paramIMixinContext, paramMethodNode1, paramMethodNode2).print(System.err);
  }
  
  private PrettyPrinter printMethod(PrettyPrinter paramPrettyPrinter, MethodNode paramMethodNode) {
    byte b = 0;
    for (ListIterator<AbstractInsnNode> listIterator = paramMethodNode.instructions.iterator(); listIterator.hasNext(); b++)
      paramPrettyPrinter.kv((b == this.index) ? ">>>>" : "", Bytecode.describeNode(listIterator.next())); 
    return paramPrettyPrinter.add();
  }
  
  private PrettyPrinter printProblem(PrettyPrinter paramPrettyPrinter, IMixinContext paramIMixinContext, MethodNode paramMethodNode1, MethodNode paramMethodNode2) {
    ListIterator<AbstractInsnNode> listIterator1, listIterator2;
    Type[] arrayOfType1, arrayOfType2;
    byte b1;
    Type type2, type3;
    MethodInsnNode methodInsnNode1, methodInsnNode2;
    Type arrayOfType3[], arrayOfType4[], type4, type5;
    byte b2;
    Type type1 = Type.getObjectType(paramIMixinContext.getTargetClassRef());
    paramPrettyPrinter.add("Analysis").add();
    switch (this.problem) {
      case BAD_INSN:
        paramPrettyPrinter.add("The bridge methods are not compatible because they contain incompatible opcodes");
        paramPrettyPrinter.add("at index " + this.index + ":").add();
        paramPrettyPrinter.kv("Existing opcode: %s", Bytecode.getOpcodeName(this.a));
        paramPrettyPrinter.kv("Incoming opcode: %s", Bytecode.getOpcodeName(this.b)).add();
        paramPrettyPrinter.add("This implies that the bridge methods are from different interfaces. This problem");
        paramPrettyPrinter.add("may not be resolvable without changing the base interfaces.").add();
        break;
      case BAD_LOAD:
        paramPrettyPrinter.add("The bridge methods are not compatible because they contain different variables at");
        paramPrettyPrinter.add("opcode index " + this.index + ".").add();
        listIterator1 = paramMethodNode1.instructions.iterator();
        listIterator2 = paramMethodNode2.instructions.iterator();
        arrayOfType1 = Type.getArgumentTypes(paramMethodNode1.desc);
        arrayOfType2 = Type.getArgumentTypes(paramMethodNode2.desc);
        for (b1 = 0; listIterator1.hasNext() && listIterator2.hasNext(); b1++) {
          AbstractInsnNode abstractInsnNode1 = listIterator1.next();
          AbstractInsnNode abstractInsnNode2 = listIterator2.next();
          if (abstractInsnNode1 instanceof VarInsnNode && abstractInsnNode2 instanceof VarInsnNode) {
            VarInsnNode varInsnNode1 = (VarInsnNode)abstractInsnNode1;
            VarInsnNode varInsnNode2 = (VarInsnNode)abstractInsnNode2;
            Type type6 = (varInsnNode1.var > 0) ? arrayOfType1[varInsnNode1.var - 1] : type1;
            Type type7 = (varInsnNode2.var > 0) ? arrayOfType2[varInsnNode2.var - 1] : type1;
            paramPrettyPrinter.kv("Target " + b1, "%8s %-2d %s", new Object[] { Bytecode.getOpcodeName((AbstractInsnNode)varInsnNode1), Integer.valueOf(varInsnNode1.var), type6 });
            paramPrettyPrinter.kv("Incoming " + b1, "%8s %-2d %s", new Object[] { Bytecode.getOpcodeName((AbstractInsnNode)varInsnNode2), Integer.valueOf(varInsnNode2.var), type7 });
            if (type6.equals(type7)) {
              paramPrettyPrinter.kv("", "Types match: %s", new Object[] { type6 });
            } else if (type6.getSort() != type7.getSort()) {
              paramPrettyPrinter.kv("", "Types are incompatible");
            } else if (type6.getSort() == 10) {
              ClassInfo classInfo = ClassInfo.getCommonSuperClassOrInterface(type6, type7);
              paramPrettyPrinter.kv("", "Common supertype: %s", new Object[] { classInfo });
            } 
            paramPrettyPrinter.add();
          } 
        } 
        paramPrettyPrinter.add("Since this probably means that the methods come from different interfaces, you");
        paramPrettyPrinter.add("may have a \"multiple inheritance\" problem, it may not be possible to implement");
        paramPrettyPrinter.add("both root interfaces");
        break;
      case BAD_CAST:
        paramPrettyPrinter.add("Incompatible CHECKCAST encountered at opcode " + this.index + ", this could indicate that the bridge");
        paramPrettyPrinter.add("is casting down for contravariant generic types. It may be possible to coalesce the");
        paramPrettyPrinter.add("bridges by adjusting the types in the target method.").add();
        type2 = Type.getObjectType(((TypeInsnNode)this.a).desc);
        type3 = Type.getObjectType(((TypeInsnNode)this.b).desc);
        paramPrettyPrinter.kv("Target type", type2);
        paramPrettyPrinter.kv("Incoming type", type3);
        paramPrettyPrinter.kv("Common supertype", ClassInfo.getCommonSuperClassOrInterface(type2, type3)).add();
        break;
      case BAD_INVOKE_NAME:
        paramPrettyPrinter.add("Incompatible invocation targets in synthetic bridge. This is extremely unusual");
        paramPrettyPrinter.add("and implies that a remapping transformer has incorrectly remapped a method. This");
        paramPrettyPrinter.add("is an unrecoverable error.");
        break;
      case BAD_INVOKE_DESC:
        methodInsnNode1 = (MethodInsnNode)this.a;
        methodInsnNode2 = (MethodInsnNode)this.b;
        arrayOfType3 = Type.getArgumentTypes(methodInsnNode1.desc);
        arrayOfType4 = Type.getArgumentTypes(methodInsnNode2.desc);
        if (arrayOfType3.length != arrayOfType4.length) {
          int i = (Type.getArgumentTypes(paramMethodNode1.desc)).length;
          String str = (arrayOfType3.length == i) ? "The TARGET" : ((arrayOfType4.length == i) ? " The INCOMING" : "NEITHER");
          paramPrettyPrinter.add("Mismatched invocation descriptors in synthetic bridge implies that a remapping");
          paramPrettyPrinter.add("transformer has incorrectly coalesced a bridge method with a conflicting name.");
          paramPrettyPrinter.add("Overlapping bridge methods should always have the same number of arguments, yet");
          paramPrettyPrinter.add("the target method has %d arguments, the incoming method has %d. This is an", new Object[] { Integer.valueOf(arrayOfType3.length), Integer.valueOf(arrayOfType4.length) });
          paramPrettyPrinter.add("unrecoverable error. %s method has the expected arg count of %d", new Object[] { str, Integer.valueOf(i) });
          break;
        } 
        type4 = Type.getReturnType(methodInsnNode1.desc);
        type5 = Type.getReturnType(methodInsnNode2.desc);
        paramPrettyPrinter.add("Incompatible invocation descriptors in synthetic bridge implies that generified");
        paramPrettyPrinter.add("types are incompatible over one or more generic superclasses or interfaces. It may");
        paramPrettyPrinter.add("be possible to adjust the generic types on implemented members to rectify this");
        paramPrettyPrinter.add("problem by coalescing the appropriate generic types.").add();
        printTypeComparison(paramPrettyPrinter, "return type", type4, type5);
        for (b2 = 0; b2 < arrayOfType3.length; b2++)
          printTypeComparison(paramPrettyPrinter, "arg " + b2, arrayOfType3[b2], arrayOfType4[b2]); 
        break;
      case BAD_LENGTH:
        paramPrettyPrinter.add("Mismatched bridge method length implies the bridge methods are incompatible");
        paramPrettyPrinter.add("and may originate from different superinterfaces. This is an unrecoverable");
        paramPrettyPrinter.add("error.").add();
        break;
    } 
    return paramPrettyPrinter;
  }
  
  private PrettyPrinter printTypeComparison(PrettyPrinter paramPrettyPrinter, String paramString, Type paramType1, Type paramType2) {
    paramPrettyPrinter.kv("Target " + paramString, "%s", new Object[] { paramType1 });
    paramPrettyPrinter.kv("Incoming " + paramString, "%s", new Object[] { paramType2 });
    if (paramType1.equals(paramType2)) {
      paramPrettyPrinter.kv("Analysis", "Types match: %s", new Object[] { paramType1 });
    } else if (paramType1.getSort() != paramType2.getSort()) {
      paramPrettyPrinter.kv("Analysis", "Types are incompatible");
    } else if (paramType1.getSort() == 10) {
      ClassInfo classInfo = ClassInfo.getCommonSuperClassOrInterface(paramType1, paramType2);
      paramPrettyPrinter.kv("Analysis", "Common supertype: L%s;", new Object[] { classInfo });
    } 
    return paramPrettyPrinter.add();
  }
}
