package me.oringo.oringoclient.qolfeatures.module.impl.skyblock;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.oringo.oringoclient.events.impl.MotionUpdateEvent;
import me.oringo.oringoclient.lunarspoof.LunarClient;
import me.oringo.oringoclient.qolfeatures.module.Module;
import me.oringo.oringoclient.qolfeatures.module.impl.other.PVPInfo;
import me.oringo.oringoclient.qolfeatures.module.impl.player.AutoUHC;
import me.oringo.oringoclient.qolfeatures.module.impl.render.Gui;
import me.oringo.oringoclient.qolfeatures.module.settings.Setting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.BooleanSetting;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class BoneThrower extends Module {
  public BooleanSetting  = new BooleanSetting("Inventory", false);
  
  public boolean ;
  
  public BooleanSetting  = new BooleanSetting("Disable", true);
  
  @SubscribeEvent
  public void (MotionUpdateEvent.Post paramPost) {
    if (()) {
      if (!this. && ())
        if (!this..()) {
          int i = mc.field_71439_g.field_71071_by.field_70461_c;
          for (byte b = 0; b < 9; b++) {
            ItemStack itemStack = mc.field_71439_g.field_71071_by.func_70301_a(b);
            if (itemStack != null && itemStack.func_82833_r().contains("Bonemerang")) {
              mc.field_71439_g.field_71071_by.field_70461_c = b;
              PVPInfo.();
              mc.func_147114_u().func_147298_b().func_179290_a((Packet)new C08PacketPlayerBlockPlacement(mc.field_71439_g.func_70694_bm()));
            } 
          } 
          mc.field_71439_g.field_71071_by.field_70461_c = i;
          PVPInfo.();
        } else {
          for (byte b = 9; b < 45; b++) {
            if (mc.field_71439_g.field_71069_bz.func_75139_a(b).func_75216_d() && mc.field_71439_g.field_71069_bz.func_75139_a(b).func_75211_c().func_82833_r().contains("Bonemerang"))
              if (b >= 36) {
                int i = mc.field_71439_g.field_71071_by.field_70461_c;
                LunarClient.(b - 36);
                mc.func_147114_u().func_147298_b().func_179290_a((Packet)new C08PacketPlayerBlockPlacement(mc.field_71439_g.func_70694_bm()));
                LunarClient.(i);
              } else {
                AutoUHC.(b, mc.field_71439_g.field_71071_by.field_70461_c);
                mc.func_147114_u().func_147298_b().func_179290_a((Packet)new C08PacketPlayerBlockPlacement(mc.field_71439_g.func_70694_bm()));
                AutoUHC.(b, mc.field_71439_g.field_71071_by.field_70461_c);
              }  
          } 
        }  
      this. = ();
    } 
  }
  
  public static boolean (Entity paramEntity) {
    if (!(paramEntity instanceof net.minecraft.client.entity.EntityOtherPlayerMP))
      return false; 
    EntityLivingBase entityLivingBase = (EntityLivingBase)paramEntity;
    return (ChatFormatting.stripFormatting(paramEntity.func_145748_c_().func_150260_c()).startsWith("[NPC]") || (paramEntity.func_110124_au().version() == 2 && entityLivingBase.func_110143_aJ() == 20.0F && entityLivingBase.func_110138_aP() == 20.0F));
  }
  
  public boolean () {
    return true;
  }
  
  public static char () {
    return (Gui..().length() < 1) ? '.' : Gui..().toLowerCase().charAt(0);
  }
  
  public static double (double paramDouble1, double paramDouble2, float paramFloat) {
    return paramDouble1 + (paramDouble2 - paramDouble1) * paramFloat;
  }
  
  public BoneThrower() {
    super("Bone Thrower", Module.Category.);
    (new Setting[] { (Setting)this., (Setting)this. });
  }
}
