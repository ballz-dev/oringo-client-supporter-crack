package org.spongepowered.asm.mixin.injection.struct;

import com.google.common.base.Joiner;
import com.google.common.base.Strings;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.spongepowered.asm.lib.Type;
import org.spongepowered.asm.lib.tree.AnnotationNode;
import org.spongepowered.asm.lib.tree.MethodNode;
import org.spongepowered.asm.mixin.injection.InjectionPoint;
import org.spongepowered.asm.mixin.injection.modify.LocalVariableDiscriminator;
import org.spongepowered.asm.mixin.injection.throwables.InvalidInjectionPointException;
import org.spongepowered.asm.mixin.refmap.IMixinContext;

public class InjectionPointData {
  private static final Pattern AT_PATTERN = createPattern();
  
  private final MethodNode method;
  
  private final AnnotationNode parent;
  
  private final String target;
  
  private final int opcode;
  
  private final IMixinContext context;
  
  private final Map<String, String> args = new HashMap<String, String>();
  
  private final String at;
  
  private final String slice;
  
  private final int ordinal;
  
  private final String type;
  
  private final String id;
  
  private final InjectionPoint.Selector selector;
  
  public InjectionPointData(IMixinContext paramIMixinContext, MethodNode paramMethodNode, AnnotationNode paramAnnotationNode, String paramString1, List<String> paramList, String paramString2, String paramString3, int paramInt1, int paramInt2, String paramString4) {
    this.context = paramIMixinContext;
    this.method = paramMethodNode;
    this.parent = paramAnnotationNode;
    this.at = paramString1;
    this.target = paramString2;
    this.slice = Strings.nullToEmpty(paramString3);
    this.ordinal = Math.max(-1, paramInt1);
    this.opcode = paramInt2;
    this.id = paramString4;
    parseArgs(paramList);
    this.args.put("target", paramString2);
    this.args.put("ordinal", String.valueOf(paramInt1));
    this.args.put("opcode", String.valueOf(paramInt2));
    Matcher matcher = AT_PATTERN.matcher(paramString1);
    this.type = parseType(matcher, paramString1);
    this.selector = parseSelector(matcher);
  }
  
  private void parseArgs(List<String> paramList) {
    if (paramList == null)
      return; 
    for (String str : paramList) {
      if (str != null) {
        int i = str.indexOf('=');
        if (i > -1) {
          this.args.put(str.substring(0, i), str.substring(i + 1));
          continue;
        } 
        this.args.put(str, "");
      } 
    } 
  }
  
  public String getAt() {
    return this.at;
  }
  
  public String getType() {
    return this.type;
  }
  
  public InjectionPoint.Selector getSelector() {
    return this.selector;
  }
  
  public IMixinContext getContext() {
    return this.context;
  }
  
  public MethodNode getMethod() {
    return this.method;
  }
  
  public Type getMethodReturnType() {
    return Type.getReturnType(this.method.desc);
  }
  
  public AnnotationNode getParent() {
    return this.parent;
  }
  
  public String getSlice() {
    return this.slice;
  }
  
  public LocalVariableDiscriminator getLocalVariableDiscriminator() {
    return LocalVariableDiscriminator.parse(this.parent);
  }
  
  public String get(String paramString1, String paramString2) {
    String str = this.args.get(paramString1);
    return (str != null) ? str : paramString2;
  }
  
  public int get(String paramString, int paramInt) {
    return parseInt(get(paramString, String.valueOf(paramInt)), paramInt);
  }
  
  public boolean get(String paramString, boolean paramBoolean) {
    return parseBoolean(get(paramString, String.valueOf(paramBoolean)), paramBoolean);
  }
  
  public MemberInfo get(String paramString) {
    try {
      return MemberInfo.parseAndValidate(get(paramString, ""), this.context);
    } catch (InvalidMemberDescriptorException invalidMemberDescriptorException) {
      throw new InvalidInjectionPointException(this.context, "Failed parsing @At(\"%s\").%s descriptor \"%s\" on %s", new Object[] { this.at, paramString, this.target, 
            InjectionInfo.describeInjector(this.context, this.parent, this.method) });
    } 
  }
  
  public MemberInfo getTarget() {
    try {
      return MemberInfo.parseAndValidate(this.target, this.context);
    } catch (InvalidMemberDescriptorException invalidMemberDescriptorException) {
      throw new InvalidInjectionPointException(this.context, "Failed parsing @At(\"%s\") descriptor \"%s\" on %s", new Object[] { this.at, this.target, 
            InjectionInfo.describeInjector(this.context, this.parent, this.method) });
    } 
  }
  
  public int getOrdinal() {
    return this.ordinal;
  }
  
  public int getOpcode() {
    return this.opcode;
  }
  
  public int getOpcode(int paramInt) {
    return (this.opcode > 0) ? this.opcode : paramInt;
  }
  
  public int getOpcode(int paramInt, int... paramVarArgs) {
    for (int i : paramVarArgs) {
      if (this.opcode == i)
        return this.opcode; 
    } 
    return paramInt;
  }
  
  public String getId() {
    return this.id;
  }
  
  public String toString() {
    return this.type;
  }
  
  private static Pattern createPattern() {
    return Pattern.compile(String.format("^([^:]+):?(%s)?$", new Object[] { Joiner.on('|').join((Object[])InjectionPoint.Selector.values()) }));
  }
  
  public static String parseType(String paramString) {
    Matcher matcher = AT_PATTERN.matcher(paramString);
    return parseType(matcher, paramString);
  }
  
  private static String parseType(Matcher paramMatcher, String paramString) {
    return paramMatcher.matches() ? paramMatcher.group(1) : paramString;
  }
  
  private static InjectionPoint.Selector parseSelector(Matcher paramMatcher) {
    return (paramMatcher.matches() && paramMatcher.group(2) != null) ? InjectionPoint.Selector.valueOf(paramMatcher.group(2)) : InjectionPoint.Selector.DEFAULT;
  }
  
  private static int parseInt(String paramString, int paramInt) {
    try {
      return Integer.parseInt(paramString);
    } catch (Exception exception) {
      return paramInt;
    } 
  }
  
  private static boolean parseBoolean(String paramString, boolean paramBoolean) {
    try {
      return Boolean.parseBoolean(paramString);
    } catch (Exception exception) {
      return paramBoolean;
    } 
  }
}
