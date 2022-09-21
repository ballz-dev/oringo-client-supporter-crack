package org.java_websocket;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.ByteChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ExecutorService;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLEngineResult;
import javax.net.ssl.SSLException;
import org.java_websocket.interfaces.ISSLChannel;
import org.java_websocket.util.ByteBufferUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SSLSocketChannel implements WrappedByteChannel, ByteChannel, ISSLChannel {
  private final SocketChannel socketChannel;
  
  private ExecutorService executor;
  
  private final SSLEngine engine;
  
  private ByteBuffer myAppData;
  
  private ByteBuffer peerAppData;
  
  private ByteBuffer peerNetData;
  
  private ByteBuffer myNetData;
  
  private final Logger log = LoggerFactory.getLogger(SSLSocketChannel.class);
  
  public SSLSocketChannel(SocketChannel paramSocketChannel, SSLEngine paramSSLEngine, ExecutorService paramExecutorService, SelectionKey paramSelectionKey) throws IOException {
    if (paramSocketChannel == null || paramSSLEngine == null || this.executor == paramExecutorService)
      throw new IllegalArgumentException("parameter must not be null"); 
    this.socketChannel = paramSocketChannel;
    this.engine = paramSSLEngine;
    this.executor = paramExecutorService;
    this.myNetData = ByteBuffer.allocate(this.engine.getSession().getPacketBufferSize());
    this.peerNetData = ByteBuffer.allocate(this.engine.getSession().getPacketBufferSize());
    this.engine.beginHandshake();
    if (doHandshake()) {
      if (paramSelectionKey != null)
        paramSelectionKey.interestOps(paramSelectionKey.interestOps() | 0x4); 
    } else {
      try {
        this.socketChannel.close();
      } catch (IOException iOException) {
        this.log.error("Exception during the closing of the channel", iOException);
      } 
    } 
  }
  
  public synchronized int read(ByteBuffer paramByteBuffer) throws IOException {
    if (!paramByteBuffer.hasRemaining())
      return 0; 
    if (this.peerAppData.hasRemaining()) {
      this.peerAppData.flip();
      return ByteBufferUtils.transferByteBuffer(this.peerAppData, paramByteBuffer);
    } 
    this.peerNetData.compact();
    int i = this.socketChannel.read(this.peerNetData);
    if (i > 0 || this.peerNetData.hasRemaining()) {
      this.peerNetData.flip();
      if (this.peerNetData.hasRemaining()) {
        SSLEngineResult sSLEngineResult;
        this.peerAppData.compact();
        try {
          sSLEngineResult = this.engine.unwrap(this.peerNetData, this.peerAppData);
        } catch (SSLException sSLException) {
          this.log.error("SSLException during unwrap", sSLException);
          throw sSLException;
        } 
        switch (sSLEngineResult.getStatus()) {
          case FINISHED:
            this.peerAppData.flip();
            return ByteBufferUtils.transferByteBuffer(this.peerAppData, paramByteBuffer);
          case NEED_UNWRAP:
            this.peerAppData.flip();
            return ByteBufferUtils.transferByteBuffer(this.peerAppData, paramByteBuffer);
          case NEED_WRAP:
            this.peerAppData = enlargeApplicationBuffer(this.peerAppData);
            return read(paramByteBuffer);
          case NEED_TASK:
            closeConnection();
            paramByteBuffer.clear();
            return -1;
        } 
        throw new IllegalStateException("Invalid SSL status: " + sSLEngineResult.getStatus());
      } 
    } else if (i < 0) {
      handleEndOfStream();
    } 
    ByteBufferUtils.transferByteBuffer(this.peerAppData, paramByteBuffer);
    return i;
  }
  
  public synchronized int write(ByteBuffer paramByteBuffer) throws IOException {
    int i = 0;
    while (paramByteBuffer.hasRemaining()) {
      this.myNetData.clear();
      SSLEngineResult sSLEngineResult = this.engine.wrap(paramByteBuffer, this.myNetData);
      switch (sSLEngineResult.getStatus()) {
        case FINISHED:
          this.myNetData.flip();
          while (this.myNetData.hasRemaining())
            i += this.socketChannel.write(this.myNetData); 
          continue;
        case NEED_WRAP:
          this.myNetData = enlargePacketBuffer(this.myNetData);
          continue;
        case NEED_UNWRAP:
          throw new SSLException("Buffer underflow occurred after a wrap. I don't think we should ever get here.");
        case NEED_TASK:
          closeConnection();
          return 0;
      } 
      throw new IllegalStateException("Invalid SSL status: " + sSLEngineResult.getStatus());
    } 
    return i;
  }
  
  private boolean doHandshake() throws IOException {
    int i = this.engine.getSession().getApplicationBufferSize();
    this.myAppData = ByteBuffer.allocate(i);
    this.peerAppData = ByteBuffer.allocate(i);
    this.myNetData.clear();
    this.peerNetData.clear();
    SSLEngineResult.HandshakeStatus handshakeStatus = this.engine.getHandshakeStatus();
    boolean bool = false;
    while (!bool) {
      SSLEngineResult sSLEngineResult;
      Runnable runnable;
      switch (handshakeStatus) {
        case FINISHED:
          bool = !this.peerNetData.hasRemaining() ? true : false;
          if (bool)
            return true; 
          this.socketChannel.write(this.peerNetData);
          continue;
        case NEED_UNWRAP:
          if (this.socketChannel.read(this.peerNetData) < 0) {
            if (this.engine.isInboundDone() && this.engine.isOutboundDone())
              return false; 
            try {
              this.engine.closeInbound();
            } catch (SSLException sSLException) {}
            this.engine.closeOutbound();
            handshakeStatus = this.engine.getHandshakeStatus();
            continue;
          } 
          this.peerNetData.flip();
          try {
            sSLEngineResult = this.engine.unwrap(this.peerNetData, this.peerAppData);
            this.peerNetData.compact();
            handshakeStatus = sSLEngineResult.getHandshakeStatus();
          } catch (SSLException sSLException) {
            this.engine.closeOutbound();
            handshakeStatus = this.engine.getHandshakeStatus();
            continue;
          } 
          switch (sSLEngineResult.getStatus()) {
            case FINISHED:
              continue;
            case NEED_WRAP:
              this.peerAppData = enlargeApplicationBuffer(this.peerAppData);
              continue;
            case NEED_UNWRAP:
              this.peerNetData = handleBufferUnderflow(this.peerNetData);
              continue;
            case NEED_TASK:
              if (this.engine.isOutboundDone())
                return false; 
              this.engine.closeOutbound();
              handshakeStatus = this.engine.getHandshakeStatus();
              continue;
          } 
          throw new IllegalStateException("Invalid SSL status: " + sSLEngineResult.getStatus());
        case NEED_WRAP:
          this.myNetData.clear();
          try {
            sSLEngineResult = this.engine.wrap(this.myAppData, this.myNetData);
            handshakeStatus = sSLEngineResult.getHandshakeStatus();
          } catch (SSLException sSLException) {
            this.engine.closeOutbound();
            handshakeStatus = this.engine.getHandshakeStatus();
            continue;
          } 
          switch (sSLEngineResult.getStatus()) {
            case FINISHED:
              this.myNetData.flip();
              while (this.myNetData.hasRemaining())
                this.socketChannel.write(this.myNetData); 
              continue;
            case NEED_WRAP:
              this.myNetData = enlargePacketBuffer(this.myNetData);
              continue;
            case NEED_UNWRAP:
              throw new SSLException("Buffer underflow occurred after a wrap. I don't think we should ever get here.");
            case NEED_TASK:
              try {
                this.myNetData.flip();
                while (this.myNetData.hasRemaining())
                  this.socketChannel.write(this.myNetData); 
                this.peerNetData.clear();
              } catch (Exception exception) {
                handshakeStatus = this.engine.getHandshakeStatus();
              } 
              continue;
          } 
          throw new IllegalStateException("Invalid SSL status: " + sSLEngineResult.getStatus());
        case NEED_TASK:
          while ((runnable = this.engine.getDelegatedTask()) != null)
            this.executor.execute(runnable); 
          handshakeStatus = this.engine.getHandshakeStatus();
          continue;
        case NOT_HANDSHAKING:
          continue;
      } 
      throw new IllegalStateException("Invalid SSL status: " + handshakeStatus);
    } 
    return true;
  }
  
  private ByteBuffer enlargePacketBuffer(ByteBuffer paramByteBuffer) {
    return enlargeBuffer(paramByteBuffer, this.engine.getSession().getPacketBufferSize());
  }
  
  private ByteBuffer enlargeApplicationBuffer(ByteBuffer paramByteBuffer) {
    return enlargeBuffer(paramByteBuffer, this.engine.getSession().getApplicationBufferSize());
  }
  
  private ByteBuffer enlargeBuffer(ByteBuffer paramByteBuffer, int paramInt) {
    if (paramInt > paramByteBuffer.capacity()) {
      paramByteBuffer = ByteBuffer.allocate(paramInt);
    } else {
      paramByteBuffer = ByteBuffer.allocate(paramByteBuffer.capacity() * 2);
    } 
    return paramByteBuffer;
  }
  
  private ByteBuffer handleBufferUnderflow(ByteBuffer paramByteBuffer) {
    if (this.engine.getSession().getPacketBufferSize() < paramByteBuffer.limit())
      return paramByteBuffer; 
    ByteBuffer byteBuffer = enlargePacketBuffer(paramByteBuffer);
    paramByteBuffer.flip();
    byteBuffer.put(paramByteBuffer);
    return byteBuffer;
  }
  
  private void closeConnection() throws IOException {
    this.engine.closeOutbound();
    try {
      doHandshake();
    } catch (IOException iOException) {}
    this.socketChannel.close();
  }
  
  private void handleEndOfStream() throws IOException {
    try {
      this.engine.closeInbound();
    } catch (Exception exception) {
      this.log.error("This engine was forced to close inbound, without having received the proper SSL/TLS close notification message from the peer, due to end of stream.");
    } 
    closeConnection();
  }
  
  public boolean isNeedWrite() {
    return false;
  }
  
  public void writeMore() throws IOException {}
  
  public boolean isNeedRead() {
    return (this.peerNetData.hasRemaining() || this.peerAppData.hasRemaining());
  }
  
  public int readMore(ByteBuffer paramByteBuffer) throws IOException {
    return read(paramByteBuffer);
  }
  
  public boolean isBlocking() {
    return this.socketChannel.isBlocking();
  }
  
  public boolean isOpen() {
    return this.socketChannel.isOpen();
  }
  
  public void close() throws IOException {
    closeConnection();
  }
  
  public SSLEngine getSSLEngine() {
    return this.engine;
  }
}
