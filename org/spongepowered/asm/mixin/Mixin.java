package org.spongepowered.asm.mixin;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.CLASS)
public @interface Mixin {
  String[] targets() default {};
  
  boolean remap() default true;
  
  Class<?>[] value() default {};
  
  int priority() default 1000;
}
