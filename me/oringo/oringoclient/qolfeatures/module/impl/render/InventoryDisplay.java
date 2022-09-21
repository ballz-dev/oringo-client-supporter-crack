package me.oringo.oringoclient.qolfeatures.module.impl.render;

import java.awt.Color;
import me.oringo.oringoclient.events.impl.GuiChatEvent;
import me.oringo.oringoclient.qolfeatures.module.Module;
import me.oringo.oringoclient.qolfeatures.module.impl.other.AntiNicker;
import me.oringo.oringoclient.qolfeatures.module.settings.Setting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.ModeSetting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.NumberSetting;
import me.oringo.oringoclient.ui.hud.impl.InventoryHUD;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Vec3;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;

public class InventoryDisplay extends Module {
  public ModeSetting  = new ModeSetting("Blur Strength", "Low", new String[] { "None", "Low", "High" });
  
  public NumberSetting  = new NumberSetting("X1234", 0.0D, -100000.0D, 100000.0D, 1.0E-5D, InventoryDisplay::);
  
  public NumberSetting  = new NumberSetting("Y1234", 0.0D, -100000.0D, 100000.0D, 1.0E-5D, InventoryDisplay::);
  
  @SubscribeEvent
  public void (GuiChatEvent paramGuiChatEvent) {
    if (!())
      return; 
    InventoryHUD inventoryHUD = InventoryHUD.;
    if (paramGuiChatEvent instanceof GuiChatEvent.MouseClicked) {
      if (inventoryHUD.(paramGuiChatEvent., paramGuiChatEvent.))
        inventoryHUD.(); 
    } else if (paramGuiChatEvent instanceof GuiChatEvent.MouseReleased) {
      inventoryHUD.();
    } else if (paramGuiChatEvent instanceof GuiChatEvent.Closed) {
      inventoryHUD.();
    } else if (paramGuiChatEvent instanceof GuiChatEvent.DrawChatEvent) {
    
    } 
  }
  
  @SubscribeEvent
  public void (RenderGameOverlayEvent.Post paramPost) {
    if (() && paramPost.type.equals(RenderGameOverlayEvent.ElementType.HOTBAR) && mc.field_71439_g != null)
      InventoryHUD..(); 
  }
  
  public static void (Vec3 paramVec31, Vec3 paramVec32, Color paramColor) {
    GL11.glPushMatrix();
    GL11.glBlendFunc(770, 771);
    GL11.glEnable(3042);
    GL11.glLineWidth(2.5F);
    GL11.glDisable(3553);
    GL11.glDisable(2929);
    GL11.glDepthMask(false);
    AntiNicker.(paramColor);
    GL11.glTranslated(-(Minecraft.func_71410_x().func_175598_ae()).field_78730_l, -(Minecraft.func_71410_x().func_175598_ae()).field_78731_m, -(Minecraft.func_71410_x().func_175598_ae()).field_78728_n);
    GL11.glBegin(1);
    GL11.glVertex3d(paramVec31.field_72450_a, paramVec31.field_72448_b, paramVec31.field_72449_c);
    GL11.glVertex3d(paramVec32.field_72450_a, paramVec32.field_72448_b, paramVec32.field_72449_c);
    GL11.glEnd();
    GL11.glEnable(3553);
    GL11.glEnable(2929);
    GL11.glDepthMask(true);
    GL11.glDisable(3042);
    GL11.glPopMatrix();
  }
  
  public InventoryDisplay() {
    super("Inventory HUD", 0, Module.Category.);
    (new Setting[] { (Setting)this., (Setting)this., (Setting)this. });
  }
}
