package org.spongepowered.asm.mixin.refmap;

import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.service.IMixinService;
import org.spongepowered.asm.service.MixinService;

public final class ReferenceMapper implements IReferenceMapper, Serializable {
  public static final ReferenceMapper DEFAULT_MAPPER = new ReferenceMapper(true, "invalid");
  
  private final Map<String, Map<String, String>> mappings = Maps.newHashMap();
  
  private final Map<String, Map<String, Map<String, String>>> data = Maps.newHashMap();
  
  private transient String context = null;
  
  public static final String DEFAULT_RESOURCE = "mixin.refmap.json";
  
  private final transient boolean readOnly;
  
  private transient String resource;
  
  private static final long serialVersionUID = 2L;
  
  public ReferenceMapper() {
    this(false, "mixin.refmap.json");
  }
  
  private ReferenceMapper(boolean paramBoolean, String paramString) {
    this.readOnly = paramBoolean;
    this.resource = paramString;
  }
  
  public boolean isDefault() {
    return this.readOnly;
  }
  
  private void setResourceName(String paramString) {
    if (!this.readOnly)
      this.resource = (paramString != null) ? paramString : "<unknown resource>"; 
  }
  
  public String getResourceName() {
    return this.resource;
  }
  
  public String getStatus() {
    return isDefault() ? "No refMap loaded." : ("Using refmap " + getResourceName());
  }
  
  public String getContext() {
    return this.context;
  }
  
  public void setContext(String paramString) {
    this.context = paramString;
  }
  
  public String remap(String paramString1, String paramString2) {
    return remapWithContext(this.context, paramString1, paramString2);
  }
  
  public String remapWithContext(String paramString1, String paramString2, String paramString3) {
    Map<String, Map<String, String>> map = this.mappings;
    if (paramString1 != null) {
      map = this.data.get(paramString1);
      if (map == null)
        map = this.mappings; 
    } 
    return remap(map, paramString2, paramString3);
  }
  
  private String remap(Map<String, Map<String, String>> paramMap, String paramString1, String paramString2) {
    if (paramString1 == null)
      for (Map<String, String> map1 : paramMap.values()) {
        if (map1.containsKey(paramString2))
          return (String)map1.get(paramString2); 
      }  
    Map map = paramMap.get(paramString1);
    if (map == null)
      return paramString2; 
    String str = (String)map.get(paramString2);
    return (str != null) ? str : paramString2;
  }
  
  public String addMapping(String paramString1, String paramString2, String paramString3, String paramString4) {
    if (this.readOnly || paramString3 == null || paramString4 == null || paramString3.equals(paramString4))
      return null; 
    Map<String, Map<String, String>> map = this.mappings;
    if (paramString1 != null) {
      map = this.data.get(paramString1);
      if (map == null) {
        map = Maps.newHashMap();
        this.data.put(paramString1, map);
      } 
    } 
    Map<Object, Object> map1 = (Map)map.get(paramString2);
    if (map1 == null) {
      map1 = new HashMap<Object, Object>();
      map.put(paramString2, map1);
    } 
    return (String)map1.put(paramString3, paramString4);
  }
  
  public void write(Appendable paramAppendable) {
    (new GsonBuilder()).setPrettyPrinting().create().toJson(this, paramAppendable);
  }
  
  public static ReferenceMapper read(String paramString) {
    Logger logger = LogManager.getLogger("mixin");
    InputStreamReader inputStreamReader = null;
    try {
      IMixinService iMixinService = MixinService.getService();
      InputStream inputStream = iMixinService.getResourceAsStream(paramString);
      if (inputStream != null) {
        inputStreamReader = new InputStreamReader(inputStream);
        ReferenceMapper referenceMapper = readJson(inputStreamReader);
        referenceMapper.setResourceName(paramString);
        return referenceMapper;
      } 
    } catch (JsonParseException jsonParseException) {
      logger.error("Invalid REFMAP JSON in " + paramString + ": " + jsonParseException.getClass().getName() + " " + jsonParseException.getMessage());
    } catch (Exception exception) {
      logger.error("Failed reading REFMAP JSON from " + paramString + ": " + exception.getClass().getName() + " " + exception.getMessage());
    } finally {
      IOUtils.closeQuietly(inputStreamReader);
    } 
    return DEFAULT_MAPPER;
  }
  
  public static ReferenceMapper read(Reader paramReader, String paramString) {
    try {
      ReferenceMapper referenceMapper = readJson(paramReader);
      referenceMapper.setResourceName(paramString);
      return referenceMapper;
    } catch (Exception exception) {
      return DEFAULT_MAPPER;
    } 
  }
  
  private static ReferenceMapper readJson(Reader paramReader) {
    return (ReferenceMapper)(new Gson()).fromJson(paramReader, ReferenceMapper.class);
  }
}
