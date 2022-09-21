package org.spongepowered.asm.launch.platform;

import java.net.URI;

public class MixinPlatformAgentDefault extends MixinPlatformAgentAbstract {
  public MixinPlatformAgentDefault(MixinPlatformManager paramMixinPlatformManager, URI paramURI) {
    super(paramMixinPlatformManager, paramURI);
  }
  
  public void prepare() {
    String str1 = this.attributes.get("MixinCompatibilityLevel");
    if (str1 != null)
      this.manager.setCompatibilityLevel(str1); 
    String str2 = this.attributes.get("MixinConfigs");
    if (str2 != null)
      for (String str : str2.split(","))
        this.manager.addConfig(str.trim());  
    String str3 = this.attributes.get("MixinTokenProviders");
    if (str3 != null)
      for (String str : str3.split(","))
        this.manager.addTokenProvider(str.trim());  
  }
  
  public void initPrimaryContainer() {}
  
  public void inject() {}
  
  public String getLaunchTarget() {
    return this.attributes.get("Main-Class");
  }
}
