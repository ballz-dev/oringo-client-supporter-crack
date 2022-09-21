package me.oringo.oringoclient.commands.impl;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.oringo.oringoclient.commands.Command;
import me.oringo.oringoclient.events.impl.WorldJoinEvent;
import me.oringo.oringoclient.qolfeatures.module.impl.movement.Sneak;
import me.oringo.oringoclient.qolfeatures.module.impl.render.FullBright;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ChecknameCommand extends Command {
  public static String ;
  
  public static void (Object paramObject) {
    FullBright.(paramObject.toString());
  }
  
  public void (String[] paramArrayOfString) throws Exception {
    if (paramArrayOfString.length != 1) {
      Sneak.("/checkname [IGN]");
      return;
    } 
    for (EntityPlayer entityPlayer : mc.field_71441_e.field_73010_i) {
      if (entityPlayer.func_70005_c_().equalsIgnoreCase(paramArrayOfString[0])) {
        if (entityPlayer.func_70032_d((Entity)mc.field_71439_g) > 6.0F) {
          Sneak.("You are too far away!");
          return;
        } 
        if (mc.field_71439_g.func_70694_bm() != null) {
          Sneak.("Your hand must be empty!");
          return;
        } 
        mc.field_71442_b.func_78768_b((EntityPlayer)mc.field_71439_g, (Entity)entityPlayer);
         = paramArrayOfString[0];
        return;
      } 
    } 
    Sneak.("Invalid name");
  }
  
  public String () {
    return null;
  }
  
  @SubscribeEvent
  public void (GuiOpenEvent paramGuiOpenEvent) {
    if (paramGuiOpenEvent.gui instanceof GuiChest &&  != null && ((ContainerChest)((GuiChest)paramGuiOpenEvent.gui).field_147002_h).func_85151_d().func_70005_c_().toLowerCase().startsWith(.toLowerCase()))
      (new Thread(ChecknameCommand::)).start(); 
  }
  
  public ChecknameCommand() {
    super("checkname", new String[] { "shownicker", "denick", "revealname" });
  }
}
