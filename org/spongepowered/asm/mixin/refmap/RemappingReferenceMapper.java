package org.spongepowered.asm.mixin.refmap;

import com.google.common.base.Charsets;
import com.google.common.base.Strings;
import com.google.common.io.Files;
import com.google.common.io.LineProcessor;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.MixinEnvironment;

public final class RemappingReferenceMapper implements IReferenceMapper {
  private static final Map<String, Map<String, String>> srgs;
  
  private static final String DEFAULT_RESOURCE_PATH_PROPERTY = "net.minecraftforge.gradle.GradleStart.srg.srg-mcp";
  
  private final Map<String, String> mappings;
  
  private static final Logger logger = LogManager.getLogger("mixin");
  
  static {
    srgs = new HashMap<String, Map<String, String>>();
  }
  
  private final Map<String, Map<String, String>> cache = new HashMap<String, Map<String, String>>();
  
  private final IReferenceMapper refMap;
  
  private static final String DEFAULT_MAPPING_ENV = "searge";
  
  private RemappingReferenceMapper(MixinEnvironment paramMixinEnvironment, IReferenceMapper paramIReferenceMapper) {
    this.refMap = paramIReferenceMapper;
    this.refMap.setContext(getMappingEnv(paramMixinEnvironment));
    String str = getResource(paramMixinEnvironment);
    this.mappings = loadSrgs(str);
    logger.info("Remapping refMap {} using {}", new Object[] { paramIReferenceMapper.getResourceName(), str });
  }
  
  public boolean isDefault() {
    return this.refMap.isDefault();
  }
  
  public String getResourceName() {
    return this.refMap.getResourceName();
  }
  
  public String getStatus() {
    return this.refMap.getStatus();
  }
  
  public String getContext() {
    return this.refMap.getContext();
  }
  
  public void setContext(String paramString) {}
  
  public String remap(String paramString1, String paramString2) {
    Map<String, String> map = getCache(paramString1);
    String str = map.get(paramString2);
    if (str == null) {
      str = this.refMap.remap(paramString1, paramString2);
      for (Map.Entry<String, String> entry : this.mappings.entrySet())
        str = str.replace((CharSequence)entry.getKey(), (CharSequence)entry.getValue()); 
      map.put(paramString2, str);
    } 
    return str;
  }
  
  private Map<String, String> getCache(String paramString) {
    Map<Object, Object> map = (Map)this.cache.get(paramString);
    if (map == null) {
      map = new HashMap<Object, Object>();
      this.cache.put(paramString, map);
    } 
    return (Map)map;
  }
  
  public String remapWithContext(String paramString1, String paramString2, String paramString3) {
    return this.refMap.remapWithContext(paramString1, paramString2, paramString3);
  }
  
  private static Map<String, String> loadSrgs(String paramString) {
    if (srgs.containsKey(paramString))
      return srgs.get(paramString); 
    final HashMap<Object, Object> map = new HashMap<Object, Object>();
    srgs.put(paramString, hashMap);
    File file = new File(paramString);
    if (!file.isFile())
      return (Map)hashMap; 
    try {
      Files.readLines(file, Charsets.UTF_8, new LineProcessor<Object>() {
            public Object getResult() {
              return null;
            }
            
            public boolean processLine(String param1String) throws IOException {
              if (Strings.isNullOrEmpty(param1String) || param1String.startsWith("#"))
                return true; 
              boolean bool1 = false, bool2 = false;
              if (bool2 = param1String.startsWith("MD: ") ? true : (param1String.startsWith("FD: ") ? true : false)) {
                String[] arrayOfString = param1String.substring(4).split(" ", 4);
                map.put(arrayOfString[bool1]
                    .substring(arrayOfString[bool1].lastIndexOf('/') + 1), arrayOfString[bool2]
                    .substring(arrayOfString[bool2].lastIndexOf('/') + 1));
              } 
              return true;
            }
          });
    } catch (IOException iOException) {
      logger.warn("Could not read input SRG file: {}", new Object[] { paramString });
      logger.catching(iOException);
    } 
    return (Map)hashMap;
  }
  
  public static IReferenceMapper of(MixinEnvironment paramMixinEnvironment, IReferenceMapper paramIReferenceMapper) {
    if (!paramIReferenceMapper.isDefault() && hasData(paramMixinEnvironment))
      return new RemappingReferenceMapper(paramMixinEnvironment, paramIReferenceMapper); 
    return paramIReferenceMapper;
  }
  
  private static boolean hasData(MixinEnvironment paramMixinEnvironment) {
    String str = getResource(paramMixinEnvironment);
    return (str != null && (new File(str)).exists());
  }
  
  private static String getResource(MixinEnvironment paramMixinEnvironment) {
    String str = paramMixinEnvironment.getOptionValue(MixinEnvironment.Option.REFMAP_REMAP_RESOURCE);
    return Strings.isNullOrEmpty(str) ? System.getProperty("net.minecraftforge.gradle.GradleStart.srg.srg-mcp") : str;
  }
  
  private static String getMappingEnv(MixinEnvironment paramMixinEnvironment) {
    String str = paramMixinEnvironment.getOptionValue(MixinEnvironment.Option.REFMAP_REMAP_SOURCE_ENV);
    return Strings.isNullOrEmpty(str) ? "searge" : str;
  }
}
