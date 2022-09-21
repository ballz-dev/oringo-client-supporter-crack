package me.oringo.oringoclient.commands.impl;

import me.oringo.oringoclient.commands.Command;
import me.oringo.oringoclient.qolfeatures.module.impl.movement.Sneak;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Team;
import org.lwjgl.opengl.GL11;

public class PlayerListCommand extends Command {
  public static void () {
    GL11.glDisable(2929);
    GL11.glEnable(3042);
    GL11.glDisable(3553);
    GL11.glBlendFunc(770, 771);
    GL11.glDepthMask(true);
    GL11.glEnable(2848);
    GL11.glHint(3154, 4354);
    GL11.glHint(3155, 4354);
  }
  
  static {
  
  }
  
  public PlayerListCommand() {
    super("pl", new String[0]);
  }
  
  public String () {
    return "Test command, displays all players from objectives";
  }
  
  public void (String[] paramArrayOfString) throws Exception {
    if (mc.field_71441_e.func_96441_U() != null)
      for (ScorePlayerTeam scorePlayerTeam : mc.field_71441_e.func_96441_U().func_96525_g()) {
        if (scorePlayerTeam.func_96668_e().startsWith("§") && !scorePlayerTeam.func_96669_c().startsWith("team") && !scorePlayerTeam.func_96668_e().contains("[NPC]"))
          for (String str : scorePlayerTeam.func_96670_d()) {
            Sneak.(ScorePlayerTeam.func_96667_a((Team)scorePlayerTeam, str).replace("§k", "[OBF] "));
            Sneak.(String.format("%s %s %s %s", new Object[] { Integer.valueOf(scorePlayerTeam.func_98299_i()), scorePlayerTeam.func_96661_b().replace("§", "&"), scorePlayerTeam.func_96669_c().replace("§", "&"), (scorePlayerTeam.func_178771_j()).field_178830_e }));
          }  
      }  
  }
}
