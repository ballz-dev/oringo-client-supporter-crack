package me.oringo.oringoclient.ui.hud.impl;

import java.awt.Color;
import me.oringo.oringoclient.OringoClient;
import me.oringo.oringoclient.commands.impl.WardrobeCommand;
import me.oringo.oringoclient.events.impl.MoveFlyingEvent;
import me.oringo.oringoclient.mixins.item.ItemSwordAccessor;
import me.oringo.oringoclient.mixins.item.ItemToolAccessor;
import me.oringo.oringoclient.qolfeatures.module.impl.movement.Scaffold;
import me.oringo.oringoclient.qolfeatures.module.impl.other.KillInsults;
import me.oringo.oringoclient.qolfeatures.module.impl.render.InventoryDisplay;
import me.oringo.oringoclient.qolfeatures.module.impl.skyblock.AutoRogueSword;
import me.oringo.oringoclient.ui.hud.DraggableComponent;
import me.oringo.oringoclient.ui.hud.HudVec;
import me.oringo.oringoclient.utils.OutlineUtils;
import me.oringo.oringoclient.utils.RenderUtils;
import me.oringo.oringoclient.utils.font.Fonts;
import me.oringo.oringoclient.utils.shader.BlurUtils;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import org.lwjgl.opengl.GL11;

public class InventoryHUD extends DraggableComponent {
  public static InventoryHUD  = new InventoryHUD();
  
  public static float (Entity paramEntity, ItemStack paramItemStack, boolean paramBoolean) {
    float f = 1.0F;
    if (paramItemStack == null)
      return f; 
    if (paramItemStack.func_77973_b() instanceof net.minecraft.item.ItemTool) {
      f += ((ItemToolAccessor)paramItemStack.func_77973_b()).getDamageVsEntity();
    } else if (paramItemStack.func_77973_b() instanceof net.minecraft.item.ItemSword) {
      f += ((ItemSwordAccessor)paramItemStack.func_77973_b()).getAttackDamage();
      f += 2.0F;
    } 
    if (paramEntity instanceof EntityLivingBase) {
      f += EnchantmentHelper.func_152377_a(paramItemStack, ((EntityLivingBase)paramEntity).func_70668_bt());
    } else {
      f += EnchantmentHelper.func_152377_a(paramItemStack, EnumCreatureAttribute.UNDEFINED);
    } 
    if (paramBoolean && paramEntity != null && !paramEntity.func_70027_ad()) {
      int i = EnchantmentHelper.func_77506_a(Enchantment.field_77334_n.field_77352_x, paramItemStack);
      f += (25 * i);
    } 
    return f;
  }
  
  public HudVec () {
    GL11.glPushMatrix();
    super.();
    ScaledResolution scaledResolution1 = new ScaledResolution(mc);
    byte b1 = 0;
    double d1 = this.;
    double d2 = this.;
    switch (OringoClient...()) {
      case "Low":
        b1 = 3;
        break;
      case "High":
        b1 = 7;
        break;
    } 
    ScaledResolution scaledResolution2 = new ScaledResolution(mc);
    KillInsults.();
    KillInsults.();
    RenderUtils.(d1, d2 + Fonts..() - 4.0D, 182.0D, (80 - Fonts..() - 4), 5.0D, Color.white.getRGB());
    Scaffold.(1);
    BlurUtils.renderBlurredBackground(b1, scaledResolution2.func_78326_a(), scaledResolution2.func_78328_b(), 0.0F, 0.0F, scaledResolution1.func_78326_a(), scaledResolution1.func_78328_b());
    AutoRogueSword.();
    ((float)d1, (float)d2 + Fonts..() - 4.0F, 182.0F, (80 - Fonts..() - 4), 5.0F, 2.5F);
    Fonts..("Inventory", ((float)d1 + 90.0F), ((float)d2 + Fonts..()), Color.white.getRGB());
    GlStateManager.func_179091_B();
    GlStateManager.func_179147_l();
    GlStateManager.func_179120_a(770, 771, 1, 0);
    RenderHelper.func_74520_c();
    for (byte b2 = 9; b2 < 36; b2++) {
      if (b2 % 9 == 0)
        d2 += 20.0D; 
      ItemStack itemStack = mc.field_71439_g.field_71071_by.func_70301_a(b2);
      if (itemStack != null) {
        mc.func_175599_af().func_180450_b(itemStack, (int)d1 + 2 + 20 * b2 % 9, (int)d2);
        (itemStack, d1 + 2.0D + (20 * b2 % 9), d2);
      } 
    } 
    RenderHelper.func_74518_a();
    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    GlStateManager.func_179101_C();
    GlStateManager.func_179084_k();
    GL11.glPopMatrix();
    InventoryDisplay inventoryDisplay = OringoClient.;
    inventoryDisplay..(this.);
    inventoryDisplay..(this.);
    return new HudVec(d1 + this., d2 + this.);
  }
  
  public InventoryHUD() {
    (182.0D, (80 - Fonts..() - 4));
    (OringoClient...(), OringoClient...());
  }
  
  public static void (Object paramObject) {
    MoveFlyingEvent.(paramObject.toString());
  }
  
  public void (ItemStack paramItemStack, double paramDouble1, double paramDouble2) {
    if (paramItemStack != null) {
      if (paramItemStack.field_77994_a != 1) {
        String str = String.valueOf(paramItemStack.field_77994_a);
        if (paramItemStack.field_77994_a < 1)
          str = String.valueOf((new StringBuilder()).append(EnumChatFormatting.RED).append(String.valueOf(paramItemStack.field_77994_a))); 
        GlStateManager.func_179140_f();
        GlStateManager.func_179097_i();
        GlStateManager.func_179084_k();
        Fonts..(str, paramDouble1 + 19.0D - 2.0D - Fonts..(str), paramDouble2 + 6.0D + 3.0D, Color.white.getRGB());
        GlStateManager.func_179145_e();
        GlStateManager.func_179126_j();
      } 
      if (paramItemStack.func_77973_b().showDurabilityBar(paramItemStack)) {
        double d = paramItemStack.func_77973_b().getDurabilityForDisplay(paramItemStack);
        int i = (int)Math.round(13.0D - d * 13.0D);
        int j = (int)Math.round(255.0D - d * 255.0D);
        GlStateManager.func_179140_f();
        GlStateManager.func_179097_i();
        GlStateManager.func_179090_x();
        GlStateManager.func_179118_c();
        GlStateManager.func_179084_k();
        Tessellator tessellator = Tessellator.func_178181_a();
        WorldRenderer worldRenderer = tessellator.func_178180_c();
        (worldRenderer, paramDouble1 + 2.0D, paramDouble2 + 13.0D, 13, 2, 0, 0, 0, 255);
        (worldRenderer, paramDouble1 + 2.0D, paramDouble2 + 13.0D, 12, 1, (255 - j) / 4, 64, 0, 255);
        (worldRenderer, paramDouble1 + 2.0D, paramDouble2 + 13.0D, i, 1, 255 - j, j, 0, 255);
        GlStateManager.func_179141_d();
        GlStateManager.func_179098_w();
        GlStateManager.func_179145_e();
        GlStateManager.func_179126_j();
      } 
    } 
  }
  
  public void (WorldRenderer paramWorldRenderer, double paramDouble1, double paramDouble2, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6) {
    paramWorldRenderer.func_181668_a(7, DefaultVertexFormats.field_181706_f);
    paramWorldRenderer.func_181662_b(paramDouble1 + 0.0D, paramDouble2 + 0.0D, 0.0D).func_181669_b(paramInt3, paramInt4, paramInt5, paramInt6).func_181675_d();
    paramWorldRenderer.func_181662_b(paramDouble1 + 0.0D, paramDouble2 + paramInt2, 0.0D).func_181669_b(paramInt3, paramInt4, paramInt5, paramInt6).func_181675_d();
    paramWorldRenderer.func_181662_b(paramDouble1 + paramInt1, paramDouble2 + paramInt2, 0.0D).func_181669_b(paramInt3, paramInt4, paramInt5, paramInt6).func_181675_d();
    paramWorldRenderer.func_181662_b(paramDouble1 + paramInt1, paramDouble2 + 0.0D, 0.0D).func_181669_b(paramInt3, paramInt4, paramInt5, paramInt6).func_181675_d();
    Tessellator.func_178181_a().func_78381_a();
  }
  
  public void (float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6) {
    Scaffold.(paramFloat1, paramFloat2, (paramFloat1 + paramFloat3), (paramFloat2 + paramFloat4), paramFloat5, (new Color(21, 21, 21, 50)).getRGB());
    if (() && mc.field_71462_r instanceof net.minecraft.client.gui.GuiChat) {
      WardrobeCommand.(paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramFloat5, paramFloat6, Color.white.getRGB());
    } else {
      OutlineUtils.(paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramFloat5, paramFloat6, OringoClient..(0).getRGB(), OringoClient..(3).getRGB(), OringoClient..(6).getRGB(), OringoClient..(9).getRGB());
    } 
  }
}
