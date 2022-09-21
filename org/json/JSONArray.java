package org.json;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class JSONArray implements Iterable<Object> {
  private final ArrayList<Object> myArrayList;
  
  public JSONArray() {
    this.myArrayList = new ArrayList();
  }
  
  public JSONArray(JSONTokener paramJSONTokener) throws JSONException {
    this();
    if (paramJSONTokener.nextClean() != '[')
      throw paramJSONTokener.syntaxError("A JSONArray text must start with '['"); 
    if (paramJSONTokener.nextClean() != ']') {
      paramJSONTokener.back();
      while (true) {
        if (paramJSONTokener.nextClean() == ',') {
          paramJSONTokener.back();
          this.myArrayList.add(JSONObject.NULL);
        } else {
          paramJSONTokener.back();
          this.myArrayList.add(paramJSONTokener.nextValue());
        } 
        switch (paramJSONTokener.nextClean()) {
          case ',':
            if (paramJSONTokener.nextClean() == ']')
              return; 
            paramJSONTokener.back();
            continue;
          case ']':
            return;
        } 
        break;
      } 
      throw paramJSONTokener.syntaxError("Expected a ',' or ']'");
    } 
  }
  
  public JSONArray(String paramString) throws JSONException {
    this(new JSONTokener(paramString));
  }
  
  public JSONArray(Collection<?> paramCollection) {
    if (paramCollection == null) {
      this.myArrayList = new ArrayList();
    } else {
      this.myArrayList = new ArrayList(paramCollection.size());
      for (Object object : paramCollection)
        this.myArrayList.add(JSONObject.wrap(object)); 
    } 
  }
  
  public JSONArray(Object paramObject) throws JSONException {
    this();
    if (paramObject.getClass().isArray()) {
      int i = Array.getLength(paramObject);
      this.myArrayList.ensureCapacity(i);
      for (byte b = 0; b < i; b++)
        put(JSONObject.wrap(Array.get(paramObject, b))); 
    } else {
      throw new JSONException("JSONArray initial value should be a string or collection or array.");
    } 
  }
  
  public Iterator<Object> iterator() {
    return this.myArrayList.iterator();
  }
  
  public Object get(int paramInt) throws JSONException {
    Object object = opt(paramInt);
    if (object == null)
      throw new JSONException("JSONArray[" + paramInt + "] not found."); 
    return object;
  }
  
  public boolean getBoolean(int paramInt) throws JSONException {
    Object object = get(paramInt);
    if (object.equals(Boolean.FALSE) || (object instanceof String && ((String)object)
      
      .equalsIgnoreCase("false")))
      return false; 
    if (object.equals(Boolean.TRUE) || (object instanceof String && ((String)object)
      
      .equalsIgnoreCase("true")))
      return true; 
    throw new JSONException("JSONArray[" + paramInt + "] is not a boolean.");
  }
  
  public double getDouble(int paramInt) throws JSONException {
    Object object = get(paramInt);
    try {
      return (object instanceof Number) ? ((Number)object).doubleValue() : 
        Double.parseDouble((String)object);
    } catch (Exception exception) {
      throw new JSONException("JSONArray[" + paramInt + "] is not a number.", exception);
    } 
  }
  
  public float getFloat(int paramInt) throws JSONException {
    Object object = get(paramInt);
    try {
      return (object instanceof Number) ? ((Number)object).floatValue() : 
        Float.parseFloat(object.toString());
    } catch (Exception exception) {
      throw new JSONException("JSONArray[" + paramInt + "] is not a number.", exception);
    } 
  }
  
  public Number getNumber(int paramInt) throws JSONException {
    Object object = get(paramInt);
    try {
      if (object instanceof Number)
        return (Number)object; 
      return JSONObject.stringToNumber(object.toString());
    } catch (Exception exception) {
      throw new JSONException("JSONArray[" + paramInt + "] is not a number.", exception);
    } 
  }
  
  public <E extends Enum<E>> E getEnum(Class<E> paramClass, int paramInt) throws JSONException {
    E e = (E)optEnum((Class)paramClass, paramInt);
    if (e == null)
      throw new JSONException("JSONArray[" + paramInt + "] is not an enum of type " + 
          JSONObject.quote(paramClass.getSimpleName()) + "."); 
    return e;
  }
  
  public BigDecimal getBigDecimal(int paramInt) throws JSONException {
    Object object = get(paramInt);
    try {
      return new BigDecimal(object.toString());
    } catch (Exception exception) {
      throw new JSONException("JSONArray[" + paramInt + "] could not convert to BigDecimal.", exception);
    } 
  }
  
  public BigInteger getBigInteger(int paramInt) throws JSONException {
    Object object = get(paramInt);
    try {
      return new BigInteger(object.toString());
    } catch (Exception exception) {
      throw new JSONException("JSONArray[" + paramInt + "] could not convert to BigInteger.", exception);
    } 
  }
  
  public int getInt(int paramInt) throws JSONException {
    Object object = get(paramInt);
    try {
      return (object instanceof Number) ? ((Number)object).intValue() : 
        Integer.parseInt((String)object);
    } catch (Exception exception) {
      throw new JSONException("JSONArray[" + paramInt + "] is not a number.", exception);
    } 
  }
  
  public JSONArray getJSONArray(int paramInt) throws JSONException {
    Object object = get(paramInt);
    if (object instanceof JSONArray)
      return (JSONArray)object; 
    throw new JSONException("JSONArray[" + paramInt + "] is not a JSONArray.");
  }
  
  public JSONObject getJSONObject(int paramInt) throws JSONException {
    Object object = get(paramInt);
    if (object instanceof JSONObject)
      return (JSONObject)object; 
    throw new JSONException("JSONArray[" + paramInt + "] is not a JSONObject.");
  }
  
  public long getLong(int paramInt) throws JSONException {
    Object object = get(paramInt);
    try {
      return (object instanceof Number) ? ((Number)object).longValue() : 
        Long.parseLong((String)object);
    } catch (Exception exception) {
      throw new JSONException("JSONArray[" + paramInt + "] is not a number.", exception);
    } 
  }
  
  public String getString(int paramInt) throws JSONException {
    Object object = get(paramInt);
    if (object instanceof String)
      return (String)object; 
    throw new JSONException("JSONArray[" + paramInt + "] not a string.");
  }
  
  public boolean isNull(int paramInt) {
    return JSONObject.NULL.equals(opt(paramInt));
  }
  
  public String join(String paramString) throws JSONException {
    int i = length();
    StringBuilder stringBuilder = new StringBuilder();
    for (byte b = 0; b < i; b++) {
      if (b > 0)
        stringBuilder.append(paramString); 
      stringBuilder.append(JSONObject.valueToString(this.myArrayList.get(b)));
    } 
    return stringBuilder.toString();
  }
  
  public int length() {
    return this.myArrayList.size();
  }
  
  public Object opt(int paramInt) {
    return (paramInt < 0 || paramInt >= length()) ? null : this.myArrayList
      .get(paramInt);
  }
  
  public boolean optBoolean(int paramInt) {
    return optBoolean(paramInt, false);
  }
  
  public boolean optBoolean(int paramInt, boolean paramBoolean) {
    try {
      return getBoolean(paramInt);
    } catch (Exception exception) {
      return paramBoolean;
    } 
  }
  
  public double optDouble(int paramInt) {
    return optDouble(paramInt, Double.NaN);
  }
  
  public double optDouble(int paramInt, double paramDouble) {
    Object object = opt(paramInt);
    if (JSONObject.NULL.equals(object))
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
  
  public float optFloat(int paramInt) {
    return optFloat(paramInt, Float.NaN);
  }
  
  public float optFloat(int paramInt, float paramFloat) {
    Object object = opt(paramInt);
    if (JSONObject.NULL.equals(object))
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
  
  public int optInt(int paramInt) {
    return optInt(paramInt, 0);
  }
  
  public int optInt(int paramInt1, int paramInt2) {
    Object object = opt(paramInt1);
    if (JSONObject.NULL.equals(object))
      return paramInt2; 
    if (object instanceof Number)
      return ((Number)object).intValue(); 
    if (object instanceof String)
      try {
        return (new BigDecimal(object.toString())).intValue();
      } catch (Exception exception) {
        return paramInt2;
      }  
    return paramInt2;
  }
  
  public <E extends Enum<E>> E optEnum(Class<E> paramClass, int paramInt) {
    return optEnum(paramClass, paramInt, null);
  }
  
  public <E extends Enum<E>> E optEnum(Class<E> paramClass, int paramInt, E paramE) {
    try {
      Object object = opt(paramInt);
      if (JSONObject.NULL.equals(object))
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
  
  public BigInteger optBigInteger(int paramInt, BigInteger paramBigInteger) {
    Object object = opt(paramInt);
    if (JSONObject.NULL.equals(object))
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
      if (JSONObject.isDecimalNotation(str))
        return (new BigDecimal(str)).toBigInteger(); 
      return new BigInteger(str);
    } catch (Exception exception) {
      return paramBigInteger;
    } 
  }
  
  public BigDecimal optBigDecimal(int paramInt, BigDecimal paramBigDecimal) {
    Object object = opt(paramInt);
    if (JSONObject.NULL.equals(object))
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
  
  public JSONArray optJSONArray(int paramInt) {
    Object object = opt(paramInt);
    return (object instanceof JSONArray) ? (JSONArray)object : null;
  }
  
  public JSONObject optJSONObject(int paramInt) {
    Object object = opt(paramInt);
    return (object instanceof JSONObject) ? (JSONObject)object : null;
  }
  
  public long optLong(int paramInt) {
    return optLong(paramInt, 0L);
  }
  
  public long optLong(int paramInt, long paramLong) {
    Object object = opt(paramInt);
    if (JSONObject.NULL.equals(object))
      return paramLong; 
    if (object instanceof Number)
      return ((Number)object).longValue(); 
    if (object instanceof String)
      try {
        return (new BigDecimal(object.toString())).longValue();
      } catch (Exception exception) {
        return paramLong;
      }  
    return paramLong;
  }
  
  public Number optNumber(int paramInt) {
    return optNumber(paramInt, null);
  }
  
  public Number optNumber(int paramInt, Number paramNumber) {
    Object object = opt(paramInt);
    if (JSONObject.NULL.equals(object))
      return paramNumber; 
    if (object instanceof Number)
      return (Number)object; 
    if (object instanceof String)
      try {
        return JSONObject.stringToNumber((String)object);
      } catch (Exception exception) {
        return paramNumber;
      }  
    return paramNumber;
  }
  
  public String optString(int paramInt) {
    return optString(paramInt, "");
  }
  
  public String optString(int paramInt, String paramString) {
    Object object = opt(paramInt);
    return JSONObject.NULL.equals(object) ? paramString : object
      .toString();
  }
  
  public JSONArray put(boolean paramBoolean) {
    put(paramBoolean ? Boolean.TRUE : Boolean.FALSE);
    return this;
  }
  
  public JSONArray put(Collection<?> paramCollection) {
    put(new JSONArray(paramCollection));
    return this;
  }
  
  public JSONArray put(double paramDouble) throws JSONException {
    Double double_ = new Double(paramDouble);
    JSONObject.testValidity(double_);
    put(double_);
    return this;
  }
  
  public JSONArray put(int paramInt) {
    put(new Integer(paramInt));
    return this;
  }
  
  public JSONArray put(long paramLong) {
    put(new Long(paramLong));
    return this;
  }
  
  public JSONArray put(Map<?, ?> paramMap) {
    put(new JSONObject(paramMap));
    return this;
  }
  
  public JSONArray put(Object paramObject) {
    this.myArrayList.add(paramObject);
    return this;
  }
  
  public JSONArray put(int paramInt, boolean paramBoolean) throws JSONException {
    put(paramInt, paramBoolean ? Boolean.TRUE : Boolean.FALSE);
    return this;
  }
  
  public JSONArray put(int paramInt, Collection<?> paramCollection) throws JSONException {
    put(paramInt, new JSONArray(paramCollection));
    return this;
  }
  
  public JSONArray put(int paramInt, double paramDouble) throws JSONException {
    put(paramInt, new Double(paramDouble));
    return this;
  }
  
  public JSONArray put(int paramInt1, int paramInt2) throws JSONException {
    put(paramInt1, new Integer(paramInt2));
    return this;
  }
  
  public JSONArray put(int paramInt, long paramLong) throws JSONException {
    put(paramInt, new Long(paramLong));
    return this;
  }
  
  public JSONArray put(int paramInt, Map<?, ?> paramMap) throws JSONException {
    put(paramInt, new JSONObject(paramMap));
    return this;
  }
  
  public JSONArray put(int paramInt, Object paramObject) throws JSONException {
    JSONObject.testValidity(paramObject);
    if (paramInt < 0)
      throw new JSONException("JSONArray[" + paramInt + "] not found."); 
    if (paramInt < length()) {
      this.myArrayList.set(paramInt, paramObject);
    } else if (paramInt == length()) {
      put(paramObject);
    } else {
      this.myArrayList.ensureCapacity(paramInt + 1);
      while (paramInt != length())
        put(JSONObject.NULL); 
      put(paramObject);
    } 
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
  
  public Object remove(int paramInt) {
    return (paramInt >= 0 && paramInt < length()) ? this.myArrayList
      .remove(paramInt) : null;
  }
  
  public boolean similar(Object paramObject) {
    if (!(paramObject instanceof JSONArray))
      return false; 
    int i = length();
    if (i != ((JSONArray)paramObject).length())
      return false; 
    for (byte b = 0; b < i; b++) {
      Object object1 = this.myArrayList.get(b);
      Object object2 = ((JSONArray)paramObject).myArrayList.get(b);
      if (object1 == object2)
        return true; 
      if (object1 == null)
        return false; 
      if (object1 instanceof JSONObject) {
        if (!((JSONObject)object1).similar(object2))
          return false; 
      } else if (object1 instanceof JSONArray) {
        if (!((JSONArray)object1).similar(object2))
          return false; 
      } else if (!object1.equals(object2)) {
        return false;
      } 
    } 
    return true;
  }
  
  public JSONObject toJSONObject(JSONArray paramJSONArray) throws JSONException {
    if (paramJSONArray == null || paramJSONArray.length() == 0 || length() == 0)
      return null; 
    JSONObject jSONObject = new JSONObject(paramJSONArray.length());
    for (byte b = 0; b < paramJSONArray.length(); b++)
      jSONObject.put(paramJSONArray.getString(b), opt(b)); 
    return jSONObject;
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
  
  public Writer write(Writer paramWriter) throws JSONException {
    return write(paramWriter, 0, 0);
  }
  
  public Writer write(Writer paramWriter, int paramInt1, int paramInt2) throws JSONException {
    try {
      boolean bool = false;
      int i = length();
      paramWriter.write(91);
      if (i == 1) {
        try {
          JSONObject.writeValue(paramWriter, this.myArrayList.get(0), paramInt1, paramInt2);
        } catch (Exception exception) {
          throw new JSONException("Unable to write JSONArray value at index: 0", exception);
        } 
      } else if (i != 0) {
        int j = paramInt2 + paramInt1;
        for (byte b = 0; b < i; b++) {
          if (bool)
            paramWriter.write(44); 
          if (paramInt1 > 0)
            paramWriter.write(10); 
          JSONObject.indent(paramWriter, j);
          try {
            JSONObject.writeValue(paramWriter, this.myArrayList.get(b), paramInt1, j);
          } catch (Exception exception) {
            throw new JSONException("Unable to write JSONArray value at index: " + b, exception);
          } 
          bool = true;
        } 
        if (paramInt1 > 0)
          paramWriter.write(10); 
        JSONObject.indent(paramWriter, paramInt2);
      } 
      paramWriter.write(93);
      return paramWriter;
    } catch (IOException iOException) {
      throw new JSONException(iOException);
    } 
  }
  
  public List<Object> toList() {
    ArrayList<List<Object>> arrayList = new ArrayList(this.myArrayList.size());
    for (JSONArray jSONArray : this.myArrayList) {
      if (jSONArray == null || JSONObject.NULL.equals(jSONArray)) {
        arrayList.add(null);
        continue;
      } 
      if (jSONArray instanceof JSONArray) {
        arrayList.add(((JSONArray)jSONArray).toList());
        continue;
      } 
      if (jSONArray instanceof JSONObject) {
        arrayList.add(((JSONObject)jSONArray).toMap());
        continue;
      } 
      arrayList.add(jSONArray);
    } 
    return (List)arrayList;
  }
}
