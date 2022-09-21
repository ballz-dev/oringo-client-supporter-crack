package org.spongepowered.asm.mixin.transformer.ext;

import com.google.common.collect.ImmutableList;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.transformer.MixinTransformer;

public final class Extensions {
  private final List<IExtension> extensions = new ArrayList<IExtension>();
  
  private final Map<Class<? extends IExtension>, IExtension> extensionMap = new HashMap<Class<? extends IExtension>, IExtension>();
  
  private final List<IClassGenerator> generators = new ArrayList<IClassGenerator>();
  
  private final List<IClassGenerator> generatorsView = Collections.unmodifiableList(this.generators);
  
  private final Map<Class<? extends IClassGenerator>, IClassGenerator> generatorMap = new HashMap<Class<? extends IClassGenerator>, IClassGenerator>();
  
  private List<IExtension> activeExtensions = Collections.emptyList();
  
  private final MixinTransformer transformer;
  
  public Extensions(MixinTransformer paramMixinTransformer) {
    this.transformer = paramMixinTransformer;
  }
  
  public MixinTransformer getTransformer() {
    return this.transformer;
  }
  
  public void add(IExtension paramIExtension) {
    this.extensions.add(paramIExtension);
    this.extensionMap.put(paramIExtension.getClass(), paramIExtension);
  }
  
  public List<IExtension> getExtensions() {
    return Collections.unmodifiableList(this.extensions);
  }
  
  public List<IExtension> getActiveExtensions() {
    return this.activeExtensions;
  }
  
  public <T extends IExtension> T getExtension(Class<? extends IExtension> paramClass) {
    return (T)lookup(paramClass, this.extensionMap, this.extensions);
  }
  
  public void select(MixinEnvironment paramMixinEnvironment) {
    ImmutableList.Builder builder = ImmutableList.builder();
    for (IExtension iExtension : this.extensions) {
      if (iExtension.checkActive(paramMixinEnvironment))
        builder.add(iExtension); 
    } 
    this.activeExtensions = (List<IExtension>)builder.build();
  }
  
  public void preApply(ITargetClassContext paramITargetClassContext) {
    for (IExtension iExtension : this.activeExtensions)
      iExtension.preApply(paramITargetClassContext); 
  }
  
  public void postApply(ITargetClassContext paramITargetClassContext) {
    for (IExtension iExtension : this.activeExtensions)
      iExtension.postApply(paramITargetClassContext); 
  }
  
  public void export(MixinEnvironment paramMixinEnvironment, String paramString, boolean paramBoolean, byte[] paramArrayOfbyte) {
    for (IExtension iExtension : this.activeExtensions)
      iExtension.export(paramMixinEnvironment, paramString, paramBoolean, paramArrayOfbyte); 
  }
  
  public void add(IClassGenerator paramIClassGenerator) {
    this.generators.add(paramIClassGenerator);
    this.generatorMap.put(paramIClassGenerator.getClass(), paramIClassGenerator);
  }
  
  public List<IClassGenerator> getGenerators() {
    return this.generatorsView;
  }
  
  public <T extends IClassGenerator> T getGenerator(Class<? extends IClassGenerator> paramClass) {
    return (T)lookup(paramClass, this.generatorMap, this.generators);
  }
  
  private static <T> T lookup(Class<? extends T> paramClass, Map<Class<? extends T>, T> paramMap, List<T> paramList) {
    // Byte code:
    //   0: aload_1
    //   1: aload_0
    //   2: invokeinterface get : (Ljava/lang/Object;)Ljava/lang/Object;
    //   7: astore_3
    //   8: aload_3
    //   9: ifnonnull -> 108
    //   12: aload_2
    //   13: invokeinterface iterator : ()Ljava/util/Iterator;
    //   18: astore #4
    //   20: aload #4
    //   22: invokeinterface hasNext : ()Z
    //   27: ifeq -> 60
    //   30: aload #4
    //   32: invokeinterface next : ()Ljava/lang/Object;
    //   37: astore #5
    //   39: aload_0
    //   40: aload #5
    //   42: invokevirtual getClass : ()Ljava/lang/Class;
    //   45: invokevirtual isAssignableFrom : (Ljava/lang/Class;)Z
    //   48: ifeq -> 57
    //   51: aload #5
    //   53: astore_3
    //   54: goto -> 60
    //   57: goto -> 20
    //   60: aload_3
    //   61: ifnonnull -> 99
    //   64: new java/lang/IllegalArgumentException
    //   67: dup
    //   68: new java/lang/StringBuilder
    //   71: dup
    //   72: invokespecial <init> : ()V
    //   75: ldc 'Extension for <'
    //   77: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   80: aload_0
    //   81: invokevirtual getName : ()Ljava/lang/String;
    //   84: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   87: ldc '> could not be found'
    //   89: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   92: invokevirtual toString : ()Ljava/lang/String;
    //   95: invokespecial <init> : (Ljava/lang/String;)V
    //   98: athrow
    //   99: aload_1
    //   100: aload_0
    //   101: aload_3
    //   102: invokeinterface put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   107: pop
    //   108: aload_3
    //   109: areturn
    // Line number table:
    //   Java source line number -> byte code offset
    //   #209	-> 0
    //   #210	-> 8
    //   #211	-> 12
    //   #212	-> 39
    //   #213	-> 51
    //   #214	-> 54
    //   #216	-> 57
    //   #218	-> 60
    //   #219	-> 64
    //   #222	-> 99
    //   #225	-> 108
  }
}
