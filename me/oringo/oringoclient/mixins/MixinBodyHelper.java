package me.oringo.oringoclient.mixins;

import me.oringo.oringoclient.OringoClient;
import me.oringo.oringoclient.mixins.entity.PlayerSPAccessor;
import me.oringo.oringoclient.qolfeatures.Updater;
import net.minecraft.entity.EntityBodyHelper;
import net.minecraft.entity.EntityLivingBase;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin({EntityBodyHelper.class})
public abstract class MixinBodyHelper {
  @Shadow
  private float field_75667_c;
  
  @Shadow
  private EntityLivingBase field_75668_a;
  
  @Shadow
  private int field_75666_b;
  
  @Shadow
  protected abstract float func_75665_a(float paramFloat1, float paramFloat2, float paramFloat3);
  
  @Overwrite
  public void func_75664_a() {
    double d1 = this.field_75668_a.field_70165_t - this.field_75668_a.field_70169_q;
    double d2 = this.field_75668_a.field_70161_v - this.field_75668_a.field_70166_s;
    if (d1 * d1 + d2 * d2 > 2.500000277905201E-7D) {
      this.field_75668_a.field_70761_aq = (this.field_75668_a == OringoClient.mc.field_71439_g && Updater.().()) ? ((PlayerSPAccessor)OringoClient.mc.field_71439_g).getLastReportedYaw() : this.field_75668_a.field_70177_z;
      this.field_75668_a.field_70759_as = func_75665_a(this.field_75668_a.field_70761_aq, this.field_75668_a.field_70759_as, 75.0F);
      this.field_75667_c = this.field_75668_a.field_70759_as;
      this.field_75666_b = 0;
    } else {
      float f = 75.0F;
      if (Math.abs(this.field_75668_a.field_70759_as - this.field_75667_c) > 15.0F) {
        this.field_75666_b = 0;
        this.field_75667_c = this.field_75668_a.field_70759_as;
      } else {
        this.field_75666_b++;
        byte b = 10;
        if (this.field_75666_b > 10)
          f = Math.max(1.0F - (this.field_75666_b - 10) / 10.0F, 0.0F) * 75.0F; 
      } 
      this.field_75668_a.field_70761_aq = func_75665_a(this.field_75668_a.field_70759_as, this.field_75668_a.field_70761_aq, f);
    } 
  }
}
