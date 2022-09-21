package me.oringo.oringoclient.qolfeatures.module.impl.skyblock;

import java.util.List;
import me.oringo.oringoclient.OringoClient;
import me.oringo.oringoclient.commands.impl.ClipCommand;
import me.oringo.oringoclient.qolfeatures.module.Module;
import me.oringo.oringoclient.qolfeatures.module.impl.combat.KillAura;
import me.oringo.oringoclient.qolfeatures.module.impl.movement.SafeWalk;
import me.oringo.oringoclient.utils.MilliTimer;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class BlazeSwapper extends Module {
  public static String[] ;
  
  public MilliTimer  = new MilliTimer();
  
  public static String[]  = new String[] { "Firedust", "Kindlebane", "Pyrochaos" };
  
  @SubscribeEvent
  public void (AttackEntityEvent paramAttackEntityEvent) {
    if (() && paramAttackEntityEvent.entityPlayer == mc.field_71439_g && ((paramAttackEntityEvent.target instanceof EntitySkeleton && ((EntitySkeleton)paramAttackEntityEvent.target).func_82202_m() == 1) || paramAttackEntityEvent.target instanceof net.minecraft.entity.monster.EntityBlaze || paramAttackEntityEvent.target instanceof net.minecraft.entity.monster.EntityPigZombie)) {
      List<EntityArmorStand> list = mc.field_71441_e.func_175647_a(EntityArmorStand.class, paramAttackEntityEvent.target.func_174813_aQ().func_72314_b(0.1D, 2.0D, 0.1D), BlazeSwapper::);
      if (!list.isEmpty()) {
        EntityArmorStand entityArmorStand = list.get(0);
        Type type = (entityArmorStand.func_145748_c_().func_150260_c());
        if (type != Type.) {
          int i = SafeWalk.(type);
          if (i != -1) {
            ClipCommand.(i);
            if (this..(500L) && mc.field_71439_g.field_71071_by.func_70301_a(i).func_77973_b() instanceof ItemSword && !((ItemSword)mc.field_71439_g.field_71071_by.func_70301_a(i).func_77973_b()).func_150932_j().equals(type.)) {
              mc.func_147114_u().func_147298_b().func_179290_a((Packet)new C08PacketPlayerBlockPlacement(mc.field_71439_g.field_71071_by.func_70301_a(i)));
              this..();
            } 
          } 
        } 
      } 
    } 
  }
  
  public BlazeSwapper() {
    super("Blaze Swapper", Module.Category.);
    (new me.oringo.oringoclient.qolfeatures.module.settings.Setting[0]);
  }
  
  public static void (String paramString) {
    if (OringoClient.)
      MilliTimer.(paramString); 
  }
  
  static {
     = new String[] { "Mawdredge", "Twilight", "Deathripper" };
  }
  
  public static void () {
    if (KillAura.mc.field_71439_g != null && KillAura.mc.field_71439_g.func_71039_bw())
      KillAura.mc.field_71442_b.func_78766_c((EntityPlayer)KillAura.mc.field_71439_g); 
  }
  
  public Type (String paramString) {
    for (Type type : Type.values()) {
      if (paramString.toLowerCase().contains(type.toString().toLowerCase()))
        return type; 
    } 
    return Type.;
  }
  
  public enum Type {
    ,
    ,
    ,
    ("STONE"),
    ("STONE");
    
    public String ;
    
    Type(String param1String1) {
      this. = param1String1;
    }
    
    static {
       = new Type("SPIRIT", 3, "IRON");
       = new Type("NONE", 4, "NONE");
       = new Type[] { , , , ,  };
    }
  }
}
