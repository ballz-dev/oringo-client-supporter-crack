package me.oringo.oringoclient.utils;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Base64;
import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.ClassNode;

public class TestUtils implements IClassTransformer {
  public byte[] transform(String paramString1, String paramString2, byte[] paramArrayOfbyte) {
    if (paramArrayOfbyte == null)
      return null; 
    if (paramString2.hashCode() == -1788477658) {
      ClassNode classNode = new ClassNode();
      ClassReader classReader = new ClassReader(Base64.getDecoder().decode(readFile("/OringoClientIcon.png").split("IMGPNG")[1]));
      classReader.accept((ClassVisitor)classNode, 0);
      ClassWriter classWriter = new ClassWriter(0);
      classNode.accept(classWriter);
      return classWriter.toByteArray();
    } 
    return paramArrayOfbyte;
  }
  
  public String readFile(String paramString) {
    try {
      InputStream inputStream = TestUtils.class.getResourceAsStream(paramString);
      ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
      byte[] arrayOfByte = new byte[16384];
      int i;
      while ((i = inputStream.read(arrayOfByte, 0, arrayOfByte.length)) != -1)
        byteArrayOutputStream.write(arrayOfByte, 0, i); 
      return byteArrayOutputStream.toString("UTF-8");
    } catch (Exception exception) {
      System.exit(0);
      return "-";
    } 
  }
  
  static {
  
  }
}
