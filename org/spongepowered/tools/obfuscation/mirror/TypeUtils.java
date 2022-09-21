package org.spongepowered.tools.obfuscation.mirror;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.ArrayType;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.type.TypeVariable;
import org.spongepowered.asm.util.SignaturePrinter;

public abstract class TypeUtils {
  private static final int MAX_GENERIC_RECURSION_DEPTH = 5;
  
  private static final String OBJECT_REF = "java/lang/Object";
  
  private static final String OBJECT_SIG = "java.lang.Object";
  
  public static PackageElement getPackage(TypeMirror paramTypeMirror) {
    if (!(paramTypeMirror instanceof DeclaredType))
      return null; 
    return getPackage((TypeElement)((DeclaredType)paramTypeMirror).asElement());
  }
  
  public static PackageElement getPackage(TypeElement paramTypeElement) {
    Element element = paramTypeElement.getEnclosingElement();
    while (element != null && !(element instanceof PackageElement))
      element = element.getEnclosingElement(); 
    return (PackageElement)element;
  }
  
  public static String getElementType(Element paramElement) {
    if (paramElement instanceof TypeElement)
      return "TypeElement"; 
    if (paramElement instanceof ExecutableElement)
      return "ExecutableElement"; 
    if (paramElement instanceof VariableElement)
      return "VariableElement"; 
    if (paramElement instanceof PackageElement)
      return "PackageElement"; 
    if (paramElement instanceof javax.lang.model.element.TypeParameterElement)
      return "TypeParameterElement"; 
    return paramElement.getClass().getSimpleName();
  }
  
  public static String stripGenerics(String paramString) {
    StringBuilder stringBuilder = new StringBuilder();
    for (byte b1 = 0, b2 = 0; b1 < paramString.length(); b1++) {
      char c = paramString.charAt(b1);
      if (c == '<')
        b2++; 
      if (b2 == 0) {
        stringBuilder.append(c);
      } else if (c == '>') {
        b2--;
      } 
    } 
    return stringBuilder.toString();
  }
  
  public static String getName(VariableElement paramVariableElement) {
    return (paramVariableElement != null) ? paramVariableElement.getSimpleName().toString() : null;
  }
  
  public static String getName(ExecutableElement paramExecutableElement) {
    return (paramExecutableElement != null) ? paramExecutableElement.getSimpleName().toString() : null;
  }
  
  public static String getJavaSignature(Element paramElement) {
    if (paramElement instanceof ExecutableElement) {
      ExecutableElement executableElement = (ExecutableElement)paramElement;
      StringBuilder stringBuilder = (new StringBuilder()).append("(");
      boolean bool = false;
      for (VariableElement variableElement : executableElement.getParameters()) {
        if (bool)
          stringBuilder.append(','); 
        stringBuilder.append(getTypeName(variableElement.asType()));
        bool = true;
      } 
      stringBuilder.append(')').append(getTypeName(executableElement.getReturnType()));
      return stringBuilder.toString();
    } 
    return getTypeName(paramElement.asType());
  }
  
  public static String getJavaSignature(String paramString) {
    return (new SignaturePrinter("", paramString)).setFullyQualified(true).toDescriptor();
  }
  
  public static String getTypeName(TypeMirror paramTypeMirror) {
    switch (paramTypeMirror.getKind()) {
      case PUBLIC:
        return getTypeName(((ArrayType)paramTypeMirror).getComponentType()) + "[]";
      case PROTECTED:
        return getTypeName((DeclaredType)paramTypeMirror);
      case PRIVATE:
        return getTypeName(getUpperBound(paramTypeMirror));
      case null:
        return "java.lang.Object";
    } 
    return paramTypeMirror.toString();
  }
  
  public static String getTypeName(DeclaredType paramDeclaredType) {
    if (paramDeclaredType == null)
      return "java.lang.Object"; 
    return getInternalName((TypeElement)paramDeclaredType.asElement()).replace('/', '.');
  }
  
  public static String getDescriptor(Element paramElement) {
    if (paramElement instanceof ExecutableElement)
      return getDescriptor((ExecutableElement)paramElement); 
    if (paramElement instanceof VariableElement)
      return getInternalName((VariableElement)paramElement); 
    return getInternalName(paramElement.asType());
  }
  
  public static String getDescriptor(ExecutableElement paramExecutableElement) {
    if (paramExecutableElement == null)
      return null; 
    StringBuilder stringBuilder = new StringBuilder();
    for (VariableElement variableElement : paramExecutableElement.getParameters())
      stringBuilder.append(getInternalName(variableElement)); 
    String str = getInternalName(paramExecutableElement.getReturnType());
    return String.format("(%s)%s", new Object[] { stringBuilder, str });
  }
  
  public static String getInternalName(VariableElement paramVariableElement) {
    if (paramVariableElement == null)
      return null; 
    return getInternalName(paramVariableElement.asType());
  }
  
  public static String getInternalName(TypeMirror paramTypeMirror) {
    switch (paramTypeMirror.getKind()) {
      case PUBLIC:
        return "[" + getInternalName(((ArrayType)paramTypeMirror).getComponentType());
      case PROTECTED:
        return "L" + getInternalName((DeclaredType)paramTypeMirror) + ";";
      case PRIVATE:
        return "L" + getInternalName(getUpperBound(paramTypeMirror)) + ";";
      case null:
        return "Z";
      case null:
        return "B";
      case null:
        return "C";
      case null:
        return "D";
      case null:
        return "F";
      case null:
        return "I";
      case null:
        return "J";
      case null:
        return "S";
      case null:
        return "V";
      case null:
        return "Ljava/lang/Object;";
    } 
    throw new IllegalArgumentException("Unable to parse type symbol " + paramTypeMirror + " with " + paramTypeMirror.getKind() + " to equivalent bytecode type");
  }
  
  public static String getInternalName(DeclaredType paramDeclaredType) {
    if (paramDeclaredType == null)
      return "java/lang/Object"; 
    return getInternalName((TypeElement)paramDeclaredType.asElement());
  }
  
  public static String getInternalName(TypeElement paramTypeElement) {
    if (paramTypeElement == null)
      return null; 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(paramTypeElement.getSimpleName());
    Element element = paramTypeElement.getEnclosingElement();
    while (element != null) {
      if (element instanceof TypeElement) {
        stringBuilder.insert(0, "$").insert(0, element.getSimpleName());
      } else if (element instanceof PackageElement) {
        stringBuilder.insert(0, "/").insert(0, ((PackageElement)element).getQualifiedName().toString().replace('.', '/'));
      } 
      element = element.getEnclosingElement();
    } 
    return stringBuilder.toString();
  }
  
  private static DeclaredType getUpperBound(TypeMirror paramTypeMirror) {
    try {
      return getUpperBound0(paramTypeMirror, 5);
    } catch (IllegalStateException illegalStateException) {
      throw new IllegalArgumentException("Type symbol \"" + paramTypeMirror + "\" is too complex", illegalStateException);
    } catch (IllegalArgumentException illegalArgumentException) {
      throw new IllegalArgumentException("Unable to compute upper bound of type symbol " + paramTypeMirror, illegalArgumentException);
    } 
  }
  
  private static DeclaredType getUpperBound0(TypeMirror paramTypeMirror, int paramInt) {
    if (paramInt == 0)
      throw new IllegalStateException("Generic symbol \"" + paramTypeMirror + "\" is too complex, exceeded " + '\005' + " iterations attempting to determine upper bound"); 
    if (paramTypeMirror instanceof DeclaredType)
      return (DeclaredType)paramTypeMirror; 
    if (paramTypeMirror instanceof TypeVariable)
      try {
        TypeMirror typeMirror = ((TypeVariable)paramTypeMirror).getUpperBound();
        return getUpperBound0(typeMirror, --paramInt);
      } catch (IllegalStateException illegalStateException) {
        throw illegalStateException;
      } catch (IllegalArgumentException illegalArgumentException) {
        throw illegalArgumentException;
      } catch (Exception exception) {
        throw new IllegalArgumentException("Unable to compute upper bound of type symbol " + paramTypeMirror);
      }  
    return null;
  }
  
  public static boolean isAssignable(ProcessingEnvironment paramProcessingEnvironment, TypeMirror paramTypeMirror1, TypeMirror paramTypeMirror2) {
    boolean bool = paramProcessingEnvironment.getTypeUtils().isAssignable(paramTypeMirror1, paramTypeMirror2);
    if (!bool && paramTypeMirror1 instanceof DeclaredType && paramTypeMirror2 instanceof DeclaredType) {
      TypeMirror typeMirror1 = toRawType(paramProcessingEnvironment, (DeclaredType)paramTypeMirror1);
      TypeMirror typeMirror2 = toRawType(paramProcessingEnvironment, (DeclaredType)paramTypeMirror2);
      return paramProcessingEnvironment.getTypeUtils().isAssignable(typeMirror1, typeMirror2);
    } 
    return bool;
  }
  
  private static TypeMirror toRawType(ProcessingEnvironment paramProcessingEnvironment, DeclaredType paramDeclaredType) {
    return paramProcessingEnvironment.getElementUtils().getTypeElement(((TypeElement)paramDeclaredType.asElement()).getQualifiedName()).asType();
  }
  
  public static Visibility getVisibility(Element paramElement) {
    if (paramElement == null)
      return null; 
    for (Modifier modifier : paramElement.getModifiers()) {
      switch (modifier) {
        case PUBLIC:
          return Visibility.PUBLIC;
        case PROTECTED:
          return Visibility.PROTECTED;
        case PRIVATE:
          return Visibility.PRIVATE;
      } 
    } 
    return Visibility.PACKAGE;
  }
}
