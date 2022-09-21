package org.json;

public class CDL {
  private static String getValue(JSONTokener paramJSONTokener) throws JSONException {
    char c;
    char c1;
    StringBuffer stringBuffer;
    do {
      c = paramJSONTokener.next();
    } while (c == ' ' || c == '\t');
    switch (c) {
      case '\000':
        return null;
      case '"':
      case '\'':
        c1 = c;
        stringBuffer = new StringBuffer();
        while (true) {
          c = paramJSONTokener.next();
          if (c == c1) {
            char c2 = paramJSONTokener.next();
            if (c2 != '"') {
              if (c2 > '\000')
                paramJSONTokener.back(); 
              break;
            } 
          } 
          if (c == '\000' || c == '\n' || c == '\r')
            throw paramJSONTokener.syntaxError("Missing close quote '" + c1 + "'."); 
          stringBuffer.append(c);
        } 
        return stringBuffer.toString();
      case ',':
        paramJSONTokener.back();
        return "";
    } 
    paramJSONTokener.back();
    return paramJSONTokener.nextTo(',');
  }
  
  public static JSONArray rowToJSONArray(JSONTokener paramJSONTokener) throws JSONException {
    JSONArray jSONArray = new JSONArray();
    while (true) {
      String str = getValue(paramJSONTokener);
      char c = paramJSONTokener.next();
      if (str == null || (jSONArray
        .length() == 0 && str.length() == 0 && c != ','))
        return null; 
      jSONArray.put(str);
      while (c != ',') {
        if (c != ' ') {
          if (c == '\n' || c == '\r' || c == '\000')
            return jSONArray; 
          throw paramJSONTokener.syntaxError("Bad character '" + c + "' (" + c + ").");
        } 
        c = paramJSONTokener.next();
      } 
    } 
  }
  
  public static JSONObject rowToJSONObject(JSONArray paramJSONArray, JSONTokener paramJSONTokener) throws JSONException {
    JSONArray jSONArray = rowToJSONArray(paramJSONTokener);
    return (jSONArray != null) ? jSONArray.toJSONObject(paramJSONArray) : null;
  }
  
  public static String rowToString(JSONArray paramJSONArray) {
    StringBuilder stringBuilder = new StringBuilder();
    for (byte b = 0; b < paramJSONArray.length(); b++) {
      if (b > 0)
        stringBuilder.append(','); 
      Object object = paramJSONArray.opt(b);
      if (object != null) {
        String str = object.toString();
        if (str.length() > 0 && (str.indexOf(',') >= 0 || str
          .indexOf('\n') >= 0 || str.indexOf('\r') >= 0 || str
          .indexOf(false) >= 0 || str.charAt(0) == '"')) {
          stringBuilder.append('"');
          int i = str.length();
          for (byte b1 = 0; b1 < i; b1++) {
            char c = str.charAt(b1);
            if (c >= ' ' && c != '"')
              stringBuilder.append(c); 
          } 
          stringBuilder.append('"');
        } else {
          stringBuilder.append(str);
        } 
      } 
    } 
    stringBuilder.append('\n');
    return stringBuilder.toString();
  }
  
  public static JSONArray toJSONArray(String paramString) throws JSONException {
    return toJSONArray(new JSONTokener(paramString));
  }
  
  public static JSONArray toJSONArray(JSONTokener paramJSONTokener) throws JSONException {
    return toJSONArray(rowToJSONArray(paramJSONTokener), paramJSONTokener);
  }
  
  public static JSONArray toJSONArray(JSONArray paramJSONArray, String paramString) throws JSONException {
    return toJSONArray(paramJSONArray, new JSONTokener(paramString));
  }
  
  public static JSONArray toJSONArray(JSONArray paramJSONArray, JSONTokener paramJSONTokener) throws JSONException {
    if (paramJSONArray == null || paramJSONArray.length() == 0)
      return null; 
    JSONArray jSONArray = new JSONArray();
    while (true) {
      JSONObject jSONObject = rowToJSONObject(paramJSONArray, paramJSONTokener);
      if (jSONObject == null)
        break; 
      jSONArray.put(jSONObject);
    } 
    if (jSONArray.length() == 0)
      return null; 
    return jSONArray;
  }
  
  public static String toString(JSONArray paramJSONArray) throws JSONException {
    JSONObject jSONObject = paramJSONArray.optJSONObject(0);
    if (jSONObject != null) {
      JSONArray jSONArray = jSONObject.names();
      if (jSONArray != null)
        return rowToString(jSONArray) + toString(jSONArray, paramJSONArray); 
    } 
    return null;
  }
  
  public static String toString(JSONArray paramJSONArray1, JSONArray paramJSONArray2) throws JSONException {
    if (paramJSONArray1 == null || paramJSONArray1.length() == 0)
      return null; 
    StringBuffer stringBuffer = new StringBuffer();
    for (byte b = 0; b < paramJSONArray2.length(); b++) {
      JSONObject jSONObject = paramJSONArray2.optJSONObject(b);
      if (jSONObject != null)
        stringBuffer.append(rowToString(jSONObject.toJSONArray(paramJSONArray1))); 
    } 
    return stringBuffer.toString();
  }
}
