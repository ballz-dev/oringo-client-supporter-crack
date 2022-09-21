package org.slf4j.helpers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;
import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;
import org.slf4j.event.SubstituteLoggingEvent;

public class SubstituteLoggerFactory implements ILoggerFactory {
  boolean postInitialization = false;
  
  final Map<String, SubstituteLogger> loggers = new HashMap<String, SubstituteLogger>();
  
  final LinkedBlockingQueue<SubstituteLoggingEvent> eventQueue = new LinkedBlockingQueue<SubstituteLoggingEvent>();
  
  public synchronized Logger getLogger(String paramString) {
    SubstituteLogger substituteLogger = this.loggers.get(paramString);
    if (substituteLogger == null) {
      substituteLogger = new SubstituteLogger(paramString, this.eventQueue, this.postInitialization);
      this.loggers.put(paramString, substituteLogger);
    } 
    return substituteLogger;
  }
  
  public List<String> getLoggerNames() {
    return new ArrayList<String>(this.loggers.keySet());
  }
  
  public List<SubstituteLogger> getLoggers() {
    return new ArrayList<SubstituteLogger>(this.loggers.values());
  }
  
  public LinkedBlockingQueue<SubstituteLoggingEvent> getEventQueue() {
    return this.eventQueue;
  }
  
  public void postInitialization() {
    this.postInitialization = true;
  }
  
  public void clear() {
    this.loggers.clear();
    this.eventQueue.clear();
  }
}
