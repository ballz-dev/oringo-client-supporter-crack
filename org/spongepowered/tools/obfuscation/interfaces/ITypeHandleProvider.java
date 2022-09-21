package org.spongepowered.tools.obfuscation.interfaces;

import javax.lang.model.type.TypeMirror;
import org.spongepowered.tools.obfuscation.mirror.TypeHandle;

public interface ITypeHandleProvider {
  TypeHandle getSimulatedHandle(String paramString, TypeMirror paramTypeMirror);
  
  TypeHandle getTypeHandle(String paramString);
}
