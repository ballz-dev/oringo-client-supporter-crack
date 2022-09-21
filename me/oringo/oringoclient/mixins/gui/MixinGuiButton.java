package me.oringo.oringoclient.mixins.gui;

import java.awt.Color;
import me.oringo.oringoclient.OringoClient;
import me.oringo.oringoclient.qolfeatures.module.impl.movement.Scaffold;
import me.oringo.oringoclient.qolfeatures.module.impl.other.NoCarpet;
import me.oringo.oringoclient.qolfeatures.module.impl.render.DungeonESP;
import me.oringo.oringoclient.utils.font.Fonts;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({GuiButton.class})
public abstract class MixinGuiButton extends GuiMixin {
  @Shadow
  public int field_146129_i;
  
  @Shadow
  public int field_146128_h;
  
  @Shadow
  public int field_146121_g;
  
  @Shadow
  public boolean field_146124_l;
  
  @Shadow
  public boolean field_146125_m;
  
  @Shadow
  public int packedFGColour;
  
  @Shadow
  public String field_146126_j;
  
  @Shadow
  public int field_146120_f;
  
  @Shadow
  @Final
  protected static ResourceLocation field_146122_a;
  
  @Shadow
  protected boolean field_146123_n;
  
  @Inject(method = {"drawButton"}, at = {@At("HEAD")}, cancellable = true)
  public void drawButton(Minecraft paramMinecraft, int paramInt1, int paramInt2, CallbackInfo paramCallbackInfo) {
    if (this.field_146125_m && OringoClient...()) {
      GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
      this.field_146123_n = (paramInt1 >= this.field_146128_h && paramInt2 >= this.field_146129_i && paramInt1 < this.field_146128_h + this.field_146120_f && paramInt2 < this.field_146129_i + this.field_146121_g);
      GlStateManager.func_179147_l();
      GlStateManager.func_179120_a(770, 771, 1, 0);
      GlStateManager.func_179112_b(770, 771);
      float f;
      for (f = 0.0F; f < 2.0F; f = (float)(f + 0.5D))
        Scaffold.((this.field_146128_h - f), (this.field_146129_i + f), ((this.field_146128_h + this.field_146120_f) - f), ((this.field_146129_i + this.field_146121_g) + f), 2.0D, (new Color(21, 21, 21, 30)).getRGB()); 
      Scaffold.(this.field_146128_h, this.field_146129_i, (this.field_146128_h + this.field_146120_f), (this.field_146129_i + this.field_146121_g), 2.0D, (new Color(21, 21, 21, 180)).getRGB());
      drawGradientRect(0.0F, 255);
      func_146119_b(paramMinecraft, paramInt1, paramInt2);
      Fonts..(this.field_146126_j, (this.field_146128_h + this.field_146120_f / 2.0F), (this.field_146129_i + (this.field_146121_g - Fonts..()) / 2.0F), this.field_146123_n ? Color.white.getRGB() : (new Color(200, 200, 200)).getRGB());
      GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
      paramCallbackInfo.cancel();
    } 
  }
  
  public void drawGradientRect(float paramFloat, int paramInt) {
    if (OringoClient...("Wave")) {
      start2ColorDraw();
      float f = this.field_146128_h;
      for (byte b = 1; b < 11; b++) {
        float f1 = this.field_146128_h + b * 0.1F * this.field_146120_f;
        if (OringoClient...("Top")) {
          addVertexes(f - paramFloat, this.field_146129_i - paramFloat, f1 + paramFloat, this.field_146129_i + 1.5F + paramFloat, DungeonESP.(OringoClient..(b), paramInt).getRGB(), DungeonESP.(OringoClient..(b + 1), paramInt).getRGB());
        } else {
          addVertexes(f - paramFloat, this.field_146129_i - paramFloat + this.field_146121_g - 1.5F, f1 + paramFloat, (this.field_146129_i + this.field_146121_g) + paramFloat, DungeonESP.(OringoClient..(b), paramInt).getRGB(), DungeonESP.(OringoClient..(b + 1), paramInt).getRGB());
        } 
        f = f1;
      } 
      end2ColorDraw();
    } else if (OringoClient...("Single")) {
      if (OringoClient...("Top")) {
        NoCarpet.(this.field_146128_h - paramFloat, this.field_146129_i - paramFloat, (this.field_146128_h + this.field_146120_f) + paramFloat, this.field_146129_i + 1.5F + paramFloat, DungeonESP.(OringoClient..(), paramInt).getRGB());
      } else {
        NoCarpet.(this.field_146128_h - paramFloat, this.field_146129_i - paramFloat + this.field_146121_g - 1.5F, (this.field_146128_h + this.field_146120_f) + paramFloat, (this.field_146129_i + this.field_146121_g) + paramFloat, DungeonESP.(OringoClient..(), paramInt).getRGB());
      } 
    } 
  }
  
  public void start2ColorDraw() {
    GlStateManager.func_179147_l();
    GlStateManager.func_179090_x();
    GlStateManager.func_179120_a(770, 771, 1, 0);
    GlStateManager.func_179103_j(7425);
    WorldRenderer worldRenderer = Tessellator.func_178181_a().func_178180_c();
    worldRenderer.func_181668_a(7, DefaultVertexFormats.field_181706_f);
  }
  
  public void end2ColorDraw() {
    Tessellator.func_178181_a().func_78381_a();
    GlStateManager.func_179103_j(7424);
    GlStateManager.func_179098_w();
    GlStateManager.func_179084_k();
  }
  
  public void addVertexes(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, int paramInt1, int paramInt2) {
    if (paramFloat1 < paramFloat3) {
      float f = paramFloat1;
      paramFloat1 = paramFloat3;
      paramFloat3 = f;
    } 
    if (paramFloat2 < paramFloat4) {
      float f = paramFloat2;
      paramFloat2 = paramFloat4;
      paramFloat4 = f;
    } 
    float f1 = (paramInt1 >> 24 & 0xFF) / 255.0F;
    float f2 = (paramInt1 >> 16 & 0xFF) / 255.0F;
    float f3 = (paramInt1 >> 8 & 0xFF) / 255.0F;
    float f4 = (paramInt1 & 0xFF) / 255.0F;
    float f5 = (paramInt2 >> 24 & 0xFF) / 255.0F;
    float f6 = (paramInt2 >> 16 & 0xFF) / 255.0F;
    float f7 = (paramInt2 >> 8 & 0xFF) / 255.0F;
    float f8 = (paramInt2 & 0xFF) / 255.0F;
    Tessellator tessellator = Tessellator.func_178181_a();
    WorldRenderer worldRenderer = tessellator.func_178180_c();
    worldRenderer.func_181662_b(paramFloat1, paramFloat4, 0.0D).func_181666_a(f6, f7, f8, f5).func_181675_d();
    worldRenderer.func_181662_b(paramFloat3, paramFloat4, 0.0D).func_181666_a(f2, f3, f4, f1).func_181675_d();
    worldRenderer.func_181662_b(paramFloat3, paramFloat2, 0.0D).func_181666_a(f2, f3, f4, f1).func_181675_d();
    worldRenderer.func_181662_b(paramFloat1, paramFloat2, 0.0D).func_181666_a(f6, f7, f8, f5).func_181675_d();
  }
  
  @Shadow
  protected abstract void func_146119_b(Minecraft paramMinecraft, int paramInt1, int paramInt2);
  
  @Shadow
  protected abstract int func_146114_a(boolean paramBoolean);
  
  @Shadow
  public abstract int func_146117_b();
}
