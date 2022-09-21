package org.spongepowered.asm.mixin.injection;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Constant {
  float floatValue() default 0.0F;
  
  Class<?> classValue() default Object.class;
  
  long longValue() default 0L;
  
  String stringValue() default "";
  
  double doubleValue() default 0.0D;
  
  boolean log() default false;
  
  String slice() default "";
  
  int ordinal() default -1;
  
  boolean nullValue() default false;
  
  Condition[] expandZeroConditions() default {};
  
  int intValue() default 0;
  
  public enum Condition {
    GREATER_THAN_ZERO,
    LESS_THAN_OR_EQUAL_TO_ZERO,
    GREATER_THAN_OR_EQUAL_TO_ZERO,
    LESS_THAN_ZERO((String)new int[] { 155, 156 });
    
    private final Condition equivalence;
    
    private final int[] opcodes;
    
    static {
      GREATER_THAN_ZERO = new Condition("GREATER_THAN_ZERO", 3, LESS_THAN_OR_EQUAL_TO_ZERO);
      $VALUES = new Condition[] { LESS_THAN_ZERO, LESS_THAN_OR_EQUAL_TO_ZERO, GREATER_THAN_OR_EQUAL_TO_ZERO, GREATER_THAN_ZERO };
    }
    
    Condition(Condition param1Condition, int... param1VarArgs) {
      this.equivalence = (param1Condition != null) ? param1Condition : this;
      this.opcodes = param1VarArgs;
    }
    
    public Condition getEquivalentCondition() {
      return this.equivalence;
    }
    
    public int[] getOpcodes() {
      return this.opcodes;
    }
  }
}
