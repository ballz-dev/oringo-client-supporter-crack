package me.oringo.oringoclient.ui.hud;

import java.lang.reflect.Field;

public class HudVec {
  public double ;
  
  public double ;
  
  public HudVec(double paramDouble1, double paramDouble2) {
    this. = paramDouble1;
    this. = paramDouble2;
  }
  
  public static void (Class paramClass, int paramInt, Object paramObject1, Object paramObject2) {
    try {
      Field field = paramClass.getDeclaredFields()[paramInt];
      field.setAccessible(true);
      field.set(paramObject1, paramObject2);
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
  }
  
  public double () {
    return this.;
  }
  
  public double () {
    return this.;
  }
}
