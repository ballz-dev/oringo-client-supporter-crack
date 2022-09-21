package org.spongepowered.asm.util.perf;

import com.google.common.base.Joiner;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.TreeMap;
import org.spongepowered.asm.util.PrettyPrinter;

public final class Profiler {
  public class Section {
    static final String SEPARATOR_CHILD = ".";
    
    private boolean fine;
    
    static final String SEPARATOR_ROOT = " -> ";
    
    private final String name;
    
    protected boolean invalidated;
    
    private boolean root;
    
    private String info;
    
    Section(String param1String) {
      this.name = param1String;
      this.info = param1String;
    }
    
    Section getDelegate() {
      return this;
    }
    
    Section invalidate() {
      this.invalidated = true;
      return this;
    }
    
    Section setRoot(boolean param1Boolean) {
      this.root = param1Boolean;
      return this;
    }
    
    public boolean isRoot() {
      return this.root;
    }
    
    Section setFine(boolean param1Boolean) {
      this.fine = param1Boolean;
      return this;
    }
    
    public boolean isFine() {
      return this.fine;
    }
    
    public String getName() {
      return this.name;
    }
    
    public String getBaseName() {
      return this.name;
    }
    
    public void setInfo(String param1String) {
      this.info = param1String;
    }
    
    public String getInfo() {
      return this.info;
    }
    
    Section start() {
      return this;
    }
    
    protected Section stop() {
      return this;
    }
    
    public Section end() {
      if (!this.invalidated)
        Profiler.this.end(this); 
      return this;
    }
    
    public Section next(String param1String) {
      end();
      return Profiler.this.begin(param1String);
    }
    
    void mark() {}
    
    public long getTime() {
      return 0L;
    }
    
    public long getTotalTime() {
      return 0L;
    }
    
    public double getSeconds() {
      return 0.0D;
    }
    
    public double getTotalSeconds() {
      return 0.0D;
    }
    
    public long[] getTimes() {
      return new long[1];
    }
    
    public int getCount() {
      return 0;
    }
    
    public int getTotalCount() {
      return 0;
    }
    
    public double getAverageTime() {
      return 0.0D;
    }
    
    public double getTotalAverageTime() {
      return 0.0D;
    }
    
    public final String toString() {
      return this.name;
    }
  }
  
  class LiveSection extends Section {
    private int cursor = 0;
    
    private long[] times = new long[0];
    
    private long start = 0L;
    
    private int markedCount;
    
    private long time;
    
    private long markedTime;
    
    private int count;
    
    LiveSection(String param1String, int param1Int) {
      super(param1String);
      this.cursor = param1Int;
    }
    
    Profiler.Section start() {
      this.start = System.currentTimeMillis();
      return this;
    }
    
    protected Profiler.Section stop() {
      if (this.start > 0L)
        this.time += System.currentTimeMillis() - this.start; 
      this.start = 0L;
      this.count++;
      return this;
    }
    
    public Profiler.Section end() {
      stop();
      if (!this.invalidated)
        Profiler.this.end(this); 
      return this;
    }
    
    void mark() {
      if (this.cursor >= this.times.length)
        this.times = Arrays.copyOf(this.times, this.cursor + 4); 
      this.times[this.cursor] = this.time;
      this.markedTime += this.time;
      this.markedCount += this.count;
      this.time = 0L;
      this.count = 0;
      this.cursor++;
    }
    
    public long getTime() {
      return this.time;
    }
    
    public long getTotalTime() {
      return this.time + this.markedTime;
    }
    
    public double getSeconds() {
      return this.time * 0.001D;
    }
    
    public double getTotalSeconds() {
      return (this.time + this.markedTime) * 0.001D;
    }
    
    public long[] getTimes() {
      long[] arrayOfLong = new long[this.cursor + 1];
      System.arraycopy(this.times, 0, arrayOfLong, 0, Math.min(this.times.length, this.cursor));
      arrayOfLong[this.cursor] = this.time;
      return arrayOfLong;
    }
    
    public int getCount() {
      return this.count;
    }
    
    public int getTotalCount() {
      return this.count + this.markedCount;
    }
    
    public double getAverageTime() {
      return (this.count > 0) ? (this.time / this.count) : 0.0D;
    }
    
    public double getTotalAverageTime() {
      return (this.count > 0) ? ((this.time + this.markedTime) / (this.count + this.markedCount)) : 0.0D;
    }
  }
  
  class SubSection extends LiveSection {
    private final String baseName;
    
    private final Profiler.Section root;
    
    SubSection(String param1String1, int param1Int, String param1String2, Profiler.Section param1Section) {
      super(param1String1, param1Int);
      this.baseName = param1String2;
      this.root = param1Section;
    }
    
    Profiler.Section invalidate() {
      this.root.invalidate();
      return super.invalidate();
    }
    
    public String getBaseName() {
      return this.baseName;
    }
    
    public void setInfo(String param1String) {
      this.root.setInfo(param1String);
      super.setInfo(param1String);
    }
    
    Profiler.Section getDelegate() {
      return this.root;
    }
    
    Profiler.Section start() {
      this.root.start();
      return super.start();
    }
    
    public Profiler.Section end() {
      this.root.stop();
      return super.end();
    }
    
    public Profiler.Section next(String param1String) {
      stop();
      return this.root.next(param1String);
    }
  }
  
  private final Map<String, Section> sections = new TreeMap<String, Section>();
  
  private final List<String> phases = new ArrayList<String>();
  
  private final Deque<Section> stack = new LinkedList<Section>();
  
  public static final int FINE = 2;
  
  private boolean active;
  
  public static final int ROOT = 1;
  
  public Profiler() {
    this.phases.add("Initial");
  }
  
  public void setActive(boolean paramBoolean) {
    if ((!this.active && paramBoolean) || !paramBoolean)
      reset(); 
    this.active = paramBoolean;
  }
  
  public void reset() {
    for (Section section : this.sections.values())
      section.invalidate(); 
    this.sections.clear();
    this.phases.clear();
    this.phases.add("Initial");
    this.stack.clear();
  }
  
  public Section get(String paramString) {
    Section section = this.sections.get(paramString);
    if (section == null) {
      section = this.active ? new LiveSection(paramString, this.phases.size() - 1) : new Section(paramString);
      this.sections.put(paramString, section);
    } 
    return section;
  }
  
  private Section getSubSection(String paramString1, String paramString2, Section paramSection) {
    Section section = this.sections.get(paramString1);
    if (section == null) {
      section = new SubSection(paramString1, this.phases.size() - 1, paramString2, paramSection);
      this.sections.put(paramString1, section);
    } 
    return section;
  }
  
  boolean isHead(Section paramSection) {
    return (this.stack.peek() == paramSection);
  }
  
  public Section begin(String... paramVarArgs) {
    return begin(0, paramVarArgs);
  }
  
  public Section begin(int paramInt, String... paramVarArgs) {
    return begin(paramInt, Joiner.on('.').join((Object[])paramVarArgs));
  }
  
  public Section begin(String paramString) {
    return begin(0, paramString);
  }
  
  public Section begin(int paramInt, String paramString) {
    boolean bool1 = ((paramInt & 0x1) != 0) ? true : false;
    boolean bool2 = ((paramInt & 0x2) != 0) ? true : false;
    String str = paramString;
    Section section1 = this.stack.peek();
    if (section1 != null) {
      str = section1.getName() + (bool1 ? " -> " : ".") + str;
      if (section1.isRoot() && !bool1) {
        int i = section1.getName().lastIndexOf(" -> ");
        paramString = ((i > -1) ? section1.getName().substring(i + 4) : section1.getName()) + "." + paramString;
        bool1 = true;
      } 
    } 
    Section section2 = get(bool1 ? paramString : str);
    if (bool1 && section1 != null && this.active)
      section2 = getSubSection(str, section1.getName(), section2); 
    section2.setFine(bool2).setRoot(bool1);
    this.stack.push(section2);
    return section2.start();
  }
  
  void end(Section paramSection) {
    try {
      for (Section section1 = this.stack.pop(), section2 = section1; section2 != paramSection; section2 = this.stack.pop()) {
        if (section2 == null && this.active) {
          if (section1 == null)
            throw new IllegalStateException("Attempted to pop " + paramSection + " but the stack is empty"); 
          throw new IllegalStateException("Attempted to pop " + paramSection + " which was not in the stack, head was " + section1);
        } 
      } 
    } catch (NoSuchElementException noSuchElementException) {
      if (this.active)
        throw new IllegalStateException("Attempted to pop " + paramSection + " but the stack is empty"); 
    } 
  }
  
  public void mark(String paramString) {
    long l = 0L;
    for (Section section : this.sections.values())
      l += section.getTime(); 
    if (l == 0L) {
      int i = this.phases.size();
      this.phases.set(i - 1, paramString);
      return;
    } 
    this.phases.add(paramString);
    for (Section section : this.sections.values())
      section.mark(); 
  }
  
  public Collection<Section> getSections() {
    return Collections.unmodifiableCollection(this.sections.values());
  }
  
  public PrettyPrinter printer(boolean paramBoolean1, boolean paramBoolean2) {
    PrettyPrinter prettyPrinter = new PrettyPrinter();
    int i = this.phases.size() + 4;
    int[] arrayOfInt = { 0, 1, 2, i - 2, i - 1 };
    Object[] arrayOfObject = new Object[i * 2];
    int j;
    for (byte b = 0; b < i; j = ++b * 2) {
      arrayOfObject[j + 1] = PrettyPrinter.Alignment.RIGHT;
      if (b == arrayOfInt[0]) {
        arrayOfObject[j] = (paramBoolean2 ? "" : "  ") + "Section";
        arrayOfObject[j + 1] = PrettyPrinter.Alignment.LEFT;
      } else if (b == arrayOfInt[1]) {
        arrayOfObject[j] = "    TOTAL";
      } else if (b == arrayOfInt[3]) {
        arrayOfObject[j] = "    Count";
      } else if (b == arrayOfInt[4]) {
        arrayOfObject[j] = "Avg. ";
      } else if (b - arrayOfInt[2] < this.phases.size()) {
        arrayOfObject[j] = this.phases.get(b - arrayOfInt[2]);
      } else {
        arrayOfObject[j] = "";
      } 
    } 
    prettyPrinter.table(arrayOfObject).th().hr().add();
    for (Section section : this.sections.values()) {
      if ((section.isFine() && !paramBoolean1) || (paramBoolean2 && section.getDelegate() != section))
        continue; 
      printSectionRow(prettyPrinter, i, arrayOfInt, section, paramBoolean2);
      if (paramBoolean2)
        for (Section section1 : this.sections.values()) {
          Section section2 = section1.getDelegate();
          if ((section1.isFine() && !paramBoolean1) || section2 != section || section2 == section1)
            continue; 
          printSectionRow(prettyPrinter, i, arrayOfInt, section1, paramBoolean2);
        }  
    } 
    return prettyPrinter.add();
  }
  
  private void printSectionRow(PrettyPrinter paramPrettyPrinter, int paramInt, int[] paramArrayOfint, Section paramSection, boolean paramBoolean) {
    boolean bool = (paramSection.getDelegate() != paramSection) ? true : false;
    Object[] arrayOfObject = new Object[paramInt];
    byte b1 = 1;
    if (paramBoolean) {
      arrayOfObject[0] = bool ? ("  > " + paramSection.getBaseName()) : paramSection.getName();
    } else {
      arrayOfObject[0] = (bool ? "+ " : "  ") + paramSection.getName();
    } 
    long[] arrayOfLong = paramSection.getTimes();
    for (long l : arrayOfLong) {
      if (b1 == paramArrayOfint[1])
        arrayOfObject[b1++] = paramSection.getTotalTime() + " ms"; 
      if (b1 >= paramArrayOfint[2] && b1 < arrayOfObject.length)
        arrayOfObject[b1++] = l + " ms"; 
    } 
    arrayOfObject[paramArrayOfint[3]] = Integer.valueOf(paramSection.getTotalCount());
    arrayOfObject[paramArrayOfint[4]] = (new DecimalFormat("   ###0.000 ms")).format(paramSection.getTotalAverageTime());
    for (byte b2 = 0; b2 < arrayOfObject.length; b2++) {
      if (arrayOfObject[b2] == null)
        arrayOfObject[b2] = "-"; 
    } 
    paramPrettyPrinter.tr(arrayOfObject);
  }
}
