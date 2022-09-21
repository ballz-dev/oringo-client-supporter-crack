package me.oringo.oringoclient.qolfeatures.module.impl.movement;

import com.google.gson.JsonObject;
import me.oringo.oringoclient.events.impl.MotionUpdateEvent;
import me.oringo.oringoclient.events.impl.MoveEvent;
import me.oringo.oringoclient.events.impl.MoveHeadingEvent;
import me.oringo.oringoclient.events.impl.WorldJoinEvent;
import me.oringo.oringoclient.lunarspoof.LunarClient;
import me.oringo.oringoclient.mixins.MinecraftAccessor;
import me.oringo.oringoclient.qolfeatures.module.Module;
import me.oringo.oringoclient.qolfeatures.module.impl.render.HidePlayers;
import me.oringo.oringoclient.qolfeatures.module.settings.Setting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.BooleanSetting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.ModeSetting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.NumberSetting;
import me.oringo.oringoclient.utils.MilliTimer;
import me.oringo.oringoclient.utils.PlayerUtils;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C13PacketPlayerAbilities;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class Flight extends Module {
  public static NumberSetting ;
  
  public double ;
  
  public MilliTimer  = new MilliTimer();
  
  public int ;
  
  public MilliTimer  = new MilliTimer();
  
  public static NumberSetting ;
  
  public static BooleanSetting ;
  
  public static ModeSetting  = new ModeSetting("Mode", "Vanilla", new String[] { "Hypixel Slime", "Vanilla", "Hypixel" });
  
  public static NumberSetting ;
  
  public boolean ;
  
  public static NumberSetting ;
  
  public boolean () {
    return (() && (!.("Hypixel Slime") || !this..((long).())));
  }
  
  public Flight() {
    super("Flight", 0, Module.Category.);
    (new Setting[] { (Setting), (Setting), (Setting), (Setting), (Setting), (Setting) });
  }
  
  @SubscribeEvent
  public void (MoveHeadingEvent paramMoveHeadingEvent) {
    if (!())
      return; 
    if (.("Hypixel") && this.)
      paramMoveHeadingEvent.setOnGround(true); 
  }
  
  @SubscribeEvent(priority = EventPriority.LOWEST)
  public void (MotionUpdateEvent paramMotionUpdateEvent) {
    if (!())
      return; 
    switch (.()) {
      case "Hypixel":
        if (paramMotionUpdateEvent.isPre() && mc.field_71439_g.field_70143_R > 2.0F) {
          paramMotionUpdateEvent.setOnGround(false);
          this. = true;
          float f = (float).();
          if (.())
            f = (float)(f * ((this. < 40) ? 1.5D : ((this. < 70) ? 1.3D : 1.0D))); 
          PlayerUtils.(HidePlayers.() ? f : 1.0F);
          paramMotionUpdateEvent. += this.;
          if (++this. % 2 == 1)
            this. += 1.0E-5D; 
        } 
        break;
    } 
  }
  
  public void () {
    (((MinecraftAccessor)mc).getTimer()).field_74278_d = 1.0F;
    mc.field_71439_g.func_70016_h(0.0D, 0.0D, 0.0D);
  }
  
  static {
     = new NumberSetting("Speed", 1.0D, 0.1D, 10.0D, 0.1D, Flight::);
     = new NumberSetting("Disabler timer", 1200.0D, 250.0D, 2500.0D, 1.0D) {
        public boolean () {
          return !Flight..("Hypixel Slime");
        }
        
        static {
        
        }
      };
     = new NumberSetting("Timer Speed", 1.0D, 0.1D, 10.0D, 0.1D);
     = new NumberSetting("Auto disable", 1500.0D, 0.0D, 5000.0D, 50.0D) {
        public boolean () {
          return !Flight..("Vanilla");
        }
        
        static {
        
        }
      };
     = new BooleanSetting("Timer boost", false, Flight::);
  }
  
  @SubscribeEvent
  public void (MoveEvent paramMoveEvent) {
    if (())
      switch (.()) {
        case "Hypixel Slime":
          if (mc.field_71439_g.field_71075_bZ.field_75101_c) {
            if (mc.field_71439_g.field_70173_aa % 6 == 0 || !this. || this..((long).() - 150L)) {
              PlayerCapabilities playerCapabilities = new PlayerCapabilities();
              playerCapabilities.field_75101_c = true;
              playerCapabilities.field_75100_b = false;
              mc.func_147114_u().func_147298_b().func_179290_a((Packet)new C13PacketPlayerAbilities(playerCapabilities));
              playerCapabilities.field_75100_b = true;
              mc.func_147114_u().func_147298_b().func_179290_a((Packet)new C13PacketPlayerAbilities(playerCapabilities));
              this. = true;
              this..();
            } 
          } else if (this..((long).())) {
            if (this.) {
              mc.field_71439_g.func_70016_h(0.0D, 0.0D, 0.0D);
              this. = false;
              (((MinecraftAccessor)mc).getTimer()).field_74278_d = 1.0F;
            } 
            break;
          } 
        case "Vanilla":
          if (.("Vanilla") && this..((long).()) && .() != 0.0D) {
            (false);
            return;
          } 
          PlayerUtils.((float).());
          paramMoveEvent.setY(0.0D);
          LunarClient.(paramMoveEvent, .());
          if (mc.field_71474_y.field_74314_A.func_151470_d())
            paramMoveEvent.setY(paramMoveEvent.getY() + .()); 
          if (mc.field_71474_y.field_74311_E.func_151470_d())
            paramMoveEvent.setY(paramMoveEvent.getY() - .()); 
          break;
        case "Hypixel":
          if (this.) {
            paramMoveEvent.setY(0.0D);
            mc.field_71439_g.field_70181_x = 0.0D;
          } 
          break;
      }  
  }
  
  public static int (JsonObject paramJsonObject) {
    try {
      return paramJsonObject.getAsJsonObject("stats").getAsJsonObject("Duels").get("current_sumo_winstreak").getAsInt();
    } catch (Exception exception) {
      return 0;
    } 
  }
  
  public void () {
    this. = false;
    this. = 0;
    this. = 1.0E-4D;
    this..();
    if (.("Hypixel"))
      WorldJoinEvent.("Jump of", 4000); 
  }
}
