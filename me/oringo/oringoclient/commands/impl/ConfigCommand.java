package me.oringo.oringoclient.commands.impl;

import me.oringo.oringoclient.commands.Command;
import me.oringo.oringoclient.config.ConfigManager;
import me.oringo.oringoclient.events.impl.MoveHeadingEvent;
import me.oringo.oringoclient.events.impl.WorldJoinEvent;
import me.oringo.oringoclient.qolfeatures.module.impl.other.Blink;
import me.oringo.oringoclient.qolfeatures.module.impl.player.NoFall;
import me.oringo.oringoclient.qolfeatures.module.impl.player.NoRotate;
import me.oringo.oringoclient.qolfeatures.module.impl.render.CustomInterfaces;
import me.oringo.oringoclient.ui.notifications.Notifications;
import net.minecraft.network.Packet;

public class ConfigCommand extends Command {
  static {
  
  }
  
  public String () {
    return "Save or load a config. .config to open explorer";
  }
  
  public ConfigCommand() {
    super("config", new String[0]);
  }
  
  public static void (Packet<?> paramPacket) {
    CustomInterfaces.(paramPacket);
  }
  
  public void (String[] paramArrayOfString) throws Exception {
    if (paramArrayOfString.length > 0) {
      String str = String.join(" ", (CharSequence[])paramArrayOfString).replaceFirst(String.valueOf((new StringBuilder()).append(paramArrayOfString[0]).append(" ")), "");
      switch (paramArrayOfString[0].toLowerCase()) {
        case "save":
          if (NoFall.(String.valueOf((new StringBuilder()).append(ConfigManager.).append(String.format("%s.json", new Object[] { str }))), true)) {
            WorldJoinEvent.(String.valueOf((new StringBuilder()).append("Successfully saved config ").append(str)), 3000);
            break;
          } 
          FireworkCommand.(String.valueOf((new StringBuilder()).append("Saving ").append(str).append(" failed")), 3000, Notifications.NotificationType.);
          break;
        case "load":
          if (Blink.(String.valueOf((new StringBuilder()).append(ConfigManager.).append(String.format("%s.json", new Object[] { str }))))) {
            NoRotate.("Oringo Client", String.valueOf((new StringBuilder()).append("Config ").append(str).append(" loaded")), 3000);
            break;
          } 
          FireworkCommand.(String.valueOf((new StringBuilder()).append("Loading config ").append(str).append(" failed")), 3000, Notifications.NotificationType.);
          break;
      } 
    } else {
      try {
        NoRotate.("Oringo Client", ".config load/save name", 3000);
        MoveHeadingEvent.();
      } catch (Exception exception) {
        exception.printStackTrace();
      } 
    } 
  }
}
