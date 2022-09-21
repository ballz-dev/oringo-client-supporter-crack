package me.oringo.oringoclient.qolfeatures.module.impl.combat;

import me.oringo.oringoclient.events.impl.PacketSentEvent;
import me.oringo.oringoclient.mixins.entity.PlayerSPAccessor;
import me.oringo.oringoclient.qolfeatures.module.Module;
import me.oringo.oringoclient.qolfeatures.module.impl.skyblock.AutoS1;
import me.oringo.oringoclient.qolfeatures.module.settings.Setting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.BooleanSetting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.ModeSetting;
import me.oringo.oringoclient.utils.StencilUtils;
import net.minecraft.entity.Entity;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.util.BlockPos;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class WTap extends Module {
  public ModeSetting  = new ModeSetting("Mode", "Packet", new String[] { "Packet" });
  
  public BooleanSetting  = new BooleanSetting("Bow", true);
  
  public static boolean () {
    return StencilUtils.(0.0D, 0.0D);
  }
  
  public static boolean (BlockPos paramBlockPos) {
    return (AutoS1.mc.field_71439_g.func_70011_f(paramBlockPos.func_177958_n(), (paramBlockPos.func_177956_o() - AutoS1.mc.field_71439_g.func_70047_e()), paramBlockPos.func_177952_p()) < 5.0D);
  }
  
  public WTap() {
    super("W Tap", Module.Category.);
    (new Setting[] { (Setting)this., (Setting)this. });
  }
  
  @SubscribeEvent
  public void (PacketSentEvent paramPacketSentEvent) {
    if (() && ((paramPacketSentEvent. instanceof C02PacketUseEntity && ((C02PacketUseEntity)paramPacketSentEvent.).func_149565_c() == C02PacketUseEntity.Action.ATTACK) || (this..() && paramPacketSentEvent. instanceof C07PacketPlayerDigging && ((C07PacketPlayerDigging)paramPacketSentEvent.).func_180762_c() == C07PacketPlayerDigging.Action.RELEASE_USE_ITEM && mc.field_71439_g.func_70694_bm() != null && mc.field_71439_g.func_70694_bm().func_77973_b() instanceof net.minecraft.item.ItemBow)))
      switch (this..()) {
        case "Packet":
          if (mc.field_71439_g.func_70051_ag())
            mc.func_147114_u().func_147298_b().func_179290_a((Packet)new C0BPacketEntityAction((Entity)mc.field_71439_g, C0BPacketEntityAction.Action.STOP_SPRINTING)); 
          mc.func_147114_u().func_147298_b().func_179290_a((Packet)new C0BPacketEntityAction((Entity)mc.field_71439_g, C0BPacketEntityAction.Action.START_SPRINTING));
          ((PlayerSPAccessor)mc.field_71439_g).setServerSprintState(true);
          break;
      }  
  }
}
