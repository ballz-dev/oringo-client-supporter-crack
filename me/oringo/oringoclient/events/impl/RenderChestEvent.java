package me.oringo.oringoclient.events.impl;

import me.oringo.oringoclient.events.OringoEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraftforge.fml.common.eventhandler.Cancelable;

@Cancelable
public class RenderChestEvent extends OringoEvent {
  public double ;
  
  public TileEntityChest ;
  
  public float ;
  
  public int ;
  
  public double ;
  
  public double ;
  
  public int getDestroyStage() {
    return this.;
  }
  
  public TileEntityChest getChest() {
    return this.;
  }
  
  public double getY() {
    return this.;
  }
  
  public double getZ() {
    return this.;
  }
  
  public RenderChestEvent(TileEntityChest paramTileEntityChest, double paramDouble1, double paramDouble2, double paramDouble3, float paramFloat, int paramInt) {
    this. = paramTileEntityChest;
    this. = paramDouble1;
    this. = paramDouble2;
    this. = paramDouble3;
    this. = paramFloat;
    this. = paramInt;
  }
  
  public boolean isPre() {
    return this instanceof Pre;
  }
  
  public static boolean (String paramString) {
    return Minecraft.func_71410_x().func_147114_u().func_175106_d().stream().anyMatch(paramString::);
  }
  
  public double getX() {
    return this.;
  }
  
  public float getPartialTicks() {
    return this.;
  }
  
  @Cancelable
  public static class Post extends RenderChestEvent {
    static {
    
    }
    
    public Post(TileEntityChest param1TileEntityChest, double param1Double1, double param1Double2, double param1Double3, float param1Float, int param1Int) {
      super(param1TileEntityChest, param1Double1, param1Double2, param1Double3, param1Float, param1Int);
    }
  }
  
  @Cancelable
  public static class Pre extends RenderChestEvent {
    public Pre(TileEntityChest param1TileEntityChest, double param1Double1, double param1Double2, double param1Double3, float param1Float, int param1Int) {
      super(param1TileEntityChest, param1Double1, param1Double2, param1Double3, param1Float, param1Int);
    }
    
    static {
    
    }
  }
}
