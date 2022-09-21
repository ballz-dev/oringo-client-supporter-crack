package org.spongepowered.tools.obfuscation.mapping.mcp;

import java.io.IOException;
import java.io.PrintWriter;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import org.spongepowered.asm.obfuscation.mapping.common.MappingField;
import org.spongepowered.asm.obfuscation.mapping.common.MappingMethod;
import org.spongepowered.tools.obfuscation.ObfuscationType;
import org.spongepowered.tools.obfuscation.mapping.IMappingConsumer;
import org.spongepowered.tools.obfuscation.mapping.common.MappingWriter;

public class MappingWriterSrg extends MappingWriter {
  public MappingWriterSrg(Messager paramMessager, Filer paramFiler) {
    super(paramMessager, paramFiler);
  }
  
  public void write(String paramString, ObfuscationType paramObfuscationType, IMappingConsumer.MappingSet<MappingField> paramMappingSet, IMappingConsumer.MappingSet<MappingMethod> paramMappingSet1) {
    if (paramString == null)
      return; 
    PrintWriter printWriter = null;
    try {
      printWriter = openFileWriter(paramString, paramObfuscationType + " output SRGs");
      writeFieldMappings(printWriter, paramMappingSet);
      writeMethodMappings(printWriter, paramMappingSet1);
    } catch (IOException iOException) {
      iOException.printStackTrace();
    } finally {
      if (printWriter != null)
        try {
          printWriter.close();
        } catch (Exception exception) {} 
    } 
  }
  
  protected void writeFieldMappings(PrintWriter paramPrintWriter, IMappingConsumer.MappingSet<MappingField> paramMappingSet) {
    for (IMappingConsumer.MappingSet.Pair<MappingField> pair : paramMappingSet)
      paramPrintWriter.println(formatFieldMapping(pair)); 
  }
  
  protected void writeMethodMappings(PrintWriter paramPrintWriter, IMappingConsumer.MappingSet<MappingMethod> paramMappingSet) {
    for (IMappingConsumer.MappingSet.Pair<MappingMethod> pair : paramMappingSet)
      paramPrintWriter.println(formatMethodMapping(pair)); 
  }
  
  protected String formatFieldMapping(IMappingConsumer.MappingSet.Pair<MappingField> paramPair) {
    return String.format("FD: %s/%s %s/%s", new Object[] { ((MappingField)paramPair.from).getOwner(), ((MappingField)paramPair.from).getName(), ((MappingField)paramPair.to).getOwner(), ((MappingField)paramPair.to).getName() });
  }
  
  protected String formatMethodMapping(IMappingConsumer.MappingSet.Pair<MappingMethod> paramPair) {
    return String.format("MD: %s %s %s %s", new Object[] { ((MappingMethod)paramPair.from).getName(), ((MappingMethod)paramPair.from).getDesc(), ((MappingMethod)paramPair.to).getName(), ((MappingMethod)paramPair.to).getDesc() });
  }
}
