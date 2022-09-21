package org.java_websocket.drafts;

import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import org.java_websocket.WebSocketImpl;
import org.java_websocket.enums.CloseHandshakeType;
import org.java_websocket.enums.HandshakeState;
import org.java_websocket.enums.Opcode;
import org.java_websocket.enums.Role;
import org.java_websocket.exceptions.IncompleteHandshakeException;
import org.java_websocket.exceptions.InvalidDataException;
import org.java_websocket.exceptions.InvalidHandshakeException;
import org.java_websocket.framing.BinaryFrame;
import org.java_websocket.framing.ContinuousFrame;
import org.java_websocket.framing.Framedata;
import org.java_websocket.framing.TextFrame;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.handshake.ClientHandshakeBuilder;
import org.java_websocket.handshake.HandshakeBuilder;
import org.java_websocket.handshake.HandshakeImpl1Client;
import org.java_websocket.handshake.HandshakeImpl1Server;
import org.java_websocket.handshake.Handshakedata;
import org.java_websocket.handshake.ServerHandshake;
import org.java_websocket.handshake.ServerHandshakeBuilder;
import org.java_websocket.util.Charsetfunctions;

public abstract class Draft {
  protected Role role = null;
  
  protected Opcode continuousFrameType = null;
  
  public static ByteBuffer readLine(ByteBuffer paramByteBuffer) {
    ByteBuffer byteBuffer = ByteBuffer.allocate(paramByteBuffer.remaining());
    byte b = 48;
    while (paramByteBuffer.hasRemaining()) {
      byte b1 = b;
      b = paramByteBuffer.get();
      byteBuffer.put(b);
      if (b1 == 13 && b == 10) {
        byteBuffer.limit(byteBuffer.position() - 2);
        byteBuffer.position(0);
        return byteBuffer;
      } 
    } 
    paramByteBuffer.position(paramByteBuffer.position() - byteBuffer.position());
    return null;
  }
  
  public static String readStringLine(ByteBuffer paramByteBuffer) {
    ByteBuffer byteBuffer = readLine(paramByteBuffer);
    return (byteBuffer == null) ? null : Charsetfunctions.stringAscii(byteBuffer.array(), 0, byteBuffer.limit());
  }
  
  public static HandshakeBuilder translateHandshakeHttp(ByteBuffer paramByteBuffer, Role paramRole) throws InvalidHandshakeException {
    HandshakeBuilder handshakeBuilder;
    String str = readStringLine(paramByteBuffer);
    if (str == null)
      throw new IncompleteHandshakeException(paramByteBuffer.capacity() + 128); 
    String[] arrayOfString = str.split(" ", 3);
    if (arrayOfString.length != 3)
      throw new InvalidHandshakeException(); 
    if (paramRole == Role.CLIENT) {
      handshakeBuilder = translateHandshakeHttpClient(arrayOfString, str);
    } else {
      handshakeBuilder = translateHandshakeHttpServer(arrayOfString, str);
    } 
    str = readStringLine(paramByteBuffer);
    while (str != null && str.length() > 0) {
      String[] arrayOfString1 = str.split(":", 2);
      if (arrayOfString1.length != 2)
        throw new InvalidHandshakeException("not an http header"); 
      if (handshakeBuilder.hasFieldValue(arrayOfString1[0])) {
        handshakeBuilder.put(arrayOfString1[0], handshakeBuilder
            .getFieldValue(arrayOfString1[0]) + "; " + arrayOfString1[1].replaceFirst("^ +", ""));
      } else {
        handshakeBuilder.put(arrayOfString1[0], arrayOfString1[1].replaceFirst("^ +", ""));
      } 
      str = readStringLine(paramByteBuffer);
    } 
    if (str == null)
      throw new IncompleteHandshakeException(); 
    return handshakeBuilder;
  }
  
  private static HandshakeBuilder translateHandshakeHttpServer(String[] paramArrayOfString, String paramString) throws InvalidHandshakeException {
    if (!"GET".equalsIgnoreCase(paramArrayOfString[0]))
      throw new InvalidHandshakeException(
          String.format("Invalid request method received: %s Status line: %s", new Object[] { paramArrayOfString[0], paramString })); 
    if (!"HTTP/1.1".equalsIgnoreCase(paramArrayOfString[2]))
      throw new InvalidHandshakeException(
          String.format("Invalid status line received: %s Status line: %s", new Object[] { paramArrayOfString[2], paramString })); 
    HandshakeImpl1Client handshakeImpl1Client = new HandshakeImpl1Client();
    handshakeImpl1Client.setResourceDescriptor(paramArrayOfString[1]);
    return (HandshakeBuilder)handshakeImpl1Client;
  }
  
  private static HandshakeBuilder translateHandshakeHttpClient(String[] paramArrayOfString, String paramString) throws InvalidHandshakeException {
    if (!"101".equals(paramArrayOfString[1]))
      throw new InvalidHandshakeException(
          String.format("Invalid status code received: %s Status line: %s", new Object[] { paramArrayOfString[1], paramString })); 
    if (!"HTTP/1.1".equalsIgnoreCase(paramArrayOfString[0]))
      throw new InvalidHandshakeException(
          String.format("Invalid status line received: %s Status line: %s", new Object[] { paramArrayOfString[0], paramString })); 
    HandshakeImpl1Server handshakeImpl1Server = new HandshakeImpl1Server();
    ServerHandshakeBuilder serverHandshakeBuilder = (ServerHandshakeBuilder)handshakeImpl1Server;
    serverHandshakeBuilder.setHttpStatus(Short.parseShort(paramArrayOfString[1]));
    serverHandshakeBuilder.setHttpStatusMessage(paramArrayOfString[2]);
    return (HandshakeBuilder)handshakeImpl1Server;
  }
  
  protected boolean basicAccept(Handshakedata paramHandshakedata) {
    return (paramHandshakedata.getFieldValue("Upgrade").equalsIgnoreCase("websocket") && paramHandshakedata
      .getFieldValue("Connection").toLowerCase(Locale.ENGLISH).contains("upgrade"));
  }
  
  public List<Framedata> continuousFrame(Opcode paramOpcode, ByteBuffer paramByteBuffer, boolean paramBoolean) {
    TextFrame textFrame;
    if (paramOpcode != Opcode.BINARY && paramOpcode != Opcode.TEXT)
      throw new IllegalArgumentException("Only Opcode.BINARY or  Opcode.TEXT are allowed"); 
    ContinuousFrame continuousFrame = null;
    if (this.continuousFrameType != null) {
      continuousFrame = new ContinuousFrame();
    } else {
      this.continuousFrameType = paramOpcode;
      if (paramOpcode == Opcode.BINARY) {
        BinaryFrame binaryFrame = new BinaryFrame();
      } else if (paramOpcode == Opcode.TEXT) {
        textFrame = new TextFrame();
      } 
    } 
    textFrame.setPayload(paramByteBuffer);
    textFrame.setFin(paramBoolean);
    try {
      textFrame.isValid();
    } catch (InvalidDataException invalidDataException) {
      throw new IllegalArgumentException(invalidDataException);
    } 
    if (paramBoolean) {
      this.continuousFrameType = null;
    } else {
      this.continuousFrameType = paramOpcode;
    } 
    return (List)Collections.singletonList(textFrame);
  }
  
  @Deprecated
  public List<ByteBuffer> createHandshake(Handshakedata paramHandshakedata, Role paramRole) {
    return createHandshake(paramHandshakedata);
  }
  
  public List<ByteBuffer> createHandshake(Handshakedata paramHandshakedata) {
    return createHandshake(paramHandshakedata, true);
  }
  
  @Deprecated
  public List<ByteBuffer> createHandshake(Handshakedata paramHandshakedata, Role paramRole, boolean paramBoolean) {
    return createHandshake(paramHandshakedata, paramBoolean);
  }
  
  public List<ByteBuffer> createHandshake(Handshakedata paramHandshakedata, boolean paramBoolean) {
    StringBuilder stringBuilder = new StringBuilder(100);
    if (paramHandshakedata instanceof ClientHandshake) {
      stringBuilder.append("GET ").append(((ClientHandshake)paramHandshakedata).getResourceDescriptor())
        .append(" HTTP/1.1");
    } else if (paramHandshakedata instanceof ServerHandshake) {
      stringBuilder.append("HTTP/1.1 101 ").append(((ServerHandshake)paramHandshakedata).getHttpStatusMessage());
    } else {
      throw new IllegalArgumentException("unknown role");
    } 
    stringBuilder.append("\r\n");
    Iterator<String> iterator = paramHandshakedata.iterateHttpFields();
    while (iterator.hasNext()) {
      String str1 = iterator.next();
      String str2 = paramHandshakedata.getFieldValue(str1);
      stringBuilder.append(str1);
      stringBuilder.append(": ");
      stringBuilder.append(str2);
      stringBuilder.append("\r\n");
    } 
    stringBuilder.append("\r\n");
    byte[] arrayOfByte1 = Charsetfunctions.asciiBytes(stringBuilder.toString());
    byte[] arrayOfByte2 = paramBoolean ? paramHandshakedata.getContent() : null;
    ByteBuffer byteBuffer = ByteBuffer.allocate(((arrayOfByte2 == null) ? 0 : arrayOfByte2.length) + arrayOfByte1.length);
    byteBuffer.put(arrayOfByte1);
    if (arrayOfByte2 != null)
      byteBuffer.put(arrayOfByte2); 
    byteBuffer.flip();
    return Collections.singletonList(byteBuffer);
  }
  
  public Handshakedata translateHandshake(ByteBuffer paramByteBuffer) throws InvalidHandshakeException {
    return (Handshakedata)translateHandshakeHttp(paramByteBuffer, this.role);
  }
  
  public int checkAlloc(int paramInt) throws InvalidDataException {
    if (paramInt < 0)
      throw new InvalidDataException(1002, "Negative count"); 
    return paramInt;
  }
  
  int readVersion(Handshakedata paramHandshakedata) {
    String str = paramHandshakedata.getFieldValue("Sec-WebSocket-Version");
    if (str.length() > 0)
      try {
        return (new Integer(str.trim())).intValue();
      } catch (NumberFormatException numberFormatException) {
        return -1;
      }  
    return -1;
  }
  
  public void setParseMode(Role paramRole) {
    this.role = paramRole;
  }
  
  public Role getRole() {
    return this.role;
  }
  
  public String toString() {
    return getClass().getSimpleName();
  }
  
  public abstract ClientHandshakeBuilder postProcessHandshakeRequestAsClient(ClientHandshakeBuilder paramClientHandshakeBuilder) throws InvalidHandshakeException;
  
  public abstract List<Framedata> createFrames(String paramString, boolean paramBoolean);
  
  public abstract HandshakeState acceptHandshakeAsClient(ClientHandshake paramClientHandshake, ServerHandshake paramServerHandshake) throws InvalidHandshakeException;
  
  public abstract void processFrame(WebSocketImpl paramWebSocketImpl, Framedata paramFramedata) throws InvalidDataException;
  
  public abstract List<Framedata> createFrames(ByteBuffer paramByteBuffer, boolean paramBoolean);
  
  public abstract List<Framedata> translateFrame(ByteBuffer paramByteBuffer) throws InvalidDataException;
  
  public abstract ByteBuffer createBinaryFrame(Framedata paramFramedata);
  
  public abstract HandshakeState acceptHandshakeAsServer(ClientHandshake paramClientHandshake) throws InvalidHandshakeException;
  
  public abstract CloseHandshakeType getCloseHandshakeType();
  
  public abstract Draft copyInstance();
  
  public abstract void reset();
  
  public abstract HandshakeBuilder postProcessHandshakeResponseAsServer(ClientHandshake paramClientHandshake, ServerHandshakeBuilder paramServerHandshakeBuilder) throws InvalidHandshakeException;
}
