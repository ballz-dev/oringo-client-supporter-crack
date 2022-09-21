package me.oringo.oringoclient.qolfeatures.module.impl.other;

import com.mojang.realmsclient.gui.ChatFormatting;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import me.oringo.oringoclient.OringoClient;
import me.oringo.oringoclient.commands.CommandHandler;
import me.oringo.oringoclient.events.impl.PreAttackEvent;
import me.oringo.oringoclient.qolfeatures.module.Module;
import me.oringo.oringoclient.qolfeatures.module.impl.movement.Sneak;
import me.oringo.oringoclient.qolfeatures.module.impl.player.AutoHeal;
import me.oringo.oringoclient.qolfeatures.module.impl.player.NoRotate;
import me.oringo.oringoclient.qolfeatures.module.settings.Setting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.BooleanSetting;
import me.oringo.oringoclient.utils.Rotation;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class GuessTheBuildAFK extends Module {
  public BooleanSetting  = new BooleanSetting("AFK Mode", false);
  
  public int  = 3200;
  
  public ArrayList<String>  = new ArrayList<>();
  
  public Thread  = null;
  
  public long  = 0L;
  
  public int  = 0;
  
  public GuessTheBuildAFK() {
    super("Build Guesser", 0, Module.Category.OTHER);
    (new Setting[] { (Setting)this. });
  }
  
  @SubscribeEvent
  public void (GuiOpenEvent paramGuiOpenEvent) {
    if (!() || !this..())
      return; 
    (new Thread(paramGuiOpenEvent::)).start();
  }
  
  public static Rotation (Rotation paramRotation1, Rotation paramRotation2, float paramFloat) {
    return new Rotation(paramRotation1.() + MathHelper.func_76131_a(PreAttackEvent.(paramRotation2.(), paramRotation1.()), -paramFloat, paramFloat), paramRotation1.() + MathHelper.func_76131_a(PreAttackEvent.(paramRotation2.(), paramRotation1.()), -paramFloat, paramFloat));
  }
  
  @SubscribeEvent
  public void (ClientChatReceivedEvent paramClientChatReceivedEvent) {
    // Byte code:
    //   0: aload_0
    //   1: invokevirtual  : ()Z
    //   4: ifne -> 8
    //   7: return
    //   8: invokestatic func_71410_x : ()Lnet/minecraft/client/Minecraft;
    //   11: getfield field_71439_g : Lnet/minecraft/client/entity/EntityPlayerSP;
    //   14: invokevirtual func_96123_co : ()Lnet/minecraft/scoreboard/Scoreboard;
    //   17: iconst_0
    //   18: invokevirtual func_96539_a : (I)Lnet/minecraft/scoreboard/ScoreObjective;
    //   21: astore_2
    //   22: aload_2
    //   23: ifnonnull -> 42
    //   26: invokestatic func_71410_x : ()Lnet/minecraft/client/Minecraft;
    //   29: getfield field_71439_g : Lnet/minecraft/client/entity/EntityPlayerSP;
    //   32: invokevirtual func_96123_co : ()Lnet/minecraft/scoreboard/Scoreboard;
    //   35: iconst_1
    //   36: invokevirtual func_96539_a : (I)Lnet/minecraft/scoreboard/ScoreObjective;
    //   39: goto -> 43
    //   42: aload_2
    //   43: invokevirtual func_96678_d : ()Ljava/lang/String;
    //   46: invokestatic stripFormatting : (Ljava/lang/String;)Ljava/lang/String;
    //   49: ldc 'GUESS THE BUILD'
    //   51: invokevirtual contains : (Ljava/lang/CharSequence;)Z
    //   54: ifeq -> 88
    //   57: aload_1
    //   58: getfield message : Lnet/minecraft/util/IChatComponent;
    //   61: invokeinterface func_150254_d : ()Ljava/lang/String;
    //   66: invokestatic stripFormatting : (Ljava/lang/String;)Ljava/lang/String;
    //   69: ldc 'This game has been recorded'
    //   71: invokevirtual startsWith : (Ljava/lang/String;)Z
    //   74: ifeq -> 88
    //   77: invokestatic func_71410_x : ()Lnet/minecraft/client/Minecraft;
    //   80: getfield field_71439_g : Lnet/minecraft/client/entity/EntityPlayerSP;
    //   83: ldc '/play build_battle_guess_the_build'
    //   85: invokevirtual func_71165_d : (Ljava/lang/String;)V
    //   88: goto -> 92
    //   91: astore_2
    //   92: aload_1
    //   93: getfield message : Lnet/minecraft/util/IChatComponent;
    //   96: invokeinterface func_150254_d : ()Ljava/lang/String;
    //   101: ldc '§eThe theme was'
    //   103: invokevirtual startsWith : (Ljava/lang/String;)Z
    //   106: ifeq -> 128
    //   109: aload_0
    //   110: getfield  : Ljava/lang/Thread;
    //   113: ifnull -> 128
    //   116: aload_0
    //   117: getfield  : Ljava/lang/Thread;
    //   120: invokevirtual stop : ()V
    //   123: aload_0
    //   124: aconst_null
    //   125: putfield  : Ljava/lang/Thread;
    //   128: aload_1
    //   129: getfield message : Lnet/minecraft/util/IChatComponent;
    //   132: invokeinterface func_150254_d : ()Ljava/lang/String;
    //   137: invokestatic stripFormatting : (Ljava/lang/String;)Ljava/lang/String;
    //   140: new java/lang/StringBuilder
    //   143: dup
    //   144: invokespecial <init> : ()V
    //   147: invokestatic func_71410_x : ()Lnet/minecraft/client/Minecraft;
    //   150: getfield field_71439_g : Lnet/minecraft/client/entity/EntityPlayerSP;
    //   153: invokevirtual func_70005_c_ : ()Ljava/lang/String;
    //   156: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   159: ldc ' correctly guessed the theme!'
    //   161: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   164: invokestatic valueOf : (Ljava/lang/Object;)Ljava/lang/String;
    //   167: invokevirtual startsWith : (Ljava/lang/String;)Z
    //   170: ifeq -> 192
    //   173: aload_0
    //   174: getfield  : Ljava/lang/Thread;
    //   177: ifnull -> 192
    //   180: aload_0
    //   181: getfield  : Ljava/lang/Thread;
    //   184: invokevirtual stop : ()V
    //   187: aload_0
    //   188: aconst_null
    //   189: putfield  : Ljava/lang/Thread;
    //   192: aload_1
    //   193: getfield type : B
    //   196: iconst_2
    //   197: if_icmpne -> 514
    //   200: aload_1
    //   201: getfield message : Lnet/minecraft/util/IChatComponent;
    //   204: invokeinterface func_150254_d : ()Ljava/lang/String;
    //   209: ldc 'The theme is'
    //   211: invokevirtual contains : (Ljava/lang/CharSequence;)Z
    //   214: ifeq -> 509
    //   217: aload_1
    //   218: getfield message : Lnet/minecraft/util/IChatComponent;
    //   221: invokeinterface func_150254_d : ()Ljava/lang/String;
    //   226: ldc '_'
    //   228: invokevirtual contains : (Ljava/lang/CharSequence;)Z
    //   231: ifeq -> 509
    //   234: aload_0
    //   235: getfield  : Ljava/util/ArrayList;
    //   238: invokevirtual isEmpty : ()Z
    //   241: ifeq -> 248
    //   244: aload_0
    //   245: invokespecial  : ()V
    //   248: aload_0
    //   249: aload_1
    //   250: getfield message : Lnet/minecraft/util/IChatComponent;
    //   253: invokeinterface func_150254_d : ()Ljava/lang/String;
    //   258: invokespecial  : (Ljava/lang/String;)I
    //   261: istore_2
    //   262: iload_2
    //   263: aload_0
    //   264: getfield  : I
    //   267: if_icmpeq -> 506
    //   270: aload_0
    //   271: iload_2
    //   272: putfield  : I
    //   275: aload_1
    //   276: getfield message : Lnet/minecraft/util/IChatComponent;
    //   279: invokeinterface func_150254_d : ()Ljava/lang/String;
    //   284: invokestatic stripFormatting : (Ljava/lang/String;)Ljava/lang/String;
    //   287: ldc 'The theme is '
    //   289: ldc ''
    //   291: invokevirtual replaceFirst : (Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   294: astore_3
    //   295: aload_0
    //   296: aload_3
    //   297: invokevirtual  : (Ljava/lang/String;)Ljava/util/ArrayList;
    //   300: astore #4
    //   302: aload #4
    //   304: invokevirtual size : ()I
    //   307: iconst_1
    //   308: if_icmpne -> 425
    //   311: ldc 'Oringo Client'
    //   313: new java/lang/StringBuilder
    //   316: dup
    //   317: invokespecial <init> : ()V
    //   320: ldc 'Found 1 matching word! Sending: §f'
    //   322: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   325: aload #4
    //   327: iconst_0
    //   328: invokevirtual get : (I)Ljava/lang/Object;
    //   331: checkcast java/lang/String
    //   334: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   337: invokestatic valueOf : (Ljava/lang/Object;)Ljava/lang/String;
    //   340: sipush #2000
    //   343: invokestatic  : (Ljava/lang/String;Ljava/lang/String;I)V
    //   346: aload_0
    //   347: getfield  : Ljava/lang/Thread;
    //   350: ifnull -> 384
    //   353: aload_0
    //   354: getfield  : Ljava/lang/Thread;
    //   357: invokevirtual stop : ()V
    //   360: aload_0
    //   361: aconst_null
    //   362: putfield  : Ljava/lang/Thread;
    //   365: new java/lang/Thread
    //   368: dup
    //   369: aload_0
    //   370: aload #4
    //   372: <illegal opcode> run : (Lme/oringo/oringoclient/qolfeatures/module/impl/other/GuessTheBuildAFK;Ljava/util/ArrayList;)Ljava/lang/Runnable;
    //   377: invokespecial <init> : (Ljava/lang/Runnable;)V
    //   380: invokevirtual start : ()V
    //   383: return
    //   384: invokestatic func_71410_x : ()Lnet/minecraft/client/Minecraft;
    //   387: getfield field_71439_g : Lnet/minecraft/client/entity/EntityPlayerSP;
    //   390: new java/lang/StringBuilder
    //   393: dup
    //   394: invokespecial <init> : ()V
    //   397: ldc_w '/ac '
    //   400: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   403: aload #4
    //   405: iconst_0
    //   406: invokevirtual get : (I)Ljava/lang/Object;
    //   409: checkcast java/lang/String
    //   412: invokevirtual toLowerCase : ()Ljava/lang/String;
    //   415: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   418: invokestatic valueOf : (Ljava/lang/Object;)Ljava/lang/String;
    //   421: invokevirtual func_71165_d : (Ljava/lang/String;)V
    //   424: return
    //   425: ldc 'Oringo Client'
    //   427: ldc_w 'Found %s matching words!'
    //   430: iconst_1
    //   431: anewarray java/lang/Object
    //   434: dup
    //   435: iconst_0
    //   436: aload #4
    //   438: invokevirtual size : ()I
    //   441: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   444: aastore
    //   445: invokestatic format : (Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
    //   448: sipush #1500
    //   451: invokestatic  : (Ljava/lang/String;Ljava/lang/String;I)V
    //   454: aload #4
    //   456: invokevirtual size : ()I
    //   459: bipush #15
    //   461: if_icmpgt -> 506
    //   464: aload #4
    //   466: invokestatic shuffle : (Ljava/util/List;)V
    //   469: aload_0
    //   470: getfield  : Ljava/lang/Thread;
    //   473: ifnull -> 483
    //   476: aload_0
    //   477: getfield  : Ljava/lang/Thread;
    //   480: invokevirtual stop : ()V
    //   483: aload_0
    //   484: new java/lang/Thread
    //   487: dup
    //   488: aload_0
    //   489: aload #4
    //   491: <illegal opcode> run : (Lme/oringo/oringoclient/qolfeatures/module/impl/other/GuessTheBuildAFK;Ljava/util/ArrayList;)Ljava/lang/Runnable;
    //   496: invokespecial <init> : (Ljava/lang/Runnable;)V
    //   499: dup_x1
    //   500: putfield  : Ljava/lang/Thread;
    //   503: invokevirtual start : ()V
    //   506: goto -> 514
    //   509: aload_0
    //   510: iconst_0
    //   511: putfield  : I
    //   514: return
    // Exception table:
    //   from	to	target	type
    //   8	88	91	java/lang/Exception
  }
  
  public static MovingObjectPosition (Rotation paramRotation) {
    return CommandHandler.(paramRotation.(), paramRotation.());
  }
  
  public void () {
    try {
      BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(OringoClient.class.getClassLoader().getResourceAsStream("words.txt")));
      String str;
      while ((str = bufferedReader.readLine()) != null)
        this..add(str); 
      bufferedReader.close();
    } catch (Exception exception) {
      exception.printStackTrace();
      Sneak.("Couldn't load word list!");
    } 
  }
  
  public ArrayList<String> (String paramString) {
    ArrayList<String> arrayList = new ArrayList();
    for (String str : this.) {
      if (str.length() == paramString.length()) {
        boolean bool = true;
        for (byte b = 0; b < str.length(); b++) {
          if (paramString.charAt(b) == '_')
            if (str.charAt(b) == ' ') {
              bool = false;
            } else {
              continue;
            }  
          if (paramString.charAt(b) != str.charAt(b))
            bool = false; 
          if (!bool)
            break; 
          continue;
        } 
        if (bool)
          arrayList.add(str); 
      } 
    } 
    return arrayList;
  }
  
  public int (String paramString) {
    return paramString.replaceAll(" ", "").replaceAll("_", "").length();
  }
}
