package org.spongepowered.asm.mixin.transformer;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import java.util.UUID;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.lib.tree.ClassNode;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.Mixins;
import org.spongepowered.asm.mixin.extensibility.IMixinConfig;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinErrorHandler;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;
import org.spongepowered.asm.mixin.injection.invoke.arg.ArgsClassGenerator;
import org.spongepowered.asm.mixin.throwables.ClassAlreadyLoadedException;
import org.spongepowered.asm.mixin.throwables.MixinApplyError;
import org.spongepowered.asm.mixin.throwables.MixinException;
import org.spongepowered.asm.mixin.throwables.MixinPrepareError;
import org.spongepowered.asm.mixin.transformer.ext.Extensions;
import org.spongepowered.asm.mixin.transformer.ext.IClassGenerator;
import org.spongepowered.asm.mixin.transformer.ext.IExtension;
import org.spongepowered.asm.mixin.transformer.ext.IHotSwap;
import org.spongepowered.asm.mixin.transformer.ext.extensions.ExtensionCheckClass;
import org.spongepowered.asm.mixin.transformer.ext.extensions.ExtensionCheckInterfaces;
import org.spongepowered.asm.mixin.transformer.ext.extensions.ExtensionClassExporter;
import org.spongepowered.asm.mixin.transformer.throwables.InvalidMixinException;
import org.spongepowered.asm.mixin.transformer.throwables.MixinTransformerError;
import org.spongepowered.asm.service.IMixinService;
import org.spongepowered.asm.service.ITransformer;
import org.spongepowered.asm.service.MixinService;
import org.spongepowered.asm.transformers.TreeTransformer;
import org.spongepowered.asm.util.PrettyPrinter;
import org.spongepowered.asm.util.ReEntranceLock;
import org.spongepowered.asm.util.perf.Profiler;

public class MixinTransformer extends TreeTransformer {
  enum ErrorPhase {
    PREPARE {
      IMixinErrorHandler.ErrorAction onError(IMixinErrorHandler param2IMixinErrorHandler, String param2String, InvalidMixinException param2InvalidMixinException, IMixinInfo param2IMixinInfo, IMixinErrorHandler.ErrorAction param2ErrorAction) {
        try {
          return param2IMixinErrorHandler.onPrepareError(param2IMixinInfo.getConfig(), (Throwable)param2InvalidMixinException, param2IMixinInfo, param2ErrorAction);
        } catch (AbstractMethodError abstractMethodError) {
          return param2ErrorAction;
        } 
      }
      
      protected String getContext(IMixinInfo param2IMixinInfo, String param2String) {
        return String.format("preparing %s in %s", new Object[] { param2IMixinInfo.getName(), param2String });
      }
    },
    APPLY {
      IMixinErrorHandler.ErrorAction onError(IMixinErrorHandler param2IMixinErrorHandler, String param2String, InvalidMixinException param2InvalidMixinException, IMixinInfo param2IMixinInfo, IMixinErrorHandler.ErrorAction param2ErrorAction) {
        try {
          return param2IMixinErrorHandler.onApplyError(param2String, (Throwable)param2InvalidMixinException, param2IMixinInfo, param2ErrorAction);
        } catch (AbstractMethodError abstractMethodError) {
          return param2ErrorAction;
        } 
      }
      
      protected String getContext(IMixinInfo param2IMixinInfo, String param2String) {
        return String.format("%s -> %s", new Object[] { param2IMixinInfo, param2String });
      }
    };
    
    private final String text;
    
    ErrorPhase() {
      this.text = name().toLowerCase();
    }
    
    public String getLogMessage(String param1String, InvalidMixinException param1InvalidMixinException, IMixinInfo param1IMixinInfo) {
      return String.format("Mixin %s failed %s: %s %s", new Object[] { this.text, getContext(param1IMixinInfo, param1String), param1InvalidMixinException.getClass().getName(), param1InvalidMixinException.getMessage() });
    }
    
    public String getErrorMessage(IMixinInfo param1IMixinInfo, IMixinConfig param1IMixinConfig, MixinEnvironment.Phase param1Phase) {
      return String.format("Mixin [%s] from phase [%s] in config [%s] FAILED during %s", new Object[] { param1IMixinInfo, param1Phase, param1IMixinConfig, name() });
    }
    
    abstract IMixinErrorHandler.ErrorAction onError(IMixinErrorHandler param1IMixinErrorHandler, String param1String, InvalidMixinException param1InvalidMixinException, IMixinInfo param1IMixinInfo, IMixinErrorHandler.ErrorAction param1ErrorAction);
    
    protected abstract String getContext(IMixinInfo param1IMixinInfo, String param1String);
  }
  
  static final Logger logger = LogManager.getLogger("mixin");
  
  private final IMixinService service = MixinService.getService();
  
  private final List<MixinConfig> configs = new ArrayList<MixinConfig>();
  
  private final List<MixinConfig> pendingConfigs = new ArrayList<MixinConfig>();
  
  private final String sessionId = UUID.randomUUID().toString();
  
  private Level verboseLoggingLevel = Level.DEBUG;
  
  private boolean errorState = false;
  
  private int transformedCount = 0;
  
  private final Profiler profiler;
  
  private final MixinPostProcessor postProcessor;
  
  private final IHotSwap hotSwapper;
  
  private final Extensions extensions;
  
  private final ReEntranceLock lock;
  
  private static final String METRONOME_AGENT_CLASS = "org.spongepowered.metronome.Agent";
  
  private MixinEnvironment currentEnvironment;
  
  private static final String MIXIN_AGENT_CLASS = "org.spongepowered.tools.agent.MixinAgent";
  
  MixinTransformer() {
    MixinEnvironment mixinEnvironment = MixinEnvironment.getCurrentEnvironment();
    Object object = mixinEnvironment.getActiveTransformer();
    if (object instanceof ITransformer)
      throw new MixinException("Terminating MixinTransformer instance " + this); 
    mixinEnvironment.setActiveTransformer((ITransformer)this);
    this.lock = this.service.getReEntranceLock();
    this.extensions = new Extensions(this);
    this.hotSwapper = initHotSwapper(mixinEnvironment);
    this.postProcessor = new MixinPostProcessor();
    this.extensions.add((IClassGenerator)new ArgsClassGenerator());
    this.extensions.add(new InnerClassGenerator());
    this.extensions.add((IExtension)new ExtensionClassExporter(mixinEnvironment));
    this.extensions.add((IExtension)new ExtensionCheckClass());
    this.extensions.add((IExtension)new ExtensionCheckInterfaces());
    this.profiler = MixinEnvironment.getProfiler();
  }
  
  private IHotSwap initHotSwapper(MixinEnvironment paramMixinEnvironment) {
    if (!paramMixinEnvironment.getOption(MixinEnvironment.Option.HOT_SWAP))
      return null; 
    try {
      logger.info("Attempting to load Hot-Swap agent");
      Class<?> clazz = Class.forName("org.spongepowered.tools.agent.MixinAgent");
      Constructor<?> constructor = clazz.getDeclaredConstructor(new Class[] { MixinTransformer.class });
      return (IHotSwap)constructor.newInstance(new Object[] { this });
    } catch (Throwable throwable) {
      logger.info("Hot-swap agent could not be loaded, hot swapping of mixins won't work. {}: {}", new Object[] { throwable
            .getClass().getSimpleName(), throwable.getMessage() });
      return null;
    } 
  }
  
  public void audit(MixinEnvironment paramMixinEnvironment) {
    HashSet<String> hashSet = new HashSet();
    for (MixinConfig mixinConfig : this.configs)
      hashSet.addAll(mixinConfig.getUnhandledTargets()); 
    Logger logger = LogManager.getLogger("mixin/audit");
    for (String str : hashSet) {
      try {
        logger.info("Force-loading class {}", new Object[] { str });
        this.service.getClassProvider().findClass(str, true);
      } catch (ClassNotFoundException classNotFoundException) {
        logger.error("Could not force-load " + str, classNotFoundException);
      } 
    } 
    for (MixinConfig mixinConfig : this.configs) {
      for (String str : mixinConfig.getUnhandledTargets()) {
        ClassAlreadyLoadedException classAlreadyLoadedException = new ClassAlreadyLoadedException(str + " was already classloaded");
        logger.error("Could not force-load " + str, (Throwable)classAlreadyLoadedException);
      } 
    } 
    if (paramMixinEnvironment.getOption(MixinEnvironment.Option.DEBUG_PROFILER))
      printProfilerSummary(); 
  }
  
  private void printProfilerSummary() {
    DecimalFormat decimalFormat1 = new DecimalFormat("(###0.000");
    DecimalFormat decimalFormat2 = new DecimalFormat("(###0.0");
    PrettyPrinter prettyPrinter = this.profiler.printer(false, false);
    long l1 = this.profiler.get("mixin.prepare").getTotalTime();
    long l2 = this.profiler.get("mixin.read").getTotalTime();
    long l3 = this.profiler.get("mixin.apply").getTotalTime();
    long l4 = this.profiler.get("mixin.write").getTotalTime();
    long l5 = this.profiler.get("mixin").getTotalTime();
    long l6 = this.profiler.get("class.load").getTotalTime();
    long l7 = this.profiler.get("class.transform").getTotalTime();
    long l8 = this.profiler.get("mixin.debug.export").getTotalTime();
    long l9 = l5 - l6 - l7 - l8;
    double d1 = l9 / l5 * 100.0D;
    double d2 = l6 / l5 * 100.0D;
    double d3 = l7 / l5 * 100.0D;
    double d4 = l8 / l5 * 100.0D;
    long l10 = 0L;
    Profiler.Section section = null;
    for (Profiler.Section section1 : this.profiler.getSections()) {
      long l = section1.getName().startsWith("class.transform.") ? section1.getTotalTime() : 0L;
      if (l > l10) {
        l10 = l;
        section = section1;
      } 
    } 
    prettyPrinter.hr().add("Summary").hr().add();
    String str = "%9d ms %12s seconds)";
    prettyPrinter.kv("Total mixin time", str, new Object[] { Long.valueOf(l5), decimalFormat1.format(l5 * 0.001D) }).add();
    prettyPrinter.kv("Preparing mixins", str, new Object[] { Long.valueOf(l1), decimalFormat1.format(l1 * 0.001D) });
    prettyPrinter.kv("Reading input", str, new Object[] { Long.valueOf(l2), decimalFormat1.format(l2 * 0.001D) });
    prettyPrinter.kv("Applying mixins", str, new Object[] { Long.valueOf(l3), decimalFormat1.format(l3 * 0.001D) });
    prettyPrinter.kv("Writing output", str, new Object[] { Long.valueOf(l4), decimalFormat1.format(l4 * 0.001D) }).add();
    prettyPrinter.kv("of which", "");
    prettyPrinter.kv("Time spent loading from disk", str, new Object[] { Long.valueOf(l6), decimalFormat1.format(l6 * 0.001D) });
    prettyPrinter.kv("Time spent transforming classes", str, new Object[] { Long.valueOf(l7), decimalFormat1.format(l7 * 0.001D) }).add();
    if (section != null) {
      prettyPrinter.kv("Worst transformer", section.getName());
      prettyPrinter.kv("Class", section.getInfo());
      prettyPrinter.kv("Time spent", "%s seconds", new Object[] { Double.valueOf(section.getTotalSeconds()) });
      prettyPrinter.kv("called", "%d times", new Object[] { Integer.valueOf(section.getTotalCount()) }).add();
    } 
    prettyPrinter.kv("   Time allocation:     Processing mixins", "%9d ms %10s%% of total)", new Object[] { Long.valueOf(l9), decimalFormat2.format(d1) });
    prettyPrinter.kv("Loading classes", "%9d ms %10s%% of total)", new Object[] { Long.valueOf(l6), decimalFormat2.format(d2) });
    prettyPrinter.kv("Running transformers", "%9d ms %10s%% of total)", new Object[] { Long.valueOf(l7), decimalFormat2.format(d3) });
    if (l8 > 0L)
      prettyPrinter.kv("Exporting classes (debug)", "%9d ms %10s%% of total)", new Object[] { Long.valueOf(l8), decimalFormat2.format(d4) }); 
    prettyPrinter.add();
    try {
      Class clazz = this.service.getClassProvider().findAgentClass("org.spongepowered.metronome.Agent", false);
      Method method = clazz.getDeclaredMethod("getTimes", new Class[0]);
      Map map = (Map)method.invoke(null, new Object[0]);
      prettyPrinter.hr().add("Transformer Times").hr().add();
      int i = 10;
      for (Map.Entry entry : map.entrySet())
        i = Math.max(i, ((String)entry.getKey()).length()); 
      for (Map.Entry entry : map.entrySet()) {
        String str1 = (String)entry.getKey();
        long l = 0L;
        for (Profiler.Section section1 : this.profiler.getSections()) {
          if (str1.equals(section1.getInfo())) {
            l = section1.getTotalTime();
            break;
          } 
        } 
        if (l > 0L) {
          prettyPrinter.add("%-" + i + "s %8s ms %8s ms in mixin)", new Object[] { str1, Long.valueOf(((Long)entry.getValue()).longValue() + l), "(" + l });
          continue;
        } 
        prettyPrinter.add("%-" + i + "s %8s ms", new Object[] { str1, entry.getValue() });
      } 
      prettyPrinter.add();
    } catch (Throwable throwable) {}
    prettyPrinter.print();
  }
  
  public String getName() {
    return getClass().getName();
  }
  
  public boolean isDelegationExcluded() {
    return true;
  }
  
  public synchronized byte[] transformClassBytes(String paramString1, String paramString2, byte[] paramArrayOfbyte) {
    if (paramString2 == null || this.errorState)
      return paramArrayOfbyte; 
    MixinEnvironment mixinEnvironment = MixinEnvironment.getCurrentEnvironment();
    if (paramArrayOfbyte == null) {
      for (IClassGenerator iClassGenerator : this.extensions.getGenerators()) {
        Profiler.Section section1 = this.profiler.begin(new String[] { "generator", iClassGenerator.getClass().getSimpleName().toLowerCase() });
        paramArrayOfbyte = iClassGenerator.generate(paramString2);
        section1.end();
        if (paramArrayOfbyte != null) {
          this.extensions.export(mixinEnvironment, paramString2.replace('.', '/'), false, paramArrayOfbyte);
          return paramArrayOfbyte;
        } 
      } 
      return paramArrayOfbyte;
    } 
    boolean bool = this.lock.push().check();
    Profiler.Section section = this.profiler.begin("mixin");
    if (!bool)
      try {
        checkSelect(mixinEnvironment);
      } catch (Exception exception) {
        this.lock.pop();
        section.end();
        throw new MixinException(exception);
      }  
    try {
      if (this.postProcessor.canTransform(paramString2)) {
        Profiler.Section section1 = this.profiler.begin("postprocessor");
        byte[] arrayOfByte = this.postProcessor.transformClassBytes(paramString1, paramString2, paramArrayOfbyte);
        section1.end();
        this.extensions.export(mixinEnvironment, paramString2, false, arrayOfByte);
        return arrayOfByte;
      } 
      TreeSet<MixinInfo> treeSet = null;
      boolean bool1 = false;
      for (MixinConfig mixinConfig : this.configs) {
        if (mixinConfig.packageMatch(paramString2)) {
          bool1 = true;
          continue;
        } 
        if (mixinConfig.hasMixinsFor(paramString2)) {
          if (treeSet == null)
            treeSet = new TreeSet(); 
          treeSet.addAll(mixinConfig.getMixinsFor(paramString2));
        } 
      } 
      if (bool1)
        throw new NoClassDefFoundError(String.format("%s is a mixin class and cannot be referenced directly", new Object[] { paramString2 })); 
      if (treeSet != null) {
        if (bool) {
          logger.warn("Re-entrance detected, this will cause serious problems.", (Throwable)new MixinException());
          throw new MixinApplyError("Re-entrance error.");
        } 
        if (this.hotSwapper != null)
          this.hotSwapper.registerTargetClass(paramString2, paramArrayOfbyte); 
        try {
          Profiler.Section section1 = this.profiler.begin("read");
          ClassNode classNode = readClass(paramArrayOfbyte, true);
          TargetClassContext targetClassContext = new TargetClassContext(mixinEnvironment, this.extensions, this.sessionId, paramString2, classNode, treeSet);
          section1.end();
          paramArrayOfbyte = applyMixins(mixinEnvironment, targetClassContext);
          this.transformedCount++;
        } catch (InvalidMixinException invalidMixinException) {
          dumpClassOnFailure(paramString2, paramArrayOfbyte, mixinEnvironment);
          handleMixinApplyError(paramString2, invalidMixinException, mixinEnvironment);
        } 
      } 
      return paramArrayOfbyte;
    } catch (Throwable throwable) {
      throwable.printStackTrace();
      dumpClassOnFailure(paramString2, paramArrayOfbyte, mixinEnvironment);
      throw new MixinTransformerError("An unexpected critical error was encountered", throwable);
    } finally {
      this.lock.pop();
      section.end();
    } 
  }
  
  public List<String> reload(String paramString, byte[] paramArrayOfbyte) {
    if (this.lock.getDepth() > 0)
      throw new MixinApplyError("Cannot reload mixin if re-entrant lock entered"); 
    ArrayList<String> arrayList = new ArrayList();
    for (MixinConfig mixinConfig : this.configs)
      arrayList.addAll(mixinConfig.reloadMixin(paramString, paramArrayOfbyte)); 
    return arrayList;
  }
  
  private void checkSelect(MixinEnvironment paramMixinEnvironment) {
    if (this.currentEnvironment != paramMixinEnvironment) {
      select(paramMixinEnvironment);
      return;
    } 
    int i = Mixins.getUnvisitedCount();
    if (i > 0 && this.transformedCount == 0)
      select(paramMixinEnvironment); 
  }
  
  private void select(MixinEnvironment paramMixinEnvironment) {
    this.verboseLoggingLevel = paramMixinEnvironment.getOption(MixinEnvironment.Option.DEBUG_VERBOSE) ? Level.INFO : Level.DEBUG;
    if (this.transformedCount > 0)
      logger.log(this.verboseLoggingLevel, "Ending {}, applied {} mixins", new Object[] { this.currentEnvironment, Integer.valueOf(this.transformedCount) }); 
    String str = (this.currentEnvironment == paramMixinEnvironment) ? "Checking for additional" : "Preparing";
    logger.log(this.verboseLoggingLevel, "{} mixins for {}", new Object[] { str, paramMixinEnvironment });
    this.profiler.setActive(true);
    this.profiler.mark(paramMixinEnvironment.getPhase().toString() + ":prepare");
    Profiler.Section section = this.profiler.begin("prepare");
    selectConfigs(paramMixinEnvironment);
    this.extensions.select(paramMixinEnvironment);
    int i = prepareConfigs(paramMixinEnvironment);
    this.currentEnvironment = paramMixinEnvironment;
    this.transformedCount = 0;
    section.end();
    long l = section.getTime();
    double d = section.getSeconds();
    if (d > 0.25D) {
      long l1 = this.profiler.get("class.load").getTime();
      long l2 = this.profiler.get("class.transform").getTime();
      long l3 = this.profiler.get("mixin.plugin").getTime();
      String str1 = (new DecimalFormat("###0.000")).format(d);
      String str2 = (new DecimalFormat("###0.0")).format(l / i);
      logger.log(this.verboseLoggingLevel, "Prepared {} mixins in {} sec ({}ms avg) ({}ms load, {}ms transform, {}ms plugin)", new Object[] { Integer.valueOf(i), str1, str2, Long.valueOf(l1), Long.valueOf(l2), Long.valueOf(l3) });
    } 
    this.profiler.mark(paramMixinEnvironment.getPhase().toString() + ":apply");
    this.profiler.setActive(paramMixinEnvironment.getOption(MixinEnvironment.Option.DEBUG_PROFILER));
  }
  
  private void selectConfigs(MixinEnvironment paramMixinEnvironment) {
    for (Iterator<Config> iterator = Mixins.getConfigs().iterator(); iterator.hasNext(); ) {
      Config config = iterator.next();
      try {
        MixinConfig mixinConfig = config.get();
        if (mixinConfig.select(paramMixinEnvironment)) {
          iterator.remove();
          logger.log(this.verboseLoggingLevel, "Selecting config {}", new Object[] { mixinConfig });
          mixinConfig.onSelect();
          this.pendingConfigs.add(mixinConfig);
        } 
      } catch (Exception exception) {
        logger.warn(String.format("Failed to select mixin config: %s", new Object[] { config }), exception);
      } 
    } 
    Collections.sort(this.pendingConfigs);
  }
  
  private int prepareConfigs(MixinEnvironment paramMixinEnvironment) {
    int i = 0;
    final IHotSwap hotSwapper = this.hotSwapper;
    for (MixinConfig mixinConfig : this.pendingConfigs) {
      mixinConfig.addListener(this.postProcessor);
      if (iHotSwap != null)
        mixinConfig.addListener(new MixinConfig.IListener() {
              public void onPrepare(MixinInfo param1MixinInfo) {
                hotSwapper.registerMixinClass(param1MixinInfo.getClassName());
              }
              
              public void onInit(MixinInfo param1MixinInfo) {}
            }); 
    } 
    for (MixinConfig mixinConfig : this.pendingConfigs) {
      try {
        logger.log(this.verboseLoggingLevel, "Preparing {} ({})", new Object[] { mixinConfig, Integer.valueOf(mixinConfig.getDeclaredMixinCount()) });
        mixinConfig.prepare();
        i += mixinConfig.getMixinCount();
      } catch (InvalidMixinException invalidMixinException) {
        handleMixinPrepareError(mixinConfig, invalidMixinException, paramMixinEnvironment);
      } catch (Exception exception) {
        String str = exception.getMessage();
        logger.error("Error encountered whilst initialising mixin config '" + mixinConfig.getName() + "': " + str, exception);
      } 
    } 
    for (MixinConfig mixinConfig : this.pendingConfigs) {
      IMixinConfigPlugin iMixinConfigPlugin = mixinConfig.getPlugin();
      if (iMixinConfigPlugin == null)
        continue; 
      HashSet<String> hashSet = new HashSet();
      for (MixinConfig mixinConfig1 : this.pendingConfigs) {
        if (!mixinConfig1.equals(mixinConfig))
          hashSet.addAll(mixinConfig1.getTargets()); 
      } 
      iMixinConfigPlugin.acceptTargets(mixinConfig.getTargets(), Collections.unmodifiableSet(hashSet));
    } 
    for (MixinConfig mixinConfig : this.pendingConfigs) {
      try {
        mixinConfig.postInitialise();
      } catch (InvalidMixinException invalidMixinException) {
        handleMixinPrepareError(mixinConfig, invalidMixinException, paramMixinEnvironment);
      } catch (Exception exception) {
        String str = exception.getMessage();
        logger.error("Error encountered during mixin config postInit step'" + mixinConfig.getName() + "': " + str, exception);
      } 
    } 
    this.configs.addAll(this.pendingConfigs);
    Collections.sort(this.configs);
    this.pendingConfigs.clear();
    return i;
  }
  
  private byte[] applyMixins(MixinEnvironment paramMixinEnvironment, TargetClassContext paramTargetClassContext) {
    Profiler.Section section = this.profiler.begin("preapply");
    this.extensions.preApply(paramTargetClassContext);
    section = section.next("apply");
    apply(paramTargetClassContext);
    section = section.next("postapply");
    try {
      this.extensions.postApply(paramTargetClassContext);
    } catch (org.spongepowered.asm.mixin.transformer.ext.extensions.ExtensionCheckClass.ValidationFailedException validationFailedException) {
      logger.info(validationFailedException.getMessage());
      if (paramTargetClassContext.isExportForced() || paramMixinEnvironment.getOption(MixinEnvironment.Option.DEBUG_EXPORT))
        writeClass(paramTargetClassContext); 
    } 
    section.end();
    return writeClass(paramTargetClassContext);
  }
  
  private void apply(TargetClassContext paramTargetClassContext) {
    paramTargetClassContext.applyMixins();
  }
  
  private void handleMixinPrepareError(MixinConfig paramMixinConfig, InvalidMixinException paramInvalidMixinException, MixinEnvironment paramMixinEnvironment) throws MixinPrepareError {
    handleMixinError(paramMixinConfig.getName(), paramInvalidMixinException, paramMixinEnvironment, ErrorPhase.PREPARE);
  }
  
  private void handleMixinApplyError(String paramString, InvalidMixinException paramInvalidMixinException, MixinEnvironment paramMixinEnvironment) throws MixinApplyError {
    handleMixinError(paramString, paramInvalidMixinException, paramMixinEnvironment, ErrorPhase.APPLY);
  }
  
  private void handleMixinError(String paramString, InvalidMixinException paramInvalidMixinException, MixinEnvironment paramMixinEnvironment, ErrorPhase paramErrorPhase) throws Error {
    this.errorState = true;
    IMixinInfo iMixinInfo = paramInvalidMixinException.getMixin();
    if (iMixinInfo == null) {
      logger.error("InvalidMixinException has no mixin!", (Throwable)paramInvalidMixinException);
      throw paramInvalidMixinException;
    } 
    IMixinConfig iMixinConfig = iMixinInfo.getConfig();
    MixinEnvironment.Phase phase = iMixinInfo.getPhase();
    IMixinErrorHandler.ErrorAction errorAction = iMixinConfig.isRequired() ? IMixinErrorHandler.ErrorAction.ERROR : IMixinErrorHandler.ErrorAction.WARN;
    if (paramMixinEnvironment.getOption(MixinEnvironment.Option.DEBUG_VERBOSE))
      (new PrettyPrinter())
        .add("Invalid Mixin").centre()
        .hr('-')
        .kvWidth(10)
        .kv("Action", paramErrorPhase.name())
        .kv("Mixin", iMixinInfo.getClassName())
        .kv("Config", iMixinConfig.getName())
        .kv("Phase", phase)
        .hr('-')
        .add("    %s", new Object[] { paramInvalidMixinException.getClass().getName() }).hr('-')
        .addWrapped("    %s", new Object[] { paramInvalidMixinException.getMessage() }).hr('-')
        .add((Throwable)paramInvalidMixinException, 8)
        .trace(errorAction.logLevel); 
    for (IMixinErrorHandler iMixinErrorHandler : getErrorHandlers(iMixinInfo.getPhase())) {
      IMixinErrorHandler.ErrorAction errorAction1 = paramErrorPhase.onError(iMixinErrorHandler, paramString, paramInvalidMixinException, iMixinInfo, errorAction);
      if (errorAction1 != null)
        errorAction = errorAction1; 
    } 
    logger.log(errorAction.logLevel, paramErrorPhase.getLogMessage(paramString, paramInvalidMixinException, iMixinInfo), (Throwable)paramInvalidMixinException);
    this.errorState = false;
    if (errorAction == IMixinErrorHandler.ErrorAction.ERROR)
      throw new MixinApplyError(paramErrorPhase.getErrorMessage(iMixinInfo, iMixinConfig, phase), paramInvalidMixinException); 
  }
  
  private List<IMixinErrorHandler> getErrorHandlers(MixinEnvironment.Phase paramPhase) {
    ArrayList<IMixinErrorHandler> arrayList = new ArrayList();
    for (String str : Mixins.getErrorHandlerClasses()) {
      try {
        logger.info("Instancing error handler class {}", new Object[] { str });
        Class<IMixinErrorHandler> clazz = this.service.getClassProvider().findClass(str, true);
        IMixinErrorHandler iMixinErrorHandler = clazz.newInstance();
        if (iMixinErrorHandler != null)
          arrayList.add(iMixinErrorHandler); 
      } catch (Throwable throwable) {}
    } 
    return arrayList;
  }
  
  private byte[] writeClass(TargetClassContext paramTargetClassContext) {
    return writeClass(paramTargetClassContext.getClassName(), paramTargetClassContext.getClassNode(), paramTargetClassContext.isExportForced());
  }
  
  private byte[] writeClass(String paramString, ClassNode paramClassNode, boolean paramBoolean) {
    Profiler.Section section = this.profiler.begin("write");
    byte[] arrayOfByte = writeClass(paramClassNode);
    section.end();
    this.extensions.export(this.currentEnvironment, paramString, paramBoolean, arrayOfByte);
    return arrayOfByte;
  }
  
  private void dumpClassOnFailure(String paramString, byte[] paramArrayOfbyte, MixinEnvironment paramMixinEnvironment) {
    if (paramMixinEnvironment.getOption(MixinEnvironment.Option.DUMP_TARGET_ON_FAILURE)) {
      ExtensionClassExporter extensionClassExporter = (ExtensionClassExporter)this.extensions.getExtension(ExtensionClassExporter.class);
      extensionClassExporter.dumpClass(paramString.replace('.', '/') + ".target", paramArrayOfbyte);
    } 
  }
}
