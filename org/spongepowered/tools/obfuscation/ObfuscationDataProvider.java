package org.spongepowered.tools.obfuscation;

import java.util.List;
import org.spongepowered.asm.mixin.injection.struct.MemberInfo;
import org.spongepowered.asm.obfuscation.mapping.IMapping;
import org.spongepowered.asm.obfuscation.mapping.common.MappingField;
import org.spongepowered.asm.obfuscation.mapping.common.MappingMethod;
import org.spongepowered.tools.obfuscation.interfaces.IMixinAnnotationProcessor;
import org.spongepowered.tools.obfuscation.interfaces.IObfuscationDataProvider;
import org.spongepowered.tools.obfuscation.mirror.TypeHandle;

public class ObfuscationDataProvider implements IObfuscationDataProvider {
  private final List<ObfuscationEnvironment> environments;
  
  private final IMixinAnnotationProcessor ap;
  
  public ObfuscationDataProvider(IMixinAnnotationProcessor paramIMixinAnnotationProcessor, List<ObfuscationEnvironment> paramList) {
    this.ap = paramIMixinAnnotationProcessor;
    this.environments = paramList;
  }
  
  public <T> ObfuscationData<T> getObfEntryRecursive(MemberInfo paramMemberInfo) {
    MemberInfo memberInfo = paramMemberInfo;
    ObfuscationData<String> obfuscationData = getObfClass(memberInfo.owner);
    ObfuscationData<?> obfuscationData1 = getObfEntry(memberInfo);
    try {
      while (obfuscationData1.isEmpty()) {
        TypeHandle typeHandle1 = this.ap.getTypeProvider().getTypeHandle(memberInfo.owner);
        if (typeHandle1 == null)
          return (ObfuscationData)obfuscationData1; 
        TypeHandle typeHandle2 = typeHandle1.getSuperclass();
        obfuscationData1 = getObfEntryUsing(memberInfo, typeHandle2);
        if (!obfuscationData1.isEmpty())
          return applyParents(obfuscationData, (ObfuscationData)obfuscationData1); 
        for (TypeHandle typeHandle : typeHandle1.getInterfaces()) {
          obfuscationData1 = getObfEntryUsing(memberInfo, typeHandle);
          if (!obfuscationData1.isEmpty())
            return applyParents(obfuscationData, (ObfuscationData)obfuscationData1); 
        } 
        if (typeHandle2 == null)
          break; 
        memberInfo = memberInfo.move(typeHandle2.getName());
      } 
    } catch (Exception exception) {
      exception.printStackTrace();
      return getObfEntry(paramMemberInfo);
    } 
    return (ObfuscationData)obfuscationData1;
  }
  
  private <T> ObfuscationData<T> getObfEntryUsing(MemberInfo paramMemberInfo, TypeHandle paramTypeHandle) {
    return (paramTypeHandle == null) ? new ObfuscationData<T>() : getObfEntry(paramMemberInfo.move(paramTypeHandle.getName()));
  }
  
  public <T> ObfuscationData<T> getObfEntry(MemberInfo paramMemberInfo) {
    if (paramMemberInfo.isField())
      return (ObfuscationData)getObfField(paramMemberInfo); 
    return (ObfuscationData)getObfMethod(paramMemberInfo.asMethodMapping());
  }
  
  public <T> ObfuscationData<T> getObfEntry(IMapping<T> paramIMapping) {
    if (paramIMapping != null) {
      if (paramIMapping.getType() == IMapping.Type.FIELD)
        return (ObfuscationData)getObfField((MappingField)paramIMapping); 
      if (paramIMapping.getType() == IMapping.Type.METHOD)
        return (ObfuscationData)getObfMethod((MappingMethod)paramIMapping); 
    } 
    return new ObfuscationData<T>();
  }
  
  public ObfuscationData<MappingMethod> getObfMethodRecursive(MemberInfo paramMemberInfo) {
    return getObfEntryRecursive(paramMemberInfo);
  }
  
  public ObfuscationData<MappingMethod> getObfMethod(MemberInfo paramMemberInfo) {
    return getRemappedMethod(paramMemberInfo, paramMemberInfo.isConstructor());
  }
  
  public ObfuscationData<MappingMethod> getRemappedMethod(MemberInfo paramMemberInfo) {
    return getRemappedMethod(paramMemberInfo, true);
  }
  
  private ObfuscationData<MappingMethod> getRemappedMethod(MemberInfo paramMemberInfo, boolean paramBoolean) {
    ObfuscationData<MappingMethod> obfuscationData = new ObfuscationData();
    for (ObfuscationEnvironment obfuscationEnvironment : this.environments) {
      MappingMethod mappingMethod = obfuscationEnvironment.getObfMethod(paramMemberInfo);
      if (mappingMethod != null)
        obfuscationData.put(obfuscationEnvironment.getType(), mappingMethod); 
    } 
    if (!obfuscationData.isEmpty() || !paramBoolean)
      return obfuscationData; 
    return remapDescriptor(obfuscationData, paramMemberInfo);
  }
  
  public ObfuscationData<MappingMethod> getObfMethod(MappingMethod paramMappingMethod) {
    return getRemappedMethod(paramMappingMethod, paramMappingMethod.isConstructor());
  }
  
  public ObfuscationData<MappingMethod> getRemappedMethod(MappingMethod paramMappingMethod) {
    return getRemappedMethod(paramMappingMethod, true);
  }
  
  private ObfuscationData<MappingMethod> getRemappedMethod(MappingMethod paramMappingMethod, boolean paramBoolean) {
    ObfuscationData<MappingMethod> obfuscationData = new ObfuscationData();
    for (ObfuscationEnvironment obfuscationEnvironment : this.environments) {
      MappingMethod mappingMethod = obfuscationEnvironment.getObfMethod(paramMappingMethod);
      if (mappingMethod != null)
        obfuscationData.put(obfuscationEnvironment.getType(), mappingMethod); 
    } 
    if (!obfuscationData.isEmpty() || !paramBoolean)
      return obfuscationData; 
    return remapDescriptor(obfuscationData, new MemberInfo((IMapping)paramMappingMethod));
  }
  
  public ObfuscationData<MappingMethod> remapDescriptor(ObfuscationData<MappingMethod> paramObfuscationData, MemberInfo paramMemberInfo) {
    for (ObfuscationEnvironment obfuscationEnvironment : this.environments) {
      MemberInfo memberInfo = obfuscationEnvironment.remapDescriptor(paramMemberInfo);
      if (memberInfo != null)
        paramObfuscationData.put(obfuscationEnvironment.getType(), memberInfo.asMethodMapping()); 
    } 
    return paramObfuscationData;
  }
  
  public ObfuscationData<MappingField> getObfFieldRecursive(MemberInfo paramMemberInfo) {
    return getObfEntryRecursive(paramMemberInfo);
  }
  
  public ObfuscationData<MappingField> getObfField(MemberInfo paramMemberInfo) {
    return getObfField(paramMemberInfo.asFieldMapping());
  }
  
  public ObfuscationData<MappingField> getObfField(MappingField paramMappingField) {
    ObfuscationData<MappingField> obfuscationData = new ObfuscationData();
    for (ObfuscationEnvironment obfuscationEnvironment : this.environments) {
      MappingField mappingField = obfuscationEnvironment.getObfField(paramMappingField);
      if (mappingField != null) {
        if (mappingField.getDesc() == null && paramMappingField.getDesc() != null)
          mappingField = mappingField.transform(obfuscationEnvironment.remapDescriptor(paramMappingField.getDesc())); 
        obfuscationData.put(obfuscationEnvironment.getType(), mappingField);
      } 
    } 
    return obfuscationData;
  }
  
  public ObfuscationData<String> getObfClass(TypeHandle paramTypeHandle) {
    return getObfClass(paramTypeHandle.getName());
  }
  
  public ObfuscationData<String> getObfClass(String paramString) {
    ObfuscationData<String> obfuscationData = new ObfuscationData<String>(paramString);
    for (ObfuscationEnvironment obfuscationEnvironment : this.environments) {
      String str = obfuscationEnvironment.getObfClass(paramString);
      if (str != null)
        obfuscationData.put(obfuscationEnvironment.getType(), str); 
    } 
    return obfuscationData;
  }
  
  private static <T> ObfuscationData<T> applyParents(ObfuscationData<String> paramObfuscationData, ObfuscationData<T> paramObfuscationData1) {
    for (ObfuscationType obfuscationType : paramObfuscationData1) {
      String str = paramObfuscationData.get(obfuscationType);
      T t = paramObfuscationData1.get(obfuscationType);
      paramObfuscationData1.put(obfuscationType, (T)MemberInfo.fromMapping((IMapping)t).move(str).asMapping());
    } 
    return paramObfuscationData1;
  }
}
