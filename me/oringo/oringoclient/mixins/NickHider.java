package me.oringo.oringoclient.mixins;

import me.oringo.oringoclient.OringoClient;
import net.minecraft.client.gui.FontRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin({FontRenderer.class})
public abstract class NickHider {
  @ModifyVariable(method = {"renderStringAtPos"}, at = @At("HEAD"), argsOnly = true)
  private String text(String paramString) {
    return OringoClient..() ? paramString.replaceAll(OringoClient.mc.func_110432_I().func_111285_a(), OringoClient...()) : paramString;
  }
  
  @ModifyVariable(method = {"getStringWidth"}, at = @At("HEAD"), argsOnly = true)
  private String text1(String paramString) {
    return OringoClient..() ? paramString.replaceAll(OringoClient.mc.func_110432_I().func_111285_a(), OringoClient...()) : paramString;
  }
}
