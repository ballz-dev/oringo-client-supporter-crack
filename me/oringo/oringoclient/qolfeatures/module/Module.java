package me.oringo.oringoclient.qolfeatures.module;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.List;
import me.oringo.oringoclient.OringoClient;
import me.oringo.oringoclient.config.ConfigManager;
import me.oringo.oringoclient.qolfeatures.module.settings.Setting;
import me.oringo.oringoclient.utils.MilliTimer;
import me.oringo.oringoclient.utils.Returnable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public class Module {
  public static Minecraft mc = Minecraft.func_71410_x();
  
  public List<Setting>  = new ArrayList<>();
  
  public Category ;
  
  @Expose
  @SerializedName("name")
  public String ;
  
  public Returnable<String> ;
  
  public boolean ;
  
  public MilliTimer  = new MilliTimer();
  
  public boolean ;
  
  @Expose
  @SerializedName("settings")
  public ConfigManager.ConfigSetting[] ;
  
  @Expose
  @SerializedName("toggled")
  public boolean ;
  
  @Expose
  @SerializedName("keyCode")
  public int ;
  
  public void () {
    (!this.);
  }
  
  public static boolean () {
    return (OringoClient.mc.field_71439_g.field_71070_bA.field_75152_c == OringoClient.mc.field_71439_g.field_71069_bz.field_75152_c);
  }
  
  public String () {
    return this.;
  }
  
  public String () {
    return ().isEmpty() ? () : String.valueOf((new StringBuilder()).append(()).append(" §7").append(()));
  }
  
  public void () {}
  
  public void (Returnable<String> paramReturnable) {
    this. = paramReturnable;
  }
  
  public List<Setting> () {
    return this.;
  }
  
  public void (Setting... paramVarArgs) {
    for (Setting setting : paramVarArgs)
      (setting); 
  }
  
  public int () {
    return this.;
  }
  
  public boolean () {
    return this.;
  }
  
  public boolean () {
    return this.;
  }
  
  public void (boolean paramBoolean) {
    if (this. != paramBoolean) {
      this. = paramBoolean;
      this..();
      if (paramBoolean) {
        ();
      } else {
        ();
      } 
    } 
  }
  
  public Category () {
    return this.;
  }
  
  public static void (double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, int paramInt1, int paramInt2) {
    GlStateManager.func_179147_l();
    GlStateManager.func_179090_x();
    GlStateManager.func_179120_a(770, 771, 1, 0);
    float f1 = (paramInt1 >> 24 & 0xFF) / 255.0F;
    float f2 = (paramInt1 >> 16 & 0xFF) / 255.0F;
    float f3 = (paramInt1 >> 8 & 0xFF) / 255.0F;
    float f4 = (paramInt1 & 0xFF) / 255.0F;
    float f5 = (paramInt2 >> 24 & 0xFF) / 255.0F;
    float f6 = (paramInt2 >> 16 & 0xFF) / 255.0F;
    float f7 = (paramInt2 >> 8 & 0xFF) / 255.0F;
    float f8 = (paramInt2 & 0xFF) / 255.0F;
    GL11.glPushAttrib(0);
    GL11.glScaled(0.5D, 0.5D, 0.5D);
    paramDouble1 *= 2.0D;
    paramDouble2 *= 2.0D;
    paramDouble3 *= 2.0D;
    paramDouble4 *= 2.0D;
    GL11.glShadeModel(7425);
    GL11.glDisable(3553);
    GL11.glEnable(2848);
    GL11.glBegin(9);
    GL11.glColor4f(f2, f3, f4, f1);
    byte b;
    for (b = 0; b <= 90; b += 3)
      GL11.glVertex2d(paramDouble1 + paramDouble5 + Math.sin(b * Math.PI / 180.0D) * paramDouble5 * -1.0D, paramDouble2 + paramDouble5 + Math.cos(b * Math.PI / 180.0D) * paramDouble5 * -1.0D); 
    for (b = 90; b <= '´'; b += 3)
      GL11.glVertex2d(paramDouble1 + paramDouble5 + Math.sin(b * Math.PI / 180.0D) * paramDouble5 * -1.0D, paramDouble4 - paramDouble5 + Math.cos(b * Math.PI / 180.0D) * paramDouble5 * -1.0D); 
    GL11.glColor4f(f6, f7, f8, f5);
    for (b = 0; b <= 90; b += 3)
      GL11.glVertex2d(paramDouble3 - paramDouble5 + Math.sin(b * Math.PI / 180.0D) * paramDouble5, paramDouble4 - paramDouble5 + Math.cos(b * Math.PI / 180.0D) * paramDouble5); 
    for (b = 90; b <= '´'; b += 3)
      GL11.glVertex2d(paramDouble3 - paramDouble5 + Math.sin(b * Math.PI / 180.0D) * paramDouble5, paramDouble2 + paramDouble5 + Math.cos(b * Math.PI / 180.0D) * paramDouble5); 
    GL11.glEnd();
    GL11.glEnable(3553);
    GL11.glDisable(2848);
    GL11.glEnable(3553);
    GL11.glShadeModel(7424);
    GL11.glScaled(2.0D, 2.0D, 2.0D);
    GL11.glPopAttrib();
    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    GlStateManager.func_179098_w();
    GlStateManager.func_179084_k();
  }
  
  public Module(String paramString, int paramInt, Category paramCategory) {
    this. = paramString;
    this. = paramInt;
    this. = paramCategory;
  }
  
  public void (boolean paramBoolean) {
    this. = paramBoolean;
  }
  
  public void (Setting paramSetting) {
    if (().contains(paramSetting))
      throw new IllegalArgumentException(String.valueOf((new StringBuilder()).append("Setting ").append(paramSetting.).append(" already present"))); 
    ().add(paramSetting);
  }
  
  public Module(String paramString, Category paramCategory) {
    this(paramString, 0, paramCategory);
  }
  
  public void () {}
  
  public boolean () {
    return (this. != 0 && ((this. < 0) ? Mouse.isButtonDown(this. + 100) : Keyboard.isKeyDown(this.)) && ());
  }
  
  public void (int paramInt) {
    this. = paramInt;
  }
  
  public boolean () {
    return false;
  }
  
  public String () {
    return (this. == null) ? "" : (String)this..get();
  }
  
  public void () {}
  
  public enum Category {
    ,
    OTHER,
    ,
    ,
    ,
    ,
    ("Combat");
    
    public String ;
    
    static {
       = new Category("RENDER", 2, "Render");
       = new Category("MOVEMENT", 3, "Movement");
       = new Category("PLAYER", 4, "Player");
      OTHER = new Category("OTHER", 5, "Other");
       = new Category("KEYBINDS", 6, "Keybinds");
       = new Category[] { , , , , , OTHER,  };
    }
    
    Category(String param1String1) {
      this. = param1String1;
    }
  }
}
