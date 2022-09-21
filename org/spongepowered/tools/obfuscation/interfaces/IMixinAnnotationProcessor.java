package org.spongepowered.tools.obfuscation.interfaces;

import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import org.spongepowered.asm.util.ITokenProvider;

public interface IMixinAnnotationProcessor extends Messager, IOptionProvider {
  IObfuscationManager getObfuscationManager();
  
  ProcessingEnvironment getProcessingEnvironment();
  
  IJavadocProvider getJavadocProvider();
  
  ITokenProvider getTokenProvider();
  
  CompilerEnvironment getCompilerEnvironment();
  
  ITypeHandleProvider getTypeProvider();
  
  public enum CompilerEnvironment {
    JDT, JAVAC;
    
    static {
    
    }
  }
}
