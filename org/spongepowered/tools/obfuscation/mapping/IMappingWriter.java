package org.spongepowered.tools.obfuscation.mapping;

import org.spongepowered.asm.obfuscation.mapping.common.MappingField;
import org.spongepowered.asm.obfuscation.mapping.common.MappingMethod;
import org.spongepowered.tools.obfuscation.ObfuscationType;

public interface IMappingWriter {
  void write(String paramString, ObfuscationType paramObfuscationType, IMappingConsumer.MappingSet<MappingField> paramMappingSet, IMappingConsumer.MappingSet<MappingMethod> paramMappingSet1);
}
