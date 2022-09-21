package org.spongepowered.tools.obfuscation.mirror;

import com.google.common.collect.ImmutableList;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;

public final class AnnotationHandle {
  public static final AnnotationHandle MISSING = new AnnotationHandle(null);
  
  private final AnnotationMirror annotation;
  
  private AnnotationHandle(AnnotationMirror paramAnnotationMirror) {
    this.annotation = paramAnnotationMirror;
  }
  
  public AnnotationMirror asMirror() {
    return this.annotation;
  }
  
  public boolean exists() {
    return (this.annotation != null);
  }
  
  public String toString() {
    if (this.annotation == null)
      return "@{UnknownAnnotation}"; 
    return "@" + this.annotation.getAnnotationType().asElement().getSimpleName();
  }
  
  public <T> T getValue(String paramString, T paramT) {
    if (this.annotation == null)
      return paramT; 
    AnnotationValue annotationValue = getAnnotationValue(paramString);
    if (paramT instanceof Enum && annotationValue != null) {
      VariableElement variableElement = (VariableElement)annotationValue.getValue();
      if (variableElement == null)
        return paramT; 
      return (T)Enum.valueOf(paramT.getClass(), variableElement.getSimpleName().toString());
    } 
    return (annotationValue != null) ? (T)annotationValue.getValue() : paramT;
  }
  
  public <T> T getValue() {
    return getValue("value", null);
  }
  
  public <T> T getValue(String paramString) {
    return getValue(paramString, null);
  }
  
  public boolean getBoolean(String paramString, boolean paramBoolean) {
    return ((Boolean)getValue(paramString, Boolean.valueOf(paramBoolean))).booleanValue();
  }
  
  public AnnotationHandle getAnnotation(String paramString) {
    AnnotationMirror annotationMirror = (AnnotationMirror)getValue(paramString);
    if (annotationMirror instanceof AnnotationMirror)
      return of(annotationMirror); 
    if (annotationMirror instanceof AnnotationValue) {
      Object object = ((AnnotationValue)annotationMirror).getValue();
      if (object instanceof AnnotationMirror)
        return of((AnnotationMirror)object); 
    } 
    return null;
  }
  
  public <T> List<T> getList() {
    return getList("value");
  }
  
  public <T> List<T> getList(String paramString) {
    List<AnnotationValue> list = getValue(paramString, Collections.emptyList());
    return unwrapAnnotationValueList(list);
  }
  
  public List<AnnotationHandle> getAnnotationList(String paramString) {
    AnnotationMirror annotationMirror = (AnnotationMirror)getValue(paramString, null);
    if (annotationMirror == null)
      return Collections.emptyList(); 
    if (annotationMirror instanceof AnnotationMirror)
      return (List<AnnotationHandle>)ImmutableList.of(of(annotationMirror)); 
    List list = (List)annotationMirror;
    ArrayList<AnnotationHandle> arrayList = new ArrayList(list.size());
    for (AnnotationValue annotationValue : list)
      arrayList.add(new AnnotationHandle((AnnotationMirror)annotationValue.getValue())); 
    return Collections.unmodifiableList(arrayList);
  }
  
  protected AnnotationValue getAnnotationValue(String paramString) {
    for (ExecutableElement executableElement : this.annotation.getElementValues().keySet()) {
      if (executableElement.getSimpleName().contentEquals(paramString))
        return this.annotation.getElementValues().get(executableElement); 
    } 
    return null;
  }
  
  protected static <T> List<T> unwrapAnnotationValueList(List<AnnotationValue> paramList) {
    if (paramList == null)
      return Collections.emptyList(); 
    ArrayList<Object> arrayList = new ArrayList(paramList.size());
    for (AnnotationValue annotationValue : paramList)
      arrayList.add(annotationValue.getValue()); 
    return arrayList;
  }
  
  protected static AnnotationMirror getAnnotation(Element paramElement, Class<? extends Annotation> paramClass) {
    if (paramElement == null)
      return null; 
    List<? extends AnnotationMirror> list = paramElement.getAnnotationMirrors();
    if (list == null)
      return null; 
    for (AnnotationMirror annotationMirror : list) {
      Element element = annotationMirror.getAnnotationType().asElement();
      if (!(element instanceof TypeElement))
        continue; 
      TypeElement typeElement = (TypeElement)element;
      if (typeElement.getQualifiedName().contentEquals(paramClass.getName()))
        return annotationMirror; 
    } 
    return null;
  }
  
  public static AnnotationHandle of(AnnotationMirror paramAnnotationMirror) {
    return new AnnotationHandle(paramAnnotationMirror);
  }
  
  public static AnnotationHandle of(Element paramElement, Class<? extends Annotation> paramClass) {
    return new AnnotationHandle(getAnnotation(paramElement, paramClass));
  }
}
