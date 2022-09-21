package me.oringo.oringoclient.qolfeatures.module.impl.movement;

import me.oringo.oringoclient.events.impl.PostGuiOpenEvent;
import me.oringo.oringoclient.qolfeatures.module.Module;
import me.oringo.oringoclient.qolfeatures.module.impl.other.Disabler;
import me.oringo.oringoclient.qolfeatures.module.settings.Setting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.BooleanSetting;
import me.oringo.oringoclient.utils.font.Fonts;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.inventory.ContainerChest;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class GuiMove extends Module {
  public static BooleanSetting  = new BooleanSetting("Hide terminals", false);
  
  public static BooleanSetting  = new BooleanSetting("Sneak", true);
  
  public static KeyBinding[] ;
  
  public static BooleanSetting  = new BooleanSetting("Melody terminal", true, GuiMove::);
  
  public void () {
    if (mc.field_71462_r != null)
      for (KeyBinding keyBinding : )
        KeyBinding.func_74510_a(keyBinding.func_151463_i(), false);  
  }
  
  @SubscribeEvent
  public void (RenderWorldLastEvent paramRenderWorldLastEvent) {
    if (mc.field_71462_r != null && !(mc.field_71462_r instanceof net.minecraft.client.gui.GuiChat) && !(mc.field_71462_r instanceof net.minecraft.client.gui.inventory.GuiEditSign) && () && Disabler.)
      for (KeyBinding keyBinding : )
        KeyBinding.func_74510_a(keyBinding.func_151463_i(), ((keyBinding.func_151463_i() != mc.field_71474_y.field_74311_E.func_151463_i() || .()) && GameSettings.func_100015_a(keyBinding)));  
  }
  
  public boolean (ContainerChest paramContainerChest) {
    return (Fonts.(paramContainerChest.func_85151_d().func_70005_c_()) && () && .() && (.() || !paramContainerChest.func_85151_d().func_70005_c_().contains("Click the button on time!")));
  }
  
  static {
     = new KeyBinding[] { mc.field_71474_y.field_74311_E, mc.field_71474_y.field_74314_A, mc.field_71474_y.field_151444_V, mc.field_71474_y.field_74351_w, mc.field_71474_y.field_74368_y, mc.field_71474_y.field_74370_x, mc.field_71474_y.field_74366_z };
  }
  
  @SubscribeEvent
  public void (PostGuiOpenEvent paramPostGuiOpenEvent) {
    if (!(paramPostGuiOpenEvent. instanceof net.minecraft.client.gui.GuiChat) && !(paramPostGuiOpenEvent. instanceof net.minecraft.client.gui.inventory.GuiEditSign) && () && Disabler.)
      for (KeyBinding keyBinding : )
        KeyBinding.func_74510_a(keyBinding.func_151463_i(), ((keyBinding.func_151463_i() != mc.field_71474_y.field_74311_E.func_151463_i() || .()) && GameSettings.func_100015_a(keyBinding)));  
  }
  
  public GuiMove() {
    super("Inv Move", Module.Category.);
    (new Setting[] { (Setting), (Setting), (Setting) });
  }
}
