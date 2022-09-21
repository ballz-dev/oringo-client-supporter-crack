package me.oringo.oringoclient.qolfeatures.module.impl.render;

import me.oringo.oringoclient.qolfeatures.module.Module;
import me.oringo.oringoclient.qolfeatures.module.settings.Setting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.BooleanSetting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.NumberSetting;
import me.oringo.oringoclient.utils.OringoPacketLog;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;

public class Camera extends Module {
  public static BooleanSetting ;
  
  public static BooleanSetting ;
  
  public static BooleanSetting  = new BooleanSetting("Camera Clip", true);
  
  public static NumberSetting ;
  
  public static BooleanSetting ;
  
  public static NumberSetting ;
  
  public static NumberSetting ;
  
  public static boolean (Block paramBlock) {
    return (paramBlock instanceof net.minecraft.block.BlockStairs || paramBlock instanceof net.minecraft.block.BlockFence || paramBlock instanceof net.minecraft.block.BlockFenceGate || paramBlock instanceof net.minecraft.block.BlockWall || paramBlock == Blocks.field_150438_bZ || paramBlock instanceof net.minecraft.block.BlockSkull);
  }
  
  static {
     = new BooleanSetting("No hurt cam", false);
     = new BooleanSetting("Smooth f5", true);
     = new BooleanSetting("No front", false);
     = new NumberSetting("Camera Distance", 4.0D, 2.0D, 10.0D, 0.1D);
     = new NumberSetting("Smooth speed", 1.0D, 0.1D, 5.0D, 0.1D, Camera::);
     = new NumberSetting("Start distance", 3.0D, 1.0D, 10.0D, 0.1D, Camera::);
  }
  
  public Camera() {
    super("Camera", Module.Category.);
    (new Setting[] { (Setting), (Setting), (Setting), (Setting), (Setting), (Setting), (Setting) });
  }
  
  public static boolean () {
    return (OringoPacketLog. != null && OringoPacketLog..isVisible());
  }
}
