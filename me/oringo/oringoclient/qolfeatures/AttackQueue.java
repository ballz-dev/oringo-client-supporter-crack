package me.oringo.oringoclient.qolfeatures;

import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class AttackQueue {
  public static boolean  = false;
  
  public static int  = 0;
  
  @SubscribeEvent
  public void (TickEvent.ClientTickEvent paramClientTickEvent) {
    if ( != 0)
      --; 
    Minecraft minecraft = Minecraft.func_71410_x();
    if (minecraft.field_71439_g != null &&  && ( == 0 || (minecraft.field_71476_x != null && minecraft.field_71476_x.field_72313_a.equals(MovingObjectPosition.MovingObjectType.ENTITY)))) {
      minecraft.field_71439_g.func_71038_i();
      if (minecraft.field_71476_x != null) {
        BlockPos blockPos;
        switch (null.[minecraft.field_71476_x.field_72313_a.ordinal()]) {
          case 1:
            minecraft.field_71442_b.func_78764_a((EntityPlayer)minecraft.field_71439_g, minecraft.field_71476_x.field_72308_g);
            break;
          case 2:
            blockPos = minecraft.field_71476_x.func_178782_a();
            if (minecraft.field_71441_e.func_180495_p(blockPos).func_177230_c().func_149688_o() != Material.field_151579_a) {
              minecraft.field_71442_b.func_180511_b(blockPos, minecraft.field_71476_x.field_178784_b);
              break;
            } 
          default:
            if (minecraft.field_71442_b.func_78762_g())
               = 10; 
            break;
        } 
      } 
       = false;
    } 
  }
}
