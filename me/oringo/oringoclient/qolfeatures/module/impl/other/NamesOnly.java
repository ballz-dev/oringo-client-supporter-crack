package me.oringo.oringoclient.qolfeatures.module.impl.other;

import com.mojang.realmsclient.gui.ChatFormatting;
import java.util.ArrayList;
import java.util.List;
import me.oringo.oringoclient.events.impl.WorldJoinEvent;
import me.oringo.oringoclient.qolfeatures.module.Module;
import me.oringo.oringoclient.qolfeatures.module.impl.player.NoRotate;
import me.oringo.oringoclient.qolfeatures.module.settings.Setting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.BooleanSetting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.ModeSetting;
import me.oringo.oringoclient.utils.MathUtil;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Mouse;

public class NamesOnly extends Module {
  public static List<String> ;
  
  public static ModeSetting ;
  
  public boolean ;
  
  public static BooleanSetting  = new BooleanSetting("Middle Click", false);
  
  public static NamesOnly ;
  
  @SubscribeEvent
  public void (TickEvent.ClientTickEvent paramClientTickEvent) {
    if (mc.field_71439_g == null || mc.field_71441_e == null || !.())
      return; 
    if (Mouse.isButtonDown(2) && mc.field_71462_r == null) {
      if (mc.field_147125_j != null && !this. && !(mc.field_147125_j instanceof net.minecraft.entity.item.EntityArmorStand) && mc.field_147125_j instanceof net.minecraft.entity.EntityLivingBase) {
        String str = ChatFormatting.stripFormatting(mc.field_147125_j.func_70005_c_().toLowerCase());
        if (!.contains(str)) {
          .add(str);
          NoRotate.("Oringo Client", String.valueOf((new StringBuilder()).append("Added ").append(ChatFormatting.AQUA).append(str).append(ChatFormatting.RESET).append(" to name sorting")), 2000);
        } else {
          .remove(str);
          NoRotate.("Oringo Client", String.valueOf((new StringBuilder()).append("Removed ").append(ChatFormatting.AQUA).append(str).append(ChatFormatting.RESET).append(" from name sorting")), 2000);
        } 
        MathUtil.();
      } 
      this. = true;
    } else {
      this. = false;
    } 
  }
  
  static {
     = new ModeSetting("Mode", "Friends", new String[] { "Friends", "Enemies" });
     = new ArrayList<>();
  }
  
  public NamesOnly() {
    super("Names Only", Module.Category.OTHER);
    (new Setting[] { (Setting), (Setting) });
     = this;
    WorldJoinEvent.();
  }
}
