package me.oringo.oringoclient.commands.impl;

import java.awt.Color;
import me.oringo.oringoclient.commands.Command;
import me.oringo.oringoclient.events.impl.PacketReceivedEvent;
import me.oringo.oringoclient.qolfeatures.module.impl.movement.Sneak;
import me.oringo.oringoclient.qolfeatures.module.impl.other.AntiNicker;
import me.oringo.oringoclient.qolfeatures.module.impl.render.Gui;
import me.oringo.oringoclient.qolfeatures.module.impl.render.XRay;
import me.oringo.oringoclient.utils.RenderUtils;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.GL11;

public class XRayCommand extends Command {
  public XRayCommand() {
    super("xray", new String[0]);
  }
  
  public String () {
    return null;
  }
  
  public static void (double paramDouble1, double paramDouble2, double paramDouble3, Color paramColor) {
    paramDouble1 -= (Minecraft.func_71410_x().func_175598_ae()).field_78730_l;
    paramDouble2 -= (Minecraft.func_71410_x().func_175598_ae()).field_78731_m;
    paramDouble3 -= (Minecraft.func_71410_x().func_175598_ae()).field_78728_n;
    GL11.glBlendFunc(770, 771);
    GL11.glEnable(3042);
    GL11.glLineWidth(2.5F);
    GL11.glEnable(2848);
    GL11.glHint(3154, 4354);
    GL11.glDisable(3553);
    GL11.glDisable(2929);
    GL11.glDepthMask(false);
    AntiNicker.(paramColor);
    GL11.glBegin(1);
    GL11.glVertex3d(0.0D, (Minecraft.func_71410_x()).field_71439_g.func_70047_e(), 0.0D);
    GL11.glVertex3d(paramDouble1, paramDouble2, paramDouble3);
    GL11.glEnd();
    GL11.glEnable(3553);
    GL11.glEnable(2929);
    GL11.glDepthMask(true);
    GL11.glDisable(2848);
    GL11.glDisable(3042);
  }
  
  static {
  
  }
  
  public void (String[] paramArrayOfString) throws Exception {
    if (paramArrayOfString.length == 0) {
      for (Block block : XRay.)
        Sneak.(block.getRegistryName()); 
      Sneak.(String.format("%s%s add/remove", new Object[] { Gui..(), ()[0] }));
    } else {
      switch (paramArrayOfString[0]) {
        case "add":
          if (paramArrayOfString.length == 2) {
            Block block = Block.func_149684_b(paramArrayOfString[1]);
            if (block != null) {
              XRay..add(block);
              PacketReceivedEvent.(String.valueOf((new StringBuilder()).append("Successfully added ").append(paramArrayOfString[1]).append(" to list")));
              break;
            } 
            PacketReceivedEvent.("Couldn't find that block");
            break;
          } 
          Sneak.(String.format("Usage: %s%s add name", new Object[] { Gui..(), ()[0] }));
          break;
        case "remove":
          if (paramArrayOfString.length == 2) {
            Block block = Block.func_149684_b(paramArrayOfString[1]);
            if (block != null)
              XRay..remove(block); 
          } 
          break;
        default:
          for (Block block : XRay.)
            Sneak.(block.getRegistryName()); 
          break;
      } 
      if (mc.field_71438_f != null)
        mc.field_71438_f.func_72712_a(); 
      RenderUtils.();
    } 
  }
}
