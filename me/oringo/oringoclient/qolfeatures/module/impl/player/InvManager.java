package me.oringo.oringoclient.qolfeatures.module.impl.player;

import com.mojang.realmsclient.gui.ChatFormatting;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import me.oringo.oringoclient.OringoClient;
import me.oringo.oringoclient.events.OringoEvent;
import me.oringo.oringoclient.events.impl.MotionUpdateEvent;
import me.oringo.oringoclient.events.impl.PacketSentEvent;
import me.oringo.oringoclient.events.impl.StepEvent;
import me.oringo.oringoclient.mixins.item.ItemToolAccessor;
import me.oringo.oringoclient.qolfeatures.module.Module;
import me.oringo.oringoclient.qolfeatures.module.impl.combat.KillAura;
import me.oringo.oringoclient.qolfeatures.module.impl.other.HClip;
import me.oringo.oringoclient.qolfeatures.module.impl.other.ResetVL;
import me.oringo.oringoclient.qolfeatures.module.impl.render.HidePlayers;
import me.oringo.oringoclient.qolfeatures.module.impl.skyblock.BoneThrower;
import me.oringo.oringoclient.qolfeatures.module.impl.skyblock.TerminatorAura;
import me.oringo.oringoclient.qolfeatures.module.settings.Setting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.BooleanSetting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.ModeSetting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.NumberSetting;
import me.oringo.oringoclient.utils.EntityUtils;
import me.oringo.oringoclient.utils.MilliTimer;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public class InvManager extends Module {
  public NumberSetting  = new NumberSetting("Pickaxe slot", 0.0D, 0.0D, 9.0D, 1.0D);
  
  public NumberSetting  = new NumberSetting("Sword slot", 0.0D, 0.0D, 9.0D, 1.0D);
  
  public ModeSetting  = new ModeSetting("Trash items", "Skyblock", new String[] { "Skyblock", "Skywars", "Custom" });
  
  public NumberSetting  = new NumberSetting("Axe slot", 0.0D, 0.0D, 9.0D, 1.0D);
  
  public List<String>  = Arrays.asList(new String[] { 
        "Training Weight", "Healing Potion", "Beating Heart", "Premium Flesh", "Mimic Fragment", "Enchanted Rotten Flesh", "Machine Gun Bow", "Enchanted Bone", "Defuse Kit", "Enchanted Ice", 
        "Diamond Atom", "Silent Death", "Cutlass", "Soulstealer Bow", "Sniper Bow", "Optical Lens", "Tripwire Hook", "Button", "Carpet", "Lever", 
        "Journal Entry", "Sign", "Zombie Commander", "Zombie Lord", "Skeleton Master, Skeleton Grunt, Skeleton Lord, Zombie Soldier", "Zombie Knight", "Heavy", "Super Heavy", "Undead", "Bouncy", 
        "Skeletor", "Trap", "Inflatable Jerry" });
  
  public BooleanSetting  = new BooleanSetting("Middle click to drop", false);
  
  public NumberSetting  = new NumberSetting("Delay", 30.0D, 0.0D, 300.0D, 1.0D);
  
  public MilliTimer  = new MilliTimer();
  
  public NumberSetting  = new NumberSetting("Block slot", 0.0D, 0.0D, 9.0D, 1.0D);
  
  public static String[]  = new String[] { "(Right Click)" };
  
  public boolean ;
  
  public NumberSetting  = new NumberSetting("Bow slot", 0.0D, 0.0D, 9.0D, 1.0D);
  
  public BooleanSetting  = new BooleanSetting("Drop trash", true);
  
  public List<String>  = Arrays.asList(new String[] { "Egg", "Snowball", "Poison", "Lava", "Steak", "Enchanting", "Poison" });
  
  public NumberSetting  = new NumberSetting("Gapple slot", 0.0D, 0.0D, 9.0D, 1.0D);
  
  public NumberSetting  = new NumberSetting("Shovel slot", 0.0D, 0.0D, 9.0D, 1.0D);
  
  public ModeSetting  = new ModeSetting("Mode", "Inv open", new String[] { "Inv open", "Always" });
  
  public static List<String>  = new ArrayList<>();
  
  public BooleanSetting  = new BooleanSetting("Auto Armor", false);
  
  public void (int paramInt) {
    this..();
    KillAura..();
    mc.field_71442_b.func_78753_a(mc.field_71439_g.field_71069_bz.field_75152_c, paramInt, 0, 1, (EntityPlayer)mc.field_71439_g);
  }
  
  public boolean (ItemStack paramItemStack, int paramInt) {
    for (byte b = 9; b < 45; b++) {
      if (mc.field_71439_g.field_71069_bz.func_75139_a(b).func_75216_d()) {
        ItemStack itemStack = mc.field_71439_g.field_71069_bz.func_75139_a(b).func_75211_c();
        if (itemStack.func_77973_b() instanceof net.minecraft.item.ItemBow && ((StepEvent.(itemStack) > StepEvent.(paramItemStack) && paramInt == ((int)this..())) || (StepEvent.(itemStack) >= StepEvent.(paramItemStack) && paramInt != ((int)this..()))) && b != paramInt)
          return false; 
      } 
    } 
    return true;
  }
  
  public static Vec3 (float paramFloat1, float paramFloat2) {
    float f1 = MathHelper.func_76134_b(-paramFloat1 * 0.017453292F - 3.1415927F);
    float f2 = MathHelper.func_76126_a(-paramFloat1 * 0.017453292F - 3.1415927F);
    float f3 = -MathHelper.func_76134_b(-paramFloat2 * 0.017453292F);
    float f4 = MathHelper.func_76126_a(-paramFloat2 * 0.017453292F);
    return new Vec3((f2 * f3), f4, (f1 * f3));
  }
  
  public static void () {
    GL11.glEnable(32823);
    GlStateManager.func_179088_q();
    GlStateManager.func_179136_a(1.0F, -1000000.0F);
  }
  
  public boolean (ItemStack paramItemStack, int paramInt) {
    for (byte b = 9; b < 45; b++) {
      if (paramInt != b && mc.field_71439_g.field_71069_bz.func_75139_a(b).func_75216_d()) {
        ItemStack itemStack = mc.field_71439_g.field_71069_bz.func_75139_a(b).func_75211_c();
        if (itemStack.func_77973_b() instanceof ItemBlock && ((ItemBlock)itemStack.func_77973_b()).field_150939_a.func_149730_j() && (itemStack.field_77994_a > paramItemStack.field_77994_a || (itemStack.field_77994_a == paramItemStack.field_77994_a && b == ((int)this..()))))
          return false; 
      } 
    } 
    return true;
  }
  
  public void (int paramInt) {
    this..();
    KillAura..();
    mc.field_71442_b.func_78753_a(mc.field_71439_g.field_71069_bz.field_75152_c, paramInt, 1, 4, (EntityPlayer)mc.field_71439_g);
  }
  
  public InvManager() {
    super("Inventory Manager", 0, Module.Category.);
    (new Setting[] { 
          (Setting)this., (Setting)this., (Setting)this., (Setting)this., (Setting)this., (Setting)this., (Setting)this., (Setting)this., (Setting)this., (Setting)this., 
          (Setting)this., (Setting)this. });
  }
  
  public void () {
    for (byte b = 9; b < 45; b++) {
      if (mc.field_71439_g.field_71069_bz.func_75139_a(b).func_75216_d() && ()) {
        ItemStack itemStack = mc.field_71439_g.field_71069_bz.func_75139_a(b).func_75211_c();
        if (itemStack.func_77973_b() instanceof net.minecraft.item.ItemSword && !ResetVL.(itemStack))
          if ((itemStack, b)) {
            if (((int)this..()) != b)
              (b, (int)this..() - 1); 
          } else {
            (b);
          }  
      } 
    } 
  }
  
  public static boolean () {
    Block block = HClip.mc.field_71441_e.func_180495_p(new BlockPos(HClip.mc.field_71439_g.func_174791_d().func_72441_c(0.0D, 0.35D, 0.0D))).func_177230_c();
    return (block instanceof net.minecraft.block.BlockSkull || block instanceof net.minecraft.block.BlockFence || block instanceof net.minecraft.block.BlockFenceGate || block instanceof net.minecraft.block.BlockWall);
  }
  
  public boolean (ItemStack paramItemStack, int paramInt) {
    if (!(paramItemStack.func_77973_b() instanceof net.minecraft.item.ItemTool))
      return false; 
    for (byte b = 9; b < 45; b++) {
      if (mc.field_71439_g.field_71069_bz.func_75139_a(b).func_75216_d()) {
        ItemStack itemStack = mc.field_71439_g.field_71069_bz.func_75139_a(b).func_75211_c();
        if ((itemStack) != 0)
          if (paramItemStack.func_77973_b() instanceof net.minecraft.item.ItemAxe && itemStack.func_77973_b() instanceof net.minecraft.item.ItemAxe) {
            if ((TerminatorAura.(itemStack) > TerminatorAura.(paramItemStack) && paramInt == ((int)this..())) || (paramInt != ((int)this..()) && OringoEvent.(itemStack) >= OringoEvent.(paramItemStack) && paramInt != b))
              return false; 
          } else if (paramItemStack.func_77973_b() instanceof net.minecraft.item.ItemPickaxe && itemStack.func_77973_b() instanceof net.minecraft.item.ItemPickaxe) {
            if ((TerminatorAura.(itemStack) > TerminatorAura.(paramItemStack) && paramInt == ((int)this..())) || (paramInt != ((int)this..()) && OringoEvent.(itemStack) >= OringoEvent.(paramItemStack) && paramInt != b))
              return false; 
          } else if (paramItemStack.func_77973_b() instanceof net.minecraft.item.ItemSpade && itemStack.func_77973_b() instanceof net.minecraft.item.ItemSpade && ((TerminatorAura.(itemStack) > TerminatorAura.(paramItemStack) && paramInt == ((int)this..())) || (paramInt != ((int)this..()) && OringoEvent.(itemStack) >= OringoEvent.(paramItemStack) && paramInt != b))) {
            return false;
          }  
      } 
    } 
    return true;
  }
  
  public void () {
    byte b;
    for (b = 5; b < 9; b++) {
      if (mc.field_71439_g.field_71069_bz.func_75139_a(b).func_75216_d() && ()) {
        ItemStack itemStack = mc.field_71439_g.field_71069_bz.func_75139_a(b).func_75211_c();
        if (!HidePlayers.(itemStack, b))
          (b); 
      } 
    } 
    for (b = 9; b < 45; b++) {
      if (mc.field_71439_g.field_71069_bz.func_75139_a(b).func_75216_d() && ()) {
        ItemStack itemStack = mc.field_71439_g.field_71069_bz.func_75139_a(b).func_75211_c();
        if (itemStack.func_77973_b() instanceof net.minecraft.item.ItemArmor && !ResetVL.(itemStack))
          if (HidePlayers.(itemStack, b)) {
            (b);
          } else {
            (b);
          }  
      } 
    } 
  }
  
  @SubscribeEvent
  public void (ItemTooltipEvent paramItemTooltipEvent) {
    if (Mouse.isButtonDown(2) && mc.field_71462_r instanceof net.minecraft.client.gui.inventory.GuiInventory && this..()) {
      if (!this.) {
        this. = true;
        String str = ChatFormatting.stripFormatting(paramItemTooltipEvent.itemStack.func_82833_r());
        if (.contains(str)) {
          .remove(str);
          NoRotate.("Oringo Client", String.valueOf((new StringBuilder()).append("Removed ").append(str).append(" from custom drop list")), 2000);
        } else {
          .add(str);
          NoRotate.("Oringo Client", String.valueOf((new StringBuilder()).append("Added ").append(ChatFormatting.AQUA).append(str).append(ChatFormatting.RESET).append(" to custom drop list")), 2000);
        } 
        NoFall.();
      } 
    } else {
      this. = false;
    } 
  }
  
  public void (int paramInt1, int paramInt2) {
    this..();
    KillAura..();
    mc.field_71442_b.func_78753_a(mc.field_71439_g.field_71069_bz.field_75152_c, paramInt1, paramInt2, 2, (EntityPlayer)mc.field_71439_g);
  }
  
  public void () {
    for (Slot slot : mc.field_71439_g.field_71069_bz.field_75151_b) {
      if (slot.func_75216_d() && ()) {
        if (this..().equals("Custom")) {
          if (.contains(ChatFormatting.stripFormatting(slot.func_75211_c().func_82833_r())))
            (slot.field_75222_d); 
          continue;
        } 
        if (this..().equals("Skyblock") && this..stream().anyMatch(slot::)) {
          (slot.field_75222_d);
          continue;
        } 
        if (this..().equals("Skywars") && this..stream().anyMatch(slot::))
          (slot.field_75222_d); 
      } 
    } 
  }
  
  @SubscribeEvent
  public void (MotionUpdateEvent.Pre paramPre) {
    if (() && Module.() && (mc.field_71462_r instanceof net.minecraft.client.gui.inventory.GuiInventory || this..("Always")) && !OringoClient..()) {
      if (this..())
        (); 
      if (this..())
        (); 
      if (this..() != 0.0D)
        (); 
      ();
      if (this..() != 0.0D)
        (); 
      if (this..() != 0.0D)
        (); 
    } else {
      this..();
    } 
  }
  
  public static Vec3 (Vec3 paramVec3, AxisAlignedBB paramAxisAlignedBB) {
    return new Vec3(Velocity.(paramAxisAlignedBB.field_72340_a, paramAxisAlignedBB.field_72336_d, paramVec3.field_72450_a), Velocity.(paramAxisAlignedBB.field_72338_b, paramAxisAlignedBB.field_72337_e, paramVec3.field_72448_b), Velocity.(paramAxisAlignedBB.field_72339_c, paramAxisAlignedBB.field_72334_f, paramVec3.field_72449_c));
  }
  
  public void () {
    for (byte b = 9; b < 45; b++) {
      if (mc.field_71439_g.field_71069_bz.func_75139_a(b).func_75216_d() && ()) {
        ItemStack itemStack = mc.field_71439_g.field_71069_bz.func_75139_a(b).func_75211_c();
        if (itemStack.func_77973_b() instanceof net.minecraft.item.ItemBow && !ResetVL.(itemStack))
          if ((itemStack, b)) {
            if (((int)this..()) != b)
              (b, (int)this..() - 1); 
          } else {
            (b);
          }  
      } 
    } 
  }
  
  public EntityPlayer (double paramDouble) {
    Objects.requireNonNull(mc.field_71439_g);
    List<EntityPlayer> list = (List)mc.field_71441_e.field_73010_i.stream().filter(paramDouble::).sorted(Comparator.comparingDouble(mc.field_71439_g::func_70032_d)).collect(Collectors.toList());
    return !list.isEmpty() ? list.get(0) : null;
  }
  
  @SubscribeEvent
  public void (PacketSentEvent paramPacketSentEvent) {
    if (paramPacketSentEvent. instanceof net.minecraft.network.play.client.C02PacketUseEntity || paramPacketSentEvent. instanceof net.minecraft.network.play.client.C08PacketPlayerBlockPlacement)
      this..(); 
  }
  
  public boolean () {
    return this..((long)this..());
  }
  
  public int (ItemStack paramItemStack) {
    if (paramItemStack == null || !(paramItemStack.func_77973_b() instanceof net.minecraft.item.ItemTool))
      return 0; 
    switch (((ItemToolAccessor)paramItemStack.func_77973_b()).getToolClass()) {
      case "pickaxe":
        return (int)this..();
      case "axe":
        return (int)this..();
      case "shovel":
        return (int)this..();
    } 
    return 0;
  }
  
  public void () {
    for (byte b = 9; b < 45; b++) {
      if (mc.field_71439_g.field_71069_bz.func_75139_a(b).func_75216_d() && ()) {
        ItemStack itemStack = mc.field_71439_g.field_71069_bz.func_75139_a(b).func_75211_c();
        if (itemStack.func_77973_b() instanceof ItemBlock && ((ItemBlock)itemStack.func_77973_b()).field_150939_a.func_149730_j() && !ResetVL.(itemStack) && (itemStack, b)) {
          if (((int)this..()) != b)
            (b, (int)this..() - 1); 
          return;
        } 
      } 
    } 
  }
  
  public void () {
    for (byte b = 9; b < 45; b++) {
      if (mc.field_71439_g.field_71069_bz.func_75139_a(b).func_75216_d() && ()) {
        ItemStack itemStack = mc.field_71439_g.field_71069_bz.func_75139_a(b).func_75211_c();
        if (itemStack.func_77973_b() instanceof net.minecraft.item.ItemTool && (itemStack) != 0 && !ResetVL.(itemStack))
          if ((itemStack, b)) {
            if (((itemStack)) != b)
              (b, (itemStack) - 1); 
          } else {
            (b);
          }  
      } 
    } 
  }
  
  public int (int paramInt) {
    return paramInt + 35;
  }
  
  public boolean (ItemStack paramItemStack, int paramInt) {
    if (!(paramItemStack.func_77973_b() instanceof net.minecraft.item.ItemSword))
      return false; 
    for (byte b = 9; b < 45; b++) {
      if (mc.field_71439_g.field_71069_bz.func_75139_a(b).func_75216_d()) {
        ItemStack itemStack = mc.field_71439_g.field_71069_bz.func_75139_a(b).func_75211_c();
        if (itemStack.func_77973_b() instanceof net.minecraft.item.ItemSword && ((OringoEvent.(itemStack) > OringoEvent.(paramItemStack) && paramInt == ((int)this..())) || (paramInt != ((int)this..()) && OringoEvent.(itemStack) >= OringoEvent.(paramItemStack) && paramInt != b)))
          return false; 
      } 
    } 
    return true;
  }
}
