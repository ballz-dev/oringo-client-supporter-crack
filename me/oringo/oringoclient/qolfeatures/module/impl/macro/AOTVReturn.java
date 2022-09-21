package me.oringo.oringoclient.qolfeatures.module.impl.macro;

import me.oringo.oringoclient.qolfeatures.module.Module;
import me.oringo.oringoclient.qolfeatures.module.impl.player.NoRotate;
import me.oringo.oringoclient.qolfeatures.module.settings.Setting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.BooleanSetting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.ModeSetting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.NumberSetting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.StringSetting;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.Entity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Mouse;

public class AOTVReturn extends Module {
  public Vec3  = null;
  
  public BooleanSetting  = new BooleanSetting("Middle click", false);
  
  public boolean ;
  
  public boolean ;
  
  public ModeSetting  = new ModeSetting("mode", "walk", new String[] { "jump", "walk" });
  
  public Thread  = null;
  
  public StringSetting  = new StringSetting("Warp command", "/warp forge");
  
  public NumberSetting  = new NumberSetting("Delay", 2000.0D, 500.0D, 5000.0D, 1.0D);
  
  public StringSetting  = new StringSetting("TP Coords", "0.5,167,-10.5;-23.5,180,-26.5;-64.5,212,-15.5;-33.5,244,-32.5");
  
  public boolean  = false;
  
  public BooleanSetting  = new BooleanSetting("Open chat", true);
  
  public void (Runnable paramRunnable, boolean paramBoolean) {
    // Byte code:
    //   0: aload_0
    //   1: getfield  : Ljava/lang/Thread;
    //   4: ifnull -> 14
    //   7: aload_0
    //   8: getfield  : Ljava/lang/Thread;
    //   11: invokevirtual stop : ()V
    //   14: aload_0
    //   15: iconst_1
    //   16: putfield  : Z
    //   19: invokestatic func_71410_x : ()Lnet/minecraft/client/Minecraft;
    //   22: astore_3
    //   23: aload_0
    //   24: new java/lang/Thread
    //   27: dup
    //   28: aload_0
    //   29: aload_3
    //   30: aload_1
    //   31: iload_2
    //   32: <illegal opcode> run : (Lme/oringo/oringoclient/qolfeatures/module/impl/macro/AOTVReturn;Lnet/minecraft/client/Minecraft;Ljava/lang/Runnable;Z)Ljava/lang/Runnable;
    //   37: invokespecial <init> : (Ljava/lang/Runnable;)V
    //   40: dup_x1
    //   41: putfield  : Ljava/lang/Thread;
    //   44: invokevirtual start : ()V
    //   47: return
  }
  
  public AOTVReturn() {
    super("AOTV Return", Module.Category.OTHER);
    (new Setting[] { (Setting)this., (Setting)this., (Setting)this., (Setting)this., (Setting)this., (Setting)this. });
  }
  
  public boolean () {
    return this.;
  }
  
  @SubscribeEvent
  public void (TickEvent.ClientTickEvent paramClientTickEvent) {
    if (this.) {
      Minecraft.func_71410_x().func_147108_a((GuiScreen)new GuiChat());
      this. = false;
    } 
    if (mc.field_71439_g == null || mc.field_71441_e == null || !this..())
      return; 
    if (Mouse.isButtonDown(2) && mc.field_71462_r == null) {
      if (!this. && mc.field_71476_x != null && mc.field_71476_x.field_72313_a == MovingObjectPosition.MovingObjectType.BLOCK) {
        BlockPos blockPos = mc.field_71476_x.func_178782_a();
        if (mc.field_71441_e.func_180495_p(blockPos).func_177230_c().func_149688_o() != Material.field_151579_a) {
          this..(String.valueOf((new StringBuilder()).append(this..()).append((this..().length() > 0) ? ";" : "").append(blockPos.func_177958_n() + 0.5D).append(",").append(blockPos.func_177956_o() + 0.5D).append(",").append(blockPos.func_177952_p() + 0.5D)));
          NoRotate.("Oringo Client", String.valueOf((new StringBuilder()).append("Added ").append(blockPos.func_177958_n()).append(" ").append(blockPos.func_177956_o()).append(" ").append(blockPos.func_177952_p()).append(" to coords!")), 2500);
        } 
      } 
      this. = true;
    } else {
      this. = false;
    } 
  }
  
  public void () {
    this. = false;
    if (this. != null)
      this..stop(); 
  }
}
