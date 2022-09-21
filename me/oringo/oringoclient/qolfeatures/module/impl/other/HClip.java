package me.oringo.oringoclient.qolfeatures.module.impl.other;

import com.mojang.realmsclient.gui.ChatFormatting;
import java.lang.reflect.Field;
import me.oringo.oringoclient.OringoClient;
import me.oringo.oringoclient.commands.CommandHandler;
import me.oringo.oringoclient.commands.impl.FireworkCommand;
import me.oringo.oringoclient.events.impl.BlockBoundsEvent;
import me.oringo.oringoclient.events.impl.MotionUpdateEvent;
import me.oringo.oringoclient.events.impl.PacketReceivedEvent;
import me.oringo.oringoclient.qolfeatures.module.Module;
import me.oringo.oringoclient.qolfeatures.module.impl.player.AutoTool;
import me.oringo.oringoclient.qolfeatures.module.impl.player.InvManager;
import me.oringo.oringoclient.qolfeatures.module.impl.render.FullBright;
import me.oringo.oringoclient.qolfeatures.module.impl.render.PopupAnimation;
import me.oringo.oringoclient.qolfeatures.module.settings.Setting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.BooleanSetting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.NumberSetting;
import me.oringo.oringoclient.ui.notifications.Notifications;
import me.oringo.oringoclient.utils.MilliTimer;
import me.oringo.oringoclient.utils.Rotation;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.network.play.client.C0EPacketClickWindow;
import net.minecraft.util.Vec3;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class HClip extends Module {
  public static MilliTimer ;
  
  public static NumberSetting  = new NumberSetting("Distance", 7.0D, 1.0D, 100.0D, 1.0D);
  
  public static BooleanSetting  = new BooleanSetting("Use Ender Pearls", true);
  
  public static BooleanSetting ;
  
  public static BooleanSetting  = new BooleanSetting("From inventory", false, HClip::);
  
  public static double ;
  
  public static MilliTimer ;
  
  public static BooleanSetting ;
  
  public static Vec3 ;
  
  public static boolean ;
  
  public static boolean ;
  
  static {
     = new BooleanSetting("Infinite", true);
     = new BooleanSetting("3D", true);
     = new MilliTimer();
     = new MilliTimer();
  }
  
  public void () {
    super.();
     = .();
     =  = !.(750L);
     = null;
    .();
  }
  
  @SubscribeEvent(receiveCanceled = true)
  public void (PacketReceivedEvent paramPacketReceivedEvent) {
    if (paramPacketReceivedEvent. instanceof net.minecraft.network.play.server.S08PacketPlayerPosLook &&  && !) {
       = true;
      .();
    } 
  }
  
  public static boolean (GuiScreen paramGuiScreen) {
    return (OringoClient..() && ((paramGuiScreen instanceof me.oringo.oringoclient.ui.gui.ClickGUI && PopupAnimation..()) || (paramGuiScreen instanceof net.minecraft.client.gui.inventory.GuiInventory && PopupAnimation..()) || (paramGuiScreen instanceof net.minecraft.client.gui.inventory.GuiChest && PopupAnimation..())));
  }
  
  public HClip() {
    super("HClip", Module.Category.OTHER);
    (new Setting[] { (Setting), (Setting), (Setting), (Setting), (Setting) });
  }
  
  public static Rotation (Entity paramEntity) {
    return CommandHandler.(paramEntity.field_70165_t, paramEntity.field_70163_u + paramEntity.func_70047_e() / 2.0D, paramEntity.field_70161_v);
  }
  
  @SubscribeEvent
  public void (BlockBoundsEvent paramBlockBoundsEvent) {
    if (() && paramBlockBoundsEvent. == mc.field_71439_g && .() && ! && InvManager.())
      paramBlockBoundsEvent. = null; 
  }
  
  public static void () {
    Framebuffer framebuffer = Minecraft.func_71410_x().func_147110_a();
    if (framebuffer != null && framebuffer.field_147624_h > -1) {
      FullBright.(framebuffer);
      framebuffer.field_147624_h = -1;
    } 
  }
  
  public static String (String paramString) {
    return paramString.contains("-") ? paramString : paramString.replaceAll("-", "").replaceFirst("(\\p{XDigit}{8})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}+)", "$1-$2-$3-$4-$5");
  }
  
  public static boolean (Entity paramEntity) {
    String str = ChatFormatting.stripFormatting(paramEntity.func_70005_c_()).toLowerCase();
    for (String str1 : NamesOnly.) {
      if (str1.contains(str))
        return true; 
    } 
    return false;
  }
  
  public static void (Object paramObject1, int paramInt, Object paramObject2) {
    try {
      Field field = paramObject1.getClass().getDeclaredFields()[paramInt];
      field.setAccessible(true);
      field.set(paramObject1, paramObject2);
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
  }
  
  @SubscribeEvent
  public void (MotionUpdateEvent paramMotionUpdateEvent) {
    if (()) {
      if (.(2500L))
        (false); 
      if (.()) {
        if (paramMotionUpdateEvent.isPre()) {
          if (!) {
            paramMotionUpdateEvent.setPitch(90.0F);
             = mc.field_71439_g.func_174791_d();
          } else if () {
            if ( == null) {
              (paramMotionUpdateEvent);
            } else {
              mc.field_71439_g.func_70107_b(.field_72450_a, .field_72448_b, .field_72449_c);
              paramMotionUpdateEvent.setPosition();
               = null;
            } 
          } 
        } else if (! && !InvManager.() && mc.field_71439_g.field_70122_E) {
          int i = AutoTool.(HClip::);
          if (i == -1 || (i < 36 && (!.() || !Disabler.))) {
            FireworkCommand.("Unable to find an Ender Pearl!", 2000, Notifications.NotificationType.);
            (false);
            return;
          } 
          if (i >= 36) {
            mc.func_147114_u().func_147298_b().func_179290_a((Packet)new C09PacketHeldItemChange(i - 36));
            mc.func_147114_u().func_147298_b().func_179290_a((Packet)new C08PacketPlayerBlockPlacement(mc.field_71439_g.field_71069_bz.func_75139_a(i).func_75211_c()));
            mc.func_147114_u().func_147298_b().func_179290_a((Packet)new C09PacketHeldItemChange(mc.field_71439_g.field_71071_by.field_70461_c));
          } else {
            short s = mc.field_71439_g.field_71070_bA.func_75136_a(mc.field_71439_g.field_71071_by);
            mc.func_147114_u().func_147298_b().func_179290_a((Packet)new C0EPacketClickWindow(mc.field_71439_g.field_71070_bA.field_75152_c, i, mc.field_71439_g.field_71071_by.field_70461_c, 2, null, s));
            mc.func_147114_u().func_147298_b().func_179290_a((Packet)new C08PacketPlayerBlockPlacement(mc.field_71439_g.field_71069_bz.func_75139_a(i).func_75211_c()));
            mc.func_147114_u().func_147298_b().func_179290_a((Packet)new C0EPacketClickWindow(mc.field_71439_g.field_71070_bA.field_75152_c, i, mc.field_71439_g.field_71071_by.field_70461_c, 2, null, (short)(s + 1)));
          } 
           = true;
        } 
      } else {
        (paramMotionUpdateEvent);
      } 
    } 
  }
  
  public void (MotionUpdateEvent paramMotionUpdateEvent) {
    if (!paramMotionUpdateEvent.isPre())
      return; 
    double d1 = ;
    if (.())
      d1 = Math.min(9.5D, d1); 
    double d2 = Math.toRadians(mc.field_71439_g.field_70177_z);
    double d3 = Math.toRadians(.() ? mc.field_71439_g.field_70125_A : 0.0D);
    mc.field_71439_g.func_70107_b(mc.field_71439_g.field_70165_t + -Math.sin(d2) * d1 * Math.cos(d3), mc.field_71439_g.field_70163_u + d1 * -Math.sin(d3), mc.field_71439_g.field_70161_v + Math.cos(d2) * d1 * Math.cos(d3));
    mc.field_71439_g.func_70016_h(0.0D, 0.0D, 0.0D);
    paramMotionUpdateEvent.setPosition(mc.field_71439_g.func_174791_d());
     -= d1;
    if ( == 0.0D)
      (false); 
  }
}
