package me.oringo.oringoclient.qolfeatures.module.impl.combat;

import com.mojang.realmsclient.gui.ChatFormatting;
import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import me.oringo.oringoclient.OringoClient;
import me.oringo.oringoclient.qolfeatures.module.Module;
import me.oringo.oringoclient.qolfeatures.module.impl.macro.AutoSumoBot;
import me.oringo.oringoclient.qolfeatures.module.impl.other.SimulatorAura;
import me.oringo.oringoclient.qolfeatures.module.impl.player.FastUse;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.NumberSetting;
import me.oringo.oringoclient.utils.DiscordWebhook;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.multiplayer.GuiConnecting;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

public class SumoFences extends Module {
  public ArrayList<BlockPos>  = new ArrayList<>();
  
  public static int ;
  
  public static int  = -1;
  
  public List<String>  = Arrays.asList(new String[] { "gg", "gf", "how" });
  
  public static int  = -1;
  
  public String  = "";
  
  public List<String>  = Arrays.asList(new String[] { "gg", "gf" });
  
  public static int ;
  
  public boolean  = false;
  
  static {
     = 0;
     = 0;
  }
  
  @SubscribeEvent
  public void (GuiOpenEvent paramGuiOpenEvent) {
    if (paramGuiOpenEvent.gui instanceof net.minecraft.client.gui.GuiDisconnected && AutoSumoBot. != null) {
      DiscordWebhook discordWebhook = new DiscordWebhook(OringoClient...());
      discordWebhook.setUsername("AutoSumo bot");
      discordWebhook.setAvatarUrl("https://cdn.discordapp.com/icons/913088401262137424/496d604510a63242db77526d8bfab9fa.png");
      discordWebhook.setContent("Bot Disconnected!");
       = 2000;
      try {
        discordWebhook.execute();
      } catch (IOException iOException) {
        iOException.printStackTrace();
      } 
    } 
  }
  
  @SubscribeEvent
  public void (ClientChatReceivedEvent paramClientChatReceivedEvent) {
    if (AutoSumoBot. == null)
      return; 
    if (ChatFormatting.stripFormatting(paramClientChatReceivedEvent.message.func_150254_d()).contains("                           Sumo Duel - "))
      this. = ChatFormatting.stripFormatting(paramClientChatReceivedEvent.message.func_150254_d()).replaceFirst("                           Sumo Duel - ", ""); 
    if (paramClientChatReceivedEvent.message.func_150254_d().contains("§f§lMelee Accuracy"))
      (new Thread(this::lambda$onChat$1)).start(); 
  }
  
  public SumoFences() {
    super("Sumo Fences", 0, Module.Category.);
  }
  
  public void (BlockPos paramBlockPos) {
    for (BlockPos blockPos : Arrays.<BlockPos>asList(new BlockPos[] { paramBlockPos.func_177976_e(), paramBlockPos.func_177978_c().func_177974_f(), paramBlockPos.func_177978_c().func_177976_e(), paramBlockPos.func_177968_d().func_177976_e(), paramBlockPos.func_177968_d().func_177974_f(), paramBlockPos.func_177974_f(), paramBlockPos.func_177978_c(), paramBlockPos.func_177968_d() })) {
      if ((Minecraft.func_71410_x()).field_71441_e.func_180495_p(blockPos).func_177230_c().equals(Blocks.field_150350_a)) {
        (Minecraft.func_71410_x()).field_71441_e.func_175656_a(blockPos.func_177984_a().func_177984_a().func_177984_a(), Blocks.field_150350_a.func_176223_P());
        (Minecraft.func_71410_x()).field_71441_e.func_175656_a(blockPos.func_177984_a().func_177984_a().func_177984_a(), Blocks.field_180407_aO.func_176223_P());
        (Minecraft.func_71410_x()).field_71441_e.func_175656_a(blockPos.func_177984_a().func_177984_a().func_177984_a().func_177984_a(), Blocks.field_180407_aO.func_176223_P());
        continue;
      } 
      if (!this..contains(blockPos)) {
        this..add(blockPos);
        (blockPos);
      } 
    } 
  }
  
  public static void (Entity paramEntity, float paramFloat1, float paramFloat2, Color paramColor) {
    Matrix4f matrix4f1 = SimulatorAura.(2982);
    Matrix4f matrix4f2 = SimulatorAura.(2983);
    GL11.glPushAttrib(8192);
    GL11.glEnable(3042);
    GL11.glDisable(3553);
    GL11.glDisable(2929);
    GL11.glMatrixMode(5889);
    GL11.glPushMatrix();
    GL11.glLoadIdentity();
    GL11.glOrtho(0.0D, OringoClient.mc.field_71443_c, OringoClient.mc.field_71440_d, 0.0D, -1.0D, 1.0D);
    GL11.glMatrixMode(5888);
    GL11.glPushMatrix();
    GL11.glLoadIdentity();
    GL11.glDisable(2929);
    GL11.glBlendFunc(770, 771);
    GlStateManager.func_179098_w();
    GlStateManager.func_179132_a(true);
    GL11.glLineWidth(paramFloat2);
    RenderManager renderManager = OringoClient.mc.func_175598_ae();
    AxisAlignedBB axisAlignedBB = paramEntity.func_174813_aQ().func_72317_d(-paramEntity.field_70165_t, -paramEntity.field_70163_u, -paramEntity.field_70161_v).func_72317_d(paramEntity.field_70142_S + (paramEntity.field_70165_t - paramEntity.field_70142_S) * paramFloat1, paramEntity.field_70137_T + (paramEntity.field_70163_u - paramEntity.field_70137_T) * paramFloat1, paramEntity.field_70136_U + (paramEntity.field_70161_v - paramEntity.field_70136_U) * paramFloat1).func_72317_d(-renderManager.field_78730_l, -renderManager.field_78731_m, -renderManager.field_78728_n);
    GL11.glColor4f(paramColor.getRed() / 255.0F, paramColor.getGreen() / 255.0F, paramColor.getBlue() / 255.0F, 1.0F);
    double[][] arrayOfDouble = { { axisAlignedBB.field_72340_a, axisAlignedBB.field_72338_b, axisAlignedBB.field_72339_c }, { axisAlignedBB.field_72340_a, axisAlignedBB.field_72337_e, axisAlignedBB.field_72339_c }, { axisAlignedBB.field_72336_d, axisAlignedBB.field_72337_e, axisAlignedBB.field_72339_c }, { axisAlignedBB.field_72336_d, axisAlignedBB.field_72338_b, axisAlignedBB.field_72339_c }, { axisAlignedBB.field_72340_a, axisAlignedBB.field_72338_b, axisAlignedBB.field_72334_f }, { axisAlignedBB.field_72340_a, axisAlignedBB.field_72337_e, axisAlignedBB.field_72334_f }, { axisAlignedBB.field_72336_d, axisAlignedBB.field_72337_e, axisAlignedBB.field_72334_f }, { axisAlignedBB.field_72336_d, axisAlignedBB.field_72338_b, axisAlignedBB.field_72334_f } };
    float f1 = Float.MAX_VALUE;
    float f2 = Float.MAX_VALUE;
    float f3 = -1.0F;
    float f4 = -1.0F;
    for (double[] arrayOfDouble1 : arrayOfDouble) {
      Vector2f vector2f = NumberSetting.(new Vector3f((float)arrayOfDouble1[0], (float)arrayOfDouble1[1], (float)arrayOfDouble1[2]), matrix4f1, matrix4f2, OringoClient.mc.field_71443_c, OringoClient.mc.field_71440_d);
      if (vector2f != null) {
        f1 = Math.min(vector2f.x, f1);
        f2 = Math.min(vector2f.y, f2);
        f3 = Math.max(vector2f.x, f3);
        f4 = Math.max(vector2f.y, f4);
      } 
    } 
    if (f1 > 0.0F || f2 > 0.0F || f3 <= OringoClient.mc.field_71443_c || f4 <= OringoClient.mc.field_71443_c) {
      GL11.glColor4f(paramColor.getRed() / 255.0F, paramColor.getGreen() / 255.0F, paramColor.getBlue() / 255.0F, 1.0F);
      GL11.glBegin(2);
      GL11.glVertex2f(f1, f2);
      GL11.glVertex2f(f1, f4);
      GL11.glVertex2f(f3, f4);
      GL11.glVertex2f(f3, f2);
      GL11.glEnd();
    } 
    GL11.glEnable(2929);
    GL11.glMatrixMode(5889);
    GL11.glPopMatrix();
    GL11.glMatrixMode(5888);
    GL11.glPopMatrix();
    GL11.glPopAttrib();
  }
  
  @SubscribeEvent
  public void (TickEvent.ClientTickEvent paramClientTickEvent) {
    if (-- == 0 && AutoSumoBot. != null)
      mc.func_147108_a((GuiScreen)new GuiConnecting((GuiScreen)new GuiMainMenu(), mc, new ServerData("Hypixel", "play.Hypixel.net", false))); 
  }
  
  @SubscribeEvent
  public void (TickEvent.ClientTickEvent paramClientTickEvent) {
    // Byte code:
    //   0: aload_0
    //   1: invokevirtual  : ()Z
    //   4: ifeq -> 143
    //   7: ldc_w 'Mode: Sumo'
    //   10: invokestatic  : (Ljava/lang/String;)Z
    //   13: ifeq -> 124
    //   16: ldc_w 'Opponent:'
    //   19: invokestatic  : (Ljava/lang/String;)Z
    //   22: ifeq -> 124
    //   25: aload_0
    //   26: getfield  : Z
    //   29: ifne -> 143
    //   32: aload_0
    //   33: iconst_1
    //   34: putfield  : Z
    //   37: invokestatic func_71410_x : ()Lnet/minecraft/client/Minecraft;
    //   40: astore_2
    //   41: aload_2
    //   42: getfield field_71439_g : Lnet/minecraft/client/entity/EntityPlayerSP;
    //   45: invokevirtual func_180425_c : ()Lnet/minecraft/util/BlockPos;
    //   48: astore_3
    //   49: iconst_0
    //   50: istore #4
    //   52: iload #4
    //   54: iinc #4, 1
    //   57: bipush #10
    //   59: if_icmpne -> 65
    //   62: goto -> 121
    //   65: invokestatic func_71410_x : ()Lnet/minecraft/client/Minecraft;
    //   68: getfield field_71441_e : Lnet/minecraft/client/multiplayer/WorldClient;
    //   71: aload_3
    //   72: invokevirtual func_177977_b : ()Lnet/minecraft/util/BlockPos;
    //   75: dup
    //   76: astore_3
    //   77: invokevirtual func_180495_p : (Lnet/minecraft/util/BlockPos;)Lnet/minecraft/block/state/IBlockState;
    //   80: invokeinterface func_177230_c : ()Lnet/minecraft/block/Block;
    //   85: getstatic net/minecraft/init/Blocks.field_150350_a : Lnet/minecraft/block/Block;
    //   88: invokevirtual equals : (Ljava/lang/Object;)Z
    //   91: ifeq -> 97
    //   94: goto -> 52
    //   97: aload_3
    //   98: astore #5
    //   100: new java/lang/Thread
    //   103: dup
    //   104: aload_0
    //   105: aload #5
    //   107: <illegal opcode> run : (Lme/oringo/oringoclient/qolfeatures/module/impl/combat/SumoFences;Lnet/minecraft/util/BlockPos;)Ljava/lang/Runnable;
    //   112: invokespecial <init> : (Ljava/lang/Runnable;)V
    //   115: invokevirtual start : ()V
    //   118: goto -> 121
    //   121: goto -> 143
    //   124: aload_0
    //   125: getfield  : Z
    //   128: ifeq -> 138
    //   131: aload_0
    //   132: getfield  : Ljava/util/ArrayList;
    //   135: invokevirtual clear : ()V
    //   138: aload_0
    //   139: iconst_0
    //   140: putfield  : Z
    //   143: goto -> 147
    //   146: astore_2
    //   147: return
    // Exception table:
    //   from	to	target	type
    //   0	143	146	java/lang/Exception
  }
}
