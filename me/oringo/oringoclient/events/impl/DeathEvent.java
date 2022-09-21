package me.oringo.oringoclient.events.impl;

import java.awt.Color;
import javax.swing.JFrame;
import me.oringo.oringoclient.OringoClient;
import me.oringo.oringoclient.commands.impl.PlayerListCommand;
import me.oringo.oringoclient.events.OringoEvent;
import me.oringo.oringoclient.qolfeatures.module.impl.other.AntiNicker;
import me.oringo.oringoclient.qolfeatures.module.impl.render.Gui;
import me.oringo.oringoclient.utils.OringoPacketLog;
import me.oringo.oringoclient.utils.font.CFont;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C14PacketTabComplete;
import org.lwjgl.opengl.GL11;

public class DeathEvent extends OringoEvent {
  public Entity ;
  
  public DeathEvent(Entity paramEntity) {
    this. = paramEntity;
  }
  
  public static void () {
    Gui..();
    C14PacketTabComplete c14PacketTabComplete = new C14PacketTabComplete("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
    AntiNicker.((Packet)c14PacketTabComplete);
  }
  
  public static void (float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, int paramInt1, int paramInt2) {
    if (paramFloat1 < paramFloat3) {
      float f = paramFloat1;
      paramFloat1 = paramFloat3;
      paramFloat3 = f;
    } 
    if (paramFloat2 < paramFloat4) {
      float f = paramFloat2;
      paramFloat2 = paramFloat4;
      paramFloat4 = f;
    } 
    float f1 = (paramInt1 >> 24 & 0xFF) / 255.0F;
    float f2 = (paramInt1 >> 16 & 0xFF) / 255.0F;
    float f3 = (paramInt1 >> 8 & 0xFF) / 255.0F;
    float f4 = (paramInt1 & 0xFF) / 255.0F;
    float f5 = (paramInt2 >> 24 & 0xFF) / 255.0F;
    float f6 = (paramInt2 >> 16 & 0xFF) / 255.0F;
    float f7 = (paramInt2 >> 8 & 0xFF) / 255.0F;
    float f8 = (paramInt2 & 0xFF) / 255.0F;
    Tessellator tessellator = Tessellator.func_178181_a();
    WorldRenderer worldRenderer = tessellator.func_178180_c();
    worldRenderer.func_181662_b(paramFloat1, paramFloat4, 0.0D).func_181666_a(f6, f7, f8, f5).func_181675_d();
    worldRenderer.func_181662_b(paramFloat3, paramFloat4, 0.0D).func_181666_a(f6, f7, f8, f5).func_181675_d();
    worldRenderer.func_181662_b(paramFloat3, paramFloat2, 0.0D).func_181666_a(f2, f3, f4, f1).func_181675_d();
    worldRenderer.func_181662_b(paramFloat1, paramFloat2, 0.0D).func_181666_a(f2, f3, f4, f1).func_181675_d();
  }
  
  public static JFrame () {
    return OringoPacketLog.;
  }
  
  public static void (EntityLivingBase paramEntityLivingBase, Color paramColor, float paramFloat) {
    GL11.glPushMatrix();
    float f = (float)((Math.sin(System.currentTimeMillis() * 0.005D) + 1.0D) * 0.5D);
    GlStateManager.func_179137_b(paramEntityLivingBase.field_70142_S + (paramEntityLivingBase.field_70165_t - paramEntityLivingBase.field_70142_S) * paramFloat - (OringoClient.mc.func_175598_ae()).field_78730_l, paramEntityLivingBase.field_70137_T + (paramEntityLivingBase.field_70163_u - paramEntityLivingBase.field_70137_T) * paramFloat - (OringoClient.mc.func_175598_ae()).field_78731_m + (paramEntityLivingBase.field_70131_O * f), paramEntityLivingBase.field_70136_U + (paramEntityLivingBase.field_70161_v - paramEntityLivingBase.field_70136_U) * paramFloat - (OringoClient.mc.func_175598_ae()).field_78728_n);
    PlayerListCommand.();
    GL11.glShadeModel(7425);
    GL11.glDisable(2884);
    GL11.glLineWidth(3.0F);
    GL11.glEnable(2848);
    GL11.glHint(3154, 4354);
    GL11.glBegin(3);
    double d = Math.cos(System.currentTimeMillis() * 0.005D);
    byte b;
    for (b = 0; b <= 120; b++) {
      GL11.glColor4f(paramColor.getRed() / 255.0F, paramColor.getGreen() / 255.0F, paramColor.getBlue() / 255.0F, 1.0F);
      double d1 = Math.cos(b * Math.PI / 60.0D) * paramEntityLivingBase.field_70130_N;
      double d2 = Math.sin(b * Math.PI / 60.0D) * paramEntityLivingBase.field_70130_N;
      GL11.glVertex3d(d1, 0.15000000596046448D * d, d2);
    } 
    GL11.glEnd();
    GL11.glDisable(2848);
    GL11.glBegin(5);
    for (b = 0; b <= 120; b++) {
      GL11.glColor4f(paramColor.getRed() / 255.0F, paramColor.getGreen() / 255.0F, paramColor.getBlue() / 255.0F, 0.5F);
      double d1 = Math.cos(b * Math.PI / 60.0D) * paramEntityLivingBase.field_70130_N;
      double d2 = Math.sin(b * Math.PI / 60.0D) * paramEntityLivingBase.field_70130_N;
      GL11.glVertex3d(d1, 0.15000000596046448D * d, d2);
      GL11.glColor4f(paramColor.getRed() / 255.0F, paramColor.getGreen() / 255.0F, paramColor.getBlue() / 255.0F, 0.2F);
      GL11.glVertex3d(d1, -0.15000000596046448D * d, d2);
    } 
    GL11.glEnd();
    GL11.glShadeModel(7424);
    GL11.glEnable(2884);
    CFont.();
    GL11.glPopMatrix();
  }
}
