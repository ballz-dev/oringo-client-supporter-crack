package me.oringo.oringoclient.ui.hud;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.oringo.oringoclient.commands.impl.ClipCommand;
import me.oringo.oringoclient.qolfeatures.module.impl.player.AntiObby;
import me.oringo.oringoclient.utils.MilliTimer;
import me.oringo.oringoclient.utils.SkyblockUtils;
import net.minecraft.client.Minecraft;

public abstract class Component {
  public double ;
  
  public double ;
  
  public MilliTimer  = new MilliTimer();
  
  public static Minecraft mc = Minecraft.func_71410_x();
  
  public boolean ;
  
  public double ;
  
  public double ;
  
  public boolean () {
    return (ClipCommand.(), AntiObby.());
  }
  
  public double () {
    return this.;
  }
  
  public boolean () {
    return this.;
  }
  
  public double () {
    return this.;
  }
  
  public boolean (int paramInt1, int paramInt2) {
    return (paramInt1 > this. && paramInt1 < this. + this. && paramInt2 > this. && paramInt2 < this. + this.);
  }
  
  public static boolean (String paramString) {
    return (SkyblockUtils.mc.field_71439_g == null || SkyblockUtils.mc.field_71439_g.func_96123_co() == null || SkyblockUtils.mc.field_71439_g.func_96123_co().func_96539_a(1) == null) ? false : ChatFormatting.stripFormatting(SkyblockUtils.mc.field_71439_g.func_96123_co().func_96539_a(1).func_96678_d()).equalsIgnoreCase(paramString);
  }
  
  public HudVec () {
    return null;
  }
  
  public double () {
    return this.;
  }
  
  public Component (double paramDouble1, double paramDouble2) {
    this. = paramDouble1;
    this. = paramDouble2;
    return this;
  }
  
  public void () {}
  
  public double () {
    return this.;
  }
  
  public Component (double paramDouble1, double paramDouble2) {
    this. = paramDouble1;
    this. = paramDouble2;
    return this;
  }
  
  public Component (boolean paramBoolean) {
    if (paramBoolean != this.) {
      this. = paramBoolean;
      this..();
    } 
    return this;
  }
}
