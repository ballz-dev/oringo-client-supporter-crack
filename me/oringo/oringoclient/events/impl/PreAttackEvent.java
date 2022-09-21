package me.oringo.oringoclient.events.impl;

import com.mojang.realmsclient.gui.ChatFormatting;
import java.util.ArrayList;
import me.oringo.oringoclient.events.OringoEvent;
import me.oringo.oringoclient.utils.SkyblockUtils;
import net.minecraft.entity.Entity;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.Team;

public class PreAttackEvent extends OringoEvent {
  public Entity ;
  
  public static boolean () {
    try {
      Scoreboard scoreboard = SkyblockUtils.mc.field_71439_g.func_96123_co();
      ArrayList arrayList = new ArrayList(scoreboard.func_96534_i(scoreboard.func_96539_a(1)));
      for (Score score : arrayList) {
        ScorePlayerTeam scorePlayerTeam = scoreboard.func_96509_i(score.func_96653_e());
        String str = ChatFormatting.stripFormatting(ScorePlayerTeam.func_96667_a((Team)scorePlayerTeam, score.func_96653_e()));
        if (str.contains("Map"))
          return true; 
      } 
    } catch (Exception exception) {}
    return false;
  }
  
  public PreAttackEvent(Entity paramEntity) {
    this. = paramEntity;
  }
  
  public static float (float paramFloat1, float paramFloat2) {
    return ((paramFloat1 - paramFloat2) % 360.0F + 540.0F) % 360.0F - 180.0F;
  }
}
