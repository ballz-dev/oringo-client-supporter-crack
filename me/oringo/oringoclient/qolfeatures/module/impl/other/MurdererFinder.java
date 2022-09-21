package me.oringo.oringoclient.qolfeatures.module.impl.other;

import com.mojang.realmsclient.gui.ChatFormatting;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import me.oringo.oringoclient.events.impl.LeftClickEvent;
import me.oringo.oringoclient.qolfeatures.module.Module;
import me.oringo.oringoclient.qolfeatures.module.impl.combat.SumoFences;
import me.oringo.oringoclient.qolfeatures.module.impl.player.NoRotate;
import me.oringo.oringoclient.qolfeatures.module.settings.Setting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.BooleanSetting;
import me.oringo.oringoclient.ui.hud.impl.TargetComponent;
import me.oringo.oringoclient.utils.MoveUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class MurdererFinder extends Module {
  public static BooleanSetting ;
  
  public ArrayList<Item>  = new ArrayList<>(Arrays.asList(new Item[] { 
          Items.field_151040_l, Items.field_151052_q, Items.field_151037_a, Items.field_151055_y, Items.field_151053_p, Items.field_151041_m, Blocks.field_150330_I.func_180665_b(null, null), Items.field_151051_r, Items.field_151047_v, Items.field_151128_bU, 
          Items.field_151158_bO, Items.field_151005_D, Items.field_151034_e, Items.field_151057_cb, Blocks.field_150360_v.func_180665_b(null, null), Items.field_151146_bM, Items.field_151103_aS, Items.field_151172_bF, Items.field_151150_bK, Items.field_151106_aX, 
          Items.field_151056_x, Blocks.field_150328_O.func_180665_b(null, null), Items.field_179562_cC, Items.field_151083_be, Items.field_151010_B, Items.field_151048_u, Items.field_151012_L, (Item)Items.field_151097_aZ, Items.field_151115_aP, Items.field_151100_aR, 
          Items.field_151124_az, Items.field_151060_bw, Items.field_151072_bj, Items.field_151115_aP }));
  
  public static BooleanSetting ;
  
  public static ArrayList<EntityPlayer>  = new ArrayList<>();
  
  public boolean ;
  
  public static BooleanSetting ;
  
  public static ArrayList<EntityPlayer>  = new ArrayList<>();
  
  @SubscribeEvent
  public void (RenderWorldLastEvent paramRenderWorldLastEvent) {
    if (!())
      return; 
    if (this.)
      for (Entity entity : mc.field_71441_e.field_72996_f) {
        if (entity instanceof EntityPlayer) {
          if (((EntityPlayer)entity).func_70608_bn() || entity == mc.field_71439_g)
            continue; 
          if (.contains(entity)) {
            SumoFences.(entity, paramRenderWorldLastEvent.partialTicks, 1.0F, Color.red);
            continue;
          } 
          if (.contains(entity)) {
            SumoFences.(entity, paramRenderWorldLastEvent.partialTicks, 1.0F, Color.blue);
            continue;
          } 
          SumoFences.(entity, paramRenderWorldLastEvent.partialTicks, 1.0F, Color.gray);
          continue;
        } 
        if (entity instanceof EntityItem && ((EntityItem)entity).func_92059_d().func_77973_b() == Items.field_151043_k && .()) {
          SumoFences.(entity, paramRenderWorldLastEvent.partialTicks, 1.0F, Color.yellow);
          continue;
        } 
        if (.() && entity instanceof EntityArmorStand && ((EntityArmorStand)entity).func_71124_b(0) != null && ((EntityArmorStand)entity).func_71124_b(0).func_77973_b() == Items.field_151031_f)
          SimulatorAura.(entity, paramRenderWorldLastEvent.partialTicks, 1.0F, Color.CYAN); 
      }  
  }
  
  static {
     = new BooleanSetting("Say murderer", false);
     = new BooleanSetting("Ingot ESP", true);
     = new BooleanSetting("Bow esp", true);
  }
  
  @SubscribeEvent
  public void (TickEvent.ClientTickEvent paramClientTickEvent) {
    if (!() || mc.field_71439_g == null || mc.field_71441_e == null)
      return; 
    try {
      if (mc.field_71439_g.func_96123_co() != null) {
        ScoreObjective scoreObjective = mc.field_71439_g.func_96123_co().func_96539_a(1);
        boolean bool = (LeftClickEvent.("Survivors:") && LeftClickEvent.("Infected:")) ? true : false;
        if (scoreObjective != null && ChatFormatting.stripFormatting(scoreObjective.func_96678_d()).equals("MURDER MYSTERY") && (LeftClickEvent.("Innocents Left:") || bool)) {
          this. = true;
          for (EntityPlayer entityPlayer : mc.field_71441_e.field_73010_i) {
            if (!.contains(entityPlayer) && !.contains(entityPlayer) && entityPlayer.func_70694_bm() != null) {
              if (.size() < 2 && entityPlayer.func_70694_bm().func_77973_b().equals(Items.field_151031_f) && !bool) {
                .add(entityPlayer);
                NoRotate.("Oringo Client", String.format("§b%s is detective!", new Object[] { entityPlayer.func_70005_c_() }), 2500);
              } 
              if (this..contains(entityPlayer.func_70694_bm().func_77973_b())) {
                .add(entityPlayer);
                if (!bool) {
                  NoRotate.("Oringo Client", String.format("§c%s is murderer!", new Object[] { entityPlayer.func_70005_c_() }), 2500);
                  if (.() && entityPlayer != mc.field_71439_g)
                    mc.field_71439_g.func_71165_d(String.format("%s is murderer!", new Object[] { ChatFormatting.stripFormatting(entityPlayer.func_70005_c_()) })); 
                } 
              } 
            } 
          } 
          return;
        } 
        this. = false;
        .clear();
        .clear();
      } 
    } catch (Exception exception) {}
  }
  
  public static float () {
    return MoveUtils.(TargetComponent..func_110143_aJ() / TargetComponent..func_110138_aP(), 1.0F, 0.0F);
  }
  
  public MurdererFinder() {
    super("Murder Mystery", Module.Category.OTHER);
    (new Setting[] { (Setting), (Setting), (Setting) });
  }
}
