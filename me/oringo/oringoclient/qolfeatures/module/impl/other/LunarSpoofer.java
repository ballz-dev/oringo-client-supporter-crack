package me.oringo.oringoclient.qolfeatures.module.impl.other;

import me.oringo.oringoclient.OringoClient;
import me.oringo.oringoclient.lunarspoof.LunarClient;
import me.oringo.oringoclient.qolfeatures.module.Module;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;

public class LunarSpoofer extends Module {
  public LunarClient ;
  
  public void () {
    if (this..isOpen())
      this..close(); 
  }
  
  public LunarSpoofer() {
    super("Lunar Spoofer", Module.Category.OTHER);
  }
  
  public void () {
    try {
      this. = new LunarClient();
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
    this..connect();
    System.out.println("Connecting");
  }
  
  public static boolean (float paramFloat, double paramDouble1, double paramDouble2) {
    BlockPos blockPos = new BlockPos(OringoClient.mc.field_71439_g.field_70165_t, OringoClient.mc.field_71439_g.field_70163_u, OringoClient.mc.field_71439_g.field_70161_v);
    if (!OringoClient.mc.field_71441_e.func_175667_e(blockPos))
      return false; 
    AxisAlignedBB axisAlignedBB = OringoClient.mc.field_71439_g.func_174813_aQ().func_72317_d(paramDouble1, 0.0D, paramDouble2);
    return OringoClient.mc.field_71441_e.func_72945_a((Entity)OringoClient.mc.field_71439_g, new AxisAlignedBB(axisAlignedBB.field_72340_a, axisAlignedBB.field_72338_b - paramFloat, axisAlignedBB.field_72339_c, axisAlignedBB.field_72336_d, axisAlignedBB.field_72337_e, axisAlignedBB.field_72334_f)).isEmpty();
  }
}
