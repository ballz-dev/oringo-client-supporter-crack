package org.spongepowered.tools.obfuscation.mirror;

import org.spongepowered.asm.obfuscation.mapping.IMapping;

public abstract class MemberHandle<T extends IMapping<T>> {
  private final String owner;
  
  private final String name;
  
  private final String desc;
  
  protected MemberHandle(String paramString1, String paramString2, String paramString3) {
    this.owner = paramString1;
    this.name = paramString2;
    this.desc = paramString3;
  }
  
  public final String getOwner() {
    return this.owner;
  }
  
  public final String getName() {
    return this.name;
  }
  
  public final String getDesc() {
    return this.desc;
  }
  
  public abstract T asMapping(boolean paramBoolean);
  
  public abstract Visibility getVisibility();
}
