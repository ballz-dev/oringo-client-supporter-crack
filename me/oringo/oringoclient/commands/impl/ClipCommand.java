package me.oringo.oringoclient.commands.impl;

import java.util.function.Predicate;
import me.oringo.oringoclient.OringoClient;
import me.oringo.oringoclient.commands.Command;
import me.oringo.oringoclient.lunarspoof.LunarClient;
import me.oringo.oringoclient.qolfeatures.module.impl.other.SimulatorAura;
import me.oringo.oringoclient.ui.hud.Component;
import me.oringo.oringoclient.ui.notifications.Notifications;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import org.lwjgl.input.Mouse;

public class ClipCommand extends Command {
  static {
  
  }
  
  public static int (Predicate<ItemStack> paramPredicate) {
    for (byte b = 0; b < 9; b++) {
      if (OringoClient.mc.field_71439_g.field_71071_by.func_70301_a(b) != null && paramPredicate.test(OringoClient.mc.field_71439_g.field_71071_by.func_70301_a(b)))
        return b; 
    } 
    return -1;
  }
  
  public ClipCommand() {
    super("clip", new String[] { "vclip" });
  }
  
  public static void (int paramInt) {
    LunarClient.(paramInt);
  }
  
  public void (String[] paramArrayOfString) throws Exception {
    if (paramArrayOfString.length == 1) {
      mc.field_71439_g.func_70107_b(mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u + Double.parseDouble(paramArrayOfString[0]), mc.field_71439_g.field_70161_v);
      mc.field_71439_g.field_70181_x = 0.0D;
    } else {
      int i = (int)mc.field_71439_g.field_70163_u;
      byte b = 0;
      while (i > 0) {
        BlockPos blockPos = new BlockPos(mc.field_71439_g.field_70165_t, i, mc.field_71439_g.field_70161_v);
        IBlockState iBlockState = mc.field_71441_e.func_180495_p(blockPos);
        if (iBlockState.func_177230_c().equals(Blocks.field_150350_a)) {
          if (++b == 2) {
            if (!WardrobeCommand.(i))
              break; 
            return;
          } 
        } else {
          b = 0;
        } 
        i--;
      } 
      if (i != 0) {
        mc.field_71439_g.func_70107_b(mc.field_71439_g.field_70165_t, i, mc.field_71439_g.field_70161_v);
        mc.field_71439_g.field_70181_x = 0.0D;
      } else {
        FireworkCommand.("No valid position!", 1500, Notifications.NotificationType.);
      } 
    } 
  }
  
  public static int () {
    return Mouse.getX() * SimulatorAura.().func_78326_a() / Component.mc.field_71443_c;
  }
  
  public String () {
    return "Clips you up x blocks";
  }
}
