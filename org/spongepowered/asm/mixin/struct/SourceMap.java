package org.spongepowered.asm.mixin.struct;

import java.util.LinkedHashMap;
import java.util.ListIterator;
import java.util.Map;
import org.spongepowered.asm.lib.tree.AbstractInsnNode;
import org.spongepowered.asm.lib.tree.ClassNode;
import org.spongepowered.asm.lib.tree.LineNumberNode;
import org.spongepowered.asm.lib.tree.MethodNode;
import org.spongepowered.asm.util.Bytecode;

public class SourceMap {
  private final String sourceFile;
  
  private static final String DEFAULT_STRATUM = "Mixin";
  
  public static class File {
    public final int lineOffset;
    
    public final String sourceFileName;
    
    public final int id;
    
    public final String sourceFilePath;
    
    public final int size;
    
    public File(int param1Int1, int param1Int2, int param1Int3, String param1String) {
      this(param1Int1, param1Int2, param1Int3, param1String, null);
    }
    
    public File(int param1Int1, int param1Int2, int param1Int3, String param1String1, String param1String2) {
      this.id = param1Int1;
      this.lineOffset = param1Int2;
      this.size = param1Int3;
      this.sourceFileName = param1String1;
      this.sourceFilePath = param1String2;
    }
    
    public void applyOffset(ClassNode param1ClassNode) {
      for (MethodNode methodNode : param1ClassNode.methods)
        applyOffset(methodNode); 
    }
    
    public void applyOffset(MethodNode param1MethodNode) {
      for (ListIterator<AbstractInsnNode> listIterator = param1MethodNode.instructions.iterator(); listIterator.hasNext(); ) {
        AbstractInsnNode abstractInsnNode = listIterator.next();
        if (abstractInsnNode instanceof LineNumberNode)
          ((LineNumberNode)abstractInsnNode).line += this.lineOffset - 1; 
      } 
    }
    
    void appendFile(StringBuilder param1StringBuilder) {
      if (this.sourceFilePath != null) {
        param1StringBuilder.append("+ ").append(this.id).append(" ").append(this.sourceFileName).append("\n");
        param1StringBuilder.append(this.sourceFilePath).append("\n");
      } else {
        param1StringBuilder.append(this.id).append(" ").append(this.sourceFileName).append("\n");
      } 
    }
    
    public void appendLines(StringBuilder param1StringBuilder) {
      param1StringBuilder.append("1#").append(this.id)
        .append(",").append(this.size)
        .append(":").append(this.lineOffset)
        .append("\n");
    }
  }
  
  static class Stratum {
    private final Map<String, SourceMap.File> files = new LinkedHashMap<String, SourceMap.File>();
    
    private static final String FILE_MARK = "*F";
    
    private static final String LINES_MARK = "*L";
    
    public final String name;
    
    private static final String STRATUM_MARK = "*S";
    
    public Stratum(String param1String) {
      this.name = param1String;
    }
    
    public SourceMap.File addFile(int param1Int1, int param1Int2, String param1String1, String param1String2) {
      SourceMap.File file = this.files.get(param1String2);
      if (file == null) {
        file = new SourceMap.File(this.files.size() + 1, param1Int1, param1Int2, param1String1, param1String2);
        this.files.put(param1String2, file);
      } 
      return file;
    }
    
    void appendTo(StringBuilder param1StringBuilder) {
      param1StringBuilder.append("*S").append(" ").append(this.name).append("\n");
      param1StringBuilder.append("*F").append("\n");
      for (SourceMap.File file : this.files.values())
        file.appendFile(param1StringBuilder); 
      param1StringBuilder.append("*L").append("\n");
      for (SourceMap.File file : this.files.values())
        file.appendLines(param1StringBuilder); 
    }
  }
  
  private final Map<String, Stratum> strata = new LinkedHashMap<String, Stratum>();
  
  private int nextLineOffset = 1;
  
  private static final String NEWLINE = "\n";
  
  private String defaultStratum = "Mixin";
  
  public SourceMap(String paramString) {
    this.sourceFile = paramString;
  }
  
  public String getSourceFile() {
    return this.sourceFile;
  }
  
  public String getPseudoGeneratedSourceFile() {
    return this.sourceFile.replace(".java", "$mixin.java");
  }
  
  public File addFile(ClassNode paramClassNode) {
    return addFile(this.defaultStratum, paramClassNode);
  }
  
  public File addFile(String paramString, ClassNode paramClassNode) {
    return addFile(paramString, paramClassNode.sourceFile, paramClassNode.name + ".java", Bytecode.getMaxLineNumber(paramClassNode, 500, 50));
  }
  
  public File addFile(String paramString1, String paramString2, int paramInt) {
    return addFile(this.defaultStratum, paramString1, paramString2, paramInt);
  }
  
  public File addFile(String paramString1, String paramString2, String paramString3, int paramInt) {
    Stratum stratum = this.strata.get(paramString1);
    if (stratum == null) {
      stratum = new Stratum(paramString1);
      this.strata.put(paramString1, stratum);
    } 
    File file = stratum.addFile(this.nextLineOffset, paramInt, paramString2, paramString3);
    this.nextLineOffset += paramInt;
    return file;
  }
  
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder();
    appendTo(stringBuilder);
    return stringBuilder.toString();
  }
  
  private void appendTo(StringBuilder paramStringBuilder) {
    paramStringBuilder.append("SMAP").append("\n");
    paramStringBuilder.append(getSourceFile()).append("\n");
    paramStringBuilder.append(this.defaultStratum).append("\n");
    for (Stratum stratum : this.strata.values())
      stratum.appendTo(paramStringBuilder); 
    paramStringBuilder.append("*E").append("\n");
  }
}
