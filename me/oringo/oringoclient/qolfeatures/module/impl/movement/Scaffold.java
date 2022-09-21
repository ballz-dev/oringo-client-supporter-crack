package me.oringo.oringoclient.qolfeatures.module.impl.movement;

import java.awt.Font;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;
import me.oringo.oringoclient.OringoClient;
import me.oringo.oringoclient.commands.CommandHandler;
import me.oringo.oringoclient.events.impl.KeyPressEvent;
import me.oringo.oringoclient.events.impl.MotionUpdateEvent;
import me.oringo.oringoclient.events.impl.MoveEvent;
import me.oringo.oringoclient.events.impl.PacketSentEvent;
import me.oringo.oringoclient.lunarspoof.LunarClient;
import me.oringo.oringoclient.qolfeatures.module.Module;
import me.oringo.oringoclient.qolfeatures.module.impl.other.GuessTheBuildAFK;
import me.oringo.oringoclient.qolfeatures.module.impl.player.ChestStealer;
import me.oringo.oringoclient.qolfeatures.module.impl.render.CustomESP;
import me.oringo.oringoclient.qolfeatures.module.impl.render.MotionBlur;
import me.oringo.oringoclient.qolfeatures.module.impl.render.NoRender;
import me.oringo.oringoclient.qolfeatures.module.settings.Setting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.BooleanSetting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.ModeSetting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.NumberSetting;
import me.oringo.oringoclient.utils.PlayerUtils;
import me.oringo.oringoclient.utils.font.Fonts;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;

public class Scaffold extends Module {
  public static BooleanSetting ;
  
  public static ModeSetting ;
  
  public int ;
  
  public static BooleanSetting ;
  
  public static NumberSetting ;
  
  public float  = 81.0F;
  
  public static NumberSetting ;
  
  public static ModeSetting ;
  
  public static BooleanSetting ;
  
  public int ;
  
  public static NumberSetting  = new NumberSetting("Range", 4.5D, 1.0D, 4.5D, 0.1D);
  
  public static NumberSetting ;
  
  public static BooleanSetting ;
  
  public static NumberSetting  = new NumberSetting("Timer", 1.0D, 1.0D, 3.0D, 0.05D);
  
  @SubscribeEvent
  public void (PacketSentEvent paramPacketSentEvent) {}
  
  public void () {
    if (mc.field_71439_g != null)
      PlayerUtils.((float).()); 
    this. = 0;
    this. = 0;
    this. = 82.0F;
  }
  
  public BlockPos () {
    ArrayList<Vec3> arrayList = new ArrayList();
    int i = (int)Math.ceil(.());
    for (int j = -i; j <= i; j++) {
      for (int k = -i + 2; k < 0; k++) {
        for (int m = -i; m <= i; m++) {
          Vec3 vec3 = (new Vec3(j, k, m)).func_72441_c(mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u, mc.field_71439_g.field_70161_v);
          BlockPos blockPos = new BlockPos(vec3);
          if (mc.field_71441_e.func_180495_p(blockPos).func_177230_c().func_149730_j())
            arrayList.add(vec3); 
        } 
      } 
    } 
    if (arrayList.isEmpty())
      return null; 
    arrayList.sort(Comparator.comparingDouble(Scaffold::));
    return new BlockPos(arrayList.get(0));
  }
  
  public static void (double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, int paramInt) {
    GlStateManager.func_179147_l();
    GlStateManager.func_179090_x();
    GlStateManager.func_179120_a(770, 771, 1, 0);
    float f1 = (paramInt >> 24 & 0xFF) / 255.0F;
    float f2 = (paramInt >> 16 & 0xFF) / 255.0F;
    float f3 = (paramInt >> 8 & 0xFF) / 255.0F;
    float f4 = (paramInt & 0xFF) / 255.0F;
    GL11.glPushAttrib(0);
    GL11.glScaled(0.5D, 0.5D, 0.5D);
    paramDouble1 *= 2.0D;
    paramDouble2 *= 2.0D;
    paramDouble3 *= 2.0D;
    paramDouble4 *= 2.0D;
    GL11.glDisable(3553);
    GL11.glColor4f(f2, f3, f4, f1);
    GL11.glEnable(2848);
    GL11.glBegin(9);
    byte b;
    for (b = 0; b <= 90; b += 3)
      GL11.glVertex2d(paramDouble1 + paramDouble5 + Math.sin(b * Math.PI / 180.0D) * paramDouble5 * -1.0D, paramDouble2 + paramDouble5 + Math.cos(b * Math.PI / 180.0D) * paramDouble5 * -1.0D); 
    for (b = 90; b <= '´'; b += 3)
      GL11.glVertex2d(paramDouble1 + paramDouble5 + Math.sin(b * Math.PI / 180.0D) * paramDouble5 * -1.0D, paramDouble4 - paramDouble5 + Math.cos(b * Math.PI / 180.0D) * paramDouble5 * -1.0D); 
    for (b = 0; b <= 90; b += 3)
      GL11.glVertex2d(paramDouble3 - paramDouble5 + Math.sin(b * Math.PI / 180.0D) * paramDouble5, paramDouble4 - paramDouble5 + Math.cos(b * Math.PI / 180.0D) * paramDouble5); 
    for (b = 90; b <= '´'; b += 3)
      GL11.glVertex2d(paramDouble3 - paramDouble5 + Math.sin(b * Math.PI / 180.0D) * paramDouble5, paramDouble2 + paramDouble5 + Math.cos(b * Math.PI / 180.0D) * paramDouble5); 
    GL11.glEnd();
    GL11.glEnable(3553);
    GL11.glDisable(2848);
    GL11.glEnable(3553);
    GL11.glScaled(2.0D, 2.0D, 2.0D);
    GL11.glPopAttrib();
    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    GlStateManager.func_179098_w();
    GlStateManager.func_179084_k();
  }
  
  public static boolean () {
    for (int i = MathHelper.func_76128_c((OringoClient.mc.field_71439_g.func_174813_aQ()).field_72340_a); i < MathHelper.func_76128_c((OringoClient.mc.field_71439_g.func_174813_aQ()).field_72336_d) + 1; i++) {
      for (int j = MathHelper.func_76128_c((OringoClient.mc.field_71439_g.func_174813_aQ()).field_72338_b); j < MathHelper.func_76128_c((OringoClient.mc.field_71439_g.func_174813_aQ()).field_72337_e) + 1; j++) {
        for (int k = MathHelper.func_76128_c((OringoClient.mc.field_71439_g.func_174813_aQ()).field_72339_c); k < MathHelper.func_76128_c((OringoClient.mc.field_71439_g.func_174813_aQ()).field_72334_f) + 1; k++) {
          Block block = OringoClient.mc.field_71441_e.func_180495_p(new BlockPos(i, j, k)).func_177230_c();
          if (block != null && !(block instanceof net.minecraft.block.BlockAir)) {
            AxisAlignedBB axisAlignedBB = block.func_180640_a((World)OringoClient.mc.field_71441_e, new BlockPos(i, j, k), OringoClient.mc.field_71441_e.func_180495_p(new BlockPos(i, j, k)));
            if (axisAlignedBB != null && OringoClient.mc.field_71439_g.func_174813_aQ().func_72326_a(axisAlignedBB))
              return true; 
          } 
        } 
      } 
    } 
    return false;
  }
  
  public Scaffold() {
    super("Scaffold", Module.Category.);
    .(Scaffold::);
    (new Setting[] { 
          (Setting), (Setting), (Setting), (Setting), (Setting), (Setting), (Setting), (Setting), (Setting), (Setting), 
          (Setting) });
  }
  
  public int () {
    for (byte b = 0; b < 9; b++) {
      ItemStack itemStack = mc.field_71439_g.field_71071_by.func_70301_a(b);
      if (itemStack != null && itemStack.func_77973_b() instanceof ItemBlock && ((ItemBlock)itemStack.func_77973_b()).field_150939_a.func_149730_j())
        return b; 
    } 
    return -1;
  }
  
  public static void () {
    MotionBlur.mc.field_71460_t.func_175069_a(new ResourceLocation("motionblur", "motionblur"));
    MotionBlur. = MotionBlur..();
  }
  
  @SubscribeEvent
  public void (MoveEvent paramMoveEvent) {
    if (()) {
      if (mc.field_71439_g.field_70122_E) {
        double d;
        switch (.()) {
          case "Semi":
            d = 0.2575D;
            break;
          case "Sprint":
            d = 0.2895D;
            break;
          default:
            d = 0.225D;
            break;
        } 
        LunarClient.(paramMoveEvent, Math.min(d, BooleanSetting.()));
      } 
      if (.("Hypixel") && (mc.field_71474_y.field_74314_A.func_151470_d() || this. > 1) && () != -1 && NoRender.(1.2000000476837158D)) {
        if (!.())
          paramMoveEvent.setZ(0.0D).setX(0.0D); 
        paramMoveEvent.setY(0.0D);
        mc.field_71439_g.field_70181_x = 0.0D;
        switch (this.) {
          case 0:
            if (!mc.field_71439_g.field_70122_E || mc.field_71439_g.field_70163_u % 1.0D != 0.0D)
              return; 
          case 1:
            paramMoveEvent.setY(0.41999998688697815D);
            this. = 2;
            break;
          case 2:
            paramMoveEvent.setY(0.3331999936342218D);
            this. = 3;
            break;
          case 3:
            paramMoveEvent.setY(Math.ceil(mc.field_71439_g.field_70163_u) - mc.field_71439_g.field_70163_u);
            this. = 1;
            break;
        } 
        mc.field_71439_g.field_70181_x = paramMoveEvent.getY();
      } else {
        this. = 0;
      } 
    } 
  }
  
  public static void (int paramInt) {
    GL11.glColorMask(true, true, true, true);
    GL11.glStencilFunc(514, paramInt, 1);
    GL11.glStencilOp(7680, 7680, 7680);
  }
  
  @SubscribeEvent
  public void (MotionUpdateEvent paramMotionUpdateEvent) {
    if (() && !KeyPressEvent.())
      if (paramMotionUpdateEvent.isPre()) {
        paramMotionUpdateEvent.setYaw(ChestStealer.() + 180.0F).setPitch(this.);
        for (float f = 81.0F; f > 72.0F; f -= 0.5F) {
          MovingObjectPosition movingObjectPosition = CommandHandler.(paramMotionUpdateEvent., f);
          if (movingObjectPosition != null) {
            paramMotionUpdateEvent.setPitch(f);
            break;
          } 
        } 
        this. = paramMotionUpdateEvent.;
        if (mc.field_71474_y.field_74314_A.func_151470_d()) {
          if (.("Hypixel"))
            paramMotionUpdateEvent. = 90.0F; 
          PlayerUtils.((float).());
        } else {
          PlayerUtils.((float).());
        } 
      } else {
        int i = ();
        if (i == -1)
          return; 
        mc.func_147114_u().func_147298_b().func_179290_a((Packet)new C09PacketHeldItemChange(i));
        if (this. <= 0) {
          MovingObjectPosition movingObjectPosition = GuessTheBuildAFK.(paramMotionUpdateEvent.getRotation());
          if (movingObjectPosition != null && movingObjectPosition.field_72313_a == MovingObjectPosition.MovingObjectType.BLOCK && mc.field_71441_e.func_180495_p(movingObjectPosition.func_178782_a()).func_177230_c().func_149730_j()) {
            if (mc.field_71474_y.field_74314_A.func_151470_d() && .("Hypixel")) {
              if (!())
                CustomESP.(i, paramMotionUpdateEvent.getRotation()); 
            } else if (mc.field_71442_b.func_178890_a(mc.field_71439_g, mc.field_71441_e, mc.field_71439_g.field_71071_by.func_70301_a(i), movingObjectPosition.func_178782_a(), movingObjectPosition.field_178784_b, movingObjectPosition.field_72307_f)) {
              mc.field_71439_g.func_71038_i();
            } 
            this. = (int)(.() + (new Random()).nextInt((int)(.() - .() + 1.0D)));
            if (mc.field_71439_g.field_71071_by.func_70301_a(i) != null && (mc.field_71439_g.field_71071_by.func_70301_a(i)).field_77994_a <= 0)
              mc.field_71439_g.field_71071_by.func_70304_b(i); 
          } 
        } 
        this.--;
      }  
  }
  
  public static String (String paramString) {
    try {
      HttpURLConnection httpURLConnection = MoveEvent.(new URL(String.format("https://plancke.io/hypixel/player/stats/%s", new Object[] { paramString })));
      if (httpURLConnection != null) {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
        StringBuilder stringBuilder = new StringBuilder();
        String str;
        while ((str = bufferedReader.readLine()) != null)
          stringBuilder.append(str).append("\n"); 
        return String.valueOf(stringBuilder);
      } 
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
    return "";
  }
  
  static {
     = new NumberSetting("Tower timer", 1.0D, 1.0D, 3.0D, 0.05D);
     = new NumberSetting("Max delay", 1.0D, 0.0D, 4.0D, 1.0D) {
        static {
        
        }
        
        public void (double param1Double) {
          super.(param1Double);
          if (() < Scaffold..())
            super.(Scaffold..()); 
        }
      };
     = new NumberSetting("Min delay", 1.0D, 0.0D, 4.0D, 1.0D) {
        static {
        
        }
        
        public void (double param1Double) {
          super.(param1Double);
          if (() > Scaffold..())
            super.(Scaffold..()); 
        }
      };
     = new BooleanSetting("Safe walk", true);
     = new BooleanSetting("Disable speed", true);
     = new BooleanSetting("Disable aura", true);
     = new BooleanSetting("Tower move", false);
     = new ModeSetting("Tower", "None", new String[] { "None", "Hypixel" });
     = new ModeSetting("Sprint", "Semi", new String[] { "None", "Semi", "Sprint" });
  }
  
  public static Font (String paramString, int paramInt) {
    Font font;
    try {
      if (Fonts..containsKey(paramString)) {
        font = ((Font)Fonts..get(paramString)).deriveFont(0, paramInt);
      } else {
        InputStream inputStream = Minecraft.func_71410_x().func_110442_L().func_110536_a(new ResourceLocation("oringoclient", String.valueOf((new StringBuilder()).append("fonts/").append(paramString)))).func_110527_b();
        font = Font.createFont(0, inputStream);
        Fonts..put(paramString, font);
        font = font.deriveFont(0, paramInt);
      } 
    } catch (Exception exception) {
      exception.printStackTrace();
      System.out.println("Error loading font");
      font = new Font("default", 0, paramInt);
    } 
    return font;
  }
  
  public void () {
    if (mc.field_71439_g != null) {
      PlayerUtils.(1.0F);
      mc.func_147114_u().func_147298_b().func_179290_a((Packet)new C09PacketHeldItemChange(mc.field_71439_g.field_71071_by.field_70461_c));
    } 
  }
  
  public static class BlockPlaceData {
    public BlockPos ;
    
    public BlockPos ;
    
    public BlockPlaceData(BlockPos param1BlockPos1, BlockPos param1BlockPos2) {
      this. = param1BlockPos1;
      this. = param1BlockPos2;
    }
  }
}
