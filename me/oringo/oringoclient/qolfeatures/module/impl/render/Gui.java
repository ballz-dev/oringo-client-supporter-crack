package me.oringo.oringoclient.qolfeatures.module.impl.render;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import me.oringo.oringoclient.OringoClient;
import me.oringo.oringoclient.events.impl.DeathEvent;
import me.oringo.oringoclient.events.impl.PacketReceivedEvent;
import me.oringo.oringoclient.qolfeatures.module.Module;
import me.oringo.oringoclient.qolfeatures.module.impl.movement.Speed;
import me.oringo.oringoclient.qolfeatures.module.impl.other.Disabler;
import me.oringo.oringoclient.qolfeatures.module.impl.other.NoCarpet;
import me.oringo.oringoclient.qolfeatures.module.settings.Setting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.BooleanSetting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.ModeSetting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.NumberSetting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.StringSetting;
import me.oringo.oringoclient.ui.gui.ClickGUI;
import me.oringo.oringoclient.utils.MilliTimer;
import me.oringo.oringoclient.utils.font.Fonts;
import me.oringo.oringoclient.utils.font.MinecraftFontRenderer;
import me.oringo.oringoclient.utils.shader.BlurUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.potion.Potion;
import net.minecraft.util.MathHelper;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.opengl.GL11;

public class Gui extends Module {
  public BooleanSetting  = new BooleanSetting("Array line", true);
  
  public static StringSetting ;
  
  public BooleanSetting  = new BooleanSetting("HSB ", true, this::lambda$new$11);
  
  public ClickGUI  = new ClickGUI();
  
  public static MilliTimer ;
  
  public ModeSetting  = new ModeSetting("Blur strength", "Low", new String[] { "None", "Low", "High" });
  
  public static ModeSetting  = new ModeSetting("Gui theme", "Cerulean", new String[] { "Cerulean", "Teal", "Crimson", "Pink", "Black", "Cinnabar", "Dark green", "Custom" });
  
  public static BooleanSetting ;
  
  public ModeSetting  = new ModeSetting("Mode", "Color shift", new String[] { "Rainbow", "Color shift", "Astolfo", "Pulse", "Custom" });
  
  public NumberSetting  = new NumberSetting("Green 2 ", 175.0D, 0.0D, 255.0D, 1.0D, this::lambda$new$7);
  
  public BooleanSetting  = new BooleanSetting("FPS counter", false);
  
  public BooleanSetting  = new BooleanSetting("Array background", true);
  
  public BooleanSetting  = new BooleanSetting("lowercase", false, this::lambda$new$12);
  
  public BooleanSetting  = new BooleanSetting("ArrayList", false);
  
  public NumberSetting  = new NumberSetting("Blue", 255.0D, 0.0D, 255.0D, 1.0D, this::lambda$new$2);
  
  public NumberSetting  = new NumberSetting("Shift Speed ", 1.0D, 0.1D, 5.0D, 0.1D, this::lambda$new$9);
  
  public BooleanSetting  = new BooleanSetting("Toggle notifications", true);
  
  public BooleanSetting  = new BooleanSetting("Ping counter", false);
  
  public NumberSetting  = new NumberSetting("Blue 1 ", 110.0D, 0.0D, 255.0D, 1.0D, this::lambda$new$5);
  
  public NumberSetting  = new NumberSetting("Red 1 ", 0.0D, 0.0D, 255.0D, 1.0D, this::lambda$new$3);
  
  public ArrayList<Long>  = new ArrayList<>();
  
  public NumberSetting  = new NumberSetting("Rainbow Speed", 2.5D, 0.1D, 5.0D, 0.1D, this::lambda$new$10);
  
  public BooleanSetting  = new BooleanSetting("TPS counter", false);
  
  public static StringSetting ;
  
  public NumberSetting  = new NumberSetting("Green", 80.0D, 0.0D, 255.0D, 1.0D, this::lambda$new$1);
  
  public NumberSetting  = new NumberSetting("Red", 0.0D, 0.0D, 255.0D, 1.0D, this::lambda$new$0);
  
  public NumberSetting  = new NumberSetting("Blue 2 ", 255.0D, 0.0D, 255.0D, 1.0D, this::lambda$new$8);
  
  public BooleanSetting  = new BooleanSetting("BPS counter", false);
  
  public static BooleanSetting  = new BooleanSetting("Toggle buttons", false);
  
  public static long ;
  
  public NumberSetting  = new NumberSetting("Green 1 ", 255.0D, 0.0D, 255.0D, 1.0D, this::lambda$new$4);
  
  public BooleanSetting  = new BooleanSetting("Watermark", false);
  
  public BooleanSetting  = new BooleanSetting("Scale gui", false);
  
  public BooleanSetting  = new BooleanSetting("Array shadow", true);
  
  public NumberSetting  = new NumberSetting("Red 2 ", 255.0D, 0.0D, 255.0D, 1.0D, this::lambda$new$6);
  
  public float () {
    if (!this..())
      return 0.0F; 
    List list = (List)OringoClient..stream().filter(Gui::).sorted(Comparator.comparingDouble(Gui::)).collect(Collectors.toList());
    float f = 3.0F;
    for (Module module : list)
      f += (Fonts..() + 5.0F) * Math.max(Math.min(module.() ? ((float)module..() / 250.0F) : ((250.0F - (float)module..()) / 250.0F), 1.0F), 0.0F); 
    return f;
  }
  
  public Color (int paramInt) {
    float f1;
    float[] arrayOfFloat1;
    Color color;
    float[] arrayOfFloat2;
    float f2;
    switch (this..()) {
      case "Color shift":
        f1 = (float)((Math.cos((paramInt * 450.0D + System.currentTimeMillis() * this..()) / 1000.0D) + 1.0D) * 0.5D);
        if (!this..())
          return new Color((int)(this..() + (this..() - this..()) * f1), (int)(this..() + (this..() - this..()) * f1), (int)(this..() + (this..() - this..()) * f1)); 
        arrayOfFloat1 = Color.RGBtoHSB((int)this..(), (int)this..(), (int)this..(), null);
        arrayOfFloat2 = Color.RGBtoHSB((int)this..(), (int)this..(), (int)this..(), null);
        return Color.getHSBColor(arrayOfFloat1[0] + (arrayOfFloat2[0] - arrayOfFloat1[0]) * f1, arrayOfFloat1[1] + (arrayOfFloat2[1] - arrayOfFloat1[1]) * f1, arrayOfFloat1[2] + (arrayOfFloat2[2] - arrayOfFloat1[2]) * f1);
      case "Rainbow":
        return Color.getHSBColor((float)((paramInt * 100.0D + System.currentTimeMillis() * this..()) / 5000.0D % 1.0D), 0.8F, 1.0F);
      case "Pulse":
        color = new Color((int)this..(), (int)this..(), (int)this..(), 255);
        return BooleanSetting.(color, color.darker().darker(), (float)((Math.sin((paramInt * 450.0D + System.currentTimeMillis() * this..()) / 1000.0D) + 1.0D) * 0.5D));
      case "Astolfo":
        f2 = (float)((Math.cos((paramInt * 450.0D + System.currentTimeMillis() * this..()) / 1000.0D) + 1.0D) * 0.5D);
        return Color.getHSBColor(0.5F + 0.4F * f2, 0.6F, 1.0F);
    } 
    return new Color((int)this..(), (int)this..(), (int)this..(), 255);
  }
  
  public static double () {
    double d = 0.2895D;
    if (Speed.mc.field_71439_g.func_70644_a(Potion.field_76424_c))
      d *= 1.0D + 0.2D * (Speed.mc.field_71439_g.func_70660_b(Potion.field_76424_c).func_76458_c() + 1); 
    if (Speed.mc.field_71439_g.func_70644_a(Potion.field_76421_d))
      d *= 1.0D - 0.15D * (Speed.mc.field_71439_g.func_70660_b(Potion.field_76421_d).func_76458_c() + 1); 
    return d;
  }
  
  public Gui() {
    super("Click Gui", 54, Module.Category.);
    (new Setting[] { 
          (Setting), (Setting)this., (Setting)this., (Setting)this., (Setting)this., (Setting)this., (Setting)this., (Setting)this., (Setting)this., (Setting)this., 
          (Setting)this., (Setting)this., (Setting)this., (Setting)this., (Setting), (Setting)this., (Setting)this., (Setting), (Setting)this., (Setting)this., 
          (Setting)this., (Setting)this., (Setting)this., (Setting)this., (Setting)this., (Setting)this., (Setting)this., (Setting)this., (Setting)this., (Setting), 
          (Setting) });
  }
  
  @SubscribeEvent
  public void (TickEvent.ClientTickEvent paramClientTickEvent) {
    if (mc.func_147114_u() != null && .(1000L))
      DeathEvent.(); 
    if (()) {
      mc.func_147108_a((GuiScreen)this.);
      ();
    } 
  }
  
  static {
     = new BooleanSetting("Search bar", true);
     = new StringSetting("Prefix", ".", 1);
     = new StringSetting("Client Name", "", 20);
     = new MilliTimer();
  }
  
  @SubscribeEvent
  public void (RenderGameOverlayEvent.Post paramPost) {
    if (paramPost.type == RenderGameOverlayEvent.ElementType.CROSSHAIRS) {
      if (this..())
        if (.().length() == 0) {
          Fonts..("ringo", (Fonts..("O", 5.0D, 5.0D, ().getRGB()) + 1.0F), 5.0D, Color.white.getRGB());
        } else {
          float f = Fonts..(String.valueOf(.().charAt(0)), 5.0D, 5.0D, ().getRGB()) + 1.0F;
          if (.().length() > 1)
            Fonts..(.().substring(1), f, 5.0D, Color.white.getRGB()); 
        }  
      if (this..()) {
        MinecraftFontRenderer minecraftFontRenderer = Fonts.;
        GL11.glPushMatrix();
        ScaledResolution scaledResolution1 = new ScaledResolution(mc);
        List<?> list = (List)OringoClient..stream().filter(Gui::).sorted(Comparator.comparingDouble(minecraftFontRenderer::)).collect(Collectors.toList());
        Collections.reverse(list);
        float f = 2.0F;
        int i = list.size();
        for (Module module : list) {
          i--;
          GL11.glPushMatrix();
          float f3 = (float)(minecraftFontRenderer.(module.()) + 5.0D);
          float f4 = f3 * Math.max(Math.min(module.() ? ((250.0F - (float)module..()) / 250.0F) : ((float)module..() / 250.0F), 1.0F), 0.0F);
          GL11.glTranslated(f4, 0.0D, 0.0D);
          float f5 = (minecraftFontRenderer.() + 5);
          if (this..()) {
            if (this..()) {
              float f6;
              for (f6 = 0.0F; f6 < 3.0F; f6 += 0.5F)
                NoCarpet.((scaledResolution1.func_78326_a() - 1) - f3 - f6, f + f6, scaledResolution1.func_78326_a(), f + (minecraftFontRenderer.() + 5.0F) * Math.max(Math.min(module.() ? ((float)module..() / 250.0F) : ((250.0F - (float)module..()) / 250.0F), 1.0F), 0.0F) + f6, (new Color(20, 20, 20, 30)).getRGB()); 
            } 
            BlurUtils.renderBlurredBackground(3.0F, scaledResolution1.func_78326_a() - f4, scaledResolution1.func_78328_b(), (scaledResolution1.func_78326_a() - 1) - f3, f, f3, f5);
          } 
          NoCarpet.((scaledResolution1.func_78326_a() - 1) - f3, f, (scaledResolution1.func_78326_a() - 1), f + f5, (new Color(19, 19, 19, 70)).getRGB());
          minecraftFontRenderer.(this..() ? module.().toLowerCase() : module.(), ((scaledResolution1.func_78326_a() - 1) - f3 / 2.0F), (f + f5 / 2.0F - minecraftFontRenderer.() / 2.0F), (i).getRGB());
          f += (minecraftFontRenderer.() + 5) * Math.max(Math.min(module.() ? ((float)module..() / 250.0F) : ((250.0F - (float)module..()) / 250.0F), 1.0F), 0.0F);
          GL11.glPopMatrix();
        } 
        i = list.size();
        f = 2.0F;
        if (this..()) {
          Tessellator tessellator = Tessellator.func_178181_a();
          WorldRenderer worldRenderer = tessellator.func_178180_c();
          GlStateManager.func_179147_l();
          GlStateManager.func_179090_x();
          GlStateManager.func_179120_a(770, 771, 1, 0);
          GlStateManager.func_179103_j(7425);
          worldRenderer.func_181668_a(7, DefaultVertexFormats.field_181706_f);
          for (Module module : list) {
            i--;
            float f3 = (minecraftFontRenderer.() + 5.0F) * Math.max(Math.min(module.() ? ((float)module..() / 250.0F) : ((250.0F - (float)module..()) / 250.0F), 1.0F), 0.0F);
            DeathEvent.((scaledResolution1.func_78326_a() - 1), f, scaledResolution1.func_78326_a(), f + f3, (i - 1).getRGB(), (i).getRGB());
            f += f3;
          } 
          tessellator.func_78381_a();
          GlStateManager.func_179103_j(7424);
        } 
        GlStateManager.func_179098_w();
        GlStateManager.func_179084_k();
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glPopMatrix();
      } 
      try {
        if (this..size() > 0)
          this..removeIf(Gui::); 
      } catch (Exception exception) {}
      ScaledResolution scaledResolution = new ScaledResolution(mc);
      float f1 = 4.0F;
      float f2 = (scaledResolution.func_78328_b() - 14);
      if (this..())
        f1 = Fonts..(String.valueOf((new StringBuilder()).append().append("  ")), Fonts..("Ping: ", f1, f2, OringoClient..().getRGB()), f2, Color.white.getRGB()); 
      if (this..())
        f1 = Fonts..(String.valueOf((new StringBuilder()).append(()).append("  ")), Fonts..("BPS: ", f1, f2, OringoClient..().getRGB()), f2, Color.white.getRGB()); 
      if (this..())
        f1 = Fonts..(String.valueOf((new StringBuilder()).append(MathHelper.func_76125_a(this..size(), 0, 20)).append("  ")), Fonts..("TPS: ", f1, f2, OringoClient..().getRGB()), f2, Color.white.getRGB()); 
      if (this..())
        f1 = Fonts..(String.valueOf((new StringBuilder()).append(Minecraft.func_175610_ah()).append("  ")), Fonts..("FPS: ", f1, f2, OringoClient..().getRGB()), f2, Color.white.getRGB()); 
    } 
  }
  
  @SubscribeEvent
  public void (PacketReceivedEvent paramPacketReceivedEvent) {
    if (paramPacketReceivedEvent. instanceof net.minecraft.network.play.server.S32PacketConfirmTransaction) {
      this..add(Long.valueOf(System.currentTimeMillis()));
    } else if (paramPacketReceivedEvent. instanceof net.minecraft.network.play.server.S3APacketTabComplete) {
       = .();
    } 
  }
  
  public String () {
    double d = Math.hypot(mc.field_71439_g.field_70165_t - mc.field_71439_g.field_70169_q, mc.field_71439_g.field_70161_v - mc.field_71439_g.field_70166_s) * (Disabler.()).field_74278_d * 20.0D;
    return String.format("%.1f", new Object[] { Double.valueOf(d) });
  }
  
  public Color () {
    return (0);
  }
}
