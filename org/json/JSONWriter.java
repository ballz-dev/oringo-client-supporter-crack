package org.json;

import java.io.IOException;

public class JSONWriter {
  protected char mode;
  
  private boolean comma;
  
  private static final int maxdepth = 200;
  
  private int top;
  
  private final JSONObject[] stack;
  
  protected Appendable writer;
  
  public JSONWriter(Appendable paramAppendable) {
    this.comma = false;
    this.mode = 'i';
    this.stack = new JSONObject[200];
    this.top = 0;
    this.writer = paramAppendable;
  }
  
  private JSONWriter append(String paramString) throws JSONException {
    if (paramString == null)
      throw new JSONException("Null pointer"); 
    if (this.mode == 'o' || this.mode == 'a') {
      try {
        if (this.comma && this.mode == 'a')
          this.writer.append(','); 
        this.writer.append(paramString);
      } catch (IOException iOException) {
        throw new JSONException(iOException);
      } 
      if (this.mode == 'o')
        this.mode = 'k'; 
      this.comma = true;
      return this;
    } 
    throw new JSONException("Value out of sequence.");
  }
  
  public JSONWriter array() throws JSONException {
    if (this.mode == 'i' || this.mode == 'o' || this.mode == 'a') {
      push(null);
      append("[");
      this.comma = false;
      return this;
    } 
    throw new JSONException("Misplaced array.");
  }
  
  private JSONWriter end(char paramChar1, char paramChar2) throws JSONException {
    if (this.mode != paramChar1)
      throw new JSONException((paramChar1 == 'a') ? "Misplaced endArray." : "Misplaced endObject."); 
    pop(paramChar1);
    try {
      this.writer.append(paramChar2);
    } catch (IOException iOException) {
      throw new JSONException(iOException);
    } 
    this.comma = true;
    return this;
  }
  
  public JSONWriter endArray() throws JSONException {
    return end('a', ']');
  }
  
  public JSONWriter endObject() throws JSONException {
    return end('k', '}');
  }
  
  public JSONWriter key(String paramString) throws JSONException {
    if (paramString == null)
      throw new JSONException("Null key."); 
    if (this.mode == 'k')
      try {
        this.stack[this.top - 1].putOnce(paramString, Boolean.TRUE);
        if (this.comma)
          this.writer.append(','); 
        this.writer.append(JSONObject.quote(paramString));
        this.writer.append(':');
        this.comma = false;
        this.mode = 'o';
        return this;
      } catch (IOException iOException) {
        throw new JSONException(iOException);
      }  
    throw new JSONException("Misplaced key.");
  }
  
  public JSONWriter object() throws JSONException {
    if (this.mode == 'i')
      this.mode = 'o'; 
    if (this.mode == 'o' || this.mode == 'a') {
      append("{");
      push(new JSONObject());
      this.comma = false;
      return this;
    } 
    throw new JSONException("Misplaced object.");
  }
  
  private void pop(char paramChar) throws JSONException {
    if (this.top <= 0)
      throw new JSONException("Nesting error."); 
    byte b = (this.stack[this.top - 1] == null) ? 97 : 107;
    if (b != paramChar)
      throw new JSONException("Nesting error."); 
    this.top--;
    this.mode = (this.top == 0) ? 'd' : ((this.stack[this.top - 1] == null) ? 'a' : 'k');
  }
  
  private void push(JSONObject paramJSONObject) throws JSONException {
    if (this.top >= 200)
      throw new JSONException("Nesting too deep."); 
    this.stack[this.top] = paramJSONObject;
    this.mode = (paramJSONObject == null) ? 'a' : 'k';
    this.top++;
  }
  
  public JSONWriter value(boolean paramBoolean) throws JSONException {
    return append(paramBoolean ? "true" : "false");
  }
  
  public JSONWriter value(double paramDouble) throws JSONException {
    return value(new Double(paramDouble));
  }
  
  public JSONWriter value(long paramLong) throws JSONException {
    return append(Long.toString(paramLong));
  }
  
  public JSONWriter value(Object paramObject) throws JSONException {
    return append(JSONObject.valueToString(paramObject));
  }
}
