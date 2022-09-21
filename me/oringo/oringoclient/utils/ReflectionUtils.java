package me.oringo.oringoclient.utils;

import me.oringo.oringoclient.OringoClient;
import me.oringo.oringoclient.commands.impl.JerryBoxCommand;
import me.oringo.oringoclient.qolfeatures.module.impl.skyblock.DojoHelper;
import net.minecraft.util.Vec3;

public class ReflectionUtils {
  static {
  
  }
  
  public static boolean () {
    return ServerUtils.;
  }
  
  public static Vec3 (float paramFloat) {
    return new Vec3(JerryBoxCommand.(OringoClient.mc.field_71439_g.field_70169_q, OringoClient.mc.field_71439_g.field_70165_t, paramFloat), JerryBoxCommand.(OringoClient.mc.field_71439_g.field_70167_r, OringoClient.mc.field_71439_g.field_70163_u, paramFloat) + 0.1D, JerryBoxCommand.(OringoClient.mc.field_71439_g.field_70166_s, OringoClient.mc.field_71439_g.field_70161_v, paramFloat));
  }
  
  public static boolean (String paramString) {
    return DojoHelper..("Red") ? paramString.startsWith("§c§l") : (DojoHelper..("Green") ? paramString.startsWith("§a§l") : (DojoHelper..("Yellow") ? paramString.startsWith("§e§l") : false));
  }
}
