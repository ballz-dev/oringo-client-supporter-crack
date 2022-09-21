package org.spongepowered.asm.launch;

import java.util.ServiceLoader;
import org.spongepowered.asm.service.IGlobalPropertyService;

public final class GlobalProperties {
  private static IGlobalPropertyService service;
  
  public static final class Keys {
    public static final String FML_CORE_MOD_MANAGER = "mixin.launch.fml.coremodmanagerclass";
    
    public static final String TRANSFORMER = "mixin.transformer";
    
    public static final String CONFIGS = "mixin.configs";
    
    public static final String INIT = "mixin.initialised";
    
    public static final String FML_LOAD_CORE_MOD = "mixin.launch.fml.loadcoremodmethod";
    
    public static final String PLATFORM_MANAGER = "mixin.platform";
    
    public static final String FML_GET_IGNORED_MODS = "mixin.launch.fml.ignoredmodsmethod";
    
    public static final String FML_GET_REPARSEABLE_COREMODS = "mixin.launch.fml.reparseablecoremodsmethod";
    
    public static final String AGENTS = "mixin.agents";
  }
  
  private static IGlobalPropertyService getService() {
    if (service == null) {
      ServiceLoader<IGlobalPropertyService> serviceLoader = ServiceLoader.load(IGlobalPropertyService.class, GlobalProperties.class.getClassLoader());
      service = serviceLoader.iterator().next();
    } 
    return service;
  }
  
  public static <T> T get(String paramString) {
    return (T)getService().getProperty(paramString);
  }
  
  public static void put(String paramString, Object paramObject) {
    getService().setProperty(paramString, paramObject);
  }
  
  public static <T> T get(String paramString, T paramT) {
    return (T)getService().getProperty(paramString, paramT);
  }
  
  public static String getString(String paramString1, String paramString2) {
    return getService().getPropertyString(paramString1, paramString2);
  }
}
