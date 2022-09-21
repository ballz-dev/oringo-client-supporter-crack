package org.scijava.nativelib;

import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MxSysInfo {
  public static String getMxSysInfo() {
    String str = System.getProperty("mx.sysinfo");
    if (str != null)
      return str; 
    return guessMxSysInfo();
  }
  
  public static String guessMxSysInfo() {
    String str1 = System.getProperty("os.arch");
    String str2 = System.getProperty("os.name");
    String str3 = "unknown";
    if ("Linux".equals(str2))
      try {
        String str6, str4 = (new File("/lib/libc.so.6")).getCanonicalPath();
        Matcher matcher1 = Pattern.compile(".*/libc-(\\d+)\\.(\\d+)\\..*").matcher(str4);
        if (!matcher1.matches())
          throw new IOException("libc symlink contains unexpected destination: " + str4); 
        File file = new File("/usr/lib/libstdc++.so.6");
        if (!file.exists())
          file = new File("/usr/lib/libstdc++.so.5"); 
        String str5 = file.getCanonicalPath();
        Matcher matcher2 = Pattern.compile(".*/libstdc\\+\\+\\.so\\.(\\d+)\\.0\\.(\\d+)").matcher(str5);
        if (!matcher2.matches())
          throw new IOException("libstdc++ symlink contains unexpected destination: " + str5); 
        if ("5".equals(matcher2.group(1))) {
          str6 = "5";
        } else if ("6".equals(matcher2.group(1))) {
          int i = Integer.parseInt(matcher2.group(2));
          if (i < 9) {
            str6 = "6";
          } else {
            str6 = "6" + matcher2.group(2);
          } 
        } else {
          str6 = matcher2.group(1) + matcher2.group(2);
        } 
        str3 = "c" + matcher1.group(1) + matcher1.group(2) + "cxx" + str6;
      } catch (IOException iOException) {
        str3 = "unknown";
      } finally {} 
    return str1 + "-" + str2 + "-" + str3;
  }
}
