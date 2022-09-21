package me.oringo.oringoclient.mixins.renderer;

import me.oringo.oringoclient.events.impl.RenderChestEvent;
import net.minecraft.client.renderer.tileentity.TileEntityChestRenderer;
import net.minecraft.tileentity.TileEntityChest;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({TileEntityChestRenderer.class})
public class RendererChestMixin {
  @Inject(method = {"renderTileEntityAt(Lnet/minecraft/tileentity/TileEntityChest;DDDFI)V"}, at = {@At("HEAD")}, cancellable = true)
  public void onDrawChest(TileEntityChest paramTileEntityChest, double paramDouble1, double paramDouble2, double paramDouble3, float paramFloat, int paramInt, CallbackInfo paramCallbackInfo) {
    if ((new RenderChestEvent.Pre(paramTileEntityChest, paramDouble1, paramDouble2, paramDouble3, paramFloat, paramInt)).post())
      paramCallbackInfo.cancel(); 
  }
  
  @Inject(method = {"renderTileEntityAt(Lnet/minecraft/tileentity/TileEntityChest;DDDFI)V"}, at = {@At("RETURN")}, cancellable = true)
  public void onDrawChestPost(TileEntityChest paramTileEntityChest, double paramDouble1, double paramDouble2, double paramDouble3, float paramFloat, int paramInt, CallbackInfo paramCallbackInfo) {
    if ((new RenderChestEvent.Post(paramTileEntityChest, paramDouble1, paramDouble2, paramDouble3, paramFloat, paramInt)).post())
      paramCallbackInfo.cancel(); 
  }
}
