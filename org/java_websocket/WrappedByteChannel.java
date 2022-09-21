package org.java_websocket;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.ByteChannel;

public interface WrappedByteChannel extends ByteChannel {
  boolean isNeedRead();
  
  int readMore(ByteBuffer paramByteBuffer) throws IOException;
  
  boolean isNeedWrite();
  
  boolean isBlocking();
  
  void writeMore() throws IOException;
}
