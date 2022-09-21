package org.spongepowered.asm.mixin.injection.struct;

import com.google.common.base.Objects;
import com.google.common.base.Strings;
import org.spongepowered.asm.lib.Type;
import org.spongepowered.asm.lib.tree.AbstractInsnNode;
import org.spongepowered.asm.lib.tree.FieldInsnNode;
import org.spongepowered.asm.lib.tree.MethodInsnNode;
import org.spongepowered.asm.mixin.refmap.IMixinContext;
import org.spongepowered.asm.mixin.refmap.IReferenceMapper;
import org.spongepowered.asm.mixin.throwables.MixinException;
import org.spongepowered.asm.obfuscation.mapping.IMapping;
import org.spongepowered.asm.obfuscation.mapping.common.MappingField;
import org.spongepowered.asm.obfuscation.mapping.common.MappingMethod;
import org.spongepowered.asm.util.SignaturePrinter;

public final class MemberInfo {
  public final boolean matchAll;
  
  private final String unparsed;
  
  public final String owner;
  
  public final String desc;
  
  public final String name;
  
  private final boolean forceField;
  
  public MemberInfo(String paramString, boolean paramBoolean) {
    this(paramString, null, null, paramBoolean);
  }
  
  public MemberInfo(String paramString1, String paramString2, boolean paramBoolean) {
    this(paramString1, paramString2, null, paramBoolean);
  }
  
  public MemberInfo(String paramString1, String paramString2, String paramString3) {
    this(paramString1, paramString2, paramString3, false);
  }
  
  public MemberInfo(String paramString1, String paramString2, String paramString3, boolean paramBoolean) {
    this(paramString1, paramString2, paramString3, paramBoolean, null);
  }
  
  public MemberInfo(String paramString1, String paramString2, String paramString3, boolean paramBoolean, String paramString4) {
    if (paramString2 != null && paramString2.contains("."))
      throw new IllegalArgumentException("Attempt to instance a MemberInfo with an invalid owner format"); 
    this.owner = paramString2;
    this.name = paramString1;
    this.desc = paramString3;
    this.matchAll = paramBoolean;
    this.forceField = false;
    this.unparsed = paramString4;
  }
  
  public MemberInfo(AbstractInsnNode paramAbstractInsnNode) {
    this.matchAll = false;
    this.forceField = false;
    this.unparsed = null;
    if (paramAbstractInsnNode instanceof MethodInsnNode) {
      MethodInsnNode methodInsnNode = (MethodInsnNode)paramAbstractInsnNode;
      this.owner = methodInsnNode.owner;
      this.name = methodInsnNode.name;
      this.desc = methodInsnNode.desc;
    } else if (paramAbstractInsnNode instanceof FieldInsnNode) {
      FieldInsnNode fieldInsnNode = (FieldInsnNode)paramAbstractInsnNode;
      this.owner = fieldInsnNode.owner;
      this.name = fieldInsnNode.name;
      this.desc = fieldInsnNode.desc;
    } else {
      throw new IllegalArgumentException("insn must be an instance of MethodInsnNode or FieldInsnNode");
    } 
  }
  
  public MemberInfo(IMapping<?> paramIMapping) {
    this.owner = paramIMapping.getOwner();
    this.name = paramIMapping.getSimpleName();
    this.desc = paramIMapping.getDesc();
    this.matchAll = false;
    this.forceField = (paramIMapping.getType() == IMapping.Type.FIELD);
    this.unparsed = null;
  }
  
  private MemberInfo(MemberInfo paramMemberInfo, MappingMethod paramMappingMethod, boolean paramBoolean) {
    this.owner = paramBoolean ? paramMappingMethod.getOwner() : paramMemberInfo.owner;
    this.name = paramMappingMethod.getSimpleName();
    this.desc = paramMappingMethod.getDesc();
    this.matchAll = paramMemberInfo.matchAll;
    this.forceField = false;
    this.unparsed = null;
  }
  
  private MemberInfo(MemberInfo paramMemberInfo, String paramString) {
    this.owner = paramString;
    this.name = paramMemberInfo.name;
    this.desc = paramMemberInfo.desc;
    this.matchAll = paramMemberInfo.matchAll;
    this.forceField = paramMemberInfo.forceField;
    this.unparsed = null;
  }
  
  public String toString() {
    String str1 = (this.owner != null) ? ("L" + this.owner + ";") : "";
    String str2 = (this.name != null) ? this.name : "";
    String str3 = this.matchAll ? "*" : "";
    String str4 = (this.desc != null) ? this.desc : "";
    String str5 = str4.startsWith("(") ? "" : ((this.desc != null) ? ":" : "");
    return str1 + str2 + str3 + str5 + str4;
  }
  
  @Deprecated
  public String toSrg() {
    if (!isFullyQualified())
      throw new MixinException("Cannot convert unqualified reference to SRG mapping"); 
    if (this.desc.startsWith("("))
      return this.owner + "/" + this.name + " " + this.desc; 
    return this.owner + "/" + this.name;
  }
  
  public String toDescriptor() {
    if (this.desc == null)
      return ""; 
    return (new SignaturePrinter(this)).setFullyQualified(true).toDescriptor();
  }
  
  public String toCtorType() {
    if (this.unparsed == null)
      return null; 
    String str = getReturnType();
    if (str != null)
      return str; 
    if (this.owner != null)
      return this.owner; 
    if (this.name != null && this.desc == null)
      return this.name; 
    return (this.desc != null) ? this.desc : this.unparsed;
  }
  
  public String toCtorDesc() {
    if (this.desc != null && this.desc.startsWith("(") && this.desc.indexOf(')') > -1)
      return this.desc.substring(0, this.desc.indexOf(')') + 1) + "V"; 
    return null;
  }
  
  public String getReturnType() {
    if (this.desc == null || this.desc.indexOf(')') == -1 || this.desc.indexOf('(') != 0)
      return null; 
    String str = this.desc.substring(this.desc.indexOf(')') + 1);
    if (str.startsWith("L") && str.endsWith(";"))
      return str.substring(1, str.length() - 1); 
    return str;
  }
  
  public IMapping<?> asMapping() {
    return isField() ? (IMapping<?>)asFieldMapping() : (IMapping<?>)asMethodMapping();
  }
  
  public MappingMethod asMethodMapping() {
    if (!isFullyQualified())
      throw new MixinException("Cannot convert unqualified reference " + this + " to MethodMapping"); 
    if (isField())
      throw new MixinException("Cannot convert a non-method reference " + this + " to MethodMapping"); 
    return new MappingMethod(this.owner, this.name, this.desc);
  }
  
  public MappingField asFieldMapping() {
    if (!isField())
      throw new MixinException("Cannot convert non-field reference " + this + " to FieldMapping"); 
    return new MappingField(this.owner, this.name, this.desc);
  }
  
  public boolean isFullyQualified() {
    return (this.owner != null && this.name != null && this.desc != null);
  }
  
  public boolean isField() {
    return (this.forceField || (this.desc != null && !this.desc.startsWith("(")));
  }
  
  public boolean isConstructor() {
    return "<init>".equals(this.name);
  }
  
  public boolean isClassInitialiser() {
    return "<clinit>".equals(this.name);
  }
  
  public boolean isInitialiser() {
    return (isConstructor() || isClassInitialiser());
  }
  
  public MemberInfo validate() throws InvalidMemberDescriptorException {
    if (this.owner != null) {
      if (!this.owner.matches("(?i)^[\\w\\p{Sc}/]+$"))
        throw new InvalidMemberDescriptorException("Invalid owner: " + this.owner); 
      if (this.unparsed != null && this.unparsed.lastIndexOf('.') > 0 && this.owner.startsWith("L"))
        throw new InvalidMemberDescriptorException("Malformed owner: " + this.owner + " If you are seeing this message unexpectedly and the owner appears to be correct, replace the owner descriptor with formal type L" + this.owner + "; to suppress this error"); 
    } 
    if (this.name != null && !this.name.matches("(?i)^<?[\\w\\p{Sc}]+>?$"))
      throw new InvalidMemberDescriptorException("Invalid name: " + this.name); 
    if (this.desc != null) {
      if (!this.desc.matches("^(\\([\\w\\p{Sc}\\[/;]*\\))?\\[*[\\w\\p{Sc}/;]+$"))
        throw new InvalidMemberDescriptorException("Invalid descriptor: " + this.desc); 
      if (isField()) {
        if (!this.desc.equals(Type.getType(this.desc).getDescriptor()))
          throw new InvalidMemberDescriptorException("Invalid field type in descriptor: " + this.desc); 
      } else {
        try {
          Type.getArgumentTypes(this.desc);
        } catch (Exception exception) {
          throw new InvalidMemberDescriptorException("Invalid descriptor: " + this.desc);
        } 
        String str = this.desc.substring(this.desc.indexOf(')') + 1);
        try {
          Type type = Type.getType(str);
          if (!str.equals(type.getDescriptor()))
            throw new InvalidMemberDescriptorException("Invalid return type \"" + str + "\" in descriptor: " + this.desc); 
        } catch (Exception exception) {
          throw new InvalidMemberDescriptorException("Invalid return type \"" + str + "\" in descriptor: " + this.desc);
        } 
      } 
    } 
    return this;
  }
  
  public boolean matches(String paramString1, String paramString2, String paramString3) {
    return matches(paramString1, paramString2, paramString3, 0);
  }
  
  public boolean matches(String paramString1, String paramString2, String paramString3, int paramInt) {
    if (this.desc != null && paramString3 != null && !this.desc.equals(paramString3))
      return false; 
    if (this.name != null && paramString2 != null && !this.name.equals(paramString2))
      return false; 
    if (this.owner != null && paramString1 != null && !this.owner.equals(paramString1))
      return false; 
    return (paramInt == 0 || this.matchAll);
  }
  
  public boolean matches(String paramString1, String paramString2) {
    return matches(paramString1, paramString2, 0);
  }
  
  public boolean matches(String paramString1, String paramString2, int paramInt) {
    return ((this.name == null || this.name.equals(paramString1)) && (this.desc == null || (paramString2 != null && paramString2
      .equals(this.desc))) && (paramInt == 0 || this.matchAll));
  }
  
  public boolean equals(Object paramObject) {
    if (paramObject == null || paramObject.getClass() != MemberInfo.class)
      return false; 
    MemberInfo memberInfo = (MemberInfo)paramObject;
    return (this.matchAll == memberInfo.matchAll && this.forceField == memberInfo.forceField && 
      Objects.equal(this.owner, memberInfo.owner) && 
      Objects.equal(this.name, memberInfo.name) && 
      Objects.equal(this.desc, memberInfo.desc));
  }
  
  public int hashCode() {
    return Objects.hashCode(new Object[] { Boolean.valueOf(this.matchAll), this.owner, this.name, this.desc });
  }
  
  public MemberInfo move(String paramString) {
    if ((paramString == null && this.owner == null) || (paramString != null && paramString.equals(this.owner)))
      return this; 
    return new MemberInfo(this, paramString);
  }
  
  public MemberInfo transform(String paramString) {
    if ((paramString == null && this.desc == null) || (paramString != null && paramString.equals(this.desc)))
      return this; 
    return new MemberInfo(this.name, this.owner, paramString, this.matchAll);
  }
  
  public MemberInfo remapUsing(MappingMethod paramMappingMethod, boolean paramBoolean) {
    return new MemberInfo(this, paramMappingMethod, paramBoolean);
  }
  
  public static MemberInfo parseAndValidate(String paramString) throws InvalidMemberDescriptorException {
    return parse(paramString, null, null).validate();
  }
  
  public static MemberInfo parseAndValidate(String paramString, IMixinContext paramIMixinContext) throws InvalidMemberDescriptorException {
    return parse(paramString, paramIMixinContext.getReferenceMapper(), paramIMixinContext.getClassRef()).validate();
  }
  
  public static MemberInfo parse(String paramString) {
    return parse(paramString, null, null);
  }
  
  public static MemberInfo parse(String paramString, IMixinContext paramIMixinContext) {
    return parse(paramString, paramIMixinContext.getReferenceMapper(), paramIMixinContext.getClassRef());
  }
  
  private static MemberInfo parse(String paramString1, IReferenceMapper paramIReferenceMapper, String paramString2) {
    String str1 = null;
    String str2 = null;
    String str3 = Strings.nullToEmpty(paramString1).replaceAll("\\s", "");
    if (paramIReferenceMapper != null)
      str3 = paramIReferenceMapper.remap(paramString2, str3); 
    int i = str3.lastIndexOf('.');
    int j = str3.indexOf(';');
    if (i > -1) {
      str2 = str3.substring(0, i).replace('.', '/');
      str3 = str3.substring(i + 1);
    } else if (j > -1 && str3.startsWith("L")) {
      str2 = str3.substring(1, j).replace('.', '/');
      str3 = str3.substring(j + 1);
    } 
    int k = str3.indexOf('(');
    int m = str3.indexOf(':');
    if (k > -1) {
      str1 = str3.substring(k);
      str3 = str3.substring(0, k);
    } else if (m > -1) {
      str1 = str3.substring(m + 1);
      str3 = str3.substring(0, m);
    } 
    if ((str3.indexOf('/') > -1 || str3.indexOf('.') > -1) && str2 == null) {
      str2 = str3;
      str3 = "";
    } 
    boolean bool = str3.endsWith("*");
    if (bool)
      str3 = str3.substring(0, str3.length() - 1); 
    if (str3.isEmpty())
      str3 = null; 
    return new MemberInfo(str3, str2, str1, bool, paramString1);
  }
  
  public static MemberInfo fromMapping(IMapping<?> paramIMapping) {
    return new MemberInfo(paramIMapping);
  }
}
