package org.spongepowered.asm.lib;

public class Label {
  Label next;
  
  private int referenceCount;
  
  Frame frame;
  
  static final int STORE = 32;
  
  static final int JSR = 128;
  
  static final int DEBUG = 1;
  
  int line;
  
  static final int VISITED2 = 2048;
  
  int status;
  
  int position;
  
  static final int SUBROUTINE = 512;
  
  static final int RET = 256;
  
  static final int VISITED = 1024;
  
  int inputStackTop;
  
  int outputStackMax;
  
  static final int RESIZED = 4;
  
  Label successor;
  
  public Object info;
  
  private int[] srcAndRefPositions;
  
  static final int TARGET = 16;
  
  Edge successors;
  
  static final int REACHABLE = 64;
  
  static final int PUSHED = 8;
  
  static final int RESOLVED = 2;
  
  public int getOffset() {
    if ((this.status & 0x2) == 0)
      throw new IllegalStateException("Label offset position has not been resolved yet"); 
    return this.position;
  }
  
  void put(MethodWriter paramMethodWriter, ByteVector paramByteVector, int paramInt, boolean paramBoolean) {
    if ((this.status & 0x2) == 0) {
      if (paramBoolean) {
        addReference(-1 - paramInt, paramByteVector.length);
        paramByteVector.putInt(-1);
      } else {
        addReference(paramInt, paramByteVector.length);
        paramByteVector.putShort(-1);
      } 
    } else if (paramBoolean) {
      paramByteVector.putInt(this.position - paramInt);
    } else {
      paramByteVector.putShort(this.position - paramInt);
    } 
  }
  
  private void addReference(int paramInt1, int paramInt2) {
    if (this.srcAndRefPositions == null)
      this.srcAndRefPositions = new int[6]; 
    if (this.referenceCount >= this.srcAndRefPositions.length) {
      int[] arrayOfInt = new int[this.srcAndRefPositions.length + 6];
      System.arraycopy(this.srcAndRefPositions, 0, arrayOfInt, 0, this.srcAndRefPositions.length);
      this.srcAndRefPositions = arrayOfInt;
    } 
    this.srcAndRefPositions[this.referenceCount++] = paramInt1;
    this.srcAndRefPositions[this.referenceCount++] = paramInt2;
  }
  
  boolean resolve(MethodWriter paramMethodWriter, int paramInt, byte[] paramArrayOfbyte) {
    boolean bool = false;
    this.status |= 0x2;
    this.position = paramInt;
    byte b = 0;
    while (b < this.referenceCount) {
      int i = this.srcAndRefPositions[b++];
      int j = this.srcAndRefPositions[b++];
      if (i >= 0) {
        int m = paramInt - i;
        if (m < -32768 || m > 32767) {
          int n = paramArrayOfbyte[j - 1] & 0xFF;
          if (n <= 168) {
            paramArrayOfbyte[j - 1] = (byte)(n + 49);
          } else {
            paramArrayOfbyte[j - 1] = (byte)(n + 20);
          } 
          bool = true;
        } 
        paramArrayOfbyte[j++] = (byte)(m >>> 8);
        paramArrayOfbyte[j] = (byte)m;
        continue;
      } 
      int k = paramInt + i + 1;
      paramArrayOfbyte[j++] = (byte)(k >>> 24);
      paramArrayOfbyte[j++] = (byte)(k >>> 16);
      paramArrayOfbyte[j++] = (byte)(k >>> 8);
      paramArrayOfbyte[j] = (byte)k;
    } 
    return bool;
  }
  
  Label getFirst() {
    return (this.frame == null) ? this : this.frame.owner;
  }
  
  boolean inSubroutine(long paramLong) {
    if ((this.status & 0x400) != 0)
      return ((this.srcAndRefPositions[(int)(paramLong >>> 32L)] & (int)paramLong) != 0); 
    return false;
  }
  
  boolean inSameSubroutine(Label paramLabel) {
    if ((this.status & 0x400) == 0 || (paramLabel.status & 0x400) == 0)
      return false; 
    for (byte b = 0; b < this.srcAndRefPositions.length; b++) {
      if ((this.srcAndRefPositions[b] & paramLabel.srcAndRefPositions[b]) != 0)
        return true; 
    } 
    return false;
  }
  
  void addToSubroutine(long paramLong, int paramInt) {
    if ((this.status & 0x400) == 0) {
      this.status |= 0x400;
      this.srcAndRefPositions = new int[paramInt / 32 + 1];
    } 
    this.srcAndRefPositions[(int)(paramLong >>> 32L)] = this.srcAndRefPositions[(int)(paramLong >>> 32L)] | (int)paramLong;
  }
  
  void visitSubroutine(Label paramLabel, long paramLong, int paramInt) {
    Label label = this;
    while (label != null) {
      Label label1 = label;
      label = label1.next;
      label1.next = null;
      if (paramLabel != null) {
        if ((label1.status & 0x800) != 0)
          continue; 
        label1.status |= 0x800;
        if ((label1.status & 0x100) != 0 && 
          !label1.inSameSubroutine(paramLabel)) {
          Edge edge1 = new Edge();
          edge1.info = label1.inputStackTop;
          edge1.successor = paramLabel.successors.successor;
          edge1.next = label1.successors;
          label1.successors = edge1;
        } 
      } else {
        if (label1.inSubroutine(paramLong))
          continue; 
        label1.addToSubroutine(paramLong, paramInt);
      } 
      Edge edge = label1.successors;
      while (edge != null) {
        if ((label1.status & 0x80) == 0 || edge != label1.successors.next)
          if (edge.successor.next == null) {
            edge.successor.next = label;
            label = edge.successor;
          }  
        edge = edge.next;
      } 
    } 
  }
  
  public String toString() {
    return "L" + System.identityHashCode(this);
  }
}
