package me.oringo.oringoclient.mixins.packet;

import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin({S08PacketPlayerPosLook.class})
public interface S08Accessor {
  @Accessor("x")
  void setX(double paramDouble);
  
  @Accessor("pitch")
  void setPitch(float paramFloat);
  
  @Accessor("yaw")
  void setYaw(float paramFloat);
  
  @Accessor("z")
  void setZ(double paramDouble);
  
  @Accessor("y")
  void setY(double paramDouble);
}
