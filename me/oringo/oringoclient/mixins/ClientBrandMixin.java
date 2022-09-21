package me.oringo.oringoclient.mixins;

import me.oringo.oringoclient.OringoClient;
import net.minecraft.client.ClientBrandRetriever;
import net.minecraftforge.fml.common.FMLCommonHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin({ClientBrandRetriever.class})
public class ClientBrandMixin {
  @Overwrite
  public static String getClientModName() {
    return OringoClient..() ? "lunarclient:9f9eccb" : FMLCommonHandler.instance().getModName();
  }
}
