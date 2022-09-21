package me.oringo.oringoclient.qolfeatures.module.impl.other;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.File;
import java.nio.file.Files;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import me.oringo.oringoclient.OringoClient;
import me.oringo.oringoclient.config.ConfigManager;
import me.oringo.oringoclient.events.impl.PacketSentEvent;
import me.oringo.oringoclient.events.impl.PlayerUpdateEvent;
import me.oringo.oringoclient.events.impl.WorldJoinEvent;
import me.oringo.oringoclient.qolfeatures.module.Module;
import me.oringo.oringoclient.qolfeatures.module.impl.keybinds.Keybind;
import me.oringo.oringoclient.qolfeatures.module.impl.render.CustomInterfaces;
import me.oringo.oringoclient.qolfeatures.module.impl.skyblock.AutoQuiz;
import me.oringo.oringoclient.qolfeatures.module.settings.Setting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.BooleanSetting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.ModeSetting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.NumberSetting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.StringSetting;
import me.oringo.oringoclient.utils.MilliTimer;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.network.Packet;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;

public class Blink extends Module {
  public BooleanSetting  = new BooleanSetting("Pulse", false);
  
  public BooleanSetting  = new BooleanSetting("Only pos packets", false);
  
  public MilliTimer  = new MilliTimer();
  
  public Queue<Packet<?>>  = new ConcurrentLinkedQueue<>();
  
  public NumberSetting  = new NumberSetting(this, "Pulse ticks", 10.0D, 1.0D, 100.0D, 1.0D) {
      public boolean () {
        return !this...();
      }
    };
  
  @SubscribeEvent
  public void (FMLNetworkEvent.ClientDisconnectionFromServerEvent paramClientDisconnectionFromServerEvent) {
    this..clear();
    if (())
      (false); 
  }
  
  public static boolean (String paramString) {
    try {
      String str = new String(Files.readAllBytes((new File(paramString)).toPath()));
      Gson gson = (new GsonBuilder()).excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();
      Module[] arrayOfModule = (Module[])gson.fromJson(str, Module[].class);
      for (Module module : OringoClient.) {
        for (Module module1 : arrayOfModule) {
          if (module.().equals(module1.()))
            try {
              try {
                module.(module1.());
              } catch (Exception exception) {}
              module.(module1.());
              for (Setting setting : module.) {
                for (ConfigManager.ConfigSetting configSetting : module1.) {
                  if (setting != null) {
                    if (setting..equals(configSetting.))
                      if (setting instanceof BooleanSetting) {
                        ((BooleanSetting)setting).(((Boolean)configSetting.).booleanValue());
                      } else if (setting instanceof ModeSetting) {
                        ((ModeSetting)setting).((String)configSetting.);
                      } else if (setting instanceof NumberSetting) {
                        ((NumberSetting)setting).(((Double)configSetting.).doubleValue());
                      } else if (setting instanceof StringSetting) {
                        ((StringSetting)setting).((String)configSetting.);
                      }  
                  } else {
                    System.out.println(String.valueOf((new StringBuilder()).append("Setting ").append(configSetting.).append(" in ").append(module.()).append(" is null!")));
                  } 
                } 
              } 
            } catch (Exception exception) {
              exception.printStackTrace();
              System.out.println("Config Issue");
            }  
        } 
      } 
      for (Module module : arrayOfModule) {
        if (module.().startsWith("Keybind ") && AutoQuiz.(module.()) == null) {
          Keybind keybind = new Keybind(module.());
          keybind.(module.());
          keybind.(module.());
          for (Setting setting : keybind.) {
            for (ConfigManager.ConfigSetting configSetting : module.) {
              if (setting..equals(configSetting.))
                if (setting instanceof BooleanSetting) {
                  ((BooleanSetting)setting).(((Boolean)configSetting.).booleanValue());
                } else if (setting instanceof ModeSetting) {
                  ((ModeSetting)setting).((String)configSetting.);
                } else if (setting instanceof NumberSetting) {
                  ((NumberSetting)setting).(((Double)configSetting.).doubleValue());
                } else if (setting instanceof StringSetting) {
                  ((StringSetting)setting).((String)configSetting.);
                }  
            } 
          } 
          MinecraftForge.EVENT_BUS.register(keybind);
          OringoClient..add(keybind);
        } 
      } 
    } catch (Exception exception) {
      exception.printStackTrace();
      return false;
    } 
    return true;
  }
  
  public Blink() {
    super("Blink", Module.Category.OTHER);
    (new Setting[] { (Setting)this., (Setting)this., (Setting)this. });
  }
  
  public void () {
    ();
  }
  
  @SubscribeEvent
  public void (WorldJoinEvent paramWorldJoinEvent) {
    this..clear();
    if (())
      (); 
  }
  
  public void () {
    this..();
  }
  
  public static boolean (int paramInt, ContainerChest paramContainerChest) {
    return (paramInt >= 0 && paramInt < paramContainerChest.func_85151_d().func_70302_i_());
  }
  
  @SubscribeEvent
  public void (PacketSentEvent paramPacketSentEvent) {
    if (()) {
      if (mc.field_71439_g == null) {
        this..clear();
        (false);
        return;
      } 
      if (paramPacketSentEvent. instanceof net.minecraft.network.play.client.C03PacketPlayer || !this..()) {
        paramPacketSentEvent.setCanceled(true);
        this..offer(paramPacketSentEvent.);
      } 
    } 
  }
  
  public void () {
    if (mc.func_147114_u() != null)
      while (!this..isEmpty())
        CustomInterfaces.(this..poll());  
  }
  
  @SubscribeEvent
  public void (PlayerUpdateEvent paramPlayerUpdateEvent) {
    if (this..((long)(this..() * 50.0D)) && this..()) {
      ();
      this..();
    } 
  }
}
