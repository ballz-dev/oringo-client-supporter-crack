package org.spongepowered.asm.lib;

class Handler {
  Handler next;
  
  Label handler;
  
  String desc;
  
  Label start;
  
  int type;
  
  Label end;
  
  static Handler remove(Handler paramHandler, Label paramLabel1, Label paramLabel2) {
    if (paramHandler == null)
      return null; 
    paramHandler.next = remove(paramHandler.next, paramLabel1, paramLabel2);
    int i = paramHandler.start.position;
    int j = paramHandler.end.position;
    int k = paramLabel1.position;
    int m = (paramLabel2 == null) ? Integer.MAX_VALUE : paramLabel2.position;
    if (k < j && m > i)
      if (k <= i) {
        if (m >= j) {
          paramHandler = paramHandler.next;
        } else {
          paramHandler.start = paramLabel2;
        } 
      } else if (m >= j) {
        paramHandler.end = paramLabel1;
      } else {
        Handler handler = new Handler();
        handler.start = paramLabel2;
        handler.end = paramHandler.end;
        handler.handler = paramHandler.handler;
        handler.desc = paramHandler.desc;
        handler.type = paramHandler.type;
        handler.next = paramHandler.next;
        paramHandler.end = paramLabel1;
        paramHandler.next = handler;
      }  
    return paramHandler;
  }
}
