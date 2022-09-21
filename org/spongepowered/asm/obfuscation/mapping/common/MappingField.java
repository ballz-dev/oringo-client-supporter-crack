package org.spongepowered.asm.obfuscation.mapping.common;

import com.google.common.base.Objects;
import com.google.common.base.Strings;
import org.spongepowered.asm.obfuscation.mapping.IMapping;

public class MappingField implements IMapping<MappingField> {
  private final String name;
  
  private final String owner;
  
  private final String desc;
  
  public MappingField(String paramString1, String paramString2) {
    this(paramString1, paramString2, null);
  }
  
  public MappingField(String paramString1, String paramString2, String paramString3) {
    this.owner = paramString1;
    this.name = paramString2;
    this.desc = paramString3;
  }
  
  public IMapping.Type getType() {
    return IMapping.Type.FIELD;
  }
  
  public String getName() {
    return this.name;
  }
  
  public final String getSimpleName() {
    return this.name;
  }
  
  public final String getOwner() {
    return this.owner;
  }
  
  public final String getDesc() {
    return this.desc;
  }
  
  public MappingField getSuper() {
    return null;
  }
  
  public MappingField move(String paramString) {
    return new MappingField(paramString, getName(), getDesc());
  }
  
  public MappingField remap(String paramString) {
    return new MappingField(getOwner(), paramString, getDesc());
  }
  
  public MappingField transform(String paramString) {
    return new MappingField(getOwner(), getName(), paramString);
  }
  
  public MappingField copy() {
    return new MappingField(getOwner(), getName(), getDesc());
  }
  
  public int hashCode() {
    return Objects.hashCode(new Object[] { toString() });
  }
  
  public boolean equals(Object paramObject) {
    if (this == paramObject)
      return true; 
    if (paramObject instanceof MappingField)
      return Objects.equal(toString(), ((MappingField)paramObject).toString()); 
    return false;
  }
  
  public String serialise() {
    return toString();
  }
  
  public String toString() {
    return String.format("L%s;%s:%s", new Object[] { getOwner(), getName(), Strings.nullToEmpty(getDesc()) });
  }
}
