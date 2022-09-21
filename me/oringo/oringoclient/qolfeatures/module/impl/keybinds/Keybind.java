package me.oringo.oringoclient.qolfeatures.module.impl.keybinds;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.oringo.oringoclient.OringoClient;
import me.oringo.oringoclient.lunarspoof.LunarClient;
import me.oringo.oringoclient.qolfeatures.module.Module;
import me.oringo.oringoclient.qolfeatures.module.impl.movement.NoSlow;
import me.oringo.oringoclient.qolfeatures.module.impl.movement.Sneak;
import me.oringo.oringoclient.qolfeatures.module.impl.movement.Step;
import me.oringo.oringoclient.qolfeatures.module.impl.other.Disabler;
import me.oringo.oringoclient.qolfeatures.module.impl.other.KillInsults;
import me.oringo.oringoclient.qolfeatures.module.settings.Setting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.BooleanSetting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.ModeSetting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.NumberSetting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.RunnableSetting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.StringSetting;
import me.oringo.oringoclient.utils.MilliTimer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class Keybind extends Module {
  public MilliTimer  = new MilliTimer();
  
  public BooleanSetting  = new BooleanSetting("From inventory", false);
  
  public RunnableSetting  = new RunnableSetting("Remove keybinding", this::lambda$new$0);
  
  public boolean ;
  
  public ModeSetting  = new ModeSetting("Button", "Right", new String[] { "Right", "Left", "Swing" });
  
  public ModeSetting  = new ModeSetting("Mode", "Normal", new String[] { "Normal", "Rapid", "Toggle" });
  
  public NumberSetting  = new NumberSetting("Click Count", 1.0D, 1.0D, 64.0D, 1.0D);
  
  public StringSetting  = new StringSetting("Item");
  
  public NumberSetting  = new NumberSetting("Delay", 50.0D, 0.0D, 5000.0D, 1.0D);
  
  public boolean ;
  
  @SubscribeEvent
  public void (RenderWorldLastEvent paramRenderWorldLastEvent) {
    boolean bool = ();
    if ((bool || this.) && () && !this..().equals("") && mc.field_71462_r == null && this..((long)this..()) && (this..("Rapid") || !this. || (this..("Toggle") && this.)))
      if (!this..() || !Disabler.) {
        for (byte b = 0; b < 9; b++) {
          if (mc.field_71439_g.field_71071_by.func_70301_a(b) != null && ChatFormatting.stripFormatting(mc.field_71439_g.field_71071_by.func_70301_a(b).func_82833_r()).toLowerCase().contains(this..().toLowerCase())) {
            int i = mc.field_71439_g.field_71071_by.field_70461_c;
            LunarClient.(b);
            ();
            LunarClient.(i);
            break;
          } 
        } 
      } else {
        int i = KillInsults.(this..());
        if (i != -1)
          if (i >= 36) {
            int j = mc.field_71439_g.field_71071_by.field_70461_c;
            LunarClient.(i - 36);
            ();
            LunarClient.(j);
          } else {
            (i, mc.field_71439_g.field_71071_by.field_70461_c);
            ();
            (i, mc.field_71439_g.field_71071_by.field_70461_c);
          }  
      }  
    if (this..("Toggle") && !this. && bool && ())
      this. = !this.; 
    this. = bool;
  }
  
  public void () {
    for (byte b = 0; b < this..(); b++) {
      switch (this..()) {
        case "Left":
          Step.();
          break;
        case "Right":
          mc.func_147114_u().func_147298_b().func_179290_a((Packet)new C08PacketPlayerBlockPlacement(mc.field_71439_g.func_70694_bm()));
          break;
        case "Swing":
          mc.field_71439_g.func_71038_i();
          break;
      } 
    } 
    this..();
  }
  
  public Keybind(String paramString) {
    super(paramString, Module.Category.);
    (new Setting[] { (Setting)this., (Setting)this., (Setting)this., (Setting)this., (Setting)this., (Setting)this. });
  }
  
  public boolean () {
    return true;
  }
  
  public static void (String paramString) {
    Sneak.(paramString);
  }
  
  public void (int paramInt1, int paramInt2) {
    mc.field_71442_b.func_78753_a(mc.field_71439_g.field_71069_bz.field_75152_c, paramInt1, paramInt2, 2, (EntityPlayer)mc.field_71439_g);
  }
  
  public String () {
    return this..().equals("") ? String.valueOf((new StringBuilder()).append("Keybind ").append(NoSlow.(Module.Category.).indexOf(this) + 1)) : this..();
  }
}
