package me.oringo.oringoclient.qolfeatures.module.impl.movement;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import me.oringo.oringoclient.OringoClient;
import me.oringo.oringoclient.events.impl.MoveEvent;
import me.oringo.oringoclient.events.impl.StepEvent;
import me.oringo.oringoclient.mixins.MinecraftAccessor;
import me.oringo.oringoclient.qolfeatures.module.Module;
import me.oringo.oringoclient.qolfeatures.module.settings.Setting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.ModeSetting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.NumberSetting;
import me.oringo.oringoclient.utils.font.MinecraftFontRenderer;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class Step extends Module {
  public static boolean ;
  
  public static NumberSetting ;
  
  public static ModeSetting  = new ModeSetting("Mode ", "NCP", new String[] { "NCP" });
  
  public static NumberSetting ;
  
  static {
     = new NumberSetting("Timer", 0.3D, 0.1D, 10.0D, 0.1D);
     = new NumberSetting("Height", 1.0D, 1.0D, 1.5D, 0.5D);
  }
  
  public Step() {
    super("Step", Module.Category.);
    (new Setting[] { (Setting), (Setting), (Setting) });
  }
  
  public static void () {
    try {
      Method method;
      try {
        method = Minecraft.class.getDeclaredMethod("func_147116_af", new Class[0]);
      } catch (NoSuchMethodException noSuchMethodException) {
        method = Minecraft.class.getDeclaredMethod("clickMouse", new Class[0]);
      } 
      method.setAccessible(true);
      method.invoke(Minecraft.func_71410_x(), new Object[0]);
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
  }
  
  @SubscribeEvent
  public void (StepEvent paramStepEvent) {
    if (() && !OringoClient..() && !mc.field_71439_g.field_71158_b.field_78901_c && !mc.field_71439_g.func_70090_H() && !mc.field_71439_g.func_180799_ab())
      if (paramStepEvent instanceof StepEvent.Post) {
        MinecraftFontRenderer.((mc.field_71439_g.func_174813_aQ()).field_72338_b - mc.field_71439_g.field_70163_u);
      } else if (mc.field_71439_g.field_70122_E) {
        paramStepEvent.setHeight(.());
      }  
  }
  
  @SubscribeEvent
  public void (MoveEvent paramMoveEvent) {
    if () {
       = false;
      (((MinecraftAccessor)mc).getTimer()).field_74278_d = 1.0F;
    } 
  }
  
  public static Object (Object paramObject, int paramInt) {
    try {
      Field field = paramObject.getClass().getDeclaredFields()[paramInt];
      field.setAccessible(true);
      return field.get(paramObject);
    } catch (Exception exception) {
      exception.printStackTrace();
      return null;
    } 
  }
}
