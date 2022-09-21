package me.oringo.oringoclient.qolfeatures.module.impl.movement;

import me.oringo.oringoclient.commands.impl.FireworkCommand;
import me.oringo.oringoclient.events.impl.MotionUpdateEvent;
import me.oringo.oringoclient.events.impl.MoveStateUpdateEvent;
import me.oringo.oringoclient.events.impl.PacketReceivedEvent;
import me.oringo.oringoclient.mixins.MinecraftAccessor;
import me.oringo.oringoclient.qolfeatures.module.Module;
import me.oringo.oringoclient.qolfeatures.module.impl.other.Disabler;
import me.oringo.oringoclient.qolfeatures.module.impl.player.NoRotate;
import me.oringo.oringoclient.qolfeatures.module.impl.render.Giants;
import me.oringo.oringoclient.qolfeatures.module.impl.render.HidePlayers;
import me.oringo.oringoclient.qolfeatures.module.impl.skyblock.AutoMask;
import me.oringo.oringoclient.qolfeatures.module.settings.Setting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.BooleanSetting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.NumberSetting;
import me.oringo.oringoclient.ui.hud.DraggableComponent;
import me.oringo.oringoclient.ui.notifications.Notifications;
import me.oringo.oringoclient.utils.MilliTimer;
import me.oringo.oringoclient.utils.SkyblockUtils;
import me.oringo.oringoclient.utils.font.CFont;
import net.minecraft.potion.Potion;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class Speed extends Module {
  public BooleanSetting  = new BooleanSetting("Disable on flag", true);
  
  public static MilliTimer  = new MilliTimer();
  
  public BooleanSetting  = new BooleanSetting("Stop on disable", true);
  
  public NumberSetting  = new NumberSetting("Friction", 0.7D, 0.5D, 1.0D, 0.01D);
  
  public NumberSetting  = new NumberSetting("Timer", 1.0D, 1.0D, 3.0D, 0.01D);
  
  public NumberSetting  = new NumberSetting("SneakTimer", 1.0D, 1.0D, 3.0D, 0.05D, this::lambda$new$0);
  
  public BooleanSetting  = new BooleanSetting("Sneak timer", true);
  
  @SubscribeEvent(receiveCanceled = true)
  public void (PacketReceivedEvent paramPacketReceivedEvent) {
    if (paramPacketReceivedEvent. instanceof net.minecraft.network.play.server.S08PacketPlayerPosLook && this..()) {
      if (!AutoMask.() && ()) {
        NoRotate.("Oringo Client", "Disabled speed due to a flag", 1500);
        (((MinecraftAccessor)mc).getTimer()).field_74278_d = 1.0F;
        if (mc.field_71439_g != null) {
          mc.field_71439_g.field_70159_w = 0.0D;
          mc.field_71439_g.field_70179_y = 0.0D;
        } 
      } 
      .();
    } 
  }
  
  @SubscribeEvent
  public void (MoveStateUpdateEvent paramMoveStateUpdateEvent) {
    if (() && !AutoMask.() && !((Sneak)Giants.(Sneak.class)).())
      paramMoveStateUpdateEvent.setSneak(false); 
  }
  
  public Speed() {
    super("Speed", Module.Category.);
    (new Setting[] { (Setting)this., (Setting)this., (Setting)this., (Setting)this., (Setting)this., (Setting)this. });
  }
  
  @SubscribeEvent(priority = EventPriority.LOWEST)
  public void (MotionUpdateEvent.Pre paramPre) {
    if (() && !AutoMask.()) {
      if (HidePlayers.()) {
        double d = CFont.();
        if (mc.field_71439_g.field_70122_E) {
          mc.field_71439_g.func_70664_aZ();
          mc.field_71439_g.field_70181_x = (mc.field_71439_g.field_70123_F ? 0.42F : 0.4F);
        } else {
          double d1 = (mc.field_71439_g.func_70644_a(Potion.field_76424_c) ? (mc.field_71439_g.func_70660_b(Potion.field_76424_c).func_76458_c() + 1) : false);
          if (mc.field_71439_g.field_70737_aN > 4 && !mc.field_71439_g.func_70027_ad() && !mc.field_71439_g.func_70644_a(Potion.field_76436_u))
            d *= Math.max(1.0D, 1.1D - 0.02D * d1); 
          d *= 1.0D + 0.02D * d1;
          double d2 = DraggableComponent.();
          mc.field_71439_g.field_70159_w -= (mc.field_71439_g.field_70159_w - -Math.sin(d2) * d) * this..();
          mc.field_71439_g.field_70179_y -= (mc.field_71439_g.field_70179_y - Math.cos(d2) * d) * this..();
        } 
      } else {
        mc.field_71439_g.field_70159_w = 0.0D;
        mc.field_71439_g.field_70179_y = 0.0D;
      } 
      (((MinecraftAccessor)mc).getTimer()).field_74278_d = (float)((this..() && mc.field_71474_y.field_74311_E.func_151470_d()) ? this..() : this..());
    } 
  }
  
  public void () {
    if (Disabler.() != null)
      (((MinecraftAccessor)mc).getTimer()).field_74278_d = 1.0F; 
    if (mc.field_71439_g != null && this..()) {
      mc.field_71439_g.field_70159_w = 0.0D;
      mc.field_71439_g.field_70179_y = 0.0D;
    } 
  }
  
  public void () {
    if (SkyblockUtils.onSkyblock)
      FireworkCommand.("Speed doesn't bypass on skyblock!", 5000, Notifications.NotificationType.); 
  }
  
  public static float (float paramFloat) {
    return AutoMask.(paramFloat, 0.1F);
  }
}
