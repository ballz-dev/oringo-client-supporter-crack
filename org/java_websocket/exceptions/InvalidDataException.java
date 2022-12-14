package org.java_websocket.exceptions;

public class InvalidDataException extends Exception {
  private static final long serialVersionUID = 3731842424390998726L;
  
  private final int closecode;
  
  public InvalidDataException(int paramInt) {
    this.closecode = paramInt;
  }
  
  public InvalidDataException(int paramInt, String paramString) {
    super(paramString);
    this.closecode = paramInt;
  }
  
  public InvalidDataException(int paramInt, Throwable paramThrowable) {
    super(paramThrowable);
    this.closecode = paramInt;
  }
  
  public InvalidDataException(int paramInt, String paramString, Throwable paramThrowable) {
    super(paramString, paramThrowable);
    this.closecode = paramInt;
  }
  
  public int getCloseCode() {
    return this.closecode;
  }
}
