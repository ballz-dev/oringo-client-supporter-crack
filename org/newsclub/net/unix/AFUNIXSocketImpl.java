package org.newsclub.net.unix;

import java.io.FileDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.SocketImpl;

class AFUNIXSocketImpl extends SocketImpl {
  private boolean closed = false;
  
  private boolean bound = false;
  
  private boolean connected = false;
  
  private boolean closedInputStream = false;
  
  private boolean closedOutputStream = false;
  
  private final AFUNIXInputStream in = new AFUNIXInputStream();
  
  private final AFUNIXOutputStream out = new AFUNIXOutputStream();
  
  private static final int SHUT_RD_WR = 2;
  
  private static final int SHUT_RD = 0;
  
  private String socketFile;
  
  private static final int SHUT_WR = 1;
  
  AFUNIXSocketImpl() {
    this.fd = new FileDescriptor();
  }
  
  FileDescriptor getFD() {
    return this.fd;
  }
  
  protected void accept(SocketImpl paramSocketImpl) throws IOException {
    AFUNIXSocketImpl aFUNIXSocketImpl = (AFUNIXSocketImpl)paramSocketImpl;
    NativeUnixSocket.accept(this.socketFile, this.fd, aFUNIXSocketImpl.fd);
    aFUNIXSocketImpl.socketFile = this.socketFile;
    aFUNIXSocketImpl.connected = true;
  }
  
  protected int available() throws IOException {
    return NativeUnixSocket.available(this.fd);
  }
  
  protected void bind(SocketAddress paramSocketAddress) throws IOException {
    bind(0, paramSocketAddress);
  }
  
  protected void bind(int paramInt, SocketAddress paramSocketAddress) throws IOException {
    if (!(paramSocketAddress instanceof AFUNIXSocketAddress))
      throw new SocketException("Cannot bind to this type of address: " + paramSocketAddress.getClass()); 
    AFUNIXSocketAddress aFUNIXSocketAddress = (AFUNIXSocketAddress)paramSocketAddress;
    this.socketFile = aFUNIXSocketAddress.getSocketFile();
    NativeUnixSocket.bind(this.socketFile, this.fd, paramInt);
    this.bound = true;
    this.localport = aFUNIXSocketAddress.getPort();
  }
  
  protected void bind(InetAddress paramInetAddress, int paramInt) throws IOException {
    throw new SocketException("Cannot bind to this type of address: " + InetAddress.class);
  }
  
  private void checkClose() throws IOException {
    if (!this.closedInputStream || this.closedOutputStream);
  }
  
  protected synchronized void close() throws IOException {
    if (this.closed)
      return; 
    this.closed = true;
    if (this.fd.valid()) {
      NativeUnixSocket.shutdown(this.fd, 2);
      NativeUnixSocket.close(this.fd);
    } 
    if (this.bound)
      NativeUnixSocket.unlink(this.socketFile); 
    this.connected = false;
  }
  
  protected void connect(String paramString, int paramInt) throws IOException {
    throw new SocketException("Cannot bind to this type of address: " + InetAddress.class);
  }
  
  protected void connect(InetAddress paramInetAddress, int paramInt) throws IOException {
    throw new SocketException("Cannot bind to this type of address: " + InetAddress.class);
  }
  
  protected void connect(SocketAddress paramSocketAddress, int paramInt) throws IOException {
    if (!(paramSocketAddress instanceof AFUNIXSocketAddress))
      throw new SocketException("Cannot bind to this type of address: " + paramSocketAddress.getClass()); 
    AFUNIXSocketAddress aFUNIXSocketAddress = (AFUNIXSocketAddress)paramSocketAddress;
    this.socketFile = aFUNIXSocketAddress.getSocketFile();
    NativeUnixSocket.connect(this.socketFile, this.fd);
    this.address = aFUNIXSocketAddress.getAddress();
    this.port = aFUNIXSocketAddress.getPort();
    this.localport = 0;
    this.connected = true;
  }
  
  protected void create(boolean paramBoolean) throws IOException {}
  
  protected InputStream getInputStream() throws IOException {
    if (!this.connected && !this.bound)
      throw new IOException("Not connected/not bound"); 
    return this.in;
  }
  
  protected OutputStream getOutputStream() throws IOException {
    if (!this.connected && !this.bound)
      throw new IOException("Not connected/not bound"); 
    return this.out;
  }
  
  protected void listen(int paramInt) throws IOException {
    NativeUnixSocket.listen(this.fd, paramInt);
  }
  
  protected void sendUrgentData(int paramInt) throws IOException {
    NativeUnixSocket.write(this.fd, new byte[] { (byte)(paramInt & 0xFF) }, 0, 1);
  }
  
  private final class AFUNIXInputStream extends InputStream {
    private boolean streamClosed = false;
    
    public int read(byte[] param1ArrayOfbyte, int param1Int1, int param1Int2) throws IOException {
      if (this.streamClosed)
        throw new IOException("This InputStream has already been closed."); 
      if (param1Int2 == 0)
        return 0; 
      int i = param1ArrayOfbyte.length - param1Int1;
      if (param1Int2 > i)
        param1Int2 = i; 
      try {
        return NativeUnixSocket.read(AFUNIXSocketImpl.this.fd, param1ArrayOfbyte, param1Int1, param1Int2);
      } catch (IOException iOException) {
        throw (IOException)(new IOException(iOException.getMessage() + " at " + AFUNIXSocketImpl.this
            .toString())).initCause(iOException);
      } 
    }
    
    public int read() throws IOException {
      byte[] arrayOfByte = new byte[1];
      int i = read(arrayOfByte, 0, 1);
      if (i <= 0)
        return -1; 
      return arrayOfByte[0] & 0xFF;
    }
    
    public void close() throws IOException {
      if (this.streamClosed)
        return; 
      this.streamClosed = true;
      if (AFUNIXSocketImpl.this.fd.valid())
        NativeUnixSocket.shutdown(AFUNIXSocketImpl.this.fd, 0); 
      AFUNIXSocketImpl.this.closedInputStream = true;
      AFUNIXSocketImpl.this.checkClose();
    }
    
    public int available() throws IOException {
      return NativeUnixSocket.available(AFUNIXSocketImpl.this.fd);
    }
    
    private AFUNIXInputStream() {}
  }
  
  private final class AFUNIXOutputStream extends OutputStream {
    private boolean streamClosed = false;
    
    public void write(int param1Int) throws IOException {
      byte[] arrayOfByte = { (byte)param1Int };
      write(arrayOfByte, 0, 1);
    }
    
    public void write(byte[] param1ArrayOfbyte, int param1Int1, int param1Int2) throws IOException {
      if (this.streamClosed)
        throw new AFUNIXSocketException("This OutputStream has already been closed."); 
      if (param1Int2 > param1ArrayOfbyte.length - param1Int1)
        throw new IndexOutOfBoundsException(); 
      try {
        while (param1Int2 > 0 && !Thread.interrupted()) {
          int i = NativeUnixSocket.write(AFUNIXSocketImpl.this.fd, param1ArrayOfbyte, param1Int1, param1Int2);
          if (i == -1)
            throw new IOException("Unspecific error while writing"); 
          param1Int2 -= i;
          param1Int1 += i;
        } 
      } catch (IOException iOException) {
        throw (IOException)(new IOException(iOException.getMessage() + " at " + AFUNIXSocketImpl.this
            .toString())).initCause(iOException);
      } 
    }
    
    public void close() throws IOException {
      if (this.streamClosed)
        return; 
      this.streamClosed = true;
      if (AFUNIXSocketImpl.this.fd.valid())
        NativeUnixSocket.shutdown(AFUNIXSocketImpl.this.fd, 1); 
      AFUNIXSocketImpl.this.closedOutputStream = true;
      AFUNIXSocketImpl.this.checkClose();
    }
    
    private AFUNIXOutputStream() {}
  }
  
  public String toString() {
    return super.toString() + "[fd=" + this.fd + "; file=" + this.socketFile + "; connected=" + this.connected + "; bound=" + this.bound + "]";
  }
  
  private static int expectInteger(Object paramObject) throws SocketException {
    try {
      return ((Integer)paramObject).intValue();
    } catch (ClassCastException classCastException) {
      throw new AFUNIXSocketException("Unsupported value: " + paramObject, classCastException);
    } catch (NullPointerException nullPointerException) {
      throw new AFUNIXSocketException("Value must not be null", nullPointerException);
    } 
  }
  
  private static int expectBoolean(Object paramObject) throws SocketException {
    try {
      return ((Boolean)paramObject).booleanValue() ? 1 : 0;
    } catch (ClassCastException classCastException) {
      throw new AFUNIXSocketException("Unsupported value: " + paramObject, classCastException);
    } catch (NullPointerException nullPointerException) {
      throw new AFUNIXSocketException("Value must not be null", nullPointerException);
    } 
  }
  
  public Object getOption(int paramInt) throws SocketException {
    try {
      switch (paramInt) {
        case 1:
        case 8:
          return Boolean.valueOf((NativeUnixSocket.getSocketOptionInt(this.fd, paramInt) != 0));
        case 128:
        case 4097:
        case 4098:
        case 4102:
          return Integer.valueOf(NativeUnixSocket.getSocketOptionInt(this.fd, paramInt));
      } 
      throw new AFUNIXSocketException("Unsupported option: " + paramInt);
    } catch (AFUNIXSocketException aFUNIXSocketException) {
      throw aFUNIXSocketException;
    } catch (Exception exception) {
      throw new AFUNIXSocketException("Error while getting option", exception);
    } 
  }
  
  public void setOption(int paramInt, Object paramObject) throws SocketException {
    try {
      switch (paramInt) {
        case 128:
          if (paramObject instanceof Boolean) {
            boolean bool = ((Boolean)paramObject).booleanValue();
            if (bool)
              throw new SocketException("Only accepting Boolean.FALSE here"); 
            NativeUnixSocket.setSocketOptionInt(this.fd, paramInt, -1);
            return;
          } 
          NativeUnixSocket.setSocketOptionInt(this.fd, paramInt, expectInteger(paramObject));
          return;
        case 4097:
        case 4098:
        case 4102:
          NativeUnixSocket.setSocketOptionInt(this.fd, paramInt, expectInteger(paramObject));
          return;
        case 1:
        case 8:
          NativeUnixSocket.setSocketOptionInt(this.fd, paramInt, expectBoolean(paramObject));
          return;
      } 
      throw new AFUNIXSocketException("Unsupported option: " + paramInt);
    } catch (AFUNIXSocketException aFUNIXSocketException) {
      throw aFUNIXSocketException;
    } catch (Exception exception) {
      throw new AFUNIXSocketException("Error while setting option", exception);
    } 
  }
  
  protected void shutdownInput() throws IOException {
    if (!this.closed && this.fd.valid())
      NativeUnixSocket.shutdown(this.fd, 0); 
  }
  
  protected void shutdownOutput() throws IOException {
    if (!this.closed && this.fd.valid())
      NativeUnixSocket.shutdown(this.fd, 1); 
  }
  
  static class Lenient extends AFUNIXSocketImpl {
    public void setOption(int param1Int, Object param1Object) throws SocketException {
      try {
        super.setOption(param1Int, param1Object);
      } catch (SocketException socketException) {
        switch (param1Int) {
          case 1:
            return;
        } 
        throw socketException;
      } 
    }
    
    public Object getOption(int param1Int) throws SocketException {
      try {
        return super.getOption(param1Int);
      } catch (SocketException socketException) {
        switch (param1Int) {
          case 1:
          case 8:
            return Boolean.valueOf(false);
        } 
        throw socketException;
      } 
    }
  }
}
