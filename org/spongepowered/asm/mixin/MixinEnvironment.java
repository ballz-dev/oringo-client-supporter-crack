package org.spongepowered.asm.mixin;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Sets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.Appender;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.Logger;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.spongepowered.asm.launch.GlobalProperties;
import org.spongepowered.asm.mixin.extensibility.IEnvironmentTokenProvider;
import org.spongepowered.asm.mixin.throwables.MixinException;
import org.spongepowered.asm.mixin.transformer.MixinTransformer;
import org.spongepowered.asm.obfuscation.RemapperChain;
import org.spongepowered.asm.service.ILegacyClassTransformer;
import org.spongepowered.asm.service.IMixinService;
import org.spongepowered.asm.service.ITransformer;
import org.spongepowered.asm.service.MixinService;
import org.spongepowered.asm.util.ITokenProvider;
import org.spongepowered.asm.util.JavaVersion;
import org.spongepowered.asm.util.PrettyPrinter;
import org.spongepowered.asm.util.perf.Profiler;

public final class MixinEnvironment implements ITokenProvider {
  public static final class Phase {
    final int ordinal;
    
    private MixinEnvironment environment;
    
    static final Phase NOT_INITIALISED = new Phase(-1, "NOT_INITIALISED");
    
    static final List<Phase> phases;
    
    public static final Phase DEFAULT = new Phase(2, "DEFAULT");
    
    final String name;
    
    public static final Phase PREINIT = new Phase(0, "PREINIT");
    
    public static final Phase INIT = new Phase(1, "INIT");
    
    static {
      phases = (List<Phase>)ImmutableList.of(PREINIT, INIT, DEFAULT);
    }
    
    private Phase(int param1Int, String param1String) {
      this.ordinal = param1Int;
      this.name = param1String;
    }
    
    public String toString() {
      return this.name;
    }
    
    public static Phase forName(String param1String) {
      for (Phase phase : phases) {
        if (phase.name.equals(param1String))
          return phase; 
      } 
      return null;
    }
    
    MixinEnvironment getEnvironment() {
      if (this.ordinal < 0)
        throw new IllegalArgumentException("Cannot access the NOT_INITIALISED environment"); 
      if (this.environment == null)
        this.environment = new MixinEnvironment(this); 
      return this.environment;
    }
  }
  
  public enum Side {
    UNKNOWN {
      protected boolean detect() {
        return false;
      }
    },
    SERVER,
    CLIENT {
      protected boolean detect() {
        String str = MixinService.getService().getSideName();
        return "CLIENT".equals(str);
      }
    };
    
    protected abstract boolean detect();
    
    static {
    
    }
  }
  
  public enum Option {
    DEFAULT_COMPATIBILITY_LEVEL,
    DEBUG_EXPORT_DECOMPILE_MERGESIGNATURES,
    ENVIRONMENT,
    DEBUG_EXPORT_FILTER,
    REFMAP_REMAP_ALLOW_PERMISSIVE,
    DEBUG_VERBOSE,
    REFMAP_REMAP_SOURCE_ENV,
    DEBUG_UNIQUE,
    CHECK_IMPLEMENTS,
    REFMAP_REMAP,
    IGNORE_REQUIRED,
    DEBUG_INJECTORS,
    INITIALISER_INJECTION_MODE,
    CHECK_IMPLEMENTS_STRICT,
    IGNORE_CONSTRAINTS,
    DEBUG_EXPORT_DECOMPILE,
    DEBUG_STRICT,
    REFMAP_REMAP_RESOURCE,
    DEBUG_EXPORT_DECOMPILE_THREADED,
    OBFUSCATION_TYPE,
    CHECK_ALL,
    DUMP_TARGET_ON_FAILURE,
    HOT_SWAP,
    DEBUG_EXPORT,
    DEBUG_ALL("debug"),
    DEBUG_TARGETS("debug"),
    DEBUG_VERIFY("debug"),
    SHIFT_BY_VIOLATION_BEHAVIOUR("debug"),
    DEBUG_PROFILER("debug"),
    DISABLE_REFMAP("debug");
    
    final Inherit inheritance;
    
    final Option parent;
    
    final boolean isFlag;
    
    final String property;
    
    private static final String PREFIX = "mixin";
    
    final int depth;
    
    final String defaultValue;
    
    static {
      DEBUG_EXPORT_FILTER = new Option("DEBUG_EXPORT_FILTER", 2, DEBUG_EXPORT, "filter", false);
      DEBUG_EXPORT_DECOMPILE = new Option("DEBUG_EXPORT_DECOMPILE", 3, DEBUG_EXPORT, Inherit.ALLOW_OVERRIDE, "decompile");
      DEBUG_EXPORT_DECOMPILE_THREADED = new Option("DEBUG_EXPORT_DECOMPILE_THREADED", 4, DEBUG_EXPORT_DECOMPILE, Inherit.ALLOW_OVERRIDE, "async");
      DEBUG_EXPORT_DECOMPILE_MERGESIGNATURES = new Option("DEBUG_EXPORT_DECOMPILE_MERGESIGNATURES", 5, DEBUG_EXPORT_DECOMPILE, Inherit.ALLOW_OVERRIDE, "mergeGenericSignatures");
      DEBUG_VERIFY = new Option("DEBUG_VERIFY", 6, DEBUG_ALL, "verify");
      DEBUG_VERBOSE = new Option("DEBUG_VERBOSE", 7, DEBUG_ALL, "verbose");
      DEBUG_INJECTORS = new Option("DEBUG_INJECTORS", 8, DEBUG_ALL, "countInjections");
      DEBUG_STRICT = new Option("DEBUG_STRICT", 9, DEBUG_ALL, Inherit.INDEPENDENT, "strict");
      DEBUG_UNIQUE = new Option("DEBUG_UNIQUE", 10, DEBUG_STRICT, "unique");
      DEBUG_TARGETS = new Option("DEBUG_TARGETS", 11, DEBUG_STRICT, "targets");
      DEBUG_PROFILER = new Option("DEBUG_PROFILER", 12, DEBUG_ALL, Inherit.ALLOW_OVERRIDE, "profiler");
      DUMP_TARGET_ON_FAILURE = new Option("DUMP_TARGET_ON_FAILURE", 13, "dumpTargetOnFailure");
      CHECK_ALL = new Option("CHECK_ALL", 14, "checks");
      CHECK_IMPLEMENTS = new Option("CHECK_IMPLEMENTS", 15, CHECK_ALL, "interfaces");
      CHECK_IMPLEMENTS_STRICT = new Option("CHECK_IMPLEMENTS_STRICT", 16, CHECK_IMPLEMENTS, Inherit.ALLOW_OVERRIDE, "strict");
      IGNORE_CONSTRAINTS = new Option("IGNORE_CONSTRAINTS", 17, "ignoreConstraints");
      HOT_SWAP = new Option("HOT_SWAP", 18, "hotSwap");
      ENVIRONMENT = new Option("ENVIRONMENT", 19, Inherit.ALWAYS_FALSE, "env");
      OBFUSCATION_TYPE = new Option("OBFUSCATION_TYPE", 20, ENVIRONMENT, Inherit.ALWAYS_FALSE, "obf");
      DISABLE_REFMAP = new Option("DISABLE_REFMAP", 21, ENVIRONMENT, Inherit.INDEPENDENT, "disableRefMap");
      REFMAP_REMAP = new Option("REFMAP_REMAP", 22, ENVIRONMENT, Inherit.INDEPENDENT, "remapRefMap");
      REFMAP_REMAP_RESOURCE = new Option("REFMAP_REMAP_RESOURCE", 23, ENVIRONMENT, Inherit.INDEPENDENT, "refMapRemappingFile", "");
      REFMAP_REMAP_SOURCE_ENV = new Option("REFMAP_REMAP_SOURCE_ENV", 24, ENVIRONMENT, Inherit.INDEPENDENT, "refMapRemappingEnv", "searge");
      REFMAP_REMAP_ALLOW_PERMISSIVE = new Option("REFMAP_REMAP_ALLOW_PERMISSIVE", 25, ENVIRONMENT, Inherit.INDEPENDENT, "allowPermissiveMatch", true, "true");
      IGNORE_REQUIRED = new Option("IGNORE_REQUIRED", 26, ENVIRONMENT, Inherit.INDEPENDENT, "ignoreRequired");
      DEFAULT_COMPATIBILITY_LEVEL = new Option("DEFAULT_COMPATIBILITY_LEVEL", 27, ENVIRONMENT, Inherit.INDEPENDENT, "compatLevel");
      SHIFT_BY_VIOLATION_BEHAVIOUR = new Option("SHIFT_BY_VIOLATION_BEHAVIOUR", 28, ENVIRONMENT, Inherit.INDEPENDENT, "shiftByViolation", "warn");
      INITIALISER_INJECTION_MODE = new Option("INITIALISER_INJECTION_MODE", 29, "initialiserInjectionMode", "default");
      $VALUES = new Option[] { 
          DEBUG_ALL, DEBUG_EXPORT, DEBUG_EXPORT_FILTER, DEBUG_EXPORT_DECOMPILE, DEBUG_EXPORT_DECOMPILE_THREADED, DEBUG_EXPORT_DECOMPILE_MERGESIGNATURES, DEBUG_VERIFY, DEBUG_VERBOSE, DEBUG_INJECTORS, DEBUG_STRICT, 
          DEBUG_UNIQUE, DEBUG_TARGETS, DEBUG_PROFILER, DUMP_TARGET_ON_FAILURE, CHECK_ALL, CHECK_IMPLEMENTS, CHECK_IMPLEMENTS_STRICT, IGNORE_CONSTRAINTS, HOT_SWAP, ENVIRONMENT, 
          OBFUSCATION_TYPE, DISABLE_REFMAP, REFMAP_REMAP, REFMAP_REMAP_RESOURCE, REFMAP_REMAP_SOURCE_ENV, REFMAP_REMAP_ALLOW_PERMISSIVE, IGNORE_REQUIRED, DEFAULT_COMPATIBILITY_LEVEL, SHIFT_BY_VIOLATION_BEHAVIOUR, INITIALISER_INJECTION_MODE };
    }
    
    private enum Inherit {
      INHERIT,
      ALWAYS_FALSE(7, 51, false),
      INDEPENDENT(7, 51, false),
      ALLOW_OVERRIDE(7, 51, false);
      
      static {
        ALWAYS_FALSE = new Inherit("ALWAYS_FALSE", 3);
        $VALUES = new Inherit[] { INHERIT, ALLOW_OVERRIDE, INDEPENDENT, ALWAYS_FALSE };
      }
    }
    
    Option(Option param1Option, Inherit param1Inherit, String param1String1, boolean param1Boolean, String param1String2) {
      this.parent = param1Option;
      this.inheritance = param1Inherit;
      this.property = ((param1Option != null) ? param1Option.property : "mixin") + "." + param1String1;
      this.defaultValue = param1String2;
      this.isFlag = param1Boolean;
      byte b = 0;
      for (; param1Option != null; b++)
        param1Option = param1Option.parent; 
      this.depth = b;
    }
    
    Option getParent() {
      return this.parent;
    }
    
    String getProperty() {
      return this.property;
    }
    
    public String toString() {
      return this.isFlag ? String.valueOf(getBooleanValue()) : getStringValue();
    }
    
    private boolean getLocalBooleanValue(boolean param1Boolean) {
      return Boolean.parseBoolean(System.getProperty(this.property, Boolean.toString(param1Boolean)));
    }
    
    private boolean getInheritedBooleanValue() {
      return (this.parent != null && this.parent.getBooleanValue());
    }
    
    final boolean getBooleanValue() {
      if (this.inheritance == Inherit.ALWAYS_FALSE)
        return false; 
      boolean bool = getLocalBooleanValue(false);
      if (this.inheritance == Inherit.INDEPENDENT)
        return bool; 
      boolean bool1 = (bool || getInheritedBooleanValue()) ? true : false;
      return (this.inheritance == Inherit.INHERIT) ? bool1 : getLocalBooleanValue(bool1);
    }
    
    final String getStringValue() {
      return (this.inheritance == Inherit.INDEPENDENT || this.parent == null || this.parent.getBooleanValue()) ? System.getProperty(this.property, this.defaultValue) : this.defaultValue;
    }
    
    <E extends Enum<E>> E getEnumValue(E param1E) {
      String str = System.getProperty(this.property, param1E.name());
      try {
        return Enum.valueOf((Class)param1E.getClass(), str.toUpperCase());
      } catch (IllegalArgumentException illegalArgumentException) {
        return param1E;
      } 
    }
  }
  
  public enum CompatibilityLevel {
    JAVA_9,
    JAVA_8,
    JAVA_6(6, 50, false),
    JAVA_7(7, 51, false) {
      boolean isSupported() {
        return (JavaVersion.current() >= 1.7D);
      }
    };
    
    private CompatibilityLevel maxCompatibleLevel;
    
    private final int ver;
    
    private final int classVersion;
    
    private final boolean supportsMethodsInInterfaces;
    
    private static final int CLASS_V1_9 = 53;
    
    static {
      JAVA_9 = new null("JAVA_9", 3, 9, 53, true);
      $VALUES = new CompatibilityLevel[] { JAVA_6, JAVA_7, JAVA_8, JAVA_9 };
    }
    
    CompatibilityLevel(int param1Int1, int param1Int2, boolean param1Boolean) {
      this.ver = param1Int1;
      this.classVersion = param1Int2;
      this.supportsMethodsInInterfaces = param1Boolean;
    }
    
    private void setMaxCompatibleLevel(CompatibilityLevel param1CompatibilityLevel) {
      this.maxCompatibleLevel = param1CompatibilityLevel;
    }
    
    boolean isSupported() {
      return true;
    }
    
    public int classVersion() {
      return this.classVersion;
    }
    
    public boolean supportsMethodsInInterfaces() {
      return this.supportsMethodsInInterfaces;
    }
    
    public boolean isAtLeast(CompatibilityLevel param1CompatibilityLevel) {
      return (param1CompatibilityLevel == null || this.ver >= param1CompatibilityLevel.ver);
    }
    
    public boolean canElevateTo(CompatibilityLevel param1CompatibilityLevel) {
      if (param1CompatibilityLevel == null || this.maxCompatibleLevel == null)
        return true; 
      return (param1CompatibilityLevel.ver <= this.maxCompatibleLevel.ver);
    }
    
    public boolean canSupport(CompatibilityLevel param1CompatibilityLevel) {
      if (param1CompatibilityLevel == null)
        return true; 
      return param1CompatibilityLevel.canElevateTo(this);
    }
  }
  
  static class TokenProviderWrapper implements Comparable<TokenProviderWrapper> {
    private final int priority;
    
    private final int order;
    
    private final MixinEnvironment environment;
    
    private final IEnvironmentTokenProvider provider;
    
    private static int nextOrder = 0;
    
    public TokenProviderWrapper(IEnvironmentTokenProvider param1IEnvironmentTokenProvider, MixinEnvironment param1MixinEnvironment) {
      this.provider = param1IEnvironmentTokenProvider;
      this.environment = param1MixinEnvironment;
      this.order = nextOrder++;
      this.priority = param1IEnvironmentTokenProvider.getPriority();
    }
    
    public int compareTo(TokenProviderWrapper param1TokenProviderWrapper) {
      if (param1TokenProviderWrapper == null)
        return 0; 
      if (param1TokenProviderWrapper.priority == this.priority)
        return param1TokenProviderWrapper.order - this.order; 
      return param1TokenProviderWrapper.priority - this.priority;
    }
    
    public IEnvironmentTokenProvider getProvider() {
      return this.provider;
    }
    
    Integer getToken(String param1String) {
      return this.provider.getToken(param1String, this.environment);
    }
  }
  
  static class MixinLogWatcher {
    static Level oldLevel = null;
    
    static MixinAppender appender = new MixinAppender();
    
    static Logger log;
    
    static {
    
    }
    
    static void begin() {
      Logger logger = LogManager.getLogger("FML");
      if (!(logger instanceof Logger))
        return; 
      log = (Logger)logger;
      oldLevel = log.getLevel();
      appender.start();
      log.addAppender((Appender)appender);
      log.setLevel(Level.ALL);
    }
    
    static void end() {
      if (log != null)
        log.removeAppender((Appender)appender); 
    }
    
    static class MixinAppender extends AbstractAppender {
      MixinAppender() {
        super("MixinLogWatcherAppender", null, null);
      }
      
      public void append(LogEvent param2LogEvent) {
        if (param2LogEvent.getLevel() != Level.DEBUG || !"Validating minecraft".equals(param2LogEvent.getMessage().getFormattedMessage()))
          return; 
        MixinEnvironment.gotoPhase(MixinEnvironment.Phase.INIT);
        if (MixinEnvironment.MixinLogWatcher.log.getLevel() == Level.ALL)
          MixinEnvironment.MixinLogWatcher.log.setLevel(MixinEnvironment.MixinLogWatcher.oldLevel); 
      }
    }
  }
  
  private static final Set<String> excludeTransformers = Sets.newHashSet((Object[])new String[] { "net.minecraftforge.fml.common.asm.transformers.EventSubscriptionTransformer", "cpw.mods.fml.common.asm.transformers.EventSubscriptionTransformer", "net.minecraftforge.fml.common.asm.transformers.TerminalTransformer", "cpw.mods.fml.common.asm.transformers.TerminalTransformer" });
  
  static {
    currentPhase = Phase.NOT_INITIALISED;
    compatibility = Option.DEFAULT_COMPATIBILITY_LEVEL.<CompatibilityLevel>getEnumValue(CompatibilityLevel.JAVA_6);
    showHeader = true;
    logger = LogManager.getLogger("mixin");
    profiler = new Profiler();
  }
  
  private final Set<String> tokenProviderClasses = new HashSet<String>();
  
  private final List<TokenProviderWrapper> tokenProviders = new ArrayList<TokenProviderWrapper>();
  
  private final Map<String, Integer> internalTokens = new HashMap<String, Integer>();
  
  private final RemapperChain remappers = new RemapperChain();
  
  private String obfuscationContext = null;
  
  private static Phase currentPhase;
  
  private Side side;
  
  private final Phase phase;
  
  private final String configsKey;
  
  private static boolean showHeader;
  
  private final boolean[] options;
  
  private final IMixinService service;
  
  private static final Profiler profiler;
  
  private List<ILegacyClassTransformer> transformers;
  
  private static MixinEnvironment currentEnvironment;
  
  private static CompatibilityLevel compatibility;
  
  private static final Logger logger;
  
  MixinEnvironment(Phase paramPhase) {
    this.service = MixinService.getService();
    this.phase = paramPhase;
    this.configsKey = "mixin.configs." + this.phase.name.toLowerCase();
    String str = getVersion();
    if (str == null || !"0.7.11".equals(str))
      throw new MixinException("Environment conflict, mismatched versions or you didn't call MixinBootstrap.init()"); 
    this.service.checkEnv(this);
    this.options = new boolean[(Option.values()).length];
    for (Option option : Option.values())
      this.options[option.ordinal()] = option.getBooleanValue(); 
    if (showHeader) {
      showHeader = false;
      printHeader(str);
    } 
  }
  
  private void printHeader(Object paramObject) {
    String str1 = getCodeSource();
    String str2 = this.service.getName();
    Side side = getSide();
    logger.info("SpongePowered MIXIN Subsystem Version={} Source={} Service={} Env={}", new Object[] { paramObject, str1, str2, side });
    boolean bool = getOption(Option.DEBUG_VERBOSE);
    if (bool || getOption(Option.DEBUG_EXPORT) || getOption(Option.DEBUG_PROFILER)) {
      PrettyPrinter prettyPrinter = new PrettyPrinter(32);
      prettyPrinter.add("SpongePowered MIXIN%s", new Object[] { bool ? " (Verbose debugging enabled)" : "" }).centre().hr();
      prettyPrinter.kv("Code source", str1);
      prettyPrinter.kv("Internal Version", paramObject);
      prettyPrinter.kv("Java 8 Supported", Boolean.valueOf(CompatibilityLevel.JAVA_8.isSupported())).hr();
      prettyPrinter.kv("Service Name", str2);
      prettyPrinter.kv("Service Class", this.service.getClass().getName()).hr();
      for (Option option : Option.values()) {
        StringBuilder stringBuilder = new StringBuilder();
        for (byte b = 0; b < option.depth; b++)
          stringBuilder.append("- "); 
        prettyPrinter.kv(option.property, "%s<%s>", new Object[] { stringBuilder, option });
      } 
      prettyPrinter.hr().kv("Detected Side", side);
      prettyPrinter.print(System.err);
    } 
  }
  
  private String getCodeSource() {
    try {
      return getClass().getProtectionDomain().getCodeSource().getLocation().toString();
    } catch (Throwable throwable) {
      return "Unknown";
    } 
  }
  
  public Phase getPhase() {
    return this.phase;
  }
  
  @Deprecated
  public List<String> getMixinConfigs() {
    List<String> list = (List)GlobalProperties.get(this.configsKey);
    if (list == null) {
      list = new ArrayList();
      GlobalProperties.put(this.configsKey, list);
    } 
    return list;
  }
  
  @Deprecated
  public MixinEnvironment addConfiguration(String paramString) {
    logger.warn("MixinEnvironment::addConfiguration is deprecated and will be removed. Use Mixins::addConfiguration instead!");
    Mixins.addConfiguration(paramString, this);
    return this;
  }
  
  void registerConfig(String paramString) {
    List<String> list = getMixinConfigs();
    if (!list.contains(paramString))
      list.add(paramString); 
  }
  
  @Deprecated
  public MixinEnvironment registerErrorHandlerClass(String paramString) {
    Mixins.registerErrorHandlerClass(paramString);
    return this;
  }
  
  public MixinEnvironment registerTokenProviderClass(String paramString) {
    if (!this.tokenProviderClasses.contains(paramString))
      try {
        Class<IEnvironmentTokenProvider> clazz = this.service.getClassProvider().findClass(paramString, true);
        IEnvironmentTokenProvider iEnvironmentTokenProvider = clazz.newInstance();
        registerTokenProvider(iEnvironmentTokenProvider);
      } catch (Throwable throwable) {
        logger.error("Error instantiating " + paramString, throwable);
      }  
    return this;
  }
  
  public MixinEnvironment registerTokenProvider(IEnvironmentTokenProvider paramIEnvironmentTokenProvider) {
    if (paramIEnvironmentTokenProvider != null && !this.tokenProviderClasses.contains(paramIEnvironmentTokenProvider.getClass().getName())) {
      String str = paramIEnvironmentTokenProvider.getClass().getName();
      TokenProviderWrapper tokenProviderWrapper = new TokenProviderWrapper(paramIEnvironmentTokenProvider, this);
      logger.info("Adding new token provider {} to {}", new Object[] { str, this });
      this.tokenProviders.add(tokenProviderWrapper);
      this.tokenProviderClasses.add(str);
      Collections.sort(this.tokenProviders);
    } 
    return this;
  }
  
  public Integer getToken(String paramString) {
    paramString = paramString.toUpperCase();
    for (TokenProviderWrapper tokenProviderWrapper : this.tokenProviders) {
      Integer integer = tokenProviderWrapper.getToken(paramString);
      if (integer != null)
        return integer; 
    } 
    return this.internalTokens.get(paramString);
  }
  
  @Deprecated
  public Set<String> getErrorHandlerClasses() {
    return Mixins.getErrorHandlerClasses();
  }
  
  public Object getActiveTransformer() {
    return GlobalProperties.get("mixin.transformer");
  }
  
  public void setActiveTransformer(ITransformer paramITransformer) {
    if (paramITransformer != null)
      GlobalProperties.put("mixin.transformer", paramITransformer); 
  }
  
  public MixinEnvironment setSide(Side paramSide) {
    if (paramSide != null && getSide() == Side.UNKNOWN && paramSide != Side.UNKNOWN)
      this.side = paramSide; 
    return this;
  }
  
  public Side getSide() {
    if (this.side == null)
      for (Side side : Side.values()) {
        if (side.detect()) {
          this.side = side;
          break;
        } 
      }  
    return (this.side != null) ? this.side : Side.UNKNOWN;
  }
  
  public String getVersion() {
    return (String)GlobalProperties.get("mixin.initialised");
  }
  
  public boolean getOption(Option paramOption) {
    return this.options[paramOption.ordinal()];
  }
  
  public void setOption(Option paramOption, boolean paramBoolean) {
    this.options[paramOption.ordinal()] = paramBoolean;
  }
  
  public String getOptionValue(Option paramOption) {
    return paramOption.getStringValue();
  }
  
  public <E extends Enum<E>> E getOption(Option paramOption, E paramE) {
    return paramOption.getEnumValue(paramE);
  }
  
  public void setObfuscationContext(String paramString) {
    this.obfuscationContext = paramString;
  }
  
  public String getObfuscationContext() {
    return this.obfuscationContext;
  }
  
  public String getRefmapObfuscationContext() {
    String str = Option.OBFUSCATION_TYPE.getStringValue();
    if (str != null)
      return str; 
    return this.obfuscationContext;
  }
  
  public RemapperChain getRemappers() {
    return this.remappers;
  }
  
  public void audit() {
    Object object = getActiveTransformer();
    if (object instanceof MixinTransformer) {
      MixinTransformer mixinTransformer = (MixinTransformer)object;
      mixinTransformer.audit(this);
    } 
  }
  
  public List<ILegacyClassTransformer> getTransformers() {
    if (this.transformers == null)
      buildTransformerDelegationList(); 
    return Collections.unmodifiableList(this.transformers);
  }
  
  public void addTransformerExclusion(String paramString) {
    excludeTransformers.add(paramString);
    this.transformers = null;
  }
  
  private void buildTransformerDelegationList() {
    logger.debug("Rebuilding transformer delegation list:");
    this.transformers = new ArrayList<ILegacyClassTransformer>();
    for (ITransformer iTransformer : this.service.getTransformers()) {
      if (!(iTransformer instanceof ILegacyClassTransformer))
        continue; 
      ILegacyClassTransformer iLegacyClassTransformer = (ILegacyClassTransformer)iTransformer;
      String str = iLegacyClassTransformer.getName();
      boolean bool = true;
      for (String str1 : excludeTransformers) {
        if (str.contains(str1)) {
          bool = false;
          break;
        } 
      } 
      if (bool && !iLegacyClassTransformer.isDelegationExcluded()) {
        logger.debug("  Adding:    {}", new Object[] { str });
        this.transformers.add(iLegacyClassTransformer);
        continue;
      } 
      logger.debug("  Excluding: {}", new Object[] { str });
    } 
    logger.debug("Transformer delegation list created with {} entries", new Object[] { Integer.valueOf(this.transformers.size()) });
  }
  
  public String toString() {
    return String.format("%s[%s]", new Object[] { getClass().getSimpleName(), this.phase });
  }
  
  private static Phase getCurrentPhase() {
    if (currentPhase == Phase.NOT_INITIALISED)
      init(Phase.PREINIT); 
    return currentPhase;
  }
  
  public static void init(Phase paramPhase) {
    if (currentPhase == Phase.NOT_INITIALISED) {
      currentPhase = paramPhase;
      MixinEnvironment mixinEnvironment = getEnvironment(paramPhase);
      getProfiler().setActive(mixinEnvironment.getOption(Option.DEBUG_PROFILER));
      MixinLogWatcher.begin();
    } 
  }
  
  public static MixinEnvironment getEnvironment(Phase paramPhase) {
    if (paramPhase == null)
      return Phase.DEFAULT.getEnvironment(); 
    return paramPhase.getEnvironment();
  }
  
  public static MixinEnvironment getDefaultEnvironment() {
    return getEnvironment(Phase.DEFAULT);
  }
  
  public static MixinEnvironment getCurrentEnvironment() {
    if (currentEnvironment == null)
      currentEnvironment = getEnvironment(getCurrentPhase()); 
    return currentEnvironment;
  }
  
  public static CompatibilityLevel getCompatibilityLevel() {
    return compatibility;
  }
  
  @Deprecated
  public static void setCompatibilityLevel(CompatibilityLevel paramCompatibilityLevel) throws IllegalArgumentException {
    StackTraceElement[] arrayOfStackTraceElement = Thread.currentThread().getStackTrace();
    if (!"org.spongepowered.asm.mixin.transformer.MixinConfig".equals(arrayOfStackTraceElement[2].getClassName()))
      logger.warn("MixinEnvironment::setCompatibilityLevel is deprecated and will be removed. Set level via config instead!"); 
    if (paramCompatibilityLevel != compatibility && paramCompatibilityLevel.isAtLeast(compatibility)) {
      if (!paramCompatibilityLevel.isSupported())
        throw new IllegalArgumentException("The requested compatibility level " + paramCompatibilityLevel + " could not be set. Level is not supported"); 
      compatibility = paramCompatibilityLevel;
      logger.info("Compatibility level set to {}", new Object[] { paramCompatibilityLevel });
    } 
  }
  
  public static Profiler getProfiler() {
    return profiler;
  }
  
  static void gotoPhase(Phase paramPhase) {
    if (paramPhase == null || paramPhase.ordinal < 0)
      throw new IllegalArgumentException("Cannot go to the specified phase, phase is null or invalid"); 
    if (paramPhase.ordinal > (getCurrentPhase()).ordinal)
      MixinService.getService().beginPhase(); 
    if (paramPhase == Phase.DEFAULT)
      MixinLogWatcher.end(); 
    currentPhase = paramPhase;
    currentEnvironment = getEnvironment(getCurrentPhase());
  }
}
