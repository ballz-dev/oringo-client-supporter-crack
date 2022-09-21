package org.slf4j;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;
import org.slf4j.event.LoggingEvent;
import org.slf4j.event.SubstituteLoggingEvent;
import org.slf4j.helpers.NOPLoggerFactory;
import org.slf4j.helpers.SubstituteLogger;
import org.slf4j.helpers.SubstituteLoggerFactory;
import org.slf4j.helpers.Util;
import org.slf4j.impl.StaticLoggerBinder;

public final class LoggerFactory {
  static final String MULTIPLE_BINDINGS_URL = "http://www.slf4j.org/codes.html#multiple_bindings";
  
  static final String JAVA_VENDOR_PROPERTY = "java.vendor.url";
  
  static final int UNINITIALIZED = 0;
  
  private static String STATIC_LOGGER_BINDER_PATH;
  
  static final String CODES_PREFIX = "http://www.slf4j.org/codes.html";
  
  static final String REPLAY_URL = "http://www.slf4j.org/codes.html#replay";
  
  static final String NO_STATICLOGGERBINDER_URL = "http://www.slf4j.org/codes.html#StaticLoggerBinder";
  
  static final SubstituteLoggerFactory SUBST_FACTORY;
  
  static final NOPLoggerFactory NOP_FALLBACK_FACTORY;
  
  static final String LOGGER_NAME_MISMATCH_URL = "http://www.slf4j.org/codes.html#loggerNameMismatch";
  
  static final int ONGOING_INITIALIZATION = 1;
  
  static final String UNSUCCESSFUL_INIT_URL = "http://www.slf4j.org/codes.html#unsuccessfulInit";
  
  static final String UNSUCCESSFUL_INIT_MSG = "org.slf4j.LoggerFactory in failed state. Original exception was thrown EARLIER. See also http://www.slf4j.org/codes.html#unsuccessfulInit";
  
  static final int FAILED_INITIALIZATION = 2;
  
  static final String VERSION_MISMATCH = "http://www.slf4j.org/codes.html#version_mismatch";
  
  static final String DETECT_LOGGER_NAME_MISMATCH_PROPERTY = "slf4j.detectLoggerNameMismatch";
  
  static boolean DETECT_LOGGER_NAME_MISMATCH;
  
  static final int NOP_FALLBACK_INITIALIZATION = 4;
  
  static final String SUBSTITUTE_LOGGER_URL = "http://www.slf4j.org/codes.html#substituteLogger";
  
  static volatile int INITIALIZATION_STATE = 0;
  
  static final String NULL_LF_URL = "http://www.slf4j.org/codes.html#null_LF";
  
  private static final String[] API_COMPATIBILITY_LIST;
  
  static final int SUCCESSFUL_INITIALIZATION = 3;
  
  static {
    SUBST_FACTORY = new SubstituteLoggerFactory();
    NOP_FALLBACK_FACTORY = new NOPLoggerFactory();
    DETECT_LOGGER_NAME_MISMATCH = Util.safeGetBooleanSystemProperty("slf4j.detectLoggerNameMismatch");
    API_COMPATIBILITY_LIST = new String[] { "1.6", "1.7" };
    STATIC_LOGGER_BINDER_PATH = "org/slf4j/impl/StaticLoggerBinder.class";
  }
  
  static void reset() {
    INITIALIZATION_STATE = 0;
  }
  
  private static final void performInitialization() {
    bind();
    if (INITIALIZATION_STATE == 3)
      versionSanityCheck(); 
  }
  
  private static boolean messageContainsOrgSlf4jImplStaticLoggerBinder(String paramString) {
    if (paramString == null)
      return false; 
    if (paramString.contains("org/slf4j/impl/StaticLoggerBinder"))
      return true; 
    if (paramString.contains("org.slf4j.impl.StaticLoggerBinder"))
      return true; 
    return false;
  }
  
  private static final void bind() {
    try {
      Set<URL> set = null;
      if (!isAndroid()) {
        set = findPossibleStaticLoggerBinderPathSet();
        reportMultipleBindingAmbiguity(set);
      } 
      StaticLoggerBinder.getSingleton();
      INITIALIZATION_STATE = 3;
      reportActualBinding(set);
      fixSubstituteLoggers();
      replayEvents();
      SUBST_FACTORY.clear();
    } catch (NoClassDefFoundError noClassDefFoundError) {
      String str = noClassDefFoundError.getMessage();
      if (messageContainsOrgSlf4jImplStaticLoggerBinder(str)) {
        INITIALIZATION_STATE = 4;
        Util.report("Failed to load class \"org.slf4j.impl.StaticLoggerBinder\".");
        Util.report("Defaulting to no-operation (NOP) logger implementation");
        Util.report("See http://www.slf4j.org/codes.html#StaticLoggerBinder for further details.");
      } else {
        failedBinding(noClassDefFoundError);
        throw noClassDefFoundError;
      } 
    } catch (NoSuchMethodError noSuchMethodError) {
      String str = noSuchMethodError.getMessage();
      if (str != null && str.contains("org.slf4j.impl.StaticLoggerBinder.getSingleton()")) {
        INITIALIZATION_STATE = 2;
        Util.report("slf4j-api 1.6.x (or later) is incompatible with this binding.");
        Util.report("Your binding is version 1.5.5 or earlier.");
        Util.report("Upgrade your binding to version 1.6.x.");
      } 
      throw noSuchMethodError;
    } catch (Exception exception) {
      failedBinding(exception);
      throw new IllegalStateException("Unexpected initialization failure", exception);
    } 
  }
  
  private static void fixSubstituteLoggers() {
    synchronized (SUBST_FACTORY) {
      SUBST_FACTORY.postInitialization();
      for (SubstituteLogger substituteLogger : SUBST_FACTORY.getLoggers()) {
        Logger logger = getLogger(substituteLogger.getName());
        substituteLogger.setDelegate(logger);
      } 
    } 
  }
  
  static void failedBinding(Throwable paramThrowable) {
    INITIALIZATION_STATE = 2;
    Util.report("Failed to instantiate SLF4J LoggerFactory", paramThrowable);
  }
  
  private static void replayEvents() {
    LinkedBlockingQueue linkedBlockingQueue = SUBST_FACTORY.getEventQueue();
    int i = linkedBlockingQueue.size();
    byte b = 0;
    char c = '';
    ArrayList arrayList = new ArrayList(128);
    while (true) {
      int j = linkedBlockingQueue.drainTo(arrayList, 128);
      if (j == 0)
        break; 
      for (SubstituteLoggingEvent substituteLoggingEvent : arrayList) {
        replaySingleEvent(substituteLoggingEvent);
        if (b++ == 0)
          emitReplayOrSubstituionWarning(substituteLoggingEvent, i); 
      } 
      arrayList.clear();
    } 
  }
  
  private static void emitReplayOrSubstituionWarning(SubstituteLoggingEvent paramSubstituteLoggingEvent, int paramInt) {
    if (paramSubstituteLoggingEvent.getLogger().isDelegateEventAware()) {
      emitReplayWarning(paramInt);
    } else if (!paramSubstituteLoggingEvent.getLogger().isDelegateNOP()) {
      emitSubstitutionWarning();
    } 
  }
  
  private static void replaySingleEvent(SubstituteLoggingEvent paramSubstituteLoggingEvent) {
    if (paramSubstituteLoggingEvent == null)
      return; 
    SubstituteLogger substituteLogger = paramSubstituteLoggingEvent.getLogger();
    String str = substituteLogger.getName();
    if (substituteLogger.isDelegateNull())
      throw new IllegalStateException("Delegate logger cannot be null at this state."); 
    if (!substituteLogger.isDelegateNOP())
      if (substituteLogger.isDelegateEventAware()) {
        substituteLogger.log((LoggingEvent)paramSubstituteLoggingEvent);
      } else {
        Util.report(str);
      }  
  }
  
  private static void emitSubstitutionWarning() {
    Util.report("The following set of substitute loggers may have been accessed");
    Util.report("during the initialization phase. Logging calls during this");
    Util.report("phase were not honored. However, subsequent logging calls to these");
    Util.report("loggers will work as normally expected.");
    Util.report("See also http://www.slf4j.org/codes.html#substituteLogger");
  }
  
  private static void emitReplayWarning(int paramInt) {
    Util.report("A number (" + paramInt + ") of logging calls during the initialization phase have been intercepted and are");
    Util.report("now being replayed. These are subject to the filtering rules of the underlying logging system.");
    Util.report("See also http://www.slf4j.org/codes.html#replay");
  }
  
  private static final void versionSanityCheck() {
    try {
      String str = StaticLoggerBinder.REQUESTED_API_VERSION;
      boolean bool = false;
      for (String str1 : API_COMPATIBILITY_LIST) {
        if (str.startsWith(str1))
          bool = true; 
      } 
      if (!bool) {
        Util.report("The requested version " + str + " by your slf4j binding is not compatible with " + Arrays.<String>asList(API_COMPATIBILITY_LIST).toString());
        Util.report("See http://www.slf4j.org/codes.html#version_mismatch for further details.");
      } 
    } catch (NoSuchFieldError noSuchFieldError) {
    
    } catch (Throwable throwable) {
      Util.report("Unexpected problem occured during version sanity check", throwable);
    } 
  }
  
  static Set<URL> findPossibleStaticLoggerBinderPathSet() {
    LinkedHashSet<URL> linkedHashSet = new LinkedHashSet();
    try {
      Enumeration<URL> enumeration;
      ClassLoader classLoader = LoggerFactory.class.getClassLoader();
      if (classLoader == null) {
        enumeration = ClassLoader.getSystemResources(STATIC_LOGGER_BINDER_PATH);
      } else {
        enumeration = classLoader.getResources(STATIC_LOGGER_BINDER_PATH);
      } 
      while (enumeration.hasMoreElements()) {
        URL uRL = enumeration.nextElement();
        linkedHashSet.add(uRL);
      } 
    } catch (IOException iOException) {
      Util.report("Error getting resources from path", iOException);
    } 
    return linkedHashSet;
  }
  
  private static boolean isAmbiguousStaticLoggerBinderPathSet(Set<URL> paramSet) {
    return (paramSet.size() > 1);
  }
  
  private static void reportMultipleBindingAmbiguity(Set<URL> paramSet) {
    if (isAmbiguousStaticLoggerBinderPathSet(paramSet)) {
      Util.report("Class path contains multiple SLF4J bindings.");
      for (URL uRL : paramSet)
        Util.report("Found binding in [" + uRL + "]"); 
      Util.report("See http://www.slf4j.org/codes.html#multiple_bindings for an explanation.");
    } 
  }
  
  private static boolean isAndroid() {
    String str = Util.safeGetSystemProperty("java.vendor.url");
    if (str == null)
      return false; 
    return str.toLowerCase().contains("android");
  }
  
  private static void reportActualBinding(Set<URL> paramSet) {
    if (paramSet != null && isAmbiguousStaticLoggerBinderPathSet(paramSet))
      Util.report("Actual binding is of type [" + StaticLoggerBinder.getSingleton().getLoggerFactoryClassStr() + "]"); 
  }
  
  public static Logger getLogger(String paramString) {
    ILoggerFactory iLoggerFactory = getILoggerFactory();
    return iLoggerFactory.getLogger(paramString);
  }
  
  public static Logger getLogger(Class<?> paramClass) {
    Logger logger = getLogger(paramClass.getName());
    if (DETECT_LOGGER_NAME_MISMATCH) {
      Class<?> clazz = Util.getCallingClass();
      if (clazz != null && nonMatchingClasses(paramClass, clazz)) {
        Util.report(String.format("Detected logger name mismatch. Given name: \"%s\"; computed name: \"%s\".", new Object[] { logger.getName(), clazz.getName() }));
        Util.report("See http://www.slf4j.org/codes.html#loggerNameMismatch for an explanation");
      } 
    } 
    return logger;
  }
  
  private static boolean nonMatchingClasses(Class<?> paramClass1, Class<?> paramClass2) {
    return !paramClass2.isAssignableFrom(paramClass1);
  }
  
  public static ILoggerFactory getILoggerFactory() {
    if (INITIALIZATION_STATE == 0)
      synchronized (LoggerFactory.class) {
        if (INITIALIZATION_STATE == 0) {
          INITIALIZATION_STATE = 1;
          performInitialization();
        } 
      }  
    switch (INITIALIZATION_STATE) {
      case 3:
        return StaticLoggerBinder.getSingleton().getLoggerFactory();
      case 4:
        return (ILoggerFactory)NOP_FALLBACK_FACTORY;
      case 2:
        throw new IllegalStateException("org.slf4j.LoggerFactory in failed state. Original exception was thrown EARLIER. See also http://www.slf4j.org/codes.html#unsuccessfulInit");
      case 1:
        return (ILoggerFactory)SUBST_FACTORY;
    } 
    throw new IllegalStateException("Unreachable code");
  }
}
