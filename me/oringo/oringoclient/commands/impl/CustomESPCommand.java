package me.oringo.oringoclient.commands.impl;

import java.awt.Color;
import me.oringo.oringoclient.commands.Command;
import me.oringo.oringoclient.qolfeatures.module.impl.movement.Sneak;
import me.oringo.oringoclient.qolfeatures.module.impl.render.CustomESP;
import me.oringo.oringoclient.qolfeatures.module.impl.render.Gui;
import me.oringo.oringoclient.qolfeatures.module.impl.skyblock.AutoS1;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Vec3;

public class CustomESPCommand extends Command {
  public String () {
    return "Adds or removes names to Custom ESP module";
  }
  
  public void (String[] paramArrayOfString) throws Exception {
    if (paramArrayOfString.length == 0) {
      for (String str : CustomESP..keySet())
        Sneak.(str); 
      Sneak.(String.format("%s%s add/remove", new Object[] { Gui..(), ()[0] }));
    } else {
      switch (paramArrayOfString[0]) {
        case "add":
          if (paramArrayOfString.length == 3) {
            if (!CustomESP..containsKey(paramArrayOfString[1])) {
              CustomESP..put(paramArrayOfString[1].toLowerCase(), Color.decode(paramArrayOfString[2]));
            } else {
              Sneak.("Name already added");
            } 
          } else {
            Sneak.(String.format("Usage: %s%s add name color", new Object[] { Gui..(), ()[0] }));
          } 
          return;
        case "remove":
          if (paramArrayOfString.length == 2)
            CustomESP..remove(paramArrayOfString[1]); 
          return;
      } 
      for (String str : CustomESP..keySet())
        Sneak.(str); 
    } 
  }
  
  public static void (BlockPos paramBlockPos) {
    AutoS1.mc.field_71442_b.func_178890_a(AutoS1.mc.field_71439_g, AutoS1.mc.field_71441_e, AutoS1.mc.field_71439_g.field_71071_by.func_70448_g(), paramBlockPos, EnumFacing.func_176733_a(AutoS1.mc.field_71439_g.field_70177_z), new Vec3(0.0D, 0.0D, 0.0D));
  }
  
  public CustomESPCommand() {
    super("esp", new String[] { "customesp" });
  }
  
  static {
  
  }
}
