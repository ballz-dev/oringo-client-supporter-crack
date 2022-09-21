package org.slf4j;

import java.io.Closeable;
import java.util.Map;
import org.slf4j.helpers.NOPMDCAdapter;
import org.slf4j.helpers.Util;
import org.slf4j.impl.StaticMDCBinder;
import org.slf4j.spi.MDCAdapter;

public class MDC {
  static final String NULL_MDCA_URL = "http://www.slf4j.org/codes.html#null_MDCA";
  
  static MDCAdapter mdcAdapter;
  
  static final String NO_STATIC_MDC_BINDER_URL = "http://www.slf4j.org/codes.html#no_static_mdc_binder";
  
  public static class MDCCloseable implements Closeable {
    private final String key;
    
    private MDCCloseable(String param1String) {
      this.key = param1String;
    }
    
    public void close() {
      MDC.remove(this.key);
    }
  }
  
  private static MDCAdapter bwCompatibleGetMDCAdapterFromBinder() throws NoClassDefFoundError {
    try {
      return StaticMDCBinder.getSingleton().getMDCA();
    } catch (NoSuchMethodError noSuchMethodError) {
      return StaticMDCBinder.SINGLETON.getMDCA();
    } 
  }
  
  static {
    try {
      mdcAdapter = bwCompatibleGetMDCAdapterFromBinder();
    } catch (NoClassDefFoundError noClassDefFoundError) {
      mdcAdapter = (MDCAdapter)new NOPMDCAdapter();
      String str = noClassDefFoundError.getMessage();
      if (str != null && str.contains("StaticMDCBinder")) {
        Util.report("Failed to load class \"org.slf4j.impl.StaticMDCBinder\".");
        Util.report("Defaulting to no-operation MDCAdapter implementation.");
        Util.report("See http://www.slf4j.org/codes.html#no_static_mdc_binder for further details.");
      } else {
        throw noClassDefFoundError;
      } 
    } catch (Exception exception) {
      Util.report("MDC binding unsuccessful.", exception);
    } 
  }
  
  public static void put(String paramString1, String paramString2) throws IllegalArgumentException {
    if (paramString1 == null)
      throw new IllegalArgumentException("key parameter cannot be null"); 
    if (mdcAdapter == null)
      throw new IllegalStateException("MDCAdapter cannot be null. See also http://www.slf4j.org/codes.html#null_MDCA"); 
    mdcAdapter.put(paramString1, paramString2);
  }
  
  public static MDCCloseable putCloseable(String paramString1, String paramString2) throws IllegalArgumentException {
    put(paramString1, paramString2);
    return new MDCCloseable(paramString1);
  }
  
  public static String get(String paramString) throws IllegalArgumentException {
    if (paramString == null)
      throw new IllegalArgumentException("key parameter cannot be null"); 
    if (mdcAdapter == null)
      throw new IllegalStateException("MDCAdapter cannot be null. See also http://www.slf4j.org/codes.html#null_MDCA"); 
    return mdcAdapter.get(paramString);
  }
  
  public static void remove(String paramString) throws IllegalArgumentException {
    if (paramString == null)
      throw new IllegalArgumentException("key parameter cannot be null"); 
    if (mdcAdapter == null)
      throw new IllegalStateException("MDCAdapter cannot be null. See also http://www.slf4j.org/codes.html#null_MDCA"); 
    mdcAdapter.remove(paramString);
  }
  
  public static void clear() {
    if (mdcAdapter == null)
      throw new IllegalStateException("MDCAdapter cannot be null. See also http://www.slf4j.org/codes.html#null_MDCA"); 
    mdcAdapter.clear();
  }
  
  public static Map<String, String> getCopyOfContextMap() {
    if (mdcAdapter == null)
      throw new IllegalStateException("MDCAdapter cannot be null. See also http://www.slf4j.org/codes.html#null_MDCA"); 
    return mdcAdapter.getCopyOfContextMap();
  }
  
  public static void setContextMap(Map<String, String> paramMap) {
    if (mdcAdapter == null)
      throw new IllegalStateException("MDCAdapter cannot be null. See also http://www.slf4j.org/codes.html#null_MDCA"); 
    mdcAdapter.setContextMap(paramMap);
  }
  
  public static MDCAdapter getMDCAdapter() {
    return mdcAdapter;
  }
}
