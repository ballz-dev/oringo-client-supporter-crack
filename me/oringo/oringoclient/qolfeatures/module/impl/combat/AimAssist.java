package me.oringo.oringoclient.qolfeatures.module.impl.combat;

import java.util.Comparator;
import java.util.List;
import me.oringo.oringoclient.OringoClient;
import me.oringo.oringoclient.commands.impl.JerryBoxCommand;
import me.oringo.oringoclient.mixins.renderer.RenderAccessor;
import me.oringo.oringoclient.qolfeatures.module.Module;
import me.oringo.oringoclient.qolfeatures.module.impl.other.HClip;
import me.oringo.oringoclient.qolfeatures.module.impl.other.NamesOnly;
import me.oringo.oringoclient.qolfeatures.module.impl.player.AntiVoid;
import me.oringo.oringoclient.qolfeatures.module.impl.player.ArmorSwap;
import me.oringo.oringoclient.qolfeatures.module.impl.player.AutoHeal;
import me.oringo.oringoclient.qolfeatures.module.impl.player.InvManager;
import me.oringo.oringoclient.qolfeatures.module.settings.Setting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.BooleanSetting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.NumberSetting;
import me.oringo.oringoclient.utils.EntityUtils;
import me.oringo.oringoclient.utils.Rotation;
import me.oringo.oringoclient.utils.RotationUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;

public class AimAssist extends Module {
  public BooleanSetting  = new BooleanSetting("Mobs", false);
  
  public BooleanSetting  = new BooleanSetting("Vertical", true);
  
  public NumberSetting  = new NumberSetting("Range", 5.0D, 0.0D, 6.0D, 0.1D);
  
  public BooleanSetting  = new BooleanSetting("Click aim", false);
  
  public NumberSetting  = new NumberSetting("Fov", 60.0D, 30.0D, 180.0D, 1.0D);
  
  public BooleanSetting  = new BooleanSetting("Teams", true);
  
  public NumberSetting  = new NumberSetting(this, "Min speed", 20.0D, 1.0D, 40.0D, 0.1D) {
      public void (double param1Double) {
        super.(param1Double);
        if (() > this...())
          super.(this...()); 
      }
    };
  
  public BooleanSetting  = new BooleanSetting("Players", true);
  
  public NumberSetting  = new NumberSetting(this, "Max speed", 30.0D, 1.0D, 40.0D, 0.1D) {
      public void (double param1Double) {
        super.(param1Double);
        if (param1Double < this...())
          super.(this...()); 
      }
    };
  
  public BooleanSetting  = new BooleanSetting("Invisibles", false);
  
  public static EntityLivingBase () {
    if ((KillAura.mc.field_71462_r instanceof net.minecraft.client.gui.inventory.GuiContainer && KillAura..()) || KillAura.mc.field_71441_e == null)
      return null; 
    List<EntityLivingBase> list = JerryBoxCommand.();
    if (!list.isEmpty()) {
      if (KillAura. >= list.stream().filter(KillAura::).count())
        KillAura. = 0; 
      switch (KillAura..()) {
        case "Switch":
          return list.get(KillAura.);
        case "Single":
          return list.get(0);
      } 
    } 
    return null;
  }
  
  public Entity () {
    List<Entity> list = mc.field_71441_e.func_175644_a(EntityLivingBase.class, this::lambda$getTarget$0);
    list.sort(Comparator.comparingDouble(AimAssist::));
    return !list.isEmpty() ? list.get(0) : null;
  }
  
  public static void (int paramInt1, int paramInt2, float paramFloat1, float paramFloat2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, float paramFloat3, float paramFloat4, EntityLivingBase paramEntityLivingBase) {
    ResourceLocation resourceLocation = null;
    if (paramEntityLivingBase instanceof AbstractClientPlayer) {
      resourceLocation = ((AbstractClientPlayer)paramEntityLivingBase).func_110306_p();
    } else {
      resourceLocation = ((RenderAccessor)OringoClient.mc.func_175598_ae().func_78715_a(paramEntityLivingBase.getClass())).getEntityTexture((Entity)paramEntityLivingBase);
    } 
    if (resourceLocation != null) {
      Minecraft.func_71410_x().func_110434_K().func_110577_a(resourceLocation);
      GL11.glEnable(3042);
      Gui.func_152125_a(paramInt1, paramInt2, paramFloat1, paramFloat2, paramInt3, paramInt4, paramInt5, paramInt6, paramFloat3, paramFloat4);
      GL11.glDisable(3042);
    } 
  }
  
  public boolean (EntityLivingBase paramEntityLivingBase) {
    if (paramEntityLivingBase == mc.field_71439_g || !ArmorSwap.((Entity)paramEntityLivingBase) || (!this..() && paramEntityLivingBase.func_82150_aj()) || paramEntityLivingBase instanceof net.minecraft.entity.item.EntityArmorStand || !mc.field_71439_g.func_70685_l((Entity)paramEntityLivingBase) || paramEntityLivingBase.func_110143_aJ() <= 0.0F || paramEntityLivingBase.func_70032_d((Entity)mc.field_71439_g) > this..() || Math.abs(MathHelper.func_76142_g(mc.field_71439_g.field_70177_z) - MathHelper.func_76142_g(((Entity)paramEntityLivingBase).())) > this..())
      return false; 
    if (AntiVoid.().()) {
      boolean bool = HClip.((Entity)paramEntityLivingBase);
      if (NamesOnly..("Enemies") || bool)
        return (NamesOnly..("Enemies") && bool); 
    } 
    return ((paramEntityLivingBase instanceof net.minecraft.entity.monster.EntityMob || paramEntityLivingBase instanceof net.minecraft.entity.passive.EntityVillager || paramEntityLivingBase instanceof net.minecraft.entity.monster.EntitySnowman || paramEntityLivingBase instanceof net.minecraft.entity.passive.EntityAmbientCreature || paramEntityLivingBase instanceof net.minecraft.entity.passive.EntityWaterMob || paramEntityLivingBase instanceof net.minecraft.entity.passive.EntityAnimal || paramEntityLivingBase instanceof net.minecraft.entity.monster.EntitySlime) && !this..()) ? false : (!(paramEntityLivingBase instanceof net.minecraft.entity.player.EntityPlayer && ((EntityUtils.isTeam((Entity)paramEntityLivingBase) && this..()) || !this..())));
  }
  
  public static boolean (double paramDouble1, double paramDouble2, double paramDouble3) {
    WorldClient worldClient = (Minecraft.func_71410_x()).field_71441_e;
    BlockPos blockPos = new BlockPos(paramDouble1, paramDouble2, paramDouble3);
    for (byte b = 0; b < 3; b++) {
      blockPos = blockPos.func_177977_b();
      if (!(worldClient.func_180495_p(blockPos).func_177230_c() instanceof net.minecraft.block.BlockAir))
        return false; 
    } 
    return true;
  }
  
  public AimAssist() {
    super("Aim Assist", Module.Category.);
    (new Setting[] { (Setting)this., (Setting)this., (Setting)this., (Setting)this., (Setting)this., (Setting)this., (Setting)this., (Setting)this., (Setting)this., (Setting)this. });
  }
  
  public static double (double paramDouble1, double paramDouble2, double paramDouble3) {
    paramDouble1 = Math.max(paramDouble2, paramDouble1);
    return Math.min(paramDouble3, paramDouble1);
  }
  
  @SubscribeEvent
  public void (RenderWorldLastEvent paramRenderWorldLastEvent) {
    if (() && (!this..() || mc.field_71474_y.field_74312_F.func_151470_d())) {
      Entity entity = ();
      if (entity != null && mc.field_71476_x != null && mc.field_71476_x.field_72308_g != entity) {
        Rotation rotation = (entity);
        float f1 = mc.field_71439_g.field_70177_z + MathHelper.func_76142_g(rotation.() - mc.field_71439_g.field_70177_z);
        float f2 = mc.field_71439_g.field_70125_A + MathHelper.func_76142_g(rotation.() - mc.field_71439_g.field_70125_A);
        float f3 = (float)((f1 - mc.field_71439_g.field_70177_z) / AutoHeal.(this..(), this..()));
        float f4 = (float)((f2 - mc.field_71439_g.field_70125_A) / AutoHeal.(this..(), this..()));
        mc.field_71439_g.field_70177_z += f3;
        if (this..())
          mc.field_71439_g.field_70125_A += f4; 
      } 
    } 
  }
  
  public Rotation (Entity paramEntity) {
    if (paramEntity != null) {
      Vec3 vec31 = mc.field_71439_g.func_174824_e(1.0F);
      Vec3 vec32 = InvManager.(mc.field_71439_g.field_70177_z, mc.field_71439_g.field_70125_A);
      Vec3 vec33 = vec31.func_72441_c(vec32.field_72450_a, vec32.field_72448_b, vec32.field_72449_c);
      return RotationUtils.(InvManager.(vec33, paramEntity.func_174813_aQ()));
    } 
    return null;
  }
}
