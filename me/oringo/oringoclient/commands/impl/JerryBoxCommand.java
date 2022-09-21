package me.oringo.oringoclient.commands.impl;

import java.util.Comparator;
import java.util.List;
import me.oringo.oringoclient.commands.Command;
import me.oringo.oringoclient.events.impl.MotionUpdateEvent;
import me.oringo.oringoclient.events.impl.MoveStateUpdateEvent;
import me.oringo.oringoclient.events.impl.PacketReceivedEvent;
import me.oringo.oringoclient.events.impl.WorldJoinEvent;
import me.oringo.oringoclient.qolfeatures.LoginWithSession;
import me.oringo.oringoclient.qolfeatures.module.impl.combat.KillAura;
import me.oringo.oringoclient.qolfeatures.module.impl.player.AutoTool;
import me.oringo.oringoclient.qolfeatures.module.impl.skyblock.BoneThrower;
import me.oringo.oringoclient.ui.notifications.Notifications;
import me.oringo.oringoclient.utils.MilliTimer;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.event.ClickEvent;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C0DPacketCloseWindow;
import net.minecraft.network.play.server.S2DPacketOpenWindow;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.IChatComponent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class JerryBoxCommand extends Command {
  public static boolean ;
  
  public static MilliTimer  = new MilliTimer();
  
  public static double (double paramDouble1, double paramDouble2, float paramFloat) {
    return paramDouble1 + (paramDouble2 - paramDouble1) * paramFloat;
  }
  
  public JerryBoxCommand() {
    super("jerrybox", new String[0]);
  }
  
  public String () {
    return "Opens jerry boxes";
  }
  
  public void () {
    int i = AutoTool.(JerryBoxCommand::);
    if (i != -1) {
      if (i - 36 != mc.field_71439_g.field_71071_by.field_70461_c)
        mc.field_71442_b.func_78753_a(mc.field_71439_g.field_71069_bz.field_75152_c, i, mc.field_71439_g.field_71071_by.field_70461_c, 2, (EntityPlayer)mc.field_71439_g); 
      mc.func_147114_u().func_147297_a((Packet)new C08PacketPlayerBlockPlacement(null));
      .();
    } else {
      FireworkCommand.("Unable to find a Jerry Box!", 5000, Notifications.NotificationType.);
       = false;
    } 
  }
  
  @SubscribeEvent
  public void (MotionUpdateEvent.Pre paramPre) {
    if (!)
      return; 
    if (.(1000L) && (mc.field_71439_g.func_70694_bm() == null || !mc.field_71439_g.func_70694_bm().func_82833_r().contains("Jerry Box")))
      (); 
  }
  
  public static void (Entity paramEntity) {
    ChatComponentText chatComponentText = new ChatComponentText(String.valueOf((new StringBuilder()).append("Name: ").append(paramEntity.func_145748_c_().func_150254_d())));
    ChatStyle chatStyle = new ChatStyle();
    chatStyle.func_150241_a(new ClickEvent(ClickEvent.Action.RUN_COMMAND, String.format("%sarmorstands %s", new Object[] { Character.valueOf(BoneThrower.()), Integer.valueOf(paramEntity.func_145782_y()) })));
    chatComponentText.func_150255_a(chatStyle);
    (Minecraft.func_71410_x()).field_71439_g.func_145747_a((IChatComponent)chatComponentText);
  }
  
  @SubscribeEvent
  public void (PacketReceivedEvent paramPacketReceivedEvent) {
    if (!)
      return; 
    if (paramPacketReceivedEvent. instanceof S2DPacketOpenWindow && ((S2DPacketOpenWindow)paramPacketReceivedEvent.).func_179840_c().func_150254_d().contains("Jerry Box")) {
      paramPacketReceivedEvent.cancel();
      mc.field_71442_b.func_78753_a(((S2DPacketOpenWindow)paramPacketReceivedEvent.).func_148901_c(), 22, 0, 3, (EntityPlayer)mc.field_71439_g);
      mc.func_147114_u().func_147297_a((Packet)new C0DPacketCloseWindow(((S2DPacketOpenWindow)paramPacketReceivedEvent.).func_148901_c()));
      if (mc.field_71439_g.func_70694_bm() != null && mc.field_71439_g.func_70694_bm().func_82833_r().contains("Jerry Box")) {
        .();
        mc.func_147114_u().func_147297_a((Packet)new C08PacketPlayerBlockPlacement(null));
      } 
    } 
  }
  
  public void (String[] paramArrayOfString) throws Exception {
     = !;
    WorldJoinEvent.( ? "Started opening!" : "Stopped opening!", 4000);
    if ()
      (); 
  }
  
  @SubscribeEvent
  public void (MoveStateUpdateEvent paramMoveStateUpdateEvent) {
    if ()
      paramMoveStateUpdateEvent.setStrafe(0.0F).setForward(0.0F).setJump(false).setSneak(false); 
  }
  
  public static List<EntityLivingBase> () {
    List<EntityLivingBase> list = KillAura.mc.field_71441_e.func_175644_a(EntityLivingBase.class, LoginWithSession::);
    list.sort(Comparator.comparingDouble(KillAura::));
    switch (KillAura..()) {
      case "Health":
        list.sort(Comparator.comparingDouble(EntityLivingBase::func_110143_aJ));
        break;
      case "Hurt":
        list.sort(Comparator.comparing(KillAura::));
        break;
      case "Hp reverse":
        list.sort(Comparator.comparingDouble(KillAura::));
        break;
    } 
    return list;
  }
}
