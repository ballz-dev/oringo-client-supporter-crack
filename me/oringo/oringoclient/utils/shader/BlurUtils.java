package me.oringo.oringoclient.utils.shader;

import java.util.HashMap;
import java.util.HashSet;
import java.util.function.Predicate;
import me.oringo.oringoclient.OringoClient;
import me.oringo.oringoclient.commands.impl.BloodSkipCommand;
import me.oringo.oringoclient.commands.impl.ClipCommand;
import me.oringo.oringoclient.qolfeatures.module.impl.other.NoCarpet;
import me.oringo.oringoclient.qolfeatures.module.impl.player.AutoHeal;
import me.oringo.oringoclient.qolfeatures.module.impl.player.AutoTool;
import me.oringo.oringoclient.ui.hud.impl.InventoryHUD;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.client.shader.Shader;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.network.play.client.C0EPacketClickWindow;
import net.minecraft.util.Matrix4f;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL14;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;

public class BlurUtils {
  public static HashMap<Float, Long> ;
  
  public static HashMap<Float, OutputStuff>  = new HashMap<>();
  
  public static HashSet<Float> ;
  
  public static boolean ;
  
  public static Framebuffer ;
  
  public static int ;
  
  public static Matrix4f createProjectionMatrix(int paramInt1, int paramInt2) {
    Matrix4f matrix4f = new Matrix4f();
    matrix4f.setIdentity();
    matrix4f.m00 = 2.0F / paramInt1;
    matrix4f.m11 = 2.0F / -paramInt2;
    matrix4f.m22 = -0.0020001999F;
    matrix4f.m33 = 1.0F;
    matrix4f.m03 = -1.0F;
    matrix4f.m13 = 1.0F;
    matrix4f.m23 = -1.0001999F;
    return matrix4f;
  }
  
  @SubscribeEvent(priority = EventPriority.HIGHEST)
  public void (RenderGameOverlayEvent.Pre paramPre) {
    if (paramPre.type == RenderGameOverlayEvent.ElementType.ALL)
      processBlurs(); 
    Minecraft.func_71410_x().func_147110_a().func_147610_a(false);
  }
  
  public static void drawTexturedRect(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7, float paramFloat8, int paramInt) {
    GlStateManager.func_179147_l();
    GL14.glBlendFuncSeparate(770, 771, 1, 771);
    drawTexturedRectNoBlend(paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramFloat5, paramFloat6, paramFloat7, paramFloat8, paramInt);
    GlStateManager.func_179084_k();
  }
  
  public static void renderBlurredBackground(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7) {
    if (!OpenGlHelper.func_148822_b() || !OpenGlHelper.func_153193_b())
      return; 
    if (paramFloat1 < 0.5D)
      return; 
    .add(Float.valueOf(paramFloat1));
    if (.isEmpty())
      return; 
    OutputStuff outputStuff = .get(Float.valueOf(paramFloat1));
    if (outputStuff == null)
      outputStuff = .values().iterator().next(); 
    float f1 = paramFloat4 / paramFloat2;
    float f2 = (paramFloat4 + paramFloat6) / paramFloat2;
    float f3 = (paramFloat3 - paramFloat5) / paramFloat3;
    float f4 = (paramFloat3 - paramFloat5 - paramFloat7) / paramFloat3;
    GlStateManager.func_179132_a(false);
    NoCarpet.(paramFloat4, paramFloat5, paramFloat4 + paramFloat6, paramFloat5 + paramFloat7, );
    outputStuff..func_147612_c();
    GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
    drawTexturedRect(paramFloat4, paramFloat5, paramFloat6, paramFloat7, f1, f2, f3, f4, 9728);
    outputStuff..func_147606_d();
    GlStateManager.func_179132_a(true);
    GlStateManager.func_179117_G();
  }
  
  static {
     = new HashMap<>();
     = new HashSet<>();
     = 0;
     = false;
     = null;
  }
  
  public static void processBlurs() {
    // Byte code:
    //   0: invokestatic currentTimeMillis : ()J
    //   3: lstore_0
    //   4: getstatic me/oringo/oringoclient/utils/shader/BlurUtils. : Ljava/util/HashSet;
    //   7: invokevirtual iterator : ()Ljava/util/Iterator;
    //   10: astore_2
    //   11: aload_2
    //   12: invokeinterface hasNext : ()Z
    //   17: ifeq -> 181
    //   20: aload_2
    //   21: invokeinterface next : ()Ljava/lang/Object;
    //   26: checkcast java/lang/Float
    //   29: invokevirtual floatValue : ()F
    //   32: fstore_3
    //   33: getstatic me/oringo/oringoclient/utils/shader/BlurUtils. : Ljava/util/HashMap;
    //   36: fload_3
    //   37: invokestatic valueOf : (F)Ljava/lang/Float;
    //   40: lload_0
    //   41: invokestatic valueOf : (J)Ljava/lang/Long;
    //   44: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   47: pop
    //   48: invokestatic func_71410_x : ()Lnet/minecraft/client/Minecraft;
    //   51: getfield field_71443_c : I
    //   54: istore #4
    //   56: invokestatic func_71410_x : ()Lnet/minecraft/client/Minecraft;
    //   59: getfield field_71440_d : I
    //   62: istore #5
    //   64: getstatic me/oringo/oringoclient/utils/shader/BlurUtils. : Ljava/util/HashMap;
    //   67: fload_3
    //   68: invokestatic valueOf : (F)Ljava/lang/Float;
    //   71: iload #4
    //   73: iload #5
    //   75: <illegal opcode> apply : (II)Ljava/util/function/Function;
    //   80: invokevirtual computeIfAbsent : (Ljava/lang/Object;Ljava/util/function/Function;)Ljava/lang/Object;
    //   83: checkcast me/oringo/oringoclient/utils/shader/BlurUtils$OutputStuff
    //   86: astore #6
    //   88: aload #6
    //   90: getfield  : Lnet/minecraft/client/shader/Framebuffer;
    //   93: getfield field_147621_c : I
    //   96: iload #4
    //   98: if_icmpne -> 114
    //   101: aload #6
    //   103: getfield  : Lnet/minecraft/client/shader/Framebuffer;
    //   106: getfield field_147618_d : I
    //   109: iload #5
    //   111: if_icmpeq -> 172
    //   114: aload #6
    //   116: getfield  : Lnet/minecraft/client/shader/Framebuffer;
    //   119: iload #4
    //   121: iload #5
    //   123: invokevirtual func_147613_a : (II)V
    //   126: aload #6
    //   128: getfield  : Lnet/minecraft/client/shader/Shader;
    //   131: ifnull -> 149
    //   134: aload #6
    //   136: getfield  : Lnet/minecraft/client/shader/Shader;
    //   139: iload #4
    //   141: iload #5
    //   143: invokestatic createProjectionMatrix : (II)Lnet/minecraft/util/Matrix4f;
    //   146: invokevirtual func_148045_a : (Lorg/lwjgl/util/vector/Matrix4f;)V
    //   149: aload #6
    //   151: getfield  : Lnet/minecraft/client/shader/Shader;
    //   154: ifnull -> 172
    //   157: aload #6
    //   159: getfield  : Lnet/minecraft/client/shader/Shader;
    //   162: iload #4
    //   164: iload #5
    //   166: invokestatic createProjectionMatrix : (II)Lnet/minecraft/util/Matrix4f;
    //   169: invokevirtual func_148045_a : (Lorg/lwjgl/util/vector/Matrix4f;)V
    //   172: aload #6
    //   174: fload_3
    //   175: invokestatic blurBackground : (Lme/oringo/oringoclient/utils/shader/BlurUtils$OutputStuff;F)V
    //   178: goto -> 11
    //   181: new java/util/HashSet
    //   184: dup
    //   185: invokespecial <init> : ()V
    //   188: astore_2
    //   189: getstatic me/oringo/oringoclient/utils/shader/BlurUtils. : Ljava/util/HashMap;
    //   192: invokevirtual entrySet : ()Ljava/util/Set;
    //   195: invokeinterface iterator : ()Ljava/util/Iterator;
    //   200: astore_3
    //   201: aload_3
    //   202: invokeinterface hasNext : ()Z
    //   207: ifeq -> 263
    //   210: aload_3
    //   211: invokeinterface next : ()Ljava/lang/Object;
    //   216: checkcast java/util/Map$Entry
    //   219: astore #4
    //   221: lload_0
    //   222: aload #4
    //   224: invokeinterface getValue : ()Ljava/lang/Object;
    //   229: checkcast java/lang/Long
    //   232: invokevirtual longValue : ()J
    //   235: lsub
    //   236: ldc2_w 30000
    //   239: lcmp
    //   240: ifle -> 260
    //   243: aload_2
    //   244: aload #4
    //   246: invokeinterface getKey : ()Ljava/lang/Object;
    //   251: checkcast java/lang/Float
    //   254: invokeinterface add : (Ljava/lang/Object;)Z
    //   259: pop
    //   260: goto -> 201
    //   263: getstatic me/oringo/oringoclient/utils/shader/BlurUtils. : Ljava/util/HashMap;
    //   266: invokevirtual entrySet : ()Ljava/util/Set;
    //   269: invokeinterface iterator : ()Ljava/util/Iterator;
    //   274: astore_3
    //   275: aload_3
    //   276: invokeinterface hasNext : ()Z
    //   281: ifeq -> 362
    //   284: aload_3
    //   285: invokeinterface next : ()Ljava/lang/Object;
    //   290: checkcast java/util/Map$Entry
    //   293: astore #4
    //   295: aload_2
    //   296: aload #4
    //   298: invokeinterface getKey : ()Ljava/lang/Object;
    //   303: invokeinterface contains : (Ljava/lang/Object;)Z
    //   308: ifeq -> 359
    //   311: aload #4
    //   313: invokeinterface getValue : ()Ljava/lang/Object;
    //   318: checkcast me/oringo/oringoclient/utils/shader/BlurUtils$OutputStuff
    //   321: getfield  : Lnet/minecraft/client/shader/Framebuffer;
    //   324: invokevirtual func_147608_a : ()V
    //   327: aload #4
    //   329: invokeinterface getValue : ()Ljava/lang/Object;
    //   334: checkcast me/oringo/oringoclient/utils/shader/BlurUtils$OutputStuff
    //   337: getfield  : Lnet/minecraft/client/shader/Shader;
    //   340: invokevirtual func_148044_b : ()V
    //   343: aload #4
    //   345: invokeinterface getValue : ()Ljava/lang/Object;
    //   350: checkcast me/oringo/oringoclient/utils/shader/BlurUtils$OutputStuff
    //   353: getfield  : Lnet/minecraft/client/shader/Shader;
    //   356: invokevirtual func_148044_b : ()V
    //   359: goto -> 275
    //   362: getstatic me/oringo/oringoclient/utils/shader/BlurUtils. : Ljava/util/HashMap;
    //   365: invokevirtual keySet : ()Ljava/util/Set;
    //   368: aload_2
    //   369: invokeinterface removeAll : (Ljava/util/Collection;)Z
    //   374: pop
    //   375: getstatic me/oringo/oringoclient/utils/shader/BlurUtils. : Ljava/util/HashMap;
    //   378: invokevirtual keySet : ()Ljava/util/Set;
    //   381: aload_2
    //   382: invokeinterface removeAll : (Ljava/util/Collection;)Z
    //   387: pop
    //   388: getstatic me/oringo/oringoclient/utils/shader/BlurUtils. : Ljava/util/HashSet;
    //   391: invokevirtual clear : ()V
    //   394: return
  }
  
  @SubscribeEvent
  public void (EntityViewRenderEvent.FogColors paramFogColors) {
     = -16777216;
     |= ((int)(paramFogColors.red * 255.0F) & 0xFF) << 16;
     |= ((int)(paramFogColors.green * 255.0F) & 0xFF) << 8;
     |= (int)(paramFogColors.blue * 255.0F) & 0xFF;
  }
  
  public static int (Entity paramEntity, boolean paramBoolean) {
    int i = OringoClient.mc.field_71439_g.field_71071_by.field_70461_c;
    float f = InventoryHUD.(paramEntity, OringoClient.mc.field_71439_g.field_71071_by.func_70301_a(i), paramBoolean);
    for (byte b = 0; b < 9; b++) {
      float f1 = InventoryHUD.(paramEntity, OringoClient.mc.field_71439_g.field_71071_by.func_70301_a(b), paramBoolean);
      if (f1 > f) {
        f = f1;
        i = b;
      } 
    } 
    return i;
  }
  
  public static void registerListener() {
    if (!) {
       = true;
      MinecraftForge.EVENT_BUS.register(new BlurUtils());
    } 
  }
  
  public static boolean (Predicate<ItemStack> paramPredicate, boolean paramBoolean) {
    int i;
    if (AutoHeal..()) {
      i = AutoTool.(paramPredicate);
      if (i != -1)
        if (i >= 36) {
          AutoHeal.mc.func_147114_u().func_147298_b().func_179290_a((Packet)new C09PacketHeldItemChange(i - 36));
          if (paramBoolean) {
            BloodSkipCommand.();
          } else {
            AutoHeal.mc.func_147114_u().func_147298_b().func_179290_a((Packet)new C08PacketPlayerBlockPlacement(AutoHeal.mc.field_71439_g.field_71069_bz.func_75139_a(i).func_75211_c()));
          } 
          AutoHeal.mc.func_147114_u().func_147298_b().func_179290_a((Packet)new C09PacketHeldItemChange(AutoHeal.mc.field_71439_g.field_71071_by.field_70461_c));
        } else {
          short s = AutoHeal.mc.field_71439_g.field_71070_bA.func_75136_a(AutoHeal.mc.field_71439_g.field_71071_by);
          AutoHeal.mc.func_147114_u().func_147298_b().func_179290_a((Packet)new C0EPacketClickWindow(AutoHeal.mc.field_71439_g.field_71070_bA.field_75152_c, i, AutoHeal.mc.field_71439_g.field_71071_by.field_70461_c, 2, AutoHeal.mc.field_71439_g.field_71070_bA.func_75139_a(i).func_75211_c(), s));
          if (paramBoolean) {
            BloodSkipCommand.();
          } else {
            AutoHeal.mc.func_147114_u().func_147298_b().func_179290_a((Packet)new C08PacketPlayerBlockPlacement(AutoHeal.mc.field_71439_g.field_71069_bz.func_75139_a(i).func_75211_c()));
          } 
          AutoHeal.mc.func_147114_u().func_147298_b().func_179290_a((Packet)new C0EPacketClickWindow(AutoHeal.mc.field_71439_g.field_71070_bA.field_75152_c, i, AutoHeal.mc.field_71439_g.field_71071_by.field_70461_c, 2, AutoHeal.mc.field_71439_g.field_71070_bA.func_75139_a(i).func_75211_c(), s));
        }  
    } else {
      i = ClipCommand.(paramPredicate);
      if (i != -1) {
        AutoHeal.mc.func_147114_u().func_147298_b().func_179290_a((Packet)new C09PacketHeldItemChange(i));
        if (paramBoolean) {
          BloodSkipCommand.();
        } else {
          AutoHeal.mc.func_147114_u().func_147298_b().func_179290_a((Packet)new C08PacketPlayerBlockPlacement(AutoHeal.mc.field_71439_g.field_71071_by.func_70301_a(i)));
        } 
        AutoHeal.mc.func_147114_u().func_147298_b().func_179290_a((Packet)new C09PacketHeldItemChange(AutoHeal.mc.field_71439_g.field_71071_by.field_70461_c));
      } 
    } 
    return (i != -1);
  }
  
  public static void drawTexturedRectNoBlend(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7, float paramFloat8, int paramInt) {
    GlStateManager.func_179098_w();
    GL11.glTexParameteri(3553, 10241, paramInt);
    GL11.glTexParameteri(3553, 10240, paramInt);
    Tessellator tessellator = Tessellator.func_178181_a();
    WorldRenderer worldRenderer = tessellator.func_178180_c();
    worldRenderer.func_181668_a(7, DefaultVertexFormats.field_181707_g);
    worldRenderer.func_181662_b(paramFloat1, (paramFloat2 + paramFloat4), 0.0D).func_181673_a(paramFloat5, paramFloat8).func_181675_d();
    worldRenderer.func_181662_b((paramFloat1 + paramFloat3), (paramFloat2 + paramFloat4), 0.0D).func_181673_a(paramFloat6, paramFloat8).func_181675_d();
    worldRenderer.func_181662_b((paramFloat1 + paramFloat3), paramFloat2, 0.0D).func_181673_a(paramFloat6, paramFloat7).func_181675_d();
    worldRenderer.func_181662_b(paramFloat1, paramFloat2, 0.0D).func_181673_a(paramFloat5, paramFloat7).func_181675_d();
    tessellator.func_78381_a();
    GL11.glTexParameteri(3553, 10241, 9728);
    GL11.glTexParameteri(3553, 10240, 9728);
  }
  
  public static void renderBlurredBackground(float paramFloat) {
    ScaledResolution scaledResolution = new ScaledResolution(Minecraft.func_71410_x());
    renderBlurredBackground(paramFloat, scaledResolution.func_78326_a(), scaledResolution.func_78328_b(), 0.0F, 0.0F, scaledResolution.func_78326_a(), scaledResolution.func_78328_b());
  }
  
  public static void blurBackground(OutputStuff paramOutputStuff, float paramFloat) {
    if (!OpenGlHelper.func_148822_b() || !OpenGlHelper.func_153193_b())
      return; 
    int i = (Minecraft.func_71410_x()).field_71443_c;
    int j = (Minecraft.func_71410_x()).field_71440_d;
    GlStateManager.func_179128_n(5889);
    GlStateManager.func_179096_D();
    GlStateManager.func_179130_a(0.0D, i, j, 0.0D, 1000.0D, 3000.0D);
    GlStateManager.func_179128_n(5888);
    GlStateManager.func_179096_D();
    GlStateManager.func_179109_b(0.0F, 0.0F, -2000.0F);
    if ( == null) {
       = new Framebuffer(i, j, false);
      .func_147607_a(9728);
    } 
    if ( == null || paramOutputStuff == null)
      return; 
    if (.field_147621_c != i || .field_147618_d != j) {
      .func_147613_a(i, j);
      Minecraft.func_71410_x().func_147110_a().func_147610_a(false);
    } 
    if (paramOutputStuff. == null)
      try {
        paramOutputStuff. = new Shader(Minecraft.func_71410_x().func_110442_L(), "blur", paramOutputStuff., );
        paramOutputStuff..func_148043_c().func_147991_a("BlurDir").func_148087_a(1.0F, 0.0F);
        paramOutputStuff..func_148045_a((Matrix4f)createProjectionMatrix(i, j));
      } catch (Exception exception) {} 
    if (paramOutputStuff. == null)
      try {
        paramOutputStuff. = new Shader(Minecraft.func_71410_x().func_110442_L(), "blur", , paramOutputStuff.);
        paramOutputStuff..func_148043_c().func_147991_a("BlurDir").func_148087_a(0.0F, 1.0F);
        paramOutputStuff..func_148045_a((Matrix4f)createProjectionMatrix(i, j));
      } catch (Exception exception) {} 
    if (paramOutputStuff. != null && paramOutputStuff. != null) {
      if (paramOutputStuff..func_148043_c().func_147991_a("Radius") == null)
        return; 
      paramOutputStuff..func_148043_c().func_147991_a("Radius").func_148090_a(paramFloat);
      paramOutputStuff..func_148043_c().func_147991_a("Radius").func_148090_a(paramFloat);
      GL11.glPushMatrix();
      GL30.glBindFramebuffer(36008, (Minecraft.func_71410_x().func_147110_a()).field_147616_f);
      GL30.glBindFramebuffer(36009, paramOutputStuff..field_147616_f);
      GL30.glBlitFramebuffer(0, 0, i, j, 0, 0, paramOutputStuff..field_147621_c, paramOutputStuff..field_147618_d, 16384, 9728);
      paramOutputStuff..func_148042_a(0.0F);
      paramOutputStuff..func_148042_a(0.0F);
      GlStateManager.func_179126_j();
      GL11.glPopMatrix();
    } 
    Minecraft.func_71410_x().func_147110_a().func_147610_a(false);
  }
  
  public static class OutputStuff {
    public Shader  = null;
    
    public Shader  = null;
    
    public Framebuffer ;
    
    public OutputStuff(Framebuffer param1Framebuffer, Shader param1Shader1, Shader param1Shader2) {
      this. = param1Framebuffer;
      this. = param1Shader1;
      this. = param1Shader2;
    }
  }
}
