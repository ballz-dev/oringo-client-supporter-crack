package me.oringo.oringoclient.qolfeatures.module.impl.player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import me.oringo.oringoclient.OringoClient;
import me.oringo.oringoclient.events.impl.MotionUpdateEvent;
import me.oringo.oringoclient.lunarspoof.LunarClient;
import me.oringo.oringoclient.qolfeatures.module.Module;
import me.oringo.oringoclient.qolfeatures.module.impl.combat.KillAura;
import me.oringo.oringoclient.qolfeatures.module.settings.Setting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.BooleanSetting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.NumberSetting;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C0EPacketClickWindow;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class AutoPot extends Module {
  public BooleanSetting  = new BooleanSetting("Ground only", true);
  
  public NumberSetting  = new NumberSetting("Delay", 1000.0D, 250.0D, 2500.0D, 1.0D);
  
  public List<Integer>  = new ArrayList<>();
  
  public HashMap<Potion, Long>  = new HashMap<>();
  
  public BooleanSetting  = new BooleanSetting("From inventory", false);
  
  public NumberSetting  = new NumberSetting("Health", 15.0D, 1.0D, 20.0D, 1.0D);
  
  @SubscribeEvent(priority = EventPriority.LOW)
  public void (MotionUpdateEvent.Pre paramPre) {
    this..clear();
    if (!() || (!mc.field_71439_g.field_70122_E && this..()) || mc.field_71439_g.field_70173_aa <= 100)
      return; 
    if (this..() && mc.field_71439_g.field_71070_bA.field_75152_c == mc.field_71439_g.field_71069_bz.field_75152_c) {
      for (byte b = 9; b < 45; b++) {
        if (mc.field_71439_g.field_71069_bz.func_75139_a(b).func_75216_d()) {
          ItemStack itemStack = mc.field_71439_g.field_71069_bz.func_75139_a(b).func_75211_c();
          if (itemStack.func_77973_b() instanceof ItemPotion && ItemPotion.func_77831_g(itemStack.func_77960_j()))
            for (PotionEffect potionEffect : ((ItemPotion)itemStack.func_77973_b()).func_77832_l(itemStack)) {
              Potion potion = Potion.field_76425_a[potionEffect.func_76456_a()];
              if ((potionEffect, itemStack) && !potion.func_76398_f() && !(potion) && (!mc.field_71439_g.func_70644_a(potion) || potionEffect.func_76458_c() > mc.field_71439_g.func_70660_b(potion).func_76458_c())) {
                if (potion == Potion.field_76432_h || potion == Potion.field_76428_l) {
                  if (mc.field_71439_g.func_110143_aJ() <= this..()) {
                    (potion);
                    this..add(Integer.valueOf(b));
                    paramPre. = 87.9F;
                    break;
                  } 
                  continue;
                } 
                (potion);
                this..add(Integer.valueOf(b));
                paramPre. = 87.9F;
                break;
              } 
            }  
        } 
      } 
    } else {
      for (byte b = 0; b < 9; b++) {
        if (mc.field_71439_g.field_71071_by.func_70301_a(b) != null) {
          ItemStack itemStack = mc.field_71439_g.field_71071_by.func_70301_a(b);
          if (itemStack.func_77973_b() instanceof ItemPotion && ItemPotion.func_77831_g(itemStack.func_77960_j()))
            for (PotionEffect potionEffect : ((ItemPotion)itemStack.func_77973_b()).func_77832_l(itemStack)) {
              Potion potion = Potion.field_76425_a[potionEffect.func_76456_a()];
              if ((potionEffect, itemStack) && !potion.func_76398_f() && !(potion) && (!mc.field_71439_g.func_70644_a(potion) || potionEffect.func_76458_c() > mc.field_71439_g.func_70660_b(potion).func_76458_c())) {
                if (potion == Potion.field_76432_h || potion == Potion.field_76428_l) {
                  if (mc.field_71439_g.func_110143_aJ() <= this..()) {
                    (potion);
                    this..add(Integer.valueOf(36 + b));
                    paramPre. = 87.9F;
                    break;
                  } 
                  continue;
                } 
                (potion);
                this..add(Integer.valueOf(36 + b));
                paramPre. = 87.9F;
                break;
              } 
            }  
        } 
      } 
    } 
  }
  
  public boolean (Potion paramPotion) {
    return (this..containsKey(paramPotion) && (System.currentTimeMillis() - ((Long)this..get(paramPotion)).longValue()) < this..());
  }
  
  public AutoPot() {
    super("Auto Pot", Module.Category.);
    (new Setting[] { (Setting)this., (Setting)this. });
    (new Setting[] { (Setting)this., (Setting)this. });
  }
  
  public void (Potion paramPotion) {
    if (this..containsKey(paramPotion)) {
      this..replace(paramPotion, Long.valueOf(System.currentTimeMillis()));
    } else {
      this..put(paramPotion, Long.valueOf(System.currentTimeMillis()));
    } 
  }
  
  public boolean (PotionEffect paramPotionEffect, ItemStack paramItemStack) {
    if (this..()) {
      for (byte b = 9; b < 45; b++) {
        if (mc.field_71439_g.field_71069_bz.func_75139_a(b).func_75216_d()) {
          ItemStack itemStack = mc.field_71439_g.field_71069_bz.func_75139_a(b).func_75211_c();
          if (itemStack != paramItemStack && itemStack.func_77973_b() instanceof ItemPotion && ItemPotion.func_77831_g(itemStack.func_77960_j()))
            for (PotionEffect potionEffect : ((ItemPotion)itemStack.func_77973_b()).func_77832_l(itemStack)) {
              if (potionEffect.func_76456_a() == paramPotionEffect.func_76456_a() && potionEffect.func_76458_c() > paramPotionEffect.func_76458_c())
                return false; 
            }  
        } 
      } 
    } else {
      for (byte b = 0; b < 9; b++) {
        if (mc.field_71439_g.field_71071_by.func_70301_a(b) != null) {
          ItemStack itemStack = mc.field_71439_g.field_71071_by.func_70301_a(b);
          if (itemStack != paramItemStack && itemStack.func_77973_b() instanceof ItemPotion && ItemPotion.func_77831_g(itemStack.func_77960_j()))
            for (PotionEffect potionEffect : ((ItemPotion)itemStack.func_77973_b()).func_77832_l(itemStack)) {
              if (potionEffect.func_76456_a() == paramPotionEffect.func_76456_a() && potionEffect.func_76458_c() > paramPotionEffect.func_76458_c())
                return false; 
            }  
        } 
      } 
    } 
    return true;
  }
  
  public void (int paramInt, ItemStack paramItemStack) {
    short s = mc.field_71439_g.field_71070_bA.func_75136_a(null);
    mc.func_147114_u().func_147298_b().func_179290_a((Packet)new C0EPacketClickWindow(mc.field_71439_g.field_71070_bA.field_75152_c, paramInt, 1, 2, paramItemStack, s));
  }
  
  @SubscribeEvent
  public void (MotionUpdateEvent.Post paramPost) {
    int i = mc.field_71439_g.field_71071_by.field_70461_c;
    Iterator<Integer> iterator = this..iterator();
    while (iterator.hasNext()) {
      int j = ((Integer)iterator.next()).intValue();
      if (j >= 36) {
        LunarClient.(j - 36);
        mc.func_147114_u().func_147298_b().func_179290_a((Packet)new C08PacketPlayerBlockPlacement(mc.field_71439_g.func_70694_bm()));
        continue;
      } 
      (j, mc.field_71439_g.field_71070_bA.func_75139_a(j).func_75211_c());
      LunarClient.(1);
      mc.func_147114_u().func_147298_b().func_179290_a((Packet)new C08PacketPlayerBlockPlacement(mc.field_71439_g.field_71070_bA.func_75139_a(j).func_75211_c()));
      (j, mc.field_71439_g.field_71070_bA.func_75139_a(37).func_75211_c());
      KillAura..();
    } 
    LunarClient.(i);
  }
  
  public static void (int paramInt) {
    OringoClient.mc.field_71442_b.func_78753_a(OringoClient.mc.field_71439_g.field_71069_bz.field_75152_c, paramInt, 0, 1, (EntityPlayer)OringoClient.mc.field_71439_g);
  }
}
