package org.spongepowered.asm.service.mojang;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import net.minecraft.launchwrapper.LaunchClassLoader;

final class LaunchClassLoaderUtil {
  private final Set<String> classLoaderExceptions;
  
  private static final String TRANSFORMER_EXCEPTIONS_FIELD = "transformerExceptions";
  
  private final Set<String> invalidClasses;
  
  private final Set<String> transformerExceptions;
  
  private final LaunchClassLoader classLoader;
  
  private final Map<String, Class<?>> cachedClasses;
  
  private static final String INVALID_CLASSES_FIELD = "invalidClasses";
  
  private static final String CACHED_CLASSES_FIELD = "cachedClasses";
  
  private static final String CLASS_LOADER_EXCEPTIONS_FIELD = "classLoaderExceptions";
  
  LaunchClassLoaderUtil(LaunchClassLoader paramLaunchClassLoader) {
    this.classLoader = paramLaunchClassLoader;
    this.cachedClasses = getField(paramLaunchClassLoader, "cachedClasses");
    this.invalidClasses = getField(paramLaunchClassLoader, "invalidClasses");
    this.classLoaderExceptions = getField(paramLaunchClassLoader, "classLoaderExceptions");
    this.transformerExceptions = getField(paramLaunchClassLoader, "transformerExceptions");
  }
  
  LaunchClassLoader getClassLoader() {
    return this.classLoader;
  }
  
  boolean isClassLoaded(String paramString) {
    return this.cachedClasses.containsKey(paramString);
  }
  
  boolean isClassExcluded(String paramString1, String paramString2) {
    return (isClassClassLoaderExcluded(paramString1, paramString2) || isClassTransformerExcluded(paramString1, paramString2));
  }
  
  boolean isClassClassLoaderExcluded(String paramString1, String paramString2) {
    for (String str : getClassLoaderExceptions()) {
      if ((paramString2 != null && paramString2.startsWith(str)) || paramString1.startsWith(str))
        return true; 
    } 
    return false;
  }
  
  boolean isClassTransformerExcluded(String paramString1, String paramString2) {
    for (String str : getTransformerExceptions()) {
      if ((paramString2 != null && paramString2.startsWith(str)) || paramString1.startsWith(str))
        return true; 
    } 
    return false;
  }
  
  void registerInvalidClass(String paramString) {
    if (this.invalidClasses != null)
      this.invalidClasses.add(paramString); 
  }
  
  Set<String> getClassLoaderExceptions() {
    if (this.classLoaderExceptions != null)
      return this.classLoaderExceptions; 
    return Collections.emptySet();
  }
  
  Set<String> getTransformerExceptions() {
    if (this.transformerExceptions != null)
      return this.transformerExceptions; 
    return Collections.emptySet();
  }
  
  private static <T> T getField(LaunchClassLoader paramLaunchClassLoader, String paramString) {
    try {
      Field field = LaunchClassLoader.class.getDeclaredField(paramString);
      field.setAccessible(true);
      return (T)field.get(paramLaunchClassLoader);
    } catch (Exception exception) {
      exception.printStackTrace();
      return null;
    } 
  }
}
