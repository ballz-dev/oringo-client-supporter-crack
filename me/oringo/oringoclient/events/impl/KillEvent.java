package me.oringo.oringoclient.events.impl;

import me.oringo.oringoclient.events.OringoEvent;
import net.minecraft.client.entity.EntityOtherPlayerMP;

public class KillEvent extends OringoEvent {
  public EntityOtherPlayerMP ;
  
  public KillEvent(EntityOtherPlayerMP paramEntityOtherPlayerMP) {
    this. = paramEntityOtherPlayerMP;
  }
}
