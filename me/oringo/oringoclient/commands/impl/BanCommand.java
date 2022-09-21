package me.oringo.oringoclient.commands.impl;

import java.util.Random;
import me.oringo.oringoclient.commands.Command;
import me.oringo.oringoclient.qolfeatures.module.impl.macro.AutoFish;
import me.oringo.oringoclient.qolfeatures.module.impl.movement.Sneak;
import me.oringo.oringoclient.qolfeatures.module.impl.other.AntiNicker;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.util.BlockPos;

public class BanCommand extends Command {
  public String () {
    return null;
  }
  
  public BanCommand() {
    super("selfban", new String[0]);
  }
  
  public void (String[] paramArrayOfString) throws Exception {
    if (paramArrayOfString.length == 1 && paramArrayOfString[0].equals("confirm")) {
      Sneak.("You will get banned in ~3 seconds!");
      for (byte b = 0; b < 10; b++)
        mc.func_147114_u().func_147298_b().func_179290_a((Packet)new C08PacketPlayerBlockPlacement(new BlockPos((new Random()).nextInt(), (new Random()).nextInt(), (new Random()).nextInt()), 1, mc.field_71439_g.field_71071_by.func_70448_g(), 0.0F, 0.0F, 0.0F)); 
    } 
  }
  
  static {
  
  }
  
  public static void () {
    AntiNicker.((Packet)new C08PacketPlayerBlockPlacement(null));
    AutoFish.mc.field_71439_g.func_71038_i();
  }
}
