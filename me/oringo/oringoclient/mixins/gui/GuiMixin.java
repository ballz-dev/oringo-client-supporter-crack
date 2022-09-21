package me.oringo.oringoclient.mixins.gui;

import net.minecraft.client.gui.Gui;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin({Gui.class})
public abstract class GuiMixin {
  @Shadow
  protected abstract void func_73733_a(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6);
  
  @Shadow
  public static void func_73734_a(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5) {}
  
  @Shadow
  public abstract void func_73729_b(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6);
}
