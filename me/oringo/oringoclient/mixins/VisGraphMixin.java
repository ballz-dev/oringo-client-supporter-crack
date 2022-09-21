package me.oringo.oringoclient.mixins;

import me.oringo.oringoclient.OringoClient;
import net.minecraft.client.renderer.chunk.SetVisibility;
import net.minecraft.client.renderer.chunk.VisGraph;
import net.minecraft.util.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({VisGraph.class})
public class VisGraphMixin {
  @Inject(method = {"computeVisibility"}, at = {@At("HEAD")}, cancellable = true)
  public void onCompute(CallbackInfoReturnable<SetVisibility> paramCallbackInfoReturnable) {
    if (OringoClient..() || OringoClient..()) {
      SetVisibility setVisibility = new SetVisibility();
      setVisibility.func_178618_a(true);
      paramCallbackInfoReturnable.setReturnValue(setVisibility);
    } 
  }
  
  @Inject(method = {"func_178606_a"}, at = {@At("HEAD")}, cancellable = true)
  public void onFunc(BlockPos paramBlockPos, CallbackInfo paramCallbackInfo) {
    if (OringoClient..() || OringoClient..())
      paramCallbackInfo.cancel(); 
  }
}
