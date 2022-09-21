package me.oringo.oringoclient.utils;

import me.oringo.oringoclient.events.OringoEvent;
import net.minecraft.client.shader.Framebuffer;
import org.lwjgl.opengl.EXTFramebufferObject;

public class MilliTimer {
  public long ;
  
  public boolean (double paramDouble, boolean paramBoolean) {
    return (Math.round(paramDouble), paramBoolean);
  }
  
  public MilliTimer() {
    ();
  }
  
  public static void (String paramString) {
    OringoEvent.(paramString);
  }
  
  public static void (Framebuffer paramFramebuffer) {
    EXTFramebufferObject.glDeleteRenderbuffersEXT(paramFramebuffer.field_147624_h);
    int i = EXTFramebufferObject.glGenRenderbuffersEXT();
    EXTFramebufferObject.glBindRenderbufferEXT(36161, i);
    EXTFramebufferObject.glRenderbufferStorageEXT(36161, 34041, StencilUtils.mc.field_71443_c, StencilUtils.mc.field_71440_d);
    EXTFramebufferObject.glFramebufferRenderbufferEXT(36160, 36128, 36161, i);
    EXTFramebufferObject.glFramebufferRenderbufferEXT(36160, 36096, 36161, i);
  }
  
  public boolean (double paramDouble) {
    return (Math.round(paramDouble));
  }
  
  public boolean (long paramLong, boolean paramBoolean) {
    if (System.currentTimeMillis() - this. >= paramLong) {
      if (paramBoolean)
        (); 
      return true;
    } 
    return false;
  }
  
  public boolean (long paramLong) {
    return (paramLong, false);
  }
  
  public long () {
    return System.currentTimeMillis() - this.;
  }
  
  public void () {
    this. = System.currentTimeMillis();
  }
  
  public void (long paramLong) {
    this. = System.currentTimeMillis() - paramLong;
  }
}
