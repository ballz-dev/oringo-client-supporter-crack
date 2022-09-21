package me.oringo.oringoclient.mixins.gui;

import com.google.common.collect.Lists;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import me.oringo.oringoclient.OringoClient;
import me.oringo.oringoclient.qolfeatures.module.impl.movement.Scaffold;
import me.oringo.oringoclient.qolfeatures.module.impl.other.Disabler;
import me.oringo.oringoclient.qolfeatures.module.impl.other.KillInsults;
import me.oringo.oringoclient.qolfeatures.module.impl.skyblock.AutoRogueSword;
import me.oringo.oringoclient.utils.MoveUtils;
import me.oringo.oringoclient.utils.font.Fonts;
import me.oringo.oringoclient.utils.shader.BlurUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ChatLine;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiNewChat;
import net.minecraft.client.gui.GuiUtilRenderComponents;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.MathHelper;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = {GuiNewChat.class}, priority = 1)
public abstract class GuiNewChatMixin extends GuiMixin {
  @Shadow
  @Final
  private Minecraft field_146247_f;
  
  @Shadow
  private int field_146250_j;
  
  @Shadow
  @Final
  private List<ChatLine> field_146253_i;
  
  @Shadow
  private boolean field_146251_k;
  
  @Inject(method = {"drawChat"}, at = {@At("HEAD")}, cancellable = true)
  private void drawChat(int paramInt, CallbackInfo paramCallbackInfo) {
    if (OringoClient...() && OringoClient..()) {
      if (this.field_146247_f.field_71474_y.field_74343_n != EntityPlayer.EnumChatVisibility.HIDDEN) {
        ScaledResolution scaledResolution = new ScaledResolution(this.field_146247_f);
        GL11.glPopMatrix();
        GL11.glPushMatrix();
        GlStateManager.func_179109_b(0.0F, (scaledResolution.func_78328_b() - 60), 0.0F);
        int i = func_146232_i();
        boolean bool = false;
        byte b = 0;
        int j = this.field_146253_i.size();
        int k = OringoClient...() ? (Fonts..() + 3) : this.field_146247_f.field_71466_p.field_78288_b;
        if (j > 0) {
          if (func_146241_e())
            bool = true; 
          float f1 = func_146244_h();
          GlStateManager.func_179094_E();
          GlStateManager.func_179109_b(2.0F, 20.0F, 0.0F);
          GlStateManager.func_179152_a(f1, f1, 1.0F);
          int m = MathHelper.func_76123_f(func_146228_f() / f1);
          float f2 = 0.0F;
          float f3 = 0.0F;
          boolean bool1 = false;
          int n;
          for (n = 0; n + this.field_146250_j < this.field_146253_i.size() && n < i; n++) {
            ChatLine chatLine = this.field_146253_i.get(n + this.field_146250_j);
            if (chatLine != null && (
              paramInt - chatLine.func_74540_b() < 200 || bool)) {
              bool1 = true;
              if (!bool && paramInt - chatLine.func_74540_b() > 195) {
                float f = 1.0F - ((paramInt - chatLine.func_74540_b()) + (Disabler.()).field_74281_c - 195.0F) / 5.0F;
                f = MoveUtils.(f, 0.0F, 1.0F);
                f3 -= k * f;
              } else {
                f3 -= k;
              } 
            } 
          } 
          if (bool1) {
            n = 0;
            switch (OringoClient...()) {
              case "Low":
                n = 3;
                break;
              case "High":
                n = 7;
                break;
            } 
            if (OringoClient...()) {
              float f;
              for (f = 0.5F; f < 3.0F; f += 0.5F)
                Scaffold.((f2 + f - 2.0F), (f3 + f), (f2 + m + 4.0F + f), (1.0F + f), 5.0D, (new Color(20, 20, 20, 40)).getRGB()); 
            } 
            KillInsults.();
            KillInsults.();
            Scaffold.((f2 - 2.0F), f3, (f2 + m + 4.0F), 1.0D, 5.0D, Color.white.getRGB());
            GL11.glPopMatrix();
            GL11.glPopMatrix();
            Scaffold.(1);
            BlurUtils.renderBlurredBackground(n, scaledResolution.func_78326_a(), scaledResolution.func_78328_b(), 0.0F, 0.0F, scaledResolution.func_78326_a(), scaledResolution.func_78328_b());
            GL11.glPushMatrix();
            GlStateManager.func_179109_b(0.0F, (scaledResolution.func_78328_b() - 60), 0.0F);
            GL11.glPushMatrix();
            GlStateManager.func_179109_b(2.0F, 20.0F, 0.0F);
            GlStateManager.func_179152_a(f1, f1, 1.0F);
            Scaffold.((f2 - 2.0F), f3, (f2 + m + 4.0F), 1.0D, 5.0D, (new Color(20, 20, 20, 60)).getRGB());
          } 
          for (n = 0; n + this.field_146250_j < this.field_146253_i.size() && n < i; n++) {
            ChatLine chatLine = this.field_146253_i.get(n + this.field_146250_j);
            if (chatLine != null) {
              int i1 = paramInt - chatLine.func_74540_b();
              if (i1 < 200 || bool) {
                b++;
                boolean bool2 = false;
                int i2 = -n * k;
                String str = chatLine.func_151461_a().func_150254_d();
                GlStateManager.func_179147_l();
                if (OringoClient...()) {
                  Fonts..(str, bool2, (float)(i2 - k - 2.3D), Color.white.getRGB());
                } else {
                  this.field_146247_f.field_71466_p.func_175063_a(str, bool2, (i2 - k - 1), 16777215);
                } 
                GlStateManager.func_179118_c();
                GlStateManager.func_179084_k();
              } 
            } 
          } 
          if (bool1)
            AutoRogueSword.(); 
          if (bool) {
            GlStateManager.func_179109_b(-3.0F, 0.0F, 0.0F);
            k = this.field_146247_f.field_71466_p.field_78288_b;
            n = j * k + j;
            int i1 = b * k + b;
            int i2 = this.field_146250_j * i1 / j;
            int i3 = i1 * i1 / n;
            if (n != i1) {
              byte b1 = (i2 > 0) ? 170 : 96;
              int i4 = this.field_146251_k ? 13382451 : 3355562;
              func_73734_a(0, -i2, 2, -i2 - i3, i4 + (b1 << 24));
              func_73734_a(2, -i2, 1, -i2 - i3, 13421772 + (b1 << 24));
            } 
          } 
          GlStateManager.func_179121_F();
        } 
      } 
      paramCallbackInfo.cancel();
    } 
  }
  
  @Overwrite
  public IChatComponent func_146236_a(int paramInt1, int paramInt2) {
    if (!func_146241_e())
      return null; 
    ScaledResolution scaledResolution = new ScaledResolution(this.field_146247_f);
    int i = scaledResolution.func_78325_e();
    float f = func_146244_h();
    int j = paramInt1 / i - 3;
    int k = paramInt2 / i - 27 - 12;
    j = MathHelper.func_76141_d(j / f);
    k = MathHelper.func_76141_d(k / f);
    if (j >= 0 && k >= 0) {
      int m = Math.min(func_146232_i(), this.field_146253_i.size());
      if (j <= MathHelper.func_76141_d(func_146228_f() / func_146244_h()) && k < getHeight() * m + m) {
        int n = k / getHeight() + this.field_146250_j;
        if (n >= 0 && n < this.field_146253_i.size()) {
          ChatLine chatLine = this.field_146253_i.get(n);
          int i1 = 0;
          for (IChatComponent iChatComponent : chatLine.func_151461_a()) {
            if (iChatComponent instanceof ChatComponentText) {
              i1 = (int)(i1 + ((OringoClient...() && OringoClient..() && OringoClient...()) ? Fonts..(GuiUtilRenderComponents.func_178909_a(((ChatComponentText)iChatComponent).func_150265_g(), false)) : this.field_146247_f.field_71466_p.func_78256_a(GuiUtilRenderComponents.func_178909_a(((ChatComponentText)iChatComponent).func_150265_g(), false))));
              if (i1 > j)
                return iChatComponent; 
            } 
          } 
        } 
        return null;
      } 
      return null;
    } 
    return null;
  }
  
  private int getHeight() {
    return (OringoClient...() && OringoClient...() && OringoClient..()) ? (Fonts..() + 3) : this.field_146247_f.field_71466_p.field_78288_b;
  }
  
  @Redirect(method = {"setChatLine"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiUtilRenderComponents;func_178908_a(Lnet/minecraft/util/IChatComponent;ILnet/minecraft/client/gui/FontRenderer;ZZ)Ljava/util/List;"))
  private List<IChatComponent> onFunc(IChatComponent paramIChatComponent, int paramInt, FontRenderer paramFontRenderer, boolean paramBoolean1, boolean paramBoolean2) {
    return (OringoClient...() && OringoClient..() && OringoClient...()) ? wrapToLen(paramIChatComponent, paramInt, paramFontRenderer) : GuiUtilRenderComponents.func_178908_a(paramIChatComponent, paramInt, paramFontRenderer, paramBoolean1, paramBoolean2);
  }
  
  private List<IChatComponent> wrapToLen(IChatComponent paramIChatComponent, int paramInt, FontRenderer paramFontRenderer) {
    int i = 0;
    ChatComponentText chatComponentText = new ChatComponentText("");
    ArrayList<ChatComponentText> arrayList = Lists.newArrayList();
    ArrayList<IChatComponent> arrayList1 = Lists.newArrayList((Iterable)paramIChatComponent);
    for (byte b = 0; b < arrayList1.size(); b++) {
      IChatComponent iChatComponent = arrayList1.get(b);
      String str1 = iChatComponent.func_150261_e();
      boolean bool = false;
      if (str1.contains("\n")) {
        int j = str1.indexOf('\n');
        String str = str1.substring(j + 1);
        str1 = str1.substring(0, j + 1);
        ChatComponentText chatComponentText2 = new ChatComponentText(str);
        chatComponentText2.func_150255_a(iChatComponent.func_150256_b().func_150232_l());
        arrayList1.add(b + 1, chatComponentText2);
        bool = true;
      } 
      String str2 = GuiUtilRenderComponents.func_178909_a(iChatComponent.func_150256_b().func_150218_j() + str1, false);
      String str3 = str2.endsWith("\n") ? str2.substring(0, str2.length() - 1) : str2;
      double d = Fonts..(str3);
      ChatComponentText chatComponentText1 = new ChatComponentText(str3);
      chatComponentText1.func_150255_a(iChatComponent.func_150256_b().func_150232_l());
      if (i + d > paramInt) {
        String str4 = Fonts..(str2, paramInt - i, false);
        String str5 = (str4.length() < str2.length()) ? str2.substring(str4.length()) : null;
        if (str5 != null && str5.length() > 0) {
          int j = str4.lastIndexOf(" ");
          if (j >= 0 && Fonts..(str2.substring(0, j)) > 0.0D) {
            str4 = str2.substring(0, j);
            str5 = str2.substring(j);
          } else if (i > 0 && !str2.contains(" ")) {
            str4 = "";
            str5 = str2;
          } 
          str5 = FontRenderer.func_78282_e(str4) + str5;
          ChatComponentText chatComponentText2 = new ChatComponentText(str5);
          chatComponentText2.func_150255_a(iChatComponent.func_150256_b().func_150232_l());
          arrayList1.add(b + 1, chatComponentText2);
        } 
        d = Fonts..(str4);
        chatComponentText1 = new ChatComponentText(str4);
        chatComponentText1.func_150255_a(iChatComponent.func_150256_b().func_150232_l());
        bool = true;
      } 
      if (i + d <= paramInt) {
        i = (int)(i + d);
        chatComponentText.func_150257_a((IChatComponent)chatComponentText1);
      } else {
        bool = true;
      } 
      if (bool) {
        arrayList.add(chatComponentText);
        i = 0;
        chatComponentText = new ChatComponentText("");
      } 
    } 
    arrayList.add(chatComponentText);
    return (List)arrayList;
  }
  
  @Shadow
  public abstract boolean func_146241_e();
  
  @Shadow
  public abstract int func_146232_i();
  
  @Shadow
  public abstract int func_146228_f();
  
  @Shadow
  public abstract float func_146244_h();
}
