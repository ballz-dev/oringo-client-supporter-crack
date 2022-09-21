package org.java_websocket.extensions.permessage_deflate;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;
import org.java_websocket.enums.Opcode;
import org.java_websocket.exceptions.InvalidDataException;
import org.java_websocket.exceptions.InvalidFrameException;
import org.java_websocket.extensions.CompressionExtension;
import org.java_websocket.extensions.ExtensionRequestData;
import org.java_websocket.extensions.IExtension;
import org.java_websocket.framing.DataFrame;
import org.java_websocket.framing.Framedata;
import org.java_websocket.framing.FramedataImpl1;

public class PerMessageDeflateExtension extends CompressionExtension {
  private static final byte[] TAIL_BYTES = new byte[] { 0, 0, -1, -1 };
  
  private int threshold = 1024;
  
  private boolean serverNoContextTakeover = true;
  
  private boolean clientNoContextTakeover = false;
  
  private Map<String, String> requestedParameters = new LinkedHashMap<>();
  
  private Inflater inflater = new Inflater(true);
  
  private Deflater deflater = new Deflater(-1, true);
  
  private static final String SERVER_NO_CONTEXT_TAKEOVER = "server_no_context_takeover";
  
  private static final String CLIENT_NO_CONTEXT_TAKEOVER = "client_no_context_takeover";
  
  private static final int serverMaxWindowBits = 32768;
  
  private static final int BUFFER_SIZE = 1024;
  
  private static final String CLIENT_MAX_WINDOW_BITS = "client_max_window_bits";
  
  private static final String SERVER_MAX_WINDOW_BITS = "server_max_window_bits";
  
  private static final String EXTENSION_REGISTERED_NAME = "permessage-deflate";
  
  private static final int clientMaxWindowBits = 32768;
  
  public Inflater getInflater() {
    return this.inflater;
  }
  
  public void setInflater(Inflater paramInflater) {
    this.inflater = paramInflater;
  }
  
  public Deflater getDeflater() {
    return this.deflater;
  }
  
  public void setDeflater(Deflater paramDeflater) {
    this.deflater = paramDeflater;
  }
  
  public int getThreshold() {
    return this.threshold;
  }
  
  public void setThreshold(int paramInt) {
    this.threshold = paramInt;
  }
  
  public boolean isServerNoContextTakeover() {
    return this.serverNoContextTakeover;
  }
  
  public void setServerNoContextTakeover(boolean paramBoolean) {
    this.serverNoContextTakeover = paramBoolean;
  }
  
  public boolean isClientNoContextTakeover() {
    return this.clientNoContextTakeover;
  }
  
  public void setClientNoContextTakeover(boolean paramBoolean) {
    this.clientNoContextTakeover = paramBoolean;
  }
  
  public void decodeFrame(Framedata paramFramedata) throws InvalidDataException {
    if (!(paramFramedata instanceof DataFrame))
      return; 
    if (!paramFramedata.isRSV1() && paramFramedata.getOpcode() != Opcode.CONTINUOUS)
      return; 
    if (paramFramedata.getOpcode() == Opcode.CONTINUOUS && paramFramedata.isRSV1())
      throw new InvalidDataException(1008, "RSV1 bit can only be set for the first frame."); 
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    try {
      decompress(paramFramedata.getPayloadData().array(), byteArrayOutputStream);
      if (this.inflater.getRemaining() > 0) {
        this.inflater = new Inflater(true);
        decompress(paramFramedata.getPayloadData().array(), byteArrayOutputStream);
      } 
      if (paramFramedata.isFin()) {
        decompress(TAIL_BYTES, byteArrayOutputStream);
        if (this.clientNoContextTakeover)
          this.inflater = new Inflater(true); 
      } 
    } catch (DataFormatException dataFormatException) {
      throw new InvalidDataException(1008, dataFormatException.getMessage());
    } 
    ((FramedataImpl1)paramFramedata)
      .setPayload(ByteBuffer.wrap(byteArrayOutputStream.toByteArray(), 0, byteArrayOutputStream.size()));
  }
  
  private void decompress(byte[] paramArrayOfbyte, ByteArrayOutputStream paramByteArrayOutputStream) throws DataFormatException {
    this.inflater.setInput(paramArrayOfbyte);
    byte[] arrayOfByte = new byte[1024];
    int i;
    while ((i = this.inflater.inflate(arrayOfByte)) > 0)
      paramByteArrayOutputStream.write(arrayOfByte, 0, i); 
  }
  
  public void encodeFrame(Framedata paramFramedata) {
    if (!(paramFramedata instanceof DataFrame))
      return; 
    byte[] arrayOfByte1 = paramFramedata.getPayloadData().array();
    if (arrayOfByte1.length < this.threshold)
      return; 
    if (!(paramFramedata instanceof org.java_websocket.framing.ContinuousFrame))
      ((DataFrame)paramFramedata).setRSV1(true); 
    this.deflater.setInput(arrayOfByte1);
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    byte[] arrayOfByte2 = new byte[1024];
    int i;
    while ((i = this.deflater.deflate(arrayOfByte2, 0, arrayOfByte2.length, 2)) > 0)
      byteArrayOutputStream.write(arrayOfByte2, 0, i); 
    byte[] arrayOfByte3 = byteArrayOutputStream.toByteArray();
    int j = arrayOfByte3.length;
    if (paramFramedata.isFin()) {
      if (endsWithTail(arrayOfByte3))
        j -= TAIL_BYTES.length; 
      if (this.serverNoContextTakeover) {
        this.deflater.end();
        this.deflater = new Deflater(-1, true);
      } 
    } 
    ((FramedataImpl1)paramFramedata).setPayload(ByteBuffer.wrap(arrayOfByte3, 0, j));
  }
  
  private static boolean endsWithTail(byte[] paramArrayOfbyte) {
    if (paramArrayOfbyte.length < 4)
      return false; 
    int i = paramArrayOfbyte.length;
    for (byte b = 0; b < TAIL_BYTES.length; b++) {
      if (TAIL_BYTES[b] != paramArrayOfbyte[i - TAIL_BYTES.length + b])
        return false; 
    } 
    return true;
  }
  
  public boolean acceptProvidedExtensionAsServer(String paramString) {
    String[] arrayOfString1 = paramString.split(",");
    String[] arrayOfString2;
    int i;
    byte b;
    for (arrayOfString2 = arrayOfString1, i = arrayOfString2.length, b = 0; b < i; ) {
      String str = arrayOfString2[b];
      ExtensionRequestData extensionRequestData = ExtensionRequestData.parseExtensionRequest(str);
      if (!"permessage-deflate".equalsIgnoreCase(extensionRequestData.getExtensionName())) {
        b++;
        continue;
      } 
      Map<? extends String, ? extends String> map = extensionRequestData.getExtensionParameters();
      this.requestedParameters.putAll(map);
      if (this.requestedParameters.containsKey("client_no_context_takeover"))
        this.clientNoContextTakeover = true; 
      return true;
    } 
    return false;
  }
  
  public boolean acceptProvidedExtensionAsClient(String paramString) {
    String[] arrayOfString1 = paramString.split(",");
    String[] arrayOfString2;
    int i;
    byte b;
    for (arrayOfString2 = arrayOfString1, i = arrayOfString2.length, b = 0; b < i; ) {
      String str = arrayOfString2[b];
      ExtensionRequestData extensionRequestData = ExtensionRequestData.parseExtensionRequest(str);
      if (!"permessage-deflate".equalsIgnoreCase(extensionRequestData.getExtensionName())) {
        b++;
        continue;
      } 
      Map map = extensionRequestData.getExtensionParameters();
      return true;
    } 
    return false;
  }
  
  public String getProvidedExtensionAsClient() {
    this.requestedParameters.put("client_no_context_takeover", "");
    this.requestedParameters.put("server_no_context_takeover", "");
    return "permessage-deflate; server_no_context_takeover; client_no_context_takeover";
  }
  
  public String getProvidedExtensionAsServer() {
    return "permessage-deflate; server_no_context_takeover" + (this.clientNoContextTakeover ? "; client_no_context_takeover" : "");
  }
  
  public IExtension copyInstance() {
    return (IExtension)new PerMessageDeflateExtension();
  }
  
  public void isFrameValid(Framedata paramFramedata) throws InvalidDataException {
    if (paramFramedata instanceof org.java_websocket.framing.ContinuousFrame && (paramFramedata.isRSV1() || paramFramedata.isRSV2() || paramFramedata
      .isRSV3()))
      throw new InvalidFrameException("bad rsv RSV1: " + paramFramedata
          .isRSV1() + " RSV2: " + paramFramedata.isRSV2() + " RSV3: " + paramFramedata
          .isRSV3()); 
    super.isFrameValid(paramFramedata);
  }
  
  public String toString() {
    return "PerMessageDeflateExtension";
  }
}
