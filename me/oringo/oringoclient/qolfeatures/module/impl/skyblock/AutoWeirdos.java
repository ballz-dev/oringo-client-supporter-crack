package me.oringo.oringoclient.qolfeatures.module.impl.skyblock;

import com.google.gson.JsonObject;
import com.mojang.realmsclient.gui.ChatFormatting;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import me.oringo.oringoclient.events.impl.MotionUpdateEvent;
import me.oringo.oringoclient.qolfeatures.module.Module;
import me.oringo.oringoclient.utils.SkyblockUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.util.Vec3i;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;

public class AutoWeirdos extends Module {
  public String ;
  
  public static String[]  = new String[] { "The reward is not in my chest!", "At least one of them is lying, and the reward is not in", "My chest doesn't have the reward. We are all telling the truth", "My chest has the reward and I'm telling the truth", "The reward isn't in any of our chests", "Both of them are telling the truth." };
  
  public List<EntityArmorStand>  = new ArrayList<>();
  
  public List<EntityArmorStand>  = new ArrayList<>();
  
  @SubscribeEvent
  public void (ClientChatReceivedEvent paramClientChatReceivedEvent) {
    if (!SkyblockUtils.inDungeon || !() || paramClientChatReceivedEvent.type == 2)
      return; 
    String str = ChatFormatting.stripFormatting(paramClientChatReceivedEvent.message.func_150254_d().trim());
    if (str.startsWith("[NPC]"))
      for (String str1 : ) {
        if (str.contains(str1)) {
          this. = str.substring(str.indexOf(']') + 2, str.indexOf(':'));
          BlazeSwapper.(this.);
        } 
      }  
  }
  
  public AutoWeirdos() {
    super("Auto Weirdos", Module.Category.);
  }
  
  @SubscribeEvent
  public void (MotionUpdateEvent.Post paramPost) {
    if (!SkyblockUtils.inDungeon || !())
      return; 
    if (this..size() + this..size() < 3) {
      for (EntityArmorStand entityArmorStand : mc.field_71441_e.func_175644_a(EntityArmorStand.class, AutoWeirdos::)) {
        List<EntityArmorStand> list = mc.field_71441_e.func_175647_a(EntityArmorStand.class, entityArmorStand.func_174813_aQ().func_72314_b(6.0D, 6.0D, 6.0D), AutoWeirdos::);
        if (list.size() == 3) {
          this. = list;
          break;
        } 
      } 
    } else if (this..size() > 2 && this. != null) {
      List list = mc.field_71441_e.func_175644_a(EntityArmorStand.class, this::lambda$onUpdate$2);
      List<TileEntityChest> list1 = (List)mc.field_71441_e.field_147482_g.stream().filter(AutoWeirdos::).collect(Collectors.toList());
      if (!list.isEmpty() && !list1.isEmpty()) {
        list1.sort(Comparator.comparingDouble(list::));
        TileEntityChest tileEntityChest = list1.get(0);
        if (mc.field_71439_g.func_70011_f(tileEntityChest.func_174877_v().func_177958_n(), (tileEntityChest.func_174877_v().func_177956_o() - mc.field_71439_g.func_70047_e()), tileEntityChest.func_174877_v().func_177952_p()) < 6.0D) {
          mc.field_71442_b.func_178890_a(mc.field_71439_g, mc.field_71441_e, mc.field_71439_g.func_70694_bm(), tileEntityChest.func_174877_v(), EnumFacing.DOWN, new Vec3(0.0D, 0.0D, 0.0D));
          this. = null;
        } 
      } 
    } 
    this..removeIf(this::lambda$onUpdate$5);
  }
  
  @SubscribeEvent
  public void (WorldEvent.Load paramLoad) {
    this. = null;
    this..clear();
    this..clear();
  }
  
  public static boolean (BlockPos paramBlockPos) {
    MovingObjectPosition movingObjectPosition = AutoBeams.mc.field_71441_e.func_147447_a(AutoBeams.mc.field_71439_g.func_174824_e(0.0F), (new Vec3((Vec3i)paramBlockPos)).func_72441_c(0.5D, 0.5D, 0.5D), true, false, true);
    return (movingObjectPosition != null && paramBlockPos.equals(movingObjectPosition.func_178782_a()));
  }
  
  public static Color (int paramInt) {
    float f1 = (paramInt >> 24 & 0xFF) / 256.0F;
    float f2 = (paramInt >> 16 & 0xFF) / 255.0F;
    float f3 = (paramInt >> 8 & 0xFF) / 255.0F;
    float f4 = (paramInt & 0xFF) / 255.0F;
    GL11.glColor4f(f2, f3, f4, f1);
    return new Color(f2, f3, f4, f1);
  }
  
  public static int (JsonObject paramJsonObject) {
    try {
      return paramJsonObject.getAsJsonObject("stats").getAsJsonObject("Duels").get("sumo_duel_wins").getAsInt();
    } catch (Exception exception) {
      return 10000000;
    } 
  }
}
