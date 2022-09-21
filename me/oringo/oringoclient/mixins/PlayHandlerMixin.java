package me.oringo.oringoclient.mixins;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.NetworkManager;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value = {NetHandlerPlayClient.class}, priority = 1)
public abstract class PlayHandlerMixin {
  @Shadow
  private WorldClient field_147300_g;
  
  @Shadow
  private Minecraft field_147299_f;
  
  @Shadow
  @Final
  private NetworkManager field_147302_e;
  
  @Shadow
  private boolean field_147309_h;
}
