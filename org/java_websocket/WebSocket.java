package org.java_websocket;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.util.Collection;
import javax.net.ssl.SSLSession;
import org.java_websocket.drafts.Draft;
import org.java_websocket.enums.Opcode;
import org.java_websocket.enums.ReadyState;
import org.java_websocket.framing.Framedata;
import org.java_websocket.protocols.IProtocol;

public interface WebSocket {
  SSLSession getSSLSession() throws IllegalArgumentException;
  
  void close();
  
  ReadyState getReadyState();
  
  IProtocol getProtocol();
  
  <T> T getAttachment();
  
  Draft getDraft();
  
  void closeConnection(int paramInt, String paramString);
  
  void close(int paramInt, String paramString);
  
  InetSocketAddress getRemoteSocketAddress();
  
  void send(byte[] paramArrayOfbyte);
  
  boolean hasSSLSupport();
  
  boolean isClosed();
  
  void sendPing();
  
  <T> void setAttachment(T paramT);
  
  InetSocketAddress getLocalSocketAddress();
  
  void send(String paramString);
  
  void sendFrame(Framedata paramFramedata);
  
  void sendFragmentedFrame(Opcode paramOpcode, ByteBuffer paramByteBuffer, boolean paramBoolean);
  
  String getResourceDescriptor();
  
  void sendFrame(Collection<Framedata> paramCollection);
  
  boolean isOpen();
  
  void close(int paramInt);
  
  boolean isFlushAndClose();
  
  boolean hasBufferedData();
  
  void send(ByteBuffer paramByteBuffer);
  
  boolean isClosing();
}
