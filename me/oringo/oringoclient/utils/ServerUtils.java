package me.oringo.oringoclient.utils;

import me.oringo.oringoclient.OringoClient;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.RunnableSetting;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;

public class ServerUtils {
  public static boolean ;
  
  public static ServerUtils  = new ServerUtils();
  
  @SubscribeEvent
  public void (FMLNetworkEvent.ClientConnectedToServerEvent paramClientConnectedToServerEvent) {
    if (!paramClientConnectedToServerEvent.isLocal)
       = (OringoClient.mc.func_147104_D()).field_78845_b.toLowerCase().contains("hypixel"); 
  }
  
  public static Rotation (AxisAlignedBB paramAxisAlignedBB, float paramFloat) {
    return RunnableSetting.(paramAxisAlignedBB.func_72314_b(-paramFloat, -paramFloat, -paramFloat));
  }
  
  @SubscribeEvent
  public void (FMLNetworkEvent.ClientDisconnectionFromServerEvent paramClientDisconnectionFromServerEvent) {
     = false;
    System.out.println("Detected leaving hypixel");
  }
}
