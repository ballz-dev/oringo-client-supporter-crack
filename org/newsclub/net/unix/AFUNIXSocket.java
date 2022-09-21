package org.newsclub.net.unix;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketAddress;

public class AFUNIXSocket extends Socket {
  protected AFUNIXSocketImpl impl;
  
  AFUNIXSocketAddress addr;
  
  private AFUNIXSocket(AFUNIXSocketImpl paramAFUNIXSocketImpl) throws IOException {
    super(paramAFUNIXSocketImpl);
    try {
      NativeUnixSocket.setCreated(this);
    } catch (UnsatisfiedLinkError unsatisfiedLinkError) {
      unsatisfiedLinkError.printStackTrace();
    } 
  }
  
  public static AFUNIXSocket newInstance() throws IOException {
    AFUNIXSocketImpl.Lenient lenient = new AFUNIXSocketImpl.Lenient();
    AFUNIXSocket aFUNIXSocket = new AFUNIXSocket(lenient);
    aFUNIXSocket.impl = lenient;
    return aFUNIXSocket;
  }
  
  public static AFUNIXSocket newStrictInstance() throws IOException {
    AFUNIXSocketImpl aFUNIXSocketImpl = new AFUNIXSocketImpl();
    AFUNIXSocket aFUNIXSocket = new AFUNIXSocket(aFUNIXSocketImpl);
    aFUNIXSocket.impl = aFUNIXSocketImpl;
    return aFUNIXSocket;
  }
  
  public static AFUNIXSocket connectTo(AFUNIXSocketAddress paramAFUNIXSocketAddress) throws IOException {
    AFUNIXSocket aFUNIXSocket = newInstance();
    aFUNIXSocket.connect(paramAFUNIXSocketAddress);
    return aFUNIXSocket;
  }
  
  public void bind(SocketAddress paramSocketAddress) throws IOException {
    super.bind(paramSocketAddress);
    this.addr = (AFUNIXSocketAddress)paramSocketAddress;
  }
  
  public void connect(SocketAddress paramSocketAddress) throws IOException {
    connect(paramSocketAddress, 0);
  }
  
  public void connect(SocketAddress paramSocketAddress, int paramInt) throws IOException {
    if (!(paramSocketAddress instanceof AFUNIXSocketAddress))
      throw new IOException("Can only connect to endpoints of type " + AFUNIXSocketAddress.class
          .getName()); 
    this.impl.connect(paramSocketAddress, paramInt);
    this.addr = (AFUNIXSocketAddress)paramSocketAddress;
    NativeUnixSocket.setConnected(this);
  }
  
  public String toString() {
    if (isConnected())
      return "AFUNIXSocket[fd=" + this.impl.getFD() + ";path=" + this.addr.getSocketFile() + "]"; 
    return "AFUNIXSocket[unconnected]";
  }
  
  public static boolean isSupported() {
    return NativeUnixSocket.isLoaded();
  }
}
