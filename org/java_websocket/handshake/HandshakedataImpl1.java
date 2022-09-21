package org.java_websocket.handshake;

import java.util.Collections;
import java.util.Iterator;
import java.util.TreeMap;

public class HandshakedataImpl1 implements HandshakeBuilder {
  private byte[] content;
  
  private TreeMap<String, String> map = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
  
  public Iterator<String> iterateHttpFields() {
    return Collections.<String>unmodifiableSet(this.map.keySet()).iterator();
  }
  
  public String getFieldValue(String paramString) {
    String str = this.map.get(paramString);
    if (str == null)
      return ""; 
    return str;
  }
  
  public byte[] getContent() {
    return this.content;
  }
  
  public void setContent(byte[] paramArrayOfbyte) {
    this.content = paramArrayOfbyte;
  }
  
  public void put(String paramString1, String paramString2) {
    this.map.put(paramString1, paramString2);
  }
  
  public boolean hasFieldValue(String paramString) {
    return this.map.containsKey(paramString);
  }
}
