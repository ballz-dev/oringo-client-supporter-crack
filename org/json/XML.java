package org.json;

import java.util.Iterator;
import java.util.Map;

public class XML {
  public static final Character APOS;
  
  public static final Character BANG;
  
  public static final Character QUEST;
  
  public static final Character QUOT;
  
  public static final Character GT;
  
  public static final Character AMP = Character.valueOf('&');
  
  public static final Character SLASH;
  
  public static final Character LT;
  
  public static final Character EQ;
  
  static {
    APOS = Character.valueOf('\'');
    BANG = Character.valueOf('!');
    EQ = Character.valueOf('=');
    GT = Character.valueOf('>');
    LT = Character.valueOf('<');
    QUEST = Character.valueOf('?');
    QUOT = Character.valueOf('"');
    SLASH = Character.valueOf('/');
  }
  
  private static Iterable<Integer> codePointIterator(final String string) {
    return new Iterable<Integer>() {
        public Iterator<Integer> iterator() {
          return new Iterator<Integer>() {
              private int nextIndex = 0;
              
              private int length = string.length();
              
              public boolean hasNext() {
                return (this.nextIndex < this.length);
              }
              
              public Integer next() {
                int i = string.codePointAt(this.nextIndex);
                this.nextIndex += Character.charCount(i);
                return Integer.valueOf(i);
              }
              
              public void remove() {
                throw new UnsupportedOperationException();
              }
            };
        }
      };
  }
  
  public static String escape(String paramString) {
    StringBuilder stringBuilder = new StringBuilder(paramString.length());
    for (Iterator<Integer> iterator = codePointIterator(paramString).iterator(); iterator.hasNext(); ) {
      int i = ((Integer)iterator.next()).intValue();
      switch (i) {
        case 38:
          stringBuilder.append("&amp;");
          continue;
        case 60:
          stringBuilder.append("&lt;");
          continue;
        case 62:
          stringBuilder.append("&gt;");
          continue;
        case 34:
          stringBuilder.append("&quot;");
          continue;
        case 39:
          stringBuilder.append("&apos;");
          continue;
      } 
      if (mustEscape(i)) {
        stringBuilder.append("&#x");
        stringBuilder.append(Integer.toHexString(i));
        stringBuilder.append(';');
        continue;
      } 
      stringBuilder.appendCodePoint(i);
    } 
    return stringBuilder.toString();
  }
  
  private static boolean mustEscape(int paramInt) {
    return ((Character.isISOControl(paramInt) && paramInt != 9 && paramInt != 10 && paramInt != 13) || ((paramInt < 32 || paramInt > 55295) && (paramInt < 57344 || paramInt > 65533) && (paramInt < 65536 || paramInt > 1114111)));
  }
  
  public static String unescape(String paramString) {
    StringBuilder stringBuilder = new StringBuilder(paramString.length());
    for (int i = 0, j = paramString.length(); i < j; i++) {
      char c = paramString.charAt(i);
      if (c == '&') {
        int k = paramString.indexOf(';', i);
        if (k > i) {
          String str = paramString.substring(i + 1, k);
          stringBuilder.append(XMLTokener.unescapeEntity(str));
          i += str.length() + 1;
        } else {
          stringBuilder.append(c);
        } 
      } else {
        stringBuilder.append(c);
      } 
    } 
    return stringBuilder.toString();
  }
  
  public static void noSpace(String paramString) throws JSONException {
    int i = paramString.length();
    if (i == 0)
      throw new JSONException("Empty string."); 
    for (byte b = 0; b < i; b++) {
      if (Character.isWhitespace(paramString.charAt(b)))
        throw new JSONException("'" + paramString + "' contains a space character."); 
    } 
  }
  
  private static boolean parse(XMLTokener paramXMLTokener, JSONObject paramJSONObject, String paramString, boolean paramBoolean) throws JSONException {
    JSONObject jSONObject = null;
    Object object = paramXMLTokener.nextToken();
    if (object == BANG) {
      char c = paramXMLTokener.next();
      if (c == '-') {
        if (paramXMLTokener.next() == '-') {
          paramXMLTokener.skipPast("-->");
          return false;
        } 
        paramXMLTokener.back();
      } else if (c == '[') {
        object = paramXMLTokener.nextToken();
        if ("CDATA".equals(object) && 
          paramXMLTokener.next() == '[') {
          String str1 = paramXMLTokener.nextCDATA();
          if (str1.length() > 0)
            paramJSONObject.accumulate("content", str1); 
          return false;
        } 
        throw paramXMLTokener.syntaxError("Expected 'CDATA['");
      } 
      byte b = 1;
      while (true) {
        object = paramXMLTokener.nextMeta();
        if (object == null)
          throw paramXMLTokener.syntaxError("Missing '>' after '<!'."); 
        if (object == LT) {
          b++;
        } else if (object == GT) {
          b--;
        } 
        if (b <= 0)
          return false; 
      } 
    } 
    if (object == QUEST) {
      paramXMLTokener.skipPast("?>");
      return false;
    } 
    if (object == SLASH) {
      object = paramXMLTokener.nextToken();
      if (paramString == null)
        throw paramXMLTokener.syntaxError("Mismatched close tag " + object); 
      if (!object.equals(paramString))
        throw paramXMLTokener.syntaxError("Mismatched " + paramString + " and " + object); 
      if (paramXMLTokener.nextToken() != GT)
        throw paramXMLTokener.syntaxError("Misshaped close tag"); 
      return true;
    } 
    if (object instanceof Character)
      throw paramXMLTokener.syntaxError("Misshaped tag"); 
    String str = (String)object;
    object = null;
    jSONObject = new JSONObject();
    while (true) {
      if (object == null)
        object = paramXMLTokener.nextToken(); 
      if (object instanceof String) {
        String str1 = (String)object;
        object = paramXMLTokener.nextToken();
        if (object == EQ) {
          object = paramXMLTokener.nextToken();
          if (!(object instanceof String))
            throw paramXMLTokener.syntaxError("Missing value"); 
          jSONObject.accumulate(str1, paramBoolean ? object : 
              stringToValue((String)object));
          object = null;
          continue;
        } 
        jSONObject.accumulate(str1, "");
        continue;
      } 
      break;
    } 
    if (object == SLASH) {
      if (paramXMLTokener.nextToken() != GT)
        throw paramXMLTokener.syntaxError("Misshaped tag"); 
      if (jSONObject.length() > 0) {
        paramJSONObject.accumulate(str, jSONObject);
      } else {
        paramJSONObject.accumulate(str, "");
      } 
      return false;
    } 
    if (object == GT)
      while (true) {
        object = paramXMLTokener.nextContent();
        if (object == null) {
          if (str != null)
            throw paramXMLTokener.syntaxError("Unclosed tag " + str); 
          return false;
        } 
        if (object instanceof String) {
          String str1 = (String)object;
          if (str1.length() > 0)
            jSONObject.accumulate("content", paramBoolean ? str1 : 
                stringToValue(str1)); 
          continue;
        } 
        if (object == LT)
          if (parse(paramXMLTokener, jSONObject, str, paramBoolean)) {
            if (jSONObject.length() == 0) {
              paramJSONObject.accumulate(str, "");
            } else if (jSONObject.length() == 1 && jSONObject
              .opt("content") != null) {
              paramJSONObject.accumulate(str, jSONObject
                  .opt("content"));
            } else {
              paramJSONObject.accumulate(str, jSONObject);
            } 
            return false;
          }  
      }  
    throw paramXMLTokener.syntaxError("Misshaped tag");
  }
  
  public static Object stringToValue(String paramString) {
    return JSONObject.stringToValue(paramString);
  }
  
  public static JSONObject toJSONObject(String paramString) throws JSONException {
    return toJSONObject(paramString, false);
  }
  
  public static JSONObject toJSONObject(String paramString, boolean paramBoolean) throws JSONException {
    JSONObject jSONObject = new JSONObject();
    XMLTokener xMLTokener = new XMLTokener(paramString);
    while (xMLTokener.more() && xMLTokener.skipPast("<"))
      parse(xMLTokener, jSONObject, null, paramBoolean); 
    return jSONObject;
  }
  
  public static String toString(Object paramObject) throws JSONException {
    return toString(paramObject, null);
  }
  
  public static String toString(Object paramObject, String paramString) throws JSONException {
    StringBuilder stringBuilder = new StringBuilder();
    if (paramObject instanceof JSONObject) {
      if (paramString != null) {
        stringBuilder.append('<');
        stringBuilder.append(paramString);
        stringBuilder.append('>');
      } 
      JSONObject jSONObject = (JSONObject)paramObject;
      for (Map.Entry<String, Object> entry : jSONObject.entrySet()) {
        String str1 = (String)entry.getKey();
        Object object = entry.getValue();
        if (object == null) {
          object = "";
        } else if (object.getClass().isArray()) {
          object = new JSONArray(object);
        } 
        if ("content".equals(str1)) {
          if (object instanceof JSONArray) {
            JSONArray jSONArray = (JSONArray)object;
            byte b = 0;
            for (Object object1 : jSONArray) {
              if (b)
                stringBuilder.append('\n'); 
              stringBuilder.append(escape(object1.toString()));
              b++;
            } 
            continue;
          } 
          stringBuilder.append(escape(object.toString()));
          continue;
        } 
        if (object instanceof JSONArray) {
          JSONArray jSONArray = (JSONArray)object;
          for (Object object1 : jSONArray) {
            if (object1 instanceof JSONArray) {
              stringBuilder.append('<');
              stringBuilder.append(str1);
              stringBuilder.append('>');
              stringBuilder.append(toString(object1));
              stringBuilder.append("</");
              stringBuilder.append(str1);
              stringBuilder.append('>');
              continue;
            } 
            stringBuilder.append(toString(object1, str1));
          } 
          continue;
        } 
        if ("".equals(object)) {
          stringBuilder.append('<');
          stringBuilder.append(str1);
          stringBuilder.append("/>");
          continue;
        } 
        stringBuilder.append(toString(object, str1));
      } 
      if (paramString != null) {
        stringBuilder.append("</");
        stringBuilder.append(paramString);
        stringBuilder.append('>');
      } 
      return stringBuilder.toString();
    } 
    if (paramObject != null && (paramObject instanceof JSONArray || paramObject.getClass().isArray())) {
      JSONArray jSONArray;
      if (paramObject.getClass().isArray()) {
        jSONArray = new JSONArray(paramObject);
      } else {
        jSONArray = (JSONArray)paramObject;
      } 
      for (Object object : jSONArray)
        stringBuilder.append(toString(object, (paramString == null) ? "array" : paramString)); 
      return stringBuilder.toString();
    } 
    String str = (paramObject == null) ? "null" : escape(paramObject.toString());
    return (paramString == null) ? ("\"" + str + "\"") : (
      (str.length() == 0) ? ("<" + paramString + "/>") : ("<" + paramString + ">" + str + "</" + paramString + ">"));
  }
}
