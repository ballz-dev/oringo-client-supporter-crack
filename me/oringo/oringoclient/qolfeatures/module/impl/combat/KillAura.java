package me.oringo.oringoclient.qolfeatures.module.impl.combat;

import java.util.Random;
import me.oringo.oringoclient.OringoClient;
import me.oringo.oringoclient.commands.impl.ConfigCommand;
import me.oringo.oringoclient.events.impl.KeyPressEvent;
import me.oringo.oringoclient.events.impl.MotionUpdateEvent;
import me.oringo.oringoclient.events.impl.MoveFlyingEvent;
import me.oringo.oringoclient.events.impl.PacketReceivedEvent;
import me.oringo.oringoclient.lunarspoof.LunarClient;
import me.oringo.oringoclient.qolfeatures.module.Module;
import me.oringo.oringoclient.qolfeatures.module.impl.macro.AutoFish;
import me.oringo.oringoclient.qolfeatures.module.impl.movement.Scaffold;
import me.oringo.oringoclient.qolfeatures.module.impl.other.GuessTheBuildAFK;
import me.oringo.oringoclient.qolfeatures.module.impl.other.HClip;
import me.oringo.oringoclient.qolfeatures.module.impl.other.LightningDetect;
import me.oringo.oringoclient.qolfeatures.module.impl.player.AntiVoid;
import me.oringo.oringoclient.qolfeatures.module.impl.player.AutoHeal;
import me.oringo.oringoclient.qolfeatures.module.impl.skyblock.Aimbot;
import me.oringo.oringoclient.qolfeatures.module.impl.skyblock.AntiNukebi;
import me.oringo.oringoclient.qolfeatures.module.impl.skyblock.BlazeSwapper;
import me.oringo.oringoclient.qolfeatures.module.impl.skyblock.Phase;
import me.oringo.oringoclient.qolfeatures.module.settings.Setting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.BooleanSetting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.ModeSetting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.NumberSetting;
import me.oringo.oringoclient.ui.hud.DraggableComponent;
import me.oringo.oringoclient.utils.MilliTimer;
import me.oringo.oringoclient.utils.MoveUtils;
import me.oringo.oringoclient.utils.Rotation;
import me.oringo.oringoclient.utils.RotationUtils;
import me.oringo.oringoclient.utils.SkyblockUtils;
import me.oringo.oringoclient.utils.api.HypixelAPI;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemArmor;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class KillAura extends Module {
  public static NumberSetting ;
  
  public static int ;
  
  public static BooleanSetting ;
  
  public static NumberSetting ;
  
  public static BooleanSetting ;
  
  public static EntityLivingBase ;
  
  public static float ;
  
  public static NumberSetting ;
  
  public static MilliTimer ;
  
  public static NumberSetting ;
  
  public static int ;
  
  public static MilliTimer ;
  
  public static ModeSetting ;
  
  public static NumberSetting ;
  
  public static ModeSetting ;
  
  public static MilliTimer ;
  
  public static NumberSetting ;
  
  public static BooleanSetting ;
  
  public static BooleanSetting ;
  
  public static BooleanSetting ;
  
  public static BooleanSetting ;
  
  public static NumberSetting ;
  
  public static BooleanSetting ;
  
  public static ModeSetting ;
  
  public static NumberSetting ;
  
  public static ModeSetting ;
  
  public static BooleanSetting ;
  
  public static NumberSetting ;
  
  public static int ;
  
  public static MilliTimer ;
  
  public static BooleanSetting  = new BooleanSetting("Players", false);
  
  public static double ;
  
  public static BooleanSetting ;
  
  public static BooleanSetting ;
  
  public static BooleanSetting ;
  
  static {
     = new BooleanSetting("Mobs", true);
     = new BooleanSetting("Through walls", true);
     = new BooleanSetting("Teams", true);
     = new BooleanSetting("Disable on join", true);
     = new BooleanSetting("No containers", true);
     = new BooleanSetting("Only swords", false);
     = new BooleanSetting("Movement fix", false);
     = new BooleanSetting("Swing on rotation", false);
     = new BooleanSetting("Shovel swap", false);
     = new BooleanSetting("Click only", false);
     = new BooleanSetting("Invisibles", false);
     = new ModeSetting("Sorting", "Distance", new String[] { "Distance", "Health", "Hurt", "Hp reverse" });
     = new ModeSetting("Rotation mode", "Simple", new String[] { "Simple", "Smooth", "Derp", "None" });
     = new ModeSetting("Autoblock", "None", new String[] { "Vanilla", "Hypixel", "Bypass", "Fake", "None" });
     = new ModeSetting("Mode", "Single", new String[] { "Single", "Switch" });
     = new NumberSetting("Range", 4.2D, 2.0D, 6.0D, 0.1D) {
        static {
        
        }
        
        public void (double param1Double) {
          super.(param1Double);
          if (() > KillAura..())
            super.(KillAura..()); 
        }
      };
     = new NumberSetting("Rotation Range", 6.0D, 2.0D, 12.0D, 0.1D) {
        public void (double param1Double) {
          super.(param1Double);
          if (() < KillAura..())
            super.(KillAura..()); 
        }
        
        static {
        
        }
      };
     = new NumberSetting("Fov", 360.0D, 30.0D, 360.0D, 1.0D);
     = new NumberSetting("Max rotation", 100.0D, 5.0D, 180.0D, 0.1D) {
        public boolean () {
          return !KillAura..("Simple");
        }
        
        public void (double param1Double) {
          super.(param1Double);
          if (KillAura..() > ())
            super.(KillAura..()); 
        }
        
        static {
        
        }
      };
     = new NumberSetting("Min rotation", 60.0D, 5.0D, 180.0D, 0.1D) {
        public boolean () {
          return !KillAura..("Simple");
        }
        
        public void (double param1Double) {
          super.(param1Double);
          if (() > KillAura..())
            super.(KillAura..()); 
        }
        
        static {
        
        }
      };
     = new NumberSetting("Max CPS", 13.0D, 1.0D, 20.0D, 1.0D) {
        static {
        
        }
        
        public void (double param1Double) {
          super.(param1Double);
          if (KillAura..() > ())
            super.(KillAura..()); 
        }
      };
     = new NumberSetting("Min CPS", 11.0D, 1.0D, 20.0D, 1.0D) {
        static {
        
        }
        
        public void (double param1Double) {
          super.(param1Double);
          if (KillAura..() < ())
            super.(KillAura..()); 
        }
      };
     = new NumberSetting("Smoothing", 12.0D, 1.0D, 20.0D, 0.1D) {
        static {
        
        }
        
        public boolean () {
          return !KillAura..("Smooth");
        }
      };
     = new NumberSetting("Switch delay", 100.0D, 0.0D, 250.0D, 1.0D, KillAura::);
     = 10.0D;
     = -1;
     = new MilliTimer();
     = new MilliTimer();
     = new MilliTimer();
     = new MilliTimer();
  }
  
  @SubscribeEvent(priority = EventPriority.NORMAL)
  public void (MotionUpdateEvent.Pre paramPre) {
    if (KeyPressEvent.() || (OringoClient..() && Scaffold..()) || !.(100L) || !() || Aimbot. || (.() && (mc.field_71439_g.func_70694_bm() == null || !(mc.field_71439_g.func_70694_bm().func_77973_b() instanceof net.minecraft.item.ItemSword)))) {
       = null;
       = 0;
      return;
    } 
     = AimAssist.();
    if (.() && !mc.field_71474_y.field_74312_F.func_151470_d())
      return; 
    if ( != null) {
      Rotation rotation = RotationUtils.(DraggableComponent.((Entity), 0.2F));
      switch (.()) {
        case "Derp":
          paramPre. = MathHelper.func_76142_g( += 45.0F);
          paramPre. = 90.0F;
          break;
        case "Smooth":
          paramPre.setRotation(AutoFish.(AntiVoid.(), rotation, (float).()).());
          break;
        case "Simple":
          paramPre.setRotation(GuessTheBuildAFK.(AntiVoid.(), rotation, (float)(.() + Math.abs(.() - .()) * (new Random()).nextFloat())).());
          break;
      } 
      paramPre.setPitch(MoveUtils.(paramPre., 90.0F, -90.0F));
      if (.() &&  instanceof EntityPlayer && ((EntityPlayer))) {
         = mc.field_71439_g.field_71071_by.field_70461_c;
        for (byte b = 0; b < 9; b++) {
          if (mc.field_71439_g.field_71071_by.func_70301_a(b) != null && mc.field_71439_g.field_71071_by.func_70301_a(b).func_77973_b() instanceof net.minecraft.item.ItemSpade) {
            LunarClient.(b);
            break;
          } 
        } 
      } 
    } 
  }
  
  public void () {
     = null;
     = 0;
    BlazeSwapper.();
  }
  
  public boolean () {
    if (.(Math.round(1000.0D / ))) {
      .();
       = AutoHeal.(.(), .());
      return true;
    } 
    return false;
  }
  
  @SubscribeEvent(priority = EventPriority.HIGHEST)
  public void (MotionUpdateEvent.Post paramPost) {
    if (.() && !mc.field_71474_y.field_74312_F.func_151470_d()) {
       = 0;
      return;
    } 
    if ( != null && HypixelAPI.((Entity)) < .()) {
      if ( > 0) {
        switch (.()) {
          case "Bypass":
            BlazeSwapper.();
            break;
        } 
        if (HypixelAPI.((Entity)) < .()) {
          while ( > 0) {
            mc.field_71439_g.func_71038_i();
            if (LightningDetect.(RotationUtils.(Phase.((Entity))), AntiVoid.()) < 5.0D || .("None") || .("Derp") || OringoClient..() || (AntiNukebi. != null && AntiNukebi..())) {
              mc.field_71442_b.func_78764_a((EntityPlayer)mc.field_71439_g, (Entity));
              if (.((long).())) {
                ++;
                .();
              } 
            } 
            --;
          } 
        } else {
          if (.())
            mc.field_71439_g.func_71038_i(); 
           = 0;
        } 
      } 
      if (mc.field_71439_g.func_70694_bm() != null && mc.field_71439_g.func_70694_bm().func_77973_b() instanceof net.minecraft.item.ItemSword)
        switch (.()) {
          case "Bypass":
          case "Vanilla":
            if (!mc.field_71439_g.func_71039_bw())
              (); 
            break;
          case "Hypixel":
            if (.(mc.field_71439_g.field_70122_E ? 200L : 100L)) {
              ();
              ConfigCommand.((Packet)new C09PacketHeldItemChange(mc.field_71439_g.field_71071_by.field_70461_c));
              .();
            } 
            break;
        }  
    } else {
       = 0;
    } 
    if (.() &&  != -1) {
      LunarClient.();
       = -1;
    } 
  }
  
  @SubscribeEvent(receiveCanceled = true)
  public void (PacketReceivedEvent paramPacketReceivedEvent) {
    if (paramPacketReceivedEvent. instanceof net.minecraft.network.play.server.S08PacketPlayerPosLook) {
      .();
    } else if (paramPacketReceivedEvent. instanceof net.minecraft.network.play.server.S30PacketWindowItems && mc.field_71439_g != null && () && .("Hypixel") && mc.field_71439_g.func_71039_bw()) {
      paramPacketReceivedEvent.setCanceled(true);
    } 
  }
  
  public static String () {
    ScoreObjective scoreObjective = SkyblockUtils.mc.field_71439_g.func_96123_co().func_96539_a(1);
    return (scoreObjective == null) ? "" : scoreObjective.func_96678_d();
  }
  
  @SubscribeEvent
  public void (RenderWorldLastEvent paramRenderWorldLastEvent) {
    if (() && ())
      ++; 
  }
  
  @SubscribeEvent
  public void (WorldEvent.Load paramLoad) {
    if (() && .())
      (); 
  }
  
  @SubscribeEvent
  public void (MoveFlyingEvent paramMoveFlyingEvent) {
    if (() &&  != null && .())
      paramMoveFlyingEvent.setYaw(HClip.((Entity)).()); 
  }
  
  public KillAura() {
    super("Kill Aura", 0, Module.Category.);
    (new Setting[] { 
          (Setting), (Setting), (Setting), (Setting), (Setting), (Setting), (Setting), (Setting), (Setting), (Setting), 
          (Setting), (Setting), (Setting), (Setting), (Setting), (Setting), (Setting), (Setting), (Setting), (Setting), 
          (Setting), (Setting), (Setting), (Setting), (Setting) });
  }
  
  public void () {
    if (mc.field_71439_g != null && mc.field_71439_g.func_70694_bm() != null && mc.field_71439_g.func_70694_bm().func_77975_n() == EnumAction.BLOCK)
      mc.field_71442_b.func_78769_a((EntityPlayer)mc.field_71439_g, (World)mc.field_71441_e, mc.field_71439_g.func_70694_bm()); 
  }
  
  public boolean (EntityPlayer paramEntityPlayer) {
    for (byte b = 1; b < 5; b++) {
      if (paramEntityPlayer.func_71124_b(b) != null && paramEntityPlayer.func_71124_b(b).func_77973_b() instanceof ItemArmor && ((ItemArmor)paramEntityPlayer.func_71124_b(b).func_77973_b()).func_82812_d() == ItemArmor.ArmorMaterial.DIAMOND)
        return true; 
    } 
    return false;
  }
}
