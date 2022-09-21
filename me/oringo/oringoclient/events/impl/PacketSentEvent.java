package me.oringo.oringoclient.events.impl;

import me.oringo.oringoclient.events.OringoEvent;
import me.oringo.oringoclient.utils.OringoPacketLog;
import net.minecraft.network.Packet;
import net.minecraftforge.fml.common.eventhandler.Cancelable;

@Cancelable
public class PacketSentEvent extends OringoEvent {
  public Packet<?> ;
  
  public Packet<?> getPacket() {
    return this.;
  }
  
  public PacketSentEvent(Packet<?> paramPacket) {
    this. = paramPacket;
  }
  
  public static void (String paramString) {
    OringoPacketLog..setText(String.valueOf((new StringBuilder()).append(OringoPacketLog..getText()).append(paramString).append("\n")));
    if (OringoPacketLog..getText().length() - OringoPacketLog..getText().replaceAll("\n", "").length() > 20)
      OringoPacketLog..setText(OringoPacketLog..getText().substring(OringoPacketLog..getText().indexOf("\n") + 1)); 
  }
  
  public static class Post extends OringoEvent {
    public Packet<?> ;
    
    public Post(Packet<?> param1Packet) {
      this. = param1Packet;
    }
  }
}
