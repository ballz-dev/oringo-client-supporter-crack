package org.spongepowered.tools.obfuscation;

import java.io.File;
import java.util.Collection;
import java.util.List;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.tools.Diagnostic;
import org.spongepowered.asm.mixin.injection.struct.MemberInfo;
import org.spongepowered.asm.obfuscation.mapping.common.MappingField;
import org.spongepowered.asm.obfuscation.mapping.common.MappingMethod;
import org.spongepowered.asm.util.ObfuscationUtil;
import org.spongepowered.tools.obfuscation.interfaces.IMixinAnnotationProcessor;
import org.spongepowered.tools.obfuscation.interfaces.IObfuscationEnvironment;
import org.spongepowered.tools.obfuscation.mapping.IMappingConsumer;
import org.spongepowered.tools.obfuscation.mapping.IMappingProvider;
import org.spongepowered.tools.obfuscation.mapping.IMappingWriter;
import org.spongepowered.tools.obfuscation.mirror.TypeHandle;

public abstract class ObfuscationEnvironment implements IObfuscationEnvironment {
  protected final IMappingWriter mappingWriter;
  
  protected final ObfuscationType type;
  
  private boolean initDone;
  
  protected final List<String> inFileNames;
  
  protected final IMixinAnnotationProcessor ap;
  
  protected final IMappingProvider mappingProvider;
  
  final class RemapperProxy implements ObfuscationUtil.IClassRemapper {
    public String map(String param1String) {
      if (ObfuscationEnvironment.this.mappingProvider == null)
        return null; 
      return ObfuscationEnvironment.this.mappingProvider.getClassMapping(param1String);
    }
    
    public String unmap(String param1String) {
      if (ObfuscationEnvironment.this.mappingProvider == null)
        return null; 
      return ObfuscationEnvironment.this.mappingProvider.getClassMapping(param1String);
    }
  }
  
  protected final RemapperProxy remapper = new RemapperProxy();
  
  protected final String outFileName;
  
  protected ObfuscationEnvironment(ObfuscationType paramObfuscationType) {
    this.type = paramObfuscationType;
    this.ap = paramObfuscationType.getAnnotationProcessor();
    this.inFileNames = paramObfuscationType.getInputFileNames();
    this.outFileName = paramObfuscationType.getOutputFileName();
    this.mappingProvider = getMappingProvider((Messager)this.ap, this.ap.getProcessingEnvironment().getFiler());
    this.mappingWriter = getMappingWriter((Messager)this.ap, this.ap.getProcessingEnvironment().getFiler());
  }
  
  public String toString() {
    return this.type.toString();
  }
  
  private boolean initMappings() {
    if (!this.initDone) {
      this.initDone = true;
      if (this.inFileNames == null) {
        this.ap.printMessage(Diagnostic.Kind.ERROR, "The " + this.type.getConfig().getInputFileOption() + " argument was not supplied, obfuscation processing will not occur");
        return false;
      } 
      byte b = 0;
      for (String str : this.inFileNames) {
        File file = new File(str);
        try {
          if (file.isFile()) {
            this.ap.printMessage(Diagnostic.Kind.NOTE, "Loading " + this.type + " mappings from " + file.getAbsolutePath());
            this.mappingProvider.read(file);
            b++;
          } 
        } catch (Exception exception) {
          exception.printStackTrace();
        } 
      } 
      if (b < 1) {
        this.ap.printMessage(Diagnostic.Kind.ERROR, "No valid input files for " + this.type + " could be read, processing may not be sucessful.");
        this.mappingProvider.clear();
      } 
    } 
    return !this.mappingProvider.isEmpty();
  }
  
  public ObfuscationType getType() {
    return this.type;
  }
  
  public MappingMethod getObfMethod(MemberInfo paramMemberInfo) {
    MappingMethod mappingMethod = getObfMethod(paramMemberInfo.asMethodMapping());
    if (mappingMethod != null || !paramMemberInfo.isFullyQualified())
      return mappingMethod; 
    TypeHandle typeHandle = this.ap.getTypeProvider().getTypeHandle(paramMemberInfo.owner);
    if (typeHandle == null || typeHandle.isImaginary())
      return null; 
    TypeMirror typeMirror = typeHandle.getElement().getSuperclass();
    if (typeMirror.getKind() != TypeKind.DECLARED)
      return null; 
    String str = ((TypeElement)((DeclaredType)typeMirror).asElement()).getQualifiedName().toString();
    return getObfMethod(new MemberInfo(paramMemberInfo.name, str.replace('.', '/'), paramMemberInfo.desc, paramMemberInfo.matchAll));
  }
  
  public MappingMethod getObfMethod(MappingMethod paramMappingMethod) {
    return getObfMethod(paramMappingMethod, true);
  }
  
  public MappingMethod getObfMethod(MappingMethod paramMappingMethod, boolean paramBoolean) {
    if (initMappings()) {
      boolean bool = true;
      MappingMethod mappingMethod1 = null;
      for (MappingMethod mappingMethod2 = paramMappingMethod; mappingMethod2 != null && mappingMethod1 == null; mappingMethod2 = mappingMethod2.getSuper())
        mappingMethod1 = this.mappingProvider.getMethodMapping(mappingMethod2); 
      if (mappingMethod1 == null) {
        if (paramBoolean)
          return null; 
        mappingMethod1 = paramMappingMethod.copy();
        bool = false;
      } 
      String str1 = getObfClass(mappingMethod1.getOwner());
      if (str1 == null || str1.equals(paramMappingMethod.getOwner()) || str1.equals(mappingMethod1.getOwner()))
        return bool ? mappingMethod1 : null; 
      if (bool)
        return mappingMethod1.move(str1); 
      String str2 = ObfuscationUtil.mapDescriptor(mappingMethod1.getDesc(), this.remapper);
      return new MappingMethod(str1, mappingMethod1.getSimpleName(), str2);
    } 
    return null;
  }
  
  public MemberInfo remapDescriptor(MemberInfo paramMemberInfo) {
    boolean bool = false;
    String str1 = paramMemberInfo.owner;
    if (str1 != null) {
      String str = this.remapper.map(str1);
      if (str != null) {
        str1 = str;
        bool = true;
      } 
    } 
    String str2 = paramMemberInfo.desc;
    if (str2 != null) {
      String str = ObfuscationUtil.mapDescriptor(paramMemberInfo.desc, this.remapper);
      if (!str.equals(paramMemberInfo.desc)) {
        str2 = str;
        bool = true;
      } 
    } 
    return bool ? new MemberInfo(paramMemberInfo.name, str1, str2, paramMemberInfo.matchAll) : null;
  }
  
  public String remapDescriptor(String paramString) {
    return ObfuscationUtil.mapDescriptor(paramString, this.remapper);
  }
  
  public MappingField getObfField(MemberInfo paramMemberInfo) {
    return getObfField(paramMemberInfo.asFieldMapping(), true);
  }
  
  public MappingField getObfField(MappingField paramMappingField) {
    return getObfField(paramMappingField, true);
  }
  
  public MappingField getObfField(MappingField paramMappingField, boolean paramBoolean) {
    if (!initMappings())
      return null; 
    MappingField mappingField = this.mappingProvider.getFieldMapping(paramMappingField);
    if (mappingField == null) {
      if (paramBoolean)
        return null; 
      mappingField = paramMappingField;
    } 
    String str = getObfClass(mappingField.getOwner());
    if (str == null || str.equals(paramMappingField.getOwner()) || str.equals(mappingField.getOwner()))
      return (mappingField != paramMappingField) ? mappingField : null; 
    return mappingField.move(str);
  }
  
  public String getObfClass(String paramString) {
    if (!initMappings())
      return null; 
    return this.mappingProvider.getClassMapping(paramString);
  }
  
  public void writeMappings(Collection<IMappingConsumer> paramCollection) {
    IMappingConsumer.MappingSet mappingSet1 = new IMappingConsumer.MappingSet();
    IMappingConsumer.MappingSet mappingSet2 = new IMappingConsumer.MappingSet();
    for (IMappingConsumer iMappingConsumer : paramCollection) {
      mappingSet1.addAll((Collection)iMappingConsumer.getFieldMappings(this.type));
      mappingSet2.addAll((Collection)iMappingConsumer.getMethodMappings(this.type));
    } 
    this.mappingWriter.write(this.outFileName, this.type, mappingSet1, mappingSet2);
  }
  
  protected abstract IMappingProvider getMappingProvider(Messager paramMessager, Filer paramFiler);
  
  protected abstract IMappingWriter getMappingWriter(Messager paramMessager, Filer paramFiler);
}
