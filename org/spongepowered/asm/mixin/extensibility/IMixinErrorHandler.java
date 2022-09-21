package org.spongepowered.asm.mixin.extensibility;

import org.apache.logging.log4j.Level;

public interface IMixinErrorHandler {
  ErrorAction onApplyError(String paramString, Throwable paramThrowable, IMixinInfo paramIMixinInfo, ErrorAction paramErrorAction);
  
  ErrorAction onPrepareError(IMixinConfig paramIMixinConfig, Throwable paramThrowable, IMixinInfo paramIMixinInfo, ErrorAction paramErrorAction);
  
  public enum ErrorAction {
    ERROR,
    WARN,
    NONE((String)Level.INFO);
    
    public final Level logLevel;
    
    static {
      ERROR = new ErrorAction("ERROR", 2, Level.FATAL);
      $VALUES = new ErrorAction[] { NONE, WARN, ERROR };
    }
    
    ErrorAction(Level param1Level) {
      this.logLevel = param1Level;
    }
  }
}
