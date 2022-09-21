package org.spongepowered.asm.lib;

class Edge {
  Label successor;
  
  static final int NORMAL = 0;
  
  Edge next;
  
  int info;
  
  static final int EXCEPTION = 2147483647;
}
