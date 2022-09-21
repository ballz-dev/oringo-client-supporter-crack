package me.oringo.oringoclient.qolfeatures.module.impl.other;

import me.oringo.oringoclient.events.impl.MotionUpdateEvent;
import me.oringo.oringoclient.qolfeatures.module.Module;
import me.oringo.oringoclient.qolfeatures.module.impl.player.InvManager;
import me.oringo.oringoclient.qolfeatures.module.settings.Setting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.NumberSetting;
import me.oringo.oringoclient.utils.PlayerUtils;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ResetVL extends Module {
  public static NumberSetting  = new NumberSetting("Timer", 1.0D, 0.1D, 3.0D, 0.05D);
  
  public int ;
  
  public static NumberSetting  = new NumberSetting("Jumps", 10.0D, 1.0D, 25.0D, 1.0D);
  
  @SubscribeEvent
  public void (MotionUpdateEvent.Pre paramPre) {
    if (())
      if (this. <= .()) {
        if (mc.field_71439_g.field_70122_E) {
          this.++;
          mc.field_71439_g.field_70181_x = 0.11D;
        } 
        PlayerUtils.((float).());
      } else {
        ();
      }  
  }
  
  public ResetVL() {
    super("Reset VL", Module.Category.OTHER);
    (new Setting[] { (Setting), (Setting) });
  }
  
  public void () {
    this. = 0;
    if (mc.field_71439_g == null || !mc.field_71439_g.field_70122_E)
      (); 
  }
  
  public void () {
    PlayerUtils.(1.0F);
  }
  
  public static boolean (ItemStack paramItemStack) {
    for (String str : InvManager.) {
      if (paramItemStack.func_82833_r().contains(str))
        return true; 
    } 
    return false;
  }
}
