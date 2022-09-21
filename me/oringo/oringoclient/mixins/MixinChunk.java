package me.oringo.oringoclient.mixins;

import me.oringo.oringoclient.events.impl.BlockChangeEvent;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockPos;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.Event;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({Chunk.class})
public abstract class MixinChunk {
  @Inject(method = {"setBlockState"}, at = {@At("HEAD")}, cancellable = true)
  private void onBlockChange(BlockPos paramBlockPos, IBlockState paramIBlockState, CallbackInfoReturnable<IBlockState> paramCallbackInfoReturnable) {
    IBlockState iBlockState = func_177435_g(paramBlockPos);
    if (paramIBlockState != null && !paramIBlockState.equals(iBlockState) && 
      MinecraftForge.EVENT_BUS.post((Event)new BlockChangeEvent(paramBlockPos, paramIBlockState, iBlockState)))
      paramCallbackInfoReturnable.setReturnValue(paramIBlockState); 
  }
  
  @Shadow
  public abstract IBlockState func_177435_g(BlockPos paramBlockPos);
}
