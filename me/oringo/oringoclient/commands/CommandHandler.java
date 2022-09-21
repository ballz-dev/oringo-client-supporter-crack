package me.oringo.oringoclient.commands;

import java.awt.Color;
import java.util.HashMap;
import me.oringo.oringoclient.OringoClient;
import me.oringo.oringoclient.qolfeatures.module.impl.movement.Scaffold;
import me.oringo.oringoclient.qolfeatures.module.impl.other.AntiNicker;
import me.oringo.oringoclient.qolfeatures.module.impl.player.InvManager;
import me.oringo.oringoclient.utils.Rotation;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import org.lwjgl.opengl.GL11;

public class CommandHandler {
  public static HashMap<String, Command>  = new HashMap<>();
  
  public static Rotation (double paramDouble1, double paramDouble2, double paramDouble3) {
    double d1 = paramDouble1 - OringoClient.mc.field_71439_g.field_70165_t;
    double d2 = paramDouble2 - OringoClient.mc.field_71439_g.field_70163_u + OringoClient.mc.field_71439_g.func_70047_e();
    double d3 = paramDouble3 - OringoClient.mc.field_71439_g.field_70161_v;
    double d4 = MathHelper.func_76133_a(d1 * d1 + d3 * d3);
    float f1 = (float)(Math.atan2(d3, d1) * 180.0D / Math.PI) - 90.0F;
    float f2 = (float)-(Math.atan2(d2, d4) * 180.0D / Math.PI);
    return new Rotation(f1, f2);
  }
  
  public static void (Entity paramEntity, float paramFloat, Color paramColor) {
    GL11.glBlendFunc(770, 771);
    GL11.glEnable(3042);
    GL11.glLineWidth(1.5F);
    GL11.glDisable(3553);
    GL11.glDisable(2929);
    GL11.glDepthMask(false);
    AntiNicker.(paramColor);
    RenderGlobal.func_181561_a(new AxisAlignedBB((paramEntity.func_174813_aQ()).field_72340_a - paramEntity.field_70165_t + paramEntity.field_70169_q + (paramEntity.field_70165_t - paramEntity.field_70169_q) * paramFloat - (Minecraft.func_71410_x().func_175598_ae()).field_78730_l, (paramEntity.func_174813_aQ()).field_72338_b - paramEntity.field_70163_u + paramEntity.field_70167_r + (paramEntity.field_70163_u - paramEntity.field_70167_r) * paramFloat - (Minecraft.func_71410_x().func_175598_ae()).field_78731_m, (paramEntity.func_174813_aQ()).field_72339_c - paramEntity.field_70161_v + paramEntity.field_70166_s + (paramEntity.field_70161_v - paramEntity.field_70166_s) * paramFloat - (Minecraft.func_71410_x().func_175598_ae()).field_78728_n, (paramEntity.func_174813_aQ()).field_72336_d - paramEntity.field_70165_t + paramEntity.field_70169_q + (paramEntity.field_70165_t - paramEntity.field_70169_q) * paramFloat - (Minecraft.func_71410_x().func_175598_ae()).field_78730_l, (paramEntity.func_174813_aQ()).field_72337_e - paramEntity.field_70163_u + paramEntity.field_70167_r + (paramEntity.field_70163_u - paramEntity.field_70167_r) * paramFloat - (Minecraft.func_71410_x().func_175598_ae()).field_78731_m, (paramEntity.func_174813_aQ()).field_72334_f - paramEntity.field_70161_v + paramEntity.field_70166_s + (paramEntity.field_70161_v - paramEntity.field_70166_s) * paramFloat - (Minecraft.func_71410_x().func_175598_ae()).field_78728_n));
    GL11.glEnable(3553);
    GL11.glEnable(2929);
    GL11.glDepthMask(true);
    GL11.glDisable(3042);
  }
  
  public static MovingObjectPosition (float paramFloat1, float paramFloat2) {
    Vec3 vec31 = Scaffold.mc.field_71439_g.func_174824_e(1.0F);
    Vec3 vec32 = InvManager.(paramFloat1, paramFloat2);
    Vec3 vec33 = vec31.func_72441_c(vec32.field_72450_a * Scaffold.mc.field_71442_b.func_78757_d(), vec32.field_72448_b * Scaffold.mc.field_71442_b.func_78757_d(), vec32.field_72449_c * Scaffold.mc.field_71442_b.func_78757_d());
    return Scaffold.mc.field_71441_e.func_72933_a(vec31, vec33);
  }
  
  public static int () {
    double d1 = System.currentTimeMillis() / 1000.0D;
    double d2 = Math.floor(d1 - 1.560276E9D);
    return (int)(d2 / 446400.0D + 1.0D);
  }
}
