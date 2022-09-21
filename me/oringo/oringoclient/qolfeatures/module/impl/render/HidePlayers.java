package me.oringo.oringoclient.qolfeatures.module.impl.render;

import me.oringo.oringoclient.OringoClient;
import me.oringo.oringoclient.qolfeatures.module.Module;
import me.oringo.oringoclient.qolfeatures.module.impl.player.ArmorSwap;
import me.oringo.oringoclient.qolfeatures.module.impl.player.InvManager;
import me.oringo.oringoclient.qolfeatures.module.settings.Setting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.BooleanSetting;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.opengl.GL11;

public class HidePlayers extends Module {
  public static BooleanSetting  = new BooleanSetting("Only AntiBot valid", true);
  
  public static boolean () {
    return (OringoClient.mc.field_71439_g.field_70701_bs != 0.0F || OringoClient.mc.field_71439_g.field_70702_br != 0.0F);
  }
  
  @SubscribeEvent
  public void (TickEvent.ClientTickEvent paramClientTickEvent) {
    if (!() || paramClientTickEvent.phase == TickEvent.Phase.START || mc.field_71441_e == null)
      return; 
    for (EntityPlayer entityPlayer : mc.field_71441_e.field_73010_i) {
      if (entityPlayer instanceof net.minecraft.client.entity.EntityOtherPlayerMP && (!.() || ArmorSwap.((Entity)entityPlayer)))
        entityPlayer.func_70107_b(2137.0D, 2137.0D, 2137.0D); 
    } 
  }
  
  public static boolean (ItemStack paramItemStack, int paramInt) {
    if (!(paramItemStack.func_77973_b() instanceof ItemArmor))
      return false; 
    for (byte b = 5; b < 45; b++) {
      if (InvManager.mc.field_71439_g.field_71069_bz.func_75139_a(b).func_75216_d()) {
        ItemStack itemStack = InvManager.mc.field_71439_g.field_71069_bz.func_75139_a(b).func_75211_c();
        if (itemStack.func_77973_b() instanceof ItemArmor && ((PlayerESP.(itemStack) > PlayerESP.(paramItemStack) && paramInt < 9) || (paramInt >= 9 && PlayerESP.(itemStack) >= PlayerESP.(paramItemStack) && paramInt != b)) && ((ItemArmor)itemStack.func_77973_b()).field_77881_a == ((ItemArmor)paramItemStack.func_77973_b()).field_77881_a)
          return false; 
      } 
    } 
    return true;
  }
  
  public HidePlayers() {
    super("Hide Players", Module.Category.);
    (new Setting[] { (Setting) });
  }
  
  public static void (double paramDouble1, double paramDouble2, double paramDouble3) {
    GL11.glTranslated(paramDouble1, paramDouble2, 0.0D);
    GL11.glScaled(paramDouble3, paramDouble3, paramDouble3);
    GL11.glTranslated(-paramDouble1, -paramDouble2, 0.0D);
  }
}
