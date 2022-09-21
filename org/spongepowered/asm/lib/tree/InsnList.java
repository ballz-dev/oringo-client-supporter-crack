package org.spongepowered.asm.lib.tree;

import java.util.ListIterator;
import java.util.NoSuchElementException;
import org.spongepowered.asm.lib.MethodVisitor;

public class InsnList {
  private int size;
  
  private AbstractInsnNode first;
  
  private AbstractInsnNode last;
  
  AbstractInsnNode[] cache;
  
  public int size() {
    return this.size;
  }
  
  public AbstractInsnNode getFirst() {
    return this.first;
  }
  
  public AbstractInsnNode getLast() {
    return this.last;
  }
  
  public AbstractInsnNode get(int paramInt) {
    if (paramInt < 0 || paramInt >= this.size)
      throw new IndexOutOfBoundsException(); 
    if (this.cache == null)
      this.cache = toArray(); 
    return this.cache[paramInt];
  }
  
  public boolean contains(AbstractInsnNode paramAbstractInsnNode) {
    AbstractInsnNode abstractInsnNode = this.first;
    while (abstractInsnNode != null && abstractInsnNode != paramAbstractInsnNode)
      abstractInsnNode = abstractInsnNode.next; 
    return (abstractInsnNode != null);
  }
  
  public int indexOf(AbstractInsnNode paramAbstractInsnNode) {
    if (this.cache == null)
      this.cache = toArray(); 
    return paramAbstractInsnNode.index;
  }
  
  public void accept(MethodVisitor paramMethodVisitor) {
    AbstractInsnNode abstractInsnNode = this.first;
    while (abstractInsnNode != null) {
      abstractInsnNode.accept(paramMethodVisitor);
      abstractInsnNode = abstractInsnNode.next;
    } 
  }
  
  public ListIterator<AbstractInsnNode> iterator() {
    return iterator(0);
  }
  
  public ListIterator<AbstractInsnNode> iterator(int paramInt) {
    return new InsnListIterator(paramInt);
  }
  
  public AbstractInsnNode[] toArray() {
    byte b = 0;
    AbstractInsnNode abstractInsnNode = this.first;
    AbstractInsnNode[] arrayOfAbstractInsnNode = new AbstractInsnNode[this.size];
    while (abstractInsnNode != null) {
      arrayOfAbstractInsnNode[b] = abstractInsnNode;
      abstractInsnNode.index = b++;
      abstractInsnNode = abstractInsnNode.next;
    } 
    return arrayOfAbstractInsnNode;
  }
  
  public void set(AbstractInsnNode paramAbstractInsnNode1, AbstractInsnNode paramAbstractInsnNode2) {
    AbstractInsnNode abstractInsnNode1 = paramAbstractInsnNode1.next;
    paramAbstractInsnNode2.next = abstractInsnNode1;
    if (abstractInsnNode1 != null) {
      abstractInsnNode1.prev = paramAbstractInsnNode2;
    } else {
      this.last = paramAbstractInsnNode2;
    } 
    AbstractInsnNode abstractInsnNode2 = paramAbstractInsnNode1.prev;
    paramAbstractInsnNode2.prev = abstractInsnNode2;
    if (abstractInsnNode2 != null) {
      abstractInsnNode2.next = paramAbstractInsnNode2;
    } else {
      this.first = paramAbstractInsnNode2;
    } 
    if (this.cache != null) {
      int i = paramAbstractInsnNode1.index;
      this.cache[i] = paramAbstractInsnNode2;
      paramAbstractInsnNode2.index = i;
    } else {
      paramAbstractInsnNode2.index = 0;
    } 
    paramAbstractInsnNode1.index = -1;
    paramAbstractInsnNode1.prev = null;
    paramAbstractInsnNode1.next = null;
  }
  
  public void add(AbstractInsnNode paramAbstractInsnNode) {
    this.size++;
    if (this.last == null) {
      this.first = paramAbstractInsnNode;
      this.last = paramAbstractInsnNode;
    } else {
      this.last.next = paramAbstractInsnNode;
      paramAbstractInsnNode.prev = this.last;
    } 
    this.last = paramAbstractInsnNode;
    this.cache = null;
    paramAbstractInsnNode.index = 0;
  }
  
  public void add(InsnList paramInsnList) {
    if (paramInsnList.size == 0)
      return; 
    this.size += paramInsnList.size;
    if (this.last == null) {
      this.first = paramInsnList.first;
      this.last = paramInsnList.last;
    } else {
      AbstractInsnNode abstractInsnNode = paramInsnList.first;
      this.last.next = abstractInsnNode;
      abstractInsnNode.prev = this.last;
      this.last = paramInsnList.last;
    } 
    this.cache = null;
    paramInsnList.removeAll(false);
  }
  
  public void insert(AbstractInsnNode paramAbstractInsnNode) {
    this.size++;
    if (this.first == null) {
      this.first = paramAbstractInsnNode;
      this.last = paramAbstractInsnNode;
    } else {
      this.first.prev = paramAbstractInsnNode;
      paramAbstractInsnNode.next = this.first;
    } 
    this.first = paramAbstractInsnNode;
    this.cache = null;
    paramAbstractInsnNode.index = 0;
  }
  
  public void insert(InsnList paramInsnList) {
    if (paramInsnList.size == 0)
      return; 
    this.size += paramInsnList.size;
    if (this.first == null) {
      this.first = paramInsnList.first;
      this.last = paramInsnList.last;
    } else {
      AbstractInsnNode abstractInsnNode = paramInsnList.last;
      this.first.prev = abstractInsnNode;
      abstractInsnNode.next = this.first;
      this.first = paramInsnList.first;
    } 
    this.cache = null;
    paramInsnList.removeAll(false);
  }
  
  public void insert(AbstractInsnNode paramAbstractInsnNode1, AbstractInsnNode paramAbstractInsnNode2) {
    this.size++;
    AbstractInsnNode abstractInsnNode = paramAbstractInsnNode1.next;
    if (abstractInsnNode == null) {
      this.last = paramAbstractInsnNode2;
    } else {
      abstractInsnNode.prev = paramAbstractInsnNode2;
    } 
    paramAbstractInsnNode1.next = paramAbstractInsnNode2;
    paramAbstractInsnNode2.next = abstractInsnNode;
    paramAbstractInsnNode2.prev = paramAbstractInsnNode1;
    this.cache = null;
    paramAbstractInsnNode2.index = 0;
  }
  
  public void insert(AbstractInsnNode paramAbstractInsnNode, InsnList paramInsnList) {
    if (paramInsnList.size == 0)
      return; 
    this.size += paramInsnList.size;
    AbstractInsnNode abstractInsnNode1 = paramInsnList.first;
    AbstractInsnNode abstractInsnNode2 = paramInsnList.last;
    AbstractInsnNode abstractInsnNode3 = paramAbstractInsnNode.next;
    if (abstractInsnNode3 == null) {
      this.last = abstractInsnNode2;
    } else {
      abstractInsnNode3.prev = abstractInsnNode2;
    } 
    paramAbstractInsnNode.next = abstractInsnNode1;
    abstractInsnNode2.next = abstractInsnNode3;
    abstractInsnNode1.prev = paramAbstractInsnNode;
    this.cache = null;
    paramInsnList.removeAll(false);
  }
  
  public void insertBefore(AbstractInsnNode paramAbstractInsnNode1, AbstractInsnNode paramAbstractInsnNode2) {
    this.size++;
    AbstractInsnNode abstractInsnNode = paramAbstractInsnNode1.prev;
    if (abstractInsnNode == null) {
      this.first = paramAbstractInsnNode2;
    } else {
      abstractInsnNode.next = paramAbstractInsnNode2;
    } 
    paramAbstractInsnNode1.prev = paramAbstractInsnNode2;
    paramAbstractInsnNode2.next = paramAbstractInsnNode1;
    paramAbstractInsnNode2.prev = abstractInsnNode;
    this.cache = null;
    paramAbstractInsnNode2.index = 0;
  }
  
  public void insertBefore(AbstractInsnNode paramAbstractInsnNode, InsnList paramInsnList) {
    if (paramInsnList.size == 0)
      return; 
    this.size += paramInsnList.size;
    AbstractInsnNode abstractInsnNode1 = paramInsnList.first;
    AbstractInsnNode abstractInsnNode2 = paramInsnList.last;
    AbstractInsnNode abstractInsnNode3 = paramAbstractInsnNode.prev;
    if (abstractInsnNode3 == null) {
      this.first = abstractInsnNode1;
    } else {
      abstractInsnNode3.next = abstractInsnNode1;
    } 
    paramAbstractInsnNode.prev = abstractInsnNode2;
    abstractInsnNode2.next = paramAbstractInsnNode;
    abstractInsnNode1.prev = abstractInsnNode3;
    this.cache = null;
    paramInsnList.removeAll(false);
  }
  
  public void remove(AbstractInsnNode paramAbstractInsnNode) {
    this.size--;
    AbstractInsnNode abstractInsnNode1 = paramAbstractInsnNode.next;
    AbstractInsnNode abstractInsnNode2 = paramAbstractInsnNode.prev;
    if (abstractInsnNode1 == null) {
      if (abstractInsnNode2 == null) {
        this.first = null;
        this.last = null;
      } else {
        abstractInsnNode2.next = null;
        this.last = abstractInsnNode2;
      } 
    } else if (abstractInsnNode2 == null) {
      this.first = abstractInsnNode1;
      abstractInsnNode1.prev = null;
    } else {
      abstractInsnNode2.next = abstractInsnNode1;
      abstractInsnNode1.prev = abstractInsnNode2;
    } 
    this.cache = null;
    paramAbstractInsnNode.index = -1;
    paramAbstractInsnNode.prev = null;
    paramAbstractInsnNode.next = null;
  }
  
  void removeAll(boolean paramBoolean) {
    if (paramBoolean) {
      AbstractInsnNode abstractInsnNode = this.first;
      while (abstractInsnNode != null) {
        AbstractInsnNode abstractInsnNode1 = abstractInsnNode.next;
        abstractInsnNode.index = -1;
        abstractInsnNode.prev = null;
        abstractInsnNode.next = null;
        abstractInsnNode = abstractInsnNode1;
      } 
    } 
    this.size = 0;
    this.first = null;
    this.last = null;
    this.cache = null;
  }
  
  public void clear() {
    removeAll(false);
  }
  
  public void resetLabels() {
    AbstractInsnNode abstractInsnNode = this.first;
    while (abstractInsnNode != null) {
      if (abstractInsnNode instanceof LabelNode)
        ((LabelNode)abstractInsnNode).resetLabel(); 
      abstractInsnNode = abstractInsnNode.next;
    } 
  }
  
  private final class InsnListIterator implements ListIterator {
    AbstractInsnNode next;
    
    AbstractInsnNode remove;
    
    AbstractInsnNode prev;
    
    InsnListIterator(int param1Int) {
      if (param1Int == InsnList.this.size()) {
        this.next = null;
        this.prev = InsnList.this.getLast();
      } else {
        this.next = InsnList.this.get(param1Int);
        this.prev = this.next.prev;
      } 
    }
    
    public boolean hasNext() {
      return (this.next != null);
    }
    
    public Object next() {
      if (this.next == null)
        throw new NoSuchElementException(); 
      AbstractInsnNode abstractInsnNode = this.next;
      this.prev = abstractInsnNode;
      this.next = abstractInsnNode.next;
      this.remove = abstractInsnNode;
      return abstractInsnNode;
    }
    
    public void remove() {
      if (this.remove != null) {
        if (this.remove == this.next) {
          this.next = this.next.next;
        } else {
          this.prev = this.prev.prev;
        } 
        InsnList.this.remove(this.remove);
        this.remove = null;
      } else {
        throw new IllegalStateException();
      } 
    }
    
    public boolean hasPrevious() {
      return (this.prev != null);
    }
    
    public Object previous() {
      AbstractInsnNode abstractInsnNode = this.prev;
      this.next = abstractInsnNode;
      this.prev = abstractInsnNode.prev;
      this.remove = abstractInsnNode;
      return abstractInsnNode;
    }
    
    public int nextIndex() {
      if (this.next == null)
        return InsnList.this.size(); 
      if (InsnList.this.cache == null)
        InsnList.this.cache = InsnList.this.toArray(); 
      return this.next.index;
    }
    
    public int previousIndex() {
      if (this.prev == null)
        return -1; 
      if (InsnList.this.cache == null)
        InsnList.this.cache = InsnList.this.toArray(); 
      return this.prev.index;
    }
    
    public void add(Object param1Object) {
      if (this.next != null) {
        InsnList.this.insertBefore(this.next, (AbstractInsnNode)param1Object);
      } else if (this.prev != null) {
        InsnList.this.insert(this.prev, (AbstractInsnNode)param1Object);
      } else {
        InsnList.this.add((AbstractInsnNode)param1Object);
      } 
      this.prev = (AbstractInsnNode)param1Object;
      this.remove = null;
    }
    
    public void set(Object param1Object) {
      if (this.remove != null) {
        InsnList.this.set(this.remove, (AbstractInsnNode)param1Object);
        if (this.remove == this.prev) {
          this.prev = (AbstractInsnNode)param1Object;
        } else {
          this.next = (AbstractInsnNode)param1Object;
        } 
      } else {
        throw new IllegalStateException();
      } 
    }
  }
}
