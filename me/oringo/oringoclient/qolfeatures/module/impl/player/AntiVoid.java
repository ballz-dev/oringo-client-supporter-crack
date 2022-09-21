package me.oringo.oringoclient.qolfeatures.module.impl.player;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import me.oringo.oringoclient.OringoClient;
import me.oringo.oringoclient.events.impl.MotionUpdateEvent;
import me.oringo.oringoclient.events.impl.PacketReceivedEvent;
import me.oringo.oringoclient.events.impl.PacketSentEvent;
import me.oringo.oringoclient.events.impl.WorldJoinEvent;
import me.oringo.oringoclient.mixins.entity.PlayerSPAccessor;
import me.oringo.oringoclient.qolfeatures.module.Module;
import me.oringo.oringoclient.qolfeatures.module.impl.combat.KillAura;
import me.oringo.oringoclient.qolfeatures.module.impl.combat.WTap;
import me.oringo.oringoclient.qolfeatures.module.impl.movement.Speed;
import me.oringo.oringoclient.qolfeatures.module.impl.other.NamesOnly;
import me.oringo.oringoclient.qolfeatures.module.impl.render.CustomInterfaces;
import me.oringo.oringoclient.qolfeatures.module.settings.Setting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.BooleanSetting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.ModeSetting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.NumberSetting;
import me.oringo.oringoclient.utils.MilliTimer;
import me.oringo.oringoclient.utils.Rotation;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.util.Vec3;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class AntiVoid extends Module {
  public ModeSetting  = new ModeSetting("Mode", "Blink", new String[] { "Flag", "Blink" });
  
  public Vec3  = new Vec3(0.0D, 0.0D, 0.0D);
  
  public static boolean ;
  
  public static BooleanSetting  = new BooleanSetting("Disable when flying", true);
  
  public static Queue<Packet<?>>  = new ConcurrentLinkedQueue<>();
  
  public MilliTimer  = new MilliTimer();
  
  public NumberSetting  = new NumberSetting("Fall distance", 5.0D, 0.5D, 10.0D, 0.1D);
  
  @SubscribeEvent
  public void (MotionUpdateEvent.Pre paramPre) {
    if (() && (!.() || !OringoClient..()) && this..("Flag") && mc.field_71439_g.field_70143_R > this..() && WTap.())
      paramPre.setPosition(mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u + 10.0D, mc.field_71439_g.field_70161_v); 
  }
  
  @SubscribeEvent(receiveCanceled = true)
  public void (PacketReceivedEvent paramPacketReceivedEvent) {
    if (paramPacketReceivedEvent. instanceof S08PacketPlayerPosLook && (mc.field_71439_g == null || mc.field_71439_g.func_70011_f(((S08PacketPlayerPosLook)paramPacketReceivedEvent.).func_148932_c(), mc.field_71439_g.field_70163_u, ((S08PacketPlayerPosLook)paramPacketReceivedEvent.).func_148933_e()) > 20.0D)) {
      this..();
      this. = new Vec3(((S08PacketPlayerPosLook)paramPacketReceivedEvent.).func_148932_c(), ((S08PacketPlayerPosLook)paramPacketReceivedEvent.).func_148928_d(), ((S08PacketPlayerPosLook)paramPacketReceivedEvent.).func_148933_e());
    } 
  }
  
  public AntiVoid() {
    super("Anti Void", 0, Module.Category.);
    (new Setting[] { (Setting)this., (Setting)this., (Setting) });
  }
  
  public static Rotation () {
    return new Rotation(((PlayerSPAccessor)OringoClient.mc.field_71439_g).getLastReportedYaw(), ((PlayerSPAccessor)OringoClient.mc.field_71439_g).getLastReportedPitch());
  }
  
  @SubscribeEvent(priority = EventPriority.LOWEST)
  public void (PacketSentEvent paramPacketSentEvent) {
    if (() && this..("Blink") && (!.() || !OringoClient..()) && mc.field_71439_g != null) {
      if (paramPacketSentEvent. instanceof net.minecraft.network.play.client.C03PacketPlayer)
        if (WTap.() && this..(200L)) {
           = true;
          if (mc.field_71439_g.field_70143_R > this..()) {
            while (!.isEmpty()) {
              Packet packet = .poll();
              if (!(packet instanceof net.minecraft.network.play.client.C03PacketPlayer))
                CustomInterfaces.(packet); 
            } 
            mc.field_71439_g.field_70143_R = 0.0F;
            mc.field_71439_g.func_70107_b(this..field_72450_a, this..field_72448_b, this..field_72449_c);
            mc.field_71439_g.func_70016_h(0.0D, 0.0D, 0.0D);
            if (OringoClient...())
              Speed..(); 
            KillAura..();
            paramPacketSentEvent.setCanceled(true);
            return;
          } 
        } else if (!(paramPacketSentEvent. instanceof net.minecraft.network.play.client.C01PacketChatMessage)) {
           = false;
          this. = mc.field_71439_g.func_174791_d();
          while (!.isEmpty())
            CustomInterfaces.(.poll()); 
        }  
      if () {
        .offer(paramPacketSentEvent.);
        paramPacketSentEvent.setCanceled(true);
      } 
    } else {
       = false;
      .clear();
    } 
  }
  
  public void () {
    .clear();
  }
  
  @SubscribeEvent
  public void (WorldJoinEvent paramWorldJoinEvent) {
    super.();
  }
  
  public static NamesOnly () {
    return NamesOnly.;
  }
}
