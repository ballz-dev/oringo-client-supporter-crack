package org.spongepowered.asm.mixin.injection;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.spongepowered.asm.lib.tree.AbstractInsnNode;
import org.spongepowered.asm.lib.tree.AnnotationNode;
import org.spongepowered.asm.lib.tree.InsnList;
import org.spongepowered.asm.lib.tree.MethodNode;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.injection.modify.AfterStoreLocal;
import org.spongepowered.asm.mixin.injection.modify.BeforeLoadLocal;
import org.spongepowered.asm.mixin.injection.points.AfterInvoke;
import org.spongepowered.asm.mixin.injection.points.BeforeConstant;
import org.spongepowered.asm.mixin.injection.points.BeforeFieldAccess;
import org.spongepowered.asm.mixin.injection.points.BeforeFinalReturn;
import org.spongepowered.asm.mixin.injection.points.BeforeInvoke;
import org.spongepowered.asm.mixin.injection.points.BeforeNew;
import org.spongepowered.asm.mixin.injection.points.BeforeReturn;
import org.spongepowered.asm.mixin.injection.points.BeforeStringInvoke;
import org.spongepowered.asm.mixin.injection.points.JumpInsnPoint;
import org.spongepowered.asm.mixin.injection.points.MethodHead;
import org.spongepowered.asm.mixin.injection.struct.InjectionPointData;
import org.spongepowered.asm.mixin.injection.throwables.InvalidInjectionException;
import org.spongepowered.asm.mixin.refmap.IMixinContext;
import org.spongepowered.asm.mixin.transformer.MixinTargetContext;
import org.spongepowered.asm.util.Annotations;
import org.spongepowered.asm.util.Bytecode;

public abstract class InjectionPoint {
  public static final int DEFAULT_ALLOWED_SHIFT_BY = 0;
  
  private final Selector selector;
  
  private final String slice;
  
  private final String id;
  
  @Retention(RetentionPolicy.RUNTIME)
  @Target({ElementType.TYPE})
  public static @interface AtCode {
    String value();
  }
  
  public enum Selector {
    FIRST, ONE, LAST;
    
    public static final Selector DEFAULT = FIRST;
    
    static {
    
    }
  }
  
  enum ShiftByViolationBehaviour {
    IGNORE, WARN, ERROR;
    
    static {
    
    }
  }
  
  private static Map<String, Class<? extends InjectionPoint>> types = new HashMap<String, Class<? extends InjectionPoint>>();
  
  public static final int MAX_ALLOWED_SHIFT_BY = 5;
  
  static {
    register((Class)BeforeFieldAccess.class);
    register((Class)BeforeInvoke.class);
    register((Class)BeforeNew.class);
    register((Class)BeforeReturn.class);
    register((Class)BeforeStringInvoke.class);
    register((Class)JumpInsnPoint.class);
    register((Class)MethodHead.class);
    register((Class)AfterInvoke.class);
    register((Class)BeforeLoadLocal.class);
    register((Class)AfterStoreLocal.class);
    register((Class)BeforeFinalReturn.class);
    register((Class)BeforeConstant.class);
  }
  
  protected InjectionPoint() {
    this("", Selector.DEFAULT, null);
  }
  
  protected InjectionPoint(InjectionPointData paramInjectionPointData) {
    this(paramInjectionPointData.getSlice(), paramInjectionPointData.getSelector(), paramInjectionPointData.getId());
  }
  
  public InjectionPoint(String paramString1, Selector paramSelector, String paramString2) {
    this.slice = paramString1;
    this.selector = paramSelector;
    this.id = paramString2;
  }
  
  public String getSlice() {
    return this.slice;
  }
  
  public Selector getSelector() {
    return this.selector;
  }
  
  public String getId() {
    return this.id;
  }
  
  public boolean checkPriority(int paramInt1, int paramInt2) {
    return (paramInt1 < paramInt2);
  }
  
  public String toString() {
    return String.format("@At(\"%s\")", new Object[] { getAtCode() });
  }
  
  protected static AbstractInsnNode nextNode(InsnList paramInsnList, AbstractInsnNode paramAbstractInsnNode) {
    int i = paramInsnList.indexOf(paramAbstractInsnNode) + 1;
    if (i > 0 && i < paramInsnList.size())
      return paramInsnList.get(i); 
    return paramAbstractInsnNode;
  }
  
  static abstract class CompositeInjectionPoint extends InjectionPoint {
    protected final InjectionPoint[] components;
    
    protected CompositeInjectionPoint(InjectionPoint... param1VarArgs) {
      if (param1VarArgs == null || param1VarArgs.length < 2)
        throw new IllegalArgumentException("Must supply two or more component injection points for composite point!"); 
      this.components = param1VarArgs;
    }
    
    public String toString() {
      return "CompositeInjectionPoint(" + getClass().getSimpleName() + ")[" + Joiner.on(',').join((Object[])this.components) + "]";
    }
  }
  
  static final class Intersection extends CompositeInjectionPoint {
    public Intersection(InjectionPoint... param1VarArgs) {
      super(param1VarArgs);
    }
    
    public boolean find(String param1String, InsnList param1InsnList, Collection<AbstractInsnNode> param1Collection) {
      boolean bool = false;
      ArrayList[] arrayOfArrayList = (ArrayList[])Array.newInstance(ArrayList.class, this.components.length);
      for (byte b1 = 0; b1 < this.components.length; b1++) {
        arrayOfArrayList[b1] = new ArrayList();
        this.components[b1].find(param1String, param1InsnList, arrayOfArrayList[b1]);
      } 
      ArrayList<AbstractInsnNode> arrayList = arrayOfArrayList[0];
      for (byte b2 = 0; b2 < arrayList.size(); b2++) {
        AbstractInsnNode abstractInsnNode = arrayList.get(b2);
        boolean bool1 = true;
        for (byte b = 1; b < arrayOfArrayList.length && 
          arrayOfArrayList[b].contains(abstractInsnNode); b++);
        if (bool1) {
          param1Collection.add(abstractInsnNode);
          bool = true;
        } 
      } 
      return bool;
    }
  }
  
  static final class Union extends CompositeInjectionPoint {
    public Union(InjectionPoint... param1VarArgs) {
      super(param1VarArgs);
    }
    
    public boolean find(String param1String, InsnList param1InsnList, Collection<AbstractInsnNode> param1Collection) {
      LinkedHashSet<AbstractInsnNode> linkedHashSet = new LinkedHashSet();
      for (byte b = 0; b < this.components.length; b++)
        this.components[b].find(param1String, param1InsnList, linkedHashSet); 
      param1Collection.addAll(linkedHashSet);
      return (linkedHashSet.size() > 0);
    }
  }
  
  static final class Shift extends InjectionPoint {
    private final InjectionPoint input;
    
    private final int shift;
    
    public Shift(InjectionPoint param1InjectionPoint, int param1Int) {
      if (param1InjectionPoint == null)
        throw new IllegalArgumentException("Must supply an input injection point for SHIFT"); 
      this.input = param1InjectionPoint;
      this.shift = param1Int;
    }
    
    public String toString() {
      return "InjectionPoint(" + getClass().getSimpleName() + ")[" + this.input + "]";
    }
    
    public boolean find(String param1String, InsnList param1InsnList, Collection<AbstractInsnNode> param1Collection) {
      List<AbstractInsnNode> list = (param1Collection instanceof List) ? (List)param1Collection : new ArrayList<AbstractInsnNode>(param1Collection);
      this.input.find(param1String, param1InsnList, param1Collection);
      for (byte b = 0; b < list.size(); b++)
        list.set(b, param1InsnList.get(param1InsnList.indexOf(list.get(b)) + this.shift)); 
      if (param1Collection != list) {
        param1Collection.clear();
        param1Collection.addAll(list);
      } 
      return (param1Collection.size() > 0);
    }
  }
  
  public static InjectionPoint and(InjectionPoint... paramVarArgs) {
    return new Intersection(paramVarArgs);
  }
  
  public static InjectionPoint or(InjectionPoint... paramVarArgs) {
    return new Union(paramVarArgs);
  }
  
  public static InjectionPoint after(InjectionPoint paramInjectionPoint) {
    return new Shift(paramInjectionPoint, 1);
  }
  
  public static InjectionPoint before(InjectionPoint paramInjectionPoint) {
    return new Shift(paramInjectionPoint, -1);
  }
  
  public static InjectionPoint shift(InjectionPoint paramInjectionPoint, int paramInt) {
    return new Shift(paramInjectionPoint, paramInt);
  }
  
  public static List<InjectionPoint> parse(IInjectionPointContext paramIInjectionPointContext, List<AnnotationNode> paramList) {
    return parse(paramIInjectionPointContext.getContext(), paramIInjectionPointContext.getMethod(), paramIInjectionPointContext.getAnnotation(), paramList);
  }
  
  public static List<InjectionPoint> parse(IMixinContext paramIMixinContext, MethodNode paramMethodNode, AnnotationNode paramAnnotationNode, List<AnnotationNode> paramList) {
    ImmutableList.Builder builder = ImmutableList.builder();
    for (AnnotationNode annotationNode : paramList) {
      InjectionPoint injectionPoint = parse(paramIMixinContext, paramMethodNode, paramAnnotationNode, annotationNode);
      if (injectionPoint != null)
        builder.add(injectionPoint); 
    } 
    return (List<InjectionPoint>)builder.build();
  }
  
  public static InjectionPoint parse(IInjectionPointContext paramIInjectionPointContext, At paramAt) {
    return parse(paramIInjectionPointContext.getContext(), paramIInjectionPointContext.getMethod(), paramIInjectionPointContext.getAnnotation(), paramAt.value(), paramAt.shift(), paramAt.by(), 
        Arrays.asList(paramAt.args()), paramAt.target(), paramAt.slice(), paramAt.ordinal(), paramAt.opcode(), paramAt.id());
  }
  
  public static InjectionPoint parse(IMixinContext paramIMixinContext, MethodNode paramMethodNode, AnnotationNode paramAnnotationNode, At paramAt) {
    return parse(paramIMixinContext, paramMethodNode, paramAnnotationNode, paramAt.value(), paramAt.shift(), paramAt.by(), Arrays.asList(paramAt.args()), paramAt.target(), paramAt.slice(), paramAt
        .ordinal(), paramAt.opcode(), paramAt.id());
  }
  
  public static InjectionPoint parse(IInjectionPointContext paramIInjectionPointContext, AnnotationNode paramAnnotationNode) {
    return parse(paramIInjectionPointContext.getContext(), paramIInjectionPointContext.getMethod(), paramIInjectionPointContext.getAnnotation(), paramAnnotationNode);
  }
  
  public static InjectionPoint parse(IMixinContext paramIMixinContext, MethodNode paramMethodNode, AnnotationNode paramAnnotationNode1, AnnotationNode paramAnnotationNode2) {
    ImmutableList immutableList;
    String str1 = (String)Annotations.getValue(paramAnnotationNode2, "value");
    List list = (List)Annotations.getValue(paramAnnotationNode2, "args");
    String str2 = (String)Annotations.getValue(paramAnnotationNode2, "target", "");
    String str3 = (String)Annotations.getValue(paramAnnotationNode2, "slice", "");
    At.Shift shift = (At.Shift)Annotations.getValue(paramAnnotationNode2, "shift", At.Shift.class, At.Shift.NONE);
    int i = ((Integer)Annotations.getValue(paramAnnotationNode2, "by", Integer.valueOf(0))).intValue();
    int j = ((Integer)Annotations.getValue(paramAnnotationNode2, "ordinal", Integer.valueOf(-1))).intValue();
    int k = ((Integer)Annotations.getValue(paramAnnotationNode2, "opcode", Integer.valueOf(0))).intValue();
    String str4 = (String)Annotations.getValue(paramAnnotationNode2, "id");
    if (list == null)
      immutableList = ImmutableList.of(); 
    return parse(paramIMixinContext, paramMethodNode, paramAnnotationNode1, str1, shift, i, (List<String>)immutableList, str2, str3, j, k, str4);
  }
  
  public static InjectionPoint parse(IMixinContext paramIMixinContext, MethodNode paramMethodNode, AnnotationNode paramAnnotationNode, String paramString1, At.Shift paramShift, int paramInt1, List<String> paramList, String paramString2, String paramString3, int paramInt2, int paramInt3, String paramString4) {
    InjectionPointData injectionPointData = new InjectionPointData(paramIMixinContext, paramMethodNode, paramAnnotationNode, paramString1, paramList, paramString2, paramString3, paramInt2, paramInt3, paramString4);
    Class<? extends InjectionPoint> clazz = findClass(paramIMixinContext, injectionPointData);
    InjectionPoint injectionPoint = create(paramIMixinContext, injectionPointData, clazz);
    return shift(paramIMixinContext, paramMethodNode, paramAnnotationNode, injectionPoint, paramShift, paramInt1);
  }
  
  private static Class<? extends InjectionPoint> findClass(IMixinContext paramIMixinContext, InjectionPointData paramInjectionPointData) {
    String str = paramInjectionPointData.getType();
    Class<?> clazz = types.get(str);
    if (clazz == null)
      if (str.matches("^([A-Za-z_][A-Za-z0-9_]*\\.)+[A-Za-z_][A-Za-z0-9_]*$")) {
        try {
          clazz = Class.forName(str);
          types.put(str, clazz);
        } catch (Exception exception) {
          throw new InvalidInjectionException(paramIMixinContext, paramInjectionPointData + " could not be loaded or is not a valid InjectionPoint", exception);
        } 
      } else {
        throw new InvalidInjectionException(paramIMixinContext, paramInjectionPointData + " is not a valid injection point specifier");
      }  
    return (Class)clazz;
  }
  
  private static InjectionPoint create(IMixinContext paramIMixinContext, InjectionPointData paramInjectionPointData, Class<? extends InjectionPoint> paramClass) {
    Constructor<? extends InjectionPoint> constructor = null;
    try {
      constructor = paramClass.getDeclaredConstructor(new Class[] { InjectionPointData.class });
      constructor.setAccessible(true);
    } catch (NoSuchMethodException noSuchMethodException) {
      throw new InvalidInjectionException(paramIMixinContext, paramClass.getName() + " must contain a constructor which accepts an InjectionPointData", noSuchMethodException);
    } 
    InjectionPoint injectionPoint = null;
    try {
      injectionPoint = constructor.newInstance(new Object[] { paramInjectionPointData });
    } catch (Exception exception) {
      throw new InvalidInjectionException(paramIMixinContext, "Error whilst instancing injection point " + paramClass.getName() + " for " + paramInjectionPointData.getAt(), exception);
    } 
    return injectionPoint;
  }
  
  private static InjectionPoint shift(IMixinContext paramIMixinContext, MethodNode paramMethodNode, AnnotationNode paramAnnotationNode, InjectionPoint paramInjectionPoint, At.Shift paramShift, int paramInt) {
    if (paramInjectionPoint != null) {
      if (paramShift == At.Shift.BEFORE)
        return before(paramInjectionPoint); 
      if (paramShift == At.Shift.AFTER)
        return after(paramInjectionPoint); 
      if (paramShift == At.Shift.BY) {
        validateByValue(paramIMixinContext, paramMethodNode, paramAnnotationNode, paramInjectionPoint, paramInt);
        return shift(paramInjectionPoint, paramInt);
      } 
    } 
    return paramInjectionPoint;
  }
  
  private static void validateByValue(IMixinContext paramIMixinContext, MethodNode paramMethodNode, AnnotationNode paramAnnotationNode, InjectionPoint paramInjectionPoint, int paramInt) {
    MixinEnvironment mixinEnvironment = paramIMixinContext.getMixin().getConfig().getEnvironment();
    ShiftByViolationBehaviour shiftByViolationBehaviour = (ShiftByViolationBehaviour)mixinEnvironment.getOption(MixinEnvironment.Option.SHIFT_BY_VIOLATION_BEHAVIOUR, ShiftByViolationBehaviour.WARN);
    if (shiftByViolationBehaviour == ShiftByViolationBehaviour.IGNORE)
      return; 
    String str1 = "the maximum allowed value: ";
    String str2 = "Increase the value of maxShiftBy to suppress this warning.";
    int i = 0;
    if (paramIMixinContext instanceof MixinTargetContext)
      i = ((MixinTargetContext)paramIMixinContext).getMaxShiftByValue(); 
    if (paramInt <= i)
      return; 
    if (paramInt > 5) {
      str1 = "MAX_ALLOWED_SHIFT_BY=";
      str2 = "You must use an alternate query or a custom injection point.";
      i = 5;
    } 
    String str3 = String.format("@%s(%s) Shift.BY=%d on %s::%s exceeds %s%d. %s", new Object[] { Bytecode.getSimpleName(paramAnnotationNode), paramInjectionPoint, 
          Integer.valueOf(paramInt), paramIMixinContext, paramMethodNode.name, str1, Integer.valueOf(i), str2 });
    if (shiftByViolationBehaviour == ShiftByViolationBehaviour.WARN && i < 5) {
      LogManager.getLogger("mixin").warn(str3);
      return;
    } 
    throw new InvalidInjectionException(paramIMixinContext, str3);
  }
  
  protected String getAtCode() {
    AtCode atCode = getClass().<AtCode>getAnnotation(AtCode.class);
    return (atCode == null) ? getClass().getName() : atCode.value();
  }
  
  public static void register(Class<? extends InjectionPoint> paramClass) {
    AtCode atCode = paramClass.<AtCode>getAnnotation(AtCode.class);
    if (atCode == null)
      throw new IllegalArgumentException("Injection point class " + paramClass + " is not annotated with @AtCode"); 
    Class clazz = types.get(atCode.value());
    if (clazz != null && !clazz.equals(paramClass))
      LogManager.getLogger("mixin").debug("Overriding InjectionPoint {} with {} (previously {})", new Object[] { atCode.value(), paramClass.getName(), clazz
            .getName() }); 
    types.put(atCode.value(), paramClass);
  }
  
  public abstract boolean find(String paramString, InsnList paramInsnList, Collection<AbstractInsnNode> paramCollection);
}
