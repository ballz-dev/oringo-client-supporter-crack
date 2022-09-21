package org.spongepowered.tools.obfuscation.mirror;

public enum Visibility {
  PROTECTED, PRIVATE, PUBLIC, PACKAGE;
  
  static {
    PROTECTED = new Visibility("PROTECTED", 1);
    PACKAGE = new Visibility("PACKAGE", 2);
    PUBLIC = new Visibility("PUBLIC", 3);
    $VALUES = new Visibility[] { PRIVATE, PROTECTED, PACKAGE, PUBLIC };
  }
}
