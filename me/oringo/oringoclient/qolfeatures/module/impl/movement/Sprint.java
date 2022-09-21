package me.oringo.oringoclient.qolfeatures.module.impl.movement;

import com.google.gson.JsonObject;
import me.oringo.oringoclient.qolfeatures.module.Module;
import me.oringo.oringoclient.qolfeatures.module.settings.Setting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.BooleanSetting;

public class Sprint extends Module {
  public BooleanSetting  = new BooleanSetting("OmniSprint", false);
  
  public BooleanSetting  = new BooleanSetting("KeepSprint", true);
  
  public static int (JsonObject paramJsonObject) {
    try {
      return paramJsonObject.getAsJsonObject("stats").getAsJsonObject("Duels").get("sumo_duel_losses").getAsInt();
    } catch (Exception exception) {
      return 0;
    } 
  }
  
  public Sprint() {
    super("Sprint", 0, Module.Category.);
    (new Setting[] { (Setting)this., (Setting)this. });
  }
}
