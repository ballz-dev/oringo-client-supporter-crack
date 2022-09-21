package org.java_websocket.exceptions;

public class LimitExceededException extends InvalidDataException {
  private final int limit;
  
  private static final long serialVersionUID = 6908339749836826785L;
  
  public LimitExceededException() {
    this(2147483647);
  }
  
  public LimitExceededException(int paramInt) {
    super(1009);
    this.limit = paramInt;
  }
  
  public LimitExceededException(String paramString, int paramInt) {
    super(1009, paramString);
    this.limit = paramInt;
  }
  
  public LimitExceededException(String paramString) {
    this(paramString, 2147483647);
  }
  
  public int getLimit() {
    return this.limit;
  }
}
