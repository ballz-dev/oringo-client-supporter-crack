package org.spongepowered.asm.mixin.struct;

import org.spongepowered.asm.lib.tree.FieldInsnNode;
import org.spongepowered.asm.lib.tree.MethodInsnNode;
import org.spongepowered.asm.mixin.transformer.throwables.MixinTransformerError;
import org.spongepowered.asm.util.Bytecode;

public abstract class MemberRef {
  public static final class Method extends MemberRef {
    private static final int OPCODES = 191;
    
    public final MethodInsnNode insn;
    
    public Method(MethodInsnNode param1MethodInsnNode) {
      this.insn = param1MethodInsnNode;
    }
    
    public boolean isField() {
      return false;
    }
    
    public int getOpcode() {
      return this.insn.getOpcode();
    }
    
    public void setOpcode(int param1Int) {
      if ((param1Int & 0xBF) == 0)
        throw new IllegalArgumentException("Invalid opcode for method instruction: 0x" + Integer.toHexString(param1Int)); 
      this.insn.setOpcode(param1Int);
    }
    
    public String getOwner() {
      return this.insn.owner;
    }
    
    public void setOwner(String param1String) {
      this.insn.owner = param1String;
    }
    
    public String getName() {
      return this.insn.name;
    }
    
    public void setName(String param1String) {
      this.insn.name = param1String;
    }
    
    public String getDesc() {
      return this.insn.desc;
    }
    
    public void setDesc(String param1String) {
      this.insn.desc = param1String;
    }
  }
  
  public static final class Field extends MemberRef {
    public final FieldInsnNode insn;
    
    private static final int OPCODES = 183;
    
    public Field(FieldInsnNode param1FieldInsnNode) {
      this.insn = param1FieldInsnNode;
    }
    
    public boolean isField() {
      return true;
    }
    
    public int getOpcode() {
      return this.insn.getOpcode();
    }
    
    public void setOpcode(int param1Int) {
      if ((param1Int & 0xB7) == 0)
        throw new IllegalArgumentException("Invalid opcode for field instruction: 0x" + Integer.toHexString(param1Int)); 
      this.insn.setOpcode(param1Int);
    }
    
    public String getOwner() {
      return this.insn.owner;
    }
    
    public void setOwner(String param1String) {
      this.insn.owner = param1String;
    }
    
    public String getName() {
      return this.insn.name;
    }
    
    public void setName(String param1String) {
      this.insn.name = param1String;
    }
    
    public String getDesc() {
      return this.insn.desc;
    }
    
    public void setDesc(String param1String) {
      this.insn.desc = param1String;
    }
  }
  
  public static final class Handle extends MemberRef {
    private org.spongepowered.asm.lib.Handle handle;
    
    public Handle(org.spongepowered.asm.lib.Handle param1Handle) {
      this.handle = param1Handle;
    }
    
    public org.spongepowered.asm.lib.Handle getMethodHandle() {
      return this.handle;
    }
    
    public boolean isField() {
      switch (this.handle.getTag()) {
        case 5:
        case 6:
        case 7:
        case 8:
        case 9:
          return false;
        case 1:
        case 2:
        case 3:
        case 4:
          return true;
      } 
      throw new MixinTransformerError("Invalid tag " + this.handle.getTag() + " for method handle " + this.handle + ".");
    }
    
    public int getOpcode() {
      int i = MemberRef.opcodeFromTag(this.handle.getTag());
      if (i == 0)
        throw new MixinTransformerError("Invalid tag " + this.handle.getTag() + " for method handle " + this.handle + "."); 
      return i;
    }
    
    public void setOpcode(int param1Int) {
      int i = MemberRef.tagFromOpcode(param1Int);
      if (i == 0)
        throw new MixinTransformerError("Invalid opcode " + Bytecode.getOpcodeName(param1Int) + " for method handle " + this.handle + "."); 
      boolean bool = (i == 9) ? true : false;
      this.handle = new org.spongepowered.asm.lib.Handle(i, this.handle.getOwner(), this.handle.getName(), this.handle.getDesc(), bool);
    }
    
    public String getOwner() {
      return this.handle.getOwner();
    }
    
    public void setOwner(String param1String) {
      boolean bool = (this.handle.getTag() == 9) ? true : false;
      this.handle = new org.spongepowered.asm.lib.Handle(this.handle.getTag(), param1String, this.handle.getName(), this.handle.getDesc(), bool);
    }
    
    public String getName() {
      return this.handle.getName();
    }
    
    public void setName(String param1String) {
      boolean bool = (this.handle.getTag() == 9) ? true : false;
      this.handle = new org.spongepowered.asm.lib.Handle(this.handle.getTag(), this.handle.getOwner(), param1String, this.handle.getDesc(), bool);
    }
    
    public String getDesc() {
      return this.handle.getDesc();
    }
    
    public void setDesc(String param1String) {
      boolean bool = (this.handle.getTag() == 9) ? true : false;
      this.handle = new org.spongepowered.asm.lib.Handle(this.handle.getTag(), this.handle.getOwner(), this.handle.getName(), param1String, bool);
    }
  }
  
  private static final int[] H_OPCODES = new int[] { 0, 180, 178, 181, 179, 182, 184, 183, 183, 185 };
  
  public String toString() {
    String str = Bytecode.getOpcodeName(getOpcode());
    return String.format("%s for %s.%s%s%s", new Object[] { str, getOwner(), getName(), isField() ? ":" : "", getDesc() });
  }
  
  public boolean equals(Object paramObject) {
    if (!(paramObject instanceof MemberRef))
      return false; 
    MemberRef memberRef = (MemberRef)paramObject;
    return (getOpcode() == memberRef.getOpcode() && 
      getOwner().equals(memberRef.getOwner()) && 
      getName().equals(memberRef.getName()) && 
      getDesc().equals(memberRef.getDesc()));
  }
  
  public int hashCode() {
    return toString().hashCode();
  }
  
  static int opcodeFromTag(int paramInt) {
    return (paramInt >= 0 && paramInt < H_OPCODES.length) ? H_OPCODES[paramInt] : 0;
  }
  
  static int tagFromOpcode(int paramInt) {
    for (byte b = 1; b < H_OPCODES.length; b++) {
      if (H_OPCODES[b] == paramInt)
        return b; 
    } 
    return 0;
  }
  
  public abstract void setOwner(String paramString);
  
  public abstract void setName(String paramString);
  
  public abstract void setDesc(String paramString);
  
  public abstract int getOpcode();
  
  public abstract String getOwner();
  
  public abstract String getName();
  
  public abstract boolean isField();
  
  public abstract void setOpcode(int paramInt);
  
  public abstract String getDesc();
}
