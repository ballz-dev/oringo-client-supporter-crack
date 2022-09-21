package org.spongepowered.asm.bridge;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import org.objectweb.asm.commons.Remapper;
import org.spongepowered.asm.mixin.extensibility.IRemapper;

public final class RemapperAdapterFML extends RemapperAdapter {
  private static final String DEOBFUSCATING_REMAPPER_CLASS_LEGACY = "cpw.mods.fml.common.asm.transformers.deobf.FMLDeobfuscatingRemapper";
  
  private final Method mdUnmap;
  
  private static final String DEOBFUSCATING_REMAPPER_CLASS = "fml.common.asm.transformers.deobf.FMLDeobfuscatingRemapper";
  
  private static final String UNMAP_METHOD = "unmap";
  
  private static final String DEOBFUSCATING_REMAPPER_CLASS_FORGE = "net.minecraftforge.fml.common.asm.transformers.deobf.FMLDeobfuscatingRemapper";
  
  private static final String INSTANCE_FIELD = "INSTANCE";
  
  private RemapperAdapterFML(Remapper paramRemapper, Method paramMethod) {
    super(paramRemapper);
    this.logger.info("Initialised Mixin FML Remapper Adapter with {}", new Object[] { paramRemapper });
    this.mdUnmap = paramMethod;
  }
  
  public String unmap(String paramString) {
    try {
      return this.mdUnmap.invoke(this.remapper, new Object[] { paramString }).toString();
    } catch (Exception exception) {
      return paramString;
    } 
  }
  
  public static IRemapper create() {
    try {
      Class<?> clazz = getFMLDeobfuscatingRemapper();
      Field field = clazz.getDeclaredField("INSTANCE");
      Method method = clazz.getDeclaredMethod("unmap", new Class[] { String.class });
      Remapper remapper = (Remapper)field.get(null);
      return new RemapperAdapterFML(remapper, method);
    } catch (Exception exception) {
      exception.printStackTrace();
      return null;
    } 
  }
  
  private static Class<?> getFMLDeobfuscatingRemapper() throws ClassNotFoundException {
    try {
      return Class.forName("net.minecraftforge.fml.common.asm.transformers.deobf.FMLDeobfuscatingRemapper");
    } catch (ClassNotFoundException classNotFoundException) {
      return Class.forName("cpw.mods.fml.common.asm.transformers.deobf.FMLDeobfuscatingRemapper");
    } 
  }
}
