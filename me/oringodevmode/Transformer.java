package me.oringodevmode;

import java.util.Map;
import me.oringo.oringoclient.utils.TestUtils;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin.MCVersion;
import org.spongepowered.asm.launch.MixinBootstrap;

@MCVersion("1.8.9")
public class Transformer implements IFMLLoadingPlugin {
  static {
  
  }
  
  public Transformer() {
    MixinBootstrap.init();
  }
  
  public String getSetupClass() {
    return null;
  }
  
  public String[] getASMTransformerClass() {
    return new String[] { OringoTransformer.class.getName(), TestUtils.class.getName() };
  }
  
  public String getAccessTransformerClass() {
    return null;
  }
  
  public void injectData(Map<String, Object> paramMap) {}
  
  public String getModContainerClass() {
    return null;
  }
}
