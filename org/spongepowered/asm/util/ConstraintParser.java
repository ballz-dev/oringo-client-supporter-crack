package org.spongepowered.asm.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.spongepowered.asm.lib.tree.AnnotationNode;
import org.spongepowered.asm.util.throwables.ConstraintViolationException;
import org.spongepowered.asm.util.throwables.InvalidConstraintException;

public final class ConstraintParser {
  public static class Constraint {
    public static final Constraint NONE = new Constraint();
    
    private final String expr;
    
    private Constraint next;
    
    private String token;
    
    static {
    
    }
    
    private int min = Integer.MIN_VALUE;
    
    private String[] constraint;
    
    private static final Pattern pattern = Pattern.compile("^([A-Z0-9\\-_\\.]+)\\((?:(<|<=|>|>=|=)?([0-9]+)(<|(-)([0-9]+)?|>|(\\+)([0-9]+)?)?)?\\)$");
    
    private int max = Integer.MAX_VALUE;
    
    Constraint(String param1String) {
      this.expr = param1String;
      Matcher matcher = pattern.matcher(param1String);
      if (!matcher.matches())
        throw new InvalidConstraintException("Constraint syntax was invalid parsing: " + this.expr); 
      this.token = matcher.group(1);
      this
        
        .constraint = new String[] { matcher.group(2), matcher.group(3), matcher.group(4), matcher.group(5), matcher.group(6), matcher.group(7), matcher.group(8) };
      parse();
    }
    
    private Constraint() {
      this.expr = null;
      this.token = "*";
      this.constraint = new String[0];
    }
    
    private void parse() {
      if (!has(1))
        return; 
      this.max = this.min = val(1);
      boolean bool = has(0);
      if (has(4)) {
        if (bool)
          throw new InvalidConstraintException("Unexpected modifier '" + elem(0) + "' in " + this.expr + " parsing range"); 
        this.max = val(4);
        if (this.max < this.min)
          throw new InvalidConstraintException("Invalid range specified '" + this.max + "' is less than " + this.min + " in " + this.expr); 
        return;
      } 
      if (has(6)) {
        if (bool)
          throw new InvalidConstraintException("Unexpected modifier '" + elem(0) + "' in " + this.expr + " parsing range"); 
        this.max = this.min + val(6);
        return;
      } 
      if (bool) {
        if (has(3))
          throw new InvalidConstraintException("Unexpected trailing modifier '" + elem(3) + "' in " + this.expr); 
        String str = elem(0);
        if (">".equals(str)) {
          this.min++;
          this.max = Integer.MAX_VALUE;
        } else if (">=".equals(str)) {
          this.max = Integer.MAX_VALUE;
        } else if ("<".equals(str)) {
          this.max = --this.min;
          this.min = Integer.MIN_VALUE;
        } else if ("<=".equals(str)) {
          this.max = this.min;
          this.min = Integer.MIN_VALUE;
        } 
      } else if (has(2)) {
        String str = elem(2);
        if ("<".equals(str)) {
          this.max = this.min;
          this.min = Integer.MIN_VALUE;
        } else {
          this.max = Integer.MAX_VALUE;
        } 
      } 
    }
    
    private boolean has(int param1Int) {
      return (this.constraint[param1Int] != null);
    }
    
    private String elem(int param1Int) {
      return this.constraint[param1Int];
    }
    
    private int val(int param1Int) {
      return (this.constraint[param1Int] != null) ? Integer.parseInt(this.constraint[param1Int]) : 0;
    }
    
    void append(Constraint param1Constraint) {
      if (this.next != null) {
        this.next.append(param1Constraint);
        return;
      } 
      this.next = param1Constraint;
    }
    
    public String getToken() {
      return this.token;
    }
    
    public int getMin() {
      return this.min;
    }
    
    public int getMax() {
      return this.max;
    }
    
    public void check(ITokenProvider param1ITokenProvider) throws ConstraintViolationException {
      if (this != NONE) {
        Integer integer = param1ITokenProvider.getToken(this.token);
        if (integer == null)
          throw new ConstraintViolationException("The token '" + this.token + "' could not be resolved in " + param1ITokenProvider, this); 
        if (integer.intValue() < this.min)
          throw new ConstraintViolationException("Token '" + this.token + "' has a value (" + integer + ") which is less than the minimum value " + this.min + " in " + param1ITokenProvider, this, integer
              .intValue()); 
        if (integer.intValue() > this.max)
          throw new ConstraintViolationException("Token '" + this.token + "' has a value (" + integer + ") which is greater than the maximum value " + this.max + " in " + param1ITokenProvider, this, integer
              .intValue()); 
      } 
      if (this.next != null)
        this.next.check(param1ITokenProvider); 
    }
    
    public String getRangeHumanReadable() {
      if (this.min == Integer.MIN_VALUE && this.max == Integer.MAX_VALUE)
        return "ANY VALUE"; 
      if (this.min == Integer.MIN_VALUE)
        return String.format("less than or equal to %d", new Object[] { Integer.valueOf(this.max) }); 
      if (this.max == Integer.MAX_VALUE)
        return String.format("greater than or equal to %d", new Object[] { Integer.valueOf(this.min) }); 
      if (this.min == this.max)
        return String.format("%d", new Object[] { Integer.valueOf(this.min) }); 
      return String.format("between %d and %d", new Object[] { Integer.valueOf(this.min), Integer.valueOf(this.max) });
    }
    
    public String toString() {
      return String.format("Constraint(%s [%d-%d])", new Object[] { this.token, Integer.valueOf(this.min), Integer.valueOf(this.max) });
    }
  }
  
  public static Constraint parse(String paramString) {
    if (paramString == null || paramString.length() == 0)
      return Constraint.NONE; 
    String[] arrayOfString = paramString.replaceAll("\\s", "").toUpperCase().split(";");
    Constraint constraint = null;
    for (String str : arrayOfString) {
      Constraint constraint1 = new Constraint(str);
      if (constraint == null) {
        constraint = constraint1;
      } else {
        constraint.append(constraint1);
      } 
    } 
    return (constraint != null) ? constraint : Constraint.NONE;
  }
  
  public static Constraint parse(AnnotationNode paramAnnotationNode) {
    String str = Annotations.<String>getValue(paramAnnotationNode, "constraints", "");
    return parse(str);
  }
}
