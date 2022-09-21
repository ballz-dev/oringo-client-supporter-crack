package me.oringo.oringoclient.qolfeatures.module.impl.other;

import com.mojang.realmsclient.gui.ChatFormatting;
import java.awt.Desktop;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import me.oringo.oringoclient.OringoClient;
import me.oringo.oringoclient.commands.impl.ThreeDClipCommand;
import me.oringo.oringoclient.events.impl.KillEvent;
import me.oringo.oringoclient.events.impl.MoveStateUpdateEvent;
import me.oringo.oringoclient.qolfeatures.module.Module;
import me.oringo.oringoclient.qolfeatures.module.impl.macro.MithrilMacro;
import me.oringo.oringoclient.qolfeatures.module.settings.Setting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.ModeSetting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.RunnableSetting;
import me.oringo.oringoclient.utils.MilliTimer;
import me.oringo.oringoclient.utils.SkyblockUtils;
import me.oringo.oringoclient.utils.StencilUtils;
import net.minecraft.inventory.Slot;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;

public class KillInsults extends Module {
  public static RunnableSetting ;
  
  public static RunnableSetting ;
  
  public static Pattern ;
  
  public static ModeSetting  = new ModeSetting("Mode", "Dungeon", new String[] { "Dungeon", "Normal" });
  
  public static List<String> ;
  
  public static Random ;
  
  public static MilliTimer ;
  
  public static void () {
    GL11.glStencilFunc(519, 1, 1);
    GL11.glStencilOp(7681, 7681, 7681);
    GL11.glColorMask(false, false, false, false);
  }
  
  static {
     = new RunnableSetting("Reload", MoveStateUpdateEvent::);
     = new RunnableSetting("Open", KillInsults::);
     = new Random();
     = new ArrayList<>();
     = new MilliTimer();
     = Pattern.compile("^ ☠ .+ and became a ghost\\.$");
  }
  
  @SubscribeEvent
  public void (KillEvent paramKillEvent) {
    if (() && .("Normal"))
      ThreeDClipCommand.(ChatFormatting.stripFormatting(paramKillEvent..func_70005_c_()), "/ac "); 
  }
  
  public static void () {
    MithrilMacro.(StencilUtils.mc.func_147110_a());
  }
  
  public static int (String paramString) {
    ArrayList<?> arrayList = new ArrayList(OringoClient.mc.field_71439_g.field_71069_bz.field_75151_b);
    Collections.reverse(arrayList);
    for (Slot slot : arrayList) {
      if (slot.func_75216_d() && slot.func_75211_c().func_82833_r().toLowerCase().contains(paramString.toLowerCase()))
        return slot.field_75222_d; 
    } 
    return -1;
  }
  
  @SubscribeEvent(receiveCanceled = true)
  public void (ClientChatReceivedEvent paramClientChatReceivedEvent) {
    if (() && .("Dungeon") && SkyblockUtils.inDungeon) {
      String str = ChatFormatting.stripFormatting(paramClientChatReceivedEvent.message.func_150254_d());
      Matcher matcher = .matcher(str);
      if (matcher.find()) {
        String[] arrayOfString = str.split(" ");
        if (arrayOfString.length > 2) {
          String str1 = arrayOfString[2];
          if (!str1.equals("You") && !str1.equals(mc.field_71439_g.func_70005_c_()))
            ThreeDClipCommand.(str1, "/pc "); 
        } 
      } 
    } 
  }
  
  public KillInsults() {
    super("Kill Insults", Module.Category.OTHER);
    (new Setting[] { (Setting), (Setting), (Setting) });
  }
}
