package me.oringo.oringoclient.mixins;

import me.oringo.oringoclient.OringoClient;
import me.oringo.oringoclient.mixins.entity.PlayerSPAccessor;
import me.oringo.oringoclient.qolfeatures.Updater;
import me.oringo.oringoclient.utils.RotationUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({ModelBiped.class})
public class MixinModelBiped {
  @Shadow
  public ModelRenderer field_78115_e;
  
  @Shadow
  public ModelRenderer field_178723_h;
  
  @Shadow
  public ModelRenderer field_78116_c;
  
  @Shadow
  public int field_78120_m;
  
  @Inject(method = {"setRotationAngles"}, at = {@At(value = "FIELD", target = "Lnet/minecraft/client/model/ModelBiped;swingProgress:F")})
  private void setRotationAngles(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, Entity paramEntity, CallbackInfo paramCallbackInfo) {
    if (!Updater.().())
      return; 
    if (paramEntity != null && paramEntity.equals((Minecraft.func_71410_x()).field_71439_g))
      this.field_78116_c.field_78795_f = (RotationUtils. + (((PlayerSPAccessor)paramEntity).getLastReportedPitch() - RotationUtils.) * (((MinecraftAccessor)OringoClient.mc).getTimer()).field_74281_c) / 57.295776F; 
  }
}
