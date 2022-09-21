package me.oringo.oringoclient.mixins.entity;

import net.minecraft.client.entity.EntityPlayerSP;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin({EntityPlayerSP.class})
public interface PlayerSPAccessor {
  @Accessor
  float getLastReportedPitch();
  
  @Accessor
  double getLastReportedPosX();
  
  @Accessor
  void setLastReportedPosY(double paramDouble);
  
  @Accessor
  boolean getServerSneakState();
  
  @Accessor
  double getLastReportedPosY();
  
  @Accessor
  void setLastReportedPosZ(double paramDouble);
  
  @Accessor
  void setServerSprintState(boolean paramBoolean);
  
  @Accessor
  void setLastReportedYaw(float paramFloat);
  
  @Accessor
  double getLastReportedPosZ();
  
  @Accessor
  void setLastReportedPitch(float paramFloat);
  
  @Accessor
  float getLastReportedYaw();
  
  @Accessor
  void setLastReportedPosX(double paramDouble);
  
  @Accessor
  void setServerSneakState(boolean paramBoolean);
  
  @Accessor
  boolean getServerSprintState();
}
