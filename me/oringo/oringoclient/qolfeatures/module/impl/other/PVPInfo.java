package me.oringo.oringoclient.qolfeatures.module.impl.other;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.realmsclient.gui.ChatFormatting;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.function.Consumer;
import me.oringo.oringoclient.OringoClient;
import me.oringo.oringoclient.events.impl.PacketReceivedEvent;
import me.oringo.oringoclient.events.impl.WorldJoinEvent;
import me.oringo.oringoclient.mixins.PlayerControllerAccessor;
import me.oringo.oringoclient.qolfeatures.module.Module;
import me.oringo.oringoclient.qolfeatures.module.impl.movement.Sneak;
import me.oringo.oringoclient.qolfeatures.module.impl.render.CustomInterfaces;
import me.oringo.oringoclient.qolfeatures.module.settings.Setting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.BooleanSetting;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.network.play.server.S3EPacketTeams;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class PVPInfo extends Module {
  public BooleanSetting  = new BooleanSetting("Show WLR", true);
  
  public String  = null;
  
  public BooleanSetting  = new BooleanSetting("Show FKDR", true);
  
  public BooleanSetting  = new BooleanSetting("Show streak", true);
  
  public HashMap<String, String>  = new HashMap<>();
  
  public HashMap<String, PlayerInfo>  = new HashMap<>();
  
  public boolean  = false;
  
  public BooleanSetting  = new BooleanSetting("Show Levels", true);
  
  @SubscribeEvent
  public void (PacketReceivedEvent paramPacketReceivedEvent) {
    if (paramPacketReceivedEvent. instanceof S3EPacketTeams && ((S3EPacketTeams)paramPacketReceivedEvent.).func_149312_c().equals("§7§k") && ((S3EPacketTeams)paramPacketReceivedEvent.).func_149310_g().size() == 1) {
      String str = ((S3EPacketTeams)paramPacketReceivedEvent.).func_149310_g().iterator().next();
      if (this. == null) {
        this. = str;
      } else {
        this..put(this., str);
        if (())
          (str, PVPInfo::); 
        this. = null;
      } 
    } 
  }
  
  public static void () {
    int i = OringoClient.mc.field_71439_g.field_71071_by.field_70461_c;
    if (i != ((PlayerControllerAccessor)OringoClient.mc.field_71442_b).getCurrentPlayerItem()) {
      ((PlayerControllerAccessor)OringoClient.mc.field_71442_b).setCurrentPlayerItem(i);
      CustomInterfaces.((Packet)new C09PacketHeldItemChange(i));
    } 
  }
  
  @SubscribeEvent
  public void (WorldJoinEvent paramWorldJoinEvent) {
    this. = false;
    this..clear();
    this. = null;
    this..clear();
  }
  
  @SubscribeEvent
  public void (ClientChatReceivedEvent paramClientChatReceivedEvent) {
    if (ChatFormatting.stripFormatting(paramClientChatReceivedEvent.message.func_150254_d()).trim().equals("The Angel of Death has corrupted this game!"))
      this. = true; 
  }
  
  public PVPInfo() {
    super("PVP Info", Module.Category.OTHER);
    (new Setting[] { (Setting)this., (Setting)this., (Setting)this., (Setting)this. });
  }
  
  public PlayerInfo (String paramString) {
    return (paramString, PVPInfo::);
  }
  
  public String (String paramString) {
    StringBuilder stringBuilder = new StringBuilder();
    boolean bool = false;
    for (char c : paramString.toCharArray()) {
      if (c == '[') {
        if (bool)
          continue; 
        bool = true;
      } 
      stringBuilder.append(c);
      continue;
    } 
    return String.valueOf(stringBuilder).replaceAll("&", "§");
  }
  
  public PlayerInfo (String paramString, Consumer<PlayerInfo> paramConsumer) {
    // Byte code:
    //   0: aload_0
    //   1: getfield  : Ljava/util/HashMap;
    //   4: aload_1
    //   5: invokevirtual containsKey : (Ljava/lang/Object;)Z
    //   8: ifne -> 41
    //   11: aload_0
    //   12: getfield  : Ljava/util/HashMap;
    //   15: aload_1
    //   16: aconst_null
    //   17: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   20: pop
    //   21: new java/lang/Thread
    //   24: dup
    //   25: aload_0
    //   26: aload_1
    //   27: aload_2
    //   28: <illegal opcode> run : (Lme/oringo/oringoclient/qolfeatures/module/impl/other/PVPInfo;Ljava/lang/String;Ljava/util/function/Consumer;)Ljava/lang/Runnable;
    //   33: invokespecial <init> : (Ljava/lang/Runnable;)V
    //   36: invokevirtual start : ()V
    //   39: aconst_null
    //   40: areturn
    //   41: aload_2
    //   42: aload_0
    //   43: getfield  : Ljava/util/HashMap;
    //   46: aload_1
    //   47: invokevirtual get : (Ljava/lang/Object;)Ljava/lang/Object;
    //   50: checkcast me/oringo/oringoclient/qolfeatures/module/impl/other/PVPInfo$PlayerInfo
    //   53: invokeinterface accept : (Ljava/lang/Object;)V
    //   58: aload_0
    //   59: getfield  : Ljava/util/HashMap;
    //   62: aload_1
    //   63: invokevirtual get : (Ljava/lang/Object;)Ljava/lang/Object;
    //   66: checkcast me/oringo/oringoclient/qolfeatures/module/impl/other/PVPInfo$PlayerInfo
    //   69: areturn
  }
  
  public static class BedWarsStats {
    public double ;
    
    public int ;
    
    public double ;
    
    public String ;
  }
  
  public static class DuelsStatsObject {
    public int winstreak = -1;
    
    public int losses;
    
    public int wins;
    
    public JsonObject winstreaks;
    
    public int getWinstreak() {
      return this.winstreak = (this.winstreak == -1) ? this.winstreaks.getAsJsonObject("current").get("overall").getAsInt() : this.winstreak;
    }
    
    public String getWLR() {
      return String.format(String.valueOf((new StringBuilder()).append((this.wins / this.losses > 3.0D) ? "§4" : ((this.wins / this.losses > 2.0D) ? "§c" : "§f")).append("%.2f")), new Object[] { Double.valueOf(this.wins / this.losses) });
    }
  }
  
  public static class SkyWarsStatsObject {
    public double level;
    
    public int kills;
    
    public int experience;
    
    public double win_loss_ratio;
    
    public int wins;
    
    public String levelFormatted;
    
    public int deaths;
    
    public int losses;
    
    public double kill_death_ratio;
    
    public String getWLR() {
      return String.format(String.valueOf((new StringBuilder()).append((this.win_loss_ratio > 0.4D) ? "§4" : ((this.win_loss_ratio > 0.3D) ? "§c" : "§f")).append("%.2f")), new Object[] { Double.valueOf(this.win_loss_ratio) });
    }
    
    public String getHead() {
      return (this.kills > 24999) ? "§5Heavenly" : ((this.kills > 9999) ? "§6Divine" : ((this.kills > 4999) ? "§dSucculent" : ((this.kills > 1999) ? "§3Tasty" : ((this.kills > 999) ? "§aSalty" : ((this.kills > 499) ? "§eDecent" : ((this.kills > 199) ? "§fMeh" : ((this.kills > 49) ? "§7Yucky" : "§8Eww")))))));
    }
  }
  
  public static class PlayerInfo {
    public PVPInfo.BedWarsStats ;
    
    public String ;
    
    public String ;
    
    public double ;
    
    public PVPInfo.SkyWarsStatsObject ;
    
    public PVPInfo.DuelsStatsObject ;
    
    public PlayerInfo(String param1String1, String param1String2, double param1Double, PVPInfo.SkyWarsStatsObject param1SkyWarsStatsObject, PVPInfo.DuelsStatsObject param1DuelsStatsObject, PVPInfo.BedWarsStats param1BedWarsStats) {
      this. = param1String1;
      this. = param1String2;
      this. = param1Double;
      this. = param1SkyWarsStatsObject;
      this. = param1DuelsStatsObject;
      this. = param1BedWarsStats;
    }
  }
}
