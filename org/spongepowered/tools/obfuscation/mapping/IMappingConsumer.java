package org.spongepowered.tools.obfuscation.mapping;

import com.google.common.base.Objects;
import java.util.LinkedHashSet;
import org.spongepowered.asm.obfuscation.mapping.IMapping;
import org.spongepowered.asm.obfuscation.mapping.common.MappingField;
import org.spongepowered.asm.obfuscation.mapping.common.MappingMethod;
import org.spongepowered.tools.obfuscation.ObfuscationType;

public interface IMappingConsumer {
  void clear();
  
  MappingSet<MappingMethod> getMethodMappings(ObfuscationType paramObfuscationType);
  
  void addFieldMapping(ObfuscationType paramObfuscationType, MappingField paramMappingField1, MappingField paramMappingField2);
  
  void addMethodMapping(ObfuscationType paramObfuscationType, MappingMethod paramMappingMethod1, MappingMethod paramMappingMethod2);
  
  MappingSet<MappingField> getFieldMappings(ObfuscationType paramObfuscationType);
  
  public static class MappingSet<TMapping extends IMapping<TMapping>> extends LinkedHashSet<MappingSet.Pair<TMapping>> {
    private static final long serialVersionUID = 1L;
    
    public static class Pair<TMapping extends IMapping<TMapping>> {
      public final TMapping to;
      
      public final TMapping from;
      
      public Pair(TMapping param2TMapping1, TMapping param2TMapping2) {
        this.from = param2TMapping1;
        this.to = param2TMapping2;
      }
      
      public boolean equals(Object param2Object) {
        if (!(param2Object instanceof Pair))
          return false; 
        Pair pair = (Pair)param2Object;
        return (Objects.equal(this.from, pair.from) && Objects.equal(this.to, pair.to));
      }
      
      public int hashCode() {
        return Objects.hashCode(new Object[] { this.from, this.to });
      }
      
      public String toString() {
        return String.format("%s -> %s", new Object[] { this.from, this.to });
      }
    }
  }
}
