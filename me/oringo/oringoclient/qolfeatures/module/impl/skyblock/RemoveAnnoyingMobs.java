package me.oringo.oringoclient.qolfeatures.module.impl.skyblock;

import me.oringo.oringoclient.events.impl.RightClickEvent;
import me.oringo.oringoclient.qolfeatures.module.Module;
import me.oringo.oringoclient.qolfeatures.module.settings.Setting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.BooleanSetting;
import me.oringo.oringoclient.utils.Rotation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class RemoveAnnoyingMobs extends Module {
  public static BooleanSetting ;
  
  public static BooleanSetting  = new BooleanSetting("Show ghosts", true);
  
  public static BooleanSetting  = new BooleanSetting("Hide near boss", true);
  
  static {
     = new BooleanSetting("Hide wither cloak", true);
  }
  
  public RemoveAnnoyingMobs() {
    super("Hide Summons", Module.Category.);
    (new Setting[] { (Setting), (Setting), (Setting) });
  }
  
  @SubscribeEvent
  public void (TickEvent.ClientTickEvent paramClientTickEvent) {
    if (paramClientTickEvent.phase == TickEvent.Phase.START)
      return; 
    if (!() || mc.field_71441_e == null)
      return; 
    for (Entity entity : mc.field_71441_e.func_72910_y()) {
      if (entity instanceof net.minecraft.entity.item.EntityArmorStand && .() && (entity.func_145748_c_().func_150254_d().contains("Voidgloom Seraph") || entity.func_145748_c_().func_150254_d().contains("Endstone Protector"))) {
        for (Entity entity1 : mc.field_71441_e.func_175674_a(entity, entity.func_174813_aQ().func_72314_b(5.0D, 5.0D, 5.0D), RemoveAnnoyingMobs::))
          entity1.func_70107_b(2137.0D, 2137.0D, 2137.0D); 
        continue;
      } 
      if (entity instanceof net.minecraft.entity.item.EntityFireworkRocket || (entity instanceof EntityCreeper && entity.func_82150_aj() && ((EntityCreeper)entity).func_110143_aJ() == 20.0F && .()) || entity instanceof net.minecraft.entity.passive.EntityHorse) {
        mc.field_71441_e.func_72900_e(entity);
        continue;
      } 
      if (entity instanceof EntityCreeper && entity.func_82150_aj() && ((EntityCreeper)entity).func_110143_aJ() != 20.0F && .())
        entity.func_82142_c(false); 
    } 
  }
  
  public static boolean (Rotation paramRotation, float paramFloat) {
    return RightClickEvent.(paramRotation.(), paramFloat);
  }
}
