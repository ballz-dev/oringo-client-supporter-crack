package org.spongepowered.tools.obfuscation;

import java.util.HashMap;
import java.util.Map;
import org.spongepowered.asm.obfuscation.mapping.IMapping;
import org.spongepowered.asm.obfuscation.mapping.common.MappingField;
import org.spongepowered.asm.obfuscation.mapping.common.MappingMethod;
import org.spongepowered.tools.obfuscation.mapping.IMappingConsumer;

class Mappings implements IMappingConsumer {
  private UniqueMappings unique;
  
  public static class MappingConflictException extends RuntimeException {
    private final IMapping<?> oldMapping;
    
    private static final long serialVersionUID = 1L;
    
    private final IMapping<?> newMapping;
    
    public MappingConflictException(IMapping<?> param1IMapping1, IMapping<?> param1IMapping2) {
      this.oldMapping = param1IMapping1;
      this.newMapping = param1IMapping2;
    }
    
    public IMapping<?> getOld() {
      return this.oldMapping;
    }
    
    public IMapping<?> getNew() {
      return this.newMapping;
    }
  }
  
  static class UniqueMappings implements IMappingConsumer {
    private final IMappingConsumer mappings;
    
    private final Map<ObfuscationType, Map<MappingField, MappingField>> fields = new HashMap<ObfuscationType, Map<MappingField, MappingField>>();
    
    private final Map<ObfuscationType, Map<MappingMethod, MappingMethod>> methods = new HashMap<ObfuscationType, Map<MappingMethod, MappingMethod>>();
    
    public UniqueMappings(IMappingConsumer param1IMappingConsumer) {
      this.mappings = param1IMappingConsumer;
    }
    
    public void clear() {
      clearMaps();
      this.mappings.clear();
    }
    
    protected void clearMaps() {
      this.fields.clear();
      this.methods.clear();
    }
    
    public void addFieldMapping(ObfuscationType param1ObfuscationType, MappingField param1MappingField1, MappingField param1MappingField2) {
      if (!checkForExistingMapping(param1ObfuscationType, param1MappingField1, param1MappingField2, this.fields))
        this.mappings.addFieldMapping(param1ObfuscationType, param1MappingField1, param1MappingField2); 
    }
    
    public void addMethodMapping(ObfuscationType param1ObfuscationType, MappingMethod param1MappingMethod1, MappingMethod param1MappingMethod2) {
      if (!checkForExistingMapping(param1ObfuscationType, param1MappingMethod1, param1MappingMethod2, this.methods))
        this.mappings.addMethodMapping(param1ObfuscationType, param1MappingMethod1, param1MappingMethod2); 
    }
    
    private <TMapping extends IMapping<TMapping>> boolean checkForExistingMapping(ObfuscationType param1ObfuscationType, TMapping param1TMapping1, TMapping param1TMapping2, Map<ObfuscationType, Map<TMapping, TMapping>> param1Map) throws Mappings.MappingConflictException {
      Map<Object, Object> map = (Map)param1Map.get(param1ObfuscationType);
      if (map == null) {
        map = new HashMap<Object, Object>();
        param1Map.put(param1ObfuscationType, map);
      } 
      IMapping iMapping = (IMapping)map.get(param1TMapping1);
      if (iMapping != null) {
        if (iMapping.equals(param1TMapping2))
          return true; 
        throw new Mappings.MappingConflictException(iMapping, param1TMapping2);
      } 
      map.put(param1TMapping1, param1TMapping2);
      return false;
    }
    
    public IMappingConsumer.MappingSet<MappingField> getFieldMappings(ObfuscationType param1ObfuscationType) {
      return this.mappings.getFieldMappings(param1ObfuscationType);
    }
    
    public IMappingConsumer.MappingSet<MappingMethod> getMethodMappings(ObfuscationType param1ObfuscationType) {
      return this.mappings.getMethodMappings(param1ObfuscationType);
    }
  }
  
  private final Map<ObfuscationType, IMappingConsumer.MappingSet<MappingField>> fieldMappings = new HashMap<ObfuscationType, IMappingConsumer.MappingSet<MappingField>>();
  
  private final Map<ObfuscationType, IMappingConsumer.MappingSet<MappingMethod>> methodMappings = new HashMap<ObfuscationType, IMappingConsumer.MappingSet<MappingMethod>>();
  
  public Mappings() {
    init();
  }
  
  private void init() {
    for (ObfuscationType obfuscationType : ObfuscationType.types()) {
      this.fieldMappings.put(obfuscationType, new IMappingConsumer.MappingSet());
      this.methodMappings.put(obfuscationType, new IMappingConsumer.MappingSet());
    } 
  }
  
  public IMappingConsumer asUnique() {
    if (this.unique == null)
      this.unique = new UniqueMappings(this); 
    return this.unique;
  }
  
  public IMappingConsumer.MappingSet<MappingField> getFieldMappings(ObfuscationType paramObfuscationType) {
    IMappingConsumer.MappingSet<MappingField> mappingSet = this.fieldMappings.get(paramObfuscationType);
    return (mappingSet != null) ? mappingSet : new IMappingConsumer.MappingSet();
  }
  
  public IMappingConsumer.MappingSet<MappingMethod> getMethodMappings(ObfuscationType paramObfuscationType) {
    IMappingConsumer.MappingSet<MappingMethod> mappingSet = this.methodMappings.get(paramObfuscationType);
    return (mappingSet != null) ? mappingSet : new IMappingConsumer.MappingSet();
  }
  
  public void clear() {
    this.fieldMappings.clear();
    this.methodMappings.clear();
    if (this.unique != null)
      this.unique.clearMaps(); 
    init();
  }
  
  public void addFieldMapping(ObfuscationType paramObfuscationType, MappingField paramMappingField1, MappingField paramMappingField2) {
    IMappingConsumer.MappingSet<MappingField> mappingSet = this.fieldMappings.get(paramObfuscationType);
    if (mappingSet == null) {
      mappingSet = new IMappingConsumer.MappingSet();
      this.fieldMappings.put(paramObfuscationType, mappingSet);
    } 
    mappingSet.add(new IMappingConsumer.MappingSet.Pair((IMapping)paramMappingField1, (IMapping)paramMappingField2));
  }
  
  public void addMethodMapping(ObfuscationType paramObfuscationType, MappingMethod paramMappingMethod1, MappingMethod paramMappingMethod2) {
    IMappingConsumer.MappingSet<MappingMethod> mappingSet = this.methodMappings.get(paramObfuscationType);
    if (mappingSet == null) {
      mappingSet = new IMappingConsumer.MappingSet();
      this.methodMappings.put(paramObfuscationType, mappingSet);
    } 
    mappingSet.add(new IMappingConsumer.MappingSet.Pair((IMapping)paramMappingMethod1, (IMapping)paramMappingMethod2));
  }
}
