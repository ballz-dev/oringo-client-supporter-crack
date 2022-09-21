package me.oringo.oringoclient.mixins.renderer;

import java.nio.ByteOrder;
import java.nio.IntBuffer;
import me.oringo.oringoclient.OringoClient;
import me.oringo.oringoclient.qolfeatures.module.impl.render.XRay;
import net.minecraft.client.renderer.WorldRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin({WorldRenderer.class})
public abstract class WorldRendererMixin {
  @Shadow
  private boolean field_78939_q;
  
  @Shadow
  private IntBuffer field_178999_b;
  
  @Overwrite
  public void func_178978_a(float paramFloat1, float paramFloat2, float paramFloat3, int paramInt) {
    int i = func_78909_a(paramInt);
    int j = -1;
    if (!this.field_78939_q) {
      j = this.field_178999_b.get(i);
      if (ByteOrder.nativeOrder() == ByteOrder.LITTLE_ENDIAN) {
        int k = (int)((j & 0xFF) * paramFloat1);
        int m = (int)((j >> 8 & 0xFF) * paramFloat2);
        int n = (int)((j >> 16 & 0xFF) * paramFloat3);
        j = OringoClient..() ? (((int)XRay..() & 0xFF) << 24) : (j & 0xFF000000);
        j = j | n << 16 | m << 8 | k;
      } else {
        int k = (int)((j >> 24 & 0xFF) * paramFloat1);
        int m = (int)((j >> 16 & 0xFF) * paramFloat2);
        int n = (int)((j >> 8 & 0xFF) * paramFloat3);
        j = (OringoClient..() ? (int)XRay..() : j) & 0xFF;
        j = j | k << 24 | m << 16 | n << 8;
      } 
    } 
    this.field_178999_b.put(i, j);
  }
  
  @Shadow
  public abstract int func_78909_a(int paramInt);
}
