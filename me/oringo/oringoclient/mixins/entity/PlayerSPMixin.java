package me.oringo.oringoclient.mixins.entity;

import java.util.List;
import me.oringo.oringoclient.OringoClient;
import me.oringo.oringoclient.events.impl.MotionUpdateEvent;
import me.oringo.oringoclient.events.impl.MoveEvent;
import me.oringo.oringoclient.events.impl.MoveFlyingEvent;
import me.oringo.oringoclient.events.impl.MoveHeadingEvent;
import me.oringo.oringoclient.events.impl.PlayerUpdateEvent;
import me.oringo.oringoclient.events.impl.StepEvent;
import me.oringo.oringoclient.qolfeatures.Updater;
import me.oringo.oringoclient.qolfeatures.module.impl.combat.KillAura;
import me.oringo.oringoclient.qolfeatures.module.impl.movement.SafeWalk;
import me.oringo.oringoclient.qolfeatures.module.impl.movement.Scaffold;
import me.oringo.oringoclient.qolfeatures.module.impl.movement.Sneak;
import me.oringo.oringoclient.qolfeatures.module.impl.other.HClip;
import me.oringo.oringoclient.qolfeatures.module.impl.player.ChestStealer;
import me.oringo.oringoclient.qolfeatures.module.impl.render.Giants;
import me.oringo.oringoclient.qolfeatures.module.impl.render.HidePlayers;
import me.oringo.oringoclient.qolfeatures.module.impl.render.NoRender;
import me.oringo.oringoclient.qolfeatures.module.impl.render.TargetHUD;
import me.oringo.oringoclient.qolfeatures.module.impl.skyblock.AutoMask;
import me.oringo.oringoclient.utils.PlayerUtils;
import me.oringo.oringoclient.utils.RotationUtils;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IEntityMultiPart;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.boss.EntityDragonPart;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.potion.Potion;
import net.minecraft.stats.AchievementList;
import net.minecraft.stats.StatBase;
import net.minecraft.stats.StatList;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovementInput;
import net.minecraft.util.ReportedException;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.ForgeHooks;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = {EntityPlayerSP.class}, priority = 1)
public abstract class PlayerSPMixin extends AbstractClientPlayerMixin {
  @Shadow
  private float field_175165_bM;
  
  @Shadow
  public float field_71163_h;
  
  @Shadow
  protected Minecraft field_71159_c;
  
  @Shadow
  private double field_175166_bJ;
  
  @Shadow
  private double field_175172_bI;
  
  @Shadow
  public float field_71086_bY;
  
  @Shadow
  public MovementInput field_71158_b;
  
  @Shadow
  private float field_175164_bL;
  
  @Shadow
  public int field_71157_e;
  
  @Shadow
  private double field_175167_bK;
  
  @Shadow
  @Final
  public NetHandlerPlayClient field_71174_a;
  
  @Shadow
  private float field_110321_bQ;
  
  @Shadow
  public float field_71154_f;
  
  @Shadow
  public float field_71080_cy;
  
  @Shadow
  public float field_71164_i;
  
  @Shadow
  private int field_110320_a;
  
  @Shadow
  private int field_175168_bP;
  
  @Shadow
  public float field_71155_g;
  
  @Shadow
  private boolean field_175171_bO;
  
  @Shadow
  private boolean field_175170_bN;
  
  @Shadow
  protected int field_71156_d;
  
  @Inject(method = {"sendChatMessage"}, at = {@At("HEAD")}, cancellable = true)
  public void onSenChatMessage(String paramString, CallbackInfo paramCallbackInfo) {
    if (TargetHUD.(paramString))
      paramCallbackInfo.cancel(); 
  }
  
  protected float func_110146_f(float paramFloat1, float paramFloat2) {
    if (this.field_70733_aJ > 0.0F && Updater.().())
      paramFloat1 = this.field_175164_bL; 
    float f1 = MathHelper.func_76142_g(paramFloat1 - this.field_70761_aq);
    this.field_70761_aq += f1 * 0.3F;
    float f2 = MathHelper.func_76142_g((Updater.().() ? this.field_175164_bL : this.field_70177_z) - this.field_70761_aq);
    boolean bool = (f2 < -90.0F || f2 >= 90.0F) ? true : false;
    if (f2 < -75.0F)
      f2 = -75.0F; 
    if (f2 >= 75.0F)
      f2 = 75.0F; 
    this.field_70761_aq = (Updater.().() ? this.field_175164_bL : this.field_70177_z) - f2;
    if (f2 * f2 > 2500.0F)
      this.field_70761_aq += f2 * 0.2F; 
    if (bool)
      paramFloat2 *= -1.0F; 
    return paramFloat2;
  }
  
  @Overwrite
  public void func_175161_p() {
    MotionUpdateEvent.Pre pre = new MotionUpdateEvent.Pre(this.field_70165_t, (func_174813_aQ()).field_72338_b, this.field_70161_v, this.field_70177_z, this.field_70125_A, this.field_70122_E, func_70051_ag(), func_70093_af());
    if (pre.post())
      return; 
    boolean bool1 = ((MotionUpdateEvent)pre).;
    if (bool1 != this.field_175171_bO) {
      if (bool1) {
        this.field_71174_a.func_147297_a((Packet)new C0BPacketEntityAction((Entity)this, C0BPacketEntityAction.Action.START_SPRINTING));
      } else {
        this.field_71174_a.func_147297_a((Packet)new C0BPacketEntityAction((Entity)this, C0BPacketEntityAction.Action.STOP_SPRINTING));
      } 
      this.field_175171_bO = bool1;
    } 
    boolean bool2 = ((MotionUpdateEvent)pre).;
    if (bool2 != this.field_175170_bN) {
      if (bool2) {
        this.field_71174_a.func_147297_a((Packet)new C0BPacketEntityAction((Entity)this, C0BPacketEntityAction.Action.START_SNEAKING));
      } else {
        this.field_71174_a.func_147297_a((Packet)new C0BPacketEntityAction((Entity)this, C0BPacketEntityAction.Action.STOP_SNEAKING));
      } 
      this.field_175170_bN = bool2;
    } 
    if (func_175160_A()) {
      double d1 = ((MotionUpdateEvent)pre). - this.field_175172_bI;
      double d2 = ((MotionUpdateEvent)pre). - this.field_175166_bJ;
      double d3 = ((MotionUpdateEvent)pre). - this.field_175167_bK;
      double d4 = (((MotionUpdateEvent)pre). - this.field_175164_bL);
      double d5 = (((MotionUpdateEvent)pre). - this.field_175165_bM);
      boolean bool3 = (d1 * d1 + d2 * d2 + d3 * d3 > 9.0E-4D || this.field_175168_bP >= 20) ? true : false;
      boolean bool4 = (d4 != 0.0D || d5 != 0.0D) ? true : false;
      if (this.field_70154_o == null) {
        if (bool3 && bool4) {
          this.field_71174_a.func_147297_a((Packet)new C03PacketPlayer.C06PacketPlayerPosLook(((MotionUpdateEvent)pre)., ((MotionUpdateEvent)pre)., ((MotionUpdateEvent)pre)., ((MotionUpdateEvent)pre)., ((MotionUpdateEvent)pre)., ((MotionUpdateEvent)pre).));
        } else if (bool3) {
          this.field_71174_a.func_147297_a((Packet)new C03PacketPlayer.C04PacketPlayerPosition(((MotionUpdateEvent)pre)., ((MotionUpdateEvent)pre)., ((MotionUpdateEvent)pre)., ((MotionUpdateEvent)pre).));
        } else if (bool4) {
          this.field_71174_a.func_147297_a((Packet)new C03PacketPlayer.C05PacketPlayerLook(((MotionUpdateEvent)pre)., ((MotionUpdateEvent)pre)., ((MotionUpdateEvent)pre).));
        } else {
          this.field_71174_a.func_147297_a((Packet)new C03PacketPlayer(((MotionUpdateEvent)pre).));
        } 
      } else {
        this.field_71174_a.func_147297_a((Packet)new C03PacketPlayer.C06PacketPlayerPosLook(this.field_70159_w, -999.0D, this.field_70179_y, ((MotionUpdateEvent)pre)., ((MotionUpdateEvent)pre)., ((MotionUpdateEvent)pre).));
        bool3 = false;
      } 
      this.field_175168_bP++;
      if (bool3) {
        this.field_175172_bI = ((MotionUpdateEvent)pre).;
        this.field_175166_bJ = ((MotionUpdateEvent)pre).;
        this.field_175167_bK = ((MotionUpdateEvent)pre).;
        this.field_175168_bP = 0;
      } 
      PlayerUtils. = ((MotionUpdateEvent)pre).;
      RotationUtils. = this.field_175165_bM;
      if (bool4) {
        this.field_175164_bL = ((MotionUpdateEvent)pre).;
        this.field_175165_bM = ((MotionUpdateEvent)pre).;
      } 
    } 
    (new MotionUpdateEvent.Post((MotionUpdateEvent)pre)).post();
  }
  
  public void func_70664_aZ() {
    this.field_70181_x = func_175134_bD();
    if (func_82165_m(Potion.field_76430_j.field_76415_H))
      this.field_70181_x += ((func_70660_b(Potion.field_76430_j).func_76458_c() + 1) * 0.1F); 
    if (func_70051_ag() && HidePlayers.()) {
      float f = ((OringoClient..() && OringoClient...()) ? ChestStealer.() : ((OringoClient..() && KillAura. != null && KillAura..()) ? HClip.((Entity)KillAura.).() : this.field_70177_z)) * 0.017453292F;
      this.field_70159_w -= (MathHelper.func_76126_a(f) * 0.2F);
      this.field_70179_y += (MathHelper.func_76134_b(f) * 0.2F);
    } 
    this.field_70160_al = true;
    ForgeHooks.onLivingJump((EntityLivingBase)this);
    func_71029_a(StatList.field_75953_u);
    if (func_70051_ag()) {
      func_71020_j(0.8F);
    } else {
      func_71020_j(0.2F);
    } 
  }
  
  public void func_70060_a(float paramFloat1, float paramFloat2, float paramFloat3) {
    MoveFlyingEvent moveFlyingEvent = new MoveFlyingEvent(paramFloat2, paramFloat1, paramFloat3, this.field_70177_z);
    if (moveFlyingEvent.post())
      return; 
    paramFloat1 = moveFlyingEvent.getStrafe();
    paramFloat2 = moveFlyingEvent.getForward();
    paramFloat3 = moveFlyingEvent.getFriction();
    float f = paramFloat1 * paramFloat1 + paramFloat2 * paramFloat2;
    if (f >= 1.0E-4F) {
      f = MathHelper.func_76129_c(f);
      if (f < 1.0F)
        f = 1.0F; 
      f = paramFloat3 / f;
      paramFloat1 *= f;
      paramFloat2 *= f;
      float f1 = moveFlyingEvent.getYaw();
      float f2 = MathHelper.func_76126_a(f1 * 3.1415927F / 180.0F);
      float f3 = MathHelper.func_76134_b(f1 * 3.1415927F / 180.0F);
      this.field_70159_w += (paramFloat1 * f3 - paramFloat2 * f2);
      this.field_70179_y += (paramFloat2 * f3 + paramFloat1 * f2);
    } 
  }
  
  public void superMoveEntityWithHeading(float paramFloat1, float paramFloat2, boolean paramBoolean, float paramFloat3) {
    if (func_70613_aW())
      if (!func_70090_H() || ((Entity)this instanceof EntityPlayer && this.field_71075_bZ.field_75100_b)) {
        if (!func_180799_ab() || ((Entity)this instanceof EntityPlayer && this.field_71075_bZ.field_75100_b)) {
          float f3, f1 = 0.91F;
          if (paramBoolean)
            f1 = (this.field_70170_p.func_180495_p(new BlockPos(MathHelper.func_76128_c(this.field_70165_t), MathHelper.func_76128_c((func_174813_aQ()).field_72338_b) - 1, MathHelper.func_76128_c(this.field_70161_v))).func_177230_c()).field_149765_K * 0.91F; 
          float f2 = 0.16277136F / f1 * f1 * f1;
          if (paramBoolean) {
            f3 = func_70689_ay() * f2;
          } else {
            f3 = this.field_70747_aH;
          } 
          func_70060_a(paramFloat1, paramFloat2, f3);
          f1 = 0.91F;
          if (paramBoolean)
            f1 = (this.field_70170_p.func_180495_p(new BlockPos(MathHelper.func_76128_c(this.field_70165_t), MathHelper.func_76128_c((func_174813_aQ()).field_72338_b) - 1, MathHelper.func_76128_c(this.field_70161_v))).func_177230_c()).field_149765_K * paramFloat3; 
          if (func_70617_f_()) {
            float f4 = 0.15F;
            this.field_70159_w = MathHelper.func_151237_a(this.field_70159_w, -f4, f4);
            this.field_70179_y = MathHelper.func_151237_a(this.field_70179_y, -f4, f4);
            this.field_70143_R = 0.0F;
            if (this.field_70181_x < -0.15D)
              this.field_70181_x = -0.15D; 
            boolean bool = func_70093_af();
            if (bool && this.field_70181_x < 0.0D)
              this.field_70181_x = 0.0D; 
          } 
          func_70091_d(this.field_70159_w, this.field_70181_x, this.field_70179_y);
          if (this.field_70123_F && func_70617_f_())
            this.field_70181_x = 0.2D; 
          if (this.field_70170_p.field_72995_K && (!this.field_70170_p.func_175667_e(new BlockPos((int)this.field_70165_t, 0, (int)this.field_70161_v)) || !this.field_70170_p.func_175726_f(new BlockPos((int)this.field_70165_t, 0, (int)this.field_70161_v)).func_177410_o())) {
            if (this.field_70163_u > 0.0D) {
              this.field_70181_x = -0.1D;
            } else {
              this.field_70181_x = 0.0D;
            } 
          } else {
            this.field_70181_x -= 0.08D;
          } 
          this.field_70181_x *= 0.9800000190734863D;
          this.field_70159_w *= f1;
          this.field_70179_y *= f1;
        } else {
          double d = this.field_70163_u;
          func_70060_a(paramFloat1, paramFloat2, 0.02F);
          func_70091_d(this.field_70159_w, this.field_70181_x, this.field_70179_y);
          this.field_70159_w *= 0.5D;
          this.field_70181_x *= 0.5D;
          this.field_70179_y *= 0.5D;
          this.field_70181_x -= 0.02D;
          if (this.field_70123_F && func_70038_c(this.field_70159_w, this.field_70181_x + 0.6000000238418579D - this.field_70163_u + d, this.field_70179_y))
            this.field_70181_x = 0.30000001192092896D; 
        } 
      } else {
        double d = this.field_70163_u;
        float f1 = 0.8F;
        float f2 = 0.02F;
        float f3 = EnchantmentHelper.func_180318_b((Entity)this);
        if (f3 > 3.0F)
          f3 = 3.0F; 
        if (!this.field_70122_E)
          f3 *= 0.5F; 
        if (f3 > 0.0F) {
          f1 += (0.54600006F - f1) * f3 / 3.0F;
          f2 += (func_70689_ay() * 1.0F - f2) * f3 / 3.0F;
        } 
        func_70060_a(paramFloat1, paramFloat2, f2);
        func_70091_d(this.field_70159_w, this.field_70181_x, this.field_70179_y);
        this.field_70159_w *= f1;
        this.field_70181_x *= 0.800000011920929D;
        this.field_70179_y *= f1;
        this.field_70181_x -= 0.02D;
        if (this.field_70123_F && func_70038_c(this.field_70159_w, this.field_70181_x + 0.6000000238418579D - this.field_70163_u + d, this.field_70179_y))
          this.field_70181_x = 0.30000001192092896D; 
      }  
    this.field_70722_aY = this.field_70721_aZ;
    double d1 = this.field_70165_t - this.field_70169_q;
    double d2 = this.field_70161_v - this.field_70166_s;
    float f = MathHelper.func_76133_a(d1 * d1 + d2 * d2) * 4.0F;
    if (f > 1.0F)
      f = 1.0F; 
    this.field_70721_aZ += (f - this.field_70721_aZ) * 0.4F;
    this.field_70754_ba += this.field_70721_aZ;
  }
  
  @Inject(method = {"pushOutOfBlocks"}, at = {@At("HEAD")}, cancellable = true)
  public void pushOutOfBlocks1(double paramDouble1, double paramDouble2, double paramDouble3, CallbackInfoReturnable<Boolean> paramCallbackInfoReturnable) {
    paramCallbackInfoReturnable.setReturnValue(Boolean.valueOf(false));
  }
  
  public boolean func_70094_T() {
    return false;
  }
  
  @Overwrite
  public void func_70636_d() {
    if (this.field_71157_e > 0) {
      this.field_71157_e--;
      if (this.field_71157_e == 0)
        func_70031_b(false); 
    } 
    if (this.field_71156_d > 0)
      this.field_71156_d--; 
    this.field_71080_cy = this.field_71086_bY;
    if (this.field_71087_bX) {
      if (this.field_71159_c.field_71462_r != null && !this.field_71159_c.field_71462_r.func_73868_f())
        this.field_71159_c.func_147108_a(null); 
      if (this.field_71086_bY == 0.0F)
        this.field_71159_c.func_147118_V().func_147682_a((ISound)PositionedSoundRecord.func_147674_a(new ResourceLocation("portal.trigger"), this.field_70146_Z.nextFloat() * 0.4F + 0.8F)); 
      this.field_71086_bY += 0.0125F;
      if (this.field_71086_bY >= 1.0F)
        this.field_71086_bY = 1.0F; 
      this.field_71087_bX = false;
    } else if (func_70644_a(Potion.field_76431_k) && func_70660_b(Potion.field_76431_k).func_76459_b() > 60) {
      this.field_71086_bY += 0.006666667F;
      if (this.field_71086_bY > 1.0F)
        this.field_71086_bY = 1.0F; 
    } else {
      if (this.field_71086_bY > 0.0F)
        this.field_71086_bY -= 0.05F; 
      if (this.field_71086_bY < 0.0F)
        this.field_71086_bY = 0.0F; 
    } 
    if (this.field_71088_bW > 0)
      this.field_71088_bW--; 
    boolean bool1 = this.field_71158_b.field_78901_c;
    boolean bool2 = this.field_71158_b.field_78899_d;
    float f = 0.8F;
    boolean bool3 = (this.field_71158_b.field_78900_b >= f) ? true : false;
    this.field_71158_b.func_78898_a();
    if (func_71039_bw() && !func_70115_ae())
      if (OringoClient..()) {
        EnumAction enumAction = func_70694_bm().func_77973_b().func_77661_b(func_70694_bm());
        if (enumAction == EnumAction.BLOCK) {
          this.field_71158_b.field_78900_b = (float)(this.field_71158_b.field_78900_b * OringoClient...());
          this.field_71158_b.field_78902_a = (float)(this.field_71158_b.field_78902_a * OringoClient...());
        } else if (enumAction == EnumAction.BOW) {
          this.field_71158_b.field_78900_b = (float)(this.field_71158_b.field_78900_b * OringoClient...());
          this.field_71158_b.field_78902_a = (float)(this.field_71158_b.field_78902_a * OringoClient...());
        } else if (enumAction != EnumAction.NONE) {
          this.field_71158_b.field_78900_b = (float)(this.field_71158_b.field_78900_b * OringoClient...());
          this.field_71158_b.field_78902_a = (float)(this.field_71158_b.field_78902_a * OringoClient...());
        } 
      } else {
        this.field_71158_b.field_78902_a *= 0.2F;
        this.field_71158_b.field_78900_b *= 0.2F;
        this.field_71156_d = 0;
      }  
    func_145771_j(this.field_70165_t - this.field_70130_N * 0.35D, (func_174813_aQ()).field_72338_b + 0.5D, this.field_70161_v + this.field_70130_N * 0.35D);
    func_145771_j(this.field_70165_t - this.field_70130_N * 0.35D, (func_174813_aQ()).field_72338_b + 0.5D, this.field_70161_v - this.field_70130_N * 0.35D);
    func_145771_j(this.field_70165_t + this.field_70130_N * 0.35D, (func_174813_aQ()).field_72338_b + 0.5D, this.field_70161_v - this.field_70130_N * 0.35D);
    func_145771_j(this.field_70165_t + this.field_70130_N * 0.35D, (func_174813_aQ()).field_72338_b + 0.5D, this.field_70161_v + this.field_70130_N * 0.35D);
    boolean bool4 = (func_71024_bL().func_75116_a() > 6.0F || this.field_71075_bZ.field_75101_c) ? true : false;
    if (OringoClient..() && Scaffold..("None")) {
      func_70031_b(false);
    } else if ((bool4 && OringoClient...() && OringoClient..()) || OringoClient..()) {
      func_70031_b((OringoClient..() || (HidePlayers.() && (!func_70093_af() || ((Sneak)Giants.(Sneak.class)).()) && (!func_71039_bw() || (OringoClient..() && OringoClient...())) && (func_71024_bL().func_75116_a() > 6.0F || this.field_71075_bZ.field_75101_c))));
    } else {
      if (this.field_70122_E && !bool2 && !bool3 && this.field_71158_b.field_78900_b >= f && !func_70051_ag() && bool4 && (!func_71039_bw() || (OringoClient..() && OringoClient...())) && !func_70644_a(Potion.field_76440_q))
        if (this.field_71156_d <= 0 && !this.field_71159_c.field_71474_y.field_151444_V.func_151470_d()) {
          this.field_71156_d = 7;
        } else {
          func_70031_b(true);
        }  
      if (!func_70051_ag() && this.field_71158_b.field_78900_b >= f && bool4 && (!func_71039_bw() || (OringoClient..() && OringoClient...())) && !func_70644_a(Potion.field_76440_q) && this.field_71159_c.field_71474_y.field_151444_V.func_151470_d())
        func_70031_b(true); 
      if (func_70051_ag() && (this.field_71158_b.field_78900_b < f || this.field_70123_F || !bool4))
        func_70031_b(false); 
    } 
    if (this.field_71075_bZ.field_75101_c)
      if (this.field_71159_c.field_71442_b.func_178887_k()) {
        if (!this.field_71075_bZ.field_75100_b) {
          this.field_71075_bZ.field_75100_b = true;
          func_71016_p();
        } 
      } else if (!bool1 && this.field_71158_b.field_78901_c) {
        if (this.field_71101_bC == 0) {
          this.field_71101_bC = 7;
        } else {
          this.field_71075_bZ.field_75100_b = !this.field_71075_bZ.field_75100_b;
          func_71016_p();
          this.field_71101_bC = 0;
        } 
      }  
    if (this.field_71075_bZ.field_75100_b && func_175160_A()) {
      if (this.field_71158_b.field_78899_d)
        this.field_70181_x -= (this.field_71075_bZ.func_75093_a() * 3.0F); 
      if (this.field_71158_b.field_78901_c)
        this.field_70181_x += (this.field_71075_bZ.func_75093_a() * 3.0F); 
    } 
    if (func_110317_t()) {
      if (this.field_110320_a < 0) {
        this.field_110320_a++;
        if (this.field_110320_a == 0)
          this.field_110321_bQ = 0.0F; 
      } 
      if (bool1 && !this.field_71158_b.field_78901_c) {
        this.field_110320_a = -10;
        func_110318_g();
      } else if (!bool1 && this.field_71158_b.field_78901_c) {
        this.field_110320_a = 0;
        this.field_110321_bQ = 0.0F;
      } else if (bool1) {
        this.field_110320_a++;
        if (this.field_110320_a < 10) {
          this.field_110321_bQ = this.field_110320_a * 0.1F;
        } else {
          this.field_110321_bQ = 0.8F + 2.0F / (this.field_110320_a - 9) * 0.1F;
        } 
      } 
    } else {
      this.field_110321_bQ = 0.0F;
    } 
    if (OringoClient..())
      this.field_70145_X = true; 
    super.func_70636_d();
    if (this.field_70122_E && this.field_71075_bZ.field_75100_b && !this.field_71159_c.field_71442_b.func_178887_k()) {
      this.field_71075_bZ.field_75100_b = false;
      func_71016_p();
    } 
  }
  
  @Inject(method = {"onUpdate"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/entity/EntityPlayerSP;isRiding()Z")}, cancellable = true)
  private void onUpdate(CallbackInfo paramCallbackInfo) {
    if ((new PlayerUpdateEvent()).post())
      paramCallbackInfo.cancel(); 
  }
  
  public void func_70612_e(float paramFloat1, float paramFloat2) {
    MoveHeadingEvent moveHeadingEvent = new MoveHeadingEvent(this.field_70122_E);
    if (moveHeadingEvent.post())
      return; 
    double d1 = this.field_70165_t;
    double d2 = this.field_70163_u;
    double d3 = this.field_70161_v;
    if (this.field_71075_bZ.field_75100_b && this.field_70154_o == null) {
      double d = this.field_70181_x;
      float f = this.field_70747_aH;
      this.field_70747_aH = this.field_71075_bZ.func_75093_a() * (func_70051_ag() ? 2 : true);
      super.func_70612_e(paramFloat1, paramFloat2);
      this.field_70181_x = d * 0.6D;
      this.field_70747_aH = f;
    } else {
      superMoveEntityWithHeading(paramFloat1, paramFloat2, moveHeadingEvent.isOnGround(), moveHeadingEvent.getFriction2Multi());
    } 
    func_71000_j(this.field_70165_t - d1, this.field_70163_u - d2, this.field_70161_v - d3);
  }
  
  public void func_70091_d(double paramDouble1, double paramDouble2, double paramDouble3) {
    MoveEvent moveEvent = new MoveEvent(paramDouble1, paramDouble2, paramDouble3);
    if (moveEvent.post())
      return; 
    paramDouble1 = moveEvent.getX();
    paramDouble2 = moveEvent.getY();
    paramDouble3 = moveEvent.getZ();
    if (this.field_70145_X) {
      func_174826_a(func_174813_aQ().func_72317_d(paramDouble1, paramDouble2, paramDouble3));
      doResetPositionToBB();
    } else {
      this.field_70170_p.field_72984_F.func_76320_a("move");
      double d1 = this.field_70165_t;
      double d2 = this.field_70163_u;
      double d3 = this.field_70161_v;
      if (this.field_70134_J) {
        this.field_70134_J = false;
        paramDouble1 *= 0.25D;
        paramDouble2 *= 0.05000000074505806D;
        paramDouble3 *= 0.25D;
        this.field_70159_w = 0.0D;
        this.field_70181_x = 0.0D;
        this.field_70179_y = 0.0D;
      } 
      double d4 = paramDouble1;
      double d5 = paramDouble2;
      double d6 = paramDouble3;
      boolean bool1 = (((this.field_70122_E && func_70093_af()) || (NoRender.(1.0D) && OringoClient..() && Scaffold..()) || (this.field_70122_E && ((SafeWalk)Giants.(SafeWalk.class)).() && SafeWalk..("Safe walk"))) && (Entity)this instanceof EntityPlayer) ? true : false;
      if (bool1) {
        double d;
        for (d = 0.05D; paramDouble1 != 0.0D && this.field_70170_p.func_72945_a((Entity)this, func_174813_aQ().func_72317_d(paramDouble1, -1.0D, 0.0D)).isEmpty(); d4 = paramDouble1) {
          if (paramDouble1 < d && paramDouble1 >= -d) {
            paramDouble1 = 0.0D;
          } else if (paramDouble1 > 0.0D) {
            paramDouble1 -= d;
          } else {
            paramDouble1 += d;
          } 
        } 
        for (; paramDouble3 != 0.0D && this.field_70170_p.func_72945_a((Entity)this, func_174813_aQ().func_72317_d(0.0D, -1.0D, paramDouble3)).isEmpty(); d6 = paramDouble3) {
          if (paramDouble3 < d && paramDouble3 >= -d) {
            paramDouble3 = 0.0D;
          } else if (paramDouble3 > 0.0D) {
            paramDouble3 -= d;
          } else {
            paramDouble3 += d;
          } 
        } 
        for (; paramDouble1 != 0.0D && paramDouble3 != 0.0D && this.field_70170_p.func_72945_a((Entity)this, func_174813_aQ().func_72317_d(paramDouble1, -1.0D, paramDouble3)).isEmpty(); d6 = paramDouble3) {
          if (paramDouble1 < d && paramDouble1 >= -d) {
            paramDouble1 = 0.0D;
          } else if (paramDouble1 > 0.0D) {
            paramDouble1 -= d;
          } else {
            paramDouble1 += d;
          } 
          d4 = paramDouble1;
          if (paramDouble3 < d && paramDouble3 >= -d) {
            paramDouble3 = 0.0D;
          } else if (paramDouble3 > 0.0D) {
            paramDouble3 -= d;
          } else {
            paramDouble3 += d;
          } 
        } 
      } 
      List list = this.field_70170_p.func_72945_a((Entity)this, func_174813_aQ().func_72321_a(paramDouble1, paramDouble2, paramDouble3));
      AxisAlignedBB axisAlignedBB = func_174813_aQ();
      for (AxisAlignedBB axisAlignedBB1 : list)
        paramDouble2 = axisAlignedBB1.func_72323_b(func_174813_aQ(), paramDouble2); 
      func_174826_a(func_174813_aQ().func_72317_d(0.0D, paramDouble2, 0.0D));
      boolean bool2 = (this.field_70122_E || (d5 != paramDouble2 && d5 < 0.0D)) ? true : false;
      for (AxisAlignedBB axisAlignedBB1 : list)
        paramDouble1 = axisAlignedBB1.func_72316_a(func_174813_aQ(), paramDouble1); 
      func_174826_a(func_174813_aQ().func_72317_d(paramDouble1, 0.0D, 0.0D));
      for (AxisAlignedBB axisAlignedBB1 : list)
        paramDouble3 = axisAlignedBB1.func_72322_c(func_174813_aQ(), paramDouble3); 
      func_174826_a(func_174813_aQ().func_72317_d(0.0D, 0.0D, paramDouble3));
      if (this.field_70138_W > 0.0F && bool2 && (d4 != paramDouble1 || d6 != paramDouble3)) {
        double d7 = paramDouble1;
        double d8 = paramDouble2;
        double d9 = paramDouble3;
        AxisAlignedBB axisAlignedBB1 = func_174813_aQ();
        func_174826_a(axisAlignedBB);
        StepEvent.Pre pre = new StepEvent.Pre(this.field_70138_W);
        pre.post();
        paramDouble2 = pre.getHeight();
        List list1 = this.field_70170_p.func_72945_a((Entity)this, func_174813_aQ().func_72321_a(d4, paramDouble2, d6));
        AxisAlignedBB axisAlignedBB2 = func_174813_aQ();
        AxisAlignedBB axisAlignedBB3 = axisAlignedBB2.func_72321_a(d4, 0.0D, d6);
        double d10 = paramDouble2;
        for (AxisAlignedBB axisAlignedBB5 : list1)
          d10 = axisAlignedBB5.func_72323_b(axisAlignedBB3, d10); 
        axisAlignedBB2 = axisAlignedBB2.func_72317_d(0.0D, d10, 0.0D);
        double d11 = d4;
        for (AxisAlignedBB axisAlignedBB5 : list1)
          d11 = axisAlignedBB5.func_72316_a(axisAlignedBB2, d11); 
        axisAlignedBB2 = axisAlignedBB2.func_72317_d(d11, 0.0D, 0.0D);
        double d12 = d6;
        for (AxisAlignedBB axisAlignedBB5 : list1)
          d12 = axisAlignedBB5.func_72322_c(axisAlignedBB2, d12); 
        axisAlignedBB2 = axisAlignedBB2.func_72317_d(0.0D, 0.0D, d12);
        AxisAlignedBB axisAlignedBB4 = func_174813_aQ();
        double d13 = paramDouble2;
        for (AxisAlignedBB axisAlignedBB5 : list1)
          d13 = axisAlignedBB5.func_72323_b(axisAlignedBB4, d13); 
        axisAlignedBB4 = axisAlignedBB4.func_72317_d(0.0D, d13, 0.0D);
        double d14 = d4;
        for (AxisAlignedBB axisAlignedBB5 : list1)
          d14 = axisAlignedBB5.func_72316_a(axisAlignedBB4, d14); 
        axisAlignedBB4 = axisAlignedBB4.func_72317_d(d14, 0.0D, 0.0D);
        double d15 = d6;
        for (AxisAlignedBB axisAlignedBB5 : list1)
          d15 = axisAlignedBB5.func_72322_c(axisAlignedBB4, d15); 
        axisAlignedBB4 = axisAlignedBB4.func_72317_d(0.0D, 0.0D, d15);
        double d16 = d11 * d11 + d12 * d12;
        double d17 = d14 * d14 + d15 * d15;
        if (d16 > d17) {
          paramDouble1 = d11;
          paramDouble3 = d12;
          paramDouble2 = -d10;
          func_174826_a(axisAlignedBB2);
        } else {
          paramDouble1 = d14;
          paramDouble3 = d15;
          paramDouble2 = -d13;
          func_174826_a(axisAlignedBB4);
        } 
        for (AxisAlignedBB axisAlignedBB5 : list1)
          paramDouble2 = axisAlignedBB5.func_72323_b(func_174813_aQ(), paramDouble2); 
        func_174826_a(func_174813_aQ().func_72317_d(0.0D, paramDouble2, 0.0D));
        if (d7 * d7 + d9 * d9 >= paramDouble1 * paramDouble1 + paramDouble3 * paramDouble3) {
          paramDouble1 = d7;
          paramDouble2 = d8;
          paramDouble3 = d9;
          func_174826_a(axisAlignedBB1);
        } else {
          (new StepEvent.Post(1.0D + paramDouble2)).post();
        } 
      } 
      this.field_70170_p.field_72984_F.func_76319_b();
      this.field_70170_p.field_72984_F.func_76320_a("rest");
      doResetPositionToBB();
      this.field_70123_F = (d4 != paramDouble1 || d6 != paramDouble3);
      this.field_70124_G = (d5 != paramDouble2);
      this.field_70122_E = (this.field_70124_G && d5 < 0.0D);
      this.field_70132_H = (this.field_70123_F || this.field_70124_G);
      int i = MathHelper.func_76128_c(this.field_70165_t);
      int j = MathHelper.func_76128_c(this.field_70163_u - 0.20000000298023224D);
      int k = MathHelper.func_76128_c(this.field_70161_v);
      BlockPos blockPos = new BlockPos(i, j, k);
      Block block = this.field_70170_p.func_180495_p(blockPos).func_177230_c();
      if (block.func_149688_o() == Material.field_151579_a) {
        Block block1 = this.field_70170_p.func_180495_p(blockPos.func_177977_b()).func_177230_c();
        if (block1 instanceof net.minecraft.block.BlockFence || block1 instanceof net.minecraft.block.BlockWall || block1 instanceof net.minecraft.block.BlockFenceGate) {
          block = block1;
          blockPos = blockPos.func_177977_b();
        } 
      } 
      func_180433_a(paramDouble2, this.field_70122_E, block, blockPos);
      if (d4 != paramDouble1)
        this.field_70159_w = 0.0D; 
      if (d6 != paramDouble3)
        this.field_70179_y = 0.0D; 
      if (d5 != paramDouble2)
        block.func_176216_a(this.field_70170_p, (Entity)this); 
      if (func_70041_e_() && !bool1 && this.field_70154_o == null) {
        double d7 = this.field_70165_t - d1;
        double d8 = this.field_70163_u - d2;
        double d9 = this.field_70161_v - d3;
        if (block != Blocks.field_150468_ap)
          d8 = 0.0D; 
        if (block != null && this.field_70122_E)
          block.func_176199_a(this.field_70170_p, blockPos, (Entity)this); 
        this.field_70140_Q = (float)(this.field_70140_Q + MathHelper.func_76133_a(d7 * d7 + d9 * d9) * 0.6D);
        this.field_82151_R = (float)(this.field_82151_R + MathHelper.func_76133_a(d7 * d7 + d8 * d8 + d9 * d9) * 0.6D);
        if (this.field_82151_R > getNextStepDistance() && block.func_149688_o() != Material.field_151579_a) {
          setNextStepDistance((int)this.field_82151_R + 1);
          if (func_70090_H()) {
            float f = MathHelper.func_76133_a(this.field_70159_w * this.field_70159_w * 0.20000000298023224D + this.field_70181_x * this.field_70181_x + this.field_70179_y * this.field_70179_y * 0.20000000298023224D) * 0.35F;
            if (f > 1.0F)
              f = 1.0F; 
            func_85030_a(func_145776_H(), f, 1.0F + (this.field_70146_Z.nextFloat() - this.field_70146_Z.nextFloat()) * 0.4F);
          } 
          func_180429_a(blockPos, block);
        } 
      } 
      try {
        func_145775_I();
      } catch (Throwable throwable) {
        CrashReport crashReport = CrashReport.func_85055_a(throwable, "Checking entity block collision");
        CrashReportCategory crashReportCategory = crashReport.func_85058_a("Entity being checked for collision");
        func_85029_a(crashReportCategory);
        throw new ReportedException(crashReport);
      } 
      boolean bool = func_70026_G();
      if (this.field_70170_p.func_147470_e(func_174813_aQ().func_72331_e(0.001D, 0.001D, 0.001D))) {
        func_70081_e(1);
        if (!bool) {
          plusPlusFire();
          if (getFire() == 0)
            func_70015_d(8); 
        } 
      } else if (getFire() <= 0) {
        SetFire(-this.field_70174_ab);
      } 
      if (bool && getFire() > 0) {
        func_85030_a("random.fizz", 0.7F, 1.6F + (this.field_70146_Z.nextFloat() - this.field_70146_Z.nextFloat()) * 0.4F);
        SetFire(-this.field_70174_ab);
      } 
      this.field_70170_p.field_72984_F.func_76319_b();
    } 
  }
  
  @Inject(method = {"updateEntityActionState"}, at = {@At("RETURN")})
  public void onUpdateAction(CallbackInfo paramCallbackInfo) {
    if ((OringoClient..() && !Scaffold..("None") && this.field_71159_c.field_71474_y.field_74314_A.func_151470_d()) || (OringoClient..() && !AutoMask.() && HidePlayers.()))
      this.field_70703_bu = false; 
  }
  
  public void func_71059_n(Entity paramEntity) {
    if (ForgeHooks.onPlayerAttackTarget((EntityPlayer)this, paramEntity) && 
      paramEntity.func_70075_an() && !paramEntity.func_85031_j((Entity)this)) {
      float f1 = (float)func_110148_a(SharedMonsterAttributes.field_111264_e).func_111126_e();
      int i = 0;
      float f2 = 0.0F;
      if (paramEntity instanceof EntityLivingBase) {
        f2 = EnchantmentHelper.func_152377_a(func_70694_bm(), ((EntityLivingBase)paramEntity).func_70668_bt());
      } else {
        f2 = EnchantmentHelper.func_152377_a(func_70694_bm(), EnumCreatureAttribute.UNDEFINED);
      } 
      i += EnchantmentHelper.func_77501_a((EntityLivingBase)this);
      if (func_70051_ag())
        i++; 
      if (f1 > 0.0F || f2 > 0.0F) {
        boolean bool1 = (this.field_70143_R > 0.0F && !this.field_70122_E && !func_70617_f_() && !func_70090_H() && !func_70644_a(Potion.field_76440_q) && this.field_70154_o == null && paramEntity instanceof EntityLivingBase) ? true : false;
        if (bool1 && f1 > 0.0F)
          f1 *= 1.5F; 
        f1 += f2;
        boolean bool2 = false;
        int j = EnchantmentHelper.func_90036_a((EntityLivingBase)this);
        if (paramEntity instanceof EntityLivingBase && j > 0 && !paramEntity.func_70027_ad()) {
          bool2 = true;
          paramEntity.func_70015_d(1);
        } 
        double d1 = paramEntity.field_70159_w;
        double d2 = paramEntity.field_70181_x;
        double d3 = paramEntity.field_70179_y;
        boolean bool = paramEntity.func_70097_a(DamageSource.func_76365_a((EntityPlayer)this), f1);
        if (bool) {
          EntityLivingBase entityLivingBase;
          if (i > 0) {
            paramEntity.func_70024_g((-MathHelper.func_76126_a(this.field_70177_z * 3.1415927F / 180.0F) * i * 0.5F), 0.1D, (MathHelper.func_76134_b(this.field_70177_z * 3.1415927F / 180.0F) * i * 0.5F));
            if (!OringoClient..() || !OringoClient...()) {
              this.field_70159_w *= 0.6D;
              this.field_70179_y *= 0.6D;
              func_70031_b(false);
            } 
          } 
          if (paramEntity instanceof EntityPlayerMP && paramEntity.field_70133_I) {
            ((EntityPlayerMP)paramEntity).field_71135_a.func_147359_a((Packet)new S12PacketEntityVelocity(paramEntity));
            paramEntity.field_70133_I = false;
            paramEntity.field_70159_w = d1;
            paramEntity.field_70181_x = d2;
            paramEntity.field_70179_y = d3;
          } 
          if (bool1)
            func_71009_b(paramEntity); 
          if (f2 > 0.0F)
            func_71047_c(paramEntity); 
          if (f1 >= 18.0F)
            func_71029_a((StatBase)AchievementList.field_75999_E); 
          func_130011_c(paramEntity);
          if (paramEntity instanceof EntityLivingBase)
            EnchantmentHelper.func_151384_a((EntityLivingBase)paramEntity, (Entity)this); 
          EnchantmentHelper.func_151385_b((EntityLivingBase)this, paramEntity);
          ItemStack itemStack = func_71045_bC();
          Entity entity = paramEntity;
          if (paramEntity instanceof EntityDragonPart) {
            IEntityMultiPart iEntityMultiPart = ((EntityDragonPart)paramEntity).field_70259_a;
            if (iEntityMultiPart instanceof EntityLivingBase)
              entityLivingBase = (EntityLivingBase)iEntityMultiPart; 
          } 
          if (itemStack != null && entityLivingBase instanceof EntityLivingBase) {
            itemStack.func_77961_a(entityLivingBase, (EntityPlayer)this);
            if (itemStack.field_77994_a <= 0)
              func_71028_bD(); 
          } 
          if (paramEntity instanceof EntityLivingBase) {
            func_71064_a(StatList.field_75951_w, Math.round(f1 * 10.0F));
            if (j > 0)
              paramEntity.func_70015_d(j * 4); 
          } 
          func_71020_j(0.3F);
        } else if (bool2) {
          paramEntity.func_70066_B();
        } 
      } 
    } 
  }
  
  @Shadow
  public abstract void func_85030_a(String paramString, float paramFloat1, float paramFloat2);
  
  @Shadow
  public abstract boolean func_70613_aW();
  
  @Shadow
  public abstract boolean func_70097_a(DamageSource paramDamageSource, float paramFloat);
  
  @Shadow
  protected abstract boolean func_175160_A();
  
  @Shadow
  public abstract void func_70031_b(boolean paramBoolean);
  
  @Shadow
  public abstract boolean func_70093_af();
  
  @Shadow
  public abstract void func_71064_a(StatBase paramStatBase, int paramInt);
  
  @Shadow
  public abstract void func_71016_p();
  
  @Shadow
  public abstract void func_71009_b(Entity paramEntity);
  
  @Shadow
  protected abstract boolean func_145771_j(double paramDouble1, double paramDouble2, double paramDouble3);
  
  @Shadow
  public abstract void func_71047_c(Entity paramEntity);
  
  @Shadow
  public abstract void func_70078_a(Entity paramEntity);
  
  @Shadow
  public abstract boolean func_110317_t();
  
  @Shadow
  protected abstract void func_110318_g();
}
