package me.oringo.oringoclient.mixins.chunk;

import me.oringo.oringoclient.extend.chunk.ChunkExtend;
import me.oringo.oringoclient.extend.chunk.GeneratedData;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({Chunk.class})
public class ChunkMixin implements ChunkExtend {
  private GeneratedData[] generatedStorage;
  
  private GeneratedData[] scanStorage;
  
  @Inject(method = {"<init>(Lnet/minecraft/world/World;II)V"}, at = {@At("RETURN")})
  public void oringo$chunkInit(World paramWorld, int paramInt1, int paramInt2, CallbackInfo paramCallbackInfo) {
    this.generatedStorage = crateStorage();
    this.scanStorage = crateStorage();
  }
  
  private GeneratedData[] crateStorage() {
    GeneratedData[] arrayOfGeneratedData = new GeneratedData[16];
    for (byte b = 0; b < 16; b++)
      arrayOfGeneratedData[b] = new GeneratedData(); 
    return arrayOfGeneratedData;
  }
  
  public boolean getGeneratedData(int paramInt1, int paramInt2, int paramInt3) {
    if (paramInt2 >= 0 && paramInt2 >> 4 < this.generatedStorage.length) {
      GeneratedData generatedData = this.generatedStorage[paramInt2 >> 4];
      if (generatedData != null)
        return generatedData.(paramInt1 & 0xF, paramInt2 & 0xF, paramInt3 & 0xF); 
    } 
    return false;
  }
  
  public void setGeneratedData(int paramInt1, int paramInt2, int paramInt3) {
    setGeneratedData(paramInt1, paramInt2, paramInt3, true);
  }
  
  public void setGeneratedData(int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean) {
    if (paramInt2 >= 0 && paramInt2 >> 4 < this.generatedStorage.length) {
      GeneratedData generatedData = this.generatedStorage[paramInt2 >> 4];
      if (generatedData != null)
        generatedData.(paramInt1 & 0xF, paramInt2 & 0xF, paramInt3 & 0xF, paramBoolean); 
    } 
  }
  
  public boolean getScanData(int paramInt1, int paramInt2, int paramInt3) {
    if (paramInt2 >= 0 && paramInt2 >> 4 < this.scanStorage.length) {
      GeneratedData generatedData = this.scanStorage[paramInt2 >> 4];
      if (generatedData != null)
        return generatedData.(paramInt1 & 0xF, paramInt2 & 0xF, paramInt3 & 0xF); 
    } 
    return false;
  }
  
  public void setScanData(int paramInt1, int paramInt2, int paramInt3) {
    setScanData(paramInt1, paramInt2, paramInt3, true);
  }
  
  public void setScanData(int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean) {
    if (paramInt2 >= 0 && paramInt2 >> 4 < this.scanStorage.length) {
      GeneratedData generatedData = this.scanStorage[paramInt2 >> 4];
      if (generatedData != null)
        generatedData.(paramInt1 & 0xF, paramInt2 & 0xF, paramInt3 & 0xF, paramBoolean); 
    } 
  }
}
