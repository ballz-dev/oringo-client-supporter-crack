package org.json;

import java.util.Map;

public class JSONML {
  private static Object parse(XMLTokener paramXMLTokener, boolean paramBoolean1, JSONArray paramJSONArray, boolean paramBoolean2) throws JSONException {
    String str1 = null;
    JSONArray jSONArray = null;
    JSONObject jSONObject = null;
    String str2 = null;
    label116: while (true) {
      if (!paramXMLTokener.more())
        throw paramXMLTokener.syntaxError("Bad XML"); 
      Object object = paramXMLTokener.nextContent();
      if (object == XML.LT) {
        object = paramXMLTokener.nextToken();
        if (object instanceof Character) {
          if (object == XML.SLASH) {
            object = paramXMLTokener.nextToken();
            if (!(object instanceof String))
              throw new JSONException("Expected a closing name instead of '" + object + "'."); 
            if (paramXMLTokener.nextToken() != XML.GT)
              throw paramXMLTokener.syntaxError("Misshaped close tag"); 
            return object;
          } 
          if (object == XML.BANG) {
            char c = paramXMLTokener.next();
            if (c == '-') {
              if (paramXMLTokener.next() == '-') {
                paramXMLTokener.skipPast("-->");
                continue;
              } 
              paramXMLTokener.back();
              continue;
            } 
            if (c == '[') {
              object = paramXMLTokener.nextToken();
              if (object.equals("CDATA") && paramXMLTokener.next() == '[') {
                if (paramJSONArray != null)
                  paramJSONArray.put(paramXMLTokener.nextCDATA()); 
                continue;
              } 
              throw paramXMLTokener.syntaxError("Expected 'CDATA['");
            } 
            byte b = 1;
            while (true) {
              object = paramXMLTokener.nextMeta();
              if (object == null)
                throw paramXMLTokener.syntaxError("Missing '>' after '<!'."); 
              if (object == XML.LT) {
                b++;
              } else if (object == XML.GT) {
                b--;
              } 
              if (b <= 0)
                continue label116; 
            } 
            break;
          } 
          if (object == XML.QUEST) {
            paramXMLTokener.skipPast("?>");
            continue;
          } 
          throw paramXMLTokener.syntaxError("Misshaped tag");
        } 
        if (!(object instanceof String))
          throw paramXMLTokener.syntaxError("Bad tagName '" + object + "'."); 
        str2 = (String)object;
        jSONArray = new JSONArray();
        jSONObject = new JSONObject();
        if (paramBoolean1) {
          jSONArray.put(str2);
          if (paramJSONArray != null)
            paramJSONArray.put(jSONArray); 
        } else {
          jSONObject.put("tagName", str2);
          if (paramJSONArray != null)
            paramJSONArray.put(jSONObject); 
        } 
        object = null;
        while (true) {
          if (object == null)
            object = paramXMLTokener.nextToken(); 
          if (object == null)
            throw paramXMLTokener.syntaxError("Misshaped tag"); 
          if (!(object instanceof String))
            break; 
          String str = (String)object;
          if (!paramBoolean1 && ("tagName".equals(str) || "childNode".equals(str)))
            throw paramXMLTokener.syntaxError("Reserved attribute."); 
          object = paramXMLTokener.nextToken();
          if (object == XML.EQ) {
            object = paramXMLTokener.nextToken();
            if (!(object instanceof String))
              throw paramXMLTokener.syntaxError("Missing value"); 
            jSONObject.accumulate(str, paramBoolean2 ? object : XML.stringToValue((String)object));
            object = null;
            continue;
          } 
          jSONObject.accumulate(str, "");
        } 
        if (paramBoolean1 && jSONObject.length() > 0)
          jSONArray.put(jSONObject); 
        if (object == XML.SLASH) {
          if (paramXMLTokener.nextToken() != XML.GT)
            throw paramXMLTokener.syntaxError("Misshaped tag"); 
          if (paramJSONArray == null) {
            if (paramBoolean1)
              return jSONArray; 
            return jSONObject;
          } 
          continue;
        } 
        if (object != XML.GT)
          throw paramXMLTokener.syntaxError("Misshaped tag"); 
        str1 = (String)parse(paramXMLTokener, paramBoolean1, jSONArray, paramBoolean2);
        if (str1 != null) {
          if (!str1.equals(str2))
            throw paramXMLTokener.syntaxError("Mismatched '" + str2 + "' and '" + str1 + "'"); 
          str2 = null;
          if (!paramBoolean1 && jSONArray.length() > 0)
            jSONObject.put("childNodes", jSONArray); 
          if (paramJSONArray == null) {
            if (paramBoolean1)
              return jSONArray; 
            return jSONObject;
          } 
        } 
        continue;
      } 
      if (paramJSONArray != null)
        paramJSONArray.put((object instanceof String) ? (paramBoolean2 ? 
            XML.unescape((String)object) : XML.stringToValue((String)object)) : object); 
    } 
  }
  
  public static JSONArray toJSONArray(String paramString) throws JSONException {
    return (JSONArray)parse(new XMLTokener(paramString), true, null, false);
  }
  
  public static JSONArray toJSONArray(String paramString, boolean paramBoolean) throws JSONException {
    return (JSONArray)parse(new XMLTokener(paramString), true, null, paramBoolean);
  }
  
  public static JSONArray toJSONArray(XMLTokener paramXMLTokener, boolean paramBoolean) throws JSONException {
    return (JSONArray)parse(paramXMLTokener, true, null, paramBoolean);
  }
  
  public static JSONArray toJSONArray(XMLTokener paramXMLTokener) throws JSONException {
    return (JSONArray)parse(paramXMLTokener, true, null, false);
  }
  
  public static JSONObject toJSONObject(String paramString) throws JSONException {
    return (JSONObject)parse(new XMLTokener(paramString), false, null, false);
  }
  
  public static JSONObject toJSONObject(String paramString, boolean paramBoolean) throws JSONException {
    return (JSONObject)parse(new XMLTokener(paramString), false, null, paramBoolean);
  }
  
  public static JSONObject toJSONObject(XMLTokener paramXMLTokener) throws JSONException {
    return (JSONObject)parse(paramXMLTokener, false, null, false);
  }
  
  public static JSONObject toJSONObject(XMLTokener paramXMLTokener, boolean paramBoolean) throws JSONException {
    return (JSONObject)parse(paramXMLTokener, false, null, paramBoolean);
  }
  
  public static String toString(JSONArray paramJSONArray) throws JSONException {
    byte b;
    StringBuilder stringBuilder = new StringBuilder();
    String str = paramJSONArray.getString(0);
    XML.noSpace(str);
    str = XML.escape(str);
    stringBuilder.append('<');
    stringBuilder.append(str);
    Object object = paramJSONArray.opt(1);
    if (object instanceof JSONObject) {
      b = 2;
      JSONObject jSONObject = (JSONObject)object;
      for (Map.Entry<String, Object> entry : jSONObject.entrySet()) {
        String str1 = (String)entry.getKey();
        XML.noSpace(str1);
        Object object1 = entry.getValue();
        if (object1 != null) {
          stringBuilder.append(' ');
          stringBuilder.append(XML.escape(str1));
          stringBuilder.append('=');
          stringBuilder.append('"');
          stringBuilder.append(XML.escape(object1.toString()));
          stringBuilder.append('"');
        } 
      } 
    } else {
      b = 1;
    } 
    int i = paramJSONArray.length();
    if (b >= i) {
      stringBuilder.append('/');
      stringBuilder.append('>');
    } else {
      stringBuilder.append('>');
      while (true) {
        object = paramJSONArray.get(b);
        b++;
        if (object != null)
          if (object instanceof String) {
            stringBuilder.append(XML.escape(object.toString()));
          } else if (object instanceof JSONObject) {
            stringBuilder.append(toString((JSONObject)object));
          } else if (object instanceof JSONArray) {
            stringBuilder.append(toString((JSONArray)object));
          } else {
            stringBuilder.append(object.toString());
          }  
        if (b >= i) {
          stringBuilder.append('<');
          stringBuilder.append('/');
          stringBuilder.append(str);
          stringBuilder.append('>');
          return stringBuilder.toString();
        } 
      } 
    } 
    return stringBuilder.toString();
  }
  
  public static String toString(JSONObject paramJSONObject) throws JSONException {
    StringBuilder stringBuilder = new StringBuilder();
    String str = paramJSONObject.optString("tagName");
    if (str == null)
      return XML.escape(paramJSONObject.toString()); 
    XML.noSpace(str);
    str = XML.escape(str);
    stringBuilder.append('<');
    stringBuilder.append(str);
    for (Map.Entry<String, Object> entry : paramJSONObject.entrySet()) {
      String str1 = (String)entry.getKey();
      if (!"tagName".equals(str1) && !"childNodes".equals(str1)) {
        XML.noSpace(str1);
        Object object = entry.getValue();
        if (object != null) {
          stringBuilder.append(' ');
          stringBuilder.append(XML.escape(str1));
          stringBuilder.append('=');
          stringBuilder.append('"');
          stringBuilder.append(XML.escape(object.toString()));
          stringBuilder.append('"');
        } 
      } 
    } 
    JSONArray jSONArray = paramJSONObject.optJSONArray("childNodes");
    if (jSONArray == null) {
      stringBuilder.append('/');
      stringBuilder.append('>');
    } else {
      stringBuilder.append('>');
      int i = jSONArray.length();
      for (byte b = 0; b < i; b++) {
        Object object = jSONArray.get(b);
        if (object != null)
          if (object instanceof String) {
            stringBuilder.append(XML.escape(object.toString()));
          } else if (object instanceof JSONObject) {
            stringBuilder.append(toString((JSONObject)object));
          } else if (object instanceof JSONArray) {
            stringBuilder.append(toString((JSONArray)object));
          } else {
            stringBuilder.append(object.toString());
          }  
      } 
      stringBuilder.append('<');
      stringBuilder.append('/');
      stringBuilder.append(str);
      stringBuilder.append('>');
    } 
    return stringBuilder.toString();
  }
}
