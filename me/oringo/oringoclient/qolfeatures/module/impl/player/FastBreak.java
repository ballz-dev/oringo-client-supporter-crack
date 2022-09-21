package me.oringo.oringoclient.qolfeatures.module.impl.player;

import me.oringo.oringoclient.OringoClient;
import me.oringo.oringoclient.events.impl.MotionUpdateEvent;
import me.oringo.oringoclient.mixins.PlayerControllerAccessor;
import me.oringo.oringoclient.qolfeatures.module.Module;
import me.oringo.oringoclient.qolfeatures.module.impl.other.Disabler;
import me.oringo.oringoclient.qolfeatures.module.settings.Setting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.BooleanSetting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.NumberSetting;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class FastBreak extends Module {
  public static boolean ;
  
  public BooleanSetting  = new BooleanSetting("Ground spoof", false);
  
  public NumberSetting  = new NumberSetting("Additional blocks", 0.0D, 0.0D, 4.0D, 1.0D);
  
  public BooleanSetting  = new BooleanSetting("No swing", true);
  
  public NumberSetting  = new NumberSetting("Speed", 1.0D, 1.0D, 2.0D, 0.1D);
  
  public static MovingObjectPosition (float paramFloat1, float paramFloat2, float paramFloat3) {
    Vec3 vec31 = OringoClient.mc.field_71439_g.func_174824_e(1.0F);
    Vec3 vec32 = InvManager.(paramFloat1, paramFloat2);
    Vec3 vec33 = vec31.func_72441_c(vec32.field_72450_a * paramFloat3, vec32.field_72448_b * paramFloat3, vec32.field_72449_c * paramFloat3);
    return OringoClient.mc.field_71441_e.func_147447_a(vec31, vec33, false, true, true);
  }
  
  public FastBreak() {
    super("Fast Break", 0, Module.Category.);
    (new Setting[] { (Setting)this., (Setting)this., (Setting)this., (Setting)this. });
  }
  
  @SubscribeEvent
  public void (MotionUpdateEvent.Pre paramPre) {
    if (!())
      return; 
    if (this..() == 0.0D)
      ((PlayerControllerAccessor)mc.field_71442_b).setBlockHitDelay(0); 
    if (((PlayerControllerAccessor)mc.field_71442_b).getCurBlockDamageMP() >= 2.0D - this..())
      ((PlayerControllerAccessor)mc.field_71442_b).setCurBlockDamageMP(1.0F); 
    if (this..() && Disabler. && (mc.field_71442_b.func_181040_m() || )) {
      paramPre.setOnGround(true);
       = mc.field_71442_b.func_181040_m();
    } 
  }
}
