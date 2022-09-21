package me.oringo.oringoclient.qolfeatures.module.impl.render;

import com.mojang.realmsclient.gui.ChatFormatting;
import java.awt.Color;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import me.oringo.oringoclient.commands.CommandHandler;
import me.oringo.oringoclient.events.impl.MotionUpdateEvent;
import me.oringo.oringoclient.events.impl.MoveStateUpdateEvent;
import me.oringo.oringoclient.events.impl.RenderLayersEvent;
import me.oringo.oringoclient.qolfeatures.module.Module;
import me.oringo.oringoclient.qolfeatures.module.impl.combat.SumoFences;
import me.oringo.oringoclient.qolfeatures.module.impl.other.LightningDetect;
import me.oringo.oringoclient.qolfeatures.module.impl.other.SimulatorAura;
import me.oringo.oringoclient.qolfeatures.module.impl.player.InvManager;
import me.oringo.oringoclient.qolfeatures.module.impl.skyblock.SecretAura;
import me.oringo.oringoclient.qolfeatures.module.settings.Setting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.BooleanSetting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.ModeSetting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.NumberSetting;
import me.oringo.oringoclient.utils.SkyblockUtils;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class DungeonESP extends Module {
  public BooleanSetting  = new BooleanSetting("Bat ESP", true);
  
  public static NumberSetting ;
  
  public static NumberSetting ;
  
  public BooleanSetting  = new BooleanSetting("Miniboss ESP", true);
  
  public static NumberSetting ;
  
  public static NumberSetting ;
  
  public BooleanSetting  = new BooleanSetting("Bow warning", false);
  
  public static NumberSetting ;
  
  public BooleanSetting  = new BooleanSetting("Show endermen", true);
  
  public static NumberSetting ;
  
  public ModeSetting  = new ModeSetting("Mode", "2D", new String[] { "Outline", "2D", "Chams", "Box", "Tracers" });
  
  public static NumberSetting ;
  
  public static HashMap<Entity, Color> ;
  
  public static NumberSetting ;
  
  public Entity ;
  
  public static NumberSetting ;
  
  public static NumberSetting ;
  
  public static NumberSetting ;
  
  public static NumberSetting ;
  
  public static NumberSetting  = new NumberSetting("Starred R", 245.0D, 0.0D, 255.0D, 1.0D);
  
  public static NumberSetting ;
  
  public BooleanSetting  = new BooleanSetting("Starred ESP", true);
  
  public static NumberSetting ;
  
  @SubscribeEvent
  public void (RenderLayersEvent paramRenderLayersEvent) {
    if (!() || !SkyblockUtils.inDungeon || !this..("Outline"))
      return; 
    if (.containsKey(paramRenderLayersEvent.))
      SecretAura.(paramRenderLayersEvent, .get(paramRenderLayersEvent.)); 
  }
  
  public static Color (Color paramColor, int paramInt) {
    return new Color(paramColor.getRed(), paramColor.getGreen(), paramColor.getBlue(), paramInt);
  }
  
  public DungeonESP() {
    super("Dungeon ESP", 0, Module.Category.);
    (new Setting[] { 
          (Setting)this., (Setting)this., (Setting), (Setting), (Setting), (Setting)this., (Setting)this., (Setting), (Setting), (Setting), 
          (Setting)this., (Setting), (Setting), (Setting), (Setting), (Setting), (Setting), (Setting), (Setting), (Setting) });
  }
  
  @SubscribeEvent
  public void (RenderLivingEvent.Specials.Pre<EntityLivingBase> paramPre) {
    if (this. == paramPre.entity) {
      this. = null;
      LightningDetect.();
    } 
  }
  
  @SubscribeEvent
  public void (MotionUpdateEvent.Pre paramPre) {
    if (mc.field_71439_g.field_70173_aa % 20 == 0 && SkyblockUtils.inDungeon) {
      .clear();
      Color color1 = new Color((int).(), (int).(), (int).());
      Color color2 = new Color((int).(), (int).(), (int).());
      Color color3 = new Color((int).(), (int).(), (int).());
      Color color4 = new Color((int).(), (int).(), (int).());
      Color color5 = new Color((int).(), (int).(), (int).());
      for (Entity entity : mc.field_71441_e.field_72996_f.stream().filter(DungeonESP::).collect(Collectors.toList())) {
        if (.containsKey(entity))
          continue; 
        if (entity instanceof net.minecraft.entity.passive.EntityBat && !entity.func_82150_aj() && this..()) {
          .put(entity, color2);
          continue;
        } 
        if (this..()) {
          if (entity instanceof net.minecraft.entity.monster.EntityEnderman && entity.func_70005_c_().equals("Dinnerbone")) {
            entity.func_82142_c(false);
            if (this..())
              .put(entity, color1); 
            continue;
          } 
          if (entity instanceof net.minecraft.entity.item.EntityArmorStand && entity.func_70005_c_().contains("✯")) {
            List<Entity> list = mc.field_71441_e.func_72839_b(entity, entity.func_174813_aQ().func_72314_b(0.1D, 3.0D, 0.1D));
            if (!list.isEmpty() && !MoveStateUpdateEvent.(list.get(0)) && !.containsKey(list.get(0)))
              .put(list.get(0), color1); 
            continue;
          } 
        } 
        if (this..() && entity instanceof EntityOtherPlayerMP && MoveStateUpdateEvent.(entity)) {
          switch (entity.func_70005_c_()) {
            case "Lost Adventurer":
              .put(entity, color4);
              break;
            case "Shadow Assassin":
              entity.func_82142_c(false);
              .put(entity, color3);
              break;
            case "Diamond Guy":
              .put(entity, color5);
              break;
          } 
          if (this..() && ((EntityOtherPlayerMP)entity).func_70694_bm() != null && ((EntityOtherPlayerMP)entity).func_70694_bm().func_77973_b() instanceof net.minecraft.item.ItemBow)
            (); 
        } 
      } 
    } 
  }
  
  @SubscribeEvent
  public void (RenderWorldLastEvent paramRenderWorldLastEvent) {
    // Byte code:
    //   0: aload_0
    //   1: invokevirtual  : ()Z
    //   4: ifeq -> 49
    //   7: getstatic me/oringo/oringoclient/utils/SkyblockUtils.inDungeon : Z
    //   10: ifeq -> 49
    //   13: aload_0
    //   14: getfield  : Lme/oringo/oringoclient/qolfeatures/module/settings/impl/ModeSetting;
    //   17: ldc '2D'
    //   19: invokevirtual  : (Ljava/lang/String;)Z
    //   22: ifne -> 50
    //   25: aload_0
    //   26: getfield  : Lme/oringo/oringoclient/qolfeatures/module/settings/impl/ModeSetting;
    //   29: ldc 'Box'
    //   31: invokevirtual  : (Ljava/lang/String;)Z
    //   34: ifne -> 50
    //   37: aload_0
    //   38: getfield  : Lme/oringo/oringoclient/qolfeatures/module/settings/impl/ModeSetting;
    //   41: ldc 'Tracers'
    //   43: invokevirtual  : (Ljava/lang/String;)Z
    //   46: ifne -> 50
    //   49: return
    //   50: getstatic me/oringo/oringoclient/qolfeatures/module/impl/render/DungeonESP. : Ljava/util/HashMap;
    //   53: aload_0
    //   54: aload_1
    //   55: <illegal opcode> accept : (Lme/oringo/oringoclient/qolfeatures/module/impl/render/DungeonESP;Lnet/minecraftforge/client/event/RenderWorldLastEvent;)Ljava/util/function/BiConsumer;
    //   60: invokevirtual forEach : (Ljava/util/function/BiConsumer;)V
    //   63: return
  }
  
  public void () {
    mc.field_71456_v.func_175178_a(null, String.valueOf((new StringBuilder()).append(ChatFormatting.DARK_RED).append("Bow")), 0, 20, 0);
  }
  
  static {
     = new NumberSetting("Starred G", 81.0D, 0.0D, 255.0D, 1.0D);
     = new NumberSetting("Starred B", 66.0D, 0.0D, 255.0D, 1.0D);
     = new NumberSetting("Bat R", 140.0D, 0.0D, 255.0D, 1.0D);
     = new NumberSetting("Bat G", 69.0D, 0.0D, 255.0D, 1.0D);
     = new NumberSetting("Bat B", 19.0D, 0.0D, 255.0D, 1.0D);
     = new NumberSetting("Sa R", 75.0D, 0.0D, 255.0D, 1.0D);
     = new NumberSetting("Sa G", 0.0D, 0.0D, 255.0D, 1.0D);
     = new NumberSetting("Sa B", 19.0D, 0.0D, 255.0D, 1.0D);
     = new NumberSetting("La R", 34.0D, 0.0D, 255.0D, 1.0D);
     = new NumberSetting("La G", 139.0D, 0.0D, 255.0D, 1.0D);
     = new NumberSetting("La B", 34.0D, 0.0D, 255.0D, 1.0D);
     = new NumberSetting("AA R", 97.0D, 0.0D, 255.0D, 1.0D);
     = new NumberSetting("AA G", 226.0D, 0.0D, 255.0D, 1.0D);
     = new NumberSetting("AA B", 255.0D, 0.0D, 255.0D, 1.0D);
     = new HashMap<>();
  }
  
  @SubscribeEvent(priority = EventPriority.LOWEST)
  public void (RenderLivingEvent.Pre<EntityLivingBase> paramPre) {
    if (!() || !SkyblockUtils.inDungeon || !this..("Chams"))
      return; 
    if (.containsKey(paramPre.entity)) {
      InvManager.();
      this. = (Entity)paramPre.entity;
    } 
  }
  
  @SubscribeEvent
  public void (WorldEvent.Load paramLoad) {
    .clear();
  }
}
