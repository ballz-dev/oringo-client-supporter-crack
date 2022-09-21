package me.oringo.oringoclient.qolfeatures.module.impl.combat;

import me.oringo.oringoclient.OringoClient;
import me.oringo.oringoclient.events.impl.PacketSentEvent;
import me.oringo.oringoclient.mixins.entity.PlayerSPAccessor;
import me.oringo.oringoclient.qolfeatures.module.Module;
import me.oringo.oringoclient.qolfeatures.module.impl.player.ArmorSwap;
import me.oringo.oringoclient.qolfeatures.module.impl.skyblock.BlazeSwapper;
import me.oringo.oringoclient.qolfeatures.module.settings.Setting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.BooleanSetting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.ModeSetting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.NumberSetting;
import me.oringo.oringoclient.utils.MilliTimer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class Criticals extends Module {
  public static NumberSetting ;
  
  public static BooleanSetting  = new BooleanSetting("Non players", false);
  
  public static boolean ;
  
  public static ModeSetting ;
  
  public static MilliTimer ;
  
  public static NumberSetting  = new NumberSetting("Delay", 500.0D, 0.0D, 2000.0D, 50.0D);
  
  public static double[] ;
  
  @SubscribeEvent
  public void (PacketSentEvent paramPacketSentEvent) {
    if (() && paramPacketSentEvent. instanceof net.minecraft.network.play.client.C0APacketAnimation) {
      Entity entity = (Entity)((KillAura. != null) ? KillAura. : ((mc.field_71476_x != null) ? mc.field_71476_x.field_72308_g : null));
      if (entity == null || !ArmorSwap.(entity) || !mc.field_71439_g.field_70122_E || ! || mc.field_71439_g.func_70090_H() || mc.field_71439_g.func_180799_ab() || mc.field_71439_g.func_70617_f_() || OringoClient..())
        return; 
      if (AutoClicker.(entity)) {
        .();
        switch (.()) {
          case "Hypixel":
            for (double d : )
              mc.func_147114_u().func_147298_b().func_179290_a((Packet)new C03PacketPlayer.C04PacketPlayerPosition(((PlayerSPAccessor)mc.field_71439_g).getLastReportedPosX(), ((PlayerSPAccessor)mc.field_71439_g).getLastReportedPosY() + d, ((PlayerSPAccessor)mc.field_71439_g).getLastReportedPosZ(), false)); 
            ((PlayerSPAccessor)mc.field_71439_g).setLastReportedPosY(((PlayerSPAccessor)mc.field_71439_g).getLastReportedPosY() + [.length - 1]);
            BlazeSwapper.("Crit");
            break;
        } 
        ((EntityLivingBase)entity).field_70737_aN = -1;
      } 
    } 
  }
  
  public Criticals() {
    super("Criticals", Module.Category.);
    (new Setting[] { (Setting), (Setting), (Setting), (Setting) });
  }
  
  @SubscribeEvent
  public void (PacketSentEvent paramPacketSentEvent) {
    if (paramPacketSentEvent. instanceof C03PacketPlayer)
       = ((C03PacketPlayer)paramPacketSentEvent.).func_149465_i(); 
  }
  
  static {
     = new NumberSetting("Hurt time", 10.0D, 0.0D, 10.0D, 1.0D);
     = new ModeSetting("Mode", "Hypixel", new String[] { "Hypixel" });
     = new double[] { 0.0625D, 0.0625D };
     = new MilliTimer();
  }
}
