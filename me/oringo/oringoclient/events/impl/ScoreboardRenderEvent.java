package me.oringo.oringoclient.events.impl;

import me.oringo.oringoclient.events.OringoEvent;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraftforge.fml.common.eventhandler.Cancelable;

@Cancelable
public class ScoreboardRenderEvent extends OringoEvent {
  public ScaledResolution ;
  
  public ScoreObjective ;
  
  public ScoreboardRenderEvent(ScoreObjective paramScoreObjective, ScaledResolution paramScaledResolution) {
    this. = paramScoreObjective;
    this. = paramScaledResolution;
  }
}
