package org.spongepowered.asm.launch.platform;

import java.io.File;
import java.net.URI;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class MixinPlatformAgentAbstract implements IMixinPlatformAgent {
  protected final MixinPlatformManager manager;
  
  protected static final Logger logger = LogManager.getLogger("mixin");
  
  protected final MainAttributes attributes;
  
  protected final URI uri;
  
  protected final File container;
  
  public MixinPlatformAgentAbstract(MixinPlatformManager paramMixinPlatformManager, URI paramURI) {
    this.manager = paramMixinPlatformManager;
    this.uri = paramURI;
    this.container = (this.uri != null) ? new File(this.uri) : null;
    this.attributes = MainAttributes.of(paramURI);
  }
  
  public String toString() {
    return String.format("PlatformAgent[%s:%s]", new Object[] { getClass().getSimpleName(), this.uri });
  }
  
  public String getPhaseProvider() {
    return null;
  }
}
