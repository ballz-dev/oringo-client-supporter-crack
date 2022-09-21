package me.oringo.oringoclient.utils;

import com.google.gson.JsonObject;

public class WorldToScreen {
  static {
  
  }
  
  public static String (JsonObject paramJsonObject) {
    try {
      return paramJsonObject.get("displayname").getAsString();
    } catch (Exception exception) {
      return "";
    } 
  }
}
