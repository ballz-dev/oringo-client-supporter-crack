package org.slf4j;

import org.slf4j.helpers.BasicMarkerFactory;
import org.slf4j.helpers.Util;
import org.slf4j.impl.StaticMarkerBinder;

public class MarkerFactory {
  static IMarkerFactory MARKER_FACTORY;
  
  private static IMarkerFactory bwCompatibleGetMarkerFactoryFromBinder() throws NoClassDefFoundError {
    try {
      return StaticMarkerBinder.getSingleton().getMarkerFactory();
    } catch (NoSuchMethodError noSuchMethodError) {
      return StaticMarkerBinder.SINGLETON.getMarkerFactory();
    } 
  }
  
  static {
    try {
      MARKER_FACTORY = bwCompatibleGetMarkerFactoryFromBinder();
    } catch (NoClassDefFoundError noClassDefFoundError) {
      MARKER_FACTORY = (IMarkerFactory)new BasicMarkerFactory();
    } catch (Exception exception) {
      Util.report("Unexpected failure while binding MarkerFactory", exception);
    } 
  }
  
  public static Marker getMarker(String paramString) {
    return MARKER_FACTORY.getMarker(paramString);
  }
  
  public static Marker getDetachedMarker(String paramString) {
    return MARKER_FACTORY.getDetachedMarker(paramString);
  }
  
  public static IMarkerFactory getIMarkerFactory() {
    return MARKER_FACTORY;
  }
}
