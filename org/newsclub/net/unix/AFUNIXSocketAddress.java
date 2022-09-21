package org.newsclub.net.unix;

import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;

public class AFUNIXSocketAddress extends InetSocketAddress {
  private static final long serialVersionUID = 1L;
  
  private final String socketFile;
  
  public AFUNIXSocketAddress(File paramFile) throws IOException {
    this(paramFile, 0);
  }
  
  public AFUNIXSocketAddress(File paramFile, int paramInt) throws IOException {
    super(0);
    if (paramInt != 0)
      NativeUnixSocket.setPort1(this, paramInt); 
    this.socketFile = paramFile.getCanonicalPath();
  }
  
  public String getSocketFile() {
    return this.socketFile;
  }
  
  public String toString() {
    return getClass().getName() + "[host=" + getHostName() + ";port=" + getPort() + ";file=" + this.socketFile + "]";
  }
}
