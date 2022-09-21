package me.oringo.oringoclient.qolfeatures.module.impl.other;

import me.oringo.oringoclient.OringoClient;
import me.oringo.oringoclient.qolfeatures.module.Module;
import me.oringo.oringoclient.qolfeatures.module.impl.render.Giants;
import me.oringo.oringoclient.qolfeatures.module.settings.Setting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.NumberSetting;
import me.oringo.oringoclient.utils.MilliTimer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.multiplayer.GuiConnecting;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class AutoReconnect extends Module {
  public static NumberSetting  = new NumberSetting("Delay", 500.0D, 250.0D, 1000.0D, 50.0D);
  
  public static MilliTimer  = new MilliTimer();
  
  public static String ;
  
  @SubscribeEvent
  public void (TickEvent.ClientTickEvent paramClientTickEvent) {
    if (paramClientTickEvent.phase == TickEvent.Phase.END)
      return; 
    if (mc.field_71462_r instanceof net.minecraft.client.gui.GuiDisconnected && () &&  != null) {
      if (.(.(), true))
        mc.func_147108_a((GuiScreen)new GuiConnecting((GuiScreen)new GuiMainMenu(), Minecraft.func_71410_x(), new ServerData("name", , false))); 
    } else if (mc.func_147104_D() != null) {
       = (mc.func_147104_D()).field_78845_b;
    } 
  }
  
  @SubscribeEvent
  public void (GuiScreenEvent.ActionPerformedEvent.Pre paramPre) {
    if (() && paramPre.gui instanceof net.minecraft.client.gui.GuiDisconnected && paramPre.button.field_146127_k == -21372137)
      (false); 
  }
  
  public AutoReconnect() {
    super("Auto Reconnect", Module.Category.OTHER);
    (new Setting[] { (Setting) });
  }
  
  @SubscribeEvent
  public void (GuiScreenEvent.InitGuiEvent.Post paramPost) {
    if (paramPost.gui instanceof net.minecraft.client.gui.GuiDisconnected) {
      .();
      ScaledResolution scaledResolution = new ScaledResolution(mc);
      paramPost.buttonList.add(new GuiButton(this, -21372137, scaledResolution.func_78326_a() / 2 - 100, ((GuiButton)paramPost.buttonList.get(0)).field_146129_i + 32, "") {
            public void func_146112_a(Minecraft param1Minecraft, int param1Int1, int param1Int2) {
              this.field_146126_j = String.valueOf((new StringBuilder()).append("Reconnecting in: ").append((int)Math.max(0.0D, AutoReconnect..() - AutoReconnect.().())).append("ms"));
              if (!((AutoReconnect)Giants.(AutoReconnect.class)).()) {
                this.field_146125_m = false;
                return;
              } 
              super.func_146112_a(param1Minecraft, param1Int1, param1Int2);
            }
          });
    } 
  }
  
  public static C03PacketPlayer.C06PacketPlayerPosLook (S08PacketPlayerPosLook paramS08PacketPlayerPosLook) {
    double d1 = paramS08PacketPlayerPosLook.func_148932_c();
    double d2 = paramS08PacketPlayerPosLook.func_148928_d();
    double d3 = paramS08PacketPlayerPosLook.func_148933_e();
    float f1 = paramS08PacketPlayerPosLook.func_148931_f();
    float f2 = paramS08PacketPlayerPosLook.func_148930_g();
    if (paramS08PacketPlayerPosLook.func_179834_f().contains(S08PacketPlayerPosLook.EnumFlags.X))
      d1 += OringoClient.mc.field_71439_g.field_70165_t; 
    if (paramS08PacketPlayerPosLook.func_179834_f().contains(S08PacketPlayerPosLook.EnumFlags.Y))
      d2 += OringoClient.mc.field_71439_g.field_70163_u; 
    if (paramS08PacketPlayerPosLook.func_179834_f().contains(S08PacketPlayerPosLook.EnumFlags.Z))
      d3 += OringoClient.mc.field_71439_g.field_70161_v; 
    if (paramS08PacketPlayerPosLook.func_179834_f().contains(S08PacketPlayerPosLook.EnumFlags.X_ROT))
      f2 += OringoClient.mc.field_71439_g.field_70125_A; 
    if (paramS08PacketPlayerPosLook.func_179834_f().contains(S08PacketPlayerPosLook.EnumFlags.Y_ROT))
      f1 += OringoClient.mc.field_71439_g.field_70177_z; 
    return new C03PacketPlayer.C06PacketPlayerPosLook(d1, d2, d3, f1 % 360.0F, f2 % 360.0F, false);
  }
}
