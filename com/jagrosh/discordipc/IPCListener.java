package com.jagrosh.discordipc;

import com.jagrosh.discordipc.entities.Packet;
import com.jagrosh.discordipc.entities.User;
import org.json.JSONObject;

public interface IPCListener {
  default void onPacketReceived(IPCClient paramIPCClient, Packet paramPacket) {}
  
  default void onActivityJoinRequest(IPCClient paramIPCClient, String paramString, User paramUser) {}
  
  default void onDisconnect(IPCClient paramIPCClient, Throwable paramThrowable) {}
  
  default void onReady(IPCClient paramIPCClient) {}
  
  default void onActivityJoin(IPCClient paramIPCClient, String paramString) {}
  
  default void onActivitySpectate(IPCClient paramIPCClient, String paramString) {}
  
  default void onPacketSent(IPCClient paramIPCClient, Packet paramPacket) {}
  
  default void onClose(IPCClient paramIPCClient, JSONObject paramJSONObject) {}
  
  static {
  
  }
}
