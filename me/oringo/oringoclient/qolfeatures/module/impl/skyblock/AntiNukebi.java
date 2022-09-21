package me.oringo.oringoclient.qolfeatures.module.impl.skyblock;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import me.oringo.oringoclient.commands.CommandHandler;
import me.oringo.oringoclient.events.impl.MotionUpdateEvent;
import me.oringo.oringoclient.events.impl.WorldJoinEvent;
import me.oringo.oringoclient.qolfeatures.module.Module;
import me.oringo.oringoclient.qolfeatures.module.impl.movement.Scaffold;
import me.oringo.oringoclient.qolfeatures.module.impl.other.HClip;
import me.oringo.oringoclient.qolfeatures.module.impl.other.SimulatorAura;
import me.oringo.oringoclient.qolfeatures.module.settings.Setting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.BooleanSetting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.NumberSetting;
import me.oringo.oringoclient.utils.MilliTimer;
import me.oringo.oringoclient.utils.Rotation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class AntiNukebi extends Module {
  public static NumberSetting ;
  
  public static BooleanSetting  = new BooleanSetting("Attack with aura", true);
  
  public MilliTimer  = new MilliTimer();
  
  public static NumberSetting ;
  
  public static EntityArmorStand ;
  
  public static List<EntityArmorStand> ;
  
  public static BooleanSetting  = new BooleanSetting("Tracer", true);
  
  public static NumberSetting ;
  
  @SubscribeEvent
  public void (RenderWorldLastEvent paramRenderWorldLastEvent) {
    if (() &&  != null)
      SimulatorAura.((Entity), paramRenderWorldLastEvent.partialTicks, 1.0F, Color.white); 
  }
  
  @SubscribeEvent
  public void (WorldJoinEvent paramWorldJoinEvent) {
    ();
  }
  
  static {
     = new NumberSetting("Timeout", 100.0D, 10.0D, 250.0D, 1.0D);
     = new NumberSetting("Distance", 10.0D, 5.0D, 20.0D, 1.0D);
     = new NumberSetting("Fov", 360.0D, 1.0D, 360.0D, 1.0D);
     = new ArrayList<>();
  }
  
  public AntiNukebi() {
    super("Anti Nukekubi", Module.Category.);
    (new Setting[] { (Setting), (Setting), (Setting), (Setting), (Setting) });
  }
  
  public static boolean (EntityArmorStand paramEntityArmorStand) {
    return (paramEntityArmorStand.func_82169_q(3) != null && paramEntityArmorStand.func_82169_q(3).serializeNBT().func_74775_l("tag").func_74775_l("SkullOwner").func_74775_l("Properties").toString().contains("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZWIwNzU5NGUyZGYyNzM5MjFhNzdjMTAxZDBiZmRmYTExMTVhYmVkNWI5YjIwMjllYjQ5NmNlYmE5YmRiYjRiMyJ9fX0="));
  }
  
  public void () {
     = null;
    .clear();
  }
  
  @SubscribeEvent(priority = EventPriority.LOW)
  public void (MotionUpdateEvent.Pre paramPre) {
    if (()) {
      if ( == null || this..((long)(.() * 50.0D)) || .field_70128_L || !.func_70685_l((Entity)mc.field_71439_g)) {
         = null;
        Iterator<Entity> iterator = ((List)mc.field_71441_e.field_72996_f.stream().filter(AntiNukebi::).collect(Collectors.toList())).iterator();
        if (iterator.hasNext()) {
          Entity entity = iterator.next();
          EntityArmorStand entityArmorStand = (EntityArmorStand)entity;
           = entityArmorStand;
          this..();
          .add(entityArmorStand);
        } 
      } 
      if ( != null) {
        Rotation rotation = CommandHandler.(.field_70165_t, .field_70163_u + 0.85D, .field_70161_v);
        paramPre.setRotation(rotation);
      } 
    } 
  }
  
  public void () {
    ();
  }
  
  public static boolean (String paramString) {
    Matcher matcher = Pattern.compile("<div class=\"card-box m-b-10\">\n<h4 class=\"m-t-0 header-title\">Status</h4>\n<b>(.*?)</b>\n</div>").matcher(Scaffold.(paramString));
    return !matcher.find();
  }
}
