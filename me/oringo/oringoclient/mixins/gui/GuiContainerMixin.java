package me.oringo.oringoclient.mixins.gui;

import java.io.IOException;
import me.oringo.oringoclient.OringoClient;
import me.oringo.oringoclient.qolfeatures.module.impl.other.HClip;
import me.oringo.oringoclient.qolfeatures.module.impl.skyblock.AutoMask;
import me.oringo.oringoclient.qolfeatures.module.impl.skyblock.CrimsonQOL;
import me.oringo.oringoclient.utils.font.Fonts;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.util.MathHelper;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.Event;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({GuiContainer.class})
public abstract class GuiContainerMixin extends GuiScreenMixin {
  @Shadow
  public Container field_147002_h;
  
  private GuiTextField chatTextField;
  
  @Shadow
  private boolean field_146995_H;
  
  @Inject(method = {"initGui"}, at = {@At("HEAD")})
  public void oringo$onInitGui(CallbackInfo paramCallbackInfo) {
    this.chatTextField = new GuiTextField(0, this.field_146289_q, 4, this.field_146295_m - 12, this.field_146294_l - 4, 12);
    this.chatTextField.func_146180_a("");
    this.chatTextField.func_146203_f(100);
    this.chatTextField.func_146185_a(false);
    this.chatTextField.func_146205_d(true);
    this.chatTextField.func_146195_b(false);
  }
  
  @Inject(method = {"drawScreen"}, at = {@At("HEAD")}, cancellable = true)
  public void onDrawScreen(int paramInt1, int paramInt2, float paramFloat, CallbackInfo paramCallbackInfo) {
    boolean bool = OringoClient...();
    if (!bool)
      this.chatTextField.func_146195_b(false); 
    this.chatTextField.func_146189_e(bool);
    this.chatTextField.func_146184_c(bool);
    if (this.field_147002_h instanceof ContainerChest && OringoClient..((ContainerChest)this.field_147002_h)) {
      this.field_146291_p = true;
      if (!this.field_146297_k.field_71415_G) {
        this.field_146297_k.field_71415_G = true;
        this.field_146297_k.field_71417_B.func_74372_a();
      } 
      ScaledResolution scaledResolution = new ScaledResolution(this.field_146297_k);
      Fonts..("In terminal!", (scaledResolution.func_78326_a() / 2), (scaledResolution.func_78328_b() / 2), OringoClient..().getRGB());
      GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
      GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
      MinecraftForge.EVENT_BUS.post((Event)new GuiScreenEvent.BackgroundDrawnEvent((GuiScreen)this));
      paramCallbackInfo.cancel();
      return;
    } 
    if (this.chatTextField.func_146176_q())
      func_73734_a(2, this.field_146295_m - 14, this.field_146294_l / 3, this.field_146295_m - 2, -2147483648); 
    this.chatTextField.func_146194_f();
    if (this.chatTextField.func_146206_l())
      KeyBinding.func_74506_a(); 
    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
  }
  
  @Inject(method = {"keyTyped"}, at = {@At("HEAD")}, cancellable = true)
  public void oringo$keyTyped(char paramChar, int paramInt, CallbackInfo paramCallbackInfo) {
    if (this.chatTextField.func_146206_l()) {
      if (paramInt != 28 && paramInt != 156) {
        this.chatTextField.func_146201_a(paramChar, paramInt);
      } else {
        String str = this.chatTextField.func_146179_b().trim();
        if (str.length() > 0) {
          func_175275_f(str);
          this.chatTextField.func_146180_a("");
        } 
      } 
      paramCallbackInfo.cancel();
    } 
  }
  
  public void func_146270_b(int paramInt) {
    if (!((GuiTextFieldAcessor)this.chatTextField).getIsEnabled())
      super.func_146270_b(paramInt); 
  }
  
  @Inject(method = {"mouseClicked"}, at = {@At("HEAD")})
  public void oringo$mouseClicked(int paramInt1, int paramInt2, int paramInt3, CallbackInfo paramCallbackInfo) {
    this.chatTextField.func_146192_a(paramInt1, paramInt2, paramInt3);
  }
  
  public void func_146274_d() throws IOException {
    super.func_146274_d();
    if (this.field_147002_h instanceof ContainerChest && OringoClient..((ContainerChest)this.field_147002_h)) {
      int i = Mouse.getEventButton();
      KeyBinding.func_74510_a(i - 100, Mouse.getEventButtonState());
      if (Mouse.getEventButtonState())
        KeyBinding.func_74507_a(i - 100); 
      int j = Mouse.getEventDWheel();
      if (j != 0)
        if (this.field_146297_k.field_71439_g.func_175149_v()) {
          j = (j < 0) ? -1 : 1;
          if (this.field_146297_k.field_71456_v.func_175187_g().func_175262_a()) {
            this.field_146297_k.field_71456_v.func_175187_g().func_175259_b(-j);
          } else {
            float f = MathHelper.func_76131_a(this.field_146297_k.field_71439_g.field_71075_bZ.func_75093_a() + j * 0.005F, 0.0F, 0.2F);
            this.field_146297_k.field_71439_g.field_71075_bZ.func_75092_a(f);
          } 
        } else {
          this.field_146297_k.field_71439_g.field_71071_by.func_70453_c(j);
        }  
    } 
  }
  
  public void func_146282_l() throws IOException {
    super.func_146282_l();
    if (this.field_147002_h instanceof ContainerChest && OringoClient..((ContainerChest)this.field_147002_h)) {
      int i = (Keyboard.getEventKey() == 0) ? (Keyboard.getEventCharacter() + 256) : Keyboard.getEventKey();
      KeyBinding.func_74510_a(i, Keyboard.getEventKeyState());
      if (Keyboard.getEventKeyState()) {
        KeyBinding.func_74507_a(i);
        CrimsonQOL.(i);
      } 
    } 
  }
  
  @Inject(method = {"drawScreen"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/gui/inventory/GuiContainer;drawGuiContainerBackgroundLayer(FII)V")}, cancellable = true)
  public void onDrawBackground(int paramInt1, int paramInt2, float paramFloat, CallbackInfo paramCallbackInfo) {
    if (HClip.((GuiScreen)this)) {
      GL11.glPushMatrix();
      AutoMask.();
    } 
  }
  
  @Inject(method = {"drawScreen"}, at = {@At("RETURN")}, cancellable = true)
  public void onDrawScreenPost(int paramInt1, int paramInt2, float paramFloat, CallbackInfo paramCallbackInfo) {
    if (HClip.((GuiScreen)this))
      GL11.glPopMatrix(); 
  }
  
  @Shadow
  public abstract void func_73866_w_();
  
  @Shadow
  protected abstract void func_73869_a(char paramChar, int paramInt) throws IOException;
}
