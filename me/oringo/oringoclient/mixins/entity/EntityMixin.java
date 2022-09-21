package me.oringo.oringoclient.mixins.entity;

import java.util.Random;
import java.util.UUID;
import me.oringo.oringoclient.events.impl.DeathEvent;
import net.minecraft.block.Block;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.Event;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({Entity.class})
public abstract class EntityMixin {
  @Shadow
  public boolean field_70123_F;
  
  @Shadow
  public float field_70144_Y;
  
  @Shadow
  public boolean field_70160_al;
  
  @Shadow
  public double field_70163_u;
  
  @Shadow
  public Entity field_70154_o;
  
  @Shadow
  protected boolean field_70134_J;
  
  @Shadow
  public boolean field_70122_E;
  
  @Shadow
  public float field_70177_z;
  
  @Shadow
  public double field_70161_v;
  
  @Shadow
  public double field_70181_x;
  
  @Shadow
  public float field_70143_R;
  
  @Shadow
  public World field_70170_p;
  
  @Shadow
  public double field_70169_q;
  
  @Shadow
  public double field_70165_t;
  
  @Shadow
  public double field_70166_s;
  
  @Shadow
  public float field_70140_Q;
  
  @Shadow
  private int field_70151_c;
  
  @Shadow
  public float field_82151_R;
  
  @Shadow
  public int field_71088_bW;
  
  @Shadow
  public double field_70159_w;
  
  @Shadow
  public boolean field_70145_X;
  
  @Shadow
  public int field_70174_ab;
  
  @Shadow
  public float field_70138_W;
  
  @Shadow
  public double field_70179_y;
  
  @Shadow
  public float field_70125_A;
  
  @Shadow
  protected Random field_70146_Z;
  
  @Shadow
  public double field_70142_S;
  
  @Shadow
  public float field_70130_N;
  
  @Shadow
  public boolean field_70132_H;
  
  @Shadow
  public int field_70173_aa;
  
  @Shadow
  protected UUID field_96093_i;
  
  @Shadow
  protected boolean field_71087_bX;
  
  @Shadow
  public boolean field_70124_G;
  
  @Shadow
  private int field_70150_b;
  
  @Shadow
  public void func_70091_d(double paramDouble1, double paramDouble2, double paramDouble3) {}
  
  @Shadow
  private void func_174829_m() {}
  
  public void doResetPositionToBB() {
    func_174829_m();
  }
  
  public void setNextStepDistance(int paramInt) {
    this.field_70150_b = paramInt;
  }
  
  public int getNextStepDistance() {
    return this.field_70150_b;
  }
  
  public int getFire() {
    return this.field_70151_c;
  }
  
  public void SetFire(int paramInt) {
    this.field_70151_c = paramInt;
  }
  
  public void plusPlusFire() {
    this.field_70151_c++;
  }
  
  @Inject(method = {"setDead"}, at = {@At("HEAD")}, cancellable = true)
  private void setDead(CallbackInfo paramCallbackInfo) {
    MinecraftForge.EVENT_BUS.post((Event)new DeathEvent((Entity)this));
  }
  
  @Shadow
  public abstract boolean func_180799_ab();
  
  @Shadow
  public abstract void func_70015_d(int paramInt);
  
  @Shadow
  public abstract void func_70066_B();
  
  @Shadow
  public abstract boolean func_70115_ae();
  
  @Shadow
  public abstract void func_85029_a(CrashReportCategory paramCrashReportCategory);
  
  @Shadow
  public abstract boolean equals(Object paramObject);
  
  @Shadow
  protected abstract void func_70088_a();
  
  @Shadow
  public abstract AxisAlignedBB func_174813_aQ();
  
  @Shadow
  public abstract void func_70060_a(float paramFloat1, float paramFloat2, float paramFloat3);
  
  @Shadow
  public abstract boolean func_70090_H();
  
  @Shadow
  protected abstract void func_70081_e(int paramInt);
  
  @Shadow
  public abstract boolean func_82150_aj();
  
  @Shadow
  public abstract float func_70111_Y();
  
  @Shadow
  public abstract boolean func_70026_G();
  
  @Shadow
  public abstract boolean func_70051_ag();
  
  @Shadow
  protected abstract void func_180429_a(BlockPos paramBlockPos, Block paramBlock);
  
  @Shadow
  public abstract boolean func_70094_T();
  
  @Shadow
  public abstract boolean func_70038_c(double paramDouble1, double paramDouble2, double paramDouble3);
  
  @Shadow
  protected abstract void func_145775_I();
  
  @Shadow
  public abstract void func_70095_a(boolean paramBoolean);
  
  @Shadow
  public abstract UUID func_110124_au();
  
  @Shadow
  public abstract void func_174826_a(AxisAlignedBB paramAxisAlignedBB);
  
  @Shadow
  protected abstract boolean func_70083_f(int paramInt);
}
