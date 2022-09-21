package me.oringo.oringoclient.qolfeatures.module.impl.player;

import me.oringo.oringoclient.extend.EntityOtherPlayerMPExtend;
import me.oringo.oringoclient.qolfeatures.module.Module;
import me.oringo.oringoclient.qolfeatures.module.impl.combat.AntiBot;
import me.oringo.oringoclient.qolfeatures.module.impl.other.Disabler;
import me.oringo.oringoclient.qolfeatures.module.impl.render.PopupAnimation;
import me.oringo.oringoclient.qolfeatures.module.impl.skyblock.BoneThrower;
import me.oringo.oringoclient.qolfeatures.module.settings.Setting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.NumberSetting;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class ArmorSwap extends Module {
  public boolean ;
  
  public static NumberSetting ;
  
  public static NumberSetting  = new NumberSetting("Armor count", 1.0D, 1.0D, 4.0D, 1.0D);
  
  public boolean () {
    return true;
  }
  
  static {
     = new NumberSetting("Start index", 9.0D, 9.0D, 35.0D, 1.0D);
  }
  
  public static float () {
    return !PopupAnimation..((long)PopupAnimation..()) ? (float)(PopupAnimation..() / PopupAnimation..() * (1.0D - PopupAnimation..()) + PopupAnimation..()) : 1.0F;
  }
  
  public static boolean (Entity paramEntity) {
    if (AntiBot..() && paramEntity instanceof net.minecraft.client.entity.EntityOtherPlayerMP)
      switch (AntiBot..()) {
        case "Hypixel":
          return ((!AntiBot..() || ((EntityOtherPlayerMPExtend)paramEntity).getTabTicks() >= 150) && (!AntiBot..() || ((EntityOtherPlayerMPExtend)paramEntity).getVisibleTicks() >= 150) && (!AntiBot..() || !BoneThrower.(paramEntity)));
        case "Hypixel Team":
          if (AntiBot.mc.field_71441_e != null && AntiBot.mc.field_71441_e.func_96441_U() != null)
            for (ScorePlayerTeam scorePlayerTeam : AntiBot.mc.field_71441_e.func_96441_U().func_96525_g()) {
              if (scorePlayerTeam.func_96668_e().startsWith("§") && !scorePlayerTeam.func_96669_c().startsWith("team"))
                for (String str : scorePlayerTeam.func_96670_d()) {
                  if (str.equals(paramEntity.func_70005_c_()) && ((!AntiBot..() && scorePlayerTeam.func_98299_i() == 0) || (AntiBot..() && scorePlayerTeam.func_96661_b().isEmpty())))
                    return false; 
                }  
            }  
          return true;
      }  
    return true;
  }
  
  public ArmorSwap() {
    super("ArmorSwapper", Module.Category.);
    (new Setting[] { (Setting), (Setting) });
  }
  
  @SubscribeEvent
  public void (TickEvent.ClientTickEvent paramClientTickEvent) {
    if (() && mc.field_71439_g != null && Disabler.) {
      if (() && !this.)
        for (byte b = 0; b < .(); b++) {
          if (mc.field_71439_g.field_71069_bz.func_75139_a((int)(.() + b)).func_75216_d()) {
            ItemStack itemStack = mc.field_71439_g.field_71069_bz.func_75139_a((int)(.() + b)).func_75211_c();
            int i = -1;
            if (itemStack.func_77973_b() instanceof ItemArmor) {
              i = ((ItemArmor)itemStack.func_77973_b()).field_77881_a;
            } else if (itemStack.func_77973_b() instanceof net.minecraft.item.ItemSkull) {
              i = 0;
            } 
            if (i != -1) {
              AutoUHC.((int)(.() + b), 0);
              AutoUHC.(5 + i, 0);
              AutoUHC.((int)(.() + b), 0);
            } 
          } 
        }  
      this. = ();
    } 
  }
}
