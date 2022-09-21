package me.oringo.oringoclient.qolfeatures.module.impl.movement;

import java.util.List;
import java.util.stream.Collectors;
import me.oringo.oringoclient.OringoClient;
import me.oringo.oringoclient.commands.impl.ConfigCommand;
import me.oringo.oringoclient.events.impl.MotionUpdateEvent;
import me.oringo.oringoclient.events.impl.PacketReceivedEvent;
import me.oringo.oringoclient.qolfeatures.module.Module;
import me.oringo.oringoclient.qolfeatures.module.impl.combat.KillAura;
import me.oringo.oringoclient.qolfeatures.module.impl.other.AntiNicker;
import me.oringo.oringoclient.qolfeatures.module.settings.Setting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.BooleanSetting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.ModeSetting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.NumberSetting;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class NoSlow extends Module {
  public BooleanSetting  = new BooleanSetting("Sprint", true);
  
  public ModeSetting  = new ModeSetting("Mode", "Hypixel", new String[] { "Hypixel", "Vanilla" });
  
  public NumberSetting  = new NumberSetting("Eating slow", 1.0D, 0.2D, 1.0D, 0.1D);
  
  public NumberSetting  = new NumberSetting("Sword slow", 1.0D, 0.2D, 1.0D, 0.1D);
  
  public NumberSetting  = new NumberSetting("Bow slow", 1.0D, 0.2D, 1.0D, 0.1D);
  
  @SubscribeEvent
  public void (PacketReceivedEvent paramPacketReceivedEvent) {
    if (paramPacketReceivedEvent. instanceof net.minecraft.network.play.server.S30PacketWindowItems && mc.field_71439_g != null && () && this..("Hypixel") && mc.field_71439_g.func_71039_bw())
      paramPacketReceivedEvent.setCanceled(true); 
  }
  
  public static float (boolean paramBoolean) {
    float f3;
    float f1 = 0.91F;
    if (paramBoolean)
      f1 = (OringoClient.mc.field_71439_g.field_70170_p.func_180495_p(new BlockPos(MathHelper.func_76128_c(OringoClient.mc.field_71439_g.field_70165_t), MathHelper.func_76128_c((OringoClient.mc.field_71439_g.func_174813_aQ()).field_72338_b) - 1, MathHelper.func_76128_c(OringoClient.mc.field_71439_g.field_70161_v))).func_177230_c()).field_149765_K * 0.91F; 
    float f2 = 0.16277136F / f1 * f1 * f1;
    if (paramBoolean) {
      f3 = OringoClient.mc.field_71439_g.func_70689_ay() * f2;
    } else {
      f3 = OringoClient.mc.field_71439_g.field_70747_aH;
    } 
    return f3;
  }
  
  public static List<Module> (Module.Category paramCategory) {
    return (List<Module>)OringoClient..stream().filter(paramCategory::).collect(Collectors.toList());
  }
  
  @SubscribeEvent
  public void (MotionUpdateEvent.Post paramPost) {
    if (() && mc.field_71439_g.func_71039_bw() && KillAura. == null)
      switch (this..()) {
        case "Hypixel":
          if (mc.field_71439_g.func_70632_aY() && KillAura..(mc.field_71439_g.field_70122_E ? 200L : 100L, true))
            AntiNicker.((Packet)new C08PacketPlayerBlockPlacement(mc.field_71439_g.func_71011_bu())); 
          ConfigCommand.((Packet)new C09PacketHeldItemChange(mc.field_71439_g.field_71071_by.field_70461_c));
          break;
      }  
  }
  
  public NoSlow() {
    super("No Slow", 0, Module.Category.);
    (new Setting[] { (Setting)this., (Setting)this., (Setting)this., (Setting)this., (Setting)this. });
  }
  
  @SubscribeEvent
  public void (PlayerInteractEvent paramPlayerInteractEvent) {
    if (paramPlayerInteractEvent.entity == mc.field_71439_g && (paramPlayerInteractEvent.action == PlayerInteractEvent.Action.RIGHT_CLICK_AIR || paramPlayerInteractEvent.action == PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK))
      KillAura..(); 
  }
}
