package org.json;

import java.util.HashMap;

public class XMLTokener extends JSONTokener {
  public static final HashMap<String, Character> entity = new HashMap<String, Character>(8);
  
  static {
    entity.put("amp", XML.AMP);
    entity.put("apos", XML.APOS);
    entity.put("gt", XML.GT);
    entity.put("lt", XML.LT);
    entity.put("quot", XML.QUOT);
  }
  
  public XMLTokener(String paramString) {
    super(paramString);
  }
  
  public String nextCDATA() throws JSONException {
    StringBuilder stringBuilder = new StringBuilder();
    while (more()) {
      char c = next();
      stringBuilder.append(c);
      int i = stringBuilder.length() - 3;
      if (i >= 0 && stringBuilder.charAt(i) == ']' && stringBuilder
        .charAt(i + 1) == ']' && stringBuilder.charAt(i + 2) == '>') {
        stringBuilder.setLength(i);
        return stringBuilder.toString();
      } 
    } 
    throw syntaxError("Unclosed CDATA");
  }
  
  public Object nextContent() throws JSONException {
    while (true) {
      char c = next();
      if (!Character.isWhitespace(c)) {
        if (c == '\000')
          return null; 
        if (c == '<')
          return XML.LT; 
        StringBuilder stringBuilder = new StringBuilder();
        while (true) {
          if (c == '\000')
            return stringBuilder.toString().trim(); 
          if (c == '<') {
            back();
            return stringBuilder.toString().trim();
          } 
          if (c == '&') {
            stringBuilder.append(nextEntity(c));
          } else {
            stringBuilder.append(c);
          } 
          c = next();
        } 
        break;
      } 
    } 
  }
  
  public Object nextEntity(char paramChar) throws JSONException {
    char c;
    StringBuilder stringBuilder = new StringBuilder();
    while (true) {
      c = next();
      if (Character.isLetterOrDigit(c) || c == '#') {
        stringBuilder.append(Character.toLowerCase(c));
        continue;
      } 
      break;
    } 
    if (c == ';') {
      String str = stringBuilder.toString();
      return unescapeEntity(str);
    } 
    throw syntaxError("Missing ';' in XML entity: &" + stringBuilder);
  }
  
  static String unescapeEntity(String paramString) {
    if (paramString == null || paramString.isEmpty())
      return ""; 
    if (paramString.charAt(0) == '#') {
      int i;
      if (paramString.charAt(1) == 'x') {
        i = Integer.parseInt(paramString.substring(2), 16);
      } else {
        i = Integer.parseInt(paramString.substring(1));
      } 
      return new String(new int[] { i }, 0, 1);
    } 
    Character character = entity.get(paramString);
    if (character == null)
      return '&' + paramString + ';'; 
    return character.toString();
  }
  
  public Object nextMeta() throws JSONException {
    char c;
    char c1;
    do {
      c = next();
    } while (Character.isWhitespace(c));
    switch (c) {
      case '\000':
        throw syntaxError("Misshaped meta tag");
      case '<':
        return XML.LT;
      case '>':
        return XML.GT;
      case '/':
        return XML.SLASH;
      case '=':
        return XML.EQ;
      case '!':
        return XML.BANG;
      case '?':
        return XML.QUEST;
      case '"':
      case '\'':
        c1 = c;
        while (true) {
          c = next();
          if (c == '\000')
            throw syntaxError("Unterminated string"); 
          if (c == c1)
            return Boolean.TRUE; 
        } 
    } 
    while (true) {
      c = next();
      if (Character.isWhitespace(c))
        return Boolean.TRUE; 
      switch (c) {
        case '\000':
        case '!':
        case '"':
        case '\'':
        case '/':
        case '<':
        case '=':
        case '>':
        case '?':
          break;
      } 
    } 
    back();
    return Boolean.TRUE;
  }
  
  public Object nextToken() throws JSONException {
    char c;
    char c1;
    do {
      c = next();
    } while (Character.isWhitespace(c));
    switch (c) {
      case '\000':
        throw syntaxError("Misshaped element");
      case '<':
        throw syntaxError("Misplaced '<'");
      case '>':
        return XML.GT;
      case '/':
        return XML.SLASH;
      case '=':
        return XML.EQ;
      case '!':
        return XML.BANG;
      case '?':
        return XML.QUEST;
      case '"':
      case '\'':
        c1 = c;
        stringBuilder = new StringBuilder();
        while (true) {
          c = next();
          if (c == '\000')
            throw syntaxError("Unterminated string"); 
          if (c == c1)
            return stringBuilder.toString(); 
          if (c == '&') {
            stringBuilder.append(nextEntity(c));
            continue;
          } 
          stringBuilder.append(c);
        } 
    } 
    StringBuilder stringBuilder = new StringBuilder();
    while (true) {
      stringBuilder.append(c);
      c = next();
      if (Character.isWhitespace(c))
        return stringBuilder.toString(); 
      switch (c) {
        case '\000':
          return stringBuilder.toString();
        case '!':
        case '/':
        case '=':
        case '>':
        case '?':
        case '[':
        case ']':
          back();
          return stringBuilder.toString();
        case '"':
        case '\'':
        case '<':
          break;
      } 
    } 
    throw syntaxError("Bad character in a name");
  }
  
  public boolean skipPast(String paramString) throws JSONException {
    int i = 0;
    int j = paramString.length();
    char[] arrayOfChar = new char[j];
    byte b;
    for (b = 0; b < j; b++) {
      char c = next();
      if (c == '\000')
        return false; 
      arrayOfChar[b] = c;
    } 
    while (true) {
      int k = i;
      boolean bool = true;
      for (b = 0; b < j; b++) {
        if (arrayOfChar[k] != paramString.charAt(b)) {
          bool = false;
          break;
        } 
        k++;
        if (k >= j)
          k -= j; 
      } 
      if (bool)
        return true; 
      char c = next();
      if (c == '\000')
        return false; 
      arrayOfChar[i] = c;
      i++;
      if (i >= j)
        i -= j; 
    } 
  }
}
