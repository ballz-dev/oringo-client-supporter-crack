package me.oringo.oringoclient.mixins;

import java.util.List;
import java.util.Map;
import me.oringo.oringoclient.OringoClient;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.fml.common.network.handshake.FMLHandshakeMessage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({FMLHandshakeMessage.ModList.class})
public class ModlessMixin {
  @Shadow
  private Map<String, String> modTags;
  
  @Inject(method = {"<init>(Ljava/util/List;)V"}, at = {@At("RETURN")})
  public void test(List<ModContainer> paramList, CallbackInfo paramCallbackInfo) {
    if (!OringoClient..())
      return; 
    try {
      if (Minecraft.func_71410_x().func_71356_B())
        return; 
    } catch (Exception exception) {
      return;
    } 
    this.modTags.entrySet().removeIf(paramEntry -> (!((String)paramEntry.getKey()).equalsIgnoreCase("fml") && !((String)paramEntry.getKey()).equalsIgnoreCase("forge") && !((String)paramEntry.getKey()).equalsIgnoreCase("mcp") && !((String)paramEntry.getKey()).equalsIgnoreCase("optifine") && !((String)paramEntry.getKey()).equalsIgnoreCase("skyblockaddons")));
  }
}
