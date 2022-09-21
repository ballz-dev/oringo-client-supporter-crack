package me.oringo.oringoclient.qolfeatures.module.impl.player;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.oringo.oringoclient.commands.impl.ClipCommand;
import me.oringo.oringoclient.events.impl.PlayerUpdateEvent;
import me.oringo.oringoclient.events.impl.WorldJoinEvent;
import me.oringo.oringoclient.qolfeatures.module.Module;
import me.oringo.oringoclient.qolfeatures.module.settings.Setting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.NumberSetting;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class AutoEcho extends Module {
  public NumberSetting  = new NumberSetting("Health", 5.0D, 1.0D, 20.0D, 0.5D);
  
  public long  = System.currentTimeMillis();
  
  public AutoEcho() {
    super("Auto Echo", Module.Category.);
    ((Setting)this.);
  }
  
  @SubscribeEvent
  public void (PlayerUpdateEvent paramPlayerUpdateEvent) {
    if (mc.field_71439_g.func_110143_aJ() < this..() && this. + 40000L < System.currentTimeMillis()) {
      int i = ClipCommand.(AutoEcho::);
      if (i != -1) {
        mc.func_147114_u().func_147298_b().func_179290_a((Packet)new C09PacketHeldItemChange(i));
        mc.func_147114_u().func_147297_a((Packet)new C08PacketPlayerBlockPlacement(mc.field_71439_g.field_71071_by.func_70301_a(i)));
        WorldJoinEvent.("Echo used!", 2500);
        this. = System.currentTimeMillis();
        mc.func_147114_u().func_147298_b().func_179290_a((Packet)new C09PacketHeldItemChange(mc.field_71439_g.field_71071_by.field_70461_c));
      } 
    } 
  }
  
  @SubscribeEvent
  public void (ClientChatReceivedEvent paramClientChatReceivedEvent) {
    if (ChatFormatting.stripFormatting(paramClientChatReceivedEvent.message.func_150254_d()).equals("You used your Echo ability!"))
      this. = System.currentTimeMillis(); 
    if (ChatFormatting.stripFormatting(paramClientChatReceivedEvent.message.func_150254_d()).trim().equals("Gather resources and equipment on your"))
      this. = System.currentTimeMillis() - 30000L; 
  }
}
