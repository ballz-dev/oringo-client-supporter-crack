package me.oringo.oringoclient.qolfeatures.module.impl.combat;

import me.oringo.oringoclient.qolfeatures.module.Module;
import me.oringo.oringoclient.qolfeatures.module.settings.Setting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.BooleanSetting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.ModeSetting;

public class AntiBot extends Module {
  public static BooleanSetting ;
  
  public static AntiBot ;
  
  public static ModeSetting  = new ModeSetting("Mode", "Hypixel", new String[] { "Hypixel", "Hypixel Team" });
  
  public static BooleanSetting  = new BooleanSetting("Reg name check", false, AntiBot::);
  
  public static BooleanSetting  = new BooleanSetting("Invis ticks check", true, AntiBot::);
  
  public static BooleanSetting  = new BooleanSetting("Tab ticks check", false, AntiBot::);
  
  public AntiBot() {
    super("Anti Bot", Module.Category.);
    (new Setting[] { (Setting), (Setting), (Setting), (Setting), (Setting) });
  }
  
  static {
     = new BooleanSetting("NPC check", true, AntiBot::);
  }
}
