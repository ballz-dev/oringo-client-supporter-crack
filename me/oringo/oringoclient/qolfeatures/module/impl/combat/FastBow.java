package me.oringo.oringoclient.qolfeatures.module.impl.combat;

import me.oringo.oringoclient.events.impl.PacketSentEvent;
import me.oringo.oringoclient.qolfeatures.module.Module;
import me.oringo.oringoclient.qolfeatures.module.impl.render.CustomInterfaces;
import me.oringo.oringoclient.qolfeatures.module.settings.Setting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.ModeSetting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.NumberSetting;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class FastBow extends Module {
  public NumberSetting  = new NumberSetting("Repeats", 1.0D, 1.0D, 20.0D, 1.0D, this::lambda$new$1);
  
  public ModeSetting  = new ModeSetting("Mode", "Instant", new String[] { "Instant", "Custom" });
  
  public NumberSetting  = new NumberSetting("Packets", 20.0D, 1.0D, 40.0D, 1.0D, this::lambda$new$0);
  
  @SubscribeEvent
  public void (PacketSentEvent.Post paramPost) {
    if (!())
      return; 
    if (paramPost. instanceof C08PacketPlayerBlockPlacement) {
      C08PacketPlayerBlockPlacement c08PacketPlayerBlockPlacement = (C08PacketPlayerBlockPlacement)paramPost.;
      if (c08PacketPlayerBlockPlacement.func_149574_g() != null && c08PacketPlayerBlockPlacement.func_149574_g().func_77975_n() == EnumAction.BOW)
        for (byte b = 0; b < (this..("Instant") ? this..() : 1.0D); b++) {
          byte b1 = 0;
          while (true) {
            if (b1 < (this..("Custom") ? this..() : 20.0D)) {
              CustomInterfaces.((Packet)new C03PacketPlayer(mc.field_71439_g.field_70122_E));
              b1++;
              continue;
            } 
            if (this..("Instant")) {
              mc.field_71442_b.func_78766_c((EntityPlayer)mc.field_71439_g);
              KeyBinding.func_74510_a(mc.field_71474_y.field_74313_G.func_151463_i(), false);
              if (this..() > 1.0D)
                CustomInterfaces.((Packet)new C08PacketPlayerBlockPlacement(mc.field_71439_g.func_70694_bm())); 
            } 
            break;
          } 
        }  
    } 
  }
  
  public FastBow() {
    super("Fast Bow", Module.Category.);
    (new Setting[] { (Setting)this., (Setting)this., (Setting)this. });
  }
}
