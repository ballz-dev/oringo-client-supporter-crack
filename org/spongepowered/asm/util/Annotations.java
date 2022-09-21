package org.spongepowered.asm.util;

import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;
import org.spongepowered.asm.lib.Type;
import org.spongepowered.asm.lib.tree.AnnotationNode;
import org.spongepowered.asm.lib.tree.ClassNode;
import org.spongepowered.asm.lib.tree.FieldNode;
import org.spongepowered.asm.lib.tree.MethodNode;

public final class Annotations {
  public static void setVisible(FieldNode paramFieldNode, Class<? extends Annotation> paramClass, Object... paramVarArgs) {
    AnnotationNode annotationNode = createNode(Type.getDescriptor(paramClass), paramVarArgs);
    paramFieldNode.visibleAnnotations = add(paramFieldNode.visibleAnnotations, annotationNode);
  }
  
  public static void setInvisible(FieldNode paramFieldNode, Class<? extends Annotation> paramClass, Object... paramVarArgs) {
    AnnotationNode annotationNode = createNode(Type.getDescriptor(paramClass), paramVarArgs);
    paramFieldNode.invisibleAnnotations = add(paramFieldNode.invisibleAnnotations, annotationNode);
  }
  
  public static void setVisible(MethodNode paramMethodNode, Class<? extends Annotation> paramClass, Object... paramVarArgs) {
    AnnotationNode annotationNode = createNode(Type.getDescriptor(paramClass), paramVarArgs);
    paramMethodNode.visibleAnnotations = add(paramMethodNode.visibleAnnotations, annotationNode);
  }
  
  public static void setInvisible(MethodNode paramMethodNode, Class<? extends Annotation> paramClass, Object... paramVarArgs) {
    AnnotationNode annotationNode = createNode(Type.getDescriptor(paramClass), paramVarArgs);
    paramMethodNode.invisibleAnnotations = add(paramMethodNode.invisibleAnnotations, annotationNode);
  }
  
  private static AnnotationNode createNode(String paramString, Object... paramVarArgs) {
    AnnotationNode annotationNode = new AnnotationNode(paramString);
    for (byte b = 0; b < paramVarArgs.length - 1; b += 2) {
      if (!(paramVarArgs[b] instanceof String))
        throw new IllegalArgumentException("Annotation keys must be strings, found " + paramVarArgs[b].getClass().getSimpleName() + " with " + paramVarArgs[b]
            .toString() + " at index " + b + " creating " + paramString); 
      annotationNode.visit((String)paramVarArgs[b], paramVarArgs[b + 1]);
    } 
    return annotationNode;
  }
  
  private static List<AnnotationNode> add(List<AnnotationNode> paramList, AnnotationNode paramAnnotationNode) {
    if (paramList == null) {
      paramList = new ArrayList<AnnotationNode>(1);
    } else {
      paramList.remove(get(paramList, paramAnnotationNode.desc));
    } 
    paramList.add(paramAnnotationNode);
    return paramList;
  }
  
  public static AnnotationNode getVisible(FieldNode paramFieldNode, Class<? extends Annotation> paramClass) {
    return get(paramFieldNode.visibleAnnotations, Type.getDescriptor(paramClass));
  }
  
  public static AnnotationNode getInvisible(FieldNode paramFieldNode, Class<? extends Annotation> paramClass) {
    return get(paramFieldNode.invisibleAnnotations, Type.getDescriptor(paramClass));
  }
  
  public static AnnotationNode getVisible(MethodNode paramMethodNode, Class<? extends Annotation> paramClass) {
    return get(paramMethodNode.visibleAnnotations, Type.getDescriptor(paramClass));
  }
  
  public static AnnotationNode getInvisible(MethodNode paramMethodNode, Class<? extends Annotation> paramClass) {
    return get(paramMethodNode.invisibleAnnotations, Type.getDescriptor(paramClass));
  }
  
  public static AnnotationNode getSingleVisible(MethodNode paramMethodNode, Class<? extends Annotation>... paramVarArgs) {
    return getSingle(paramMethodNode.visibleAnnotations, paramVarArgs);
  }
  
  public static AnnotationNode getSingleInvisible(MethodNode paramMethodNode, Class<? extends Annotation>... paramVarArgs) {
    return getSingle(paramMethodNode.invisibleAnnotations, paramVarArgs);
  }
  
  public static AnnotationNode getVisible(ClassNode paramClassNode, Class<? extends Annotation> paramClass) {
    return get(paramClassNode.visibleAnnotations, Type.getDescriptor(paramClass));
  }
  
  public static AnnotationNode getInvisible(ClassNode paramClassNode, Class<? extends Annotation> paramClass) {
    return get(paramClassNode.invisibleAnnotations, Type.getDescriptor(paramClass));
  }
  
  public static AnnotationNode getVisibleParameter(MethodNode paramMethodNode, Class<? extends Annotation> paramClass, int paramInt) {
    return getParameter((List<AnnotationNode>[])paramMethodNode.visibleParameterAnnotations, Type.getDescriptor(paramClass), paramInt);
  }
  
  public static AnnotationNode getInvisibleParameter(MethodNode paramMethodNode, Class<? extends Annotation> paramClass, int paramInt) {
    return getParameter((List<AnnotationNode>[])paramMethodNode.invisibleParameterAnnotations, Type.getDescriptor(paramClass), paramInt);
  }
  
  public static AnnotationNode getParameter(List<AnnotationNode>[] paramArrayOfList, String paramString, int paramInt) {
    if (paramArrayOfList == null || paramInt < 0 || paramInt >= paramArrayOfList.length)
      return null; 
    return get(paramArrayOfList[paramInt], paramString);
  }
  
  public static AnnotationNode get(List<AnnotationNode> paramList, String paramString) {
    if (paramList == null)
      return null; 
    for (AnnotationNode annotationNode : paramList) {
      if (paramString.equals(annotationNode.desc))
        return annotationNode; 
    } 
    return null;
  }
  
  private static AnnotationNode getSingle(List<AnnotationNode> paramList, Class<? extends Annotation>[] paramArrayOfClass) {
    ArrayList<AnnotationNode> arrayList = new ArrayList();
    for (Class<? extends Annotation> clazz : paramArrayOfClass) {
      AnnotationNode annotationNode = get(paramList, Type.getDescriptor(clazz));
      if (annotationNode != null)
        arrayList.add(annotationNode); 
    } 
    int i = arrayList.size();
    if (i > 1)
      throw new IllegalArgumentException("Conflicting annotations found: " + Lists.transform(arrayList, new Function<AnnotationNode, String>() {
              public String apply(AnnotationNode param1AnnotationNode) {
                return param1AnnotationNode.desc;
              }
            })); 
    return (i == 0) ? null : arrayList.get(0);
  }
  
  public static <T> T getValue(AnnotationNode paramAnnotationNode) {
    return getValue(paramAnnotationNode, "value");
  }
  
  public static <T> T getValue(AnnotationNode paramAnnotationNode, String paramString, T paramT) {
    T t = (T)getValue(paramAnnotationNode, paramString);
    return (t != null) ? t : paramT;
  }
  
  public static <T> T getValue(AnnotationNode paramAnnotationNode, String paramString, Class<?> paramClass) {
    Preconditions.checkNotNull(paramClass, "annotationClass cannot be null");
    Object object = getValue(paramAnnotationNode, paramString);
    if (object == null)
      try {
        object = paramClass.getDeclaredMethod(paramString, new Class[0]).getDefaultValue();
      } catch (NoSuchMethodException noSuchMethodException) {} 
    return (T)object;
  }
  
  public static <T> T getValue(AnnotationNode paramAnnotationNode, String paramString) {
    boolean bool = false;
    if (paramAnnotationNode == null || paramAnnotationNode.values == null)
      return null; 
    for (T t : paramAnnotationNode.values) {
      if (bool)
        return t; 
      if (t.equals(paramString))
        bool = true; 
    } 
    return null;
  }
  
  public static <T extends Enum<T>> T getValue(AnnotationNode paramAnnotationNode, String paramString, Class<T> paramClass, T paramT) {
    String[] arrayOfString = getValue(paramAnnotationNode, paramString);
    if (arrayOfString == null)
      return paramT; 
    return toEnumValue(paramClass, arrayOfString);
  }
  
  public static <T> List<T> getValue(AnnotationNode paramAnnotationNode, String paramString, boolean paramBoolean) {
    List list = (List)getValue(paramAnnotationNode, paramString);
    if (list instanceof List)
      return list; 
    if (list != null) {
      ArrayList<List> arrayList = new ArrayList();
      arrayList.add(list);
      return (List)arrayList;
    } 
    return Collections.emptyList();
  }
  
  public static <T extends Enum<T>> List<T> getValue(AnnotationNode paramAnnotationNode, String paramString, boolean paramBoolean, Class<T> paramClass) {
    List<T> list = (List<T>)getValue(paramAnnotationNode, paramString);
    if (list instanceof List) {
      for (ListIterator<String[]> listIterator = ((List)list).listIterator(); listIterator.hasNext();)
        listIterator.set(toEnumValue(paramClass, listIterator.next())); 
      return list;
    } 
    if (list instanceof String[]) {
      ArrayList<T> arrayList = new ArrayList();
      arrayList.add(toEnumValue(paramClass, (String[])list));
      return arrayList;
    } 
    return Collections.emptyList();
  }
  
  private static <T extends Enum<T>> T toEnumValue(Class<T> paramClass, String[] paramArrayOfString) {
    if (!paramClass.getName().equals(Type.getType(paramArrayOfString[0]).getClassName()))
      throw new IllegalArgumentException("The supplied enum class does not match the stored enum value"); 
    return Enum.valueOf(paramClass, paramArrayOfString[1]);
  }
}
