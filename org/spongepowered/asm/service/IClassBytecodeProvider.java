package org.spongepowered.asm.service;

import java.io.IOException;
import org.spongepowered.asm.lib.tree.ClassNode;

public interface IClassBytecodeProvider {
  byte[] getClassBytes(String paramString, boolean paramBoolean) throws ClassNotFoundException, IOException;
  
  byte[] getClassBytes(String paramString1, String paramString2) throws IOException;
  
  ClassNode getClassNode(String paramString) throws ClassNotFoundException, IOException;
}
