package me.oringo.oringoclient.mixins.renderer;

import me.oringo.oringoclient.OringoClient;
import me.oringo.oringoclient.qolfeatures.module.impl.combat.KillAura;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value = {ItemRenderer.class}, priority = 1)
public abstract class AnimationsMixin {
  @Shadow
  private float field_78451_d;
  
  @Shadow
  private float field_78454_c;
  
  @Shadow
  private ItemStack field_78453_b;
  
  @Shadow
  @Final
  private Minecraft field_78455_a;
  
  @Overwrite
  public void func_78440_a(float paramFloat) {
    float f1 = 1.0F - this.field_78451_d + (this.field_78454_c - this.field_78451_d) * paramFloat;
    EntityPlayerSP entityPlayerSP = this.field_78455_a.field_71439_g;
    float f2 = entityPlayerSP.func_70678_g(paramFloat);
    float f3 = entityPlayerSP.field_70127_C + (entityPlayerSP.field_70125_A - entityPlayerSP.field_70127_C) * paramFloat;
    float f4 = entityPlayerSP.field_70126_B + (entityPlayerSP.field_70177_z - entityPlayerSP.field_70126_B) * paramFloat;
    func_178101_a(f3, f4);
    func_178109_a((AbstractClientPlayer)entityPlayerSP);
    func_178110_a(entityPlayerSP, paramFloat);
    GlStateManager.func_179091_B();
    GlStateManager.func_179094_E();
    if (this.field_78453_b != null) {
      boolean bool = (KillAura. != null && !KillAura..().equals("None")) ? true : false;
      if (this.field_78453_b.func_77973_b() instanceof net.minecraft.item.ItemMap) {
        func_178097_a((AbstractClientPlayer)entityPlayerSP, f3, f1, f2);
      } else if (entityPlayerSP.func_71052_bv() > 0 || bool) {
        EnumAction enumAction = this.field_78453_b.func_77975_n();
        if (bool)
          enumAction = EnumAction.BLOCK; 
        switch (null.[enumAction.ordinal()]) {
          case 1:
            func_178096_b(f1, 0.0F);
            break;
          case 2:
          case 3:
            func_178104_a((AbstractClientPlayer)entityPlayerSP, paramFloat);
            func_178096_b(f1, OringoClient..() ? f2 : 0.0F);
            break;
          case 4:
            if (OringoClient..()) {
              if (OringoClient...())
                f1 = 0.0F; 
              float f5 = MathHelper.func_76126_a(MathHelper.func_76129_c(f2) * 3.1415927F);
              float f6 = MathHelper.func_76126_a(f2 * f2 * 3.1415927F);
              switch (OringoClient...()) {
                case "1.7":
                  func_178096_b(f1 / 2.0F, f2);
                  func_178103_d();
                  break;
                case "Stab":
                  func_178096_b(f1, 0.0F);
                  GlStateManager.func_179109_b(-0.5F, 0.2F, 0.0F);
                  GlStateManager.func_179114_b(-10.0F, 0.0F, 1.0F, 0.0F);
                  GlStateManager.func_179114_b(-80.0F, 1.0F, 0.0F, 1.0F);
                  GlStateManager.func_179109_b(0.0F, f5, 0.0F);
                  GlStateManager.func_179114_b(f6 * -50.0F, 1.0F, 0.0F, 1.0F);
                  break;
                case "Reverse":
                  func_178096_b(f1, f2);
                  GlStateManager.func_179114_b(f6 * 20.0F, 0.0F, 1.0F, 0.0F);
                  GlStateManager.func_179114_b(f5 * 20.0F, 0.0F, 0.0F, 1.0F);
                  GlStateManager.func_179114_b(f5 * 80.0F, 1.0F, 0.0F, 0.0F);
                  func_178103_d();
                  break;
                case "Long hit":
                  func_178096_b(f1, 0.0F);
                  func_178103_d();
                  GlStateManager.func_179109_b(-0.05F, 0.6F, 0.3F);
                  GlStateManager.func_179114_b(-f5 * 70.0F / 2.0F, -8.0F, -0.0F, 9.0F);
                  GlStateManager.func_179114_b(-f5 * 70.0F, 1.5F, -0.4F, -0.0F);
                  break;
                case "Chill":
                  func_178096_b(f1 / 2.0F - 0.18F, 0.0F);
                  GL11.glRotatef(f5 * 60.0F / 2.0F, -f5 / 2.0F, -0.0F, -16.0F);
                  GL11.glRotatef(-f5 * 30.0F, 1.0F, f5 / 2.0F, -1.0F);
                  func_178103_d();
                  break;
                case "Push":
                  func_178096_b(f1, f2 - 1.0F);
                  func_178103_d();
                  break;
                case "Helicopter":
                  GlStateManager.func_179114_b((float)(System.currentTimeMillis() / 3L % 360L), 0.0F, 0.0F, -0.1F);
                  func_178096_b(f1 / 1.6F, 0.0F);
                  func_178103_d();
                  break;
                case "Exhibition":
                  func_178096_b(f1 / 2.0F, 0.0F);
                  GlStateManager.func_179109_b(0.0F, 0.3F, -0.0F);
                  GlStateManager.func_179114_b(-f5 * 31.0F, 1.0F, 0.0F, 2.0F);
                  GlStateManager.func_179114_b(-f5 * 33.0F, 1.5F, f5 / 1.1F, 0.0F);
                  func_178103_d();
                  break;
                case "Exhibition 2":
                  func_178096_b(f1 / 2.0F, 0.0F);
                  GlStateManager.func_179109_b(0.0F, 0.3F, -0.0F);
                  GlStateManager.func_179114_b(f5 * 31.0F, -1.0F, 0.0F, 2.0F);
                  GlStateManager.func_179114_b(f5 * 33.0F, 1.5F, -(f5 / 1.1F), 0.0F);
                  func_178103_d();
                  break;
                case "Z axis":
                  func_178096_b(f1 / 2.0F, 0.0F);
                  func_178103_d();
                  GlStateManager.func_179114_b(f5 * -40.0F, 0.0F, 0.0F, 1.0F);
                  break;
                case "Sigma":
                  func_178096_b(f1 / 2.0F, 0.0F);
                  GlStateManager.func_179114_b(-f6 * 55.0F / 2.0F, -8.0F, -0.0F, 9.0F);
                  GlStateManager.func_179114_b(-f6 * 45.0F, 1.0F, f6 / 2.0F, -0.0F);
                  func_178103_d();
                  GL11.glTranslated(1.2D, 0.3D, 0.5D);
                  GL11.glTranslatef(-1.0F, this.field_78455_a.field_71439_g.func_70093_af() ? -0.1F : -0.2F, 0.2F);
                  break;
                case "None":
                  if (!OringoClient..() || !OringoClient...())
                    func_178105_d(f2); 
                  func_178096_b(f1, f2);
                  break;
              } 
              func_178096_b(f1, 0.0F);
              func_178103_d();
              break;
            } 
            func_178096_b(f1, 0.0F);
            func_178103_d();
            break;
          case 5:
            func_178096_b(f1, OringoClient..() ? f2 : 0.0F);
            func_178098_a(paramFloat, (AbstractClientPlayer)entityPlayerSP);
            break;
        } 
      } else {
        if (!OringoClient..() || !OringoClient...())
          func_178105_d(f2); 
        func_178096_b(f1, f2);
      } 
      func_178099_a((EntityLivingBase)entityPlayerSP, this.field_78453_b, ItemCameraTransforms.TransformType.FIRST_PERSON);
    } else if (!entityPlayerSP.func_82150_aj()) {
      func_178095_a((AbstractClientPlayer)entityPlayerSP, f1, f2);
    } 
    GlStateManager.func_179121_F();
    GlStateManager.func_179101_C();
    RenderHelper.func_74518_a();
  }
  
  @Overwrite
  private void func_178096_b(float paramFloat1, float paramFloat2) {
    float f1 = (float)OringoClient...();
    float f2 = (float)OringoClient...();
    float f3 = (float)OringoClient...();
    float f4 = (float)OringoClient...();
    if (!OringoClient..()) {
      f1 = 1.0F;
      f2 = f3 = f4 = 0.0F;
    } 
    GlStateManager.func_179109_b(0.56F, -0.52F, -0.71999997F);
    GlStateManager.func_179109_b(f2, f3, f4);
    GlStateManager.func_179109_b(0.0F, paramFloat1 * -0.6F, 0.0F);
    GlStateManager.func_179114_b(45.0F, 0.0F, 1.0F, 0.0F);
    if (OringoClient..()) {
      GlStateManager.func_179114_b((float)OringoClient...(), 1.0F, 0.0F, 0.0F);
      GlStateManager.func_179114_b((float)OringoClient...(), 0.0F, 1.0F, 0.0F);
      GlStateManager.func_179114_b((float)OringoClient...(), 0.0F, 0.0F, 1.0F);
    } 
    float f5 = MathHelper.func_76126_a(paramFloat2 * paramFloat2 * 3.1415927F);
    float f6 = MathHelper.func_76126_a(MathHelper.func_76129_c(paramFloat2) * 3.1415927F);
    GlStateManager.func_179114_b(f5 * -20.0F, 0.0F, 1.0F, 0.0F);
    GlStateManager.func_179114_b(f6 * -20.0F, 0.0F, 0.0F, 1.0F);
    GlStateManager.func_179114_b(f6 * -80.0F, 1.0F, 0.0F, 0.0F);
    GlStateManager.func_179152_a(0.4F * f1, 0.4F * f1, 0.4F * f1);
  }
  
  @Overwrite
  private void func_178103_d() {
    GlStateManager.func_179109_b(-0.5F, 0.2F, 0.0F);
    GlStateManager.func_179114_b(30.0F, 0.0F, 1.0F, 0.0F);
    GlStateManager.func_179114_b(-80.0F, 1.0F, 0.0F, 0.0F);
    GlStateManager.func_179114_b(60.0F, 0.0F, 1.0F, 0.0F);
  }
  
  @Shadow
  protected abstract void func_178097_a(AbstractClientPlayer paramAbstractClientPlayer, float paramFloat1, float paramFloat2, float paramFloat3);
  
  @Shadow
  protected abstract void func_178104_a(AbstractClientPlayer paramAbstractClientPlayer, float paramFloat);
  
  @Shadow
  protected abstract void func_178095_a(AbstractClientPlayer paramAbstractClientPlayer, float paramFloat1, float paramFloat2);
  
  @Shadow
  public abstract void func_178099_a(EntityLivingBase paramEntityLivingBase, ItemStack paramItemStack, ItemCameraTransforms.TransformType paramTransformType);
  
  @Shadow
  protected abstract void func_178109_a(AbstractClientPlayer paramAbstractClientPlayer);
  
  @Shadow
  protected abstract void func_178110_a(EntityPlayerSP paramEntityPlayerSP, float paramFloat);
  
  @Shadow
  protected abstract void func_178098_a(float paramFloat, AbstractClientPlayer paramAbstractClientPlayer);
  
  @Shadow
  protected abstract void func_178105_d(float paramFloat);
  
  @Shadow
  protected abstract void func_178101_a(float paramFloat1, float paramFloat2);
}
