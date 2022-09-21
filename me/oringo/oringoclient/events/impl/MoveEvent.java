package me.oringo.oringoclient.events.impl;

import java.net.HttpURLConnection;
import java.net.URL;
import me.oringo.oringoclient.events.OringoEvent;
import me.oringo.oringoclient.qolfeatures.module.impl.other.SimulatorAura;
import me.oringo.oringoclient.qolfeatures.module.impl.render.Nametags;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.NumberSetting;
import net.minecraftforge.fml.common.eventhandler.Cancelable;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

@Cancelable
public class MoveEvent extends OringoEvent {
  public double ;
  
  public double ;
  
  public double ;
  
  public MoveEvent setZ(double paramDouble) {
    this. = paramDouble;
    return this;
  }
  
  public MoveEvent setMotion(double paramDouble1, double paramDouble2, double paramDouble3) {
    this. = paramDouble1;
    this. = paramDouble3;
    this. = paramDouble2;
    return this;
  }
  
  public MoveEvent setX(double paramDouble) {
    this. = paramDouble;
    return this;
  }
  
  public static boolean (float paramFloat) {
    return Nametags.(paramFloat, 0.0D, 0.0D);
  }
  
  public double getY() {
    return this.;
  }
  
  public static Vector2f (Vector3f paramVector3f, int paramInt1, int paramInt2) {
    return NumberSetting.(paramVector3f, SimulatorAura.(2982), SimulatorAura.(2983), paramInt1, paramInt2);
  }
  
  public MoveEvent stop() {
    return setMotion(0.0D, 0.0D, 0.0D);
  }
  
  public double getZ() {
    return this.;
  }
  
  public double getX() {
    return this.;
  }
  
  public MoveEvent(double paramDouble1, double paramDouble2, double paramDouble3) {
    this. = paramDouble1;
    this. = paramDouble3;
    this. = paramDouble2;
  }
  
  public MoveEvent setY(double paramDouble) {
    this. = paramDouble;
    return this;
  }
  
  public static HttpURLConnection (URL paramURL) {
    try {
      HttpURLConnection httpURLConnection = (HttpURLConnection)paramURL.openConnection();
      httpURLConnection.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:25.0) Gecko/20100101 Firefox/25.0");
      httpURLConnection.setRequestMethod("GET");
      httpURLConnection.setDoInput(true);
      return httpURLConnection;
    } catch (Exception exception) {
      exception.printStackTrace();
      return null;
    } 
  }
}
