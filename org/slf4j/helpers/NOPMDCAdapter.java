package org.slf4j.helpers;

import java.util.Map;
import org.slf4j.spi.MDCAdapter;

public class NOPMDCAdapter implements MDCAdapter {
  public void clear() {}
  
  public String get(String paramString) {
    return null;
  }
  
  public void put(String paramString1, String paramString2) {}
  
  public void remove(String paramString) {}
  
  public Map<String, String> getCopyOfContextMap() {
    return null;
  }
  
  public void setContextMap(Map<String, String> paramMap) {}
}
