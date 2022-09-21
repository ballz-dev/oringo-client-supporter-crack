package me.oringo.oringoclient.ui.notifications;

import java.awt.Color;
import java.util.ArrayList;
import me.oringo.oringoclient.OringoClient;
import me.oringo.oringoclient.qolfeatures.module.impl.movement.Scaffold;
import me.oringo.oringoclient.qolfeatures.module.impl.other.NoCarpet;
import me.oringo.oringoclient.utils.font.Fonts;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.opengl.GL11;

public class Notifications {
  public static ArrayList<Notification>  = new ArrayList<>();
  
  public static void (float paramFloat1, float paramFloat2, int paramInt1, int paramInt2, float paramFloat3, int paramInt3) {
    GL11.glDisable(3553);
    GL11.glEnable(3042);
    GL11.glEnable(2881);
    GL11.glBlendFunc(770, 771);
    float f1 = (paramInt3 >> 24 & 0xFF) / 255.0F;
    float f2 = (paramInt3 >> 16 & 0xFF) / 255.0F;
    float f3 = (paramInt3 >> 8 & 0xFF) / 255.0F;
    float f4 = (paramInt3 & 0xFF) / 255.0F;
    GL11.glColor4f(f2, f3, f4, f1);
    GL11.glScaled(0.5D, 0.5D, 0.5D);
    paramFloat1 *= 2.0F;
    paramFloat2 *= 2.0F;
    paramFloat3 *= 2.0F;
    GL11.glBegin(9);
    for (int i = paramInt1; i <= paramInt2; i++) {
      double d1 = Math.sin(i * Math.PI / 45.0D) * paramFloat3;
      double d2 = Math.cos(i * Math.PI / 45.0D) * paramFloat3;
      GL11.glVertex3d(paramFloat1 + d1, paramFloat2 + d2, 0.0D);
    } 
    GL11.glEnd();
    GL11.glScaled(2.0D, 2.0D, 2.0D);
    GL11.glDisable(2881);
    GL11.glEnable(3553);
    GL11.glDisable(3042);
  }
  
  @SubscribeEvent
  public void (TickEvent.RenderTickEvent paramRenderTickEvent) {
    if (paramRenderTickEvent.phase == TickEvent.Phase.END) {
      GL11.glPushMatrix();
      .removeIf(Notifications::);
      if (.size() > 0) {
        ScaledResolution scaledResolution = new ScaledResolution(OringoClient.mc);
        float f = (scaledResolution.func_78328_b() - 37);
        for (byte b = 0; b < .size(); b++) {
          Notification notification = .get(b);
          GL11.glPushMatrix();
          float f1 = (float)Math.max(150.0D, Fonts..(notification.()) + 10.0D);
          float f2 = 35.0F;
          float f3 = scaledResolution.func_78326_a() - f1 - 2.0F;
          if (notification.() <= 250L) {
            if (notification.() >= 100L) {
              f3 = (float)(f3 + (250L - notification.()) / 150.0D * (f1 + 2.0F));
            } else {
              f3 += 10000.0F;
            } 
          } else if (notification.() - System.currentTimeMillis() <= 250L) {
            long l = notification.() - System.currentTimeMillis();
            if (l >= 100L) {
              f3 = (float)(f3 + (250L - l) / 150.0D * (f1 + 2.0F));
            } else {
              f3 += 10000.0F;
            } 
          } 
          Scaffold.(f3, f, (f3 + f1), (f + f2), 3.0D, (new Color(21, 21, 21, 90)).getRGB());
          Fonts..(notification.(), (f3 + 3.0F), (f + 5.0F), notification.().getRGB());
          Fonts..(notification.(), (f3 + 5.0F), (f + 10.0F + Fonts..()), Color.white.getRGB());
          NoCarpet.(f3, f + f2 - 2.0F, f3 + f1 * (float)notification.() / notification.(), f + f2, notification.().getRGB());
          if (notification.() < 100L) {
            f2 = (float)(f2 * notification.() / 100.0D);
          } else if (notification.() - System.currentTimeMillis() < 100L) {
            long l = notification.() - System.currentTimeMillis();
            f2 = (float)(f2 * l / 100.0D);
          } 
          f -= f2 + 1.0F;
          GL11.glPopMatrix();
        } 
      } 
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      GL11.glPopMatrix();
    } 
  }
  
  public enum NotificationType {
    ,
    ,
    ("Warning", new Color(255, 204, 0));
    
    public Color ;
    
    public String ;
    
    public String getName() {
      return this.;
    }
    
    public Color getColor(int param1Int) {
      return (this == ) ? OringoClient..(param1Int) : this.;
    }
    
    NotificationType(String param1String1, Color param1Color) {
      this. = param1String1;
      this. = param1Color;
    }
    
    static {
    
    }
  }
  
  public static class Notification {
    public Notifications.NotificationType ;
    
    public int ;
    
    public String ;
    
    public int ;
    
    public long ;
    
    public int () {
      return this.;
    }
    
    public String () {
      return this..getName();
    }
    
    public long () {
      return this.;
    }
    
    public long () {
      return System.currentTimeMillis() - this. + this.;
    }
    
    public Notification(String param1String, int param1Int, Notifications.NotificationType param1NotificationType) {
      this. = param1String;
      this. = System.currentTimeMillis() + param1Int;
      this. = param1Int;
      this. = param1NotificationType;
      this. = Notifications.().size() + 1;
    }
    
    public String () {
      return this.;
    }
    
    public Color () {
      return this..getColor(this.);
    }
  }
}
