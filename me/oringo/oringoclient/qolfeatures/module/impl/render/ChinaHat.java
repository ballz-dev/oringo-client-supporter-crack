package me.oringo.oringoclient.qolfeatures.module.impl.render;

import java.awt.Color;
import me.oringo.oringoclient.OringoClient;
import me.oringo.oringoclient.commands.impl.WardrobeCommand;
import me.oringo.oringoclient.qolfeatures.module.Module;
import me.oringo.oringoclient.qolfeatures.module.impl.movement.Scaffold;
import me.oringo.oringoclient.qolfeatures.module.impl.other.HClip;
import me.oringo.oringoclient.qolfeatures.module.settings.Setting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.BooleanSetting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.ModeSetting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.NumberSetting;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;

public class ChinaHat extends Module {
  public NumberSetting  = new NumberSetting(this, "Blue 1", 255.0D, 0.0D, 255.0D, 1.0D) {
      public boolean () {
        return !this...("Gradient");
      }
    };
  
  public ModeSetting  = new ModeSetting("Color mode", "Rainbow", new String[] { "Rainbow", "Gradient", "Single", "Theme" });
  
  public NumberSetting  = new NumberSetting(this, "Red 1", 255.0D, 0.0D, 255.0D, 1.0D) {
      public boolean () {
        return !this...("Gradient");
      }
    };
  
  public NumberSetting  = new NumberSetting(this, "Red 2", 90.0D, 0.0D, 255.0D, 1.0D) {
      public boolean () {
        return !this...("Gradient");
      }
    };
  
  public NumberSetting  = new NumberSetting(this, "Blue 2", 255.0D, 0.0D, 255.0D, 1.0D) {
      public boolean () {
        return !this...("Gradient");
      }
    };
  
  public NumberSetting  = new NumberSetting("Position", 0.1D, -0.5D, 0.5D, 0.01D);
  
  public NumberSetting  = new NumberSetting("Rotation", 5.0D, 0.0D, 5.0D, 0.1D);
  
  public NumberSetting  = new NumberSetting("Angles", 8.0D, 4.0D, 90.0D, 1.0D);
  
  public BooleanSetting  = new BooleanSetting("Shade", true);
  
  public NumberSetting  = new NumberSetting(this, "Green 1", 0.0D, 0.0D, 255.0D, 1.0D) {
      public boolean () {
        return !this...("Gradient");
      }
    };
  
  public BooleanSetting  = new BooleanSetting("Show in first person", false);
  
  public NumberSetting  = new NumberSetting(this, "Red", 0.0D, 0.0D, 255.0D, 1.0D) {
      public boolean () {
        return !this...("Single");
      }
    };
  
  public NumberSetting  = new NumberSetting(this, "Green 2", 10.0D, 0.0D, 255.0D, 1.0D) {
      public boolean () {
        return !this...("Gradient");
      }
    };
  
  public NumberSetting  = new NumberSetting(this, "Green", 80.0D, 0.0D, 255.0D, 1.0D) {
      public boolean () {
        return !this...("Single");
      }
    };
  
  public NumberSetting  = new NumberSetting("Height", 0.3D, 0.10000000149011612D, 0.699999988079071D, 0.01D);
  
  public NumberSetting  = new NumberSetting(this, "Blue", 255.0D, 0.0D, 255.0D, 1.0D) {
      public boolean () {
        return !this...("Single");
      }
    };
  
  public NumberSetting  = new NumberSetting("Radius", 0.7D, 0.5D, 1.0D, 0.01D);
  
  public static void (float paramFloat) {
    HClip.();
    GL11.glPushAttrib(1048575);
    GL11.glDisable(3008);
    GL11.glDisable(3553);
    GL11.glDisable(2896);
    GL11.glEnable(3042);
    GL11.glBlendFunc(770, 771);
    GL11.glLineWidth(paramFloat);
    GL11.glEnable(2848);
    GL11.glEnable(2960);
    GL11.glClear(1024);
    GL11.glClearStencil(15);
    GL11.glStencilFunc(512, 1, 15);
    GL11.glStencilOp(7681, 7681, 7681);
    GL11.glPolygonMode(1032, 6913);
  }
  
  public Color (double paramDouble1, double paramDouble2, boolean paramBoolean) {
    double d;
    switch (this..()) {
      case "Rainbow":
        return Color.getHSBColor((float)(paramDouble1 / paramDouble2), 0.65F, 1.0F);
      case "Gradient":
        return paramBoolean ? new Color((int)this..(), (int)this..(), (int)this..()) : new Color((int)this..(), (int)this..(), (int)this..());
      case "Theme":
        d = paramDouble1 / paramDouble2 * 10.0D;
        if (paramDouble1 > paramDouble2 / 2.0D)
          d = 10.0D - d; 
        return OringoClient..((int)d);
    } 
    return paramBoolean ? (new Color((int)this..(), (int)this..(), (int)this..())).darker().darker() : new Color((int)this..(), (int)this..(), (int)this..());
  }
  
  @SubscribeEvent
  public void (RenderWorldLastEvent paramRenderWorldLastEvent) {
    if (() && (mc.field_71474_y.field_74320_O != 0 || this..()))
      ((EntityLivingBase)mc.field_71439_g, paramRenderWorldLastEvent.partialTicks); 
  }
  
  public ChinaHat() {
    super("China hat", 0, Module.Category.);
    (new Setting[] { 
          (Setting)this., (Setting)this., (Setting)this., (Setting)this., (Setting)this., (Setting)this., (Setting)this., (Setting)this., (Setting)this., (Setting)this., 
          (Setting)this., (Setting)this., (Setting)this., (Setting)this., (Setting)this., (Setting)this., (Setting)this. });
  }
  
  public void (EntityLivingBase paramEntityLivingBase, float paramFloat) {
    GL11.glPushMatrix();
    GL11.glEnable(3042);
    OpenGlHelper.func_148821_a(770, 771, 1, 0);
    GL11.glEnable(2848);
    GL11.glHint(3154, 4354);
    if (this..())
      GL11.glShadeModel(7425); 
    GL11.glDisable(3553);
    GL11.glDisable(2884);
    GlStateManager.func_179140_f();
    GL11.glTranslated(paramEntityLivingBase.field_70142_S + (paramEntityLivingBase.field_70165_t - paramEntityLivingBase.field_70142_S) * paramFloat - (mc.func_175598_ae()).field_78730_l, paramEntityLivingBase.field_70137_T + (paramEntityLivingBase.field_70163_u - paramEntityLivingBase.field_70137_T) * paramFloat - (mc.func_175598_ae()).field_78731_m + paramEntityLivingBase.field_70131_O + (paramEntityLivingBase.func_70093_af() ? (this..() - 0.23000000417232513D) : this..()), paramEntityLivingBase.field_70136_U + (paramEntityLivingBase.field_70161_v - paramEntityLivingBase.field_70136_U) * paramFloat - (mc.func_175598_ae()).field_78728_n);
    GL11.glRotatef((float)((paramEntityLivingBase.field_70173_aa + paramFloat) * this..()) - 90.0F, 0.0F, 1.0F, 0.0F);
    GL11.glRotatef(-(mc.field_71439_g.field_70758_at + (mc.field_71439_g.field_70759_as - mc.field_71439_g.field_70758_at) * paramFloat), 0.0F, 1.0F, 0.0F);
    double d = this..();
    GL11.glLineWidth(2.0F);
    GL11.glBegin(2);
    for (byte b1 = 0; b1 <= this..(); b1++) {
      Color color1 = (b1, (int)this..(), false);
      GL11.glColor4f(color1.getRed() / 255.0F, color1.getGreen() / 255.0F, color1.getBlue() / 255.0F, 0.5F);
      GL11.glVertex3d(Math.cos(b1 * Math.PI / this..() / 2.0D) * d, 0.0D, Math.sin(b1 * Math.PI / this..() / 2.0D) * d);
    } 
    GL11.glEnd();
    GL11.glBegin(6);
    Color color = (0.0D, this..(), true);
    GL11.glColor4f(color.getRed() / 255.0F, color.getGreen() / 255.0F, color.getBlue() / 255.0F, 0.8F);
    GL11.glVertex3d(0.0D, this..(), 0.0D);
    for (byte b2 = 0; b2 <= this..(); b2++) {
      Color color1 = (b2, (int)this..(), false);
      GL11.glColor4f(color1.getRed() / 255.0F, color1.getGreen() / 255.0F, color1.getBlue() / 255.0F, 0.3F);
      GL11.glVertex3d(Math.cos(b2 * Math.PI / this..() / 2.0D) * d, 0.0D, Math.sin(b2 * Math.PI / this..() / 2.0D) * d);
    } 
    GL11.glVertex3d(0.0D, this..(), 0.0D);
    GL11.glEnd();
    GL11.glShadeModel(7424);
    GL11.glEnable(2884);
    GlStateManager.func_179117_G();
    GL11.glEnable(3553);
    GL11.glDisable(3042);
    GL11.glDisable(2848);
    GL11.glPopMatrix();
  }
  
  public static void (float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, int paramInt1, int paramInt2) {
    Scaffold.(paramFloat1, paramFloat2, (paramFloat1 + paramFloat3), (paramFloat2 + paramFloat4), paramFloat5, paramInt1);
    WardrobeCommand.(paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramFloat5, paramFloat6, paramInt2);
  }
}
