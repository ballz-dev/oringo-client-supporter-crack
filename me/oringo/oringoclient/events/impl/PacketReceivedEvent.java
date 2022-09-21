package me.oringo.oringoclient.events.impl;

import io.netty.channel.ChannelHandlerContext;
import me.oringo.oringoclient.events.OringoEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.network.Packet;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import net.minecraftforge.fml.common.eventhandler.Cancelable;

@Cancelable
public class PacketReceivedEvent extends OringoEvent {
  public ChannelHandlerContext ;
  
  public Packet<?> ;
  
  public static void (String paramString) {
    (Minecraft.func_71410_x()).field_71439_g.func_145747_a((IChatComponent)new ChatComponentText(String.valueOf((new StringBuilder()).append("§bOringoClient §3» §7").append(paramString))));
  }
  
  public PacketReceivedEvent(Packet<?> paramPacket, ChannelHandlerContext paramChannelHandlerContext) {
    this. = paramPacket;
    this. = paramChannelHandlerContext;
  }
  
  public static class Post extends OringoEvent {
    public ChannelHandlerContext ;
    
    public Packet<?> ;
    
    public Post(Packet<?> param1Packet, ChannelHandlerContext param1ChannelHandlerContext) {
      this. = param1Packet;
      this. = param1ChannelHandlerContext;
    }
  }
}
