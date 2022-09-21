package me.oringo.oringoclient.qolfeatures.module.impl.render;

import me.oringo.oringoclient.OringoClient;
import me.oringo.oringoclient.qolfeatures.module.Module;
import me.oringo.oringoclient.qolfeatures.module.settings.Setting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.ModeSetting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.NumberSetting;
import net.minecraft.entity.Entity;

public class EnchantGlint extends Module {
  public NumberSetting  = new NumberSetting("Blue", 255.0D, 0.0D, 255.0D, 1.0D, this::lambda$new$2);
  
  public NumberSetting  = new NumberSetting("Red", 0.0D, 0.0D, 255.0D, 1.0D, this::lambda$new$0);
  
  public NumberSetting  = new NumberSetting("Green", 80.0D, 0.0D, 255.0D, 1.0D, this::lambda$new$1);
  
  public NumberSetting  = new NumberSetting("Rainbow speed", 5.0D, 0.1D, 10.0D, 0.1D, this::lambda$new$4);
  
  public NumberSetting  = new NumberSetting("Speed", 100.0D, 0.0D, 200.0D, 1.0D);
  
  public ModeSetting  = new ModeSetting("Color Mode", "RGBA", new String[] { "RGBA", "Rainbow", "Theme" });
  
  public NumberSetting  = new NumberSetting("Alpha", 255.0D, 0.0D, 255.0D, 1.0D, this::lambda$new$3);
  
  public static boolean (double paramDouble) {
    return !OringoClient.mc.field_71441_e.func_72945_a((Entity)OringoClient.mc.field_71439_g, OringoClient.mc.field_71439_g.func_174813_aQ().func_72317_d(0.0D, -paramDouble, 0.0D)).isEmpty();
  }
  
  public EnchantGlint() {
    super("Enchant Glint", Module.Category.);
    (new Setting[] { (Setting)this., (Setting)this., (Setting)this., (Setting)this., (Setting)this., (Setting)this., (Setting)this. });
  }
}
