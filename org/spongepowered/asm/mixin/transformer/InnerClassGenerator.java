package org.spongepowered.asm.mixin.transformer;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.lib.AnnotationVisitor;
import org.spongepowered.asm.lib.ClassReader;
import org.spongepowered.asm.lib.ClassVisitor;
import org.spongepowered.asm.lib.commons.ClassRemapper;
import org.spongepowered.asm.lib.commons.Remapper;
import org.spongepowered.asm.mixin.transformer.ext.IClassGenerator;
import org.spongepowered.asm.mixin.transformer.throwables.InvalidMixinException;
import org.spongepowered.asm.service.MixinService;
import org.spongepowered.asm.transformers.MixinClassWriter;

final class InnerClassGenerator implements IClassGenerator {
  static class InnerClassInfo extends Remapper {
    private final String ownerName;
    
    private final String targetName;
    
    private final MixinInfo owner;
    
    private final String originalName;
    
    private final String name;
    
    private final MixinTargetContext target;
    
    InnerClassInfo(String param1String1, String param1String2, MixinInfo param1MixinInfo, MixinTargetContext param1MixinTargetContext) {
      this.name = param1String1;
      this.originalName = param1String2;
      this.owner = param1MixinInfo;
      this.ownerName = param1MixinInfo.getClassRef();
      this.target = param1MixinTargetContext;
      this.targetName = param1MixinTargetContext.getTargetClassRef();
    }
    
    String getName() {
      return this.name;
    }
    
    String getOriginalName() {
      return this.originalName;
    }
    
    MixinInfo getOwner() {
      return this.owner;
    }
    
    MixinTargetContext getTarget() {
      return this.target;
    }
    
    String getOwnerName() {
      return this.ownerName;
    }
    
    String getTargetName() {
      return this.targetName;
    }
    
    byte[] getClassBytes() throws ClassNotFoundException, IOException {
      return MixinService.getService().getBytecodeProvider().getClassBytes(this.originalName, true);
    }
    
    public String mapMethodName(String param1String1, String param1String2, String param1String3) {
      if (this.ownerName.equalsIgnoreCase(param1String1)) {
        ClassInfo.Method method = this.owner.getClassInfo().findMethod(param1String2, param1String3, 10);
        if (method != null)
          return method.getName(); 
      } 
      return super.mapMethodName(param1String1, param1String2, param1String3);
    }
    
    public String map(String param1String) {
      if (this.originalName.equals(param1String))
        return this.name; 
      if (this.ownerName.equals(param1String))
        return this.targetName; 
      return param1String;
    }
    
    public String toString() {
      return this.name;
    }
  }
  
  static class InnerClassAdapter extends ClassRemapper {
    private final InnerClassGenerator.InnerClassInfo info;
    
    public InnerClassAdapter(ClassVisitor param1ClassVisitor, InnerClassGenerator.InnerClassInfo param1InnerClassInfo) {
      super(327680, param1ClassVisitor, param1InnerClassInfo);
      this.info = param1InnerClassInfo;
    }
    
    public void visitSource(String param1String1, String param1String2) {
      super.visitSource(param1String1, param1String2);
      AnnotationVisitor annotationVisitor = this.cv.visitAnnotation("Lorg/spongepowered/asm/mixin/transformer/meta/MixinInner;", false);
      annotationVisitor.visit("mixin", this.info.getOwner().toString());
      annotationVisitor.visit("name", this.info.getOriginalName().substring(this.info.getOriginalName().lastIndexOf('/') + 1));
      annotationVisitor.visitEnd();
    }
    
    public void visitInnerClass(String param1String1, String param1String2, String param1String3, int param1Int) {
      if (param1String1.startsWith(this.info.getOriginalName() + "$"))
        throw new InvalidMixinException(this.info.getOwner(), "Found unsupported nested inner class " + param1String1 + " in " + this.info
            .getOriginalName()); 
      super.visitInnerClass(param1String1, param1String2, param1String3, param1Int);
    }
  }
  
  private static final Logger logger = LogManager.getLogger("mixin");
  
  private final Map<String, String> innerClassNames = new HashMap<String, String>();
  
  private final Map<String, InnerClassInfo> innerClasses = new HashMap<String, InnerClassInfo>();
  
  public String registerInnerClass(MixinInfo paramMixinInfo, String paramString, MixinTargetContext paramMixinTargetContext) {
    String str1 = String.format("%s%s", new Object[] { paramString, paramMixinTargetContext });
    String str2 = this.innerClassNames.get(str1);
    if (str2 == null) {
      str2 = getUniqueReference(paramString, paramMixinTargetContext);
      this.innerClassNames.put(str1, str2);
      this.innerClasses.put(str2, new InnerClassInfo(str2, paramString, paramMixinInfo, paramMixinTargetContext));
      logger.debug("Inner class {} in {} on {} gets unique name {}", new Object[] { paramString, paramMixinInfo.getClassRef(), paramMixinTargetContext
            .getTargetClassRef(), str2 });
    } 
    return str2;
  }
  
  public byte[] generate(String paramString) {
    String str = paramString.replace('.', '/');
    InnerClassInfo innerClassInfo = this.innerClasses.get(str);
    if (innerClassInfo != null)
      return generate(innerClassInfo); 
    return null;
  }
  
  private byte[] generate(InnerClassInfo paramInnerClassInfo) {
    try {
      logger.debug("Generating mapped inner class {} (originally {})", new Object[] { paramInnerClassInfo.getName(), paramInnerClassInfo.getOriginalName() });
      ClassReader classReader = new ClassReader(paramInnerClassInfo.getClassBytes());
      MixinClassWriter mixinClassWriter = new MixinClassWriter(classReader, 0);
      classReader.accept((ClassVisitor)new InnerClassAdapter((ClassVisitor)mixinClassWriter, paramInnerClassInfo), 8);
      return mixinClassWriter.toByteArray();
    } catch (InvalidMixinException invalidMixinException) {
      throw invalidMixinException;
    } catch (Exception exception) {
      logger.catching(exception);
      return null;
    } 
  }
  
  private static String getUniqueReference(String paramString, MixinTargetContext paramMixinTargetContext) {
    String str = paramString.substring(paramString.lastIndexOf('$') + 1);
    if (str.matches("^[0-9]+$"))
      str = "Anonymous"; 
    return String.format("%s$%s$%s", new Object[] { paramMixinTargetContext.getTargetClassRef(), str, UUID.randomUUID().toString().replace("-", "") });
  }
}
