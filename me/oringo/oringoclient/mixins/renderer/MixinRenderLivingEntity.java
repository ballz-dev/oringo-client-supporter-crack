package me.oringo.oringoclient.mixins.renderer;

import me.oringo.oringoclient.OringoClient;
import me.oringo.oringoclient.events.impl.RenderLayersEvent;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({RendererLivingEntity.class})
public abstract class MixinRenderLivingEntity extends RenderMixin {
  @Shadow
  @Final
  private static Logger field_147923_a;
  
  @Shadow
  protected ModelBase field_77045_g;
  
  @Shadow
  protected boolean field_177098_i;
  
  @Shadow
  protected abstract void func_180565_e();
  
  @Shadow
  public abstract void func_76986_a(Entity paramEntity, double paramDouble1, double paramDouble2, double paramDouble3, float paramFloat1, float paramFloat2);
  
  @Shadow
  protected abstract <T extends EntityLivingBase> boolean func_177090_c(T paramT, float paramFloat);
  
  @Shadow
  protected abstract <T extends EntityLivingBase> void func_77039_a(T paramT, double paramDouble1, double paramDouble2, double paramDouble3);
  
  @Shadow
  protected abstract float func_77034_a(float paramFloat1, float paramFloat2, float paramFloat3);
  
  @Shadow
  protected abstract <T extends EntityLivingBase> void func_177093_a(T paramT, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7);
  
  @Shadow
  protected abstract <T extends EntityLivingBase> void func_77036_a(T paramT, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6);
  
  @Inject(method = {"renderLayers"}, at = {@At("RETURN")}, cancellable = true)
  protected void renderLayersPost(EntityLivingBase paramEntityLivingBase, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7, CallbackInfo paramCallbackInfo) {
    if ((new RenderLayersEvent(paramEntityLivingBase, paramFloat1, paramFloat2, paramFloat4, paramFloat5, paramFloat6, paramFloat7, this.field_77045_g)).post())
      paramCallbackInfo.cancel(); 
  }
  
  @Inject(method = {"preRenderCallback"}, at = {@At("HEAD")}, cancellable = true)
  private <T extends EntityLivingBase> void onPreRenderCallback(T paramT, float paramFloat, CallbackInfo paramCallbackInfo) {
    if (OringoClient..() && OringoClient...() && (!(paramT instanceof net.minecraft.entity.item.EntityArmorStand) || OringoClient...()))
      GlStateManager.func_179139_a(OringoClient...(), OringoClient...(), OringoClient...()); 
  }
  
  @Shadow
  protected abstract <T extends EntityLivingBase> void func_77041_b(T paramT, float paramFloat);
  
  @Shadow
  protected abstract <T extends EntityLivingBase> boolean func_177088_c(T paramT);
  
  @Shadow
  protected abstract <T extends EntityLivingBase> float func_77044_a(T paramT, float paramFloat);
  
  @Shadow
  protected abstract <T extends EntityLivingBase> void func_77043_a(T paramT, float paramFloat1, float paramFloat2, float paramFloat3);
  
  @Shadow
  protected abstract void func_177091_f();
  
  @Shadow
  protected abstract <T extends EntityLivingBase> float func_77040_d(T paramT, float paramFloat);
}
