package me.oringo.oringoclient.qolfeatures.module.impl.render;

import java.awt.Color;
import me.oringo.oringoclient.OringoClient;
import me.oringo.oringoclient.events.impl.KeyPressEvent;
import me.oringo.oringoclient.qolfeatures.module.Module;
import me.oringo.oringoclient.qolfeatures.module.impl.other.NoCarpet;
import me.oringo.oringoclient.qolfeatures.module.impl.player.ArmorSwap;
import me.oringo.oringoclient.utils.font.Fonts;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;

public class Nametags extends Module {
  @SubscribeEvent
  public void (RenderLivingEvent.Specials.Pre<EntityLivingBase> paramPre) {
    if (() && ArmorSwap.((Entity)paramPre.entity) && paramPre.entity instanceof net.minecraft.entity.player.EntityPlayer && paramPre.entity != mc.field_71439_g) {
      paramPre.setCanceled(true);
      GlStateManager.func_179092_a(516, 0.1F);
      String str = paramPre.entity.func_70005_c_();
      double d1 = paramPre.x;
      double d2 = paramPre.y;
      double d3 = paramPre.z;
      float f1 = Math.max(1.4F, paramPre.entity.func_70032_d((Entity)mc.field_71439_g) / 10.0F);
      float f2 = 0.016666668F * f1;
      GlStateManager.func_179094_E();
      GlStateManager.func_179109_b((float)d1 + 0.0F, (float)d2 + paramPre.entity.field_70131_O + 0.5F, (float)d3);
      GL11.glNormal3f(0.0F, 1.0F, 0.0F);
      GlStateManager.func_179114_b(-(mc.func_175598_ae()).field_78735_i, 0.0F, 1.0F, 0.0F);
      GlStateManager.func_179114_b((mc.func_175598_ae()).field_78732_j, 1.0F, 0.0F, 0.0F);
      GlStateManager.func_179152_a(-f2, -f2, f2);
      GlStateManager.func_179140_f();
      GlStateManager.func_179132_a(false);
      GlStateManager.func_179097_i();
      GlStateManager.func_179147_l();
      GlStateManager.func_179120_a(770, 771, 1, 0);
      float f3 = (float)Math.max(Fonts..(str) / 2.0D, 30.0D);
      GlStateManager.func_179090_x();
      NoCarpet.(-f3 - 3.0F, (Fonts..() + 3), f3 + 3.0F, -3.0F, (new Color(20, 20, 20, 80)).getRGB());
      NoCarpet.(-f3 - 3.0F, (Fonts..() + 3), (float)((f3 + 3.0F) * (KeyPressEvent.((paramPre.entity.func_110143_aJ() / paramPre.entity.func_110138_aP()), 1.0D, 0.0D) - 0.5D) * 2.0D), (Fonts..() + 2), OringoClient..(paramPre.entity.func_145782_y()).getRGB());
      GlStateManager.func_179098_w();
      Fonts..(str, -Fonts..(str) / 2.0D, 0.0D, Color.WHITE.getRGB());
      GlStateManager.func_179126_j();
      GlStateManager.func_179132_a(true);
      Fonts..(str, -Fonts..(str) / 2.0D, 0.0D, Color.WHITE.getRGB());
      GlStateManager.func_179145_e();
      GlStateManager.func_179084_k();
      GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
      GlStateManager.func_179121_F();
    } 
  }
  
  static {
  
  }
  
  public Nametags() {
    super("Nametags", Module.Category.);
  }
  
  public static boolean (float paramFloat, double paramDouble1, double paramDouble2) {
    BlockPos blockPos = new BlockPos(OringoClient.mc.field_71439_g.field_70165_t, OringoClient.mc.field_71439_g.field_70163_u, OringoClient.mc.field_71439_g.field_70161_v);
    if (!OringoClient.mc.field_71441_e.func_175667_e(blockPos))
      return false; 
    AxisAlignedBB axisAlignedBB = OringoClient.mc.field_71439_g.func_174813_aQ().func_72317_d(paramDouble1, 0.0D, paramDouble2);
    return OringoClient.mc.field_71441_e.func_72953_d(new AxisAlignedBB(axisAlignedBB.field_72340_a, axisAlignedBB.field_72338_b - paramFloat, axisAlignedBB.field_72339_c, axisAlignedBB.field_72336_d, axisAlignedBB.field_72337_e, axisAlignedBB.field_72334_f));
  }
}
