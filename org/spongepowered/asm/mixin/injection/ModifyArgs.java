package org.spongepowered.asm.mixin.injection;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ModifyArgs {
  At at();
  
  int require() default -1;
  
  Slice slice() default @Slice;
  
  int expect() default 1;
  
  String[] method();
  
  String constraints() default "";
  
  boolean remap() default true;
  
  int allow() default -1;
}
