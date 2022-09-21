package org.json;

import java.io.Closeable;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

public class JSONObject {
  private static final class Null {
    private Null() {}
    
    protected final Object clone() {
      return this;
    }
    
    public boolean equals(Object param1Object) {
      return (param1Object == null || param1Object == this);
    }
    
    public int hashCode() {
      return 0;
    }
    
    public String toString() {
      return "null";
    }
  }
  
  public static final Object NULL = new Null();
  
  private final Map<String, Object> map;
  
  public JSONObject() {
    this.map = new HashMap<String, Object>();
  }
  
  public JSONObject(JSONObject paramJSONObject, String[] paramArrayOfString) {
    this(paramArrayOfString.length);
    for (byte b = 0; b < paramArrayOfString.length; b++) {
      try {
        putOnce(paramArrayOfString[b], paramJSONObject.opt(paramArrayOfString[b]));
      } catch (Exception exception) {}
    } 
  }
  
  public JSONObject(JSONTokener paramJSONTokener) throws JSONException {
    this();
    if (paramJSONTokener.nextClean() != '{')
      throw paramJSONTokener.syntaxError("A JSONObject text must begin with '{'"); 
    while (true) {
      char c = paramJSONTokener.nextClean();
      switch (c) {
        case '\000':
          throw paramJSONTokener.syntaxError("A JSONObject text must end with '}'");
        case '}':
          return;
      } 
      paramJSONTokener.back();
      String str = paramJSONTokener.nextValue().toString();
      c = paramJSONTokener.nextClean();
      if (c != ':')
        throw paramJSONTokener.syntaxError("Expected a ':' after a key"); 
      if (str != null) {
        if (opt(str) != null)
          throw paramJSONTokener.syntaxError("Duplicate key \"" + str + "\""); 
        Object object = paramJSONTokener.nextValue();
        if (object != null)
          put(str, object); 
      } 
      switch (paramJSONTokener.nextClean()) {
        case ',':
        case ';':
          if (paramJSONTokener.nextClean() == '}')
            return; 
          paramJSONTokener.back();
          continue;
        case '}':
          return;
      } 
      break;
    } 
    throw paramJSONTokener.syntaxError("Expected a ',' or '}'");
  }
  
  public JSONObject(Map<?, ?> paramMap) {
    if (paramMap == null) {
      this.map = new HashMap<String, Object>();
    } else {
      this.map = new HashMap<String, Object>(paramMap.size());
      for (Map.Entry<?, ?> entry : paramMap.entrySet()) {
        Object object = entry.getValue();
        if (object != null)
          this.map.put(String.valueOf(entry.getKey()), wrap(object)); 
      } 
    } 
  }
  
  public JSONObject(Object paramObject) {
    this();
    populateMap(paramObject);
  }
  
  public JSONObject(Object paramObject, String[] paramArrayOfString) {
    this(paramArrayOfString.length);
    Class<?> clazz = paramObject.getClass();
    for (byte b = 0; b < paramArrayOfString.length; b++) {
      String str = paramArrayOfString[b];
      try {
        putOpt(str, clazz.getField(str).get(paramObject));
      } catch (Exception exception) {}
    } 
  }
  
  public JSONObject(String paramString) throws JSONException {
    this(new JSONTokener(paramString));
  }
  
  public JSONObject(String paramString, Locale paramLocale) throws JSONException {
    this();
    ResourceBundle resourceBundle = ResourceBundle.getBundle(paramString, paramLocale, 
        Thread.currentThread().getContextClassLoader());
    Enumeration<String> enumeration = resourceBundle.getKeys();
    while (enumeration.hasMoreElements()) {
      String str = (String)enumeration.nextElement();
      if (str != null) {
        String[] arrayOfString = ((String)str).split("\\.");
        int i = arrayOfString.length - 1;
        JSONObject jSONObject = this;
        for (byte b = 0; b < i; b++) {
          String str1 = arrayOfString[b];
          JSONObject jSONObject1 = jSONObject.optJSONObject(str1);
          if (jSONObject1 == null) {
            jSONObject1 = new JSONObject();
            jSONObject.put(str1, jSONObject1);
          } 
          jSONObject = jSONObject1;
        } 
        jSONObject.put(arrayOfString[i], resourceBundle.getString(str));
      } 
    } 
  }
  
  protected JSONObject(int paramInt) {
    this.map = new HashMap<String, Object>(paramInt);
  }
  
  public JSONObject accumulate(String paramString, Object paramObject) throws JSONException {
    testValidity(paramObject);
    Object object = opt(paramString);
    if (object == null) {
      put(paramString, (paramObject instanceof JSONArray) ? (new JSONArray())
          .put(paramObject) : paramObject);
    } else if (object instanceof JSONArray) {
      ((JSONArray)object).put(paramObject);
    } else {
      put(paramString, (new JSONArray()).put(object).put(paramObject));
    } 
    return this;
  }
  
  public JSONObject append(String paramString, Object paramObject) throws JSONException {
    testValidity(paramObject);
    Object object = opt(paramString);
    if (object == null) {
      put(paramString, (new JSONArray()).put(paramObject));
    } else if (object instanceof JSONArray) {
      put(paramString, ((JSONArray)object).put(paramObject));
    } else {
      throw new JSONException("JSONObject[" + paramString + "] is not a JSONArray.");
    } 
    return this;
  }
  
  public static String doubleToString(double paramDouble) {
    if (Double.isInfinite(paramDouble) || Double.isNaN(paramDouble))
      return "null"; 
    String str = Double.toString(paramDouble);
    if (str.indexOf('.') > 0 && str.indexOf('e') < 0 && str
      .indexOf('E') < 0) {
      while (str.endsWith("0"))
        str = str.substring(0, str.length() - 1); 
      if (str.endsWith("."))
        str = str.substring(0, str.length() - 1); 
    } 
    return str;
  }
  
  public Object get(String paramString) throws JSONException {
    if (paramString == null)
      throw new JSONException("Null key."); 
    Object object = opt(paramString);
    if (object == null)
      throw new JSONException("JSONObject[" + quote(paramString) + "] not found."); 
    return object;
  }
  
  public <E extends Enum<E>> E getEnum(Class<E> paramClass, String paramString) throws JSONException {
    E e = (E)optEnum((Class)paramClass, paramString);
    if (e == null)
      throw new JSONException("JSONObject[" + quote(paramString) + "] is not an enum of type " + 
          quote(paramClass.getSimpleName()) + "."); 
    return e;
  }
  
  public boolean getBoolean(String paramString) throws JSONException {
    Object object = get(paramString);
    if (object.equals(Boolean.FALSE) || (object instanceof String && ((String)object)
      
      .equalsIgnoreCase("false")))
      return false; 
    if (object.equals(Boolean.TRUE) || (object instanceof String && ((String)object)
      
      .equalsIgnoreCase("true")))
      return true; 
    throw new JSONException("JSONObject[" + quote(paramString) + "] is not a Boolean.");
  }
  
  public BigInteger getBigInteger(String paramString) throws JSONException {
    Object object = get(paramString);
    try {
      return new BigInteger(object.toString());
    } catch (Exception exception) {
      throw new JSONException("JSONObject[" + quote(paramString) + "] could not be converted to BigInteger.", exception);
    } 
  }
  
  public BigDecimal getBigDecimal(String paramString) throws JSONException {
    Object object = get(paramString);
    if (object instanceof BigDecimal)
      return (BigDecimal)object; 
    try {
      return new BigDecimal(object.toString());
    } catch (Exception exception) {
      throw new JSONException("JSONObject[" + quote(paramString) + "] could not be converted to BigDecimal.", exception);
    } 
  }
  
  public double getDouble(String paramString) throws JSONException {
    Object object = get(paramString);
    try {
      return (object instanceof Number) ? ((Number)object).doubleValue() : 
        Double.parseDouble(object.toString());
    } catch (Exception exception) {
      throw new JSONException("JSONObject[" + quote(paramString) + "] is not a number.", exception);
    } 
  }
  
  public float getFloat(String paramString) throws JSONException {
    Object object = get(paramString);
    try {
      return (object instanceof Number) ? ((Number)object).floatValue() : 
        Float.parseFloat(object.toString());
    } catch (Exception exception) {
      throw new JSONException("JSONObject[" + quote(paramString) + "] is not a number.", exception);
    } 
  }
  
  public Number getNumber(String paramString) throws JSONException {
    Object object = get(paramString);
    try {
      if (object instanceof Number)
        return (Number)object; 
      return stringToNumber(object.toString());
    } catch (Exception exception) {
      throw new JSONException("JSONObject[" + quote(paramString) + "] is not a number.", exception);
    } 
  }
  
  public int getInt(String paramString) throws JSONException {
    Object object = get(paramString);
    try {
      return (object instanceof Number) ? ((Number)object).intValue() : 
        Integer.parseInt((String)object);
    } catch (Exception exception) {
      throw new JSONException("JSONObject[" + quote(paramString) + "] is not an int.", exception);
    } 
  }
  
  public JSONArray getJSONArray(String paramString) throws JSONException {
    Object object = get(paramString);
    if (object instanceof JSONArray)
      return (JSONArray)object; 
    throw new JSONException("JSONObject[" + quote(paramString) + "] is not a JSONArray.");
  }
  
  public JSONObject getJSONObject(String paramString) throws JSONException {
    Object object = get(paramString);
    if (object instanceof JSONObject)
      return (JSONObject)object; 
    throw new JSONException("JSONObject[" + quote(paramString) + "] is not a JSONObject.");
  }
  
  public long getLong(String paramString) throws JSONException {
    Object object = get(paramString);
    try {
      return (object instanceof Number) ? ((Number)object).longValue() : 
        Long.parseLong((String)object);
    } catch (Exception exception) {
      throw new JSONException("JSONObject[" + quote(paramString) + "] is not a long.", exception);
    } 
  }
  
  public static String[] getNames(JSONObject paramJSONObject) {
    int i = paramJSONObject.length();
    if (i == 0)
      return null; 
    return paramJSONObject.keySet().<String>toArray(new String[i]);
  }
  
  public static String[] getNames(Object paramObject) {
    if (paramObject == null)
      return null; 
    Class<?> clazz = paramObject.getClass();
    Field[] arrayOfField = clazz.getFields();
    int i = arrayOfField.length;
    if (i == 0)
      return null; 
    String[] arrayOfString = new String[i];
    for (byte b = 0; b < i; b++)
      arrayOfString[b] = arrayOfField[b].getName(); 
    return arrayOfString;
  }
  
  public String getString(String paramString) throws JSONException {
    Object object = get(paramString);
    if (object instanceof String)
      return (String)object; 
    throw new JSONException("JSONObject[" + quote(paramString) + "] not a string.");
  }
  
  public boolean has(String paramString) {
    return this.map.containsKey(paramString);
  }
  
  public JSONObject increment(String paramString) throws JSONException {
    Object object = opt(paramString);
    if (object == null) {
      put(paramString, 1);
    } else if (object instanceof BigInteger) {
      put(paramString, ((BigInteger)object).add(BigInteger.ONE));
    } else if (object instanceof BigDecimal) {
      put(paramString, ((BigDecimal)object).add(BigDecimal.ONE));
    } else if (object instanceof Integer) {
      put(paramString, ((Integer)object).intValue() + 1);
    } else if (object instanceof Long) {
      put(paramString, ((Long)object).longValue() + 1L);
    } else if (object instanceof Double) {
      put(paramString, ((Double)object).doubleValue() + 1.0D);
    } else if (object instanceof Float) {
      put(paramString, ((Float)object).floatValue() + 1.0F);
    } else {
      throw new JSONException("Unable to increment [" + quote(paramString) + "].");
    } 
    return this;
  }
  
  public boolean isNull(String paramString) {
    return NULL.equals(opt(paramString));
  }
  
  public Iterator<String> keys() {
    return keySet().iterator();
  }
  
  public Set<String> keySet() {
    return this.map.keySet();
  }
  
  protected Set<Map.Entry<String, Object>> entrySet() {
    return this.map.entrySet();
  }
  
  public int length() {
    return this.map.size();
  }
  
  public JSONArray names() {
    if (this.map.isEmpty())
      return null; 
    return new JSONArray(this.map.keySet());
  }
  
  public static String numberToString(Number paramNumber) throws JSONException {
    if (paramNumber == null)
      throw new JSONException("Null pointer"); 
    testValidity(paramNumber);
    String str = paramNumber.toString();
    if (str.indexOf('.') > 0 && str.indexOf('e') < 0 && str
      .indexOf('E') < 0) {
      while (str.endsWith("0"))
        str = str.substring(0, str.length() - 1); 
      if (str.endsWith("."))
        str = str.substring(0, str.length() - 1); 
    } 
    return str;
  }
  
  public Object opt(String paramString) {
    return (paramString == null) ? null : this.map.get(paramString);
  }
  
  public <E extends Enum<E>> E optEnum(Class<E> paramClass, String paramString) {
    return optEnum(paramClass, paramString, null);
  }
  
  public <E extends Enum<E>> E optEnum(Class<E> paramClass, String paramString, E paramE) {
    try {
      Object object = opt(paramString);
      if (NULL.equals(object))
        return paramE; 
      if (paramClass.isAssignableFrom(object.getClass()))
        return (E)object; 
      return Enum.valueOf(paramClass, object.toString());
    } catch (IllegalArgumentException illegalArgumentException) {
      return paramE;
    } catch (NullPointerException nullPointerException) {
      return paramE;
    } 
  }
  
  public boolean optBoolean(String paramString) {
    return optBoolean(paramString, false);
  }
  
  public boolean optBoolean(String paramString, boolean paramBoolean) {
    Object object = opt(paramString);
    if (NULL.equals(object))
      return paramBoolean; 
    if (object instanceof Boolean)
      return ((Boolean)object).booleanValue(); 
    try {
      return getBoolean(paramString);
    } catch (Exception exception) {
      return paramBoolean;
    } 
  }
  
  public BigDecimal optBigDecimal(String paramString, BigDecimal paramBigDecimal) {
    Object object = opt(paramString);
    if (NULL.equals(object))
      return paramBigDecimal; 
    if (object instanceof BigDecimal)
      return (BigDecimal)object; 
    if (object instanceof BigInteger)
      return new BigDecimal((BigInteger)object); 
    if (object instanceof Double || object instanceof Float)
      return new BigDecimal(((Number)object).doubleValue()); 
    if (object instanceof Long || object instanceof Integer || object instanceof Short || object instanceof Byte)
      return new BigDecimal(((Number)object).longValue()); 
    try {
      return new BigDecimal(object.toString());
    } catch (Exception exception) {
      return paramBigDecimal;
    } 
  }
  
  public BigInteger optBigInteger(String paramString, BigInteger paramBigInteger) {
    Object object = opt(paramString);
    if (NULL.equals(object))
      return paramBigInteger; 
    if (object instanceof BigInteger)
      return (BigInteger)object; 
    if (object instanceof BigDecimal)
      return ((BigDecimal)object).toBigInteger(); 
    if (object instanceof Double || object instanceof Float)
      return (new BigDecimal(((Number)object).doubleValue())).toBigInteger(); 
    if (object instanceof Long || object instanceof Integer || object instanceof Short || object instanceof Byte)
      return BigInteger.valueOf(((Number)object).longValue()); 
    try {
      String str = object.toString();
      if (isDecimalNotation(str))
        return (new BigDecimal(str)).toBigInteger(); 
      return new BigInteger(str);
    } catch (Exception exception) {
      return paramBigInteger;
    } 
  }
  
  public double optDouble(String paramString) {
    return optDouble(paramString, Double.NaN);
  }
  
  public double optDouble(String paramString, double paramDouble) {
    Object object = opt(paramString);
    if (NULL.equals(object))
      return paramDouble; 
    if (object instanceof Number)
      return ((Number)object).doubleValue(); 
    if (object instanceof String)
      try {
        return Double.parseDouble((String)object);
      } catch (Exception exception) {
        return paramDouble;
      }  
    return paramDouble;
  }
  
  public float optFloat(String paramString) {
    return optFloat(paramString, Float.NaN);
  }
  
  public float optFloat(String paramString, float paramFloat) {
    Object object = opt(paramString);
    if (NULL.equals(object))
      return paramFloat; 
    if (object instanceof Number)
      return ((Number)object).floatValue(); 
    if (object instanceof String)
      try {
        return Float.parseFloat((String)object);
      } catch (Exception exception) {
        return paramFloat;
      }  
    return paramFloat;
  }
  
  public int optInt(String paramString) {
    return optInt(paramString, 0);
  }
  
  public int optInt(String paramString, int paramInt) {
    Object object = opt(paramString);
    if (NULL.equals(object))
      return paramInt; 
    if (object instanceof Number)
      return ((Number)object).intValue(); 
    if (object instanceof String)
      try {
        return (new BigDecimal((String)object)).intValue();
      } catch (Exception exception) {
        return paramInt;
      }  
    return paramInt;
  }
  
  public JSONArray optJSONArray(String paramString) {
    Object object = opt(paramString);
    return (object instanceof JSONArray) ? (JSONArray)object : null;
  }
  
  public JSONObject optJSONObject(String paramString) {
    Object object = opt(paramString);
    return (object instanceof JSONObject) ? (JSONObject)object : null;
  }
  
  public long optLong(String paramString) {
    return optLong(paramString, 0L);
  }
  
  public long optLong(String paramString, long paramLong) {
    Object object = opt(paramString);
    if (NULL.equals(object))
      return paramLong; 
    if (object instanceof Number)
      return ((Number)object).longValue(); 
    if (object instanceof String)
      try {
        return (new BigDecimal((String)object)).longValue();
      } catch (Exception exception) {
        return paramLong;
      }  
    return paramLong;
  }
  
  public Number optNumber(String paramString) {
    return optNumber(paramString, null);
  }
  
  public Number optNumber(String paramString, Number paramNumber) {
    Object object = opt(paramString);
    if (NULL.equals(object))
      return paramNumber; 
    if (object instanceof Number)
      return (Number)object; 
    if (object instanceof String)
      try {
        return stringToNumber((String)object);
      } catch (Exception exception) {
        return paramNumber;
      }  
    return paramNumber;
  }
  
  public String optString(String paramString) {
    return optString(paramString, "");
  }
  
  public String optString(String paramString1, String paramString2) {
    Object object = opt(paramString1);
    return NULL.equals(object) ? paramString2 : object.toString();
  }
  
  private void populateMap(Object paramObject) {
    Class<?> clazz = paramObject.getClass();
    boolean bool = (clazz.getClassLoader() != null) ? true : false;
    Method[] arrayOfMethod = bool ? clazz.getMethods() : clazz.getDeclaredMethods();
    for (Method method : arrayOfMethod) {
      int i = method.getModifiers();
      if (Modifier.isPublic(i) && 
        !Modifier.isStatic(i) && (method
        .getParameterTypes()).length == 0 && 
        !method.isBridge() && method
        .getReturnType() != void.class) {
        String str2, str1 = method.getName();
        if (str1.startsWith("get")) {
          if ("getClass".equals(str1) || "getDeclaringClass".equals(str1))
            continue; 
          str2 = str1.substring(3);
        } else if (str1.startsWith("is")) {
          str2 = str1.substring(2);
        } else {
          continue;
        } 
        if (str2.length() > 0 && 
          Character.isUpperCase(str2.charAt(0))) {
          if (str2.length() == 1) {
            str2 = str2.toLowerCase(Locale.ROOT);
          } else if (!Character.isUpperCase(str2.charAt(1))) {
            str2 = str2.substring(0, 1).toLowerCase(Locale.ROOT) + str2.substring(1);
          } 
          try {
            Object object = method.invoke(paramObject, new Object[0]);
            if (object != null) {
              this.map.put(str2, wrap(object));
              if (object instanceof Closeable)
                try {
                  ((Closeable)object).close();
                } catch (IOException iOException) {} 
            } 
          } catch (IllegalAccessException illegalAccessException) {
          
          } catch (IllegalArgumentException illegalArgumentException) {
          
          } catch (InvocationTargetException invocationTargetException) {}
        } 
      } 
      continue;
    } 
  }
  
  public JSONObject put(String paramString, boolean paramBoolean) throws JSONException {
    put(paramString, paramBoolean ? Boolean.TRUE : Boolean.FALSE);
    return this;
  }
  
  public JSONObject put(String paramString, Collection<?> paramCollection) throws JSONException {
    put(paramString, new JSONArray(paramCollection));
    return this;
  }
  
  public JSONObject put(String paramString, double paramDouble) throws JSONException {
    put(paramString, Double.valueOf(paramDouble));
    return this;
  }
  
  public JSONObject put(String paramString, float paramFloat) throws JSONException {
    put(paramString, Float.valueOf(paramFloat));
    return this;
  }
  
  public JSONObject put(String paramString, int paramInt) throws JSONException {
    put(paramString, Integer.valueOf(paramInt));
    return this;
  }
  
  public JSONObject put(String paramString, long paramLong) throws JSONException {
    put(paramString, Long.valueOf(paramLong));
    return this;
  }
  
  public JSONObject put(String paramString, Map<?, ?> paramMap) throws JSONException {
    put(paramString, new JSONObject(paramMap));
    return this;
  }
  
  public JSONObject put(String paramString, Object paramObject) throws JSONException {
    if (paramString == null)
      throw new NullPointerException("Null key."); 
    if (paramObject != null) {
      testValidity(paramObject);
      this.map.put(paramString, paramObject);
    } else {
      remove(paramString);
    } 
    return this;
  }
  
  public JSONObject putOnce(String paramString, Object paramObject) throws JSONException {
    if (paramString != null && paramObject != null) {
      if (opt(paramString) != null)
        throw new JSONException("Duplicate key \"" + paramString + "\""); 
      put(paramString, paramObject);
    } 
    return this;
  }
  
  public JSONObject putOpt(String paramString, Object paramObject) throws JSONException {
    if (paramString != null && paramObject != null)
      put(paramString, paramObject); 
    return this;
  }
  
  public Object query(String paramString) {
    return query(new JSONPointer(paramString));
  }
  
  public Object query(JSONPointer paramJSONPointer) {
    return paramJSONPointer.queryFrom(this);
  }
  
  public Object optQuery(String paramString) {
    return optQuery(new JSONPointer(paramString));
  }
  
  public Object optQuery(JSONPointer paramJSONPointer) {
    try {
      return paramJSONPointer.queryFrom(this);
    } catch (JSONPointerException jSONPointerException) {
      return null;
    } 
  }
  
  public static String quote(String paramString) {
    StringWriter stringWriter = new StringWriter();
    synchronized (stringWriter.getBuffer()) {
      return quote(paramString, stringWriter).toString();
    } 
  }
  
  public static Writer quote(String paramString, Writer paramWriter) throws IOException {
    if (paramString == null || paramString.length() == 0) {
      paramWriter.write("\"\"");
      return paramWriter;
    } 
    char c = Character.MIN_VALUE;
    int i = paramString.length();
    paramWriter.write(34);
    for (byte b = 0; b < i; b++) {
      char c1 = c;
      c = paramString.charAt(b);
      switch (c) {
        case '"':
        case '\\':
          paramWriter.write(92);
          paramWriter.write(c);
          break;
        case '/':
          if (c1 == '<')
            paramWriter.write(92); 
          paramWriter.write(c);
          break;
        case '\b':
          paramWriter.write("\\b");
          break;
        case '\t':
          paramWriter.write("\\t");
          break;
        case '\n':
          paramWriter.write("\\n");
          break;
        case '\f':
          paramWriter.write("\\f");
          break;
        case '\r':
          paramWriter.write("\\r");
          break;
        default:
          if (c < ' ' || (c >= '' && c < ' ') || (c >= ' ' && c < '℀')) {
            paramWriter.write("\\u");
            String str = Integer.toHexString(c);
            paramWriter.write("0000", 0, 4 - str.length());
            paramWriter.write(str);
            break;
          } 
          paramWriter.write(c);
          break;
      } 
    } 
    paramWriter.write(34);
    return paramWriter;
  }
  
  public Object remove(String paramString) {
    return this.map.remove(paramString);
  }
  
  public boolean similar(Object paramObject) {
    try {
      if (!(paramObject instanceof JSONObject))
        return false; 
      if (!keySet().equals(((JSONObject)paramObject).keySet()))
        return false; 
      for (Map.Entry<String, Object> entry : entrySet()) {
        String str = (String)entry.getKey();
        Object object1 = entry.getValue();
        Object object2 = ((JSONObject)paramObject).get(str);
        if (object1 == object2)
          return true; 
        if (object1 == null)
          return false; 
        if (object1 instanceof JSONObject) {
          if (!((JSONObject)object1).similar(object2))
            return false; 
          continue;
        } 
        if (object1 instanceof JSONArray) {
          if (!((JSONArray)object1).similar(object2))
            return false; 
          continue;
        } 
        if (!object1.equals(object2))
          return false; 
      } 
      return true;
    } catch (Throwable throwable) {
      return false;
    } 
  }
  
  protected static boolean isDecimalNotation(String paramString) {
    return (paramString.indexOf('.') > -1 || paramString.indexOf('e') > -1 || paramString
      .indexOf('E') > -1 || "-0".equals(paramString));
  }
  
  protected static Number stringToNumber(String paramString) throws NumberFormatException {
    char c = paramString.charAt(0);
    if ((c >= '0' && c <= '9') || c == '-') {
      if (isDecimalNotation(paramString)) {
        if (paramString.length() > 14)
          return new BigDecimal(paramString); 
        Double double_ = Double.valueOf(paramString);
        if (double_.isInfinite() || double_.isNaN())
          return new BigDecimal(paramString); 
        return double_;
      } 
      BigInteger bigInteger = new BigInteger(paramString);
      if (bigInteger.bitLength() <= 31)
        return Integer.valueOf(bigInteger.intValue()); 
      if (bigInteger.bitLength() <= 63)
        return Long.valueOf(bigInteger.longValue()); 
      return bigInteger;
    } 
    throw new NumberFormatException("val [" + paramString + "] is not a valid number.");
  }
  
  public static Object stringToValue(String paramString) {
    if (paramString.equals(""))
      return paramString; 
    if (paramString.equalsIgnoreCase("true"))
      return Boolean.TRUE; 
    if (paramString.equalsIgnoreCase("false"))
      return Boolean.FALSE; 
    if (paramString.equalsIgnoreCase("null"))
      return NULL; 
    char c = paramString.charAt(0);
    if ((c >= '0' && c <= '9') || c == '-')
      try {
        if (isDecimalNotation(paramString)) {
          Double double_ = Double.valueOf(paramString);
          if (!double_.isInfinite() && !double_.isNaN())
            return double_; 
        } else {
          Long long_ = Long.valueOf(paramString);
          if (paramString.equals(long_.toString())) {
            if (long_.longValue() == long_.intValue())
              return Integer.valueOf(long_.intValue()); 
            return long_;
          } 
        } 
      } catch (Exception exception) {} 
    return paramString;
  }
  
  public static void testValidity(Object paramObject) throws JSONException {
    if (paramObject != null)
      if (paramObject instanceof Double) {
        if (((Double)paramObject).isInfinite() || ((Double)paramObject).isNaN())
          throw new JSONException("JSON does not allow non-finite numbers."); 
      } else if (paramObject instanceof Float && ((
        (Float)paramObject).isInfinite() || ((Float)paramObject).isNaN())) {
        throw new JSONException("JSON does not allow non-finite numbers.");
      }  
  }
  
  public JSONArray toJSONArray(JSONArray paramJSONArray) throws JSONException {
    if (paramJSONArray == null || paramJSONArray.length() == 0)
      return null; 
    JSONArray jSONArray = new JSONArray();
    for (byte b = 0; b < paramJSONArray.length(); b++)
      jSONArray.put(opt(paramJSONArray.getString(b))); 
    return jSONArray;
  }
  
  public String toString() {
    try {
      return toString(0);
    } catch (Exception exception) {
      return null;
    } 
  }
  
  public String toString(int paramInt) throws JSONException {
    StringWriter stringWriter = new StringWriter();
    synchronized (stringWriter.getBuffer()) {
      return write(stringWriter, paramInt, 0).toString();
    } 
  }
  
  public static String valueToString(Object paramObject) throws JSONException {
    if (paramObject == null || paramObject.equals(null))
      return "null"; 
    if (paramObject instanceof JSONString) {
      String str;
      try {
        str = ((JSONString)paramObject).toJSONString();
      } catch (Exception exception) {
        throw new JSONException(exception);
      } 
      if (str instanceof String)
        return str; 
      throw new JSONException("Bad value from toJSONString: " + str);
    } 
    if (paramObject instanceof Number) {
      String str = numberToString((Number)paramObject);
      try {
        BigDecimal bigDecimal = new BigDecimal(str);
        return str;
      } catch (NumberFormatException numberFormatException) {
        return quote(str);
      } 
    } 
    if (paramObject instanceof Boolean || paramObject instanceof JSONObject || paramObject instanceof JSONArray)
      return paramObject.toString(); 
    if (paramObject instanceof Map) {
      Map<?, ?> map = (Map)paramObject;
      return (new JSONObject(map)).toString();
    } 
    if (paramObject instanceof Collection) {
      Collection<?> collection = (Collection)paramObject;
      return (new JSONArray(collection)).toString();
    } 
    if (paramObject.getClass().isArray())
      return (new JSONArray(paramObject)).toString(); 
    if (paramObject instanceof Enum)
      return quote(((Enum)paramObject).name()); 
    return quote(paramObject.toString());
  }
  
  public static Object wrap(Object paramObject) {
    try {
      if (paramObject == null)
        return NULL; 
      if (paramObject instanceof JSONObject || paramObject instanceof JSONArray || NULL
        .equals(paramObject) || paramObject instanceof JSONString || paramObject instanceof Byte || paramObject instanceof Character || paramObject instanceof Short || paramObject instanceof Integer || paramObject instanceof Long || paramObject instanceof Boolean || paramObject instanceof Float || paramObject instanceof Double || paramObject instanceof String || paramObject instanceof BigInteger || paramObject instanceof BigDecimal || paramObject instanceof Enum)
        return paramObject; 
      if (paramObject instanceof Collection) {
        Collection<?> collection = (Collection)paramObject;
        return new JSONArray(collection);
      } 
      if (paramObject.getClass().isArray())
        return new JSONArray(paramObject); 
      if (paramObject instanceof Map) {
        Map<?, ?> map = (Map)paramObject;
        return new JSONObject(map);
      } 
      Package package_ = paramObject.getClass().getPackage();
      String str = (package_ != null) ? package_.getName() : "";
      if (str.startsWith("java.") || str
        .startsWith("javax.") || paramObject
        .getClass().getClassLoader() == null)
        return paramObject.toString(); 
      return new JSONObject(paramObject);
    } catch (Exception exception) {
      return null;
    } 
  }
  
  public Writer write(Writer paramWriter) throws JSONException {
    return write(paramWriter, 0, 0);
  }
  
  static final Writer writeValue(Writer paramWriter, Object paramObject, int paramInt1, int paramInt2) throws JSONException, IOException {
    if (paramObject == null || paramObject.equals(null)) {
      paramWriter.write("null");
    } else if (paramObject instanceof JSONString) {
      String str;
      try {
        str = ((JSONString)paramObject).toJSONString();
      } catch (Exception exception) {
        throw new JSONException(exception);
      } 
      paramWriter.write((str != null) ? str.toString() : quote(paramObject.toString()));
    } else if (paramObject instanceof Number) {
      String str = numberToString((Number)paramObject);
      try {
        BigDecimal bigDecimal = new BigDecimal(str);
        paramWriter.write(str);
      } catch (NumberFormatException numberFormatException) {
        quote(str, paramWriter);
      } 
    } else if (paramObject instanceof Boolean) {
      paramWriter.write(paramObject.toString());
    } else if (paramObject instanceof Enum) {
      paramWriter.write(quote(((Enum)paramObject).name()));
    } else if (paramObject instanceof JSONObject) {
      ((JSONObject)paramObject).write(paramWriter, paramInt1, paramInt2);
    } else if (paramObject instanceof JSONArray) {
      ((JSONArray)paramObject).write(paramWriter, paramInt1, paramInt2);
    } else if (paramObject instanceof Map) {
      Map<?, ?> map = (Map)paramObject;
      (new JSONObject(map)).write(paramWriter, paramInt1, paramInt2);
    } else if (paramObject instanceof Collection) {
      Collection<?> collection = (Collection)paramObject;
      (new JSONArray(collection)).write(paramWriter, paramInt1, paramInt2);
    } else if (paramObject.getClass().isArray()) {
      (new JSONArray(paramObject)).write(paramWriter, paramInt1, paramInt2);
    } else {
      quote(paramObject.toString(), paramWriter);
    } 
    return paramWriter;
  }
  
  static final void indent(Writer paramWriter, int paramInt) throws IOException {
    for (byte b = 0; b < paramInt; b++)
      paramWriter.write(32); 
  }
  
  public Writer write(Writer paramWriter, int paramInt1, int paramInt2) throws JSONException {
    try {
      boolean bool = false;
      int i = length();
      paramWriter.write(123);
      if (i == 1) {
        Map.Entry entry = entrySet().iterator().next();
        String str = (String)entry.getKey();
        paramWriter.write(quote(str));
        paramWriter.write(58);
        if (paramInt1 > 0)
          paramWriter.write(32); 
        try {
          writeValue(paramWriter, entry.getValue(), paramInt1, paramInt2);
        } catch (Exception exception) {
          throw new JSONException("Unable to write JSONObject value for key: " + str, exception);
        } 
      } else if (i != 0) {
        int j = paramInt2 + paramInt1;
        for (Map.Entry<String, Object> entry : entrySet()) {
          if (bool)
            paramWriter.write(44); 
          if (paramInt1 > 0)
            paramWriter.write(10); 
          indent(paramWriter, j);
          String str = (String)entry.getKey();
          paramWriter.write(quote(str));
          paramWriter.write(58);
          if (paramInt1 > 0)
            paramWriter.write(32); 
          try {
            writeValue(paramWriter, entry.getValue(), paramInt1, j);
          } catch (Exception exception) {
            throw new JSONException("Unable to write JSONObject value for key: " + str, exception);
          } 
          bool = true;
        } 
        if (paramInt1 > 0)
          paramWriter.write(10); 
        indent(paramWriter, paramInt2);
      } 
      paramWriter.write(125);
      return paramWriter;
    } catch (IOException iOException) {
      throw new JSONException(iOException);
    } 
  }
  
  public Map<String, Object> toMap() {
    HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
    for (Map.Entry<String, Object> entry : entrySet()) {
      Object object;
      if (entry.getValue() == null || NULL.equals(entry.getValue())) {
        object = null;
      } else if (entry.getValue() instanceof JSONObject) {
        object = ((JSONObject)entry.getValue()).toMap();
      } else if (entry.getValue() instanceof JSONArray) {
        object = ((JSONArray)entry.getValue()).toList();
      } else {
        object = entry.getValue();
      } 
      hashMap.put(entry.getKey(), object);
    } 
    return (Map)hashMap;
  }
}
