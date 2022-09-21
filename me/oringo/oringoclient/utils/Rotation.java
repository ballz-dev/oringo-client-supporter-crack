package me.oringo.oringoclient.utils;

import java.util.Objects;
import me.oringo.oringoclient.OringoClient;
import me.oringo.oringoclient.mixins.entity.PlayerSPAccessor;
import me.oringo.oringoclient.qolfeatures.module.impl.other.LightningDetect;
import net.minecraft.util.MathHelper;

public class Rotation {
  public float ;
  
  public float ;
  
  public Rotation(float paramFloat1, float paramFloat2) {
    this. = paramFloat2;
    this. = paramFloat1;
  }
  
  public Rotation (float paramFloat) {
    this. = paramFloat;
    return this;
  }
  
  public Rotation () {
    this. = ((PlayerSPAccessor)OringoClient.mc.field_71439_g).getLastReportedYaw() + MathHelper.func_76142_g(this. - ((PlayerSPAccessor)OringoClient.mc.field_71439_g).getLastReportedYaw());
    this. = ((PlayerSPAccessor)OringoClient.mc.field_71439_g).getLastReportedPitch() + MathHelper.func_76142_g(this. - ((PlayerSPAccessor)OringoClient.mc.field_71439_g).getLastReportedPitch());
    return ();
  }
  
  public int () {
    return Objects.hash(new Object[] { Float.valueOf(this.), Float.valueOf(this.) });
  }
  
  public boolean (Object paramObject) {
    if (this == paramObject)
      return true; 
    if (paramObject == null || getClass() != paramObject.getClass())
      return false; 
    Rotation rotation = (Rotation)paramObject;
    return (LightningDetect.(this, rotation) == 0.0D);
  }
  
  public float () {
    return this.;
  }
  
  public Rotation () {
    this. = MoveUtils.(MathHelper.func_76142_g(this.), -90.0F, 90.0F);
    return this;
  }
  
  public float () {
    return this.;
  }
  
  public Rotation (float paramFloat) {
    this. = paramFloat;
    return this;
  }
}
