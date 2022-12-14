package org.java_websocket.extensions;

import org.java_websocket.exceptions.InvalidDataException;
import org.java_websocket.exceptions.InvalidFrameException;
import org.java_websocket.framing.Framedata;

public abstract class CompressionExtension extends DefaultExtension {
  public void isFrameValid(Framedata paramFramedata) throws InvalidDataException {
    if (paramFramedata instanceof org.java_websocket.framing.DataFrame && (paramFramedata.isRSV2() || paramFramedata.isRSV3()))
      throw new InvalidFrameException("bad rsv RSV1: " + paramFramedata
          .isRSV1() + " RSV2: " + paramFramedata.isRSV2() + " RSV3: " + paramFramedata
          .isRSV3()); 
    if (paramFramedata instanceof org.java_websocket.framing.ControlFrame && (paramFramedata.isRSV1() || paramFramedata.isRSV2() || paramFramedata
      .isRSV3()))
      throw new InvalidFrameException("bad rsv RSV1: " + paramFramedata
          .isRSV1() + " RSV2: " + paramFramedata.isRSV2() + " RSV3: " + paramFramedata
          .isRSV3()); 
  }
}
