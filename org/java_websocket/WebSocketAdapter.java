package org.java_websocket;

import org.java_websocket.drafts.Draft;
import org.java_websocket.exceptions.InvalidDataException;
import org.java_websocket.framing.Framedata;
import org.java_websocket.framing.PingFrame;
import org.java_websocket.framing.PongFrame;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.handshake.HandshakeImpl1Server;
import org.java_websocket.handshake.ServerHandshake;
import org.java_websocket.handshake.ServerHandshakeBuilder;

public abstract class WebSocketAdapter implements WebSocketListener {
  private PingFrame pingFrame;
  
  public ServerHandshakeBuilder onWebsocketHandshakeReceivedAsServer(WebSocket paramWebSocket, Draft paramDraft, ClientHandshake paramClientHandshake) throws InvalidDataException {
    return (ServerHandshakeBuilder)new HandshakeImpl1Server();
  }
  
  public void onWebsocketHandshakeReceivedAsClient(WebSocket paramWebSocket, ClientHandshake paramClientHandshake, ServerHandshake paramServerHandshake) throws InvalidDataException {}
  
  public void onWebsocketHandshakeSentAsClient(WebSocket paramWebSocket, ClientHandshake paramClientHandshake) throws InvalidDataException {}
  
  public void onWebsocketPing(WebSocket paramWebSocket, Framedata paramFramedata) {
    paramWebSocket.sendFrame((Framedata)new PongFrame((PingFrame)paramFramedata));
  }
  
  public void onWebsocketPong(WebSocket paramWebSocket, Framedata paramFramedata) {}
  
  public PingFrame onPreparePing(WebSocket paramWebSocket) {
    if (this.pingFrame == null)
      this.pingFrame = new PingFrame(); 
    return this.pingFrame;
  }
}
