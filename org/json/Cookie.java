package org.json;

public class Cookie {
  public static String escape(String paramString) {
    String str = paramString.trim();
    int i = str.length();
    StringBuilder stringBuilder = new StringBuilder(i);
    for (byte b = 0; b < i; b++) {
      char c = str.charAt(b);
      if (c < ' ' || c == '+' || c == '%' || c == '=' || c == ';') {
        stringBuilder.append('%');
        stringBuilder.append(Character.forDigit((char)(c >>> 4 & 0xF), 16));
        stringBuilder.append(Character.forDigit((char)(c & 0xF), 16));
      } else {
        stringBuilder.append(c);
      } 
    } 
    return stringBuilder.toString();
  }
  
  public static JSONObject toJSONObject(String paramString) throws JSONException {
    JSONObject jSONObject = new JSONObject();
    JSONTokener jSONTokener = new JSONTokener(paramString);
    jSONObject.put("name", jSONTokener.nextTo('='));
    jSONTokener.next('=');
    jSONObject.put("value", jSONTokener.nextTo(';'));
    jSONTokener.next();
    while (jSONTokener.more()) {
      String str2, str1 = unescape(jSONTokener.nextTo("=;"));
      if (jSONTokener.next() != '=') {
        if (str1.equals("secure")) {
          Boolean bool = Boolean.TRUE;
        } else {
          throw jSONTokener.syntaxError("Missing '=' in cookie parameter.");
        } 
      } else {
        str2 = unescape(jSONTokener.nextTo(';'));
        jSONTokener.next();
      } 
      jSONObject.put(str1, str2);
    } 
    return jSONObject;
  }
  
  public static String toString(JSONObject paramJSONObject) throws JSONException {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(escape(paramJSONObject.getString("name")));
    stringBuilder.append("=");
    stringBuilder.append(escape(paramJSONObject.getString("value")));
    if (paramJSONObject.has("expires")) {
      stringBuilder.append(";expires=");
      stringBuilder.append(paramJSONObject.getString("expires"));
    } 
    if (paramJSONObject.has("domain")) {
      stringBuilder.append(";domain=");
      stringBuilder.append(escape(paramJSONObject.getString("domain")));
    } 
    if (paramJSONObject.has("path")) {
      stringBuilder.append(";path=");
      stringBuilder.append(escape(paramJSONObject.getString("path")));
    } 
    if (paramJSONObject.optBoolean("secure"))
      stringBuilder.append(";secure"); 
    return stringBuilder.toString();
  }
  
  public static String unescape(String paramString) {
    int i = paramString.length();
    StringBuilder stringBuilder = new StringBuilder(i);
    for (byte b = 0; b < i; b++) {
      char c = paramString.charAt(b);
      if (c == '+') {
        c = ' ';
      } else if (c == '%' && b + 2 < i) {
        int j = JSONTokener.dehexchar(paramString.charAt(b + 1));
        int k = JSONTokener.dehexchar(paramString.charAt(b + 2));
        if (j >= 0 && k >= 0) {
          c = (char)(j * 16 + k);
          b += 2;
        } 
      } 
      stringBuilder.append(c);
    } 
    return stringBuilder.toString();
  }
}
