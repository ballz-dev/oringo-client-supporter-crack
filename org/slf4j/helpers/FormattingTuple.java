package org.slf4j.helpers;

public class FormattingTuple {
  private Object[] argArray;
  
  private Throwable throwable;
  
  public static FormattingTuple NULL = new FormattingTuple(null);
  
  private String message;
  
  public FormattingTuple(String paramString) {
    this(paramString, null, null);
  }
  
  public FormattingTuple(String paramString, Object[] paramArrayOfObject, Throwable paramThrowable) {
    this.message = paramString;
    this.throwable = paramThrowable;
    this.argArray = paramArrayOfObject;
  }
  
  public String getMessage() {
    return this.message;
  }
  
  public Object[] getArgArray() {
    return this.argArray;
  }
  
  public Throwable getThrowable() {
    return this.throwable;
  }
}
