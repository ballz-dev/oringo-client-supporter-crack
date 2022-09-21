package org.spongepowered.asm.mixin.transformer;

import com.google.common.base.Strings;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.lib.tree.MethodNode;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.injection.struct.InjectionInfo;
import org.spongepowered.asm.util.Counter;

public class MethodMapper {
  private static final Map<String, Counter> methods;
  
  private static final Logger logger = LogManager.getLogger("mixin");
  
  private final ClassInfo info;
  
  private static final List<String> classes = new ArrayList<String>();
  
  static {
    methods = new HashMap<String, Counter>();
  }
  
  public MethodMapper(MixinEnvironment paramMixinEnvironment, ClassInfo paramClassInfo) {
    this.info = paramClassInfo;
  }
  
  public ClassInfo getClassInfo() {
    return this.info;
  }
  
  public void remapHandlerMethod(MixinInfo paramMixinInfo, MethodNode paramMethodNode, ClassInfo.Method paramMethod) {
    if (!(paramMethodNode instanceof MixinInfo.MixinMethodNode) || !((MixinInfo.MixinMethodNode)paramMethodNode).isInjector())
      return; 
    if (paramMethod.isUnique())
      logger.warn("Redundant @Unique on injector method {} in {}. Injectors are implicitly unique", new Object[] { paramMethod, paramMixinInfo }); 
    if (paramMethod.isRenamed()) {
      paramMethodNode.name = paramMethod.getName();
      return;
    } 
    String str = getHandlerName((MixinInfo.MixinMethodNode)paramMethodNode);
    paramMethodNode.name = paramMethod.renameTo(str);
  }
  
  public String getHandlerName(MixinInfo.MixinMethodNode paramMixinMethodNode) {
    String str1 = InjectionInfo.getInjectorPrefix(paramMixinMethodNode.getInjectorAnnotation());
    String str2 = getClassUID(paramMixinMethodNode.getOwner().getClassRef());
    String str3 = getMethodUID(paramMixinMethodNode.name, paramMixinMethodNode.desc, !paramMixinMethodNode.isSurrogate());
    return String.format("%s$%s$%s%s", new Object[] { str1, paramMixinMethodNode.name, str2, str3 });
  }
  
  private static String getClassUID(String paramString) {
    int i = classes.indexOf(paramString);
    if (i < 0) {
      i = classes.size();
      classes.add(paramString);
    } 
    return finagle(i);
  }
  
  private static String getMethodUID(String paramString1, String paramString2, boolean paramBoolean) {
    String str = String.format("%s%s", new Object[] { paramString1, paramString2 });
    Counter counter = methods.get(str);
    if (counter == null) {
      counter = new Counter();
      methods.put(str, counter);
    } else if (paramBoolean) {
      counter.value++;
    } 
    return String.format("%03x", new Object[] { Integer.valueOf(counter.value) });
  }
  
  private static String finagle(int paramInt) {
    String str = Integer.toHexString(paramInt);
    StringBuilder stringBuilder = new StringBuilder();
    for (byte b = 0; b < str.length(); b++) {
      char c = str.charAt(b);
      stringBuilder.append(c = (char)(c + ((c < ':') ? 49 : 10)));
    } 
    return Strings.padStart(stringBuilder.toString(), 3, 'z');
  }
}
