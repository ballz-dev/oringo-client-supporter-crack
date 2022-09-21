package me.oringo.oringoclient.qolfeatures.module.impl.render;

import me.oringo.oringoclient.events.impl.PostGuiOpenEvent;
import me.oringo.oringoclient.qolfeatures.module.Module;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Mouse;

public class NoRenderOld extends Module {
  static {
  
  }
  
  public void () {
    mc.field_71454_w = false;
  }
  
  @SubscribeEvent
  public void (PostGuiOpenEvent paramPostGuiOpenEvent) {
    if (())
      mc.field_71454_w = true; 
  }
  
  public void () {
    mc.field_71454_w = true;
  }
  
  public NoRenderOld() {
    super("NoRender", Module.Category.);
  }
}
