package me.oringo.oringoclient.mixins.entity;

import me.oringo.oringoclient.OringoClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.entity.projectile.EntityFishHook;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatBase;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.FoodStats;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin({EntityPlayer.class})
public abstract class PlayerMixin extends EntityLivingBaseMixin {
  @Shadow
  public PlayerCapabilities field_71075_bZ;
  
  @Shadow
  public float field_71079_bU;
  
  @Shadow
  private int field_71072_f;
  
  @Shadow
  protected FoodStats field_71100_bB;
  
  private boolean wasSprinting;
  
  @Shadow
  public float field_71089_bV;
  
  @Shadow
  protected float field_71102_ce;
  
  @Shadow
  public int field_71068_ca;
  
  @Shadow
  public EntityFishHook field_71104_cf;
  
  @Shadow
  protected int field_71101_bC;
  
  @Shadow
  public InventoryPlayer field_71071_by;
  
  @Shadow
  public float eyeHeight;
  
  @Shadow
  public int field_71067_cb;
  
  @Shadow
  public float field_71106_cc;
  
  @Shadow
  public float field_71082_cx;
  
  @Shadow
  protected float field_71108_cd;
  
  @Shadow
  protected void func_70626_be() {}
  
  @Shadow
  public boolean func_70094_T() {
    return false;
  }
  
  @Shadow
  public void func_70612_e(float paramFloat1, float paramFloat2) {}
  
  @Shadow
  public void func_70636_d() {}
  
  public float func_70111_Y() {
    return OringoClient..() ? (float)OringoClient...() : 0.1F;
  }
  
  @Redirect(method = {"getBreakSpeed"}, at = @At(value = "FIELD", target = "Lnet/minecraft/entity/player/EntityPlayer;onGround:Z"))
  private boolean onGround(EntityPlayer paramEntityPlayer) {
    if (paramEntityPlayer == OringoClient.mc.field_71439_g && OringoClient..() && OringoClient...() && !this.field_70122_E)
      return true; 
    return this.field_70122_E;
  }
  
  @Shadow
  public abstract void func_71059_n(Entity paramEntity);
  
  @Shadow
  public abstract ItemStack func_71045_bC();
  
  @Shadow
  public abstract boolean func_70097_a(DamageSource paramDamageSource, float paramFloat);
  
  @Shadow
  public abstract void func_71029_a(StatBase paramStatBase);
  
  @Shadow
  public abstract void func_70071_h_();
  
  @Shadow
  public abstract void func_71028_bD();
  
  @Shadow
  protected abstract String func_145776_H();
  
  @Shadow
  public abstract ItemStack func_71011_bu();
  
  @Shadow
  public abstract FoodStats func_71024_bL();
  
  @Shadow
  public abstract void func_71020_j(float paramFloat);
  
  @Shadow
  public abstract void setSpawnChunk(BlockPos paramBlockPos, boolean paramBoolean, int paramInt);
  
  @Shadow
  public abstract boolean func_71039_bw();
  
  @Shadow
  public abstract float func_70689_ay();
  
  @Shadow
  public abstract void func_70062_b(int paramInt, ItemStack paramItemStack);
  
  @Shadow
  protected abstract boolean func_70041_e_();
  
  @Shadow
  public abstract EntityPlayer.EnumStatus func_180469_a(BlockPos paramBlockPos);
  
  @Shadow
  public abstract ItemStack func_70694_bm();
  
  @Shadow
  public abstract void func_71000_j(double paramDouble1, double paramDouble2, double paramDouble3);
  
  @Shadow
  protected abstract void func_70088_a();
  
  @Shadow
  public abstract void func_71016_p();
}
