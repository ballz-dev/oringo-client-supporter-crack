package me.oringo.oringoclient.qolfeatures.module.impl.skyblock;

import com.mojang.authlib.properties.Property;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import me.oringo.oringoclient.events.impl.BlockChangeEvent;
import me.oringo.oringoclient.events.impl.PacketReceivedEvent;
import me.oringo.oringoclient.events.impl.WorldJoinEvent;
import me.oringo.oringoclient.qolfeatures.module.Module;
import me.oringo.oringoclient.qolfeatures.module.impl.other.AntiNicker;
import me.oringo.oringoclient.qolfeatures.module.settings.Setting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.BooleanSetting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.NumberSetting;
import me.oringo.oringoclient.ui.hud.impl.TargetComponent;
import me.oringo.oringoclient.utils.MilliTimer;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.tileentity.TileEntitySkull;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class GhostBlocks extends Module {
  public BooleanSetting  = new BooleanSetting("Swing", true);
  
  public boolean ;
  
  public MilliTimer  = new MilliTimer();
  
  public static ArrayList<BlockPos>  = new ArrayList<>();
  
  public NumberSetting  = new NumberSetting("Delay", 200.0D, 0.0D, 1000.0D, 50.0D);
  
  public NumberSetting  = new NumberSetting("Range", 10.0D, 1.0D, 100.0D, 1.0D);
  
  public BooleanSetting  = new BooleanSetting("Cord blocks", true);
  
  public static HashMap<Long, BlockChangeEvent>  = new HashMap<>();
  
  public static int[][]  = new int[][] { 
      { 275, 220, 231 }, { 275, 220, 232 }, { 299, 168, 243 }, { 299, 168, 244 }, { 299, 168, 246 }, { 299, 168, 247 }, { 299, 168, 247 }, { 300, 168, 247 }, { 300, 168, 246 }, { 300, 168, 244 }, 
      { 300, 168, 243 }, { 298, 168, 247 }, { 298, 168, 246 }, { 298, 168, 244 }, { 298, 168, 243 }, { 287, 167, 240 }, { 288, 167, 240 }, { 289, 167, 240 }, { 290, 167, 240 }, { 291, 167, 240 }, 
      { 292, 167, 240 }, { 293, 167, 240 }, { 294, 167, 240 }, { 295, 167, 240 }, { 290, 167, 239 }, { 291, 167, 239 }, { 292, 167, 239 }, { 293, 167, 239 }, { 294, 167, 239 }, { 295, 167, 239 }, 
      { 290, 166, 239 }, { 291, 166, 239 }, { 292, 166, 239 }, { 293, 166, 239 }, { 294, 166, 239 }, { 295, 166, 239 }, { 290, 166, 240 }, { 291, 166, 240 }, { 292, 166, 240 }, { 293, 166, 240 }, 
      { 294, 166, 240 }, { 295, 166, 240 } };
  
  public boolean (BlockPos paramBlockPos) {
    Block block = mc.field_71441_e.func_180495_p(paramBlockPos).func_177230_c();
    if (block == Blocks.field_150465_bP) {
      TileEntitySkull tileEntitySkull = (TileEntitySkull)mc.field_71441_e.func_175625_s(paramBlockPos);
      if (tileEntitySkull.func_145904_a() == 3 && tileEntitySkull.func_152108_a() != null && tileEntitySkull.func_152108_a().getProperties() != null) {
        Property property = (Property)TargetComponent.(tileEntitySkull.func_152108_a().getProperties().get("textures"));
        return (property != null && property.getValue().equals("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzRkYjRhZGZhOWJmNDhmZjVkNDE3MDdhZTM0ZWE3OGJkMjM3MTY1OWZjZDhjZDg5MzQ3NDlhZjRjY2U5YiJ9fX0="));
      } 
    } 
    return (block == Blocks.field_150442_at || block == Blocks.field_150486_ae || block == Blocks.field_150447_bR || block == Blocks.field_150350_a);
  }
  
  public boolean () {
    return true;
  }
  
  @SubscribeEvent
  public void (WorldJoinEvent paramWorldJoinEvent) {
    .clear();
    .clear();
  }
  
  @SubscribeEvent
  public void (TickEvent.ClientTickEvent paramClientTickEvent) {
    if (mc.field_71462_r != null || mc.field_71441_e == null || !() || paramClientTickEvent.phase == TickEvent.Phase.END)
      return; 
    if (this..() && SecretAura.)
      for (int[] arrayOfInt : )
        mc.field_71441_e.func_175698_g(new BlockPos(arrayOfInt[0], arrayOfInt[1], arrayOfInt[2]));  
    this. = true;
    .entrySet().removeIf(GhostBlocks::);
    this. = false;
    if (() && this..((long)this..())) {
      Vec3 vec31 = mc.field_71439_g.func_174824_e(0.0F);
      Vec3 vec32 = mc.field_71439_g.func_70676_i(0.0F);
      Vec3 vec33 = vec31.func_72441_c(vec32.field_72450_a * this..(), vec32.field_72448_b * this..(), vec32.field_72449_c * this..());
      BlockPos blockPos = mc.field_71441_e.func_147447_a(vec31, vec33, true, false, true).func_178782_a();
      if ((blockPos))
        return; 
      mc.field_71441_e.func_175698_g(blockPos);
      if (this..())
        AntiNicker.((Packet)new C0APacketAnimation()); 
      .add(blockPos);
      this..();
    } 
  }
  
  public GhostBlocks() {
    super("Ghost Blocks", 0, Module.Category.);
    (new Setting[] { (Setting)this., (Setting)this., (Setting)this., (Setting)this. });
  }
  
  @SubscribeEvent(priority = EventPriority.LOWEST)
  public void (BlockChangeEvent paramBlockChangeEvent) {
    if (paramBlockChangeEvent. != null && .contains(paramBlockChangeEvent.) && !this. && () && paramBlockChangeEvent..func_177230_c() != Blocks.field_150350_a) {
      paramBlockChangeEvent.setCanceled(true);
      .put(Long.valueOf(System.currentTimeMillis()), paramBlockChangeEvent);
    } 
  }
  
  @SubscribeEvent(receiveCanceled = true)
  public void (PacketReceivedEvent paramPacketReceivedEvent) {
    if (paramPacketReceivedEvent. instanceof net.minecraft.network.play.server.S08PacketPlayerPosLook)
      .clear(); 
  }
}
