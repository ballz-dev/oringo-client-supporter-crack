package me.oringo.oringoclient.qolfeatures;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.jagrosh.discordipc.IPCListener;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import me.oringo.oringoclient.OringoClient;
import me.oringo.oringoclient.commands.impl.ClipCommand;
import me.oringo.oringoclient.events.impl.DeathEvent;
import me.oringo.oringoclient.events.impl.KillEvent;
import me.oringo.oringoclient.events.impl.PacketReceivedEvent;
import me.oringo.oringoclient.events.impl.PacketSentEvent;
import me.oringo.oringoclient.events.impl.PreAttackEvent;
import me.oringo.oringoclient.events.impl.WorldJoinEvent;
import me.oringo.oringoclient.qolfeatures.module.impl.render.CustomInterfaces;
import me.oringo.oringoclient.qolfeatures.module.impl.render.RichPresenceModule;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.network.play.server.S09PacketHeldItemChange;
import net.minecraftforge.client.event.RenderBlockOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class BackgroundProcess {
  public static List<EntityOtherPlayerMP>  = new ArrayList<>();
  
  public static void () {
    if (Minecraft.field_142025_a)
      return; 
    try {
      JsonObject jsonObject = (new JsonParser()).parse(new InputStreamReader((new URL("https://randomuser.me/api/?format=json")).openStream())).getAsJsonObject().get("results").getAsJsonArray().get(0).getAsJsonObject();
      RichPresenceModule..setListener((IPCListener)new Object(jsonObject));
      RichPresenceModule..connect(new com.jagrosh.discordipc.entities.DiscordBuild[0]);
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
  }
  
  public static int (String paramString) {
    return ClipCommand.(paramString::);
  }
  
  @SubscribeEvent
  public void (WorldJoinEvent paramWorldJoinEvent) {
    .clear();
  }
  
  @SubscribeEvent
  public void (PacketSentEvent paramPacketSentEvent) {
    if (paramPacketSentEvent. instanceof C08PacketPlayerBlockPlacement) {
      C08PacketPlayerBlockPlacement c08PacketPlayerBlockPlacement = (C08PacketPlayerBlockPlacement)paramPacketSentEvent.;
      if (c08PacketPlayerBlockPlacement.func_149574_g() != null && c08PacketPlayerBlockPlacement.func_149574_g().func_77973_b() instanceof net.minecraft.item.ItemEnderPearl) {
        paramPacketSentEvent.setCanceled(true);
        CustomInterfaces.((Packet)new C08PacketPlayerBlockPlacement(c08PacketPlayerBlockPlacement.func_149574_g()));
      } 
    } 
  }
  
  @SubscribeEvent
  public void (RenderBlockOverlayEvent paramRenderBlockOverlayEvent) {
    if (paramRenderBlockOverlayEvent.overlayType == RenderBlockOverlayEvent.OverlayType.FIRE)
      paramRenderBlockOverlayEvent.setCanceled(true); 
  }
  
  @SubscribeEvent
  public void (DeathEvent paramDeathEvent) {
    if (paramDeathEvent. instanceof EntityOtherPlayerMP && .contains(paramDeathEvent.)) {
      MinecraftForge.EVENT_BUS.post((Event)new KillEvent((EntityOtherPlayerMP)paramDeathEvent.));
      .remove(paramDeathEvent.);
    } 
  }
  
  @SubscribeEvent
  public void (PreAttackEvent paramPreAttackEvent) {
    if (paramPreAttackEvent. instanceof EntityOtherPlayerMP && !.contains(paramPreAttackEvent.))
      .add((EntityOtherPlayerMP)paramPreAttackEvent.); 
  }
  
  @SubscribeEvent(priority = EventPriority.LOWEST)
  public void (RenderGameOverlayEvent.Chat paramChat) {
    paramChat.posY = paramChat.resolution.func_78328_b() - 60;
  }
  
  @SubscribeEvent
  public void (PacketReceivedEvent paramPacketReceivedEvent) {
    if (paramPacketReceivedEvent. instanceof S09PacketHeldItemChange) {
      if (OringoClient.mc.field_71439_g == null || OringoClient.mc.field_71439_g.field_70173_aa < 80)
        return; 
      OringoClient.mc.func_147114_u().func_147297_a((Packet)new C09PacketHeldItemChange(((S09PacketHeldItemChange)paramPacketReceivedEvent.).func_149385_c()));
      OringoClient.mc.func_147114_u().func_147297_a((Packet)new C09PacketHeldItemChange(OringoClient.mc.field_71439_g.field_71071_by.field_70461_c));
      paramPacketReceivedEvent.cancel();
    } 
  }
}
