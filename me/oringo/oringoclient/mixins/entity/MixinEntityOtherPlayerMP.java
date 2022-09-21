package me.oringo.oringoclient.mixins.entity;

import me.oringo.oringoclient.extend.EntityOtherPlayerMPExtend;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.network.NetHandlerPlayClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({EntityOtherPlayerMP.class})
public abstract class MixinEntityOtherPlayerMP extends AbstractClientPlayerMixin implements EntityOtherPlayerMPExtend {
  private int visibleTicks;
  
  private int tabTicks;
  
  @Inject(method = {"onUpdate"}, at = {@At("HEAD")})
  public void oringo$onUpdate(CallbackInfo paramCallbackInfo) {
    if (!func_82150_aj())
      this.visibleTicks++; 
    NetHandlerPlayClient netHandlerPlayClient = Minecraft.func_71410_x().func_147114_u();
    if (netHandlerPlayClient != null && netHandlerPlayClient.func_175102_a(this.field_96093_i) != null)
      this.tabTicks++; 
  }
  
  public int getVisibleTicks() {
    return this.visibleTicks;
  }
  
  public int getTabTicks() {
    return this.tabTicks;
  }
}
