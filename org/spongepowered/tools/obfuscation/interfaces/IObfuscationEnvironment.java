package org.spongepowered.tools.obfuscation.interfaces;

import java.util.Collection;
import org.spongepowered.asm.mixin.injection.struct.MemberInfo;
import org.spongepowered.asm.obfuscation.mapping.common.MappingField;
import org.spongepowered.asm.obfuscation.mapping.common.MappingMethod;
import org.spongepowered.tools.obfuscation.mapping.IMappingConsumer;

public interface IObfuscationEnvironment {
  MappingField getObfField(MemberInfo paramMemberInfo);
  
  MappingMethod getObfMethod(MemberInfo paramMemberInfo);
  
  String remapDescriptor(String paramString);
  
  MemberInfo remapDescriptor(MemberInfo paramMemberInfo);
  
  void writeMappings(Collection<IMappingConsumer> paramCollection);
  
  MappingMethod getObfMethod(MappingMethod paramMappingMethod, boolean paramBoolean);
  
  MappingField getObfField(MappingField paramMappingField);
  
  MappingMethod getObfMethod(MappingMethod paramMappingMethod);
  
  MappingField getObfField(MappingField paramMappingField, boolean paramBoolean);
  
  String getObfClass(String paramString);
}
