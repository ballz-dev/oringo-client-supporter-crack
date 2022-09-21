package org.json;

import java.util.Enumeration;
import java.util.Map;
import java.util.Properties;

public class Property {
  public static JSONObject toJSONObject(Properties paramProperties) throws JSONException {
    JSONObject jSONObject = new JSONObject((paramProperties == null) ? 0 : paramProperties.size());
    if (paramProperties != null && !paramProperties.isEmpty()) {
      Enumeration<?> enumeration = paramProperties.propertyNames();
      while (enumeration.hasMoreElements()) {
        String str = (String)enumeration.nextElement();
        jSONObject.put(str, paramProperties.getProperty(str));
      } 
    } 
    return jSONObject;
  }
  
  public static Properties toProperties(JSONObject paramJSONObject) throws JSONException {
    Properties properties = new Properties();
    if (paramJSONObject != null)
      for (Map.Entry<String, Object> entry : paramJSONObject.entrySet()) {
        Object object = entry.getValue();
        if (!JSONObject.NULL.equals(object))
          properties.put(entry.getKey(), object.toString()); 
      }  
    return properties;
  }
}
