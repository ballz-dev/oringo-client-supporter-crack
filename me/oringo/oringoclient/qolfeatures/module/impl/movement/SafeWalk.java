package me.oringo.oringoclient.qolfeatures.module.impl.movement;

import me.oringo.oringoclient.commands.impl.ClipCommand;
import me.oringo.oringoclient.qolfeatures.module.Module;
import me.oringo.oringoclient.qolfeatures.module.impl.skyblock.BlazeSwapper;
import me.oringo.oringoclient.qolfeatures.module.settings.Setting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.BooleanSetting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.ModeSetting;
import me.oringo.oringoclient.ui.gui.ClickGUI;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class SafeWalk extends Module {
  public static BooleanSetting  = new BooleanSetting("Only Backwards", true);
  
  public static ModeSetting  = new ModeSetting("Mode", "Sneak", new String[] { "Sneak", "Safe walk" });
  
  @SubscribeEvent
  public void (TickEvent.ClientTickEvent paramClientTickEvent) {
    if (mc.field_71439_g == null || mc.field_71441_e == null || !() || mc.field_71462_r != null)
      return; 
    if (.("Sneak")) {
      BlockPos blockPos = new BlockPos(mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u - 0.5D, mc.field_71439_g.field_70161_v);
      if (mc.field_71441_e.func_180495_p(blockPos).func_177230_c() == Blocks.field_150350_a && mc.field_71441_e.func_180495_p(blockPos.func_177977_b()).func_177230_c() == Blocks.field_150350_a && mc.field_71439_g.field_70122_E && (mc.field_71439_g.field_71158_b.field_78900_b < 0.1F || !.())) {
        KeyBinding.func_74510_a(mc.field_71474_y.field_74311_E.func_151463_i(), true);
      } else {
        KeyBinding.func_74510_a(mc.field_71474_y.field_74311_E.func_151463_i(), GameSettings.func_100015_a(mc.field_71474_y.field_74311_E));
      } 
    } 
  }
  
  public static int (BlazeSwapper.Type paramType) {
    return (paramType == BlazeSwapper.Type.) ? -1 : ClipCommand.(paramType::);
  }
  
  public static void () {
    ClickGUI.(BooleanSetting.());
  }
  
  public void () {
    KeyBinding.func_74510_a(mc.field_71474_y.field_74311_E.func_151463_i(), GameSettings.func_100015_a(mc.field_71474_y.field_74311_E));
  }
  
  public SafeWalk() {
    super("Eagle", 0, Module.Category.);
    (new Setting[] { (Setting), (Setting) });
  }
}
