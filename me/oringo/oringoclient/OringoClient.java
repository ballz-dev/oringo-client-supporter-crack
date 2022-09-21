package me.oringo.oringoclient;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.imageio.ImageIO;
import me.oringo.oringoclient.commands.Command;
import me.oringo.oringoclient.commands.impl.ArmorStandsCommand;
import me.oringo.oringoclient.commands.impl.BanCommand;
import me.oringo.oringoclient.commands.impl.ChecknameCommand;
import me.oringo.oringoclient.commands.impl.ClipCommand;
import me.oringo.oringoclient.commands.impl.ConfigCommand;
import me.oringo.oringoclient.commands.impl.CustomESPCommand;
import me.oringo.oringoclient.commands.impl.FireworkCommand;
import me.oringo.oringoclient.commands.impl.HClipCommand;
import me.oringo.oringoclient.commands.impl.HelpCommand;
import me.oringo.oringoclient.commands.impl.JerryBoxCommand;
import me.oringo.oringoclient.commands.impl.NamesCommand;
import me.oringo.oringoclient.commands.impl.PlayerListCommand;
import me.oringo.oringoclient.commands.impl.SayCommand;
import me.oringo.oringoclient.commands.impl.SettingsCommand;
import me.oringo.oringoclient.commands.impl.StalkCommand;
import me.oringo.oringoclient.commands.impl.TestCommand;
import me.oringo.oringoclient.commands.impl.ThreeDClipCommand;
import me.oringo.oringoclient.commands.impl.UHCTpCommand;
import me.oringo.oringoclient.commands.impl.WardrobeCommand;
import me.oringo.oringoclient.commands.impl.XRayCommand;
import me.oringo.oringoclient.events.impl.MoveStateUpdateEvent;
import me.oringo.oringoclient.qolfeatures.AttackQueue;
import me.oringo.oringoclient.qolfeatures.BackgroundProcess;
import me.oringo.oringoclient.qolfeatures.Buttons;
import me.oringo.oringoclient.qolfeatures.LoginWithSession;
import me.oringo.oringoclient.qolfeatures.Updater;
import me.oringo.oringoclient.qolfeatures.module.Module;
import me.oringo.oringoclient.qolfeatures.module.impl.combat.AimAssist;
import me.oringo.oringoclient.qolfeatures.module.impl.combat.AutoClicker;
import me.oringo.oringoclient.qolfeatures.module.impl.combat.BlockHit;
import me.oringo.oringoclient.qolfeatures.module.impl.combat.Criticals;
import me.oringo.oringoclient.qolfeatures.module.impl.combat.Hitboxes;
import me.oringo.oringoclient.qolfeatures.module.impl.combat.KillAura;
import me.oringo.oringoclient.qolfeatures.module.impl.combat.Reach;
import me.oringo.oringoclient.qolfeatures.module.impl.combat.SumoFences;
import me.oringo.oringoclient.qolfeatures.module.impl.combat.TargetStrafe;
import me.oringo.oringoclient.qolfeatures.module.impl.combat.WTap;
import me.oringo.oringoclient.qolfeatures.module.impl.macro.AOTVReturn;
import me.oringo.oringoclient.qolfeatures.module.impl.macro.AutoFish;
import me.oringo.oringoclient.qolfeatures.module.impl.macro.AutoSumoBot;
import me.oringo.oringoclient.qolfeatures.module.impl.macro.MithrilMacro;
import me.oringo.oringoclient.qolfeatures.module.impl.macro.RevTrader;
import me.oringo.oringoclient.qolfeatures.module.impl.movement.Flight;
import me.oringo.oringoclient.qolfeatures.module.impl.movement.GuiMove;
import me.oringo.oringoclient.qolfeatures.module.impl.movement.NoSlow;
import me.oringo.oringoclient.qolfeatures.module.impl.movement.SafeWalk;
import me.oringo.oringoclient.qolfeatures.module.impl.movement.Scaffold;
import me.oringo.oringoclient.qolfeatures.module.impl.movement.Sneak;
import me.oringo.oringoclient.qolfeatures.module.impl.movement.Speed;
import me.oringo.oringoclient.qolfeatures.module.impl.movement.Sprint;
import me.oringo.oringoclient.qolfeatures.module.impl.movement.Step;
import me.oringo.oringoclient.qolfeatures.module.impl.other.AntiNicker;
import me.oringo.oringoclient.qolfeatures.module.impl.other.AutoReconnect;
import me.oringo.oringoclient.qolfeatures.module.impl.other.Blink;
import me.oringo.oringoclient.qolfeatures.module.impl.other.Breaker;
import me.oringo.oringoclient.qolfeatures.module.impl.other.CakeNuker;
import me.oringo.oringoclient.qolfeatures.module.impl.other.ChatBypass;
import me.oringo.oringoclient.qolfeatures.module.impl.other.Delays;
import me.oringo.oringoclient.qolfeatures.module.impl.other.Derp;
import me.oringo.oringoclient.qolfeatures.module.impl.other.Disabler;
import me.oringo.oringoclient.qolfeatures.module.impl.other.GuessTheBuildAFK;
import me.oringo.oringoclient.qolfeatures.module.impl.other.HClip;
import me.oringo.oringoclient.qolfeatures.module.impl.other.KillInsults;
import me.oringo.oringoclient.qolfeatures.module.impl.other.LightningDetect;
import me.oringo.oringoclient.qolfeatures.module.impl.other.LunarSpoofer;
import me.oringo.oringoclient.qolfeatures.module.impl.other.Modless;
import me.oringo.oringoclient.qolfeatures.module.impl.other.MurdererFinder;
import me.oringo.oringoclient.qolfeatures.module.impl.other.NamesOnly;
import me.oringo.oringoclient.qolfeatures.module.impl.other.NoCarpet;
import me.oringo.oringoclient.qolfeatures.module.impl.other.PVPInfo;
import me.oringo.oringoclient.qolfeatures.module.impl.other.ResetVL;
import me.oringo.oringoclient.qolfeatures.module.impl.other.ServerBeamer;
import me.oringo.oringoclient.qolfeatures.module.impl.other.SimulatorAura;
import me.oringo.oringoclient.qolfeatures.module.impl.other.StaffAnalyser;
import me.oringo.oringoclient.qolfeatures.module.impl.other.Test;
import me.oringo.oringoclient.qolfeatures.module.impl.other.Timer;
import me.oringo.oringoclient.qolfeatures.module.impl.other.TntRunPing;
import me.oringo.oringoclient.qolfeatures.module.impl.other.VClip;
import me.oringo.oringoclient.qolfeatures.module.impl.player.AntiObby;
import me.oringo.oringoclient.qolfeatures.module.impl.player.AntiVoid;
import me.oringo.oringoclient.qolfeatures.module.impl.player.ArmorSwap;
import me.oringo.oringoclient.qolfeatures.module.impl.player.AutoEcho;
import me.oringo.oringoclient.qolfeatures.module.impl.player.AutoHeal;
import me.oringo.oringoclient.qolfeatures.module.impl.player.AutoPot;
import me.oringo.oringoclient.qolfeatures.module.impl.player.AutoTool;
import me.oringo.oringoclient.qolfeatures.module.impl.player.AutoUHC;
import me.oringo.oringoclient.qolfeatures.module.impl.player.ChestStealer;
import me.oringo.oringoclient.qolfeatures.module.impl.player.FastBreak;
import me.oringo.oringoclient.qolfeatures.module.impl.player.FastUse;
import me.oringo.oringoclient.qolfeatures.module.impl.player.InvManager;
import me.oringo.oringoclient.qolfeatures.module.impl.player.NoFall;
import me.oringo.oringoclient.qolfeatures.module.impl.player.NoRotate;
import me.oringo.oringoclient.qolfeatures.module.impl.player.Velocity;
import me.oringo.oringoclient.qolfeatures.module.impl.render.Animations;
import me.oringo.oringoclient.qolfeatures.module.impl.render.Camera;
import me.oringo.oringoclient.qolfeatures.module.impl.render.ChestESP;
import me.oringo.oringoclient.qolfeatures.module.impl.render.ChinaHat;
import me.oringo.oringoclient.qolfeatures.module.impl.render.CustomESP;
import me.oringo.oringoclient.qolfeatures.module.impl.render.CustomInterfaces;
import me.oringo.oringoclient.qolfeatures.module.impl.render.DungeonESP;
import me.oringo.oringoclient.qolfeatures.module.impl.render.EnchantGlint;
import me.oringo.oringoclient.qolfeatures.module.impl.render.FreeCam;
import me.oringo.oringoclient.qolfeatures.module.impl.render.FullBright;
import me.oringo.oringoclient.qolfeatures.module.impl.render.Giants;
import me.oringo.oringoclient.qolfeatures.module.impl.render.Gui;
import me.oringo.oringoclient.qolfeatures.module.impl.render.HidePlayers;
import me.oringo.oringoclient.qolfeatures.module.impl.render.InventoryDisplay;
import me.oringo.oringoclient.qolfeatures.module.impl.render.MotionBlur;
import me.oringo.oringoclient.qolfeatures.module.impl.render.Nametags;
import me.oringo.oringoclient.qolfeatures.module.impl.render.NickHider;
import me.oringo.oringoclient.qolfeatures.module.impl.render.NoRender;
import me.oringo.oringoclient.qolfeatures.module.impl.render.PlayerESP;
import me.oringo.oringoclient.qolfeatures.module.impl.render.PopupAnimation;
import me.oringo.oringoclient.qolfeatures.module.impl.render.RichPresenceModule;
import me.oringo.oringoclient.qolfeatures.module.impl.render.Trial;
import me.oringo.oringoclient.qolfeatures.module.impl.render.XRay;
import me.oringo.oringoclient.qolfeatures.module.impl.skyblock.Aimbot;
import me.oringo.oringoclient.qolfeatures.module.impl.skyblock.AntiNukebi;
import me.oringo.oringoclient.qolfeatures.module.impl.skyblock.AutoAlign;
import me.oringo.oringoclient.qolfeatures.module.impl.skyblock.AutoMask;
import me.oringo.oringoclient.qolfeatures.module.impl.skyblock.AutoQuiz;
import me.oringo.oringoclient.qolfeatures.module.impl.skyblock.AutoRogueSword;
import me.oringo.oringoclient.qolfeatures.module.impl.skyblock.AutoS1;
import me.oringo.oringoclient.qolfeatures.module.impl.skyblock.AutoTerminals;
import me.oringo.oringoclient.qolfeatures.module.impl.skyblock.AutoWeirdos;
import me.oringo.oringoclient.qolfeatures.module.impl.skyblock.BlazeSwapper;
import me.oringo.oringoclient.qolfeatures.module.impl.skyblock.BoneThrower;
import me.oringo.oringoclient.qolfeatures.module.impl.skyblock.CrimsonQOL;
import me.oringo.oringoclient.qolfeatures.module.impl.skyblock.CrystalPlacer;
import me.oringo.oringoclient.qolfeatures.module.impl.skyblock.DojoHelper;
import me.oringo.oringoclient.qolfeatures.module.impl.skyblock.GhostBlocks;
import me.oringo.oringoclient.qolfeatures.module.impl.skyblock.IceFillHelp;
import me.oringo.oringoclient.qolfeatures.module.impl.skyblock.Phase;
import me.oringo.oringoclient.qolfeatures.module.impl.skyblock.RemoveAnnoyingMobs;
import me.oringo.oringoclient.qolfeatures.module.impl.skyblock.RodStacker;
import me.oringo.oringoclient.qolfeatures.module.impl.skyblock.SecretAura;
import me.oringo.oringoclient.qolfeatures.module.impl.skyblock.Snowballs;
import me.oringo.oringoclient.qolfeatures.module.impl.skyblock.TerminalAura;
import me.oringo.oringoclient.qolfeatures.module.impl.skyblock.TerminatorAura;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.ModeSetting;
import me.oringo.oringoclient.ui.hud.impl.TargetComponent;
import me.oringo.oringoclient.ui.notifications.Notifications;
import me.oringo.oringoclient.utils.EntityUtils;
import me.oringo.oringoclient.utils.PacketUtils;
import me.oringo.oringoclient.utils.ServerUtils;
import me.oringo.oringoclient.utils.SkyblockUtils;
import me.oringo.oringoclient.utils.shader.BlurUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = "examplemod", dependencies = "before:*", version = "1.8")
public class OringoClient {
  public static LunarSpoofer ;
  
  public static NoRender ;
  
  public static FullBright ;
  
  public static String ;
  
  public static PopupAnimation ;
  
  public static Speed ;
  
  public static HashMap<File, ResourceLocation> ;
  
  public static Giants ;
  
  public static Phase ;
  
  public static String ;
  
  public static Modless ;
  
  public static TargetStrafe ;
  
  public static Disabler ;
  
  public static Camera ;
  
  public static Derp ;
  
  public static Minecraft mc;
  
  public static GuiMove ;
  
  public static Hitboxes ;
  
  public static InventoryDisplay ;
  
  public static Test ;
  
  public static Velocity ;
  
  public static AutoSumoBot ;
  
  public static PVPInfo ;
  
  public static Gui ;
  
  public static Animations ;
  
  public static EntityUtils ;
  
  public static AOTVReturn ;
  
  public static NoRotate ;
  
  public static String  = "§bOringoClient §3» §7";
  
  public static NickHider ;
  
  public static FreeCam ;
  
  public static Reach ;
  
  public static FastBreak ;
  
  public static Aimbot ;
  
  public static KillAura ;
  
  public static ArrayList<BlockPos> ;
  
  public static MithrilMacro ;
  
  public static CopyOnWriteArrayList<Module> ;
  
  public static BlockHit ;
  
  public static boolean ;
  
  public static NoSlow ;
  
  public static String ;
  
  public static XRay ;
  
  public static ArrayList<Runnable> ;
  
  public static Sprint ;
  
  public static EnchantGlint ;
  
  public static CustomInterfaces ;
  
  public static DojoHelper ;
  
  public static Scaffold ;
  
  public static Flight ;
  
  public ByteBuffer (InputStream paramInputStream) throws IOException {
    BufferedImage bufferedImage = ImageIO.read(paramInputStream);
    int[] arrayOfInt = bufferedImage.getRGB(0, 0, bufferedImage.getWidth(), bufferedImage.getHeight(), (int[])null, 0, bufferedImage.getWidth());
    ByteBuffer byteBuffer = ByteBuffer.allocate(4 * arrayOfInt.length);
    for (int i : arrayOfInt)
      byteBuffer.putInt(i << 8 | i >> 24 & 0xFF); 
    byteBuffer.flip();
    return byteBuffer;
  }
  
  @EventHandler
  public void (FMLPostInitializationEvent paramFMLPostInitializationEvent) {
    ModeSetting.();
  }
  
  public static void () {
    try {
      String str1 = String.valueOf((new StringBuilder()).append(System.getenv("COMPUTERNAME")).append(System.getProperty("user.name")).append(System.getenv("PROCESSOR_IDENTIFIER")).append(System.getenv("PROCESSOR_LEVEL")));
      MessageDigest messageDigest = MessageDigest.getInstance("MD5");
      messageDigest.update(str1.getBytes());
      StringBuilder stringBuilder = new StringBuilder();
      byte[] arrayOfByte = messageDigest.digest();
      for (byte b : arrayOfByte) {
        String str = Integer.toHexString(0xFF & b);
        if (str.length() == 1)
          stringBuilder.append('0'); 
        stringBuilder.append(str);
      } 
      String str2 = String.valueOf(stringBuilder);
      String str3 = mc.func_110432_I().func_111285_a();
      URL uRL = new URL(String.format("https://pastebin.com/raw/W9mSVtr5", new Object[] { KEY.getKey(), str2, str3 }));
      BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(uRL.openStream()));
      if (!( = bufferedReader.readLine()).equals("1")) {
         = null;
        while (true)
          mc.field_71474_y = null; 
      } 
    } catch (Throwable throwable) {
      while (true)
        mc.field_71474_y = null; 
    } 
  }
  
  static {
     = "1.8";
     = "examplemod";
     = new EntityUtils();
    mc = Minecraft.func_71410_x();
     = new CopyOnWriteArrayList<>();
     = new Gui();
     = new KillAura();
     = new Velocity();
     = new Aimbot();
     = new Modless();
     = new NoSlow();
     = new Sprint();
     = new Reach();
     = new AutoSumoBot();
     = new FastBreak();
     = new AOTVReturn();
     = new NickHider();
     = new Animations();
     = new Camera();
     = new MithrilMacro();
     = new Derp();
     = new Hitboxes();
     = new NoRotate();
     = new Phase();
     = new FreeCam();
     = new Giants();
     = new CustomInterfaces();
     = new BlockHit();
     = new Speed();
     = new Test();
     = new NoRender();
     = new TargetStrafe();
     = new GuiMove();
     = new DojoHelper();
     = new PopupAnimation();
     = new Disabler();
     = new Scaffold();
     = new Flight();
     = new InventoryDisplay();
     = new XRay();
     = new LunarSpoofer();
     = new FullBright();
     = new EnchantGlint();
     = new PVPInfo();
     = new ArrayList<>();
     = false;
     = new ArrayList<>();
     = new HashMap<>();
  }
  
  @EventHandler
  public void (FMLPreInitializationEvent paramFMLPreInitializationEvent) {
    (new File(String.valueOf((new StringBuilder()).append(mc.field_71412_D.getPath()).append("/config/OringoClient")))).mkdir();
    (new File(String.valueOf((new StringBuilder()).append(mc.field_71412_D.getPath()).append("/config/OringoClient/capes")))).mkdir();
    (new File(String.valueOf((new StringBuilder()).append(mc.field_71412_D.getPath()).append("/config/OringoClient/configs")))).mkdir();
    File file = new File(String.valueOf((new StringBuilder()).append(mc.field_71412_D.getPath()).append("/config/OringoClient/insults.txt")));
    try {
      if (!file.exists())
        file.createNewFile(); 
    } catch (Exception exception) {}
    MoveStateUpdateEvent.();
    ();
    .(true);
    .add();
    .add(new AntiVoid());
    .add();
    .add();
    .add();
    .add();
    .add();
    .add();
    .add();
    .add(new AntiNicker());
    .add(new TerminalAura());
    .add(new ChatBypass());
    .add(new TerminatorAura());
    .add(new AutoEcho());
    .add(new SecretAura());
    .add(new DungeonESP());
    .add(new SafeWalk());
    .add(new RemoveAnnoyingMobs());
    .add(new AutoFish());
    .add(new GhostBlocks());
    .add(new SumoFences());
    .add();
    .add(new CrimsonQOL());
    .add();
    .add(new TntRunPing());
    .add();
    .add();
    .add();
    .add(new AutoS1());
    .add(new InvManager());
    .add(new ChestStealer());
    .add(new PlayerESP());
    .add(new VClip());
    .add();
    .add();
    .add();
    .add(new ChinaHat());
    .add();
    .add();
    RichPresenceModule richPresenceModule = new RichPresenceModule();
    .add(richPresenceModule);
    .add();
    .add(new CustomESP());
    .add();
    .add(new AutoRogueSword());
    .add(new GuessTheBuildAFK());
    .add(new Snowballs());
    .add(new IceFillHelp());
    .add();
    .add(Updater.());
    .add(Command.());
    .add(new WTap());
    .add(new AutoTool());
    .add();
    .add();
    .add(new ServerBeamer());
    .add();
    .add(new Blink());
    .add(new MotionBlur());
    .add();
    .add();
    .add(new MurdererFinder());
    .add(new ChestESP());
    .add();
    .add(new BoneThrower());
    .add();
    .add();
    .add(new AutoPot());
    .add();
    .add();
    .add();
    .add();
    .add(new NoFall());
    .add(new Step());
    .add();
    .add();
    .add(new AntiNukebi());
    .add(new Trial());
    .add(new AutoClicker());
    .add(new StaffAnalyser());
    .add(new ArmorSwap());
    .add();
    .add(new AimAssist());
    .add(ServerBeamer.());
    .add(new AutoHeal());
    .add(new Nametags());
    .add(new BlazeSwapper());
    .add(new Timer());
    .add(new KillInsults());
    .add(Delays.);
    .add();
    .add(new Breaker());
    .add(new HidePlayers());
    .add(new SimulatorAura());
    .add(new ResetVL());
    .add(new RodStacker());
    .add(new NamesOnly());
    .add(new NoCarpet());
    .add(new CrystalPlacer());
    .add(new HClip());
    .add(new Criticals());
    .add(new AutoQuiz());
    .add(new AutoWeirdos());
    .add(new CakeNuker());
    .add(new AutoAlign());
    .add(new Sneak());
    .add(new AutoReconnect());
    .add(new LightningDetect());
    .add();
    .add(new RevTrader());
    .add(new AutoMask());
    .add(new AntiObby());
    .add(new AutoUHC());
    .add(new AutoTerminals());
    BlurUtils.registerListener();
    for (Module module : )
      MinecraftForge.EVENT_BUS.register(module); 
    Modless.((Command)new JerryBoxCommand());
    Modless.((Command)new StalkCommand());
    Modless.((Command)new WardrobeCommand());
    Modless.((Command)new HelpCommand());
    Modless.((Command)new ArmorStandsCommand());
    Modless.((Command)new ChecknameCommand());
    Modless.((Command)new ClipCommand());
    Modless.((Command)new ConfigCommand());
    Modless.((Command)new FireworkCommand());
    Modless.((Command)new SettingsCommand());
    Modless.((Command)new SayCommand());
    Modless.((Command)new CustomESPCommand());
    Modless.((Command)new XRayCommand());
    Modless.((Command)new HClipCommand());
    Modless.((Command)new NamesCommand());
    Modless.((Command)new PlayerListCommand());
    Modless.((Command)new UHCTpCommand());
    Modless.((Command)new ThreeDClipCommand());
    Modless.((Command)new TestCommand());
    MinecraftForge.EVENT_BUS.register(new Notifications());
    MinecraftForge.EVENT_BUS.register(this);
    MinecraftForge.EVENT_BUS.register(ServerUtils.);
    MinecraftForge.EVENT_BUS.register(new Updater());
    MinecraftForge.EVENT_BUS.register(new AttackQueue());
    MinecraftForge.EVENT_BUS.register(new SkyblockUtils());
    MinecraftForge.EVENT_BUS.register(new Buttons());
    MinecraftForge.EVENT_BUS.register(new BackgroundProcess());
    FastUse.();
    TargetComponent..((Command.())..(), (Command.())..());
    if (Disabler..()) {
      .(true);
      Disabler..(false);
    } 
    if (richPresenceModule.())
      richPresenceModule.(); 
    if ((new File("OringoDev")).exists())
       = true; 
    if () {
      Modless.((Command)new BanCommand());
      MinecraftForge.EVENT_BUS.register(new LoginWithSession());
    } 
    PacketUtils.();
  }
  
  public static class SkinColorException extends Exception {
    static {
    
    }
  }
}
