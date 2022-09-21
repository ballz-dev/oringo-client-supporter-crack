package org.spongepowered.asm.launch.platform;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

final class MainAttributes {
  private static final Map<URI, MainAttributes> instances = new HashMap<URI, MainAttributes>();
  
  protected final Attributes attributes;
  
  private MainAttributes() {
    this.attributes = new Attributes();
  }
  
  private MainAttributes(File paramFile) {
    this.attributes = getAttributes(paramFile);
  }
  
  public final String get(String paramString) {
    if (this.attributes != null)
      return this.attributes.getValue(paramString); 
    return null;
  }
  
  private static Attributes getAttributes(File paramFile) {
    if (paramFile == null)
      return null; 
    JarFile jarFile = null;
    try {
      jarFile = new JarFile(paramFile);
      Manifest manifest = jarFile.getManifest();
      if (manifest != null)
        return manifest.getMainAttributes(); 
    } catch (IOException iOException) {
      try {
        if (jarFile != null)
          jarFile.close(); 
      } catch (IOException iOException1) {}
    } finally {
      try {
        if (jarFile != null)
          jarFile.close(); 
      } catch (IOException iOException) {}
    } 
    return new Attributes();
  }
  
  public static MainAttributes of(File paramFile) {
    return of(paramFile.toURI());
  }
  
  public static MainAttributes of(URI paramURI) {
    MainAttributes mainAttributes = instances.get(paramURI);
    if (mainAttributes == null) {
      mainAttributes = new MainAttributes(new File(paramURI));
      instances.put(paramURI, mainAttributes);
    } 
    return mainAttributes;
  }
}
