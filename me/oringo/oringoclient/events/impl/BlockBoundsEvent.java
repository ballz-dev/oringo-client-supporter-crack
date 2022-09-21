package me.oringo.oringoclient.events.impl;

import me.oringo.oringoclient.OringoClient;
import me.oringo.oringoclient.commands.impl.NamesCommand;
import me.oringo.oringoclient.events.OringoEvent;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3;
import net.minecraftforge.fml.common.eventhandler.Cancelable;

@Cancelable
public class BlockBoundsEvent extends OringoEvent {
  public Block ;
  
  public Entity ;
  
  public AxisAlignedBB ;
  
  public BlockPos ;
  
  public static Vec3 (float paramFloat) {
    return new Vec3(NamesCommand.(OringoClient.mc.field_71439_g.field_70169_q, OringoClient.mc.field_71439_g.field_70165_t, paramFloat), NamesCommand.(OringoClient.mc.field_71439_g.field_70167_r, OringoClient.mc.field_71439_g.field_70163_u, paramFloat) + 0.1D, NamesCommand.(OringoClient.mc.field_71439_g.field_70166_s, OringoClient.mc.field_71439_g.field_70161_v, paramFloat));
  }
  
  public BlockBoundsEvent(Block paramBlock, AxisAlignedBB paramAxisAlignedBB, BlockPos paramBlockPos, Entity paramEntity) {
    this. = paramAxisAlignedBB;
    this. = paramBlock;
    this. = paramBlockPos;
    this. = paramEntity;
  }
  
  public static void () {
    // Byte code:
    //   0: getstatic me/oringo/oringoclient/qolfeatures/module/impl/render/NoRenderOld.mc : Lnet/minecraft/client/Minecraft;
    //   3: getfield field_71454_w : Z
    //   6: ifeq -> 261
    //   9: getstatic me/oringo/oringoclient/qolfeatures/module/impl/render/NoRenderOld.mc : Lnet/minecraft/client/Minecraft;
    //   12: getfield field_71462_r : Lnet/minecraft/client/gui/GuiScreen;
    //   15: ifnull -> 261
    //   18: getstatic me/oringo/oringoclient/qolfeatures/module/impl/render/NoRenderOld.mc : Lnet/minecraft/client/Minecraft;
    //   21: invokevirtual func_71364_i : ()V
    //   24: getstatic me/oringo/oringoclient/qolfeatures/module/impl/render/NoRenderOld.mc : Lnet/minecraft/client/Minecraft;
    //   27: getfield field_71441_e : Lnet/minecraft/client/multiplayer/WorldClient;
    //   30: ifnonnull -> 80
    //   33: iconst_0
    //   34: iconst_0
    //   35: getstatic me/oringo/oringoclient/qolfeatures/module/impl/render/NoRenderOld.mc : Lnet/minecraft/client/Minecraft;
    //   38: getfield field_71443_c : I
    //   41: getstatic me/oringo/oringoclient/qolfeatures/module/impl/render/NoRenderOld.mc : Lnet/minecraft/client/Minecraft;
    //   44: getfield field_71440_d : I
    //   47: invokestatic func_179083_b : (IIII)V
    //   50: sipush #5889
    //   53: invokestatic func_179128_n : (I)V
    //   56: invokestatic func_179096_D : ()V
    //   59: sipush #5888
    //   62: invokestatic func_179128_n : (I)V
    //   65: invokestatic func_179096_D : ()V
    //   68: getstatic me/oringo/oringoclient/qolfeatures/module/impl/render/NoRenderOld.mc : Lnet/minecraft/client/Minecraft;
    //   71: getfield field_71460_t : Lnet/minecraft/client/renderer/EntityRenderer;
    //   74: invokevirtual func_78478_c : ()V
    //   77: goto -> 97
    //   80: sipush #516
    //   83: ldc 0.1
    //   85: invokestatic func_179092_a : (IF)V
    //   88: getstatic me/oringo/oringoclient/qolfeatures/module/impl/render/NoRenderOld.mc : Lnet/minecraft/client/Minecraft;
    //   91: getfield field_71460_t : Lnet/minecraft/client/renderer/EntityRenderer;
    //   94: invokevirtual func_78478_c : ()V
    //   97: new net/minecraft/client/gui/ScaledResolution
    //   100: dup
    //   101: getstatic me/oringo/oringoclient/qolfeatures/module/impl/render/NoRenderOld.mc : Lnet/minecraft/client/Minecraft;
    //   104: invokespecial <init> : (Lnet/minecraft/client/Minecraft;)V
    //   107: astore_0
    //   108: aload_0
    //   109: invokevirtual func_78326_a : ()I
    //   112: istore_1
    //   113: aload_0
    //   114: invokevirtual func_78328_b : ()I
    //   117: istore_2
    //   118: invokestatic getX : ()I
    //   121: iload_1
    //   122: imul
    //   123: getstatic me/oringo/oringoclient/qolfeatures/module/impl/render/NoRenderOld.mc : Lnet/minecraft/client/Minecraft;
    //   126: getfield field_71443_c : I
    //   129: idiv
    //   130: istore_3
    //   131: iload_2
    //   132: invokestatic getY : ()I
    //   135: iload_2
    //   136: imul
    //   137: getstatic me/oringo/oringoclient/qolfeatures/module/impl/render/NoRenderOld.mc : Lnet/minecraft/client/Minecraft;
    //   140: getfield field_71440_d : I
    //   143: idiv
    //   144: isub
    //   145: iconst_1
    //   146: isub
    //   147: istore #4
    //   149: sipush #256
    //   152: invokestatic func_179086_m : (I)V
    //   155: fconst_0
    //   156: fconst_0
    //   157: iload_1
    //   158: i2f
    //   159: iload_2
    //   160: i2f
    //   161: getstatic java/awt/Color.black : Ljava/awt/Color;
    //   164: invokevirtual getRGB : ()I
    //   167: invokestatic  : (FFFFI)V
    //   170: getstatic me/oringo/oringoclient/qolfeatures/module/impl/render/NoRenderOld.mc : Lnet/minecraft/client/Minecraft;
    //   173: getfield field_71462_r : Lnet/minecraft/client/gui/GuiScreen;
    //   176: iload_3
    //   177: iload #4
    //   179: invokestatic  : ()Lnet/minecraft/util/Timer;
    //   182: getfield field_74281_c : F
    //   185: invokestatic drawScreen : (Lnet/minecraft/client/gui/GuiScreen;IIF)V
    //   188: goto -> 261
    //   191: astore #5
    //   193: aload #5
    //   195: ldc 'Rendering screen'
    //   197: invokestatic func_85055_a : (Ljava/lang/Throwable;Ljava/lang/String;)Lnet/minecraft/crash/CrashReport;
    //   200: astore #6
    //   202: aload #6
    //   204: ldc 'Screen render details'
    //   206: invokevirtual func_85058_a : (Ljava/lang/String;)Lnet/minecraft/crash/CrashReportCategory;
    //   209: astore #7
    //   211: aload #7
    //   213: ldc 'Screen name'
    //   215: <illegal opcode> call : ()Ljava/util/concurrent/Callable;
    //   220: invokevirtual func_71500_a : (Ljava/lang/String;Ljava/util/concurrent/Callable;)V
    //   223: aload #7
    //   225: ldc 'Mouse location'
    //   227: iload_3
    //   228: iload #4
    //   230: <illegal opcode> call : (II)Ljava/util/concurrent/Callable;
    //   235: invokevirtual func_71500_a : (Ljava/lang/String;Ljava/util/concurrent/Callable;)V
    //   238: aload #7
    //   240: ldc 'Screen size'
    //   242: aload_0
    //   243: <illegal opcode> call : (Lnet/minecraft/client/gui/ScaledResolution;)Ljava/util/concurrent/Callable;
    //   248: invokevirtual func_71500_a : (Ljava/lang/String;Ljava/util/concurrent/Callable;)V
    //   251: new net/minecraft/util/ReportedException
    //   254: dup
    //   255: aload #6
    //   257: invokespecial <init> : (Lnet/minecraft/crash/CrashReport;)V
    //   260: athrow
    //   261: return
    // Exception table:
    //   from	to	target	type
    //   170	188	191	java/lang/Throwable
  }
}
