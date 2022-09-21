package me.oringo.oringoclient.qolfeatures.module.impl.macro;

import com.mojang.realmsclient.gui.ChatFormatting;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;
import me.oringo.oringoclient.OringoClient;
import me.oringo.oringoclient.events.impl.PacketReceivedEvent;
import me.oringo.oringoclient.events.impl.RenderChestEvent;
import me.oringo.oringoclient.lunarspoof.LunarClient;
import me.oringo.oringoclient.qolfeatures.module.Module;
import me.oringo.oringoclient.qolfeatures.module.impl.movement.Sneak;
import me.oringo.oringoclient.qolfeatures.module.impl.movement.Step;
import me.oringo.oringoclient.qolfeatures.module.impl.other.CakeNuker;
import me.oringo.oringoclient.qolfeatures.module.impl.other.Disabler;
import me.oringo.oringoclient.qolfeatures.module.impl.player.ChestStealer;
import me.oringo.oringoclient.qolfeatures.module.impl.skyblock.CrimsonQOL;
import me.oringo.oringoclient.qolfeatures.module.settings.Setting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.BooleanSetting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.ModeSetting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.NumberSetting;
import me.oringo.oringoclient.utils.EntityUtils;
import me.oringo.oringoclient.utils.Rotation;
import me.oringo.oringoclient.utils.RotationUtils;
import net.minecraft.block.BlockColored;
import net.minecraft.block.BlockStone;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.multiplayer.GuiConnecting;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.renderer.DestroyBlockProgress;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.Slot;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.opengl.GL11;

public class MithrilMacro extends Module {
  public int  = 0;
  
  public NumberSetting  = new NumberSetting("Head movements", 5.0D, 0.0D, 50.0D, 1.0D);
  
  public BlockPos  = null;
  
  public NumberSetting  = new NumberSetting("Accuracy", 5.0D, 3.0D, 10.0D, 1.0D);
  
  public NumberSetting  = new NumberSetting("Max break time", 160.0D, 40.0D, 400.0D, 1.0D);
  
  public int  = -1;
  
  public int  = 0;
  
  public int  = 0;
  
  public BooleanSetting  = new BooleanSetting("Sneak", false);
  
  public Vec3  = null;
  
  public BlockPos  = null;
  
  public NumberSetting  = new NumberSetting("Rotations", 10.0D, 1.0D, 20.0D, 1.0D);
  
  public BooleanSetting  = new BooleanSetting("Prioritize titanium", true);
  
  public BooleanSetting  = new BooleanSetting("Auto ability", true);
  
  public ModeSetting  = new ModeSetting("Target", "Clay", new String[] { "Clay", "Prismarine", "Wool", "Blue", "Gold" });
  
  public BooleanSetting  = new BooleanSetting("Drill Refuel", false);
  
  public NumberSetting  = new NumberSetting("Walking ticks", 5.0D, 0.0D, 60.0D, 1.0D);
  
  public ArrayList<Float>  = new ArrayList<>();
  
  public int  = 0;
  
  public boolean  = false;
  
  public ArrayList<Float>  = new ArrayList<>();
  
  public BooleanSetting  = new BooleanSetting("Mine under", false);
  
  public NumberSetting  = new NumberSetting("Auto leave", 100.0D, 0.0D, 200.0D, 1.0D);
  
  public Vec3  = null;
  
  public NumberSetting  = new NumberSetting("Walking %", 0.1D, 0.0D, 5.0D, 0.1D);
  
  public int  = 0;
  
  public int  = 0;
  
  public EntityArmorStand ;
  
  public NumberSetting  = new NumberSetting("Block skip progress", 0.9D, 0.0D, 1.0D, 0.1D);
  
  public int  = -1;
  
  @SubscribeEvent(receiveCanceled = true)
  public void (PacketReceivedEvent paramPacketReceivedEvent) {
    if (paramPacketReceivedEvent. instanceof net.minecraft.network.play.server.S08PacketPlayerPosLook && ()) {
      this. = 200;
      this. = null;
      KeyBinding.func_74510_a((Minecraft.func_71410_x()).field_71474_y.field_74312_F.func_151463_i(), false);
      KeyBinding.func_74510_a((Minecraft.func_71410_x()).field_71474_y.field_74311_E.func_151463_i(), false);
      for (int i : new int[] { mc.field_71474_y.field_74351_w.func_151463_i(), mc.field_71474_y.field_74370_x.func_151463_i(), mc.field_71474_y.field_74368_y.func_151463_i(), mc.field_71474_y.field_74366_z.func_151463_i() })
        KeyBinding.func_74510_a(i, false); 
    } 
  }
  
  public void () {
    KeyBinding.func_74510_a((Minecraft.func_71410_x()).field_71474_y.field_74312_F.func_151463_i(), false);
    KeyBinding.func_74510_a((Minecraft.func_71410_x()).field_71474_y.field_74311_E.func_151463_i(), false);
  }
  
  public double (BlockPos paramBlockPos1, BlockPos paramBlockPos2, double paramDouble) {
    double d1 = (paramBlockPos1.func_177958_n() - paramBlockPos2.func_177958_n());
    double d2 = (paramBlockPos1.func_177956_o() - paramBlockPos2.func_177956_o()) * paramDouble;
    double d3 = (paramBlockPos1.func_177952_p() - paramBlockPos2.func_177952_p());
    return Math.sqrt(d1 * d1 + d2 * d2 + d3 * d3);
  }
  
  public boolean (BlockPos paramBlockPos) {
    IBlockState iBlockState = mc.field_71441_e.func_180495_p(paramBlockPos);
    if ((paramBlockPos))
      return true; 
    switch (this..()) {
      case "Clay":
        return (iBlockState.func_177230_c().equals(Blocks.field_150406_ce) || (iBlockState.func_177230_c().equals(Blocks.field_150325_L) && ((EnumDyeColor)iBlockState.func_177229_b((IProperty)BlockColored.field_176581_a)).equals(EnumDyeColor.GRAY)));
      case "Prismarine":
        return iBlockState.func_177230_c().equals(Blocks.field_180397_cI);
      case "Wool":
        return (iBlockState.func_177230_c().equals(Blocks.field_150325_L) && ((EnumDyeColor)iBlockState.func_177229_b((IProperty)BlockColored.field_176581_a)).equals(EnumDyeColor.LIGHT_BLUE));
      case "Blue":
        return ((iBlockState.func_177230_c().equals(Blocks.field_150325_L) && ((EnumDyeColor)iBlockState.func_177229_b((IProperty)BlockColored.field_176581_a)).equals(EnumDyeColor.LIGHT_BLUE)) || iBlockState.func_177230_c().equals(Blocks.field_180397_cI));
      case "Gold":
        return iBlockState.func_177230_c().equals(Blocks.field_150340_R);
    } 
    return false;
  }
  
  @SubscribeEvent
  public void (TickEvent.ClientTickEvent paramClientTickEvent) {
    if (mc.field_71462_r instanceof net.minecraft.client.gui.GuiDisconnected && this. < 0 && ()) {
      this. = 250;
      (false);
    } 
    if (this.-- == 0) {
      mc.func_147108_a((GuiScreen)new GuiConnecting((GuiScreen)new GuiMainMenu(), mc, new ServerData("Hypixel", "play.Hypixel.net", false)));
      (new Thread(this::lambda$reconnect$2)).start();
    } 
  }
  
  @SubscribeEvent
  public void (TickEvent.ClientTickEvent paramClientTickEvent) {
    this.--;
    if (() && !(mc.field_71462_r instanceof net.minecraft.client.gui.inventory.GuiContainer) && !(mc.field_71462_r instanceof net.minecraft.client.gui.inventory.GuiEditSign) && this. < 1) {
      this.++;
      if (mc.field_71439_g != null && mc.field_71439_g.func_70694_bm() != null && mc.field_71439_g.func_70694_bm().func_77973_b() instanceof net.minecraft.item.ItemMap) {
        (false);
        mc.field_71439_g.func_71165_d("/l");
      } 
      if (mc.field_71441_e != null) {
        if (this. == null && this..()) {
          for (Entity entity : mc.field_71441_e.func_72910_y().stream().filter(MithrilMacro::).collect(Collectors.toList())) {
            if (entity.func_145748_c_().func_150254_d().contains("§e§lDRILL MECHANIC§r")) {
              OringoClient.. = (EntityArmorStand)entity;
              Sneak.("Mechanic");
              return;
            } 
          } 
          (false);
          OringoClient..(this::lambda$onTick$6, false);
          return;
        } 
        if (!(mc.field_71439_g.func_70694_bm()))
          for (byte b = 0; b < 9; b++) {
            if ((mc.field_71439_g.field_71071_by.func_70301_a(b)))
              LunarClient.(b); 
          }  
        if (this.-- <= 0) {
          int[] arrayOfInt = { mc.field_71474_y.field_74351_w.func_151463_i(), mc.field_71474_y.field_74370_x.func_151463_i(), mc.field_71474_y.field_74368_y.func_151463_i(), mc.field_71474_y.field_74366_z.func_151463_i(), mc.field_71474_y.field_74370_x.func_151463_i(), mc.field_71474_y.field_74368_y.func_151463_i(), mc.field_71474_y.field_74366_z.func_151463_i(), mc.field_71474_y.field_74368_y.func_151463_i(), mc.field_71474_y.field_74368_y.func_151463_i() };
          if (this. != -1) {
            KeyBinding.func_74510_a(this., false);
            KeyBinding.func_74510_a((Minecraft.func_71410_x()).field_71474_y.field_74311_E.func_151463_i(), this..());
          } 
          if ((new Random()).nextFloat() < this..() / 100.0D) {
            this. = arrayOfInt[(new Random()).nextInt(arrayOfInt.length)];
            KeyBinding.func_74510_a((Minecraft.func_71410_x()).field_71474_y.field_74311_E.func_151463_i(), true);
            KeyBinding.func_74510_a(this., true);
            this. = (int)this..();
          } 
        } else {
          KeyBinding.func_74510_a(this., true);
          KeyBinding.func_74510_a(mc.field_71474_y.field_74311_E.func_151463_i(), true);
        } 
        if (mc.field_71476_x != null && mc.field_71476_x.field_72313_a == MovingObjectPosition.MovingObjectType.ENTITY) {
          Entity entity = mc.field_71476_x.field_72308_g;
          if (entity instanceof EntityPlayer && !EntityUtils.isTeam(entity)) {
            Step.();
            this. = 5;
            return;
          } 
        } 
        if (mc.field_71441_e.field_73010_i.stream().anyMatch(MithrilMacro::)) {
          this.++;
        } else {
          this. = 0;
        } 
        boolean bool = RenderChestEvent.("Dwarven Mines");
        if ((this..() <= this. && this..() != 0.0D) || !bool) {
          (false);
          if (OringoClient..())
            OringoClient..(this::lambda$onTick$8, false); 
          this. = 0;
          Sneak.(!bool ? "Not in dwarven" : String.valueOf((new StringBuilder()).append("You have been seen by ").append(((EntityPlayer)mc.field_71441_e.field_73010_i.stream().filter(MithrilMacro::).findFirst().get()).func_70005_c_())));
          return;
        } 
        if (this. == null) {
          if (!())
            Sneak.("No possible target found"); 
          return;
        } 
        if (mc.field_71476_x != null && mc.field_71476_x.field_72313_a == MovingObjectPosition.MovingObjectType.ENTITY) {
          if (this.++ == 40) {
            (false);
            if (OringoClient..())
              OringoClient..(this::lambda$onTick$10, false); 
            return;
          } 
        } else {
          this. = 0;
        } 
        KeyBinding.func_74510_a((Minecraft.func_71410_x()).field_71474_y.field_74312_F.func_151463_i(), true);
        if (this..() || this. != 0)
          KeyBinding.func_74510_a((Minecraft.func_71410_x()).field_71474_y.field_74311_E.func_151463_i(), true); 
        if (mc.field_71476_x.field_72313_a == MovingObjectPosition.MovingObjectType.BLOCK && mc.field_71462_r != null && !(mc.field_71462_r instanceof net.minecraft.client.gui.inventory.GuiContainer) && this. % 2 == 0)
          Step.(); 
        if (!this..isEmpty() && (this. || !(this.))) {
          mc.field_71439_g.field_70177_z = ((Float)this..get(0)).floatValue();
          mc.field_71439_g.field_70125_A = ((Float)this..get(0)).floatValue();
          this..remove(0);
          this..remove(0);
          if (this..isEmpty() && (this.) && this..() != 0.0D) {
            this. = false;
            Vec3 vec3 = this.;
            this. = (this.);
            this. = this.;
            (false);
            this. = vec3;
            return;
          } 
          if (this..() == 0.0D)
            this. = null; 
          if (this.)
            return; 
        } 
        if (mc.field_71441_e.func_180495_p(this.).func_177230_c().equals(Blocks.field_150357_h)) {
          if (!());
          return;
        } 
        if (mc.field_71476_x.field_72313_a == MovingObjectPosition.MovingObjectType.BLOCK) {
          BlockPos blockPos = mc.field_71476_x.func_178782_a();
          if (!blockPos.equals(this.)) {
            if (!());
            return;
          } 
        } else {
          if (!());
          return;
        } 
        if (this..() != 0.0D && !(this.) && CakeNuker.().values().stream().anyMatch(this::lambda$onTick$11) && CakeNuker.().values().stream().anyMatch(this::lambda$onTick$12))
          (); 
        if (this.++ == this..()) {
          Sneak.("Mining one block took too long");
          ();
        } 
      } 
    } 
  }
  
  public void (boolean paramBoolean) {
    Vec3 vec3 = mc.field_71439_g.func_70040_Z().func_178787_e(mc.field_71439_g.func_174824_e(0.0F));
    if (!this..isEmpty()) {
      this..clear();
      this..clear();
    } 
    double d = (this..() + 1.0D) * (paramBoolean ? 1.0D : this..());
    for (byte b = 0; b < d; b++) {
      Vec3 vec31 = new Vec3(vec3.field_72450_a + (this..field_72450_a - vec3.field_72450_a) / d * b, vec3.field_72448_b + (this..field_72448_b - vec3.field_72448_b) / d * b, vec3.field_72449_c + (this..field_72449_c - vec3.field_72449_c) / d * b);
      Rotation rotation = RotationUtils.(vec31);
      this..add(Float.valueOf(rotation.()));
      this..add(Float.valueOf(rotation.()));
    } 
    this. = paramBoolean;
  }
  
  public boolean () {
    // Byte code:
    //   0: new java/util/ArrayList
    //   3: dup
    //   4: invokespecial <init> : ()V
    //   7: astore_1
    //   8: bipush #-5
    //   10: istore_2
    //   11: iload_2
    //   12: bipush #6
    //   14: if_icmpge -> 104
    //   17: bipush #-5
    //   19: istore_3
    //   20: iload_3
    //   21: bipush #6
    //   23: if_icmpge -> 98
    //   26: bipush #-5
    //   28: istore #4
    //   30: iload #4
    //   32: bipush #6
    //   34: if_icmpge -> 92
    //   37: aload_1
    //   38: new net/minecraft/util/BlockPos
    //   41: dup
    //   42: getstatic me/oringo/oringoclient/qolfeatures/module/impl/macro/MithrilMacro.mc : Lnet/minecraft/client/Minecraft;
    //   45: getfield field_71439_g : Lnet/minecraft/client/entity/EntityPlayerSP;
    //   48: getfield field_70165_t : D
    //   51: iload_2
    //   52: i2d
    //   53: dadd
    //   54: getstatic me/oringo/oringoclient/qolfeatures/module/impl/macro/MithrilMacro.mc : Lnet/minecraft/client/Minecraft;
    //   57: getfield field_71439_g : Lnet/minecraft/client/entity/EntityPlayerSP;
    //   60: getfield field_70163_u : D
    //   63: iload_3
    //   64: i2d
    //   65: dadd
    //   66: getstatic me/oringo/oringoclient/qolfeatures/module/impl/macro/MithrilMacro.mc : Lnet/minecraft/client/Minecraft;
    //   69: getfield field_71439_g : Lnet/minecraft/client/entity/EntityPlayerSP;
    //   72: getfield field_70161_v : D
    //   75: iload #4
    //   77: i2d
    //   78: dadd
    //   79: invokespecial <init> : (DDD)V
    //   82: invokevirtual add : (Ljava/lang/Object;)Z
    //   85: pop
    //   86: iinc #4, 1
    //   89: goto -> 30
    //   92: iinc #3, 1
    //   95: goto -> 20
    //   98: iinc #2, 1
    //   101: goto -> 11
    //   104: aload_0
    //   105: getfield  : Lnet/minecraft/util/BlockPos;
    //   108: ifnull -> 118
    //   111: aload_0
    //   112: getfield  : Lnet/minecraft/util/BlockPos;
    //   115: goto -> 127
    //   118: getstatic me/oringo/oringoclient/qolfeatures/module/impl/macro/MithrilMacro.mc : Lnet/minecraft/client/Minecraft;
    //   121: getfield field_71439_g : Lnet/minecraft/client/entity/EntityPlayerSP;
    //   124: invokevirtual func_180425_c : ()Lnet/minecraft/util/BlockPos;
    //   127: astore_2
    //   128: aload_1
    //   129: invokevirtual stream : ()Ljava/util/stream/Stream;
    //   132: aload_0
    //   133: <illegal opcode> test : (Lme/oringo/oringoclient/qolfeatures/module/impl/macro/MithrilMacro;)Ljava/util/function/Predicate;
    //   138: invokeinterface filter : (Ljava/util/function/Predicate;)Ljava/util/stream/Stream;
    //   143: aload_0
    //   144: <illegal opcode> test : (Lme/oringo/oringoclient/qolfeatures/module/impl/macro/MithrilMacro;)Ljava/util/function/Predicate;
    //   149: invokeinterface filter : (Ljava/util/function/Predicate;)Ljava/util/stream/Stream;
    //   154: <illegal opcode> test : ()Ljava/util/function/Predicate;
    //   159: invokeinterface filter : (Ljava/util/function/Predicate;)Ljava/util/stream/Stream;
    //   164: aload_0
    //   165: <illegal opcode> test : (Lme/oringo/oringoclient/qolfeatures/module/impl/macro/MithrilMacro;)Ljava/util/function/Predicate;
    //   170: invokeinterface filter : (Ljava/util/function/Predicate;)Ljava/util/stream/Stream;
    //   175: aload_0
    //   176: aload_2
    //   177: <illegal opcode> applyAsDouble : (Lme/oringo/oringoclient/qolfeatures/module/impl/macro/MithrilMacro;Lnet/minecraft/util/BlockPos;)Ljava/util/function/ToDoubleFunction;
    //   182: invokestatic comparingDouble : (Ljava/util/function/ToDoubleFunction;)Ljava/util/Comparator;
    //   185: invokeinterface min : (Ljava/util/Comparator;)Ljava/util/Optional;
    //   190: astore_3
    //   191: aload_3
    //   192: invokevirtual isPresent : ()Z
    //   195: ifeq -> 237
    //   198: aload_0
    //   199: aload_3
    //   200: invokevirtual get : ()Ljava/lang/Object;
    //   203: checkcast net/minecraft/util/BlockPos
    //   206: putfield  : Lnet/minecraft/util/BlockPos;
    //   209: aload_0
    //   210: aconst_null
    //   211: putfield  : Lnet/minecraft/util/Vec3;
    //   214: aload_0
    //   215: aload_0
    //   216: aload_3
    //   217: invokevirtual get : ()Ljava/lang/Object;
    //   220: checkcast net/minecraft/util/BlockPos
    //   223: invokespecial  : (Lnet/minecraft/util/BlockPos;)Lnet/minecraft/util/Vec3;
    //   226: putfield  : Lnet/minecraft/util/Vec3;
    //   229: aload_0
    //   230: iconst_1
    //   231: invokespecial  : (Z)V
    //   234: goto -> 343
    //   237: aload_1
    //   238: invokevirtual stream : ()Ljava/util/stream/Stream;
    //   241: aload_0
    //   242: <illegal opcode> test : (Lme/oringo/oringoclient/qolfeatures/module/impl/macro/MithrilMacro;)Ljava/util/function/Predicate;
    //   247: invokeinterface filter : (Ljava/util/function/Predicate;)Ljava/util/stream/Stream;
    //   252: aload_0
    //   253: <illegal opcode> test : (Lme/oringo/oringoclient/qolfeatures/module/impl/macro/MithrilMacro;)Ljava/util/function/Predicate;
    //   258: invokeinterface filter : (Ljava/util/function/Predicate;)Ljava/util/stream/Stream;
    //   263: <illegal opcode> test : ()Ljava/util/function/Predicate;
    //   268: invokeinterface filter : (Ljava/util/function/Predicate;)Ljava/util/stream/Stream;
    //   273: aload_0
    //   274: <illegal opcode> test : (Lme/oringo/oringoclient/qolfeatures/module/impl/macro/MithrilMacro;)Ljava/util/function/Predicate;
    //   279: invokeinterface filter : (Ljava/util/function/Predicate;)Ljava/util/stream/Stream;
    //   284: aload_0
    //   285: aload_2
    //   286: <illegal opcode> applyAsDouble : (Lme/oringo/oringoclient/qolfeatures/module/impl/macro/MithrilMacro;Lnet/minecraft/util/BlockPos;)Ljava/util/function/ToDoubleFunction;
    //   291: invokestatic comparingDouble : (Ljava/util/function/ToDoubleFunction;)Ljava/util/Comparator;
    //   294: invokeinterface min : (Ljava/util/Comparator;)Ljava/util/Optional;
    //   299: astore_3
    //   300: aload_3
    //   301: invokevirtual isPresent : ()Z
    //   304: ifeq -> 343
    //   307: aload_0
    //   308: aload_3
    //   309: invokevirtual get : ()Ljava/lang/Object;
    //   312: checkcast net/minecraft/util/BlockPos
    //   315: putfield  : Lnet/minecraft/util/BlockPos;
    //   318: aload_0
    //   319: aconst_null
    //   320: putfield  : Lnet/minecraft/util/Vec3;
    //   323: aload_0
    //   324: aload_0
    //   325: aload_3
    //   326: invokevirtual get : ()Ljava/lang/Object;
    //   329: checkcast net/minecraft/util/BlockPos
    //   332: invokespecial  : (Lnet/minecraft/util/BlockPos;)Lnet/minecraft/util/Vec3;
    //   335: putfield  : Lnet/minecraft/util/Vec3;
    //   338: aload_0
    //   339: iconst_1
    //   340: invokespecial  : (Z)V
    //   343: aload_0
    //   344: iconst_0
    //   345: putfield  : I
    //   348: aload_3
    //   349: invokevirtual isPresent : ()Z
    //   352: ireturn
  }
  
  public boolean (ItemStack paramItemStack) {
    return (paramItemStack != null && (paramItemStack.func_82833_r().contains("Pickaxe") || paramItemStack.func_77973_b() == Items.field_179562_cC || paramItemStack.func_82833_r().contains("Gauntlet")));
  }
  
  public Vec3 (BlockPos paramBlockPos) {
    ArrayList<Vec3> arrayList = new ArrayList();
    for (byte b = 0; b < this..(); b++) {
      for (byte b1 = 0; b1 < this..(); b1++) {
        for (byte b2 = 0; b2 < this..(); b2++) {
          Vec3 vec3 = new Vec3(paramBlockPos.func_177958_n() + b / this..(), paramBlockPos.func_177956_o() + b1 / this..(), paramBlockPos.func_177952_p() + b2 / this..());
          this. = new BlockPos(vec3.field_72450_a, vec3.field_72448_b, vec3.field_72449_c);
          MovingObjectPosition movingObjectPosition = mc.field_71441_e.func_147447_a(mc.field_71439_g.func_174824_e(0.0F), vec3, true, false, true);
          if (movingObjectPosition != null) {
            BlockPos blockPos = movingObjectPosition.func_178782_a();
            if (blockPos.equals(this.) && mc.field_71439_g.func_70011_f(vec3.field_72450_a, vec3.field_72448_b - mc.field_71439_g.func_70047_e(), vec3.field_72449_c) < 4.5D && (this..() || Math.abs(mc.field_71439_g.field_70163_u - vec3.field_72448_b) > 1.3D))
              arrayList.add(vec3); 
          } 
        } 
      } 
    } 
    return arrayList.isEmpty() ? null : arrayList.get((new Random()).nextInt(arrayList.size()));
  }
  
  @SubscribeEvent
  public void (WorldEvent.Load paramLoad) {
    this. = null;
    if (()) {
      (false);
      if (OringoClient..())
        OringoClient..(this::lambda$onLoad$0, false); 
    } 
  }
  
  @SubscribeEvent
  public void (ClientChatReceivedEvent paramClientChatReceivedEvent) {
    if (!())
      return; 
    String str = paramClientChatReceivedEvent.message.func_150254_d();
    if (this..() && ChatFormatting.stripFormatting(str).startsWith("Your") && ChatFormatting.stripFormatting(str).endsWith("Refuel it by talking to a Drill Mechanic!") && this. != null) {
      (false);
      (new Thread(this::lambda$onChat$3)).start();
    } 
    if (ChatFormatting.stripFormatting(paramClientChatReceivedEvent.message.func_150260_c()).equals("Mining Speed Boost is now available!") && this..() && mc.field_71439_g.func_70694_bm() != null) {
      Sneak.("Auto ability");
      mc.field_71442_b.func_78769_a((EntityPlayer)mc.field_71439_g, (World)mc.field_71441_e, mc.field_71439_g.func_70694_bm());
    } 
    if (ChatFormatting.stripFormatting(paramClientChatReceivedEvent.message.func_150260_c()).equals("Oh no! Your Pickonimbus 2000 broke!"))
      (new Thread(MithrilMacro::)).start(); 
  }
  
  public boolean (BlockPos paramBlockPos) {
    return ((paramBlockPos) != null);
  }
  
  @SubscribeEvent
  public void (RenderWorldLastEvent paramRenderWorldLastEvent) {
    if (!())
      return; 
    if (this. != null)
      ChestStealer.(this., Color.CYAN); 
    if (this. != null)
      CrimsonQOL.(this., Color.GREEN); 
    if (this. != null)
      CrimsonQOL.(this., Color.RED); 
  }
  
  public void () {
    this. = 0;
    this. = 0;
    this. = 0;
    if (this..() && mc.field_71439_g.func_70694_bm() != null)
      mc.field_71442_b.func_78769_a((EntityPlayer)mc.field_71439_g, (World)mc.field_71441_e, mc.field_71439_g.func_70694_bm()); 
  }
  
  public boolean (BlockPos paramBlockPos) {
    IBlockState iBlockState = mc.field_71441_e.func_180495_p(paramBlockPos);
    return (iBlockState.func_177230_c() == Blocks.field_150348_b && ((BlockStone.EnumType)iBlockState.func_177229_b((IProperty)BlockStone.field_176247_a)).equals(BlockStone.EnumType.DIORITE_SMOOTH));
  }
  
  public static void (Framebuffer paramFramebuffer) {
    paramFramebuffer.func_147610_a(false);
    Disabler.(paramFramebuffer);
    GL11.glClear(1024);
    GL11.glEnable(2960);
  }
  
  public MithrilMacro() {
    super("Mithril Macro", 0, Module.Category.OTHER);
    (new Setting[] { 
          (Setting)this., (Setting)this., (Setting)this., (Setting)this., (Setting)this., (Setting)this., (Setting)this., (Setting)this., (Setting)this., (Setting)this., 
          (Setting)this., (Setting)this., (Setting)this., (Setting)this. });
  }
  
  public boolean (BlockPos paramBlockPos) {
    IBlockState iBlockState = mc.field_71441_e.func_180495_p(paramBlockPos);
    return ((iBlockState.func_177230_c().equals(Blocks.field_150325_L) && iBlockState.func_177228_b().entrySet().stream().anyMatch(MithrilMacro::)) || iBlockState.func_177230_c().equals(Blocks.field_180397_cI) || iBlockState.func_177230_c().equals(Blocks.field_150406_ce) || (iBlockState.func_177230_c().equals(Blocks.field_150325_L) && iBlockState.func_177228_b().entrySet().stream().anyMatch(MithrilMacro::)) || (paramBlockPos));
  }
}
