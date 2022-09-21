package me.oringo.oringoclient.mixins.entity;

import me.oringo.oringoclient.OringoClient;
import me.oringo.oringoclient.qolfeatures.module.impl.other.Delays;
import me.oringo.oringoclient.qolfeatures.module.impl.other.Disabler;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S0BPacketAnimation;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.BlockPos;
import net.minecraft.world.WorldServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({EntityLivingBase.class})
public abstract class EntityLivingBaseMixin extends EntityMixin {
  @Shadow
  private int field_70773_bE;
  
  @Shadow
  protected float field_70768_au;
  
  @Shadow
  protected int field_70708_bq;
  
  @Shadow
  public float field_70761_aq;
  
  @Shadow
  public float field_70702_br;
  
  @Shadow
  public float field_70747_aH;
  
  @Shadow
  public float field_70770_ap;
  
  @Shadow
  public float field_70721_aZ;
  
  @Shadow
  public float field_70759_as;
  
  @Shadow
  public float field_70769_ao;
  
  @Shadow
  public int field_110158_av;
  
  @Shadow
  protected float field_70764_aw;
  
  @Shadow
  protected double field_70709_bj;
  
  @Shadow
  public float field_70754_ba;
  
  @Shadow
  public boolean field_82175_bq;
  
  @Shadow
  public float field_70733_aJ;
  
  @Shadow
  public float field_70701_bs;
  
  @Shadow
  protected boolean field_70703_bu;
  
  @Shadow
  protected float field_110154_aX;
  
  @Shadow
  public float field_70722_aY;
  
  @Shadow
  protected float field_70741_aB;
  
  @Shadow
  protected int field_70716_bi;
  
  @Inject(method = {"onLivingUpdate"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/entity/EntityLivingBase;moveEntityWithHeading(FF)V")})
  private void h(CallbackInfo paramCallbackInfo) {
    if (Delays..() && this.field_70773_bE == 10)
      this.field_70773_bE = (int)Delays...(); 
  }
  
  @Inject(method = {"getArmSwingAnimationEnd"}, at = {@At("RETURN")}, cancellable = true)
  protected void getSwingEnd(CallbackInfoReturnable<Integer> paramCallbackInfoReturnable) {
    if (this == OringoClient.mc.field_71439_g) {
      int i = 6;
      if (OringoClient..())
        i = (int)(OringoClient...() * (Disabler.()).field_74278_d); 
      if (OringoClient..() && !OringoClient...())
        i = func_70644_a(Potion.field_76422_e) ? (i - 1 + func_70660_b(Potion.field_76422_e).func_76458_c()) : (func_70644_a(Potion.field_76419_f) ? (i + (1 + func_70660_b(Potion.field_76419_f).func_76458_c()) * 2) : i); 
      paramCallbackInfoReturnable.setReturnValue(Integer.valueOf(i));
    } 
  }
  
  @Overwrite
  public void func_71038_i() {
    ItemStack itemStack = func_70694_bm();
    if (itemStack != null && itemStack.func_77973_b() != null)
      if (itemStack.func_77973_b().onEntitySwing((EntityLivingBase)this, itemStack))
        return;  
    if (!this.field_82175_bq || this.field_110158_av >= (int)((this == OringoClient.mc.field_71439_g) ? (func_82166_i() / OringoClient...()) : (func_82166_i() / 2.0D)) || this.field_110158_av < 0) {
      this.field_110158_av = -1;
      this.field_82175_bq = true;
      if (this.field_70170_p instanceof WorldServer)
        ((WorldServer)this.field_70170_p).func_73039_n().func_151247_a((Entity)this, (Packet)new S0BPacketAnimation((Entity)this, 0)); 
    } 
  }
  
  public void setJumpTicks(int paramInt) {
    this.field_70773_bE = paramInt;
  }
  
  public int getJumpTicks() {
    return this.field_70773_bE;
  }
  
  @Shadow
  public abstract IAttributeInstance func_110148_a(IAttribute paramIAttribute);
  
  @Shadow
  protected abstract void func_70664_aZ();
  
  @Shadow
  protected abstract float func_110146_f(float paramFloat1, float paramFloat2);
  
  @Shadow
  public abstract float func_110143_aJ();
  
  @Shadow
  public abstract boolean func_70644_a(Potion paramPotion);
  
  @Shadow
  protected abstract void func_175133_bi();
  
  @Shadow
  public abstract float func_70689_ay();
  
  @Shadow
  protected abstract int func_82166_i();
  
  @Shadow
  public abstract boolean func_82165_m(int paramInt);
  
  @Shadow
  protected abstract void func_180433_a(double paramDouble, boolean paramBoolean, Block paramBlock, BlockPos paramBlockPos);
  
  @Shadow
  public abstract void func_130011_c(Entity paramEntity);
  
  @Shadow
  public abstract PotionEffect func_70660_b(Potion paramPotion);
  
  @Shadow
  protected abstract float func_175134_bD();
  
  @Shadow
  public abstract float func_70678_g(float paramFloat);
  
  @Shadow
  public abstract boolean func_70617_f_();
  
  @Shadow
  public abstract ItemStack func_70694_bm();
  
  @Shadow
  protected abstract void func_70088_a();
  
  @Shadow
  public abstract void func_70031_b(boolean paramBoolean);
}
