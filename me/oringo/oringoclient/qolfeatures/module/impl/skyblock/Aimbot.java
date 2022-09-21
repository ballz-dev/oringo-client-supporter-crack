package me.oringo.oringoclient.qolfeatures.module.impl.skyblock;

import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.List;
import me.oringo.oringoclient.events.impl.MotionUpdateEvent;
import me.oringo.oringoclient.qolfeatures.module.Module;
import me.oringo.oringoclient.qolfeatures.module.settings.Setting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.NumberSetting;
import me.oringo.oringoclient.utils.Rotation;
import me.oringo.oringoclient.utils.RotationUtils;
import me.oringo.oringoclient.utils.SkyblockUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.util.Vec3;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class Aimbot extends Module {
  public static String[] ;
  
  public static boolean ;
  
  public static NumberSetting  = new NumberSetting("Y offset", 0.0D, -2.0D, 2.0D, 0.1D);
  
  public static NumberSetting  = new NumberSetting("Fov", 360.0D, 1.0D, 360.0D, 1.0D);
  
  public static List<Entity>  = new ArrayList<>();
  
  public static double (JsonObject paramJsonObject) {
    try {
      return (paramJsonObject.getAsJsonObject("stats").getAsJsonObject("Duels").get("sumo_duel_wins").getAsInt() == 0) ? 0.0D : ((paramJsonObject.getAsJsonObject("stats").getAsJsonObject("Duels").get("sumo_duel_losses").getAsInt() == 0) ? -1.0D : (paramJsonObject.getAsJsonObject("stats").getAsJsonObject("Duels").get("sumo_duel_wins").getAsInt() / paramJsonObject.getAsJsonObject("stats").getAsJsonObject("Duels").get("sumo_duel_losses").getAsInt()));
    } catch (Exception exception) {
      return -1.0D;
    } 
  }
  
  @SubscribeEvent(priority = EventPriority.HIGH)
  public void (MotionUpdateEvent.Pre paramPre) {
    if (!() || !SkyblockUtils.inDungeon || !SkyblockUtils. || mc.field_71441_e == null)
      return; 
    for (EntityPlayer entityPlayer : mc.field_71441_e.field_73010_i) {
      if (entityPlayer.func_70032_d((Entity)mc.field_71439_g) < 20.0F && entityPlayer.func_70685_l((Entity)mc.field_71439_g) && !entityPlayer.field_70128_L && !.contains(entityPlayer))
        for (String str : ) {
          if (entityPlayer.func_70005_c_().contains(str)) {
            Rotation rotation = RotationUtils.(new Vec3(entityPlayer.field_70165_t, entityPlayer.field_70163_u + .(), entityPlayer.field_70161_v));
            if (RemoveAnnoyingMobs.(rotation, (float).())) {
               = true;
              paramPre. = rotation.();
              paramPre. = rotation.();
              .add(entityPlayer);
              break;
            } 
          } 
        }  
    } 
  }
  
  @SubscribeEvent
  public void (MotionUpdateEvent.Post paramPost) {
    if (!)
      return; 
    mc.func_147114_u().func_147298_b().func_179290_a((Packet)new C0APacketAnimation());
     = false;
  }
  
  public Aimbot() {
    super("Blood Aimbot", 0, Module.Category.);
    (new Setting[] { (Setting), (Setting) });
  }
  
  @SubscribeEvent
  public void (WorldEvent.Load paramLoad) {
    .clear();
  }
  
  static {
     = new String[] { 
        "Revoker", "Psycho", "Reaper", "Cannibal", "Mute", "Ooze", "Putrid", "Freak", "Leech", "Tear", 
        "Parasite", "Flamer", "Skull", "Mr. Dead", "Vader", "Frost", "Walker", "WanderingSoul" };
  }
}
