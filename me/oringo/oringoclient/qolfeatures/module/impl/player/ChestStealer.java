package me.oringo.oringoclient.qolfeatures.module.impl.player;

import com.mojang.realmsclient.gui.ChatFormatting;
import java.awt.Color;
import me.oringo.oringoclient.qolfeatures.module.Module;
import me.oringo.oringoclient.qolfeatures.module.impl.other.AntiNicker;
import me.oringo.oringoclient.qolfeatures.module.settings.Setting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.BooleanSetting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.NumberSetting;
import me.oringo.oringoclient.utils.MilliTimer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.item.Item;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;

public class ChestStealer extends Module {
  public MilliTimer  = new MilliTimer();
  
  public BooleanSetting  = new BooleanSetting("Auto close", true);
  
  public BooleanSetting  = new BooleanSetting("Steal trash", false);
  
  public NumberSetting  = new NumberSetting("Delay", 100.0D, 30.0D, 200.0D, 1.0D);
  
  public BooleanSetting  = new BooleanSetting("Name check", true);
  
  @SubscribeEvent
  public void (GuiScreenEvent.BackgroundDrawnEvent paramBackgroundDrawnEvent) {
    if (paramBackgroundDrawnEvent.gui instanceof GuiChest && ()) {
      Container container = ((GuiChest)paramBackgroundDrawnEvent.gui).field_147002_h;
      if (container instanceof ContainerChest && (!this..() || ChatFormatting.stripFormatting(((ContainerChest)container).func_85151_d().func_145748_c_().func_150254_d()).equals("Chest") || ChatFormatting.stripFormatting(((ContainerChest)container).func_85151_d().func_145748_c_().func_150254_d()).equals("LOW"))) {
        byte b;
        for (b = 0; b < ((ContainerChest)container).func_85151_d().func_70302_i_(); b++) {
          if (container.func_75139_a(b).func_75216_d() && this..((long)this..())) {
            Item item = container.func_75139_a(b).func_75211_c().func_77973_b();
            if (this..() || item instanceof net.minecraft.item.ItemEnderPearl || item instanceof net.minecraft.item.ItemTool || item instanceof net.minecraft.item.ItemArmor || item instanceof net.minecraft.item.ItemBow || item instanceof net.minecraft.item.ItemPotion || item == Items.field_151032_g || item instanceof net.minecraft.item.ItemAppleGold || item instanceof net.minecraft.item.ItemSword || item instanceof net.minecraft.item.ItemBlock) {
              mc.field_71442_b.func_78753_a(container.field_75152_c, b, 0, 1, (EntityPlayer)mc.field_71439_g);
              this..();
              return;
            } 
          } 
        } 
        for (b = 0; b < ((ContainerChest)container).func_85151_d().func_70302_i_(); b++) {
          if (container.func_75139_a(b).func_75216_d()) {
            Item item = container.func_75139_a(b).func_75211_c().func_77973_b();
            if (this..() || item instanceof net.minecraft.item.ItemEnderPearl || item instanceof net.minecraft.item.ItemTool || item instanceof net.minecraft.item.ItemArmor || item instanceof net.minecraft.item.ItemBow || item instanceof net.minecraft.item.ItemPotion || item == Items.field_151032_g || item instanceof net.minecraft.item.ItemAppleGold || item instanceof net.minecraft.item.ItemSword || item instanceof net.minecraft.item.ItemBlock)
              return; 
          } 
        } 
        if (this..())
          mc.field_71439_g.func_71053_j(); 
      } 
    } 
  }
  
  public static float () {
    // Byte code:
    //   0: getstatic me/oringo/oringoclient/qolfeatures/module/impl/combat/KillAura. : Lnet/minecraft/entity/EntityLivingBase;
    //   3: ifnull -> 19
    //   6: getstatic me/oringo/oringoclient/OringoClient. : Lme/oringo/oringoclient/qolfeatures/module/impl/combat/KillAura;
    //   9: pop
    //   10: getstatic me/oringo/oringoclient/qolfeatures/module/impl/combat/KillAura. : Lme/oringo/oringoclient/qolfeatures/module/settings/impl/BooleanSetting;
    //   13: invokevirtual  : ()Z
    //   16: ifne -> 28
    //   19: getstatic me/oringo/oringoclient/OringoClient. : Lme/oringo/oringoclient/qolfeatures/module/impl/combat/TargetStrafe;
    //   22: invokevirtual  : ()Z
    //   25: ifeq -> 40
    //   28: getstatic me/oringo/oringoclient/qolfeatures/module/impl/combat/KillAura. : Lnet/minecraft/entity/EntityLivingBase;
    //   31: invokestatic  : (Lnet/minecraft/entity/Entity;)Lme/oringo/oringoclient/utils/Rotation;
    //   34: invokevirtual  : ()F
    //   37: goto -> 49
    //   40: getstatic me/oringo/oringoclient/OringoClient.mc : Lnet/minecraft/client/Minecraft;
    //   43: getfield field_71439_g : Lnet/minecraft/client/entity/EntityPlayerSP;
    //   46: getfield field_70177_z : F
    //   49: fstore_0
    //   50: getstatic me/oringo/oringoclient/OringoClient.mc : Lnet/minecraft/client/Minecraft;
    //   53: getfield field_71439_g : Lnet/minecraft/client/entity/EntityPlayerSP;
    //   56: getfield field_70701_bs : F
    //   59: fconst_0
    //   60: fcmpg
    //   61: ifge -> 69
    //   64: fload_0
    //   65: ldc 180.0
    //   67: fadd
    //   68: fstore_0
    //   69: fconst_1
    //   70: fstore_1
    //   71: getstatic me/oringo/oringoclient/OringoClient.mc : Lnet/minecraft/client/Minecraft;
    //   74: getfield field_71439_g : Lnet/minecraft/client/entity/EntityPlayerSP;
    //   77: getfield field_70701_bs : F
    //   80: fconst_0
    //   81: fcmpg
    //   82: ifge -> 91
    //   85: ldc -0.5
    //   87: fstore_1
    //   88: goto -> 108
    //   91: getstatic me/oringo/oringoclient/OringoClient.mc : Lnet/minecraft/client/Minecraft;
    //   94: getfield field_71439_g : Lnet/minecraft/client/entity/EntityPlayerSP;
    //   97: getfield field_70701_bs : F
    //   100: fconst_0
    //   101: fcmpl
    //   102: ifle -> 108
    //   105: ldc 0.5
    //   107: fstore_1
    //   108: getstatic me/oringo/oringoclient/OringoClient.mc : Lnet/minecraft/client/Minecraft;
    //   111: getfield field_71439_g : Lnet/minecraft/client/entity/EntityPlayerSP;
    //   114: getfield field_70702_br : F
    //   117: fconst_0
    //   118: fcmpl
    //   119: ifle -> 129
    //   122: fload_0
    //   123: ldc 90.0
    //   125: fload_1
    //   126: fmul
    //   127: fsub
    //   128: fstore_0
    //   129: getstatic me/oringo/oringoclient/OringoClient.mc : Lnet/minecraft/client/Minecraft;
    //   132: getfield field_71439_g : Lnet/minecraft/client/entity/EntityPlayerSP;
    //   135: getfield field_70702_br : F
    //   138: fconst_0
    //   139: fcmpg
    //   140: ifge -> 150
    //   143: fload_0
    //   144: ldc 90.0
    //   146: fload_1
    //   147: fmul
    //   148: fadd
    //   149: fstore_0
    //   150: fload_0
    //   151: freturn
  }
  
  public ChestStealer() {
    super("Chest Stealer", 0, Module.Category.);
    (new Setting[] { (Setting)this., (Setting)this., (Setting)this., (Setting)this. });
  }
  
  public static void (BlockPos paramBlockPos, Color paramColor) {
    GL11.glBlendFunc(770, 771);
    GL11.glEnable(3042);
    GL11.glLineWidth(2.0F);
    GL11.glDisable(3553);
    GL11.glDisable(2929);
    GL11.glDepthMask(false);
    AntiNicker.(paramColor);
    RenderGlobal.func_181561_a(new AxisAlignedBB(paramBlockPos.func_177958_n() - (Minecraft.func_71410_x().func_175598_ae()).field_78730_l, paramBlockPos.func_177956_o() - (Minecraft.func_71410_x().func_175598_ae()).field_78731_m, paramBlockPos.func_177952_p() - (Minecraft.func_71410_x().func_175598_ae()).field_78728_n, (paramBlockPos.func_177958_n() + 1) - (Minecraft.func_71410_x().func_175598_ae()).field_78730_l, (paramBlockPos.func_177956_o() + 1) - (Minecraft.func_71410_x().func_175598_ae()).field_78731_m, (paramBlockPos.func_177952_p() + 1) - (Minecraft.func_71410_x().func_175598_ae()).field_78728_n));
    GL11.glEnable(3553);
    GL11.glEnable(2929);
    GL11.glDepthMask(true);
    GL11.glDisable(3042);
  }
}
