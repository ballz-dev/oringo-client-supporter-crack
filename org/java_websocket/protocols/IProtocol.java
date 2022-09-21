package org.java_websocket.protocols;

public interface IProtocol {
  boolean acceptProvidedProtocol(String paramString);
  
  String toString();
  
  IProtocol copyInstance();
  
  String getProvidedProtocol();
}
