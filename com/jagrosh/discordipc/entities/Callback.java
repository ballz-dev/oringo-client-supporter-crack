package com.jagrosh.discordipc.entities;

import java.util.function.Consumer;

public class Callback {
  private final Consumer<String> failure;
  
  private final Consumer<Packet> success;
  
  public Callback(Consumer<Packet> paramConsumer) {
    this(paramConsumer, (Consumer<String>)null);
  }
  
  public Callback(Consumer<Packet> paramConsumer, Consumer<String> paramConsumer1) {
    this.success = paramConsumer;
    this.failure = paramConsumer1;
  }
  
  @Deprecated
  public Callback(Runnable paramRunnable) {
    this(paramPacket -> paramRunnable.run(), (Consumer<String>)null);
  }
  
  public void succeed(Packet paramPacket) {
    if (this.success != null)
      this.success.accept(paramPacket); 
  }
  
  @Deprecated
  public Callback(Runnable paramRunnable, Consumer<String> paramConsumer) {
    this(paramPacket -> paramRunnable.run(), paramConsumer);
  }
  
  public boolean isEmpty() {
    return (this.success == null && this.failure == null);
  }
  
  public void fail(String paramString) {
    if (this.failure != null)
      this.failure.accept(paramString); 
  }
  
  public Callback() {
    this((Consumer<Packet>)null, (Consumer<String>)null);
  }
}
