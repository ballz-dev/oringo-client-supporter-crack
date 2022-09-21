package me.oringo.oringoclient.qolfeatures.module.impl.macro;

import me.oringo.oringoclient.commands.impl.BanCommand;
import me.oringo.oringoclient.commands.impl.ClipCommand;
import me.oringo.oringoclient.events.impl.PacketReceivedEvent;
import me.oringo.oringoclient.events.impl.PlayerUpdateEvent;
import me.oringo.oringoclient.events.impl.PreAttackEvent;
import me.oringo.oringoclient.lunarspoof.LunarClient;
import me.oringo.oringoclient.qolfeatures.module.Module;
import me.oringo.oringoclient.qolfeatures.module.impl.movement.Sneak;
import me.oringo.oringoclient.utils.MilliTimer;
import me.oringo.oringoclient.utils.Rotation;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class AutoFish extends Module {
  public static Stage ;
  
  public boolean ;
  
  public static String[]  = new String[] { 
      "Squid", "Sea Walker", "Sea Guardian", "Sea Witch", "Sea Archer", "Monster of the Deep", "Catfish", "Carrot King", "sea Leech", "Guardian Defender", 
      "Deep Sea Protector", "Water Hydra", "Sea Emperor", "Oasis Rabbit", "Oasis Sheep", "Water Worm", "Poisoned Water Worm", "Flaming Worm", "Lava Blaze", "Lava Pigman", 
      "Zombie Miner", "Magma Slug", "Moogma", "Lava Leech", "Pyroclastic Worm", "Lava Flame", "Fire Eel", "Taurus", "Thunder", "Thunder", 
      "Lord Jawbus", "Scarecrow", "Nightmare", "Werewolf", "Phantom Fisher", "Grim Reaper", "Frozen Steve", "Frosty", "Grinch", "Yeti", 
      "Nurse Shark", "Blue Shark", "Tiger Shark", "Great White Shark" };
  
  public boolean ;
  
  public MilliTimer  = new MilliTimer();
  
  @SubscribeEvent
  public void (PlayerUpdateEvent paramPlayerUpdateEvent) {
    if (!())
      return; 
    switch (null.[.ordinal()]) {
      case 1:
        if (mc.field_71439_g.func_70694_bm() == null || mc.field_71439_g.func_70694_bm().func_77973_b() != Items.field_151112_aM) {
          int i = ClipCommand.(AutoFish::);
          if (i != -1) {
            this..();
            LunarClient.(i);
            return;
          } 
          (false);
          break;
        } 
        if (mc.field_71439_g.field_71104_cf != null) {
          if (this..(250L, true) && this.) {
            Sneak.("RMB");
            BanCommand.();
            ();
          } 
          break;
        } 
        if (this..(250L, true)) {
          Sneak.("Recast");
          BanCommand.();
        } 
        ();
        break;
    } 
  }
  
  static {
     = Stage.;
  }
  
  public void () {
     = Stage.;
    ();
    this..();
    super.();
  }
  
  public void () {
    this. = this. = false;
  }
  
  public static Rotation (Rotation paramRotation1, Rotation paramRotation2, float paramFloat) {
    return new Rotation(paramRotation1.() + PreAttackEvent.(paramRotation2.(), paramRotation1.()) / paramFloat, paramRotation1.() + PreAttackEvent.(paramRotation2.(), paramRotation1.()) / paramFloat);
  }
  
  public AutoFish() {
    super("Auto Fish", Module.Category.OTHER);
  }
  
  @SubscribeEvent
  public void (PacketReceivedEvent paramPacketReceivedEvent) {
    if (!())
      return; 
    if (paramPacketReceivedEvent. instanceof S12PacketEntityVelocity && mc.field_71439_g.field_71104_cf != null && ((S12PacketEntityVelocity)paramPacketReceivedEvent.).func_149412_c() == mc.field_71439_g.field_71104_cf.func_145782_y())
      if (((S12PacketEntityVelocity)paramPacketReceivedEvent.).func_149410_e() == 0) {
        Sneak.("Stable");
        this. = true;
        this. = false;
      } else if (((S12PacketEntityVelocity)paramPacketReceivedEvent.).func_149410_e() > -800 && ((S12PacketEntityVelocity)paramPacketReceivedEvent.).func_149410_e() < -500 && this.) {
        Sneak.("Caught");
        this..();
        this. = true;
      }  
  }
  
  public enum Stage {
    , , ;
    
    static {
       = new Stage[] { , ,  };
    }
  }
}
