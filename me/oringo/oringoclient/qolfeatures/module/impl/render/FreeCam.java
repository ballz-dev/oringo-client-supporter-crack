package me.oringo.oringoclient.qolfeatures.module.impl.render;

import me.oringo.oringoclient.OringoClient;
import me.oringo.oringoclient.events.impl.BlockBoundsEvent;
import me.oringo.oringoclient.events.impl.MoveEvent;
import me.oringo.oringoclient.events.impl.PacketSentEvent;
import me.oringo.oringoclient.lunarspoof.LunarClient;
import me.oringo.oringoclient.qolfeatures.module.Module;
import me.oringo.oringoclient.qolfeatures.module.impl.other.SimulatorAura;
import me.oringo.oringoclient.qolfeatures.module.settings.Setting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.BooleanSetting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.NumberSetting;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class FreeCam extends Module {
  public BooleanSetting  = new BooleanSetting("Show tracer", false);
  
  public EntityOtherPlayerMP ;
  
  public NumberSetting  = new NumberSetting("Speed", 3.0D, 0.1D, 5.0D, 0.1D);
  
  public FreeCam() {
    super("FreeCam", Module.Category.);
    (new Setting[] { (Setting)this., (Setting)this. });
  }
  
  @SubscribeEvent
  public void (RenderWorldLastEvent paramRenderWorldLastEvent) {
    if (() && this. != null && this..())
      SimulatorAura.((Entity)this., paramRenderWorldLastEvent.partialTicks, 1.0F, OringoClient..()); 
  }
  
  public void () {
    if (mc.field_71441_e != null) {
      this. = new EntityOtherPlayerMP((World)mc.field_71441_e, mc.field_71439_g.func_146103_bH());
      this..func_82149_j((Entity)mc.field_71439_g);
      this..field_70122_E = mc.field_71439_g.field_70122_E;
      mc.field_71441_e.func_73027_a(-2137, (Entity)this.);
    } 
  }
  
  @SubscribeEvent
  public void (PacketSentEvent paramPacketSentEvent) {
    if (() && paramPacketSentEvent. instanceof net.minecraft.network.play.client.C03PacketPlayer)
      paramPacketSentEvent.setCanceled(true); 
  }
  
  @SubscribeEvent
  public void (MoveEvent paramMoveEvent) {
    if (()) {
      paramMoveEvent.setY(0.0D);
      LunarClient.(paramMoveEvent, this..());
      if (mc.field_71474_y.field_74314_A.func_151470_d())
        paramMoveEvent.setY(paramMoveEvent.getY() + this..()); 
      if (mc.field_71474_y.field_74311_E.func_151470_d())
        paramMoveEvent.setY(paramMoveEvent.getY() - this..()); 
    } 
  }
  
  @SubscribeEvent
  public void (BlockBoundsEvent paramBlockBoundsEvent) {
    if (() && paramBlockBoundsEvent. == mc.field_71439_g)
      paramBlockBoundsEvent.setCanceled(true); 
  }
  
  @SubscribeEvent
  public void (WorldEvent.Load paramLoad) {
    (false);
  }
  
  public void () {
    if (mc.field_71439_g == null || mc.field_71441_e == null || this. == null)
      return; 
    mc.field_71439_g.field_70145_X = false;
    mc.field_71439_g.func_70107_b(this..field_70165_t, this..field_70163_u, this..field_70161_v);
    mc.field_71441_e.func_73028_b(-2137);
    this. = null;
    mc.field_71439_g.func_70016_h(0.0D, 0.0D, 0.0D);
  }
}
