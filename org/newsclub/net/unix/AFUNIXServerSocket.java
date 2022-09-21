package org.newsclub.net.unix;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;

public class AFUNIXServerSocket extends ServerSocket {
  private AFUNIXSocketAddress boundEndpoint = null;
  
  private final AFUNIXSocketImpl implementation;
  
  private final Thread shutdownThread = new Thread() {
      public void run() {
        try {
          if (AFUNIXServerSocket.this.boundEndpoint != null)
            NativeUnixSocket.unlink(AFUNIXServerSocket.this.boundEndpoint.getSocketFile()); 
        } catch (IOException iOException) {}
      }
    };
  
  protected AFUNIXServerSocket() throws IOException {
    this.implementation = new AFUNIXSocketImpl();
    NativeUnixSocket.initServerImpl(this, this.implementation);
    Runtime.getRuntime().addShutdownHook(this.shutdownThread);
    NativeUnixSocket.setCreatedServer(this);
  }
  
  public static AFUNIXServerSocket newInstance() throws IOException {
    return new AFUNIXServerSocket();
  }
  
  public static AFUNIXServerSocket bindOn(AFUNIXSocketAddress paramAFUNIXSocketAddress) throws IOException {
    AFUNIXServerSocket aFUNIXServerSocket = newInstance();
    aFUNIXServerSocket.bind(paramAFUNIXSocketAddress);
    return aFUNIXServerSocket;
  }
  
  public void bind(SocketAddress paramSocketAddress, int paramInt) throws IOException {
    if (isClosed())
      throw new SocketException("Socket is closed"); 
    if (isBound())
      throw new SocketException("Already bound"); 
    if (!(paramSocketAddress instanceof AFUNIXSocketAddress))
      throw new IOException("Can only bind to endpoints of type " + AFUNIXSocketAddress.class
          .getName()); 
    this.implementation.bind(paramInt, paramSocketAddress);
    this.boundEndpoint = (AFUNIXSocketAddress)paramSocketAddress;
  }
  
  public boolean isBound() {
    return (this.boundEndpoint != null);
  }
  
  public Socket accept() throws IOException {
    if (isClosed())
      throw new SocketException("Socket is closed"); 
    AFUNIXSocket aFUNIXSocket = AFUNIXSocket.newInstance();
    this.implementation.accept(aFUNIXSocket.impl);
    aFUNIXSocket.addr = this.boundEndpoint;
    NativeUnixSocket.setConnected(aFUNIXSocket);
    return aFUNIXSocket;
  }
  
  public String toString() {
    if (!isBound())
      return "AFUNIXServerSocket[unbound]"; 
    return "AFUNIXServerSocket[" + this.boundEndpoint.getSocketFile() + "]";
  }
  
  public void close() throws IOException {
    if (isClosed())
      return; 
    super.close();
    this.implementation.close();
    if (this.boundEndpoint != null)
      NativeUnixSocket.unlink(this.boundEndpoint.getSocketFile()); 
    try {
      Runtime.getRuntime().removeShutdownHook(this.shutdownThread);
    } catch (IllegalStateException illegalStateException) {}
  }
  
  public static boolean isSupported() {
    return NativeUnixSocket.isLoaded();
  }
}
