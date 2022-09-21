package org.spongepowered.asm.mixin.injection.callback;

import com.google.common.base.Strings;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.spongepowered.asm.lib.Type;
import org.spongepowered.asm.lib.tree.AbstractInsnNode;
import org.spongepowered.asm.lib.tree.InsnList;
import org.spongepowered.asm.lib.tree.InsnNode;
import org.spongepowered.asm.lib.tree.JumpInsnNode;
import org.spongepowered.asm.lib.tree.LabelNode;
import org.spongepowered.asm.lib.tree.LdcInsnNode;
import org.spongepowered.asm.lib.tree.LocalVariableNode;
import org.spongepowered.asm.lib.tree.MethodInsnNode;
import org.spongepowered.asm.lib.tree.MethodNode;
import org.spongepowered.asm.lib.tree.TypeInsnNode;
import org.spongepowered.asm.lib.tree.VarInsnNode;
import org.spongepowered.asm.mixin.injection.Coerce;
import org.spongepowered.asm.mixin.injection.InjectionPoint;
import org.spongepowered.asm.mixin.injection.Surrogate;
import org.spongepowered.asm.mixin.injection.code.Injector;
import org.spongepowered.asm.mixin.injection.points.BeforeReturn;
import org.spongepowered.asm.mixin.injection.struct.InjectionInfo;
import org.spongepowered.asm.mixin.injection.struct.InjectionNodes;
import org.spongepowered.asm.mixin.injection.struct.Target;
import org.spongepowered.asm.mixin.injection.throwables.InjectionError;
import org.spongepowered.asm.mixin.injection.throwables.InvalidInjectionException;
import org.spongepowered.asm.util.Annotations;
import org.spongepowered.asm.util.Bytecode;
import org.spongepowered.asm.util.Locals;
import org.spongepowered.asm.util.PrettyPrinter;
import org.spongepowered.asm.util.SignaturePrinter;

public class CallbackInjector extends Injector {
  private class Callback extends InsnList {
    final String[] argNames;
    
    int invoke;
    
    final boolean isAtReturn;
    
    final String descl;
    
    private final AbstractInsnNode head;
    
    final InjectionNodes.InjectionNode node;
    
    private final MethodNode handler;
    
    int ctor;
    
    final LocalVariableNode[] locals;
    
    final String desc;
    
    private int marshalVar = -1;
    
    final boolean canCaptureLocals;
    
    final Target target;
    
    final int extraArgs;
    
    final int frameSize;
    
    final Type[] localTypes;
    
    private boolean captureArgs = true;
    
    Callback(MethodNode param1MethodNode, Target param1Target, InjectionNodes.InjectionNode param1InjectionNode, LocalVariableNode[] param1ArrayOfLocalVariableNode, boolean param1Boolean) {
      this.handler = param1MethodNode;
      this.target = param1Target;
      this.head = param1Target.insns.getFirst();
      this.node = param1InjectionNode;
      this.locals = param1ArrayOfLocalVariableNode;
      this.localTypes = (param1ArrayOfLocalVariableNode != null) ? new Type[param1ArrayOfLocalVariableNode.length] : null;
      this.frameSize = Bytecode.getFirstNonArgLocalIndex(param1Target.arguments, !CallbackInjector.this.isStatic());
      ArrayList<String> arrayList = null;
      if (param1ArrayOfLocalVariableNode != null) {
        byte b1 = CallbackInjector.this.isStatic() ? 0 : 1;
        arrayList = new ArrayList();
        for (byte b2 = 0; b2 <= param1ArrayOfLocalVariableNode.length; b2++) {
          if (b2 == this.frameSize)
            arrayList.add((param1Target.returnType == Type.VOID_TYPE) ? "ci" : "cir"); 
          if (b2 < param1ArrayOfLocalVariableNode.length && param1ArrayOfLocalVariableNode[b2] != null) {
            this.localTypes[b2] = Type.getType((param1ArrayOfLocalVariableNode[b2]).desc);
            if (b2 >= b1)
              arrayList.add(CallbackInjector.meltSnowman(b2, (param1ArrayOfLocalVariableNode[b2]).name)); 
          } 
        } 
      } 
      this.extraArgs = Math.max(0, Bytecode.getFirstNonArgLocalIndex(this.handler) - this.frameSize + 1);
      this.argNames = (arrayList != null) ? arrayList.<String>toArray(new String[arrayList.size()]) : null;
      this.canCaptureLocals = (param1Boolean && param1ArrayOfLocalVariableNode != null && param1ArrayOfLocalVariableNode.length > this.frameSize);
      this.isAtReturn = (this.node.getCurrentTarget() instanceof InsnNode && isValueReturnOpcode(this.node.getCurrentTarget().getOpcode()));
      this.desc = param1Target.getCallbackDescriptor(this.localTypes, param1Target.arguments);
      this.descl = param1Target.getCallbackDescriptor(true, this.localTypes, param1Target.arguments, this.frameSize, this.extraArgs);
      this.invoke = param1Target.arguments.length + (this.canCaptureLocals ? (this.localTypes.length - this.frameSize) : 0);
    }
    
    private boolean isValueReturnOpcode(int param1Int) {
      return (param1Int >= 172 && param1Int < 177);
    }
    
    String getDescriptor() {
      return this.canCaptureLocals ? this.descl : this.desc;
    }
    
    String getDescriptorWithAllLocals() {
      return this.target.getCallbackDescriptor(true, this.localTypes, this.target.arguments, this.frameSize, 32767);
    }
    
    String getCallbackInfoConstructorDescriptor() {
      return this.isAtReturn ? CallbackInfo.getConstructorDescriptor(this.target.returnType) : CallbackInfo.getConstructorDescriptor();
    }
    
    void add(AbstractInsnNode param1AbstractInsnNode, boolean param1Boolean1, boolean param1Boolean2) {
      add(param1AbstractInsnNode, param1Boolean1, param1Boolean2, false);
    }
    
    void add(AbstractInsnNode param1AbstractInsnNode, boolean param1Boolean1, boolean param1Boolean2, boolean param1Boolean3) {
      if (param1Boolean3) {
        this.target.insns.insertBefore(this.head, param1AbstractInsnNode);
      } else {
        add(param1AbstractInsnNode);
      } 
      this.ctor += param1Boolean1 ? 1 : 0;
      this.invoke += param1Boolean2 ? 1 : 0;
    }
    
    void inject() {
      this.target.insertBefore(this.node, this);
      this.target.addToStack(Math.max(this.invoke, this.ctor));
    }
    
    boolean checkDescriptor(String param1String) {
      if (getDescriptor().equals(param1String))
        return true; 
      if (this.target.getSimpleCallbackDescriptor().equals(param1String) && !this.canCaptureLocals) {
        this.captureArgs = false;
        return true;
      } 
      Type[] arrayOfType1 = Type.getArgumentTypes(param1String);
      Type[] arrayOfType2 = Type.getArgumentTypes(this.descl);
      if (arrayOfType1.length != arrayOfType2.length)
        return false; 
      for (byte b = 0; b < arrayOfType2.length; b++) {
        Type type = arrayOfType1[b];
        if (!type.equals(arrayOfType2[b])) {
          if (type.getSort() == 9)
            return false; 
          if (Annotations.getInvisibleParameter(this.handler, Coerce.class, b) == null)
            return false; 
          if (!Injector.canCoerce(arrayOfType1[b], arrayOfType2[b]))
            return false; 
        } 
      } 
      return true;
    }
    
    boolean captureArgs() {
      return this.captureArgs;
    }
    
    int marshalVar() {
      if (this.marshalVar < 0)
        this.marshalVar = this.target.allocateLocal(); 
      return this.marshalVar;
    }
  }
  
  private final Map<Integer, String> ids = new HashMap<Integer, String>();
  
  private int totalInjections = 0;
  
  private int callbackInfoVar = -1;
  
  private final LocalCapture localCapture;
  
  private Target lastTarget;
  
  private final boolean cancellable;
  
  private String callbackInfoClass;
  
  private final String identifier;
  
  private String lastId;
  
  private String lastDesc;
  
  public CallbackInjector(InjectionInfo paramInjectionInfo, boolean paramBoolean, LocalCapture paramLocalCapture, String paramString) {
    super(paramInjectionInfo);
    this.cancellable = paramBoolean;
    this.localCapture = paramLocalCapture;
    this.identifier = paramString;
  }
  
  protected void sanityCheck(Target paramTarget, List<InjectionPoint> paramList) {
    super.sanityCheck(paramTarget, paramList);
    if (paramTarget.isStatic != this.isStatic)
      throw new InvalidInjectionException(this.info, "'static' modifier of callback method does not match target in " + this); 
    if ("<init>".equals(paramTarget.method.name))
      for (InjectionPoint injectionPoint : paramList) {
        if (!injectionPoint.getClass().equals(BeforeReturn.class))
          throw new InvalidInjectionException(this.info, "Found injection point type " + injectionPoint.getClass().getSimpleName() + " targetting a ctor in " + this + ". Only RETURN allowed for a ctor target"); 
      }  
  }
  
  protected void addTargetNode(Target paramTarget, List<InjectionNodes.InjectionNode> paramList, AbstractInsnNode paramAbstractInsnNode, Set<InjectionPoint> paramSet) {
    InjectionNodes.InjectionNode injectionNode = paramTarget.addInjectionNode(paramAbstractInsnNode);
    for (InjectionPoint injectionPoint : paramSet) {
      String str1 = injectionPoint.getId();
      if (Strings.isNullOrEmpty(str1))
        continue; 
      String str2 = this.ids.get(Integer.valueOf(injectionNode.getId()));
      if (str2 != null && !str2.equals(str1)) {
        Injector.logger.warn("Conflicting id for {} insn in {}, found id {} on {}, previously defined as {}", new Object[] { Bytecode.getOpcodeName(paramAbstractInsnNode), paramTarget
              .toString(), str1, this.info, str2 });
        break;
      } 
      this.ids.put(Integer.valueOf(injectionNode.getId()), str1);
    } 
    paramList.add(injectionNode);
    this.totalInjections++;
  }
  
  protected void inject(Target paramTarget, InjectionNodes.InjectionNode paramInjectionNode) {
    LocalVariableNode[] arrayOfLocalVariableNode = null;
    if (this.localCapture.isCaptureLocals() || this.localCapture.isPrintLocals())
      arrayOfLocalVariableNode = Locals.getLocalsAt(this.classNode, paramTarget.method, paramInjectionNode.getCurrentTarget()); 
    inject(new Callback(this.methodNode, paramTarget, paramInjectionNode, arrayOfLocalVariableNode, this.localCapture.isCaptureLocals()));
  }
  
  private void inject(Callback paramCallback) {
    if (this.localCapture.isPrintLocals()) {
      printLocals(paramCallback);
      this.info.addCallbackInvocation(this.methodNode);
      return;
    } 
    MethodNode methodNode = this.methodNode;
    if (!paramCallback.checkDescriptor(this.methodNode.desc)) {
      if (this.info.getTargets().size() > 1)
        return; 
      if (paramCallback.canCaptureLocals) {
        MethodNode methodNode1 = Bytecode.findMethod(this.classNode, this.methodNode.name, paramCallback.getDescriptor());
        if (methodNode1 != null && Annotations.getVisible(methodNode1, Surrogate.class) != null) {
          methodNode = methodNode1;
        } else {
          String str = generateBadLVTMessage(paramCallback);
          switch (this.localCapture) {
            case CAPTURE_FAILEXCEPTION:
              Injector.logger.error("Injection error: {}", new Object[] { str });
              methodNode = generateErrorMethod(paramCallback, "org/spongepowered/asm/mixin/injection/throwables/InjectionError", str);
              break;
            case CAPTURE_FAILSOFT:
              Injector.logger.warn("Injection warning: {}", new Object[] { str });
              return;
            default:
              Injector.logger.error("Critical injection failure: {}", new Object[] { str });
              throw new InjectionError(str);
          } 
        } 
      } else {
        String str = this.methodNode.desc.replace("Lorg/spongepowered/asm/mixin/injection/callback/CallbackInfo;", "Lorg/spongepowered/asm/mixin/injection/callback/CallbackInfoReturnable;");
        if (paramCallback.checkDescriptor(str))
          throw new InvalidInjectionException(this.info, "Invalid descriptor on " + this.info + "! CallbackInfoReturnable is required!"); 
        MethodNode methodNode1 = Bytecode.findMethod(this.classNode, this.methodNode.name, paramCallback.getDescriptor());
        if (methodNode1 != null && Annotations.getVisible(methodNode1, Surrogate.class) != null) {
          methodNode = methodNode1;
        } else {
          throw new InvalidInjectionException(this.info, "Invalid descriptor on " + this.info + "! Expected " + paramCallback.getDescriptor() + " but found " + this.methodNode.desc);
        } 
      } 
    } 
    dupReturnValue(paramCallback);
    if (this.cancellable || this.totalInjections > 1)
      createCallbackInfo(paramCallback, true); 
    invokeCallback(paramCallback, methodNode);
    injectCancellationCode(paramCallback);
    paramCallback.inject();
    this.info.notifyInjected(paramCallback.target);
  }
  
  private String generateBadLVTMessage(Callback paramCallback) {
    int i = paramCallback.target.indexOf(paramCallback.node);
    List<String> list1 = summariseLocals(this.methodNode.desc, paramCallback.target.arguments.length + 1);
    List<String> list2 = summariseLocals(paramCallback.getDescriptorWithAllLocals(), paramCallback.frameSize);
    return String.format("LVT in %s has incompatible changes at opcode %d in callback %s.\nExpected: %s\n   Found: %s", new Object[] { paramCallback.target, 
          Integer.valueOf(i), this, list1, list2 });
  }
  
  private MethodNode generateErrorMethod(Callback paramCallback, String paramString1, String paramString2) {
    MethodNode methodNode = this.info.addMethod(this.methodNode.access, this.methodNode.name + "$missing", paramCallback.getDescriptor());
    methodNode.maxLocals = Bytecode.getFirstNonArgLocalIndex(Type.getArgumentTypes(paramCallback.getDescriptor()), !this.isStatic);
    methodNode.maxStack = 3;
    InsnList insnList = methodNode.instructions;
    insnList.add((AbstractInsnNode)new TypeInsnNode(187, paramString1));
    insnList.add((AbstractInsnNode)new InsnNode(89));
    insnList.add((AbstractInsnNode)new LdcInsnNode(paramString2));
    insnList.add((AbstractInsnNode)new MethodInsnNode(183, paramString1, "<init>", "(Ljava/lang/String;)V", false));
    insnList.add((AbstractInsnNode)new InsnNode(191));
    return methodNode;
  }
  
  private void printLocals(Callback paramCallback) {
    Type[] arrayOfType = Type.getArgumentTypes(paramCallback.getDescriptorWithAllLocals());
    SignaturePrinter signaturePrinter1 = new SignaturePrinter(paramCallback.target.method, paramCallback.argNames);
    SignaturePrinter signaturePrinter2 = new SignaturePrinter(this.methodNode.name, paramCallback.target.returnType, arrayOfType, paramCallback.argNames);
    signaturePrinter2.setModifiers(this.methodNode);
    PrettyPrinter prettyPrinter = new PrettyPrinter();
    prettyPrinter.kv("Target Class", this.classNode.name.replace('/', '.'));
    prettyPrinter.kv("Target Method", signaturePrinter1);
    prettyPrinter.kv("Target Max LOCALS", Integer.valueOf(paramCallback.target.getMaxLocals()));
    prettyPrinter.kv("Initial Frame Size", Integer.valueOf(paramCallback.frameSize));
    prettyPrinter.kv("Callback Name", this.methodNode.name);
    prettyPrinter.kv("Instruction", "%s %s", new Object[] { paramCallback.node.getClass().getSimpleName(), 
          Bytecode.getOpcodeName(paramCallback.node.getCurrentTarget().getOpcode()) });
    prettyPrinter.hr();
    if (paramCallback.locals.length > paramCallback.frameSize) {
      prettyPrinter.add("  %s  %20s  %s", new Object[] { "LOCAL", "TYPE", "NAME" });
      for (byte b = 0; b < paramCallback.locals.length; b++) {
        String str = (b == paramCallback.frameSize) ? ">" : " ";
        if (paramCallback.locals[b] != null) {
          prettyPrinter.add("%s [%3d]  %20s  %-50s %s", new Object[] { str, Integer.valueOf(b), SignaturePrinter.getTypeName(paramCallback.localTypes[b], false), 
                meltSnowman(b, (paramCallback.locals[b]).name), (b >= paramCallback.frameSize) ? "<capture>" : "" });
        } else {
          boolean bool = (b > 0 && paramCallback.localTypes[b - 1] != null && paramCallback.localTypes[b - 1].getSize() > 1) ? true : false;
          prettyPrinter.add("%s [%3d]  %20s", new Object[] { str, Integer.valueOf(b), bool ? "<top>" : "-" });
        } 
      } 
      prettyPrinter.hr();
    } 
    prettyPrinter.add().add("/**").add(" * Expected callback signature").add(" * /");
    prettyPrinter.add("%s {", new Object[] { signaturePrinter2 });
    prettyPrinter.add("    // Method body").add("}").add().print(System.err);
  }
  
  private void createCallbackInfo(Callback paramCallback, boolean paramBoolean) {
    if (paramCallback.target != this.lastTarget) {
      this.lastId = null;
      this.lastDesc = null;
    } 
    this.lastTarget = paramCallback.target;
    String str1 = getIdentifier(paramCallback);
    String str2 = paramCallback.getCallbackInfoConstructorDescriptor();
    if (str1.equals(this.lastId) && str2.equals(this.lastDesc) && !paramCallback.isAtReturn && !this.cancellable)
      return; 
    instanceCallbackInfo(paramCallback, str1, str2, paramBoolean);
  }
  
  private void loadOrCreateCallbackInfo(Callback paramCallback) {
    if (this.cancellable || this.totalInjections > 1) {
      paramCallback.add((AbstractInsnNode)new VarInsnNode(25, this.callbackInfoVar), false, true);
    } else {
      createCallbackInfo(paramCallback, false);
    } 
  }
  
  private void dupReturnValue(Callback paramCallback) {
    if (!paramCallback.isAtReturn)
      return; 
    paramCallback.add((AbstractInsnNode)new InsnNode(89));
    paramCallback.add((AbstractInsnNode)new VarInsnNode(paramCallback.target.returnType.getOpcode(54), paramCallback.marshalVar()));
  }
  
  protected void instanceCallbackInfo(Callback paramCallback, String paramString1, String paramString2, boolean paramBoolean) {
    this.lastId = paramString1;
    this.lastDesc = paramString2;
    this.callbackInfoVar = paramCallback.marshalVar();
    this.callbackInfoClass = paramCallback.target.getCallbackInfoClass();
    boolean bool = (paramBoolean && this.totalInjections > 1 && !paramCallback.isAtReturn && !this.cancellable) ? true : false;
    paramCallback.add((AbstractInsnNode)new TypeInsnNode(187, this.callbackInfoClass), true, !paramBoolean, bool);
    paramCallback.add((AbstractInsnNode)new InsnNode(89), true, true, bool);
    paramCallback.add((AbstractInsnNode)new LdcInsnNode(paramString1), true, !paramBoolean, bool);
    paramCallback.add((AbstractInsnNode)new InsnNode(this.cancellable ? 4 : 3), true, !paramBoolean, bool);
    if (paramCallback.isAtReturn) {
      paramCallback.add((AbstractInsnNode)new VarInsnNode(paramCallback.target.returnType.getOpcode(21), paramCallback.marshalVar()), true, !paramBoolean);
      paramCallback.add((AbstractInsnNode)new MethodInsnNode(183, this.callbackInfoClass, "<init>", paramString2, false));
    } else {
      paramCallback.add((AbstractInsnNode)new MethodInsnNode(183, this.callbackInfoClass, "<init>", paramString2, false), false, false, bool);
    } 
    if (paramBoolean) {
      paramCallback.target.addLocalVariable(this.callbackInfoVar, "callbackInfo" + this.callbackInfoVar, "L" + this.callbackInfoClass + ";");
      paramCallback.add((AbstractInsnNode)new VarInsnNode(58, this.callbackInfoVar), false, false, bool);
    } 
  }
  
  private void invokeCallback(Callback paramCallback, MethodNode paramMethodNode) {
    if (!this.isStatic)
      paramCallback.add((AbstractInsnNode)new VarInsnNode(25, 0), false, true); 
    if (paramCallback.captureArgs())
      Bytecode.loadArgs(paramCallback.target.arguments, paramCallback, this.isStatic ? 0 : 1, -1); 
    loadOrCreateCallbackInfo(paramCallback);
    if (paramCallback.canCaptureLocals)
      Locals.loadLocals(paramCallback.localTypes, paramCallback, paramCallback.frameSize, paramCallback.extraArgs); 
    invokeHandler(paramCallback, paramMethodNode);
  }
  
  private String getIdentifier(Callback paramCallback) {
    String str1 = Strings.isNullOrEmpty(this.identifier) ? paramCallback.target.method.name : this.identifier;
    String str2 = this.ids.get(Integer.valueOf(paramCallback.node.getId()));
    return str1 + (Strings.isNullOrEmpty(str2) ? "" : (":" + str2));
  }
  
  protected void injectCancellationCode(Callback paramCallback) {
    if (!this.cancellable)
      return; 
    paramCallback.add((AbstractInsnNode)new VarInsnNode(25, this.callbackInfoVar));
    paramCallback.add((AbstractInsnNode)new MethodInsnNode(182, this.callbackInfoClass, CallbackInfo.getIsCancelledMethodName(), 
          CallbackInfo.getIsCancelledMethodSig(), false));
    LabelNode labelNode = new LabelNode();
    paramCallback.add((AbstractInsnNode)new JumpInsnNode(153, labelNode));
    injectReturnCode(paramCallback);
    paramCallback.add((AbstractInsnNode)labelNode);
  }
  
  protected void injectReturnCode(Callback paramCallback) {
    if (paramCallback.target.returnType.equals(Type.VOID_TYPE)) {
      paramCallback.add((AbstractInsnNode)new InsnNode(177));
    } else {
      paramCallback.add((AbstractInsnNode)new VarInsnNode(25, paramCallback.marshalVar()));
      String str1 = CallbackInfoReturnable.getReturnAccessor(paramCallback.target.returnType);
      String str2 = CallbackInfoReturnable.getReturnDescriptor(paramCallback.target.returnType);
      paramCallback.add((AbstractInsnNode)new MethodInsnNode(182, this.callbackInfoClass, str1, str2, false));
      if (paramCallback.target.returnType.getSort() == 10)
        paramCallback.add((AbstractInsnNode)new TypeInsnNode(192, paramCallback.target.returnType.getInternalName())); 
      paramCallback.add((AbstractInsnNode)new InsnNode(paramCallback.target.returnType.getOpcode(172)));
    } 
  }
  
  protected boolean isStatic() {
    return this.isStatic;
  }
  
  private static List<String> summariseLocals(String paramString, int paramInt) {
    return summariseLocals(Type.getArgumentTypes(paramString), paramInt);
  }
  
  private static List<String> summariseLocals(Type[] paramArrayOfType, int paramInt) {
    ArrayList<String> arrayList = new ArrayList();
    if (paramArrayOfType != null)
      for (; paramInt < paramArrayOfType.length; paramInt++) {
        if (paramArrayOfType[paramInt] != null)
          arrayList.add(paramArrayOfType[paramInt].toString()); 
      }  
    return arrayList;
  }
  
  static String meltSnowman(int paramInt, String paramString) {
    return (paramString != null && '☃' == paramString.charAt(0)) ? ("var" + paramInt) : paramString;
  }
}
