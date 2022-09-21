package org.slf4j.helpers;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.slf4j.spi.MDCAdapter;

public class BasicMDCAdapter implements MDCAdapter {
  private InheritableThreadLocal<Map<String, String>> inheritableThreadLocal = new InheritableThreadLocal<Map<String, String>>() {
      protected Map<String, String> childValue(Map<String, String> param1Map) {
        if (param1Map == null)
          return null; 
        return new HashMap<String, String>(param1Map);
      }
    };
  
  public void put(String paramString1, String paramString2) {
    if (paramString1 == null)
      throw new IllegalArgumentException("key cannot be null"); 
    Map<Object, Object> map = (Map)this.inheritableThreadLocal.get();
    if (map == null) {
      map = new HashMap<Object, Object>();
      this.inheritableThreadLocal.set(map);
    } 
    map.put(paramString1, paramString2);
  }
  
  public String get(String paramString) {
    Map map = this.inheritableThreadLocal.get();
    if (map != null && paramString != null)
      return (String)map.get(paramString); 
    return null;
  }
  
  public void remove(String paramString) {
    Map map = this.inheritableThreadLocal.get();
    if (map != null)
      map.remove(paramString); 
  }
  
  public void clear() {
    Map map = this.inheritableThreadLocal.get();
    if (map != null) {
      map.clear();
      this.inheritableThreadLocal.remove();
    } 
  }
  
  public Set<String> getKeys() {
    Map map = this.inheritableThreadLocal.get();
    if (map != null)
      return map.keySet(); 
    return null;
  }
  
  public Map<String, String> getCopyOfContextMap() {
    Map<? extends String, ? extends String> map = this.inheritableThreadLocal.get();
    if (map != null)
      return new HashMap<String, String>(map); 
    return null;
  }
  
  public void setContextMap(Map<String, String> paramMap) {
    this.inheritableThreadLocal.set(new HashMap<String, String>(paramMap));
  }
}
