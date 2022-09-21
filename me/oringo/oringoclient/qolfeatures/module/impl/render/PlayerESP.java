package me.oringo.oringoclient.qolfeatures.module.impl.render;

import java.awt.Color;
import me.oringo.oringoclient.OringoClient;
import me.oringo.oringoclient.commands.CommandHandler;
import me.oringo.oringoclient.events.impl.RenderLayersEvent;
import me.oringo.oringoclient.qolfeatures.module.Module;
import me.oringo.oringoclient.qolfeatures.module.impl.combat.SumoFences;
import me.oringo.oringoclient.qolfeatures.module.impl.other.LightningDetect;
import me.oringo.oringoclient.qolfeatures.module.impl.other.SimulatorAura;
import me.oringo.oringoclient.qolfeatures.module.impl.player.ArmorSwap;
import me.oringo.oringoclient.qolfeatures.module.impl.player.InvManager;
import me.oringo.oringoclient.qolfeatures.module.impl.skyblock.SecretAura;
import me.oringo.oringoclient.qolfeatures.module.settings.Setting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.ModeSetting;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class PlayerESP extends Module {
  public EntityPlayer ;
  
  public ModeSetting  = new ModeSetting("Mode", "2D", new String[] { "Outline", "2D", "Chams", "Box", "Tracers" });
  
  @SubscribeEvent(priority = EventPriority.LOWEST, receiveCanceled = true)
  public void (RenderLivingEvent.Pre paramPre) {
    if (this. != null) {
      this. = null;
      LightningDetect.();
    } 
    if (!(paramPre.entity instanceof net.minecraft.client.entity.EntityOtherPlayerMP) || !this..().equals("Chams") || !())
      return; 
    InvManager.();
    this. = (EntityPlayer)paramPre.entity;
  }
  
  @SubscribeEvent(receiveCanceled = true)
  public void (RenderLivingEvent.Specials.Pre paramPre) {
    if (paramPre.entity == this.) {
      this. = null;
      LightningDetect.();
    } 
  }
  
  public boolean (EntityPlayer paramEntityPlayer) {
    return (ArmorSwap.((Entity)paramEntityPlayer) && paramEntityPlayer.func_110143_aJ() > 0.0F && !paramEntityPlayer.field_70128_L);
  }
  
  public PlayerESP() {
    super("PlayerESP", 0, Module.Category.);
    (new Setting[] { (Setting)this. });
  }
  
  @SubscribeEvent
  public void (RenderLayersEvent paramRenderLayersEvent) {
    Color color = OringoClient..();
    if (() && paramRenderLayersEvent. instanceof EntityPlayer && ((EntityPlayer)paramRenderLayersEvent.) && paramRenderLayersEvent. != mc.field_71439_g && this..().equals("Outline"))
      SecretAura.(paramRenderLayersEvent, color); 
  }
  
  public static float (ItemStack paramItemStack) {
    float f = 0.0F;
    if (paramItemStack.func_77973_b() instanceof ItemArmor) {
      ItemArmor itemArmor = (ItemArmor)paramItemStack.func_77973_b();
      f = (float)(f + itemArmor.field_77879_b + ((100 - itemArmor.field_77879_b) * EnchantmentHelper.func_77506_a(Enchantment.field_180310_c.field_77352_x, paramItemStack)) * 0.0075D);
      f = (float)(f + EnchantmentHelper.func_77506_a(Enchantment.field_77327_f.field_77352_x, paramItemStack) / 100.0D);
      f = (float)(f + EnchantmentHelper.func_77506_a(Enchantment.field_77329_d.field_77352_x, paramItemStack) / 100.0D);
      f = (float)(f + EnchantmentHelper.func_77506_a(Enchantment.field_92091_k.field_77352_x, paramItemStack) / 100.0D);
      f = (float)(f + EnchantmentHelper.func_77506_a(Enchantment.field_77347_r.field_77352_x, paramItemStack) / 50.0D);
      f = (float)(f + EnchantmentHelper.func_77506_a(Enchantment.field_180308_g.field_77352_x, paramItemStack) / 100.0D);
      f = (float)(f + paramItemStack.func_77958_k() / 1000.0D);
    } 
    return f;
  }
  
  @SubscribeEvent
  public void (RenderWorldLastEvent paramRenderWorldLastEvent) {
    if (!() || (!this..().equals("2D") && !this..().equals("Box") && !this..().equals("Tracers")))
      return; 
    Color color = OringoClient..();
    for (EntityPlayer entityPlayer : mc.field_71441_e.field_73010_i) {
      if ((entityPlayer) && entityPlayer != mc.field_71439_g)
        switch (this..()) {
          case "2D":
            SumoFences.((Entity)entityPlayer, paramRenderWorldLastEvent.partialTicks, 1.0F, color);
          case "Box":
            CommandHandler.((Entity)entityPlayer, paramRenderWorldLastEvent.partialTicks, color);
          case "Tracers":
            SimulatorAura.((Entity)entityPlayer, paramRenderWorldLastEvent.partialTicks, 1.0F, color);
        }  
    } 
  }
}
