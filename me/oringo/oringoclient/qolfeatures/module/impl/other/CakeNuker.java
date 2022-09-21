package me.oringo.oringoclient.qolfeatures.module.impl.other;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import me.oringo.oringoclient.events.impl.MotionUpdateEvent;
import me.oringo.oringoclient.events.impl.WorldJoinEvent;
import me.oringo.oringoclient.qolfeatures.module.Module;
import me.oringo.oringoclient.qolfeatures.module.settings.Setting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.NumberSetting;
import net.minecraft.block.BlockCake;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.DestroyBlockProgress;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Vec3;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class CakeNuker extends Module {
  public static NumberSetting  = new NumberSetting("Distance", 6.0D, 1.0D, 6.0D, 0.1D);
  
  public static HashMap<BlockPos, Integer>  = new HashMap<>();
  
  public static Map<Integer, DestroyBlockProgress> () {
    try {
      Field field = RenderGlobal.class.getDeclaredField("field_72738_E");
      field.setAccessible(true);
      return (Map<Integer, DestroyBlockProgress>)field.get((Minecraft.func_71410_x()).field_71438_f);
    } catch (Exception exception) {
      return new HashMap<>();
    } 
  }
  
  @SubscribeEvent
  public void (MotionUpdateEvent.Post paramPost) {
    if (()) {
      BlockPos blockPos = new BlockPos(mc.field_71439_g.func_174791_d());
      byte b = 0;
      for (BlockPos blockPos1 : BlockPos.func_177980_a(blockPos.func_177982_a(-7, -7, -7), blockPos.func_177982_a(7, 7, 7))) {
        IBlockState iBlockState = mc.field_71441_e.func_180495_p(blockPos1);
        if (iBlockState.func_177230_c().equals(Blocks.field_150414_aQ) && mc.field_71439_g.func_70092_e(blockPos1.func_177958_n(), (blockPos1.func_177956_o() - mc.field_71439_g.func_70047_e()), blockPos1.func_177952_p()) < .() * .()) {
          Integer integer = .get(blockPos1);
          int i = ((integer == null) ? (Integer)iBlockState.func_177229_b((IProperty)BlockCake.field_176589_a) : integer).intValue() + 1;
          if (i > 7)
            continue; 
          if (integer == null) {
            .put(blockPos1, Integer.valueOf(i));
          } else {
            .replace(blockPos1, Integer.valueOf(i));
          } 
          mc.field_71442_b.func_178890_a(mc.field_71439_g, mc.field_71441_e, mc.field_71439_g.func_70694_bm(), blockPos1, EnumFacing.func_176733_a(mc.field_71439_g.field_70177_z), new Vec3(0.0D, 0.0D, 0.0D));
          if (++b > 20)
            break; 
        } 
      } 
    } 
  }
  
  @SubscribeEvent
  public void (WorldJoinEvent paramWorldJoinEvent) {
    .clear();
  }
  
  public CakeNuker() {
    super("Cake Nuker", Module.Category.OTHER);
    (new Setting[] { (Setting) });
  }
}
