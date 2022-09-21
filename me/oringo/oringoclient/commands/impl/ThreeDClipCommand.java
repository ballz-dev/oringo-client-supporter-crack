package me.oringo.oringoclient.commands.impl;

import me.oringo.oringoclient.commands.Command;
import me.oringo.oringoclient.qolfeatures.module.impl.other.KillInsults;

public class ThreeDClipCommand extends Command {
  static {
  
  }
  
  public void (String[] paramArrayOfString) throws Exception {
    double d1 = Double.parseDouble(paramArrayOfString[0]);
    double d2 = Math.toRadians(mc.field_71439_g.field_70177_z);
    double d3 = Math.toRadians(mc.field_71439_g.field_70125_A);
    mc.field_71439_g.func_70107_b(mc.field_71439_g.field_70165_t + -Math.sin(d2) * d1 * Math.cos(d3), mc.field_71439_g.field_70163_u + d1 * -Math.sin(d3), mc.field_71439_g.field_70161_v + Math.cos(d2) * d1 * Math.cos(d3));
    mc.field_71439_g.func_70016_h(0.0D, mc.field_71439_g.field_70181_x, 0.0D);
  }
  
  public ThreeDClipCommand() {
    super("3dclip", new String[0]);
  }
  
  public String () {
    return null;
  }
  
  public static void (String paramString1, String paramString2) {
    if (KillInsults..(500L) && !KillInsults..isEmpty()) {
      String str = KillInsults..get(KillInsults..nextInt(KillInsults..size()));
      if (str.contains("%s"))
        str = str.replaceAll("%s", paramString1); 
      KillInsults.mc.field_71439_g.func_71165_d(String.valueOf((new StringBuilder()).append(paramString2).append(str)));
      KillInsults..();
    } 
  }
}
