package me.oringo.oringoclient.mixins;

import me.oringo.oringoclient.events.impl.PreAttackEvent;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({PlayerControllerMP.class})
public class PlayerControllerMixin {
  @Inject(method = {"attackEntity"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/PlayerControllerMP;syncCurrentPlayItem()V")})
  public void attackEntity(EntityPlayer paramEntityPlayer, Entity paramEntity, CallbackInfo paramCallbackInfo) {
    if ((new PreAttackEvent(paramEntity)).post())
      paramCallbackInfo.cancel(); 
  }
}
