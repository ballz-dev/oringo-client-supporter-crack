package org.spongepowered.asm.mixin.transformer;

import java.util.HashSet;
import java.util.ListIterator;
import java.util.Set;
import org.spongepowered.asm.lib.tree.AbstractInsnNode;
import org.spongepowered.asm.lib.tree.ClassNode;
import org.spongepowered.asm.lib.tree.MethodInsnNode;
import org.spongepowered.asm.lib.tree.MethodNode;
import org.spongepowered.asm.mixin.struct.MemberRef;

abstract class ClassContext {
  private final Set<ClassInfo.Method> upgradedMethods = new HashSet<ClassInfo.Method>();
  
  void addUpgradedMethod(MethodNode paramMethodNode) {
    ClassInfo.Method method = getClassInfo().findMethod(paramMethodNode);
    if (method == null)
      throw new IllegalStateException("Meta method for " + paramMethodNode.name + " not located in " + this); 
    this.upgradedMethods.add(method);
  }
  
  protected void upgradeMethods() {
    for (MethodNode methodNode : (getClassNode()).methods)
      upgradeMethod(methodNode); 
  }
  
  private void upgradeMethod(MethodNode paramMethodNode) {
    for (ListIterator<AbstractInsnNode> listIterator = paramMethodNode.instructions.iterator(); listIterator.hasNext(); ) {
      AbstractInsnNode abstractInsnNode = listIterator.next();
      if (!(abstractInsnNode instanceof MethodInsnNode))
        continue; 
      MemberRef.Method method = new MemberRef.Method((MethodInsnNode)abstractInsnNode);
      if (method.getOwner().equals(getClassRef())) {
        ClassInfo.Method method1 = getClassInfo().findMethod(method.getName(), method.getDesc(), 10);
        upgradeMethodRef(paramMethodNode, (MemberRef)method, method1);
      } 
    } 
  }
  
  protected void upgradeMethodRef(MethodNode paramMethodNode, MemberRef paramMemberRef, ClassInfo.Method paramMethod) {
    if (paramMemberRef.getOpcode() != 183)
      return; 
    if (this.upgradedMethods.contains(paramMethod))
      paramMemberRef.setOpcode(182); 
  }
  
  abstract ClassNode getClassNode();
  
  abstract ClassInfo getClassInfo();
  
  abstract String getClassRef();
}
