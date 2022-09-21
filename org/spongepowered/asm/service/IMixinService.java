package org.spongepowered.asm.service;

import java.io.InputStream;
import java.util.Collection;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.util.ReEntranceLock;

public interface IMixinService {
  void beginPhase();
  
  Collection<ITransformer> getTransformers();
  
  boolean isValid();
  
  void init();
  
  boolean isClassLoaded(String paramString);
  
  void checkEnv(Object paramObject);
  
  void prepare();
  
  IClassProvider getClassProvider();
  
  InputStream getResourceAsStream(String paramString);
  
  ReEntranceLock getReEntranceLock();
  
  Collection<String> getPlatformAgents();
  
  String getName();
  
  void registerInvalidClass(String paramString);
  
  String getSideName();
  
  MixinEnvironment.Phase getInitialPhase();
  
  String getClassRestrictions(String paramString);
  
  IClassBytecodeProvider getBytecodeProvider();
}
