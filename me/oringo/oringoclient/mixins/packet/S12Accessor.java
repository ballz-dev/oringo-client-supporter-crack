package me.oringo.oringoclient.mixins.packet;

import net.minecraft.network.play.server.S12PacketEntityVelocity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin({S12PacketEntityVelocity.class})
public interface S12Accessor {
  @Accessor
  void setMotionZ(int paramInt);
  
  @Accessor
  void setMotionX(int paramInt);
  
  @Accessor
  void setMotionY(int paramInt);
}
