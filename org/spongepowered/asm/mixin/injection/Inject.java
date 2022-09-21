package org.spongepowered.asm.mixin.injection;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Inject {
  int allow() default -1;
  
  boolean cancellable() default false;
  
  boolean remap() default true;
  
  At[] at();
  
  String constraints() default "";
  
  int expect() default 1;
  
  int require() default -1;
  
  LocalCapture locals() default LocalCapture.NO_CAPTURE;
  
  String id() default "";
  
  String[] method();
  
  Slice[] slice() default {};
}
