package org.spongepowered.tools.obfuscation.mirror;

import java.io.Serializable;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.TypeElement;

public class TypeReference implements Serializable, Comparable<TypeReference> {
  private static final long serialVersionUID = 1L;
  
  private transient TypeHandle handle;
  
  private final String name;
  
  public TypeReference(TypeHandle paramTypeHandle) {
    this.name = paramTypeHandle.getName();
    this.handle = paramTypeHandle;
  }
  
  public TypeReference(String paramString) {
    this.name = paramString;
  }
  
  public String getName() {
    return this.name;
  }
  
  public String getClassName() {
    return this.name.replace('/', '.');
  }
  
  public TypeHandle getHandle(ProcessingEnvironment paramProcessingEnvironment) {
    if (this.handle == null) {
      TypeElement typeElement = paramProcessingEnvironment.getElementUtils().getTypeElement(getClassName());
      try {
        this.handle = new TypeHandle(typeElement);
      } catch (Exception exception) {
        exception.printStackTrace();
      } 
    } 
    return this.handle;
  }
  
  public String toString() {
    return String.format("TypeReference[%s]", new Object[] { this.name });
  }
  
  public int compareTo(TypeReference paramTypeReference) {
    return (paramTypeReference == null) ? -1 : this.name.compareTo(paramTypeReference.name);
  }
  
  public boolean equals(Object paramObject) {
    return (paramObject instanceof TypeReference && compareTo((TypeReference)paramObject) == 0);
  }
  
  public int hashCode() {
    return this.name.hashCode();
  }
}
