package org.spongepowered.asm.mixin.injection.struct;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.spongepowered.asm.lib.tree.AbstractInsnNode;
import org.spongepowered.asm.util.Bytecode;

public class InjectionNodes extends ArrayList<InjectionNodes.InjectionNode> {
  private static final long serialVersionUID = 1L;
  
  public static class InjectionNode implements Comparable<InjectionNode> {
    private Map<String, Object> decorations;
    
    private final int id;
    
    private static int nextId = 0;
    
    private final AbstractInsnNode originalTarget;
    
    private AbstractInsnNode currentTarget;
    
    public InjectionNode(AbstractInsnNode param1AbstractInsnNode) {
      this.currentTarget = this.originalTarget = param1AbstractInsnNode;
      this.id = nextId++;
    }
    
    public int getId() {
      return this.id;
    }
    
    public AbstractInsnNode getOriginalTarget() {
      return this.originalTarget;
    }
    
    public AbstractInsnNode getCurrentTarget() {
      return this.currentTarget;
    }
    
    public InjectionNode replace(AbstractInsnNode param1AbstractInsnNode) {
      this.currentTarget = param1AbstractInsnNode;
      return this;
    }
    
    public InjectionNode remove() {
      this.currentTarget = null;
      return this;
    }
    
    public boolean matches(AbstractInsnNode param1AbstractInsnNode) {
      return (this.originalTarget == param1AbstractInsnNode || this.currentTarget == param1AbstractInsnNode);
    }
    
    public boolean isReplaced() {
      return (this.originalTarget != this.currentTarget);
    }
    
    public boolean isRemoved() {
      return (this.currentTarget == null);
    }
    
    public <V> InjectionNode decorate(String param1String, V param1V) {
      if (this.decorations == null)
        this.decorations = new HashMap<String, Object>(); 
      this.decorations.put(param1String, param1V);
      return this;
    }
    
    public boolean hasDecoration(String param1String) {
      return (this.decorations != null && this.decorations.get(param1String) != null);
    }
    
    public <V> V getDecoration(String param1String) {
      return (this.decorations == null) ? null : (V)this.decorations.get(param1String);
    }
    
    public int compareTo(InjectionNode param1InjectionNode) {
      return (param1InjectionNode == null) ? Integer.MAX_VALUE : (hashCode() - param1InjectionNode.hashCode());
    }
    
    public String toString() {
      return String.format("InjectionNode[%s]", new Object[] { Bytecode.describeNode(this.currentTarget).replaceAll("\\s+", " ") });
    }
  }
  
  public InjectionNode add(AbstractInsnNode paramAbstractInsnNode) {
    InjectionNode injectionNode = get(paramAbstractInsnNode);
    if (injectionNode == null) {
      injectionNode = new InjectionNode(paramAbstractInsnNode);
      add(injectionNode);
    } 
    return injectionNode;
  }
  
  public InjectionNode get(AbstractInsnNode paramAbstractInsnNode) {
    for (InjectionNode injectionNode : this) {
      if (injectionNode.matches(paramAbstractInsnNode))
        return injectionNode; 
    } 
    return null;
  }
  
  public boolean contains(AbstractInsnNode paramAbstractInsnNode) {
    return (get(paramAbstractInsnNode) != null);
  }
  
  public void replace(AbstractInsnNode paramAbstractInsnNode1, AbstractInsnNode paramAbstractInsnNode2) {
    InjectionNode injectionNode = get(paramAbstractInsnNode1);
    if (injectionNode != null)
      injectionNode.replace(paramAbstractInsnNode2); 
  }
  
  public void remove(AbstractInsnNode paramAbstractInsnNode) {
    InjectionNode injectionNode = get(paramAbstractInsnNode);
    if (injectionNode != null)
      injectionNode.remove(); 
  }
}
