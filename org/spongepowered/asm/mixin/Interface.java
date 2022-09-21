package org.spongepowered.asm.mixin;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.CLASS)
public @interface Interface {
  Class<?> iface();
  
  String prefix();
  
  boolean unique() default false;
  
  Remap remap() default Remap.ALL;
  
  public enum Remap {
    FORCE, ALL, ONLY_PREFIXED, NONE;
    
    private final boolean forceRemap;
    
    static {
      $VALUES = new Remap[] { ALL, FORCE, ONLY_PREFIXED, NONE };
    }
    
    Remap(boolean param1Boolean) {
      this.forceRemap = param1Boolean;
    }
    
    public boolean forceRemap() {
      return this.forceRemap;
    }
  }
}
