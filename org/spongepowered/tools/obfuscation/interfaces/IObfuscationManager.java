package org.spongepowered.tools.obfuscation.interfaces;

import java.util.List;
import org.spongepowered.tools.obfuscation.ObfuscationEnvironment;
import org.spongepowered.tools.obfuscation.mapping.IMappingConsumer;

public interface IObfuscationManager {
  List<ObfuscationEnvironment> getEnvironments();
  
  IObfuscationDataProvider getDataProvider();
  
  void init();
  
  void writeMappings();
  
  IReferenceManager getReferenceManager();
  
  IMappingConsumer createMappingConsumer();
  
  void writeReferences();
}
