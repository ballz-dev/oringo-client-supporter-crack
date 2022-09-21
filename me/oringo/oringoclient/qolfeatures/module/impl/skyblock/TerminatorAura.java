package me.oringo.oringoclient.qolfeatures.module.impl.skyblock;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import me.oringo.oringoclient.events.impl.MotionUpdateEvent;
import me.oringo.oringoclient.events.impl.MoveStateUpdateEvent;
import me.oringo.oringoclient.qolfeatures.module.Module;
import me.oringo.oringoclient.qolfeatures.module.impl.combat.KillAura;
import me.oringo.oringoclient.qolfeatures.module.impl.other.HClip;
import me.oringo.oringoclient.qolfeatures.module.impl.other.LightningDetect;
import me.oringo.oringoclient.qolfeatures.module.impl.other.NamesOnly;
import me.oringo.oringoclient.qolfeatures.module.impl.other.PVPInfo;
import me.oringo.oringoclient.qolfeatures.module.impl.player.AntiVoid;
import me.oringo.oringoclient.qolfeatures.module.settings.Setting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.BooleanSetting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.ModeSetting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.NumberSetting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.StringSetting;
import me.oringo.oringoclient.utils.EntityUtils;
import me.oringo.oringoclient.utils.Rotation;
import me.oringo.oringoclient.utils.RotationUtils;
import me.oringo.oringoclient.utils.SkyblockUtils;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.util.Vec3;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class TerminatorAura extends Module {
  public static HashMap<EntityLivingBase, Long>  = new HashMap<>();
  
  public ModeSetting  = new ModeSetting("Rotation", "Bow", new String[] { "Bow", "Middle" });
  
  public BooleanSetting inDungeon = new BooleanSetting("only Dungeon", true);
  
  public ModeSetting  = new ModeSetting("Mode", "Swap", new String[] { "Swap", "Held" });
  
  public NumberSetting  = new NumberSetting("Use delay", 3.0D, 1.0D, 10.0D, 1.0D);
  
  public NumberSetting  = new NumberSetting("Range", 15.0D, 5.0D, 30.0D, 1.0D);
  
  public static EntityLivingBase ;
  
  public BooleanSetting  = new BooleanSetting("Teamcheck", false);
  
  public ModeSetting  = new ModeSetting("Mouse", "Right", new String[] { "Left", "Right" });
  
  public StringSetting  = new StringSetting("Custom Item");
  
  public BooleanSetting  = new BooleanSetting("Boss Lock", true);
  
  public static boolean ;
  
  public NumberSetting  = new NumberSetting("Fov", 360.0D, 1.0D, 360.0D, 1.0D);
  
  public void () {
    switch (this..()) {
      case "Left":
        mc.field_71439_g.func_71038_i();
        break;
      case "Right":
        mc.func_147114_u().func_147298_b().func_179290_a((Packet)new C08PacketPlayerBlockPlacement(mc.field_71439_g.func_70694_bm()));
        break;
    } 
  }
  
  public EntityLivingBase (EntityLivingBase paramEntityLivingBase) {
    if (this..() && paramEntityLivingBase != null && MoveStateUpdateEvent.((Entity)paramEntityLivingBase) && paramEntityLivingBase.func_110143_aJ() > 0.0F && !paramEntityLivingBase.field_70128_L && paramEntityLivingBase.func_70685_l((Entity)mc.field_71439_g) && paramEntityLivingBase.func_70032_d((Entity)mc.field_71439_g) < this..())
      return paramEntityLivingBase; 
    .entrySet().removeIf(TerminatorAura::);
    Objects.requireNonNull(mc.field_71439_g);
    List list = (List)mc.field_71441_e.func_72910_y().stream().filter(this::lambda$getTarget$1).sorted(Comparator.comparingDouble(mc.field_71439_g::func_70032_d)).sorted(Comparator.comparing(paramEntityLivingBase::).reversed()).collect(Collectors.toList());
    Iterator<Entity> iterator = list.iterator();
    if (iterator.hasNext()) {
      Entity entity = iterator.next();
      .put((EntityLivingBase)entity, Long.valueOf(System.currentTimeMillis()));
      return (EntityLivingBase)entity;
    } 
    return null;
  }
  
  public TerminatorAura() {
    super("Terminator Aura", 0, Module.Category.);
    (new Setting[] { (Setting)this., (Setting)this., (Setting)this., (Setting)this., (Setting)this., (Setting)this., (Setting)this., (Setting)this., (Setting)this.inDungeon, (Setting)this. });
  }
  
  public boolean (EntityLivingBase paramEntityLivingBase) {
    if (paramEntityLivingBase == mc.field_71439_g || .containsKey(paramEntityLivingBase) || paramEntityLivingBase instanceof net.minecraft.entity.monster.EntityBlaze || (EntityUtils.isTeam((Entity)paramEntityLivingBase) && this..()) || paramEntityLivingBase instanceof net.minecraft.entity.item.EntityArmorStand || !mc.field_71439_g.func_70685_l((Entity)paramEntityLivingBase) || paramEntityLivingBase.func_110143_aJ() <= 0.0F || paramEntityLivingBase.func_70032_d((Entity)mc.field_71439_g) > this..() || ((paramEntityLivingBase instanceof net.minecraft.entity.player.EntityPlayer || paramEntityLivingBase instanceof net.minecraft.entity.passive.EntityBat || paramEntityLivingBase instanceof net.minecraft.entity.monster.EntityZombie || paramEntityLivingBase instanceof net.minecraft.entity.monster.EntityGiantZombie) && paramEntityLivingBase.func_82150_aj()) || paramEntityLivingBase.func_70005_c_().equals("Dummy") || paramEntityLivingBase.func_70005_c_().startsWith("Decoy"))
      return false; 
    if (!RemoveAnnoyingMobs.(HClip.((Entity)paramEntityLivingBase), (float)this..()))
      return false; 
    if (AntiVoid.().()) {
      boolean bool = HClip.((Entity)paramEntityLivingBase);
      if (NamesOnly..("Enemies") || bool)
        return (NamesOnly..("Enemies") && bool); 
    } 
    return true;
  }
  
  public static float (ItemStack paramItemStack) {
    return (paramItemStack.func_77973_b() instanceof ItemTool) ? (float)(((ItemTool)paramItemStack.func_77973_b()).func_150913_i().func_77996_d() + EnchantmentHelper.func_77506_a(Enchantment.field_77349_p.field_77352_x, paramItemStack) * 0.75D) : 0.0F;
  }
  
  @SubscribeEvent
  public void (MotionUpdateEvent.Post paramPost) {
    if (!)
      return; 
    int i = mc.field_71439_g.field_71071_by.field_70461_c;
    if (this..().equals("Swap"))
      for (byte b = 0; b < 9; b++) {
        if (mc.field_71439_g.field_71071_by.func_70301_a(b) != null && (mc.field_71439_g.field_71071_by.func_70301_a(b).func_82833_r().contains("Juju") || mc.field_71439_g.field_71071_by.func_70301_a(b).func_82833_r().contains("Terminator") || (!this..("") && mc.field_71439_g.field_71071_by.func_70301_a(b).func_82833_r().contains(this..())))) {
          mc.field_71439_g.field_71071_by.field_70461_c = b;
          break;
        } 
      }  
    PVPInfo.();
    ();
    mc.field_71439_g.field_71071_by.field_70461_c = i;
    PVPInfo.();
     = false;
  }
  
  @SubscribeEvent(priority = EventPriority.LOW)
  public void (MotionUpdateEvent.Pre paramPre) {
    if (KillAura. != null || Aimbot. || !() || mc.field_71439_g.field_70173_aa % this..() != 0.0D || (!SkyblockUtils.inDungeon && this.inDungeon.()))
      return; 
    boolean bool = (mc.field_71439_g.func_70694_bm() != null && (mc.field_71439_g.func_70694_bm().func_82833_r().contains("Juju") || mc.field_71439_g.func_70694_bm().func_82833_r().contains("Terminator") || (!this..().equals("") && mc.field_71439_g.func_70694_bm().func_82833_r().contains(this..())))) ? true : false;
    if (this..().equals("Swap"))
      for (byte b = 0; b < 9; b++) {
        if (mc.field_71439_g.field_71071_by.func_70301_a(b) != null && (mc.field_71439_g.field_71071_by.func_70301_a(b).func_82833_r().contains("Juju") || mc.field_71439_g.field_71071_by.func_70301_a(b).func_82833_r().contains("Terminator") || (!this..("") && mc.field_71439_g.field_71071_by.func_70301_a(b).func_82833_r().contains(this..())))) {
          bool = true;
          break;
        } 
      }  
    if (!bool)
      return; 
     = ();
    if ( != null) {
       = true;
      Rotation rotation = this..("Bow") ? DojoHelper.((Entity)) : RotationUtils.(new Vec3(.field_70165_t, .field_70163_u + .func_70047_e() / 2.0D, .field_70161_v));
      paramPre. = rotation.();
      paramPre. = rotation.();
    } 
  }
}
