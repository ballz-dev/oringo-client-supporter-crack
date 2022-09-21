package org.newsclub.net.unix;

import java.io.File;
import java.net.URL;
import org.scijava.nativelib.DefaultJniExtractor;

public final class NarSystem {
  public static void loadLibrary() {
    String str1 = "junixsocket-native-2.0.4";
    String str2 = System.mapLibraryName("junixsocket-native-2.0.4");
    String[] arrayOfString = getAOLs();
    ClassLoader classLoader = NarSystem.class.getClassLoader();
    File file = getUnpackedLibPath(classLoader, arrayOfString, "junixsocket-native-2.0.4", str2);
    if (file != null) {
      System.load(file.getPath());
    } else {
      try {
        String str = getLibPath(classLoader, arrayOfString, str2);
        DefaultJniExtractor defaultJniExtractor = new DefaultJniExtractor(NarSystem.class, System.getProperty("java.io.tmpdir"));
        File file1 = defaultJniExtractor.extractJni(str, "junixsocket-native-2.0.4");
        System.load(file1.getPath());
      } catch (Exception exception) {
        exception.printStackTrace();
        throw (exception instanceof RuntimeException) ? (RuntimeException)exception : new RuntimeException(exception);
      } 
    } 
  }
  
  public static int runUnitTests() {
    return (new NarSystem()).runUnitTestsNative();
  }
  
  private static String[] getAOLs() {
    String str = System.getProperty("os.arch") + "-" + System.getProperty("os.name").replaceAll(" ", "");
    if (str.startsWith("i386-Linux"))
      return new String[] { "i386-Linux-ecpc", "i386-Linux-gpp", "i386-Linux-icc", "i386-Linux-ecc", "i386-Linux-icpc", "i386-Linux-linker", "i386-Linux-gcc" }; 
    if (str.startsWith("x86-Windows"))
      return new String[] { "x86-Windows-linker", "x86-Windows-gpp", "x86-Windows-msvc", "x86-Windows-icl", "x86-Windows-gcc" }; 
    if (str.startsWith("amd64-Linux"))
      return new String[] { "amd64-Linux-gpp", "amd64-Linux-icpc", "amd64-Linux-gcc", "amd64-Linux-linker" }; 
    if (str.startsWith("amd64-Windows"))
      return new String[] { "amd64-Windows-gpp", "amd64-Windows-msvc", "amd64-Windows-icl", "amd64-Windows-linker", "amd64-Windows-gcc" }; 
    if (str.startsWith("amd64-FreeBSD"))
      return new String[] { "amd64-FreeBSD-gpp", "amd64-FreeBSD-gcc", "amd64-FreeBSD-linker" }; 
    if (str.startsWith("ppc-MacOSX"))
      return new String[] { "ppc-MacOSX-gpp", "ppc-MacOSX-linker", "ppc-MacOSX-gcc" }; 
    if (str.startsWith("x86_64-MacOSX"))
      return new String[] { "x86_64-MacOSX-icc", "x86_64-MacOSX-icpc", "x86_64-MacOSX-gpp", "x86_64-MacOSX-linker", "x86_64-MacOSX-gcc" }; 
    if (str.startsWith("ppc-AIX"))
      return new String[] { "ppc-AIX-gpp", "ppc-AIX-xlC", "ppc-AIX-gcc", "ppc-AIX-linker" }; 
    if (str.startsWith("i386-FreeBSD"))
      return new String[] { "i386-FreeBSD-gpp", "i386-FreeBSD-gcc", "i386-FreeBSD-linker" }; 
    if (str.startsWith("sparc-SunOS"))
      return new String[] { "sparc-SunOS-cc", "sparc-SunOS-CC", "sparc-SunOS-linker" }; 
    if (str.startsWith("arm-Linux"))
      return new String[] { "arm-Linux-gpp", "arm-Linux-linker", "arm-Linux-gcc" }; 
    if (str.startsWith("x86-SunOS"))
      return new String[] { "x86-SunOS-g++", "x86-SunOS-linker" }; 
    if (str.startsWith("i386-MacOSX"))
      return new String[] { "i386-MacOSX-gpp", "i386-MacOSX-gcc", "i386-MacOSX-linker" }; 
    throw new RuntimeException("Unhandled architecture/OS: " + str);
  }
  
  private static File getUnpackedLibPath(ClassLoader paramClassLoader, String[] paramArrayOfString, String paramString1, String paramString2) {
    String str1 = NarSystem.class.getName().replace('.', '/') + ".class";
    URL uRL = paramClassLoader.getResource(str1);
    if (uRL == null || !"file".equals(uRL.getProtocol()))
      return null; 
    String str2 = uRL.getPath();
    String str3 = str2.substring(0, str2.length() - str1.length()) + "../nar/" + paramString1 + "-";
    for (String str : paramArrayOfString) {
      File file = new File(str3 + str + "-jni/lib/" + str + "/jni/" + paramString2);
      if (file.isFile())
        return file; 
    } 
    return null;
  }
  
  private static String getLibPath(ClassLoader paramClassLoader, String[] paramArrayOfString, String paramString) {
    for (String str1 : paramArrayOfString) {
      String str2 = "lib/" + str1 + "/jni/";
      if (paramClassLoader.getResource(str2 + paramString) != null)
        return str2; 
    } 
    throw new RuntimeException("Library '" + paramString + "' not found!");
  }
  
  public native int runUnitTestsNative();
}
