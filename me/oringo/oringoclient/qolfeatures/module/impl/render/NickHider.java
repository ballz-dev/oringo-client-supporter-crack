package me.oringo.oringoclient.qolfeatures.module.impl.render;

import me.oringo.oringoclient.OringoClient;
import me.oringo.oringoclient.qolfeatures.module.Module;
import me.oringo.oringoclient.qolfeatures.module.settings.Setting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.StringSetting;
import me.oringo.oringoclient.ui.hud.DraggableComponent;
import me.oringo.oringoclient.utils.Rotation;
import net.minecraft.util.BlockPos;

public class NickHider extends Module {
  public StringSetting  = new StringSetting("Name");
  
  public static void (float paramFloat1, float paramFloat2) {
    if (!HidePlayers.())
      return; 
    OringoClient.mc.field_71439_g.field_70159_w = -Math.sin(Math.toRadians(paramFloat2)) * paramFloat1;
    OringoClient.mc.field_71439_g.field_70179_y = Math.cos(Math.toRadians(paramFloat2)) * paramFloat1;
  }
  
  public NickHider() {
    super("Nick Hider", 0, Module.Category.);
    (new Setting[] { (Setting)this. });
  }
  
  public static Rotation () {
    return new Rotation(OringoClient.mc.field_71439_g.field_70177_z, OringoClient.mc.field_71439_g.field_70125_A);
  }
  
  public static Rotation (BlockPos paramBlockPos) {
    double d1 = DraggableComponent.();
    double d2 = -Math.sin(d1) * 0.5D;
    double d3 = Math.cos(d1) * 0.5D;
    double d4 = paramBlockPos.func_177958_n() - OringoClient.mc.field_71439_g.field_70165_t - d2;
    double d5 = paramBlockPos.func_177956_o() - OringoClient.mc.field_71439_g.field_70167_r - OringoClient.mc.field_71439_g.func_70047_e();
    double d6 = paramBlockPos.func_177952_p() - OringoClient.mc.field_71439_g.field_70161_v - d3;
    double d7 = Math.hypot(d4, d6);
    float f1 = (float)(Math.atan2(d6, d4) * 180.0D / Math.PI - 90.0D);
    float f2 = (float)-(Math.atan2(d5, d7) * 180.0D / Math.PI);
    return new Rotation(f1, f2);
  }
}
