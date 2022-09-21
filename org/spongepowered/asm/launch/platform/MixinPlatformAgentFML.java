package org.spongepowered.asm.launch.platform;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URI;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import net.minecraft.launchwrapper.ITweaker;
import net.minecraft.launchwrapper.Launch;
import net.minecraft.launchwrapper.LaunchClassLoader;
import org.apache.logging.log4j.Level;
import org.spongepowered.asm.launch.GlobalProperties;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.extensibility.IRemapper;

public class MixinPlatformAgentFML extends MixinPlatformAgentAbstract {
  private static final String FML_TWEAKER_DEOBF = "FMLDeobfTweaker";
  
  private final String fileName;
  
  private static final String FML_TWEAKER_INJECTION = "FMLInjectionAndSortingTweaker";
  
  private Class<?> clCoreModManager;
  
  private static final String FML_CMDLINE_COREMODS = "fml.coreMods.load";
  
  private static final String FML_PLUGIN_WRAPPER_CLASS = "FMLPluginWrapper";
  
  private static final String LOAD_CORE_MOD_METHOD = "loadCoreMod";
  
  private static final String CORE_MOD_MANAGER_CLASS_LEGACY = "cpw.mods.fml.relauncher.CoreModManager";
  
  private static final String FML_TWEAKER_TERMINAL = "TerminalTweaker";
  
  private static final String GET_REPARSEABLE_COREMODS_METHOD = "getReparseableCoremods";
  
  private static final String MFATT_FORCELOADASMOD = "ForceLoadAsMod";
  
  private static final String MFATT_COREMODCONTAINSMOD = "FMLCorePluginContainsFMLMod";
  
  private static final String CORE_MOD_MANAGER_CLASS = "net.minecraftforge.fml.relauncher.CoreModManager";
  
  private static final String FML_CORE_MOD_INSTANCE_FIELD = "coreModInstance";
  
  private boolean initInjectionState;
  
  private static final String GET_IGNORED_MODS_METHOD = "getIgnoredMods";
  
  private static final String MFATT_FMLCOREPLUGIN = "FMLCorePlugin";
  
  private static final Set<String> loadedCoreMods = new HashSet<String>();
  
  private static final String FML_REMAPPER_ADAPTER_CLASS = "org.spongepowered.asm.bridge.RemapperAdapterFML";
  
  private static final String GET_IGNORED_MODS_METHOD_LEGACY = "getLoadedCoremods";
  
  private final ITweaker coreModWrapper;
  
  static {
    for (String str : System.getProperty("fml.coreMods.load", "").split(",")) {
      if (!str.isEmpty()) {
        MixinPlatformAgentAbstract.logger.debug("FML platform agent will ignore coremod {} specified on the command line", new Object[] { str });
        loadedCoreMods.add(str);
      } 
    } 
  }
  
  public MixinPlatformAgentFML(MixinPlatformManager paramMixinPlatformManager, URI paramURI) {
    super(paramMixinPlatformManager, paramURI);
    this.fileName = this.container.getName();
    this.coreModWrapper = initFMLCoreMod();
  }
  
  private ITweaker initFMLCoreMod() {
    try {
      try {
        this.clCoreModManager = getCoreModManagerClass();
      } catch (ClassNotFoundException classNotFoundException) {
        MixinPlatformAgentAbstract.logger.info("FML platform manager could not load class {}. Proceeding without FML support.", new Object[] { classNotFoundException
              .getMessage() });
        return null;
      } 
      if ("true".equalsIgnoreCase(this.attributes.get("ForceLoadAsMod"))) {
        MixinPlatformAgentAbstract.logger.debug("ForceLoadAsMod was specified for {}, attempting force-load", new Object[] { this.fileName });
        loadAsMod();
      } 
      return injectCorePlugin();
    } catch (Exception exception) {
      MixinPlatformAgentAbstract.logger.catching(exception);
      return null;
    } 
  }
  
  private void loadAsMod() {
    try {
      getIgnoredMods(this.clCoreModManager).remove(this.fileName);
    } catch (Exception exception) {
      MixinPlatformAgentAbstract.logger.catching(exception);
    } 
    if (this.attributes.get("FMLCorePluginContainsFMLMod") != null) {
      if (isIgnoredReparseable()) {
        MixinPlatformAgentAbstract.logger.debug("Ignoring request to add {} to reparseable coremod collection - it is a deobfuscated dependency", new Object[] { this.fileName });
        return;
      } 
      addReparseableJar();
    } 
  }
  
  private boolean isIgnoredReparseable() {
    return this.container.toString().contains("deobfedDeps");
  }
  
  private void addReparseableJar() {
    try {
      Method method = this.clCoreModManager.getDeclaredMethod(GlobalProperties.getString("mixin.launch.fml.reparseablecoremodsmethod", "getReparseableCoremods"), new Class[0]);
      List<String> list = (List)method.invoke(null, new Object[0]);
      if (!list.contains(this.fileName)) {
        MixinPlatformAgentAbstract.logger.debug("Adding {} to reparseable coremod collection", new Object[] { this.fileName });
        list.add(this.fileName);
      } 
    } catch (Exception exception) {
      MixinPlatformAgentAbstract.logger.catching(exception);
    } 
  }
  
  private ITweaker injectCorePlugin() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
    String str = this.attributes.get("FMLCorePlugin");
    if (str == null)
      return null; 
    if (isAlreadyInjected(str)) {
      MixinPlatformAgentAbstract.logger.debug("{} has core plugin {}. Skipping because it was already injected.", new Object[] { this.fileName, str });
      return null;
    } 
    MixinPlatformAgentAbstract.logger.debug("{} has core plugin {}. Injecting it into FML for co-initialisation:", new Object[] { this.fileName, str });
    Method method = this.clCoreModManager.getDeclaredMethod(GlobalProperties.getString("mixin.launch.fml.loadcoremodmethod", "loadCoreMod"), new Class[] { LaunchClassLoader.class, String.class, File.class });
    method.setAccessible(true);
    ITweaker iTweaker = (ITweaker)method.invoke(null, new Object[] { Launch.classLoader, str, this.container });
    if (iTweaker == null) {
      MixinPlatformAgentAbstract.logger.debug("Core plugin {} could not be loaded.", new Object[] { str });
      return null;
    } 
    this.initInjectionState = isTweakerQueued("FMLInjectionAndSortingTweaker");
    loadedCoreMods.add(str);
    return iTweaker;
  }
  
  private boolean isAlreadyInjected(String paramString) {
    if (loadedCoreMods.contains(paramString))
      return true; 
    try {
      List list = (List)GlobalProperties.get("Tweaks");
      if (list == null)
        return false; 
      for (ITweaker iTweaker : list) {
        Class<?> clazz = iTweaker.getClass();
        if ("FMLPluginWrapper".equals(clazz.getSimpleName())) {
          Field field = clazz.getField("coreModInstance");
          field.setAccessible(true);
          Object object = field.get(iTweaker);
          if (paramString.equals(object.getClass().getName()))
            return true; 
        } 
      } 
    } catch (Exception exception) {}
    return false;
  }
  
  public String getPhaseProvider() {
    return MixinPlatformAgentFML.class.getName() + "$PhaseProvider";
  }
  
  public void prepare() {
    this.initInjectionState |= isTweakerQueued("FMLInjectionAndSortingTweaker");
  }
  
  public void initPrimaryContainer() {
    if (this.clCoreModManager != null)
      injectRemapper(); 
  }
  
  private void injectRemapper() {
    try {
      MixinPlatformAgentAbstract.logger.debug("Creating FML remapper adapter: {}", new Object[] { "org.spongepowered.asm.bridge.RemapperAdapterFML" });
      Class<?> clazz = Class.forName("org.spongepowered.asm.bridge.RemapperAdapterFML", true, (ClassLoader)Launch.classLoader);
      Method method = clazz.getDeclaredMethod("create", new Class[0]);
      IRemapper iRemapper = (IRemapper)method.invoke(null, new Object[0]);
      MixinEnvironment.getDefaultEnvironment().getRemappers().add(iRemapper);
    } catch (Exception exception) {
      MixinPlatformAgentAbstract.logger.debug("Failed instancing FML remapper adapter, things will probably go horribly for notch-obf'd mods!");
    } 
  }
  
  public void inject() {
    if (this.coreModWrapper != null && checkForCoInitialisation()) {
      MixinPlatformAgentAbstract.logger.debug("FML agent is co-initiralising coremod instance {} for {}", new Object[] { this.coreModWrapper, this.uri });
      this.coreModWrapper.injectIntoClassLoader(Launch.classLoader);
    } 
  }
  
  public String getLaunchTarget() {
    return null;
  }
  
  protected final boolean checkForCoInitialisation() {
    boolean bool1 = isTweakerQueued("FMLInjectionAndSortingTweaker");
    boolean bool2 = isTweakerQueued("TerminalTweaker");
    if ((this.initInjectionState && bool2) || bool1) {
      MixinPlatformAgentAbstract.logger.debug("FML agent is skipping co-init for {} because FML will inject it normally", new Object[] { this.coreModWrapper });
      return false;
    } 
    return !isTweakerQueued("FMLDeobfTweaker");
  }
  
  private static boolean isTweakerQueued(String paramString) {
    for (String str : GlobalProperties.get("TweakClasses")) {
      if (str.endsWith(paramString))
        return true; 
    } 
    return false;
  }
  
  private static Class<?> getCoreModManagerClass() throws ClassNotFoundException {
    try {
      return Class.forName(GlobalProperties.getString("mixin.launch.fml.coremodmanagerclass", "net.minecraftforge.fml.relauncher.CoreModManager"));
    } catch (ClassNotFoundException classNotFoundException) {
      return Class.forName("cpw.mods.fml.relauncher.CoreModManager");
    } 
  }
  
  private static List<String> getIgnoredMods(Class<?> paramClass) throws IllegalAccessException, InvocationTargetException {
    Method method = null;
    try {
      method = paramClass.getDeclaredMethod(GlobalProperties.getString("mixin.launch.fml.ignoredmodsmethod", "getIgnoredMods"), new Class[0]);
    } catch (NoSuchMethodException noSuchMethodException) {
      try {
        method = paramClass.getDeclaredMethod("getLoadedCoremods", new Class[0]);
      } catch (NoSuchMethodException noSuchMethodException1) {
        MixinPlatformAgentAbstract.logger.catching(Level.DEBUG, noSuchMethodException1);
        return Collections.emptyList();
      } 
    } 
    return (List<String>)method.invoke(null, new Object[0]);
  }
}
