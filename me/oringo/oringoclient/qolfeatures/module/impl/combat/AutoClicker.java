package me.oringo.oringoclient.qolfeatures.module.impl.combat;

import me.oringo.oringoclient.OringoClient;
import me.oringo.oringoclient.qolfeatures.module.Module;
import me.oringo.oringoclient.qolfeatures.module.impl.player.AutoHeal;
import me.oringo.oringoclient.qolfeatures.module.settings.Setting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.ModeSetting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.NumberSetting;
import me.oringo.oringoclient.utils.MilliTimer;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class AutoClicker extends Module {
  public MilliTimer  = new MilliTimer();
  
  public static NumberSetting  = new NumberSetting("Max CPS", 12.0D, 1.0D, 100.0D, 1.0D) {
      static {
      
      }
      
      public void (double param1Double) {
        super.(param1Double);
        if (AutoClicker..() > ())
          AutoClicker..(()); 
      }
    };
  
  public static ModeSetting ;
  
  public static NumberSetting  = new NumberSetting("Min CPS", 10.0D, 1.0D, 100.0D, 1.0D) {
      static {
      
      }
      
      public void (double param1Double) {
        super.(param1Double);
        if (AutoClicker..() < ())
          AutoClicker..(()); 
      }
    };
  
  public double  = 10.0D;
  
  public AutoClicker() {
    super("Auto Clicker", Module.Category.);
    (new Setting[] { (Setting), (Setting), (Setting) });
  }
  
  public boolean () {
    switch (.()) {
      case "Key held":
        return super.();
      case "Toggle":
        return ();
    } 
    return mc.field_71474_y.field_74312_F.func_151470_d();
  }
  
  @SubscribeEvent
  public void (RenderWorldLastEvent paramRenderWorldLastEvent) {
    if (() && mc.field_71439_g != null && super.() && !mc.field_71439_g.func_71039_bw() && mc.field_71462_r == null && this..((long)(1000.0D / this.))) {
      this..();
      this. = AutoHeal.(.(), .());
      KeyBinding.func_74507_a(mc.field_71474_y.field_74312_F.func_151463_i());
    } 
  }
  
  public static boolean (Entity paramEntity) {
    return (Criticals.mc.field_71439_g.field_70122_E && Criticals. && !OringoClient..() && paramEntity instanceof EntityLivingBase && (paramEntity instanceof net.minecraft.client.entity.EntityOtherPlayerMP || Criticals..()) && ((EntityLivingBase)paramEntity).field_70737_aN <= Criticals..() && ((EntityLivingBase)paramEntity).field_70737_aN != -1 && Criticals..((long)Criticals..()));
  }
  
  static {
     = new ModeSetting("Mode", "Attack held", new String[] { "Key held", "Toggle", "Attack held" });
  }
  
  public boolean () {
    return !.("Toggle");
  }
}
