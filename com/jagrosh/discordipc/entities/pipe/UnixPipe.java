package com.jagrosh.discordipc.entities.pipe;

import com.jagrosh.discordipc.IPCClient;
import com.jagrosh.discordipc.entities.Callback;
import com.jagrosh.discordipc.entities.Packet;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.util.HashMap;
import org.json.JSONException;
import org.json.JSONObject;
import org.newsclub.net.unix.AFUNIXSocket;
import org.newsclub.net.unix.AFUNIXSocketAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UnixPipe extends Pipe {
  private final AFUNIXSocket socket = AFUNIXSocket.newInstance();
  
  private static final Logger LOGGER = LoggerFactory.getLogger(UnixPipe.class);
  
  public Packet read() throws IOException, JSONException {
    InputStream inputStream = this.socket.getInputStream();
    while ((this.status == PipeStatus.CONNECTED || this.status == PipeStatus.CLOSING) && inputStream.available() == 0) {
      try {
        Thread.sleep(50L);
      } catch (InterruptedException interruptedException) {}
    } 
    if (this.status == PipeStatus.DISCONNECTED)
      throw new IOException("Disconnected!"); 
    if (this.status == PipeStatus.CLOSED)
      return new Packet(Packet.OpCode.CLOSE, null); 
    byte[] arrayOfByte = new byte[8];
    inputStream.read(arrayOfByte);
    ByteBuffer byteBuffer = ByteBuffer.wrap(arrayOfByte);
    Packet.OpCode opCode = Packet.OpCode.values()[Integer.reverseBytes(byteBuffer.getInt())];
    arrayOfByte = new byte[Integer.reverseBytes(byteBuffer.getInt())];
    inputStream.read(arrayOfByte);
    Packet packet = new Packet(opCode, new JSONObject(new String(arrayOfByte)));
    LOGGER.debug(String.format("Received packet: %s", new Object[] { packet.toString() }));
    if (this.listener != null)
      this.listener.onPacketReceived(this.ipcClient, packet); 
    return packet;
  }
  
  public void write(byte[] paramArrayOfbyte) throws IOException {
    this.socket.getOutputStream().write(paramArrayOfbyte);
  }
  
  public void close() throws IOException {
    LOGGER.debug("Closing IPC pipe...");
    this.status = PipeStatus.CLOSING;
    send(Packet.OpCode.CLOSE, new JSONObject(), null);
    this.status = PipeStatus.CLOSED;
    this.socket.close();
  }
  
  UnixPipe(IPCClient paramIPCClient, HashMap<String, Callback> paramHashMap, String paramString) throws IOException {
    super(paramIPCClient, paramHashMap);
    this.socket.connect((SocketAddress)new AFUNIXSocketAddress(new File(paramString)));
  }
}
