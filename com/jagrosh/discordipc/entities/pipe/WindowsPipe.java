package com.jagrosh.discordipc.entities.pipe;

import com.jagrosh.discordipc.IPCClient;
import com.jagrosh.discordipc.entities.Callback;
import com.jagrosh.discordipc.entities.Packet;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.HashMap;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WindowsPipe extends Pipe {
  private static final Logger LOGGER = LoggerFactory.getLogger(WindowsPipe.class);
  
  private final RandomAccessFile file;
  
  public void write(byte[] paramArrayOfbyte) throws IOException {
    this.file.write(paramArrayOfbyte);
  }
  
  public void close() throws IOException {
    LOGGER.debug("Closing IPC pipe...");
    this.status = PipeStatus.CLOSING;
    send(Packet.OpCode.CLOSE, new JSONObject(), null);
    this.status = PipeStatus.CLOSED;
    this.file.close();
  }
  
  WindowsPipe(IPCClient paramIPCClient, HashMap<String, Callback> paramHashMap, String paramString) {
    super(paramIPCClient, paramHashMap);
    try {
      this.file = new RandomAccessFile(paramString, "rw");
    } catch (FileNotFoundException fileNotFoundException) {
      throw new RuntimeException(fileNotFoundException);
    } 
  }
  
  public Packet read() throws IOException, JSONException {
    while ((this.status == PipeStatus.CONNECTED || this.status == PipeStatus.CLOSING) && this.file.length() == 0L) {
      try {
        Thread.sleep(50L);
      } catch (InterruptedException interruptedException) {}
    } 
    if (this.status == PipeStatus.DISCONNECTED)
      throw new IOException("Disconnected!"); 
    if (this.status == PipeStatus.CLOSED)
      return new Packet(Packet.OpCode.CLOSE, null); 
    Packet.OpCode opCode = Packet.OpCode.values()[Integer.reverseBytes(this.file.readInt())];
    int i = Integer.reverseBytes(this.file.readInt());
    byte[] arrayOfByte = new byte[i];
    this.file.readFully(arrayOfByte);
    Packet packet = new Packet(opCode, new JSONObject(new String(arrayOfByte)));
    LOGGER.debug(String.format("Received packet: %s", new Object[] { packet.toString() }));
    if (this.listener != null)
      this.listener.onPacketReceived(this.ipcClient, packet); 
    return packet;
  }
}
