package me.oringo.oringoclient.qolfeatures.module.impl.skyblock;

import java.util.HashMap;
import me.oringo.oringoclient.OringoClient;
import me.oringo.oringoclient.commands.CommandHandler;
import me.oringo.oringoclient.events.impl.MotionUpdateEvent;
import me.oringo.oringoclient.qolfeatures.module.Module;
import me.oringo.oringoclient.qolfeatures.module.impl.movement.Speed;
import me.oringo.oringoclient.qolfeatures.module.impl.other.Timer;
import me.oringo.oringoclient.qolfeatures.module.settings.Setting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.NumberSetting;
import me.oringo.oringoclient.utils.OringoPacketLog;
import me.oringo.oringoclient.utils.SkyblockUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class AutoQuiz extends Module {
  public static NumberSetting  = new NumberSetting("Distance", 64.0D, 1.0D, 64.0D, 0.1D);
  
  public static HashMap<String, String[]>  = new HashMap<String, String[]>() {
      static {
      
      }
    };
  
  public static String[] ;
  
  public static Module (String paramString) {
    for (Module module : OringoClient.) {
      if (module.().equalsIgnoreCase(paramString))
        return module; 
    } 
    return null;
  }
  
  @SubscribeEvent(receiveCanceled = true)
  public void (ClientChatReceivedEvent paramClientChatReceivedEvent) {
    if (paramClientChatReceivedEvent.type == 2 || !SkyblockUtils.inDungeon)
      return; 
    String str = paramClientChatReceivedEvent.message.func_150260_c().trim();
    if (str.startsWith("[STATUE] Oruo the Omniscient: ") && str.endsWith("correctly!")) {
       = null;
    } else if (str.equals("What Skyblock year is it?")) {
       = new String[] { String.valueOf((new StringBuilder()).append("Year ").append(CommandHandler.())) };
    } else {
      String[] arrayOfString = .get(str);
      if (arrayOfString != null)
         = arrayOfString; 
    } 
  }
  
  public AutoQuiz() {
    super("Auto Quiz", Module.Category.);
    (new Setting[] { (Setting) });
  }
  
  public static float () {
    return Speed.(0.42F);
  }
  
  public static void (String paramString) {
    OringoPacketLog..setText(String.valueOf((new StringBuilder()).append(OringoPacketLog..getText()).append(paramString).append("\n")));
    if (OringoPacketLog..getText().length() - OringoPacketLog..getText().replaceAll("\n", "").length() > 20)
      OringoPacketLog..setText(OringoPacketLog..getText().substring(OringoPacketLog..getText().indexOf("\n") + 1)); 
  }
  
  @SubscribeEvent
  public void (MotionUpdateEvent.Post paramPost) {
    if (() && mc.field_71439_g.field_70173_aa % 5 == 0 &&  != null)
      for (EntityArmorStand entityArmorStand : mc.field_71441_e.func_175644_a(EntityArmorStand.class, Timer::))
        mc.func_147114_u().func_147298_b().func_179290_a((Packet)new C02PacketUseEntity((Entity)entityArmorStand, C02PacketUseEntity.Action.INTERACT));  
  }
}
