package org.java_websocket.handshake;

public class HandshakeImpl1Server extends HandshakedataImpl1 implements ServerHandshakeBuilder {
  private String httpstatusmessage;
  
  private short httpstatus;
  
  public String getHttpStatusMessage() {
    return this.httpstatusmessage;
  }
  
  public short getHttpStatus() {
    return this.httpstatus;
  }
  
  public void setHttpStatusMessage(String paramString) {
    this.httpstatusmessage = paramString;
  }
  
  public void setHttpStatus(short paramShort) {
    this.httpstatus = paramShort;
  }
}
