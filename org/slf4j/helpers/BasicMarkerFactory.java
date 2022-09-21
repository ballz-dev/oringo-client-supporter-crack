package org.slf4j.helpers;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.slf4j.IMarkerFactory;
import org.slf4j.Marker;

public class BasicMarkerFactory implements IMarkerFactory {
  private final ConcurrentMap<String, Marker> markerMap = new ConcurrentHashMap<String, Marker>();
  
  public Marker getMarker(String paramString) {
    if (paramString == null)
      throw new IllegalArgumentException("Marker name cannot be null"); 
    Marker marker = this.markerMap.get(paramString);
    if (marker == null) {
      marker = new BasicMarker(paramString);
      Marker marker1 = this.markerMap.putIfAbsent(paramString, marker);
      if (marker1 != null)
        marker = marker1; 
    } 
    return marker;
  }
  
  public boolean exists(String paramString) {
    if (paramString == null)
      return false; 
    return this.markerMap.containsKey(paramString);
  }
  
  public boolean detachMarker(String paramString) {
    if (paramString == null)
      return false; 
    return (this.markerMap.remove(paramString) != null);
  }
  
  public Marker getDetachedMarker(String paramString) {
    return new BasicMarker(paramString);
  }
}
