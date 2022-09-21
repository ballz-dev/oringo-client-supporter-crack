package me.oringo.oringoclient.qolfeatures.module.impl.skyblock;

import java.awt.Color;
import me.oringo.oringoclient.OringoClient;
import me.oringo.oringoclient.commands.impl.ClipCommand;
import me.oringo.oringoclient.events.impl.PacketSentEvent;
import me.oringo.oringoclient.qolfeatures.module.Module;
import me.oringo.oringoclient.qolfeatures.module.impl.other.AntiNicker;
import me.oringo.oringoclient.qolfeatures.module.impl.player.AutoTool;
import me.oringo.oringoclient.qolfeatures.module.impl.player.AutoUHC;
import me.oringo.oringoclient.qolfeatures.module.settings.Setting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.BooleanSetting;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;

public class RodStacker extends Module {
  public BooleanSetting  = new BooleanSetting("From inv", true);
  
  public static void (TileEntity paramTileEntity, Color paramColor) {
    GL11.glBlendFunc(770, 771);
    GL11.glEnable(3042);
    GL11.glLineWidth(2.0F);
    GL11.glDisable(3553);
    GL11.glDisable(2929);
    GL11.glDepthMask(false);
    AntiNicker.(paramColor);
    RenderGlobal.func_181561_a(paramTileEntity.getRenderBoundingBox().func_72317_d(-(OringoClient.mc.func_175598_ae()).field_78730_l, -(OringoClient.mc.func_175598_ae()).field_78731_m, -(OringoClient.mc.func_175598_ae()).field_78728_n));
    GL11.glEnable(3553);
    GL11.glEnable(2929);
    GL11.glDepthMask(true);
    GL11.glDisable(3042);
  }
  
  public RodStacker() {
    super("Rod Stacker", Module.Category.);
    (new Setting[] { (Setting)this. });
  }
  
  @SubscribeEvent
  public void (PacketSentEvent.Post paramPost) {
    if (() && paramPost. instanceof C08PacketPlayerBlockPlacement) {
      C08PacketPlayerBlockPlacement c08PacketPlayerBlockPlacement = (C08PacketPlayerBlockPlacement)paramPost.;
      if (c08PacketPlayerBlockPlacement.func_149574_g() != null && c08PacketPlayerBlockPlacement.func_149574_g().func_77973_b() == Items.field_151112_aM && mc.field_71439_g.field_71070_bA.field_75152_c == mc.field_71439_g.field_71069_bz.field_75152_c) {
        int i = this..() ? AutoTool.(c08PacketPlayerBlockPlacement::) : ClipCommand.(c08PacketPlayerBlockPlacement::);
        if (i != -1) {
          if (!this..())
            i += 36; 
          mc.field_71442_b.func_78753_a(mc.field_71439_g.field_71069_bz.field_75152_c, i, mc.field_71439_g.field_71071_by.field_70461_c, 2, (EntityPlayer)mc.field_71439_g);
        } 
      } 
    } 
  }
  
  public static void (String paramString) {
    AutoUHC.++;
    AutoUHC..();
    AutoUHC.mc.field_71439_g.func_71165_d(paramString);
  }
}
