package me.oringo.oringoclient.qolfeatures.module.impl.render;

import me.oringo.oringoclient.OringoClient;
import me.oringo.oringoclient.qolfeatures.module.Module;
import net.minecraft.entity.Entity;

public class NoRender extends Module {
  public NoRender() {
    super("No Render", Module.Category.);
  }
  
  public static boolean (double paramDouble) {
    return !OringoClient.mc.field_71441_e.func_72945_a((Entity)OringoClient.mc.field_71439_g, OringoClient.mc.field_71439_g.func_174813_aQ().func_72317_d(0.0D, -paramDouble, 0.0D)).isEmpty();
  }
  
  static {
  
  }
}
