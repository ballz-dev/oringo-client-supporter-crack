package org.spongepowered.asm.mixin.transformer;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.launch.MixinInitialisationError;
import org.spongepowered.asm.lib.tree.ClassNode;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.extensibility.IMixinConfig;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.injection.InjectionPoint;
import org.spongepowered.asm.mixin.refmap.IReferenceMapper;
import org.spongepowered.asm.mixin.refmap.ReferenceMapper;
import org.spongepowered.asm.mixin.refmap.RemappingReferenceMapper;
import org.spongepowered.asm.mixin.transformer.throwables.InvalidMixinException;
import org.spongepowered.asm.service.IMixinService;
import org.spongepowered.asm.service.MixinService;
import org.spongepowered.asm.util.VersionNumber;

final class MixinConfig implements Comparable<MixinConfig>, IMixinConfig {
  static class InjectorOptions {
    @SerializedName("defaultRequire")
    int defaultRequireValue = 0;
    
    @SerializedName("defaultGroup")
    String defaultGroup = "default";
    
    @SerializedName("maxShiftBy")
    int maxShiftBy = 0;
    
    @SerializedName("injectionPoints")
    List<String> injectionPoints;
  }
  
  static class OverwriteOptions {
    @SerializedName("conformVisibility")
    boolean conformAccessModifiers;
    
    @SerializedName("requireAnnotations")
    boolean requireOverwriteAnnotations;
  }
  
  private static int configOrder = 0;
  
  private static final Set<String> globalMixinList = new HashSet<String>();
  
  private final Logger logger = LogManager.getLogger("mixin");
  
  private final transient Map<String, List<MixinInfo>> mixinMapping = new HashMap<String, List<MixinInfo>>();
  
  private final transient Set<String> unhandledTargets = new HashSet<String>();
  
  private final transient List<MixinInfo> mixins = new ArrayList<MixinInfo>();
  
  @SerializedName("priority")
  private int priority = 1000;
  
  @SerializedName("mixinPriority")
  private int mixinPriority = 1000;
  
  @SerializedName("setSourceFile")
  private boolean setSourceFile = false;
  
  private final transient int order = configOrder++;
  
  private final transient List<IListener> listeners = new ArrayList<IListener>();
  
  @SerializedName("injectors")
  private InjectorOptions injectorOptions = new InjectorOptions();
  
  @SerializedName("overwrites")
  private OverwriteOptions overwriteOptions = new OverwriteOptions();
  
  private transient boolean prepared = false;
  
  private transient boolean visited = false;
  
  @SerializedName("compatibilityLevel")
  private String compatibility;
  
  @SerializedName("verbose")
  private boolean verboseLogging;
  
  private transient String name;
  
  private transient MixinEnvironment env;
  
  private transient IReferenceMapper refMapper;
  
  @SerializedName("target")
  private String selector;
  
  @SerializedName("minVersion")
  private String version;
  
  @SerializedName("client")
  private List<String> mixinClassesClient;
  
  private transient IMixinConfigPlugin plugin;
  
  @SerializedName("plugin")
  private String pluginClassName;
  
  private transient IMixinService service;
  
  private transient Config handle;
  
  @SerializedName("server")
  private List<String> mixinClassesServer;
  
  @SerializedName("refmap")
  private String refMapperConfig;
  
  @SerializedName("mixins")
  private List<String> mixinClasses;
  
  @SerializedName("package")
  private String mixinPackage;
  
  @SerializedName("required")
  private boolean required;
  
  private boolean onLoad(IMixinService paramIMixinService, String paramString, MixinEnvironment paramMixinEnvironment) {
    this.service = paramIMixinService;
    this.name = paramString;
    this.env = parseSelector(this.selector, paramMixinEnvironment);
    this.required &= !this.env.getOption(MixinEnvironment.Option.IGNORE_REQUIRED) ? 1 : 0;
    initCompatibilityLevel();
    initInjectionPoints();
    return checkVersion();
  }
  
  private void initCompatibilityLevel() {
    if (this.compatibility == null)
      return; 
    MixinEnvironment.CompatibilityLevel compatibilityLevel1 = MixinEnvironment.CompatibilityLevel.valueOf(this.compatibility.trim().toUpperCase());
    MixinEnvironment.CompatibilityLevel compatibilityLevel2 = MixinEnvironment.getCompatibilityLevel();
    if (compatibilityLevel1 == compatibilityLevel2)
      return; 
    if (compatibilityLevel2.isAtLeast(compatibilityLevel1) && 
      !compatibilityLevel2.canSupport(compatibilityLevel1))
      throw new MixinInitialisationError("Mixin config " + this.name + " requires compatibility level " + compatibilityLevel1 + " which is too old"); 
    if (!compatibilityLevel2.canElevateTo(compatibilityLevel1))
      throw new MixinInitialisationError("Mixin config " + this.name + " requires compatibility level " + compatibilityLevel1 + " which is prohibited by " + compatibilityLevel2); 
    MixinEnvironment.setCompatibilityLevel(compatibilityLevel1);
  }
  
  private MixinEnvironment parseSelector(String paramString, MixinEnvironment paramMixinEnvironment) {
    if (paramString != null) {
      String[] arrayOfString = paramString.split("[&\\| ]");
      for (String str : arrayOfString) {
        str = str.trim();
        Pattern pattern = Pattern.compile("^@env(?:ironment)?\\(([A-Z]+)\\)$");
        Matcher matcher = pattern.matcher(str);
        if (matcher.matches())
          return MixinEnvironment.getEnvironment(MixinEnvironment.Phase.forName(matcher.group(1))); 
      } 
      MixinEnvironment.Phase phase = MixinEnvironment.Phase.forName(paramString);
      if (phase != null)
        return MixinEnvironment.getEnvironment(phase); 
    } 
    return paramMixinEnvironment;
  }
  
  private void initInjectionPoints() {
    if (this.injectorOptions.injectionPoints == null)
      return; 
    for (String str : this.injectorOptions.injectionPoints) {
      try {
        Class<?> clazz = this.service.getClassProvider().findClass(str, true);
        if (InjectionPoint.class.isAssignableFrom(clazz)) {
          InjectionPoint.register(clazz);
          continue;
        } 
        this.logger.error("Unable to register injection point {} for {}, class must extend InjectionPoint", new Object[] { clazz, this });
      } catch (Throwable throwable) {
        this.logger.catching(throwable);
      } 
    } 
  }
  
  private boolean checkVersion() throws MixinInitialisationError {
    if (this.version == null)
      this.logger.error("Mixin config {} does not specify \"minVersion\" property", new Object[] { this.name }); 
    VersionNumber versionNumber1 = VersionNumber.parse(this.version);
    VersionNumber versionNumber2 = VersionNumber.parse(this.env.getVersion());
    if (versionNumber1.compareTo(versionNumber2) > 0) {
      this.logger.warn("Mixin config {} requires mixin subsystem version {} but {} was found. The mixin config will not be applied.", new Object[] { this.name, versionNumber1, versionNumber2 });
      if (this.required)
        throw new MixinInitialisationError("Required mixin config " + this.name + " requires mixin subsystem version " + versionNumber1); 
      return false;
    } 
    return true;
  }
  
  void addListener(IListener paramIListener) {
    this.listeners.add(paramIListener);
  }
  
  void onSelect() {
    if (this.pluginClassName != null)
      try {
        Class<IMixinConfigPlugin> clazz = this.service.getClassProvider().findClass(this.pluginClassName, true);
        this.plugin = clazz.newInstance();
        if (this.plugin != null)
          this.plugin.onLoad(this.mixinPackage); 
      } catch (Throwable throwable) {
        throwable.printStackTrace();
        this.plugin = null;
      }  
    if (!this.mixinPackage.endsWith("."))
      this.mixinPackage += "."; 
    boolean bool = false;
    if (this.refMapperConfig == null) {
      if (this.plugin != null)
        this.refMapperConfig = this.plugin.getRefMapperConfig(); 
      if (this.refMapperConfig == null) {
        bool = true;
        this.refMapperConfig = "mixin.refmap.json";
      } 
    } 
    this.refMapper = (IReferenceMapper)ReferenceMapper.read(this.refMapperConfig);
    this.verboseLogging |= this.env.getOption(MixinEnvironment.Option.DEBUG_VERBOSE);
    if (!bool && this.refMapper.isDefault() && !this.env.getOption(MixinEnvironment.Option.DISABLE_REFMAP))
      this.logger.warn("Reference map '{}' for {} could not be read. If this is a development environment you can ignore this message", new Object[] { this.refMapperConfig, this }); 
    if (this.env.getOption(MixinEnvironment.Option.REFMAP_REMAP))
      this.refMapper = RemappingReferenceMapper.of(this.env, this.refMapper); 
  }
  
  void prepare() {
    if (this.prepared)
      return; 
    this.prepared = true;
    prepareMixins(this.mixinClasses, false);
    switch (this.env.getSide()) {
      case CLIENT:
        prepareMixins(this.mixinClassesClient, false);
        return;
      case SERVER:
        prepareMixins(this.mixinClassesServer, false);
        return;
    } 
    this.logger.warn("Mixin environment was unable to detect the current side, sided mixins will not be applied");
  }
  
  static interface IListener {
    void onPrepare(MixinInfo param1MixinInfo);
    
    void onInit(MixinInfo param1MixinInfo);
  }
  
  void postInitialise() {
    if (this.plugin != null) {
      List<String> list = this.plugin.getMixins();
      prepareMixins(list, true);
    } 
    for (Iterator<MixinInfo> iterator = this.mixins.iterator(); iterator.hasNext(); ) {
      MixinInfo mixinInfo = iterator.next();
      try {
        mixinInfo.validate();
        for (IListener iListener : this.listeners)
          iListener.onInit(mixinInfo); 
      } catch (InvalidMixinException invalidMixinException) {
        this.logger.error(invalidMixinException.getMixin() + ": " + invalidMixinException.getMessage(), (Throwable)invalidMixinException);
        removeMixin(mixinInfo);
        iterator.remove();
      } catch (Exception exception) {
        this.logger.error(exception.getMessage(), exception);
        removeMixin(mixinInfo);
        iterator.remove();
      } 
    } 
  }
  
  private void removeMixin(MixinInfo paramMixinInfo) {
    for (List<MixinInfo> list : this.mixinMapping.values()) {
      for (Iterator<MixinInfo> iterator = list.iterator(); iterator.hasNext();) {
        if (paramMixinInfo == iterator.next())
          iterator.remove(); 
      } 
    } 
  }
  
  private void prepareMixins(List<String> paramList, boolean paramBoolean) {
    if (paramList == null)
      return; 
    for (String str1 : paramList) {
      String str2 = this.mixinPackage + str1;
      if (str1 == null || globalMixinList.contains(str2))
        continue; 
      MixinInfo mixinInfo = null;
      try {
        mixinInfo = new MixinInfo(this.service, this, str1, true, this.plugin, paramBoolean);
        if (mixinInfo.getTargetClasses().size() > 0) {
          globalMixinList.add(str2);
          for (String str3 : mixinInfo.getTargetClasses()) {
            String str4 = str3.replace('/', '.');
            mixinsFor(str4).add(mixinInfo);
            this.unhandledTargets.add(str4);
          } 
          for (IListener iListener : this.listeners)
            iListener.onPrepare(mixinInfo); 
          this.mixins.add(mixinInfo);
        } 
      } catch (InvalidMixinException invalidMixinException) {
        if (this.required)
          throw invalidMixinException; 
        this.logger.error(invalidMixinException.getMessage(), (Throwable)invalidMixinException);
      } catch (Exception exception) {
        if (this.required)
          throw new InvalidMixinException(mixinInfo, "Error initialising mixin " + mixinInfo + " - " + exception.getClass() + ": " + exception.getMessage(), exception); 
        this.logger.error(exception.getMessage(), exception);
      } 
    } 
  }
  
  void postApply(String paramString, ClassNode paramClassNode) {
    this.unhandledTargets.remove(paramString);
  }
  
  public Config getHandle() {
    if (this.handle == null)
      this.handle = new Config(this); 
    return this.handle;
  }
  
  public boolean isRequired() {
    return this.required;
  }
  
  public MixinEnvironment getEnvironment() {
    return this.env;
  }
  
  public String getName() {
    return this.name;
  }
  
  public String getMixinPackage() {
    return this.mixinPackage;
  }
  
  public int getPriority() {
    return this.priority;
  }
  
  public int getDefaultMixinPriority() {
    return this.mixinPriority;
  }
  
  public int getDefaultRequiredInjections() {
    return this.injectorOptions.defaultRequireValue;
  }
  
  public String getDefaultInjectorGroup() {
    String str = this.injectorOptions.defaultGroup;
    return (str != null && !str.isEmpty()) ? str : "default";
  }
  
  public boolean conformOverwriteVisibility() {
    return this.overwriteOptions.conformAccessModifiers;
  }
  
  public boolean requireOverwriteAnnotations() {
    return this.overwriteOptions.requireOverwriteAnnotations;
  }
  
  public int getMaxShiftByValue() {
    return Math.min(Math.max(this.injectorOptions.maxShiftBy, 0), 5);
  }
  
  public boolean select(MixinEnvironment paramMixinEnvironment) {
    this.visited = true;
    return (this.env == paramMixinEnvironment);
  }
  
  boolean isVisited() {
    return this.visited;
  }
  
  int getDeclaredMixinCount() {
    return getCollectionSize((Collection<?>[])new Collection[] { this.mixinClasses, this.mixinClassesClient, this.mixinClassesServer });
  }
  
  int getMixinCount() {
    return this.mixins.size();
  }
  
  public List<String> getClasses() {
    return Collections.unmodifiableList(this.mixinClasses);
  }
  
  public boolean shouldSetSourceFile() {
    return this.setSourceFile;
  }
  
  public IReferenceMapper getReferenceMapper() {
    if (this.env.getOption(MixinEnvironment.Option.DISABLE_REFMAP))
      return (IReferenceMapper)ReferenceMapper.DEFAULT_MAPPER; 
    this.refMapper.setContext(this.env.getRefmapObfuscationContext());
    return this.refMapper;
  }
  
  String remapClassName(String paramString1, String paramString2) {
    return getReferenceMapper().remap(paramString1, paramString2);
  }
  
  public IMixinConfigPlugin getPlugin() {
    return this.plugin;
  }
  
  public Set<String> getTargets() {
    return Collections.unmodifiableSet(this.mixinMapping.keySet());
  }
  
  public Set<String> getUnhandledTargets() {
    return Collections.unmodifiableSet(this.unhandledTargets);
  }
  
  public Level getLoggingLevel() {
    return this.verboseLogging ? Level.INFO : Level.DEBUG;
  }
  
  public boolean packageMatch(String paramString) {
    return paramString.startsWith(this.mixinPackage);
  }
  
  public boolean hasMixinsFor(String paramString) {
    return this.mixinMapping.containsKey(paramString);
  }
  
  public List<MixinInfo> getMixinsFor(String paramString) {
    return mixinsFor(paramString);
  }
  
  private List<MixinInfo> mixinsFor(String paramString) {
    List<MixinInfo> list = this.mixinMapping.get(paramString);
    if (list == null) {
      list = new ArrayList();
      this.mixinMapping.put(paramString, list);
    } 
    return list;
  }
  
  public List<String> reloadMixin(String paramString, byte[] paramArrayOfbyte) {
    for (MixinInfo mixinInfo : this.mixins) {
      if (mixinInfo.getClassName().equals(paramString)) {
        mixinInfo.reloadMixin(paramArrayOfbyte);
        return mixinInfo.getTargetClasses();
      } 
    } 
    return Collections.emptyList();
  }
  
  public String toString() {
    return this.name;
  }
  
  public int compareTo(MixinConfig paramMixinConfig) {
    if (paramMixinConfig == null)
      return 0; 
    if (paramMixinConfig.priority == this.priority)
      return this.order - paramMixinConfig.order; 
    return this.priority - paramMixinConfig.priority;
  }
  
  static Config create(String paramString, MixinEnvironment paramMixinEnvironment) {
    try {
      IMixinService iMixinService = MixinService.getService();
      MixinConfig mixinConfig = (MixinConfig)(new Gson()).fromJson(new InputStreamReader(iMixinService.getResourceAsStream(paramString)), MixinConfig.class);
      if (mixinConfig.onLoad(iMixinService, paramString, paramMixinEnvironment))
        return mixinConfig.getHandle(); 
      return null;
    } catch (Exception exception) {
      exception.printStackTrace();
      throw new IllegalArgumentException(String.format("The specified resource '%s' was invalid or could not be read", new Object[] { paramString }), exception);
    } 
  }
  
  private static int getCollectionSize(Collection<?>... paramVarArgs) {
    int i = 0;
    for (Collection<?> collection : paramVarArgs) {
      if (collection != null)
        i += collection.size(); 
    } 
    return i;
  }
}
