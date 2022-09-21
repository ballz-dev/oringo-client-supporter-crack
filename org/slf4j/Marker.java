package org.slf4j;

import java.io.Serializable;
import java.util.Iterator;

public interface Marker extends Serializable {
  public static final String ANY_NON_NULL_MARKER = "+";
  
  public static final String ANY_MARKER = "*";
  
  boolean hasReferences();
  
  void add(Marker paramMarker);
  
  boolean hasChildren();
  
  Iterator<Marker> iterator();
  
  boolean equals(Object paramObject);
  
  int hashCode();
  
  boolean remove(Marker paramMarker);
  
  boolean contains(String paramString);
  
  String getName();
  
  boolean contains(Marker paramMarker);
}
