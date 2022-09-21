package me.oringo.oringoclient.qolfeatures.module.impl.skyblock;

import com.mojang.realmsclient.gui.ChatFormatting;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import me.oringo.oringoclient.OringoClient;
import me.oringo.oringoclient.commands.impl.ClipCommand;
import me.oringo.oringoclient.events.impl.BlockBoundsEvent;
import me.oringo.oringoclient.events.impl.LeftClickEvent;
import me.oringo.oringoclient.events.impl.MotionUpdateEvent;
import me.oringo.oringoclient.events.impl.MoveEvent;
import me.oringo.oringoclient.events.impl.PreAttackEvent;
import me.oringo.oringoclient.lunarspoof.LunarClient;
import me.oringo.oringoclient.qolfeatures.module.Module;
import me.oringo.oringoclient.qolfeatures.module.impl.player.FastBreak;
import me.oringo.oringoclient.qolfeatures.module.settings.Setting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.BooleanSetting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.ModeSetting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.NumberSetting;
import me.oringo.oringoclient.utils.ReflectionUtils;
import me.oringo.oringoclient.utils.Rotation;
import me.oringo.oringoclient.utils.RotationUtils;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.opengl.GL11;

public class DojoHelper extends Module {
  public static BooleanSetting ;
  
  public int ;
  
  public static NumberSetting ;
  
  public static BooleanSetting ;
  
  public static BooleanSetting ;
  
  public static NumberSetting ;
  
  public static HashMap<Entity, Long>  = new HashMap<>();
  
  public static BooleanSetting  = new BooleanSetting("Hide bad zombies", true);
  
  public static ModeSetting ;
  
  public static BooleanSetting ;
  
  public static boolean ;
  
  public static boolean ;
  
  public int ;
  
  public void (Entity paramEntity) {
    if (LeftClickEvent.("Challenge: Discipline") && paramEntity instanceof EntityZombie && ((EntityZombie)paramEntity).func_82169_q(3) != null) {
      Item item = ((EntityZombie)paramEntity).func_82169_q(3).func_77973_b();
      if (Items.field_151024_Q.equals(item)) {
        (DojoHelper::);
      } else if (Items.field_151169_ag.equals(item)) {
        (DojoHelper::);
      } else if (Items.field_151161_ac.equals(item)) {
        (DojoHelper::);
      } else if (Items.field_151028_Y.equals(item)) {
        (DojoHelper::);
      } 
    } 
  }
  
  public void (Predicate<ItemStack> paramPredicate) {
    int i = ClipCommand.(paramPredicate);
    if (i != -1)
      LunarClient.(i); 
  }
  
  public static Rotation (Entity paramEntity) {
    double d1 = (paramEntity.field_70165_t - paramEntity.field_70142_S) * 0.4D;
    double d2 = (paramEntity.field_70161_v - paramEntity.field_70136_U) * 0.4D;
    double d3 = OringoClient.mc.field_71439_g.func_70032_d(paramEntity);
    d3 -= d3 % 0.8D;
    double d4 = d3 / 0.8D * d1;
    double d5 = d3 / 0.8D * d2;
    double d6 = paramEntity.field_70165_t + d4 - OringoClient.mc.field_71439_g.field_70165_t;
    double d7 = paramEntity.field_70161_v + d5 - OringoClient.mc.field_71439_g.field_70161_v;
    double d8 = OringoClient.mc.field_71439_g.field_70163_u + OringoClient.mc.field_71439_g.func_70047_e() - paramEntity.field_70163_u + paramEntity.func_70047_e();
    double d9 = OringoClient.mc.field_71439_g.func_70032_d(paramEntity);
    float f1 = (float)Math.toDegrees(Math.atan2(d7, d6)) - 90.0F;
    double d10 = MathHelper.func_76133_a(d6 * d6 + d7 * d7);
    float f2 = (float)-(Math.atan2(d8, d10) * 180.0D / Math.PI) + (float)d9 * 0.11F;
    return new Rotation(f1, -f2);
  }
  
  @SubscribeEvent
  public void (PreAttackEvent paramPreAttackEvent) {
    if (() && .())
      (paramPreAttackEvent.); 
  }
  
  public double (String paramString) {
    null = 100000.0D;
    paramString = paramString.replaceAll(":", ".");
    return Double.parseDouble(paramString);
  }
  
  public static void () {
    GL11.glStencilFunc(514, 1, 15);
    GL11.glStencilOp(7680, 7680, 7680);
    GL11.glPolygonMode(1032, 6913);
  }
  
  static {
     = new BooleanSetting("Auto sword swap", true);
     = new BooleanSetting("Tenacity float", true);
     = new BooleanSetting("Mastery aimbot", true);
     = new BooleanSetting("W tap", true, DojoHelper::);
     = new NumberSetting("Time", 0.3D, 0.1D, 5.0D, 0.05D, DojoHelper::);
     = new NumberSetting("Bow charge", 0.6D, 0.1D, 1.0D, 0.1D, DojoHelper::);
     = new ModeSetting("Color", DojoHelper::, "Yellow", new String[] { "Red", "Yellow", "Green" });
  }
  
  @SubscribeEvent
  public void (MoveEvent paramMoveEvent) {
    if (() && .() &&  && this. == 2) {
      paramMoveEvent.stop();
      mc.field_71439_g.func_70016_h(0.0D, 0.0D, 0.0D);
    } 
  }
  
  public static float (IBlockState paramIBlockState, ItemStack paramItemStack) {
    if (paramItemStack == null)
      return 1.0F; 
    float f = paramItemStack.func_77973_b().getDigSpeed(paramItemStack, paramIBlockState);
    if (f > 1.0F && paramItemStack.func_150998_b(paramIBlockState.func_177230_c())) {
      int i = EnchantmentHelper.func_77506_a(Enchantment.field_77349_p.field_77352_x, paramItemStack);
      if (i > 0)
        f += (i * i + 1); 
    } 
    return f;
  }
  
  @SubscribeEvent
  public void (BlockBoundsEvent paramBlockBoundsEvent) {
    if (paramBlockBoundsEvent. == Blocks.field_150353_l &&  && () && .())
      paramBlockBoundsEvent. = new AxisAlignedBB(paramBlockBoundsEvent..func_177958_n(), paramBlockBoundsEvent..func_177956_o(), paramBlockBoundsEvent..func_177952_p(), (paramBlockBoundsEvent..func_177958_n() + 1), (paramBlockBoundsEvent..func_177956_o() + 1), (paramBlockBoundsEvent..func_177952_p() + 1)); 
  }
  
  @SubscribeEvent
  public void (MotionUpdateEvent paramMotionUpdateEvent) {
    if (()) {
      if (.() &&  && mc.field_71439_g.func_70694_bm() != null && mc.field_71439_g.func_70694_bm().func_77973_b() == Items.field_151031_f) {
        if (!mc.field_71439_g.func_71039_bw())
          mc.field_71442_b.func_78769_a((EntityPlayer)mc.field_71439_g, (World)mc.field_71441_e, mc.field_71439_g.func_70694_bm()); 
        KeyBinding.func_74510_a(mc.field_71474_y.field_74313_G.func_151463_i(), true);
        Pattern pattern = Pattern.compile("\\d:\\d\\d\\d");
        Entity entity = null;
        double d = 100.0D;
        .entrySet().removeIf(DojoHelper::);
        Iterator<Entity> iterator = ((List)mc.field_71441_e.field_72996_f.stream().filter(pattern::).sorted(Comparator.comparingDouble(this::lambda$onPlayerUpdate$6)).collect(Collectors.toList())).iterator();
        if (iterator.hasNext()) {
          Entity entity1 = iterator.next();
          Rotation rotation = RotationUtils.(entity1.func_174791_d().func_72441_c(0.0D, 4.0D, 0.0D));
          entity = entity1;
          d = (ChatFormatting.stripFormatting(entity1.func_70005_c_()));
          paramMotionUpdateEvent.setRotation(rotation);
        } 
        if (mc.field_71439_g.func_71039_bw() && mc.field_71439_g.func_71011_bu().func_77973_b() == Items.field_151031_f && entity != null && !paramMotionUpdateEvent.isPre()) {
          ItemBow itemBow = (ItemBow)mc.field_71439_g.func_71011_bu().func_77973_b();
          int i = itemBow.func_77626_a(mc.field_71439_g.func_71011_bu()) - mc.field_71439_g.func_71052_bv();
          float f = i / 20.0F;
          f = (f * f + f * 2.0F) / 3.0F;
          if (f >= .() && d <= .()) {
            if (.()) {
              mc.func_147114_u().func_147298_b().func_179290_a((Packet)new C0BPacketEntityAction((Entity)mc.field_71439_g, C0BPacketEntityAction.Action.STOP_SPRINTING));
              mc.func_147114_u().func_147298_b().func_179290_a((Packet)new C0BPacketEntityAction((Entity)mc.field_71439_g, C0BPacketEntityAction.Action.START_SPRINTING));
            } 
            mc.field_71442_b.func_78766_c((EntityPlayer)mc.field_71439_g);
            .put(entity, Long.valueOf(System.currentTimeMillis()));
          } 
        } 
      } 
      if (paramMotionUpdateEvent.isPre())
        if (.() && ) {
          if (this. == 0) {
            paramMotionUpdateEvent.setPitch(90.0F);
            if (MoveEvent.(0.01F) && mc.field_71439_g.field_70122_E) {
              MovingObjectPosition movingObjectPosition = FastBreak.(0.0F, 90.0F, 4.5F);
              if (movingObjectPosition != null) {
                int i = mc.field_71439_g.field_71071_by.field_70461_c;
                LunarClient.(8);
                mc.func_147114_u().func_147298_b().func_179290_a((Packet)new C08PacketPlayerBlockPlacement(mc.field_71439_g.func_70694_bm()));
                Vec3 vec3 = movingObjectPosition.field_72307_f;
                BlockPos blockPos = movingObjectPosition.func_178782_a();
                float f1 = (float)(vec3.field_72450_a - blockPos.func_177958_n());
                float f2 = (float)(vec3.field_72448_b - blockPos.func_177956_o());
                float f3 = (float)(vec3.field_72449_c - blockPos.func_177952_p());
                mc.func_147114_u().func_147298_b().func_179290_a((Packet)new C08PacketPlayerBlockPlacement(movingObjectPosition.func_178782_a(), movingObjectPosition.field_178784_b.func_176745_a(), mc.field_71439_g.func_70694_bm(), f1, f2, f3));
                mc.func_147114_u().func_147298_b().func_179290_a((Packet)new C0APacketAnimation());
                LunarClient.(i);
              } 
              mc.field_71439_g.func_70664_aZ();
              this. = 1;
            } 
          } else if (this. == 1) {
            if (MoveEvent.(0.5F) && mc.field_71439_g.field_70181_x < 0.0D)
              this. = 2; 
          } else if (this. == 2) {
            this. %= 40;
            this.++;
            if (this. == 40) {
              paramMotionUpdateEvent.setY(paramMotionUpdateEvent. - 0.20000000298023224D);
            } else if (this. == 39) {
              paramMotionUpdateEvent.setY(paramMotionUpdateEvent. - 0.10000000149011612D);
            } else if (this. == 38) {
              paramMotionUpdateEvent.setY(paramMotionUpdateEvent. - 0.07999999821186066D);
              paramMotionUpdateEvent.setX(paramMotionUpdateEvent. + 0.20000000298023224D);
              paramMotionUpdateEvent.setZ(paramMotionUpdateEvent. + 0.20000000298023224D);
            } 
          } 
        } else {
          this. = this. = 0;
        }  
    } 
  }
  
  public DojoHelper() {
    super("Dojo Helper", Module.Category.);
    (new Setting[] { (Setting), (Setting), (Setting), (Setting), (Setting), (Setting), (Setting), (Setting) });
  }
  
  @SubscribeEvent
  public void (TickEvent.ClientTickEvent paramClientTickEvent) {
    if (()) {
       = LeftClickEvent.("Challenge: Tenacity");
       = LeftClickEvent.("Challenge: Mastery");
      if (.() && mc.field_71439_g != null && mc.field_71441_e != null && mc.field_71439_g.func_96123_co() != null && LeftClickEvent.("Challenge: Force"))
        for (Entity entity : new ArrayList(mc.field_71441_e.field_72996_f)) {
          if (entity instanceof EntityZombie && ((EntityZombie)entity).func_82169_q(3) != null && ((EntityZombie)entity).func_82169_q(3).func_77973_b() == Items.field_151024_Q) {
            entity.field_70163_u = -100.0D;
            entity.field_70137_T = -100.0D;
          } 
          if (entity instanceof net.minecraft.entity.item.EntityArmorStand && entity.func_145748_c_().func_150260_c().startsWith("§c-")) {
            entity.field_70163_u = -100.0D;
            entity.field_70137_T = -100.0D;
          } 
        }  
    } 
  }
}
