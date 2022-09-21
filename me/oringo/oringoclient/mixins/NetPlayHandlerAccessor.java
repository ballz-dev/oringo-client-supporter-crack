package me.oringo.oringoclient.mixins;

import net.minecraft.client.network.NetHandlerPlayClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin({NetHandlerPlayClient.class})
public interface NetPlayHandlerAccessor {
  @Accessor("doneLoadingTerrain")
  boolean isDoneLoadingTerrain();
  
  @Accessor("doneLoadingTerrain")
  void setDoneLoadingTerrain(boolean paramBoolean);
}
