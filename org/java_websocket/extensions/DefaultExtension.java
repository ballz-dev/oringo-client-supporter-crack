package org.java_websocket.extensions;

import org.java_websocket.exceptions.InvalidDataException;
import org.java_websocket.exceptions.InvalidFrameException;
import org.java_websocket.framing.Framedata;

public class DefaultExtension implements IExtension {
  public void decodeFrame(Framedata paramFramedata) throws InvalidDataException {}
  
  public void encodeFrame(Framedata paramFramedata) {}
  
  public boolean acceptProvidedExtensionAsServer(String paramString) {
    return true;
  }
  
  public boolean acceptProvidedExtensionAsClient(String paramString) {
    return true;
  }
  
  public void isFrameValid(Framedata paramFramedata) throws InvalidDataException {
    if (paramFramedata.isRSV1() || paramFramedata.isRSV2() || paramFramedata.isRSV3())
      throw new InvalidFrameException("bad rsv RSV1: " + paramFramedata
          .isRSV1() + " RSV2: " + paramFramedata.isRSV2() + " RSV3: " + paramFramedata
          .isRSV3()); 
  }
  
  public String getProvidedExtensionAsClient() {
    return "";
  }
  
  public String getProvidedExtensionAsServer() {
    return "";
  }
  
  public IExtension copyInstance() {
    return new DefaultExtension();
  }
  
  public void reset() {}
  
  public String toString() {
    return getClass().getSimpleName();
  }
  
  public int hashCode() {
    return getClass().hashCode();
  }
  
  public boolean equals(Object paramObject) {
    return (this == paramObject || (paramObject != null && getClass() == paramObject.getClass()));
  }
}
