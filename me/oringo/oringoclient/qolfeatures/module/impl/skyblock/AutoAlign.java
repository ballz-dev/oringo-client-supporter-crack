package me.oringo.oringoclient.qolfeatures.module.impl.skyblock;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import me.oringo.oringoclient.commands.impl.SayCommand;
import me.oringo.oringoclient.events.impl.MotionUpdateEvent;
import me.oringo.oringoclient.qolfeatures.module.Module;
import me.oringo.oringoclient.qolfeatures.module.impl.other.AntiNicker;
import me.oringo.oringoclient.qolfeatures.module.settings.Setting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.ModeSetting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.NumberSetting;
import me.oringo.oringoclient.utils.MilliTimer;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class AutoAlign extends Module {
  public static boolean ;
  
  public static NumberSetting ;
  
  public static ModeSetting  = new ModeSetting("Mode", "Fast Click", new String[] { "Instant", "Fast Click" });
  
  public static MilliTimer ;
  
  public static MilliTimer ;
  
  public static NumberSetting ;
  
  public static List<BlockPos> ;
  
  public static List<BlockPos> ;
  
  public static ArrayList<MoveData> ;
  
  public static NumberSetting  = new NumberSetting("Distance", 6.0D, 1.0D, 6.0D, 0.1D, AutoAlign::);
  
  public void () {
    .clear();
  }
  
  static {
     = new NumberSetting("Delay", 250.0D, 0.0D, 500.0D, 50.0D, AutoAlign::);
     = new NumberSetting("Scan delay", 1500.0D, 250.0D, 2500.0D, 50.0D);
    ArrayList<BlockPos> arrayList = new ArrayList();
    Objects.requireNonNull(arrayList);
    BlockPos.func_177980_a(new BlockPos(-2, 125, 79), new BlockPos(-2, 121, 75)).forEach(arrayList::add);
     = arrayList;
     = new ArrayList<>();
     = new MilliTimer();
     = new MilliTimer();
     = new ArrayList<>();
  }
  
  @SubscribeEvent
  public void (MotionUpdateEvent.Post paramPost) {
    if (!() || mc.field_71439_g.func_174818_b(new BlockPos(-2, 125, 79)) > 625.0D)
      return; 
    if (.(.(), true) || .isEmpty()) {
      .clear();
      .clear();
      List list1 = mc.field_71441_e.func_175644_a(EntityItemFrame.class, AutoAlign::);
      List list2 = (List)list1.stream().filter(AutoAlign::).collect(Collectors.toList());
       = (list2.stream().filter(AutoAlign::).count() == 2L && list2.stream().filter(AutoAlign::).count() == 1L);
      list1.sort(Comparator.comparingDouble(AutoAlign::));
      for (EntityItemFrame entityItemFrame : list2) {
        if (entityItemFrame.func_82335_i().func_77960_j() == ( ? 14 : 5))
          SayCommand.(entityItemFrame, 8, null, list1); 
      } 
    } 
    if (.("Instant") && .(.())) {
      List<MoveData> list = (List).stream().filter(AutoAlign::).collect(Collectors.toList());
      if (!list.isEmpty()) {
        ((MoveData)list.get(0)).();
        .();
      } 
    } else if (.("Fast Click") && mc.field_147125_j instanceof EntityItemFrame) {
      for (MoveData moveData : ) {
        if (!moveData.() || moveData. != mc.field_147125_j)
          continue; 
        moveData.();
        return;
      } 
    } 
  }
  
  public AutoAlign() {
    super("Auto Align", Module.Category.);
    (new Setting[] { (Setting), (Setting), (Setting), (Setting) });
  }
  
  @SubscribeEvent
  public void (WorldEvent.Load paramLoad) {
    .clear();
    .clear();
  }
  
  public static class MoveData {
    public int ;
    
    public boolean ;
    
    public EntityItemFrame ;
    
    public void () {
      while (this..func_82333_j() != this.)
        (); 
      this. = true;
    }
    
    public boolean () {
      return ((AutoAlign.()).field_71439_g.func_70032_d((Entity)this.) < AutoAlign..());
    }
    
    public void () {
      this..func_82336_g(this..func_82333_j() + 1);
      AutoAlign.((Packet)new C02PacketUseEntity((Entity)this., C02PacketUseEntity.Action.INTERACT));
    }
    
    public boolean () {
      return (this..func_82333_j() != this. && !this.);
    }
    
    public MoveData(EntityItemFrame param1EntityItemFrame, int param1Int) {
      this. = param1Int;
      this. = param1EntityItemFrame;
    }
  }
}
