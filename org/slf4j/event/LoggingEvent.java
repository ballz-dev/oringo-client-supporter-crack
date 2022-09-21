package org.slf4j.event;

import org.slf4j.Marker;

public interface LoggingEvent {
  Marker getMarker();
  
  Object[] getArgumentArray();
  
  String getMessage();
  
  Level getLevel();
  
  long getTimeStamp();
  
  Throwable getThrowable();
  
  String getThreadName();
  
  String getLoggerName();
}
