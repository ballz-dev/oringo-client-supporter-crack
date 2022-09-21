package org.java_websocket.drafts;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import org.java_websocket.WebSocket;
import org.java_websocket.WebSocketImpl;
import org.java_websocket.enums.CloseHandshakeType;
import org.java_websocket.enums.HandshakeState;
import org.java_websocket.enums.Opcode;
import org.java_websocket.enums.ReadyState;
import org.java_websocket.enums.Role;
import org.java_websocket.exceptions.IncompleteException;
import org.java_websocket.exceptions.InvalidDataException;
import org.java_websocket.exceptions.InvalidFrameException;
import org.java_websocket.exceptions.InvalidHandshakeException;
import org.java_websocket.exceptions.LimitExceededException;
import org.java_websocket.exceptions.NotSendableException;
import org.java_websocket.extensions.DefaultExtension;
import org.java_websocket.extensions.IExtension;
import org.java_websocket.framing.BinaryFrame;
import org.java_websocket.framing.CloseFrame;
import org.java_websocket.framing.Framedata;
import org.java_websocket.framing.FramedataImpl1;
import org.java_websocket.framing.TextFrame;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.handshake.ClientHandshakeBuilder;
import org.java_websocket.handshake.HandshakeBuilder;
import org.java_websocket.handshake.Handshakedata;
import org.java_websocket.handshake.ServerHandshake;
import org.java_websocket.handshake.ServerHandshakeBuilder;
import org.java_websocket.protocols.IProtocol;
import org.java_websocket.protocols.Protocol;
import org.java_websocket.util.Base64;
import org.java_websocket.util.Charsetfunctions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Draft_6455 extends Draft {
  private final Logger log = LoggerFactory.getLogger(Draft_6455.class);
  
  private IExtension negotiatedExtension = (IExtension)new DefaultExtension();
  
  private IExtension defaultExtension = (IExtension)new DefaultExtension();
  
  private final SecureRandom reuseableRandom = new SecureRandom();
  
  private List<IExtension> knownExtensions;
  
  private List<IProtocol> knownProtocols;
  
  private final List<ByteBuffer> byteBufferList;
  
  private int maxFrameSize;
  
  private static final String SEC_WEB_SOCKET_PROTOCOL = "Sec-WebSocket-Protocol";
  
  private static final String SEC_WEB_SOCKET_ACCEPT = "Sec-WebSocket-Accept";
  
  private IProtocol protocol;
  
  private static final String SEC_WEB_SOCKET_EXTENSIONS = "Sec-WebSocket-Extensions";
  
  private static final String CONNECTION = "Connection";
  
  private static final String SEC_WEB_SOCKET_KEY = "Sec-WebSocket-Key";
  
  private Framedata currentContinuousFrame;
  
  private ByteBuffer incompleteframe;
  
  private IExtension currentDecodingExtension;
  
  private static final String UPGRADE = "Upgrade";
  
  public Draft_6455() {
    this(Collections.emptyList());
  }
  
  public Draft_6455(IExtension paramIExtension) {
    this(Collections.singletonList(paramIExtension));
  }
  
  public Draft_6455(List<IExtension> paramList) {
    this(paramList, (List)Collections.singletonList(new Protocol("")));
  }
  
  public Draft_6455(List<IExtension> paramList, List<IProtocol> paramList1) {
    this(paramList, paramList1, 2147483647);
  }
  
  public Draft_6455(List<IExtension> paramList, int paramInt) {
    this(paramList, (List)Collections.singletonList(new Protocol("")), paramInt);
  }
  
  public Draft_6455(List<IExtension> paramList, List<IProtocol> paramList1, int paramInt) {
    if (paramList == null || paramList1 == null || paramInt < 1)
      throw new IllegalArgumentException(); 
    this.knownExtensions = new ArrayList<>(paramList.size());
    this.knownProtocols = new ArrayList<>(paramList1.size());
    boolean bool = false;
    this.byteBufferList = new ArrayList<>();
    for (IExtension iExtension : paramList) {
      if (iExtension.getClass().equals(DefaultExtension.class))
        bool = true; 
    } 
    this.knownExtensions.addAll(paramList);
    if (!bool)
      this.knownExtensions.add(this.knownExtensions.size(), this.negotiatedExtension); 
    this.knownProtocols.addAll(paramList1);
    this.maxFrameSize = paramInt;
    this.currentDecodingExtension = null;
  }
  
  public HandshakeState acceptHandshakeAsServer(ClientHandshake paramClientHandshake) throws InvalidHandshakeException {
    int i = readVersion((Handshakedata)paramClientHandshake);
    if (i != 13) {
      this.log.trace("acceptHandshakeAsServer - Wrong websocket version.");
      return HandshakeState.NOT_MATCHED;
    } 
    HandshakeState handshakeState1 = HandshakeState.NOT_MATCHED;
    String str = paramClientHandshake.getFieldValue("Sec-WebSocket-Extensions");
    for (IExtension iExtension : this.knownExtensions) {
      if (iExtension.acceptProvidedExtensionAsServer(str)) {
        this.negotiatedExtension = iExtension;
        handshakeState1 = HandshakeState.MATCHED;
        this.log.trace("acceptHandshakeAsServer - Matching extension found: {}", this.negotiatedExtension);
        break;
      } 
    } 
    HandshakeState handshakeState2 = containsRequestedProtocol(paramClientHandshake
        .getFieldValue("Sec-WebSocket-Protocol"));
    if (handshakeState2 == HandshakeState.MATCHED && handshakeState1 == HandshakeState.MATCHED)
      return HandshakeState.MATCHED; 
    this.log.trace("acceptHandshakeAsServer - No matching extension or protocol found.");
    return HandshakeState.NOT_MATCHED;
  }
  
  private HandshakeState containsRequestedProtocol(String paramString) {
    for (IProtocol iProtocol : this.knownProtocols) {
      if (iProtocol.acceptProvidedProtocol(paramString)) {
        this.protocol = iProtocol;
        this.log.trace("acceptHandshake - Matching protocol found: {}", this.protocol);
        return HandshakeState.MATCHED;
      } 
    } 
    return HandshakeState.NOT_MATCHED;
  }
  
  public HandshakeState acceptHandshakeAsClient(ClientHandshake paramClientHandshake, ServerHandshake paramServerHandshake) throws InvalidHandshakeException {
    if (!basicAccept((Handshakedata)paramServerHandshake)) {
      this.log.trace("acceptHandshakeAsClient - Missing/wrong upgrade or connection in handshake.");
      return HandshakeState.NOT_MATCHED;
    } 
    if (!paramClientHandshake.hasFieldValue("Sec-WebSocket-Key") || 
      !paramServerHandshake.hasFieldValue("Sec-WebSocket-Accept")) {
      this.log.trace("acceptHandshakeAsClient - Missing Sec-WebSocket-Key or Sec-WebSocket-Accept");
      return HandshakeState.NOT_MATCHED;
    } 
    String str1 = paramServerHandshake.getFieldValue("Sec-WebSocket-Accept");
    String str2 = paramClientHandshake.getFieldValue("Sec-WebSocket-Key");
    str2 = generateFinalKey(str2);
    if (!str2.equals(str1)) {
      this.log.trace("acceptHandshakeAsClient - Wrong key for Sec-WebSocket-Key.");
      return HandshakeState.NOT_MATCHED;
    } 
    HandshakeState handshakeState1 = HandshakeState.NOT_MATCHED;
    String str3 = paramServerHandshake.getFieldValue("Sec-WebSocket-Extensions");
    for (IExtension iExtension : this.knownExtensions) {
      if (iExtension.acceptProvidedExtensionAsClient(str3)) {
        this.negotiatedExtension = iExtension;
        handshakeState1 = HandshakeState.MATCHED;
        this.log.trace("acceptHandshakeAsClient - Matching extension found: {}", this.negotiatedExtension);
        break;
      } 
    } 
    HandshakeState handshakeState2 = containsRequestedProtocol(paramServerHandshake
        .getFieldValue("Sec-WebSocket-Protocol"));
    if (handshakeState2 == HandshakeState.MATCHED && handshakeState1 == HandshakeState.MATCHED)
      return HandshakeState.MATCHED; 
    this.log.trace("acceptHandshakeAsClient - No matching extension or protocol found.");
    return HandshakeState.NOT_MATCHED;
  }
  
  public IExtension getExtension() {
    return this.negotiatedExtension;
  }
  
  public List<IExtension> getKnownExtensions() {
    return this.knownExtensions;
  }
  
  public IProtocol getProtocol() {
    return this.protocol;
  }
  
  public int getMaxFrameSize() {
    return this.maxFrameSize;
  }
  
  public List<IProtocol> getKnownProtocols() {
    return this.knownProtocols;
  }
  
  public ClientHandshakeBuilder postProcessHandshakeRequestAsClient(ClientHandshakeBuilder paramClientHandshakeBuilder) {
    paramClientHandshakeBuilder.put("Upgrade", "websocket");
    paramClientHandshakeBuilder.put("Connection", "Upgrade");
    byte[] arrayOfByte = new byte[16];
    this.reuseableRandom.nextBytes(arrayOfByte);
    paramClientHandshakeBuilder.put("Sec-WebSocket-Key", Base64.encodeBytes(arrayOfByte));
    paramClientHandshakeBuilder.put("Sec-WebSocket-Version", "13");
    StringBuilder stringBuilder1 = new StringBuilder();
    for (IExtension iExtension : this.knownExtensions) {
      if (iExtension.getProvidedExtensionAsClient() != null && iExtension
        .getProvidedExtensionAsClient().length() != 0) {
        if (stringBuilder1.length() > 0)
          stringBuilder1.append(", "); 
        stringBuilder1.append(iExtension.getProvidedExtensionAsClient());
      } 
    } 
    if (stringBuilder1.length() != 0)
      paramClientHandshakeBuilder.put("Sec-WebSocket-Extensions", stringBuilder1.toString()); 
    StringBuilder stringBuilder2 = new StringBuilder();
    for (IProtocol iProtocol : this.knownProtocols) {
      if (iProtocol.getProvidedProtocol().length() != 0) {
        if (stringBuilder2.length() > 0)
          stringBuilder2.append(", "); 
        stringBuilder2.append(iProtocol.getProvidedProtocol());
      } 
    } 
    if (stringBuilder2.length() != 0)
      paramClientHandshakeBuilder.put("Sec-WebSocket-Protocol", stringBuilder2.toString()); 
    return paramClientHandshakeBuilder;
  }
  
  public HandshakeBuilder postProcessHandshakeResponseAsServer(ClientHandshake paramClientHandshake, ServerHandshakeBuilder paramServerHandshakeBuilder) throws InvalidHandshakeException {
    paramServerHandshakeBuilder.put("Upgrade", "websocket");
    paramServerHandshakeBuilder.put("Connection", paramClientHandshake
        .getFieldValue("Connection"));
    String str = paramClientHandshake.getFieldValue("Sec-WebSocket-Key");
    if (str == null || "".equals(str))
      throw new InvalidHandshakeException("missing Sec-WebSocket-Key"); 
    paramServerHandshakeBuilder.put("Sec-WebSocket-Accept", generateFinalKey(str));
    if (getExtension().getProvidedExtensionAsServer().length() != 0)
      paramServerHandshakeBuilder.put("Sec-WebSocket-Extensions", getExtension().getProvidedExtensionAsServer()); 
    if (getProtocol() != null && getProtocol().getProvidedProtocol().length() != 0)
      paramServerHandshakeBuilder.put("Sec-WebSocket-Protocol", getProtocol().getProvidedProtocol()); 
    paramServerHandshakeBuilder.setHttpStatusMessage("Web Socket Protocol Handshake");
    paramServerHandshakeBuilder.put("Server", "TooTallNate Java-WebSocket");
    paramServerHandshakeBuilder.put("Date", getServerTime());
    return (HandshakeBuilder)paramServerHandshakeBuilder;
  }
  
  public Draft copyInstance() {
    ArrayList<IExtension> arrayList = new ArrayList();
    for (IExtension iExtension : getKnownExtensions())
      arrayList.add(iExtension.copyInstance()); 
    ArrayList<IProtocol> arrayList1 = new ArrayList();
    for (IProtocol iProtocol : getKnownProtocols())
      arrayList1.add(iProtocol.copyInstance()); 
    return new Draft_6455(arrayList, arrayList1, this.maxFrameSize);
  }
  
  public ByteBuffer createBinaryFrame(Framedata paramFramedata) {
    getExtension().encodeFrame(paramFramedata);
    if (this.log.isTraceEnabled())
      this.log.trace("afterEnconding({}): {}", Integer.valueOf(paramFramedata.getPayloadData().remaining()), 
          (paramFramedata.getPayloadData().remaining() > 1000) ? "too big to display" : new String(paramFramedata
            .getPayloadData().array())); 
    return createByteBufferFromFramedata(paramFramedata);
  }
  
  private ByteBuffer createByteBufferFromFramedata(Framedata paramFramedata) {
    ByteBuffer byteBuffer1 = paramFramedata.getPayloadData();
    boolean bool = (this.role == Role.CLIENT) ? true : false;
    int i = getSizeBytes(byteBuffer1);
    ByteBuffer byteBuffer2 = ByteBuffer.allocate(1 + ((i > 1) ? (i + 1) : i) + (bool ? 4 : 0) + byteBuffer1
        .remaining());
    byte b1 = fromOpcode(paramFramedata.getOpcode());
    byte b2 = (byte)(paramFramedata.isFin() ? Byte.MIN_VALUE : 0);
    b2 = (byte)(b2 | b1);
    if (paramFramedata.isRSV1())
      b2 = (byte)(b2 | getRSVByte(1)); 
    if (paramFramedata.isRSV2())
      b2 = (byte)(b2 | getRSVByte(2)); 
    if (paramFramedata.isRSV3())
      b2 = (byte)(b2 | getRSVByte(3)); 
    byteBuffer2.put(b2);
    byte[] arrayOfByte = toByteArray(byteBuffer1.remaining(), i);
    assert arrayOfByte.length == i;
    if (i == 1) {
      byteBuffer2.put((byte)(arrayOfByte[0] | getMaskByte(bool)));
    } else if (i == 2) {
      byteBuffer2.put((byte)(0x7E | getMaskByte(bool)));
      byteBuffer2.put(arrayOfByte);
    } else if (i == 8) {
      byteBuffer2.put((byte)(Byte.MAX_VALUE | getMaskByte(bool)));
      byteBuffer2.put(arrayOfByte);
    } else {
      throw new IllegalStateException("Size representation not supported/specified");
    } 
    if (bool) {
      ByteBuffer byteBuffer = ByteBuffer.allocate(4);
      byteBuffer.putInt(this.reuseableRandom.nextInt());
      byteBuffer2.put(byteBuffer.array());
      for (byte b = 0; byteBuffer1.hasRemaining(); b++)
        byteBuffer2.put((byte)(byteBuffer1.get() ^ byteBuffer.get(b % 4))); 
    } else {
      byteBuffer2.put(byteBuffer1);
      byteBuffer1.flip();
    } 
    assert byteBuffer2.remaining() == 0 : byteBuffer2.remaining();
    byteBuffer2.flip();
    return byteBuffer2;
  }
  
  private Framedata translateSingleFrame(ByteBuffer paramByteBuffer) throws IncompleteException, InvalidDataException {
    if (paramByteBuffer == null)
      throw new IllegalArgumentException(); 
    int i = paramByteBuffer.remaining();
    int j = 2;
    translateSingleFrameCheckPacketSize(i, j);
    byte b1 = paramByteBuffer.get();
    boolean bool1 = (b1 >> 8 != 0) ? true : false;
    boolean bool2 = ((b1 & 0x40) != 0) ? true : false;
    boolean bool3 = ((b1 & 0x20) != 0) ? true : false;
    boolean bool4 = ((b1 & 0x10) != 0) ? true : false;
    byte b2 = paramByteBuffer.get();
    boolean bool5 = ((b2 & Byte.MIN_VALUE) != 0) ? true : false;
    int k = (byte)(b2 & Byte.MAX_VALUE);
    Opcode opcode = toOpcode((byte)(b1 & 0xF));
    if (k < 0 || k > 125) {
      TranslatedPayloadMetaData translatedPayloadMetaData = translateSingleFramePayloadLength(paramByteBuffer, opcode, k, i, j);
      k = translatedPayloadMetaData.getPayloadLength();
      j = translatedPayloadMetaData.getRealPackageSize();
    } 
    translateSingleFrameCheckLengthLimit(k);
    j += bool5 ? 4 : 0;
    j += k;
    translateSingleFrameCheckPacketSize(i, j);
    ByteBuffer byteBuffer = ByteBuffer.allocate(checkAlloc(k));
    if (bool5) {
      byte[] arrayOfByte = new byte[4];
      paramByteBuffer.get(arrayOfByte);
      for (byte b = 0; b < k; b++)
        byteBuffer.put((byte)(paramByteBuffer.get() ^ arrayOfByte[b % 4])); 
    } else {
      byteBuffer.put(paramByteBuffer.array(), paramByteBuffer.position(), byteBuffer.limit());
      paramByteBuffer.position(paramByteBuffer.position() + byteBuffer.limit());
    } 
    FramedataImpl1 framedataImpl1 = FramedataImpl1.get(opcode);
    framedataImpl1.setFin(bool1);
    framedataImpl1.setRSV1(bool2);
    framedataImpl1.setRSV2(bool3);
    framedataImpl1.setRSV3(bool4);
    byteBuffer.flip();
    framedataImpl1.setPayload(byteBuffer);
    if (framedataImpl1.getOpcode() != Opcode.CONTINUOUS)
      if (framedataImpl1.isRSV1() || framedataImpl1.isRSV2() || framedataImpl1.isRSV3()) {
        this.currentDecodingExtension = getExtension();
      } else {
        this.currentDecodingExtension = this.defaultExtension;
      }  
    if (this.currentDecodingExtension == null)
      this.currentDecodingExtension = this.defaultExtension; 
    this.currentDecodingExtension.isFrameValid((Framedata)framedataImpl1);
    this.currentDecodingExtension.decodeFrame((Framedata)framedataImpl1);
    if (this.log.isTraceEnabled())
      this.log.trace("afterDecoding({}): {}", Integer.valueOf(framedataImpl1.getPayloadData().remaining()), 
          (framedataImpl1.getPayloadData().remaining() > 1000) ? "too big to display" : new String(framedataImpl1
            .getPayloadData().array())); 
    framedataImpl1.isValid();
    return (Framedata)framedataImpl1;
  }
  
  private TranslatedPayloadMetaData translateSingleFramePayloadLength(ByteBuffer paramByteBuffer, Opcode paramOpcode, int paramInt1, int paramInt2, int paramInt3) throws InvalidFrameException, IncompleteException, LimitExceededException {
    int i = paramInt1;
    int j = paramInt3;
    if (paramOpcode == Opcode.PING || paramOpcode == Opcode.PONG || paramOpcode == Opcode.CLOSING) {
      this.log.trace("Invalid frame: more than 125 octets");
      throw new InvalidFrameException("more than 125 octets");
    } 
    if (i == 126) {
      j += 2;
      translateSingleFrameCheckPacketSize(paramInt2, j);
      byte[] arrayOfByte = new byte[3];
      arrayOfByte[1] = paramByteBuffer.get();
      arrayOfByte[2] = paramByteBuffer.get();
      i = (new BigInteger(arrayOfByte)).intValue();
    } else {
      j += 8;
      translateSingleFrameCheckPacketSize(paramInt2, j);
      byte[] arrayOfByte = new byte[8];
      for (byte b = 0; b < 8; b++)
        arrayOfByte[b] = paramByteBuffer.get(); 
      long l = (new BigInteger(arrayOfByte)).longValue();
      translateSingleFrameCheckLengthLimit(l);
      i = (int)l;
    } 
    return new TranslatedPayloadMetaData(i, j);
  }
  
  private void translateSingleFrameCheckLengthLimit(long paramLong) throws LimitExceededException {
    if (paramLong > 2147483647L) {
      this.log.trace("Limit exedeed: Payloadsize is to big...");
      throw new LimitExceededException("Payloadsize is to big...");
    } 
    if (paramLong > this.maxFrameSize) {
      this.log.trace("Payload limit reached. Allowed: {} Current: {}", Integer.valueOf(this.maxFrameSize), Long.valueOf(paramLong));
      throw new LimitExceededException("Payload limit reached.", this.maxFrameSize);
    } 
    if (paramLong < 0L) {
      this.log.trace("Limit underflow: Payloadsize is to little...");
      throw new LimitExceededException("Payloadsize is to little...");
    } 
  }
  
  private void translateSingleFrameCheckPacketSize(int paramInt1, int paramInt2) throws IncompleteException {
    if (paramInt1 < paramInt2) {
      this.log.trace("Incomplete frame: maxpacketsize < realpacketsize");
      throw new IncompleteException(paramInt2);
    } 
  }
  
  private byte getRSVByte(int paramInt) {
    switch (paramInt) {
      case 1:
        return 64;
      case 2:
        return 32;
      case 3:
        return 16;
    } 
    return 0;
  }
  
  private byte getMaskByte(boolean paramBoolean) {
    return paramBoolean ? Byte.MIN_VALUE : 0;
  }
  
  private int getSizeBytes(ByteBuffer paramByteBuffer) {
    if (paramByteBuffer.remaining() <= 125)
      return 1; 
    if (paramByteBuffer.remaining() <= 65535)
      return 2; 
    return 8;
  }
  
  public List<Framedata> translateFrame(ByteBuffer paramByteBuffer) throws InvalidDataException {
    LinkedList<Framedata> linkedList;
    while (true) {
      linkedList = new LinkedList();
      if (this.incompleteframe != null)
        try {
          paramByteBuffer.mark();
          int i = paramByteBuffer.remaining();
          int j = this.incompleteframe.remaining();
          if (j > i) {
            this.incompleteframe.put(paramByteBuffer.array(), paramByteBuffer.position(), i);
            paramByteBuffer.position(paramByteBuffer.position() + i);
            return Collections.emptyList();
          } 
          this.incompleteframe.put(paramByteBuffer.array(), paramByteBuffer.position(), j);
          paramByteBuffer.position(paramByteBuffer.position() + j);
          Framedata framedata = translateSingleFrame((ByteBuffer)this.incompleteframe.duplicate().position(0));
          linkedList.add(framedata);
          this.incompleteframe = null;
          break;
        } catch (IncompleteException incompleteException) {
          ByteBuffer byteBuffer = ByteBuffer.allocate(checkAlloc(incompleteException.getPreferredSize()));
          assert byteBuffer.limit() > this.incompleteframe.limit();
          this.incompleteframe.rewind();
          byteBuffer.put(this.incompleteframe);
          this.incompleteframe = byteBuffer;
          continue;
        }  
      break;
    } 
    while (paramByteBuffer.hasRemaining()) {
      paramByteBuffer.mark();
      try {
        Framedata framedata = translateSingleFrame(paramByteBuffer);
        linkedList.add(framedata);
      } catch (IncompleteException incompleteException) {
        paramByteBuffer.reset();
        int i = incompleteException.getPreferredSize();
        this.incompleteframe = ByteBuffer.allocate(checkAlloc(i));
        this.incompleteframe.put(paramByteBuffer);
        break;
      } 
    } 
    return linkedList;
  }
  
  public List<Framedata> createFrames(ByteBuffer paramByteBuffer, boolean paramBoolean) {
    BinaryFrame binaryFrame = new BinaryFrame();
    binaryFrame.setPayload(paramByteBuffer);
    binaryFrame.setTransferemasked(paramBoolean);
    try {
      binaryFrame.isValid();
    } catch (InvalidDataException invalidDataException) {
      throw new NotSendableException(invalidDataException);
    } 
    return (List)Collections.singletonList(binaryFrame);
  }
  
  public List<Framedata> createFrames(String paramString, boolean paramBoolean) {
    TextFrame textFrame = new TextFrame();
    textFrame.setPayload(ByteBuffer.wrap(Charsetfunctions.utf8Bytes(paramString)));
    textFrame.setTransferemasked(paramBoolean);
    try {
      textFrame.isValid();
    } catch (InvalidDataException invalidDataException) {
      throw new NotSendableException(invalidDataException);
    } 
    return (List)Collections.singletonList(textFrame);
  }
  
  public void reset() {
    this.incompleteframe = null;
    if (this.negotiatedExtension != null)
      this.negotiatedExtension.reset(); 
    this.negotiatedExtension = (IExtension)new DefaultExtension();
    this.protocol = null;
  }
  
  private String getServerTime() {
    Calendar calendar = Calendar.getInstance();
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
    simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
    return simpleDateFormat.format(calendar.getTime());
  }
  
  private String generateFinalKey(String paramString) {
    MessageDigest messageDigest;
    String str1 = paramString.trim();
    String str2 = str1 + "258EAFA5-E914-47DA-95CA-C5AB0DC85B11";
    try {
      messageDigest = MessageDigest.getInstance("SHA1");
    } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
      throw new IllegalStateException(noSuchAlgorithmException);
    } 
    return Base64.encodeBytes(messageDigest.digest(str2.getBytes()));
  }
  
  private byte[] toByteArray(long paramLong, int paramInt) {
    byte[] arrayOfByte = new byte[paramInt];
    int i = 8 * paramInt - 8;
    for (byte b = 0; b < paramInt; b++)
      arrayOfByte[b] = (byte)(int)(paramLong >>> i - 8 * b); 
    return arrayOfByte;
  }
  
  private byte fromOpcode(Opcode paramOpcode) {
    if (paramOpcode == Opcode.CONTINUOUS)
      return 0; 
    if (paramOpcode == Opcode.TEXT)
      return 1; 
    if (paramOpcode == Opcode.BINARY)
      return 2; 
    if (paramOpcode == Opcode.CLOSING)
      return 8; 
    if (paramOpcode == Opcode.PING)
      return 9; 
    if (paramOpcode == Opcode.PONG)
      return 10; 
    throw new IllegalArgumentException("Don't know how to handle " + paramOpcode.toString());
  }
  
  private Opcode toOpcode(byte paramByte) throws InvalidFrameException {
    switch (paramByte) {
      case 0:
        return Opcode.CONTINUOUS;
      case 1:
        return Opcode.TEXT;
      case 2:
        return Opcode.BINARY;
      case 8:
        return Opcode.CLOSING;
      case 9:
        return Opcode.PING;
      case 10:
        return Opcode.PONG;
    } 
    throw new InvalidFrameException("Unknown opcode " + (short)paramByte);
  }
  
  public void processFrame(WebSocketImpl paramWebSocketImpl, Framedata paramFramedata) throws InvalidDataException {
    Opcode opcode = paramFramedata.getOpcode();
    if (opcode == Opcode.CLOSING) {
      processFrameClosing(paramWebSocketImpl, paramFramedata);
    } else if (opcode == Opcode.PING) {
      paramWebSocketImpl.getWebSocketListener().onWebsocketPing((WebSocket)paramWebSocketImpl, paramFramedata);
    } else if (opcode == Opcode.PONG) {
      paramWebSocketImpl.updateLastPong();
      paramWebSocketImpl.getWebSocketListener().onWebsocketPong((WebSocket)paramWebSocketImpl, paramFramedata);
    } else if (!paramFramedata.isFin() || opcode == Opcode.CONTINUOUS) {
      processFrameContinuousAndNonFin(paramWebSocketImpl, paramFramedata, opcode);
    } else {
      if (this.currentContinuousFrame != null) {
        this.log.error("Protocol error: Continuous frame sequence not completed.");
        throw new InvalidDataException(1002, "Continuous frame sequence not completed.");
      } 
      if (opcode == Opcode.TEXT) {
        processFrameText(paramWebSocketImpl, paramFramedata);
      } else if (opcode == Opcode.BINARY) {
        processFrameBinary(paramWebSocketImpl, paramFramedata);
      } else {
        this.log.error("non control or continious frame expected");
        throw new InvalidDataException(1002, "non control or continious frame expected");
      } 
    } 
  }
  
  private void processFrameContinuousAndNonFin(WebSocketImpl paramWebSocketImpl, Framedata paramFramedata, Opcode paramOpcode) throws InvalidDataException {
    if (paramOpcode != Opcode.CONTINUOUS) {
      processFrameIsNotFin(paramFramedata);
    } else if (paramFramedata.isFin()) {
      processFrameIsFin(paramWebSocketImpl, paramFramedata);
    } else if (this.currentContinuousFrame == null) {
      this.log.error("Protocol error: Continuous frame sequence was not started.");
      throw new InvalidDataException(1002, "Continuous frame sequence was not started.");
    } 
    if (paramOpcode == Opcode.TEXT && !Charsetfunctions.isValidUTF8(paramFramedata.getPayloadData())) {
      this.log.error("Protocol error: Payload is not UTF8");
      throw new InvalidDataException(1007);
    } 
    if (paramOpcode == Opcode.CONTINUOUS && this.currentContinuousFrame != null)
      addToBufferList(paramFramedata.getPayloadData()); 
  }
  
  private void processFrameBinary(WebSocketImpl paramWebSocketImpl, Framedata paramFramedata) {
    try {
      paramWebSocketImpl.getWebSocketListener()
        .onWebsocketMessage((WebSocket)paramWebSocketImpl, paramFramedata.getPayloadData());
    } catch (RuntimeException runtimeException) {
      logRuntimeException(paramWebSocketImpl, runtimeException);
    } 
  }
  
  private void logRuntimeException(WebSocketImpl paramWebSocketImpl, RuntimeException paramRuntimeException) {
    this.log.error("Runtime exception during onWebsocketMessage", paramRuntimeException);
    paramWebSocketImpl.getWebSocketListener().onWebsocketError((WebSocket)paramWebSocketImpl, paramRuntimeException);
  }
  
  private void processFrameText(WebSocketImpl paramWebSocketImpl, Framedata paramFramedata) throws InvalidDataException {
    try {
      paramWebSocketImpl.getWebSocketListener()
        .onWebsocketMessage((WebSocket)paramWebSocketImpl, Charsetfunctions.stringUtf8(paramFramedata.getPayloadData()));
    } catch (RuntimeException runtimeException) {
      logRuntimeException(paramWebSocketImpl, runtimeException);
    } 
  }
  
  private void processFrameIsFin(WebSocketImpl paramWebSocketImpl, Framedata paramFramedata) throws InvalidDataException {
    if (this.currentContinuousFrame == null) {
      this.log.trace("Protocol error: Previous continuous frame sequence not completed.");
      throw new InvalidDataException(1002, "Continuous frame sequence was not started.");
    } 
    addToBufferList(paramFramedata.getPayloadData());
    checkBufferLimit();
    if (this.currentContinuousFrame.getOpcode() == Opcode.TEXT) {
      ((FramedataImpl1)this.currentContinuousFrame).setPayload(getPayloadFromByteBufferList());
      ((FramedataImpl1)this.currentContinuousFrame).isValid();
      try {
        paramWebSocketImpl.getWebSocketListener().onWebsocketMessage((WebSocket)paramWebSocketImpl, 
            Charsetfunctions.stringUtf8(this.currentContinuousFrame.getPayloadData()));
      } catch (RuntimeException runtimeException) {
        logRuntimeException(paramWebSocketImpl, runtimeException);
      } 
    } else if (this.currentContinuousFrame.getOpcode() == Opcode.BINARY) {
      ((FramedataImpl1)this.currentContinuousFrame).setPayload(getPayloadFromByteBufferList());
      ((FramedataImpl1)this.currentContinuousFrame).isValid();
      try {
        paramWebSocketImpl.getWebSocketListener()
          .onWebsocketMessage((WebSocket)paramWebSocketImpl, this.currentContinuousFrame.getPayloadData());
      } catch (RuntimeException runtimeException) {
        logRuntimeException(paramWebSocketImpl, runtimeException);
      } 
    } 
    this.currentContinuousFrame = null;
    clearBufferList();
  }
  
  private void processFrameIsNotFin(Framedata paramFramedata) throws InvalidDataException {
    if (this.currentContinuousFrame != null) {
      this.log.trace("Protocol error: Previous continuous frame sequence not completed.");
      throw new InvalidDataException(1002, "Previous continuous frame sequence not completed.");
    } 
    this.currentContinuousFrame = paramFramedata;
    addToBufferList(paramFramedata.getPayloadData());
    checkBufferLimit();
  }
  
  private void processFrameClosing(WebSocketImpl paramWebSocketImpl, Framedata paramFramedata) {
    int i = 1005;
    String str = "";
    if (paramFramedata instanceof CloseFrame) {
      CloseFrame closeFrame = (CloseFrame)paramFramedata;
      i = closeFrame.getCloseCode();
      str = closeFrame.getMessage();
    } 
    if (paramWebSocketImpl.getReadyState() == ReadyState.CLOSING) {
      paramWebSocketImpl.closeConnection(i, str, true);
    } else if (getCloseHandshakeType() == CloseHandshakeType.TWOWAY) {
      paramWebSocketImpl.close(i, str, true);
    } else {
      paramWebSocketImpl.flushAndClose(i, str, false);
    } 
  }
  
  private void clearBufferList() {
    synchronized (this.byteBufferList) {
      this.byteBufferList.clear();
    } 
  }
  
  private void addToBufferList(ByteBuffer paramByteBuffer) {
    synchronized (this.byteBufferList) {
      this.byteBufferList.add(paramByteBuffer);
    } 
  }
  
  private void checkBufferLimit() throws LimitExceededException {
    long l = getByteBufferListSize();
    if (l > this.maxFrameSize) {
      clearBufferList();
      this.log.trace("Payload limit reached. Allowed: {} Current: {}", Integer.valueOf(this.maxFrameSize), Long.valueOf(l));
      throw new LimitExceededException(this.maxFrameSize);
    } 
  }
  
  public CloseHandshakeType getCloseHandshakeType() {
    return CloseHandshakeType.TWOWAY;
  }
  
  public String toString() {
    String str = super.toString();
    if (getExtension() != null)
      str = str + " extension: " + getExtension().toString(); 
    if (getProtocol() != null)
      str = str + " protocol: " + getProtocol().toString(); 
    str = str + " max frame size: " + this.maxFrameSize;
    return str;
  }
  
  public boolean equals(Object paramObject) {
    if (this == paramObject)
      return true; 
    if (paramObject == null || getClass() != paramObject.getClass())
      return false; 
    Draft_6455 draft_6455 = (Draft_6455)paramObject;
    if (this.maxFrameSize != draft_6455.getMaxFrameSize())
      return false; 
    if ((this.negotiatedExtension != null) ? !this.negotiatedExtension.equals(draft_6455.getExtension()) : (draft_6455.getExtension() != null))
      return false; 
    return (this.protocol != null) ? this.protocol.equals(draft_6455.getProtocol()) : ((draft_6455.getProtocol() == null));
  }
  
  public int hashCode() {
    int i = (this.negotiatedExtension != null) ? this.negotiatedExtension.hashCode() : 0;
    i = 31 * i + ((this.protocol != null) ? this.protocol.hashCode() : 0);
    i = 31 * i + (this.maxFrameSize ^ this.maxFrameSize >>> 32);
    return i;
  }
  
  private ByteBuffer getPayloadFromByteBufferList() throws LimitExceededException {
    ByteBuffer byteBuffer;
    long l = 0L;
    synchronized (this.byteBufferList) {
      for (ByteBuffer byteBuffer1 : this.byteBufferList)
        l += byteBuffer1.limit(); 
      checkBufferLimit();
      byteBuffer = ByteBuffer.allocate((int)l);
      for (ByteBuffer byteBuffer1 : this.byteBufferList)
        byteBuffer.put(byteBuffer1); 
    } 
    byteBuffer.flip();
    return byteBuffer;
  }
  
  private long getByteBufferListSize() {
    long l = 0L;
    synchronized (this.byteBufferList) {
      for (ByteBuffer byteBuffer : this.byteBufferList)
        l += byteBuffer.limit(); 
    } 
    return l;
  }
  
  private class TranslatedPayloadMetaData {
    private int realPackageSize;
    
    private int payloadLength;
    
    private int getPayloadLength() {
      return this.payloadLength;
    }
    
    private int getRealPackageSize() {
      return this.realPackageSize;
    }
    
    TranslatedPayloadMetaData(int param1Int1, int param1Int2) {
      this.payloadLength = param1Int1;
      this.realPackageSize = param1Int2;
    }
  }
}
