package me.oringo.oringoclient.utils;

import me.oringo.oringoclient.qolfeatures.module.impl.other.Disabler;
import net.minecraft.item.ItemStack;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector4f;

public class PlayerUtils {
  public static boolean ;
  
  public static Vector4f (Vector4f paramVector4f, Matrix4f paramMatrix4f) {
    return new Vector4f(paramVector4f.x * paramMatrix4f.m00 + paramVector4f.y * paramMatrix4f.m10 + paramVector4f.z * paramMatrix4f.m20 + paramVector4f.w * paramMatrix4f.m30, paramVector4f.x * paramMatrix4f.m01 + paramVector4f.y * paramMatrix4f.m11 + paramVector4f.z * paramMatrix4f.m21 + paramVector4f.w * paramMatrix4f.m31, paramVector4f.x * paramMatrix4f.m02 + paramVector4f.y * paramMatrix4f.m12 + paramVector4f.z * paramMatrix4f.m22 + paramVector4f.w * paramMatrix4f.m32, paramVector4f.x * paramMatrix4f.m03 + paramVector4f.y * paramMatrix4f.m13 + paramVector4f.z * paramMatrix4f.m23 + paramVector4f.w * paramMatrix4f.m33);
  }
  
  public static void (float paramFloat) {
    (Disabler.()).field_74278_d = paramFloat;
  }
}
