package me.oringo.oringoclient.commands.impl;

import java.awt.Color;
import java.util.UUID;
import me.oringo.oringoclient.commands.Command;
import me.oringo.oringoclient.qolfeatures.module.impl.other.SimulatorAura;
import me.oringo.oringoclient.qolfeatures.module.impl.other.VClip;
import me.oringo.oringoclient.qolfeatures.module.impl.player.NoRotate;
import me.oringo.oringoclient.ui.notifications.Notifications;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class StalkCommand extends Command {
  public static UUID ;
  
  public String () {
    return "Shows you a player";
  }
  
  public static boolean () {
    Block block = VClip.mc.field_71441_e.func_180495_p(new BlockPos(VClip.mc.field_71439_g.func_174791_d().func_72441_c(0.0D, 0.35D, 0.0D))).func_177230_c();
    return (block instanceof net.minecraft.block.BlockSkull || block instanceof net.minecraft.block.BlockFence || block instanceof net.minecraft.block.BlockFenceGate || block instanceof net.minecraft.block.BlockWall);
  }
  
  @SubscribeEvent
  public void (RenderWorldLastEvent paramRenderWorldLastEvent) {
    if ( != null)
      for (EntityPlayer entityPlayer : mc.field_71441_e.field_73010_i) {
        if (entityPlayer.func_110124_au().equals()) {
          SimulatorAura.((Entity)entityPlayer, paramRenderWorldLastEvent.partialTicks, 1.0F, Color.cyan);
          break;
        } 
      }  
  }
  
  public StalkCommand() {
    super("stalk", new String[] { "hunt" });
  }
  
  public void (String[] paramArrayOfString) throws Exception {
     = null;
    if (paramArrayOfString.length == 1) {
      for (EntityPlayer entityPlayer : (Minecraft.func_71410_x()).field_71441_e.field_73010_i) {
        if (entityPlayer.func_70005_c_().equalsIgnoreCase(paramArrayOfString[0])) {
           = entityPlayer.func_110124_au();
          NoRotate.("Oringo Client", "Enabled stalking!", 1000);
          return;
        } 
      } 
      FireworkCommand.("Player not found!", 1000, Notifications.NotificationType.);
      return;
    } 
    NoRotate.("Oringo Client", "Disabled Stalking!", 1000);
  }
}
