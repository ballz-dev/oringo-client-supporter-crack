package org.spongepowered.asm.lib.util;

import java.util.Map;
import org.spongepowered.asm.lib.Label;

public interface ASMifiable {
  void asmify(StringBuffer paramStringBuffer, String paramString, Map<Label, String> paramMap);
}
