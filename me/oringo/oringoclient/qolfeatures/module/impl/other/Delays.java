package me.oringo.oringoclient.qolfeatures.module.impl.other;

import me.oringo.oringoclient.qolfeatures.module.Module;
import me.oringo.oringoclient.qolfeatures.module.impl.render.Giants;
import me.oringo.oringoclient.qolfeatures.module.settings.Setting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.NumberSetting;

public class Delays extends Module {
  public NumberSetting  = new NumberSetting("Hit Delay", 0.0D, 0.0D, 10.0D, 1.0D);
  
  public NumberSetting  = new NumberSetting("Place Delay", 0.0D, 0.0D, 6.0D, 1.0D);
  
  public static Delays  = new Delays();
  
  public NumberSetting  = new NumberSetting("Jump Delay", 0.0D, 0.0D, 10.0D, 1.0D);
  
  public Delays() {
    super("Delays", Module.Category.OTHER);
    (new Setting[] { (Setting)this., (Setting)this., (Setting)this. });
  }
  
  public static <T extends Module> boolean (Class<T> paramClass) {
    return Giants.(paramClass).();
  }
}
