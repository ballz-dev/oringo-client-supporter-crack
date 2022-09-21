package org.spongepowered.asm.mixin.transformer;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.spongepowered.asm.lib.ClassReader;
import org.spongepowered.asm.lib.ClassVisitor;
import org.spongepowered.asm.lib.FieldVisitor;
import org.spongepowered.asm.lib.MethodVisitor;
import org.spongepowered.asm.lib.Type;
import org.spongepowered.asm.lib.tree.AbstractInsnNode;
import org.spongepowered.asm.lib.tree.AnnotationNode;
import org.spongepowered.asm.lib.tree.InsnNode;
import org.spongepowered.asm.lib.tree.MethodInsnNode;
import org.spongepowered.asm.lib.tree.MethodNode;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.transformer.throwables.MixinTransformerError;
import org.spongepowered.asm.transformers.MixinClassWriter;
import org.spongepowered.asm.transformers.TreeTransformer;
import org.spongepowered.asm.util.Bytecode;

class MixinPostProcessor extends TreeTransformer implements MixinConfig.IListener {
  private final Set<String> syntheticInnerClasses = new HashSet<String>();
  
  private final Map<String, MixinInfo> accessorMixins = new HashMap<String, MixinInfo>();
  
  private final Set<String> loadable = new HashSet<String>();
  
  public void onInit(MixinInfo paramMixinInfo) {
    for (String str : paramMixinInfo.getSyntheticInnerClasses())
      registerSyntheticInner(str.replace('/', '.')); 
  }
  
  public void onPrepare(MixinInfo paramMixinInfo) {
    String str = paramMixinInfo.getClassName();
    if (paramMixinInfo.isLoadable())
      registerLoadable(str); 
    if (paramMixinInfo.isAccessor())
      registerAccessor(paramMixinInfo); 
  }
  
  void registerSyntheticInner(String paramString) {
    this.syntheticInnerClasses.add(paramString);
  }
  
  void registerLoadable(String paramString) {
    this.loadable.add(paramString);
  }
  
  void registerAccessor(MixinInfo paramMixinInfo) {
    registerLoadable(paramMixinInfo.getClassName());
    this.accessorMixins.put(paramMixinInfo.getClassName(), paramMixinInfo);
  }
  
  boolean canTransform(String paramString) {
    return (this.syntheticInnerClasses.contains(paramString) || this.loadable.contains(paramString));
  }
  
  public String getName() {
    return getClass().getName();
  }
  
  public boolean isDelegationExcluded() {
    return true;
  }
  
  public byte[] transformClassBytes(String paramString1, String paramString2, byte[] paramArrayOfbyte) {
    if (this.syntheticInnerClasses.contains(paramString2))
      return processSyntheticInner(paramArrayOfbyte); 
    if (this.accessorMixins.containsKey(paramString2)) {
      MixinInfo mixinInfo = this.accessorMixins.get(paramString2);
      return processAccessor(paramArrayOfbyte, mixinInfo);
    } 
    return paramArrayOfbyte;
  }
  
  private byte[] processSyntheticInner(byte[] paramArrayOfbyte) {
    ClassReader classReader = new ClassReader(paramArrayOfbyte);
    MixinClassWriter mixinClassWriter = new MixinClassWriter(classReader, 0);
    ClassVisitor classVisitor = new ClassVisitor(327680, (ClassVisitor)mixinClassWriter) {
        public void visit(int param1Int1, int param1Int2, String param1String1, String param1String2, String param1String3, String[] param1ArrayOfString) {
          super.visit(param1Int1, param1Int2 | 0x1, param1String1, param1String2, param1String3, param1ArrayOfString);
        }
        
        public FieldVisitor visitField(int param1Int, String param1String1, String param1String2, String param1String3, Object param1Object) {
          if ((param1Int & 0x6) == 0)
            param1Int |= 0x1; 
          return super.visitField(param1Int, param1String1, param1String2, param1String3, param1Object);
        }
        
        public MethodVisitor visitMethod(int param1Int, String param1String1, String param1String2, String param1String3, String[] param1ArrayOfString) {
          if ((param1Int & 0x6) == 0)
            param1Int |= 0x1; 
          return super.visitMethod(param1Int, param1String1, param1String2, param1String3, param1ArrayOfString);
        }
      };
    classReader.accept(classVisitor, 8);
    return mixinClassWriter.toByteArray();
  }
  
  private byte[] processAccessor(byte[] paramArrayOfbyte, MixinInfo paramMixinInfo) {
    if (!MixinEnvironment.getCompatibilityLevel().isAtLeast(MixinEnvironment.CompatibilityLevel.JAVA_8))
      return paramArrayOfbyte; 
    boolean bool = false;
    MixinInfo.MixinClassNode mixinClassNode = paramMixinInfo.getClassNode(0);
    ClassInfo classInfo = paramMixinInfo.getTargets().get(0);
    for (MixinInfo.MixinMethodNode mixinMethodNode : mixinClassNode.mixinMethods) {
      if (!Bytecode.hasFlag(mixinMethodNode, 8))
        continue; 
      AnnotationNode annotationNode1 = mixinMethodNode.getVisibleAnnotation((Class)Accessor.class);
      AnnotationNode annotationNode2 = mixinMethodNode.getVisibleAnnotation((Class)Invoker.class);
      if (annotationNode1 != null || annotationNode2 != null) {
        ClassInfo.Method method = getAccessorMethod(paramMixinInfo, mixinMethodNode, classInfo);
        createProxy(mixinMethodNode, classInfo, method);
        bool = true;
      } 
    } 
    if (bool)
      return writeClass(mixinClassNode); 
    return paramArrayOfbyte;
  }
  
  private static ClassInfo.Method getAccessorMethod(MixinInfo paramMixinInfo, MethodNode paramMethodNode, ClassInfo paramClassInfo) throws MixinTransformerError {
    ClassInfo.Method method = paramMixinInfo.getClassInfo().findMethod(paramMethodNode, 10);
    if (!method.isRenamed())
      throw new MixinTransformerError("Unexpected state: " + paramMixinInfo + " loaded before " + paramClassInfo + " was conformed"); 
    return method;
  }
  
  private static void createProxy(MethodNode paramMethodNode, ClassInfo paramClassInfo, ClassInfo.Method paramMethod) {
    paramMethodNode.instructions.clear();
    Type[] arrayOfType = Type.getArgumentTypes(paramMethodNode.desc);
    Type type = Type.getReturnType(paramMethodNode.desc);
    Bytecode.loadArgs(arrayOfType, paramMethodNode.instructions, 0);
    paramMethodNode.instructions.add((AbstractInsnNode)new MethodInsnNode(184, paramClassInfo.getName(), paramMethod.getName(), paramMethodNode.desc, false));
    paramMethodNode.instructions.add((AbstractInsnNode)new InsnNode(type.getOpcode(172)));
    paramMethodNode.maxStack = Bytecode.getFirstNonArgLocalIndex(arrayOfType, false);
    paramMethodNode.maxLocals = 0;
  }
}
