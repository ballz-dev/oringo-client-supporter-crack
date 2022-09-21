package me.oringo.oringoclient.qolfeatures.module.impl.other;

import javax.swing.JTextArea;
import me.oringo.oringoclient.commands.impl.FireworkCommand;
import me.oringo.oringoclient.commands.impl.StalkCommand;
import me.oringo.oringoclient.events.impl.BlockBoundsEvent;
import me.oringo.oringoclient.events.impl.MotionUpdateEvent;
import me.oringo.oringoclient.events.impl.PacketReceivedEvent;
import me.oringo.oringoclient.qolfeatures.module.Module;
import me.oringo.oringoclient.qolfeatures.module.impl.player.AutoTool;
import me.oringo.oringoclient.qolfeatures.module.settings.Setting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.BooleanSetting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.NumberSetting;
import me.oringo.oringoclient.ui.notifications.Notifications;
import me.oringo.oringoclient.utils.MilliTimer;
import me.oringo.oringoclient.utils.OringoPacketLog;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.network.play.client.C0EPacketClickWindow;
import net.minecraft.util.Vec3;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class VClip extends Module {
  public static NumberSetting  = new NumberSetting("Distance", 7.0D, 1.0D, 100.0D, 1.0D);
  
  public static boolean ;
  
  public static double ;
  
  public static BooleanSetting ;
  
  public static boolean ;
  
  public static MilliTimer ;
  
  public static Vec3 ;
  
  public static BooleanSetting ;
  
  public static MilliTimer ;
  
  public static BooleanSetting ;
  
  public static BooleanSetting  = new BooleanSetting("Use Ender Pearls", true);
  
  public VClip() {
    super("VClip", Module.Category.OTHER);
    (new Setting[] { (Setting), (Setting), (Setting), (Setting), (Setting) });
  }
  
  @SubscribeEvent
  public void (BlockBoundsEvent paramBlockBoundsEvent) {
    if (() && paramBlockBoundsEvent. == mc.field_71439_g && .() && ! && StalkCommand.())
      paramBlockBoundsEvent. = null; 
  }
  
  @SubscribeEvent(receiveCanceled = true)
  public void (PacketReceivedEvent paramPacketReceivedEvent) {
    if (paramPacketReceivedEvent. instanceof net.minecraft.network.play.server.S08PacketPlayerPosLook &&  && !) {
       = true;
      .();
    } 
  }
  
  public static JTextArea () {
    return OringoPacketLog.;
  }
  
  public void () {
    super.();
     = .();
     =  = !.(750L);
     = null;
    .();
  }
  
  public void (MotionUpdateEvent paramMotionUpdateEvent) {
    if (!paramMotionUpdateEvent.isPre())
      return; 
    double d = ;
    if (.())
      d = Math.min(9.5D, d); 
    mc.field_71439_g.func_70107_b(mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u + d, mc.field_71439_g.field_70161_v);
    mc.field_71439_g.func_70016_h(0.0D, 0.0D, 0.0D);
    paramMotionUpdateEvent.setPosition(mc.field_71439_g.func_174791_d());
     -= d;
    if ( == 0.0D)
      (false); 
  }
  
  static {
     = new BooleanSetting("From inventory", false, VClip::);
     = new BooleanSetting("Infinite", true);
     = new BooleanSetting("3D", true);
     = new MilliTimer();
     = new MilliTimer();
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
        } else if (! && !StalkCommand.() && mc.field_71439_g.field_70122_E) {
          int i = AutoTool.(VClip::);
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
}
