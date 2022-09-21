package me.oringo.oringoclient.mixins.renderer;

import me.oringo.oringoclient.OringoClient;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderVillager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({RenderVillager.class})
public class RenderVillagerMixin {
  @Inject(method = {"preRenderCallback(Lnet/minecraft/entity/passive/EntityVillager;F)V"}, at = {@At("HEAD")}, cancellable = true)
  private <T extends net.minecraft.entity.passive.EntityVillager> void onPreRenderCallback(T paramT, float paramFloat, CallbackInfo paramCallbackInfo) {
    if (OringoClient..() && OringoClient...())
      GlStateManager.func_179139_a(OringoClient...(), OringoClient...(), OringoClient...()); 
  }
}
