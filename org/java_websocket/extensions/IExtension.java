package org.java_websocket.extensions;

import org.java_websocket.exceptions.InvalidDataException;
import org.java_websocket.framing.Framedata;

public interface IExtension {
  void encodeFrame(Framedata paramFramedata);
  
  IExtension copyInstance();
  
  String getProvidedExtensionAsClient();
  
  String toString();
  
  void reset();
  
  String getProvidedExtensionAsServer();
  
  void isFrameValid(Framedata paramFramedata) throws InvalidDataException;
  
  boolean acceptProvidedExtensionAsClient(String paramString);
  
  boolean acceptProvidedExtensionAsServer(String paramString);
  
  void decodeFrame(Framedata paramFramedata) throws InvalidDataException;
}
