package me.oringo.oringoclient.mixins.renderer;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.util.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin({Render.class})
public interface RenderAccessor<T extends net.minecraft.entity.Entity> {
  @Invoker("getEntityTexture")
  ResourceLocation getEntityTexture(T paramT);
}
