package org.spongepowered.asm.mixin.injection;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ModifyVariable {
  int ordinal() default -1;
  
  String[] name() default {};
  
  String[] method();
  
  String constraints() default "";
  
  int require() default -1;
  
  int index() default -1;
  
  int allow() default -1;
  
  boolean remap() default true;
  
  boolean print() default false;
  
  At at();
  
  boolean argsOnly() default false;
  
  Slice slice() default @Slice;
  
  int expect() default 1;
}
