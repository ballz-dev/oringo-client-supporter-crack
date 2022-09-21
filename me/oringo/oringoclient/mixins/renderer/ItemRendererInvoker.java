package me.oringo.oringoclient.mixins.renderer;

import net.minecraft.client.renderer.ItemRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin({ItemRenderer.class})
public interface ItemRendererInvoker {
  @Invoker("renderFireInFirstPerson")
  void renderFireOverlay(float paramFloat);
}
