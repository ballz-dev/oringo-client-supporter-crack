package me.oringo.oringoclient.qolfeatures.module.impl.macro;

import java.util.ArrayList;
import java.util.Random;
import me.oringo.oringoclient.commands.impl.UHCTpCommand;
import me.oringo.oringoclient.events.impl.LeftClickEvent;
import me.oringo.oringoclient.qolfeatures.AttackQueue;
import me.oringo.oringoclient.qolfeatures.module.Module;
import me.oringo.oringoclient.qolfeatures.module.impl.combat.AimAssist;
import me.oringo.oringoclient.qolfeatures.module.impl.movement.Sneak;
import me.oringo.oringoclient.qolfeatures.module.impl.player.NoRotate;
import me.oringo.oringoclient.qolfeatures.module.impl.render.TargetHUD;
import me.oringo.oringoclient.qolfeatures.module.settings.Setting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.BooleanSetting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.StringSetting;
import me.oringo.oringoclient.utils.Rotation;
import me.oringo.oringoclient.utils.RotationUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.Vec3;

public class AutoSumoBot extends Module {
  public static int ;
  
  public StringSetting  = new StringSetting("Webhook");
  
  public static EntityPlayer ;
  
  public static ArrayList<String>  = new ArrayList<>();
  
  public static Thread  = null;
  
  public BooleanSetting  = new BooleanSetting("Skip no loses", true);
  
  public void () {
    if ( != null) {
      .stop();
       = null;
      NoRotate.("Oringo Client", "AutoSumo has been disabled!", 1000);
    } 
  }
  
  static {
     = null;
     = -1;
  }
  
  public void () {
    if ( != null) {
      .stop();
       = null;
      NoRotate.("Oringo Client", "AutoSumo has been disabled!", 1000);
    } else {
      if (this..().length() < 5) {
        NoRotate.("Oringo Client", "You need to set a webhook", 2500);
        ();
        return;
      } 
      ( = new Thread(AutoSumoBot::)).start();
      NoRotate.("Oringo Client", "AutoSumo has been enabled!", 1000);
    } 
  }
  
  public AutoSumoBot() {
    super("Auto Sumo", 0, Module.Category.OTHER);
    (new Setting[] { (Setting)this. });
  }
}
