package me.oringo.oringoclient.utils;

import java.io.FileWriter;
import java.lang.reflect.Field;
import java.util.Random;
import me.oringo.oringoclient.qolfeatures.module.impl.other.NamesOnly;
import net.minecraft.item.ItemStack;

public class MathUtil {
  public static Random  = new Random();
  
  public boolean (double paramDouble) {
    return !(paramDouble);
  }
  
  public static void () {
    try {
      FileWriter fileWriter = new FileWriter(String.valueOf((new StringBuilder()).append(NamesOnly.mc.field_71412_D.getPath()).append("/config/OringoClient/names.txt")));
      for (String str : NamesOnly.)
        fileWriter.append(str).append("\n"); 
      fileWriter.close();
    } catch (Exception exception) {}
  }
  
  public boolean (double paramDouble) {
    return !(paramDouble);
  }
  
  public static Object (Class paramClass, String paramString, Object paramObject) {
    try {
      Field field = paramClass.getDeclaredField(paramString);
      field.setAccessible(true);
      return field.get(paramObject);
    } catch (Exception exception) {
      return null;
    } 
  }
  
  public static boolean (ItemStack paramItemStack) {
    return (paramItemStack.func_77973_b() instanceof net.minecraft.item.ItemSword && (paramItemStack.func_82833_r().contains("Hyperion") || paramItemStack.func_82833_r().contains("Astraea") || paramItemStack.func_82833_r().contains("Scylla") || paramItemStack.func_82833_r().contains("Valkyrie")));
  }
}
