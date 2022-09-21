package org.spongepowered.asm.mixin.injection.modify;

import java.util.Collection;
import java.util.ListIterator;
import org.spongepowered.asm.lib.Type;
import org.spongepowered.asm.lib.tree.AbstractInsnNode;
import org.spongepowered.asm.lib.tree.InsnList;
import org.spongepowered.asm.lib.tree.VarInsnNode;
import org.spongepowered.asm.mixin.injection.InjectionPoint.AtCode;
import org.spongepowered.asm.mixin.injection.struct.InjectionPointData;
import org.spongepowered.asm.mixin.injection.struct.Target;

@AtCode("LOAD")
public class BeforeLoadLocal extends ModifyVariableInjector.ContextualInjectionPoint {
  private final int opcode;
  
  private boolean opcodeAfter;
  
  private final int ordinal;
  
  private final LocalVariableDiscriminator discriminator;
  
  private final Type returnType;
  
  static class SearchState {
    private int ordinal = 0;
    
    private boolean pendingCheck = false;
    
    private boolean found = false;
    
    private final int targetOrdinal;
    
    private VarInsnNode varNode;
    
    private final boolean print;
    
    SearchState(int param1Int, boolean param1Boolean) {
      this.targetOrdinal = param1Int;
      this.print = param1Boolean;
    }
    
    boolean success() {
      return this.found;
    }
    
    boolean isPendingCheck() {
      return this.pendingCheck;
    }
    
    void setPendingCheck() {
      this.pendingCheck = true;
    }
    
    void register(VarInsnNode param1VarInsnNode) {
      this.varNode = param1VarInsnNode;
    }
    
    void check(Collection<AbstractInsnNode> param1Collection, AbstractInsnNode param1AbstractInsnNode, int param1Int) {
      this.pendingCheck = false;
      if (param1Int != this.varNode.var && (param1Int > -2 || !this.print))
        return; 
      if (this.targetOrdinal == -1 || this.targetOrdinal == this.ordinal) {
        param1Collection.add(param1AbstractInsnNode);
        this.found = true;
      } 
      this.ordinal++;
      this.varNode = null;
    }
  }
  
  protected BeforeLoadLocal(InjectionPointData paramInjectionPointData) {
    this(paramInjectionPointData, 21, false);
  }
  
  protected BeforeLoadLocal(InjectionPointData paramInjectionPointData, int paramInt, boolean paramBoolean) {
    super(paramInjectionPointData.getContext());
    this.returnType = paramInjectionPointData.getMethodReturnType();
    this.discriminator = paramInjectionPointData.getLocalVariableDiscriminator();
    this.opcode = paramInjectionPointData.getOpcode(this.returnType.getOpcode(paramInt));
    this.ordinal = paramInjectionPointData.getOrdinal();
    this.opcodeAfter = paramBoolean;
  }
  
  boolean find(Target paramTarget, Collection<AbstractInsnNode> paramCollection) {
    SearchState searchState = new SearchState(this.ordinal, this.discriminator.printLVT());
    ListIterator<AbstractInsnNode> listIterator = paramTarget.method.instructions.iterator();
    while (listIterator.hasNext()) {
      AbstractInsnNode abstractInsnNode = listIterator.next();
      if (searchState.isPendingCheck()) {
        int i = this.discriminator.findLocal(this.returnType, this.discriminator.isArgsOnly(), paramTarget, abstractInsnNode);
        searchState.check(paramCollection, abstractInsnNode, i);
        continue;
      } 
      if (abstractInsnNode instanceof VarInsnNode && abstractInsnNode.getOpcode() == this.opcode && (this.ordinal == -1 || !searchState.success())) {
        searchState.register((VarInsnNode)abstractInsnNode);
        if (this.opcodeAfter) {
          searchState.setPendingCheck();
          continue;
        } 
        int i = this.discriminator.findLocal(this.returnType, this.discriminator.isArgsOnly(), paramTarget, abstractInsnNode);
        searchState.check(paramCollection, abstractInsnNode, i);
      } 
    } 
    return searchState.success();
  }
}
