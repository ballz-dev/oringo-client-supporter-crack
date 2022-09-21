package me.oringo.oringoclient.commands.impl;

import java.util.regex.Pattern;
import me.oringo.oringoclient.commands.Command;
import me.oringo.oringoclient.events.impl.KeyPressEvent;
import me.oringo.oringoclient.events.impl.MotionUpdateEvent;
import me.oringo.oringoclient.events.impl.MoveEvent;
import me.oringo.oringoclient.events.impl.PacketReceivedEvent;
import me.oringo.oringoclient.qolfeatures.BackgroundProcess;
import me.oringo.oringoclient.qolfeatures.module.impl.other.Blink;
import me.oringo.oringoclient.qolfeatures.module.impl.player.AutoHeal;
import me.oringo.oringoclient.qolfeatures.module.impl.skyblock.AutoBeams;
import me.oringo.oringoclient.qolfeatures.module.impl.skyblock.AutoS1;
import me.oringo.oringoclient.qolfeatures.module.impl.skyblock.AutoTerminals;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.BooleanSetting;
import me.oringo.oringoclient.ui.notifications.Notifications;
import me.oringo.oringoclient.utils.MilliTimer;
import me.oringo.oringoclient.utils.PlayerUtils;
import me.oringo.oringoclient.utils.RotationUtils;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3;
import net.minecraft.util.Vec3i;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class BloodSkipCommand extends Command {
  public static boolean ;
  
  public static int ;
  
  public static Pattern  = Pattern.compile("§7 §cThe Catac§combs §7\\((M|F)(.*?)\\)");
  
  public static double ;
  
  public static BlockPos ;
  
  public static MilliTimer  = new MilliTimer();
  
  public static Vec3 ;
  
  public static int ;
  
  public static boolean ;
  
  @SubscribeEvent(receiveCanceled = true)
  public void (PacketReceivedEvent paramPacketReceivedEvent) {
    if (paramPacketReceivedEvent. instanceof net.minecraft.network.play.server.S08PacketPlayerPosLook) {
      if ()
         = null; 
       = true;
      BooleanSetting.();
    } 
  }
  
  public String () {
    return "Teleports you to the boss room";
  }
  
  public static int (int paramInt1, int paramInt2) {
    ContainerChest containerChest = (ContainerChest)AutoTerminals.mc.field_71439_g.field_71070_bA;
    for (int i : AutoTerminals.) {
      int j = paramInt1 + i;
      if (j != paramInt2 && Blink.(j, containerChest)) {
        ItemStack itemStack = containerChest.func_75139_a(j).func_75211_c();
        if (itemStack.func_77973_b() instanceof ItemBlock && ((ItemBlock)itemStack.func_77973_b()).field_150939_a instanceof net.minecraft.block.BlockStainedGlassPane) {
          EnumDyeColor enumDyeColor = AutoS1.(itemStack);
          if (enumDyeColor == EnumDyeColor.WHITE || enumDyeColor == EnumDyeColor.LIME || enumDyeColor == EnumDyeColor.RED)
            return j; 
        } 
      } 
    } 
    return -1;
  }
  
  public static int (int paramInt) {
    return paramInt % 100 + 1;
  }
  
  public void (String[] paramArrayOfString) throws Exception {
    if (paramArrayOfString.length == 1) {
       = Integer.parseInt(paramArrayOfString[0]);
    } else {
       = 0;
    } 
     = 0;
     = mc.field_71439_g.func_174791_d();
     =  = false;
    .();
    int i = BackgroundProcess.("Ender Pearl");
    if (i == -1) {
      FireworkCommand.("You need an Ender Pearl!", 4000, Notifications.NotificationType.);
      return;
    } 
    if (!mc.field_71441_e.func_180495_p(new BlockPos(mc.field_71439_g.func_174791_d())).func_177230_c().equals(Blocks.field_150438_bZ)) {
      FireworkCommand.("You need to stand on a hopper", 4000, Notifications.NotificationType.);
      return;
    } 
    switch (AutoBeams.()) {
      case 7:
         = new BlockPos(90, 255, 50);
         = 247.0D;
        break;
      case 6:
         = new BlockPos(-15, 111, 62);
         = 104.0D;
        break;
      case 5:
         = new BlockPos(5, 113, 42);
         = 105.0D;
        break;
      case 4:
         = new BlockPos(14, 113, -23);
         = 106.0D;
        break;
      case 3:
         = new BlockPos(8, 119, 2);
         = 113.0D;
        break;
      default:
        FireworkCommand.("Invalid floor!", 4000, Notifications.NotificationType.);
        return;
    } 
    mc.func_147114_u().func_147297_a((Packet)new C03PacketPlayer.C06PacketPlayerPosLook(mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u, mc.field_71439_g.field_70161_v, mc.field_71439_g.field_70177_z, 90.0F, mc.field_71439_g.field_70122_E));
    mc.func_147114_u().func_147297_a((Packet)new C09PacketHeldItemChange(i));
    mc.func_147114_u().func_147297_a((Packet)new C08PacketPlayerBlockPlacement(mc.field_71439_g.field_71071_by.func_70301_a(i)));
    mc.func_147114_u().func_147297_a((Packet)new C09PacketHeldItemChange(mc.field_71439_g.field_71071_by.field_70461_c));
  }
  
  public static void () {
    AutoHeal.mc.func_147114_u().func_147298_b().func_179290_a((Packet)new C0APacketAnimation());
  }
  
  @SubscribeEvent
  public void (MotionUpdateEvent.Pre paramPre) {
    if ( != null && ) {
      double d = Math.max(.func_177956_o(), 165);
      PlayerUtils.(5.0F);
      if ( > 100) {
         = null;
        BooleanSetting.();
        return;
      } 
      if (++ > 1) {
        if (mc.field_71439_g.field_70163_u != d && !) {
          double d1 = d - mc.field_71439_g.field_70163_u;
          mc.field_71439_g.func_70107_b((int)mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u + KeyPressEvent.(d1, 9.0D, -9.0D), (int)mc.field_71439_g.field_70161_v);
           = (mc.field_71439_g.field_70163_u == d);
        } else if (mc.field_71439_g.func_70092_e(.func_177958_n(), mc.field_71439_g.field_70163_u, .func_177952_p()) != 0.0D) {
          double d1 = Math.toRadians(RotationUtils.(new Vec3((Vec3i))).());
          double d2 = Math.min(9.0D, mc.field_71439_g.func_70011_f(.func_177958_n(), mc.field_71439_g.field_70163_u, .func_177952_p()));
          mc.field_71439_g.func_70107_b(mc.field_71439_g.field_70165_t + -Math.sin(d1) * d2, mc.field_71439_g.field_70163_u, mc.field_71439_g.field_70161_v + Math.cos(d1) * d2);
        } else if (mc.field_71439_g.field_70163_u != .func_177956_o()) {
          double d1 = .func_177956_o() - mc.field_71439_g.field_70163_u;
          mc.field_71439_g.func_70107_b((int)mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u + KeyPressEvent.(d1, 9.0D, -9.0D), (int)mc.field_71439_g.field_70161_v);
        } else {
          BooleanSetting.();
          if (.(500L)) {
            mc.field_71439_g.func_70107_b(mc.field_71439_g.field_70165_t, , mc.field_71439_g.field_70161_v);
            FireworkCommand.("Thank you for flying with Hypixel Airlines!", 5000, Notifications.NotificationType.);
             = null;
          } 
        } 
      } else {
        mc.field_71439_g.func_70107_b(.field_72450_a, .field_72448_b, .field_72449_c);
      } 
      paramPre.setPosition(mc.field_71439_g.func_174791_d()).setOnGround(true);
    } 
  }
  
  public BloodSkipCommand() {
    super("bloodskip", new String[0]);
  }
  
  @SubscribeEvent
  public void (MoveEvent paramMoveEvent) {
    if ( != null)
      paramMoveEvent.stop(); 
  }
}
