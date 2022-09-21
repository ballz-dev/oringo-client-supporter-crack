package me.oringo.oringoclient.qolfeatures.module.impl.other;

import me.oringo.oringoclient.commands.Command;
import me.oringo.oringoclient.commands.CommandHandler;
import me.oringo.oringoclient.events.impl.MotionUpdateEvent;
import me.oringo.oringoclient.qolfeatures.module.Module;
import net.minecraft.util.MathHelper;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class Derp extends Module {
  public float ;
  
  @SubscribeEvent(priority = EventPriority.LOWEST)
  public void (MotionUpdateEvent.Pre paramPre) {
    if (!())
      return; 
    paramPre. = MathHelper.func_76142_g(this. += 45.0F);
    paramPre. = 90.0F;
  }
  
  public Derp() {
    super("Derp", Module.Category.OTHER);
  }
  
  public static void (Command paramCommand) {
    for (String str : paramCommand.())
      CommandHandler..remove(str.toLowerCase()); 
  }
}
