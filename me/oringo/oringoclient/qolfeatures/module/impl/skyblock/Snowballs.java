package me.oringo.oringoclient.qolfeatures.module.impl.skyblock;

import me.oringo.oringoclient.lunarspoof.LunarClient;
import me.oringo.oringoclient.qolfeatures.module.Module;
import me.oringo.oringoclient.qolfeatures.module.impl.movement.Speed;
import me.oringo.oringoclient.qolfeatures.module.impl.other.PVPInfo;
import me.oringo.oringoclient.qolfeatures.module.impl.player.AutoUHC;
import me.oringo.oringoclient.qolfeatures.module.settings.Setting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.BooleanSetting;
import net.minecraft.entity.Entity;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.util.BlockPos;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class Snowballs extends Module {
  public BooleanSetting  = new BooleanSetting("Inventory", false);
  
  public boolean ;
  
  public BooleanSetting  = new BooleanSetting("Pick up stash", true);
  
  public static boolean (char paramChar) {
    return (paramChar >= ' ');
  }
  
  public boolean () {
    return true;
  }
  
  public Snowballs() {
    super("Snowballs", Module.Category.);
    (new Setting[] { (Setting)this., (Setting)this. });
  }
  
  public static boolean () {
    return Speed.mc.field_71441_e.func_180495_p(new BlockPos((Entity)Speed.mc.field_71439_g)).func_177230_c() instanceof net.minecraft.block.BlockLiquid;
  }
  
  @SubscribeEvent
  public void (TickEvent.ClientTickEvent paramClientTickEvent) {
    if (mc.field_71462_r != null || !())
      return; 
    if (() && !this.) {
      if (this..()) {
        for (byte b = 9; b < 45; b++) {
          if (mc.field_71439_g.field_71069_bz.func_75139_a(b).func_75216_d() && (mc.field_71439_g.field_71069_bz.func_75139_a(b).func_75211_c().func_77973_b() instanceof net.minecraft.item.ItemSnowball || mc.field_71439_g.field_71069_bz.func_75139_a(b).func_75211_c().func_77973_b() instanceof net.minecraft.item.ItemEgg))
            if (b >= 36) {
              int i = mc.field_71439_g.field_71071_by.field_70461_c;
              LunarClient.(b - 36);
              for (byte b1 = 0; b1 < 16; b1++)
                mc.func_147114_u().func_147298_b().func_179290_a((Packet)new C08PacketPlayerBlockPlacement(mc.field_71439_g.func_70694_bm())); 
              LunarClient.(i);
            } else {
              AutoUHC.(b, mc.field_71439_g.field_71071_by.field_70461_c);
              for (byte b1 = 0; b1 < 16; b1++)
                mc.func_147114_u().func_147298_b().func_179290_a((Packet)new C08PacketPlayerBlockPlacement(mc.field_71439_g.func_70694_bm())); 
              AutoUHC.(b, mc.field_71439_g.field_71071_by.field_70461_c);
            }  
        } 
      } else {
        int i = mc.field_71439_g.field_71071_by.field_70461_c;
        for (byte b = 0; b < 9; b++) {
          if (mc.field_71439_g.field_71071_by.func_70301_a(b) != null && (mc.field_71439_g.field_71071_by.func_70301_a(b).func_77973_b() instanceof net.minecraft.item.ItemSnowball || mc.field_71439_g.field_71071_by.func_70301_a(b).func_77973_b() instanceof net.minecraft.item.ItemEgg)) {
            mc.field_71439_g.field_71071_by.field_70461_c = b;
            PVPInfo.();
            for (byte b1 = 0; b1 < 16; b1++)
              mc.func_147114_u().func_147298_b().func_179290_a((Packet)new C08PacketPlayerBlockPlacement(mc.field_71439_g.func_70694_bm())); 
          } 
        } 
        mc.field_71439_g.field_71071_by.field_70461_c = i;
        PVPInfo.();
      } 
      if (this..())
        mc.field_71439_g.func_71165_d("/pickupstash"); 
    } 
    this. = ();
  }
}
