package com.jagrosh.discordipc.entities.pipe;

import com.jagrosh.discordipc.IPCClient;
import com.jagrosh.discordipc.IPCListener;
import com.jagrosh.discordipc.entities.Callback;
import com.jagrosh.discordipc.entities.DiscordBuild;
import com.jagrosh.discordipc.entities.Packet;
import com.jagrosh.discordipc.exceptions.NoDiscordClientException;
import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class Pipe {
  private static final String[] unixPaths;
  
  private static final Logger LOGGER;
  
  PipeStatus status = PipeStatus.CONNECTING;
  
  final IPCClient ipcClient;
  
  private final HashMap<String, Callback> callbacks;
  
  private DiscordBuild build;
  
  private static final int VERSION = 1;
  
  IPCListener listener;
  
  private static String getPipeLocation(int paramInt) {
    if (System.getProperty("os.name").contains("Win"))
      return String.valueOf((new StringBuilder()).append("\\\\?\\pipe\\discord-ipc-").append(paramInt)); 
    String str = null;
    for (String str1 : unixPaths) {
      str = System.getenv(str1);
      if (str != null)
        break; 
    } 
    if (str == null)
      str = "/tmp"; 
    return String.valueOf((new StringBuilder()).append(str).append("/discord-ipc-").append(paramInt));
  }
  
  Pipe(IPCClient paramIPCClient, HashMap<String, Callback> paramHashMap) {
    this.ipcClient = paramIPCClient;
    this.callbacks = paramHashMap;
  }
  
  static {
    LOGGER = LoggerFactory.getLogger(Pipe.class);
    unixPaths = new String[] { "XDG_RUNTIME_DIR", "TMPDIR", "TMP", "TEMP" };
  }
  
  public void send(Packet.OpCode paramOpCode, JSONObject paramJSONObject, Callback paramCallback) {
    try {
      String str = generateNonce();
      Packet packet = new Packet(paramOpCode, paramJSONObject.put("nonce", str));
      if (paramCallback != null && !paramCallback.isEmpty())
        this.callbacks.put(str, paramCallback); 
      write(packet.toBytes());
      LOGGER.debug(String.format("Sent packet: %s", new Object[] { packet.toString() }));
      if (this.listener != null)
        this.listener.onPacketSent(this.ipcClient, packet); 
    } catch (IOException iOException) {
      LOGGER.error("Encountered an IOException while sending a packet and disconnected!");
      this.status = PipeStatus.DISCONNECTED;
    } 
  }
  
  public abstract void close() throws IOException;
  
  public abstract Packet read() throws IOException, JSONException;
  
  public void setStatus(PipeStatus paramPipeStatus) {
    this.status = paramPipeStatus;
  }
  
  public PipeStatus getStatus() {
    return this.status;
  }
  
  public static Pipe openPipe(IPCClient paramIPCClient, long paramLong, HashMap<String, Callback> paramHashMap, DiscordBuild... paramVarArgs) throws NoDiscordClientException {
    if (paramVarArgs == null || paramVarArgs.length == 0)
      paramVarArgs = new DiscordBuild[] { DiscordBuild.ANY }; 
    Pipe pipe = null;
    Pipe[] arrayOfPipe = new Pipe[(DiscordBuild.values()).length];
    byte b;
    for (b = 0; b < 10; b++) {
      try {
        String str = getPipeLocation(b);
        LOGGER.debug(String.format("Searching for IPC: %s", new Object[] { str }));
        pipe = createPipe(paramIPCClient, paramHashMap, str);
        pipe.send(Packet.OpCode.HANDSHAKE, (new JSONObject()).put("v", 1).put("client_id", Long.toString(paramLong)), null);
        Packet packet = pipe.read();
        pipe.build = DiscordBuild.from(packet.getJson().getJSONObject("data").getJSONObject("config").getString("api_endpoint"));
        LOGGER.debug(String.format("Found a valid client (%s) with packet: %s", new Object[] { pipe.build.name(), packet.toString() }));
        if (pipe.build == paramVarArgs[0] || DiscordBuild.ANY == paramVarArgs[0]) {
          LOGGER.info(String.format("Found preferred client: %s", new Object[] { pipe.build.name() }));
          break;
        } 
        arrayOfPipe[pipe.build.ordinal()] = pipe;
        arrayOfPipe[DiscordBuild.ANY.ordinal()] = pipe;
        pipe.build = null;
        pipe = null;
      } catch (IOException|JSONException iOException) {
        pipe = null;
      } 
    } 
    if (pipe == null) {
      for (b = 1; b < paramVarArgs.length; b++) {
        DiscordBuild discordBuild = paramVarArgs[b];
        LOGGER.debug(String.format("Looking for client build: %s", new Object[] { discordBuild.name() }));
        if (arrayOfPipe[discordBuild.ordinal()] != null) {
          pipe = arrayOfPipe[discordBuild.ordinal()];
          arrayOfPipe[discordBuild.ordinal()] = null;
          if (discordBuild == DiscordBuild.ANY) {
            for (byte b1 = 0; b1 < arrayOfPipe.length; b1++) {
              if (arrayOfPipe[b1] == pipe) {
                pipe.build = DiscordBuild.values()[b1];
                arrayOfPipe[b1] = null;
              } 
            } 
          } else {
            pipe.build = discordBuild;
          } 
          LOGGER.info(String.format("Found preferred client: %s", new Object[] { pipe.build.name() }));
          break;
        } 
      } 
      if (pipe == null)
        throw new NoDiscordClientException(); 
    } 
    for (b = 0; b < arrayOfPipe.length; b++) {
      if (b != DiscordBuild.ANY.ordinal() && arrayOfPipe[b] != null)
        try {
          arrayOfPipe[b].close();
        } catch (IOException iOException) {
          LOGGER.debug("Failed to close an open IPC pipe!", iOException);
        }  
    } 
    pipe.status = PipeStatus.CONNECTED;
    return pipe;
  }
  
  public void setListener(IPCListener paramIPCListener) {
    this.listener = paramIPCListener;
  }
  
  private static String generateNonce() {
    return UUID.randomUUID().toString();
  }
  
  public abstract void write(byte[] paramArrayOfbyte) throws IOException;
  
  private static Pipe createPipe(IPCClient paramIPCClient, HashMap<String, Callback> paramHashMap, String paramString) {
    String str = System.getProperty("os.name").toLowerCase();
    if (str.contains("win"))
      return new WindowsPipe(paramIPCClient, paramHashMap, paramString); 
    if (str.contains("linux") || str.contains("mac"))
      try {
        return new UnixPipe(paramIPCClient, paramHashMap, paramString);
      } catch (IOException iOException) {
        throw new RuntimeException(iOException);
      }  
    throw new RuntimeException(String.valueOf((new StringBuilder()).append("Unsupported OS: ").append(str)));
  }
  
  public DiscordBuild getDiscordBuild() {
    return this.build;
  }
}
