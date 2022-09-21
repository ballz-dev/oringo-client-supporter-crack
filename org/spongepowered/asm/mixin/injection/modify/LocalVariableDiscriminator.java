package org.spongepowered.asm.mixin.injection.modify;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.spongepowered.asm.lib.Type;
import org.spongepowered.asm.lib.tree.AbstractInsnNode;
import org.spongepowered.asm.lib.tree.AnnotationNode;
import org.spongepowered.asm.lib.tree.LocalVariableNode;
import org.spongepowered.asm.mixin.injection.struct.Target;
import org.spongepowered.asm.util.Annotations;
import org.spongepowered.asm.util.Bytecode;
import org.spongepowered.asm.util.Locals;
import org.spongepowered.asm.util.PrettyPrinter;
import org.spongepowered.asm.util.SignaturePrinter;

public class LocalVariableDiscriminator {
  private final int ordinal;
  
  private final boolean print;
  
  private final int index;
  
  private final boolean argsOnly;
  
  private final Set<String> names;
  
  public static class Context implements PrettyPrinter.IPrettyPrintable {
    final Type returnType;
    
    final Target target;
    
    final int baseArgIndex;
    
    private final boolean isStatic;
    
    final Local[] locals;
    
    final AbstractInsnNode node;
    
    public class Local {
      int ord = 0;
      
      String name;
      
      Type type;
      
      public Local(String param2String, Type param2Type) {
        this.name = param2String;
        this.type = param2Type;
      }
      
      public String toString() {
        return String.format("Local[ordinal=%d, name=%s, type=%s]", new Object[] { Integer.valueOf(this.ord), this.name, this.type });
      }
    }
    
    public Context(Type param1Type, boolean param1Boolean, Target param1Target, AbstractInsnNode param1AbstractInsnNode) {
      this.isStatic = Bytecode.methodIsStatic(param1Target.method);
      this.returnType = param1Type;
      this.target = param1Target;
      this.node = param1AbstractInsnNode;
      this.baseArgIndex = this.isStatic ? 0 : 1;
      this.locals = initLocals(param1Target, param1Boolean, param1AbstractInsnNode);
      initOrdinals();
    }
    
    private Local[] initLocals(Target param1Target, boolean param1Boolean, AbstractInsnNode param1AbstractInsnNode) {
      if (!param1Boolean) {
        LocalVariableNode[] arrayOfLocalVariableNode = Locals.getLocalsAt(param1Target.classNode, param1Target.method, param1AbstractInsnNode);
        if (arrayOfLocalVariableNode != null) {
          Local[] arrayOfLocal1 = new Local[arrayOfLocalVariableNode.length];
          for (byte b = 0; b < arrayOfLocalVariableNode.length; b++) {
            if (arrayOfLocalVariableNode[b] != null)
              arrayOfLocal1[b] = new Local((arrayOfLocalVariableNode[b]).name, Type.getType((arrayOfLocalVariableNode[b]).desc)); 
          } 
          return arrayOfLocal1;
        } 
      } 
      Local[] arrayOfLocal = new Local[this.baseArgIndex + param1Target.arguments.length];
      if (!this.isStatic)
        arrayOfLocal[0] = new Local("this", Type.getType(param1Target.classNode.name)); 
      for (int i = this.baseArgIndex; i < arrayOfLocal.length; i++) {
        Type type = param1Target.arguments[i - this.baseArgIndex];
        arrayOfLocal[i] = new Local("arg" + i, type);
      } 
      return arrayOfLocal;
    }
    
    private void initOrdinals() {
      HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
      for (byte b = 0; b < this.locals.length; b++) {
        Integer integer = Integer.valueOf(0);
        if (this.locals[b] != null) {
          integer = (Integer)hashMap.get((this.locals[b]).type);
          hashMap.put((this.locals[b]).type, integer = Integer.valueOf((integer == null) ? 0 : (integer.intValue() + 1)));
          (this.locals[b]).ord = integer.intValue();
        } 
      } 
    }
    
    public void print(PrettyPrinter param1PrettyPrinter) {
      param1PrettyPrinter.add("%5s  %7s  %30s  %-50s  %s", new Object[] { "INDEX", "ORDINAL", "TYPE", "NAME", "CANDIDATE" });
      for (int i = this.baseArgIndex; i < this.locals.length; i++) {
        Local local = this.locals[i];
        if (local != null) {
          Type type = local.type;
          String str1 = local.name;
          int j = local.ord;
          String str2 = this.returnType.equals(type) ? "YES" : "-";
          param1PrettyPrinter.add("[%3d]    [%3d]  %30s  %-50s  %s", new Object[] { Integer.valueOf(i), Integer.valueOf(j), SignaturePrinter.getTypeName(type, false), str1, str2 });
        } else if (i > 0) {
          Local local1 = this.locals[i - 1];
          boolean bool = (local1 != null && local1.type != null && local1.type.getSize() > 1) ? true : false;
          param1PrettyPrinter.add("[%3d]           %30s", new Object[] { Integer.valueOf(i), bool ? "<top>" : "-" });
        } 
      } 
    }
  }
  
  public class Local {
    int ord = 0;
    
    String name;
    
    Type type;
    
    public Local(String param1String, Type param1Type) {
      this.name = param1String;
      this.type = param1Type;
    }
    
    public String toString() {
      return String.format("Local[ordinal=%d, name=%s, type=%s]", new Object[] { Integer.valueOf(this.ord), this.name, this.type });
    }
  }
  
  public LocalVariableDiscriminator(boolean paramBoolean1, int paramInt1, int paramInt2, Set<String> paramSet, boolean paramBoolean2) {
    this.argsOnly = paramBoolean1;
    this.ordinal = paramInt1;
    this.index = paramInt2;
    this.names = Collections.unmodifiableSet(paramSet);
    this.print = paramBoolean2;
  }
  
  public boolean isArgsOnly() {
    return this.argsOnly;
  }
  
  public int getOrdinal() {
    return this.ordinal;
  }
  
  public int getIndex() {
    return this.index;
  }
  
  public Set<String> getNames() {
    return this.names;
  }
  
  public boolean hasNames() {
    return !this.names.isEmpty();
  }
  
  public boolean printLVT() {
    return this.print;
  }
  
  protected boolean isImplicit(Context paramContext) {
    return (this.ordinal < 0 && this.index < paramContext.baseArgIndex && this.names.isEmpty());
  }
  
  public int findLocal(Type paramType, boolean paramBoolean, Target paramTarget, AbstractInsnNode paramAbstractInsnNode) {
    try {
      return findLocal(new Context(paramType, paramBoolean, paramTarget, paramAbstractInsnNode));
    } catch (InvalidImplicitDiscriminatorException invalidImplicitDiscriminatorException) {
      return -2;
    } 
  }
  
  public int findLocal(Context paramContext) {
    if (isImplicit(paramContext))
      return findImplicitLocal(paramContext); 
    return findExplicitLocal(paramContext);
  }
  
  private int findImplicitLocal(Context paramContext) {
    int i = 0;
    byte b = 0;
    for (int j = paramContext.baseArgIndex; j < paramContext.locals.length; j++) {
      Context.Local local = paramContext.locals[j];
      if (local != null && local.type.equals(paramContext.returnType)) {
        b++;
        i = j;
      } 
    } 
    if (b == 1)
      return i; 
    throw new InvalidImplicitDiscriminatorException("Found " + b + " candidate variables but exactly 1 is required.");
  }
  
  private int findExplicitLocal(Context paramContext) {
    for (int i = paramContext.baseArgIndex; i < paramContext.locals.length; i++) {
      Context.Local local = paramContext.locals[i];
      if (local != null && local.type.equals(paramContext.returnType))
        if (this.ordinal > -1) {
          if (this.ordinal == local.ord)
            return i; 
        } else if (this.index >= paramContext.baseArgIndex) {
          if (this.index == i)
            return i; 
        } else if (this.names.contains(local.name)) {
          return i;
        }  
    } 
    return -1;
  }
  
  public static LocalVariableDiscriminator parse(AnnotationNode paramAnnotationNode) {
    boolean bool1 = ((Boolean)Annotations.getValue(paramAnnotationNode, "argsOnly", Boolean.FALSE)).booleanValue();
    int i = ((Integer)Annotations.getValue(paramAnnotationNode, "ordinal", Integer.valueOf(-1))).intValue();
    int j = ((Integer)Annotations.getValue(paramAnnotationNode, "index", Integer.valueOf(-1))).intValue();
    boolean bool2 = ((Boolean)Annotations.getValue(paramAnnotationNode, "print", Boolean.FALSE)).booleanValue();
    HashSet<String> hashSet = new HashSet();
    List list = (List)Annotations.getValue(paramAnnotationNode, "name", null);
    if (list != null)
      hashSet.addAll(list); 
    return new LocalVariableDiscriminator(bool1, i, j, hashSet, bool2);
  }
}
