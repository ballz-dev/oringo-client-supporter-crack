package org.spongepowered.asm.lib;

class Context {
  int[] index;
  
  String name;
  
  Label[] end;
  
  Label[] start;
  
  int access;
  
  int typeRef;
  
  int stackCount;
  
  Object[] local;
  
  String desc;
  
  int[] bootstrapMethods;
  
  Object[] stack;
  
  Label[] labels;
  
  Attribute[] attrs;
  
  int flags;
  
  int localDiff;
  
  int mode;
  
  int localCount;
  
  char[] buffer;
  
  int offset;
  
  TypePath typePath;
}
