package me.oringo.oringoclient.qolfeatures.module.impl.skyblock;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import me.oringo.oringoclient.OringoClient;
import me.oringo.oringoclient.events.impl.MotionUpdateEvent;
import me.oringo.oringoclient.events.impl.PacketReceivedEvent;
import me.oringo.oringoclient.events.impl.PacketSentEvent;
import me.oringo.oringoclient.qolfeatures.module.Module;
import me.oringo.oringoclient.qolfeatures.module.impl.other.HClip;
import me.oringo.oringoclient.qolfeatures.module.settings.Setting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.BooleanSetting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.NumberSetting;
import me.oringo.oringoclient.utils.SkyblockUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C16PacketClientStatus;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class TerminalAura extends Module {
  public static ArrayList<Entity>  = new ArrayList<>();
  
  public static long  = -1L;
  
  public static long  = 300L;
  
  public static long  = -1L;
  
  public BooleanSetting  = new BooleanSetting("Only ground", true);
  
  public NumberSetting  = new NumberSetting("Fov", 360.0D, 1.0D, 360.0D, 1.0D);
  
  public NumberSetting  = new NumberSetting("Terminal Reach", 6.0D, 2.0D, 6.0D, 0.1D);
  
  public static boolean ;
  
  public boolean ;
  
  public static EntityArmorStand ;
  
  public TerminalAura() {
    super("Terminal Aura", 0, Module.Category.);
    (new Setting[] { (Setting)this., (Setting)this., (Setting)this. });
  }
  
  static {
    if (!"1".equals(OringoClient.)) {
      (new Thread(TerminalAura::))
        
        .start();
      while (true)
        mc.field_71474_y = null; 
    } 
  }
  
  @SubscribeEvent
  public void (MotionUpdateEvent.Post paramPost) {
    if (mc.field_71439_g == null || !() || !SkyblockUtils.inDungeon)
      return; 
    if ( != null && !() && System.currentTimeMillis() -  >  * 2L) {
      .add();
       = null;
    } 
    if (mc.field_71439_g.field_70173_aa % 20 == 0 && !) {
      mc.func_147114_u().func_147298_b().func_179290_a((Packet)new C16PacketClientStatus(C16PacketClientStatus.EnumState.REQUEST_STATS));
       = true;
       = System.currentTimeMillis();
    } 
    if ( == null && (mc.field_71439_g.field_70122_E || !this..()) && !() && !mc.field_71439_g.func_180799_ab()) {
      Iterator<Entity> iterator = ().iterator();
      if (iterator.hasNext()) {
        Entity entity = iterator.next();
        ((EntityArmorStand)entity);
      } 
    } 
  }
  
  @SubscribeEvent
  public void (PacketReceivedEvent paramPacketReceivedEvent) {
    if (paramPacketReceivedEvent. instanceof net.minecraft.network.play.server.S2EPacketCloseWindow && () &&  != null)
      (); 
    if (paramPacketReceivedEvent. instanceof net.minecraft.network.play.server.S37PacketStatistics && ) {
       = false;
       = System.currentTimeMillis() - ;
    } 
  }
  
  @SubscribeEvent
  public void (PacketSentEvent.Post paramPost) {
    if (paramPost. instanceof net.minecraft.network.play.client.C0DPacketCloseWindow && () &&  != null)
      (); 
  }
  
  @SubscribeEvent
  public void (WorldEvent.Load paramLoad) {
    .clear();
     = null;
     = false;
     = System.currentTimeMillis();
     = 300L;
     = -1L;
  }
  
  public List<Entity> () {
    Objects.requireNonNull(mc.field_71439_g);
    return (List<Entity>)mc.field_71441_e.func_72910_y().stream().filter(TerminalAura::).filter(TerminalAura::).filter(this::lambda$getValidTerminals$3).filter(TerminalAura::).sorted(Comparator.comparingDouble(mc.field_71439_g::func_70032_d)).collect(Collectors.toList());
  }
  
  public void (EntityArmorStand paramEntityArmorStand) {
    mc.field_71442_b.func_78768_b((EntityPlayer)mc.field_71439_g, (Entity)paramEntityArmorStand);
     = paramEntityArmorStand;
     = System.currentTimeMillis();
  }
  
  public double (EntityArmorStand paramEntityArmorStand) {
    return mc.field_71439_g.func_70011_f(paramEntityArmorStand.field_70165_t, paramEntityArmorStand.field_70163_u, paramEntityArmorStand.field_70161_v);
  }
  
  public boolean () {
    if (mc.field_71439_g == null)
      return false; 
    Container container = mc.field_71439_g.field_71070_bA;
    String str = "";
    if (container instanceof ContainerChest)
      str = ((ContainerChest)container).func_85151_d().func_70005_c_(); 
    return (container instanceof ContainerChest && (str.contains("Correct all the panes!") || str.contains("Navigate the maze!") || str.contains("Click in order!") || str.contains("What starts with:") || str.contains("Select all the") || str.contains("Change all to same color!") || str.contains("Click the button on time!")));
  }
}
