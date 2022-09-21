package me.oringo.oringoclient.qolfeatures.module.settings.impl;

import me.oringo.oringoclient.OringoClient;
import me.oringo.oringoclient.qolfeatures.module.impl.render.XRay;
import me.oringo.oringoclient.qolfeatures.module.settings.Setting;
import net.minecraft.entity.player.EntityPlayer;

public class StringSetting extends Setting {
  public String ;
  
  public int ;
  
  public StringSetting(String paramString1, String paramString2, int paramInt) {
    super(paramString1);
    this. = paramString2;
    this. = paramInt;
  }
  
  public boolean (String paramString) {
    return this..equals(paramString);
  }
  
  public void (String paramString) {
    paramString = XRay.(paramString);
    this. = paramString;
    if (this..length() > this. && this. > 0)
      this. = this..substring(0, this. - 1); 
  }
  
  public StringSetting(String paramString) {
    this(paramString, "", -1);
  }
  
  public static void (int paramInt) {
    OringoClient.mc.field_71442_b.func_78753_a(OringoClient.mc.field_71439_g.field_71069_bz.field_75152_c, paramInt, 1, 4, (EntityPlayer)OringoClient.mc.field_71439_g);
  }
  
  public StringSetting(String paramString1, String paramString2) {
    this(paramString1, paramString2, -1);
  }
  
  public boolean () {
    return this..isEmpty();
  }
  
  public StringSetting(String paramString, int paramInt) {
    this(paramString, "", paramInt);
  }
  
  public String () {
    return this.;
  }
}
