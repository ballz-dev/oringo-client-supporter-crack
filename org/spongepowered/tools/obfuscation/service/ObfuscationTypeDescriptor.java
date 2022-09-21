package org.spongepowered.tools.obfuscation.service;

import org.spongepowered.tools.obfuscation.ObfuscationEnvironment;

public class ObfuscationTypeDescriptor {
  private final String extraInputFilesArgName;
  
  private final String inputFileArgName;
  
  private final String key;
  
  private final String outFileArgName;
  
  private final Class<? extends ObfuscationEnvironment> environmentType;
  
  public ObfuscationTypeDescriptor(String paramString1, String paramString2, String paramString3, Class<? extends ObfuscationEnvironment> paramClass) {
    this(paramString1, paramString2, null, paramString3, paramClass);
  }
  
  public ObfuscationTypeDescriptor(String paramString1, String paramString2, String paramString3, String paramString4, Class<? extends ObfuscationEnvironment> paramClass) {
    this.key = paramString1;
    this.inputFileArgName = paramString2;
    this.extraInputFilesArgName = paramString3;
    this.outFileArgName = paramString4;
    this.environmentType = paramClass;
  }
  
  public final String getKey() {
    return this.key;
  }
  
  public String getInputFileOption() {
    return this.inputFileArgName;
  }
  
  public String getExtraInputFilesOption() {
    return this.extraInputFilesArgName;
  }
  
  public String getOutputFileOption() {
    return this.outFileArgName;
  }
  
  public Class<? extends ObfuscationEnvironment> getEnvironmentType() {
    return this.environmentType;
  }
}
