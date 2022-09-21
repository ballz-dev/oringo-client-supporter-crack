package org.spongepowered.asm.mixin.refmap;

public interface IReferenceMapper {
  boolean isDefault();
  
  String getStatus();
  
  String remap(String paramString1, String paramString2);
  
  void setContext(String paramString);
  
  String remapWithContext(String paramString1, String paramString2, String paramString3);
  
  String getResourceName();
  
  String getContext();
}
