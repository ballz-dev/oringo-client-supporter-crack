package org.spongepowered.tools.obfuscation.mapping;

import java.io.File;
import java.io.IOException;
import org.spongepowered.asm.obfuscation.mapping.common.MappingField;
import org.spongepowered.asm.obfuscation.mapping.common.MappingMethod;

public interface IMappingProvider {
  MappingField getFieldMapping(MappingField paramMappingField);
  
  String getClassMapping(String paramString);
  
  boolean isEmpty();
  
  String getPackageMapping(String paramString);
  
  void read(File paramFile) throws IOException;
  
  MappingMethod getMethodMapping(MappingMethod paramMappingMethod);
  
  void clear();
}
