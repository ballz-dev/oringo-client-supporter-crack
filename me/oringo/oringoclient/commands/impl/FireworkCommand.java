package me.oringo.oringoclient.commands.impl;

import me.oringo.oringoclient.commands.Command;
import me.oringo.oringoclient.qolfeatures.module.impl.player.NoRotate;
import me.oringo.oringoclient.ui.notifications.Notifications;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagByte;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C10PacketCreativeInventoryAction;

public class FireworkCommand extends Command {
  public static void (String paramString, int paramInt, Notifications.NotificationType paramNotificationType) {
    paramInt += 500;
    Notifications..add(new Notifications.Notification(paramString, paramInt, paramNotificationType));
  }
  
  public String () {
    return "Gives you a crash firework. You need to have creative";
  }
  
  public void (String[] paramArrayOfString) throws Exception {
    if (paramArrayOfString.length == 2) {
      ItemStack itemStack = new ItemStack(Items.field_151152_bP);
      itemStack.field_77994_a = 64;
      itemStack.func_151001_c("crash");
      NBTTagList nBTTagList1 = new NBTTagList();
      NBTTagCompound nBTTagCompound1 = itemStack.serializeNBT();
      NBTTagCompound nBTTagCompound2 = nBTTagCompound1.func_74775_l("tag").func_74775_l("Fireworks");
      NBTTagList nBTTagList2 = new NBTTagList();
      NBTTagCompound nBTTagCompound3 = new NBTTagCompound();
      nBTTagCompound3.func_74782_a("Type", (NBTBase)new NBTTagByte((byte)1));
      nBTTagCompound3.func_74782_a("Flicker", (NBTBase)new NBTTagByte((byte)1));
      nBTTagCompound3.func_74782_a("Trail", (NBTBase)new NBTTagByte((byte)3));
      int[] arrayOfInt = new int[Integer.parseInt(paramArrayOfString[1])];
      byte b;
      for (b = 0; b < Integer.parseInt(paramArrayOfString[1]); b++)
        arrayOfInt[b] = 261799 + b; 
      nBTTagCompound3.func_74783_a("Colors", arrayOfInt);
      arrayOfInt = new int[100];
      for (b = 0; b < 100; b++)
        arrayOfInt[b] = 11250603 + b; 
      nBTTagCompound3.func_74783_a("FadeColors", arrayOfInt);
      for (b = 0; b < Integer.parseInt(paramArrayOfString[0]); b++)
        nBTTagList2.func_74742_a((NBTBase)nBTTagCompound3); 
      nBTTagCompound2.func_74782_a("Explosions", (NBTBase)nBTTagList2);
      nBTTagCompound1.func_74775_l("tag").func_74782_a("Fireworks", (NBTBase)nBTTagCompound2);
      NoRotate.("Oringo Client", String.valueOf((new StringBuilder()).append("NBT Size: ").append(nBTTagCompound1.toString().length())), 2000);
      itemStack.deserializeNBT(nBTTagCompound1);
      mc.field_71439_g.field_71174_a.func_147297_a((Packet)new C10PacketCreativeInventoryAction(36, itemStack));
    } else {
      NoRotate.("Oringo Client", "/firework explosions colors", 1000);
    } 
  }
  
  static {
  
  }
  
  public FireworkCommand() {
    super("firework", new String[0]);
  }
}
