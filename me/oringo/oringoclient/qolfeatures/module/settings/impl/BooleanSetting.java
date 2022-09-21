package me.oringo.oringoclient.qolfeatures.module.settings.impl;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.awt.Color;
import me.oringo.oringoclient.OringoClient;
import me.oringo.oringoclient.qolfeatures.module.settings.Setting;
import me.oringo.oringoclient.utils.PlayerUtils;
import me.oringo.oringoclient.utils.Returnable;

public class BooleanSetting extends Setting {
  @Expose
  @SerializedName("value")
  public boolean ;
  
  public void () {
    (!());
  }
  
  public BooleanSetting(String paramString, boolean paramBoolean) {
    super(paramString);
    this. = paramBoolean;
  }
  
  public static Color (Color paramColor1, Color paramColor2, float paramFloat) {
    return new Color((int)(paramColor1.getRed() + (paramColor2.getRed() - paramColor1.getRed()) * paramFloat), (int)(paramColor1.getGreen() + (paramColor2.getGreen() - paramColor1.getGreen()) * paramFloat), (int)(paramColor1.getBlue() + (paramColor2.getBlue() - paramColor1.getBlue()) * paramFloat));
  }
  
  public static void () {
    PlayerUtils.(1.0F);
  }
  
  public static float () {
    return (float)Math.sqrt(OringoClient.mc.field_71439_g.field_70159_w * OringoClient.mc.field_71439_g.field_70159_w + OringoClient.mc.field_71439_g.field_70179_y * OringoClient.mc.field_71439_g.field_70179_y);
  }
  
  public boolean () {
    return this.;
  }
  
  public BooleanSetting(String paramString, boolean paramBoolean, Returnable<Boolean> paramReturnable) {
    super(paramString, paramReturnable);
    this. = paramBoolean;
  }
  
  public void (boolean paramBoolean) {
    this. = paramBoolean;
  }
}
