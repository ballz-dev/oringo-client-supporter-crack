package me.oringo.oringoclient.mixins.packet;

import net.minecraft.network.play.server.S27PacketExplosion;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin({S27PacketExplosion.class})
public interface S27Accessor {
  @Accessor("field_149152_f")
  void setMotionX(float paramFloat);
  
  @Accessor("field_149153_g")
  void setMotionY(float paramFloat);
  
  @Accessor("field_149159_h")
  void setMotionZ(float paramFloat);
}
