package org.spongepowered.asm.mixin.transformer.ext;

public interface IHotSwap {
  void registerTargetClass(String paramString, byte[] paramArrayOfbyte);
  
  void registerMixinClass(String paramString);
}
