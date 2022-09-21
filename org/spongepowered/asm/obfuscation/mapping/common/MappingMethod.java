package org.spongepowered.asm.obfuscation.mapping.common;

import com.google.common.base.Objects;
import org.spongepowered.asm.obfuscation.mapping.IMapping;

public class MappingMethod implements IMapping<MappingMethod> {
  private final String desc;
  
  private final String name;
  
  private final String owner;
  
  public MappingMethod(String paramString1, String paramString2) {
    this(getOwnerFromName(paramString1), getBaseName(paramString1), paramString2);
  }
  
  public MappingMethod(String paramString1, String paramString2, String paramString3) {
    this.owner = paramString1;
    this.name = paramString2;
    this.desc = paramString3;
  }
  
  public IMapping.Type getType() {
    return IMapping.Type.METHOD;
  }
  
  public String getName() {
    if (this.name == null)
      return null; 
    return ((this.owner != null) ? (this.owner + "/") : "") + this.name;
  }
  
  public String getSimpleName() {
    return this.name;
  }
  
  public String getOwner() {
    return this.owner;
  }
  
  public String getDesc() {
    return this.desc;
  }
  
  public MappingMethod getSuper() {
    return null;
  }
  
  public boolean isConstructor() {
    return "<init>".equals(this.name);
  }
  
  public MappingMethod move(String paramString) {
    return new MappingMethod(paramString, getSimpleName(), getDesc());
  }
  
  public MappingMethod remap(String paramString) {
    return new MappingMethod(getOwner(), paramString, getDesc());
  }
  
  public MappingMethod transform(String paramString) {
    return new MappingMethod(getOwner(), getSimpleName(), paramString);
  }
  
  public MappingMethod copy() {
    return new MappingMethod(getOwner(), getSimpleName(), getDesc());
  }
  
  public MappingMethod addPrefix(String paramString) {
    String str = getSimpleName();
    if (str == null || str.startsWith(paramString))
      return this; 
    return new MappingMethod(getOwner(), paramString + str, getDesc());
  }
  
  public int hashCode() {
    return Objects.hashCode(new Object[] { getName(), getDesc() });
  }
  
  public boolean equals(Object paramObject) {
    if (this == paramObject)
      return true; 
    if (paramObject instanceof MappingMethod)
      return (Objects.equal(this.name, ((MappingMethod)paramObject).name) && Objects.equal(this.desc, ((MappingMethod)paramObject).desc)); 
    return false;
  }
  
  public String serialise() {
    return toString();
  }
  
  public String toString() {
    String str = getDesc();
    return String.format("%s%s%s", new Object[] { getName(), (str != null) ? " " : "", (str != null) ? str : "" });
  }
  
  private static String getBaseName(String paramString) {
    if (paramString == null)
      return null; 
    int i = paramString.lastIndexOf('/');
    return (i > -1) ? paramString.substring(i + 1) : paramString;
  }
  
  private static String getOwnerFromName(String paramString) {
    if (paramString == null)
      return null; 
    int i = paramString.lastIndexOf('/');
    return (i > -1) ? paramString.substring(0, i) : null;
  }
}
