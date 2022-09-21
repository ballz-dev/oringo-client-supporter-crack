package me.oringo.oringoclient.qolfeatures.module.impl.player;

import me.oringo.oringoclient.events.impl.PacketReceivedEvent;
import me.oringo.oringoclient.mixins.packet.S12Accessor;
import me.oringo.oringoclient.mixins.packet.S27Accessor;
import me.oringo.oringoclient.qolfeatures.module.Module;
import me.oringo.oringoclient.qolfeatures.module.settings.Setting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.BooleanSetting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.NumberSetting;
import me.oringo.oringoclient.utils.SkyblockUtils;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.network.play.server.S27PacketExplosion;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class Velocity extends Module {
  public NumberSetting  = new NumberSetting("Vertical", 0.0D, -2.0D, 2.0D, 0.05D);
  
  public NumberSetting  = new NumberSetting("Horizontal", 0.0D, -2.0D, 2.0D, 0.05D);
  
  public BooleanSetting  = new BooleanSetting("Vertical Jerry-chine", false, this::lambda$new$0);
  
  public BooleanSetting  = new BooleanSetting("Only on skyblock", false);
  
  public static String[]  = new String[] { "Jerry-chine Gun", "Bonzo's Staff", "Grappling Hook", "Leaping sword", "Silk-Edge Sword", "Spring Boots", "Spider Boots", "Tarantula Boots" };
  
  public BooleanSetting  = new BooleanSetting("Skyblock kb", true);
  
  public static double (double paramDouble1, double paramDouble2, double paramDouble3) {
    return Math.max(paramDouble1, Math.min(paramDouble2, paramDouble3));
  }
  
  public boolean () {
    if (!this..())
      return false; 
    if (mc.field_71439_g.func_180799_ab() && SkyblockUtils.inDungeon)
      return true; 
    for (String str : ) {
      if ((mc.field_71439_g.func_70694_bm() != null && mc.field_71439_g.func_70694_bm().func_82833_r().contains(str)) || (mc.field_71439_g.func_82169_q(0) != null && mc.field_71439_g.func_82169_q(0).func_82833_r().contains(str)))
        return true; 
    } 
    return false;
  }
  
  @SubscribeEvent(priority = EventPriority.LOWEST)
  public void (PacketReceivedEvent paramPacketReceivedEvent) {
    if (!() || mc.field_71439_g == null || (this..() && !SkyblockUtils.onSkyblock))
      return; 
    if (paramPacketReceivedEvent. instanceof S27PacketExplosion) {
      S27PacketExplosion s27PacketExplosion = (S27PacketExplosion)paramPacketReceivedEvent.;
      if (()) {
        if (this..() && mc.field_71439_g.func_70694_bm() != null && mc.field_71439_g.func_70694_bm().func_82833_r().contains("Jerry-chine Gun")) {
          ((S27Accessor)s27PacketExplosion).setMotionX(0.0F);
          ((S27Accessor)s27PacketExplosion).setMotionZ(0.0F);
        } 
      } else {
        ((S27Accessor)s27PacketExplosion).setMotionX((float)(s27PacketExplosion.func_149149_c() * this..()));
        ((S27Accessor)s27PacketExplosion).setMotionY((float)(s27PacketExplosion.func_149144_d() * this..()));
        ((S27Accessor)s27PacketExplosion).setMotionZ((float)(s27PacketExplosion.func_149147_e() * this..()));
      } 
    } else if (paramPacketReceivedEvent. instanceof S12PacketEntityVelocity && ((S12PacketEntityVelocity)paramPacketReceivedEvent.).func_149412_c() == mc.field_71439_g.func_145782_y()) {
      S12PacketEntityVelocity s12PacketEntityVelocity = (S12PacketEntityVelocity)paramPacketReceivedEvent.;
      if (()) {
        if (this..() && mc.field_71439_g.func_70694_bm() != null && mc.field_71439_g.func_70694_bm().func_82833_r().contains("Jerry-chine Gun")) {
          ((S12Accessor)s12PacketEntityVelocity).setMotionX((int)(mc.field_71439_g.field_70159_w * 8000.0D));
          ((S12Accessor)s12PacketEntityVelocity).setMotionZ((int)(mc.field_71439_g.field_70179_y * 8000.0D));
        } 
      } else {
        ((S12Accessor)s12PacketEntityVelocity).setMotionX((this..() == 0.0D) ? (int)(mc.field_71439_g.field_70159_w * 8000.0D) : (int)(s12PacketEntityVelocity.func_149411_d() * this..()));
        ((S12Accessor)s12PacketEntityVelocity).setMotionY((this..() == 0.0D) ? (int)(mc.field_71439_g.field_70181_x * 8000.0D) : (int)(s12PacketEntityVelocity.func_149410_e() * this..()));
        ((S12Accessor)s12PacketEntityVelocity).setMotionZ((this..() == 0.0D) ? (int)(mc.field_71439_g.field_70179_y * 8000.0D) : (int)(s12PacketEntityVelocity.func_149409_f() * this..()));
      } 
    } 
  }
  
  public Velocity() {
    super("Velocity", 0, Module.Category.);
    (new Setting[] { (Setting)this., (Setting)this., (Setting)this., (Setting)this., (Setting)this. });
  }
}
