package me.oringo.oringoclient.utils.api;

import me.oringo.oringoclient.qolfeatures.module.impl.combat.KillAura;
import me.oringo.oringoclient.qolfeatures.module.impl.player.InvManager;
import me.oringo.oringoclient.qolfeatures.module.impl.skyblock.Phase;
import me.oringo.oringoclient.utils.Rotation;
import me.oringo.oringoclient.utils.RotationUtils;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;

public class HypixelAPI {
  static {
  
  }
  
  public static double (Entity paramEntity) {
    AxisAlignedBB axisAlignedBB = paramEntity.func_174813_aQ();
    if (axisAlignedBB == null)
      return 100.0D; 
    Rotation rotation = RotationUtils.(Phase.(paramEntity));
    Vec3 vec31 = KillAura.mc.field_71439_g.func_174824_e(1.0F);
    Vec3 vec32 = InvManager.(rotation.(), rotation.());
    Vec3 vec33 = vec31.func_72441_c(vec32.field_72450_a * 13.0D, vec32.field_72448_b * 13.0D, vec32.field_72449_c * 13.0D);
    MovingObjectPosition movingObjectPosition = axisAlignedBB.func_72327_a(vec31, vec33);
    return (movingObjectPosition == null) ? 100.0D : movingObjectPosition.field_72307_f.func_72438_d(KillAura.mc.field_71439_g.func_174824_e(1.0F));
  }
}
