package org.spongepowered.asm.mixin.injection;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ModifyArg {
  boolean remap() default true;
  
  int index() default -1;
  
  Slice slice() default @Slice;
  
  At at();
  
  String constraints() default "";
  
  int require() default -1;
  
  int allow() default -1;
  
  String[] method();
  
  int expect() default 1;
}
