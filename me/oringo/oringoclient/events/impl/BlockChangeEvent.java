package me.oringo.oringoclient.events.impl;

import java.awt.Color;
import me.oringo.oringoclient.events.OringoEvent;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockPos;
import net.minecraftforge.fml.common.eventhandler.Cancelable;
import org.lwjgl.opengl.GL11;

@Cancelable
public class BlockChangeEvent extends OringoEvent {
  public IBlockState ;
  
  public BlockPos ;
  
  public IBlockState ;
  
  public static void (Color paramColor) {
    GL11.glColor4d((paramColor.getRed() / 255.0F), (paramColor.getGreen() / 255.0F), (paramColor.getBlue() / 255.0F), (paramColor.getAlpha() / 255.0F));
  }
  
  public BlockChangeEvent(BlockPos paramBlockPos, IBlockState paramIBlockState1, IBlockState paramIBlockState2) {
    this. = paramBlockPos;
    this. = paramIBlockState1;
    this. = paramIBlockState2;
  }
}
