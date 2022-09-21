package org.spongepowered.asm.mixin.injection.callback;

public enum LocalCapture {
  PRINT,
  CAPTURE_FAILHARD,
  NO_CAPTURE(false, false),
  CAPTURE_FAILSOFT(false, false),
  CAPTURE_FAILEXCEPTION(false, false);
  
  private final boolean printLocals;
  
  private final boolean captureLocals;
  
  static {
    PRINT = new LocalCapture("PRINT", 1, false, true);
    CAPTURE_FAILSOFT = new LocalCapture("CAPTURE_FAILSOFT", 2);
    CAPTURE_FAILHARD = new LocalCapture("CAPTURE_FAILHARD", 3);
    CAPTURE_FAILEXCEPTION = new LocalCapture("CAPTURE_FAILEXCEPTION", 4);
    $VALUES = new LocalCapture[] { NO_CAPTURE, PRINT, CAPTURE_FAILSOFT, CAPTURE_FAILHARD, CAPTURE_FAILEXCEPTION };
  }
  
  LocalCapture(boolean paramBoolean1, boolean paramBoolean2) {
    this.captureLocals = paramBoolean1;
    this.printLocals = paramBoolean2;
  }
  
  boolean isCaptureLocals() {
    return this.captureLocals;
  }
  
  boolean isPrintLocals() {
    return this.printLocals;
  }
}
