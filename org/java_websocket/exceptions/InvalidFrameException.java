package org.java_websocket.exceptions;

public class InvalidFrameException extends InvalidDataException {
  private static final long serialVersionUID = -9016496369828887591L;
  
  public InvalidFrameException() {
    super(1002);
  }
  
  public InvalidFrameException(String paramString) {
    super(1002, paramString);
  }
  
  public InvalidFrameException(Throwable paramThrowable) {
    super(1002, paramThrowable);
  }
  
  public InvalidFrameException(String paramString, Throwable paramThrowable) {
    super(1002, paramString, paramThrowable);
  }
}
