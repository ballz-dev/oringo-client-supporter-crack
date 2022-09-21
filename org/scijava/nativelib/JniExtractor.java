package org.scijava.nativelib;

import java.io.File;
import java.io.IOException;

public interface JniExtractor {
  void extractRegistered() throws IOException;
  
  File extractJni(String paramString1, String paramString2) throws IOException;
}
