package org.json;

public class HTTPTokener extends JSONTokener {
  public HTTPTokener(String paramString) {
    super(paramString);
  }
  
  public String nextToken() throws JSONException {
    StringBuilder stringBuilder = new StringBuilder();
    while (true) {
      char c = next();
      if (!Character.isWhitespace(c)) {
        if (c == '"' || c == '\'') {
          char c1 = c;
          while (true) {
            c = next();
            if (c < ' ')
              throw syntaxError("Unterminated string."); 
            if (c == c1)
              return stringBuilder.toString(); 
            stringBuilder.append(c);
          } 
          break;
        } 
        while (true) {
          if (c == '\000' || Character.isWhitespace(c))
            return stringBuilder.toString(); 
          stringBuilder.append(c);
          c = next();
        } 
        break;
      } 
    } 
  }
}
