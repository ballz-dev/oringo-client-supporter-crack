package org.slf4j.event;

import org.slf4j.Marker;
import org.slf4j.helpers.SubstituteLogger;

public class SubstituteLoggingEvent implements LoggingEvent {
  Level level;
  
  String loggerName;
  
  SubstituteLogger logger;
  
  long timeStamp;
  
  Throwable throwable;
  
  String threadName;
  
  Object[] argArray;
  
  String message;
  
  Marker marker;
  
  public Level getLevel() {
    return this.level;
  }
  
  public void setLevel(Level paramLevel) {
    this.level = paramLevel;
  }
  
  public Marker getMarker() {
    return this.marker;
  }
  
  public void setMarker(Marker paramMarker) {
    this.marker = paramMarker;
  }
  
  public String getLoggerName() {
    return this.loggerName;
  }
  
  public void setLoggerName(String paramString) {
    this.loggerName = paramString;
  }
  
  public SubstituteLogger getLogger() {
    return this.logger;
  }
  
  public void setLogger(SubstituteLogger paramSubstituteLogger) {
    this.logger = paramSubstituteLogger;
  }
  
  public String getMessage() {
    return this.message;
  }
  
  public void setMessage(String paramString) {
    this.message = paramString;
  }
  
  public Object[] getArgumentArray() {
    return this.argArray;
  }
  
  public void setArgumentArray(Object[] paramArrayOfObject) {
    this.argArray = paramArrayOfObject;
  }
  
  public long getTimeStamp() {
    return this.timeStamp;
  }
  
  public void setTimeStamp(long paramLong) {
    this.timeStamp = paramLong;
  }
  
  public String getThreadName() {
    return this.threadName;
  }
  
  public void setThreadName(String paramString) {
    this.threadName = paramString;
  }
  
  public Throwable getThrowable() {
    return this.throwable;
  }
  
  public void setThrowable(Throwable paramThrowable) {
    this.throwable = paramThrowable;
  }
}
