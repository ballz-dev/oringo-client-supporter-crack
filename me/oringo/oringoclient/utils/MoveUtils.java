package me.oringo.oringoclient.utils;

import me.oringo.oringoclient.qolfeatures.module.impl.render.Trial;
import net.minecraft.util.Vec3;

public class MoveUtils {
  public static double  = 0.015625D;
  
  public static Vec3 (Vec3 paramVec3) {
    return paramVec3.func_72441_c(-(Trial.mc.func_175598_ae()).field_78730_l, -(Trial.mc.func_175598_ae()).field_78731_m, -(Trial.mc.func_175598_ae()).field_78728_n);
  }
  
  public static float (float paramFloat1, float paramFloat2, float paramFloat3) {
    if (paramFloat2 < paramFloat3) {
      float f = paramFloat2;
      paramFloat2 = paramFloat3;
      paramFloat3 = f;
    } 
    return Math.max(Math.min(paramFloat2, paramFloat1), paramFloat3);
  }
}
