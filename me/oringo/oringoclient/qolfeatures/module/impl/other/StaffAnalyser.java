package me.oringo.oringoclient.qolfeatures.module.impl.other;

import me.oringo.oringoclient.OringoClient;
import me.oringo.oringoclient.commands.impl.FireworkCommand;
import me.oringo.oringoclient.events.impl.MoveFlyingEvent;
import me.oringo.oringoclient.qolfeatures.module.Module;
import me.oringo.oringoclient.qolfeatures.module.settings.Setting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.NumberSetting;
import me.oringo.oringoclient.ui.notifications.Notifications;
import me.oringo.oringoclient.utils.MilliTimer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class StaffAnalyser extends Module {
  public int  = -1;
  
  public NumberSetting  = new NumberSetting("Delay", 5.0D, 5.0D, 60.0D, 1.0D);
  
  public MilliTimer  = new MilliTimer();
  
  public static boolean () {
    return (OringoClient.mc.field_71439_g.field_70159_w != 0.0D && OringoClient.mc.field_71439_g.field_70179_y != 0.0D && OringoClient.mc.field_71439_g.field_70181_x != 0.0D);
  }
  
  public StaffAnalyser() {
    super("Staff Analyser", Module.Category.OTHER);
    (new Setting[] { (Setting)this. });
  }
  
  @SubscribeEvent
  public void (TickEvent.ClientTickEvent paramClientTickEvent) {
    if (() && this..((long)(this..() * 1000.0D))) {
      this..();
      (new Thread(this::lambda$onTick$0)).start();
    } 
  }
}
