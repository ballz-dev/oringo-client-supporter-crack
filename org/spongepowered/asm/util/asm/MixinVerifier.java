package org.spongepowered.asm.util.asm;

import java.util.List;
import org.spongepowered.asm.lib.Type;
import org.spongepowered.asm.lib.tree.analysis.SimpleVerifier;
import org.spongepowered.asm.mixin.transformer.ClassInfo;

public class MixinVerifier extends SimpleVerifier {
  private List<Type> currentClassInterfaces;
  
  private Type currentClass;
  
  private Type currentSuperClass;
  
  private boolean isInterface;
  
  public MixinVerifier(Type paramType1, Type paramType2, List<Type> paramList, boolean paramBoolean) {
    super(paramType1, paramType2, paramList, paramBoolean);
    this.currentClass = paramType1;
    this.currentSuperClass = paramType2;
    this.currentClassInterfaces = paramList;
    this.isInterface = paramBoolean;
  }
  
  protected boolean isInterface(Type paramType) {
    if (this.currentClass != null && paramType.equals(this.currentClass))
      return this.isInterface; 
    return ClassInfo.forType(paramType).isInterface();
  }
  
  protected Type getSuperClass(Type paramType) {
    if (this.currentClass != null && paramType.equals(this.currentClass))
      return this.currentSuperClass; 
    ClassInfo classInfo = ClassInfo.forType(paramType).getSuperClass();
    return (classInfo == null) ? null : Type.getType("L" + classInfo.getName() + ";");
  }
  
  protected boolean isAssignableFrom(Type paramType1, Type paramType2) {
    if (paramType1.equals(paramType2))
      return true; 
    if (this.currentClass != null && paramType1.equals(this.currentClass)) {
      if (getSuperClass(paramType2) == null)
        return false; 
      if (this.isInterface)
        return (paramType2.getSort() == 10 || paramType2.getSort() == 9); 
      return isAssignableFrom(paramType1, getSuperClass(paramType2));
    } 
    if (this.currentClass != null && paramType2.equals(this.currentClass)) {
      if (isAssignableFrom(paramType1, this.currentSuperClass))
        return true; 
      if (this.currentClassInterfaces != null)
        for (byte b = 0; b < this.currentClassInterfaces.size(); b++) {
          Type type = this.currentClassInterfaces.get(b);
          if (isAssignableFrom(paramType1, type))
            return true; 
        }  
      return false;
    } 
    ClassInfo classInfo = ClassInfo.forType(paramType1);
    if (classInfo == null)
      return false; 
    if (classInfo.isInterface())
      classInfo = ClassInfo.forName("java/lang/Object"); 
    return ClassInfo.forType(paramType2).hasSuperClass(classInfo);
  }
}
