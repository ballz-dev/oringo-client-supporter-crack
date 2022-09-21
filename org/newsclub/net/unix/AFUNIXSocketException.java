package org.newsclub.net.unix;

import java.net.SocketException;

public class AFUNIXSocketException extends SocketException {
  private final String socketFile;
  
  private static final long serialVersionUID = 1L;
  
  public AFUNIXSocketException(String paramString) {
    this(paramString, (String)null);
  }
  
  public AFUNIXSocketException(String paramString, Throwable paramThrowable) {
    this(paramString, (String)null);
    initCause(paramThrowable);
  }
  
  public AFUNIXSocketException(String paramString1, String paramString2) {
    super(paramString1);
    this.socketFile = paramString2;
  }
  
  public String toString() {
    if (this.socketFile == null)
      return super.toString(); 
    return super.toString() + " (socket: " + this.socketFile + ")";
  }
}
