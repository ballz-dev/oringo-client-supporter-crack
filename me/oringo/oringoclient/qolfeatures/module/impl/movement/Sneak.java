package me.oringo.oringoclient.qolfeatures.module.impl.movement;

import me.oringo.oringoclient.events.impl.MotionUpdateEvent;
import me.oringo.oringoclient.events.impl.MoveStateUpdateEvent;
import me.oringo.oringoclient.events.impl.PacketSentEvent;
import me.oringo.oringoclient.qolfeatures.module.Module;
import me.oringo.oringoclient.qolfeatures.module.impl.other.AntiNicker;
import me.oringo.oringoclient.qolfeatures.module.settings.Setting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.BooleanSetting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.ModeSetting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.NumberSetting;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class Sneak extends Module {
  public static ModeSetting  = new ModeSetting("Mode", "NCP", new String[] { "NCP", "Vanilla", "Fake" });
  
  public static BooleanSetting  = new BooleanSetting("Always", false);
  
  public static NumberSetting  = new NumberSetting("Speed", 1.0D, 0.0D, 1.0D, 0.1D);
  
  @SubscribeEvent
  public void (PacketSentEvent paramPacketSentEvent) {
    if (!() || mc.field_71439_g == null || !mc.field_71439_g.func_70093_af())
      return; 
    if (.("Fake") && paramPacketSentEvent. instanceof C0BPacketEntityAction && (((C0BPacketEntityAction)paramPacketSentEvent.).func_180764_b() == C0BPacketEntityAction.Action.START_SNEAKING || ((C0BPacketEntityAction)paramPacketSentEvent.).func_180764_b() == C0BPacketEntityAction.Action.STOP_SNEAKING))
      paramPacketSentEvent.setCanceled(true); 
  }
  
  public static void (String paramString) {
    try {
      (Minecraft.func_71410_x()).field_71439_g.func_145747_a((IChatComponent)new ChatComponentText(String.valueOf((new StringBuilder()).append("§bOringoClient §3» §7").append(paramString))));
    } catch (Exception exception) {}
  }
  
  public Sneak() {
    super("Sneak", Module.Category.);
    (new Setting[] { (Setting), (Setting), (Setting) });
  }
  
  @SubscribeEvent
  public void (MoveStateUpdateEvent paramMoveStateUpdateEvent) {
    if (()) {
      if (.())
        paramMoveStateUpdateEvent.setSneak(!mc.field_71474_y.field_74311_E.func_151470_d()); 
      if (paramMoveStateUpdateEvent.isSneak())
        paramMoveStateUpdateEvent.setForward((float)(paramMoveStateUpdateEvent.getForward() / 0.3D * .())).setStrafe((float)(paramMoveStateUpdateEvent.getStrafe() / 0.3D * .())); 
    } 
  }
  
  @SubscribeEvent
  public void (MotionUpdateEvent paramMotionUpdateEvent) {
    if (!() || !mc.field_71439_g.func_70093_af())
      return; 
    if (.("NCP"))
      AntiNicker.((Packet)new C0BPacketEntityAction((Entity)mc.field_71439_g, paramMotionUpdateEvent.isPre() ? C0BPacketEntityAction.Action.STOP_SNEAKING : C0BPacketEntityAction.Action.START_SNEAKING)); 
  }
}
