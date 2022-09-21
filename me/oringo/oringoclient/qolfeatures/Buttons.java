package me.oringo.oringoclient.qolfeatures;

import me.oringo.oringoclient.qolfeatures.module.impl.combat.KillAura;
import me.oringo.oringoclient.qolfeatures.module.impl.player.ChestStealer;
import me.oringo.oringoclient.qolfeatures.module.impl.player.InvManager;
import me.oringo.oringoclient.qolfeatures.module.impl.render.Giants;
import me.oringo.oringoclient.qolfeatures.module.impl.render.Gui;
import me.oringo.oringoclient.qolfeatures.module.impl.skyblock.TerminalAura;
import net.minecraft.client.gui.GuiButton;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class Buttons {
  @SubscribeEvent
  public void (GuiScreenEvent.InitGuiEvent paramInitGuiEvent) {
    if (paramInitGuiEvent.gui instanceof net.minecraft.client.gui.inventory.GuiContainer && Gui..()) {
      paramInitGuiEvent.buttonList.add(new GuiButton(-5228, 3, 3, 150, 20, "Disable Kill Aura"));
      paramInitGuiEvent.buttonList.add(new GuiButton(-5936, 3, 26, 150, 20, "Disable Chest Stealer"));
      paramInitGuiEvent.buttonList.add(new GuiButton(-1835, 3, 49, 150, 20, "Disable Inventory Manager"));
      paramInitGuiEvent.buttonList.add(new GuiButton(-1464, 3, 72, 150, 20, "Disable Terminal Aura"));
    } 
  }
  
  @SubscribeEvent
  public void (GuiScreenEvent.ActionPerformedEvent.Pre paramPre) {
    switch (paramPre.button.field_146127_k) {
      case -5228:
        ((KillAura)Giants.(KillAura.class)).(false);
        break;
      case -5936:
        ((ChestStealer)Giants.(ChestStealer.class)).(false);
        break;
      case -1835:
        ((InvManager)Giants.(InvManager.class)).(false);
        break;
      case -1464:
        ((TerminalAura)Giants.(TerminalAura.class)).(false);
        break;
    } 
  }
  
  static {
  
  }
}
