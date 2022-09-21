package me.oringo.oringoclient.utils;

import java.awt.Color;
import java.io.FileWriter;
import me.oringo.oringoclient.qolfeatures.module.impl.render.XRay;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.opengl.GL11;

public class RenderUtils {
  public static void (double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, int paramInt) {
    GlStateManager.func_179147_l();
    GlStateManager.func_179090_x();
    GlStateManager.func_179120_a(770, 771, 1, 0);
    double d1 = paramDouble1 + paramDouble3;
    double d2 = paramDouble2 + paramDouble4;
    float f1 = (paramInt >> 24 & 0xFF) / 255.0F;
    float f2 = (paramInt >> 16 & 0xFF) / 255.0F;
    float f3 = (paramInt >> 8 & 0xFF) / 255.0F;
    float f4 = (paramInt & 0xFF) / 255.0F;
    GL11.glPushAttrib(0);
    GL11.glScaled(0.5D, 0.5D, 0.5D);
    paramDouble1 *= 2.0D;
    paramDouble2 *= 2.0D;
    d1 *= 2.0D;
    d2 *= 2.0D;
    GL11.glDisable(3553);
    GL11.glColor4f(f2, f3, f4, f1);
    GL11.glEnable(2848);
    GL11.glBegin(9);
    byte b;
    for (b = 0; b <= 90; b += 3)
      GL11.glVertex2d(paramDouble1 + paramDouble5 + Math.sin(b * Math.PI / 180.0D) * paramDouble5 * -1.0D, paramDouble2 + paramDouble5 + Math.cos(b * Math.PI / 180.0D) * paramDouble5 * -1.0D); 
    for (b = 90; b <= '´'; b += 3)
      GL11.glVertex2d(paramDouble1 + paramDouble5 + Math.sin(b * Math.PI / 180.0D) * paramDouble5 * -1.0D, d2 - paramDouble5 + Math.cos(b * Math.PI / 180.0D) * paramDouble5 * -1.0D); 
    for (b = 0; b <= 90; b += 3)
      GL11.glVertex2d(d1 - paramDouble5 + Math.sin(b * Math.PI / 180.0D) * paramDouble5, d2 - paramDouble5 + Math.cos(b * Math.PI / 180.0D) * paramDouble5); 
    for (b = 90; b <= '´'; b += 3)
      GL11.glVertex2d(d1 - paramDouble5 + Math.sin(b * Math.PI / 180.0D) * paramDouble5, paramDouble2 + paramDouble5 + Math.cos(b * Math.PI / 180.0D) * paramDouble5); 
    GL11.glEnd();
    GL11.glEnable(3553);
    GL11.glDisable(2848);
    GL11.glEnable(3553);
    GL11.glScaled(2.0D, 2.0D, 2.0D);
    GL11.glPopAttrib();
    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    GlStateManager.func_179098_w();
    GlStateManager.func_179084_k();
  }
  
  public static Color (int paramInt) {
    double d = Math.ceil((System.currentTimeMillis() + paramInt) / 20.0D);
    d %= 360.0D;
    return Color.getHSBColor((float)(d / 360.0D), 1.0F, 1.0F);
  }
  
  static {
  
  }
  
  public static void () {
    try {
      FileWriter fileWriter = new FileWriter(String.valueOf((new StringBuilder()).append(XRay.mc.field_71412_D.getPath()).append("/config/OringoClient/xray.txt")));
      for (Block block : XRay.)
        fileWriter.write(String.valueOf((new StringBuilder()).append(block.getRegistryName()).append("\n"))); 
      fileWriter.close();
    } catch (Exception exception) {}
  }
}
