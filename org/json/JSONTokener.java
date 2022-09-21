package org.json;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;

public class JSONTokener {
  private boolean eof;
  
  private long index;
  
  private boolean usePrevious;
  
  private long line;
  
  private final Reader reader;
  
  private char previous;
  
  private long characterPreviousLine;
  
  private long character;
  
  public JSONTokener(Reader paramReader) {
    this.reader = paramReader.markSupported() ? paramReader : new BufferedReader(paramReader);
    this.eof = false;
    this.usePrevious = false;
    this.previous = Character.MIN_VALUE;
    this.index = 0L;
    this.character = 1L;
    this.characterPreviousLine = 0L;
    this.line = 1L;
  }
  
  public JSONTokener(InputStream paramInputStream) {
    this(new InputStreamReader(paramInputStream));
  }
  
  public JSONTokener(String paramString) {
    this(new StringReader(paramString));
  }
  
  public void back() throws JSONException {
    if (this.usePrevious || this.index <= 0L)
      throw new JSONException("Stepping back two steps is not supported"); 
    decrementIndexes();
    this.usePrevious = true;
    this.eof = false;
  }
  
  private void decrementIndexes() {
    this.index--;
    if (this.previous == '\r' || this.previous == '\n') {
      this.line--;
      this.character = this.characterPreviousLine;
    } else if (this.character > 0L) {
      this.character--;
    } 
  }
  
  public static int dehexchar(char paramChar) {
    if (paramChar >= '0' && paramChar <= '9')
      return paramChar - 48; 
    if (paramChar >= 'A' && paramChar <= 'F')
      return paramChar - 55; 
    if (paramChar >= 'a' && paramChar <= 'f')
      return paramChar - 87; 
    return -1;
  }
  
  public boolean end() {
    return (this.eof && !this.usePrevious);
  }
  
  public boolean more() throws JSONException {
    if (this.usePrevious)
      return true; 
    try {
      this.reader.mark(1);
    } catch (IOException iOException) {
      throw new JSONException("Unable to preserve stream position", iOException);
    } 
    try {
      if (this.reader.read() <= 0) {
        this.eof = true;
        return false;
      } 
      this.reader.reset();
    } catch (IOException iOException) {
      throw new JSONException("Unable to read the next character from the stream", iOException);
    } 
    return true;
  }
  
  public char next() throws JSONException {
    int i;
    if (this.usePrevious) {
      this.usePrevious = false;
      i = this.previous;
    } else {
      try {
        i = this.reader.read();
      } catch (IOException iOException) {
        throw new JSONException(iOException);
      } 
    } 
    if (i <= 0) {
      this.eof = true;
      return Character.MIN_VALUE;
    } 
    incrementIndexes(i);
    this.previous = (char)i;
    return this.previous;
  }
  
  private void incrementIndexes(int paramInt) {
    if (paramInt > 0) {
      this.index++;
      if (paramInt == 13) {
        this.line++;
        this.characterPreviousLine = this.character;
        this.character = 0L;
      } else if (paramInt == 10) {
        if (this.previous != '\r') {
          this.line++;
          this.characterPreviousLine = this.character;
        } 
        this.character = 0L;
      } else {
        this.character++;
      } 
    } 
  }
  
  public char next(char paramChar) throws JSONException {
    char c = next();
    if (c != paramChar) {
      if (c > '\000')
        throw syntaxError("Expected '" + paramChar + "' and instead saw '" + c + "'"); 
      throw syntaxError("Expected '" + paramChar + "' and instead saw ''");
    } 
    return c;
  }
  
  public String next(int paramInt) throws JSONException {
    if (paramInt == 0)
      return ""; 
    char[] arrayOfChar = new char[paramInt];
    byte b = 0;
    while (b < paramInt) {
      arrayOfChar[b] = next();
      if (end())
        throw syntaxError("Substring bounds error"); 
      b++;
    } 
    return new String(arrayOfChar);
  }
  
  public char nextClean() throws JSONException {
    char c;
    do {
      c = next();
    } while (c != '\000' && c <= ' ');
    return c;
  }
  
  public String nextString(char paramChar) throws JSONException {
    StringBuilder stringBuilder = new StringBuilder();
    while (true) {
      char c = next();
      switch (c) {
        case '\000':
        case '\n':
        case '\r':
          throw syntaxError("Unterminated string");
        case '\\':
          c = next();
          switch (c) {
            case 'b':
              stringBuilder.append('\b');
              continue;
            case 't':
              stringBuilder.append('\t');
              continue;
            case 'n':
              stringBuilder.append('\n');
              continue;
            case 'f':
              stringBuilder.append('\f');
              continue;
            case 'r':
              stringBuilder.append('\r');
              continue;
            case 'u':
              try {
                stringBuilder.append((char)Integer.parseInt(next(4), 16));
              } catch (NumberFormatException numberFormatException) {
                throw syntaxError("Illegal escape.", numberFormatException);
              } 
              continue;
            case '"':
            case '\'':
            case '/':
            case '\\':
              stringBuilder.append(c);
              continue;
          } 
          throw syntaxError("Illegal escape.");
      } 
      if (c == paramChar)
        return stringBuilder.toString(); 
      stringBuilder.append(c);
    } 
  }
  
  public String nextTo(char paramChar) throws JSONException {
    StringBuilder stringBuilder = new StringBuilder();
    while (true) {
      char c = next();
      if (c == paramChar || c == '\000' || c == '\n' || c == '\r') {
        if (c != '\000')
          back(); 
        return stringBuilder.toString().trim();
      } 
      stringBuilder.append(c);
    } 
  }
  
  public String nextTo(String paramString) throws JSONException {
    StringBuilder stringBuilder = new StringBuilder();
    while (true) {
      char c = next();
      if (paramString.indexOf(c) >= 0 || c == '\000' || c == '\n' || c == '\r') {
        if (c != '\000')
          back(); 
        return stringBuilder.toString().trim();
      } 
      stringBuilder.append(c);
    } 
  }
  
  public Object nextValue() throws JSONException {
    char c = nextClean();
    switch (c) {
      case '"':
      case '\'':
        return nextString(c);
      case '{':
        back();
        return new JSONObject(this);
      case '[':
        back();
        return new JSONArray(this);
    } 
    StringBuilder stringBuilder = new StringBuilder();
    while (c >= ' ' && ",:]}/\\\"[{;=#".indexOf(c) < 0) {
      stringBuilder.append(c);
      c = next();
    } 
    back();
    String str = stringBuilder.toString().trim();
    if ("".equals(str))
      throw syntaxError("Missing value"); 
    return JSONObject.stringToValue(str);
  }
  
  public char skipTo(char paramChar) throws JSONException {
    try {
      long l1 = this.index;
      long l2 = this.character;
      long l3 = this.line;
      this.reader.mark(1000000);
      while (true) {
        char c = next();
        if (c == '\000') {
          this.reader.reset();
          this.index = l1;
          this.character = l2;
          this.line = l3;
          return Character.MIN_VALUE;
        } 
        if (c == paramChar) {
          this.reader.mark(1);
          back();
          return c;
        } 
      } 
    } catch (IOException iOException) {
      throw new JSONException(iOException);
    } 
  }
  
  public JSONException syntaxError(String paramString) {
    return new JSONException(paramString + toString());
  }
  
  public JSONException syntaxError(String paramString, Throwable paramThrowable) {
    return new JSONException(paramString + toString(), paramThrowable);
  }
  
  public String toString() {
    return " at " + this.index + " [character " + this.character + " line " + this.line + "]";
  }
}
