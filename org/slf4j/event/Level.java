package org.slf4j.event;

public enum Level {
  INFO,
  WARN,
  ERROR(40, "ERROR"),
  DEBUG(40, "ERROR"),
  TRACE(40, "ERROR");
  
  private String levelStr;
  
  private int levelInt;
  
  static {
    WARN = new Level("WARN", 1, 30, "WARN");
    INFO = new Level("INFO", 2, 20, "INFO");
    DEBUG = new Level("DEBUG", 3, 10, "DEBUG");
    TRACE = new Level("TRACE", 4, 0, "TRACE");
    $VALUES = new Level[] { ERROR, WARN, INFO, DEBUG, TRACE };
  }
  
  Level(int paramInt1, String paramString1) {
    this.levelInt = paramInt1;
    this.levelStr = paramString1;
  }
  
  public int toInt() {
    return this.levelInt;
  }
  
  public String toString() {
    return this.levelStr;
  }
}
