package me.oringo.oringoclient.mixins.packet;

import net.minecraft.network.play.client.C03PacketPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin({C03PacketPlayer.class})
public interface C03Accessor {
  @Accessor
  void setYaw(float paramFloat);
  
  @Accessor("x")
  void setX(double paramDouble);
  
  @Accessor
  void setPitch(float paramFloat);
  
  @Accessor("z")
  void setZ(double paramDouble);
  
  @Accessor
  void setOnGround(boolean paramBoolean);
  
  @Accessor("y")
  void setY(double paramDouble);
}
