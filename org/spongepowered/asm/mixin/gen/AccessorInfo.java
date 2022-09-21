package org.spongepowered.asm.mixin.gen;

import com.google.common.base.Strings;
import com.google.common.collect.ImmutableSet;
import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.logging.log4j.LogManager;
import org.spongepowered.asm.lib.Type;
import org.spongepowered.asm.lib.tree.FieldNode;
import org.spongepowered.asm.lib.tree.MethodNode;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.gen.throwables.InvalidAccessorException;
import org.spongepowered.asm.mixin.injection.struct.MemberInfo;
import org.spongepowered.asm.mixin.refmap.IMixinContext;
import org.spongepowered.asm.mixin.struct.SpecialMethodInfo;
import org.spongepowered.asm.mixin.transformer.MixinTargetContext;
import org.spongepowered.asm.util.Annotations;
import org.spongepowered.asm.util.Bytecode;

public class AccessorInfo extends SpecialMethodInfo {
  public enum AccessorType {
    FIELD_SETTER,
    METHOD_PROXY,
    FIELD_GETTER((String)ImmutableSet.of("get", "is")) {
      AccessorGenerator getGenerator(AccessorInfo param2AccessorInfo) {
        return new AccessorGeneratorFieldGetter(param2AccessorInfo);
      }
    };
    
    private final Set<String> expectedPrefixes;
    
    static {
    
    }
    
    AccessorType(Set<String> param1Set) {
      this.expectedPrefixes = param1Set;
    }
    
    public boolean isExpectedPrefix(String param1String) {
      return this.expectedPrefixes.contains(param1String);
    }
    
    public String getExpectedPrefixes() {
      return this.expectedPrefixes.toString();
    }
    
    abstract AccessorGenerator getGenerator(AccessorInfo param1AccessorInfo);
  }
  
  protected static final Pattern PATTERN_ACCESSOR = Pattern.compile("^(get|set|is|invoke|call)(([A-Z])(.*?))(_\\$md.*)?$");
  
  protected FieldNode targetField;
  
  protected final Type[] argTypes;
  
  protected MethodNode targetMethod;
  
  protected final MemberInfo target;
  
  protected final AccessorType type;
  
  private final Type targetFieldType;
  
  protected final Type returnType;
  
  public AccessorInfo(MixinTargetContext paramMixinTargetContext, MethodNode paramMethodNode) {
    this(paramMixinTargetContext, paramMethodNode, (Class)Accessor.class);
  }
  
  protected AccessorInfo(MixinTargetContext paramMixinTargetContext, MethodNode paramMethodNode, Class<? extends Annotation> paramClass) {
    super(paramMixinTargetContext, paramMethodNode, Annotations.getVisible(paramMethodNode, paramClass));
    this.argTypes = Type.getArgumentTypes(paramMethodNode.desc);
    this.returnType = Type.getReturnType(paramMethodNode.desc);
    this.type = initType();
    this.targetFieldType = initTargetFieldType();
    this.target = initTarget();
  }
  
  protected AccessorType initType() {
    if (this.returnType.equals(Type.VOID_TYPE))
      return AccessorType.FIELD_SETTER; 
    return AccessorType.FIELD_GETTER;
  }
  
  protected Type initTargetFieldType() {
    switch (this.type) {
      case FIELD_GETTER:
        if (this.argTypes.length > 0)
          throw new InvalidAccessorException(this.mixin, this + " must take exactly 0 arguments, found " + this.argTypes.length); 
        return this.returnType;
      case FIELD_SETTER:
        if (this.argTypes.length != 1)
          throw new InvalidAccessorException(this.mixin, this + " must take exactly 1 argument, found " + this.argTypes.length); 
        return this.argTypes[0];
    } 
    throw new InvalidAccessorException(this.mixin, "Computed unsupported accessor type " + this.type + " for " + this);
  }
  
  protected MemberInfo initTarget() {
    MemberInfo memberInfo = new MemberInfo(getTargetName(), null, this.targetFieldType.getDescriptor());
    this.annotation.visit("target", memberInfo.toString());
    return memberInfo;
  }
  
  protected String getTargetName() {
    String str = (String)Annotations.getValue(this.annotation);
    if (Strings.isNullOrEmpty(str)) {
      String str1 = inflectTarget();
      if (str1 == null)
        throw new InvalidAccessorException(this.mixin, "Failed to inflect target name for " + this + ", supported prefixes: [get, set, is]"); 
      return str1;
    } 
    return (MemberInfo.parse(str, (IMixinContext)this.mixin)).name;
  }
  
  protected String inflectTarget() {
    return inflectTarget(this.method.name, this.type, toString(), (IMixinContext)this.mixin, this.mixin
        .getEnvironment().getOption(MixinEnvironment.Option.DEBUG_VERBOSE));
  }
  
  public static String inflectTarget(String paramString1, AccessorType paramAccessorType, String paramString2, IMixinContext paramIMixinContext, boolean paramBoolean) {
    Matcher matcher = PATTERN_ACCESSOR.matcher(paramString1);
    if (matcher.matches()) {
      String str1 = matcher.group(1);
      String str2 = matcher.group(3);
      String str3 = matcher.group(4);
      String str4 = String.format("%s%s", new Object[] { toLowerCase(str2, !isUpperCase(str3)), str3 });
      if (!paramAccessorType.isExpectedPrefix(str1) && paramBoolean)
        LogManager.getLogger("mixin").warn("Unexpected prefix for {}, found [{}] expecting {}", new Object[] { paramString2, str1, paramAccessorType
              .getExpectedPrefixes() }); 
      return (MemberInfo.parse(str4, paramIMixinContext)).name;
    } 
    return null;
  }
  
  public final MemberInfo getTarget() {
    return this.target;
  }
  
  public final Type getTargetFieldType() {
    return this.targetFieldType;
  }
  
  public final FieldNode getTargetField() {
    return this.targetField;
  }
  
  public final MethodNode getTargetMethod() {
    return this.targetMethod;
  }
  
  public final Type getReturnType() {
    return this.returnType;
  }
  
  public final Type[] getArgTypes() {
    return this.argTypes;
  }
  
  public String toString() {
    return String.format("%s->@%s[%s]::%s%s", new Object[] { this.mixin.toString(), Bytecode.getSimpleName(this.annotation), this.type.toString(), this.method.name, this.method.desc });
  }
  
  public void locate() {
    this.targetField = findTargetField();
  }
  
  public MethodNode generate() {
    MethodNode methodNode = this.type.getGenerator(this).generate();
    Bytecode.mergeAnnotations(this.method, methodNode);
    return methodNode;
  }
  
  private FieldNode findTargetField() {
    return findTarget(this.classNode.fields);
  }
  
  protected <TNode> TNode findTarget(List<TNode> paramList) {
    // Byte code:
    //   0: aconst_null
    //   1: astore_2
    //   2: new java/util/ArrayList
    //   5: dup
    //   6: invokespecial <init> : ()V
    //   9: astore_3
    //   10: aload_1
    //   11: invokeinterface iterator : ()Ljava/util/Iterator;
    //   16: astore #4
    //   18: aload #4
    //   20: invokeinterface hasNext : ()Z
    //   25: ifeq -> 124
    //   28: aload #4
    //   30: invokeinterface next : ()Ljava/lang/Object;
    //   35: astore #5
    //   37: aload #5
    //   39: invokestatic getNodeDesc : (Ljava/lang/Object;)Ljava/lang/String;
    //   42: astore #6
    //   44: aload #6
    //   46: ifnull -> 18
    //   49: aload #6
    //   51: aload_0
    //   52: getfield target : Lorg/spongepowered/asm/mixin/injection/struct/MemberInfo;
    //   55: getfield desc : Ljava/lang/String;
    //   58: invokevirtual equals : (Ljava/lang/Object;)Z
    //   61: ifne -> 67
    //   64: goto -> 18
    //   67: aload #5
    //   69: invokestatic getNodeName : (Ljava/lang/Object;)Ljava/lang/String;
    //   72: astore #7
    //   74: aload #7
    //   76: ifnull -> 121
    //   79: aload #7
    //   81: aload_0
    //   82: getfield target : Lorg/spongepowered/asm/mixin/injection/struct/MemberInfo;
    //   85: getfield name : Ljava/lang/String;
    //   88: invokevirtual equals : (Ljava/lang/Object;)Z
    //   91: ifeq -> 97
    //   94: aload #5
    //   96: astore_2
    //   97: aload #7
    //   99: aload_0
    //   100: getfield target : Lorg/spongepowered/asm/mixin/injection/struct/MemberInfo;
    //   103: getfield name : Ljava/lang/String;
    //   106: invokevirtual equalsIgnoreCase : (Ljava/lang/String;)Z
    //   109: ifeq -> 121
    //   112: aload_3
    //   113: aload #5
    //   115: invokeinterface add : (Ljava/lang/Object;)Z
    //   120: pop
    //   121: goto -> 18
    //   124: aload_2
    //   125: ifnull -> 169
    //   128: aload_3
    //   129: invokeinterface size : ()I
    //   134: iconst_1
    //   135: if_icmple -> 167
    //   138: ldc_w 'mixin'
    //   141: invokestatic getLogger : (Ljava/lang/String;)Lorg/apache/logging/log4j/Logger;
    //   144: ldc_w '{} found an exact match for {} but other candidates were found!'
    //   147: iconst_2
    //   148: anewarray java/lang/Object
    //   151: dup
    //   152: iconst_0
    //   153: aload_0
    //   154: aastore
    //   155: dup
    //   156: iconst_1
    //   157: aload_0
    //   158: getfield target : Lorg/spongepowered/asm/mixin/injection/struct/MemberInfo;
    //   161: aastore
    //   162: invokeinterface debug : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   167: aload_2
    //   168: areturn
    //   169: aload_3
    //   170: invokeinterface size : ()I
    //   175: iconst_1
    //   176: if_icmpne -> 187
    //   179: aload_3
    //   180: iconst_0
    //   181: invokeinterface get : (I)Ljava/lang/Object;
    //   186: areturn
    //   187: aload_3
    //   188: invokeinterface size : ()I
    //   193: ifne -> 202
    //   196: ldc_w 'No'
    //   199: goto -> 205
    //   202: ldc_w 'Multiple'
    //   205: astore #4
    //   207: new org/spongepowered/asm/mixin/gen/throwables/InvalidAccessorException
    //   210: dup
    //   211: aload_0
    //   212: new java/lang/StringBuilder
    //   215: dup
    //   216: invokespecial <init> : ()V
    //   219: aload #4
    //   221: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   224: ldc_w ' candidates were found matching '
    //   227: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   230: aload_0
    //   231: getfield target : Lorg/spongepowered/asm/mixin/injection/struct/MemberInfo;
    //   234: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   237: ldc_w ' in '
    //   240: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   243: aload_0
    //   244: getfield classNode : Lorg/spongepowered/asm/lib/tree/ClassNode;
    //   247: getfield name : Ljava/lang/String;
    //   250: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   253: ldc ' for '
    //   255: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   258: aload_0
    //   259: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   262: invokevirtual toString : ()Ljava/lang/String;
    //   265: invokespecial <init> : (Lorg/spongepowered/asm/mixin/gen/AccessorInfo;Ljava/lang/String;)V
    //   268: athrow
    // Line number table:
    //   Java source line number -> byte code offset
    //   #350	-> 0
    //   #351	-> 2
    //   #353	-> 10
    //   #354	-> 37
    //   #355	-> 44
    //   #356	-> 64
    //   #359	-> 67
    //   #360	-> 74
    //   #361	-> 79
    //   #362	-> 94
    //   #364	-> 97
    //   #365	-> 112
    //   #368	-> 121
    //   #370	-> 124
    //   #371	-> 128
    //   #372	-> 138
    //   #374	-> 167
    //   #377	-> 169
    //   #378	-> 179
    //   #381	-> 187
    //   #382	-> 207
  }
  
  private static <TNode> String getNodeDesc(TNode paramTNode) {
    return (paramTNode instanceof MethodNode) ? ((MethodNode)paramTNode).desc : ((paramTNode instanceof FieldNode) ? ((FieldNode)paramTNode).desc : null);
  }
  
  private static <TNode> String getNodeName(TNode paramTNode) {
    return (paramTNode instanceof MethodNode) ? ((MethodNode)paramTNode).name : ((paramTNode instanceof FieldNode) ? ((FieldNode)paramTNode).name : null);
  }
  
  public static AccessorInfo of(MixinTargetContext paramMixinTargetContext, MethodNode paramMethodNode, Class<? extends Annotation> paramClass) {
    if (paramClass == Accessor.class)
      return new AccessorInfo(paramMixinTargetContext, paramMethodNode); 
    if (paramClass == Invoker.class)
      return new InvokerInfo(paramMixinTargetContext, paramMethodNode); 
    throw new InvalidAccessorException(paramMixinTargetContext, "Could not parse accessor for unknown type " + paramClass.getName());
  }
  
  private static String toLowerCase(String paramString, boolean paramBoolean) {
    return paramBoolean ? paramString.toLowerCase() : paramString;
  }
  
  private static boolean isUpperCase(String paramString) {
    return paramString.toUpperCase().equals(paramString);
  }
}
