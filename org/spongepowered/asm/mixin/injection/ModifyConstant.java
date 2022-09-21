package org.spongepowered.asm.mixin.injection;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ModifyConstant {
  boolean remap() default true;
  
  int expect() default 1;
  
  Constant[] constant() default {};
  
  int allow() default -1;
  
  String constraints() default "";
  
  Slice[] slice() default {};
  
  String[] method();
  
  int require() default -1;
}
