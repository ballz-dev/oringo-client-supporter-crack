package org.scijava.nativelib;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class BaseJniExtractor implements JniExtractor {
  private String[] nativeResourcePaths;
  
  private Class libraryJarClass;
  
  private static final Logger LOGGER = Logger.getLogger("org.scijava.nativelib.BaseJniExtractor");
  
  private static final String JAVA_TMPDIR = "java.io.tmpdir";
  
  public BaseJniExtractor() throws IOException {
    init(null);
  }
  
  public BaseJniExtractor(Class paramClass) throws IOException {
    init(paramClass);
  }
  
  private void init(Class paramClass) throws IOException {
    this.libraryJarClass = paramClass;
    String str = MxSysInfo.getMxSysInfo();
    if (str != null) {
      this.nativeResourcePaths = new String[] { "META-INF/lib/" + str + "/", "META-INF/lib/" };
    } else {
      this.nativeResourcePaths = new String[] { "META-INF/lib/" };
    } 
  }
  
  public File extractJni(String paramString1, String paramString2) throws IOException {
    String str = System.mapLibraryName(paramString2);
    LOGGER.log(Level.FINE, "mappedLib is " + str);
    URL uRL = null;
    if (null == this.libraryJarClass)
      this.libraryJarClass = getClass(); 
    uRL = this.libraryJarClass.getClassLoader().getResource(paramString1 + str);
    if (null == uRL && 
      str.endsWith(".jnilib")) {
      uRL = getClass().getClassLoader().getResource(paramString1 + str.substring(0, str.length() - 7) + ".dylib");
      if (null != uRL)
        str = str.substring(0, str.length() - 7) + ".dylib"; 
    } 
    if (null != uRL) {
      LOGGER.log(Level.FINE, "URL is " + uRL.toString());
      LOGGER.log(Level.FINE, "URL path is " + uRL.getPath());
      return extractResource(getJniDir(), uRL, str);
    } 
    LOGGER.log(Level.INFO, "Couldn't find resource " + paramString1 + " " + str);
    throw new IOException("Couldn't find resource " + paramString1 + " " + str);
  }
  
  public void extractRegistered() throws IOException {
    LOGGER.log(Level.FINE, "Extracting libraries registered in classloader " + getClass().getClassLoader());
    for (byte b = 0; b < this.nativeResourcePaths.length; b++) {
      Enumeration<URL> enumeration = getClass().getClassLoader().getResources(this.nativeResourcePaths[b] + "AUTOEXTRACT.LIST");
      while (enumeration.hasMoreElements()) {
        URL uRL = enumeration.nextElement();
        LOGGER.log(Level.FINE, "Extracting libraries listed in " + uRL);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(uRL.openStream(), "UTF-8"));
        String str;
        while ((str = bufferedReader.readLine()) != null) {
          URL uRL1 = null;
          for (byte b1 = 0; b1 < this.nativeResourcePaths.length; b1++) {
            uRL1 = getClass().getClassLoader().getResource(this.nativeResourcePaths[b1] + str);
            if (uRL1 != null)
              break; 
          } 
          if (uRL1 != null) {
            extractResource(getNativeDir(), uRL1, str);
            continue;
          } 
          throw new IOException("Couldn't find native library " + str + "on the classpath");
        } 
      } 
    } 
  }
  
  File extractResource(File paramFile, URL paramURL, String paramString) throws IOException {
    InputStream inputStream = paramURL.openStream();
    String str1 = paramString;
    String str2 = null;
    int i = paramString.lastIndexOf('.');
    if (-1 != i) {
      str1 = paramString.substring(0, i);
      str2 = paramString.substring(i);
    } 
    deleteLeftoverFiles(str1, str2);
    File file = File.createTempFile(str1, str2);
    LOGGER.log(Level.FINE, "Extracting '" + paramURL + "' to '" + file.getAbsolutePath() + "'");
    FileOutputStream fileOutputStream = new FileOutputStream(file);
    copy(inputStream, fileOutputStream);
    fileOutputStream.close();
    inputStream.close();
    file.deleteOnExit();
    return file;
  }
  
  void deleteLeftoverFiles(final String prefix, final String suffix) {
    File file = new File(System.getProperty("java.io.tmpdir"));
    File[] arrayOfFile = file.listFiles(new FilenameFilter() {
          public boolean accept(File param1File, String param1String) {
            return (param1String.startsWith(prefix) && param1String.endsWith(suffix));
          }
        });
    if (arrayOfFile == null)
      return; 
    for (File file1 : arrayOfFile) {
      try {
        file1.delete();
      } catch (SecurityException securityException) {}
    } 
  }
  
  static void copy(InputStream paramInputStream, OutputStream paramOutputStream) throws IOException {
    byte[] arrayOfByte = new byte[8192];
    int i = 0;
    while (true) {
      i = paramInputStream.read(arrayOfByte);
      if (i <= 0)
        break; 
      paramOutputStream.write(arrayOfByte, 0, i);
    } 
  }
  
  public abstract File getJniDir();
  
  public abstract File getNativeDir();
}
