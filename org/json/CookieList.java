package org.json;

import java.util.Map;

public class CookieList {
  public static JSONObject toJSONObject(String paramString) throws JSONException {
    JSONObject jSONObject = new JSONObject();
    JSONTokener jSONTokener = new JSONTokener(paramString);
    while (jSONTokener.more()) {
      String str = Cookie.unescape(jSONTokener.nextTo('='));
      jSONTokener.next('=');
      jSONObject.put(str, Cookie.unescape(jSONTokener.nextTo(';')));
      jSONTokener.next();
    } 
    return jSONObject;
  }
  
  public static String toString(JSONObject paramJSONObject) throws JSONException {
    boolean bool = false;
    StringBuilder stringBuilder = new StringBuilder();
    for (Map.Entry<String, Object> entry : paramJSONObject.entrySet()) {
      String str = (String)entry.getKey();
      Object object = entry.getValue();
      if (!JSONObject.NULL.equals(object)) {
        if (bool)
          stringBuilder.append(';'); 
        stringBuilder.append(Cookie.escape(str));
        stringBuilder.append("=");
        stringBuilder.append(Cookie.escape(object.toString()));
        bool = true;
      } 
    } 
    return stringBuilder.toString();
  }
}
