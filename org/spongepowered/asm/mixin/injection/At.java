package org.spongepowered.asm.mixin.injection;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface At {
  int opcode() default -1;
  
  String[] args() default {};
  
  String id() default "";
  
  int ordinal() default -1;
  
  String target() default "";
  
  int by() default 0;
  
  String slice() default "";
  
  Shift shift() default Shift.NONE;
  
  boolean remap() default true;
  
  String value();
  
  public enum Shift {
    BY, NONE, BEFORE, AFTER;
    
    static {
      $VALUES = new Shift[] { NONE, BEFORE, AFTER, BY };
    }
  }
}
