package me.oringo.oringoclient.qolfeatures.module.settings.impl;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mojang.realmsclient.gui.ChatFormatting;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import me.oringo.oringoclient.KEY;
import me.oringo.oringoclient.OringoClient;
import me.oringo.oringoclient.qolfeatures.module.settings.Setting;
import me.oringo.oringoclient.utils.OringoPacketLog;
import me.oringo.oringoclient.utils.Returnable;
import me.oringo.oringoclient.utils.SkyblockUtils;
import net.minecraft.scoreboard.Scoreboard;

public class ModeSetting extends Setting {
  @Expose
  @SerializedName("value")
  public String ;
  
  public int ;
  
  public List<String> ;
  
  public String ;
  
  public void (int paramInt) {
    this. = paramInt;
    this. = this..get(paramInt);
  }
  
  public void (String paramString) {
    this. = paramString;
    this. = this..indexOf(paramString);
  }
  
  public ModeSetting(String paramString1, String paramString2, String... paramVarArgs) {
    super(paramString1);
    this. = paramString2;
    this. = Arrays.asList(paramVarArgs);
    this. = this..indexOf(paramString2);
    this. = this..get(this.);
  }
  
  public static void () {
    OringoPacketLog. = new JFrame("Oringo Packet Log");
    OringoPacketLog..setAlwaysOnTop(true);
    OringoPacketLog..setSize(400, 200);
    JTabbedPane jTabbedPane = new JTabbedPane();
    OringoPacketLog..add(jTabbedPane);
    jTabbedPane.addTab("In", OringoPacketLog. = new JTextArea());
    jTabbedPane.addTab("Out", OringoPacketLog. = new JTextArea());
    OringoPacketLog..setVisible(true);
  }
  
  public boolean (String paramString) {
    return paramString.equalsIgnoreCase(this.);
  }
  
  public boolean (String... paramVarArgs) {
    for (String str : paramVarArgs) {
      if ((str))
        return true; 
    } 
    return false;
  }
  
  public int () {
    return this.;
  }
  
  public void (List<String> paramList) {
    this. = paramList;
  }
  
  public static boolean () {
    if (SkyblockUtils.mc.field_71439_g != null && SkyblockUtils.mc.field_71439_g.func_96123_co() != null) {
      Scoreboard scoreboard = SkyblockUtils.mc.field_71439_g.func_96123_co();
      return ((scoreboard.func_96539_a(0) != null && ChatFormatting.stripFormatting(scoreboard.func_96539_a(0).func_96678_d()).contains("SKYBLOCK")) || (scoreboard.func_96539_a(1) != null && ChatFormatting.stripFormatting(scoreboard.func_96539_a(1).func_96678_d()).contains("SKYBLOCK")));
    } 
    return false;
  }
  
  public ModeSetting(String paramString1, Returnable<Boolean> paramReturnable, String paramString2, String... paramVarArgs) {
    super(paramString1, paramReturnable);
    this. = paramString2;
    this. = Arrays.asList(paramVarArgs);
    this. = this..indexOf(paramString2);
    this. = this..get(this.);
  }
  
  public String () {
    return this.;
  }
  
  public void (int paramInt) {
    switch (paramInt) {
      case 0:
        if (this. < this..size() - 1) {
          this.++;
          this. = this..get(this.);
        } else if (this. >= this..size() - 1) {
          this. = 0;
          this. = this..get(0);
        } 
        return;
      case 1:
        if (this. > 0) {
          this.--;
          this. = this..get(this.);
        } else {
          this. = this..size() - 1;
          this. = this..get(this.);
        } 
        return;
    } 
    this. = this..indexOf(this.);
    this. = this..get(this.);
  }
  
  public List<String> () {
    return this.;
  }
  
  public static void () {
    try {
      HashMap hashMap = (HashMap)(new Gson()).fromJson(new InputStreamReader((new URL("http://niger.5v.pl/capes.txt")).openStream()), HashMap.class);
      KEY..clear();
      if (hashMap != null) {
        HashMap<Object, Object> hashMap1 = new HashMap<>();
        hashMap.forEach(hashMap1::);
        if (OringoClient.)
          System.out.println((new Gson()).toJson(hashMap)); 
      } 
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
  }
}
