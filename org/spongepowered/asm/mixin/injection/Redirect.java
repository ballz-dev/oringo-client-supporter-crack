package org.spongepowered.asm.mixin.injection;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Redirect {
  String constraints() default "";
  
  boolean remap() default true;
  
  int expect() default 1;
  
  int require() default -1;
  
  Slice slice() default @Slice;
  
  String[] method();
  
  At at();
  
  int allow() default -1;
}
