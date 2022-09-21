package me.oringo.oringoclient.events.impl;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import me.oringo.oringoclient.config.ConfigManager;
import me.oringo.oringoclient.events.OringoEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;

public class MoveHeadingEvent extends OringoEvent {
  public float  = 0.91F;
  
  public boolean ;
  
  public MoveHeadingEvent setOnGround(boolean paramBoolean) {
    this. = paramBoolean;
    return this;
  }
  
  public boolean isOnGround() {
    return this.;
  }
  
  public MoveHeadingEvent(boolean paramBoolean) {
    this. = paramBoolean;
  }
  
  public float getFriction2Multi() {
    return this.;
  }
  
  public void setFriction2Multi(float paramFloat) {
    this. = paramFloat;
  }
  
  public static void () throws IOException {
    Desktop.getDesktop().open(new File(ConfigManager.));
  }
  
  public static void (String paramString) {
    (Minecraft.func_71410_x()).field_71439_g.func_145747_a((IChatComponent)new ChatComponentText(paramString));
  }
}
