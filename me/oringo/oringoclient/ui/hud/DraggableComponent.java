package me.oringo.oringoclient.ui.hud;

import me.oringo.oringoclient.commands.impl.ClipCommand;
import me.oringo.oringoclient.qolfeatures.module.impl.player.AntiObby;
import me.oringo.oringoclient.qolfeatures.module.impl.player.AutoHeal;
import me.oringo.oringoclient.qolfeatures.module.impl.player.ChestStealer;
import net.minecraft.entity.Entity;
import net.minecraft.util.Vec3;

public class DraggableComponent extends Component {
  public double ;
  
  public boolean ;
  
  public double ;
  
  public static double () {
    return Math.toRadians(ChestStealer.());
  }
  
  public void () {}
  
  public void () {
    this. = true;
    this. = this. - ClipCommand.();
    this. = this. - AntiObby.();
  }
  
  public boolean () {
    return this.;
  }
  
  public static Vec3 (Entity paramEntity, float paramFloat) {
    return new Vec3(paramEntity.field_70165_t + AutoHeal.(paramFloat, -paramFloat), paramEntity.field_70163_u + paramEntity.func_70047_e() / 2.0D + AutoHeal.(paramFloat, -paramFloat), paramEntity.field_70161_v + AutoHeal.(paramFloat, -paramFloat));
  }
  
  public HudVec () {
    if (this.) {
      this. = AntiObby.() + this.;
      this. = ClipCommand.() + this.;
    } 
    return null;
  }
  
  public void () {
    this. = false;
  }
}
