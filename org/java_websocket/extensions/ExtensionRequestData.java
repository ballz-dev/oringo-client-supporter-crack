package org.java_websocket.extensions;

import java.util.LinkedHashMap;
import java.util.Map;

public class ExtensionRequestData {
  private String extensionName;
  
  private Map<String, String> extensionParameters = new LinkedHashMap<>();
  
  public static final String EMPTY_VALUE = "";
  
  public static ExtensionRequestData parseExtensionRequest(String paramString) {
    ExtensionRequestData extensionRequestData = new ExtensionRequestData();
    String[] arrayOfString = paramString.split(";");
    extensionRequestData.extensionName = arrayOfString[0].trim();
    for (byte b = 1; b < arrayOfString.length; b++) {
      String[] arrayOfString1 = arrayOfString[b].split("=");
      String str = "";
      if (arrayOfString1.length > 1) {
        String str1 = arrayOfString1[1].trim();
        if ((str1.startsWith("\"") && str1.endsWith("\"")) || (str1
          .startsWith("'") && str1.endsWith("'") && str1
          .length() > 2))
          str1 = str1.substring(1, str1.length() - 1); 
        str = str1;
      } 
      extensionRequestData.extensionParameters.put(arrayOfString1[0].trim(), str);
    } 
    return extensionRequestData;
  }
  
  public String getExtensionName() {
    return this.extensionName;
  }
  
  public Map<String, String> getExtensionParameters() {
    return this.extensionParameters;
  }
}
