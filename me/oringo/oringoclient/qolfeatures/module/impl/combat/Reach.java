package me.oringo.oringoclient.qolfeatures.module.impl.combat;

import me.oringo.oringoclient.qolfeatures.module.Module;
import me.oringo.oringoclient.qolfeatures.module.impl.player.AutoHeal;
import me.oringo.oringoclient.qolfeatures.module.settings.Setting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.NumberSetting;
import org.lwjgl.opengl.GL11;

public class Reach extends Module {
  public float  = 3.0F;
  
  public NumberSetting  = new NumberSetting("Min Range", 3.0D, 2.0D, 4.5D, 0.01D);
  
  public NumberSetting  = new NumberSetting("Max Range", 3.0D, 2.0D, 4.5D, 0.01D);
  
  public NumberSetting  = new NumberSetting("Block Range", 4.5D, 2.0D, 6.0D, 0.01D);
  
  public static void (double paramDouble) {
    GL11.glBegin(6);
    for (byte b = 0; b <= 90; b++) {
      double d1 = Math.sin(b * Math.PI / 45.0D) * paramDouble;
      double d2 = Math.cos(b * Math.PI / 45.0D) * paramDouble;
      GL11.glVertex2d(d1, d2);
    } 
    GL11.glEnd();
  }
  
  public Reach() {
    super("Reach", 0, Module.Category.);
    (new Setting[] { (Setting)this., (Setting)this., (Setting)this. });
  }
  
  public void () {
    this. = (float)AutoHeal.(this..(), this..());
  }
}
