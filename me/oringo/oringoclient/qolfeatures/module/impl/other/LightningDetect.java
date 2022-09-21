package me.oringo.oringoclient.qolfeatures.module.impl.other;

import me.oringo.oringoclient.events.impl.PacketReceivedEvent;
import me.oringo.oringoclient.events.impl.PreAttackEvent;
import me.oringo.oringoclient.qolfeatures.module.Module;
import me.oringo.oringoclient.qolfeatures.module.impl.skyblock.BoneThrower;
import me.oringo.oringoclient.utils.Rotation;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.event.ClickEvent;
import net.minecraft.network.play.server.S29PacketSoundEffect;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.IChatComponent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;

public class LightningDetect extends Module {
  static {
  
  }
  
  public static void () {
    GL11.glDisable(32823);
    GlStateManager.func_179136_a(1.0F, 1000000.0F);
    GlStateManager.func_179113_r();
  }
  
  public static double (Rotation paramRotation1, Rotation paramRotation2) {
    return Math.hypot(PreAttackEvent.(paramRotation1.(), paramRotation2.()), PreAttackEvent.(paramRotation1.(), paramRotation2.()));
  }
  
  @SubscribeEvent
  public void (PacketReceivedEvent paramPacketReceivedEvent) {
    if (() && paramPacketReceivedEvent. instanceof S29PacketSoundEffect) {
      S29PacketSoundEffect s29PacketSoundEffect = (S29PacketSoundEffect)paramPacketReceivedEvent.;
      if (s29PacketSoundEffect.func_149212_c().equals("ambient.weather.thunder")) {
        ChatComponentText chatComponentText = new ChatComponentText(String.valueOf((new StringBuilder()).append("§bOringoClient §3» §7Lightning found! X:").append((int)s29PacketSoundEffect.func_149207_d()).append(" Y:").append((int)s29PacketSoundEffect.func_149211_e()).append(" Z:").append((int)s29PacketSoundEffect.func_149210_f())));
        ChatStyle chatStyle = new ChatStyle();
        chatStyle.func_150241_a(new ClickEvent(ClickEvent.Action.RUN_COMMAND, String.format("%suhctp %s %s %s", new Object[] { Character.valueOf(BoneThrower.()), Double.valueOf(s29PacketSoundEffect.func_149207_d()), Integer.valueOf(100), Double.valueOf(s29PacketSoundEffect.func_149210_f()) })));
        chatComponentText.func_150255_a(chatStyle);
        mc.field_71439_g.func_145747_a((IChatComponent)chatComponentText);
      } 
    } 
  }
  
  public LightningDetect() {
    super("Lightning Detect", Module.Category.OTHER);
  }
}
