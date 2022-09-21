package me.oringo.oringoclient.mixins.entity;

import net.minecraft.entity.EntityLivingBase;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin({EntityLivingBase.class})
public interface EntityLivingBaseAccessor {
  @Accessor
  void setJumpTicks(int paramInt);
  
  @Accessor
  int getJumpTicks();
  
  @Invoker("getArmSwingAnimationEnd")
  int getArmSwingAnimationEnd();
}
