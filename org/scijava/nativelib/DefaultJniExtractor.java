package org.scijava.nativelib;

import java.io.File;
import java.io.IOException;

public class DefaultJniExtractor extends BaseJniExtractor {
  private File nativeDir;
  
  public DefaultJniExtractor() throws IOException {
    super(null);
    init("tmplib");
  }
  
  public DefaultJniExtractor(Class paramClass, String paramString) throws IOException {
    super(paramClass);
    init(paramString);
  }
  
  void init(String paramString) throws IOException {
    this.nativeDir = new File(System.getProperty("java.library.tmpdir", paramString));
    this.nativeDir.mkdirs();
    if (!this.nativeDir.isDirectory())
      throw new IOException("Unable to create native library working directory " + this.nativeDir); 
  }
  
  public File getJniDir() {
    return this.nativeDir;
  }
  
  public File getNativeDir() {
    return this.nativeDir;
  }
}
