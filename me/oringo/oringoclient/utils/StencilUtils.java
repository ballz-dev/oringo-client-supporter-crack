package me.oringo.oringoclient.utils;

import me.oringo.oringoclient.OringoClient;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import org.lwjgl.opengl.GL11;

public class StencilUtils {
  public static Minecraft mc = Minecraft.func_71410_x();
  
  public static void () {
    GL11.glStencilFunc(512, 0, 15);
    GL11.glStencilOp(7681, 7681, 7681);
    GL11.glPolygonMode(1032, 6914);
  }
  
  public static boolean (double paramDouble1, double paramDouble2) {
    BlockPos blockPos = new BlockPos(OringoClient.mc.field_71439_g.field_70165_t, OringoClient.mc.field_71439_g.field_70163_u, OringoClient.mc.field_71439_g.field_70161_v);
    if (!OringoClient.mc.field_71441_e.func_175667_e(blockPos))
      return false; 
    AxisAlignedBB axisAlignedBB = OringoClient.mc.field_71439_g.func_174813_aQ().func_72317_d(paramDouble1, 0.0D, paramDouble2);
    return OringoClient.mc.field_71441_e.func_72945_a((Entity)OringoClient.mc.field_71439_g, new AxisAlignedBB(axisAlignedBB.field_72340_a, 0.0D, axisAlignedBB.field_72339_c, axisAlignedBB.field_72336_d, axisAlignedBB.field_72337_e, axisAlignedBB.field_72334_f)).isEmpty();
  }
}
