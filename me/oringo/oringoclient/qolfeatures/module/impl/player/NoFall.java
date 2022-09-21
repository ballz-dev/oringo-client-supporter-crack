package me.oringo.oringoclient.qolfeatures.module.impl.player;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import me.oringo.oringoclient.OringoClient;
import me.oringo.oringoclient.commands.impl.FireworkCommand;
import me.oringo.oringoclient.config.ConfigManager;
import me.oringo.oringoclient.events.impl.MotionUpdateEvent;
import me.oringo.oringoclient.events.impl.MoveHeadingEvent;
import me.oringo.oringoclient.events.impl.PacketSentEvent;
import me.oringo.oringoclient.mixins.packet.C03Accessor;
import me.oringo.oringoclient.qolfeatures.module.Module;
import me.oringo.oringoclient.qolfeatures.module.impl.other.Disabler;
import me.oringo.oringoclient.qolfeatures.module.settings.Setting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.BooleanSetting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.ModeSetting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.NumberSetting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.StringSetting;
import me.oringo.oringoclient.ui.notifications.Notifications;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class NoFall extends Module {
  public ModeSetting  = new ModeSetting("Mode ", "Edit", new String[] { "Edit", "Packet", "No ground" });
  
  @SubscribeEvent
  public void (PacketSentEvent paramPacketSentEvent) {
    if (paramPacketSentEvent. instanceof C03PacketPlayer && super.())
      switch (this..()) {
        case "No ground":
          ((C03Accessor)paramPacketSentEvent.).setOnGround(false);
          break;
      }  
  }
  
  public void () {
    if (!OringoClient..())
      FireworkCommand.("Disabler not enabled", 3000, Notifications.NotificationType.); 
  }
  
  public NoFall() {
    super("No Fall", Module.Category.);
    (new Setting[] { (Setting)this. });
  }
  
  public static void () {
    try {
      DataOutputStream dataOutputStream = new DataOutputStream(new FileOutputStream("config/OringoClient/InventoryManager.cfg"));
      dataOutputStream.writeInt(InvManager..size());
      for (String str : InvManager.)
        dataOutputStream.writeUTF(str); 
      dataOutputStream.close();
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
  }
  
  public boolean () {
    return super.();
  }
  
  @SubscribeEvent(priority = EventPriority.HIGHEST)
  public void (MotionUpdateEvent.Pre paramPre) {
    if (super.())
      switch (this..()) {
        case "Edit":
          if (mc.field_71439_g.field_70143_R > 2.0F && Disabler.)
            paramPre.setOnGround((mc.field_71439_g.field_70173_aa % 2 == 0)); 
          break;
        case "Packet":
          if (mc.field_71439_g.field_70143_R > 2.0F) {
            mc.func_147114_u().func_147298_b().func_179290_a((Packet)new C03PacketPlayer(true));
            mc.field_71439_g.field_70143_R = 0.0F;
          } 
          break;
      }  
  }
  
  public static boolean (String paramString, boolean paramBoolean) {
    Gson gson = (new GsonBuilder()).excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();
    for (Module module : OringoClient.) {
      module.();
      ArrayList<ConfigManager.ConfigSetting> arrayList = new ArrayList();
      for (Setting setting : module.) {
        ConfigManager.ConfigSetting configSetting = new ConfigManager.ConfigSetting(null, null);
        configSetting. = setting.;
        if (setting instanceof BooleanSetting) {
          configSetting. = Boolean.valueOf(((BooleanSetting)setting).());
        } else if (setting instanceof ModeSetting) {
          configSetting. = ((ModeSetting)setting).();
        } else if (setting instanceof NumberSetting) {
          configSetting. = Double.valueOf(((NumberSetting)setting).());
        } else if (setting instanceof StringSetting) {
          configSetting. = ((StringSetting)setting).();
        } 
        arrayList.add(configSetting);
      } 
      module. = arrayList.<ConfigManager.ConfigSetting>toArray(new ConfigManager.ConfigSetting[0]);
    } 
    try {
      File file = new File(paramString);
      Files.write(file.toPath(), gson.toJson(OringoClient.).getBytes(StandardCharsets.UTF_8), new java.nio.file.OpenOption[0]);
      if (paramBoolean)
        try {
          MoveHeadingEvent.();
        } catch (Exception exception) {
          exception.printStackTrace();
        }  
    } catch (Exception exception) {
      exception.printStackTrace();
      return false;
    } 
    return true;
  }
}
