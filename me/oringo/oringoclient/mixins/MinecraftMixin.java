package me.oringo.oringoclient.mixins;

import com.mojang.authlib.properties.PropertyMap;
import me.oringo.oringoclient.OringoClient;
import me.oringo.oringoclient.events.impl.BlockBoundsEvent;
import me.oringo.oringoclient.events.impl.KeyPressEvent;
import me.oringo.oringoclient.events.impl.LeftClickEvent;
import me.oringo.oringoclient.events.impl.PostGuiOpenEvent;
import me.oringo.oringoclient.events.impl.RightClickEvent;
import me.oringo.oringoclient.mixins.entity.EntityLivingBaseAccessor;
import me.oringo.oringoclient.mixins.entity.PlayerSPAccessor;
import me.oringo.oringoclient.qolfeatures.Updater;
import me.oringo.oringoclient.qolfeatures.module.impl.combat.KillAura;
import me.oringo.oringoclient.qolfeatures.module.impl.other.Delays;
import me.oringo.oringoclient.qolfeatures.module.impl.other.Disabler;
import me.oringo.oringoclient.qolfeatures.module.impl.render.Camera;
import me.oringo.oringoclient.qolfeatures.module.impl.skyblock.BoneThrower;
import me.oringo.oringoclient.qolfeatures.module.impl.skyblock.CrimsonQOL;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S0BPacketAnimation;
import net.minecraft.potion.Potion;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Timer;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.Event;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({Minecraft.class})
public abstract class MinecraftMixin {
  @Shadow
  public EntityPlayerSP field_71439_g;
  
  @Shadow
  public MovingObjectPosition field_71476_x;
  
  @Shadow
  public boolean field_71454_w;
  
  @Shadow
  private Timer field_71428_T;
  
  @Shadow
  public boolean field_175612_E;
  
  @Shadow
  public WorldClient field_71441_e;
  
  @Shadow
  public PlayerControllerMP field_71442_b;
  
  @Shadow
  private int field_71435_Y;
  
  @Shadow
  private int field_71429_W;
  
  @Shadow
  public boolean field_71415_G;
  
  @Shadow
  private int field_71467_ac;
  
  @Shadow
  private Entity field_175622_Z;
  
  @Shadow
  private static Minecraft field_71432_P;
  
  @Shadow
  public GuiScreen field_71462_r;
  
  @Shadow
  public GuiIngame field_71456_v;
  
  @Shadow
  public EffectRenderer field_71452_i;
  
  @Shadow
  public GameSettings field_71474_y;
  
  @Shadow
  public EntityRenderer field_71460_t;
  
  @Inject(method = {"getRenderViewEntity"}, at = {@At("HEAD")})
  public void getRenderViewEntity(CallbackInfoReturnable<Entity> paramCallbackInfoReturnable) {
    if (!Updater.().() || this.field_175622_Z == null || this.field_175622_Z != OringoClient.mc.field_71439_g)
      return; 
    ((EntityLivingBase)this.field_175622_Z).field_70759_as = ((PlayerSPAccessor)this.field_175622_Z).getLastReportedYaw();
  }
  
  @Inject(method = {"runTick"}, at = {@At("HEAD")}, cancellable = true)
  public void onRunTick(CallbackInfo paramCallbackInfo) {}
  
  @Redirect(method = {"runTick"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/settings/KeyBinding;isKeyDown()Z", ordinal = 0))
  public boolean isDown(KeyBinding paramKeyBinding) {
    return (paramKeyBinding.func_151470_d() || (KillAura. != null && !KillAura..("Fake") && !KillAura..("None")));
  }
  
  @Inject(method = {"runGameLoop"}, at = {@At(value = "FIELD", target = "Lnet/minecraft/client/Minecraft;skipRenderWorld:Z")})
  public void skipRenderWorld(CallbackInfo paramCallbackInfo) {
    if (this.field_71454_w) {
      BlockBoundsEvent.();
      try {
        Thread.sleep((long)(50.0F / (Disabler.()).field_74278_d));
      } catch (InterruptedException interruptedException) {
        interruptedException.printStackTrace();
      } 
    } 
  }
  
  @Inject(method = {"setIngameNotInFocus"}, at = {@At("HEAD")}, cancellable = true)
  public void onSetfocus(CallbackInfo paramCallbackInfo) {
    if (this.field_71462_r instanceof GuiChest && OringoClient..((ContainerChest)((GuiChest)this.field_71462_r).field_147002_h)) {
      KeyBinding.func_74506_a();
      paramCallbackInfo.cancel();
    } 
  }
  
  @Inject(method = {"displayGuiScreen"}, at = {@At("RETURN")})
  public void onGuiOpen(GuiScreen paramGuiScreen, CallbackInfo paramCallbackInfo) {
    (new PostGuiOpenEvent((Gui)paramGuiScreen)).post();
  }
  
  @Inject(method = {"runTick"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;dispatchKeypresses()V")})
  public void keyPresses(CallbackInfo paramCallbackInfo) {
    int i = (Keyboard.getEventKey() == 0) ? (Keyboard.getEventCharacter() + 256) : Keyboard.getEventKey();
    char c = Keyboard.getEventCharacter();
    if (Keyboard.getEventKeyState()) {
      if (MinecraftForge.EVENT_BUS.post((Event)new KeyPressEvent(i, c)))
        return; 
      if (OringoClient.mc.field_71462_r == null) {
        if (c == BoneThrower.() && this.field_71474_y.field_74343_n != EntityPlayer.EnumChatVisibility.HIDDEN)
          OringoClient.mc.func_147108_a((GuiScreen)new GuiChat()); 
        CrimsonQOL.(i);
      } 
    } 
  }
  
  @Inject(method = {"runTick"}, at = {@At(value = "INVOKE", target = "Lorg/lwjgl/input/Mouse;getEventButton()I")})
  public void mousePress(CallbackInfo paramCallbackInfo) {
    int i = Mouse.getEventButton() - 100;
    if (Mouse.getEventButtonState() && 
      OringoClient.mc.field_71462_r == null)
      CrimsonQOL.(i); 
  }
  
  @Inject(method = {"runTick"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/settings/KeyBinding;isPressed()Z", ordinal = 7)})
  public void onIsPressed(CallbackInfo paramCallbackInfo) {
    if (OringoClient..() && this.field_71476_x != null && this.field_71476_x.field_72313_a == MovingObjectPosition.MovingObjectType.ENTITY) {
      while (OringoClient.mc.field_71474_y.field_74312_F.func_151468_f())
        func_147116_af(); 
    } else if (OringoClient..() && (
      this.field_71474_y.field_74312_F.func_151468_f() || (this.field_71474_y.field_74312_F.func_151470_d() && this.field_71476_x != null && this.field_71476_x.field_72313_a == MovingObjectPosition.MovingObjectType.BLOCK)) && (
      !this.field_71439_g.field_82175_bq || this.field_71439_g.field_110158_av >= (int)(((EntityLivingBaseAccessor)this.field_71439_g).getArmSwingAnimationEnd() / OringoClient...()) || this.field_71439_g.field_110158_av < 0)) {
      this.field_71439_g.field_110158_av = -1;
      this.field_71439_g.field_82175_bq = true;
      if (this.field_71439_g.field_70170_p instanceof WorldServer)
        ((WorldServer)this.field_71439_g.field_70170_p).func_73039_n().func_151247_a((Entity)this.field_71439_g, (Packet)new S0BPacketAnimation((Entity)this.field_71439_g, 0)); 
    } 
  }
  
  @Inject(method = {"rightClickMouse"}, at = {@At("HEAD")}, cancellable = true)
  public void onRightClick(CallbackInfo paramCallbackInfo) {
    if ((new RightClickEvent()).post())
      paramCallbackInfo.cancel(); 
  }
  
  @Inject(method = {"rightClickMouse"}, at = {@At("RETURN")}, cancellable = true)
  public void onRightClickPost(CallbackInfo paramCallbackInfo) {
    if (Delays..())
      this.field_71467_ac = (int)Delays...(); 
  }
  
  @Inject(method = {"clickMouse"}, at = {@At("HEAD")}, cancellable = true)
  public void onClick(CallbackInfo paramCallbackInfo) {
    if ((new LeftClickEvent()).post())
      paramCallbackInfo.cancel(); 
  }
  
  @Inject(method = {"clickMouse"}, at = {@At("RETURN")})
  public void onClick1(CallbackInfo paramCallbackInfo) {
    if (Delays..() && this.field_71429_W == 10 && (this.field_71476_x == null || this.field_71476_x.field_72313_a == MovingObjectPosition.MovingObjectType.MISS))
      this.field_71429_W = (int)Delays...(); 
    if (OringoClient..())
      this.field_71429_W = 0; 
  }
  
  @Inject(method = {"sendClickBlockToController"}, at = {@At("RETURN")})
  public void sendClickBlock(CallbackInfo paramCallbackInfo) {
    boolean bool = (this.field_71462_r == null && this.field_71474_y.field_74312_F.func_151470_d() && this.field_71415_G) ? true : false;
    if (OringoClient..() && bool && this.field_71476_x != null && this.field_175622_Z != null && this.field_71476_x.field_72313_a == MovingObjectPosition.MovingObjectType.BLOCK)
      for (byte b = 0; b < OringoClient...(); ) {
        BlockPos blockPos = this.field_71476_x.func_178782_a();
        this.field_71460_t.func_78473_a(this.field_71428_T.field_74281_c);
        if (this.field_71476_x != null) {
          BlockPos blockPos1 = this.field_71476_x.func_178782_a();
          if (blockPos1 != null && 
            this.field_71476_x.field_72313_a == MovingObjectPosition.MovingObjectType.BLOCK && !blockPos1.equals(blockPos) && this.field_71441_e.func_180495_p(blockPos1).func_177230_c().func_149688_o() != Material.field_151579_a) {
            this.field_71442_b.func_180511_b(blockPos1, this.field_71476_x.field_178784_b);
            if (!OringoClient...())
              this.field_71439_g.func_71038_i(); 
            b++;
          } 
        } 
      }  
  }
  
  @Redirect(method = {"runTick"}, at = @At(value = "FIELD", target = "Lnet/minecraft/client/settings/GameSettings;thirdPersonView:I", opcode = 180, ordinal = 1))
  private int injected(GameSettings paramGameSettings) {
    return (OringoClient..() && Camera..()) ? (this.field_71474_y.field_74320_O + 1) : this.field_71474_y.field_74320_O;
  }
  
  @Redirect(method = {"sendClickBlockToController"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/entity/EntityPlayerSP;swingItem()V", ordinal = 0))
  public void onSwing(EntityPlayerSP paramEntityPlayerSP) {
    if (OringoClient..() && OringoClient...()) {
      byte b = paramEntityPlayerSP.func_70644_a(Potion.field_76422_e) ? (6 - 1 + paramEntityPlayerSP.func_70660_b(Potion.field_76422_e).func_76458_c()) : (paramEntityPlayerSP.func_70644_a(Potion.field_76419_f) ? (6 + (1 + paramEntityPlayerSP.func_70660_b(Potion.field_76419_f).func_76458_c()) * 2) : 6);
      if (paramEntityPlayerSP.field_82175_bq && paramEntityPlayerSP.field_110158_av < b / 2 && paramEntityPlayerSP.field_110158_av >= 0)
        return; 
    } 
    paramEntityPlayerSP.func_71038_i();
  }
  
  @Shadow
  public abstract PropertyMap func_181037_M();
  
  @Shadow
  public abstract Entity func_175606_aa();
  
  @Shadow
  protected abstract void func_147116_af();
}
