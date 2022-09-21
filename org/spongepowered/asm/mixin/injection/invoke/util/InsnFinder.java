package org.spongepowered.asm.mixin.injection.invoke.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.lib.tree.AbstractInsnNode;
import org.spongepowered.asm.lib.tree.analysis.Analyzer;
import org.spongepowered.asm.lib.tree.analysis.AnalyzerException;
import org.spongepowered.asm.lib.tree.analysis.BasicInterpreter;
import org.spongepowered.asm.lib.tree.analysis.BasicValue;
import org.spongepowered.asm.lib.tree.analysis.Frame;
import org.spongepowered.asm.lib.tree.analysis.Interpreter;
import org.spongepowered.asm.lib.tree.analysis.Value;
import org.spongepowered.asm.mixin.injection.struct.Target;

public class InsnFinder {
  static class AnalysisResultException extends RuntimeException {
    private AbstractInsnNode result;
    
    private static final long serialVersionUID = 1L;
    
    public AnalysisResultException(AbstractInsnNode param1AbstractInsnNode) {
      this.result = param1AbstractInsnNode;
    }
    
    public AbstractInsnNode getResult() {
      return this.result;
    }
  }
  
  enum AnalyzerState {
    SEARCH, ANALYSE, COMPLETE;
  }
  
  static class PopAnalyzer extends Analyzer<BasicValue> {
    protected final AbstractInsnNode node;
    
    class PopFrame extends Frame<BasicValue> {
      private InsnFinder.AnalyzerState state = InsnFinder.AnalyzerState.SEARCH;
      
      private int depth = 0;
      
      private AbstractInsnNode current;
      
      public PopFrame(int param2Int1, int param2Int2) {
        super(param2Int1, param2Int2);
      }
      
      public void execute(AbstractInsnNode param2AbstractInsnNode, Interpreter<BasicValue> param2Interpreter) throws AnalyzerException {
        this.current = param2AbstractInsnNode;
        super.execute(param2AbstractInsnNode, param2Interpreter);
      }
      
      public void push(BasicValue param2BasicValue) throws IndexOutOfBoundsException {
        if (this.current == InsnFinder.PopAnalyzer.this.node && this.state == InsnFinder.AnalyzerState.SEARCH) {
          this.state = InsnFinder.AnalyzerState.ANALYSE;
          this.depth++;
        } else if (this.state == InsnFinder.AnalyzerState.ANALYSE) {
          this.depth++;
        } 
        super.push((Value)param2BasicValue);
      }
      
      public BasicValue pop() throws IndexOutOfBoundsException {
        if (this.state == InsnFinder.AnalyzerState.ANALYSE && 
          --this.depth == 0) {
          this.state = InsnFinder.AnalyzerState.COMPLETE;
          throw new InsnFinder.AnalysisResultException(this.current);
        } 
        return (BasicValue)super.pop();
      }
    }
    
    public PopAnalyzer(AbstractInsnNode param1AbstractInsnNode) {
      super((Interpreter)new BasicInterpreter());
      this.node = param1AbstractInsnNode;
    }
    
    protected Frame<BasicValue> newFrame(int param1Int1, int param1Int2) {
      return new PopFrame(param1Int1, param1Int2);
    }
  }
  
  private static final Logger logger = LogManager.getLogger("mixin");
  
  public AbstractInsnNode findPopInsn(Target paramTarget, AbstractInsnNode paramAbstractInsnNode) {
    try {
      (new PopAnalyzer(paramAbstractInsnNode)).analyze(paramTarget.classNode.name, paramTarget.method);
    } catch (AnalyzerException analyzerException) {
      if (analyzerException.getCause() instanceof AnalysisResultException)
        return ((AnalysisResultException)analyzerException.getCause()).getResult(); 
      logger.catching((Throwable)analyzerException);
    } 
    return null;
  }
}
