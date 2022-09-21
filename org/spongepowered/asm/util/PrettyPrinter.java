package org.spongepowered.asm.util;

import com.google.common.base.Strings;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PrettyPrinter {
  public static interface IPrettyPrintable {
    void print(PrettyPrinter param1PrettyPrinter);
  }
  
  static interface IVariableWidthEntry {
    int getWidth();
  }
  
  static interface ISpecialEntry {}
  
  class KeyValue implements IVariableWidthEntry {
    private final Object value;
    
    private final String key;
    
    public KeyValue(String param1String, Object param1Object) {
      this.key = param1String;
      this.value = param1Object;
    }
    
    public String toString() {
      return String.format(PrettyPrinter.this.kvFormat, new Object[] { this.key, this.value });
    }
    
    public int getWidth() {
      return toString().length();
    }
  }
  
  class HorizontalRule implements ISpecialEntry {
    private final char[] hrChars;
    
    public HorizontalRule(char... param1VarArgs) {
      this.hrChars = param1VarArgs;
    }
    
    public String toString() {
      return Strings.repeat(new String(this.hrChars), PrettyPrinter.this.width + 2);
    }
  }
  
  class CentredText {
    private final Object centred;
    
    public CentredText(Object param1Object) {
      this.centred = param1Object;
    }
    
    public String toString() {
      String str = this.centred.toString();
      return String.format("%" + ((PrettyPrinter.this.width - str.length()) / 2 + str.length()) + "s", new Object[] { str });
    }
  }
  
  public enum Alignment {
    LEFT, RIGHT;
    
    static {
    
    }
  }
  
  static class Table implements IVariableWidthEntry {
    final List<PrettyPrinter.Column> columns = new ArrayList<PrettyPrinter.Column>();
    
    final List<PrettyPrinter.Row> rows = new ArrayList<PrettyPrinter.Row>();
    
    String format = "%s";
    
    int colSpacing = 2;
    
    boolean addHeader = true;
    
    void headerAdded() {
      this.addHeader = false;
    }
    
    void setColSpacing(int param1Int) {
      this.colSpacing = Math.max(0, param1Int);
      updateFormat();
    }
    
    Table grow(int param1Int) {
      while (this.columns.size() < param1Int)
        this.columns.add(new PrettyPrinter.Column(this)); 
      updateFormat();
      return this;
    }
    
    PrettyPrinter.Column add(PrettyPrinter.Column param1Column) {
      this.columns.add(param1Column);
      return param1Column;
    }
    
    PrettyPrinter.Row add(PrettyPrinter.Row param1Row) {
      this.rows.add(param1Row);
      return param1Row;
    }
    
    PrettyPrinter.Column addColumn(String param1String) {
      return add(new PrettyPrinter.Column(this, param1String));
    }
    
    PrettyPrinter.Column addColumn(PrettyPrinter.Alignment param1Alignment, int param1Int, String param1String) {
      return add(new PrettyPrinter.Column(this, param1Alignment, param1Int, param1String));
    }
    
    PrettyPrinter.Row addRow(Object... param1VarArgs) {
      return add(new PrettyPrinter.Row(this, param1VarArgs));
    }
    
    void updateFormat() {
      String str = Strings.repeat(" ", this.colSpacing);
      StringBuilder stringBuilder = new StringBuilder();
      boolean bool = false;
      for (PrettyPrinter.Column column : this.columns) {
        if (bool)
          stringBuilder.append(str); 
        bool = true;
        stringBuilder.append(column.getFormat());
      } 
      this.format = stringBuilder.toString();
    }
    
    String getFormat() {
      return this.format;
    }
    
    Object[] getTitles() {
      ArrayList<String> arrayList = new ArrayList();
      for (PrettyPrinter.Column column : this.columns)
        arrayList.add(column.getTitle()); 
      return arrayList.toArray();
    }
    
    public String toString() {
      int i = 0;
      String[] arrayOfString = new String[this.columns.size()];
      for (byte b = 0; b < this.columns.size(); b++) {
        arrayOfString[b] = ((PrettyPrinter.Column)this.columns.get(b)).toString();
        i |= !arrayOfString[b].isEmpty() ? 1 : 0;
      } 
      return (i != 0) ? String.format(this.format, (Object[])arrayOfString) : null;
    }
    
    public int getWidth() {
      String str = toString();
      return (str != null) ? str.length() : 0;
    }
  }
  
  static class Column {
    private PrettyPrinter.Alignment align = PrettyPrinter.Alignment.LEFT;
    
    private int minWidth = 1;
    
    private int maxWidth = Integer.MAX_VALUE;
    
    private int size = 0;
    
    private String title = "";
    
    private String format = "%s";
    
    private final PrettyPrinter.Table table;
    
    Column(PrettyPrinter.Table param1Table) {
      this.table = param1Table;
    }
    
    Column(PrettyPrinter.Table param1Table, String param1String) {
      this(param1Table);
      this.title = param1String;
      this.minWidth = param1String.length();
      updateFormat();
    }
    
    Column(PrettyPrinter.Table param1Table, PrettyPrinter.Alignment param1Alignment, int param1Int, String param1String) {
      this(param1Table, param1String);
      this.align = param1Alignment;
      this.size = param1Int;
    }
    
    void setAlignment(PrettyPrinter.Alignment param1Alignment) {
      this.align = param1Alignment;
      updateFormat();
    }
    
    void setWidth(int param1Int) {
      if (param1Int > this.size) {
        this.size = param1Int;
        updateFormat();
      } 
    }
    
    void setMinWidth(int param1Int) {
      if (param1Int > this.minWidth) {
        this.minWidth = param1Int;
        updateFormat();
      } 
    }
    
    void setMaxWidth(int param1Int) {
      this.size = Math.min(this.size, this.maxWidth);
      this.maxWidth = Math.max(1, param1Int);
      updateFormat();
    }
    
    void setTitle(String param1String) {
      this.title = param1String;
      setWidth(param1String.length());
    }
    
    private void updateFormat() {
      int i = Math.min(this.maxWidth, (this.size == 0) ? this.minWidth : this.size);
      this.format = "%" + ((this.align == PrettyPrinter.Alignment.RIGHT) ? "" : "-") + i + "s";
      this.table.updateFormat();
    }
    
    int getMaxWidth() {
      return this.maxWidth;
    }
    
    String getTitle() {
      return this.title;
    }
    
    String getFormat() {
      return this.format;
    }
    
    public String toString() {
      if (this.title.length() > this.maxWidth)
        return this.title.substring(0, this.maxWidth); 
      return this.title;
    }
  }
  
  static class Row implements IVariableWidthEntry {
    final PrettyPrinter.Table table;
    
    final String[] args;
    
    public Row(PrettyPrinter.Table param1Table, Object... param1VarArgs) {
      this.table = param1Table.grow(param1VarArgs.length);
      this.args = new String[param1VarArgs.length];
      for (byte b = 0; b < param1VarArgs.length; b++) {
        this.args[b] = param1VarArgs[b].toString();
        ((PrettyPrinter.Column)this.table.columns.get(b)).setMinWidth(this.args[b].length());
      } 
    }
    
    public String toString() {
      Object[] arrayOfObject = new Object[this.table.columns.size()];
      for (byte b = 0; b < arrayOfObject.length; b++) {
        PrettyPrinter.Column column = this.table.columns.get(b);
        if (b >= this.args.length) {
          arrayOfObject[b] = "";
        } else {
          arrayOfObject[b] = (this.args[b].length() > column.getMaxWidth()) ? this.args[b].substring(0, column.getMaxWidth()) : this.args[b];
        } 
      } 
      return String.format(this.table.format, arrayOfObject);
    }
    
    public int getWidth() {
      return toString().length();
    }
  }
  
  private final HorizontalRule horizontalRule = new HorizontalRule(new char[] { '*' });
  
  private final List<Object> lines = new ArrayList();
  
  private boolean recalcWidth = false;
  
  protected int width = 100;
  
  protected int wrapWidth = 80;
  
  protected int kvKeyWidth = 10;
  
  protected String kvFormat = makeKvFormat(this.kvKeyWidth);
  
  private Table table;
  
  public PrettyPrinter() {
    this(100);
  }
  
  public PrettyPrinter(int paramInt) {
    this.width = paramInt;
  }
  
  public PrettyPrinter wrapTo(int paramInt) {
    this.wrapWidth = paramInt;
    return this;
  }
  
  public int wrapTo() {
    return this.wrapWidth;
  }
  
  public PrettyPrinter table() {
    this.table = new Table();
    return this;
  }
  
  public PrettyPrinter table(String... paramVarArgs) {
    this.table = new Table();
    for (String str : paramVarArgs)
      this.table.addColumn(str); 
    return this;
  }
  
  public PrettyPrinter table(Object... paramVarArgs) {
    this.table = new Table();
    Column column = null;
    for (Object object : paramVarArgs) {
      if (object instanceof String) {
        column = this.table.addColumn((String)object);
      } else if (object instanceof Integer && column != null) {
        int i = ((Integer)object).intValue();
        if (i > 0) {
          column.setWidth(i);
        } else if (i < 0) {
          column.setMaxWidth(-i);
        } 
      } else if (object instanceof Alignment && column != null) {
        column.setAlignment((Alignment)object);
      } else if (object != null) {
        column = this.table.addColumn(object.toString());
      } 
    } 
    return this;
  }
  
  public PrettyPrinter spacing(int paramInt) {
    if (this.table == null)
      this.table = new Table(); 
    this.table.setColSpacing(paramInt);
    return this;
  }
  
  public PrettyPrinter th() {
    return th(false);
  }
  
  private PrettyPrinter th(boolean paramBoolean) {
    if (this.table == null)
      this.table = new Table(); 
    if (!paramBoolean || this.table.addHeader) {
      this.table.headerAdded();
      addLine(this.table);
    } 
    return this;
  }
  
  public PrettyPrinter tr(Object... paramVarArgs) {
    th(true);
    addLine(this.table.addRow(paramVarArgs));
    this.recalcWidth = true;
    return this;
  }
  
  public PrettyPrinter add() {
    addLine("");
    return this;
  }
  
  public PrettyPrinter add(String paramString) {
    addLine(paramString);
    this.width = Math.max(this.width, paramString.length());
    return this;
  }
  
  public PrettyPrinter add(String paramString, Object... paramVarArgs) {
    String str = String.format(paramString, paramVarArgs);
    addLine(str);
    this.width = Math.max(this.width, str.length());
    return this;
  }
  
  public PrettyPrinter add(Object[] paramArrayOfObject) {
    return add(paramArrayOfObject, "%s");
  }
  
  public PrettyPrinter add(Object[] paramArrayOfObject, String paramString) {
    for (Object object : paramArrayOfObject) {
      add(paramString, new Object[] { object });
    } 
    return this;
  }
  
  public PrettyPrinter addIndexed(Object[] paramArrayOfObject) {
    int i = String.valueOf(paramArrayOfObject.length - 1).length();
    String str = "[%" + i + "d] %s";
    for (byte b = 0; b < paramArrayOfObject.length; b++) {
      add(str, new Object[] { Integer.valueOf(b), paramArrayOfObject[b] });
    } 
    return this;
  }
  
  public PrettyPrinter addWithIndices(Collection<?> paramCollection) {
    return addIndexed(paramCollection.toArray());
  }
  
  public PrettyPrinter add(IPrettyPrintable paramIPrettyPrintable) {
    if (paramIPrettyPrintable != null)
      paramIPrettyPrintable.print(this); 
    return this;
  }
  
  public PrettyPrinter add(Throwable paramThrowable) {
    return add(paramThrowable, 4);
  }
  
  public PrettyPrinter add(Throwable paramThrowable, int paramInt) {
    while (paramThrowable != null) {
      add("%s: %s", new Object[] { paramThrowable.getClass().getName(), paramThrowable.getMessage() });
      add(paramThrowable.getStackTrace(), paramInt);
      paramThrowable = paramThrowable.getCause();
    } 
    return this;
  }
  
  public PrettyPrinter add(StackTraceElement[] paramArrayOfStackTraceElement, int paramInt) {
    String str = Strings.repeat(" ", paramInt);
    for (StackTraceElement stackTraceElement : paramArrayOfStackTraceElement) {
      add("%s%s", new Object[] { str, stackTraceElement });
    } 
    return this;
  }
  
  public PrettyPrinter add(Object paramObject) {
    return add(paramObject, 0);
  }
  
  public PrettyPrinter add(Object paramObject, int paramInt) {
    String str = Strings.repeat(" ", paramInt);
    return append(paramObject, paramInt, str);
  }
  
  private PrettyPrinter append(Object paramObject, int paramInt, String paramString) {
    if (paramObject instanceof String)
      return add("%s%s", new Object[] { paramString, paramObject }); 
    if (paramObject instanceof Iterable) {
      for (Object object : paramObject)
        append(object, paramInt, paramString); 
      return this;
    } 
    if (paramObject instanceof Map) {
      kvWidth(paramInt);
      return add((Map<?, ?>)paramObject);
    } 
    if (paramObject instanceof IPrettyPrintable)
      return add((IPrettyPrintable)paramObject); 
    if (paramObject instanceof Throwable)
      return add((Throwable)paramObject, paramInt); 
    if (paramObject.getClass().isArray())
      return add((Object[])paramObject, paramInt + "%s"); 
    return add("%s%s", new Object[] { paramString, paramObject });
  }
  
  public PrettyPrinter addWrapped(String paramString, Object... paramVarArgs) {
    return addWrapped(this.wrapWidth, paramString, paramVarArgs);
  }
  
  public PrettyPrinter addWrapped(int paramInt, String paramString, Object... paramVarArgs) {
    String str1 = "";
    String str2 = String.format(paramString, paramVarArgs).replace("\t", "    ");
    Matcher matcher = Pattern.compile("^(\\s+)(.*)$").matcher(str2);
    if (matcher.matches())
      str1 = matcher.group(1); 
    try {
      for (String str : getWrapped(paramInt, str2, str1))
        addLine(str); 
    } catch (Exception exception) {
      add(str2);
    } 
    return this;
  }
  
  private List<String> getWrapped(int paramInt, String paramString1, String paramString2) {
    ArrayList<String> arrayList = new ArrayList();
    while (paramString1.length() > paramInt) {
      int i = paramString1.lastIndexOf(' ', paramInt);
      if (i < 10)
        i = paramInt; 
      String str = paramString1.substring(0, i);
      arrayList.add(str);
      paramString1 = paramString2 + paramString1.substring(i + 1);
    } 
    if (paramString1.length() > 0)
      arrayList.add(paramString1); 
    return arrayList;
  }
  
  public PrettyPrinter kv(String paramString1, String paramString2, Object... paramVarArgs) {
    return kv(paramString1, String.format(paramString2, paramVarArgs));
  }
  
  public PrettyPrinter kv(String paramString, Object paramObject) {
    addLine(new KeyValue(paramString, paramObject));
    return kvWidth(paramString.length());
  }
  
  public PrettyPrinter kvWidth(int paramInt) {
    if (paramInt > this.kvKeyWidth) {
      this.kvKeyWidth = paramInt;
      this.kvFormat = makeKvFormat(paramInt);
    } 
    this.recalcWidth = true;
    return this;
  }
  
  public PrettyPrinter add(Map<?, ?> paramMap) {
    for (Map.Entry<?, ?> entry : paramMap.entrySet()) {
      String str = (entry.getKey() == null) ? "null" : entry.getKey().toString();
      kv(str, entry.getValue());
    } 
    return this;
  }
  
  public PrettyPrinter hr() {
    return hr('*');
  }
  
  public PrettyPrinter hr(char paramChar) {
    addLine(new HorizontalRule(new char[] { paramChar }));
    return this;
  }
  
  public PrettyPrinter centre() {
    if (!this.lines.isEmpty()) {
      Object object = this.lines.get(this.lines.size() - 1);
      if (object instanceof String)
        addLine(new CentredText(this.lines.remove(this.lines.size() - 1))); 
    } 
    return this;
  }
  
  private void addLine(Object paramObject) {
    if (paramObject == null)
      return; 
    this.lines.add(paramObject);
    this.recalcWidth |= paramObject instanceof IVariableWidthEntry;
  }
  
  public PrettyPrinter trace() {
    return trace(getDefaultLoggerName());
  }
  
  public PrettyPrinter trace(Level paramLevel) {
    return trace(getDefaultLoggerName(), paramLevel);
  }
  
  public PrettyPrinter trace(String paramString) {
    return trace(System.err, LogManager.getLogger(paramString));
  }
  
  public PrettyPrinter trace(String paramString, Level paramLevel) {
    return trace(System.err, LogManager.getLogger(paramString), paramLevel);
  }
  
  public PrettyPrinter trace(Logger paramLogger) {
    return trace(System.err, paramLogger);
  }
  
  public PrettyPrinter trace(Logger paramLogger, Level paramLevel) {
    return trace(System.err, paramLogger, paramLevel);
  }
  
  public PrettyPrinter trace(PrintStream paramPrintStream) {
    return trace(paramPrintStream, getDefaultLoggerName());
  }
  
  public PrettyPrinter trace(PrintStream paramPrintStream, Level paramLevel) {
    return trace(paramPrintStream, getDefaultLoggerName(), paramLevel);
  }
  
  public PrettyPrinter trace(PrintStream paramPrintStream, String paramString) {
    return trace(paramPrintStream, LogManager.getLogger(paramString));
  }
  
  public PrettyPrinter trace(PrintStream paramPrintStream, String paramString, Level paramLevel) {
    return trace(paramPrintStream, LogManager.getLogger(paramString), paramLevel);
  }
  
  public PrettyPrinter trace(PrintStream paramPrintStream, Logger paramLogger) {
    return trace(paramPrintStream, paramLogger, Level.DEBUG);
  }
  
  public PrettyPrinter trace(PrintStream paramPrintStream, Logger paramLogger, Level paramLevel) {
    log(paramLogger, paramLevel);
    print(paramPrintStream);
    return this;
  }
  
  public PrettyPrinter print() {
    return print(System.err);
  }
  
  public PrettyPrinter print(PrintStream paramPrintStream) {
    updateWidth();
    printSpecial(paramPrintStream, this.horizontalRule);
    for (ISpecialEntry iSpecialEntry : this.lines) {
      if (iSpecialEntry instanceof ISpecialEntry) {
        printSpecial(paramPrintStream, iSpecialEntry);
        continue;
      } 
      printString(paramPrintStream, iSpecialEntry.toString());
    } 
    printSpecial(paramPrintStream, this.horizontalRule);
    return this;
  }
  
  private void printSpecial(PrintStream paramPrintStream, ISpecialEntry paramISpecialEntry) {
    paramPrintStream.printf("/*%s*/\n", new Object[] { paramISpecialEntry.toString() });
  }
  
  private void printString(PrintStream paramPrintStream, String paramString) {
    if (paramString != null)
      paramPrintStream.printf("/* %-" + this.width + "s */\n", new Object[] { paramString }); 
  }
  
  public PrettyPrinter log(Logger paramLogger) {
    return log(paramLogger, Level.INFO);
  }
  
  public PrettyPrinter log(Logger paramLogger, Level paramLevel) {
    updateWidth();
    logSpecial(paramLogger, paramLevel, this.horizontalRule);
    for (ISpecialEntry iSpecialEntry : this.lines) {
      if (iSpecialEntry instanceof ISpecialEntry) {
        logSpecial(paramLogger, paramLevel, iSpecialEntry);
        continue;
      } 
      logString(paramLogger, paramLevel, iSpecialEntry.toString());
    } 
    logSpecial(paramLogger, paramLevel, this.horizontalRule);
    return this;
  }
  
  private void logSpecial(Logger paramLogger, Level paramLevel, ISpecialEntry paramISpecialEntry) {
    paramLogger.log(paramLevel, "/*{}*/", new Object[] { paramISpecialEntry.toString() });
  }
  
  private void logString(Logger paramLogger, Level paramLevel, String paramString) {
    if (paramString != null)
      paramLogger.log(paramLevel, String.format("/* %-" + this.width + "s */", new Object[] { paramString })); 
  }
  
  private void updateWidth() {
    if (this.recalcWidth) {
      this.recalcWidth = false;
      for (IVariableWidthEntry iVariableWidthEntry : this.lines) {
        if (iVariableWidthEntry instanceof IVariableWidthEntry)
          this.width = Math.min(4096, Math.max(this.width, ((IVariableWidthEntry)iVariableWidthEntry).getWidth())); 
      } 
    } 
  }
  
  private static String makeKvFormat(int paramInt) {
    return String.format("%%%ds : %%s", new Object[] { Integer.valueOf(paramInt) });
  }
  
  private static String getDefaultLoggerName() {
    String str = (new Throwable()).getStackTrace()[2].getClassName();
    int i = str.lastIndexOf('.');
    return (i == -1) ? str : str.substring(i + 1);
  }
  
  public static void dumpStack() {
    (new PrettyPrinter()).add(new Exception("Stack trace")).print(System.err);
  }
  
  public static void print(Throwable paramThrowable) {
    (new PrettyPrinter()).add(paramThrowable).print(System.err);
  }
}
