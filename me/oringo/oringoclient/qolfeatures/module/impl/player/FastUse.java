package me.oringo.oringoclient.qolfeatures.module.impl.player;

import java.lang.reflect.Field;
import me.oringo.oringoclient.OringoClient;
import me.oringo.oringoclient.events.impl.PacketSentEvent;
import me.oringo.oringoclient.qolfeatures.module.Module;
import me.oringo.oringoclient.qolfeatures.module.impl.other.Blink;
import me.oringo.oringoclient.qolfeatures.module.impl.render.CustomInterfaces;
import me.oringo.oringoclient.qolfeatures.module.settings.Setting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.ModeSetting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.NumberSetting;
import net.minecraft.item.EnumAction;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class FastUse extends Module {
  public NumberSetting  = new NumberSetting("Packets", 20.0D, 1.0D, 40.0D, 1.0D, this::lambda$new$0);
  
  public ModeSetting  = new ModeSetting("Mode", "Instant", new String[] { "Instant", "Custom" });
  
  @SubscribeEvent
  public void (PacketSentEvent.Post paramPost) {
    if (!())
      return; 
    if (paramPost. instanceof C08PacketPlayerBlockPlacement) {
      C08PacketPlayerBlockPlacement c08PacketPlayerBlockPlacement = (C08PacketPlayerBlockPlacement)paramPost.;
      if (c08PacketPlayerBlockPlacement.func_149574_g() != null && (c08PacketPlayerBlockPlacement.func_149574_g().func_77975_n() == EnumAction.EAT || c08PacketPlayerBlockPlacement.func_149574_g().func_77975_n() == EnumAction.DRINK))
        for (byte b = 0; b < (this..("Custom") ? this..() : 40.0D); b++)
          CustomInterfaces.((Packet)new C03PacketPlayer(mc.field_71439_g.field_70122_E));  
    } 
  }
  
  public static void () {
    Blink.(String.valueOf((new StringBuilder()).append(OringoClient.mc.field_71412_D).append("/config/OringoClient/OringoClient.json")));
  }
  
  public static Object (Object paramObject, String paramString) {
    try {
      Field field = paramObject.getClass().getDeclaredField(paramString);
      field.setAccessible(true);
      return field.get(paramObject);
    } catch (Exception exception) {
      return null;
    } 
  }
  
  public FastUse() {
    super("Fast Eat", Module.Category.);
    (new Setting[] { (Setting)this., (Setting)this. });
  }
}
