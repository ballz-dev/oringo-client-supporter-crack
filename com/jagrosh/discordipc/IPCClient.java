package com.jagrosh.discordipc;

import com.jagrosh.discordipc.entities.Callback;
import com.jagrosh.discordipc.entities.DiscordBuild;
import com.jagrosh.discordipc.entities.Packet;
import com.jagrosh.discordipc.entities.RichPresence;
import com.jagrosh.discordipc.entities.User;
import com.jagrosh.discordipc.entities.pipe.Pipe;
import com.jagrosh.discordipc.entities.pipe.PipeStatus;
import com.jagrosh.discordipc.exceptions.NoDiscordClientException;
import java.io.Closeable;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.util.HashMap;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class IPCClient implements Closeable {
  private IPCListener listener = null;
  
  private static final Logger LOGGER = LoggerFactory.getLogger(IPCClient.class);
  
  private final long clientId;
  
  private final HashMap<String, Callback> callbacks = new HashMap<>();
  
  private volatile Pipe pipe;
  
  private Thread readThread = null;
  
  public DiscordBuild getDiscordBuild() {
    return (this.pipe == null) ? null : this.pipe.getDiscordBuild();
  }
  
  public void subscribe(Event paramEvent, Callback paramCallback) {
    checkConnected(true);
    if (!paramEvent.isSubscribable())
      throw new IllegalStateException(String.valueOf((new StringBuilder()).append("Cannot subscribe to ").append(paramEvent).append(" event!"))); 
    LOGGER.debug(String.format("Subscribing to Event: %s", new Object[] { paramEvent.name() }));
    this.pipe.send(Packet.OpCode.FRAME, (new JSONObject()).put("cmd", "SUBSCRIBE").put("evt", paramEvent.name()), paramCallback);
  }
  
  public IPCClient(long paramLong) {
    this.clientId = paramLong;
  }
  
  public void setListener(IPCListener paramIPCListener) {
    this.listener = paramIPCListener;
    if (this.pipe != null)
      this.pipe.setListener(paramIPCListener); 
  }
  
  public void sendRichPresence(RichPresence paramRichPresence, Callback paramCallback) {
    checkConnected(true);
    LOGGER.debug(String.valueOf((new StringBuilder()).append("Sending RichPresence to discord: ").append((paramRichPresence == null) ? null : paramRichPresence.toJson().toString())));
    this.pipe.send(Packet.OpCode.FRAME, (new JSONObject()).put("cmd", "SET_ACTIVITY").put("args", (new JSONObject()).put("pid", getPID()).put("activity", (paramRichPresence == null) ? null : paramRichPresence.toJson())), paramCallback);
  }
  
  public void connect(DiscordBuild... paramVarArgs) throws NoDiscordClientException {
    checkConnected(false);
    this.callbacks.clear();
    this.pipe = null;
    this.pipe = Pipe.openPipe(this, this.clientId, this.callbacks, paramVarArgs);
    LOGGER.debug("Client is now connected and ready!");
    if (this.listener != null)
      this.listener.onReady(this); 
    startReading();
  }
  
  private void startReading() {
    this.readThread = new Thread(() -> {
          try {
            Packet packet;
            while ((packet = this.pipe.read()).getOp() != Packet.OpCode.CLOSE) {
              JSONObject jSONObject = packet.getJson();
              Event event = Event.of(jSONObject.optString("evt", null));
              String str = jSONObject.optString("nonce", null);
              switch (event) {
                case NULL:
                  if (str != null && this.callbacks.containsKey(str))
                    ((Callback)this.callbacks.remove(str)).succeed(packet); 
                  break;
                case ERROR:
                  if (str != null && this.callbacks.containsKey(str))
                    ((Callback)this.callbacks.remove(str)).fail(jSONObject.getJSONObject("data").optString("message", null)); 
                  break;
                case ACTIVITY_JOIN:
                  LOGGER.debug("Reading thread received a 'join' event.");
                  break;
                case ACTIVITY_SPECTATE:
                  LOGGER.debug("Reading thread received a 'spectate' event.");
                  break;
                case ACTIVITY_JOIN_REQUEST:
                  LOGGER.debug("Reading thread received a 'join request' event.");
                  break;
                case UNKNOWN:
                  LOGGER.debug(String.valueOf((new StringBuilder()).append("Reading thread encountered an event with an unknown type: ").append(jSONObject.getString("evt"))));
                  break;
              } 
              if (this.listener != null && jSONObject.has("cmd") && jSONObject.getString("cmd").equals("DISPATCH"))
                try {
                  JSONObject jSONObject2;
                  User user;
                  JSONObject jSONObject1 = jSONObject.getJSONObject("data");
                  switch (Event.of(jSONObject.getString("evt"))) {
                    case ACTIVITY_JOIN:
                      this.listener.onActivityJoin(this, jSONObject1.getString("secret"));
                    case ACTIVITY_SPECTATE:
                      this.listener.onActivitySpectate(this, jSONObject1.getString("secret"));
                    case ACTIVITY_JOIN_REQUEST:
                      jSONObject2 = jSONObject1.getJSONObject("user");
                      user = new User(jSONObject2.getString("username"), jSONObject2.getString("discriminator"), Long.parseLong(jSONObject2.getString("id")), jSONObject2.optString("avatar", null));
                      this.listener.onActivityJoinRequest(this, jSONObject1.optString("secret", null), user);
                  } 
                } catch (Exception exception) {
                  LOGGER.error("Exception when handling event: ", exception);
                }  
            } 
            this.pipe.setStatus(PipeStatus.DISCONNECTED);
            if (this.listener != null)
              this.listener.onClose(this, packet.getJson()); 
          } catch (IOException|org.json.JSONException iOException) {
            if (iOException instanceof IOException) {
              LOGGER.error("Reading thread encountered an IOException", iOException);
            } else {
              LOGGER.error("Reading thread encountered an JSONException", iOException);
            } 
            this.pipe.setStatus(PipeStatus.DISCONNECTED);
            if (this.listener != null)
              this.listener.onDisconnect(this, iOException); 
          } 
        });
    LOGGER.debug("Starting IPCClient reading thread!");
    this.readThread.start();
  }
  
  public void close() {
    checkConnected(true);
    try {
      this.pipe.close();
    } catch (IOException iOException) {
      LOGGER.debug("Failed to close pipe", iOException);
    } 
  }
  
  public PipeStatus getStatus() {
    return (this.pipe == null) ? PipeStatus.UNINITIALIZED : this.pipe.getStatus();
  }
  
  private static int getPID() {
    String str = ManagementFactory.getRuntimeMXBean().getName();
    return Integer.parseInt(str.substring(0, str.indexOf('@')));
  }
  
  private void checkConnected(boolean paramBoolean) {
    if (paramBoolean && getStatus() != PipeStatus.CONNECTED)
      throw new IllegalStateException(String.format("IPCClient (ID: %d) is not connected!", new Object[] { Long.valueOf(this.clientId) })); 
    if (!paramBoolean && getStatus() == PipeStatus.CONNECTED)
      throw new IllegalStateException(String.format("IPCClient (ID: %d) is already connected!", new Object[] { Long.valueOf(this.clientId) })); 
  }
  
  public void subscribe(Event paramEvent) {
    subscribe(paramEvent, null);
  }
  
  public void sendRichPresence(RichPresence paramRichPresence) {
    sendRichPresence(paramRichPresence, null);
  }
  
  public enum Event {
    NULL(false),
    ERROR(false),
    READY(false),
    UNKNOWN(false),
    ACTIVITY_JOIN_REQUEST(false),
    ACTIVITY_SPECTATE(false),
    ACTIVITY_JOIN(false);
    
    private final boolean subscribable;
    
    static Event of(String param1String) {
      if (param1String == null)
        return NULL; 
      for (Event event : values()) {
        if (event != UNKNOWN && event.name().equalsIgnoreCase(param1String))
          return event; 
      } 
      return UNKNOWN;
    }
    
    static {
      ACTIVITY_SPECTATE = new Event("ACTIVITY_SPECTATE", 4, true);
      ACTIVITY_JOIN_REQUEST = new Event("ACTIVITY_JOIN_REQUEST", 5, true);
      UNKNOWN = new Event("UNKNOWN", 6, false);
      $VALUES = new Event[] { NULL, READY, ERROR, ACTIVITY_JOIN, ACTIVITY_SPECTATE, ACTIVITY_JOIN_REQUEST, UNKNOWN };
    }
    
    public boolean isSubscribable() {
      return this.subscribable;
    }
    
    Event(boolean param1Boolean) {
      this.subscribable = param1Boolean;
    }
  }
}
