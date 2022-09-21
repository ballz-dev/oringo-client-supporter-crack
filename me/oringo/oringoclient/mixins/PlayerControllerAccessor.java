package me.oringo.oringoclient.mixins;

import net.minecraft.client.multiplayer.PlayerControllerMP;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin({PlayerControllerMP.class})
public interface PlayerControllerAccessor {
  @Accessor
  void setBlockHitDelay(int paramInt);
  
  @Accessor
  int getCurrentPlayerItem();
  
  @Accessor
  void setCurrentPlayerItem(int paramInt);
  
  @Accessor
  int getBlockHitDelay();
  
  @Accessor
  void setCurBlockDamageMP(float paramFloat);
  
  @Accessor
  float getCurBlockDamageMP();
}
