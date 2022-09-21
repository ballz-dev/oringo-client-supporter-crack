package org.spongepowered.asm.mixin.injection.points;

import java.util.Collection;
import java.util.ListIterator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.lib.tree.AbstractInsnNode;
import org.spongepowered.asm.lib.tree.InsnList;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.injection.InjectionPoint;
import org.spongepowered.asm.mixin.injection.InjectionPoint.AtCode;
import org.spongepowered.asm.mixin.injection.struct.InjectionPointData;
import org.spongepowered.asm.mixin.injection.struct.MemberInfo;
import org.spongepowered.asm.mixin.refmap.IMixinContext;

@AtCode("INVOKE")
public class BeforeInvoke extends InjectionPoint {
  protected final boolean allowPermissive;
  
  protected final String className;
  
  public enum SearchType {
    STRICT, PERMISSIVE;
  }
  
  protected final Logger logger = LogManager.getLogger("mixin");
  
  private boolean log = false;
  
  protected final MemberInfo target;
  
  protected final int ordinal;
  
  protected final IMixinContext context;
  
  public BeforeInvoke(InjectionPointData paramInjectionPointData) {
    super(paramInjectionPointData);
    this.target = paramInjectionPointData.getTarget();
    this.ordinal = paramInjectionPointData.getOrdinal();
    this.log = paramInjectionPointData.get("log", false);
    this.className = getClassName();
    this.context = paramInjectionPointData.getContext();
    this
      .allowPermissive = (this.context.getOption(MixinEnvironment.Option.REFMAP_REMAP) && this.context.getOption(MixinEnvironment.Option.REFMAP_REMAP_ALLOW_PERMISSIVE) && !this.context.getReferenceMapper().isDefault());
  }
  
  private String getClassName() {
    InjectionPoint.AtCode atCode = getClass().<InjectionPoint.AtCode>getAnnotation(InjectionPoint.AtCode.class);
    return String.format("@At(%s)", new Object[] { (atCode != null) ? atCode.value() : getClass().getSimpleName().toUpperCase() });
  }
  
  public BeforeInvoke setLogging(boolean paramBoolean) {
    this.log = paramBoolean;
    return this;
  }
  
  public boolean find(String paramString, InsnList paramInsnList, Collection<AbstractInsnNode> paramCollection) {
    log("{} is searching for an injection point in method with descriptor {}", new Object[] { this.className, paramString });
    if (!find(paramString, paramInsnList, paramCollection, this.target, SearchType.STRICT) && this.target.desc != null && this.allowPermissive) {
      this.logger.warn("STRICT match for {} using \"{}\" in {} returned 0 results, attempting permissive search. To inhibit permissive search set mixin.env.allowPermissiveMatch=false", new Object[] { this.className, this.target, this.context });
      return find(paramString, paramInsnList, paramCollection, this.target, SearchType.PERMISSIVE);
    } 
    return true;
  }
  
  protected boolean find(String paramString, InsnList paramInsnList, Collection<AbstractInsnNode> paramCollection, MemberInfo paramMemberInfo, SearchType paramSearchType) {
    if (paramMemberInfo == null)
      return false; 
    MemberInfo memberInfo = (paramSearchType == SearchType.PERMISSIVE) ? paramMemberInfo.transform(null) : paramMemberInfo;
    byte b1 = 0;
    byte b2 = 0;
    ListIterator<AbstractInsnNode> listIterator = paramInsnList.iterator();
    while (listIterator.hasNext()) {
      AbstractInsnNode abstractInsnNode = listIterator.next();
      if (matchesInsn(abstractInsnNode)) {
        MemberInfo memberInfo1 = new MemberInfo(abstractInsnNode);
        log("{} is considering insn {}", new Object[] { this.className, memberInfo1 });
        if (memberInfo.matches(memberInfo1.owner, memberInfo1.name, memberInfo1.desc)) {
          log("{} > found a matching insn, checking preconditions...", new Object[] { this.className });
          if (matchesInsn(memberInfo1, b1)) {
            log("{} > > > found a matching insn at ordinal {}", new Object[] { this.className, Integer.valueOf(b1) });
            if (addInsn(paramInsnList, paramCollection, abstractInsnNode))
              b2++; 
            if (this.ordinal == b1)
              break; 
          } 
          b1++;
        } 
      } 
      inspectInsn(paramString, paramInsnList, abstractInsnNode);
    } 
    if (paramSearchType == SearchType.PERMISSIVE && b2 > 1)
      this.logger.warn("A permissive match for {} using \"{}\" in {} matched {} instructions, this may cause unexpected behaviour. To inhibit permissive search set mixin.env.allowPermissiveMatch=false", new Object[] { this.className, paramMemberInfo, this.context, 
            Integer.valueOf(b2) }); 
    return (b2 > 0);
  }
  
  protected boolean addInsn(InsnList paramInsnList, Collection<AbstractInsnNode> paramCollection, AbstractInsnNode paramAbstractInsnNode) {
    paramCollection.add(paramAbstractInsnNode);
    return true;
  }
  
  protected boolean matchesInsn(AbstractInsnNode paramAbstractInsnNode) {
    return paramAbstractInsnNode instanceof org.spongepowered.asm.lib.tree.MethodInsnNode;
  }
  
  protected void inspectInsn(String paramString, InsnList paramInsnList, AbstractInsnNode paramAbstractInsnNode) {}
  
  protected boolean matchesInsn(MemberInfo paramMemberInfo, int paramInt) {
    log("{} > > comparing target ordinal {} with current ordinal {}", new Object[] { this.className, Integer.valueOf(this.ordinal), Integer.valueOf(paramInt) });
    return (this.ordinal == -1 || this.ordinal == paramInt);
  }
  
  protected void log(String paramString, Object... paramVarArgs) {
    if (this.log)
      this.logger.info(paramString, paramVarArgs); 
  }
}
