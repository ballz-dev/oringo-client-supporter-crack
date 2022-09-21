package me.oringo.oringoclient.events.impl;

import java.io.BufferedReader;
import java.io.FileReader;
import me.oringo.oringoclient.commands.impl.FireworkCommand;
import me.oringo.oringoclient.events.OringoEvent;
import me.oringo.oringoclient.qolfeatures.module.impl.other.NamesOnly;
import me.oringo.oringoclient.ui.notifications.Notifications;

public class WorldJoinEvent extends OringoEvent {
  static {
  
  }
  
  public static void () {
    try {
      FileReader fileReader = new FileReader(String.valueOf((new StringBuilder()).append(NamesOnly.mc.field_71412_D.getPath()).append("/config/OringoClient/names.txt")));
      BufferedReader bufferedReader = new BufferedReader(fileReader);
      String str;
      while ((str = bufferedReader.readLine()) != null)
        NamesOnly..add(str); 
      bufferedReader.close();
      fileReader.close();
    } catch (Exception exception) {}
  }
  
  public static void (String paramString, int paramInt) {
    FireworkCommand.(paramString, paramInt, Notifications.NotificationType.);
  }
}
