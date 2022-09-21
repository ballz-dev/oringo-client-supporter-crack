package org.json;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class JSONPointer {
  private static final String ENCODING = "utf-8";
  
  private final List<String> refTokens;
  
  public static class Builder {
    private final List<String> refTokens = new ArrayList<String>();
    
    public JSONPointer build() {
      return new JSONPointer(this.refTokens);
    }
    
    public Builder append(String param1String) {
      if (param1String == null)
        throw new NullPointerException("token cannot be null"); 
      this.refTokens.add(param1String);
      return this;
    }
    
    public Builder append(int param1Int) {
      this.refTokens.add(String.valueOf(param1Int));
      return this;
    }
  }
  
  public static Builder builder() {
    return new Builder();
  }
  
  public JSONPointer(String paramString) {
    String str;
    if (paramString == null)
      throw new NullPointerException("pointer cannot be null"); 
    if (paramString.isEmpty() || paramString.equals("#")) {
      this.refTokens = Collections.emptyList();
      return;
    } 
    if (paramString.startsWith("#/")) {
      str = paramString.substring(2);
      try {
        str = URLDecoder.decode(str, "utf-8");
      } catch (UnsupportedEncodingException unsupportedEncodingException) {
        throw new RuntimeException(unsupportedEncodingException);
      } 
    } else if (paramString.startsWith("/")) {
      str = paramString.substring(1);
    } else {
      throw new IllegalArgumentException("a JSON pointer should start with '/' or '#/'");
    } 
    this.refTokens = new ArrayList<String>();
    for (String str1 : str.split("/"))
      this.refTokens.add(unescape(str1)); 
  }
  
  public JSONPointer(List<String> paramList) {
    this.refTokens = new ArrayList<String>(paramList);
  }
  
  private String unescape(String paramString) {
    return paramString.replace("~1", "/").replace("~0", "~")
      .replace("\\\"", "\"")
      .replace("\\\\", "\\");
  }
  
  public Object queryFrom(Object paramObject) {
    if (this.refTokens.isEmpty())
      return paramObject; 
    Object object = paramObject;
    for (String str : this.refTokens) {
      if (object instanceof JSONObject) {
        object = ((JSONObject)object).opt(unescape(str));
        continue;
      } 
      if (object instanceof JSONArray) {
        object = readByIndexToken(object, str);
        continue;
      } 
      throw new JSONPointerException(String.format("value [%s] is not an array or object therefore its key %s cannot be resolved", new Object[] { object, str }));
    } 
    return object;
  }
  
  private Object readByIndexToken(Object paramObject, String paramString) {
    try {
      int i = Integer.parseInt(paramString);
      JSONArray jSONArray = (JSONArray)paramObject;
      if (i >= jSONArray.length())
        throw new JSONPointerException(String.format("index %d is out of bounds - the array has %d elements", new Object[] { Integer.valueOf(i), 
                Integer.valueOf(jSONArray.length()) })); 
      return jSONArray.get(i);
    } catch (NumberFormatException numberFormatException) {
      throw new JSONPointerException(String.format("%s is not an array index", new Object[] { paramString }), numberFormatException);
    } 
  }
  
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder("");
    for (String str : this.refTokens)
      stringBuilder.append('/').append(escape(str)); 
    return stringBuilder.toString();
  }
  
  private String escape(String paramString) {
    return paramString.replace("~", "~0")
      .replace("/", "~1")
      .replace("\\", "\\\\")
      .replace("\"", "\\\"");
  }
  
  public String toURIFragment() {
    try {
      StringBuilder stringBuilder = new StringBuilder("#");
      for (String str : this.refTokens)
        stringBuilder.append('/').append(URLEncoder.encode(str, "utf-8")); 
      return stringBuilder.toString();
    } catch (UnsupportedEncodingException unsupportedEncodingException) {
      throw new RuntimeException(unsupportedEncodingException);
    } 
  }
}
