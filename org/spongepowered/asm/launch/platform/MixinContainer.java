package org.spongepowered.asm.launch.platform;

import java.lang.reflect.Constructor;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.launch.GlobalProperties;
import org.spongepowered.asm.service.MixinService;

public class MixinContainer {
  private static final List<String> agentClasses = new ArrayList<String>();
  
  static {
    GlobalProperties.put("mixin.agents", agentClasses);
    for (String str : MixinService.getService().getPlatformAgents())
      agentClasses.add(str); 
    agentClasses.add("org.spongepowered.asm.launch.platform.MixinPlatformAgentDefault");
  }
  
  private final Logger logger = LogManager.getLogger("mixin");
  
  private final List<IMixinPlatformAgent> agents = new ArrayList<IMixinPlatformAgent>();
  
  private final URI uri;
  
  public MixinContainer(MixinPlatformManager paramMixinPlatformManager, URI paramURI) {
    this.uri = paramURI;
    for (String str : agentClasses) {
      try {
        Class<?> clazz = Class.forName(str);
        Constructor<?> constructor = clazz.getDeclaredConstructor(new Class[] { MixinPlatformManager.class, URI.class });
        this.logger.debug("Instancing new {} for {}", new Object[] { clazz.getSimpleName(), this.uri });
        IMixinPlatformAgent iMixinPlatformAgent = (IMixinPlatformAgent)constructor.newInstance(new Object[] { paramMixinPlatformManager, paramURI });
        this.agents.add(iMixinPlatformAgent);
      } catch (Exception exception) {
        this.logger.catching(exception);
      } 
    } 
  }
  
  public URI getURI() {
    return this.uri;
  }
  
  public Collection<String> getPhaseProviders() {
    ArrayList<String> arrayList = new ArrayList();
    for (IMixinPlatformAgent iMixinPlatformAgent : this.agents) {
      String str = iMixinPlatformAgent.getPhaseProvider();
      if (str != null)
        arrayList.add(str); 
    } 
    return arrayList;
  }
  
  public void prepare() {
    for (IMixinPlatformAgent iMixinPlatformAgent : this.agents) {
      this.logger.debug("Processing prepare() for {}", new Object[] { iMixinPlatformAgent });
      iMixinPlatformAgent.prepare();
    } 
  }
  
  public void initPrimaryContainer() {
    for (IMixinPlatformAgent iMixinPlatformAgent : this.agents) {
      this.logger.debug("Processing launch tasks for {}", new Object[] { iMixinPlatformAgent });
      iMixinPlatformAgent.initPrimaryContainer();
    } 
  }
  
  public void inject() {
    for (IMixinPlatformAgent iMixinPlatformAgent : this.agents) {
      this.logger.debug("Processing inject() for {}", new Object[] { iMixinPlatformAgent });
      iMixinPlatformAgent.inject();
    } 
  }
  
  public String getLaunchTarget() {
    for (IMixinPlatformAgent iMixinPlatformAgent : this.agents) {
      String str = iMixinPlatformAgent.getLaunchTarget();
      if (str != null)
        return str; 
    } 
    return null;
  }
}
