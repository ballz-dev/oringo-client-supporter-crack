package org.spongepowered.tools.obfuscation;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ObfuscationData<T> implements Iterable<ObfuscationType> {
  private final Map<ObfuscationType, T> data = new HashMap<ObfuscationType, T>();
  
  private final T defaultValue;
  
  public ObfuscationData() {
    this(null);
  }
  
  public ObfuscationData(T paramT) {
    this.defaultValue = paramT;
  }
  
  @Deprecated
  public void add(ObfuscationType paramObfuscationType, T paramT) {
    put(paramObfuscationType, paramT);
  }
  
  public void put(ObfuscationType paramObfuscationType, T paramT) {
    this.data.put(paramObfuscationType, paramT);
  }
  
  public boolean isEmpty() {
    return this.data.isEmpty();
  }
  
  public T get(ObfuscationType paramObfuscationType) {
    T t = this.data.get(paramObfuscationType);
    return (t != null) ? t : this.defaultValue;
  }
  
  public Iterator<ObfuscationType> iterator() {
    return this.data.keySet().iterator();
  }
  
  public String toString() {
    return String.format("ObfuscationData[%s,DEFAULT=%s]", new Object[] { listValues(), this.defaultValue });
  }
  
  public String values() {
    return "[" + listValues() + "]";
  }
  
  private String listValues() {
    StringBuilder stringBuilder = new StringBuilder();
    boolean bool = false;
    for (ObfuscationType obfuscationType : this.data.keySet()) {
      if (bool)
        stringBuilder.append(','); 
      stringBuilder.append(obfuscationType.getKey()).append('=').append(this.data.get(obfuscationType));
      bool = true;
    } 
    return stringBuilder.toString();
  }
}
