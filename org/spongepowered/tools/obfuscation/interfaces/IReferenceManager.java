package org.spongepowered.tools.obfuscation.interfaces;

import org.spongepowered.asm.mixin.injection.struct.MemberInfo;
import org.spongepowered.asm.mixin.refmap.ReferenceMapper;
import org.spongepowered.asm.obfuscation.mapping.common.MappingField;
import org.spongepowered.asm.obfuscation.mapping.common.MappingMethod;
import org.spongepowered.tools.obfuscation.ObfuscationData;

public interface IReferenceManager {
  boolean getAllowConflicts();
  
  void setAllowConflicts(boolean paramBoolean);
  
  ReferenceMapper getMapper();
  
  void addMethodMapping(String paramString1, String paramString2, MemberInfo paramMemberInfo, ObfuscationData<MappingMethod> paramObfuscationData);
  
  void addMethodMapping(String paramString1, String paramString2, ObfuscationData<MappingMethod> paramObfuscationData);
  
  void addClassMapping(String paramString1, String paramString2, ObfuscationData<String> paramObfuscationData);
  
  void write();
  
  void addFieldMapping(String paramString1, String paramString2, MemberInfo paramMemberInfo, ObfuscationData<MappingField> paramObfuscationData);
}
