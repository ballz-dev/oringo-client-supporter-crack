package me.oringo.oringoclient.ui.gui;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import me.oringo.oringoclient.OringoClient;
import me.oringo.oringoclient.events.impl.MoveFlyingEvent;
import me.oringo.oringoclient.mixins.MinecraftAccessor;
import me.oringo.oringoclient.qolfeatures.module.Module;
import me.oringo.oringoclient.qolfeatures.module.impl.keybinds.Keybind;
import me.oringo.oringoclient.qolfeatures.module.impl.movement.NoSlow;
import me.oringo.oringoclient.qolfeatures.module.impl.other.HClip;
import me.oringo.oringoclient.qolfeatures.module.impl.other.NoCarpet;
import me.oringo.oringoclient.qolfeatures.module.impl.player.AntiObby;
import me.oringo.oringoclient.qolfeatures.module.impl.render.ChinaHat;
import me.oringo.oringoclient.qolfeatures.module.impl.render.Gui;
import me.oringo.oringoclient.qolfeatures.module.impl.render.HidePlayers;
import me.oringo.oringoclient.qolfeatures.module.impl.skyblock.AutoMask;
import me.oringo.oringoclient.qolfeatures.module.settings.Setting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.BooleanSetting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.ModeSetting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.NumberSetting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.RunnableSetting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.StringSetting;
import me.oringo.oringoclient.ui.hud.DraggableComponent;
import me.oringo.oringoclient.utils.MilliTimer;
import me.oringo.oringoclient.utils.font.Fonts;
import me.oringo.oringoclient.utils.shader.BlurUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public class ClickGUI extends GuiScreen {
  public Panel ;
  
  public NumberSetting ;
  
  public StringSetting ;
  
  public float ;
  
  public MilliTimer  = new MilliTimer();
  
  public float ;
  
  public StringSetting  = new StringSetting("Search");
  
  public static ArrayList<Panel> ;
  
  public int ;
  
  public Module  = null;
  
  public void func_146280_a(Minecraft paramMinecraft, int paramInt1, int paramInt2) {
    this..();
    super.func_146280_a(paramMinecraft, paramInt1, paramInt2);
  }
  
  public static void (double paramDouble) {
    if (!HidePlayers.())
      return; 
    double d = DraggableComponent.();
    OringoClient.mc.field_71439_g.field_70159_w = -Math.sin(d) * paramDouble;
    OringoClient.mc.field_71439_g.field_70179_y = Math.cos(d) * paramDouble;
  }
  
  public void func_146281_b() {
    this. = null;
    this. = null;
    this. = null;
    this. = null;
    super.func_146281_b();
  }
  
  public void func_146274_d() throws IOException {
    super.func_146274_d();
    int i = Mouse.getEventDWheel();
    if (i != 0) {
      if (i > 1)
        i = 1; 
      if (i < -1)
        i = -1; 
      int j = Mouse.getX() * this.field_146294_l / this.field_146297_k.field_71443_c;
      int k = this.field_146295_m - Mouse.getY() * this.field_146295_m / this.field_146297_k.field_71440_d - 1;
      handle(j, k, i, 0.0F, Handle.);
    } 
  }
  
  public int getTotalSettingsCount(Panel paramPanel) {
    byte b = 0;
    for (Module module : NoSlow.(paramPanel.).stream().filter(this::lambda$getTotalSettingsCount$2).collect(Collectors.toList())) {
      if (module.() && !OringoClient.)
        continue; 
      b++;
      if (module.) {
        for (Setting setting : module.) {
          if (!setting.())
            b++; 
        } 
        b++;
      } 
    } 
    return b;
  }
  
  public boolean isHovered(int paramInt1, int paramInt2, double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4) {
    return (paramInt1 > paramDouble1 && paramInt1 < paramDouble1 + paramDouble4 && paramInt2 > paramDouble2 && paramInt2 < paramDouble2 + paramDouble3);
  }
  
  public void func_146269_k() throws IOException {
    for (Panel panel : )
      panel. = panel.; 
    super.func_146269_k();
  }
  
  public ClickGUI() {
     = new ArrayList<>();
    byte b1 = 100;
    byte b2 = 20;
    int i = 100;
    byte b3 = 10;
    for (Module.Category category : Module.Category.values()) {
      .add(new Panel(category, i, b3, b1, b2));
      i += b1 + 10;
    } 
  }
  
  public void func_73869_a(char paramChar, int paramInt) throws IOException {
    AntiObby.();
    int i = Mouse.getX() * this.field_146294_l / this.field_146297_k.field_71443_c;
    int j = this.field_146295_m - Mouse.getY() * this.field_146295_m / this.field_146297_k.field_71440_d - 1;
    if (paramInt == 1 || paramInt == OringoClient..()) {
      if (this. != null) {
        this..(0);
        this. = null;
      } else if (this. != null) {
        this. = null;
      } else {
        this. = null;
        this. = null;
        super.func_73869_a(paramChar, paramInt);
      } 
    } else if (this. != null) {
      this..(paramInt);
      this. = null;
    } else if (this. != null) {
      if (paramInt == 28) {
        this. = null;
      } else if (paramInt == 47 && (Keyboard.isKeyDown(157) || Keyboard.isKeyDown(29))) {
        this..(String.valueOf((new StringBuilder()).append(this..()).append(func_146277_j())));
      } else if (paramInt != 14) {
        this..(String.valueOf((new StringBuilder()).append(this..()).append(paramChar)));
      } else if (Keyboard.isKeyDown(157) || Keyboard.isKeyDown(29)) {
        this..(this..().substring(0, Math.max(0, this..().lastIndexOf(" "))));
      } else {
        this..(this..().substring(0, Math.max(0, this..().length() - 1)));
      } 
    } 
    handle(i, j, paramInt, 0.0F, Handle.);
  }
  
  public static void (ResourceLocation paramResourceLocation, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5) {
    GL11.glPushMatrix();
    GL11.glDisable(2929);
    GL11.glEnable(3042);
    GL11.glDepthMask(false);
    OpenGlHelper.func_148821_a(770, 771, 1, 0);
    GL11.glColor4f(1.0F, 1.0F, 1.0F, paramFloat5);
    OringoClient.mc.func_110434_K().func_110577_a(paramResourceLocation);
    GL11.glTexParameteri(3553, 10241, 9729);
    GL11.glTexParameteri(3553, 10240, 9729);
    MoveFlyingEvent.(paramFloat1, paramFloat2, 0.0F, 0.0F, paramFloat3, paramFloat4, paramFloat3, paramFloat4);
    GL11.glDepthMask(true);
    GL11.glDisable(3042);
    GL11.glEnable(2929);
    GL11.glPopMatrix();
    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
  }
  
  public void handle(int paramInt1, int paramInt2, int paramInt3, float paramFloat, Handle paramHandle) {
    Color color1 = Color.cyan;
    Color color2 = Color.white;
    Color color3 = Color.getHSBColor(0.0F, 0.0F, 0.1F);
    Color color4 = new Color(143, 143, 143, 255);
    switch (Gui..()) {
      case "Cerulean":
        color1 = new Color(0.0F, 0.42F, 0.65F);
        break;
      case "Teal":
        color1 = new Color(59, 172, 182);
        break;
      case "Crimson":
        color1 = new Color(0.86F, 0.08F, 0.24F);
        break;
      case "Pink":
        color1 = new Color(0.99F, 0.42F, 0.62F);
        break;
      case "Black":
        color1 = Color.black;
        break;
      case "Dark green":
        color1 = new Color(0.09F, 0.39F, 0.27F);
        break;
      case "Cinnabar":
        color1 = new Color(0.89F, 0.26F, 0.2F);
        break;
      case "Custom":
        color1 = OringoClient..();
        break;
    } 
    ScaledResolution scaledResolution = new ScaledResolution(this.field_146297_k);
    double d = 2.0D / scaledResolution.func_78325_e();
    GL11.glPushMatrix();
    if (paramHandle == Handle.) {
      byte b = 0;
      switch (OringoClient...()) {
        case "Low":
          b = 3;
          break;
        case "High":
          b = 7;
          break;
      } 
      BlurUtils.renderBlurredBackground(b, scaledResolution.func_78326_a(), scaledResolution.func_78328_b(), 0.0F, 0.0F, scaledResolution.func_78326_a(), scaledResolution.func_78328_b());
      if (HClip.(this))
        AutoMask.(); 
    } 
    if (this.field_146297_k.field_71474_y.field_74335_Z != 1 && !OringoClient...()) {
      paramInt1 = (int)(paramInt1 / d);
      paramInt2 = (int)(paramInt2 / d);
      GL11.glScaled(d, d, d);
    } 
    for (Panel panel : ) {
      switch (null.[paramHandle.ordinal()]) {
        case 1:
          if (this. == panel) {
            panel. = this. + paramInt1;
            panel. = this. + paramInt2;
          } 
        case 2:
          if (!isHovered(paramInt1, paramInt2, panel., panel., panel., panel.))
            break; 
          if (paramInt3 == 1) {
            this. = panel. - paramInt1;
            this. = panel. - paramInt2;
            this. = panel;
            this. = null;
            break;
          } 
          if (paramInt3 == 0) {
            if (panel.) {
              panel. = -getTotalSettingsCount(panel);
            } else {
              panel. = 0;
            } 
            panel. = !panel.;
          } 
          break;
        case 3:
          this. = null;
          this. = null;
          break;
      } 
      float f = panel. + panel. + 3.0F;
      byte b = 15;
      f += b * (panel. + (panel. - panel.) * (((MinecraftAccessor)this.field_146297_k).getTimer()).field_74281_c);
      List<?> list = (List)NoSlow.(panel.).stream().sorted(Comparator.comparing(ClickGUI::)).filter(this::lambda$handle$1).collect(Collectors.toList());
      Collections.reverse(list);
      for (Module module : list) {
        if (module.() && !OringoClient.)
          continue; 
        switch (null.[paramHandle.ordinal()]) {
          case 1:
            if (f < panel.)
              break; 
            NoCarpet.(panel., f, panel. + panel., f + b, module.() ? color1.getRGB() : color3.getRGB());
            Fonts..(module.(), (panel. + panel. / 2.0F), (f + b / 2.0F - Fonts..() / 2.0F), color2.getRGB());
            break;
          case 2:
            if (!isHovered(paramInt1, paramInt2, panel., f, b, panel.) || f < panel. + panel. + 3.0F)
              break; 
            switch (paramInt3) {
              case 1:
                module. = !module.;
                break;
              case 0:
                module.();
                break;
            } 
            break;
        } 
        f += b;
        if (module.) {
          for (Setting setting : module.) {
            if (setting.())
              continue; 
            if ((paramHandle == Handle. && f >= panel.) || ((paramHandle == Handle. || paramHandle == Handle.) && f >= panel. + panel. + 3.0F))
              if (setting instanceof ModeSetting) {
                switch (null.[paramHandle.ordinal()]) {
                  case 1:
                    NoCarpet.(panel., f, panel. + panel., f + b, color3.brighter().getRGB());
                    Fonts..(setting., (panel. + 2.0F), (f + b / 2.0F - Fonts..() / 2.0F), color2.getRGB());
                    Fonts..(((ModeSetting)setting).(), (panel. + panel.) - Fonts..(((ModeSetting)setting).()) - 2.0D, (f + b / 2.0F - Fonts..() / 2.0F), color4.getRGB());
                    break;
                  case 2:
                    if (!isHovered(paramInt1, paramInt2, (panel. + panel.) - Fonts..(((ModeSetting)setting).()) - 2.0D, f, b, Fonts..(((ModeSetting)setting).())))
                      break; 
                    ((ModeSetting)setting).(paramInt3);
                    break;
                } 
              } else if (setting instanceof NumberSetting) {
                float f1 = (float)((((NumberSetting)setting).() - ((NumberSetting)setting).()) / (((NumberSetting)setting).() - ((NumberSetting)setting).()));
                float f2 = f1 * panel.;
                if (this. != null && this. == setting) {
                  double d1 = Math.min(1.0F, Math.max(0.0F, (paramInt1 - panel.) / panel.));
                  double d2 = d1 * (((NumberSetting)setting).() - ((NumberSetting)setting).()) + ((NumberSetting)setting).();
                  ((NumberSetting)setting).(d2);
                } 
                switch (null.[paramHandle.ordinal()]) {
                  case 1:
                    NoCarpet.(panel., f, panel. + panel., f + b, color3.brighter().brighter().getRGB());
                    NoCarpet.(panel., f, panel. + f2, f + b, color1.brighter().getRGB());
                    Fonts..(setting., (panel. + 2.0F), (f + b / 2.0F - Fonts..() / 2.0F), color2.getRGB());
                    Fonts..(String.valueOf(((NumberSetting)setting).()), (panel. + panel. - 2.0F) - Fonts..(String.valueOf(((NumberSetting)setting).())), (f + b / 2.0F - Fonts..() / 2.0F), color2.getRGB());
                    break;
                  case 2:
                    if (!isHovered(paramInt1, paramInt2, panel., f, b, panel.))
                      break; 
                    this. = (NumberSetting)setting;
                    this. = null;
                    break;
                  case 4:
                    if (isHovered(paramInt1, paramInt2, panel., f, b, panel.) && (paramInt3 == 203 || paramInt3 == 205)) {
                      NumberSetting numberSetting = (NumberSetting)setting;
                      numberSetting.(numberSetting.() + ((paramInt3 == 203) ? -numberSetting.() : numberSetting.()));
                    } 
                    break;
                } 
              } else if (setting instanceof BooleanSetting) {
                switch (null.[paramHandle.ordinal()]) {
                  case 1:
                    NoCarpet.(panel., f, panel. + panel., f + b, color3.brighter().getRGB());
                    ChinaHat.(panel. + panel. - 12.0F, f + b / 2.0F - 4.0F, 8.0F, 8.0F, 3.0F, 2.0F, ((BooleanSetting)setting).() ? color1.brighter().getRGB() : color3.brighter().getRGB(), color1.getRGB());
                    Fonts..(setting., (panel. + 2.0F), (f + b / 2.0F - Fonts..() / 2.0F), color2.getRGB());
                    break;
                  case 2:
                    if (!isHovered(paramInt1, paramInt2, (panel. + panel. - 12.0F), (f + b / 2.0F - 4.0F), 8.0D, 8.0D))
                      break; 
                    ((BooleanSetting)setting).();
                    break;
                } 
              } else if (setting instanceof StringSetting) {
                String str = (this. == setting) ? String.valueOf((new StringBuilder()).append(((StringSetting)setting).()).append("_")) : ((((StringSetting)setting).() == null || ((StringSetting)setting).().equals("")) ? String.valueOf((new StringBuilder()).append(setting.).append("...")) : Fonts..(((StringSetting)setting).(), (int)panel.));
                switch (null.[paramHandle.ordinal()]) {
                  case 1:
                    NoCarpet.(panel., f, panel. + panel., f + b, color3.brighter().getRGB());
                    Fonts..(str, panel. + panel. / 2.0F, f + b / 2.0F - Fonts..() / 2.0F, (((StringSetting)setting).() == null || (((StringSetting)setting).().equals("") && this. != setting)) ? color4.getRGB() : color2.getRGB());
                    break;
                  case 2:
                    if (!isHovered(paramInt1, paramInt2, panel., f, b, panel.))
                      break; 
                    switch (paramInt3) {
                      case 0:
                        this. = (StringSetting)setting;
                        break;
                      case 1:
                        ((StringSetting)setting).("");
                        break;
                    } 
                    break;
                } 
              } else if (setting instanceof RunnableSetting) {
                switch (null.[paramHandle.ordinal()]) {
                  case 2:
                    if (!isHovered(paramInt1, paramInt2, panel., f, b, panel.))
                      break; 
                    ((RunnableSetting)setting).();
                    break;
                  case 1:
                    NoCarpet.(panel., f, panel. + panel., f + b, color3.brighter().getRGB());
                    Fonts..(setting., panel. + panel. / 2.0F, f + b / 2.0F - Fonts..() / 2.0F, color2.getRGB());
                    break;
                } 
              }  
            f += b;
          } 
          if ((paramHandle == Handle. && f >= panel.) || (paramHandle == Handle. && f >= panel. + panel. + 3.0F)) {
            String str = (this. == module) ? "[...]" : String.valueOf((new StringBuilder()).append("[").append((module.() >= 256) ? "  " : ((module.() < 0) ? Mouse.getButtonName(module.() + 100) : Keyboard.getKeyName(module.()).replaceAll("NONE", "  "))).append("]"));
            switch (null.[paramHandle.ordinal()]) {
              case 1:
                NoCarpet.(panel., f, panel. + panel., f + b, color3.brighter().getRGB());
                Fonts..("Key", (panel. + 2.0F), (f + b / 2.0F - Fonts..() / 2.0F), color2.getRGB());
                Fonts..(str, (panel. + panel.) - Fonts..(str) - 2.0D, (f + b / 2.0F - Fonts..() / 2.0F), color4.getRGB());
                break;
              case 2:
                if (!isHovered(paramInt1, paramInt2, (panel. + panel.) - Fonts..(str) - 2.0D, f, b, Fonts..(str)))
                  break; 
                switch (paramInt3) {
                  case 0:
                    this. = module;
                    break;
                  case 1:
                    module.(0);
                    break;
                } 
                break;
            } 
          } 
          f += b;
        } 
      } 
      if (panel. == Module.Category.) {
        Keybind keybind;
        switch (null.[paramHandle.ordinal()]) {
          case 2:
            if (!isHovered(paramInt1, paramInt2, panel., f, 15.0D, panel.))
              break; 
            keybind = new Keybind(String.valueOf((new StringBuilder()).append("Keybind ").append(NoSlow.(Module.Category.).size() + 1)));
            OringoClient..add(keybind);
            MinecraftForge.EVENT_BUS.register(keybind);
            keybind.(true);
            break;
          case 1:
            NoCarpet.(panel., f, panel. + panel., f + 15.0F, color1.getRGB());
            Fonts..("Add new keybind", (panel. + panel. / 2.0F), (f + 7.5F - Fonts..() / 2.0F), color2.getRGB());
            break;
        } 
        f += 15.0F;
      } 
      if (paramHandle == Handle.) {
        NoCarpet.(panel., panel. + 3.0F, panel. + panel., panel. + panel. + 3.0F, color1.getRGB());
        for (byte b1 = 1; b1 < 4; b1++)
          NoCarpet.(panel., panel. + b1, panel. + panel., panel. + panel. + b1, (new Color(0, 0, 0, 40)).getRGB()); 
        NoCarpet.(panel. - 1.0F, panel., panel. + panel. + 1.0F, panel. + panel., color1.darker().getRGB());
        Fonts..(panel.., (panel. + panel. / 2.0F), (panel. + panel. / 2.0F - Fonts..() / 2.0F), color2.getRGB());
        NoCarpet.(panel. - 1.0F, f, panel. + panel. + 1.0F, f + 5.0F, color1.darker().getRGB());
        continue;
      } 
      if (paramHandle == Handle. && isHovered(paramInt1, paramInt2, panel., panel., (f - panel.), panel.)) {
        if (paramInt3 == -1) {
          panel.--;
        } else if (paramInt3 == 1) {
          panel.++;
        } 
        panel. = Math.min(0, Math.max(panel., -getTotalSettingsCount(panel)));
      } 
    } 
    if (Gui..()) {
      String str = (this. == this.) ? String.valueOf((new StringBuilder()).append(this..()).append("_")) : ((this..() == null || this..().equals("")) ? String.valueOf((new StringBuilder()).append(this..).append("...")) : Fonts..(this..(), 200));
      ScaledResolution scaledResolution1 = new ScaledResolution(this.field_146297_k);
      int i = scaledResolution1.func_78326_a();
      int j = scaledResolution1.func_78328_b();
      switch (null.[paramHandle.ordinal()]) {
        case 1:
          NoCarpet.((i / 2 - 102), (j - 43), (i / 2 + 102), (j - 62), color1.getRGB());
          NoCarpet.((i / 2 - 100), (j - 45), (i / 2 + 100), (j - 60), color3.brighter().getRGB());
          Fonts..(str, (i / 2), (j - 53) - Fonts..() / 2.0F, (this..() == null || (this..().equals("") && this. != this.)) ? color4.getRGB() : color2.getRGB());
          break;
        case 2:
          if (!isHovered(paramInt1, paramInt2, (i / 2 - 200), (this.field_146295_m - 60), 15.0D, 400.0D))
            break; 
          switch (paramInt3) {
            case 0:
              this. = this.;
              break;
            case 1:
              this..("");
              break;
          } 
          break;
      } 
    } else {
      this..("");
      if (this. == this.)
        this. = null; 
    } 
    GL11.glPopMatrix();
  }
  
  public void func_146286_b(int paramInt1, int paramInt2, int paramInt3) {
    AntiObby.();
    handle(paramInt1, paramInt2, paramInt3, 0.0F, Handle.);
    super.func_146286_b(paramInt1, paramInt2, paramInt3);
  }
  
  public void func_73864_a(int paramInt1, int paramInt2, int paramInt3) {
    this. = null;
    this. = null;
    this. = null;
    if (this. != null) {
      if (paramInt3 > 1)
        this..(paramInt3 - 100); 
      this. = null;
      return;
    } 
    handle(paramInt1, paramInt2, paramInt3, 0.0F, Handle.);
  }
  
  public boolean func_73868_f() {
    return false;
  }
  
  public void func_73863_a(int paramInt1, int paramInt2, float paramFloat) {
    handle(paramInt1, paramInt2, -1, paramFloat, Handle.);
    super.func_73863_a(paramInt1, paramInt2, paramFloat);
  }
  
  public static class Panel {
    public boolean ;
    
    public float ;
    
    public float ;
    
    public Module.Category ;
    
    public float ;
    
    public boolean ;
    
    public float ;
    
    public int ;
    
    public int ;
    
    public Panel(Module.Category param1Category, float param1Float1, float param1Float2, float param1Float3, float param1Float4) {
      this. = param1Category;
      this. = param1Float1;
      this. = param1Float2;
      this. = param1Float3;
      this. = param1Float4;
      this. = true;
      this. = false;
    }
  }
  
  public enum Handle {
    , , , , ;
    
    static {
       = new Handle("SCROLL", 3);
       = new Handle("TYPED", 4);
       = new Handle[] { , , , ,  };
    }
  }
}
