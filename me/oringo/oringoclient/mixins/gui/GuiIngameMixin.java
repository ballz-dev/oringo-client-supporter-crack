package me.oringo.oringoclient.mixins.gui;

import me.oringo.oringoclient.OringoClient;
import me.oringo.oringoclient.events.impl.ScoreboardRenderEvent;
import me.oringo.oringoclient.qolfeatures.module.impl.render.CustomInterfaces;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.util.ResourceLocation;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({GuiIngame.class})
public abstract class GuiIngameMixin extends Gui {
  @Shadow
  @Final
  protected static ResourceLocation field_110330_c;
  
  @Shadow
  @Final
  protected Minecraft field_73839_d;
  
  @Shadow
  protected abstract void func_175184_a(int paramInt1, int paramInt2, int paramInt3, float paramFloat, EntityPlayer paramEntityPlayer);
  
  @Inject(method = {"renderScoreboard"}, at = {@At("HEAD")}, cancellable = true)
  public void renderScoreboard(ScoreObjective paramScoreObjective, ScaledResolution paramScaledResolution, CallbackInfo paramCallbackInfo) {
    if ((new ScoreboardRenderEvent(paramScoreObjective, paramScaledResolution)).post())
      paramCallbackInfo.cancel(); 
  }
  
  @Shadow
  public abstract FontRenderer func_175179_f();
  
  @Overwrite
  protected void func_180479_a(ScaledResolution paramScaledResolution, float paramFloat) {
    if (this.field_73839_d.func_175606_aa() instanceof EntityPlayer) {
      GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
      this.field_73839_d.func_110434_K().func_110577_a(field_110330_c);
      EntityPlayer entityPlayer = (EntityPlayer)this.field_73839_d.func_175606_aa();
      int i = paramScaledResolution.func_78326_a() / 2;
      float f = this.field_73735_i;
      this.field_73735_i = -90.0F;
      func_73729_b(i - 91, paramScaledResolution.func_78328_b() - 22, 0, 0, 182, 22);
      if (OringoClient...()) {
        func_175174_a((i - 91 - 1) + (CustomInterfaces. + (entityPlayer.field_71071_by.field_70461_c - CustomInterfaces.) * paramFloat) * 20.0F, (paramScaledResolution.func_78328_b() - 22 - 1), 0, 22, 24, 22);
      } else {
        func_73729_b(i - 91 - 1 + entityPlayer.field_71071_by.field_70461_c * 20, paramScaledResolution.func_78328_b() - 22 - 1, 0, 22, 24, 22);
      } 
      this.field_73735_i = f;
      GlStateManager.func_179091_B();
      GlStateManager.func_179147_l();
      GlStateManager.func_179120_a(770, 771, 1, 0);
      RenderHelper.func_74520_c();
      for (byte b = 0; b < 9; b++) {
        int j = paramScaledResolution.func_78326_a() / 2 - 90 + b * 20 + 2;
        int k = paramScaledResolution.func_78328_b() - 16 - 3;
        func_175184_a(b, j, k, paramFloat, entityPlayer);
      } 
      RenderHelper.func_74518_a();
      GlStateManager.func_179101_C();
      GlStateManager.func_179084_k();
    } 
  }
}
