package me.oringo.oringoclient.qolfeatures.module.impl.skyblock;

import com.mojang.realmsclient.gui.ChatFormatting;
import java.awt.Color;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import me.oringo.oringoclient.OringoClient;
import me.oringo.oringoclient.commands.impl.ClipCommand;
import me.oringo.oringoclient.events.impl.WorldJoinEvent;
import me.oringo.oringoclient.qolfeatures.module.Module;
import me.oringo.oringoclient.qolfeatures.module.impl.other.AntiNicker;
import me.oringo.oringoclient.qolfeatures.module.settings.Setting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.NumberSetting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.opengl.GL11;

public class CrimsonQOL extends Module {
  public static NumberSetting  = new NumberSetting("Time", 1.0D, 1.0D, 8.0D, 1.0D);
  
  public boolean ;
  
  public static NumberSetting  = new NumberSetting("Distance", 30.0D, 1.0D, 64.0D, 1.0D);
  
  public CrimsonQOL() {
    super("Auto Cloak", Module.Category.);
    (new Setting[] { (Setting), (Setting) });
  }
  
  @SubscribeEvent
  public void (TickEvent.ClientTickEvent paramClientTickEvent) {
    if (() && paramClientTickEvent.phase == TickEvent.Phase.END && mc.field_71439_g != null && mc.field_71441_e != null) {
      boolean bool = false;
      int i = ClipCommand.(CrimsonQOL::);
      Pattern pattern = Pattern.compile("(.*)s [1-8] hits", 2);
      for (EntityArmorStand entityArmorStand : mc.field_71441_e.func_175644_a(EntityArmorStand.class, CrimsonQOL::)) {
        if (i != -1 && entityArmorStand.func_70068_e((Entity)mc.field_71439_g) < .() * .()) {
          Matcher matcher = pattern.matcher(ChatFormatting.stripFormatting(entityArmorStand.func_145748_c_().func_150260_c()));
          if (matcher.find()) {
            String str = matcher.group(1);
            int j = Integer.parseInt(str);
            if (j <= .()) {
              bool = true;
              if (!this.) {
                this. = true;
                mc.func_147114_u().func_147298_b().func_179290_a((Packet)new C09PacketHeldItemChange(i));
                mc.func_147114_u().func_147298_b().func_179290_a((Packet)new C08PacketPlayerBlockPlacement(mc.field_71439_g.field_71071_by.func_70301_a(i)));
                mc.func_147114_u().func_147298_b().func_179290_a((Packet)new C09PacketHeldItemChange(mc.field_71439_g.field_71071_by.field_70461_c));
              } 
            } 
          } 
        } 
      } 
      if (this. && !bool) {
        if (i != -1) {
          mc.func_147114_u().func_147298_b().func_179290_a((Packet)new C09PacketHeldItemChange(i));
          mc.func_147114_u().func_147298_b().func_179290_a((Packet)new C08PacketPlayerBlockPlacement(mc.field_71439_g.field_71071_by.func_70301_a(i)));
          mc.func_147114_u().func_147298_b().func_179290_a((Packet)new C09PacketHeldItemChange(mc.field_71439_g.field_71071_by.field_70461_c));
        } 
        this. = false;
      } 
    } 
  }
  
  public static void (Vec3 paramVec3, Color paramColor) {
    GL11.glBlendFunc(770, 771);
    GL11.glEnable(3042);
    GL11.glLineWidth(2.0F);
    GL11.glDisable(3553);
    GL11.glDisable(2929);
    GL11.glDepthMask(false);
    AntiNicker.(paramColor);
    Minecraft.func_71410_x().func_175598_ae();
    RenderGlobal.func_181561_a(new AxisAlignedBB(paramVec3.field_72450_a - 0.05D - (Minecraft.func_71410_x().func_175598_ae()).field_78730_l, paramVec3.field_72448_b - 0.05D - (Minecraft.func_71410_x().func_175598_ae()).field_78731_m, paramVec3.field_72449_c - 0.05D - (Minecraft.func_71410_x().func_175598_ae()).field_78728_n, paramVec3.field_72450_a + 0.05D - (Minecraft.func_71410_x().func_175598_ae()).field_78730_l, paramVec3.field_72448_b + 0.05D - (Minecraft.func_71410_x().func_175598_ae()).field_78731_m, paramVec3.field_72449_c + 0.05D - (Minecraft.func_71410_x().func_175598_ae()).field_78728_n));
    GL11.glEnable(3553);
    GL11.glEnable(2929);
    GL11.glDepthMask(true);
    GL11.glDisable(3042);
  }
  
  public static void (int paramInt) {
    if (paramInt == 0)
      return; 
    for (Module module : OringoClient.) {
      if (module.() == paramInt && !module.()) {
        module.();
        if (OringoClient...())
          WorldJoinEvent.(String.valueOf((new StringBuilder()).append(module.()).append(module.() ? " enabled!" : " disabled!")), 2500); 
      } 
    } 
  }
}
