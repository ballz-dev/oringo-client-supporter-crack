package me.oringo.oringoclient.extend.chunk;

public interface ChunkExtend {
  boolean getScanData(int paramInt1, int paramInt2, int paramInt3);
  
  void setScanData(int paramInt1, int paramInt2, int paramInt3);
  
  void setGeneratedData(int paramInt1, int paramInt2, int paramInt3);
  
  boolean getGeneratedData(int paramInt1, int paramInt2, int paramInt3);
  
  void setGeneratedData(int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean);
  
  static {
  
  }
  
  void setScanData(int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean);
}
