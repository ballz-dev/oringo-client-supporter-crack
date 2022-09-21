package me.oringo.oringoclient.qolfeatures.module.impl.skyblock;

import me.oringo.oringoclient.events.impl.MoveEvent;
import me.oringo.oringoclient.qolfeatures.module.Module;
import me.oringo.oringoclient.qolfeatures.module.settings.Setting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.BooleanSetting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.NumberSetting;
import me.oringo.oringoclient.utils.SkyblockUtils;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class IceFillHelp extends Module {
  public NumberSetting  = new NumberSetting("Ice slowdown", 0.15D, 0.05D, 1.0D, 0.05D);
  
  public BooleanSetting  = new BooleanSetting("Auto stop", true);
  
  public BooleanSetting  = new BooleanSetting("No ice slip", true);
  
  @SubscribeEvent
  public void (MoveEvent paramMoveEvent) {
    if (!() || !mc.field_71439_g.field_70122_E || !SkyblockUtils.inDungeon)
      return; 
    BlockPos blockPos = new BlockPos(mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u - 0.4D, mc.field_71439_g.field_70161_v);
    if (mc.field_71441_e.func_180495_p(blockPos).func_177230_c() == Blocks.field_150432_aD) {
      paramMoveEvent.setZ(paramMoveEvent.getZ() * this..());
      paramMoveEvent.setX(paramMoveEvent.getX() * this..());
      BlockPos blockPos1 = new BlockPos(mc.field_71439_g.field_70165_t + paramMoveEvent.getX(), mc.field_71439_g.field_70163_u - 0.4D, mc.field_71439_g.field_70161_v + paramMoveEvent.getZ());
      if (this..() && !blockPos.equals(blockPos1) && mc.field_71441_e.func_180495_p(blockPos1).func_177230_c() == Blocks.field_150432_aD) {
        paramMoveEvent.setZ(0.0D);
        paramMoveEvent.setX(0.0D);
      } 
    } 
    if (this..()) {
      Blocks.field_150403_cj.field_149765_K = 0.6F;
      Blocks.field_150432_aD.field_149765_K = 0.6F;
    } else {
      Blocks.field_150403_cj.field_149765_K = 0.98F;
      Blocks.field_150432_aD.field_149765_K = 0.98F;
    } 
  }
  
  public IceFillHelp() {
    super("Ice Fill Helper", Module.Category.);
    (new Setting[] { (Setting)this., (Setting)this., (Setting)this. });
  }
  
  public void () {
    Blocks.field_150403_cj.field_149765_K = 0.98F;
    Blocks.field_150432_aD.field_149765_K = 0.98F;
  }
}
