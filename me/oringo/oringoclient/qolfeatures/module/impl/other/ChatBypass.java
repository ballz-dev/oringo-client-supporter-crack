package me.oringo.oringoclient.qolfeatures.module.impl.other;

import java.util.Arrays;
import me.oringo.oringoclient.events.impl.PacketSentEvent;
import me.oringo.oringoclient.qolfeatures.module.Module;
import me.oringo.oringoclient.qolfeatures.module.settings.Setting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.ModeSetting;
import net.minecraft.network.play.client.C01PacketChatMessage;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ChatBypass extends Module {
  public String[]  = new String[] { "nigger", "kill yourself", "kurwa", "fucking", "spierdalaj" };
  
  public ModeSetting  = new ModeSetting("mode", "font", new String[] { "font", "dots" });
  
  public static String  = "qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM0123456789";
  
  public static String  = "ｑｗｅｒｔｙｕｉｏｐａｓｄｆｇｈｊｋｌｚｘｃｖｂｎｍｑｗｅｒｔｙｕｉｏｐａｓｄｆｇｈｊｋｌｚｘｃｖｂｎｍ０１２３４５６７８９";
  
  public String  = "";
  
  public ChatBypass() {
    super("Chat bypass", 0, Module.Category.OTHER);
    (new Setting[] { (Setting)this. });
  }
  
  @SubscribeEvent
  public void (PacketSentEvent paramPacketSentEvent) {
    if (paramPacketSentEvent. instanceof C01PacketChatMessage) {
      this. = "";
      String str = ((C01PacketChatMessage)paramPacketSentEvent.).func_149439_c();
      if (str.charAt(0) == '/') {
        this. = str.split(" ")[0];
        if (this..equalsIgnoreCase("/msg") || this..equalsIgnoreCase("/message") || this..equalsIgnoreCase("/t") || this..equalsIgnoreCase("/tell") || this..equalsIgnoreCase("/w")) {
          this. = String.valueOf((new StringBuilder()).append(this.).append(" "));
          if ((str.split(" ")).length > 1)
            this. = String.valueOf((new StringBuilder()).append(this.).append(str.split(" ")[1])); 
        } 
      } 
      if (Arrays.<String>stream(this.).anyMatch(str::) && ()) {
        paramPacketSentEvent.cancel();
        (this., this..isEmpty() ? str : str.replaceFirst(String.valueOf((new StringBuilder()).append(this.).append(" ")), ""));
      } 
    } 
  }
  
  public void (String paramString1, String paramString2) {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(paramString1).append(" ");
    for (byte b = 0; b < paramString2.length(); b++) {
      char c = paramString2.charAt(b);
      switch (this..()) {
        case "dots":
          stringBuilder.append(c).append((c == ' ') ? "" : "ˌ");
          break;
        case "font":
          stringBuilder.append(.contains(String.valueOf((new StringBuilder()).append(c).append(""))) ? .toCharArray()[.indexOf(c)] : c);
          break;
      } 
    } 
    mc.field_71439_g.func_71165_d(String.valueOf(stringBuilder));
  }
  
  @SubscribeEvent
  public void (ClientChatReceivedEvent paramClientChatReceivedEvent) {
    // Byte code:
    //   0: aload_0
    //   1: invokevirtual  : ()Z
    //   4: ifne -> 8
    //   7: return
    //   8: aload_1
    //   9: getfield message : Lnet/minecraft/util/IChatComponent;
    //   12: invokeinterface func_150254_d : ()Ljava/lang/String;
    //   17: invokestatic stripFormatting : (Ljava/lang/String;)Ljava/lang/String;
    //   20: astore_2
    //   21: aload_1
    //   22: getfield message : Lnet/minecraft/util/IChatComponent;
    //   25: invokeinterface func_150254_d : ()Ljava/lang/String;
    //   30: ldc_w '§r§6§m-----------------------------------------§r'
    //   33: invokevirtual equals : (Ljava/lang/Object;)Z
    //   36: ifeq -> 44
    //   39: aload_1
    //   40: iconst_1
    //   41: invokevirtual setCanceled : (Z)V
    //   44: aload_2
    //   45: ldc_w 'We blocked your comment "'
    //   48: invokevirtual startsWith : (Ljava/lang/String;)Z
    //   51: ifeq -> 131
    //   54: new java/lang/StringBuilder
    //   57: dup
    //   58: invokespecial <init> : ()V
    //   61: astore_3
    //   62: iconst_1
    //   63: istore #4
    //   65: iload #4
    //   67: aload_2
    //   68: ldc_w '"'
    //   71: invokevirtual split : (Ljava/lang/String;)[Ljava/lang/String;
    //   74: arraylength
    //   75: iconst_1
    //   76: isub
    //   77: if_icmpge -> 101
    //   80: aload_3
    //   81: aload_2
    //   82: ldc_w '"'
    //   85: invokevirtual split : (Ljava/lang/String;)[Ljava/lang/String;
    //   88: iload #4
    //   90: aaload
    //   91: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   94: pop
    //   95: iinc #4, 1
    //   98: goto -> 65
    //   101: aload_0
    //   102: getfield  : Ljava/lang/String;
    //   105: astore #4
    //   107: aload_1
    //   108: iconst_1
    //   109: invokevirtual setCanceled : (Z)V
    //   112: new java/lang/Thread
    //   115: dup
    //   116: aload_0
    //   117: aload #4
    //   119: aload_3
    //   120: <illegal opcode> run : (Lme/oringo/oringoclient/qolfeatures/module/impl/other/ChatBypass;Ljava/lang/String;Ljava/lang/StringBuilder;)Ljava/lang/Runnable;
    //   125: invokespecial <init> : (Ljava/lang/Runnable;)V
    //   128: invokevirtual start : ()V
    //   131: return
  }
}
