package org.spongepowered.tools.obfuscation.interfaces;

import org.spongepowered.asm.mixin.injection.struct.MemberInfo;
import org.spongepowered.asm.obfuscation.mapping.IMapping;
import org.spongepowered.asm.obfuscation.mapping.common.MappingField;
import org.spongepowered.asm.obfuscation.mapping.common.MappingMethod;
import org.spongepowered.tools.obfuscation.ObfuscationData;
import org.spongepowered.tools.obfuscation.mirror.TypeHandle;

public interface IObfuscationDataProvider {
  ObfuscationData<MappingMethod> getObfMethod(MemberInfo paramMemberInfo);
  
  <T> ObfuscationData<T> getObfEntry(IMapping<T> paramIMapping);
  
  ObfuscationData<MappingField> getObfField(MappingField paramMappingField);
  
  ObfuscationData<MappingMethod> getObfMethodRecursive(MemberInfo paramMemberInfo);
  
  ObfuscationData<String> getObfClass(TypeHandle paramTypeHandle);
  
  ObfuscationData<MappingMethod> getRemappedMethod(MappingMethod paramMappingMethod);
  
  ObfuscationData<MappingField> getObfFieldRecursive(MemberInfo paramMemberInfo);
  
  ObfuscationData<String> getObfClass(String paramString);
  
  ObfuscationData<MappingField> getObfField(MemberInfo paramMemberInfo);
  
  ObfuscationData<MappingMethod> getRemappedMethod(MemberInfo paramMemberInfo);
  
  <T> ObfuscationData<T> getObfEntryRecursive(MemberInfo paramMemberInfo);
  
  <T> ObfuscationData<T> getObfEntry(MemberInfo paramMemberInfo);
  
  ObfuscationData<MappingMethod> getObfMethod(MappingMethod paramMappingMethod);
}
