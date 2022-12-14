package org.slf4j.helpers;

public class NOPLogger extends MarkerIgnoringBase {
  public static final NOPLogger NOP_LOGGER = new NOPLogger();
  
  private static final long serialVersionUID = -517220405410904473L;
  
  public String getName() {
    return "NOP";
  }
  
  public final boolean isTraceEnabled() {
    return false;
  }
  
  public final void trace(String paramString) {}
  
  public final void trace(String paramString, Object paramObject) {}
  
  public final void trace(String paramString, Object paramObject1, Object paramObject2) {}
  
  public final void trace(String paramString, Object... paramVarArgs) {}
  
  public final void trace(String paramString, Throwable paramThrowable) {}
  
  public final boolean isDebugEnabled() {
    return false;
  }
  
  public final void debug(String paramString) {}
  
  public final void debug(String paramString, Object paramObject) {}
  
  public final void debug(String paramString, Object paramObject1, Object paramObject2) {}
  
  public final void debug(String paramString, Object... paramVarArgs) {}
  
  public final void debug(String paramString, Throwable paramThrowable) {}
  
  public final boolean isInfoEnabled() {
    return false;
  }
  
  public final void info(String paramString) {}
  
  public final void info(String paramString, Object paramObject) {}
  
  public final void info(String paramString, Object paramObject1, Object paramObject2) {}
  
  public final void info(String paramString, Object... paramVarArgs) {}
  
  public final void info(String paramString, Throwable paramThrowable) {}
  
  public final boolean isWarnEnabled() {
    return false;
  }
  
  public final void warn(String paramString) {}
  
  public final void warn(String paramString, Object paramObject) {}
  
  public final void warn(String paramString, Object paramObject1, Object paramObject2) {}
  
  public final void warn(String paramString, Object... paramVarArgs) {}
  
  public final void warn(String paramString, Throwable paramThrowable) {}
  
  public final boolean isErrorEnabled() {
    return false;
  }
  
  public final void error(String paramString) {}
  
  public final void error(String paramString, Object paramObject) {}
  
  public final void error(String paramString, Object paramObject1, Object paramObject2) {}
  
  public final void error(String paramString, Object... paramVarArgs) {}
  
  public final void error(String paramString, Throwable paramThrowable) {}
}
