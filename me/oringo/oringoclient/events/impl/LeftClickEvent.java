package me.oringo.oringoclient.events.impl;

import com.mojang.realmsclient.gui.ChatFormatting;
import java.util.ArrayList;
import me.oringo.oringoclient.events.OringoEvent;
import me.oringo.oringoclient.utils.SkyblockUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Scoreboard;

public class LeftClickEvent extends OringoEvent {
  static {
  
  }
  
  public static boolean (String paramString) {
    if (SkyblockUtils.mc.field_71439_g != null && SkyblockUtils.mc.field_71439_g.func_96123_co() != null && SkyblockUtils.mc.field_71439_g.func_96123_co().func_96539_a(1) != null) {
      Scoreboard scoreboard = (Minecraft.func_71410_x()).field_71439_g.func_96123_co();
      ArrayList arrayList = new ArrayList(scoreboard.func_96534_i(scoreboard.func_96539_a(1)));
      for (Score score : arrayList) {
        ScorePlayerTeam scorePlayerTeam = scoreboard.func_96509_i(score.func_96653_e());
        if (scorePlayerTeam != null) {
          String str = ChatFormatting.stripFormatting(String.valueOf((new StringBuilder()).append(scorePlayerTeam.func_96668_e()).append(score.func_96653_e()).append(scorePlayerTeam.func_96663_f())));
          StringBuilder stringBuilder = new StringBuilder();
          for (char c : str.toCharArray()) {
            if (c < 'Ā')
              stringBuilder.append(c); 
          } 
          if (String.valueOf(stringBuilder).toLowerCase().contains(paramString.toLowerCase()))
            return true; 
        } 
      } 
    } 
    return false;
  }
}
