package me.oringo.oringoclient.ui.hud.impl;

import com.google.common.collect.Iterables;
import com.mojang.realmsclient.gui.ChatFormatting;
import java.awt.Color;
import me.oringo.oringoclient.OringoClient;
import me.oringo.oringoclient.commands.Command;
import me.oringo.oringoclient.qolfeatures.module.impl.combat.AimAssist;
import me.oringo.oringoclient.qolfeatures.module.impl.combat.KillAura;
import me.oringo.oringoclient.qolfeatures.module.impl.movement.Scaffold;
import me.oringo.oringoclient.qolfeatures.module.impl.other.AntiNicker;
import me.oringo.oringoclient.qolfeatures.module.impl.other.Disabler;
import me.oringo.oringoclient.qolfeatures.module.impl.other.KillInsults;
import me.oringo.oringoclient.qolfeatures.module.impl.other.MurdererFinder;
import me.oringo.oringoclient.qolfeatures.module.impl.render.ChinaHat;
import me.oringo.oringoclient.qolfeatures.module.impl.render.HidePlayers;
import me.oringo.oringoclient.qolfeatures.module.impl.skyblock.AutoRogueSword;
import me.oringo.oringoclient.qolfeatures.module.impl.skyblock.AutoTerminals;
import me.oringo.oringoclient.ui.hud.DraggableComponent;
import me.oringo.oringoclient.ui.hud.HudVec;
import me.oringo.oringoclient.utils.MilliTimer;
import me.oringo.oringoclient.utils.RenderUtils;
import me.oringo.oringoclient.utils.font.Fonts;
import me.oringo.oringoclient.utils.shader.BlurUtils;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import org.lwjgl.opengl.GL11;

public class TargetComponent extends DraggableComponent {
  public static MilliTimer ;
  
  public float  = 0.8F;
  
  public static MilliTimer ;
  
  public static MilliTimer ;
  
  public static TargetComponent  = new TargetComponent();
  
  public static EntityLivingBase ;
  
  static {
     = new MilliTimer();
     = new MilliTimer();
     = new MilliTimer();
  }
  
  public HudVec (EntityLivingBase paramEntityLivingBase) {
    if (paramEntityLivingBase != null) {
      if ( == null)
        .(); 
      .();
    } 
    if (.(750L) || paramEntityLivingBase != null)
       = paramEntityLivingBase; 
    if ( != null) {
      byte b2;
      int i;
      float f2;
      super.();
      double d1 = ();
      double d2 = ();
      GL11.glPushMatrix();
      byte b1 = 0;
      switch ((Command.())..()) {
        case "Low":
          b1 = 3;
          break;
        case "High":
          b1 = 7;
          break;
      } 
      ScaledResolution scaledResolution = new ScaledResolution(mc);
      GL11.glPushMatrix();
      AutoTerminals.(d1 + 75.0D, d2 + 25.0D, 0.0D);
      float f1;
      for (f1 = 0.0F; f1 < 3.0F; f1 += 0.5F) {
        float f = (d1 + 75.0D > scaledResolution.func_78326_a() / 2.0D) ? f1 : -f1;
        RenderUtils.(d1 - f, d2 + f1, 150.0D, 50.0D, 5.0D, (new Color(20, 20, 20, 30)).getRGB());
      } 
      KillInsults.();
      KillInsults.();
      RenderUtils.(d1, d2, 150.0D, 50.0D, 5.0D, Color.black.getRGB());
      Scaffold.(1);
      GL11.glPopMatrix();
      BlurUtils.renderBlurredBackground(b1, scaledResolution.func_78326_a(), scaledResolution.func_78328_b(), 0.0F, 0.0F, scaledResolution.func_78326_a(), scaledResolution.func_78328_b());
      AutoRogueSword.();
      AutoTerminals.(d1 + 75.0D, d2 + 25.0D, 0.0D);
      f1 = this. + (MurdererFinder.() - this.) / 7.0F;
      if (mc.field_71462_r instanceof net.minecraft.client.gui.GuiChat && ()) {
        ChinaHat.((float)d1, (float)d2, 150.0F, 50.0F, 5.0F, 2.0F, (new Color(21, 21, 21, 52)).getRGB(), Color.white.getRGB());
      } else {
        RenderUtils.(d1, d2, 150.0D, 50.0D, 5.0D, (new Color(21, 21, 21, 52)).getRGB());
      } 
      String str = String.valueOf((new StringBuilder()).append(String.format("%.0f", new Object[] { Float.valueOf(MurdererFinder.() * 100.0F) })).append("%"));
      switch ((Command.())..()) {
        case "Old":
          Fonts..(ChatFormatting.stripFormatting(.func_70005_c_()), d1 + 5.0D, d2 + 6.0D, OringoClient..(0).brighter().getRGB());
          GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
          try {
            .field_70163_u += 1000.0D;
            GuiInventory.func_147046_a((int)(d1 + 130.0D), (int)(d2 + 40.0D), (int)(35.0D / Math.max(.field_70131_O, 1.5D)), 20.0F, 10.0F, );
            .field_70163_u -= 1000.0D;
          } catch (Exception exception) {}
          Fonts..(String.valueOf((new StringBuilder()).append((int)(.func_70032_d((Entity)mc.field_71439_g) * 10.0F) / 10.0D).append("m")), d1 + 5.0D, d2 + 11.0D + Fonts..(), (new Color(231, 231, 231)).getRGB());
          Scaffold.(d1 + 10.0D, d2 + 42.0D, d1 + 140.0D, d2 + 46.0D, 2.0D, Color.HSBtoRGB(0.0F, 0.0F, 0.1F));
          if (f1 > 0.05D)
            Scaffold.(d1 + 10.0D, d2 + 42.0D, d1 + (140.0F * f1), d2 + 46.0D, 2.0D, OringoClient..(0).getRGB()); 
          Fonts..(str, d1 + 75.0D - Fonts..(str) / 2.0D, d2 + 33.0D, (new Color(231, 231, 231)).getRGB());
          break;
        case "New":
          b2 = 36;
          i = (50 - b2) / 2;
          f2 = .field_70737_aN - (Disabler.()).field_74281_c;
          GL11.glPushMatrix();
          if (f2 > 0.0F && !.field_70128_L) {
            float f = f2 * 20.0F;
            AntiNicker.(new Color(255, (int)(255.0F - f), (int)(255.0F - f)));
            HidePlayers.(((int)d1 + 25), ((int)d2 + 25), 1.0D - (f2 / .field_70738_aO) * 0.2D);
          } 
          AimAssist.((int)d1 + 5, (int)d2 + i, 3.0F, 3.0F, 3, 3, b2, b2, 24.0F, 24.5F, );
          AimAssist.((int)d1 + 5, (int)d2 + i, 15.0F, 3.0F, 3, 3, b2, b2, 24.0F, 24.5F, );
          GL11.glPopMatrix();
          Fonts..(ChatFormatting.stripFormatting(.func_70005_c_()), ((int)d1 + 5 + b2 + 3), d2 + i + 1.0D, OringoClient..(0).getRGB());
          Fonts..(String.valueOf((new StringBuilder()).append((int)(.func_70032_d((Entity)mc.field_71439_g) * 10.0F) / 10.0D).append("m")), ((int)d1 + 5 + b2 + 4), ((int)d2 + i + 6 + Fonts..()), Color.white.getRGB());
          Fonts..(str, ((int)d1 + b2 + 9) + 45.0D, d2 + i + 29.0D - Fonts..(), Color.white.getRGB());
          RenderUtils.(((int)d1 + b2 + 9), ((int)d2 + i + 30), 90.0D, 5.0D, 3.0D, (new Color(20, 20, 20, 100)).getRGB());
          RenderUtils.(((int)d1 + b2 + 9), ((int)d2 + i + 30), Math.max(6.0F, 90.0F * f1), 5.0D, 3.0D, OringoClient..(0).getRGB());
          break;
      } 
      if (.(16L, true))
        this. = f1; 
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      GL11.glPopMatrix();
    } 
    (Command.())..(this.);
    (Command.())..(this.);
    return new HudVec(this. + (), this. + ());
  }
  
  public static <T> T (Iterable<T> paramIterable) {
    return (T)Iterables.getFirst(paramIterable, null);
  }
  
  public TargetComponent() {
    (150.0D, 50.0D);
  }
  
  public HudVec () {
    return ((KillAura. == null && mc.field_71462_r instanceof net.minecraft.client.gui.GuiChat) ? (EntityLivingBase)mc.field_71439_g : KillAura.);
  }
}
