package me.oringo.oringoclient.qolfeatures.module.impl.render;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.mojang.realmsclient.gui.ChatFormatting;
import java.awt.Color;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import me.oringo.oringoclient.OringoClient;
import me.oringo.oringoclient.events.impl.PacketReceivedEvent;
import me.oringo.oringoclient.events.impl.ScoreboardRenderEvent;
import me.oringo.oringoclient.mixins.packet.S3EAccessor;
import me.oringo.oringoclient.qolfeatures.module.Module;
import me.oringo.oringoclient.qolfeatures.module.impl.movement.Scaffold;
import me.oringo.oringoclient.qolfeatures.module.impl.other.KillInsults;
import me.oringo.oringoclient.qolfeatures.module.impl.skyblock.AutoRogueSword;
import me.oringo.oringoclient.qolfeatures.module.settings.Setting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.BooleanSetting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.ModeSetting;
import me.oringo.oringoclient.utils.MilliTimer;
import me.oringo.oringoclient.utils.OutlineUtils;
import me.oringo.oringoclient.utils.PacketUtils;
import me.oringo.oringoclient.utils.RenderUtils;
import me.oringo.oringoclient.utils.Rotation;
import me.oringo.oringoclient.utils.font.Fonts;
import me.oringo.oringoclient.utils.shader.BlurUtils;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S3EPacketTeams;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.Team;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class CustomInterfaces extends Module {
  public BooleanSetting  = new BooleanSetting("Hide lobby", true);
  
  public ModeSetting  = new ModeSetting(this, "Blur Strength", "Low", new String[] { "None", "Low", "High" }) {
      public boolean () {
        return !this...();
      }
    };
  
  public BooleanSetting  = new BooleanSetting("Custom Buttons", false);
  
  public static MilliTimer  = new MilliTimer();
  
  public static Pattern  = Pattern.compile("[0-9][0-9]/[0-9][0-9]/[0-9][0-9]");
  
  public BooleanSetting  = new BooleanSetting("Custom Scoreboard", true);
  
  public BooleanSetting  = new BooleanSetting(this, "Custom Font", true) {
      public boolean () {
        return !this...();
      }
    };
  
  public BooleanSetting  = new BooleanSetting("Custom chat font", false, this::lambda$new$0);
  
  public ModeSetting  = new ModeSetting(this, "Line location", "Top", new String[] { "Top", "Bottom" }) {
      public boolean () {
        return (!this...() || this...("None"));
      }
    };
  
  public BooleanSetting  = new BooleanSetting("Chat in gui", false);
  
  public BooleanSetting  = new BooleanSetting("Shadow", false);
  
  public BooleanSetting  = new BooleanSetting("Custom chat", true);
  
  public static int ;
  
  public BooleanSetting  = new BooleanSetting("Smooth hotbar", false);
  
  public BooleanSetting  = new BooleanSetting(this, "Outline", false) {
      public boolean () {
        return !this...();
      }
    };
  
  public ModeSetting  = new ModeSetting(this, "Button line", "Single", new String[] { "Wave", "Single", "None" }) {
      public boolean () {
        return !this...();
      }
    };
  
  public void (ScoreObjective paramScoreObjective, ScaledResolution paramScaledResolution, boolean paramBoolean) {
    Scoreboard scoreboard = paramScoreObjective.func_96682_a();
    Collection collection = scoreboard.func_96534_i(paramScoreObjective);
    List list = (List)collection.stream().filter(CustomInterfaces::).collect(Collectors.toList());
    if (list.size() > 15) {
      collection = Lists.newArrayList(Iterables.skip(list, collection.size() - 15));
    } else {
      collection = list;
    } 
    float f1 = (paramScoreObjective.func_96678_d(), paramBoolean);
    int i = paramBoolean ? (Fonts..() + 2) : mc.field_71466_p.field_78288_b;
    for (Score score : collection) {
      ScorePlayerTeam scorePlayerTeam = scoreboard.func_96509_i(score.func_96653_e());
      String str = String.valueOf((new StringBuilder()).append(ScorePlayerTeam.func_96667_a((Team)scorePlayerTeam, score.func_96653_e())).append(": ").append(EnumChatFormatting.RED).append(score.func_96652_c()));
      f1 = Math.max(f1, (str, paramBoolean));
    } 
    float f2 = (collection.size() * i);
    float f3 = OringoClient..();
    float f4 = paramScaledResolution.func_78328_b() / 2.0F + f2 / 3.0F;
    if (OringoClient...())
      f4 = Math.max(f4, f3 + 40.0F + (collection.size() * i - i - 3)); 
    float f5 = 3.0F;
    float f6 = paramScaledResolution.func_78326_a() - f1 - f5;
    float f7 = paramScaledResolution.func_78326_a() - f5 + 2.0F;
    byte b1 = 0;
    switch (this..()) {
      case "Low":
        b1 = 3;
        break;
      case "High":
        b1 = 7;
        break;
    } 
    if (this..()) {
      float f;
      for (f = 0.5F; f < 3.0F; f += 0.5F)
        RenderUtils.((f6 - 2.0F - f), (f4 - (collection.size() * i) - i - 3.0F + f), (f7 - f6 - 2.0F), (i * (collection.size() + 1) + 4), 5.0D, (new Color(20, 20, 20, 30)).getRGB()); 
    } 
    KillInsults.();
    KillInsults.();
    RenderUtils.((f6 - 2.0F), (f4 - (collection.size() * i) - i - 3.0F), (f7 - f6 - 2.0F), (i * (collection.size() + 1) + 4), 5.0D, Color.black.getRGB());
    Scaffold.(1);
    BlurUtils.renderBlurredBackground(b1, paramScaledResolution.func_78326_a(), paramScaledResolution.func_78328_b(), f6 - 2.0F, f4 - (collection.size() * i) - i - 3.0F, f7 - f6 - 2.0F, (i * (collection.size() + 1) + 4));
    AutoRogueSword.();
    if (this..()) {
      (f6 - 2.0F, f4 - (collection.size() * i) - i - 3.0F, f7 - f6 - 2.0F, (i * (collection.size() + 1) + 4), 5.0F, 2.0F);
    } else {
      RenderUtils.((f6 - 2.0F), (f4 - (collection.size() * i) - i - 3.0F), (f7 - f6 - 2.0F), (i * (collection.size() + 1) + 4), 5.0D, (new Color(21, 21, 21, 50)).getRGB());
    } 
    byte b2 = 0;
    for (Score score : collection) {
      b2++;
      ScorePlayerTeam scorePlayerTeam = scoreboard.func_96509_i(score.func_96653_e());
      String str = ScorePlayerTeam.func_96667_a((Team)scorePlayerTeam, score.func_96653_e());
      if (str.contains("§ewww.hypixel.ne🎂§et"))
        str = str.replaceAll("§ewww.hypixel.ne🎂§et", "Oringo Client"); 
      float f = f4 - (b2 * i);
      Matcher matcher = .matcher(str);
      if (this..() && matcher.find())
        str = String.valueOf((new StringBuilder()).append(ChatFormatting.GRAY).append(matcher.group())); 
      boolean bool = str.equals("Oringo Client");
      if (bool) {
        if (paramBoolean) {
          Fonts..(str, (f6 + f1 / 2.0F), f, OringoClient..().getRGB());
        } else {
          mc.field_71466_p.func_78276_b(str, (int)(f6 + f1 / 2.0F - (mc.field_71466_p.func_78256_a(str) / 2)), (int)f, OringoClient..().getRGB());
        } 
      } else {
        (str, f6, f, 553648127, paramBoolean);
      } 
      if (b2 == collection.size()) {
        String str1 = paramScoreObjective.func_96678_d();
        (str1, f6 + f1 / 2.0F - (str1, paramBoolean) / 2.0F, f - i, Color.white.getRGB(), paramBoolean);
      } 
    } 
    GlStateManager.func_179124_c(1.0F, 1.0F, 1.0F);
  }
  
  @SubscribeEvent
  public void (PacketReceivedEvent paramPacketReceivedEvent) {
    if (!this..() && this..() && () && paramPacketReceivedEvent. instanceof S3EPacketTeams) {
      S3EPacketTeams s3EPacketTeams = (S3EPacketTeams)paramPacketReceivedEvent.;
      if (s3EPacketTeams.func_149311_e().length() != 0 && s3EPacketTeams.func_179813_h() == 15) {
        Matcher matcher = .matcher(s3EPacketTeams.func_149311_e());
        if (matcher.find()) {
          ((S3EAccessor)s3EPacketTeams).setScoreName(s3EPacketTeams.func_149311_e().replaceAll(String.valueOf((new StringBuilder()).append(matcher.group()).append("  §8")), String.valueOf((new StringBuilder()).append(matcher.group()).append("  §8").append(ChatFormatting.OBFUSCATED))).replaceAll(String.valueOf((new StringBuilder()).append(matcher.group()).append(" §8")), String.valueOf((new StringBuilder()).append(matcher.group()).append(" §8").append(ChatFormatting.OBFUSCATED))));
          ((S3EAccessor)s3EPacketTeams).setScoreName2(s3EPacketTeams.func_149309_f().replaceAll("§8", String.valueOf((new StringBuilder()).append("§8").append(ChatFormatting.OBFUSCATED))));
        } 
      } 
    } 
  }
  
  public static Rotation (Vec3 paramVec3) {
    double d1 = OringoClient.mc.field_71439_g.func_174824_e(1.0F).func_72438_d(paramVec3);
    double d2 = paramVec3.field_72450_a - OringoClient.mc.field_71439_g.field_70165_t;
    double d3 = paramVec3.field_72449_c - OringoClient.mc.field_71439_g.field_70161_v;
    double d4 = OringoClient.mc.field_71439_g.field_70163_u + OringoClient.mc.field_71439_g.func_70047_e() - paramVec3.field_72448_b;
    float f1 = (float)Math.toDegrees(Math.atan2(d3, d2)) - 90.0F;
    double d5 = MathHelper.func_76133_a(d2 * d2 + d3 * d3);
    float f2 = (float)-(Math.atan2(d4, d5) * 180.0D / Math.PI) + (float)d1 * 0.11F;
    return new Rotation(f1, -f2);
  }
  
  public float (String paramString, boolean paramBoolean) {
    return paramBoolean ? (float)Fonts..(paramString) : mc.field_71466_p.func_78256_a(paramString);
  }
  
  @SubscribeEvent
  public void (ScoreboardRenderEvent paramScoreboardRenderEvent) {
    if (!() || !this..())
      return; 
    paramScoreboardRenderEvent.setCanceled(true);
    (paramScoreboardRenderEvent., paramScoreboardRenderEvent., this..());
  }
  
  public static void (Packet<?> paramPacket) {
    OringoClient.mc.func_147114_u().func_147298_b().func_179288_a(paramPacket, PacketUtils::, new io.netty.util.concurrent.GenericFutureListener[0]);
  }
  
  public CustomInterfaces() {
    super("Interfaces", Module.Category.);
    (new Setting[] { (Setting)this., (Setting)this., (Setting)this., (Setting)this., (Setting)this., (Setting)this., (Setting)this., (Setting)this., (Setting)this. });
    (true);
  }
  
  public void (String paramString, float paramFloat1, float paramFloat2, int paramInt, boolean paramBoolean) {
    if (OringoClient..() && paramString.contains(mc.func_110432_I().func_111285_a()))
      paramString = paramString.replaceAll(mc.func_110432_I().func_111285_a(), OringoClient...()); 
    if (paramBoolean) {
      Fonts..(paramString, paramFloat1, paramFloat2, Color.white.getRGB());
    } else {
      mc.field_71466_p.func_78276_b(paramString, (int)paramFloat1, (int)paramFloat2, paramInt);
    } 
  }
  
  public void (float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6) {
    Scaffold.(paramFloat1, paramFloat2, (paramFloat1 + paramFloat3), (paramFloat2 + paramFloat4), paramFloat5, (new Color(21, 21, 21, 50)).getRGB());
    OutlineUtils.(paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramFloat5, paramFloat6, OringoClient..(0).getRGB(), OringoClient..(3).getRGB(), OringoClient..(6).getRGB(), OringoClient..(9).getRGB());
  }
}
