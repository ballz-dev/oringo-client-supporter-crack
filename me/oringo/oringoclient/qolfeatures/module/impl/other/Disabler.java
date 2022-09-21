package me.oringo.oringoclient.qolfeatures.module.impl.other;

import me.oringo.oringoclient.OringoClient;
import me.oringo.oringoclient.commands.impl.ConfigCommand;
import me.oringo.oringoclient.commands.impl.UHCTpCommand;
import me.oringo.oringoclient.events.impl.MotionUpdateEvent;
import me.oringo.oringoclient.events.impl.PacketSentEvent;
import me.oringo.oringoclient.mixins.MinecraftAccessor;
import me.oringo.oringoclient.qolfeatures.module.Module;
import me.oringo.oringoclient.qolfeatures.module.settings.Setting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.BooleanSetting;
import me.oringo.oringoclient.utils.MilliTimer;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.entity.Entity;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.Timer;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class Disabler extends Module {
  public static BooleanSetting ;
  
  public static boolean ;
  
  public static BooleanSetting ;
  
  public static int  = 80;
  
  static {
     = false;
     = new BooleanSetting("First2", true, Disabler::);
     = new BooleanSetting("Timer semi", false);
  }
  
  public static void (Framebuffer paramFramebuffer) {
    if (paramFramebuffer != null && paramFramebuffer.field_147624_h > -1) {
      MilliTimer.(paramFramebuffer);
      paramFramebuffer.field_147624_h = -1;
    } 
  }
  
  @SubscribeEvent
  public void (TickEvent.ClientTickEvent paramClientTickEvent) {
    if (() && mc.field_71439_g != null && mc.field_71439_g.field_70173_aa < 80) {
      KeyBinding.func_74510_a(mc.field_71474_y.field_74312_F.func_151463_i(), false);
      KeyBinding.func_74510_a(mc.field_71474_y.field_74313_G.func_151463_i(), false);
    } 
  }
  
  @SubscribeEvent(priority = EventPriority.LOWEST)
  public void (MotionUpdateEvent.Pre paramPre) {
    if (() && mc.field_71439_g.field_70173_aa == 80) {
      ConfigCommand.((Packet)new C0BPacketEntityAction((Entity)mc.field_71439_g, C0BPacketEntityAction.Action.STOP_SPRINTING));
      ConfigCommand.((Packet)new C09PacketHeldItemChange(mc.field_71439_g.field_71071_by.field_70461_c));
    } 
  }
  
  public Disabler() {
    super("Disabler", Module.Category.OTHER);
    (new Setting[] { (Setting), (Setting) });
  }
  
  @SubscribeEvent
  public void (InputEvent paramInputEvent) {
    if (() && mc.field_71439_g != null && mc.field_71439_g.field_70173_aa < 80)
      while (mc.field_71474_y.field_74312_F.func_151468_f() || mc.field_71474_y.field_74313_G.func_151468_f()); 
  }
  
  @SubscribeEvent(priority = EventPriority.HIGHEST)
  public void (PacketSentEvent paramPacketSentEvent) {
    if (()) {
      if (paramPacketSentEvent. instanceof C0BPacketEntityAction) {
        C0BPacketEntityAction c0BPacketEntityAction = (C0BPacketEntityAction)paramPacketSentEvent.;
        if (c0BPacketEntityAction.func_180764_b() == C0BPacketEntityAction.Action.START_SPRINTING || c0BPacketEntityAction.func_180764_b() == C0BPacketEntityAction.Action.STOP_SPRINTING)
          paramPacketSentEvent.setCanceled(true); 
      } 
      if (.() && paramPacketSentEvent. instanceof C03PacketPlayer) {
        C03PacketPlayer c03PacketPlayer = (C03PacketPlayer)paramPacketSentEvent.;
        if (!c03PacketPlayer.func_149466_j() && !UHCTpCommand. && !OringoClient..() && !mc.field_71439_g.func_71039_bw() && mc.field_71439_g.field_71086_bY == 0.0F)
          paramPacketSentEvent.setCanceled(true); 
      } 
    } 
  }
  
  public void () {
    if (mc.field_71439_g != null)
      mc.func_147114_u().func_147298_b().func_150718_a((IChatComponent)new ChatComponentText("Rejoin")); 
  }
  
  public static Timer () {
    return ((MinecraftAccessor)OringoClient.mc).getTimer();
  }
}
