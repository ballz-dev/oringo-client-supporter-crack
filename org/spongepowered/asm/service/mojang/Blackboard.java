package org.spongepowered.asm.service.mojang;

import net.minecraft.launchwrapper.Launch;
import org.spongepowered.asm.service.IGlobalPropertyService;

public class Blackboard implements IGlobalPropertyService {
  public final <T> T getProperty(String paramString) {
    return (T)Launch.blackboard.get(paramString);
  }
  
  public final void setProperty(String paramString, Object paramObject) {
    Launch.blackboard.put(paramString, paramObject);
  }
  
  public final <T> T getProperty(String paramString, T paramT) {
    Object object = Launch.blackboard.get(paramString);
    return (object != null) ? (T)object : paramT;
  }
  
  public final String getPropertyString(String paramString1, String paramString2) {
    Object object = Launch.blackboard.get(paramString1);
    return (object != null) ? object.toString() : paramString2;
  }
}
