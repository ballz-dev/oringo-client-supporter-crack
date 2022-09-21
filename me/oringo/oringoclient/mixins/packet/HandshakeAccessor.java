package me.oringo.oringoclient.mixins.packet;

import net.minecraft.network.handshake.client.C00Handshake;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin({C00Handshake.class})
public interface HandshakeAccessor {
  @Accessor
  void setProtocolVersion(int paramInt);
  
  @Accessor
  String getIp();
}
