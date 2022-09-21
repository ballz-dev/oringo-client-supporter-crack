package me.oringo.oringoclient.qolfeatures.module.impl.skyblock;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import me.oringo.oringoclient.commands.impl.BloodSkipCommand;
import me.oringo.oringoclient.events.impl.MotionUpdateEvent;
import me.oringo.oringoclient.qolfeatures.module.Module;
import me.oringo.oringoclient.qolfeatures.module.settings.Setting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.NumberSetting;
import me.oringo.oringoclient.utils.MilliTimer;
import net.minecraft.block.Block;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3;
import net.minecraft.util.Vec3i;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class AutoBeams extends Module {
  public static EntityCreeper ;
  
  public static MilliTimer ;
  
  public static boolean ;
  
  public static Pair<BlockPos, BlockPos> ;
  
  public static NumberSetting ;
  
  public static NumberSetting  = new NumberSetting("Shoot delay", 250.0D, 50.0D, 2000.0D, 50.0D);
  
  public static List<Pair<BlockPos, BlockPos>> ;
  
  public static boolean ;
  
  public static MilliTimer ;
  
  public static int ;
  
  @SubscribeEvent
  public void (WorldEvent.Load paramLoad) {
    .clear();
     = 0;
     = null;
     = null;
     = false;
  }
  
  public BlockPos (Vec3 paramVec31, Vec3 paramVec32, int paramInt1, int paramInt2) {
    double d1 = paramVec32.field_72450_a - paramVec31.field_72450_a;
    double d2 = paramVec32.field_72448_b - paramVec31.field_72448_b;
    double d3 = paramVec32.field_72449_c - paramVec31.field_72449_c;
    for (int i = paramInt1; i < paramInt2 * paramInt1; i++) {
      double d4 = paramVec31.field_72450_a + d1 / paramInt1 * i;
      double d5 = paramVec31.field_72448_b + d2 / paramInt1 * i;
      double d6 = paramVec31.field_72449_c + d3 / paramInt1 * i;
      BlockPos blockPos = new BlockPos(d4, d5, d6);
      if (!mc.field_71441_e.func_180495_p(blockPos).func_177230_c().equals(Blocks.field_150350_a))
        return blockPos; 
    } 
    return null;
  }
  
  static {
     = new NumberSetting("Delay", 750.0D, 50.0D, 2000.0D, 50.0D);
     = new ArrayList<>();
     = new MilliTimer();
     = new MilliTimer();
  }
  
  @SubscribeEvent
  public void (MotionUpdateEvent paramMotionUpdateEvent) {
    // Byte code:
    //   0: aload_0
    //   1: invokevirtual  : ()Z
    //   4: ifeq -> 13
    //   7: getstatic me/oringo/oringoclient/utils/SkyblockUtils.inDungeon : Z
    //   10: ifne -> 14
    //   13: return
    //   14: aload_1
    //   15: invokevirtual isPre : ()Z
    //   18: ifeq -> 670
    //   21: getstatic me/oringo/oringoclient/qolfeatures/module/impl/skyblock/AutoBeams. : Z
    //   24: ifne -> 312
    //   27: getstatic me/oringo/oringoclient/qolfeatures/module/impl/skyblock/AutoBeams. : Lnet/minecraft/entity/monster/EntityCreeper;
    //   30: ifnonnull -> 75
    //   33: getstatic me/oringo/oringoclient/qolfeatures/module/impl/skyblock/AutoBeams.mc : Lnet/minecraft/client/Minecraft;
    //   36: getfield field_71441_e : Lnet/minecraft/client/multiplayer/WorldClient;
    //   39: ldc net/minecraft/entity/monster/EntityCreeper
    //   41: <illegal opcode> apply : ()Lcom/google/common/base/Predicate;
    //   46: invokevirtual func_175644_a : (Ljava/lang/Class;Lcom/google/common/base/Predicate;)Ljava/util/List;
    //   49: astore_2
    //   50: aload_2
    //   51: invokeinterface isEmpty : ()Z
    //   56: ifne -> 72
    //   59: aload_2
    //   60: iconst_0
    //   61: invokeinterface get : (I)Ljava/lang/Object;
    //   66: checkcast net/minecraft/entity/monster/EntityCreeper
    //   69: putstatic me/oringo/oringoclient/qolfeatures/module/impl/skyblock/AutoBeams. : Lnet/minecraft/entity/monster/EntityCreeper;
    //   72: goto -> 764
    //   75: getstatic me/oringo/oringoclient/qolfeatures/module/impl/skyblock/AutoBeams.mc : Lnet/minecraft/client/Minecraft;
    //   78: getfield field_71439_g : Lnet/minecraft/client/entity/EntityPlayerSP;
    //   81: getstatic me/oringo/oringoclient/qolfeatures/module/impl/skyblock/AutoBeams. : Lnet/minecraft/entity/monster/EntityCreeper;
    //   84: invokevirtual func_70068_e : (Lnet/minecraft/entity/Entity;)D
    //   87: ldc2_w 900.0
    //   90: dcmpg
    //   91: ifge -> 764
    //   94: getstatic me/oringo/oringoclient/qolfeatures/module/impl/skyblock/AutoBeams.mc : Lnet/minecraft/client/Minecraft;
    //   97: getfield field_71439_g : Lnet/minecraft/client/entity/EntityPlayerSP;
    //   100: getfield field_70173_aa : I
    //   103: bipush #20
    //   105: irem
    //   106: ifne -> 764
    //   109: new net/minecraft/util/BlockPos
    //   112: dup
    //   113: getstatic me/oringo/oringoclient/qolfeatures/module/impl/skyblock/AutoBeams. : Lnet/minecraft/entity/monster/EntityCreeper;
    //   116: invokevirtual func_174791_d : ()Lnet/minecraft/util/Vec3;
    //   119: invokespecial <init> : (Lnet/minecraft/util/Vec3;)V
    //   122: astore_2
    //   123: aload_2
    //   124: bipush #-14
    //   126: bipush #-8
    //   128: bipush #-13
    //   130: invokevirtual func_177982_a : (III)Lnet/minecraft/util/BlockPos;
    //   133: aload_2
    //   134: bipush #14
    //   136: bipush #10
    //   138: bipush #13
    //   140: invokevirtual func_177982_a : (III)Lnet/minecraft/util/BlockPos;
    //   143: invokestatic func_177980_a : (Lnet/minecraft/util/BlockPos;Lnet/minecraft/util/BlockPos;)Ljava/lang/Iterable;
    //   146: invokeinterface iterator : ()Ljava/util/Iterator;
    //   151: astore_3
    //   152: aload_3
    //   153: invokeinterface hasNext : ()Z
    //   158: ifeq -> 305
    //   161: aload_3
    //   162: invokeinterface next : ()Ljava/lang/Object;
    //   167: checkcast net/minecraft/util/BlockPos
    //   170: astore #4
    //   172: getstatic me/oringo/oringoclient/qolfeatures/module/impl/skyblock/AutoBeams.mc : Lnet/minecraft/client/Minecraft;
    //   175: getfield field_71441_e : Lnet/minecraft/client/multiplayer/WorldClient;
    //   178: aload #4
    //   180: invokevirtual func_180495_p : (Lnet/minecraft/util/BlockPos;)Lnet/minecraft/block/state/IBlockState;
    //   183: invokeinterface func_177230_c : ()Lnet/minecraft/block/Block;
    //   188: astore #5
    //   190: aload #5
    //   192: getstatic net/minecraft/init/Blocks.field_180398_cJ : Lnet/minecraft/block/Block;
    //   195: invokevirtual equals : (Ljava/lang/Object;)Z
    //   198: ifeq -> 302
    //   201: aload_0
    //   202: new net/minecraft/util/Vec3
    //   205: dup
    //   206: aload #4
    //   208: invokespecial <init> : (Lnet/minecraft/util/Vec3i;)V
    //   211: ldc2_w 0.5
    //   214: ldc2_w 0.5
    //   217: ldc2_w 0.5
    //   220: invokevirtual func_72441_c : (DDD)Lnet/minecraft/util/Vec3;
    //   223: getstatic me/oringo/oringoclient/qolfeatures/module/impl/skyblock/AutoBeams. : Lnet/minecraft/entity/monster/EntityCreeper;
    //   226: invokevirtual func_174791_d : ()Lnet/minecraft/util/Vec3;
    //   229: dconst_0
    //   230: dconst_1
    //   231: dconst_0
    //   232: invokevirtual func_72441_c : (DDD)Lnet/minecraft/util/Vec3;
    //   235: bipush #10
    //   237: bipush #20
    //   239: invokespecial  : (Lnet/minecraft/util/Vec3;Lnet/minecraft/util/Vec3;II)Lnet/minecraft/util/BlockPos;
    //   242: astore #6
    //   244: aload_0
    //   245: aload #6
    //   247: invokespecial  : (Lnet/minecraft/util/BlockPos;)Lnet/minecraft/util/BlockPos;
    //   250: astore #7
    //   252: aload #7
    //   254: ifnull -> 302
    //   257: getstatic me/oringo/oringoclient/qolfeatures/module/impl/skyblock/AutoBeams. : Ljava/util/List;
    //   260: invokeinterface stream : ()Ljava/util/stream/Stream;
    //   265: aload #7
    //   267: aload #4
    //   269: <illegal opcode> test : (Lnet/minecraft/util/BlockPos;Lnet/minecraft/util/BlockPos;)Ljava/util/function/Predicate;
    //   274: invokeinterface noneMatch : (Ljava/util/function/Predicate;)Z
    //   279: ifeq -> 302
    //   282: getstatic me/oringo/oringoclient/qolfeatures/module/impl/skyblock/AutoBeams. : Ljava/util/List;
    //   285: new me/oringo/oringoclient/qolfeatures/module/impl/skyblock/AutoBeams$Pair
    //   288: dup
    //   289: aload #4
    //   291: aload #7
    //   293: invokespecial <init> : (Ljava/lang/Object;Ljava/lang/Object;)V
    //   296: invokeinterface add : (Ljava/lang/Object;)Z
    //   301: pop
    //   302: goto -> 152
    //   305: iconst_1
    //   306: putstatic me/oringo/oringoclient/qolfeatures/module/impl/skyblock/AutoBeams. : Z
    //   309: goto -> 764
    //   312: getstatic me/oringo/oringoclient/qolfeatures/module/impl/skyblock/AutoBeams. : Ljava/util/List;
    //   315: invokeinterface isEmpty : ()Z
    //   320: ifne -> 764
    //   323: getstatic me/oringo/oringoclient/qolfeatures/module/impl/skyblock/AutoBeams. : Lme/oringo/oringoclient/utils/MilliTimer;
    //   326: getstatic me/oringo/oringoclient/qolfeatures/module/impl/skyblock/AutoBeams. : Lme/oringo/oringoclient/qolfeatures/module/settings/impl/NumberSetting;
    //   329: invokevirtual  : ()D
    //   332: invokevirtual  : (D)Z
    //   335: ifeq -> 764
    //   338: getstatic me/oringo/oringoclient/qolfeatures/module/impl/skyblock/AutoBeams. : Lme/oringo/oringoclient/utils/MilliTimer;
    //   341: getstatic me/oringo/oringoclient/qolfeatures/module/impl/skyblock/AutoBeams. : Lme/oringo/oringoclient/qolfeatures/module/settings/impl/NumberSetting;
    //   344: invokevirtual  : ()D
    //   347: invokevirtual  : (D)Z
    //   350: ifeq -> 764
    //   353: getstatic me/oringo/oringoclient/qolfeatures/module/impl/skyblock/AutoBeams.mc : Lnet/minecraft/client/Minecraft;
    //   356: getfield field_71441_e : Lnet/minecraft/client/multiplayer/WorldClient;
    //   359: getfield field_72996_f : Ljava/util/List;
    //   362: getstatic me/oringo/oringoclient/qolfeatures/module/impl/skyblock/AutoBeams. : Lnet/minecraft/entity/monster/EntityCreeper;
    //   365: invokeinterface contains : (Ljava/lang/Object;)Z
    //   370: ifeq -> 764
    //   373: getstatic me/oringo/oringoclient/qolfeatures/module/impl/skyblock/AutoBeams. : Lme/oringo/oringoclient/qolfeatures/module/impl/skyblock/AutoBeams$Pair;
    //   376: ifnonnull -> 453
    //   379: getstatic me/oringo/oringoclient/qolfeatures/module/impl/skyblock/AutoBeams. : Ljava/util/List;
    //   382: invokeinterface iterator : ()Ljava/util/Iterator;
    //   387: astore_2
    //   388: aload_2
    //   389: invokeinterface hasNext : ()Z
    //   394: ifeq -> 453
    //   397: aload_2
    //   398: invokeinterface next : ()Ljava/lang/Object;
    //   403: checkcast me/oringo/oringoclient/qolfeatures/module/impl/skyblock/AutoBeams$Pair
    //   406: astore_3
    //   407: aload_3
    //   408: invokevirtual getValue : ()Ljava/lang/Object;
    //   411: checkcast net/minecraft/util/BlockPos
    //   414: invokestatic  : (Lnet/minecraft/util/BlockPos;)Z
    //   417: ifeq -> 450
    //   420: aload_3
    //   421: invokevirtual getKey : ()Ljava/lang/Object;
    //   424: checkcast net/minecraft/util/BlockPos
    //   427: invokestatic  : (Lnet/minecraft/util/BlockPos;)Z
    //   430: ifeq -> 450
    //   433: getstatic me/oringo/oringoclient/qolfeatures/module/impl/skyblock/AutoBeams. : Ljava/util/List;
    //   436: aload_3
    //   437: invokeinterface remove : (Ljava/lang/Object;)Z
    //   442: pop
    //   443: aload_3
    //   444: putstatic me/oringo/oringoclient/qolfeatures/module/impl/skyblock/AutoBeams. : Lme/oringo/oringoclient/qolfeatures/module/impl/skyblock/AutoBeams$Pair;
    //   447: goto -> 453
    //   450: goto -> 388
    //   453: getstatic me/oringo/oringoclient/qolfeatures/module/impl/skyblock/AutoBeams. : Lme/oringo/oringoclient/qolfeatures/module/impl/skyblock/AutoBeams$Pair;
    //   456: ifnull -> 764
    //   459: getstatic me/oringo/oringoclient/qolfeatures/module/impl/skyblock/AutoBeams. : I
    //   462: ifne -> 477
    //   465: getstatic me/oringo/oringoclient/qolfeatures/module/impl/skyblock/AutoBeams. : Lme/oringo/oringoclient/qolfeatures/module/impl/skyblock/AutoBeams$Pair;
    //   468: invokevirtual getKey : ()Ljava/lang/Object;
    //   471: checkcast net/minecraft/util/BlockPos
    //   474: goto -> 486
    //   477: getstatic me/oringo/oringoclient/qolfeatures/module/impl/skyblock/AutoBeams. : Lme/oringo/oringoclient/qolfeatures/module/impl/skyblock/AutoBeams$Pair;
    //   480: invokevirtual getValue : ()Ljava/lang/Object;
    //   483: checkcast net/minecraft/util/BlockPos
    //   486: invokestatic  : (Lnet/minecraft/util/BlockPos;)Z
    //   489: ifeq -> 764
    //   492: new java/lang/StringBuilder
    //   495: dup
    //   496: invokespecial <init> : ()V
    //   499: getstatic me/oringo/oringoclient/qolfeatures/module/impl/skyblock/AutoBeams. : Lme/oringo/oringoclient/qolfeatures/module/impl/skyblock/AutoBeams$Pair;
    //   502: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   505: ldc_w ' '
    //   508: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   511: getstatic me/oringo/oringoclient/qolfeatures/module/impl/skyblock/AutoBeams. : Ljava/util/List;
    //   514: invokeinterface size : ()I
    //   519: invokevirtual append : (I)Ljava/lang/StringBuilder;
    //   522: invokestatic valueOf : (Ljava/lang/Object;)Ljava/lang/String;
    //   525: invokestatic  : (Ljava/lang/String;)V
    //   528: getstatic me/oringo/oringoclient/qolfeatures/module/impl/skyblock/AutoBeams. : I
    //   531: iconst_1
    //   532: iadd
    //   533: dup
    //   534: putstatic me/oringo/oringoclient/qolfeatures/module/impl/skyblock/AutoBeams. : I
    //   537: lookupswitch default -> 661, 1 -> 564, 2 -> 607
    //   564: aload_1
    //   565: new net/minecraft/util/Vec3
    //   568: dup
    //   569: getstatic me/oringo/oringoclient/qolfeatures/module/impl/skyblock/AutoBeams. : Lme/oringo/oringoclient/qolfeatures/module/impl/skyblock/AutoBeams$Pair;
    //   572: invokevirtual getKey : ()Ljava/lang/Object;
    //   575: checkcast net/minecraft/util/Vec3i
    //   578: invokespecial <init> : (Lnet/minecraft/util/Vec3i;)V
    //   581: ldc2_w 0.5
    //   584: ldc2_w 0.5
    //   587: ldc2_w 0.5
    //   590: invokevirtual func_72441_c : (DDD)Lnet/minecraft/util/Vec3;
    //   593: invokestatic  : (Lnet/minecraft/util/Vec3;)Lme/oringo/oringoclient/utils/Rotation;
    //   596: invokevirtual setRotation : (Lme/oringo/oringoclient/utils/Rotation;)Lme/oringo/oringoclient/events/impl/MotionUpdateEvent;
    //   599: pop
    //   600: iconst_1
    //   601: putstatic me/oringo/oringoclient/qolfeatures/module/impl/skyblock/AutoBeams. : Z
    //   604: goto -> 661
    //   607: aload_1
    //   608: new net/minecraft/util/Vec3
    //   611: dup
    //   612: getstatic me/oringo/oringoclient/qolfeatures/module/impl/skyblock/AutoBeams. : Lme/oringo/oringoclient/qolfeatures/module/impl/skyblock/AutoBeams$Pair;
    //   615: invokevirtual getValue : ()Ljava/lang/Object;
    //   618: checkcast net/minecraft/util/Vec3i
    //   621: invokespecial <init> : (Lnet/minecraft/util/Vec3i;)V
    //   624: ldc2_w 0.5
    //   627: ldc2_w 0.5
    //   630: ldc2_w 0.5
    //   633: invokevirtual func_72441_c : (DDD)Lnet/minecraft/util/Vec3;
    //   636: invokestatic  : (Lnet/minecraft/util/Vec3;)Lme/oringo/oringoclient/utils/Rotation;
    //   639: invokevirtual setRotation : (Lme/oringo/oringoclient/utils/Rotation;)Lme/oringo/oringoclient/events/impl/MotionUpdateEvent;
    //   642: pop
    //   643: aconst_null
    //   644: putstatic me/oringo/oringoclient/qolfeatures/module/impl/skyblock/AutoBeams. : Lme/oringo/oringoclient/qolfeatures/module/impl/skyblock/AutoBeams$Pair;
    //   647: iconst_0
    //   648: putstatic me/oringo/oringoclient/qolfeatures/module/impl/skyblock/AutoBeams. : I
    //   651: iconst_1
    //   652: putstatic me/oringo/oringoclient/qolfeatures/module/impl/skyblock/AutoBeams. : Z
    //   655: getstatic me/oringo/oringoclient/qolfeatures/module/impl/skyblock/AutoBeams. : Lme/oringo/oringoclient/utils/MilliTimer;
    //   658: invokevirtual  : ()V
    //   661: getstatic me/oringo/oringoclient/qolfeatures/module/impl/skyblock/AutoBeams. : Lme/oringo/oringoclient/utils/MilliTimer;
    //   664: invokevirtual  : ()V
    //   667: goto -> 764
    //   670: getstatic me/oringo/oringoclient/qolfeatures/module/impl/skyblock/AutoBeams. : Z
    //   673: ifeq -> 764
    //   676: <illegal opcode> test : ()Ljava/util/function/Predicate;
    //   681: invokestatic  : (Ljava/util/function/Predicate;)I
    //   684: istore_2
    //   685: iload_2
    //   686: iconst_m1
    //   687: if_icmpeq -> 760
    //   690: getstatic me/oringo/oringoclient/qolfeatures/module/impl/skyblock/AutoBeams.mc : Lnet/minecraft/client/Minecraft;
    //   693: invokevirtual func_147114_u : ()Lnet/minecraft/client/network/NetHandlerPlayClient;
    //   696: invokevirtual func_147298_b : ()Lnet/minecraft/network/NetworkManager;
    //   699: new net/minecraft/network/play/client/C09PacketHeldItemChange
    //   702: dup
    //   703: iload_2
    //   704: invokespecial <init> : (I)V
    //   707: invokevirtual func_179290_a : (Lnet/minecraft/network/Packet;)V
    //   710: getstatic me/oringo/oringoclient/qolfeatures/module/impl/skyblock/AutoBeams.mc : Lnet/minecraft/client/Minecraft;
    //   713: invokevirtual func_147114_u : ()Lnet/minecraft/client/network/NetHandlerPlayClient;
    //   716: invokevirtual func_147298_b : ()Lnet/minecraft/network/NetworkManager;
    //   719: new net/minecraft/network/play/client/C0APacketAnimation
    //   722: dup
    //   723: invokespecial <init> : ()V
    //   726: invokevirtual func_179290_a : (Lnet/minecraft/network/Packet;)V
    //   729: getstatic me/oringo/oringoclient/qolfeatures/module/impl/skyblock/AutoBeams.mc : Lnet/minecraft/client/Minecraft;
    //   732: invokevirtual func_147114_u : ()Lnet/minecraft/client/network/NetHandlerPlayClient;
    //   735: invokevirtual func_147298_b : ()Lnet/minecraft/network/NetworkManager;
    //   738: new net/minecraft/network/play/client/C09PacketHeldItemChange
    //   741: dup
    //   742: getstatic me/oringo/oringoclient/qolfeatures/module/impl/skyblock/AutoBeams.mc : Lnet/minecraft/client/Minecraft;
    //   745: getfield field_71439_g : Lnet/minecraft/client/entity/EntityPlayerSP;
    //   748: getfield field_71071_by : Lnet/minecraft/entity/player/InventoryPlayer;
    //   751: getfield field_70461_c : I
    //   754: invokespecial <init> : (I)V
    //   757: invokevirtual func_179290_a : (Lnet/minecraft/network/Packet;)V
    //   760: iconst_0
    //   761: putstatic me/oringo/oringoclient/qolfeatures/module/impl/skyblock/AutoBeams. : Z
    //   764: return
  }
  
  public BlockPos (BlockPos paramBlockPos) {
    if (paramBlockPos == null)
      return null; 
    double d = 99.0D;
    BlockPos blockPos = null;
    for (BlockPos blockPos1 : BlockPos.func_177980_a(paramBlockPos.func_177982_a(-2, -3, -2), paramBlockPos.func_177982_a(2, 3, 2))) {
      Block block = mc.field_71441_e.func_180495_p(blockPos1).func_177230_c();
      if (block.equals(Blocks.field_180398_cJ) && blockPos1.func_177951_i((Vec3i)paramBlockPos) < d) {
        blockPos = blockPos1;
        d = blockPos1.func_177951_i((Vec3i)blockPos1);
      } 
    } 
    return blockPos;
  }
  
  public static int () {
    if (BloodSkipCommand. != 0)
      return BloodSkipCommand.; 
    if (BloodSkipCommand.mc.field_71439_g != null && BloodSkipCommand.mc.field_71439_g.func_96123_co() != null) {
      Scoreboard scoreboard = BloodSkipCommand.mc.field_71439_g.func_96123_co();
      ArrayList arrayList = new ArrayList(scoreboard.func_96534_i(scoreboard.func_96539_a(1)));
      for (Score score : arrayList) {
        StringBuilder stringBuilder = new StringBuilder();
        ScorePlayerTeam scorePlayerTeam = scoreboard.func_96509_i(score.func_96653_e());
        String str = String.valueOf((new StringBuilder()).append(scorePlayerTeam.func_96668_e()).append(score.func_96653_e()).append(scorePlayerTeam.func_96663_f()));
        for (char c : str.toCharArray()) {
          if (c < 'Ā')
            stringBuilder.append(c); 
        } 
        Matcher matcher = BloodSkipCommand..matcher(String.valueOf(stringBuilder));
        if (matcher.find())
          return Integer.parseInt(matcher.group(2)); 
      } 
    } 
    return 0;
  }
  
  public AutoBeams() {
    super("Auto Beams", Module.Category.);
    (new Setting[] { (Setting), (Setting) });
  }
  
  public static class Pair<K, V> implements Serializable {
    public V ;
    
    public K ;
    
    public K getKey() {
      return this.;
    }
    
    public V getValue() {
      return this.;
    }
    
    public String toString() {
      return String.valueOf((new StringBuilder()).append(this.).append("=").append(this.));
    }
    
    public boolean equals(Object param1Object) {
      if (this == param1Object)
        return true; 
      if (param1Object instanceof Pair) {
        Pair pair = (Pair)param1Object;
        return (Objects.equals(this., pair.) || Objects.equals(this., pair.));
      } 
      return false;
    }
    
    public Pair(K param1K, V param1V) {
      this. = param1K;
      this. = param1V;
    }
  }
}
