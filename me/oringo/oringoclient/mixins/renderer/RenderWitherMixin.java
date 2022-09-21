package me.oringo.oringoclient.mixins.renderer;

import me.oringo.oringoclient.OringoClient;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderWither;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({RenderWither.class})
public class RenderWitherMixin {
  @Inject(method = {"preRenderCallback(Lnet/minecraft/entity/boss/EntityWither;F)V"}, at = {@At("HEAD")}, cancellable = true)
  private <T extends net.minecraft.entity.boss.EntityWither> void onPreRenderCallback(T paramT, float paramFloat, CallbackInfo paramCallbackInfo) {
    if (OringoClient..() && OringoClient...())
      GlStateManager.func_179139_a(OringoClient...(), OringoClient...(), OringoClient...()); 
  }
}
