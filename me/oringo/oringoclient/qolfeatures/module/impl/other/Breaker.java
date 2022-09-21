package me.oringo.oringoclient.qolfeatures.module.impl.other;

import java.util.ArrayList;
import java.util.List;
import me.oringo.oringoclient.events.impl.MotionUpdateEvent;
import me.oringo.oringoclient.qolfeatures.LoginWithSession;
import me.oringo.oringoclient.qolfeatures.module.Module;
import me.oringo.oringoclient.qolfeatures.module.impl.player.AntiVoid;
import me.oringo.oringoclient.qolfeatures.module.settings.Setting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.BooleanSetting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.ModeSetting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.NumberSetting;
import me.oringo.oringoclient.utils.Rotation;
import me.oringo.oringoclient.utils.RotationUtils;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Vec3;
import net.minecraft.util.Vec3i;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class Breaker extends Module {
  public static ModeSetting ;
  
  public static NumberSetting ;
  
  public static BooleanSetting ;
  
  public static ModeSetting ;
  
  public static NumberSetting ;
  
  public static NumberSetting ;
  
  public static List<BlockPos>  = new ArrayList<>();
  
  static {
     = new ModeSetting("Target", "Bed", new String[] { "Bed", "Cake" });
     = new ModeSetting("Mode", "Left", new String[] { "Left", "Right" });
     = new BooleanSetting("Rotate", false);
     = new NumberSetting("Rotation speed", 80.0D, 10.0D, 360.0D, 1.0D);
     = new NumberSetting("Range", 4.5D, 1.0D, 6.0D, 0.1D);
     = new NumberSetting("Max targets", 1.0D, 1.0D, 10.0D, 1.0D, Breaker::);
  }
  
  @SubscribeEvent
  public void (MotionUpdateEvent paramMotionUpdateEvent) {
    if (())
      if (paramMotionUpdateEvent.isPre()) {
        LoginWithSession.();
        if (.() && !.isEmpty()) {
          Rotation rotation = GuessTheBuildAFK.(AntiVoid.(), RotationUtils.((new Vec3((Vec3i).get(0))).func_72441_c(0.5D, 0.5D, 0.5D)), (float).());
          paramMotionUpdateEvent.setRotation(rotation);
        } 
      } else if (!.isEmpty()) {
        for (BlockPos blockPos : ) {
          if (!.() || LightningDetect.(paramMotionUpdateEvent.getRotation(), RotationUtils.((new Vec3((Vec3i)blockPos)).func_72441_c(0.5D, 0.5D, 0.5D))) < 5.0D)
            switch (.()) {
              case "Left":
                if (mc.field_71442_b.func_180512_c(blockPos, EnumFacing.func_176733_a(paramMotionUpdateEvent.)))
                  mc.field_71439_g.func_71038_i(); 
              case "Right":
                if (mc.field_71442_b.func_178890_a(mc.field_71439_g, mc.field_71441_e, mc.field_71439_g.field_71071_by.func_70448_g(), blockPos, EnumFacing.func_176733_a(paramMotionUpdateEvent.), new Vec3(0.0D, 0.0D, 0.0D)))
                  mc.field_71439_g.func_71038_i(); 
            }  
        } 
      }  
  }
  
  public Breaker() {
    super("Breaker", Module.Category.OTHER);
    (new Setting[] { (Setting), (Setting), (Setting), (Setting), (Setting), (Setting) });
    .(Breaker::);
  }
}
