package me.oringo.oringoclient.qolfeatures.module.impl.render;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import me.oringo.oringoclient.commands.impl.TestCommand;
import me.oringo.oringoclient.events.impl.MotionUpdateEvent;
import me.oringo.oringoclient.events.impl.PacketReceivedEvent;
import me.oringo.oringoclient.extend.chunk.ChunkExtend;
import me.oringo.oringoclient.qolfeatures.module.Module;
import me.oringo.oringoclient.qolfeatures.module.impl.other.AntiNicker;
import me.oringo.oringoclient.qolfeatures.module.impl.skyblock.Snowballs;
import me.oringo.oringoclient.qolfeatures.module.settings.Setting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.BooleanSetting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.ModeSetting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.NumberSetting;
import me.oringo.oringoclient.utils.RenderUtils;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.server.S22PacketMultiBlockChange;
import net.minecraft.network.play.server.S23PacketBlockChange;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class XRay extends Module {
  public static NumberSetting ;
  
  public static NumberSetting ;
  
  public static boolean ;
  
  public static List<Block> ;
  
  public static double ;
  
  public static BooleanSetting ;
  
  public static NumberSetting ;
  
  public static ModeSetting  = new ModeSetting("Mode", "Normal", new String[] { "Normal", "UHC" });
  
  public static BooleanSetting ;
  
  public XRay() {
    super("XRay", Module.Category.);
    (new Setting[] { (Setting), (Setting), (Setting), (Setting), (Setting), (Setting) });
  }
  
  @SubscribeEvent
  public void (MotionUpdateEvent.Post paramPost) {
    if (! && () && .()) {
      BlockPos blockPos = new BlockPos(mc.field_71439_g.func_174824_e(1.0F));
      byte b = 0;
      for (BlockPos blockPos1 : BlockPos.func_177980_a(blockPos.func_177982_a(7, 7, 7), blockPos.func_177982_a(-7, -7, -7))) {
        Chunk chunk = mc.field_71441_e.func_175726_f(blockPos1);
        if (blockPos1.func_177954_c(mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u, mc.field_71439_g.field_70161_v) >= .() * .() || ((ChunkExtend)chunk).getGeneratedData(blockPos1.func_177958_n(), blockPos1.func_177956_o(), blockPos1.func_177952_p()) || ((ChunkExtend)chunk).getScanData(blockPos1.func_177958_n(), blockPos1.func_177956_o(), blockPos1.func_177952_p()))
          continue; 
        if (++b > .())
          break; 
        ((ChunkExtend)chunk).setScanData(blockPos1.func_177958_n(), blockPos1.func_177956_o(), blockPos1.func_177952_p());
        AntiNicker.((Packet)new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.ABORT_DESTROY_BLOCK, blockPos1, EnumFacing.DOWN));
      } 
    } 
  }
  
  public static String (String paramString) {
    StringBuilder stringBuilder = new StringBuilder();
    for (char c : paramString.toCharArray()) {
      if (Snowballs.(c))
        stringBuilder.append(c); 
    } 
    return String.valueOf(stringBuilder);
  }
  
  public void () {
    TestCommand.();
  }
  
  @SubscribeEvent
  public void (PacketReceivedEvent paramPacketReceivedEvent) {
    if (paramPacketReceivedEvent. instanceof S23PacketBlockChange) {
      BlockPos blockPos = ((S23PacketBlockChange)paramPacketReceivedEvent.).func_179827_b();
      Chunk chunk = mc.field_71441_e.func_175726_f(blockPos);
      ((ChunkExtend)chunk).setGeneratedData(blockPos.func_177958_n(), blockPos.func_177956_o(), blockPos.func_177952_p());
    } else if (paramPacketReceivedEvent. instanceof S22PacketMultiBlockChange) {
      for (S22PacketMultiBlockChange.BlockUpdateData blockUpdateData : ((S22PacketMultiBlockChange)paramPacketReceivedEvent.).func_179844_a()) {
        BlockPos blockPos = blockUpdateData.func_180090_a();
        Chunk chunk = mc.field_71441_e.func_175726_f(blockPos);
        ((ChunkExtend)chunk).setGeneratedData(blockPos.func_177958_n(), blockPos.func_177956_o(), blockPos.func_177952_p());
      } 
    } 
  }
  
  @SubscribeEvent
  public void (TickEvent.ClientTickEvent paramClientTickEvent) {
     = .("Normal");
    if ( != .() && ())
      TestCommand.(); 
  }
  
  public void () {
    TestCommand.();
  }
  
  static {
     = new BooleanSetting("Cave finder", false, XRay::);
     = new BooleanSetting("Update blocks", true, XRay::);
     = new NumberSetting("Opacity", 120.0D, 0.0D, 255.0D, 1.0D);
     = new NumberSetting("Update reach", 5.0D, 1.0D, 6.0D, 0.1D, XRay::);
     = new NumberSetting("Clicks per tick ", 3.0D, 1.0D, 100.0D, 1.0D, XRay::);
     = 0.0D;
    ArrayList<Block> arrayList = new ArrayList();
    arrayList.add(Blocks.field_150482_ag);
    arrayList.add(Blocks.field_150366_p);
    arrayList.add(Blocks.field_150352_o);
    arrayList.add(Blocks.field_150450_ax);
    arrayList.add(Blocks.field_150365_q);
     = arrayList;
    File file = new File(String.valueOf((new StringBuilder()).append(mc.field_71412_D.getPath()).append("/config/OringoClient/xray.txt")));
    if (!file.exists()) {
      RenderUtils.();
    } else {
      try {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        String str;
        while ((str = bufferedReader.readLine()) != null) {
          Block block = Block.func_149684_b(str);
          if (block == null)
            continue; 
          .add(block);
        } 
        bufferedReader.close();
      } catch (Exception exception) {}
    } 
  }
}
