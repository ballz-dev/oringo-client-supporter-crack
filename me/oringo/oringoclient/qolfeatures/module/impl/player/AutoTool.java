package me.oringo.oringoclient.qolfeatures.module.impl.player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.function.Predicate;
import me.oringo.oringoclient.OringoClient;
import me.oringo.oringoclient.events.impl.PacketSentEvent;
import me.oringo.oringoclient.qolfeatures.module.Module;
import me.oringo.oringoclient.qolfeatures.module.impl.other.PVPInfo;
import me.oringo.oringoclient.qolfeatures.module.impl.other.SimulatorAura;
import me.oringo.oringoclient.qolfeatures.module.impl.render.ChestESP;
import me.oringo.oringoclient.qolfeatures.module.settings.Setting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.BooleanSetting;
import me.oringo.oringoclient.utils.MilliTimer;
import me.oringo.oringoclient.utils.shader.BlurUtils;
import net.minecraft.inventory.Slot;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class AutoTool extends Module {
  public BooleanSetting  = new BooleanSetting("Tools", true);
  
  public static MilliTimer  = new MilliTimer();
  
  public BooleanSetting  = new BooleanSetting("Auto fire aspect", true, this::lambda$new$0);
  
  public BooleanSetting  = new BooleanSetting("Swords", true);
  
  @SubscribeEvent
  public void (PacketSentEvent paramPacketSentEvent) {
    if (!() || mc.field_71439_g == null)
      return; 
    if (paramPacketSentEvent. instanceof C07PacketPlayerDigging) {
      C07PacketPlayerDigging c07PacketPlayerDigging = (C07PacketPlayerDigging)paramPacketSentEvent.;
      if (this..() && !mc.field_71439_g.func_71039_bw() && c07PacketPlayerDigging.func_180762_c() == C07PacketPlayerDigging.Action.START_DESTROY_BLOCK) {
        mc.field_71439_g.field_71071_by.field_70461_c = SimulatorAura.(c07PacketPlayerDigging.func_179715_a());
        PVPInfo.();
      } 
    } else if (paramPacketSentEvent. instanceof C02PacketUseEntity) {
      C02PacketUseEntity c02PacketUseEntity = (C02PacketUseEntity)paramPacketSentEvent.;
      if (this..() && c02PacketUseEntity.func_149565_c() == C02PacketUseEntity.Action.ATTACK && .(250L) && (!mc.field_71439_g.func_71039_bw() || mc.field_71439_g.func_71011_bu().func_77975_n() == EnumAction.BLOCK) && !mc.field_71442_b.func_181040_m()) {
        mc.field_71439_g.field_71071_by.field_70461_c = BlurUtils.(c02PacketUseEntity.func_149564_a((World)mc.field_71441_e), this..());
        PVPInfo.();
      } 
    } else if (paramPacketSentEvent. instanceof net.minecraft.network.play.client.C09PacketHeldItemChange) {
      .();
    } 
  }
  
  public static int (Predicate<ItemStack> paramPredicate) {
    ArrayList<?> arrayList = new ArrayList(OringoClient.mc.field_71439_g.field_71069_bz.field_75151_b);
    Collections.reverse(arrayList);
    for (Slot slot : arrayList) {
      if (slot.func_75216_d() && paramPredicate.test(slot.func_75211_c()))
        return slot.field_75222_d; 
    } 
    return -1;
  }
  
  public static void () {
    ChestESP.();
  }
  
  public AutoTool() {
    super("Auto Tool", Module.Category.);
    (new Setting[] { (Setting)this., (Setting)this., (Setting)this. });
  }
}
