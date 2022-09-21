package me.oringo.oringoclient.qolfeatures.module.impl.player;

import java.awt.Color;
import java.lang.reflect.Method;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import me.oringo.oringoclient.commands.impl.ClipCommand;
import me.oringo.oringoclient.commands.impl.NamesCommand;
import me.oringo.oringoclient.events.impl.BlockChangeEvent;
import me.oringo.oringoclient.events.impl.MotionUpdateEvent;
import me.oringo.oringoclient.events.impl.PacketSentEvent;
import me.oringo.oringoclient.qolfeatures.Updater;
import me.oringo.oringoclient.qolfeatures.module.Module;
import me.oringo.oringoclient.qolfeatures.module.impl.combat.BlockHit;
import me.oringo.oringoclient.qolfeatures.module.impl.render.ChinaHat;
import me.oringo.oringoclient.qolfeatures.module.impl.skyblock.DojoHelper;
import me.oringo.oringoclient.qolfeatures.module.settings.Setting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.BooleanSetting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.NumberSetting;
import me.oringo.oringoclient.utils.MathUtil;
import me.oringo.oringoclient.utils.MilliTimer;
import me.oringo.oringoclient.utils.StencilUtils;
import me.oringo.oringoclient.utils.shader.BlurUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.network.play.client.C0EPacketClickWindow;
import net.minecraft.potion.Potion;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class AutoHeal extends Module {
  public boolean ;
  
  public static NumberSetting ;
  
  public static BooleanSetting ;
  
  public static MilliTimer ;
  
  public static BooleanSetting ;
  
  public static MilliTimer ;
  
  public static NumberSetting ;
  
  public static NumberSetting ;
  
  public static BooleanSetting ;
  
  public static MilliTimer ;
  
  public int  = 0;
  
  public static BooleanSetting ;
  
  public static MilliTimer ;
  
  public static MilliTimer ;
  
  public static BooleanSetting  = new BooleanSetting("Heads", false);
  
  public static BooleanSetting ;
  
  public static BooleanSetting ;
  
  public static NumberSetting ;
  
  public static BooleanSetting ;
  
  public static BooleanSetting ;
  
  public static NumberSetting ;
  
  public boolean ;
  
  public static NumberSetting ;
  
  public static BooleanSetting ;
  
  public AutoHeal() {
    super("Auto Heal", Module.Category.);
    (new Setting[] { 
          (Setting), (Setting), (Setting), (Setting), (Setting), (Setting), (Setting), (Setting), (Setting), (Setting), 
          (Setting), (Setting), (Setting), (Setting), (Setting), (Setting) });
  }
  
  public static void () {
    try {
      Method method = null;
      try {
        method = Minecraft.class.getDeclaredMethod("func_147121_ag", new Class[0]);
      } catch (NoSuchMethodException noSuchMethodException) {
        method = Minecraft.class.getDeclaredMethod("rightClickMouse", new Class[0]);
      } 
      method.setAccessible(true);
      method.invoke(Minecraft.func_71410_x(), new Object[0]);
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
  }
  
  @SubscribeEvent(receiveCanceled = true, priority = EventPriority.HIGHEST)
  public void (ClientChatReceivedEvent paramClientChatReceivedEvent) {
    if (paramClientChatReceivedEvent.type == 2) {
      if (paramClientChatReceivedEvent.message.func_150260_c().contains("❈ Defense")) {
        Matcher matcher = Pattern.compile("§3(.*?)ʬ").matcher(paramClientChatReceivedEvent.message.func_150260_c());
        if (matcher.find()) {
          String str = matcher.group(1);
          this. = Integer.parseInt(str);
        } else {
          this. = 0;
        } 
      } 
    } else if (() && .() && paramClientChatReceivedEvent.message.func_150254_d().contains("Creeper Veil Activated!")) {
      int i = ClipCommand.(AutoHeal::);
      if (i != -1) {
        mc.func_147114_u().func_147298_b().func_179290_a((Packet)new C09PacketHeldItemChange(i));
        mc.func_147114_u().func_147297_a((Packet)new C08PacketPlayerBlockPlacement(mc.field_71439_g.field_71071_by.func_70301_a(i)));
        this. = true;
        .();
      } 
    } 
  }
  
  @SubscribeEvent
  public void (PacketSentEvent paramPacketSentEvent) {
    if (paramPacketSentEvent. instanceof C08PacketPlayerBlockPlacement && ((C08PacketPlayerBlockPlacement)paramPacketSentEvent.).func_149574_g() != null && MathUtil.(((C08PacketPlayerBlockPlacement)paramPacketSentEvent.).func_149574_g()) && .((long).()))
      .(); 
    if (() && paramPacketSentEvent. instanceof C02PacketUseEntity && ((C02PacketUseEntity)paramPacketSentEvent.).func_149565_c() == C02PacketUseEntity.Action.ATTACK)
      this. = true; 
  }
  
  public static double (double paramDouble1, double paramDouble2) {
    return paramDouble2 + (paramDouble1 - paramDouble2) * MathUtil..nextFloat();
  }
  
  static {
     = new BooleanSetting("Soup", false);
     = new BooleanSetting("Wither Impact", true);
     = new BooleanSetting("Wand", false);
     = new BooleanSetting("Gloom Lock", false);
     = new BooleanSetting("Zombie Sword", false);
     = new BooleanSetting("Power Orb", false);
     = new BooleanSetting("Sword of Bad Health", false);
     = new BooleanSetting("Ragnarok Axe", false);
     = new BooleanSetting("From Inv", false);
     = new NumberSetting("Health", 70.0D, 0.0D, 100.0D, 1.0D);
     = new NumberSetting("Gloomlock Health", 70.0D, 0.0D, 100.0D, 1.0D, AutoHeal::);
     = new NumberSetting("Overflow mana", 600.0D, 0.0D, 1000.0D, 50.0D, AutoHeal::);
     = new NumberSetting("Power Orb Health", 85.0D, 0.0D, 100.0D, 1.0D, AutoHeal::);
     = new NumberSetting("Impact Health", 40.0D, 0.0D, 100.0D, 1.0D, AutoHeal::);
     = new NumberSetting("Delay", 5400.0D, 5000.0D, 6000.0D, 1.0D, AutoHeal::);
     = new MilliTimer();
     = new MilliTimer();
     = new MilliTimer();
     = new MilliTimer();
     = new MilliTimer();
  }
  
  @SubscribeEvent
  public void (MotionUpdateEvent paramMotionUpdateEvent) {
    if (())
      if (paramMotionUpdateEvent.isPre()) {
        if ((mc.field_71439_g.func_110143_aJ() / mc.field_71439_g.func_110138_aP()) <= .() / 100.0D && .() && (!.() ? ClipCommand.(MathUtil::) : AutoTool.(MathUtil::)) != -1 && .(5200L))
          paramMotionUpdateEvent.setPitch(90.0F); 
      } else {
        if (.(500L) && .() && (mc.field_71439_g.func_110143_aJ() / mc.field_71439_g.func_110138_aP()) >= .() / 100.0D && this. < .() && BlurUtils.(AutoHeal::, true))
          .(); 
        if (.(500L) && .() && (mc.field_71439_g.func_110143_aJ() / mc.field_71439_g.func_110138_aP()) <= .() / 100.0D) {
          List<EntityArmorStand> list = mc.field_71441_e.func_175644_a(EntityArmorStand.class, AutoHeal::);
          list.sort(Comparator.comparingDouble(AutoHeal::));
          int i = 4;
          if (!list.isEmpty())
            i = NamesCommand.(((EntityArmorStand)list.get(0)).func_70005_c_()); 
          byte b = 0;
          int j = -1;
          while (b < i) {
            byte b1 = b;
            if (.()) {
              j = AutoTool.(b1::);
            } else {
              j = ClipCommand.(b1::);
            } 
            if (j != -1)
              break; 
            b++;
          } 
          if (j != -1) {
            if (.()) {
              if (j >= 36) {
                mc.func_147114_u().func_147298_b().func_179290_a((Packet)new C09PacketHeldItemChange(j - 36));
                mc.func_147114_u().func_147298_b().func_179290_a((Packet)new C08PacketPlayerBlockPlacement(mc.field_71439_g.field_71069_bz.func_75139_a(j - 36).func_75211_c()));
                mc.func_147114_u().func_147298_b().func_179290_a((Packet)new C09PacketHeldItemChange(mc.field_71439_g.field_71071_by.field_70461_c));
              } else {
                short s = mc.field_71439_g.field_71070_bA.func_75136_a(mc.field_71439_g.field_71071_by);
                mc.func_147114_u().func_147298_b().func_179290_a((Packet)new C0EPacketClickWindow(mc.field_71439_g.field_71070_bA.field_75152_c, j, mc.field_71439_g.field_71071_by.field_70461_c, 2, mc.field_71439_g.field_71070_bA.func_75139_a(j).func_75211_c(), s));
                mc.func_147114_u().func_147298_b().func_179290_a((Packet)new C08PacketPlayerBlockPlacement(mc.field_71439_g.field_71069_bz.func_75139_a(j).func_75211_c()));
                mc.func_147114_u().func_147298_b().func_179290_a((Packet)new C0EPacketClickWindow(mc.field_71439_g.field_71070_bA.field_75152_c, j, mc.field_71439_g.field_71071_by.field_70461_c, 2, mc.field_71439_g.field_71070_bA.func_75139_a(j).func_75211_c(), s));
              } 
            } else {
              mc.func_147114_u().func_147298_b().func_179290_a((Packet)new C09PacketHeldItemChange(j));
              mc.func_147114_u().func_147298_b().func_179290_a((Packet)new C08PacketPlayerBlockPlacement(mc.field_71439_g.field_71071_by.func_70301_a(j)));
              mc.func_147114_u().func_147298_b().func_179290_a((Packet)new C09PacketHeldItemChange(mc.field_71439_g.field_71071_by.field_70461_c));
            } 
            .();
          } 
        } 
        if (.() && (mc.field_71439_g.func_110143_aJ() / mc.field_71439_g.func_110138_aP()) <= .() / 100.0D && .((long).()) && BlurUtils.(MathUtil::, false))
          .(); 
        if ((mc.field_71439_g.func_110143_aJ() / mc.field_71439_g.func_110138_aP()) <= .() / 100.0D) {
          if (.(500L)) {
            if (.() && BlurUtils.(AutoHeal::, false))
              .(); 
            if (.() && !mc.field_71439_g.func_70644_a(Potion.field_76428_l) && BlurUtils.(AutoHeal::, false))
              .(); 
            if (.() && BlurUtils.(AutoHeal::, false))
              .(); 
          } 
          if (.(7200L) && .() && BlurUtils.(AutoHeal::, false))
            .(); 
        } 
        if (.() && .(5400L) && this.) {
          this. = false;
          BlurUtils.(AutoHeal::, false);
          .();
        } 
        if (.() && this. && .(3300L)) {
          this. = false;
          BlurUtils.(AutoHeal::, false);
        } 
      }  
  }
  
  public static void (EntityLivingBase paramEntityLivingBase, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, ModelBase paramModelBase, Color paramColor) {
    Minecraft minecraft = Minecraft.func_71410_x();
    boolean bool = minecraft.field_71474_y.field_74347_j;
    float f = minecraft.field_71474_y.field_74333_Y;
    minecraft.field_71474_y.field_74347_j = false;
    minecraft.field_71474_y.field_74333_Y = 100000.0F;
    GlStateManager.func_179117_G();
    BlockChangeEvent.(paramColor);
    ChinaHat.(2.0F);
    paramModelBase.func_78088_a((Entity)paramEntityLivingBase, paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramFloat5, paramFloat6);
    BlockChangeEvent.(paramColor);
    StencilUtils.();
    paramModelBase.func_78088_a((Entity)paramEntityLivingBase, paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramFloat5, paramFloat6);
    BlockChangeEvent.(paramColor);
    DojoHelper.();
    paramModelBase.func_78088_a((Entity)paramEntityLivingBase, paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramFloat5, paramFloat6);
    BlockChangeEvent.(paramColor);
    Updater.(paramColor);
    paramModelBase.func_78088_a((Entity)paramEntityLivingBase, paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramFloat5, paramFloat6);
    BlockChangeEvent.(paramColor);
    BlockHit.();
    BlockChangeEvent.(Color.WHITE);
    minecraft.field_71474_y.field_74347_j = bool;
    minecraft.field_71474_y.field_74333_Y = f;
  }
}
