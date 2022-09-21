package org.java_websocket.handshake;

import java.util.Iterator;

public interface Handshakedata {
  boolean hasFieldValue(String paramString);
  
  byte[] getContent();
  
  Iterator<String> iterateHttpFields();
  
  String getFieldValue(String paramString);
}
