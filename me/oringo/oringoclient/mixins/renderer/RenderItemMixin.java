package me.oringo.oringoclient.mixins.renderer;

import java.awt.Color;
import me.oringo.oringoclient.OringoClient;
import me.oringo.oringoclient.qolfeatures.module.impl.render.EnchantGlint;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin({RenderItem.class})
public class RenderItemMixin {
  @Redirect(method = {"renderEffect"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;getSystemTime()J"))
  public long getSystemTime() {
    return OringoClient..() ? (long)(Minecraft.func_71386_F() * OringoClient...() / 100.0D) : Minecraft.func_71386_F();
  }
  
  @ModifyConstant(method = {"renderEffect"}, constant = {@Constant(intValue = -8372020)})
  public int getColor(int paramInt) {
    return OringoClient..() ? getColorSetting(paramInt).getRGB() : paramInt;
  }
  
  private Color getColorSetting(int paramInt) {
    EnchantGlint enchantGlint = OringoClient.;
    switch (enchantGlint..()) {
      case "RGBA":
        return new Color((int)enchantGlint..(), (int)enchantGlint..(), (int)enchantGlint..(), (int)enchantGlint..());
      case "Rainbow":
        return Color.getHSBColor((float)(System.currentTimeMillis() % enchantGlint..() * 1000.0D / enchantGlint..() * 1000.0D), 0.65F, 1.0F);
      case "Theme":
        return OringoClient..();
    } 
    return new Color(paramInt);
  }
}
