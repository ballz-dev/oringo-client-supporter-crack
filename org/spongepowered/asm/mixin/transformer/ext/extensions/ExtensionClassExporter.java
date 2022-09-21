package org.spongepowered.asm.mixin.transformer.ext.extensions;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.regex.Pattern;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.transformer.ext.IDecompiler;
import org.spongepowered.asm.mixin.transformer.ext.IExtension;
import org.spongepowered.asm.mixin.transformer.ext.ITargetClassContext;
import org.spongepowered.asm.util.Constants;
import org.spongepowered.asm.util.perf.Profiler;

public class ExtensionClassExporter implements IExtension {
  private static final String EXPORT_CLASS_DIR = "class";
  
  private static final String DECOMPILER_CLASS = "org.spongepowered.asm.mixin.transformer.debug.RuntimeDecompiler";
  
  private static final Logger logger = LogManager.getLogger("mixin");
  
  private final File classExportDir = new File(Constants.DEBUG_OUTPUT_DIR, "class");
  
  private static final String EXPORT_JAVA_DIR = "java";
  
  private final IDecompiler decompiler;
  
  public ExtensionClassExporter(MixinEnvironment paramMixinEnvironment) {
    this.decompiler = initDecompiler(paramMixinEnvironment, new File(Constants.DEBUG_OUTPUT_DIR, "java"));
    try {
      FileUtils.deleteDirectory(this.classExportDir);
    } catch (IOException iOException) {
      logger.warn("Error cleaning class output directory: {}", new Object[] { iOException.getMessage() });
    } 
  }
  
  public boolean isDecompilerActive() {
    return (this.decompiler != null);
  }
  
  private IDecompiler initDecompiler(MixinEnvironment paramMixinEnvironment, File paramFile) {
    if (!paramMixinEnvironment.getOption(MixinEnvironment.Option.DEBUG_EXPORT_DECOMPILE))
      return null; 
    try {
      boolean bool = paramMixinEnvironment.getOption(MixinEnvironment.Option.DEBUG_EXPORT_DECOMPILE_THREADED);
      logger.info("Attempting to load Fernflower decompiler{}", new Object[] { bool ? " (Threaded mode)" : "" });
      String str = "org.spongepowered.asm.mixin.transformer.debug.RuntimeDecompiler" + (bool ? "Async" : "");
      Class<?> clazz = Class.forName(str);
      Constructor<?> constructor = clazz.getDeclaredConstructor(new Class[] { File.class });
      IDecompiler iDecompiler = (IDecompiler)constructor.newInstance(new Object[] { paramFile });
      logger.info("Fernflower decompiler was successfully initialised, exported classes will be decompiled{}", new Object[] { bool ? " in a separate thread" : "" });
      return iDecompiler;
    } catch (Throwable throwable) {
      logger.info("Fernflower could not be loaded, exported classes will not be decompiled. {}: {}", new Object[] { throwable
            .getClass().getSimpleName(), throwable.getMessage() });
      return null;
    } 
  }
  
  private String prepareFilter(String paramString) {
    paramString = "^\\Q" + paramString.replace("**", "").replace("*", "").replace("?", "") + "\\E$";
    return paramString.replace("", "\\E.*\\Q").replace("", "\\E[^\\.]+\\Q").replace("", "\\E.\\Q").replace("\\Q\\E", "");
  }
  
  private boolean applyFilter(String paramString1, String paramString2) {
    return Pattern.compile(prepareFilter(paramString1), 2).matcher(paramString2).matches();
  }
  
  public boolean checkActive(MixinEnvironment paramMixinEnvironment) {
    return true;
  }
  
  public void preApply(ITargetClassContext paramITargetClassContext) {}
  
  public void postApply(ITargetClassContext paramITargetClassContext) {}
  
  public void export(MixinEnvironment paramMixinEnvironment, String paramString, boolean paramBoolean, byte[] paramArrayOfbyte) {
    if (paramBoolean || paramMixinEnvironment.getOption(MixinEnvironment.Option.DEBUG_EXPORT)) {
      String str = paramMixinEnvironment.getOptionValue(MixinEnvironment.Option.DEBUG_EXPORT_FILTER);
      if (paramBoolean || str == null || applyFilter(str, paramString)) {
        Profiler.Section section = MixinEnvironment.getProfiler().begin("debug.export");
        File file = dumpClass(paramString.replace('.', '/'), paramArrayOfbyte);
        if (this.decompiler != null)
          this.decompiler.decompile(file); 
        section.end();
      } 
    } 
  }
  
  public File dumpClass(String paramString, byte[] paramArrayOfbyte) {
    File file = new File(this.classExportDir, paramString + ".class");
    try {
      FileUtils.writeByteArrayToFile(file, paramArrayOfbyte);
    } catch (IOException iOException) {}
    return file;
  }
}
