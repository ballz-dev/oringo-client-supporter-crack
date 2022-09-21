package me.oringo.oringoclient.events.impl;

import java.io.BufferedReader;
import java.io.FileReader;
import me.oringo.oringoclient.events.OringoEvent;
import me.oringo.oringoclient.qolfeatures.module.impl.keybinds.Keybind;
import me.oringo.oringoclient.qolfeatures.module.impl.other.KillInsults;
import net.minecraft.entity.Entity;

public class MoveStateUpdateEvent extends OringoEvent {
  public boolean ;
  
  public boolean ;
  
  public float ;
  
  public float ;
  
  public boolean isJump() {
    return this.;
  }
  
  public static boolean (Entity paramEntity) {
    return (paramEntity.func_70005_c_().equals("Shadow Assassin") || paramEntity.func_70005_c_().equals("Lost Adventurer") || paramEntity.func_70005_c_().equals("Diamond Guy"));
  }
  
  public MoveStateUpdateEvent setSneak(boolean paramBoolean) {
    this. = paramBoolean;
    return this;
  }
  
  public MoveStateUpdateEvent setJump(boolean paramBoolean) {
    this. = paramBoolean;
    return this;
  }
  
  public MoveStateUpdateEvent(float paramFloat1, float paramFloat2, boolean paramBoolean1, boolean paramBoolean2) {
    this. = paramFloat1;
    this. = paramBoolean1;
    this. = paramFloat2;
    this. = paramBoolean2;
  }
  
  public float getStrafe() {
    return this.;
  }
  
  public MoveStateUpdateEvent setForward(float paramFloat) {
    this. = paramFloat;
    return this;
  }
  
  public boolean isSneak() {
    return this.;
  }
  
  public MoveStateUpdateEvent setStrafe(float paramFloat) {
    this. = paramFloat;
    return this;
  }
  
  public static void () {
    KillInsults..clear();
    try {
      BufferedReader bufferedReader = new BufferedReader(new FileReader(String.valueOf((new StringBuilder()).append(KillInsults.mc.field_71412_D.getPath()).append("/config/OringoClient/insults.txt"))));
      String str;
      while ((str = bufferedReader.readLine()) != null)
        KillInsults..add(str); 
      bufferedReader.close();
    } catch (Exception exception) {
      Keybind.("Unable to load insults");
    } 
  }
  
  public float getForward() {
    return this.;
  }
}
