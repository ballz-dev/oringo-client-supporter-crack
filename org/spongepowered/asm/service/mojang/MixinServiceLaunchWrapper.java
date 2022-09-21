package org.spongepowered.asm.service.mojang;

import com.google.common.collect.ImmutableList;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import net.minecraft.launchwrapper.IClassNameTransformer;
import net.minecraft.launchwrapper.IClassTransformer;
import net.minecraft.launchwrapper.ITweaker;
import net.minecraft.launchwrapper.Launch;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.launch.GlobalProperties;
import org.spongepowered.asm.lib.ClassReader;
import org.spongepowered.asm.lib.ClassVisitor;
import org.spongepowered.asm.lib.tree.ClassNode;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.throwables.MixinException;
import org.spongepowered.asm.service.IClassBytecodeProvider;
import org.spongepowered.asm.service.IClassProvider;
import org.spongepowered.asm.service.ILegacyClassTransformer;
import org.spongepowered.asm.service.IMixinService;
import org.spongepowered.asm.service.ITransformer;
import org.spongepowered.asm.util.ReEntranceLock;
import org.spongepowered.asm.util.perf.Profiler;

public class MixinServiceLaunchWrapper implements IMixinService, IClassProvider, IClassBytecodeProvider {
  private static final Logger logger = LogManager.getLogger("mixin");
  
  private final LaunchClassLoaderUtil classLoaderUtil = new LaunchClassLoaderUtil(Launch.classLoader);
  
  private final ReEntranceLock lock = new ReEntranceLock(1);
  
  public static final String BLACKBOARD_KEY_TWEAKCLASSES = "TweakClasses";
  
  private static final String LAUNCH_PACKAGE = "org.spongepowered.asm.launch.";
  
  public static final String BLACKBOARD_KEY_TWEAKS = "Tweaks";
  
  private static final String STATE_TWEAKER = "org.spongepowered.asm.mixin.EnvironmentStateTweaker";
  
  private static final String TRANSFORMER_PROXY_CLASS = "org.spongepowered.asm.mixin.transformer.Proxy";
  
  private IClassNameTransformer nameTransformer;
  
  private static final String MIXIN_PACKAGE = "org.spongepowered.asm.mixin.";
  
  public String getName() {
    return "LaunchWrapper";
  }
  
  public boolean isValid() {
    try {
      Launch.classLoader.hashCode();
    } catch (Throwable throwable) {
      return false;
    } 
    return true;
  }
  
  public void prepare() {
    Launch.classLoader.addClassLoaderExclusion("org.spongepowered.asm.launch.");
  }
  
  public MixinEnvironment.Phase getInitialPhase() {
    if (findInStackTrace("net.minecraft.launchwrapper.Launch", "launch") > 132)
      return MixinEnvironment.Phase.DEFAULT; 
    return MixinEnvironment.Phase.PREINIT;
  }
  
  public void init() {
    if (findInStackTrace("net.minecraft.launchwrapper.Launch", "launch") < 4)
      logger.error("MixinBootstrap.doInit() called during a tweak constructor!"); 
    List<String> list = (List)GlobalProperties.get("TweakClasses");
    if (list != null)
      list.add("org.spongepowered.asm.mixin.EnvironmentStateTweaker"); 
  }
  
  public ReEntranceLock getReEntranceLock() {
    return this.lock;
  }
  
  public Collection<String> getPlatformAgents() {
    return (Collection<String>)ImmutableList.of("org.spongepowered.asm.launch.platform.MixinPlatformAgentFML");
  }
  
  public IClassProvider getClassProvider() {
    return this;
  }
  
  public IClassBytecodeProvider getBytecodeProvider() {
    return this;
  }
  
  public Class<?> findClass(String paramString) throws ClassNotFoundException {
    return Launch.classLoader.findClass(paramString);
  }
  
  public Class<?> findClass(String paramString, boolean paramBoolean) throws ClassNotFoundException {
    return Class.forName(paramString, paramBoolean, (ClassLoader)Launch.classLoader);
  }
  
  public Class<?> findAgentClass(String paramString, boolean paramBoolean) throws ClassNotFoundException {
    return Class.forName(paramString, paramBoolean, Launch.class.getClassLoader());
  }
  
  public void beginPhase() {
    Launch.classLoader.registerTransformer("org.spongepowered.asm.mixin.transformer.Proxy");
  }
  
  public void checkEnv(Object paramObject) {
    if (paramObject.getClass().getClassLoader() != Launch.class.getClassLoader())
      throw new MixinException("Attempted to init the mixin environment in the wrong classloader"); 
  }
  
  public InputStream getResourceAsStream(String paramString) {
    return Launch.classLoader.getResourceAsStream(paramString);
  }
  
  public void registerInvalidClass(String paramString) {
    this.classLoaderUtil.registerInvalidClass(paramString);
  }
  
  public boolean isClassLoaded(String paramString) {
    return this.classLoaderUtil.isClassLoaded(paramString);
  }
  
  public String getClassRestrictions(String paramString) {
    String str = "";
    if (this.classLoaderUtil.isClassClassLoaderExcluded(paramString, null))
      str = "PACKAGE_CLASSLOADER_EXCLUSION"; 
    if (this.classLoaderUtil.isClassTransformerExcluded(paramString, null))
      str = ((str.length() > 0) ? (str + ",") : "") + "PACKAGE_TRANSFORMER_EXCLUSION"; 
    return str;
  }
  
  public URL[] getClassPath() {
    return (URL[])Launch.classLoader.getSources().toArray((Object[])new URL[0]);
  }
  
  public Collection<ITransformer> getTransformers() {
    List list = Launch.classLoader.getTransformers();
    ArrayList<ITransformer> arrayList = new ArrayList(list.size());
    for (IClassTransformer iClassTransformer : list) {
      if (iClassTransformer instanceof ITransformer) {
        arrayList.add((ITransformer)iClassTransformer);
      } else {
        arrayList.add(new LegacyTransformerHandle(iClassTransformer));
      } 
      if (iClassTransformer instanceof IClassNameTransformer) {
        logger.debug("Found name transformer: {}", new Object[] { iClassTransformer.getClass().getName() });
        this.nameTransformer = (IClassNameTransformer)iClassTransformer;
      } 
    } 
    return arrayList;
  }
  
  public byte[] getClassBytes(String paramString1, String paramString2) throws IOException {
    byte[] arrayOfByte = Launch.classLoader.getClassBytes(paramString1);
    if (arrayOfByte != null)
      return arrayOfByte; 
    URLClassLoader uRLClassLoader = (URLClassLoader)Launch.class.getClassLoader();
    InputStream inputStream = null;
    try {
      String str = paramString2.replace('.', '/').concat(".class");
      inputStream = uRLClassLoader.getResourceAsStream(str);
      return IOUtils.toByteArray(inputStream);
    } catch (Exception exception) {
      return null;
    } finally {
      IOUtils.closeQuietly(inputStream);
    } 
  }
  
  public byte[] getClassBytes(String paramString, boolean paramBoolean) throws ClassNotFoundException, IOException {
    String str1 = paramString.replace('/', '.');
    String str2 = unmapClassName(str1);
    Profiler profiler = MixinEnvironment.getProfiler();
    Profiler.Section section = profiler.begin(1, "class.load");
    byte[] arrayOfByte = getClassBytes(str2, str1);
    section.end();
    if (paramBoolean) {
      Profiler.Section section1 = profiler.begin(1, "class.transform");
      arrayOfByte = applyTransformers(str2, str1, arrayOfByte, profiler);
      section1.end();
    } 
    if (arrayOfByte == null)
      throw new ClassNotFoundException(String.format("The specified class '%s' was not found", new Object[] { str1 })); 
    return arrayOfByte;
  }
  
  private byte[] applyTransformers(String paramString1, String paramString2, byte[] paramArrayOfbyte, Profiler paramProfiler) {
    if (this.classLoaderUtil.isClassExcluded(paramString1, paramString2))
      return paramArrayOfbyte; 
    MixinEnvironment mixinEnvironment = MixinEnvironment.getCurrentEnvironment();
    for (ILegacyClassTransformer iLegacyClassTransformer : mixinEnvironment.getTransformers()) {
      this.lock.clear();
      int i = iLegacyClassTransformer.getName().lastIndexOf('.');
      String str = iLegacyClassTransformer.getName().substring(i + 1);
      Profiler.Section section = paramProfiler.begin(2, str.toLowerCase());
      section.setInfo(iLegacyClassTransformer.getName());
      paramArrayOfbyte = iLegacyClassTransformer.transformClassBytes(paramString1, paramString2, paramArrayOfbyte);
      section.end();
      if (this.lock.isSet()) {
        mixinEnvironment.addTransformerExclusion(iLegacyClassTransformer.getName());
        this.lock.clear();
        logger.info("A re-entrant transformer '{}' was detected and will no longer process meta class data", new Object[] { iLegacyClassTransformer
              .getName() });
      } 
    } 
    return paramArrayOfbyte;
  }
  
  private String unmapClassName(String paramString) {
    if (this.nameTransformer == null)
      findNameTransformer(); 
    if (this.nameTransformer != null)
      return this.nameTransformer.unmapClassName(paramString); 
    return paramString;
  }
  
  private void findNameTransformer() {
    List list = Launch.classLoader.getTransformers();
    for (IClassTransformer iClassTransformer : list) {
      if (iClassTransformer instanceof IClassNameTransformer) {
        logger.debug("Found name transformer: {}", new Object[] { iClassTransformer.getClass().getName() });
        this.nameTransformer = (IClassNameTransformer)iClassTransformer;
      } 
    } 
  }
  
  public ClassNode getClassNode(String paramString) throws ClassNotFoundException, IOException {
    return getClassNode(getClassBytes(paramString, true), 0);
  }
  
  private ClassNode getClassNode(byte[] paramArrayOfbyte, int paramInt) {
    ClassNode classNode = new ClassNode();
    ClassReader classReader = new ClassReader(paramArrayOfbyte);
    classReader.accept((ClassVisitor)classNode, paramInt);
    return classNode;
  }
  
  public final String getSideName() {
    for (ITweaker iTweaker : GlobalProperties.get("Tweaks")) {
      if (iTweaker.getClass().getName().endsWith(".common.launcher.FMLServerTweaker"))
        return "SERVER"; 
      if (iTweaker.getClass().getName().endsWith(".common.launcher.FMLTweaker"))
        return "CLIENT"; 
    } 
    String str = getSideName("net.minecraftforge.fml.relauncher.FMLLaunchHandler", "side");
    if (str != null)
      return str; 
    str = getSideName("cpw.mods.fml.relauncher.FMLLaunchHandler", "side");
    if (str != null)
      return str; 
    str = getSideName("com.mumfrey.liteloader.launch.LiteLoaderTweaker", "getEnvironmentType");
    if (str != null)
      return str; 
    return "UNKNOWN";
  }
  
  private String getSideName(String paramString1, String paramString2) {
    try {
      Class<?> clazz = Class.forName(paramString1, false, (ClassLoader)Launch.classLoader);
      Method method = clazz.getDeclaredMethod(paramString2, new Class[0]);
      return ((Enum)method.invoke(null, new Object[0])).name();
    } catch (Exception exception) {
      return null;
    } 
  }
  
  private static int findInStackTrace(String paramString1, String paramString2) {
    Thread thread = Thread.currentThread();
    if (!"main".equals(thread.getName()))
      return 0; 
    StackTraceElement[] arrayOfStackTraceElement = thread.getStackTrace();
    for (StackTraceElement stackTraceElement : arrayOfStackTraceElement) {
      if (paramString1.equals(stackTraceElement.getClassName()) && paramString2.equals(stackTraceElement.getMethodName()))
        return stackTraceElement.getLineNumber(); 
    } 
    return 0;
  }
}
