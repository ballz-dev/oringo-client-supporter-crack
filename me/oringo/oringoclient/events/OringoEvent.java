package me.oringo.oringoclient.events;

import me.oringo.oringoclient.qolfeatures.module.impl.movement.Sneak;
import me.oringo.oringoclient.qolfeatures.module.impl.player.AntiObby;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.Event;

public class OringoEvent extends Event {
  public static Minecraft mc = Minecraft.func_71410_x();
  
  public void cancel() {
    setCanceled(true);
  }
  
  public boolean post() {
    try {
      return MinecraftForge.EVENT_BUS.post(this);
    } catch (Exception exception) {
      Sneak.(String.valueOf((new StringBuilder()).append("Caught an ").append(exception.getClass().getSimpleName()).append(" at ").append(getClass().getName()).append(". Please report this!")));
      exception.printStackTrace();
      return false;
    } 
  }
  
  public static void (String paramString) {
    try {
      (Minecraft.func_71410_x()).field_71439_g.func_145747_a((IChatComponent)new ChatComponentText(paramString));
    } catch (Exception exception) {}
  }
  
  public static float (ItemStack paramItemStack) {
    float f = 0.0F;
    if (paramItemStack != null && (paramItemStack.func_77973_b() instanceof ItemTool || paramItemStack.func_77973_b() instanceof ItemSword)) {
      if (paramItemStack.func_77973_b() instanceof ItemSword) {
        f += 4.0F;
        f++;
      } else if (paramItemStack.func_77973_b() instanceof net.minecraft.item.ItemAxe) {
        f += 3.0F;
      } else if (paramItemStack.func_77973_b() instanceof net.minecraft.item.ItemPickaxe) {
        f += 2.0F;
      } else if (paramItemStack.func_77973_b() instanceof net.minecraft.item.ItemSpade) {
        f++;
      } 
      f += (paramItemStack.func_77973_b() instanceof ItemTool) ? ((ItemTool)paramItemStack.func_77973_b()).func_150913_i().func_78000_c() : ((ItemSword)paramItemStack.func_77973_b()).func_150931_i();
      f = (float)(f + 1.25D * EnchantmentHelper.func_77506_a(Enchantment.field_180314_l.field_77352_x, paramItemStack));
      f = (float)(f + EnchantmentHelper.func_77506_a(Enchantment.field_180314_l.field_77352_x, paramItemStack) * 0.5D);
    } 
    return f;
  }
  
  public static boolean (boolean paramBoolean) {
    BlockPos blockPos = (new BlockPos(AntiObby.mc.field_71439_g.field_70165_t, AntiObby.mc.field_71439_g.field_70163_u, AntiObby.mc.field_71439_g.field_70161_v)).func_177984_a();
    Block block = AntiObby.mc.field_71441_e.func_180495_p(blockPos).func_177230_c();
    if (paramBoolean && block instanceof net.minecraft.block.BlockFalling)
      return true; 
    for (Block block1 : AntiObby.) {
      if (block1 == block)
        return true; 
    } 
    return false;
  }
}
