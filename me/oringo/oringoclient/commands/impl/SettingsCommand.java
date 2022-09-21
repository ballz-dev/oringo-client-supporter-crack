package me.oringo.oringoclient.commands.impl;

import java.util.ArrayList;
import me.oringo.oringoclient.OringoClient;
import me.oringo.oringoclient.commands.Command;
import net.minecraft.client.Minecraft;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Scoreboard;

public class SettingsCommand extends Command {
  public void (String[] paramArrayOfString) throws Exception {
    if (paramArrayOfString.length != 0 && paramArrayOfString[0].equalsIgnoreCase("scoreboard") && mc.field_71439_g.func_96123_co() != null) {
      StringBuilder stringBuilder = new StringBuilder();
      Scoreboard scoreboard = (Minecraft.func_71410_x()).field_71439_g.func_96123_co();
      ArrayList arrayList = new ArrayList(scoreboard.func_96534_i(scoreboard.func_96539_a(1)));
      for (Score score : arrayList) {
        ScorePlayerTeam scorePlayerTeam = scoreboard.func_96509_i(score.func_96653_e());
        String str = String.valueOf((new StringBuilder()).append(scorePlayerTeam.func_96668_e()).append(score.func_96653_e()).append(scorePlayerTeam.func_96663_f()));
        for (char c : str.toCharArray()) {
          if (c < 'Ā')
            stringBuilder.append(c); 
        } 
        stringBuilder.append("\n");
      } 
      stringBuilder.append(mc.field_71439_g.func_96123_co().func_96539_a(1).func_96678_d());
      System.out.println(stringBuilder);
      return;
    } 
    OringoClient..();
  }
  
  public SettingsCommand() {
    super("oringo", new String[0]);
  }
  
  public String () {
    return "Opens the menu";
  }
  
  static {
  
  }
}
