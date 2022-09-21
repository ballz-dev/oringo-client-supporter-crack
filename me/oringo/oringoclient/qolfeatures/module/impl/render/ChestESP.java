package me.oringo.oringoclient.qolfeatures.module.impl.render;

import java.util.stream.Collectors;
import me.oringo.oringoclient.OringoClient;
import me.oringo.oringoclient.commands.impl.XRayCommand;
import me.oringo.oringoclient.events.impl.RenderChestEvent;
import me.oringo.oringoclient.qolfeatures.module.Module;
import me.oringo.oringoclient.qolfeatures.module.impl.other.LightningDetect;
import me.oringo.oringoclient.qolfeatures.module.impl.player.InvManager;
import me.oringo.oringoclient.qolfeatures.module.settings.Setting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.BooleanSetting;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;

public class ChestESP extends Module {
  public BooleanSetting  = new BooleanSetting("Tracer", true);
  
  public boolean ;
  
  @SubscribeEvent
  public void (RenderWorldLastEvent paramRenderWorldLastEvent) {
    if (!() || !this..())
      return; 
    for (TileEntity tileEntity : mc.field_71441_e.field_147482_g.stream().filter(ChestESP::).collect(Collectors.toList()))
      XRayCommand.(tileEntity.func_174877_v().func_177958_n() + 0.5D, tileEntity.func_174877_v().func_177956_o() + 0.5D, tileEntity.func_174877_v().func_177952_p() + 0.5D, OringoClient..()); 
  }
  
  @SubscribeEvent
  public void (RenderChestEvent paramRenderChestEvent) {
    if (())
      if (paramRenderChestEvent.isPre() && paramRenderChestEvent.getChest() == mc.field_71441_e.func_175625_s(paramRenderChestEvent.getChest().func_174877_v())) {
        InvManager.();
        this. = true;
      } else if (this.) {
        LightningDetect.();
        this. = false;
      }  
  }
  
  public static void () {
    GlStateManager.func_179138_g(OpenGlHelper.field_77478_a);
    GlStateManager.func_179098_w();
    GL11.glTexEnvi(8960, 8704, OpenGlHelper.field_176095_s);
    GL11.glTexEnvi(8960, OpenGlHelper.field_176099_x, 8448);
    GL11.glTexEnvi(8960, OpenGlHelper.field_176098_y, OpenGlHelper.field_77478_a);
    GL11.glTexEnvi(8960, OpenGlHelper.field_176097_z, OpenGlHelper.field_176093_u);
    GL11.glTexEnvi(8960, OpenGlHelper.field_176081_B, 768);
    GL11.glTexEnvi(8960, OpenGlHelper.field_176082_C, 768);
    GL11.glTexEnvi(8960, OpenGlHelper.field_176077_E, 8448);
    GL11.glTexEnvi(8960, OpenGlHelper.field_176078_F, OpenGlHelper.field_77478_a);
    GL11.glTexEnvi(8960, OpenGlHelper.field_176079_G, OpenGlHelper.field_176093_u);
    GL11.glTexEnvi(8960, OpenGlHelper.field_176085_I, 770);
    GL11.glTexEnvi(8960, OpenGlHelper.field_176086_J, 770);
    GlStateManager.func_179138_g(OpenGlHelper.field_77476_b);
    GL11.glTexEnvi(8960, 8704, OpenGlHelper.field_176095_s);
    GL11.glTexEnvi(8960, OpenGlHelper.field_176099_x, 8448);
    GL11.glTexEnvi(8960, OpenGlHelper.field_176081_B, 768);
    GL11.glTexEnvi(8960, OpenGlHelper.field_176082_C, 768);
    GL11.glTexEnvi(8960, OpenGlHelper.field_176098_y, 5890);
    GL11.glTexEnvi(8960, OpenGlHelper.field_176097_z, OpenGlHelper.field_176091_w);
    GL11.glTexEnvi(8960, OpenGlHelper.field_176077_E, 8448);
    GL11.glTexEnvi(8960, OpenGlHelper.field_176085_I, 770);
    GL11.glTexEnvi(8960, OpenGlHelper.field_176078_F, 5890);
    GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
    GlStateManager.func_179138_g(OpenGlHelper.field_176096_r);
    GlStateManager.func_179090_x();
    GlStateManager.func_179144_i(0);
    GL11.glTexEnvi(8960, 8704, OpenGlHelper.field_176095_s);
    GL11.glTexEnvi(8960, OpenGlHelper.field_176099_x, 8448);
    GL11.glTexEnvi(8960, OpenGlHelper.field_176081_B, 768);
    GL11.glTexEnvi(8960, OpenGlHelper.field_176082_C, 768);
    GL11.glTexEnvi(8960, OpenGlHelper.field_176098_y, 5890);
    GL11.glTexEnvi(8960, OpenGlHelper.field_176097_z, OpenGlHelper.field_176091_w);
    GL11.glTexEnvi(8960, OpenGlHelper.field_176077_E, 8448);
    GL11.glTexEnvi(8960, OpenGlHelper.field_176085_I, 770);
    GL11.glTexEnvi(8960, OpenGlHelper.field_176078_F, 5890);
    GlStateManager.func_179138_g(OpenGlHelper.field_77478_a);
  }
  
  public ChestESP() {
    super("ChestESP", Module.Category.);
    (new Setting[] { (Setting)this. });
  }
}
