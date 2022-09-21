package me.oringo.oringoclient.qolfeatures.module.impl.render;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import me.oringo.oringoclient.OringoClient;
import me.oringo.oringoclient.qolfeatures.module.Module;
import me.oringo.oringoclient.qolfeatures.module.impl.combat.Reach;
import me.oringo.oringoclient.qolfeatures.module.impl.skyblock.BoneThrower;
import me.oringo.oringoclient.qolfeatures.module.settings.Setting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.ModeSetting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.NumberSetting;
import me.oringo.oringoclient.utils.MoveUtils;
import net.minecraft.util.Vec3;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;

public class Trial extends Module {
  public static int ;
  
  public static ModeSetting ;
  
  public static NumberSetting  = new NumberSetting("Points", 20.0D, 5.0D, 100.0D, 1.0D);
  
  public static List<Vec3> ;
  
  static {
     = new ModeSetting("Mode", "Crumbs", new String[] { "Crumbs", "Trail" });
     = new ArrayList<>();
  }
  
  @SubscribeEvent
  public void (RenderWorldLastEvent paramRenderWorldLastEvent) {
    if (() && mc.field_71439_g != null && mc.func_175598_ae() != null) {
      byte b;
      Color color1;
      Color color2;
      GL11.glBlendFunc(770, 771);
      GL11.glEnable(3042);
      GL11.glDisable(3553);
      GL11.glEnable(2848);
      GL11.glDisable(2884);
      if ( != mc.field_71439_g.field_70173_aa && ()) {
        .add(mc.field_71439_g.func_174791_d().func_72441_c(0.0D, 0.15D, 0.0D));
         = mc.field_71439_g.field_70173_aa;
      } 
      while (.size() > .())
        .remove(0); 
      switch (.()) {
        case "Trail":
          GL11.glShadeModel(7425);
          GL11.glLineWidth(2.5F);
          GL11.glHint(3154, 4354);
          GL11.glBegin(3);
          b = 0;
          for (Vec3 vec3 : ) {
            boolean bool = !b ? true : false;
            Color color = OringoClient..(++b);
            GL11.glColor3f(color.getRed() / 255.0F, color.getGreen() / 255.0F, color.getBlue() / 255.0F);
            if (bool && .size() >= 2 && ()) {
              Vec3 vec31 = .get(1);
              GL11.glVertex3d(BoneThrower.(vec3.field_72450_a, vec31.field_72450_a, paramRenderWorldLastEvent.partialTicks) - (mc.func_175598_ae()).field_78730_l, BoneThrower.(vec3.field_72448_b, vec31.field_72448_b, paramRenderWorldLastEvent.partialTicks) - (mc.func_175598_ae()).field_78731_m, BoneThrower.(vec3.field_72449_c, vec31.field_72449_c, paramRenderWorldLastEvent.partialTicks) - (mc.func_175598_ae()).field_78728_n);
              continue;
            } 
            GL11.glVertex3d(vec3.field_72450_a - (mc.func_175598_ae()).field_78730_l, vec3.field_72448_b - (mc.func_175598_ae()).field_78731_m, vec3.field_72449_c - (mc.func_175598_ae()).field_78728_n);
          } 
          color1 = OringoClient..(b);
          GL11.glColor3f(color1.getRed() / 255.0F, color1.getGreen() / 255.0F, color1.getBlue() / 255.0F);
          GL11.glVertex3d(mc.field_71439_g.field_70169_q + (mc.field_71439_g.field_70165_t - mc.field_71439_g.field_70169_q) * paramRenderWorldLastEvent.partialTicks - (mc.func_175598_ae()).field_78730_l, mc.field_71439_g.field_70167_r + (mc.field_71439_g.field_70163_u - mc.field_71439_g.field_70167_r) * paramRenderWorldLastEvent.partialTicks - (mc.func_175598_ae()).field_78731_m + 0.1D, mc.field_71439_g.field_70166_s + (mc.field_71439_g.field_70161_v - mc.field_71439_g.field_70166_s) * paramRenderWorldLastEvent.partialTicks - (mc.func_175598_ae()).field_78728_n);
          GL11.glEnd();
          GL11.glDisable(2881);
          GL11.glShadeModel(7424);
          GL11.glLineWidth(1.0F);
          break;
        case "Crumbs":
          color2 = OringoClient..();
          Collections.reverse();
          for (Vec3 vec3 : ) {
            GL11.glPushMatrix();
            vec3 = MoveUtils.(vec3);
            GL11.glTranslated(vec3.field_72450_a, vec3.field_72448_b, vec3.field_72449_c);
            GL11.glRotated(-(mc.func_175598_ae()).field_78735_i, 0.0D, 1.0D, 0.0D);
            GL11.glRotated((mc.func_175598_ae()).field_78732_j, 1.0D, 0.0D, 0.0D);
            GL11.glColor4f(color2.getRed() / 255.0F, color2.getGreen() / 255.0F, color2.getBlue() / 255.0F, 0.3F);
            Reach.(0.07D);
            GL11.glColor4f(color2.getRed() / 255.0F, color2.getGreen() / 255.0F, color2.getBlue() / 255.0F, 1.0F);
            Reach.(0.04D);
            GL11.glPopMatrix();
          } 
          Collections.reverse();
          break;
      } 
      GL11.glEnable(2884);
      GL11.glEnable(3553);
      GL11.glDisable(3042);
      GL11.glDisable(2848);
    } else {
      .clear();
    } 
  }
  
  public boolean () {
    return (mc.field_71439_g.field_70161_v != mc.field_71439_g.field_70166_s || mc.field_71439_g.field_70163_u != mc.field_71439_g.field_70167_r || mc.field_71439_g.field_70165_t != mc.field_71439_g.field_70169_q);
  }
  
  public Trial() {
    super("Trail", Module.Category.);
    (new Setting[] { (Setting), (Setting) });
  }
}
