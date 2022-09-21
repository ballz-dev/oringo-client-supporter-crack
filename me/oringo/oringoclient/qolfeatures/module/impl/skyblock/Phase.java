package me.oringo.oringoclient.qolfeatures.module.impl.skyblock;

import me.oringo.oringoclient.OringoClient;
import me.oringo.oringoclient.events.impl.BlockBoundsEvent;
import me.oringo.oringoclient.events.impl.MotionUpdateEvent;
import me.oringo.oringoclient.mixins.MinecraftAccessor;
import me.oringo.oringoclient.qolfeatures.module.Module;
import me.oringo.oringoclient.qolfeatures.module.impl.movement.Scaffold;
import me.oringo.oringoclient.qolfeatures.module.impl.render.Camera;
import me.oringo.oringoclient.qolfeatures.module.settings.Setting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.BooleanSetting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.ModeSetting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.NumberSetting;
import me.oringo.oringoclient.ui.hud.DraggableComponent;
import me.oringo.oringoclient.utils.font.Fonts;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.Entity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class Phase extends Module {
  public BooleanSetting  = new BooleanSetting("Float", true);
  
  public ModeSetting  = new ModeSetting("Activate", "on Key", new String[] { "Auto", "on Key", "Always" });
  
  public boolean ;
  
  public boolean ;
  
  public boolean ;
  
  public NumberSetting  = new NumberSetting("Timer", 1.0D, 0.1D, 1.0D, 0.1D);
  
  public BooleanSetting  = new BooleanSetting("Barrier clip", true);
  
  public int ;
  
  public BooleanSetting  = new BooleanSetting("Autoclip", true);
  
  public double ;
  
  public boolean () {
    return this..("on Key");
  }
  
  @SubscribeEvent
  public void (RenderGameOverlayEvent.Post paramPost) {
    if (mc.field_71441_e == null || mc.field_71439_g == null || !())
      return; 
    if (this. && this..("on Key") && paramPost.type == RenderGameOverlayEvent.ElementType.HOTBAR) {
      ScaledResolution scaledResolution = new ScaledResolution(mc);
      Fonts..("Phase usage detected", (scaledResolution.func_78326_a() / 2.0F), (scaledResolution.func_78328_b() - scaledResolution.func_78328_b() / 4.5F), OringoClient..().getRGB());
    } 
  }
  
  @SubscribeEvent
  public void (BlockBoundsEvent paramBlockBoundsEvent) {
    if (mc.field_71439_g == null || !())
      return; 
    if (paramBlockBoundsEvent. instanceof net.minecraft.block.BlockBarrier && this..() && ((paramBlockBoundsEvent. != null && paramBlockBoundsEvent..field_72337_e > (mc.field_71439_g.func_174813_aQ()).field_72338_b) || mc.field_71474_y.field_74311_E.func_151470_d()))
      paramBlockBoundsEvent.setCanceled(true); 
    if ((this. || this..("Always")) && paramBlockBoundsEvent. == mc.field_71439_g && ((paramBlockBoundsEvent. != null && paramBlockBoundsEvent..field_72337_e > (mc.field_71439_g.func_174813_aQ()).field_72338_b) || mc.field_71474_y.field_74311_E.func_151470_d() || (this. == 7 && this..())))
      paramBlockBoundsEvent.setCanceled(true); 
  }
  
  public void () {
    this. = false;
  }
  
  @SubscribeEvent
  public void (MotionUpdateEvent.Pre paramPre) {
    if (mc.field_71439_g == null || mc.field_71441_e == null)
      return; 
    this.--;
    if (()) {
      if (this.)
        (((MinecraftAccessor)mc).getTimer()).field_74278_d = (float)this..(); 
      if (mc.field_71439_g.field_70122_E)
        this. = mc.field_71439_g.field_70163_u; 
      if (this. == mc.field_71439_g.field_70163_u && this..() && this.) {
        mc.field_71439_g.field_70181_x = 0.0D;
        mc.field_71439_g.field_70122_E = true;
      } 
      this. = (mc.field_71439_g.field_70122_E && mc.field_71439_g.field_70124_G && Camera.(mc.field_71441_e.func_180495_p(new BlockPos(mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u, mc.field_71439_g.field_70161_v)).func_177230_c()));
      if (!this. && (!super.() || (() && !this.)) && mc.field_71439_g.field_70122_E && mc.field_71439_g.field_70124_G && Camera.(mc.field_71441_e.func_180495_p(new BlockPos(mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u, mc.field_71439_g.field_70161_v)).func_177230_c())) {
        this. = true;
        this. = 8;
      } else if (this. && ((!Scaffold.() && this. < 0) || (() && !this. && super.()))) {
        mc.field_71439_g.func_70016_h(0.0D, 0.0D, 0.0D);
        this. = false;
        (((MinecraftAccessor)mc).getTimer()).field_74278_d = 1.0F;
      } 
    } 
    this. = ();
  }
  
  public Phase() {
    super("Stair Phase", Module.Category.);
    (new Setting[] { (Setting)this., (Setting)this., (Setting)this., (Setting)this., (Setting)this. });
  }
  
  public static Vec3 (Entity paramEntity) {
    return DraggableComponent.(paramEntity, 0.0F);
  }
}
