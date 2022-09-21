package me.oringo.oringoclient.qolfeatures.module.impl.other;

import com.mojang.realmsclient.gui.ChatFormatting;
import io.netty.channel.ChannelHandlerContext;
import java.util.Arrays;
import me.oringo.oringoclient.OringoClient;
import me.oringo.oringoclient.events.impl.PacketReceivedEvent;
import me.oringo.oringoclient.qolfeatures.module.Module;
import me.oringo.oringoclient.qolfeatures.module.settings.Setting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.NumberSetting;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.server.S22PacketMultiBlockChange;
import net.minecraft.network.play.server.S23PacketBlockChange;
import net.minecraft.network.play.server.S25PacketBlockBreakAnim;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.util.BlockPos;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;

public class TntRunPing extends Module {
  public NumberSetting  = new NumberSetting("Ping", 2000.0D, 1.0D, 2000.0D, 1.0D);
  
  public TntRunPing() {
    super("TNT Run ping", 0, Module.Category.OTHER);
    (new Setting[] { (Setting)this. });
  }
  
  public void (ChannelHandlerContext paramChannelHandlerContext, BlockPos paramBlockPos, IBlockState paramIBlockState) {
    // Byte code:
    //   0: aload_0
    //   1: invokevirtual  : ()Z
    //   4: ifne -> 8
    //   7: return
    //   8: invokestatic func_71410_x : ()Lnet/minecraft/client/Minecraft;
    //   11: getfield field_71441_e : Lnet/minecraft/client/multiplayer/WorldClient;
    //   14: aload_2
    //   15: getstatic net/minecraft/init/Blocks.field_150325_L : Lnet/minecraft/block/Block;
    //   18: invokevirtual func_176223_P : ()Lnet/minecraft/block/state/IBlockState;
    //   21: invokevirtual func_175656_a : (Lnet/minecraft/util/BlockPos;Lnet/minecraft/block/state/IBlockState;)Z
    //   24: pop
    //   25: new java/lang/Thread
    //   28: dup
    //   29: aload_0
    //   30: aload_2
    //   31: aload_3
    //   32: <illegal opcode> run : (Lme/oringo/oringoclient/qolfeatures/module/impl/other/TntRunPing;Lnet/minecraft/util/BlockPos;Lnet/minecraft/block/state/IBlockState;)Ljava/lang/Runnable;
    //   37: invokespecial <init> : (Ljava/lang/Runnable;)V
    //   40: invokevirtual start : ()V
    //   43: return
  }
  
  @SubscribeEvent
  public void (PacketReceivedEvent paramPacketReceivedEvent) {
    if (!())
      return; 
    try {
      ScoreObjective scoreObjective = mc.field_71439_g.func_96123_co().func_96539_a(1);
      if (!Arrays.<String>asList(new String[] { "TNT RUN", "PVP RUN" }).contains(ChatFormatting.stripFormatting(scoreObjective.func_96678_d())))
        return; 
    } catch (Exception exception) {
      return;
    } 
    if (paramPacketReceivedEvent. instanceof S22PacketMultiBlockChange && (((S22PacketMultiBlockChange)paramPacketReceivedEvent.).func_179844_a()).length <= 10) {
      paramPacketReceivedEvent.setCanceled(true);
      for (S22PacketMultiBlockChange.BlockUpdateData blockUpdateData : ((S22PacketMultiBlockChange)paramPacketReceivedEvent.).func_179844_a())
        (paramPacketReceivedEvent., blockUpdateData.func_180090_a(), blockUpdateData.func_180088_c()); 
    } 
    if (paramPacketReceivedEvent. instanceof S23PacketBlockChange) {
      if (OringoClient..contains(((S23PacketBlockChange)paramPacketReceivedEvent.).func_179827_b()))
        paramPacketReceivedEvent.setCanceled(true); 
      if (!(Minecraft.func_71410_x()).field_71441_e.func_180495_p(((S23PacketBlockChange)paramPacketReceivedEvent.).func_179827_b()).func_177230_c().equals(Blocks.field_150325_L) && ((S23PacketBlockChange)paramPacketReceivedEvent.).func_180728_a().func_177230_c().equals(Blocks.field_150350_a)) {
        paramPacketReceivedEvent.setCanceled(true);
        (paramPacketReceivedEvent., ((S23PacketBlockChange)paramPacketReceivedEvent.).func_179827_b(), ((S23PacketBlockChange)paramPacketReceivedEvent.).func_180728_a());
      } 
    } 
  }
  
  public static void (Entity paramEntity, String paramString, double paramDouble1, double paramDouble2, double paramDouble3, int paramInt) {
    double d = paramEntity.func_70068_e((OringoClient.mc.func_175598_ae()).field_78734_h);
    if (d <= (paramInt * paramInt)) {
      FontRenderer fontRenderer = OringoClient.mc.func_175598_ae().func_78716_a();
      float f1 = 1.6F;
      float f2 = 0.016666668F * f1;
      GlStateManager.func_179094_E();
      GlStateManager.func_179109_b((float)paramDouble1 + 0.0F, (float)paramDouble2 + paramEntity.field_70131_O + 0.5F, (float)paramDouble3);
      GL11.glNormal3f(0.0F, 1.0F, 0.0F);
      GlStateManager.func_179114_b(-(OringoClient.mc.func_175598_ae()).field_78735_i, 0.0F, 1.0F, 0.0F);
      GlStateManager.func_179114_b((OringoClient.mc.func_175598_ae()).field_78732_j, 1.0F, 0.0F, 0.0F);
      GlStateManager.func_179152_a(-f2, -f2, f2);
      GlStateManager.func_179140_f();
      GlStateManager.func_179132_a(false);
      GlStateManager.func_179097_i();
      GlStateManager.func_179147_l();
      GlStateManager.func_179120_a(770, 771, 1, 0);
      Tessellator tessellator = Tessellator.func_178181_a();
      WorldRenderer worldRenderer = tessellator.func_178180_c();
      byte b = 0;
      if (paramString.equals("deadmau5"))
        b = -10; 
      int i = fontRenderer.func_78256_a(paramString) / 2;
      GlStateManager.func_179090_x();
      worldRenderer.func_181668_a(7, DefaultVertexFormats.field_181706_f);
      worldRenderer.func_181662_b((-i - 1), (-1 + b), 0.0D).func_181666_a(0.0F, 0.0F, 0.0F, 0.25F).func_181675_d();
      worldRenderer.func_181662_b((-i - 1), (8 + b), 0.0D).func_181666_a(0.0F, 0.0F, 0.0F, 0.25F).func_181675_d();
      worldRenderer.func_181662_b((i + 1), (8 + b), 0.0D).func_181666_a(0.0F, 0.0F, 0.0F, 0.25F).func_181675_d();
      worldRenderer.func_181662_b((i + 1), (-1 + b), 0.0D).func_181666_a(0.0F, 0.0F, 0.0F, 0.25F).func_181675_d();
      tessellator.func_78381_a();
      GlStateManager.func_179098_w();
      fontRenderer.func_78276_b(paramString, -fontRenderer.func_78256_a(paramString) / 2, b, 553648127);
      GlStateManager.func_179126_j();
      GlStateManager.func_179132_a(true);
      fontRenderer.func_78276_b(paramString, -fontRenderer.func_78256_a(paramString) / 2, b, -1);
      GlStateManager.func_179145_e();
      GlStateManager.func_179084_k();
      GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
      GlStateManager.func_179121_F();
    } 
  }
}
