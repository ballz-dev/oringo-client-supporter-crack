package org.spongepowered.asm.service;

public interface IGlobalPropertyService {
  void setProperty(String paramString, Object paramObject);
  
  <T> T getProperty(String paramString);
  
  <T> T getProperty(String paramString, T paramT);
  
  String getPropertyString(String paramString1, String paramString2);
}
