package me.oringo.oringoclient.qolfeatures.module.impl.other;

import java.awt.Color;
import java.nio.FloatBuffer;
import java.util.HashMap;
import java.util.Map;
import me.oringo.oringoclient.OringoClient;
import me.oringo.oringoclient.events.impl.LeftClickEvent;
import me.oringo.oringoclient.events.impl.MotionUpdateEvent;
import me.oringo.oringoclient.qolfeatures.module.Module;
import me.oringo.oringoclient.qolfeatures.module.impl.player.InvManager;
import me.oringo.oringoclient.qolfeatures.module.impl.skyblock.DojoHelper;
import me.oringo.oringoclient.qolfeatures.module.settings.Setting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.NumberSetting;
import me.oringo.oringoclient.ui.hud.Component;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Matrix4f;

public class SimulatorAura extends Module {
  public static HashMap<BlockPos, Long> ;
  
  public static NumberSetting  = new NumberSetting("Distance", 6.0D, 1.0D, 6.0D, 0.1D);
  
  public SimulatorAura() {
    super("Simulator Aura", Module.Category.OTHER);
    (new Setting[] { (Setting) });
  }
  
  public static ScaledResolution () {
    return new ScaledResolution(Component.mc);
  }
  
  public static void (Entity paramEntity, float paramFloat1, float paramFloat2, Color paramColor) {
    double d1 = paramEntity.field_70169_q + (paramEntity.field_70165_t - paramEntity.field_70169_q) * paramFloat1 - (Minecraft.func_71410_x().func_175598_ae()).field_78730_l;
    double d2 = paramEntity.field_70167_r + (paramEntity.field_70163_u - paramEntity.field_70167_r) * paramFloat1 + (paramEntity.field_70131_O / 2.0F) - (Minecraft.func_71410_x().func_175598_ae()).field_78731_m;
    double d3 = paramEntity.field_70166_s + (paramEntity.field_70161_v - paramEntity.field_70166_s) * paramFloat1 - (Minecraft.func_71410_x().func_175598_ae()).field_78728_n;
    GL11.glBlendFunc(770, 771);
    GL11.glEnable(3042);
    GL11.glEnable(2848);
    GL11.glHint(3154, 4354);
    GL11.glDisable(3553);
    GL11.glDisable(2929);
    GL11.glLineWidth(paramFloat2);
    GL11.glDepthMask(false);
    AntiNicker.(paramColor);
    GL11.glBegin(2);
    GL11.glVertex3d(0.0D, (Minecraft.func_71410_x()).field_71439_g.func_70047_e(), 0.0D);
    GL11.glVertex3d(d1, d2, d3);
    GL11.glEnd();
    GL11.glEnable(3553);
    GL11.glDisable(2848);
    GL11.glEnable(2929);
    GL11.glDepthMask(true);
    GL11.glDisable(3042);
  }
  
  static {
     = new HashMap<>();
  }
  
  public static int (BlockPos paramBlockPos) {
    IBlockState iBlockState = OringoClient.mc.field_71441_e.func_180495_p(paramBlockPos);
    int i = OringoClient.mc.field_71439_g.field_71071_by.field_70461_c;
    float f = DojoHelper.(iBlockState, OringoClient.mc.field_71439_g.field_71071_by.func_70301_a(i));
    for (byte b = 0; b < 9; b++) {
      float f1 = DojoHelper.(iBlockState, OringoClient.mc.field_71439_g.field_71071_by.func_70301_a(b));
      if (f1 > f) {
        i = b;
        f = f1;
      } 
    } 
    return i;
  }
  
  @SubscribeEvent
  public void (MotionUpdateEvent.Post paramPost) {
    if (() && LeftClickEvent.("Simulator")) {
      .entrySet().removeIf(SimulatorAura::);
      for (TileEntity tileEntity : mc.field_71441_e.field_147482_g) {
        if (tileEntity instanceof net.minecraft.tileentity.TileEntitySkull && !.containsKey(tileEntity.func_174877_v())) {
          double d = mc.field_71439_g.func_174824_e(1.0F).func_72438_d(InvManager.(mc.field_71439_g.func_174824_e(1.0F), mc.field_71441_e.func_180495_p(tileEntity.func_174877_v()).func_177230_c().func_180646_a((World)mc.field_71441_e, tileEntity.func_174877_v())));
          if (d <= .()) {
            mc.field_71442_b.func_178890_a(mc.field_71439_g, mc.field_71441_e, mc.field_71439_g.field_71071_by.func_70448_g(), tileEntity.func_174877_v(), EnumFacing.func_176733_a(mc.field_71439_g.field_70177_z), new Vec3(0.0D, 0.0D, 0.0D));
            .put(tileEntity.func_174877_v(), Long.valueOf(System.currentTimeMillis()));
          } 
        } 
      } 
    } 
  }
  
  public static Matrix4f (int paramInt) {
    FloatBuffer floatBuffer = BufferUtils.createFloatBuffer(16);
    GL11.glGetFloat(paramInt, floatBuffer);
    return (Matrix4f)(new Matrix4f()).load(floatBuffer);
  }
}
