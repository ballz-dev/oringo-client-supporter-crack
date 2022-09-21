package me.oringo.oringoclient.qolfeatures.module.impl.other;

import com.mojang.realmsclient.gui.ChatFormatting;
import java.awt.Color;
import me.oringo.oringoclient.qolfeatures.module.Module;
import me.oringo.oringoclient.qolfeatures.module.impl.skyblock.AutoQuiz;
import me.oringo.oringoclient.qolfeatures.module.settings.Setting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.BooleanSetting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.NumberSetting;
import me.oringo.oringoclient.utils.MobRenderUtils;
import me.oringo.oringoclient.utils.PlayerUtils;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.opengl.GL11;

public class Timer extends Module {
  public static NumberSetting  = new NumberSetting("Timer", 1.0D, 0.1D, 10.0D, 0.1D);
  
  public Timer() {
    super("Timer", Module.Category.OTHER);
    (new Setting[] { (Setting) });
  }
  
  public static void (Color paramColor) {
    GlStateManager.func_179138_g(OpenGlHelper.field_77478_a);
    GlStateManager.func_179098_w();
    GL11.glTexEnvi(8960, 8704, OpenGlHelper.field_176095_s);
    GL11.glTexEnvi(8960, OpenGlHelper.field_176099_x, 8448);
    GL11.glTexEnvi(8960, OpenGlHelper.field_176098_y, OpenGlHelper.field_77478_a);
    GL11.glTexEnvi(8960, OpenGlHelper.field_176097_z, OpenGlHelper.field_176093_u);
    GL11.glTexEnvi(8960, OpenGlHelper.field_176081_B, 768);
    GL11.glTexEnvi(8960, OpenGlHelper.field_176082_C, 768);
    GL11.glTexEnvi(8960, OpenGlHelper.field_176077_E, 7681);
    GL11.glTexEnvi(8960, OpenGlHelper.field_176078_F, OpenGlHelper.field_77478_a);
    GL11.glTexEnvi(8960, OpenGlHelper.field_176085_I, 770);
    GlStateManager.func_179138_g(OpenGlHelper.field_77476_b);
    GlStateManager.func_179098_w();
    GL11.glTexEnvi(8960, 8704, OpenGlHelper.field_176095_s);
    GL11.glTexEnvi(8960, OpenGlHelper.field_176099_x, OpenGlHelper.field_176094_t);
    GL11.glTexEnvi(8960, OpenGlHelper.field_176098_y, OpenGlHelper.field_176092_v);
    GL11.glTexEnvi(8960, OpenGlHelper.field_176097_z, OpenGlHelper.field_176091_w);
    GL11.glTexEnvi(8960, OpenGlHelper.field_176080_A, OpenGlHelper.field_176092_v);
    GL11.glTexEnvi(8960, OpenGlHelper.field_176081_B, 768);
    GL11.glTexEnvi(8960, OpenGlHelper.field_176082_C, 768);
    GL11.glTexEnvi(8960, OpenGlHelper.field_176076_D, 770);
    GL11.glTexEnvi(8960, OpenGlHelper.field_176077_E, 7681);
    GL11.glTexEnvi(8960, OpenGlHelper.field_176078_F, OpenGlHelper.field_176091_w);
    GL11.glTexEnvi(8960, OpenGlHelper.field_176085_I, 770);
    MobRenderUtils..position(0);
    MobRenderUtils..put(paramColor.getRed() / 255.0F);
    MobRenderUtils..put(paramColor.getGreen() / 255.0F);
    MobRenderUtils..put(paramColor.getBlue() / 255.0F);
    MobRenderUtils..put(paramColor.getAlpha() / 255.0F);
    MobRenderUtils..flip();
    GL11.glTexEnv(8960, 8705, MobRenderUtils.);
    GlStateManager.func_179138_g(OpenGlHelper.field_176096_r);
    GlStateManager.func_179098_w();
    GlStateManager.func_179144_i(MobRenderUtils..func_110552_b());
    GL11.glTexEnvi(8960, 8704, OpenGlHelper.field_176095_s);
    GL11.glTexEnvi(8960, OpenGlHelper.field_176099_x, 8448);
    GL11.glTexEnvi(8960, OpenGlHelper.field_176098_y, OpenGlHelper.field_176091_w);
    GL11.glTexEnvi(8960, OpenGlHelper.field_176097_z, OpenGlHelper.field_77476_b);
    GL11.glTexEnvi(8960, OpenGlHelper.field_176081_B, 768);
    GL11.glTexEnvi(8960, OpenGlHelper.field_176082_C, 768);
    GL11.glTexEnvi(8960, OpenGlHelper.field_176077_E, 7681);
    GL11.glTexEnvi(8960, OpenGlHelper.field_176078_F, OpenGlHelper.field_176091_w);
    GL11.glTexEnvi(8960, OpenGlHelper.field_176085_I, 770);
    GlStateManager.func_179138_g(OpenGlHelper.field_77478_a);
  }
  
  public static boolean (EntityArmorStand paramEntityArmorStand) {
    if (paramEntityArmorStand.func_70032_d((Entity)AutoQuiz.mc.field_71439_g) > AutoQuiz..())
      return false; 
    boolean bool = false;
    String str = ChatFormatting.stripFormatting(paramEntityArmorStand.func_145748_c_().func_150254_d());
    for (String str1 : AutoQuiz.) {
      if (str.contains(str1)) {
        bool = true;
        break;
      } 
    } 
    return ((str.contains("ⓐ") || str.contains("ⓑ") || str.contains("ⓒ")) && bool);
  }
  
  @SubscribeEvent
  public void (TickEvent.ClientTickEvent paramClientTickEvent) {
    if (paramClientTickEvent.phase == TickEvent.Phase.START && ())
      PlayerUtils.((float).()); 
  }
  
  public void () {
    if (Disabler.() != null)
      BooleanSetting.(); 
  }
}
