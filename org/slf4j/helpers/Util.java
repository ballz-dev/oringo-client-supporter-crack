package org.slf4j.helpers;

public final class Util {
  private static ClassContextSecurityManager SECURITY_MANAGER;
  
  public static String safeGetSystemProperty(String paramString) {
    if (paramString == null)
      throw new IllegalArgumentException("null input"); 
    String str = null;
    try {
      str = System.getProperty(paramString);
    } catch (SecurityException securityException) {}
    return str;
  }
  
  public static boolean safeGetBooleanSystemProperty(String paramString) {
    String str = safeGetSystemProperty(paramString);
    if (str == null)
      return false; 
    return str.equalsIgnoreCase("true");
  }
  
  private static final class ClassContextSecurityManager extends SecurityManager {
    private ClassContextSecurityManager() {}
    
    protected Class<?>[] getClassContext() {
      return super.getClassContext();
    }
  }
  
  private static boolean SECURITY_MANAGER_CREATION_ALREADY_ATTEMPTED = false;
  
  private static ClassContextSecurityManager getSecurityManager() {
    if (SECURITY_MANAGER != null)
      return SECURITY_MANAGER; 
    if (SECURITY_MANAGER_CREATION_ALREADY_ATTEMPTED)
      return null; 
    SECURITY_MANAGER = safeCreateSecurityManager();
    SECURITY_MANAGER_CREATION_ALREADY_ATTEMPTED = true;
    return SECURITY_MANAGER;
  }
  
  private static ClassContextSecurityManager safeCreateSecurityManager() {
    try {
      return new ClassContextSecurityManager();
    } catch (SecurityException securityException) {
      return null;
    } 
  }
  
  public static Class<?> getCallingClass() {
    ClassContextSecurityManager classContextSecurityManager = getSecurityManager();
    if (classContextSecurityManager == null)
      return null; 
    Class[] arrayOfClass = classContextSecurityManager.getClassContext();
    String str = Util.class.getName();
    byte b;
    for (b = 0; b < arrayOfClass.length && 
      !str.equals(arrayOfClass[b].getName()); b++);
    if (b >= arrayOfClass.length || b + 2 >= arrayOfClass.length)
      throw new IllegalStateException("Failed to find org.slf4j.helpers.Util or its caller in the stack; this should not happen"); 
    return arrayOfClass[b + 2];
  }
  
  public static final void report(String paramString, Throwable paramThrowable) {
    System.err.println(paramString);
    System.err.println("Reported exception:");
    paramThrowable.printStackTrace();
  }
  
  public static final void report(String paramString) {
    System.err.println("SLF4J: " + paramString);
  }
}
