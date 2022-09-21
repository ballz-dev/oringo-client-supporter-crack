package org.spongepowered.asm.lib.tree.analysis;

import java.util.AbstractSet;
import java.util.HashSet;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;

class SmallSet<E> extends AbstractSet<E> implements Iterator<E> {
  E e1;
  
  E e2;
  
  static final <T> Set<T> emptySet() {
    return new SmallSet<T>(null, null);
  }
  
  SmallSet(E paramE1, E paramE2) {
    this.e1 = paramE1;
    this.e2 = paramE2;
  }
  
  public Iterator<E> iterator() {
    return new SmallSet(this.e1, this.e2);
  }
  
  public int size() {
    return (this.e1 == null) ? 0 : ((this.e2 == null) ? 1 : 2);
  }
  
  public boolean hasNext() {
    return (this.e1 != null);
  }
  
  public E next() {
    if (this.e1 == null)
      throw new NoSuchElementException(); 
    E e = this.e1;
    this.e1 = this.e2;
    this.e2 = null;
    return e;
  }
  
  public void remove() {}
  
  Set<E> union(SmallSet<E> paramSmallSet) {
    if ((paramSmallSet.e1 == this.e1 && paramSmallSet.e2 == this.e2) || (paramSmallSet.e1 == this.e2 && paramSmallSet.e2 == this.e1))
      return this; 
    if (paramSmallSet.e1 == null)
      return this; 
    if (this.e1 == null)
      return paramSmallSet; 
    if (paramSmallSet.e2 == null) {
      if (this.e2 == null)
        return new SmallSet(this.e1, paramSmallSet.e1); 
      if (paramSmallSet.e1 == this.e1 || paramSmallSet.e1 == this.e2)
        return this; 
    } 
    if (this.e2 == null)
      if (this.e1 == paramSmallSet.e1 || this.e1 == paramSmallSet.e2)
        return paramSmallSet;  
    HashSet<E> hashSet = new HashSet(4);
    hashSet.add(this.e1);
    if (this.e2 != null)
      hashSet.add(this.e2); 
    hashSet.add(paramSmallSet.e1);
    if (paramSmallSet.e2 != null)
      hashSet.add(paramSmallSet.e2); 
    return hashSet;
  }
}
