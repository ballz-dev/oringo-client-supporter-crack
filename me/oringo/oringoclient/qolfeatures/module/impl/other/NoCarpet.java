package me.oringo.oringoclient.qolfeatures.module.impl.other;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import me.oringo.oringoclient.events.impl.BlockBoundsEvent;
import me.oringo.oringoclient.qolfeatures.module.Module;
import me.oringo.oringoclient.qolfeatures.module.impl.macro.RevTrader;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class NoCarpet extends Module {
  public static void (float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, int paramInt) {
    if (paramFloat1 < paramFloat3) {
      float f = paramFloat1;
      paramFloat1 = paramFloat3;
      paramFloat3 = f;
    } 
    if (paramFloat2 < paramFloat4) {
      float f = paramFloat2;
      paramFloat2 = paramFloat4;
      paramFloat4 = f;
    } 
    float f1 = (paramInt >> 24 & 0xFF) / 255.0F;
    float f2 = (paramInt >> 16 & 0xFF) / 255.0F;
    float f3 = (paramInt >> 8 & 0xFF) / 255.0F;
    float f4 = (paramInt & 0xFF) / 255.0F;
    Tessellator tessellator = Tessellator.func_178181_a();
    WorldRenderer worldRenderer = tessellator.func_178180_c();
    GlStateManager.func_179147_l();
    GlStateManager.func_179090_x();
    GlStateManager.func_179120_a(770, 771, 1, 0);
    GlStateManager.func_179131_c(f2, f3, f4, f1);
    worldRenderer.func_181668_a(7, DefaultVertexFormats.field_181705_e);
    worldRenderer.func_181662_b(paramFloat1, paramFloat4, 0.0D).func_181675_d();
    worldRenderer.func_181662_b(paramFloat3, paramFloat4, 0.0D).func_181675_d();
    worldRenderer.func_181662_b(paramFloat3, paramFloat2, 0.0D).func_181675_d();
    worldRenderer.func_181662_b(paramFloat1, paramFloat2, 0.0D).func_181675_d();
    tessellator.func_78381_a();
    GlStateManager.func_179124_c(1.0F, 1.0F, 1.0F);
    GlStateManager.func_179098_w();
    GlStateManager.func_179084_k();
  }
  
  static {
  
  }
  
  public NoCarpet() {
    super("No Carpet", Module.Category.OTHER);
  }
  
  @SubscribeEvent
  public void (BlockBoundsEvent paramBlockBoundsEvent) {
    if (() && paramBlockBoundsEvent. == Blocks.field_150404_cg) {
      BlockPos blockPos = paramBlockBoundsEvent..func_177977_b();
      if (!mc.field_71441_e.func_180495_p(blockPos).func_177230_c().func_176205_b((IBlockAccess)mc.field_71441_e, blockPos))
        paramBlockBoundsEvent.cancel(); 
    } 
  }
  
  public static EntityZombie () {
    List<EntityZombie> list = RevTrader.mc.field_71441_e.func_175644_a(EntityZombie.class, RevTrader::);
    Objects.requireNonNull(RevTrader.mc.field_71439_g);
    list.sort(Comparator.comparingDouble(RevTrader.mc.field_71439_g::func_70068_e));
    return list.isEmpty() ? null : list.get(0);
  }
}
