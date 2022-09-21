package org.json;

public class JSONException extends RuntimeException {
  private static final long serialVersionUID = 0L;
  
  public JSONException(String paramString) {
    super(paramString);
  }
  
  public JSONException(String paramString, Throwable paramThrowable) {
    super(paramString, paramThrowable);
  }
  
  public JSONException(Throwable paramThrowable) {
    super(paramThrowable.getMessage(), paramThrowable);
  }
}
