package org.spongepowered.asm.mixin.transformer.ext.extensions;

import org.spongepowered.asm.lib.ClassVisitor;
import org.spongepowered.asm.lib.util.CheckClassAdapter;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.throwables.MixinException;
import org.spongepowered.asm.mixin.transformer.ext.IExtension;
import org.spongepowered.asm.mixin.transformer.ext.ITargetClassContext;
import org.spongepowered.asm.transformers.MixinClassWriter;

public class ExtensionCheckClass implements IExtension {
  public static class ValidationFailedException extends MixinException {
    private static final long serialVersionUID = 1L;
    
    public ValidationFailedException(String param1String, Throwable param1Throwable) {
      super(param1String, param1Throwable);
    }
    
    public ValidationFailedException(String param1String) {
      super(param1String);
    }
    
    public ValidationFailedException(Throwable param1Throwable) {
      super(param1Throwable);
    }
  }
  
  public boolean checkActive(MixinEnvironment paramMixinEnvironment) {
    return paramMixinEnvironment.getOption(MixinEnvironment.Option.DEBUG_VERIFY);
  }
  
  public void preApply(ITargetClassContext paramITargetClassContext) {}
  
  public void postApply(ITargetClassContext paramITargetClassContext) {
    try {
      paramITargetClassContext.getClassNode().accept((ClassVisitor)new CheckClassAdapter((ClassVisitor)new MixinClassWriter(2)));
    } catch (RuntimeException runtimeException) {
      throw new ValidationFailedException(runtimeException.getMessage(), runtimeException);
    } 
  }
  
  public void export(MixinEnvironment paramMixinEnvironment, String paramString, boolean paramBoolean, byte[] paramArrayOfbyte) {}
}
