package me.oringo.oringoclient.commands.impl;

import java.util.Random;
import me.oringo.oringoclient.commands.Command;
import me.oringo.oringoclient.events.impl.MotionUpdateEvent;
import me.oringo.oringoclient.events.impl.PacketReceivedEvent;
import me.oringo.oringoclient.events.impl.WorldJoinEvent;
import me.oringo.oringoclient.qolfeatures.module.impl.player.AntiVoid;
import me.oringo.oringoclient.qolfeatures.module.impl.render.Giants;
import me.oringo.oringoclient.qolfeatures.module.impl.skyblock.BoneThrower;
import me.oringo.oringoclient.ui.notifications.Notifications;
import me.oringo.oringoclient.utils.MilliTimer;
import net.minecraft.client.Minecraft;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class UHCTpCommand extends Command {
  public static Vec3 ;
  
  public static int ;
  
  public static MilliTimer  = new MilliTimer();
  
  public static boolean ;
  
  public void (String[] paramArrayOfString) throws Exception {
    if () {
       = false;
      WorldJoinEvent.("Cancelled teleport!", 4000);
      return;
    } 
    if (paramArrayOfString.length == 3 || paramArrayOfString.length == 2) {
       = 0;
       = true;
      .();
       = new Vec3(Double.parseDouble(paramArrayOfString[0]), (paramArrayOfString.length == 2) ? 100.0D : Double.parseDouble(paramArrayOfString[1]), Double.parseDouble(paramArrayOfString[paramArrayOfString.length - 1]));
      mc.field_71439_g.func_71165_d("/l");
      ((AntiVoid)Giants.(AntiVoid.class)).(false);
    } else {
      FireworkCommand.(String.format("%suhctp x y z", new Object[] { Character.valueOf(BoneThrower.()) }), 4000, Notifications.NotificationType.);
    } 
  }
  
  public UHCTpCommand() {
    super("uhctp", new String[0]);
  }
  
  public String () {
    return "Teleports you in hypixel uhc";
  }
  
  @SubscribeEvent
  public void (MotionUpdateEvent.Pre paramPre) {
    if () {
      if (.(( == 2) ? 5000L : 10000L))
         = false; 
      paramPre.setPosition(.func_72441_c((new Random()).nextDouble(), (new Random()).nextDouble(), (new Random()).nextDouble()));
      mc.field_71439_g.field_70173_aa = Math.max(45, mc.field_71439_g.field_70173_aa);
    } 
  }
  
  @SubscribeEvent(receiveCanceled = true, priority = EventPriority.HIGHEST)
  public void (PacketReceivedEvent paramPacketReceivedEvent) {
    if ()
      if (paramPacketReceivedEvent. instanceof net.minecraft.network.play.server.S01PacketJoinGame) {
        switch (++) {
          case 2:
            .();
            break;
          case 1:
            mc.field_71439_g.func_71165_d("/rejoin");
            break;
        } 
      } else if (paramPacketReceivedEvent. instanceof net.minecraft.network.play.server.S08PacketPlayerPosLook &&  == 0) {
        paramPacketReceivedEvent.cancel();
      }  
  }
  
  public static boolean () {
    return ((Minecraft.func_71410_x()).field_71476_x != null && (Minecraft.func_71410_x()).field_71476_x.field_72313_a.equals(MovingObjectPosition.MovingObjectType.ENTITY));
  }
}
