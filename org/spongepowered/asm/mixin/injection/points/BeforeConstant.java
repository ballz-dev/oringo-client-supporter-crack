package org.spongepowered.asm.mixin.injection.points;

import com.google.common.primitives.Doubles;
import com.google.common.primitives.Floats;
import com.google.common.primitives.Ints;
import com.google.common.primitives.Longs;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.ListIterator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.lib.Type;
import org.spongepowered.asm.lib.tree.AbstractInsnNode;
import org.spongepowered.asm.lib.tree.AnnotationNode;
import org.spongepowered.asm.lib.tree.InsnList;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.InjectionPoint;
import org.spongepowered.asm.mixin.injection.InjectionPoint.AtCode;
import org.spongepowered.asm.mixin.injection.struct.InjectionPointData;
import org.spongepowered.asm.mixin.injection.throwables.InvalidInjectionException;
import org.spongepowered.asm.mixin.refmap.IMixinContext;
import org.spongepowered.asm.util.Annotations;
import org.spongepowered.asm.util.Bytecode;

@AtCode("CONSTANT")
public class BeforeConstant extends InjectionPoint {
  private final String matchByType;
  
  private final boolean log;
  
  private static final Logger logger = LogManager.getLogger("mixin");
  
  private final int ordinal;
  
  private final Float floatValue;
  
  private final Type typeValue;
  
  private final Integer intValue;
  
  private final Double doubleValue;
  
  private final Long longValue;
  
  private final boolean nullValue;
  
  private final int[] expandOpcodes;
  
  private final boolean expand;
  
  private final String stringValue;
  
  public BeforeConstant(IMixinContext paramIMixinContext, AnnotationNode paramAnnotationNode, String paramString) {
    super((String)Annotations.getValue(paramAnnotationNode, "slice", ""), InjectionPoint.Selector.DEFAULT, null);
    Boolean bool = (Boolean)Annotations.getValue(paramAnnotationNode, "nullValue", null);
    this.ordinal = ((Integer)Annotations.getValue(paramAnnotationNode, "ordinal", Integer.valueOf(-1))).intValue();
    this.nullValue = (bool != null && bool.booleanValue());
    this.intValue = (Integer)Annotations.getValue(paramAnnotationNode, "intValue", null);
    this.floatValue = (Float)Annotations.getValue(paramAnnotationNode, "floatValue", null);
    this.longValue = (Long)Annotations.getValue(paramAnnotationNode, "longValue", null);
    this.doubleValue = (Double)Annotations.getValue(paramAnnotationNode, "doubleValue", null);
    this.stringValue = (String)Annotations.getValue(paramAnnotationNode, "stringValue", null);
    this.typeValue = (Type)Annotations.getValue(paramAnnotationNode, "classValue", null);
    this.matchByType = validateDiscriminator(paramIMixinContext, paramString, bool, "on @Constant annotation");
    this.expandOpcodes = parseExpandOpcodes(Annotations.getValue(paramAnnotationNode, "expandZeroConditions", true, Constant.Condition.class));
    this.expand = (this.expandOpcodes.length > 0);
    this.log = ((Boolean)Annotations.getValue(paramAnnotationNode, "log", Boolean.FALSE)).booleanValue();
  }
  
  public BeforeConstant(InjectionPointData paramInjectionPointData) {
    super(paramInjectionPointData);
    String str1 = paramInjectionPointData.get("nullValue", null);
    Boolean bool = (str1 != null) ? Boolean.valueOf(Boolean.parseBoolean(str1)) : null;
    this.ordinal = paramInjectionPointData.getOrdinal();
    this.nullValue = (bool != null && bool.booleanValue());
    this.intValue = Ints.tryParse(paramInjectionPointData.get("intValue", ""));
    this.floatValue = Floats.tryParse(paramInjectionPointData.get("floatValue", ""));
    this.longValue = Longs.tryParse(paramInjectionPointData.get("longValue", ""));
    this.doubleValue = Doubles.tryParse(paramInjectionPointData.get("doubleValue", ""));
    this.stringValue = paramInjectionPointData.get("stringValue", null);
    String str2 = paramInjectionPointData.get("classValue", null);
    this.typeValue = (str2 != null) ? Type.getObjectType(str2.replace('.', '/')) : null;
    this.matchByType = validateDiscriminator(paramInjectionPointData.getContext(), "V", bool, "in @At(\"CONSTANT\") args");
    if ("V".equals(this.matchByType))
      throw new InvalidInjectionException(paramInjectionPointData.getContext(), "No constant discriminator could be parsed in @At(\"CONSTANT\") args"); 
    ArrayList<Constant.Condition> arrayList = new ArrayList();
    String str3 = paramInjectionPointData.get("expandZeroConditions", "").toLowerCase();
    for (Constant.Condition condition : Constant.Condition.values()) {
      if (str3.contains(condition.name().toLowerCase()))
        arrayList.add(condition); 
    } 
    this.expandOpcodes = parseExpandOpcodes(arrayList);
    this.expand = (this.expandOpcodes.length > 0);
    this.log = paramInjectionPointData.get("log", false);
  }
  
  private String validateDiscriminator(IMixinContext paramIMixinContext, String paramString1, Boolean paramBoolean, String paramString2) {
    int i = count(new Object[] { paramBoolean, this.intValue, this.floatValue, this.longValue, this.doubleValue, this.stringValue, this.typeValue });
    if (i == 1) {
      paramString1 = null;
    } else if (i > 1) {
      throw new InvalidInjectionException(paramIMixinContext, "Conflicting constant discriminators specified " + paramString2 + " for " + paramIMixinContext);
    } 
    return paramString1;
  }
  
  private int[] parseExpandOpcodes(List<Constant.Condition> paramList) {
    HashSet<Integer> hashSet = new HashSet();
    for (Constant.Condition condition1 : paramList) {
      Constant.Condition condition2 = condition1.getEquivalentCondition();
      for (int i : condition2.getOpcodes())
        hashSet.add(Integer.valueOf(i)); 
    } 
    return Ints.toArray(hashSet);
  }
  
  public boolean find(String paramString, InsnList paramInsnList, Collection<AbstractInsnNode> paramCollection) {
    boolean bool = false;
    log("BeforeConstant is searching for constants in method with descriptor {}", new Object[] { paramString });
    ListIterator<AbstractInsnNode> listIterator = paramInsnList.iterator();
    int i;
    for (byte b = 0; listIterator.hasNext(); ) {
      AbstractInsnNode abstractInsnNode = listIterator.next();
      boolean bool1 = this.expand ? matchesConditionalInsn(i, abstractInsnNode) : matchesConstantInsn(abstractInsnNode);
      if (bool1) {
        log("    BeforeConstant found a matching constant{} at ordinal {}", new Object[] { (this.matchByType != null) ? " TYPE" : " value", Integer.valueOf(b) });
        if (this.ordinal == -1 || this.ordinal == b) {
          log("      BeforeConstant found {}", new Object[] { Bytecode.describeNode(abstractInsnNode).trim() });
          paramCollection.add(abstractInsnNode);
          bool = true;
        } 
        b++;
      } 
      if (!(abstractInsnNode instanceof org.spongepowered.asm.lib.tree.LabelNode) && !(abstractInsnNode instanceof org.spongepowered.asm.lib.tree.FrameNode))
        i = abstractInsnNode.getOpcode(); 
    } 
    return bool;
  }
  
  private boolean matchesConditionalInsn(int paramInt, AbstractInsnNode paramAbstractInsnNode) {
    for (int i : this.expandOpcodes) {
      int j = paramAbstractInsnNode.getOpcode();
      if (j == i) {
        if (paramInt == 148 || paramInt == 149 || paramInt == 150 || paramInt == 151 || paramInt == 152) {
          log("  BeforeConstant is ignoring {} following {}", new Object[] { Bytecode.getOpcodeName(j), Bytecode.getOpcodeName(paramInt) });
          return false;
        } 
        log("  BeforeConstant found {} instruction", new Object[] { Bytecode.getOpcodeName(j) });
        return true;
      } 
    } 
    if (this.intValue != null && this.intValue.intValue() == 0 && Bytecode.isConstant(paramAbstractInsnNode)) {
      Object object = Bytecode.getConstant(paramAbstractInsnNode);
      log("  BeforeConstant found INTEGER constant: value = {}", new Object[] { object });
      return (object instanceof Integer && ((Integer)object).intValue() == 0);
    } 
    return false;
  }
  
  private boolean matchesConstantInsn(AbstractInsnNode paramAbstractInsnNode) {
    if (!Bytecode.isConstant(paramAbstractInsnNode))
      return false; 
    Object object = Bytecode.getConstant(paramAbstractInsnNode);
    if (object == null) {
      log("  BeforeConstant found NULL constant: nullValue = {}", new Object[] { Boolean.valueOf(this.nullValue) });
      return (this.nullValue || "Ljava/lang/Object;".equals(this.matchByType));
    } 
    if (object instanceof Integer) {
      log("  BeforeConstant found INTEGER constant: value = {}, intValue = {}", new Object[] { object, this.intValue });
      return (object.equals(this.intValue) || "I".equals(this.matchByType));
    } 
    if (object instanceof Float) {
      log("  BeforeConstant found FLOAT constant: value = {}, floatValue = {}", new Object[] { object, this.floatValue });
      return (object.equals(this.floatValue) || "F".equals(this.matchByType));
    } 
    if (object instanceof Long) {
      log("  BeforeConstant found LONG constant: value = {}, longValue = {}", new Object[] { object, this.longValue });
      return (object.equals(this.longValue) || "J".equals(this.matchByType));
    } 
    if (object instanceof Double) {
      log("  BeforeConstant found DOUBLE constant: value = {}, doubleValue = {}", new Object[] { object, this.doubleValue });
      return (object.equals(this.doubleValue) || "D".equals(this.matchByType));
    } 
    if (object instanceof String) {
      log("  BeforeConstant found STRING constant: value = {}, stringValue = {}", new Object[] { object, this.stringValue });
      return (object.equals(this.stringValue) || "Ljava/lang/String;".equals(this.matchByType));
    } 
    if (object instanceof Type) {
      log("  BeforeConstant found CLASS constant: value = {}, typeValue = {}", new Object[] { object, this.typeValue });
      return (object.equals(this.typeValue) || "Ljava/lang/Class;".equals(this.matchByType));
    } 
    return false;
  }
  
  protected void log(String paramString, Object... paramVarArgs) {
    if (this.log)
      logger.info(paramString, paramVarArgs); 
  }
  
  private static int count(Object... paramVarArgs) {
    byte b = 0;
    for (Object object : paramVarArgs) {
      if (object != null)
        b++; 
    } 
    return b;
  }
}
