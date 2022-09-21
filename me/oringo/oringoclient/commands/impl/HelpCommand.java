package me.oringo.oringoclient.commands.impl;

import java.util.HashMap;
import me.oringo.oringoclient.commands.Command;
import me.oringo.oringoclient.events.OringoEvent;
import me.oringo.oringoclient.qolfeatures.module.impl.skyblock.BoneThrower;

public class HelpCommand extends Command {
  public static HashMap<String, Command>  = new HashMap<>();
  
  public HelpCommand() {
    super("help", new String[] { "commands", "info" });
  }
  
  public void (String[] paramArrayOfString) throws Exception {
    if (paramArrayOfString.length == 0) {
      OringoEvent.(String.format("§d%shelp command §7for more info", new Object[] { Character.valueOf(BoneThrower.()) }));
      .forEach(HelpCommand::);
    } else if (.containsKey(paramArrayOfString[0])) {
      String str = paramArrayOfString[0];
      Command command = .get(paramArrayOfString[0]);
      OringoEvent.(String.format("§b%s%s §3» §7%s", new Object[] { Character.valueOf(BoneThrower.()), str, command.() }));
    } else {
      OringoEvent.(String.format("§bOringoClient §3» §cInvalid command \"%shelp\" for §cmore info", new Object[] { Character.valueOf(BoneThrower.()) }));
    } 
  }
  
  public String () {
    return "Shows all commands";
  }
}
