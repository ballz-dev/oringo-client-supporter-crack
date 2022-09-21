package org.scijava.nativelib;

import java.io.IOException;

public class NativeLoader {
  private static JniExtractor jniExtractor = null;
  
  static {
    try {
      if (NativeLoader.class.getClassLoader() == ClassLoader.getSystemClassLoader()) {
        jniExtractor = new DefaultJniExtractor();
      } else {
        jniExtractor = new WebappJniExtractor("Classloader");
      } 
    } catch (IOException iOException) {
      throw new ExceptionInInitializerError(iOException);
    } 
  }
  
  public static void loadLibrary(String paramString) throws IOException {
    System.load(jniExtractor.extractJni("", paramString).getAbsolutePath());
  }
  
  public static void extractRegistered() throws IOException {
    jniExtractor.extractRegistered();
  }
  
  public static JniExtractor getJniExtractor() {
    return jniExtractor;
  }
  
  public static void setJniExtractor(JniExtractor paramJniExtractor) {
    jniExtractor = paramJniExtractor;
  }
}
