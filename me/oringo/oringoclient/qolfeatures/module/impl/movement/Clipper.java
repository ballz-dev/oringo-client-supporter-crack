package me.oringo.oringoclient.qolfeatures.module.impl.movement;

import me.oringo.oringoclient.OringoClient;
import me.oringo.oringoclient.events.impl.MotionUpdateEvent;
import me.oringo.oringoclient.qolfeatures.module.Module;
import me.oringo.oringoclient.qolfeatures.module.impl.player.ChestStealer;
import me.oringo.oringoclient.qolfeatures.module.settings.Setting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.BooleanSetting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.NumberSetting;
import me.oringo.oringoclient.utils.font.Fonts;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;

public class Clipper extends Module {
  public BooleanSetting  = new BooleanSetting("Round position", true);
  
  public int ;
  
  public NumberSetting  = new NumberSetting("Distance", 10.0D, 3.0D, 50.0D, 1.0D);
  
  public int ;
  
  public static boolean ;
  
  public static BlockPos ;
  
  public static int (int paramInt1, int paramInt2, int paramInt3) {
    if (paramInt2 < paramInt3) {
      int i = paramInt2;
      paramInt2 = paramInt3;
      paramInt3 = i;
    } 
    return Math.max(Math.min(paramInt2, paramInt1), paramInt3);
  }
  
  public void () {
    this. = this. = 0;
     = null;
  }
  
  @SubscribeEvent
  public void (MotionUpdateEvent.Pre paramPre) {
    ();
    if (() && mc.field_71439_g.field_70122_E) {
      BlockPos blockPos = (new BlockPos(mc.field_71439_g.func_174791_d())).func_177977_b();
      for (int i = blockPos.func_177956_o(); i > 0; i--) {
        blockPos = blockPos.func_177977_b();
        this.++;
        IBlockState iBlockState = mc.field_71441_e.func_180495_p(blockPos);
        if (iBlockState.func_177230_c().func_176205_b((IBlockAccess)mc.field_71441_e, blockPos)) {
          this.++;
        } else {
          if (this. > this..())
            break; 
          if (this. >= 2) {
             = new BlockPos(mc.field_71439_g.field_70165_t, blockPos.func_177956_o() + iBlockState.func_177230_c().func_149669_A(), mc.field_71439_g.field_70161_v);
            if (() && ! && mc.field_71462_r == null)
              mc.field_71439_g.func_70107_b(this..() ? (.func_177958_n() + 0.5D) : mc.field_71439_g.field_70165_t, blockPos.func_177956_o() + iBlockState.func_177230_c().func_149669_A(), this..() ? (.func_177952_p() + 0.5D) : mc.field_71439_g.field_70161_v); 
            break;
          } 
          this. = 0;
        } 
      } 
    } 
     = ();
  }
  
  public static void (double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4) {
    ScaledResolution scaledResolution = new ScaledResolution(OringoClient.mc);
    double d = scaledResolution.func_78325_e();
    paramDouble2 = scaledResolution.func_78328_b() - paramDouble2;
    paramDouble1 *= d;
    paramDouble2 *= d;
    paramDouble3 *= d;
    paramDouble4 *= d;
    GL11.glScissor((int)paramDouble1, (int)(paramDouble2 - paramDouble4), (int)paramDouble3, (int)paramDouble4);
  }
  
  public Clipper() {
    super("Clipper", 29, Module.Category.);
    (new Setting[] { (Setting)this., (Setting)this. });
  }
  
  @SubscribeEvent
  public void (RenderWorldLastEvent paramRenderWorldLastEvent) {
    if ( != null)
      ChestStealer.(, OringoClient..()); 
  }
  
  @SubscribeEvent
  public void (RenderGameOverlayEvent.Pre paramPre) {
    if ( != null && paramPre.type == RenderGameOverlayEvent.ElementType.CROSSHAIRS)
      Fonts..("Clipper usage found!", paramPre.resolution.func_78326_a() / 2.0D, paramPre.resolution.func_78328_b() / 4.0D * 3.0D, OringoClient..().getRGB()); 
  }
  
  public boolean () {
    return true;
  }
}
