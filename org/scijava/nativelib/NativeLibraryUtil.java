package org.scijava.nativelib;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class NativeLibraryUtil {
  public enum Architecture {
    UNKNOWN, LINUX_64, WINDOWS_32, WINDOWS_64, OSX_64, LINUX_32, OSX_PPC, OSX_32;
    
    static {
      OSX_32 = new Architecture("OSX_32", 5);
      OSX_64 = new Architecture("OSX_64", 6);
      OSX_PPC = new Architecture("OSX_PPC", 7);
      $VALUES = new Architecture[] { UNKNOWN, LINUX_32, LINUX_64, WINDOWS_32, WINDOWS_64, OSX_32, OSX_64, OSX_PPC };
    }
  }
  
  private enum Processor {
    INTEL_64, PPC, UNKNOWN, INTEL_32;
    
    static {
    
    }
  }
  
  private static Architecture architecture = Architecture.UNKNOWN;
  
  private static final Logger LOGGER = Logger.getLogger("org.scijava.nativelib.NativeLibraryUtil");
  
  private static final String JAVA_TMPDIR = "java.io.tmpdir";
  
  private static final String DELIM = "/";
  
  public static Architecture getArchitecture() {
    if (Architecture.UNKNOWN == architecture) {
      Processor processor = getProcessor();
      if (Processor.UNKNOWN != processor) {
        String str = System.getProperty("os.name").toLowerCase();
        if (str.indexOf("nix") >= 0 || str.indexOf("nux") >= 0) {
          if (Processor.INTEL_32 == processor) {
            architecture = Architecture.LINUX_32;
          } else if (Processor.INTEL_64 == processor) {
            architecture = Architecture.LINUX_64;
          } 
        } else if (str.indexOf("win") >= 0) {
          if (Processor.INTEL_32 == processor) {
            architecture = Architecture.WINDOWS_32;
          } else if (Processor.INTEL_64 == processor) {
            architecture = Architecture.WINDOWS_64;
          } 
        } else if (str.indexOf("mac") >= 0) {
          if (Processor.INTEL_32 == processor) {
            architecture = Architecture.OSX_32;
          } else if (Processor.INTEL_64 == processor) {
            architecture = Architecture.OSX_64;
          } else if (Processor.PPC == processor) {
            architecture = Architecture.OSX_PPC;
          } 
        } 
      } 
    } 
    LOGGER.log(Level.FINE, "architecture is " + architecture + " os.name is " + System.getProperty("os.name").toLowerCase());
    return architecture;
  }
  
  private static Processor getProcessor() {
    Processor processor = Processor.UNKNOWN;
    String str = System.getProperty("os.arch").toLowerCase();
    if (str.indexOf("ppc") >= 0) {
      processor = Processor.PPC;
    } else if (str.indexOf("86") >= 0 || str.indexOf("amd") >= 0) {
      byte b = 32;
      if (str.indexOf("64") >= 0)
        b = 64; 
      processor = (32 == b) ? Processor.INTEL_32 : Processor.INTEL_64;
    } 
    LOGGER.log(Level.FINE, "processor is " + processor + " os.arch is " + System.getProperty("os.arch").toLowerCase());
    return processor;
  }
  
  public static String getPlatformLibraryPath() {
    String str = "META-INF/lib/";
    str = str + getArchitecture().name().toLowerCase() + "/";
    LOGGER.log(Level.FINE, "platform specific path is " + str);
    return str;
  }
  
  public static String getPlatformLibraryName(String paramString) {
    String str = null;
    switch (getArchitecture()) {
      case LINUX_32:
      case LINUX_64:
        str = paramString + ".so";
        break;
      case WINDOWS_32:
      case WINDOWS_64:
        str = paramString + ".dll";
        break;
      case OSX_32:
      case OSX_64:
        str = "lib" + paramString + ".dylib";
        break;
    } 
    LOGGER.log(Level.FINE, "native library name " + str);
    return str;
  }
  
  public static String getVersionedLibraryName(Class paramClass, String paramString) {
    String str = paramClass.getPackage().getImplementationVersion();
    if (null != str && str.length() > 0)
      paramString = paramString + "-" + str; 
    return paramString;
  }
  
  public static boolean loadVersionedNativeLibrary(Class paramClass, String paramString) {
    paramString = getVersionedLibraryName(paramClass, paramString);
    return loadNativeLibrary(paramClass, paramString);
  }
  
  public static boolean loadNativeLibrary(Class paramClass, String paramString) {
    boolean bool = false;
    if (Architecture.UNKNOWN == getArchitecture()) {
      LOGGER.log(Level.WARNING, "No native library available for this platform.");
    } else {
      try {
        String str = System.getProperty("java.io.tmpdir");
        DefaultJniExtractor defaultJniExtractor = new DefaultJniExtractor(paramClass, str);
        File file = defaultJniExtractor.extractJni(getPlatformLibraryPath(), paramString);
        System.load(file.getPath());
        bool = true;
      } catch (IOException iOException) {
        LOGGER.log(Level.WARNING, "IOException creating DefaultJniExtractor", iOException);
      } catch (SecurityException securityException) {
        LOGGER.log(Level.WARNING, "Can't load dynamic library", securityException);
      } catch (UnsatisfiedLinkError unsatisfiedLinkError) {
        LOGGER.log(Level.WARNING, "Problem with library", unsatisfiedLinkError);
      } 
    } 
    return bool;
  }
}
