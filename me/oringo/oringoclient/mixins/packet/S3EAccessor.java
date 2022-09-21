package me.oringo.oringoclient.mixins.packet;

import net.minecraft.network.play.server.S3EPacketTeams;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin({S3EPacketTeams.class})
public interface S3EAccessor {
  @Accessor("field_149316_d")
  void setScoreName2(String paramString);
  
  @Accessor("field_149319_c")
  void setScoreName(String paramString);
}
