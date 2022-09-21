package me.oringo.oringoclient.qolfeatures.module.impl.render;

import me.oringo.oringoclient.qolfeatures.module.Module;
import me.oringo.oringoclient.qolfeatures.module.settings.Setting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.BooleanSetting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.NumberSetting;
import me.oringo.oringoclient.utils.MilliTimer;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class PopupAnimation extends Module {
  public static BooleanSetting ;
  
  public static MilliTimer ;
  
  public static NumberSetting ;
  
  public static BooleanSetting ;
  
  public static NumberSetting ;
  
  public static BooleanSetting ;
  
  public static MilliTimer  = new MilliTimer();
  
  @SubscribeEvent
  public void (GuiOpenEvent paramGuiOpenEvent) {
    if (mc.field_71462_r == null && .(150L))
      .(); 
  }
  
  static {
     = new MilliTimer();
     = new BooleanSetting("Click Gui", true);
     = new BooleanSetting("Inventory", false);
     = new BooleanSetting("Chests", false);
     = new NumberSetting("Starting size", 0.75D, 0.01D, 1.0D, 0.01D);
     = new NumberSetting("Time", 200.0D, 0.0D, 1000.0D, 10.0D);
  }
  
  public PopupAnimation() {
    super("Popup Animation", Module.Category.);
    (true);
    (new Setting[] { (Setting), (Setting), (Setting), (Setting), (Setting) });
  }
  
  @SubscribeEvent
  public void (RenderWorldLastEvent paramRenderWorldLastEvent) {
    if (mc.field_71462_r != null)
      .(); 
  }
}
