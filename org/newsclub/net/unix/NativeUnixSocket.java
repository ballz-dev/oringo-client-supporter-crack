package org.newsclub.net.unix;

import java.io.FileDescriptor;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.InetSocketAddress;

final class NativeUnixSocket {
  private static boolean loaded = false;
  
  static {
    try {
      Class.forName("org.newsclub.net.unix.NarSystem").getMethod("loadLibrary", new Class[0]).invoke(null, new Object[0]);
    } catch (ClassNotFoundException classNotFoundException) {
      throw new IllegalStateException("Could not find NarSystem class.\n\n*** ECLIPSE USERS ***\nIf you're running from within Eclipse, please try closing the \"junixsocket-native-common\" project\n", classNotFoundException);
    } catch (Exception exception) {
      throw new IllegalStateException(exception);
    } 
    loaded = true;
  }
  
  static boolean isLoaded() {
    return loaded;
  }
  
  static void checkSupported() {}
  
  static void setPort1(AFUNIXSocketAddress paramAFUNIXSocketAddress, int paramInt) throws AFUNIXSocketException {
    if (paramInt < 0)
      throw new IllegalArgumentException("port out of range:" + paramInt); 
    boolean bool = false;
    try {
      Field field = InetSocketAddress.class.getDeclaredField("holder");
      if (field != null) {
        field.setAccessible(true);
        Object object = field.get(paramAFUNIXSocketAddress);
        if (object != null) {
          Field field1 = object.getClass().getDeclaredField("port");
          if (field1 != null) {
            field1.setAccessible(true);
            field1.set(object, Integer.valueOf(paramInt));
            bool = true;
          } 
        } 
      } else {
        setPort(paramAFUNIXSocketAddress, paramInt);
      } 
    } catch (RuntimeException runtimeException) {
      throw runtimeException;
    } catch (Exception exception) {
      if (exception instanceof AFUNIXSocketException)
        throw (AFUNIXSocketException)exception; 
      throw new AFUNIXSocketException("Could not set port", exception);
    } 
    if (!bool)
      throw new AFUNIXSocketException("Could not set port"); 
  }
  
  static native int getSocketOptionInt(FileDescriptor paramFileDescriptor, int paramInt) throws IOException;
  
  static native void bind(String paramString, FileDescriptor paramFileDescriptor, int paramInt) throws IOException;
  
  static native void setPort(AFUNIXSocketAddress paramAFUNIXSocketAddress, int paramInt);
  
  static native void unlink(String paramString) throws IOException;
  
  static native void listen(FileDescriptor paramFileDescriptor, int paramInt) throws IOException;
  
  static native int available(FileDescriptor paramFileDescriptor) throws IOException;
  
  static native void initServerImpl(AFUNIXServerSocket paramAFUNIXServerSocket, AFUNIXSocketImpl paramAFUNIXSocketImpl);
  
  static native void accept(String paramString, FileDescriptor paramFileDescriptor1, FileDescriptor paramFileDescriptor2) throws IOException;
  
  static native int read(FileDescriptor paramFileDescriptor, byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws IOException;
  
  static native void setConnected(AFUNIXSocket paramAFUNIXSocket);
  
  static native int write(FileDescriptor paramFileDescriptor, byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws IOException;
  
  static native void shutdown(FileDescriptor paramFileDescriptor, int paramInt) throws IOException;
  
  static native void setBoundServer(AFUNIXServerSocket paramAFUNIXServerSocket);
  
  static native void setCreated(AFUNIXSocket paramAFUNIXSocket);
  
  static native void setCreatedServer(AFUNIXServerSocket paramAFUNIXServerSocket);
  
  static native void setSocketOptionInt(FileDescriptor paramFileDescriptor, int paramInt1, int paramInt2) throws IOException;
  
  static native void setBound(AFUNIXSocket paramAFUNIXSocket);
  
  static native void close(FileDescriptor paramFileDescriptor) throws IOException;
  
  static native void connect(String paramString, FileDescriptor paramFileDescriptor) throws IOException;
}
