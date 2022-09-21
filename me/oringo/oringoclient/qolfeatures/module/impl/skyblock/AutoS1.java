package me.oringo.oringoclient.qolfeatures.module.impl.skyblock;

import java.util.ArrayList;
import java.util.List;
import me.oringo.oringoclient.commands.impl.CustomESPCommand;
import me.oringo.oringoclient.events.impl.BlockChangeEvent;
import me.oringo.oringoclient.events.impl.MotionUpdateEvent;
import me.oringo.oringoclient.qolfeatures.module.Module;
import me.oringo.oringoclient.qolfeatures.module.impl.combat.WTap;
import me.oringo.oringoclient.qolfeatures.module.settings.Setting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.NumberSetting;
import me.oringo.oringoclient.utils.MilliTimer;
import me.oringo.oringoclient.utils.SkyblockUtils;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class AutoS1 extends Module {
  public static NumberSetting  = new NumberSetting("Delay", 250.0D, 0.0D, 500.0D, 50.0D);
  
  public static MilliTimer ;
  
  public static BlockPos ;
  
  public static boolean ;
  
  public static List<BlockPos>  = new ArrayList<>();
  
  public AutoS1() {
    super("Auto Simon", Module.Category.);
    (new Setting[] { (Setting) });
  }
  
  @SubscribeEvent
  public void (WorldEvent.Load paramLoad) {
     = false;
    .clear();
  }
  
  public static EnumDyeColor (ItemStack paramItemStack) {
    return EnumDyeColor.func_176764_b(paramItemStack.func_77952_i());
  }
  
  static {
     = new MilliTimer();
     = new BlockPos(110, 121, 91);
  }
  
  @SubscribeEvent
  public void (MotionUpdateEvent.Post paramPost) {
    if (() && SkyblockUtils.inDungeon && SkyblockUtils.)
      if (!) {
        if (WTap.()) {
           = true;
          .clear();
          CustomESPCommand.();
        } 
      } else if (mc.field_71441_e.func_180495_p(.func_177982_a(0, 0, 2)).func_177230_c() == Blocks.field_150430_aB && .(.()) && !.isEmpty()) {
        if (!WTap.(.get(0)))
          return; 
        CustomESPCommand.(.remove(0));
        .();
      }  
  }
  
  @SubscribeEvent
  public void (BlockChangeEvent paramBlockChangeEvent) {
    if (SkyblockUtils. && paramBlockChangeEvent..func_177230_c() == Blocks.field_180398_cJ && paramBlockChangeEvent..func_177958_n() == 111 && paramBlockChangeEvent..func_177956_o() >= 100 && paramBlockChangeEvent..func_177956_o() <= 150 && paramBlockChangeEvent..func_177952_p() >= 70 && paramBlockChangeEvent..func_177952_p() <= 120 && !.contains(paramBlockChangeEvent.))
      .add(paramBlockChangeEvent..func_177982_a(-1, 0, 0)); 
  }
}
