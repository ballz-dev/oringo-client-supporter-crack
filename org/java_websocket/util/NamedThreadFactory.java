package org.java_websocket.util;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class NamedThreadFactory implements ThreadFactory {
  private final String threadPrefix;
  
  private final ThreadFactory defaultThreadFactory = Executors.defaultThreadFactory();
  
  private final AtomicInteger threadNumber = new AtomicInteger(1);
  
  public NamedThreadFactory(String paramString) {
    this.threadPrefix = paramString;
  }
  
  public Thread newThread(Runnable paramRunnable) {
    Thread thread = this.defaultThreadFactory.newThread(paramRunnable);
    thread.setName(this.threadPrefix + "-" + this.threadNumber);
    return thread;
  }
}
