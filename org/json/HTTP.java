package org.json;

import java.util.Locale;
import java.util.Map;

public class HTTP {
  public static final String CRLF = "\r\n";
  
  public static JSONObject toJSONObject(String paramString) throws JSONException {
    JSONObject jSONObject = new JSONObject();
    HTTPTokener hTTPTokener = new HTTPTokener(paramString);
    String str = hTTPTokener.nextToken();
    if (str.toUpperCase(Locale.ROOT).startsWith("HTTP")) {
      jSONObject.put("HTTP-Version", str);
      jSONObject.put("Status-Code", hTTPTokener.nextToken());
      jSONObject.put("Reason-Phrase", hTTPTokener.nextTo(false));
      hTTPTokener.next();
    } else {
      jSONObject.put("Method", str);
      jSONObject.put("Request-URI", hTTPTokener.nextToken());
      jSONObject.put("HTTP-Version", hTTPTokener.nextToken());
    } 
    while (hTTPTokener.more()) {
      String str1 = hTTPTokener.nextTo(':');
      hTTPTokener.next(':');
      jSONObject.put(str1, hTTPTokener.nextTo(false));
      hTTPTokener.next();
    } 
    return jSONObject;
  }
  
  public static String toString(JSONObject paramJSONObject) throws JSONException {
    StringBuilder stringBuilder = new StringBuilder();
    if (paramJSONObject.has("Status-Code") && paramJSONObject.has("Reason-Phrase")) {
      stringBuilder.append(paramJSONObject.getString("HTTP-Version"));
      stringBuilder.append(' ');
      stringBuilder.append(paramJSONObject.getString("Status-Code"));
      stringBuilder.append(' ');
      stringBuilder.append(paramJSONObject.getString("Reason-Phrase"));
    } else if (paramJSONObject.has("Method") && paramJSONObject.has("Request-URI")) {
      stringBuilder.append(paramJSONObject.getString("Method"));
      stringBuilder.append(' ');
      stringBuilder.append('"');
      stringBuilder.append(paramJSONObject.getString("Request-URI"));
      stringBuilder.append('"');
      stringBuilder.append(' ');
      stringBuilder.append(paramJSONObject.getString("HTTP-Version"));
    } else {
      throw new JSONException("Not enough material for an HTTP header.");
    } 
    stringBuilder.append("\r\n");
    for (Map.Entry<String, Object> entry : paramJSONObject.entrySet()) {
      String str = (String)entry.getKey();
      if (!"HTTP-Version".equals(str) && !"Status-Code".equals(str) && 
        !"Reason-Phrase".equals(str) && !"Method".equals(str) && 
        !"Request-URI".equals(str) && !JSONObject.NULL.equals(entry.getValue())) {
        stringBuilder.append(str);
        stringBuilder.append(": ");
        stringBuilder.append(paramJSONObject.optString(str));
        stringBuilder.append("\r\n");
      } 
    } 
    stringBuilder.append("\r\n");
    return stringBuilder.toString();
  }
}
