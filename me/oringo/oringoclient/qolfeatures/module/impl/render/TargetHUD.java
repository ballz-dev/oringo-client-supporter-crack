package me.oringo.oringoclient.qolfeatures.module.impl.render;

import java.util.Random;
import me.oringo.oringoclient.OringoClient;
import me.oringo.oringoclient.commands.Command;
import me.oringo.oringoclient.commands.CommandHandler;
import me.oringo.oringoclient.events.OringoEvent;
import me.oringo.oringoclient.events.impl.DeathEvent;
import me.oringo.oringoclient.events.impl.GuiChatEvent;
import me.oringo.oringoclient.qolfeatures.module.Module;
import me.oringo.oringoclient.qolfeatures.module.impl.combat.AimAssist;
import me.oringo.oringoclient.qolfeatures.module.impl.combat.KillAura;
import me.oringo.oringoclient.qolfeatures.module.impl.macro.AutoFish;
import me.oringo.oringoclient.qolfeatures.module.impl.movement.Sneak;
import me.oringo.oringoclient.qolfeatures.module.impl.skyblock.BoneThrower;
import me.oringo.oringoclient.qolfeatures.module.settings.Setting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.BooleanSetting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.ModeSetting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.NumberSetting;
import me.oringo.oringoclient.ui.hud.impl.TargetComponent;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class TargetHUD extends Module {
  public NumberSetting  = new NumberSetting("X123", 0.0D, -100000.0D, 100000.0D, 1.0E-5D, TargetHUD::);
  
  public NumberSetting  = new NumberSetting("Y123", 0.0D, -100000.0D, 100000.0D, 1.0E-5D, TargetHUD::);
  
  public ModeSetting  = new ModeSetting("Blur Strength", "Low", new String[] { "None", "Low", "High" });
  
  public static TargetHUD  = new TargetHUD();
  
  public BooleanSetting  = new BooleanSetting("Target ESP", true);
  
  public ModeSetting  = new ModeSetting("Style", "New", new String[] { "New", "Old" });
  
  @SubscribeEvent
  public void (GuiChatEvent paramGuiChatEvent) {
    if (!())
      return; 
    TargetComponent targetComponent = TargetComponent.;
    if (paramGuiChatEvent instanceof GuiChatEvent.MouseClicked) {
      if (targetComponent.(paramGuiChatEvent., paramGuiChatEvent.))
        targetComponent.(); 
    } else if (paramGuiChatEvent instanceof GuiChatEvent.MouseReleased || paramGuiChatEvent instanceof GuiChatEvent.Closed) {
      targetComponent.();
    } 
  }
  
  @SubscribeEvent
  public void (RenderWorldLastEvent paramRenderWorldLastEvent) {
    if (KillAura. != null && KillAura..func_110143_aJ() > 0.0F && !KillAura..field_70128_L && this..())
      DeathEvent.(KillAura., OringoClient..(), paramRenderWorldLastEvent.partialTicks); 
  }
  
  public static boolean (String paramString) {
    paramString = paramString.trim();
    if (paramString.length() > 0 && paramString.charAt(0) == BoneThrower.()) {
      paramString = paramString.toLowerCase().substring(1);
      String str = paramString.split(" ")[0];
      String[] arrayOfString = new String[0];
      if (paramString.contains(" "))
        arrayOfString = paramString.replaceFirst(str, "").replaceFirst(" ", "").split(" "); 
      if (CommandHandler..containsKey(str)) {
        Command command = (Command)CommandHandler..get(str);
        try {
          command.(arrayOfString);
        } catch (Exception exception) {
          exception.printStackTrace();
        } 
      } else {
        OringoEvent.(String.format("§bOringoClient §3» §cInvalid command \"%shelp\" for §cmore info", new Object[] { Character.valueOf(BoneThrower.()) }));
      } 
      return true;
    } 
    return false;
  }
  
  public static boolean (EntityLivingBase paramEntityLivingBase) {
    for (String str : AutoFish.) {
      if (paramEntityLivingBase.func_70005_c_().contains(str))
        return !paramEntityLivingBase.func_70005_c_().contains("Pet"); 
    } 
    return false;
  }
  
  public TargetHUD() {
    super("Target HUD", Module.Category.);
    (new Setting[] { (Setting)this., (Setting)this., (Setting)this., (Setting)this., (Setting)this. });
  }
  
  public static int (Entity paramEntity) {
    Minecraft minecraft = Minecraft.func_71410_x();
    if (paramEntity != null) {
      for (byte b = 0; b < 100; b++) {
        byte b1 = 60;
        if (AimAssist.(minecraft.field_71439_g.field_70165_t - Math.sin(Math.toRadians((paramEntity.field_70177_z - b1)) * 0.13D * b), minecraft.field_71439_g.field_70163_u, minecraft.field_71439_g.field_70161_v + Math.cos(Math.toRadians((paramEntity.field_70177_z - b1))) * 0.13D * b)) {
          if (AimAssist.(minecraft.field_71439_g.field_70165_t - Math.sin(Math.toRadians((paramEntity.field_70177_z + b1)) * 0.13D * b), minecraft.field_71439_g.field_70163_u, minecraft.field_71439_g.field_70161_v + Math.cos(Math.toRadians((paramEntity.field_70177_z + b1))) * 0.13D * b))
            return -1; 
          Sneak.("Smart: A");
          return 0;
        } 
        if (AimAssist.(minecraft.field_71439_g.field_70165_t - Math.sin(Math.toRadians((paramEntity.field_70177_z + b1)) * 0.13D * b), minecraft.field_71439_g.field_70163_u, minecraft.field_71439_g.field_70161_v + Math.cos(Math.toRadians((paramEntity.field_70177_z + b1))) * 0.13D * b)) {
          Sneak.("Smart: D");
          return 1;
        } 
      } 
      if (AimAssist.(paramEntity.field_70165_t - Math.sin(Math.toRadians(minecraft.field_71439_g.field_70177_z) * 3.0D), paramEntity.field_70163_u, paramEntity.field_70161_v + Math.cos(Math.toRadians(minecraft.field_71439_g.field_70177_z)) * 3.0D)) {
        Sneak.("Smart: No strafe");
        return -1;
      } 
    } 
    return (new Random()).nextInt(2);
  }
  
  @SubscribeEvent
  public void (RenderGameOverlayEvent.Pre paramPre) {
    if (() && paramPre.type == RenderGameOverlayEvent.ElementType.CROSSHAIRS)
      TargetComponent..(); 
  }
}
