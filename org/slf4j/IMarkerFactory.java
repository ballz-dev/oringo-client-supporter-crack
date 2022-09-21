package org.slf4j;

public interface IMarkerFactory {
  boolean detachMarker(String paramString);
  
  Marker getDetachedMarker(String paramString);
  
  Marker getMarker(String paramString);
  
  boolean exists(String paramString);
}
