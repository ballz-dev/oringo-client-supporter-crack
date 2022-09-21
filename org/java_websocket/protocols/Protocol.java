package org.java_websocket.protocols;

import java.util.regex.Pattern;

public class Protocol implements IProtocol {
  private static final Pattern patternSpace = Pattern.compile(" ");
  
  private static final Pattern patternComma = Pattern.compile(",");
  
  private final String providedProtocol;
  
  public Protocol(String paramString) {
    if (paramString == null)
      throw new IllegalArgumentException(); 
    this.providedProtocol = paramString;
  }
  
  public boolean acceptProvidedProtocol(String paramString) {
    if ("".equals(this.providedProtocol))
      return true; 
    String str = patternSpace.matcher(paramString).replaceAll("");
    String[] arrayOfString = patternComma.split(str);
    for (String str1 : arrayOfString) {
      if (this.providedProtocol.equals(str1))
        return true; 
    } 
    return false;
  }
  
  public String getProvidedProtocol() {
    return this.providedProtocol;
  }
  
  public IProtocol copyInstance() {
    return new Protocol(getProvidedProtocol());
  }
  
  public String toString() {
    return getProvidedProtocol();
  }
  
  public boolean equals(Object paramObject) {
    if (this == paramObject)
      return true; 
    if (paramObject == null || getClass() != paramObject.getClass())
      return false; 
    Protocol protocol = (Protocol)paramObject;
    return this.providedProtocol.equals(protocol.providedProtocol);
  }
  
  public int hashCode() {
    return this.providedProtocol.hashCode();
  }
}
