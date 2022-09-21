package org.spongepowered.asm.launch;

import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.launch.platform.MixinPlatformManager;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.service.MixinService;

public abstract class MixinBootstrap {
  public static final String VERSION = "0.7.11";
  
  private static boolean initState;
  
  private static MixinPlatformManager platform;
  
  private static final Logger logger = LogManager.getLogger("mixin");
  
  private static boolean initialised = false;
  
  static {
    initState = true;
    MixinService.boot();
    MixinService.getService().prepare();
  }
  
  @Deprecated
  public static void addProxy() {
    MixinService.getService().beginPhase();
  }
  
  public static MixinPlatformManager getPlatform() {
    if (platform == null) {
      MixinPlatformManager mixinPlatformManager = (MixinPlatformManager)GlobalProperties.get("mixin.platform");
      if (mixinPlatformManager instanceof MixinPlatformManager) {
        platform = mixinPlatformManager;
      } else {
        platform = new MixinPlatformManager();
        GlobalProperties.put("mixin.platform", platform);
        platform.init();
      } 
    } 
    return platform;
  }
  
  public static void init() {
    if (!start())
      return; 
    doInit(null);
  }
  
  static boolean start() {
    if (isSubsystemRegistered()) {
      if (!checkSubsystemVersion())
        throw new MixinInitialisationError("Mixin subsystem version " + getActiveSubsystemVersion() + " was already initialised. Cannot bootstrap version " + "0.7.11"); 
      return false;
    } 
    registerSubsystem("0.7.11");
    if (!initialised) {
      initialised = true;
      String str = System.getProperty("sun.java.command");
      if (str != null && str.contains("GradleStart"))
        System.setProperty("mixin.env.remapRefMap", "true"); 
      MixinEnvironment.Phase phase = MixinService.getService().getInitialPhase();
      if (phase == MixinEnvironment.Phase.DEFAULT) {
        logger.error("Initialising mixin subsystem after game pre-init phase! Some mixins may be skipped.");
        MixinEnvironment.init(phase);
        getPlatform().prepare(null);
        initState = false;
      } else {
        MixinEnvironment.init(phase);
      } 
      MixinService.getService().beginPhase();
    } 
    getPlatform();
    return true;
  }
  
  static void doInit(List<String> paramList) {
    if (!initialised) {
      if (isSubsystemRegistered()) {
        logger.warn("Multiple Mixin containers present, init suppressed for 0.7.11");
        return;
      } 
      throw new IllegalStateException("MixinBootstrap.doInit() called before MixinBootstrap.start()");
    } 
    getPlatform().getPhaseProviderClasses();
    if (initState) {
      getPlatform().prepare(paramList);
      MixinService.getService().init();
    } 
  }
  
  static void inject() {
    getPlatform().inject();
  }
  
  private static boolean isSubsystemRegistered() {
    return (GlobalProperties.get("mixin.initialised") != null);
  }
  
  private static boolean checkSubsystemVersion() {
    return "0.7.11".equals(getActiveSubsystemVersion());
  }
  
  private static Object getActiveSubsystemVersion() {
    Object object = GlobalProperties.get("mixin.initialised");
    return (object != null) ? object : "";
  }
  
  private static void registerSubsystem(String paramString) {
    GlobalProperties.put("mixin.initialised", paramString);
  }
}
