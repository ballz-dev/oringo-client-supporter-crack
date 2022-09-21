package me.oringo.oringoclient.mixins.renderer;

import me.oringo.oringoclient.OringoClient;
import me.oringo.oringoclient.events.impl.KeyPressEvent;
import me.oringo.oringoclient.qolfeatures.module.impl.render.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.util.Vec3;
import org.lwjgl.opengl.Display;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({EntityRenderer.class})
public class EntityRendererMixin {
  @Shadow
  @Final
  private DynamicTexture field_78513_d;
  
  @Shadow
  private boolean field_78536_aa;
  
  @Shadow
  private float field_78490_B;
  
  @Shadow
  @Final
  public ItemRenderer field_78516_c;
  
  @Shadow
  private float field_78491_C;
  
  @Shadow
  @Final
  private int[] field_78504_Q;
  
  @Shadow
  private Minecraft field_78531_r;
  
  @Redirect(method = {"setupFog"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/EntityLivingBase;isPotionActive(Lnet/minecraft/potion/Potion;)Z"))
  public boolean removeBlindness(EntityLivingBase paramEntityLivingBase, Potion paramPotion) {
    return false;
  }
  
  @Inject(method = {"hurtCameraEffect"}, at = {@At("HEAD")}, cancellable = true)
  public void hurtCam(float paramFloat, CallbackInfo paramCallbackInfo) {
    if (Camera..() && OringoClient..())
      paramCallbackInfo.cancel(); 
  }
  
  @Redirect(method = {"orientCamera"}, at = @At(value = "FIELD", target = "Lnet/minecraft/client/renderer/EntityRenderer;thirdPersonDistanceTemp:F"))
  public float thirdPersonDistanceTemp(EntityRenderer paramEntityRenderer) {
    return (OringoClient..() && !Camera..()) ? (float)Camera..() : this.field_78491_C;
  }
  
  @Redirect(method = {"orientCamera"}, at = @At(value = "FIELD", target = "Lnet/minecraft/client/renderer/EntityRenderer;thirdPersonDistance:F"))
  public float thirdPersonDistance(EntityRenderer paramEntityRenderer) {
    return (OringoClient..() && !Camera..()) ? (float)Camera..() : this.field_78490_B;
  }
  
  @Redirect(method = {"orientCamera"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/util/Vec3;distanceTo(Lnet/minecraft/util/Vec3;)D"))
  public double onCamera(Vec3 paramVec31, Vec3 paramVec32) {
    return (OringoClient..() && Camera..()) ? Camera..() : paramVec31.func_72438_d(paramVec32);
  }
  
  @Inject(method = {"updateRenderer"}, at = {@At("RETURN")})
  public void onUpdate(CallbackInfo paramCallbackInfo) {
    if (this.field_78531_r.field_71474_y.field_74320_O > 0) {
      if (OringoClient..() && Camera..())
        this.field_78490_B = (float)KeyPressEvent.(this.field_78490_B + Camera..(), Camera..(), 0.0D); 
    } else {
      this.field_78490_B = this.field_78491_C = (!OringoClient..() || !Camera..()) ? 4.0F : (float)Camera..();
    } 
  }
  
  @Redirect(method = {"getMouseOver"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/PlayerControllerMP;getBlockReachDistance()F"))
  private float getBlockReachDistance(PlayerControllerMP paramPlayerControllerMP) {
    OringoClient..();
    return OringoClient..() ? (float)OringoClient...() : this.field_78531_r.field_71442_b.func_78757_d();
  }
  
  @Redirect(method = {"getMouseOver"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/util/Vec3;distanceTo(Lnet/minecraft/util/Vec3;)D", ordinal = 2))
  private double distanceTo(Vec3 paramVec31, Vec3 paramVec32) {
    return (OringoClient..() && paramVec31.func_72438_d(paramVec32) <= OringoClient..) ? 0.0D : paramVec31.func_72438_d(paramVec32);
  }
  
  @Inject(method = {"updateCameraAndRender"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GlStateManager;alphaFunc(IF)V")})
  public void onRenderGameOverlay(float paramFloat, long paramLong, CallbackInfo paramCallbackInfo) {
    boolean bool = (this.field_78531_r.func_175606_aa() instanceof EntityLivingBase && ((EntityLivingBase)this.field_78531_r.func_175606_aa()).func_70608_bn()) ? true : false;
    if (!bool && this.field_78531_r.field_71474_y.field_74320_O == 0 && 
      this.field_78531_r.field_71439_g.func_70027_ad())
      ((ItemRendererInvoker)this.field_78516_c).renderFireOverlay(paramFloat); 
  }
  
  @Inject(method = {"updateCameraAndRender"}, at = {@At("HEAD")}, cancellable = true)
  public void noRender(float paramFloat, long paramLong, CallbackInfo paramCallbackInfo) {
    if (OringoClient..() && !Display.isVisible()) {
      try {
        Thread.sleep(10L);
      } catch (InterruptedException interruptedException) {}
      paramCallbackInfo.cancel();
    } 
  }
  
  @Redirect(method = {"updateLightmap"}, at = @At(value = "FIELD", target = "Lnet/minecraft/client/settings/GameSettings;gammaSetting:F"))
  public float gamma(GameSettings paramGameSettings) {
    return (OringoClient..() || OringoClient..()) ? 10000.0F : paramGameSettings.field_74333_Y;
  }
}
