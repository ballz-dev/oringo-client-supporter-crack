package org.slf4j.spi;

import java.util.Map;

public interface MDCAdapter {
  void setContextMap(Map<String, String> paramMap);
  
  void clear();
  
  String get(String paramString);
  
  void remove(String paramString);
  
  Map<String, String> getCopyOfContextMap();
  
  void put(String paramString1, String paramString2);
}
