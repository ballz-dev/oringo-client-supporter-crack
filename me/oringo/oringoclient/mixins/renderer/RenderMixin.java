package me.oringo.oringoclient.mixins.renderer;

import net.minecraft.client.renderer.entity.Render;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin({Render.class})
public abstract class RenderMixin {
  @Shadow
  public <T extends net.minecraft.entity.Entity> void func_76986_a(T paramT, double paramDouble1, double paramDouble2, double paramDouble3, float paramFloat1, float paramFloat2) {}
}
