package org.scijava.nativelib;

import java.io.File;
import java.io.IOException;

public class WebappJniExtractor extends BaseJniExtractor {
  private File jniSubDir;
  
  private File nativeDir;
  
  public WebappJniExtractor(String paramString) throws IOException {
    File file;
    this.nativeDir = new File(System.getProperty("java.library.tmpdir", "tmplib"));
    this.nativeDir.mkdirs();
    if (!this.nativeDir.isDirectory())
      throw new IOException("Unable to create native library working directory " + this.nativeDir); 
    long l = System.currentTimeMillis();
    byte b = 0;
    while (true) {
      file = new File(this.nativeDir, paramString + "." + l + "." + b);
      if (file.mkdir())
        break; 
      if (file.exists()) {
        b++;
        continue;
      } 
      throw new IOException("Unable to create native library working directory " + file);
    } 
    this.jniSubDir = file;
    this.jniSubDir.deleteOnExit();
  }
  
  protected void finalize() throws Throwable {
    File[] arrayOfFile = this.jniSubDir.listFiles();
    for (byte b = 0; b < arrayOfFile.length; b++)
      arrayOfFile[b].delete(); 
    this.jniSubDir.delete();
  }
  
  public File getJniDir() {
    return this.jniSubDir;
  }
  
  public File getNativeDir() {
    return this.nativeDir;
  }
}
