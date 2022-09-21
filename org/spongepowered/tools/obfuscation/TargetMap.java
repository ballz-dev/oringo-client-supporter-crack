package org.spongepowered.tools.obfuscation;

import com.google.common.io.Files;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.charset.Charset;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.lang.model.element.TypeElement;
import org.spongepowered.tools.obfuscation.mirror.TypeHandle;
import org.spongepowered.tools.obfuscation.mirror.TypeReference;

public final class TargetMap extends HashMap<TypeReference, Set<TypeReference>> {
  private static final long serialVersionUID = 1L;
  
  private final String sessionId;
  
  private TargetMap() {
    this(String.valueOf(System.currentTimeMillis()));
  }
  
  private TargetMap(String paramString) {
    this.sessionId = paramString;
  }
  
  public String getSessionId() {
    return this.sessionId;
  }
  
  public void registerTargets(AnnotatedMixin paramAnnotatedMixin) {
    registerTargets(paramAnnotatedMixin.getTargets(), paramAnnotatedMixin.getHandle());
  }
  
  public void registerTargets(List<TypeHandle> paramList, TypeHandle paramTypeHandle) {
    for (TypeHandle typeHandle : paramList)
      addMixin(typeHandle, paramTypeHandle); 
  }
  
  public void addMixin(TypeHandle paramTypeHandle1, TypeHandle paramTypeHandle2) {
    addMixin(paramTypeHandle1.getReference(), paramTypeHandle2.getReference());
  }
  
  public void addMixin(String paramString1, String paramString2) {
    addMixin(new TypeReference(paramString1), new TypeReference(paramString2));
  }
  
  public void addMixin(TypeReference paramTypeReference1, TypeReference paramTypeReference2) {
    Set<TypeReference> set = getMixinsFor(paramTypeReference1);
    set.add(paramTypeReference2);
  }
  
  public Collection<TypeReference> getMixinsTargeting(TypeElement paramTypeElement) {
    return getMixinsTargeting(new TypeHandle(paramTypeElement));
  }
  
  public Collection<TypeReference> getMixinsTargeting(TypeHandle paramTypeHandle) {
    return getMixinsTargeting(paramTypeHandle.getReference());
  }
  
  public Collection<TypeReference> getMixinsTargeting(TypeReference paramTypeReference) {
    return Collections.unmodifiableCollection(getMixinsFor(paramTypeReference));
  }
  
  private Set<TypeReference> getMixinsFor(TypeReference paramTypeReference) {
    Set<TypeReference> set = get(paramTypeReference);
    if (set == null) {
      set = new HashSet<TypeReference>();
      put(paramTypeReference, set);
    } 
    return set;
  }
  
  public void readImports(File paramFile) throws IOException {
    if (!paramFile.isFile())
      return; 
    for (String str : Files.readLines(paramFile, Charset.defaultCharset())) {
      String[] arrayOfString = str.split("\t");
      if (arrayOfString.length == 2)
        addMixin(arrayOfString[1], arrayOfString[0]); 
    } 
  }
  
  public void write(boolean paramBoolean) {
    ObjectOutputStream objectOutputStream = null;
    FileOutputStream fileOutputStream = null;
    try {
      File file = getSessionFile(this.sessionId);
      if (paramBoolean)
        file.deleteOnExit(); 
      fileOutputStream = new FileOutputStream(file, true);
      objectOutputStream = new ObjectOutputStream(fileOutputStream);
      objectOutputStream.writeObject(this);
    } catch (Exception exception) {
      exception.printStackTrace();
    } finally {
      if (objectOutputStream != null)
        try {
          objectOutputStream.close();
        } catch (IOException iOException) {
          iOException.printStackTrace();
        }  
    } 
  }
  
  private static TargetMap read(File paramFile) {
    ObjectInputStream objectInputStream = null;
    FileInputStream fileInputStream = null;
    try {
      fileInputStream = new FileInputStream(paramFile);
      objectInputStream = new ObjectInputStream(fileInputStream);
      return (TargetMap)objectInputStream.readObject();
    } catch (Exception exception) {
      exception.printStackTrace();
    } finally {
      if (objectInputStream != null)
        try {
          objectInputStream.close();
        } catch (IOException iOException) {
          iOException.printStackTrace();
        }  
    } 
    return null;
  }
  
  public static TargetMap create(String paramString) {
    if (paramString != null) {
      File file = getSessionFile(paramString);
      if (file.exists()) {
        TargetMap targetMap = read(file);
        if (targetMap != null)
          return targetMap; 
      } 
    } 
    return new TargetMap();
  }
  
  private static File getSessionFile(String paramString) {
    File file = new File(System.getProperty("java.io.tmpdir"));
    return new File(file, String.format("mixin-targetdb-%s.tmp", new Object[] { paramString }));
  }
}
