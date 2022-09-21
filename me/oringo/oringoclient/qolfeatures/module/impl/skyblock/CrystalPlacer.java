package me.oringo.oringoclient.qolfeatures.module.impl.skyblock;

import com.mojang.realmsclient.gui.ChatFormatting;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import me.oringo.oringoclient.events.impl.MotionUpdateEvent;
import me.oringo.oringoclient.qolfeatures.module.Module;
import me.oringo.oringoclient.utils.MilliTimer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class CrystalPlacer extends Module {
  public static MilliTimer  = new MilliTimer();
  
  public CrystalPlacer() {
    super("Crystal Placer", Module.Category.);
  }
  
  @SubscribeEvent
  public void (MotionUpdateEvent.Post paramPost) {
    if (() && .(500L) && mc.field_71439_g.field_71071_by.func_70301_a(8) != null && ChatFormatting.stripFormatting(mc.field_71439_g.field_71071_by.func_70301_a(8).func_82833_r()).contains("Energy Crystal")) {
      List<Entity> list = mc.field_71441_e.func_175644_a(EntityArmorStand.class, CrystalPlacer::);
      if (!list.isEmpty()) {
        Objects.requireNonNull(mc.field_71439_g);
        list.sort(Comparator.comparingDouble(mc.field_71439_g::func_70068_e));
        mc.field_71442_b.func_78768_b((EntityPlayer)mc.field_71439_g, list.get(0));
        .();
      } 
    } 
  }
}
