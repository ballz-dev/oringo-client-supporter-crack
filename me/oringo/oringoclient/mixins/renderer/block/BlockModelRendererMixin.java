package me.oringo.oringoclient.mixins.renderer.block;

import me.oringo.oringoclient.OringoClient;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockModelRenderer;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ReportedException;
import net.minecraft.world.IBlockAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin({BlockModelRenderer.class})
public abstract class BlockModelRendererMixin {
  @Shadow
  public abstract boolean func_178265_a(IBlockAccess paramIBlockAccess, IBakedModel paramIBakedModel, Block paramBlock, BlockPos paramBlockPos, WorldRenderer paramWorldRenderer, boolean paramBoolean);
  
  @Shadow
  public abstract boolean func_178258_b(IBlockAccess paramIBlockAccess, IBakedModel paramIBakedModel, Block paramBlock, BlockPos paramBlockPos, WorldRenderer paramWorldRenderer, boolean paramBoolean);
  
  @Shadow
  public abstract boolean func_178259_a(IBlockAccess paramIBlockAccess, IBakedModel paramIBakedModel, IBlockState paramIBlockState, BlockPos paramBlockPos, WorldRenderer paramWorldRenderer);
  
  @Overwrite
  public boolean func_178267_a(IBlockAccess paramIBlockAccess, IBakedModel paramIBakedModel, IBlockState paramIBlockState, BlockPos paramBlockPos, WorldRenderer paramWorldRenderer, boolean paramBoolean) {
    boolean bool = ((Minecraft.func_71379_u() && paramIBlockState.func_177230_c().func_149750_m() == 0 && paramIBakedModel.func_177555_b()) || OringoClient..()) ? true : false;
    try {
      Block block = paramIBlockState.func_177230_c();
      return bool ? func_178265_a(paramIBlockAccess, paramIBakedModel, block, paramBlockPos, paramWorldRenderer, paramBoolean) : func_178258_b(paramIBlockAccess, paramIBakedModel, block, paramBlockPos, paramWorldRenderer, paramBoolean);
    } catch (Throwable throwable) {
      CrashReport crashReport = CrashReport.func_85055_a(throwable, "Tesselating block model");
      CrashReportCategory crashReportCategory = crashReport.func_85058_a("Block model being tesselated");
      CrashReportCategory.func_175750_a(crashReportCategory, paramBlockPos, paramIBlockState);
      crashReportCategory.func_71507_a("Using AO", Boolean.valueOf(bool));
      throw new ReportedException(crashReport);
    } 
  }
}
