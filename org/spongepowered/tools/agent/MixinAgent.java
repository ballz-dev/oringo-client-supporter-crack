package org.spongepowered.tools.agent;

import java.lang.instrument.ClassDefinition;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.transformer.MixinTransformer;
import org.spongepowered.asm.mixin.transformer.ext.IHotSwap;
import org.spongepowered.asm.mixin.transformer.throwables.MixinReloadException;
import org.spongepowered.asm.service.IMixinService;
import org.spongepowered.asm.service.MixinService;

public class MixinAgent implements IHotSwap {
  static Instrumentation instrumentation;
  
  static final Logger logger;
  
  final MixinTransformer classTransformer;
  
  private static List<MixinAgent> agents;
  
  class Transformer implements ClassFileTransformer {
    public byte[] transform(ClassLoader param1ClassLoader, String param1String, Class<?> param1Class, ProtectionDomain param1ProtectionDomain, byte[] param1ArrayOfbyte) throws IllegalClassFormatException {
      if (param1Class == null)
        return null; 
      byte[] arrayOfByte = MixinAgent.classLoader.getFakeMixinBytecode(param1Class);
      if (arrayOfByte != null) {
        List<String> list = reloadMixin(param1String, param1ArrayOfbyte);
        if (list == null || !reApplyMixins(list))
          return MixinAgent.ERROR_BYTECODE; 
        return arrayOfByte;
      } 
      try {
        MixinAgent.logger.info("Redefining class " + param1String);
        return MixinAgent.this.classTransformer.transformClassBytes(null, param1String, param1ArrayOfbyte);
      } catch (Throwable throwable) {
        MixinAgent.logger.error("Error while re-transforming class " + param1String, throwable);
        return MixinAgent.ERROR_BYTECODE;
      } 
    }
    
    private List<String> reloadMixin(String param1String, byte[] param1ArrayOfbyte) {
      MixinAgent.logger.info("Redefining mixin {}", new Object[] { param1String });
      try {
        return MixinAgent.this.classTransformer.reload(param1String.replace('/', '.'), param1ArrayOfbyte);
      } catch (MixinReloadException mixinReloadException) {
        MixinAgent.logger.error("Mixin {} cannot be reloaded, needs a restart to be applied: {} ", new Object[] { mixinReloadException.getMixinInfo(), mixinReloadException.getMessage() });
      } catch (Throwable throwable) {
        MixinAgent.logger.error("Error while finding targets for mixin " + param1String, throwable);
      } 
      return null;
    }
    
    private boolean reApplyMixins(List<String> param1List) {
      IMixinService iMixinService = MixinService.getService();
      for (String str1 : param1List) {
        String str2 = str1.replace('/', '.');
        MixinAgent.logger.debug("Re-transforming target class {}", new Object[] { str1 });
        try {
          Class<?> clazz = iMixinService.getClassProvider().findClass(str2);
          byte[] arrayOfByte = MixinAgent.classLoader.getOriginalTargetBytecode(str2);
          if (arrayOfByte == null) {
            MixinAgent.logger.error("Target class {} bytecode is not registered", new Object[] { str2 });
            return false;
          } 
          arrayOfByte = MixinAgent.this.classTransformer.transformClassBytes(null, str2, arrayOfByte);
          MixinAgent.instrumentation.redefineClasses(new ClassDefinition[] { new ClassDefinition(clazz, arrayOfByte) });
        } catch (Throwable throwable) {
          MixinAgent.logger.error("Error while re-transforming target class " + str1, throwable);
          return false;
        } 
      } 
      return true;
    }
  }
  
  public static final byte[] ERROR_BYTECODE = new byte[] { 1 };
  
  static final MixinAgentClassLoader classLoader = new MixinAgentClassLoader();
  
  static {
    logger = LogManager.getLogger("mixin.agent");
    instrumentation = null;
    agents = new ArrayList<MixinAgent>();
  }
  
  public MixinAgent(MixinTransformer paramMixinTransformer) {
    this.classTransformer = paramMixinTransformer;
    agents.add(this);
    if (instrumentation != null)
      initTransformer(); 
  }
  
  private void initTransformer() {
    instrumentation.addTransformer(new Transformer(), true);
  }
  
  public void registerMixinClass(String paramString) {
    classLoader.addMixinClass(paramString);
  }
  
  public void registerTargetClass(String paramString, byte[] paramArrayOfbyte) {
    classLoader.addTargetClass(paramString, paramArrayOfbyte);
  }
  
  public static void init(Instrumentation paramInstrumentation) {
    instrumentation = paramInstrumentation;
    if (!instrumentation.isRedefineClassesSupported())
      logger.error("The instrumentation doesn't support re-definition of classes"); 
    for (MixinAgent mixinAgent : agents)
      mixinAgent.initTransformer(); 
  }
  
  public static void premain(String paramString, Instrumentation paramInstrumentation) {
    System.setProperty("mixin.hotSwap", "true");
    init(paramInstrumentation);
  }
  
  public static void agentmain(String paramString, Instrumentation paramInstrumentation) {
    init(paramInstrumentation);
  }
}
