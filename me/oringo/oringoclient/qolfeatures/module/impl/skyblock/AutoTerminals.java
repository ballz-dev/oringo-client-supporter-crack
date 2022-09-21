package me.oringo.oringoclient.qolfeatures.module.impl.skyblock;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import me.oringo.oringoclient.events.impl.MotionUpdateEvent;
import me.oringo.oringoclient.events.impl.PacketReceivedEvent;
import me.oringo.oringoclient.qolfeatures.module.Module;
import me.oringo.oringoclient.qolfeatures.module.impl.render.HidePlayers;
import me.oringo.oringoclient.qolfeatures.module.settings.Setting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.BooleanSetting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.ModeSetting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.NumberSetting;
import me.oringo.oringoclient.ui.hud.impl.TargetComponent;
import me.oringo.oringoclient.utils.MilliTimer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class AutoTerminals extends Module {
  public static MilliTimer ;
  
  public static ModeSetting  = new ModeSetting("Mode", "Pingless", new String[] { "Pingless", "Vanilla" });
  
  public static TerminalType ;
  
  public static int[] ;
  
  public static NumberSetting  = new NumberSetting("Click Delay", 110.0D, 0.0D, 500.0D, 5.0D);
  
  public static BooleanSetting ;
  
  public static List<ClickData> ;
  
  public static MilliTimer ;
  
  public static Pattern ;
  
  public static NumberSetting  = new NumberSetting("Rescan Delay", 250.0D, 0.0D, 1000.0D, 5.0D);
  
  public static List<EnumDyeColor> ;
  
  public static void (double paramDouble1, double paramDouble2, double paramDouble3) {
    ScaledResolution scaledResolution = new ScaledResolution(TargetComponent.mc);
    if (TargetComponent..(550L)) {
      HidePlayers.(paramDouble1, paramDouble2, (750L - TargetComponent..()) / 200.0D);
    } else if (!TargetComponent..(200L)) {
      HidePlayers.(paramDouble1, paramDouble2, TargetComponent..() / 200.0D * (1.0D - paramDouble3 + paramDouble3));
    } 
  }
  
  public TerminalType () {
    if (mc.field_71439_g.field_71070_bA instanceof ContainerChest) {
      ContainerChest containerChest = (ContainerChest)mc.field_71439_g.field_71070_bA;
      for (byte b = 0; b < containerChest.func_85151_d().func_70302_i_(); b++) {
        if (!containerChest.func_75139_a(b).func_75216_d())
          return TerminalType.; 
      } 
      String str = containerChest.func_85151_d().func_145748_c_().func_150260_c();
      for (TerminalType terminalType : TerminalType.values()) {
        if (terminalType.is(str))
          return terminalType; 
      } 
    } 
    return TerminalType.;
  }
  
  @SubscribeEvent
  public void (MotionUpdateEvent.Post paramPost) {
    if (!())
      return; 
    if ( != () || .(.())) {
       = ();
      ();
    } 
    if ( == TerminalType. && .()) {
      ContainerChest containerChest = (ContainerChest)mc.field_71439_g.field_71070_bA;
      for (byte b = 16; b < 44; b += 9) {
        ItemStack itemStack = containerChest.func_75139_a(b).func_75211_c();
        if (itemStack.func_82833_r().contains("Lock In Slot")) {
          for (byte b1 = 1; b1 < 6; b1++) {
            ItemStack itemStack1 = containerChest.func_75139_a(b1).func_75211_c();
            if (AutoS1.(itemStack1) == EnumDyeColor.PURPLE && AutoS1.(containerChest.func_75139_a(b - 7 - b1).func_75211_c()) == EnumDyeColor.LIME) {
              mc.field_71442_b.func_78753_a(mc.field_71439_g.field_71070_bA.field_75152_c, b, 0, 3, (EntityPlayer)mc.field_71439_g);
              return;
            } 
          } 
          break;
        } 
      } 
      return;
    } 
    while (!.isEmpty() && .(.(), true))
      ((ClickData).remove(0)).(); 
  }
  
  static {
     = new BooleanSetting("Melody Terminal", true);
     = new MilliTimer();
     = new MilliTimer();
     = TerminalType.;
     = new ArrayList<>();
     = Pattern.compile("^What starts with: ['\"](.+)['\"]\\?$");
     = new int[] { -1, 1, -9, 9 };
     = Lists.newArrayList((Object[])new EnumDyeColor[] { EnumDyeColor.RED, EnumDyeColor.ORANGE, EnumDyeColor.YELLOW, EnumDyeColor.GREEN, EnumDyeColor.BLUE });
  }
  
  public AutoTerminals() {
    super("Auto Terminals", Module.Category.);
    (new Setting[] { (Setting), (Setting), (Setting), (Setting) });
  }
  
  public void () {
    // Byte code:
    //   0: getstatic me/oringo/oringoclient/qolfeatures/module/impl/skyblock/AutoTerminals. : Ljava/util/List;
    //   3: invokeinterface clear : ()V
    //   8: getstatic me/oringo/oringoclient/qolfeatures/module/impl/skyblock/AutoTerminals. : Lme/oringo/oringoclient/utils/MilliTimer;
    //   11: invokevirtual  : ()V
    //   14: getstatic me/oringo/oringoclient/qolfeatures/module/impl/skyblock/AutoTerminals. : Lme/oringo/oringoclient/qolfeatures/module/impl/skyblock/AutoTerminals$TerminalType;
    //   17: getstatic me/oringo/oringoclient/qolfeatures/module/impl/skyblock/AutoTerminals$TerminalType. : Lme/oringo/oringoclient/qolfeatures/module/impl/skyblock/AutoTerminals$TerminalType;
    //   20: if_acmpeq -> 1035
    //   23: getstatic me/oringo/oringoclient/qolfeatures/module/impl/skyblock/AutoTerminals.mc : Lnet/minecraft/client/Minecraft;
    //   26: getfield field_71439_g : Lnet/minecraft/client/entity/EntityPlayerSP;
    //   29: getfield field_71070_bA : Lnet/minecraft/inventory/Container;
    //   32: checkcast net/minecraft/inventory/ContainerChest
    //   35: astore_1
    //   36: getstatic me/oringo/oringoclient/qolfeatures/module/impl/skyblock/AutoTerminals.mc : Lnet/minecraft/client/Minecraft;
    //   39: getfield field_71439_g : Lnet/minecraft/client/entity/EntityPlayerSP;
    //   42: getfield field_71070_bA : Lnet/minecraft/inventory/Container;
    //   45: getfield field_75152_c : I
    //   48: istore_2
    //   49: getstatic me/oringo/oringoclient/qolfeatures/module/impl/skyblock/AutoTerminals$1. : [I
    //   52: getstatic me/oringo/oringoclient/qolfeatures/module/impl/skyblock/AutoTerminals. : Lme/oringo/oringoclient/qolfeatures/module/impl/skyblock/AutoTerminals$TerminalType;
    //   55: invokevirtual ordinal : ()I
    //   58: iaload
    //   59: tableswitch default -> 1035, 1 -> 96, 2 -> 181, 3 -> 317, 4 -> 379, 5 -> 555, 6 -> 897
    //   96: bipush #49
    //   98: istore_3
    //   99: iconst_4
    //   100: istore #5
    //   102: iload_3
    //   103: iload #5
    //   105: if_icmpeq -> 1035
    //   108: iload_3
    //   109: istore #4
    //   111: iload_3
    //   112: iload #4
    //   114: invokestatic  : (II)I
    //   117: istore_3
    //   118: iload_3
    //   119: iconst_m1
    //   120: if_icmpne -> 138
    //   123: ldc_w 'Unable to find maze solution! Please report this!'
    //   126: invokestatic  : (Ljava/lang/String;)V
    //   129: getstatic me/oringo/oringoclient/qolfeatures/module/impl/skyblock/AutoTerminals. : Ljava/util/List;
    //   132: invokeinterface clear : ()V
    //   137: return
    //   138: aload_1
    //   139: iload_3
    //   140: invokevirtual func_75139_a : (I)Lnet/minecraft/inventory/Slot;
    //   143: invokevirtual func_75211_c : ()Lnet/minecraft/item/ItemStack;
    //   146: invokestatic  : (Lnet/minecraft/item/ItemStack;)Lnet/minecraft/item/EnumDyeColor;
    //   149: getstatic net/minecraft/item/EnumDyeColor.WHITE : Lnet/minecraft/item/EnumDyeColor;
    //   152: if_acmpne -> 102
    //   155: getstatic me/oringo/oringoclient/qolfeatures/module/impl/skyblock/AutoTerminals. : Ljava/util/List;
    //   158: new me/oringo/oringoclient/qolfeatures/module/impl/skyblock/AutoTerminals$ClickData
    //   161: dup
    //   162: iload_2
    //   163: iload_3
    //   164: invokespecial <init> : (II)V
    //   167: invokeinterface add : (Ljava/lang/Object;)Z
    //   172: pop
    //   173: iload_2
    //   174: invokestatic  : (I)I
    //   177: istore_2
    //   178: goto -> 102
    //   181: getstatic me/oringo/oringoclient/qolfeatures/module/impl/skyblock/AutoTerminals. : Ljava/util/regex/Pattern;
    //   184: aload_1
    //   185: invokevirtual func_85151_d : ()Lnet/minecraft/inventory/IInventory;
    //   188: invokeinterface func_70005_c_ : ()Ljava/lang/String;
    //   193: invokevirtual matcher : (Ljava/lang/CharSequence;)Ljava/util/regex/Matcher;
    //   196: astore #6
    //   198: aload #6
    //   200: invokevirtual find : ()Z
    //   203: ifeq -> 1035
    //   206: aload #6
    //   208: iconst_1
    //   209: invokevirtual group : (I)Ljava/lang/String;
    //   212: astore #7
    //   214: bipush #9
    //   216: istore #8
    //   218: iload #8
    //   220: bipush #45
    //   222: if_icmpge -> 314
    //   225: iload #8
    //   227: bipush #9
    //   229: irem
    //   230: ifeq -> 308
    //   233: iload #8
    //   235: bipush #9
    //   237: irem
    //   238: bipush #8
    //   240: if_icmpne -> 246
    //   243: goto -> 308
    //   246: aload_1
    //   247: iload #8
    //   249: invokevirtual func_75139_a : (I)Lnet/minecraft/inventory/Slot;
    //   252: invokevirtual func_75211_c : ()Lnet/minecraft/item/ItemStack;
    //   255: astore #9
    //   257: aload #9
    //   259: invokevirtual func_77948_v : ()Z
    //   262: ifeq -> 268
    //   265: goto -> 308
    //   268: aload #9
    //   270: invokevirtual func_82833_r : ()Ljava/lang/String;
    //   273: invokestatic stripFormatting : (Ljava/lang/String;)Ljava/lang/String;
    //   276: aload #7
    //   278: invokevirtual startsWith : (Ljava/lang/String;)Z
    //   281: ifeq -> 308
    //   284: getstatic me/oringo/oringoclient/qolfeatures/module/impl/skyblock/AutoTerminals. : Ljava/util/List;
    //   287: new me/oringo/oringoclient/qolfeatures/module/impl/skyblock/AutoTerminals$ClickData
    //   290: dup
    //   291: iload_2
    //   292: iload #8
    //   294: invokespecial <init> : (II)V
    //   297: invokeinterface add : (Ljava/lang/Object;)Z
    //   302: pop
    //   303: iload_2
    //   304: invokestatic  : (I)I
    //   307: istore_2
    //   308: iinc #8, 1
    //   311: goto -> 218
    //   314: goto -> 1035
    //   317: bipush #11
    //   319: istore #7
    //   321: iload #7
    //   323: bipush #34
    //   325: if_icmpge -> 376
    //   328: aload_1
    //   329: iload #7
    //   331: invokevirtual func_75139_a : (I)Lnet/minecraft/inventory/Slot;
    //   334: invokevirtual func_75211_c : ()Lnet/minecraft/item/ItemStack;
    //   337: invokestatic  : (Lnet/minecraft/item/ItemStack;)Lnet/minecraft/item/EnumDyeColor;
    //   340: getstatic net/minecraft/item/EnumDyeColor.RED : Lnet/minecraft/item/EnumDyeColor;
    //   343: if_acmpne -> 370
    //   346: getstatic me/oringo/oringoclient/qolfeatures/module/impl/skyblock/AutoTerminals. : Ljava/util/List;
    //   349: new me/oringo/oringoclient/qolfeatures/module/impl/skyblock/AutoTerminals$ClickData
    //   352: dup
    //   353: iload_2
    //   354: iload #7
    //   356: invokespecial <init> : (II)V
    //   359: invokeinterface add : (Ljava/lang/Object;)Z
    //   364: pop
    //   365: iload_2
    //   366: invokestatic  : (I)I
    //   369: istore_2
    //   370: iinc #7, 1
    //   373: goto -> 321
    //   376: goto -> 1035
    //   379: getstatic net/minecraft/item/EnumDyeColor.WHITE : Lnet/minecraft/item/EnumDyeColor;
    //   382: astore #7
    //   384: invokestatic values : ()[Lnet/minecraft/item/EnumDyeColor;
    //   387: astore #8
    //   389: aload #8
    //   391: arraylength
    //   392: istore #9
    //   394: iconst_0
    //   395: istore #10
    //   397: iload #10
    //   399: iload #9
    //   401: if_icmpge -> 452
    //   404: aload #8
    //   406: iload #10
    //   408: aaload
    //   409: astore #11
    //   411: aload_1
    //   412: invokevirtual func_85151_d : ()Lnet/minecraft/inventory/IInventory;
    //   415: invokeinterface func_145748_c_ : ()Lnet/minecraft/util/IChatComponent;
    //   420: invokeinterface func_150260_c : ()Ljava/lang/String;
    //   425: aload #11
    //   427: invokevirtual func_176610_l : ()Ljava/lang/String;
    //   430: invokevirtual toUpperCase : ()Ljava/lang/String;
    //   433: invokevirtual contains : (Ljava/lang/CharSequence;)Z
    //   436: ifeq -> 446
    //   439: aload #11
    //   441: astore #7
    //   443: goto -> 452
    //   446: iinc #10, 1
    //   449: goto -> 397
    //   452: bipush #9
    //   454: istore #8
    //   456: iload #8
    //   458: bipush #45
    //   460: if_icmpge -> 552
    //   463: iload #8
    //   465: bipush #9
    //   467: irem
    //   468: ifeq -> 546
    //   471: iload #8
    //   473: bipush #9
    //   475: irem
    //   476: bipush #8
    //   478: if_icmpne -> 484
    //   481: goto -> 546
    //   484: aload_1
    //   485: iload #8
    //   487: invokevirtual func_75139_a : (I)Lnet/minecraft/inventory/Slot;
    //   490: invokevirtual func_75211_c : ()Lnet/minecraft/item/ItemStack;
    //   493: astore #9
    //   495: aload #9
    //   497: invokevirtual func_77948_v : ()Z
    //   500: ifeq -> 506
    //   503: goto -> 546
    //   506: aload #9
    //   508: invokevirtual func_77977_a : ()Ljava/lang/String;
    //   511: aload #7
    //   513: invokevirtual func_176762_d : ()Ljava/lang/String;
    //   516: invokevirtual contains : (Ljava/lang/CharSequence;)Z
    //   519: ifeq -> 546
    //   522: getstatic me/oringo/oringoclient/qolfeatures/module/impl/skyblock/AutoTerminals. : Ljava/util/List;
    //   525: new me/oringo/oringoclient/qolfeatures/module/impl/skyblock/AutoTerminals$ClickData
    //   528: dup
    //   529: iload_2
    //   530: iload #8
    //   532: invokespecial <init> : (II)V
    //   535: invokeinterface add : (Ljava/lang/Object;)Z
    //   540: pop
    //   541: iload_2
    //   542: invokestatic  : (I)I
    //   545: istore_2
    //   546: iinc #8, 1
    //   549: goto -> 456
    //   552: goto -> 1035
    //   555: new java/util/ArrayList
    //   558: dup
    //   559: invokespecial <init> : ()V
    //   562: astore #8
    //   564: bipush #12
    //   566: istore #9
    //   568: iload #9
    //   570: bipush #33
    //   572: if_icmpge -> 621
    //   575: aload_1
    //   576: invokevirtual func_85151_d : ()Lnet/minecraft/inventory/IInventory;
    //   579: iload #9
    //   581: invokeinterface func_70301_a : (I)Lnet/minecraft/item/ItemStack;
    //   586: astore #10
    //   588: aload #10
    //   590: invokevirtual func_82833_r : ()Ljava/lang/String;
    //   593: ldc_w '§a'
    //   596: invokevirtual startsWith : (Ljava/lang/String;)Z
    //   599: ifeq -> 615
    //   602: aload #8
    //   604: iload #9
    //   606: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   609: invokeinterface add : (Ljava/lang/Object;)Z
    //   614: pop
    //   615: iinc #9, 1
    //   618: goto -> 568
    //   621: getstatic me/oringo/oringoclient/qolfeatures/module/impl/skyblock/AutoTerminals. : Ljava/util/List;
    //   624: invokeinterface stream : ()Ljava/util/stream/Stream;
    //   629: aload #8
    //   631: aload_1
    //   632: <illegal opcode> applyAsInt : (Ljava/util/List;Lnet/minecraft/inventory/ContainerChest;)Ljava/util/function/ToIntFunction;
    //   637: invokestatic comparingInt : (Ljava/util/function/ToIntFunction;)Ljava/util/Comparator;
    //   640: invokeinterface max : (Ljava/util/Comparator;)Ljava/util/Optional;
    //   645: astore #9
    //   647: aload #9
    //   649: invokevirtual isPresent : ()Z
    //   652: ifeq -> 1035
    //   655: aload #9
    //   657: invokevirtual get : ()Ljava/lang/Object;
    //   660: checkcast net/minecraft/item/EnumDyeColor
    //   663: astore #10
    //   665: getstatic me/oringo/oringoclient/qolfeatures/module/impl/skyblock/AutoTerminals. : Ljava/util/List;
    //   668: aload #10
    //   670: invokeinterface indexOf : (Ljava/lang/Object;)I
    //   675: istore #11
    //   677: aload #8
    //   679: invokeinterface iterator : ()Ljava/util/Iterator;
    //   684: astore #12
    //   686: aload #12
    //   688: invokeinterface hasNext : ()Z
    //   693: ifeq -> 894
    //   696: aload #12
    //   698: invokeinterface next : ()Ljava/lang/Object;
    //   703: checkcast java/lang/Integer
    //   706: invokevirtual intValue : ()I
    //   709: istore #13
    //   711: aload_1
    //   712: invokevirtual func_85151_d : ()Lnet/minecraft/inventory/IInventory;
    //   715: iload #13
    //   717: invokeinterface func_70301_a : (I)Lnet/minecraft/item/ItemStack;
    //   722: astore #14
    //   724: aload #14
    //   726: invokestatic  : (Lnet/minecraft/item/ItemStack;)Lnet/minecraft/item/EnumDyeColor;
    //   729: aload #10
    //   731: if_acmpne -> 737
    //   734: goto -> 686
    //   737: getstatic me/oringo/oringoclient/qolfeatures/module/impl/skyblock/AutoTerminals. : Ljava/util/List;
    //   740: aload #14
    //   742: invokestatic  : (Lnet/minecraft/item/ItemStack;)Lnet/minecraft/item/EnumDyeColor;
    //   745: invokeinterface indexOf : (Ljava/lang/Object;)I
    //   750: istore #15
    //   752: iload #11
    //   754: iload #15
    //   756: isub
    //   757: getstatic me/oringo/oringoclient/qolfeatures/module/impl/skyblock/AutoTerminals. : Ljava/util/List;
    //   760: invokeinterface size : ()I
    //   765: irem
    //   766: getstatic me/oringo/oringoclient/qolfeatures/module/impl/skyblock/AutoTerminals. : Ljava/util/List;
    //   769: invokeinterface size : ()I
    //   774: iadd
    //   775: getstatic me/oringo/oringoclient/qolfeatures/module/impl/skyblock/AutoTerminals. : Ljava/util/List;
    //   778: invokeinterface size : ()I
    //   783: irem
    //   784: istore #16
    //   786: iload #15
    //   788: iload #11
    //   790: isub
    //   791: getstatic me/oringo/oringoclient/qolfeatures/module/impl/skyblock/AutoTerminals. : Ljava/util/List;
    //   794: invokeinterface size : ()I
    //   799: irem
    //   800: getstatic me/oringo/oringoclient/qolfeatures/module/impl/skyblock/AutoTerminals. : Ljava/util/List;
    //   803: invokeinterface size : ()I
    //   808: iadd
    //   809: getstatic me/oringo/oringoclient/qolfeatures/module/impl/skyblock/AutoTerminals. : Ljava/util/List;
    //   812: invokeinterface size : ()I
    //   817: irem
    //   818: ineg
    //   819: istore #17
    //   821: iload #16
    //   823: iload #17
    //   825: ineg
    //   826: if_icmple -> 834
    //   829: iload #17
    //   831: goto -> 836
    //   834: iload #16
    //   836: istore #18
    //   838: iconst_0
    //   839: istore #19
    //   841: iload #19
    //   843: iload #18
    //   845: invokestatic abs : (I)I
    //   848: if_icmpge -> 891
    //   851: getstatic me/oringo/oringoclient/qolfeatures/module/impl/skyblock/AutoTerminals. : Ljava/util/List;
    //   854: new me/oringo/oringoclient/qolfeatures/module/impl/skyblock/AutoTerminals$ClickData
    //   857: dup
    //   858: iload_2
    //   859: iload #13
    //   861: iload #18
    //   863: ifle -> 870
    //   866: iconst_0
    //   867: goto -> 871
    //   870: iconst_1
    //   871: invokespecial <init> : (III)V
    //   874: invokeinterface add : (Ljava/lang/Object;)Z
    //   879: pop
    //   880: iload_2
    //   881: invokestatic  : (I)I
    //   884: istore_2
    //   885: iinc #19, 1
    //   888: goto -> 841
    //   891: goto -> 686
    //   894: goto -> 1035
    //   897: new java/util/ArrayList
    //   900: dup
    //   901: invokespecial <init> : ()V
    //   904: astore #10
    //   906: bipush #10
    //   908: istore #11
    //   910: iload #11
    //   912: bipush #26
    //   914: if_icmpge -> 958
    //   917: aload_1
    //   918: iload #11
    //   920: invokevirtual func_75139_a : (I)Lnet/minecraft/inventory/Slot;
    //   923: invokevirtual func_75211_c : ()Lnet/minecraft/item/ItemStack;
    //   926: astore #12
    //   928: aload #12
    //   930: invokestatic  : (Lnet/minecraft/item/ItemStack;)Lnet/minecraft/item/EnumDyeColor;
    //   933: getstatic net/minecraft/item/EnumDyeColor.RED : Lnet/minecraft/item/EnumDyeColor;
    //   936: if_acmpne -> 952
    //   939: aload #10
    //   941: iload #11
    //   943: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   946: invokeinterface add : (Ljava/lang/Object;)Z
    //   951: pop
    //   952: iinc #11, 1
    //   955: goto -> 910
    //   958: aload #10
    //   960: aload_1
    //   961: <illegal opcode> applyAsInt : (Lnet/minecraft/inventory/ContainerChest;)Ljava/util/function/ToIntFunction;
    //   966: invokestatic comparingInt : (Ljava/util/function/ToIntFunction;)Ljava/util/Comparator;
    //   969: invokeinterface sort : (Ljava/util/Comparator;)V
    //   974: aload #10
    //   976: invokeinterface iterator : ()Ljava/util/Iterator;
    //   981: astore #11
    //   983: aload #11
    //   985: invokeinterface hasNext : ()Z
    //   990: ifeq -> 1035
    //   993: aload #11
    //   995: invokeinterface next : ()Ljava/lang/Object;
    //   1000: checkcast java/lang/Integer
    //   1003: invokevirtual intValue : ()I
    //   1006: istore #12
    //   1008: getstatic me/oringo/oringoclient/qolfeatures/module/impl/skyblock/AutoTerminals. : Ljava/util/List;
    //   1011: new me/oringo/oringoclient/qolfeatures/module/impl/skyblock/AutoTerminals$ClickData
    //   1014: dup
    //   1015: iload_2
    //   1016: iload #12
    //   1018: invokespecial <init> : (II)V
    //   1021: invokeinterface add : (Ljava/lang/Object;)Z
    //   1026: pop
    //   1027: iload_2
    //   1028: invokestatic  : (I)I
    //   1031: istore_2
    //   1032: goto -> 983
    //   1035: return
  }
  
  @SubscribeEvent
  public void (PacketReceivedEvent paramPacketReceivedEvent) {
    if (paramPacketReceivedEvent. instanceof net.minecraft.network.play.server.S2DPacketOpenWindow)
      .(); 
  }
  
  public enum TerminalType {
    ,
    ,
    ,
    ,
    ,
    ("Select all the"),
    ("Select all the"),
    ("Select all the");
    
    public String ;
    
    public boolean is(String param1String) {
      return param1String.contains(this.);
    }
    
    static {
       = new TerminalType("PANES", 4, "Correct all the panes!");
       = new TerminalType("PING", 5, "Click the button on time!");
       = new TerminalType("COLOR2", 6, "Change all to same color!");
       = new TerminalType("NONE", 7, "");
       = new TerminalType[] { , , , , , , ,  };
    }
    
    TerminalType(String param1String1) {
      this. = param1String1;
    }
  }
  
  public static class ClickData {
    public int ;
    
    public int ;
    
    public int ;
    
    public int ;
    
    public void () {
      (AutoTerminals.()).field_71442_b.func_78753_a(AutoTerminals..("Vanilla") ? (AutoTerminals.()).field_71439_g.field_71070_bA.field_75152_c : this., this., this., this., (EntityPlayer)(AutoTerminals.()).field_71439_g);
    }
    
    public ClickData(int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
      this. = param1Int3;
      this. = param1Int1;
      this. = param1Int4;
      this. = param1Int2;
    }
    
    public ClickData(int param1Int1, int param1Int2) {
      this(param1Int1, param1Int2, 3, 0);
    }
    
    public ClickData(int param1Int1, int param1Int2, int param1Int3) {
      this(param1Int1, param1Int2, 0, param1Int3);
    }
  }
}
