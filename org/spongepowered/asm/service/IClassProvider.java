package org.spongepowered.asm.service;

import java.net.URL;

public interface IClassProvider {
  Class<?> findClass(String paramString) throws ClassNotFoundException;
  
  Class<?> findClass(String paramString, boolean paramBoolean) throws ClassNotFoundException;
  
  URL[] getClassPath();
  
  Class<?> findAgentClass(String paramString, boolean paramBoolean) throws ClassNotFoundException;
}
