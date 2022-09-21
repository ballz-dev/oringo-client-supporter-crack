package me.oringo.oringoclient.mixins.renderer;

import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.entity.EntityLivingBase;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin({RendererLivingEntity.class})
public interface RendererLivingAccessor {
  @Invoker("renderLayers")
  void renderLayers(EntityLivingBase paramEntityLivingBase, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7);
  
  @Invoker("renderModel")
  <T extends EntityLivingBase> void renderModel(T paramT, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6);
}
