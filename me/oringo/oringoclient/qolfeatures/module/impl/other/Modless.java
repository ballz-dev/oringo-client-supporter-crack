package me.oringo.oringoclient.qolfeatures.module.impl.other;

import me.oringo.oringoclient.commands.Command;
import me.oringo.oringoclient.commands.CommandHandler;
import me.oringo.oringoclient.commands.impl.HelpCommand;
import me.oringo.oringoclient.qolfeatures.module.Module;

public class Modless extends Module {
  static {
  
  }
  
  public static void (Command paramCommand) {
    for (String str : paramCommand.()) {
      if (!CommandHandler..containsKey(str.toLowerCase()))
        CommandHandler..put(str.toLowerCase(), paramCommand); 
    } 
    HelpCommand..put(paramCommand.()[0].toLowerCase(), paramCommand);
  }
  
  public Modless() {
    super("Hide modlist", 0, Module.Category.OTHER);
  }
}
