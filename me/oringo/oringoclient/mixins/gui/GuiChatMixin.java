package me.oringo.oringoclient.mixins.gui;

import me.oringo.oringoclient.events.impl.GuiChatEvent;
import net.minecraft.client.gui.GuiChat;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.Event;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = {GuiChat.class}, priority = 1)
public abstract class GuiChatMixin extends GuiScreenMixin {
  @Inject(method = {"drawScreen"}, at = {@At("RETURN")})
  public void drawScreen(int paramInt1, int paramInt2, float paramFloat, CallbackInfo paramCallbackInfo) {
    if (MinecraftForge.EVENT_BUS.post((Event)new GuiChatEvent.DrawChatEvent(paramInt1, paramInt2)))
      paramCallbackInfo.cancel(); 
  }
  
  @Inject(method = {"keyTyped"}, at = {@At("RETURN")})
  public void keyTyped(char paramChar, int paramInt, CallbackInfo paramCallbackInfo) {
    if (MinecraftForge.EVENT_BUS.post((Event)new GuiChatEvent.KeyTyped(paramInt, paramChar)))
      paramCallbackInfo.cancel(); 
  }
  
  @Inject(method = {"mouseClicked"}, at = {@At("RETURN")})
  public void mouseClicked(int paramInt1, int paramInt2, int paramInt3, CallbackInfo paramCallbackInfo) {
    if (MinecraftForge.EVENT_BUS.post((Event)new GuiChatEvent.MouseClicked(paramInt1, paramInt2, paramInt3)))
      paramCallbackInfo.cancel(); 
  }
  
  protected void func_146286_b(int paramInt1, int paramInt2, int paramInt3) {
    MinecraftForge.EVENT_BUS.post((Event)new GuiChatEvent.MouseReleased(paramInt1, paramInt2, paramInt3));
  }
  
  @Inject(method = {"onGuiClosed"}, at = {@At("RETURN")})
  public void onGuiClosed(CallbackInfo paramCallbackInfo) {
    MinecraftForge.EVENT_BUS.post((Event)new GuiChatEvent.Closed());
  }
}
