package org.slf4j;

public interface Logger {
  public static final String ROOT_LOGGER_NAME = "ROOT";
  
  void warn(Marker paramMarker, String paramString, Object... paramVarArgs);
  
  boolean isErrorEnabled(Marker paramMarker);
  
  void trace(String paramString);
  
  void error(String paramString, Object paramObject1, Object paramObject2);
  
  void error(Marker paramMarker, String paramString, Object paramObject);
  
  void error(Marker paramMarker, String paramString, Object... paramVarArgs);
  
  void debug(Marker paramMarker, String paramString, Object paramObject1, Object paramObject2);
  
  void error(String paramString, Object paramObject);
  
  boolean isDebugEnabled(Marker paramMarker);
  
  void trace(String paramString, Object paramObject);
  
  String getName();
  
  void error(String paramString, Throwable paramThrowable);
  
  void debug(String paramString, Object paramObject);
  
  void debug(Marker paramMarker, String paramString, Object... paramVarArgs);
  
  void warn(Marker paramMarker, String paramString, Object paramObject1, Object paramObject2);
  
  void info(String paramString);
  
  void trace(String paramString, Object... paramVarArgs);
  
  boolean isTraceEnabled();
  
  void trace(Marker paramMarker, String paramString, Throwable paramThrowable);
  
  void debug(Marker paramMarker, String paramString, Object paramObject);
  
  void debug(Marker paramMarker, String paramString, Throwable paramThrowable);
  
  void warn(String paramString);
  
  boolean isWarnEnabled(Marker paramMarker);
  
  boolean isDebugEnabled();
  
  void error(Marker paramMarker, String paramString, Object paramObject1, Object paramObject2);
  
  void debug(Marker paramMarker, String paramString);
  
  boolean isWarnEnabled();
  
  void debug(String paramString, Object paramObject1, Object paramObject2);
  
  void trace(Marker paramMarker, String paramString, Object... paramVarArgs);
  
  void trace(String paramString, Object paramObject1, Object paramObject2);
  
  void info(String paramString, Throwable paramThrowable);
  
  void error(Marker paramMarker, String paramString);
  
  boolean isErrorEnabled();
  
  void debug(String paramString);
  
  void info(Marker paramMarker, String paramString);
  
  void info(Marker paramMarker, String paramString, Object paramObject);
  
  void warn(String paramString, Throwable paramThrowable);
  
  void warn(String paramString, Object... paramVarArgs);
  
  boolean isTraceEnabled(Marker paramMarker);
  
  void debug(String paramString, Object... paramVarArgs);
  
  void error(Marker paramMarker, String paramString, Throwable paramThrowable);
  
  void info(String paramString, Object... paramVarArgs);
  
  void info(String paramString, Object paramObject1, Object paramObject2);
  
  void info(Marker paramMarker, String paramString, Object... paramVarArgs);
  
  void error(String paramString, Object... paramVarArgs);
  
  void warn(String paramString, Object paramObject1, Object paramObject2);
  
  void trace(Marker paramMarker, String paramString, Object paramObject);
  
  void info(Marker paramMarker, String paramString, Throwable paramThrowable);
  
  void trace(Marker paramMarker, String paramString);
  
  boolean isInfoEnabled();
  
  void warn(Marker paramMarker, String paramString);
  
  boolean isInfoEnabled(Marker paramMarker);
  
  void debug(String paramString, Throwable paramThrowable);
  
  void error(String paramString);
  
  void trace(Marker paramMarker, String paramString, Object paramObject1, Object paramObject2);
  
  void warn(Marker paramMarker, String paramString, Object paramObject);
  
  void warn(String paramString, Object paramObject);
  
  void warn(Marker paramMarker, String paramString, Throwable paramThrowable);
  
  void trace(String paramString, Throwable paramThrowable);
  
  void info(String paramString, Object paramObject);
  
  void info(Marker paramMarker, String paramString, Object paramObject1, Object paramObject2);
}
