package me.oringo.oringoclient.commands;

import java.util.ArrayList;
import java.util.Arrays;
import me.oringo.oringoclient.qolfeatures.module.impl.render.TargetHUD;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;

public abstract class Command {
  public String[] ;
  
  public static Minecraft mc = Minecraft.func_71410_x();
  
  public Command(String paramString, String... paramVarArgs) {
    ArrayList<String> arrayList = new ArrayList();
    arrayList.add(paramString);
    arrayList.addAll(Arrays.asList(paramVarArgs));
    this. = arrayList.<String>toArray(new String[0]);
    MinecraftForge.EVENT_BUS.register(this);
  }
  
  public abstract String ();
  
  public String () {
    return ();
  }
  
  public static TargetHUD () {
    return TargetHUD.;
  }
  
  public abstract void (String[] paramArrayOfString) throws Exception;
  
  public String[] () {
    return this.;
  }
}
