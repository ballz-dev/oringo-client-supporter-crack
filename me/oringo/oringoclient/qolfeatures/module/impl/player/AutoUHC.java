package me.oringo.oringoclient.qolfeatures.module.impl.player;

import me.oringo.oringoclient.OringoClient;
import me.oringo.oringoclient.events.impl.MotionUpdateEvent;
import me.oringo.oringoclient.events.impl.PacketReceivedEvent;
import me.oringo.oringoclient.qolfeatures.module.Module;
import me.oringo.oringoclient.qolfeatures.module.impl.combat.KillAura;
import me.oringo.oringoclient.qolfeatures.module.impl.other.AntiNicker;
import me.oringo.oringoclient.qolfeatures.module.impl.other.KillInsults;
import me.oringo.oringoclient.qolfeatures.module.impl.other.LunarSpoofer;
import me.oringo.oringoclient.qolfeatures.module.impl.skyblock.RodStacker;
import me.oringo.oringoclient.qolfeatures.module.settings.Setting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.BooleanSetting;
import me.oringo.oringoclient.utils.MilliTimer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.event.ClickEvent;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C0DPacketCloseWindow;
import net.minecraft.network.play.client.C0EPacketClickWindow;
import net.minecraft.network.play.server.S2DPacketOpenWindow;
import net.minecraft.util.ChatStyle;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class AutoUHC extends Module {
  public static BooleanSetting ;
  
  public static int ;
  
  public static BooleanSetting ;
  
  public static BooleanSetting ;
  
  public static BooleanSetting  = new BooleanSetting("Auto craft", true, AutoUHC::);
  
  public static MilliTimer ;
  
  static {
     = new BooleanSetting("Plancks", true, AutoUHC::);
     = new BooleanSetting("Quick Pick", true, AutoUHC::);
     = new BooleanSetting("Ore packs", true, AutoUHC::);
     = new MilliTimer();
  }
  
  public AutoUHC() {
    super("Auto Craft", Module.Category.);
    (new Setting[] { (Setting), (Setting), (Setting), (Setting) });
  }
  
  @SubscribeEvent(receiveCanceled = true)
  public void (ClientChatReceivedEvent paramClientChatReceivedEvent) {
    if (!() || !.() || KillAura. != null)
      return; 
    ChatStyle chatStyle = paramClientChatReceivedEvent.message.func_150256_b();
    if (chatStyle != null && chatStyle.func_150235_h() != null) {
      ClickEvent clickEvent = chatStyle.func_150235_h();
      if (clickEvent.func_150669_a() == ClickEvent.Action.RUN_COMMAND) {
        String str = clickEvent.func_150668_b();
        if (str.startsWith("/internal_autocraftitem "))
          switch (str.replaceAll("/internal_autocraftitem ", "")) {
            case "EFFICIENCY_PICKAXE":
              if (.())
                RodStacker.(str); 
              break;
            case "IRON_INGOTS":
              if (.() && (!.() || KillInsults.("Quick Pick") != -1))
                RodStacker.(str); 
              break;
            case "GOLD_PACK":
              if (.())
                RodStacker.(str); 
              break;
          }  
      } 
    } 
  }
  
  public static boolean (float paramFloat) {
    return LunarSpoofer.(paramFloat, 0.0D, 0.0D);
  }
  
  @SubscribeEvent
  public void (PacketReceivedEvent paramPacketReceivedEvent) {
    if (!() ||  == 0)
      return; 
    if (paramPacketReceivedEvent. instanceof S2DPacketOpenWindow) {
      S2DPacketOpenWindow s2DPacketOpenWindow = (S2DPacketOpenWindow)paramPacketReceivedEvent.;
      if (s2DPacketOpenWindow.func_179840_c().func_150260_c().contains("Crafting Table")) {
        paramPacketReceivedEvent.setCanceled(true);
        AntiNicker.((Packet)new C0EPacketClickWindow(s2DPacketOpenWindow.func_148901_c(), 24, 0, 1, null, (short)1));
        AntiNicker.((Packet)new C0DPacketCloseWindow(s2DPacketOpenWindow.func_148901_c()));
        --;
      } 
    } 
    if (.(500L))
       = 0; 
  }
  
  public static void (int paramInt1, int paramInt2) {
    OringoClient.mc.field_71442_b.func_78753_a(OringoClient.mc.field_71439_g.field_71069_bz.field_75152_c, paramInt1, paramInt2, 2, (EntityPlayer)OringoClient.mc.field_71439_g);
  }
  
  @SubscribeEvent
  public void (MotionUpdateEvent.Pre paramPre) {
    if (!() || mc.field_71439_g.field_70173_aa < 150)
      return; 
    if (.() && mc.field_71439_g.field_71069_bz.field_75152_c == mc.field_71439_g.field_71070_bA.field_75152_c &&  == 0 && KillAura. == null && .()) {
      int i = AutoTool.(AutoUHC::);
      if (i != -1) {
        (i, 0);
        (1, 0);
        (i, 0);
        AutoPot.(0);
      } 
    } 
  }
}
