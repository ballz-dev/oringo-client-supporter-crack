package me.oringo.oringoclient.utils;

import me.oringo.oringoclient.OringoClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.scoreboard.Score;

public class EntityUtils {
  public static boolean isOnTeam(EntityPlayer paramEntityPlayer) {
    for (Score score : OringoClient.mc.field_71439_g.func_96123_co().func_96528_e()) {
      if (score.func_96645_d().func_96679_b().equals("health") && score.func_96653_e().contains(paramEntityPlayer.func_70005_c_()))
        return true; 
    } 
    return false;
  }
  
  public static boolean isTeam(Entity paramEntity) {
    if (!(paramEntity instanceof EntityPlayer) || paramEntity.func_145748_c_().func_150260_c().length() < 4)
      return false; 
    if (SkyblockUtils.onSkyblock)
      return isOnTeam((EntityPlayer)paramEntity); 
    if (OringoClient.mc.field_71439_g.func_145748_c_().func_150254_d().charAt(2) == '§' && paramEntity.func_145748_c_().func_150254_d().charAt(2) == '§')
      return (OringoClient.mc.field_71439_g.func_145748_c_().func_150254_d().charAt(3) == paramEntity.func_145748_c_().func_150254_d().charAt(3)); 
    return false;
  }
}
