package me.oringo.oringoclient.mixins.block;

import java.util.List;
import me.oringo.oringoclient.OringoClient;
import me.oringo.oringoclient.events.impl.BlockBoundsEvent;
import me.oringo.oringoclient.extend.chunk.ChunkExtend;
import me.oringo.oringoclient.qolfeatures.module.impl.render.XRay;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.Event;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = {Block.class}, priority = 1)
public abstract class BlockMixin {
  @Shadow
  protected double field_149760_C;
  
  @Shadow
  protected double field_149756_F;
  
  @Overwrite
  public void func_180638_a(World paramWorld, BlockPos paramBlockPos, IBlockState paramIBlockState, AxisAlignedBB paramAxisAlignedBB, List<AxisAlignedBB> paramList, Entity paramEntity) {
    BlockBoundsEvent blockBoundsEvent = new BlockBoundsEvent((Block)this, func_180640_a(paramWorld, paramBlockPos, paramIBlockState), paramBlockPos, paramEntity);
    if (MinecraftForge.EVENT_BUS.post((Event)blockBoundsEvent))
      return; 
    if (blockBoundsEvent. != null && paramAxisAlignedBB.func_72326_a(blockBoundsEvent.))
      paramList.add(blockBoundsEvent.); 
  }
  
  @Inject(method = {"shouldSideBeRendered"}, at = {@At("HEAD")}, cancellable = true)
  public void shouldBerendererd(IBlockAccess paramIBlockAccess, BlockPos paramBlockPos, EnumFacing paramEnumFacing, CallbackInfoReturnable<Boolean> paramCallbackInfoReturnable) {
    if (OringoClient..() && 
      XRay..contains(this)) {
      BlockPos blockPos = paramBlockPos.func_177967_a(paramEnumFacing, -1);
      if (!XRay.) {
        Chunk chunk = OringoClient.mc.field_71441_e.func_175726_f(blockPos);
        if (((ChunkExtend)chunk).getGeneratedData(blockPos.func_177958_n(), blockPos.func_177956_o(), blockPos.func_177952_p())) {
          paramCallbackInfoReturnable.setReturnValue(Boolean.valueOf(true));
          return;
        } 
      } 
      if (XRay..() || !XRay.) {
        boolean bool = false;
        for (EnumFacing enumFacing : EnumFacing.values()) {
          BlockPos blockPos1 = blockPos.func_177971_a(enumFacing.func_176730_m());
          Block block = paramIBlockAccess.func_180495_p(blockPos1).func_177230_c();
          if (block instanceof net.minecraft.block.BlockAir || block instanceof net.minecraft.block.BlockLiquid) {
            if (!OringoClient.mc.field_71441_e.func_175668_a(blockPos1, false))
              return; 
            bool = true;
          } 
        } 
        if (bool)
          paramCallbackInfoReturnable.setReturnValue(Boolean.valueOf(true)); 
        return;
      } 
      paramCallbackInfoReturnable.setReturnValue(Boolean.valueOf(true));
    } 
  }
  
  @Inject(method = {"canRenderInLayer"}, at = {@At("HEAD")}, cancellable = true, remap = false)
  public void canRenderInLayer(EnumWorldBlockLayer paramEnumWorldBlockLayer, CallbackInfoReturnable<Boolean> paramCallbackInfoReturnable) {
    if (OringoClient..())
      paramCallbackInfoReturnable.setReturnValue(Boolean.valueOf((paramEnumWorldBlockLayer == (XRay..contains(this) ? EnumWorldBlockLayer.SOLID : EnumWorldBlockLayer.TRANSLUCENT)))); 
  }
  
  @Inject(method = {"getAmbientOcclusionLightValue"}, at = {@At("HEAD")}, cancellable = true)
  public void getLight(CallbackInfoReturnable<Float> paramCallbackInfoReturnable) {
    if (OringoClient..())
      paramCallbackInfoReturnable.setReturnValue(Float.valueOf(1.0F)); 
  }
  
  @Shadow
  public abstract boolean func_176225_a(IBlockAccess paramIBlockAccess, BlockPos paramBlockPos, EnumFacing paramEnumFacing);
  
  @Shadow
  public abstract void func_176224_k(World paramWorld, BlockPos paramBlockPos);
  
  @Shadow
  public abstract boolean func_149721_r();
  
  @Shadow
  public abstract String toString();
  
  @Shadow
  public abstract AxisAlignedBB func_180640_a(World paramWorld, BlockPos paramBlockPos, IBlockState paramIBlockState);
  
  @Shadow
  public abstract void func_149676_a(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6);
  
  @Shadow
  public abstract boolean func_181623_g();
  
  @Shadow
  public abstract void func_180663_b(World paramWorld, BlockPos paramBlockPos, IBlockState paramIBlockState);
  
  @Shadow
  public abstract void beginLeavesDecay(World paramWorld, BlockPos paramBlockPos);
  
  @Shadow
  public abstract Block func_149722_s();
  
  @Shadow
  public abstract int func_149738_a(World paramWorld);
}
