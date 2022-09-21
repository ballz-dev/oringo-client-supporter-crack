package org.java_websocket;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.ByteChannel;
import org.java_websocket.enums.Role;

public class SocketChannelIOHelper {
  private SocketChannelIOHelper() {
    throw new IllegalStateException("Utility class");
  }
  
  public static boolean read(ByteBuffer paramByteBuffer, WebSocketImpl paramWebSocketImpl, ByteChannel paramByteChannel) throws IOException {
    paramByteBuffer.clear();
    int i = paramByteChannel.read(paramByteBuffer);
    paramByteBuffer.flip();
    if (i == -1) {
      paramWebSocketImpl.eot();
      return false;
    } 
    return (i != 0);
  }
  
  public static boolean readMore(ByteBuffer paramByteBuffer, WebSocketImpl paramWebSocketImpl, WrappedByteChannel paramWrappedByteChannel) throws IOException {
    paramByteBuffer.clear();
    int i = paramWrappedByteChannel.readMore(paramByteBuffer);
    paramByteBuffer.flip();
    if (i == -1) {
      paramWebSocketImpl.eot();
      return false;
    } 
    return paramWrappedByteChannel.isNeedRead();
  }
  
  public static boolean batch(WebSocketImpl paramWebSocketImpl, ByteChannel paramByteChannel) throws IOException {
    if (paramWebSocketImpl == null)
      return false; 
    ByteBuffer byteBuffer = paramWebSocketImpl.outQueue.peek();
    WrappedByteChannel wrappedByteChannel = null;
    if (byteBuffer == null) {
      if (paramByteChannel instanceof WrappedByteChannel) {
        wrappedByteChannel = (WrappedByteChannel)paramByteChannel;
        if (wrappedByteChannel.isNeedWrite())
          wrappedByteChannel.writeMore(); 
      } 
    } else {
      do {
        paramByteChannel.write(byteBuffer);
        if (byteBuffer.remaining() > 0)
          return false; 
        paramWebSocketImpl.outQueue.poll();
        byteBuffer = paramWebSocketImpl.outQueue.peek();
      } while (byteBuffer != null);
    } 
    if (paramWebSocketImpl.outQueue.isEmpty() && paramWebSocketImpl.isFlushAndClose() && paramWebSocketImpl.getDraft() != null && paramWebSocketImpl
      .getDraft().getRole() != null && paramWebSocketImpl.getDraft().getRole() == Role.SERVER)
      paramWebSocketImpl.closeConnection(); 
    return (wrappedByteChannel == null || !((WrappedByteChannel)paramByteChannel).isNeedWrite());
  }
}
