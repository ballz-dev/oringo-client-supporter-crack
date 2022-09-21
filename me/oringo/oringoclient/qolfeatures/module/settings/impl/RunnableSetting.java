package me.oringo.oringoclient.qolfeatures.module.settings.impl;

import me.oringo.oringoclient.OringoClient;
import me.oringo.oringoclient.qolfeatures.module.impl.player.InvManager;
import me.oringo.oringoclient.qolfeatures.module.settings.Setting;
import me.oringo.oringoclient.utils.Rotation;
import me.oringo.oringoclient.utils.RotationUtils;
import net.minecraft.util.AxisAlignedBB;

public class RunnableSetting extends Setting {
  public Runnable ;
  
  public void () {
    this..run();
  }
  
  public RunnableSetting(String paramString, Runnable paramRunnable) {
    super(paramString);
    this. = paramRunnable;
  }
  
  public static Rotation (AxisAlignedBB paramAxisAlignedBB) {
    return RotationUtils.(InvManager.(OringoClient.mc.field_71439_g.func_174824_e(1.0F), paramAxisAlignedBB));
  }
}
