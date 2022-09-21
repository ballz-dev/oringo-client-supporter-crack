package me.oringo.oringoclient.mixins.entity;

import me.oringo.oringoclient.KEY;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.codec.digest.DigestUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = {AbstractClientPlayer.class}, priority = 1001)
public abstract class AbstractClientPlayerMixin extends PlayerMixin {
  private ResourceLocation getCape(String paramString) {
    return (ResourceLocation)KEY..get(DigestUtils.sha256Hex(paramString));
  }
  
  @Inject(method = {"getLocationCape"}, at = {@At("HEAD")}, cancellable = true)
  public void oringo$getLocationCape(CallbackInfoReturnable<ResourceLocation> paramCallbackInfoReturnable) {
    ResourceLocation resourceLocation = getCape(((AbstractClientPlayer)this).func_110124_au().toString());
    if (resourceLocation != null)
      paramCallbackInfoReturnable.setReturnValue(resourceLocation); 
  }
  
  @Shadow
  public abstract boolean func_152122_n();
}
