package org.java_websocket;

import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ByteChannel;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLEngineResult;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSession;
import org.java_websocket.interfaces.ISSLChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SSLSocketChannel2 implements ByteChannel, WrappedByteChannel, ISSLChannel {
  protected ByteBuffer outCrypt;
  
  protected SSLEngineResult readEngineResult;
  
  protected ByteBuffer inData;
  
  protected SSLEngine sslEngine;
  
  private byte[] saveCryptData;
  
  protected ByteBuffer inCrypt;
  
  protected SSLEngineResult writeEngineResult;
  
  protected List<Future<?>> tasks;
  
  protected SocketChannel socketChannel;
  
  protected static ByteBuffer emptybuffer = ByteBuffer.allocate(0);
  
  private final Logger log = LoggerFactory.getLogger(SSLSocketChannel2.class);
  
  protected int bufferallocations = 0;
  
  protected ExecutorService exec;
  
  protected SelectionKey selectionKey;
  
  private void consumeFutureUninterruptible(Future<?> paramFuture) {
    try {
      while (true) {
        try {
          paramFuture.get();
          break;
        } catch (InterruptedException interruptedException) {
          Thread.currentThread().interrupt();
        } 
      } 
    } catch (ExecutionException executionException) {
      throw new RuntimeException(executionException);
    } 
  }
  
  private synchronized void processHandshake() throws IOException {
    if (this.sslEngine.getHandshakeStatus() == SSLEngineResult.HandshakeStatus.NOT_HANDSHAKING)
      return; 
    if (!this.tasks.isEmpty()) {
      Iterator<Future<?>> iterator = this.tasks.iterator();
      while (iterator.hasNext()) {
        Future<?> future = iterator.next();
        if (future.isDone()) {
          iterator.remove();
          continue;
        } 
        if (isBlocking())
          consumeFutureUninterruptible(future); 
        return;
      } 
    } 
    if (this.sslEngine.getHandshakeStatus() == SSLEngineResult.HandshakeStatus.NEED_UNWRAP) {
      if (!isBlocking() || this.readEngineResult.getStatus() == SSLEngineResult.Status.BUFFER_UNDERFLOW) {
        this.inCrypt.compact();
        int i = this.socketChannel.read(this.inCrypt);
        if (i == -1)
          throw new IOException("connection closed unexpectedly by peer"); 
        this.inCrypt.flip();
      } 
      this.inData.compact();
      unwrap();
      if (this.readEngineResult.getHandshakeStatus() == SSLEngineResult.HandshakeStatus.FINISHED) {
        createBuffers(this.sslEngine.getSession());
        return;
      } 
    } 
    consumeDelegatedTasks();
    if (this.tasks.isEmpty() || this.sslEngine
      .getHandshakeStatus() == SSLEngineResult.HandshakeStatus.NEED_WRAP) {
      this.socketChannel.write(wrap(emptybuffer));
      if (this.writeEngineResult.getHandshakeStatus() == SSLEngineResult.HandshakeStatus.FINISHED) {
        createBuffers(this.sslEngine.getSession());
        return;
      } 
    } 
    assert this.sslEngine.getHandshakeStatus() != SSLEngineResult.HandshakeStatus.NOT_HANDSHAKING;
    this.bufferallocations = 1;
  }
  
  private synchronized ByteBuffer wrap(ByteBuffer paramByteBuffer) throws SSLException {
    this.outCrypt.compact();
    this.writeEngineResult = this.sslEngine.wrap(paramByteBuffer, this.outCrypt);
    this.outCrypt.flip();
    return this.outCrypt;
  }
  
  private synchronized ByteBuffer unwrap() throws SSLException {
    int i;
    if (this.readEngineResult.getStatus() == SSLEngineResult.Status.CLOSED && this.sslEngine
      .getHandshakeStatus() == SSLEngineResult.HandshakeStatus.NOT_HANDSHAKING)
      try {
        close();
      } catch (IOException iOException) {} 
    do {
      i = this.inData.remaining();
      this.readEngineResult = this.sslEngine.unwrap(this.inCrypt, this.inData);
    } while (this.readEngineResult.getStatus() == SSLEngineResult.Status.OK && (i != this.inData.remaining() || this.sslEngine
      .getHandshakeStatus() == SSLEngineResult.HandshakeStatus.NEED_UNWRAP));
    this.inData.flip();
    return this.inData;
  }
  
  protected void consumeDelegatedTasks() {
    Runnable runnable;
    while ((runnable = this.sslEngine.getDelegatedTask()) != null)
      this.tasks.add(this.exec.submit(runnable)); 
  }
  
  protected void createBuffers(SSLSession paramSSLSession) {
    saveCryptedData();
    int i = paramSSLSession.getPacketBufferSize();
    int j = Math.max(paramSSLSession.getApplicationBufferSize(), i);
    if (this.inData == null) {
      this.inData = ByteBuffer.allocate(j);
      this.outCrypt = ByteBuffer.allocate(i);
      this.inCrypt = ByteBuffer.allocate(i);
    } else {
      if (this.inData.capacity() != j)
        this.inData = ByteBuffer.allocate(j); 
      if (this.outCrypt.capacity() != i)
        this.outCrypt = ByteBuffer.allocate(i); 
      if (this.inCrypt.capacity() != i)
        this.inCrypt = ByteBuffer.allocate(i); 
    } 
    if (this.inData.remaining() != 0 && this.log.isTraceEnabled())
      this.log.trace(new String(this.inData.array(), this.inData.position(), this.inData.remaining())); 
    this.inData.rewind();
    this.inData.flip();
    if (this.inCrypt.remaining() != 0 && this.log.isTraceEnabled())
      this.log.trace(new String(this.inCrypt.array(), this.inCrypt.position(), this.inCrypt.remaining())); 
    this.inCrypt.rewind();
    this.inCrypt.flip();
    this.outCrypt.rewind();
    this.outCrypt.flip();
    this.bufferallocations++;
  }
  
  public int write(ByteBuffer paramByteBuffer) throws IOException {
    if (!isHandShakeComplete()) {
      processHandshake();
      return 0;
    } 
    int i = this.socketChannel.write(wrap(paramByteBuffer));
    if (this.writeEngineResult.getStatus() == SSLEngineResult.Status.CLOSED)
      throw new EOFException("Connection is closed"); 
    return i;
  }
  
  public int read(ByteBuffer paramByteBuffer) throws IOException {
    int i;
    tryRestoreCryptedData();
    while (true) {
      if (!paramByteBuffer.hasRemaining())
        return 0; 
      if (!isHandShakeComplete())
        if (isBlocking()) {
          while (!isHandShakeComplete())
            processHandshake(); 
        } else {
          processHandshake();
          if (!isHandShakeComplete())
            return 0; 
        }  
      int j = readRemaining(paramByteBuffer);
      if (j != 0)
        return j; 
      assert this.inData.position() == 0;
      this.inData.clear();
      if (!this.inCrypt.hasRemaining()) {
        this.inCrypt.clear();
      } else {
        this.inCrypt.compact();
      } 
      if ((isBlocking() || this.readEngineResult.getStatus() == SSLEngineResult.Status.BUFFER_UNDERFLOW) && 
        this.socketChannel.read(this.inCrypt) == -1)
        return -1; 
      this.inCrypt.flip();
      unwrap();
      i = transfereTo(this.inData, paramByteBuffer);
      if (i == 0 && isBlocking())
        continue; 
      break;
    } 
    return i;
  }
  
  private int readRemaining(ByteBuffer paramByteBuffer) throws SSLException {
    if (this.inData.hasRemaining())
      return transfereTo(this.inData, paramByteBuffer); 
    if (!this.inData.hasRemaining())
      this.inData.clear(); 
    tryRestoreCryptedData();
    if (this.inCrypt.hasRemaining()) {
      unwrap();
      int i = transfereTo(this.inData, paramByteBuffer);
      if (this.readEngineResult.getStatus() == SSLEngineResult.Status.CLOSED)
        return -1; 
      if (i > 0)
        return i; 
    } 
    return 0;
  }
  
  public boolean isConnected() {
    return this.socketChannel.isConnected();
  }
  
  public void close() throws IOException {
    this.sslEngine.closeOutbound();
    this.sslEngine.getSession().invalidate();
    try {
      if (this.socketChannel.isOpen())
        this.socketChannel.write(wrap(emptybuffer)); 
    } finally {
      this.socketChannel.close();
    } 
  }
  
  private boolean isHandShakeComplete() {
    SSLEngineResult.HandshakeStatus handshakeStatus = this.sslEngine.getHandshakeStatus();
    return (handshakeStatus == SSLEngineResult.HandshakeStatus.FINISHED || handshakeStatus == SSLEngineResult.HandshakeStatus.NOT_HANDSHAKING);
  }
  
  public SelectableChannel configureBlocking(boolean paramBoolean) throws IOException {
    return this.socketChannel.configureBlocking(paramBoolean);
  }
  
  public boolean connect(SocketAddress paramSocketAddress) throws IOException {
    return this.socketChannel.connect(paramSocketAddress);
  }
  
  public boolean finishConnect() throws IOException {
    return this.socketChannel.finishConnect();
  }
  
  public Socket socket() {
    return this.socketChannel.socket();
  }
  
  public boolean isInboundDone() {
    return this.sslEngine.isInboundDone();
  }
  
  public boolean isOpen() {
    return this.socketChannel.isOpen();
  }
  
  public boolean isNeedWrite() {
    return (this.outCrypt.hasRemaining() || 
      !isHandShakeComplete());
  }
  
  public void writeMore() throws IOException {
    write(this.outCrypt);
  }
  
  public boolean isNeedRead() {
    return (this.saveCryptData != null || this.inData.hasRemaining() || (this.inCrypt.hasRemaining() && this.readEngineResult
      .getStatus() != SSLEngineResult.Status.BUFFER_UNDERFLOW && this.readEngineResult
      .getStatus() != SSLEngineResult.Status.CLOSED));
  }
  
  public int readMore(ByteBuffer paramByteBuffer) throws SSLException {
    return readRemaining(paramByteBuffer);
  }
  
  private int transfereTo(ByteBuffer paramByteBuffer1, ByteBuffer paramByteBuffer2) {
    int i = paramByteBuffer1.remaining();
    int j = paramByteBuffer2.remaining();
    if (i > j) {
      int k = Math.min(i, j);
      for (byte b = 0; b < k; b++)
        paramByteBuffer2.put(paramByteBuffer1.get()); 
      return k;
    } 
    paramByteBuffer2.put(paramByteBuffer1);
    return i;
  }
  
  public boolean isBlocking() {
    return this.socketChannel.isBlocking();
  }
  
  public SSLEngine getSSLEngine() {
    return this.sslEngine;
  }
  
  public SSLSocketChannel2(SocketChannel paramSocketChannel, SSLEngine paramSSLEngine, ExecutorService paramExecutorService, SelectionKey paramSelectionKey) throws IOException {
    this.saveCryptData = null;
    if (paramSocketChannel == null || paramSSLEngine == null || paramExecutorService == null)
      throw new IllegalArgumentException("parameter must not be null"); 
    this.socketChannel = paramSocketChannel;
    this.sslEngine = paramSSLEngine;
    this.exec = paramExecutorService;
    this.readEngineResult = this.writeEngineResult = new SSLEngineResult(SSLEngineResult.Status.BUFFER_UNDERFLOW, paramSSLEngine.getHandshakeStatus(), 0, 0);
    this.tasks = new ArrayList<>(3);
    if (paramSelectionKey != null) {
      paramSelectionKey.interestOps(paramSelectionKey.interestOps() | 0x4);
      this.selectionKey = paramSelectionKey;
    } 
    createBuffers(paramSSLEngine.getSession());
    this.socketChannel.write(wrap(emptybuffer));
    processHandshake();
  }
  
  private void saveCryptedData() {
    if (this.inCrypt != null && this.inCrypt.remaining() > 0) {
      int i = this.inCrypt.remaining();
      this.saveCryptData = new byte[i];
      this.inCrypt.get(this.saveCryptData);
    } 
  }
  
  private void tryRestoreCryptedData() {
    if (this.saveCryptData != null) {
      this.inCrypt.clear();
      this.inCrypt.put(this.saveCryptData);
      this.inCrypt.flip();
      this.saveCryptData = null;
    } 
  }
}
