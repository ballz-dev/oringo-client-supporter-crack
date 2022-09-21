package org.spongepowered.tools.obfuscation.mirror;

import com.google.common.base.Strings;
import javax.lang.model.element.ExecutableElement;
import org.spongepowered.asm.obfuscation.mapping.IMapping;
import org.spongepowered.asm.obfuscation.mapping.common.MappingMethod;
import org.spongepowered.tools.obfuscation.mirror.mapping.ResolvableMappingMethod;

public class MethodHandle extends MemberHandle<MappingMethod> {
  private final ExecutableElement element;
  
  private final TypeHandle ownerHandle;
  
  public MethodHandle(TypeHandle paramTypeHandle, ExecutableElement paramExecutableElement) {
    this(paramTypeHandle, paramExecutableElement, TypeUtils.getName(paramExecutableElement), TypeUtils.getDescriptor(paramExecutableElement));
  }
  
  public MethodHandle(TypeHandle paramTypeHandle, String paramString1, String paramString2) {
    this(paramTypeHandle, (ExecutableElement)null, paramString1, paramString2);
  }
  
  private MethodHandle(TypeHandle paramTypeHandle, ExecutableElement paramExecutableElement, String paramString1, String paramString2) {
    super((paramTypeHandle != null) ? paramTypeHandle.getName() : null, paramString1, paramString2);
    this.element = paramExecutableElement;
    this.ownerHandle = paramTypeHandle;
  }
  
  public boolean isImaginary() {
    return (this.element == null);
  }
  
  public ExecutableElement getElement() {
    return this.element;
  }
  
  public Visibility getVisibility() {
    return TypeUtils.getVisibility(this.element);
  }
  
  public MappingMethod asMapping(boolean paramBoolean) {
    if (paramBoolean) {
      if (this.ownerHandle != null)
        return (MappingMethod)new ResolvableMappingMethod(this.ownerHandle, getName(), getDesc()); 
      return new MappingMethod(getOwner(), getName(), getDesc());
    } 
    return new MappingMethod(null, getName(), getDesc());
  }
  
  public String toString() {
    String str1 = (getOwner() != null) ? ("L" + getOwner() + ";") : "";
    String str2 = Strings.nullToEmpty(getName());
    String str3 = Strings.nullToEmpty(getDesc());
    return String.format("%s%s%s", new Object[] { str1, str2, str3 });
  }
}
