package me.oringo.oringoclient.events.impl;

import me.oringo.oringoclient.events.OringoEvent;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;

public class StepEvent extends OringoEvent {
  public double ;
  
  public double getHeight() {
    return this.;
  }
  
  public void setHeight(double paramDouble) {
    this. = paramDouble;
  }
  
  public StepEvent(double paramDouble) {
    this. = paramDouble;
  }
  
  public static int (ItemStack paramItemStack) {
    return EnchantmentHelper.func_77506_a(Enchantment.field_77345_t.field_77352_x, paramItemStack) + EnchantmentHelper.func_77506_a(Enchantment.field_77343_v.field_77352_x, paramItemStack) * 2;
  }
  
  public static class Post extends StepEvent {
    static {
    
    }
    
    public Post(double param1Double) {
      super(param1Double);
    }
  }
  
  public static class Pre extends StepEvent {
    static {
    
    }
    
    public Pre(double param1Double) {
      super(param1Double);
    }
  }
}
