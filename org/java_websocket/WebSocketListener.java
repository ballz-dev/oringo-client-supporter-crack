package org.java_websocket;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import org.java_websocket.drafts.Draft;
import org.java_websocket.exceptions.InvalidDataException;
import org.java_websocket.framing.Framedata;
import org.java_websocket.framing.PingFrame;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.handshake.Handshakedata;
import org.java_websocket.handshake.ServerHandshake;
import org.java_websocket.handshake.ServerHandshakeBuilder;

public interface WebSocketListener {
  void onWebsocketClosing(WebSocket paramWebSocket, int paramInt, String paramString, boolean paramBoolean);
  
  void onWebsocketHandshakeSentAsClient(WebSocket paramWebSocket, ClientHandshake paramClientHandshake) throws InvalidDataException;
  
  InetSocketAddress getRemoteSocketAddress(WebSocket paramWebSocket);
  
  void onWriteDemand(WebSocket paramWebSocket);
  
  PingFrame onPreparePing(WebSocket paramWebSocket);
  
  void onWebsocketOpen(WebSocket paramWebSocket, Handshakedata paramHandshakedata);
  
  void onWebsocketClose(WebSocket paramWebSocket, int paramInt, String paramString, boolean paramBoolean);
  
  void onWebsocketHandshakeReceivedAsClient(WebSocket paramWebSocket, ClientHandshake paramClientHandshake, ServerHandshake paramServerHandshake) throws InvalidDataException;
  
  void onWebsocketPong(WebSocket paramWebSocket, Framedata paramFramedata);
  
  void onWebsocketCloseInitiated(WebSocket paramWebSocket, int paramInt, String paramString);
  
  ServerHandshakeBuilder onWebsocketHandshakeReceivedAsServer(WebSocket paramWebSocket, Draft paramDraft, ClientHandshake paramClientHandshake) throws InvalidDataException;
  
  void onWebsocketPing(WebSocket paramWebSocket, Framedata paramFramedata);
  
  void onWebsocketError(WebSocket paramWebSocket, Exception paramException);
  
  void onWebsocketMessage(WebSocket paramWebSocket, String paramString);
  
  void onWebsocketMessage(WebSocket paramWebSocket, ByteBuffer paramByteBuffer);
  
  InetSocketAddress getLocalSocketAddress(WebSocket paramWebSocket);
}
