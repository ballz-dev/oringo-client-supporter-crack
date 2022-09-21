package me.oringo.oringoclient.events.impl;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import me.oringo.oringoclient.events.OringoEvent;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;

public class MoveFlyingEvent extends OringoEvent {
  public float ;
  
  public float ;
  
  public float ;
  
  public float ;
  
  public static void (float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7, float paramFloat8) {
    float f1 = 1.0F / paramFloat7;
    float f2 = 1.0F / paramFloat8;
    Tessellator tessellator = Tessellator.func_178181_a();
    WorldRenderer worldRenderer = tessellator.func_178180_c();
    worldRenderer.func_181668_a(7, DefaultVertexFormats.field_181707_g);
    worldRenderer.func_181662_b(paramFloat1, (paramFloat2 + paramFloat6), 0.0D).func_181673_a((paramFloat3 * f1), ((paramFloat4 + paramFloat6) * f2)).func_181675_d();
    worldRenderer.func_181662_b((paramFloat1 + paramFloat5), (paramFloat2 + paramFloat6), 0.0D).func_181673_a(((paramFloat3 + paramFloat5) * f1), ((paramFloat4 + paramFloat6) * f2)).func_181675_d();
    worldRenderer.func_181662_b((paramFloat1 + paramFloat5), paramFloat2, 0.0D).func_181673_a(((paramFloat3 + paramFloat5) * f1), (paramFloat4 * f2)).func_181675_d();
    worldRenderer.func_181662_b(paramFloat1, paramFloat2, 0.0D).func_181673_a((paramFloat3 * f1), (paramFloat4 * f2)).func_181675_d();
    tessellator.func_78381_a();
  }
  
  public MoveFlyingEvent setStrafe(float paramFloat) {
    this. = paramFloat;
    return this;
  }
  
  public static void (String paramString) {
    System.out.print(paramString);
  }
  
  public float getStrafe() {
    return this.;
  }
  
  public float getForward() {
    return this.;
  }
  
  public MoveFlyingEvent setYaw(float paramFloat) {
    this. = paramFloat;
    return this;
  }
  
  public MoveFlyingEvent setForward(float paramFloat) {
    this. = paramFloat;
    return this;
  }
  
  public static JsonObject (String paramString1, String paramString2) {
    try {
      JsonObject jsonObject = (new JsonParser()).parse(new InputStreamReader((new URL(String.format("https://api.hypixel.net/player?uuid=%s&key=%s", new Object[] { paramString1, paramString2 }))).openStream())).getAsJsonObject();
      return (jsonObject.get("player") instanceof com.google.gson.JsonNull) ? null : jsonObject.getAsJsonObject("player");
    } catch (Exception exception) {
      return null;
    } 
  }
  
  public static int () {
    try {
      HttpURLConnection httpURLConnection = (HttpURLConnection)(new URL("https://api.plancke.io/hypixel/v1/punishmentStats")).openConnection();
      httpURLConnection.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:25.0) Gecko/20100101 Firefox/25.0");
      httpURLConnection.setRequestMethod("GET");
      JsonObject jsonObject = (new JsonParser()).parse(new InputStreamReader(httpURLConnection.getInputStream())).getAsJsonObject();
      return jsonObject.get("record").getAsJsonObject().get("staff_total").getAsInt();
    } catch (Exception exception) {
      exception.printStackTrace();
      return -1;
    } 
  }
  
  public MoveFlyingEvent(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4) {
    this. = paramFloat1;
    this. = paramFloat3;
    this. = paramFloat2;
    this. = paramFloat4;
  }
  
  public float getYaw() {
    return this.;
  }
  
  public MoveFlyingEvent setFriction(float paramFloat) {
    this. = paramFloat;
    return this;
  }
  
  public float getFriction() {
    return this.;
  }
}
