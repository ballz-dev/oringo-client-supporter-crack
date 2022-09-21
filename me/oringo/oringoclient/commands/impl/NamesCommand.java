package me.oringo.oringoclient.commands.impl;

import java.util.Arrays;
import me.oringo.oringoclient.commands.Command;
import me.oringo.oringoclient.events.impl.PacketReceivedEvent;
import me.oringo.oringoclient.qolfeatures.module.impl.other.NamesOnly;
import me.oringo.oringoclient.qolfeatures.module.impl.skyblock.BoneThrower;
import me.oringo.oringoclient.utils.MathUtil;

public class NamesCommand extends Command {
  public static int (String paramString) {
    return paramString.contains("Plasmaflux") ? 0 : (paramString.contains("Overflux") ? 1 : (paramString.contains("Mana Flux") ? 2 : (paramString.contains("Radiant") ? 3 : 4)));
  }
  
  public String () {
    return "Adds names to name filter in Kill Aura";
  }
  
  static {
  
  }
  
  public void (String[] paramArrayOfString) throws Exception {
    if (paramArrayOfString.length < 2) {
      for (String str : NamesOnly.)
        PacketReceivedEvent.(str); 
      PacketReceivedEvent.(String.valueOf((new StringBuilder()).append(BoneThrower.()).append("names add/remove name")));
      return;
    } 
    paramArrayOfString[1] = String.join(" ", Arrays.<CharSequence>copyOfRange((CharSequence[])paramArrayOfString, 1, paramArrayOfString.length));
    switch (paramArrayOfString[0]) {
      case "add":
        if (!NamesOnly..contains(paramArrayOfString[1]))
          NamesOnly..add(paramArrayOfString[1]); 
        PacketReceivedEvent.(String.valueOf((new StringBuilder()).append("Added ").append(paramArrayOfString[1]).append(" to name list!")));
        break;
      case "remove":
        if (NamesOnly..contains(paramArrayOfString[1])) {
          NamesOnly..remove(paramArrayOfString[1]);
          PacketReceivedEvent.(String.valueOf((new StringBuilder()).append("Removed ").append(paramArrayOfString[1]).append("from name list!")));
        } 
        break;
    } 
    MathUtil.();
  }
  
  public static double (double paramDouble1, double paramDouble2, float paramFloat) {
    return paramDouble1 + (paramDouble2 - paramDouble1) * paramFloat;
  }
  
  public NamesCommand() {
    super("names", new String[] { "friends" });
  }
}
