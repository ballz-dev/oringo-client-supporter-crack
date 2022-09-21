package me.oringo.oringoclient.qolfeatures.module.impl.other;

import com.google.gson.JsonParser;
import com.mojang.authlib.properties.Property;
import com.mojang.realmsclient.gui.ChatFormatting;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import me.oringo.oringoclient.events.impl.WorldJoinEvent;
import me.oringo.oringoclient.qolfeatures.module.Module;
import me.oringo.oringoclient.qolfeatures.module.impl.movement.Sneak;
import me.oringo.oringoclient.qolfeatures.module.impl.skyblock.BoneThrower;
import me.oringo.oringoclient.qolfeatures.module.settings.Setting;
import me.oringo.oringoclient.utils.SkyblockUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.Packet;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.opengl.GL11;

public class AntiNicker extends Module {
  public static List<UUID>  = new ArrayList<>();
  
  @SubscribeEvent
  public void (WorldJoinEvent paramWorldJoinEvent) {
    .clear();
  }
  
  @SubscribeEvent
  public void (TickEvent.ClientTickEvent paramClientTickEvent) {
    if (!() || mc.field_71441_e == null || paramClientTickEvent.phase == TickEvent.Phase.START || (!SkyblockUtils.onSkyblock && !SkyblockUtils.))
      return; 
    for (EntityPlayer entityPlayer : mc.field_71441_e.field_73010_i.stream().filter(AntiNicker::).collect(Collectors.toList())) {
      String str1 = Setting.(entityPlayer.func_146103_bH());
      String str2 = entityPlayer.func_70005_c_();
      .add(entityPlayer.func_110124_au());
      if (!str1.isEmpty() && !str1.equals(str2) && !str2.contains(" "))
        Sneak.(String.valueOf((new StringBuilder()).append(str2).append(ChatFormatting.RESET).append(ChatFormatting.GRAY).append(" is nicked!").append((str1.equals("Tactful") || str1.equals("Pickguard")) ? "" : String.valueOf((new StringBuilder()).append(" Their real name is ").append(str1).append("!"))))); 
    } 
  }
  
  public static void (Packet<?> paramPacket) {
    Module.mc.func_147114_u().func_147297_a(paramPacket);
  }
  
  public AntiNicker() {
    super("Anti Nicker", 0, Module.Category.OTHER);
  }
  
  public static void (Color paramColor) {
    GL11.glColor4f(paramColor.getRed() / 255.0F, paramColor.getGreen() / 255.0F, paramColor.getBlue() / 255.0F, paramColor.getAlpha() / 255.0F);
  }
}
