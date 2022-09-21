package me.oringo.oringoclient.qolfeatures.module.settings.impl;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import me.oringo.oringoclient.qolfeatures.module.impl.combat.AimAssist;
import me.oringo.oringoclient.qolfeatures.module.settings.Setting;
import me.oringo.oringoclient.utils.PlayerUtils;
import me.oringo.oringoclient.utils.Returnable;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

public class NumberSetting extends Setting {
  public double ;
  
  public double ;
  
  public double ;
  
  @Expose
  @SerializedName("value")
  public double ;
  
  public double () {
    return this.;
  }
  
  public double () {
    return this.;
  }
  
  public void (double paramDouble) {
    this. = paramDouble;
  }
  
  public void (double paramDouble) {
    this. = paramDouble;
  }
  
  public void (double paramDouble) {
    this. = paramDouble;
  }
  
  public double () {
    return this.;
  }
  
  public void (double paramDouble) {
    this. = paramDouble;
  }
  
  public void (double paramDouble) {
    paramDouble = AimAssist.(paramDouble, (), ());
    paramDouble = Math.round(paramDouble * 1.0D / ()) / 1.0D / ();
    this. = paramDouble;
  }
  
  public double () {
    return this.;
  }
  
  public NumberSetting(String paramString, double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4) {
    super(paramString);
    this. = paramDouble1;
    this. = paramDouble2;
    this. = paramDouble3;
    this. = paramDouble4;
  }
  
  public NumberSetting(String paramString, double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, Returnable<Boolean> paramReturnable) {
    super(paramString, paramReturnable);
    this. = paramDouble1;
    this. = paramDouble2;
    this. = paramDouble3;
    this. = paramDouble4;
  }
  
  public static Vector2f (Vector3f paramVector3f, Matrix4f paramMatrix4f1, Matrix4f paramMatrix4f2, int paramInt1, int paramInt2) {
    Vector4f vector4f = PlayerUtils.(PlayerUtils.(new Vector4f(paramVector3f.x, paramVector3f.y, paramVector3f.z, 1.0F), paramMatrix4f1), paramMatrix4f2);
    Vector3f vector3f = new Vector3f(vector4f.x / vector4f.w, vector4f.y / vector4f.w, vector4f.z / vector4f.w);
    float f1 = (vector3f.x + 1.0F) / 2.0F * paramInt1;
    float f2 = (1.0F - vector3f.y) / 2.0F * paramInt2;
    return (vector3f.z < -1.0D || vector3f.z > 1.0D) ? null : new Vector2f(f1, f2);
  }
}
