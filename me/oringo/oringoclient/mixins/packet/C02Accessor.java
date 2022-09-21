package me.oringo.oringoclient.mixins.packet;

import net.minecraft.network.play.client.C02PacketUseEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin({C02PacketUseEntity.class})
public interface C02Accessor {
  @Accessor
  void setAction(C02PacketUseEntity.Action paramAction);
  
  @Accessor
  void setEntityId(int paramInt);
}
