package me.oringo.oringoclient.utils.font;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;
import me.oringo.oringoclient.OringoClient;
import me.oringo.oringoclient.mixins.MinecraftAccessor;
import me.oringo.oringoclient.mixins.entity.PlayerSPAccessor;
import me.oringo.oringoclient.qolfeatures.module.impl.movement.Step;
import me.oringo.oringoclient.qolfeatures.module.impl.other.AntiNicker;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.BooleanSetting;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.entity.Entity;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import org.lwjgl.opengl.GL11;

public class MinecraftFontRenderer extends CFont {
  public CFont.CharData[]  = new CFont.CharData[256];
  
  public DynamicTexture ;
  
  public String  = "0123456789abcdefklmnor";
  
  public CFont.CharData[]  = new CFont.CharData[256];
  
  public DynamicTexture ;
  
  public int[]  = new int[32];
  
  public CFont.CharData[]  = new CFont.CharData[256];
  
  public DynamicTexture ;
  
  public void (boolean paramBoolean) {
    super.(paramBoolean);
    ();
  }
  
  public static void (double paramDouble) {
    double[] arrayOfDouble = new double[0];
    switch (Step..()) {
      case "NCP":
        if (paramDouble > 1.0D) {
          arrayOfDouble = new double[] { 0.41999998688697815D, 0.7532D, 1.001136019570835D, 0.9855809468686361D, 1.1719230967896395D };
        } else if (paramDouble > 0.87D) {
          arrayOfDouble = new double[] { 0.41999998688697815D, 0.7531999805212024D };
        } else if (paramDouble > 0.7D) {
          arrayOfDouble = new double[] { 0.38999998569488525D };
        } 
        if (paramDouble > 0.7D) {
          for (double d : arrayOfDouble)
            AntiNicker.((Packet)new C03PacketPlayer.C04PacketPlayerPosition(Step.mc.field_71439_g.field_70165_t, Step.mc.field_71439_g.field_70163_u + d, Step.mc.field_71439_g.field_70161_v, false)); 
          if (((PlayerSPAccessor)Step.mc.field_71439_g).getServerSneakState()) {
            AntiNicker.((Packet)new C0BPacketEntityAction((Entity)Step.mc.field_71439_g, C0BPacketEntityAction.Action.STOP_SNEAKING));
            ((PlayerSPAccessor)Step.mc.field_71439_g).setServerSneakState(false);
          } 
          if (Step..() != 1.0D) {
            (((MinecraftAccessor)Step.mc).getTimer()).field_74278_d = (float)Step..();
            Step. = true;
          } 
        } 
        break;
    } 
  }
  
  public float (String paramString, double paramDouble1, double paramDouble2, int paramInt, boolean paramBoolean) {
    paramDouble1--;
    if (paramString == null)
      return 0.0F; 
    if (OringoClient..() && paramString.contains(OringoClient.mc.func_110432_I().func_111285_a()))
      paramString = paramString.replaceAll(OringoClient.mc.func_110432_I().func_111285_a(), OringoClient...()); 
    CFont.CharData[] arrayOfCharData = this.;
    float f = (paramInt >> 24 & 0xFF) / 255.0F;
    boolean bool1 = false;
    boolean bool2 = false;
    boolean bool3 = false;
    boolean bool4 = false;
    boolean bool5 = false;
    boolean bool6 = true;
    paramDouble1 *= 2.0D;
    paramDouble2 = (paramDouble2 - 3.0D) * 2.0D;
    GL11.glPushMatrix();
    GlStateManager.func_179139_a(0.5D, 0.5D, 0.5D);
    GlStateManager.func_179147_l();
    GlStateManager.func_179112_b(770, 771);
    GlStateManager.func_179131_c((paramInt >> 16 & 0xFF) / 255.0F, (paramInt >> 8 & 0xFF) / 255.0F, (paramInt & 0xFF) / 255.0F, f);
    GlStateManager.func_179098_w();
    GlStateManager.func_179144_i(this..func_110552_b());
    GL11.glBindTexture(3553, this..func_110552_b());
    GL11.glTexParameteri(3553, 10241, 9729);
    GL11.glTexParameteri(3553, 10240, 9729);
    for (byte b = 0; b < paramString.length(); b++) {
      char c = paramString.charAt(b);
      if (c == '§') {
        if (b < paramString.length() - 1) {
          int i = 21;
          try {
            i = "0123456789abcdefklmnor".indexOf(paramString.charAt(b + 1));
          } catch (Exception exception) {
            exception.printStackTrace();
          } 
          if (i < 16) {
            bool2 = false;
            bool3 = false;
            bool5 = false;
            bool4 = false;
            GlStateManager.func_179144_i(this..func_110552_b());
            arrayOfCharData = this.;
            if (i < 0)
              i = 15; 
            int j = this.[i];
            if (!paramBoolean)
              GlStateManager.func_179131_c((j >> 16 & 0xFF) / 255.0F, (j >> 8 & 0xFF) / 255.0F, (j & 0xFF) / 255.0F, f); 
          } else if (i != 16) {
            if (i == 17) {
              bool2 = true;
              if (bool3) {
                GlStateManager.func_179144_i(this..func_110552_b());
                GL11.glBindTexture(3553, this..func_110552_b());
                GL11.glTexParameteri(3553, 10241, 9729);
                GL11.glTexParameteri(3553, 10240, 9729);
                arrayOfCharData = this.;
              } else {
                GlStateManager.func_179144_i(this..func_110552_b());
                GL11.glBindTexture(3553, this..func_110552_b());
                GL11.glTexParameteri(3553, 10241, 9729);
                GL11.glTexParameteri(3553, 10240, 9729);
                arrayOfCharData = this.;
              } 
            } else if (i == 18) {
              bool4 = true;
            } else if (i == 19) {
              bool5 = true;
            } else if (i == 20) {
              bool3 = true;
              if (bool2) {
                GlStateManager.func_179144_i(this..func_110552_b());
                GL11.glBindTexture(3553, this..func_110552_b());
                GL11.glTexParameteri(3553, 10241, 9729);
                GL11.glTexParameteri(3553, 10240, 9729);
                arrayOfCharData = this.;
              } else {
                GlStateManager.func_179144_i(this..func_110552_b());
                GL11.glBindTexture(3553, this..func_110552_b());
                GL11.glTexParameteri(3553, 10241, 9729);
                GL11.glTexParameteri(3553, 10240, 9729);
                arrayOfCharData = this.;
              } 
            } else {
              bool2 = false;
              bool3 = false;
              bool5 = false;
              bool4 = false;
              GlStateManager.func_179131_c((paramInt >> 16 & 0xFF) / 255.0F, (paramInt >> 8 & 0xFF) / 255.0F, (paramInt & 0xFF) / 255.0F, f);
              GlStateManager.func_179144_i(this..func_110552_b());
              arrayOfCharData = this.;
            } 
          } 
        } 
        b++;
      } else if (c < arrayOfCharData.length && arrayOfCharData[c] != null) {
        GL11.glBegin(4);
        (arrayOfCharData, c, (float)paramDouble1, (float)paramDouble2);
        GL11.glEnd();
        if (bool4)
          (paramDouble1, paramDouble2 + ((arrayOfCharData[c]). / 2), paramDouble1 + (arrayOfCharData[c]). - 8.0D, paramDouble2 + ((arrayOfCharData[c]). / 2), 1.0F); 
        if (bool5)
          (paramDouble1, paramDouble2 + (arrayOfCharData[c]). - 2.0D, paramDouble1 + (arrayOfCharData[c]). - 8.0D, paramDouble2 + (arrayOfCharData[c]). - 2.0D, 1.0F); 
        paramDouble1 += ((arrayOfCharData[c]). - 8.3F + this.);
      } 
    } 
    GL11.glHint(3155, 4352);
    GL11.glPopMatrix();
    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    return (float)paramDouble1 / 2.0F;
  }
  
  public float (String paramString, float paramFloat1, float paramFloat2, int paramInt) {
    return (paramString, (paramFloat1 - (float)((paramString) / 2.0D)), paramFloat2, paramInt);
  }
  
  public MinecraftFontRenderer(Font paramFont) {
    this(paramFont, true, false);
  }
  
  public float (String paramString, double paramDouble1, double paramDouble2, int paramInt) {
    return (paramString, paramDouble1, paramDouble2, paramInt, false);
  }
  
  public MinecraftFontRenderer(Font paramFont, boolean paramBoolean1, boolean paramBoolean2) {
    super(paramFont, paramBoolean1, paramBoolean2);
    ();
    ();
  }
  
  public String (String paramString, int paramInt) {
    return (paramString, paramInt, false);
  }
  
  public int (String paramString, double paramDouble1, double paramDouble2, int paramInt) {
    float f = (paramString, paramDouble1 + 0.5D, paramDouble2 + 0.8999999761581421D, (new Color(20, 20, 20)).getRGB(), true, 8.3F);
    return (int)Math.max(f, (paramString, paramDouble1, paramDouble2, paramInt, false, 8.3F));
  }
  
  public float (String paramString, double paramDouble1, double paramDouble2, int paramInt1, int paramInt2) {
    paramDouble1--;
    if (paramString == null)
      return 0.0F; 
    if (OringoClient..() && paramString.contains(OringoClient.mc.func_110432_I().func_111285_a()))
      paramString = paramString.replaceAll(OringoClient.mc.func_110432_I().func_111285_a(), OringoClient...()); 
    CFont.CharData[] arrayOfCharData = this.;
    paramDouble1 *= 2.0D;
    paramDouble2 = (paramDouble2 - 3.0D) * 2.0D;
    GL11.glPushMatrix();
    GlStateManager.func_179139_a(0.5D, 0.5D, 0.5D);
    GlStateManager.func_179147_l();
    GlStateManager.func_179112_b(770, 771);
    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    GlStateManager.func_179098_w();
    GlStateManager.func_179144_i(this..func_110552_b());
    GL11.glBindTexture(3553, this..func_110552_b());
    GL11.glTexParameteri(3553, 10241, 9729);
    GL11.glTexParameteri(3553, 10240, 9729);
    GL11.glShadeModel(7425);
    Color color1 = new Color(paramInt1);
    Color color2 = new Color(paramInt2);
    for (byte b = 0; b < paramString.length(); b++) {
      char c = paramString.charAt(b);
      if (c < arrayOfCharData.length && arrayOfCharData[c] != null) {
        float f1 = b / paramString.length();
        float f2 = (b + 1) / paramString.length();
        GL11.glShadeModel(7425);
        GL11.glBegin(4);
        (arrayOfCharData, c, (float)paramDouble1, (float)paramDouble2, BooleanSetting.(color1, color2, f1).getRGB(), BooleanSetting.(color1, color2, f2).getRGB());
        GL11.glEnd();
        GL11.glShadeModel(7424);
        paramDouble1 += ((arrayOfCharData[c]). - 8.3F + this.);
      } 
    } 
    GL11.glHint(3155, 4352);
    GL11.glShadeModel(7424);
    GL11.glPopMatrix();
    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    return (float)paramDouble1 / 2.0F;
  }
  
  public List<String> (String paramString, double paramDouble) {
    ArrayList<String> arrayList = new ArrayList();
    StringBuilder stringBuilder = new StringBuilder();
    char c = '￿';
    char[] arrayOfChar = paramString.toCharArray();
    if (OringoClient..() && paramString.contains(OringoClient.mc.func_110432_I().func_111285_a()))
      paramString = paramString.replaceAll(OringoClient.mc.func_110432_I().func_111285_a(), OringoClient...()); 
    for (byte b = 0; b < arrayOfChar.length; b++) {
      char c1 = arrayOfChar[b];
      if (c1 == '§' && b < arrayOfChar.length - 1)
        c = arrayOfChar[b + 1]; 
      if ((String.valueOf((new StringBuilder()).append(String.valueOf(stringBuilder)).append(c1))) < paramDouble) {
        stringBuilder.append(c1);
      } else {
        arrayList.add(String.valueOf(stringBuilder));
        stringBuilder = new StringBuilder(String.valueOf((new StringBuilder()).append("§").append(c).append(c1)));
      } 
    } 
    if (stringBuilder.length() > 0)
      arrayList.add(String.valueOf(stringBuilder)); 
    return arrayList;
  }
  
  public void (Font paramFont) {
    super.(paramFont);
    ();
  }
  
  public void (double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, float paramFloat) {
    GL11.glDisable(3553);
    GL11.glLineWidth(paramFloat);
    GL11.glBegin(1);
    GL11.glVertex2d(paramDouble1, paramDouble2);
    GL11.glVertex2d(paramDouble3, paramDouble4);
    GL11.glEnd();
    GL11.glEnable(3553);
  }
  
  public int (String paramString, double paramDouble1, double paramDouble2, int paramInt) {
    float f = (paramString, paramDouble1 + 0.5D - (float)((paramString) / 2.0D), paramDouble2 + 0.8999999761581421D, (new Color(20, 20, 20)).getRGB(), true);
    return (int)Math.max(f, (paramString, paramDouble1 - (float)((paramString) / 2.0D), paramDouble2, paramInt, false));
  }
  
  public void () {
    for (byte b = 0; b < 32; b++) {
      int i = (b >> 3 & 0x1) * 85;
      int j = (b >> 2 & 0x1) * 170 + i;
      int k = (b >> 1 & 0x1) * 170 + i;
      int m = (b & 0x1) * 170 + i;
      if (b == 6)
        j += 85; 
      if (b >= 16) {
        j /= 4;
        k /= 4;
        m /= 4;
      } 
      this.[b] = (j & 0xFF) << 16 | (k & 0xFF) << 8 | m & 0xFF;
    } 
  }
  
  public String (String paramString, int paramInt, boolean paramBoolean) {
    StringBuilder stringBuilder = new StringBuilder();
    float f = 0.0F;
    byte b1 = paramBoolean ? (paramString.length() - 1) : 0;
    byte b2 = paramBoolean ? -1 : 1;
    boolean bool1 = false;
    boolean bool2 = false;
    if (OringoClient..() && paramString.contains(OringoClient.mc.func_110432_I().func_111285_a()))
      paramString = paramString.replaceAll(OringoClient.mc.func_110432_I().func_111285_a(), OringoClient...()); 
    int i;
    for (i = b1; i && i < paramString.length() && f < paramInt; i += b2) {
      char c = paramString.charAt(i);
      float f1 = (c);
      if (bool1) {
        bool1 = false;
        if (c != 'l' && c != 'L') {
          if (c == 'r' || c == 'R')
            bool2 = false; 
        } else {
          bool2 = true;
        } 
      } else if (f1 < 0.0F) {
        bool1 = true;
      } else {
        f += f1;
        if (bool2)
          f++; 
      } 
      if (f > paramInt)
        break; 
      if (paramBoolean) {
        stringBuilder.insert(0, c);
      } else {
        stringBuilder.append(c);
      } 
    } 
    return String.valueOf(stringBuilder);
  }
  
  public double (String paramString, float paramFloat) {
    if (paramString == null)
      return 0.0D; 
    if (OringoClient..() && paramString.contains(OringoClient.mc.func_110432_I().func_111285_a()))
      paramString = paramString.replaceAll(OringoClient.mc.func_110432_I().func_111285_a(), OringoClient...()); 
    float f = 0.0F;
    CFont.CharData[] arrayOfCharData = this.;
    boolean bool1 = false;
    boolean bool2 = false;
    for (byte b = 0; b < paramString.length(); b++) {
      char c = paramString.charAt(b);
      if (c == '§') {
        int i = "0123456789abcdefklmnor".indexOf(c);
        b++;
      } else if (c < arrayOfCharData.length) {
        f += (arrayOfCharData[c]). - paramFloat + this.;
      } 
    } 
    return (f / 2.0F);
  }
  
  public void (boolean paramBoolean) {
    super.(paramBoolean);
    ();
  }
  
  public int () {
    return (this. - 8) / 2;
  }
  
  public float (String paramString, float paramFloat1, float paramFloat2, int paramInt) {
    return (paramString, (paramFloat1 - (float)((paramString) / 2.0D)), paramFloat2, paramInt);
  }
  
  public float (String paramString, double paramDouble1, double paramDouble2, int paramInt, boolean paramBoolean, float paramFloat) {
    paramDouble1--;
    if (paramString == null)
      return 0.0F; 
    if (OringoClient..() && paramString.contains(OringoClient.mc.func_110432_I().func_111285_a()))
      paramString = paramString.replaceAll(OringoClient.mc.func_110432_I().func_111285_a(), OringoClient...()); 
    if (paramInt == 553648127)
      paramInt = 16777215; 
    if ((paramInt & 0xFC000000) == 0)
      paramInt |= 0xFF000000; 
    if (paramBoolean)
      paramInt = (paramInt & 0xFCFCFC) >> 2 | paramInt & 0xFF000000; 
    CFont.CharData[] arrayOfCharData = this.;
    float f = (paramInt >> 24 & 0xFF) / 255.0F;
    boolean bool1 = false;
    boolean bool2 = false;
    boolean bool3 = false;
    boolean bool4 = false;
    boolean bool5 = false;
    boolean bool6 = true;
    paramDouble1 *= 2.0D;
    paramDouble2 = (paramDouble2 - 3.0D) * 2.0D;
    GL11.glPushMatrix();
    GlStateManager.func_179139_a(0.5D, 0.5D, 0.5D);
    GlStateManager.func_179147_l();
    GlStateManager.func_179112_b(770, 771);
    GlStateManager.func_179131_c((paramInt >> 16 & 0xFF) / 255.0F, (paramInt >> 8 & 0xFF) / 255.0F, (paramInt & 0xFF) / 255.0F, f);
    GlStateManager.func_179098_w();
    GlStateManager.func_179144_i(this..func_110552_b());
    GL11.glBindTexture(3553, this..func_110552_b());
    for (byte b = 0; b < paramString.length(); b++) {
      char c = paramString.charAt(b);
      if (c == '§') {
        int i = 21;
        try {
          i = "0123456789abcdefklmnor".indexOf(paramString.charAt(b + 1));
        } catch (Exception exception) {
          exception.printStackTrace();
        } 
        if (i < 16) {
          bool2 = false;
          bool3 = false;
          bool5 = false;
          bool4 = false;
          GlStateManager.func_179144_i(this..func_110552_b());
          arrayOfCharData = this.;
          if (i < 0)
            i = 15; 
          if (paramBoolean)
            i += 16; 
          int j = this.[i];
          GlStateManager.func_179131_c((j >> 16 & 0xFF) / 255.0F, (j >> 8 & 0xFF) / 255.0F, (j & 0xFF) / 255.0F, f);
        } else if (i == 17) {
          bool2 = true;
          if (bool3) {
            GlStateManager.func_179144_i(this..func_110552_b());
            arrayOfCharData = this.;
          } else {
            GlStateManager.func_179144_i(this..func_110552_b());
            arrayOfCharData = this.;
          } 
        } else if (i == 18) {
          bool4 = true;
        } else if (i == 19) {
          bool5 = true;
        } else if (i == 20) {
          bool3 = true;
          if (bool2) {
            GlStateManager.func_179144_i(this..func_110552_b());
            arrayOfCharData = this.;
          } else {
            GlStateManager.func_179144_i(this..func_110552_b());
            arrayOfCharData = this.;
          } 
        } else {
          bool2 = false;
          bool3 = false;
          bool5 = false;
          bool4 = false;
          GlStateManager.func_179131_c((paramInt >> 16 & 0xFF) / 255.0F, (paramInt >> 8 & 0xFF) / 255.0F, (paramInt & 0xFF) / 255.0F, f);
          GlStateManager.func_179144_i(this..func_110552_b());
          arrayOfCharData = this.;
        } 
        b++;
      } else if (c < arrayOfCharData.length && arrayOfCharData[c] != null) {
        GL11.glBegin(4);
        (arrayOfCharData, c, (float)paramDouble1, (float)paramDouble2);
        GL11.glEnd();
        if (bool4)
          (paramDouble1, paramDouble2 + ((arrayOfCharData[c]). / 2), paramDouble1 + (arrayOfCharData[c]). - 8.0D, paramDouble2 + ((arrayOfCharData[c]). / 2), 1.0F); 
        if (bool5)
          (paramDouble1, paramDouble2 + (arrayOfCharData[c]). - 2.0D, paramDouble1 + (arrayOfCharData[c]). - 8.0D, paramDouble2 + (arrayOfCharData[c]). - 2.0D, 1.0F); 
        paramDouble1 += ((arrayOfCharData[c]). - paramFloat + this.);
      } 
    } 
    GL11.glHint(3155, 4352);
    GL11.glPopMatrix();
    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    return (float)paramDouble1 / 2.0F;
  }
  
  public float (String paramString, float paramFloat1, float paramFloat2, int paramInt1, int paramInt2) {
    return (paramString, (paramFloat1 - (float)((paramString) / 2.0D)), paramFloat2, paramInt1, paramInt2, false);
  }
  
  public float (String paramString, float paramFloat1, float paramFloat2, int paramInt) {
    return (paramString, (paramFloat1 - (float)((paramString) / 2.0D)), paramFloat2, paramInt);
  }
  
  public void () {
    this. = (this..deriveFont(1), this., this., this.);
    this. = (this..deriveFont(2), this., this., this.);
    this. = (this..deriveFont(3), this., this., this.);
  }
  
  public float (String paramString, double paramDouble1, double paramDouble2, int paramInt1, int paramInt2, boolean paramBoolean) {
    paramDouble1--;
    if (paramString == null)
      return 0.0F; 
    if (OringoClient..() && paramString.contains(OringoClient.mc.func_110432_I().func_111285_a()))
      paramString = paramString.replaceAll(OringoClient.mc.func_110432_I().func_111285_a(), OringoClient...()); 
    CFont.CharData[] arrayOfCharData = this.;
    paramDouble1 *= 2.0D;
    paramDouble2 = (paramDouble2 - 3.0D) * 2.0D;
    GL11.glPushMatrix();
    GlStateManager.func_179139_a(0.5D, 0.5D, 0.5D);
    GlStateManager.func_179147_l();
    GlStateManager.func_179112_b(770, 771);
    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    GlStateManager.func_179098_w();
    GlStateManager.func_179144_i(this..func_110552_b());
    GL11.glBindTexture(3553, this..func_110552_b());
    GL11.glTexParameteri(3553, 10241, 9729);
    GL11.glTexParameteri(3553, 10240, 9729);
    GL11.glShadeModel(7425);
    for (byte b = 0; b < paramString.length(); b++) {
      char c = paramString.charAt(b);
      if (c < arrayOfCharData.length && arrayOfCharData[c] != null) {
        GL11.glShadeModel(7425);
        GL11.glBegin(4);
        (arrayOfCharData, c, (float)paramDouble1, (float)paramDouble2, paramInt1, paramInt2);
        GL11.glEnd();
        GL11.glShadeModel(7424);
        paramDouble1 += ((arrayOfCharData[c]). - 8.3F + this.);
      } 
    } 
    GL11.glHint(3155, 4352);
    GL11.glShadeModel(7424);
    GL11.glPopMatrix();
    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    return (float)paramDouble1 / 2.0F;
  }
  
  public double (String paramString) {
    if (paramString == null)
      return 0.0D; 
    if (OringoClient..() && paramString.contains(OringoClient.mc.func_110432_I().func_111285_a()))
      paramString = paramString.replaceAll(OringoClient.mc.func_110432_I().func_111285_a(), OringoClient...()); 
    float f = 0.0F;
    CFont.CharData[] arrayOfCharData = this.;
    boolean bool1 = false;
    boolean bool2 = false;
    for (byte b = 0; b < paramString.length(); b++) {
      char c = paramString.charAt(b);
      if (c == '§') {
        b++;
      } else if (c < arrayOfCharData.length && arrayOfCharData[c] != null) {
        f += (arrayOfCharData[c]). - 8.3F + this.;
      } 
    } 
    return (f / 2.0F);
  }
  
  public List<String> (String paramString, double paramDouble) {
    ArrayList<String> arrayList = new ArrayList();
    if (OringoClient..() && paramString.contains(OringoClient.mc.func_110432_I().func_111285_a()))
      paramString = paramString.replaceAll(OringoClient.mc.func_110432_I().func_111285_a(), OringoClient...()); 
    if ((paramString) > paramDouble) {
      String[] arrayOfString = paramString.split(" ");
      StringBuilder stringBuilder = new StringBuilder();
      char c = '￿';
      for (String str : arrayOfString) {
        for (byte b = 0; b < (str.toCharArray()).length; b++) {
          char c1 = str.toCharArray()[b];
          if (c1 == '§' && b < (str.toCharArray()).length - 1)
            c = str.toCharArray()[b + 1]; 
        } 
        if ((String.valueOf((new StringBuilder()).append(stringBuilder).append(str).append(" "))) < paramDouble) {
          stringBuilder.append(str).append(" ");
        } else {
          arrayList.add(String.valueOf(stringBuilder));
          stringBuilder = new StringBuilder(String.valueOf((new StringBuilder()).append("§").append(c).append(str).append(" ")));
        } 
      } 
      if (stringBuilder.length() > 0)
        if ((String.valueOf(stringBuilder)) < paramDouble) {
          arrayList.add(String.valueOf((new StringBuilder()).append("§").append(c).append(stringBuilder).append(" ")));
          stringBuilder = new StringBuilder();
        } else {
          arrayList.addAll((String.valueOf(stringBuilder), paramDouble));
        }  
    } else {
      arrayList.add(paramString);
    } 
    return arrayList;
  }
  
  public float (char paramChar) {
    if (paramChar == '§')
      return -1.0F; 
    if (paramChar == ' ')
      return 2.0F; 
    if (paramChar >= this..length || this.[paramChar] == null)
      return 0.0F; 
    int i = "ÀÁÂÈÊËÍÓÔÕÚßãõğİıŒœŞşŴŵžȇ\000\000\000\000\000\000\000 !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~\000ÇüéâäàåçêëèïîìÄÅÉæÆôöòûùÿÖÜø£Ø×ƒáíóúñÑªº¿®¬½¼¡«»░▒▓│┤╡╢╖╕╣║╗╝╜╛┐└┴┬├─┼╞╟╚╔╩╦╠═╬╧╨╤╥╙╘╒╓╫╪┘┌█▄▌▐▀αβΓπΣσμτΦΘΩδ∞∅∈∩≡±≥≤⌠⌡÷≈°∙·√ⁿ²■\000".indexOf(paramChar);
    if (paramChar > '\000' && i != -1)
      return (this.[i]). / 2.0F - 4.0F; 
    if ((this.[paramChar]). / 2.0F - 4.0F != 0.0F) {
      int j = (int)((this.[paramChar]). / 2.0F - 4.0F) >>> 4;
      int k = (int)((this.[paramChar]). / 2.0F - 4.0F) & 0xF;
      j &= 0xF;
      return ((++k - j) / 2 + 1);
    } 
    return 0.0F;
  }
  
  public int (String paramString, double paramDouble1, double paramDouble2, int paramInt) {
    return (int)(paramString, paramDouble1, paramDouble2, paramInt, false, 8.3F);
  }
  
  public float (String paramString, double paramDouble1, double paramDouble2, int paramInt, boolean paramBoolean) {
    paramDouble1--;
    if (paramString == null)
      return 0.0F; 
    if (OringoClient..() && paramString.contains(OringoClient.mc.func_110432_I().func_111285_a()))
      paramString = paramString.replaceAll(OringoClient.mc.func_110432_I().func_111285_a(), OringoClient...()); 
    CFont.CharData[] arrayOfCharData = this.;
    float f = (paramInt >> 24 & 0xFF) / 255.0F;
    boolean bool1 = false;
    boolean bool2 = false;
    boolean bool3 = false;
    boolean bool4 = false;
    boolean bool5 = false;
    boolean bool6 = true;
    paramDouble1 *= 2.0D;
    paramDouble2 = (paramDouble2 - 3.0D) * 2.0D;
    GL11.glPushMatrix();
    GlStateManager.func_179139_a(0.5D, 0.5D, 0.5D);
    GlStateManager.func_179147_l();
    GlStateManager.func_179112_b(770, 771);
    GlStateManager.func_179131_c((paramInt >> 16 & 0xFF) / 255.0F, (paramInt >> 8 & 0xFF) / 255.0F, (paramInt & 0xFF) / 255.0F, f);
    GlStateManager.func_179098_w();
    GlStateManager.func_179144_i(this..func_110552_b());
    GL11.glBindTexture(3553, this..func_110552_b());
    GL11.glTexParameteri(3553, 10241, 9728);
    GL11.glTexParameteri(3553, 10240, 9728);
    for (byte b = 0; b < paramString.length(); b++) {
      char c = paramString.charAt(b);
      if (c == '§') {
        int i = 21;
        try {
          i = "0123456789abcdefklmnor".indexOf(paramString.charAt(b + 1));
        } catch (Exception exception) {
          exception.printStackTrace();
        } 
        if (i < 16) {
          bool2 = false;
          bool3 = false;
          bool5 = false;
          bool4 = false;
          GlStateManager.func_179144_i(this..func_110552_b());
          arrayOfCharData = this.;
          if (i < 0)
            i = 15; 
          if (paramBoolean)
            i += 16; 
          int j = this.[i];
          GlStateManager.func_179131_c((j >> 16 & 0xFF) / 255.0F, (j >> 8 & 0xFF) / 255.0F, (j & 0xFF) / 255.0F, f);
        } else if (i != 16) {
          if (i == 17) {
            bool2 = true;
            if (bool3) {
              GlStateManager.func_179144_i(this..func_110552_b());
              arrayOfCharData = this.;
            } else {
              GlStateManager.func_179144_i(this..func_110552_b());
              arrayOfCharData = this.;
            } 
          } else if (i == 18) {
            bool4 = true;
          } else if (i == 19) {
            bool5 = true;
          } else if (i == 20) {
            bool3 = true;
            if (bool2) {
              GlStateManager.func_179144_i(this..func_110552_b());
              arrayOfCharData = this.;
            } else {
              GlStateManager.func_179144_i(this..func_110552_b());
              arrayOfCharData = this.;
            } 
          } else {
            bool2 = false;
            bool3 = false;
            bool5 = false;
            bool4 = false;
            GlStateManager.func_179131_c((paramInt >> 16 & 0xFF) / 255.0F, (paramInt >> 8 & 0xFF) / 255.0F, (paramInt & 0xFF) / 255.0F, f);
            GlStateManager.func_179144_i(this..func_110552_b());
            arrayOfCharData = this.;
          } 
        } 
        b++;
      } else if (c < arrayOfCharData.length && arrayOfCharData[c] != null) {
        GL11.glBegin(4);
        (arrayOfCharData, c, (float)paramDouble1, (float)paramDouble2);
        GL11.glEnd();
        if (bool4)
          (paramDouble1, paramDouble2 + ((arrayOfCharData[c]). / 2), paramDouble1 + (arrayOfCharData[c]). - 8.0D, paramDouble2 + ((arrayOfCharData[c]). / 2), 1.0F); 
        if (bool5)
          (paramDouble1, paramDouble2 + (arrayOfCharData[c]). - 2.0D, paramDouble1 + (arrayOfCharData[c]). - 8.0D, paramDouble2 + (arrayOfCharData[c]). - 2.0D, 1.0F); 
        paramDouble1 += ((arrayOfCharData[c]). - 8.3F + this.);
      } 
    } 
    GL11.glHint(3155, 4352);
    GL11.glPopMatrix();
    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    return (float)paramDouble1 / 2.0F;
  }
  
  public float (String paramString, double paramDouble1, double paramDouble2, int paramInt) {
    return (paramString, paramDouble1 - (paramString) / 2.0D, paramDouble2, paramInt);
  }
  
  public int (String paramString, double paramDouble, float paramFloat, int paramInt) {
    return (int)(paramString, paramDouble, paramFloat, paramInt, false);
  }
  
  public int (String paramString, double paramDouble1, double paramDouble2, int paramInt) {
    float f = (paramString, paramDouble1 + 0.5D, paramDouble2 + 0.8999999761581421D, (new Color(20, 20, 20)).getRGB(), true);
    return (int)Math.max(f, (paramString, paramDouble1, paramDouble2, paramInt, false));
  }
}
