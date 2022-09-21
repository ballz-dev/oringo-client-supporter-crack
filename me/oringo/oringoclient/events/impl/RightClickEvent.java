package me.oringo.oringoclient.events.impl;

import me.oringo.oringoclient.OringoClient;
import me.oringo.oringoclient.events.OringoEvent;
import net.minecraftforge.fml.common.eventhandler.Cancelable;

@Cancelable
public class RightClickEvent extends OringoEvent {
  public static boolean (float paramFloat1, float paramFloat2) {
    float f = Math.abs(paramFloat1 - OringoClient.mc.field_71439_g.field_70177_z) % 360.0F;
    if (f > 180.0F)
      f = 360.0F - f; 
    return (f * 2.0F <= paramFloat2);
  }
  
  static {
  
  }
}
