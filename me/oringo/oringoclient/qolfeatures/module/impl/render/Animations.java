package me.oringo.oringoclient.qolfeatures.module.impl.render;

import java.lang.reflect.Field;
import me.oringo.oringoclient.qolfeatures.module.Module;
import me.oringo.oringoclient.qolfeatures.module.settings.Setting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.BooleanSetting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.ModeSetting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.NumberSetting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.RunnableSetting;

public class Animations extends Module {
  public NumberSetting  = new NumberSetting("X", 0.0D, -3.0D, 3.0D, 0.01D);
  
  public NumberSetting  = new NumberSetting("Reset swing", 2.0D, 0.1D, 10.0D, 0.1D);
  
  public NumberSetting  = new NumberSetting("Z", 0.0D, -3.0D, 3.0D, 0.01D);
  
  public NumberSetting  = new NumberSetting("Size", 1.0D, 0.1D, 3.0D, 0.1D);
  
  public ModeSetting  = new ModeSetting("Mode", "1.8", new String[] { 
        "1.7", "1.8", "None", "Chill", "Push", "Helicopter", "Exhibition", "Reverse", "Z axis", "Stab", 
        "Sigma" });
  
  public NumberSetting  = new NumberSetting("Pitch", 0.0D, 0.0D, 360.0D, 1.0D);
  
  public BooleanSetting  = new BooleanSetting("Ignore haste", true);
  
  public BooleanSetting  = new BooleanSetting("No equip reset", true);
  
  public NumberSetting  = new NumberSetting("Roll", 0.0D, 0.0D, 360.0D, 1.0D);
  
  public RunnableSetting  = new RunnableSetting("Reset", this::lambda$new$0);
  
  public NumberSetting  = new NumberSetting("Speed", 6.0D, 1.0D, 50.0D, 1.0D);
  
  public BooleanSetting  = new BooleanSetting("Smooth swing", false);
  
  public NumberSetting  = new NumberSetting("Yaw", 0.0D, 0.0D, 360.0D, 1.0D);
  
  public NumberSetting  = new NumberSetting("Y", 0.0D, -3.0D, 3.0D, 0.01D);
  
  public Animations() {
    super("Animations", Module.Category.);
    (new Setting[] { 
          (Setting)this., (Setting)this., (Setting)this., (Setting)this., (Setting)this., (Setting)this., (Setting)this., (Setting)this., (Setting)this., (Setting)this., 
          (Setting)this., (Setting)this., (Setting)this., (Setting)this. });
  }
  
  public static void (Object paramObject1, String paramString, Object paramObject2) {
    try {
      Field field = paramObject1.getClass().getDeclaredField(paramString);
      field.setAccessible(true);
      field.set(paramObject1, paramObject2);
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
  }
}
