package me.oringo.oringoclient.mixins;

import io.netty.channel.ChannelHandlerContext;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import me.oringo.oringoclient.OringoClient;
import me.oringo.oringoclient.events.impl.PacketReceivedEvent;
import me.oringo.oringoclient.events.impl.PacketSentEvent;
import me.oringo.oringoclient.events.impl.WorldJoinEvent;
import me.oringo.oringoclient.qolfeatures.module.impl.other.Disabler;
import net.minecraft.network.EnumConnectionState;
import net.minecraft.network.EnumPacketDirection;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({NetworkManager.class})
public abstract class Packets {
  @Inject(method = {"sendPacket(Lnet/minecraft/network/Packet;)V"}, at = {@At("HEAD")}, cancellable = true)
  private void onSendPacket(Packet<?> paramPacket, CallbackInfo paramCallbackInfo) {
    if (OringoClient.mc.field_71439_g != null && EnumConnectionState.PLAY.func_179246_a(EnumPacketDirection.SERVERBOUND, paramPacket) != null && !(paramPacket instanceof net.minecraft.network.play.client.C01PacketChatMessage) && !(paramPacket instanceof net.minecraft.network.play.client.C15PacketClientSettings) && !(paramPacket instanceof net.minecraft.network.play.client.C17PacketCustomPayload) && !(paramPacket instanceof net.minecraft.network.play.client.C19PacketResourcePackStatus) && !(paramPacket instanceof net.minecraft.network.play.client.C0FPacketConfirmTransaction) && !(paramPacket instanceof net.minecraft.network.play.client.C00PacketKeepAlive) && OringoClient.mc.field_71439_g.field_70173_aa < 80) {
      if (OringoClient..()) {
        Disabler. = true;
        paramCallbackInfo.cancel();
        return;
      } 
      Disabler. = false;
    } 
    if ((new PacketSentEvent(paramPacket)).post())
      paramCallbackInfo.cancel(); 
  }
  
  @Inject(method = {"sendPacket(Lnet/minecraft/network/Packet;)V"}, at = {@At("RETURN")}, cancellable = true)
  private void onSendPacketPost(Packet<?> paramPacket, CallbackInfo paramCallbackInfo) {
    if ((new PacketSentEvent.Post(paramPacket)).post())
      paramCallbackInfo.cancel(); 
  }
  
  @Inject(method = {"sendPacket(Lnet/minecraft/network/Packet;Lio/netty/util/concurrent/GenericFutureListener;[Lio/netty/util/concurrent/GenericFutureListener;)V"}, at = {@At("HEAD")}, cancellable = true)
  private void onNoEvent(Packet<?> paramPacket, GenericFutureListener<? extends Future<? super Void>> paramGenericFutureListener, GenericFutureListener<? extends Future<? super Void>>[] paramArrayOfGenericFutureListener, CallbackInfo paramCallbackInfo) {
    if (OringoClient.mc.field_71439_g != null && EnumConnectionState.PLAY.func_179246_a(EnumPacketDirection.SERVERBOUND, paramPacket) != null && !(paramPacket instanceof net.minecraft.network.play.client.C01PacketChatMessage) && !(paramPacket instanceof net.minecraft.network.play.client.C15PacketClientSettings) && !(paramPacket instanceof net.minecraft.network.play.client.C17PacketCustomPayload) && !(paramPacket instanceof net.minecraft.network.play.client.C19PacketResourcePackStatus) && OringoClient.mc.field_71439_g.field_70173_aa < 80)
      if (OringoClient..()) {
        paramCallbackInfo.cancel();
        Disabler. = true;
      } else {
        Disabler. = false;
      }  
  }
  
  @Inject(method = {"channelRead0*"}, at = {@At("HEAD")}, cancellable = true)
  private void onChannelReadHead(ChannelHandlerContext paramChannelHandlerContext, Packet<?> paramPacket, CallbackInfo paramCallbackInfo) {
    if (paramPacket instanceof net.minecraft.network.play.server.S01PacketJoinGame)
      (new WorldJoinEvent()).post(); 
    if ((new PacketReceivedEvent(paramPacket, paramChannelHandlerContext)).post())
      paramCallbackInfo.cancel(); 
  }
  
  @Inject(method = {"channelRead0*"}, at = {@At("RETURN")}, cancellable = true)
  private void onPost(ChannelHandlerContext paramChannelHandlerContext, Packet<?> paramPacket, CallbackInfo paramCallbackInfo) {
    if ((new PacketReceivedEvent.Post(paramPacket, paramChannelHandlerContext)).post())
      paramCallbackInfo.cancel(); 
  }
}
