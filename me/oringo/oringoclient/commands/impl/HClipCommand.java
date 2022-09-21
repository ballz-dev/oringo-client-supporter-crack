package me.oringo.oringoclient.commands.impl;

import me.oringo.oringoclient.commands.Command;
import me.oringo.oringoclient.qolfeatures.module.impl.skyblock.BoneThrower;
import me.oringo.oringoclient.ui.notifications.Notifications;

public class HClipCommand extends Command {
  public void (String[] paramArrayOfString) throws Exception {
    if (paramArrayOfString.length > 0) {
      double d1 = Math.toRadians(mc.field_71439_g.field_70177_z);
      double d2 = Double.parseDouble(paramArrayOfString[0]);
      mc.field_71439_g.func_70107_b(mc.field_71439_g.field_70165_t + -Math.sin(d1) * d2, mc.field_71439_g.field_70163_u, mc.field_71439_g.field_70161_v + Math.cos(d1) * d2);
      mc.field_71439_g.func_70016_h(0.0D, 0.0D, 0.0D);
    } else {
      FireworkCommand.(String.format("%shclip distance", new Object[] { Character.valueOf(BoneThrower.()) }), 2500, Notifications.NotificationType.);
    } 
  }
  
  public String () {
    return "clips you forward x blocks";
  }
  
  public HClipCommand() {
    super("hclip", new String[0]);
  }
  
  static {
  
  }
}
