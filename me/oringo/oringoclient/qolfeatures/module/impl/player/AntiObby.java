package me.oringo.oringoclient.qolfeatures.module.impl.player;

import me.oringo.oringoclient.OringoClient;
import me.oringo.oringoclient.events.OringoEvent;
import me.oringo.oringoclient.events.impl.MotionUpdateEvent;
import me.oringo.oringoclient.events.impl.PacketSentEvent;
import me.oringo.oringoclient.qolfeatures.module.Module;
import me.oringo.oringoclient.qolfeatures.module.impl.other.SimulatorAura;
import me.oringo.oringoclient.qolfeatures.module.impl.skyblock.AutoWeirdos;
import me.oringo.oringoclient.qolfeatures.module.settings.Setting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.BooleanSetting;
import me.oringo.oringoclient.ui.hud.Component;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Mouse;

public class AntiObby extends Module {
  public static Block[] ;
  
  public static BooleanSetting  = new BooleanSetting("Cancel pos packets", true);
  
  public static boolean (TileEntityChest paramTileEntityChest) {
    return !AutoWeirdos.mc.field_71441_e.func_175647_a(EntityArmorStand.class, new AxisAlignedBB((paramTileEntityChest.func_174877_v().func_177958_n() - 2), paramTileEntityChest.func_174877_v().func_177956_o(), (paramTileEntityChest.func_174877_v().func_177952_p() - 2), (paramTileEntityChest.func_174877_v().func_177958_n() + 2), (paramTileEntityChest.func_174877_v().func_177956_o() + 2), (paramTileEntityChest.func_174877_v().func_177952_p() + 2)), AutoWeirdos::).isEmpty();
  }
  
  public static void () {
    NoFall.(String.valueOf((new StringBuilder()).append(OringoClient.mc.field_71412_D).append("/config/OringoClient/OringoClient.json")), false);
  }
  
  @SubscribeEvent
  public void (PacketSentEvent paramPacketSentEvent) {
    if (!() || !.())
      return; 
    if (paramPacketSentEvent. instanceof C03PacketPlayer && mc.field_71439_g.field_70122_E) {
      C03PacketPlayer c03PacketPlayer = (C03PacketPlayer)paramPacketSentEvent.;
      if (!c03PacketPlayer.func_149466_j() || (c03PacketPlayer.func_149464_c() == mc.field_71439_g.field_70169_q && c03PacketPlayer.func_149472_e() == mc.field_71439_g.field_70166_s && c03PacketPlayer.func_149467_d() == mc.field_71439_g.field_70167_r)) {
        BlockPos blockPos = new BlockPos(mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u, mc.field_71439_g.field_70161_v);
        if (!mc.field_71441_e.func_180495_p(blockPos.func_177977_b()).func_177230_c().func_176205_b((IBlockAccess)mc.field_71441_e, blockPos.func_177977_b()) && OringoEvent.(true))
          paramPacketSentEvent.setCanceled(true); 
      } 
    } 
  }
  
  static {
     = new Block[] { Blocks.field_150347_e, Blocks.field_150343_Z };
  }
  
  @SubscribeEvent
  public void (MotionUpdateEvent.Pre paramPre) {
    if (!() || mc.field_71442_b.func_181040_m())
      return; 
    BlockPos blockPos = new BlockPos(mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u, mc.field_71439_g.field_70161_v);
    if (OringoEvent.(false)) {
      BlockPos blockPos1 = blockPos.func_177977_b();
      if (!(mc.field_71441_e.func_180495_p(blockPos1).func_177230_c() instanceof net.minecraft.block.BlockAir)) {
        paramPre.setPitch(90.0F);
        mc.field_71439_g.field_71071_by.field_70461_c = SimulatorAura.(blockPos1);
        if (mc.field_71442_b.func_180512_c(blockPos1, EnumFacing.UP)) {
          mc.field_71452_i.func_180532_a(blockPos1, EnumFacing.UP);
          mc.field_71439_g.func_71038_i();
        } 
      } 
    } 
  }
  
  public static int () {
    int i = SimulatorAura.().func_78328_b();
    return i - Mouse.getY() * i / Component.mc.field_71440_d - 1;
  }
  
  public AntiObby() {
    super("Anti Obsidian", Module.Category.);
    (new Setting[] { (Setting) });
  }
  
  public static void (float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, int paramInt1, int paramInt2) {
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
    float f1 = (paramInt1 >> 24 & 0xFF) / 255.0F;
    float f2 = (paramInt1 >> 16 & 0xFF) / 255.0F;
    float f3 = (paramInt1 >> 8 & 0xFF) / 255.0F;
    float f4 = (paramInt1 & 0xFF) / 255.0F;
    float f5 = (paramInt2 >> 24 & 0xFF) / 255.0F;
    float f6 = (paramInt2 >> 16 & 0xFF) / 255.0F;
    float f7 = (paramInt2 >> 8 & 0xFF) / 255.0F;
    float f8 = (paramInt2 & 0xFF) / 255.0F;
    Tessellator tessellator = Tessellator.func_178181_a();
    WorldRenderer worldRenderer = tessellator.func_178180_c();
    GlStateManager.func_179147_l();
    GlStateManager.func_179090_x();
    GlStateManager.func_179120_a(770, 771, 1, 0);
    GlStateManager.func_179103_j(7425);
    GlStateManager.func_179131_c(f2, f3, f4, f1);
    worldRenderer.func_181668_a(7, DefaultVertexFormats.field_181706_f);
    worldRenderer.func_181662_b(paramFloat1, paramFloat4, 0.0D).func_181666_a(f6, f7, f8, f5).func_181675_d();
    worldRenderer.func_181662_b(paramFloat3, paramFloat4, 0.0D).func_181666_a(f2, f3, f4, f1).func_181675_d();
    worldRenderer.func_181662_b(paramFloat3, paramFloat2, 0.0D).func_181666_a(f2, f3, f4, f1).func_181675_d();
    worldRenderer.func_181662_b(paramFloat1, paramFloat2, 0.0D).func_181666_a(f6, f7, f8, f5).func_181675_d();
    tessellator.func_78381_a();
    GlStateManager.func_179103_j(7424);
    GlStateManager.func_179098_w();
    GlStateManager.func_179084_k();
  }
}
