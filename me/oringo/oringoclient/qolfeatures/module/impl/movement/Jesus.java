package me.oringo.oringoclient.qolfeatures.module.impl.movement;

import me.oringo.oringoclient.events.impl.BlockBoundsEvent;
import me.oringo.oringoclient.events.impl.MotionUpdateEvent;
import me.oringo.oringoclient.events.impl.MoveStateUpdateEvent;
import me.oringo.oringoclient.qolfeatures.module.Module;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class Jesus extends Module {
  public Jesus() {
    super("Jesus", Module.Category.);
  }
  
  @SubscribeEvent
  public void (MotionUpdateEvent.Pre paramPre) {
    if (() && !mc.field_71439_g.func_70093_af() && mc.field_71441_e.func_180495_p((new BlockPos((Entity)mc.field_71439_g)).func_177977_b()).func_177230_c() instanceof net.minecraft.block.BlockLiquid && mc.field_71439_g.field_70173_aa % 2 == 0)
      paramPre. -= 0.015625D; 
  }
  
  @SubscribeEvent
  public void (BlockBoundsEvent paramBlockBoundsEvent) {
    if (() && mc.field_71439_g != null && paramBlockBoundsEvent. instanceof net.minecraft.block.BlockLiquid && !mc.field_71439_g.func_70093_af() && paramBlockBoundsEvent..func_177956_o() < mc.field_71439_g.field_70163_u)
      paramBlockBoundsEvent. = new AxisAlignedBB(paramBlockBoundsEvent..func_177958_n(), paramBlockBoundsEvent..func_177956_o(), paramBlockBoundsEvent..func_177952_p(), (paramBlockBoundsEvent..func_177958_n() + 1), (paramBlockBoundsEvent..func_177956_o() + 1), (paramBlockBoundsEvent..func_177952_p() + 1)); 
  }
  
  static {
  
  }
  
  @SubscribeEvent
  public void (MoveStateUpdateEvent paramMoveStateUpdateEvent) {
    if (() && mc.field_71441_e.func_180495_p((new BlockPos((Entity)mc.field_71439_g)).func_177977_b()).func_177230_c() instanceof net.minecraft.block.BlockLiquid && !mc.field_71439_g.func_70093_af())
      paramMoveStateUpdateEvent.setJump(false); 
  }
}
