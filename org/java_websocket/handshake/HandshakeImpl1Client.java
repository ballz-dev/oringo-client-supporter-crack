package org.java_websocket.handshake;

public class HandshakeImpl1Client extends HandshakedataImpl1 implements ClientHandshakeBuilder {
  private String resourceDescriptor = "*";
  
  public void setResourceDescriptor(String paramString) {
    if (paramString == null)
      throw new IllegalArgumentException("http resource descriptor must not be null"); 
    this.resourceDescriptor = paramString;
  }
  
  public String getResourceDescriptor() {
    return this.resourceDescriptor;
  }
}
