package org.spongepowered.asm.launch.platform;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.Mixins;
import org.spongepowered.asm.service.MixinService;

public class MixinPlatformManager {
  private static final Logger logger = LogManager.getLogger("mixin");
  
  private final Map<URI, MixinContainer> containers = new LinkedHashMap<URI, MixinContainer>();
  
  private boolean prepared = false;
  
  private static final String MIXIN_TWEAKER_CLASS = "org.spongepowered.asm.launch.MixinTweaker";
  
  private boolean injected;
  
  private static final String DEFAULT_MAIN_CLASS = "net.minecraft.client.main.Main";
  
  private MixinContainer primaryContainer;
  
  public void init() {
    logger.debug("Initialising Mixin Platform Manager");
    URI uRI = null;
    try {
      uRI = getClass().getProtectionDomain().getCodeSource().getLocation().toURI();
      if (uRI != null) {
        logger.debug("Mixin platform: primary container is {}", new Object[] { uRI });
        this.primaryContainer = addContainer(uRI);
      } 
    } catch (URISyntaxException uRISyntaxException) {
      uRISyntaxException.printStackTrace();
    } 
    scanClasspath();
  }
  
  public Collection<String> getPhaseProviderClasses() {
    Collection<String> collection = this.primaryContainer.getPhaseProviders();
    if (collection != null)
      return Collections.unmodifiableCollection(collection); 
    return Collections.emptyList();
  }
  
  public final MixinContainer addContainer(URI paramURI) {
    MixinContainer mixinContainer1 = this.containers.get(paramURI);
    if (mixinContainer1 != null)
      return mixinContainer1; 
    logger.debug("Adding mixin platform agents for container {}", new Object[] { paramURI });
    MixinContainer mixinContainer2 = new MixinContainer(this, paramURI);
    this.containers.put(paramURI, mixinContainer2);
    if (this.prepared)
      mixinContainer2.prepare(); 
    return mixinContainer2;
  }
  
  public final void prepare(List<String> paramList) {
    this.prepared = true;
    for (MixinContainer mixinContainer : this.containers.values())
      mixinContainer.prepare(); 
    if (paramList != null) {
      parseArgs(paramList);
    } else {
      String str = System.getProperty("sun.java.command");
      if (str != null)
        parseArgs(Arrays.asList(str.split(" "))); 
    } 
  }
  
  private void parseArgs(List<String> paramList) {
    boolean bool = false;
    for (String str : paramList) {
      if (bool)
        addConfig(str); 
      bool = "--mixin".equals(str);
    } 
  }
  
  public final void inject() {
    if (this.injected)
      return; 
    this.injected = true;
    if (this.primaryContainer != null)
      this.primaryContainer.initPrimaryContainer(); 
    scanClasspath();
    logger.debug("inject() running with {} agents", new Object[] { Integer.valueOf(this.containers.size()) });
    for (MixinContainer mixinContainer : this.containers.values()) {
      try {
        mixinContainer.inject();
      } catch (Exception exception) {
        exception.printStackTrace();
      } 
    } 
  }
  
  private void scanClasspath() {
    URL[] arrayOfURL = MixinService.getService().getClassProvider().getClassPath();
    for (URL uRL : arrayOfURL) {
      try {
        URI uRI = uRL.toURI();
        if (!this.containers.containsKey(uRI)) {
          logger.debug("Scanning {} for mixin tweaker", new Object[] { uRI });
          if ("file".equals(uRI.getScheme()) && (new File(uRI)).exists()) {
            MainAttributes mainAttributes = MainAttributes.of(uRI);
            String str = mainAttributes.get("TweakClass");
            if ("org.spongepowered.asm.launch.MixinTweaker".equals(str)) {
              logger.debug("{} contains a mixin tweaker, adding agents", new Object[] { uRI });
              addContainer(uRI);
            } 
          } 
        } 
      } catch (Exception exception) {
        exception.printStackTrace();
      } 
    } 
  }
  
  public String getLaunchTarget() {
    for (MixinContainer mixinContainer : this.containers.values()) {
      String str = mixinContainer.getLaunchTarget();
      if (str != null)
        return str; 
    } 
    return "net.minecraft.client.main.Main";
  }
  
  final void setCompatibilityLevel(String paramString) {
    try {
      MixinEnvironment.CompatibilityLevel compatibilityLevel = MixinEnvironment.CompatibilityLevel.valueOf(paramString.toUpperCase());
      logger.debug("Setting mixin compatibility level: {}", new Object[] { compatibilityLevel });
      MixinEnvironment.setCompatibilityLevel(compatibilityLevel);
    } catch (IllegalArgumentException illegalArgumentException) {
      logger.warn("Invalid compatibility level specified: {}", new Object[] { paramString });
    } 
  }
  
  final void addConfig(String paramString) {
    if (paramString.endsWith(".json")) {
      logger.debug("Registering mixin config: {}", new Object[] { paramString });
      Mixins.addConfiguration(paramString);
    } else if (paramString.contains(".json@")) {
      int i = paramString.indexOf(".json@");
      String str = paramString.substring(i + 6);
      paramString = paramString.substring(0, i + 5);
      MixinEnvironment.Phase phase = MixinEnvironment.Phase.forName(str);
      if (phase != null) {
        logger.warn("Setting config phase via manifest is deprecated: {}. Specify target in config instead", new Object[] { paramString });
        logger.debug("Registering mixin config: {}", new Object[] { paramString });
        MixinEnvironment.getEnvironment(phase).addConfiguration(paramString);
      } 
    } 
  }
  
  final void addTokenProvider(String paramString) {
    if (paramString.contains("@")) {
      String[] arrayOfString = paramString.split("@", 2);
      MixinEnvironment.Phase phase = MixinEnvironment.Phase.forName(arrayOfString[1]);
      if (phase != null) {
        logger.debug("Registering token provider class: {}", new Object[] { arrayOfString[0] });
        MixinEnvironment.getEnvironment(phase).registerTokenProviderClass(arrayOfString[0]);
      } 
      return;
    } 
    MixinEnvironment.getDefaultEnvironment().registerTokenProviderClass(paramString);
  }
}
