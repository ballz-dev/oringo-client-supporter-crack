package me.oringo.oringoclient.qolfeatures.module.impl.skyblock;

import me.oringo.oringoclient.qolfeatures.module.Module;
import me.oringo.oringoclient.qolfeatures.module.impl.player.AutoTool;
import me.oringo.oringoclient.qolfeatures.module.impl.render.CustomInterfaces;
import me.oringo.oringoclient.qolfeatures.module.settings.Setting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.BooleanSetting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.NumberSetting;
import me.oringo.oringoclient.utils.MilliTimer;
import me.oringo.oringoclient.utils.SkyblockUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.opengl.GL11;

public class AutoRogueSword extends Module {
  public BooleanSetting  = new BooleanSetting("Only dungeon", false);
  
  public BooleanSetting  = new BooleanSetting("From inv", false);
  
  public NumberSetting  = new NumberSetting("Delay", 31.0D, 0.0D, 100.0D, 1.0D);
  
  public MilliTimer  = new MilliTimer();
  
  @SubscribeEvent
  public void (TickEvent.ClientTickEvent paramClientTickEvent) {
    if (mc.field_71439_g == null || (!SkyblockUtils.inDungeon && this..()) || !())
      return; 
    if (this..((long)this..() * 1000L))
      if (this..() && Module.()) {
        int i = AutoTool.(AutoRogueSword::);
        if (i != -1) {
          mc.field_71442_b.func_78753_a(0, i, mc.field_71439_g.field_71071_by.field_70461_c, 2, (EntityPlayer)mc.field_71439_g);
          mc.func_147114_u().func_147298_b().func_179290_a((Packet)new C08PacketPlayerBlockPlacement(null));
          mc.field_71442_b.func_78753_a(0, i, mc.field_71439_g.field_71071_by.field_70461_c, 2, (EntityPlayer)mc.field_71439_g);
        } 
      } else {
        for (byte b = 0; b < 9; b++) {
          if (mc.field_71439_g.field_71071_by.func_70301_a(b) != null && mc.field_71439_g.field_71071_by.func_70301_a(b).func_82833_r().toLowerCase().contains("rogue sword")) {
            CustomInterfaces.((Packet)new C09PacketHeldItemChange(b));
            mc.func_147114_u().func_147298_b().func_179290_a((Packet)new C08PacketPlayerBlockPlacement(mc.field_71439_g.field_71071_by.func_70301_a(b)));
            CustomInterfaces.((Packet)new C09PacketHeldItemChange(mc.field_71439_g.field_71071_by.field_70461_c));
            this..();
            break;
          } 
        } 
      }  
  }
  
  public AutoRogueSword() {
    super("Auto Rogue", 0, Module.Category.);
    (new Setting[] { (Setting)this., (Setting)this., (Setting)this. });
  }
  
  public static void () {
    GL11.glDisable(2960);
  }
  
  public static String (ItemStack paramItemStack) {
    return (paramItemStack == null) ? "null" : paramItemStack.func_82833_r();
  }
}
