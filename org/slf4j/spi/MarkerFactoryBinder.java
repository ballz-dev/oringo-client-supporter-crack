package org.slf4j.spi;

import org.slf4j.IMarkerFactory;

public interface MarkerFactoryBinder {
  String getMarkerFactoryClassStr();
  
  IMarkerFactory getMarkerFactory();
}
