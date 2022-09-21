package org.spongepowered.asm.mixin.injection.code;

import com.google.common.base.Strings;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.lib.tree.AbstractInsnNode;
import org.spongepowered.asm.lib.tree.AnnotationNode;
import org.spongepowered.asm.lib.tree.InsnList;
import org.spongepowered.asm.lib.tree.MethodNode;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.InjectionPoint;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.throwables.InjectionError;
import org.spongepowered.asm.mixin.injection.throwables.InvalidSliceException;
import org.spongepowered.asm.util.Annotations;
import org.spongepowered.asm.util.Bytecode;

public final class MethodSlice {
  private final InjectionPoint from;
  
  private final InjectionPoint to;
  
  private final String id;
  
  private final String name;
  
  static final class InsnListSlice extends ReadOnlyInsnList {
    private final int start;
    
    private final int end;
    
    static class SliceIterator implements ListIterator<AbstractInsnNode> {
      private int start;
      
      private final ListIterator<AbstractInsnNode> iter;
      
      private int index;
      
      private int end;
      
      public SliceIterator(ListIterator<AbstractInsnNode> param2ListIterator, int param2Int1, int param2Int2, int param2Int3) {
        this.iter = param2ListIterator;
        this.start = param2Int1;
        this.end = param2Int2;
        this.index = param2Int3;
      }
      
      public boolean hasNext() {
        return (this.index <= this.end && this.iter.hasNext());
      }
      
      public AbstractInsnNode next() {
        if (this.index > this.end)
          throw new NoSuchElementException(); 
        this.index++;
        return this.iter.next();
      }
      
      public boolean hasPrevious() {
        return (this.index > this.start);
      }
      
      public AbstractInsnNode previous() {
        if (this.index <= this.start)
          throw new NoSuchElementException(); 
        this.index--;
        return this.iter.previous();
      }
      
      public int nextIndex() {
        return this.index - this.start;
      }
      
      public int previousIndex() {
        return this.index - this.start - 1;
      }
      
      public void remove() {
        throw new UnsupportedOperationException("Cannot remove insn from slice");
      }
      
      public void set(AbstractInsnNode param2AbstractInsnNode) {
        throw new UnsupportedOperationException("Cannot set insn using slice");
      }
      
      public void add(AbstractInsnNode param2AbstractInsnNode) {
        throw new UnsupportedOperationException("Cannot add insn using slice");
      }
    }
    
    protected InsnListSlice(InsnList param1InsnList, int param1Int1, int param1Int2) {
      super(param1InsnList);
      this.start = param1Int1;
      this.end = param1Int2;
    }
    
    public ListIterator<AbstractInsnNode> iterator() {
      return iterator(0);
    }
    
    public ListIterator<AbstractInsnNode> iterator(int param1Int) {
      return new SliceIterator(super.iterator(this.start + param1Int), this.start, this.end, this.start + param1Int);
    }
    
    public AbstractInsnNode[] toArray() {
      AbstractInsnNode[] arrayOfAbstractInsnNode1 = super.toArray();
      AbstractInsnNode[] arrayOfAbstractInsnNode2 = new AbstractInsnNode[size()];
      System.arraycopy(arrayOfAbstractInsnNode1, this.start, arrayOfAbstractInsnNode2, 0, arrayOfAbstractInsnNode2.length);
      return arrayOfAbstractInsnNode2;
    }
    
    public int size() {
      return this.end - this.start + 1;
    }
    
    public AbstractInsnNode getFirst() {
      return super.get(this.start);
    }
    
    public AbstractInsnNode getLast() {
      return super.get(this.end);
    }
    
    public AbstractInsnNode get(int param1Int) {
      return super.get(this.start + param1Int);
    }
    
    public boolean contains(AbstractInsnNode param1AbstractInsnNode) {
      for (AbstractInsnNode abstractInsnNode : toArray()) {
        if (abstractInsnNode == param1AbstractInsnNode)
          return true; 
      } 
      return false;
    }
    
    public int indexOf(AbstractInsnNode param1AbstractInsnNode) {
      int i = super.indexOf(param1AbstractInsnNode);
      return (i >= this.start && i <= this.end) ? (i - this.start) : -1;
    }
    
    public int realIndexOf(AbstractInsnNode param1AbstractInsnNode) {
      return super.indexOf(param1AbstractInsnNode);
    }
  }
  
  private static final Logger logger = LogManager.getLogger("mixin");
  
  private final ISliceContext owner;
  
  private MethodSlice(ISliceContext paramISliceContext, String paramString, InjectionPoint paramInjectionPoint1, InjectionPoint paramInjectionPoint2) {
    if (paramInjectionPoint1 == null && paramInjectionPoint2 == null)
      throw new InvalidSliceException(paramISliceContext, String.format("%s is redundant. No 'from' or 'to' value specified", new Object[] { this })); 
    this.owner = paramISliceContext;
    this.id = Strings.nullToEmpty(paramString);
    this.from = paramInjectionPoint1;
    this.to = paramInjectionPoint2;
    this.name = getSliceName(paramString);
  }
  
  public String getId() {
    return this.id;
  }
  
  public ReadOnlyInsnList getSlice(MethodNode paramMethodNode) {
    int i = paramMethodNode.instructions.size() - 1;
    int j = find(paramMethodNode, this.from, 0, 0, this.name + "(from)");
    int k = find(paramMethodNode, this.to, i, j, this.name + "(to)");
    if (j > k)
      throw new InvalidSliceException(this.owner, String.format("%s is negative size. Range(%d -> %d)", new Object[] { describe(), Integer.valueOf(j), Integer.valueOf(k) })); 
    if (j < 0 || k < 0 || j > i || k > i)
      throw new InjectionError("Unexpected critical error in " + this + ": out of bounds start=" + j + " end=" + k + " lim=" + i); 
    if (j == 0 && k == i)
      return new ReadOnlyInsnList(paramMethodNode.instructions); 
    return new InsnListSlice(paramMethodNode.instructions, j, k);
  }
  
  private int find(MethodNode paramMethodNode, InjectionPoint paramInjectionPoint, int paramInt1, int paramInt2, String paramString) {
    if (paramInjectionPoint == null)
      return paramInt1; 
    LinkedList<AbstractInsnNode> linkedList = new LinkedList();
    ReadOnlyInsnList readOnlyInsnList = new ReadOnlyInsnList(paramMethodNode.instructions);
    boolean bool = paramInjectionPoint.find(paramMethodNode.desc, readOnlyInsnList, linkedList);
    InjectionPoint.Selector selector = paramInjectionPoint.getSelector();
    if (linkedList.size() != 1 && selector == InjectionPoint.Selector.ONE)
      throw new InvalidSliceException(this.owner, String.format("%s requires 1 result but found %d", new Object[] { describe(paramString), Integer.valueOf(linkedList.size()) })); 
    if (!bool) {
      if (this.owner.getContext().getOption(MixinEnvironment.Option.DEBUG_VERBOSE))
        logger.warn("{} did not match any instructions", new Object[] { describe(paramString) }); 
      return paramInt2;
    } 
    return paramMethodNode.instructions.indexOf((selector == InjectionPoint.Selector.FIRST) ? linkedList.getFirst() : linkedList.getLast());
  }
  
  public String toString() {
    return describe();
  }
  
  private String describe() {
    return describe(this.name);
  }
  
  private String describe(String paramString) {
    return describeSlice(paramString, this.owner);
  }
  
  private static String describeSlice(String paramString, ISliceContext paramISliceContext) {
    String str = Bytecode.getSimpleName(paramISliceContext.getAnnotation());
    MethodNode methodNode = paramISliceContext.getMethod();
    return String.format("%s->%s(%s)::%s%s", new Object[] { paramISliceContext.getContext(), str, paramString, methodNode.name, methodNode.desc });
  }
  
  private static String getSliceName(String paramString) {
    return String.format("@Slice[%s]", new Object[] { Strings.nullToEmpty(paramString) });
  }
  
  public static MethodSlice parse(ISliceContext paramISliceContext, Slice paramSlice) {
    String str = paramSlice.id();
    At at1 = paramSlice.from();
    At at2 = paramSlice.to();
    InjectionPoint injectionPoint1 = (at1 != null) ? InjectionPoint.parse(paramISliceContext, at1) : null;
    InjectionPoint injectionPoint2 = (at2 != null) ? InjectionPoint.parse(paramISliceContext, at2) : null;
    return new MethodSlice(paramISliceContext, str, injectionPoint1, injectionPoint2);
  }
  
  public static MethodSlice parse(ISliceContext paramISliceContext, AnnotationNode paramAnnotationNode) {
    String str = (String)Annotations.getValue(paramAnnotationNode, "id");
    AnnotationNode annotationNode1 = (AnnotationNode)Annotations.getValue(paramAnnotationNode, "from");
    AnnotationNode annotationNode2 = (AnnotationNode)Annotations.getValue(paramAnnotationNode, "to");
    InjectionPoint injectionPoint1 = (annotationNode1 != null) ? InjectionPoint.parse(paramISliceContext, annotationNode1) : null;
    InjectionPoint injectionPoint2 = (annotationNode2 != null) ? InjectionPoint.parse(paramISliceContext, annotationNode2) : null;
    return new MethodSlice(paramISliceContext, str, injectionPoint1, injectionPoint2);
  }
}
