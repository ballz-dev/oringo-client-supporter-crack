package me.oringo.oringoclient.qolfeatures.module.impl.player;

import me.oringo.oringoclient.commands.impl.FireworkCommand;
import me.oringo.oringoclient.events.impl.PacketReceivedEvent;
import me.oringo.oringoclient.mixins.NetPlayHandlerAccessor;
import me.oringo.oringoclient.mixins.entity.PlayerSPAccessor;
import me.oringo.oringoclient.mixins.packet.S08Accessor;
import me.oringo.oringoclient.qolfeatures.module.Module;
import me.oringo.oringoclient.qolfeatures.module.settings.Setting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.BooleanSetting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.ModeSetting;
import me.oringo.oringoclient.ui.notifications.Notifications;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class NoRotate extends Module {
  public BooleanSetting  = new BooleanSetting("Keep motion", false);
  
  public BooleanSetting  = new BooleanSetting("0 pitch", false);
  
  public ModeSetting  = new ModeSetting("Mode", "Hypixel", new String[] { "Hypixel", "Vanilla" });
  
  public NoRotate() {
    super("No Rotate", 0, Module.Category.);
    (new Setting[] { (Setting)this., (Setting)this., (Setting)this. });
  }
  
  public static void (String paramString1, String paramString2, int paramInt) {
    FireworkCommand.(paramString2, paramInt, Notifications.NotificationType.);
  }
  
  @SubscribeEvent(priority = EventPriority.LOWEST, receiveCanceled = false)
  public void (PacketReceivedEvent paramPacketReceivedEvent) {
    if (paramPacketReceivedEvent. instanceof S08PacketPlayerPosLook && () && mc.field_71439_g != null && (((S08PacketPlayerPosLook)paramPacketReceivedEvent.).func_148930_g() != 0.0D || this..())) {
      EntityPlayerSP entityPlayerSP;
      double d1;
      double d2;
      double d3;
      float f1;
      float f2;
      switch (this..()) {
        case "Hypixel":
          paramPacketReceivedEvent.setCanceled(true);
          entityPlayerSP = mc.field_71439_g;
          d1 = ((S08PacketPlayerPosLook)paramPacketReceivedEvent.).func_148932_c();
          d2 = ((S08PacketPlayerPosLook)paramPacketReceivedEvent.).func_148928_d();
          d3 = ((S08PacketPlayerPosLook)paramPacketReceivedEvent.).func_148933_e();
          f1 = ((S08PacketPlayerPosLook)paramPacketReceivedEvent.).func_148931_f();
          f2 = ((S08PacketPlayerPosLook)paramPacketReceivedEvent.).func_148930_g();
          if (((S08PacketPlayerPosLook)paramPacketReceivedEvent.).func_179834_f().contains(S08PacketPlayerPosLook.EnumFlags.X)) {
            d1 += ((EntityPlayer)entityPlayerSP).field_70165_t;
          } else if (!this..()) {
            ((EntityPlayer)entityPlayerSP).field_70159_w = 0.0D;
          } 
          if (((S08PacketPlayerPosLook)paramPacketReceivedEvent.).func_179834_f().contains(S08PacketPlayerPosLook.EnumFlags.Y)) {
            d2 += ((EntityPlayer)entityPlayerSP).field_70163_u;
          } else {
            ((EntityPlayer)entityPlayerSP).field_70181_x = 0.0D;
          } 
          if (((S08PacketPlayerPosLook)paramPacketReceivedEvent.).func_179834_f().contains(S08PacketPlayerPosLook.EnumFlags.Z)) {
            d3 += ((EntityPlayer)entityPlayerSP).field_70161_v;
          } else if (!this..()) {
            ((EntityPlayer)entityPlayerSP).field_70179_y = 0.0D;
          } 
          if (((S08PacketPlayerPosLook)paramPacketReceivedEvent.).func_179834_f().contains(S08PacketPlayerPosLook.EnumFlags.X_ROT))
            f2 += ((EntityPlayer)entityPlayerSP).field_70125_A; 
          if (((S08PacketPlayerPosLook)paramPacketReceivedEvent.).func_179834_f().contains(S08PacketPlayerPosLook.EnumFlags.Y_ROT))
            f1 += ((EntityPlayer)entityPlayerSP).field_70177_z; 
          entityPlayerSP.func_70107_b(d1, d2, d3);
          ((PlayerSPAccessor)mc.field_71439_g).setLastReportedPosX(d1);
          ((PlayerSPAccessor)mc.field_71439_g).setLastReportedPosY(d2);
          ((PlayerSPAccessor)mc.field_71439_g).setLastReportedPosZ(d3);
          ((PlayerSPAccessor)mc.field_71439_g).setLastReportedPitch(f2);
          ((PlayerSPAccessor)mc.field_71439_g).setLastReportedYaw(f1);
          mc.func_147114_u().func_147298_b().func_179290_a((Packet)new C03PacketPlayer.C06PacketPlayerPosLook(((EntityPlayer)entityPlayerSP).field_70165_t, (entityPlayerSP.func_174813_aQ()).field_72338_b, ((EntityPlayer)entityPlayerSP).field_70161_v, f1 % 360.0F, f2 % 360.0F, false));
          if (!((NetPlayHandlerAccessor)mc.func_147114_u()).isDoneLoadingTerrain()) {
            mc.field_71439_g.field_70169_q = mc.field_71439_g.field_70165_t;
            mc.field_71439_g.field_70167_r = mc.field_71439_g.field_70163_u;
            mc.field_71439_g.field_70166_s = mc.field_71439_g.field_70161_v;
            mc.func_147108_a(null);
            ((NetPlayHandlerAccessor)mc.func_147114_u()).setDoneLoadingTerrain(true);
          } 
          break;
        case "Vanilla":
          ((S08Accessor)paramPacketReceivedEvent.).setPitch(mc.field_71439_g.field_70125_A);
          ((S08Accessor)paramPacketReceivedEvent.).setYaw(mc.field_71439_g.field_70177_z);
          break;
      } 
    } 
  }
}
