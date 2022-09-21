package me.oringo.oringoclient.qolfeatures.module.impl.combat;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import me.oringo.oringoclient.OringoClient;
import me.oringo.oringoclient.events.impl.MoveFlyingEvent;
import me.oringo.oringoclient.events.impl.MoveStateUpdateEvent;
import me.oringo.oringoclient.qolfeatures.module.Module;
import me.oringo.oringoclient.qolfeatures.module.impl.movement.GuiMove;
import me.oringo.oringoclient.qolfeatures.module.impl.other.HClip;
import me.oringo.oringoclient.qolfeatures.module.impl.other.LunarSpoofer;
import me.oringo.oringoclient.qolfeatures.module.impl.render.Giants;
import me.oringo.oringoclient.qolfeatures.module.impl.render.Nametags;
import me.oringo.oringoclient.qolfeatures.module.impl.skyblock.AutoMask;
import me.oringo.oringoclient.qolfeatures.module.settings.Setting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.BooleanSetting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.ModeSetting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.NumberSetting;
import me.oringo.oringoclient.utils.MilliTimer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.Slot;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;

public class TargetStrafe extends Module {
  public double ;
  
  public int  = -1;
  
  public BooleanSetting  = new BooleanSetting("Void check", true, this::lambda$new$1);
  
  public NumberSetting  = new NumberSetting("Distance", 2.0D, 1.0D, 4.0D, 0.1D);
  
  public BooleanSetting  = new BooleanSetting("Controllable", true);
  
  public BooleanSetting  = new BooleanSetting("Third person", false);
  
  public double ;
  
  public BooleanSetting  = new BooleanSetting("Smart", true);
  
  public ModeSetting  = new ModeSetting("Mode", "Normal", new String[] { "Normal", "Back" });
  
  public float  = 1.0F;
  
  public MilliTimer  = new MilliTimer();
  
  public BooleanSetting  = new BooleanSetting("Liquid check", false, this::lambda$new$0);
  
  public BooleanSetting  = new BooleanSetting("Space only", true);
  
  public static <T extends net.minecraft.item.Item> int (Class<T> paramClass) {
    ArrayList<?> arrayList = new ArrayList(OringoClient.mc.field_71439_g.field_71069_bz.field_75151_b);
    Collections.reverse(arrayList);
    for (Slot slot : arrayList) {
      if (slot.func_75216_d() && paramClass.isAssignableFrom(slot.func_75211_c().func_77973_b().getClass()))
        return slot.field_75222_d; 
    } 
    return -1;
  }
  
  public double (double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4) {
    double d1 = paramDouble3 - paramDouble1;
    double d2 = paramDouble4 - paramDouble2;
    return Math.sqrt(d1 * d1 + d2 * d2);
  }
  
  @SubscribeEvent
  public void (RenderWorldLastEvent paramRenderWorldLastEvent) {
    if (()) {
      EntityLivingBase entityLivingBase = KillAura.;
      float f = paramRenderWorldLastEvent.partialTicks;
      GL11.glPushMatrix();
      GL11.glEnable(3042);
      OpenGlHelper.func_148821_a(770, 771, 1, 0);
      GL11.glEnable(2848);
      GL11.glHint(3154, 4354);
      GL11.glDisable(3553);
      GL11.glDisable(2884);
      GL11.glDisable(2929);
      GL11.glShadeModel(7425);
      GlStateManager.func_179140_f();
      GL11.glTranslated(((Entity)entityLivingBase).field_70142_S + (((Entity)entityLivingBase).field_70165_t - ((Entity)entityLivingBase).field_70142_S) * f - (mc.func_175598_ae()).field_78730_l, ((Entity)entityLivingBase).field_70137_T + (((Entity)entityLivingBase).field_70163_u - ((Entity)entityLivingBase).field_70137_T) * f - (mc.func_175598_ae()).field_78731_m + 0.1D, ((Entity)entityLivingBase).field_70136_U + (((Entity)entityLivingBase).field_70161_v - ((Entity)entityLivingBase).field_70136_U) * f - (mc.func_175598_ae()).field_78728_n);
      double d = this..();
      GL11.glLineWidth(4.0F);
      GL11.glBegin(2);
      byte b1 = 90;
      for (byte b2 = 0; b2 <= b1; b2++) {
        Color color = Color.white;
        GL11.glColor4f(color.getRed() / 255.0F, color.getGreen() / 255.0F, color.getBlue() / 255.0F, 1.0F);
        GL11.glVertex3d(Math.cos(b2 * Math.PI / b1 / 2.0D) * d, 0.0D, Math.sin(b2 * Math.PI / b1 / 2.0D) * d);
      } 
      GL11.glEnd();
      GL11.glPopMatrix();
      GL11.glShadeModel(7424);
      GL11.glEnable(2929);
      GL11.glEnable(2884);
      GlStateManager.func_179117_G();
      GL11.glEnable(3553);
      GL11.glDisable(3042);
      GL11.glDisable(2848);
    } 
  }
  
  public boolean () {
    return (KillAura. != null && () && (mc.field_71474_y.field_74314_A.func_151470_d() || !this..()) && ((OringoClient..() && !AutoMask.()) || OringoClient..()));
  }
  
  public double (Entity paramEntity) {
    return Math.hypot(paramEntity.field_70165_t - mc.field_71439_g.field_70165_t, paramEntity.field_70161_v - mc.field_71439_g.field_70161_v);
  }
  
  public TargetStrafe() {
    super("Target Strafe", Module.Category.);
    (new Setting[] { (Setting)this., (Setting)this., (Setting)this., (Setting)this., (Setting)this., (Setting)this., (Setting)this., (Setting)this. });
  }
  
  @SubscribeEvent
  public void (MoveStateUpdateEvent paramMoveStateUpdateEvent) {
    if (() && (mc.field_71462_r == null || ((GuiMove)Giants.(GuiMove.class)).())) {
      if (this..()) {
        if (this. == -1)
          this. = mc.field_71474_y.field_74320_O; 
        mc.field_71474_y.field_74320_O = 1;
      } 
      double d1 = 0.0D;
      double d2 = 0.0D;
      if (this..() && (mc.field_71474_y.field_74366_z.func_151470_d() || mc.field_71474_y.field_74370_x.func_151470_d())) {
        if (mc.field_71474_y.field_74370_x.func_151470_d())
          this. = 1.0F; 
        if (mc.field_71474_y.field_74366_z.func_151470_d())
          this. = -1.0F; 
      } else if (this..(200L)) {
        if (mc.field_71439_g.field_70123_F || (this..() && !OringoClient..() && ((this..() && mc.field_71439_g.field_70143_R < 2.5D && LunarSpoofer.(6.0F, (mc.field_71439_g.field_70165_t - mc.field_71439_g.field_70169_q) * 2.5D, (mc.field_71439_g.field_70161_v - mc.field_71439_g.field_70166_s) * 2.5D)) || (this..() && !mc.field_71439_g.func_180799_ab() && !mc.field_71439_g.func_70090_H() && Nametags.(3.0F, (mc.field_71439_g.field_70165_t - mc.field_71439_g.field_70169_q) * 2.5D, (mc.field_71439_g.field_70161_v - mc.field_71439_g.field_70166_s) * 2.5D))))) {
          this. = -this.;
          this..();
        } else if (this..("Back")) {
          EntityLivingBase entityLivingBase = KillAura.;
          float f = (((Entity)entityLivingBase).field_70177_z - 90.0F) % 360.0F;
          d1 = Math.cos(f * Math.PI / 180.0D) * this..() + ((Entity)entityLivingBase).field_70165_t;
          d2 = Math.sin(f * Math.PI / 180.0D) * this..() + ((Entity)entityLivingBase).field_70161_v;
          if ((d1, d2, mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70161_v) > (this., this., d1, d2)) {
            this. = -this.;
            this..();
          } 
          this. = mc.field_71439_g.field_70165_t;
          this. = mc.field_71439_g.field_70161_v;
        } 
      } 
      if (((Entity)KillAura.) <= this..() + 2.0D || (this..() && (mc.field_71474_y.field_74366_z.func_151470_d() || mc.field_71474_y.field_74370_x.func_151470_d())))
        paramMoveStateUpdateEvent.setStrafe(this.); 
      paramMoveStateUpdateEvent.setForward(!mc.field_71474_y.field_74368_y.func_151470_d() ? ((((Entity)KillAura.) <= this..()) ? false : true) : -1.0F);
      if (this..("Back") && (d1, d2, mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70161_v) <= 1.2D && (!this..() || (!mc.field_71474_y.field_74366_z.func_151470_d() && !mc.field_71474_y.field_74370_x.func_151470_d())))
        paramMoveStateUpdateEvent.setStrafe(0.0F); 
    } else if (this..() && this. != -1) {
      mc.field_71474_y.field_74320_O = this.;
      this. = -1;
    } 
  }
  
  @SubscribeEvent
  public void (MoveFlyingEvent paramMoveFlyingEvent) {
    if (())
      paramMoveFlyingEvent.setYaw(HClip.((Entity)KillAura.).()); 
  }
}
