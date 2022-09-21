package me.oringo.oringoclient.qolfeatures.module.impl.combat;

import me.oringo.oringoclient.qolfeatures.module.Module;
import me.oringo.oringoclient.qolfeatures.module.settings.Setting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.BooleanSetting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.NumberSetting;
import net.minecraft.entity.monster.EntityZombie;

public class Hitboxes extends Module {
  public NumberSetting  = new NumberSetting("Expand", 0.5D, 0.1D, 1.0D, 0.1D);
  
  public BooleanSetting  = new BooleanSetting("Only players", false);
  
  public static boolean (EntityZombie paramEntityZombie) {
    return true;
  }
  
  public static double (double paramDouble1, double paramDouble2) {
    return Math.sqrt(paramDouble1 * paramDouble1 + paramDouble2 * paramDouble2);
  }
  
  public Hitboxes() {
    super("Hitboxes", Module.Category.);
    (new Setting[] { (Setting)this. });
  }
}
