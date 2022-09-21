package org.spongepowered.tools.obfuscation.mapping.common;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import org.spongepowered.asm.obfuscation.mapping.common.MappingField;
import org.spongepowered.asm.obfuscation.mapping.common.MappingMethod;
import org.spongepowered.tools.obfuscation.mapping.IMappingProvider;

public abstract class MappingProvider implements IMappingProvider {
  protected final BiMap<String, String> packageMap = (BiMap<String, String>)HashBiMap.create();
  
  protected final BiMap<String, String> classMap = (BiMap<String, String>)HashBiMap.create();
  
  protected final BiMap<MappingField, MappingField> fieldMap = (BiMap<MappingField, MappingField>)HashBiMap.create();
  
  protected final BiMap<MappingMethod, MappingMethod> methodMap = (BiMap<MappingMethod, MappingMethod>)HashBiMap.create();
  
  protected final Filer filer;
  
  protected final Messager messager;
  
  public MappingProvider(Messager paramMessager, Filer paramFiler) {
    this.messager = paramMessager;
    this.filer = paramFiler;
  }
  
  public void clear() {
    this.packageMap.clear();
    this.classMap.clear();
    this.fieldMap.clear();
    this.methodMap.clear();
  }
  
  public boolean isEmpty() {
    return (this.packageMap.isEmpty() && this.classMap.isEmpty() && this.fieldMap.isEmpty() && this.methodMap.isEmpty());
  }
  
  public MappingMethod getMethodMapping(MappingMethod paramMappingMethod) {
    return (MappingMethod)this.methodMap.get(paramMappingMethod);
  }
  
  public MappingField getFieldMapping(MappingField paramMappingField) {
    return (MappingField)this.fieldMap.get(paramMappingField);
  }
  
  public String getClassMapping(String paramString) {
    return (String)this.classMap.get(paramString);
  }
  
  public String getPackageMapping(String paramString) {
    return (String)this.packageMap.get(paramString);
  }
}
