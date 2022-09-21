package org.spongepowered.asm.lib.tree.analysis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.spongepowered.asm.lib.Opcodes;
import org.spongepowered.asm.lib.Type;
import org.spongepowered.asm.lib.tree.AbstractInsnNode;
import org.spongepowered.asm.lib.tree.IincInsnNode;
import org.spongepowered.asm.lib.tree.InsnList;
import org.spongepowered.asm.lib.tree.JumpInsnNode;
import org.spongepowered.asm.lib.tree.LabelNode;
import org.spongepowered.asm.lib.tree.LookupSwitchInsnNode;
import org.spongepowered.asm.lib.tree.MethodNode;
import org.spongepowered.asm.lib.tree.TableSwitchInsnNode;
import org.spongepowered.asm.lib.tree.TryCatchBlockNode;
import org.spongepowered.asm.lib.tree.VarInsnNode;

public class Analyzer<V extends Value> implements Opcodes {
  private int top;
  
  private Subroutine[] subroutines;
  
  private List<TryCatchBlockNode>[] handlers;
  
  private boolean[] queued;
  
  private Frame<V>[] frames;
  
  private int n;
  
  private InsnList insns;
  
  private int[] queue;
  
  private final Interpreter<V> interpreter;
  
  public Analyzer(Interpreter<V> paramInterpreter) {
    this.interpreter = paramInterpreter;
  }
  
  public Frame<V>[] analyze(String paramString, MethodNode paramMethodNode) throws AnalyzerException {
    if ((paramMethodNode.access & 0x500) != 0) {
      this.frames = (Frame<V>[])new Frame[0];
      return this.frames;
    } 
    this.n = paramMethodNode.instructions.size();
    this.insns = paramMethodNode.instructions;
    this.handlers = (List<TryCatchBlockNode>[])new List[this.n];
    this.frames = (Frame<V>[])new Frame[this.n];
    this.subroutines = new Subroutine[this.n];
    this.queued = new boolean[this.n];
    this.queue = new int[this.n];
    this.top = 0;
    for (byte b1 = 0; b1 < paramMethodNode.tryCatchBlocks.size(); b1++) {
      TryCatchBlockNode tryCatchBlockNode = paramMethodNode.tryCatchBlocks.get(b1);
      int j = this.insns.indexOf((AbstractInsnNode)tryCatchBlockNode.start);
      int k = this.insns.indexOf((AbstractInsnNode)tryCatchBlockNode.end);
      for (int m = j; m < k; m++) {
        List<TryCatchBlockNode> list = this.handlers[m];
        if (list == null) {
          list = new ArrayList<TryCatchBlockNode>();
          this.handlers[m] = list;
        } 
        list.add(tryCatchBlockNode);
      } 
    } 
    Subroutine subroutine = new Subroutine(null, paramMethodNode.maxLocals, null);
    ArrayList<AbstractInsnNode> arrayList = new ArrayList();
    HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
    findSubroutine(0, subroutine, arrayList);
    while (!arrayList.isEmpty()) {
      JumpInsnNode jumpInsnNode = (JumpInsnNode)arrayList.remove(0);
      Subroutine subroutine1 = (Subroutine)hashMap.get(jumpInsnNode.label);
      if (subroutine1 == null) {
        subroutine1 = new Subroutine(jumpInsnNode.label, paramMethodNode.maxLocals, jumpInsnNode);
        hashMap.put(jumpInsnNode.label, subroutine1);
        findSubroutine(this.insns.indexOf((AbstractInsnNode)jumpInsnNode.label), subroutine1, arrayList);
        continue;
      } 
      subroutine1.callers.add(jumpInsnNode);
    } 
    for (byte b2 = 0; b2 < this.n; b2++) {
      if (this.subroutines[b2] != null && (this.subroutines[b2]).start == null)
        this.subroutines[b2] = null; 
    } 
    Frame<V> frame1 = newFrame(paramMethodNode.maxLocals, paramMethodNode.maxStack);
    Frame<V> frame2 = newFrame(paramMethodNode.maxLocals, paramMethodNode.maxStack);
    frame1.setReturn(this.interpreter.newValue(Type.getReturnType(paramMethodNode.desc)));
    Type[] arrayOfType = Type.getArgumentTypes(paramMethodNode.desc);
    byte b3 = 0;
    if ((paramMethodNode.access & 0x8) == 0) {
      Type type = Type.getObjectType(paramString);
      frame1.setLocal(b3++, this.interpreter.newValue(type));
    } 
    int i;
    for (i = 0; i < arrayOfType.length; i++) {
      frame1.setLocal(b3++, this.interpreter.newValue(arrayOfType[i]));
      if (arrayOfType[i].getSize() == 2)
        frame1.setLocal(b3++, this.interpreter.newValue(null)); 
    } 
    while (b3 < paramMethodNode.maxLocals)
      frame1.setLocal(b3++, this.interpreter.newValue(null)); 
    merge(0, frame1, null);
    init(paramString, paramMethodNode);
    while (this.top > 0) {
      i = this.queue[--this.top];
      Frame<V> frame = this.frames[i];
      Subroutine subroutine1 = this.subroutines[i];
      this.queued[i] = false;
      AbstractInsnNode abstractInsnNode = null;
      try {
        abstractInsnNode = paramMethodNode.instructions.get(i);
        int j = abstractInsnNode.getOpcode();
        int k = abstractInsnNode.getType();
        if (k == 8 || k == 15 || k == 14) {
          merge(i + 1, frame, subroutine1);
          newControlFlowEdge(i, i + 1);
        } else {
          frame1.init(frame).execute(abstractInsnNode, this.interpreter);
          subroutine1 = (subroutine1 == null) ? null : subroutine1.copy();
          if (abstractInsnNode instanceof JumpInsnNode) {
            JumpInsnNode jumpInsnNode = (JumpInsnNode)abstractInsnNode;
            if (j != 167 && j != 168) {
              merge(i + 1, frame1, subroutine1);
              newControlFlowEdge(i, i + 1);
            } 
            int m = this.insns.indexOf((AbstractInsnNode)jumpInsnNode.label);
            if (j == 168) {
              merge(m, frame1, new Subroutine(jumpInsnNode.label, paramMethodNode.maxLocals, jumpInsnNode));
            } else {
              merge(m, frame1, subroutine1);
            } 
            newControlFlowEdge(i, m);
          } else if (abstractInsnNode instanceof LookupSwitchInsnNode) {
            LookupSwitchInsnNode lookupSwitchInsnNode = (LookupSwitchInsnNode)abstractInsnNode;
            int m = this.insns.indexOf((AbstractInsnNode)lookupSwitchInsnNode.dflt);
            merge(m, frame1, subroutine1);
            newControlFlowEdge(i, m);
            for (byte b = 0; b < lookupSwitchInsnNode.labels.size(); b++) {
              LabelNode labelNode = lookupSwitchInsnNode.labels.get(b);
              m = this.insns.indexOf((AbstractInsnNode)labelNode);
              merge(m, frame1, subroutine1);
              newControlFlowEdge(i, m);
            } 
          } else if (abstractInsnNode instanceof TableSwitchInsnNode) {
            TableSwitchInsnNode tableSwitchInsnNode = (TableSwitchInsnNode)abstractInsnNode;
            int m = this.insns.indexOf((AbstractInsnNode)tableSwitchInsnNode.dflt);
            merge(m, frame1, subroutine1);
            newControlFlowEdge(i, m);
            for (byte b = 0; b < tableSwitchInsnNode.labels.size(); b++) {
              LabelNode labelNode = tableSwitchInsnNode.labels.get(b);
              m = this.insns.indexOf((AbstractInsnNode)labelNode);
              merge(m, frame1, subroutine1);
              newControlFlowEdge(i, m);
            } 
          } else if (j == 169) {
            if (subroutine1 == null)
              throw new AnalyzerException(abstractInsnNode, "RET instruction outside of a sub routine"); 
            for (byte b = 0; b < subroutine1.callers.size(); b++) {
              JumpInsnNode jumpInsnNode = subroutine1.callers.get(b);
              int m = this.insns.indexOf((AbstractInsnNode)jumpInsnNode);
              if (this.frames[m] != null) {
                merge(m + 1, this.frames[m], frame1, this.subroutines[m], subroutine1.access);
                newControlFlowEdge(i, m + 1);
              } 
            } 
          } else if (j != 191 && (j < 172 || j > 177)) {
            if (subroutine1 != null)
              if (abstractInsnNode instanceof VarInsnNode) {
                int m = ((VarInsnNode)abstractInsnNode).var;
                subroutine1.access[m] = true;
                if (j == 22 || j == 24 || j == 55 || j == 57)
                  subroutine1.access[m + 1] = true; 
              } else if (abstractInsnNode instanceof IincInsnNode) {
                int m = ((IincInsnNode)abstractInsnNode).var;
                subroutine1.access[m] = true;
              }  
            merge(i + 1, frame1, subroutine1);
            newControlFlowEdge(i, i + 1);
          } 
        } 
        List<TryCatchBlockNode> list = this.handlers[i];
        if (list != null)
          for (byte b = 0; b < list.size(); b++) {
            Type type;
            TryCatchBlockNode tryCatchBlockNode = list.get(b);
            if (tryCatchBlockNode.type == null) {
              type = Type.getObjectType("java/lang/Throwable");
            } else {
              type = Type.getObjectType(tryCatchBlockNode.type);
            } 
            int m = this.insns.indexOf((AbstractInsnNode)tryCatchBlockNode.handler);
            if (newControlFlowExceptionEdge(i, tryCatchBlockNode)) {
              frame2.init(frame);
              frame2.clearStack();
              frame2.push(this.interpreter.newValue(type));
              merge(m, frame2, subroutine1);
            } 
          }  
      } catch (AnalyzerException analyzerException) {
        throw new AnalyzerException(analyzerException.node, "Error at instruction " + i + ": " + analyzerException
            .getMessage(), analyzerException);
      } catch (Exception exception) {
        throw new AnalyzerException(abstractInsnNode, "Error at instruction " + i + ": " + exception
            .getMessage(), exception);
      } 
    } 
    return this.frames;
  }
  
  private void findSubroutine(int paramInt, Subroutine paramSubroutine, List<AbstractInsnNode> paramList) throws AnalyzerException {
    while (true) {
      if (paramInt < 0 || paramInt >= this.n)
        throw new AnalyzerException(null, "Execution can fall off end of the code"); 
      if (this.subroutines[paramInt] != null)
        return; 
      this.subroutines[paramInt] = paramSubroutine.copy();
      AbstractInsnNode abstractInsnNode = this.insns.get(paramInt);
      if (abstractInsnNode instanceof JumpInsnNode) {
        if (abstractInsnNode.getOpcode() == 168) {
          paramList.add(abstractInsnNode);
        } else {
          JumpInsnNode jumpInsnNode = (JumpInsnNode)abstractInsnNode;
          findSubroutine(this.insns.indexOf((AbstractInsnNode)jumpInsnNode.label), paramSubroutine, paramList);
        } 
      } else if (abstractInsnNode instanceof TableSwitchInsnNode) {
        TableSwitchInsnNode tableSwitchInsnNode = (TableSwitchInsnNode)abstractInsnNode;
        findSubroutine(this.insns.indexOf((AbstractInsnNode)tableSwitchInsnNode.dflt), paramSubroutine, paramList);
        for (int i = tableSwitchInsnNode.labels.size() - 1; i >= 0; i--) {
          LabelNode labelNode = tableSwitchInsnNode.labels.get(i);
          findSubroutine(this.insns.indexOf((AbstractInsnNode)labelNode), paramSubroutine, paramList);
        } 
      } else if (abstractInsnNode instanceof LookupSwitchInsnNode) {
        LookupSwitchInsnNode lookupSwitchInsnNode = (LookupSwitchInsnNode)abstractInsnNode;
        findSubroutine(this.insns.indexOf((AbstractInsnNode)lookupSwitchInsnNode.dflt), paramSubroutine, paramList);
        for (int i = lookupSwitchInsnNode.labels.size() - 1; i >= 0; i--) {
          LabelNode labelNode = lookupSwitchInsnNode.labels.get(i);
          findSubroutine(this.insns.indexOf((AbstractInsnNode)labelNode), paramSubroutine, paramList);
        } 
      } 
      List<TryCatchBlockNode> list = this.handlers[paramInt];
      if (list != null)
        for (byte b = 0; b < list.size(); b++) {
          TryCatchBlockNode tryCatchBlockNode = list.get(b);
          findSubroutine(this.insns.indexOf((AbstractInsnNode)tryCatchBlockNode.handler), paramSubroutine, paramList);
        }  
      switch (abstractInsnNode.getOpcode()) {
        case 167:
        case 169:
        case 170:
        case 171:
        case 172:
        case 173:
        case 174:
        case 175:
        case 176:
        case 177:
        case 191:
          return;
      } 
      paramInt++;
    } 
  }
  
  public Frame<V>[] getFrames() {
    return this.frames;
  }
  
  public List<TryCatchBlockNode> getHandlers(int paramInt) {
    return this.handlers[paramInt];
  }
  
  protected void init(String paramString, MethodNode paramMethodNode) throws AnalyzerException {}
  
  protected Frame<V> newFrame(int paramInt1, int paramInt2) {
    return new Frame<V>(paramInt1, paramInt2);
  }
  
  protected Frame<V> newFrame(Frame<? extends V> paramFrame) {
    return new Frame<V>(paramFrame);
  }
  
  protected void newControlFlowEdge(int paramInt1, int paramInt2) {}
  
  protected boolean newControlFlowExceptionEdge(int paramInt1, int paramInt2) {
    return true;
  }
  
  protected boolean newControlFlowExceptionEdge(int paramInt, TryCatchBlockNode paramTryCatchBlockNode) {
    return newControlFlowExceptionEdge(paramInt, this.insns.indexOf((AbstractInsnNode)paramTryCatchBlockNode.handler));
  }
  
  private void merge(int paramInt, Frame<V> paramFrame, Subroutine paramSubroutine) throws AnalyzerException {
    boolean bool;
    Frame<V> frame = this.frames[paramInt];
    Subroutine subroutine = this.subroutines[paramInt];
    if (frame == null) {
      this.frames[paramInt] = newFrame(paramFrame);
      bool = true;
    } else {
      bool = frame.merge(paramFrame, this.interpreter);
    } 
    if (subroutine == null) {
      if (paramSubroutine != null) {
        this.subroutines[paramInt] = paramSubroutine.copy();
        bool = true;
      } 
    } else if (paramSubroutine != null) {
      bool |= subroutine.merge(paramSubroutine);
    } 
    if (bool && !this.queued[paramInt]) {
      this.queued[paramInt] = true;
      this.queue[this.top++] = paramInt;
    } 
  }
  
  private void merge(int paramInt, Frame<V> paramFrame1, Frame<V> paramFrame2, Subroutine paramSubroutine, boolean[] paramArrayOfboolean) throws AnalyzerException {
    boolean bool;
    Frame<V> frame = this.frames[paramInt];
    Subroutine subroutine = this.subroutines[paramInt];
    paramFrame2.merge(paramFrame1, paramArrayOfboolean);
    if (frame == null) {
      this.frames[paramInt] = newFrame(paramFrame2);
      bool = true;
    } else {
      bool = frame.merge(paramFrame2, this.interpreter);
    } 
    if (subroutine != null && paramSubroutine != null)
      bool |= subroutine.merge(paramSubroutine); 
    if (bool && !this.queued[paramInt]) {
      this.queued[paramInt] = true;
      this.queue[this.top++] = paramInt;
    } 
  }
}
