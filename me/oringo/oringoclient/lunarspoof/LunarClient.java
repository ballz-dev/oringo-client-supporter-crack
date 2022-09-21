package me.oringo.oringoclient.lunarspoof;

import com.google.common.collect.ImmutableMap;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.authlib.minecraft.MinecraftSessionService;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import java.math.BigInteger;
import java.net.Proxy;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;
import java.security.PublicKey;
import java.util.Base64;
import java.util.Map;
import java.util.UUID;
import javax.crypto.SecretKey;
import me.oringo.oringoclient.OringoClient;
import me.oringo.oringoclient.events.impl.MoveEvent;
import me.oringo.oringoclient.qolfeatures.module.impl.combat.KillAura;
import me.oringo.oringoclient.qolfeatures.module.impl.other.HClip;
import me.oringo.oringoclient.qolfeatures.module.impl.other.PVPInfo;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.util.CryptManager;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;

public class LunarClient extends WebSocketClient {
  public void (Exception paramException) {
    paramException.printStackTrace();
  }
  
  public void (String paramString) {}
  
  public void (ByteBuffer paramByteBuffer) {
    System.out.println("Message received");
    JsonObject jsonObject = (new JsonParser()).parse(new String(paramByteBuffer.array())).getAsJsonObject();
    switch (jsonObject.get("packetType").getAsString()) {
      case "SPacketEncryptionRequest":
        System.out.println("Encryption received");
        (jsonObject);
        break;
    } 
  }
  
  public void (JsonObject paramJsonObject) {
    System.out.println(paramJsonObject);
    SecretKey secretKey = CryptManager.func_75890_a();
    PublicKey publicKey = CryptManager.func_75896_a(Base64.getUrlDecoder().decode(paramJsonObject.get("publicKey").getAsString()));
    byte[] arrayOfByte1 = Base64.getUrlDecoder().decode(paramJsonObject.get("randomBytes").getAsString());
    byte[] arrayOfByte2 = CryptManager.func_75895_a("", publicKey, secretKey);
    if (arrayOfByte2 == null)
      return; 
    String str = (new BigInteger(arrayOfByte2)).toString(16);
    try {
      MinecraftSessionService minecraftSessionService = (new YggdrasilAuthenticationService(Proxy.NO_PROXY, UUID.randomUUID().toString())).createMinecraftSessionService();
      minecraftSessionService.joinServer(Minecraft.func_71410_x().func_110432_I().func_148256_e(), Minecraft.func_71410_x().func_110432_I().func_148254_d(), str);
      System.out.println("Joined received");
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
    (secretKey, publicKey, arrayOfByte1);
    System.out.println("Encryption sent");
  }
  
  public static void (int paramInt) {
    OringoClient.mc.field_71439_g.field_71071_by.field_70461_c = paramInt;
    PVPInfo.();
  }
  
  public static void (float paramFloat1, float paramFloat2, float paramFloat3, int paramInt) {
    Tessellator tessellator = Tessellator.func_178181_a();
    WorldRenderer worldRenderer = tessellator.func_178180_c();
    worldRenderer.func_181668_a(6, DefaultVertexFormats.field_181705_e);
    GlStateManager.func_179137_b(paramFloat1, paramFloat2, 0.0D);
    worldRenderer.func_181662_b(0.0D, 0.0D, 0.0D).func_181675_d();
    byte b = 20;
    double d;
    for (d = 0.0D; d < b; d++) {
      double d1 = Math.toRadians(d / b * 90.0D + paramInt);
      worldRenderer.func_181662_b(paramFloat3 * Math.sin(d1), paramFloat3 * Math.cos(d1), 0.0D).func_181675_d();
    } 
    tessellator.func_78381_a();
    GlStateManager.func_179137_b(-paramFloat1, -paramFloat2, 0.0D);
  }
  
  public LunarClient() throws URISyntaxException {
    super(new URI("wss://authenticator.lunarclientprod.com/"), (Draft)new Draft_6455(), (Map)ImmutableMap.builder().put("username", Minecraft.func_71410_x().func_110432_I().func_111285_a()).put("playerId", HClip.(Minecraft.func_71410_x().func_110432_I().func_148255_b())).build(), 30000);
  }
  
  public void (int paramInt, String paramString, boolean paramBoolean) {
    System.out.println(String.format("Closed %s, %s, %s", new Object[] { Integer.valueOf(paramInt), paramString, Boolean.valueOf(paramBoolean) }));
  }
  
  public void (SecretKey paramSecretKey, PublicKey paramPublicKey, byte[] paramArrayOfbyte) {
    JsonObject jsonObject = new JsonObject();
    jsonObject.addProperty("packetType", "CPacketEncryptionResponse");
    jsonObject.addProperty("secretKey", Base64.getUrlEncoder().encodeToString(CryptManager.func_75894_a(paramPublicKey, paramSecretKey.getEncoded())));
    jsonObject.addProperty("publicKey", Base64.getUrlEncoder().encodeToString(CryptManager.func_75894_a(paramPublicKey, paramArrayOfbyte)));
    send(jsonObject.toString());
  }
  
  public static void (MoveEvent paramMoveEvent, double paramDouble) {
    double d1 = OringoClient.mc.field_71439_g.field_71158_b.field_78900_b;
    double d2 = OringoClient.mc.field_71439_g.field_71158_b.field_78902_a;
    float f = ((KillAura. != null && KillAura..()) || OringoClient..()) ? HClip.((Entity)KillAura.).() : OringoClient.mc.field_71439_g.field_70177_z;
    if (d1 == 0.0D && d2 == 0.0D) {
      OringoClient.mc.field_71439_g.field_70159_w = 0.0D;
      OringoClient.mc.field_71439_g.field_70179_y = 0.0D;
      if (paramMoveEvent != null) {
        paramMoveEvent.setX(0.0D);
        paramMoveEvent.setZ(0.0D);
      } 
    } else {
      if (d1 != 0.0D) {
        if (d2 > 0.0D) {
          f += ((d1 > 0.0D) ? -45 : 45);
        } else if (d2 < 0.0D) {
          f += ((d1 > 0.0D) ? 45 : -45);
        } 
        d2 = 0.0D;
        if (d1 > 0.0D) {
          d1 = 1.0D;
        } else if (d1 < 0.0D) {
          d1 = -1.0D;
        } 
      } 
      double d3 = Math.cos(Math.toRadians((f + 90.0F)));
      double d4 = Math.sin(Math.toRadians((f + 90.0F)));
      OringoClient.mc.field_71439_g.field_70159_w = d1 * paramDouble * d3 + d2 * paramDouble * d4;
      OringoClient.mc.field_71439_g.field_70179_y = d1 * paramDouble * d4 - d2 * paramDouble * d3;
      if (paramMoveEvent != null) {
        paramMoveEvent.setX(OringoClient.mc.field_71439_g.field_70159_w);
        paramMoveEvent.setZ(OringoClient.mc.field_71439_g.field_70179_y);
      } 
    } 
  }
  
  static {
  
  }
  
  public void (ServerHandshake paramServerHandshake) {
    System.out.println("Connection open");
  }
}
