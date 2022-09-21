package org.java_websocket.exceptions;

import java.io.UnsupportedEncodingException;

public class InvalidEncodingException extends RuntimeException {
  private final UnsupportedEncodingException encodingException;
  
  public InvalidEncodingException(UnsupportedEncodingException paramUnsupportedEncodingException) {
    if (paramUnsupportedEncodingException == null)
      throw new IllegalArgumentException(); 
    this.encodingException = paramUnsupportedEncodingException;
  }
  
  public UnsupportedEncodingException getEncodingException() {
    return this.encodingException;
  }
}
