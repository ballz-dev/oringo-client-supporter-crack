package me.oringo.oringoclient.utils;

import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.opengl.GL11;

public class OutlineUtils {
  public static void (float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    GlStateManager.func_179147_l();
    GlStateManager.func_179090_x();
    GlStateManager.func_179120_a(770, 771, 1, 0);
    double d1 = (paramFloat1 + paramFloat3);
    double d2 = (paramFloat2 + paramFloat4);
    float f1 = (paramInt1 >> 24 & 0xFF) / 255.0F;
    float f2 = (paramInt1 >> 16 & 0xFF) / 255.0F;
    float f3 = (paramInt1 >> 8 & 0xFF) / 255.0F;
    float f4 = (paramInt1 & 0xFF) / 255.0F;
    float f5 = (paramInt2 >> 24 & 0xFF) / 255.0F;
    float f6 = (paramInt2 >> 16 & 0xFF) / 255.0F;
    float f7 = (paramInt2 >> 8 & 0xFF) / 255.0F;
    float f8 = (paramInt2 & 0xFF) / 255.0F;
    float f9 = (paramInt3 >> 24 & 0xFF) / 255.0F;
    float f10 = (paramInt3 >> 16 & 0xFF) / 255.0F;
    float f11 = (paramInt3 >> 8 & 0xFF) / 255.0F;
    float f12 = (paramInt3 & 0xFF) / 255.0F;
    float f13 = (paramInt4 >> 24 & 0xFF) / 255.0F;
    float f14 = (paramInt4 >> 16 & 0xFF) / 255.0F;
    float f15 = (paramInt4 >> 8 & 0xFF) / 255.0F;
    float f16 = (paramInt4 & 0xFF) / 255.0F;
    GL11.glPushAttrib(0);
    GL11.glScaled(0.5D, 0.5D, 0.5D);
    paramFloat1 *= 2.0F;
    paramFloat2 *= 2.0F;
    d1 *= 2.0D;
    d2 *= 2.0D;
    GL11.glLineWidth(paramFloat6);
    GL11.glDisable(3553);
    GL11.glEnable(2848);
    GL11.glShadeModel(7425);
    GL11.glBegin(2);
    GL11.glColor4f(f2, f3, f4, f1);
    byte b;
    for (b = 0; b <= 90; b += 3)
      GL11.glVertex2d((paramFloat1 + paramFloat5) + Math.sin(b * Math.PI / 180.0D) * (paramFloat5 * -1.0F), (paramFloat2 + paramFloat5) + Math.cos(b * Math.PI / 180.0D) * (paramFloat5 * -1.0F)); 
    GL11.glColor4f(f6, f7, f8, f5);
    for (b = 90; b <= '´'; b += 3)
      GL11.glVertex2d((paramFloat1 + paramFloat5) + Math.sin(b * Math.PI / 180.0D) * (paramFloat5 * -1.0F), d2 - paramFloat5 + Math.cos(b * Math.PI / 180.0D) * (paramFloat5 * -1.0F)); 
    GL11.glColor4f(f10, f11, f12, f9);
    for (b = 0; b <= 90; b += 3)
      GL11.glVertex2d(d1 - paramFloat5 + Math.sin(b * Math.PI / 180.0D) * paramFloat5, d2 - paramFloat5 + Math.cos(b * Math.PI / 180.0D) * paramFloat5); 
    GL11.glColor4f(f14, f15, f16, f13);
    for (b = 90; b <= '´'; b += 3)
      GL11.glVertex2d(d1 - paramFloat5 + Math.sin(b * Math.PI / 180.0D) * paramFloat5, (paramFloat2 + paramFloat5) + Math.cos(b * Math.PI / 180.0D) * paramFloat5); 
    GL11.glEnd();
    GL11.glEnable(3553);
    GL11.glDisable(2848);
    GL11.glEnable(3553);
    GL11.glShadeModel(7424);
    GL11.glScaled(2.0D, 2.0D, 2.0D);
    GL11.glPopAttrib();
    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    GlStateManager.func_179098_w();
    GlStateManager.func_179084_k();
  }
  
  static {
  
  }
}
