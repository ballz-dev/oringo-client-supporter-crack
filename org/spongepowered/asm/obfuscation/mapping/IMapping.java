package org.spongepowered.asm.obfuscation.mapping;

public interface IMapping<TMapping> {
  TMapping transform(String paramString);
  
  TMapping getSuper();
  
  String getName();
  
  String serialise();
  
  String getSimpleName();
  
  TMapping copy();
  
  TMapping move(String paramString);
  
  TMapping remap(String paramString);
  
  String getDesc();
  
  String getOwner();
  
  Type getType();
  
  public enum Type {
    PACKAGE, CLASS, FIELD, METHOD;
    
    static {
      PACKAGE = new Type("PACKAGE", 3);
      $VALUES = new Type[] { FIELD, METHOD, CLASS, PACKAGE };
    }
  }
}
