package me.oringo.oringoclient.mixins.gui;

import net.minecraft.client.gui.GuiTextField;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin({GuiTextField.class})
public interface GuiTextFieldAcessor {
  @Accessor
  boolean getIsEnabled();
}
