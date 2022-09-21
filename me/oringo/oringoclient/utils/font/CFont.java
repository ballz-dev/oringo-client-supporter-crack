package me.oringo.oringoclient.utils.font;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import me.oringo.oringoclient.commands.impl.ClipCommand;
import me.oringo.oringoclient.qolfeatures.module.impl.combat.Hitboxes;
import me.oringo.oringoclient.qolfeatures.module.impl.movement.Speed;
import me.oringo.oringoclient.qolfeatures.module.impl.render.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.DynamicTexture;
import org.lwjgl.opengl.GL11;

public class CFont {
  public int  = 0;
  
  public CharData[]  = new CharData[256];
  
  public boolean ;
  
  public boolean ;
  
  public float  = 512.0F;
  
  public int  = -1;
  
  public DynamicTexture ;
  
  public Font ;
  
  public DynamicTexture (Font paramFont, boolean paramBoolean1, boolean paramBoolean2, CharData[] paramArrayOfCharData) {
    BufferedImage bufferedImage = (paramFont, paramBoolean1, paramBoolean2, paramArrayOfCharData);
    try {
      return new DynamicTexture(bufferedImage);
    } catch (Exception exception) {
      exception.printStackTrace();
      return null;
    } 
  }
  
  public void (boolean paramBoolean) {
    if (this. != paramBoolean) {
      this. = paramBoolean;
      this. = (this., this., paramBoolean, this.);
    } 
  }
  
  public void (CharData[] paramArrayOfCharData, char paramChar, float paramFloat1, float paramFloat2, int paramInt1, int paramInt2) throws ArrayIndexOutOfBoundsException {
    if (paramArrayOfCharData[paramChar] == null)
      return; 
    (paramFloat1, paramFloat2, (paramArrayOfCharData[paramChar])., (paramArrayOfCharData[paramChar])., (paramArrayOfCharData[paramChar])., (paramArrayOfCharData[paramChar])., (paramArrayOfCharData[paramChar])., (paramArrayOfCharData[paramChar])., paramInt1, paramInt2);
  }
  
  public static double () {
    return Math.max(Gui.() * 0.85D, Hitboxes.(Speed.mc.field_71439_g.field_70159_w, Speed.mc.field_71439_g.field_70179_y));
  }
  
  public void (boolean paramBoolean) {
    if (this. != paramBoolean) {
      this. = paramBoolean;
      this. = (this., paramBoolean, this., this.);
    } 
  }
  
  public void (float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7, float paramFloat8, int paramInt1, int paramInt2) {
    float f1 = paramFloat5 / this.;
    float f2 = paramFloat6 / this.;
    float f3 = paramFloat7 / this.;
    float f4 = paramFloat8 / this.;
    GlStateManager.func_179124_c((paramInt1 >> 16 & 0xFF) / 255.0F, (paramInt1 >> 8 & 0xFF) / 255.0F, (paramInt1 & 0xFF) / 255.0F);
    GL11.glTexCoord2f(f1 + f3, f2);
    GL11.glVertex2d((paramFloat1 + paramFloat3), paramFloat2);
    GL11.glTexCoord2f(f1, f2);
    GL11.glVertex2d(paramFloat1, paramFloat2);
    GlStateManager.func_179124_c((paramInt2 >> 16 & 0xFF) / 255.0F, (paramInt2 >> 8 & 0xFF) / 255.0F, (paramInt2 & 0xFF) / 255.0F);
    GL11.glTexCoord2f(f1, f2 + f4);
    GL11.glVertex2d(paramFloat1, (paramFloat2 + paramFloat4));
    GL11.glTexCoord2f(f1, f2 + f4);
    GL11.glVertex2d(paramFloat1, (paramFloat2 + paramFloat4));
    GL11.glTexCoord2f(f1 + f3, f2 + f4);
    GL11.glVertex2d((paramFloat1 + paramFloat3), (paramFloat2 + paramFloat4));
    GlStateManager.func_179124_c((paramInt1 >> 16 & 0xFF) / 255.0F, (paramInt1 >> 8 & 0xFF) / 255.0F, (paramInt1 & 0xFF) / 255.0F);
    GL11.glTexCoord2f(f1 + f3, f2);
    GL11.glVertex2d((paramFloat1 + paramFloat3), paramFloat2);
  }
  
  public boolean () {
    return this.;
  }
  
  public void (float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7, float paramFloat8) {
    float f1 = paramFloat5 / this.;
    float f2 = paramFloat6 / this.;
    float f3 = paramFloat7 / this.;
    float f4 = paramFloat8 / this.;
    GL11.glTexCoord2f(f1 + f3, f2);
    GL11.glVertex2d((paramFloat1 + paramFloat3), paramFloat2);
    GL11.glTexCoord2f(f1, f2);
    GL11.glVertex2d(paramFloat1, paramFloat2);
    GL11.glTexCoord2f(f1, f2 + f4);
    GL11.glVertex2d(paramFloat1, (paramFloat2 + paramFloat4));
    GL11.glTexCoord2f(f1, f2 + f4);
    GL11.glVertex2d(paramFloat1, (paramFloat2 + paramFloat4));
    GL11.glTexCoord2f(f1 + f3, f2 + f4);
    GL11.glVertex2d((paramFloat1 + paramFloat3), (paramFloat2 + paramFloat4));
    GL11.glTexCoord2f(f1 + f3, f2);
    GL11.glVertex2d((paramFloat1 + paramFloat3), paramFloat2);
  }
  
  public void (CharData[] paramArrayOfCharData, char paramChar, float paramFloat1, float paramFloat2) throws ArrayIndexOutOfBoundsException {
    if (paramArrayOfCharData[paramChar] == null)
      return; 
    (paramFloat1, paramFloat2, (paramArrayOfCharData[paramChar])., (paramArrayOfCharData[paramChar])., (paramArrayOfCharData[paramChar])., (paramArrayOfCharData[paramChar])., (paramArrayOfCharData[paramChar])., (paramArrayOfCharData[paramChar]).);
  }
  
  public static void () {
    GL11.glEnable(3553);
    GL11.glDisable(3042);
    GL11.glEnable(2929);
    GL11.glDisable(2848);
    GL11.glHint(3154, 4352);
    GL11.glHint(3155, 4352);
  }
  
  public BufferedImage (Font paramFont, boolean paramBoolean1, boolean paramBoolean2, CharData[] paramArrayOfCharData) {
    int i = (int)this.;
    BufferedImage bufferedImage = new BufferedImage(i, i, 2);
    Graphics2D graphics2D = (Graphics2D)bufferedImage.getGraphics();
    graphics2D.setFont(paramFont);
    graphics2D.setColor(new Color(255, 255, 255, 0));
    graphics2D.fillRect(0, 0, i, i);
    graphics2D.setColor(Color.WHITE);
    graphics2D.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, paramBoolean2 ? RenderingHints.VALUE_FRACTIONALMETRICS_ON : RenderingHints.VALUE_FRACTIONALMETRICS_OFF);
    graphics2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, paramBoolean1 ? RenderingHints.VALUE_TEXT_ANTIALIAS_ON : RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
    FontMetrics fontMetrics = graphics2D.getFontMetrics();
    int j = 0;
    int k = 0;
    int m = 1;
    for (byte b = 0; b < paramArrayOfCharData.length; b++) {
      char c = (char)b;
      CharData charData = new CharData();
      Rectangle2D rectangle2D = fontMetrics.getStringBounds(String.valueOf(c), graphics2D);
      charData. = (rectangle2D.getBounds()).width + 8;
      charData. = (rectangle2D.getBounds()).height;
      if (k + charData. >= i) {
        k = 0;
        m += j;
        j = 0;
      } 
      if (charData. > j)
        j = charData.; 
      charData. = k;
      charData. = m;
      if (charData. > this.)
        this. = charData.; 
      paramArrayOfCharData[b] = charData;
      graphics2D.drawString(String.valueOf(c), k + 2, m + fontMetrics.getAscent());
      k += charData.;
    } 
    return bufferedImage;
  }
  
  public CFont(Font paramFont, boolean paramBoolean1, boolean paramBoolean2) {
    this. = paramFont;
    this. = paramBoolean1;
    this. = paramBoolean2;
    this. = (paramFont, paramBoolean1, paramBoolean2, this.);
  }
  
  public void (Font paramFont) {
    this. = paramFont;
    this. = (paramFont, this., this., this.);
  }
  
  public void (float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7, float paramFloat8, int paramInt1, int paramInt2) {
    float f1 = paramFloat5 / this.;
    float f2 = paramFloat6 / this.;
    float f3 = paramFloat7 / this.;
    float f4 = paramFloat8 / this.;
    GlStateManager.func_179124_c((paramInt2 >> 16 & 0xFF) / 255.0F, (paramInt2 >> 8 & 0xFF) / 255.0F, (paramInt2 & 0xFF) / 255.0F);
    GL11.glTexCoord2f(f1 + f3, f2);
    GL11.glVertex2d((paramFloat1 + paramFloat3), paramFloat2);
    GlStateManager.func_179124_c((paramInt1 >> 16 & 0xFF) / 255.0F, (paramInt1 >> 8 & 0xFF) / 255.0F, (paramInt1 & 0xFF) / 255.0F);
    GL11.glTexCoord2f(f1, f2);
    GL11.glVertex2d(paramFloat1, paramFloat2);
    GL11.glTexCoord2f(f1, f2 + f4);
    GL11.glVertex2d(paramFloat1, (paramFloat2 + paramFloat4));
    GL11.glTexCoord2f(f1, f2 + f4);
    GL11.glVertex2d(paramFloat1, (paramFloat2 + paramFloat4));
    GlStateManager.func_179124_c((paramInt2 >> 16 & 0xFF) / 255.0F, (paramInt2 >> 8 & 0xFF) / 255.0F, (paramInt2 & 0xFF) / 255.0F);
    GL11.glTexCoord2f(f1 + f3, f2 + f4);
    GL11.glVertex2d((paramFloat1 + paramFloat3), (paramFloat2 + paramFloat4));
    GL11.glTexCoord2f(f1 + f3, f2);
    GL11.glVertex2d((paramFloat1 + paramFloat3), paramFloat2);
  }
  
  public static <T extends net.minecraft.item.Item> int (Class<T> paramClass) {
    return ClipCommand.(paramClass::);
  }
  
  public void (CharData[] paramArrayOfCharData, char paramChar, float paramFloat1, float paramFloat2, int paramInt1, int paramInt2) throws ArrayIndexOutOfBoundsException {
    if (paramArrayOfCharData[paramChar] == null)
      return; 
    (paramFloat1, paramFloat2, (paramArrayOfCharData[paramChar])., (paramArrayOfCharData[paramChar])., (paramArrayOfCharData[paramChar])., (paramArrayOfCharData[paramChar])., (paramArrayOfCharData[paramChar])., (paramArrayOfCharData[paramChar])., paramInt1, paramInt2);
  }
  
  public static class CharData {
    public int ;
    
    public int ;
    
    public int ;
    
    public int ;
  }
}
