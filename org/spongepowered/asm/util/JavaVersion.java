package org.spongepowered.asm.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class JavaVersion {
  private static double current = 0.0D;
  
  public static double current() {
    if (current == 0.0D)
      current = resolveCurrentVersion(); 
    return current;
  }
  
  private static double resolveCurrentVersion() {
    String str = System.getProperty("java.version");
    Matcher matcher = Pattern.compile("[0-9]+\\.[0-9]+").matcher(str);
    if (matcher.find())
      return Double.parseDouble(matcher.group()); 
    return 1.6D;
  }
}
