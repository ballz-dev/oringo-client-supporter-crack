package org.spongepowered.tools.obfuscation.mapping.mcp;

import com.google.common.base.Strings;
import com.google.common.collect.BiMap;
import com.google.common.io.Files;
import com.google.common.io.LineProcessor;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import org.spongepowered.asm.mixin.throwables.MixinException;
import org.spongepowered.asm.obfuscation.mapping.common.MappingField;
import org.spongepowered.asm.obfuscation.mapping.common.MappingMethod;
import org.spongepowered.asm.obfuscation.mapping.mcp.MappingFieldSrg;
import org.spongepowered.tools.obfuscation.mapping.common.MappingProvider;

public class MappingProviderSrg extends MappingProvider {
  public MappingProviderSrg(Messager paramMessager, Filer paramFiler) {
    super(paramMessager, paramFiler);
  }
  
  public void read(final File input) throws IOException {
    final BiMap packageMap = this.packageMap;
    final BiMap classMap = this.classMap;
    final BiMap fieldMap = this.fieldMap;
    final BiMap methodMap = this.methodMap;
    Files.readLines(input, Charset.defaultCharset(), new LineProcessor<String>() {
          public String getResult() {
            return null;
          }
          
          public boolean processLine(String param1String) throws IOException {
            if (Strings.isNullOrEmpty(param1String) || param1String.startsWith("#"))
              return true; 
            String str = param1String.substring(0, 2);
            String[] arrayOfString = param1String.substring(4).split(" ");
            if (str.equals("PK")) {
              packageMap.forcePut(arrayOfString[0], arrayOfString[1]);
            } else if (str.equals("CL")) {
              classMap.forcePut(arrayOfString[0], arrayOfString[1]);
            } else if (str.equals("FD")) {
              fieldMap.forcePut((new MappingFieldSrg(arrayOfString[0])).copy(), (new MappingFieldSrg(arrayOfString[1])).copy());
            } else if (str.equals("MD")) {
              methodMap.forcePut(new MappingMethod(arrayOfString[0], arrayOfString[1]), new MappingMethod(arrayOfString[2], arrayOfString[3]));
            } else {
              throw new MixinException("Invalid SRG file: " + input);
            } 
            return true;
          }
        });
  }
  
  public MappingField getFieldMapping(MappingField paramMappingField) {
    MappingFieldSrg mappingFieldSrg;
    if (paramMappingField.getDesc() != null)
      mappingFieldSrg = new MappingFieldSrg(paramMappingField); 
    return (MappingField)this.fieldMap.get(mappingFieldSrg);
  }
}
