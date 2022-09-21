package me.oringo.oringoclient.mixins.gui;

import java.io.IOException;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiYesNoCallback;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin({GuiScreen.class})
public abstract class GuiScreenMixin extends GuiMixin implements GuiYesNoCallback {
  @Shadow
  protected FontRenderer field_146289_q;
  
  @Shadow
  public boolean field_146291_p;
  
  @Shadow
  public int field_146295_m;
  
  @Shadow
  public Minecraft field_146297_k;
  
  @Shadow
  public int field_146294_l;
  
  @Shadow
  public void func_146270_b(int paramInt) {}
  
  @Shadow
  public void func_146282_l() throws IOException {}
  
  @Shadow
  public void func_146274_d() throws IOException {}
  
  @Shadow
  protected void func_146286_b(int paramInt1, int paramInt2, int paramInt3) {}
  
  @Shadow
  public abstract void func_175275_f(String paramString);
  
  @Shadow
  public abstract void func_146269_k() throws IOException;
  
  @Shadow
  public abstract void func_73863_a(int paramInt1, int paramInt2, float paramFloat);
  
  @Shadow
  public abstract void func_146278_c(int paramInt);
}
