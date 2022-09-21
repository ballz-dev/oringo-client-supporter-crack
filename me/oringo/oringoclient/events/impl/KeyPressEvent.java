package me.oringo.oringoclient.events.impl;

import me.oringo.oringoclient.OringoClient;
import me.oringo.oringoclient.events.OringoEvent;
import me.oringo.oringoclient.qolfeatures.module.impl.player.AntiVoid;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import org.lwjgl.opengl.GL11;

public class KeyPressEvent extends OringoEvent {
  public char ;
  
  public int ;
  
  public static void (Entity paramEntity, String paramString, double paramDouble1, double paramDouble2, double paramDouble3, int paramInt) {
    double d = paramEntity.func_70068_e((OringoClient.mc.func_175598_ae()).field_78734_h);
    if (d <= (paramInt * paramInt)) {
      FontRenderer fontRenderer = OringoClient.mc.func_175598_ae().func_78716_a();
      float f1 = 1.6F;
      float f2 = 0.016666668F * f1;
      GlStateManager.func_179094_E();
      GlStateManager.func_179109_b((float)paramDouble1 + 0.0F, (float)paramDouble2 + paramEntity.field_70131_O + 0.5F, (float)paramDouble3);
      GL11.glNormal3f(0.0F, 1.0F, 0.0F);
      GlStateManager.func_179114_b(-(OringoClient.mc.func_175598_ae()).field_78735_i, 0.0F, 1.0F, 0.0F);
      GlStateManager.func_179114_b((OringoClient.mc.func_175598_ae()).field_78732_j, 1.0F, 0.0F, 0.0F);
      GlStateManager.func_179152_a(-f2, -f2, f2);
      GlStateManager.func_179140_f();
      GlStateManager.func_179132_a(false);
      GlStateManager.func_179097_i();
      GlStateManager.func_179147_l();
      GlStateManager.func_179120_a(770, 771, 1, 0);
      boolean bool = false;
      fontRenderer.func_78276_b(paramString, -fontRenderer.func_78256_a(paramString) / 2, bool, -1);
      GlStateManager.func_179126_j();
      GlStateManager.func_179132_a(true);
      fontRenderer.func_78276_b(paramString, -fontRenderer.func_78256_a(paramString) / 2, bool, -1);
      GlStateManager.func_179145_e();
      GlStateManager.func_179084_k();
      GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
      GlStateManager.func_179121_F();
    } 
  }
  
  public static boolean () {
    return AntiVoid.;
  }
  
  public static double (double paramDouble1, double paramDouble2, double paramDouble3) {
    if (paramDouble2 < paramDouble3) {
      double d = paramDouble2;
      paramDouble2 = paramDouble3;
      paramDouble3 = d;
    } 
    return Math.max(Math.min(paramDouble2, paramDouble1), paramDouble3);
  }
  
  public KeyPressEvent(int paramInt, char paramChar) {
    this. = paramInt;
    this. = paramChar;
  }
}
