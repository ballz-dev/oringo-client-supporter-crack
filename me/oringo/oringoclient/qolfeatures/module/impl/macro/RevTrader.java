package me.oringo.oringoclient.qolfeatures.module.impl.macro;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.oringo.oringoclient.events.impl.MotionUpdateEvent;
import me.oringo.oringoclient.events.impl.MoveStateUpdateEvent;
import me.oringo.oringoclient.lunarspoof.LunarClient;
import me.oringo.oringoclient.qolfeatures.BackgroundProcess;
import me.oringo.oringoclient.qolfeatures.module.Module;
import me.oringo.oringoclient.qolfeatures.module.impl.combat.Hitboxes;
import me.oringo.oringoclient.qolfeatures.module.impl.other.GuessTheBuildAFK;
import me.oringo.oringoclient.qolfeatures.module.impl.other.HClip;
import me.oringo.oringoclient.qolfeatures.module.impl.other.NoCarpet;
import me.oringo.oringoclient.qolfeatures.module.impl.player.AntiVoid;
import me.oringo.oringoclient.qolfeatures.module.impl.skyblock.RemoveAnnoyingMobs;
import me.oringo.oringoclient.qolfeatures.module.settings.Setting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.BooleanSetting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.NumberSetting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.StringSetting;
import me.oringo.oringoclient.utils.MilliTimer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.Vec3;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class RevTrader extends Module {
  public static StringSetting ;
  
  public static NumberSetting ;
  
  public static EntityZombie ;
  
  public static MilliTimer ;
  
  public static NumberSetting ;
  
  public boolean  = false;
  
  public static BooleanSetting ;
  
  public static NumberSetting ;
  
  public int  = 8;
  
  public Vec3 ;
  
  public static BooleanSetting ;
  
  public static StringSetting  = new StringSetting("Weapon", "Axe of the Shredded");
  
  @SubscribeEvent
  public void (MotionUpdateEvent paramMotionUpdateEvent) {
    if (!() || this.) {
       = null;
      return;
    } 
     = NoCarpet.();
    if ( == null)
      return; 
    if (paramMotionUpdateEvent.isPre()) {
      int i = BackgroundProcess.(.());
      if (i != -1)
        LunarClient.(i); 
      paramMotionUpdateEvent.setRotation(GuessTheBuildAFK.(AntiVoid.(), HClip.((Entity)), 60.0F));
    } else if (.(1000.0D / .(), true) && paramMotionUpdateEvent.getRotation().(HClip.((Entity)))) {
      mc.field_71439_g.func_71038_i();
      mc.field_71442_b.func_78764_a((EntityPlayer)mc.field_71439_g, (Entity));
    } 
  }
  
  public RevTrader() {
    super("Rev Trader", Module.Category.);
    (new Setting[] { (Setting), (Setting), (Setting), (Setting), (Setting), (Setting), (Setting) });
  }
  
  @SubscribeEvent
  public void (MoveStateUpdateEvent paramMoveStateUpdateEvent) {
    this.++;
    if (!() || !this. || mc.field_71462_r instanceof net.minecraft.client.gui.inventory.GuiContainer)
      return; 
    paramMoveStateUpdateEvent.setSneak(.());
    if (this. > 9 || !.())
      return; 
    paramMoveStateUpdateEvent.setForward(0.0F).setStrafe((this. < 3 || this. > 6) ? 1.0F : -1.0F);
    if (this. == 9) {
      paramMoveStateUpdateEvent.setStrafe(0.0F);
      mc.field_71439_g.field_70179_y = mc.field_71439_g.field_70159_w = 0.0D;
      mc.field_71439_g.func_70107_b(this..field_72450_a, this..field_72448_b, this..field_72449_c);
    } 
  }
  
  @SubscribeEvent(receiveCanceled = true)
  public void (ClientChatReceivedEvent paramClientChatReceivedEvent) {
    if (!())
      return; 
    String str = ChatFormatting.stripFormatting(paramClientChatReceivedEvent.message.func_150254_d()).trim();
    if (str.startsWith("LOOT SHARE")) {
      this. = true;
      this. = mc.field_71439_g.func_174791_d();
      this. = 0;
      if (!.()) {
        int i = BackgroundProcess.(.());
        if (i != -1)
          LunarClient.(i); 
      } 
    } else if (str.equalsIgnoreCase("slayer quest complete!")) {
      this. = false;
    } 
  }
  
  static {
     = new StringSetting("Magic find weapon");
     = new NumberSetting("Range", 4.0D, 1.0D, 6.0D, 0.1D);
     = new NumberSetting("CPS", 10.0D, 1.0D, 20.0D, 1.0D);
     = new NumberSetting("Fov", 90.0D, 30.0D, 360.0D, 1.0D);
     = new BooleanSetting("Sneak", true);
     = new BooleanSetting("Move", true);
     = new MilliTimer();
  }
}
