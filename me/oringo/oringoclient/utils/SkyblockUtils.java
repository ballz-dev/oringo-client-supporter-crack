package me.oringo.oringoclient.utils;

import com.mojang.realmsclient.gui.ChatFormatting;
import java.util.Locale;
import me.oringo.oringoclient.OringoClient;
import me.oringo.oringoclient.events.impl.LeftClickEvent;
import me.oringo.oringoclient.events.impl.PacketReceivedEvent;
import me.oringo.oringoclient.events.impl.PreAttackEvent;
import me.oringo.oringoclient.events.impl.WorldJoinEvent;
import me.oringo.oringoclient.qolfeatures.module.impl.combat.KillAura;
import me.oringo.oringoclient.qolfeatures.module.impl.player.NoRotate;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.ModeSetting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.network.play.server.S02PacketChat;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class SkyblockUtils {
  public static boolean inDungeon;
  
  public static boolean ;
  
  public static boolean ;
  
  public static Minecraft mc = Minecraft.func_71410_x();
  
  public static boolean onSkyblock;
  
  public static boolean ;
  
  public static boolean ;
  
  public static boolean ;
  
  public static boolean ;
  
  @SubscribeEvent
  public void (WorldJoinEvent paramWorldJoinEvent) {
     = false;
     = false;
  }
  
  @SubscribeEvent
  public void (TickEvent.ClientTickEvent paramClientTickEvent) {
    if (mc.field_71441_e != null && paramClientTickEvent.phase == TickEvent.Phase.START) {
      onSkyblock = ModeSetting.();
      inDungeon = ((onSkyblock && (LeftClickEvent.("Cleared:") || LeftClickEvent.("Start"))) || mc.func_71356_B());
       = PreAttackEvent.();
       = (ChatFormatting.stripFormatting(KillAura.()).equals("BED WARS") && !LeftClickEvent.("Coins: "));
       = ChatFormatting.stripFormatting(KillAura.()).equals("DUELS");
       = (ChatFormatting.stripFormatting(KillAura.()).equals("SKYWARS") && LeftClickEvent.("Mode: "));
    } 
  }
  
  @SubscribeEvent
  public void (PacketReceivedEvent paramPacketReceivedEvent) {
    if (paramPacketReceivedEvent. instanceof S02PacketChat) {
      if (ChatFormatting.stripFormatting(((S02PacketChat)paramPacketReceivedEvent.).func_148915_c().func_150254_d()).startsWith("[BOSS] The Watcher: ") && !) {
         = true;
        if (OringoClient..())
          WorldJoinEvent.("Started Camp", 1000); 
      } 
      if (ChatFormatting.stripFormatting(((S02PacketChat)paramPacketReceivedEvent.).func_148915_c().func_150254_d()).equals("[BOSS] The Watcher: You have proven yourself. You may pass.")) {
         = false;
        if (OringoClient..())
          NoRotate.("Oringo Client", "Stopped camp", 1000); 
      } 
      if (ChatFormatting.stripFormatting(((S02PacketChat)paramPacketReceivedEvent.).func_148915_c().func_150254_d()).startsWith("[BOSS] Goldor: Who dares trespass into my domain?"))
         = true; 
    } 
  }
}
