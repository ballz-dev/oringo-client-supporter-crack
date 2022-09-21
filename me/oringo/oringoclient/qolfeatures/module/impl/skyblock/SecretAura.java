package me.oringo.oringoclient.qolfeatures.module.impl.skyblock;

import com.mojang.authlib.properties.Property;
import com.mojang.realmsclient.gui.ChatFormatting;
import java.awt.Color;
import java.util.ArrayList;
import me.oringo.oringoclient.commands.CommandHandler;
import me.oringo.oringoclient.events.impl.BlockChangeEvent;
import me.oringo.oringoclient.events.impl.MotionUpdateEvent;
import me.oringo.oringoclient.events.impl.PacketReceivedEvent;
import me.oringo.oringoclient.events.impl.RenderLayersEvent;
import me.oringo.oringoclient.events.impl.RightClickEvent;
import me.oringo.oringoclient.qolfeatures.Updater;
import me.oringo.oringoclient.qolfeatures.module.Module;
import me.oringo.oringoclient.qolfeatures.module.impl.combat.BlockHit;
import me.oringo.oringoclient.qolfeatures.module.impl.movement.Sneak;
import me.oringo.oringoclient.qolfeatures.module.impl.other.AntiNicker;
import me.oringo.oringoclient.qolfeatures.module.impl.player.AntiObby;
import me.oringo.oringoclient.qolfeatures.module.impl.render.ChinaHat;
import me.oringo.oringoclient.qolfeatures.module.impl.render.FullBright;
import me.oringo.oringoclient.qolfeatures.module.settings.Setting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.BooleanSetting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.ModeSetting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.NumberSetting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.StringSetting;
import me.oringo.oringoclient.ui.hud.impl.TargetComponent;
import me.oringo.oringoclient.utils.Rotation;
import me.oringo.oringoclient.utils.SkyblockUtils;
import me.oringo.oringoclient.utils.StencilUtils;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.network.play.client.C0DPacketCloseWindow;
import net.minecraft.network.play.server.S02PacketChat;
import net.minecraft.network.play.server.S2DPacketOpenWindow;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntitySkull;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.util.Vec3i;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;

public class SecretAura extends Module {
  public StringSetting  = new StringSetting("Item");
  
  public NumberSetting  = new NumberSetting("Reach", 5.0D, 2.0D, 6.0D, 0.1D);
  
  public static ArrayList<BlockPos>  = new ArrayList<>();
  
  public static boolean ;
  
  public NumberSetting  = new NumberSetting("Fov", 360.0D, 1.0D, 360.0D, 1.0D, this::lambda$new$0);
  
  public ModeSetting  = new ModeSetting("Mode", "Aura", new String[] { "Aura", "Stonkless" });
  
  public BooleanSetting  = new BooleanSetting("Cancel chests", true);
  
  public BooleanSetting  = new BooleanSetting("Secret ESP", false);
  
  public BooleanSetting  = new BooleanSetting("Rotation", false, this::lambda$new$2);
  
  public BooleanSetting  = new BooleanSetting("Clicked check", true, this::lambda$new$1);
  
  public boolean ;
  
  public BooleanSetting  = new BooleanSetting("Sneak ", false);
  
  public static boolean ;
  
  public NumberSetting  = new NumberSetting("Essence reach", 5.0D, 2.0D, 6.0D, 0.1D);
  
  @SubscribeEvent
  public void (RightClickEvent paramRightClickEvent) {
     = (() && this..("Stonkless"));
  }
  
  public boolean (BlockPos paramBlockPos, double paramDouble) {
    return (mc.field_71439_g.func_70011_f(paramBlockPos.func_177958_n(), (paramBlockPos.func_177956_o() - mc.field_71439_g.func_70047_e()), paramBlockPos.func_177952_p()) < paramDouble);
  }
  
  @SubscribeEvent
  public void (WorldEvent.Load paramLoad) {
     = false;
    .clear();
  }
  
  public static void (RenderLayersEvent paramRenderLayersEvent, Color paramColor) {
    Minecraft minecraft = Minecraft.func_71410_x();
    boolean bool = minecraft.field_71474_y.field_74347_j;
    float f = minecraft.field_71474_y.field_74333_Y;
    minecraft.field_71474_y.field_74347_j = false;
    minecraft.field_71474_y.field_74333_Y = 100000.0F;
    GlStateManager.func_179117_G();
    BlockChangeEvent.(paramColor);
    ChinaHat.(3.0F);
    paramRenderLayersEvent..func_78088_a((Entity)paramRenderLayersEvent., paramRenderLayersEvent., paramRenderLayersEvent., paramRenderLayersEvent., paramRenderLayersEvent., paramRenderLayersEvent., paramRenderLayersEvent.);
    BlockChangeEvent.(paramColor);
    StencilUtils.();
    paramRenderLayersEvent..func_78088_a((Entity)paramRenderLayersEvent., paramRenderLayersEvent., paramRenderLayersEvent., paramRenderLayersEvent., paramRenderLayersEvent., paramRenderLayersEvent., paramRenderLayersEvent.);
    BlockChangeEvent.(paramColor);
    DojoHelper.();
    paramRenderLayersEvent..func_78088_a((Entity)paramRenderLayersEvent., paramRenderLayersEvent., paramRenderLayersEvent., paramRenderLayersEvent., paramRenderLayersEvent., paramRenderLayersEvent., paramRenderLayersEvent.);
    BlockChangeEvent.(paramColor);
    Updater.(paramColor);
    paramRenderLayersEvent..func_78088_a((Entity)paramRenderLayersEvent., paramRenderLayersEvent., paramRenderLayersEvent., paramRenderLayersEvent., paramRenderLayersEvent., paramRenderLayersEvent., paramRenderLayersEvent.);
    BlockChangeEvent.(paramColor);
    BlockHit.();
    BlockChangeEvent.(Color.WHITE);
    minecraft.field_71474_y.field_74347_j = bool;
    minecraft.field_71474_y.field_74333_Y = f;
  }
  
  public SecretAura() {
    super("Secret Aura", 0, Module.Category.);
    (new Setting[] { (Setting)this., (Setting)this., (Setting)this., (Setting)this., (Setting)this., (Setting)this., (Setting)this., (Setting)this., (Setting)this., (Setting)this. });
  }
  
  @SubscribeEvent
  public void (RenderWorldLastEvent paramRenderWorldLastEvent) {
    if (() && this..() && SkyblockUtils.inDungeon) {
      Vec3i vec3i = new Vec3i(10, 10, 10);
      for (BlockPos blockPos : BlockPos.func_177980_a((new BlockPos((Vec3i)mc.field_71439_g.func_180425_c())).func_177971_a(vec3i), new BlockPos((Vec3i)mc.field_71439_g.func_180425_c().func_177973_b(vec3i)))) {
        if ((blockPos) && (blockPos, (mc.field_71441_e.func_180495_p(blockPos).func_177230_c() != Blocks.field_150465_bP) ? this..() : this..())) {
          Color color = .contains(blockPos) ? Color.RED : Color.green;
          GL11.glColor4f(color.getRed() / 255.0F, color.getGreen() / 255.0F, color.getBlue() / 255.0F, 0.2F);
          GL11.glBlendFunc(770, 771);
          GL11.glEnable(3042);
          GL11.glDisable(3553);
          GL11.glDisable(2929);
          GL11.glDepthMask(false);
          FullBright.((new AxisAlignedBB(blockPos.func_177958_n(), blockPos.func_177956_o(), blockPos.func_177952_p(), (blockPos.func_177958_n() + 1), (blockPos.func_177956_o() + 1), (blockPos.func_177952_p() + 1))).func_72317_d(-(mc.func_175598_ae()).field_78730_l, -(mc.func_175598_ae()).field_78731_m, -(mc.func_175598_ae()).field_78728_n));
          GL11.glEnable(3553);
          GL11.glEnable(2929);
          GL11.glDepthMask(true);
          GL11.glDisable(3042);
        } 
      } 
    } 
  }
  
  @SubscribeEvent
  public void (PacketReceivedEvent paramPacketReceivedEvent) {
    if (paramPacketReceivedEvent. instanceof S02PacketChat && ChatFormatting.stripFormatting(((S02PacketChat)paramPacketReceivedEvent.).func_148915_c().func_150254_d()).startsWith("[BOSS] Maxor"))
       = true; 
  }
  
  @SubscribeEvent
  public void (MotionUpdateEvent.Post paramPost) {
    if (this..("Stonkless") && )
      return; 
    if (() && SkyblockUtils.inDungeon && ( || this..("Aura"))) {
      Vec3i vec3i = new Vec3i(10, 10, 10);
      for (BlockPos blockPos : BlockPos.func_177980_a((new BlockPos((Vec3i)mc.field_71439_g.func_180425_c())).func_177971_a(vec3i), new BlockPos((Vec3i)mc.field_71439_g.func_180425_c().func_177973_b(vec3i)))) {
        if ((blockPos) && (blockPos, (mc.field_71441_e.func_180495_p(blockPos).func_177230_c() == Blocks.field_150465_bP) ? this..() : this..())) {
          Rotation rotation;
          Vec3 vec31;
          Vec3 vec32;
          Vec3 vec33;
          MovingObjectPosition movingObjectPosition;
          AxisAlignedBB axisAlignedBB = mc.field_71441_e.func_180495_p(blockPos).func_177230_c().func_180646_a((World)mc.field_71441_e, blockPos);
          switch (this..()) {
            case "Aura":
              rotation = CommandHandler.(axisAlignedBB.field_72340_a + (axisAlignedBB.field_72336_d - axisAlignedBB.field_72340_a) / 2.0D, axisAlignedBB.field_72338_b + (axisAlignedBB.field_72337_e - axisAlignedBB.field_72338_b) / 2.0D, axisAlignedBB.field_72339_c + (axisAlignedBB.field_72334_f - axisAlignedBB.field_72339_c) / 2.0D);
              if (RemoveAnnoyingMobs.(rotation, (float)this..())) {
                (blockPos, EnumFacing.func_176733_a(mc.field_71439_g.field_70177_z), new Vec3(0.0D, 0.0D, 0.0D));
                return;
              } 
            case "Stonkless":
              vec31 = mc.field_71439_g.func_174824_e(1.0F);
              vec32 = mc.field_71439_g.func_70676_i(1.0F);
              vec33 = vec31.func_72441_c(vec32.field_72450_a * 7.0D, vec32.field_72448_b * 7.0D, vec32.field_72449_c * 7.0D);
              movingObjectPosition = axisAlignedBB.func_72327_a(vec31, vec33);
              if (movingObjectPosition != null) {
                mc.field_71439_g.func_71038_i();
                (blockPos, movingObjectPosition.field_178784_b, movingObjectPosition.field_72307_f);
              } 
          } 
        } 
      } 
    } 
     = false;
  }
  
  public void (BlockPos paramBlockPos, EnumFacing paramEnumFacing, Vec3 paramVec3) {
    for (byte b = 0; b < 9; b++) {
      if (mc.field_71439_g.field_71071_by.func_70301_a(b) != null && mc.field_71439_g.field_71071_by.func_70301_a(b).func_82833_r().toLowerCase().contains(this..().toLowerCase())) {
        int i = mc.field_71439_g.field_71071_by.field_70461_c;
        mc.field_71439_g.field_71071_by.field_70461_c = b;
        if (!)
          if (this..()) {
            AntiNicker.((Packet)new C0BPacketEntityAction((Entity)mc.field_71439_g, C0BPacketEntityAction.Action.START_SNEAKING));
          } else if (mc.field_71441_e.func_180495_p(paramBlockPos).func_177230_c() == Blocks.field_150442_at) {
            mc.field_71442_b.func_178890_a(mc.field_71439_g, mc.field_71441_e, mc.field_71439_g.field_71071_by.func_70448_g(), paramBlockPos, paramEnumFacing, paramVec3);
          }  
        mc.field_71442_b.func_178890_a(mc.field_71439_g, mc.field_71441_e, mc.field_71439_g.field_71071_by.func_70448_g(), paramBlockPos, paramEnumFacing, paramVec3);
        if (! && this..())
          AntiNicker.((Packet)new C0BPacketEntityAction((Entity)mc.field_71439_g, C0BPacketEntityAction.Action.STOP_SNEAKING)); 
        mc.field_71439_g.field_71071_by.field_70461_c = i;
        if (!.contains(paramBlockPos))
          .add(paramBlockPos); 
        return;
      } 
    } 
    if (!this.) {
      Sneak.("You don't have the required item in your hotbar!");
      this. = true;
    } 
  }
  
  @SubscribeEvent
  public void (PacketReceivedEvent paramPacketReceivedEvent) {
    if (paramPacketReceivedEvent. instanceof S2DPacketOpenWindow && ChatFormatting.stripFormatting(((S2DPacketOpenWindow)paramPacketReceivedEvent.).func_179840_c().func_150254_d()).equals("Chest") && SkyblockUtils.inDungeon && this..()) {
      paramPacketReceivedEvent.setCanceled(true);
      mc.func_147114_u().func_147298_b().func_179290_a((Packet)new C0DPacketCloseWindow(((S2DPacketOpenWindow)paramPacketReceivedEvent.).func_148901_c()));
    } 
  }
  
  public boolean (BlockPos paramBlockPos) {
    Block block = mc.field_71441_e.func_180495_p(paramBlockPos).func_177230_c();
    if (block == Blocks.field_150465_bP) {
      TileEntitySkull tileEntitySkull = (TileEntitySkull)mc.field_71441_e.func_175625_s(paramBlockPos);
      if (tileEntitySkull.func_145904_a() == 3 && tileEntitySkull.func_152108_a() != null && tileEntitySkull.func_152108_a().getProperties() != null) {
        Property property = (Property)TargetComponent.(tileEntitySkull.func_152108_a().getProperties().get("textures"));
        return (property != null && property.getValue().equals("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzRkYjRhZGZhOWJmNDhmZjVkNDE3MDdhZTM0ZWE3OGJkMjM3MTY1OWZjZDhjZDg5MzQ3NDlhZjRjY2U5YiJ9fX0=") && (!.contains(paramBlockPos) || !this..()));
      } 
    } else if (block == Blocks.field_150486_ae && AntiObby.((TileEntityChest)mc.field_71441_e.func_175625_s(paramBlockPos))) {
      return false;
    } 
    return ((block == Blocks.field_150442_at || block == Blocks.field_150486_ae || block == Blocks.field_150447_bR) && (!.contains(paramBlockPos) || !this..() || this..("Stonkless")));
  }
  
  @SubscribeEvent(priority = EventPriority.LOWEST)
  public void (MotionUpdateEvent.Pre paramPre) {
    if (() && SkyblockUtils.inDungeon && this..() && this..("Aura")) {
      Vec3i vec3i = new Vec3i(10, 10, 10);
      for (BlockPos blockPos : BlockPos.func_177980_a((new BlockPos(mc.field_71439_g.func_174791_d())).func_177971_a(vec3i), (new BlockPos(mc.field_71439_g.func_174791_d())).func_177973_b(vec3i))) {
        if ((blockPos) && (blockPos, (mc.field_71441_e.func_180495_p(blockPos).func_177230_c() == Blocks.field_150465_bP) ? this..() : this..())) {
          AxisAlignedBB axisAlignedBB = mc.field_71441_e.func_180495_p(blockPos).func_177230_c().func_180646_a((World)mc.field_71441_e, blockPos);
          Rotation rotation = CommandHandler.(axisAlignedBB.field_72340_a + (axisAlignedBB.field_72336_d - axisAlignedBB.field_72340_a) / 2.0D, axisAlignedBB.field_72338_b + (axisAlignedBB.field_72337_e - axisAlignedBB.field_72338_b) / 2.0D, axisAlignedBB.field_72339_c + (axisAlignedBB.field_72334_f - axisAlignedBB.field_72339_c) / 2.0D);
          if (RemoveAnnoyingMobs.(rotation, (float)this..())) {
            paramPre. = rotation.();
            paramPre. = rotation.();
            break;
          } 
        } 
      } 
    } 
  }
}
