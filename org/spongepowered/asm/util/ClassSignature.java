package org.spongepowered.asm.util;

import java.util.ArrayList;
import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;
import org.spongepowered.asm.lib.signature.SignatureReader;
import org.spongepowered.asm.lib.signature.SignatureVisitor;
import org.spongepowered.asm.lib.signature.SignatureWriter;
import org.spongepowered.asm.lib.tree.ClassNode;

public class ClassSignature {
  static class Lazy extends ClassSignature {
    private final String sig;
    
    private ClassSignature generated;
    
    Lazy(String param1String) {
      this.sig = param1String;
    }
    
    public ClassSignature wake() {
      if (this.generated == null)
        this.generated = ClassSignature.of(this.sig); 
      return this.generated;
    }
  }
  
  static class TypeVar implements Comparable<TypeVar> {
    private String currentName;
    
    private final String originalName;
    
    TypeVar(String param1String) {
      this.currentName = this.originalName = param1String;
    }
    
    public int compareTo(TypeVar param1TypeVar) {
      return this.currentName.compareTo(param1TypeVar.currentName);
    }
    
    public String toString() {
      return this.currentName;
    }
    
    String getOriginalName() {
      return this.originalName;
    }
    
    void rename(String param1String) {
      this.currentName = param1String;
    }
    
    public boolean matches(String param1String) {
      return this.originalName.equals(param1String);
    }
    
    public boolean equals(Object param1Object) {
      return this.currentName.equals(param1Object);
    }
    
    public int hashCode() {
      return this.currentName.hashCode();
    }
  }
  
  static interface IToken {
    public static final String WILDCARDS = "+-";
    
    String asType();
    
    IToken setWildcard(char param1Char);
    
    IToken setArray(boolean param1Boolean);
    
    String asBound();
    
    ClassSignature.Token asToken();
  }
  
  static class Token implements IToken {
    private Token tail;
    
    private boolean array;
    
    private List<Token> classBound;
    
    private String type;
    
    private List<Token> ifaceBound;
    
    private List<ClassSignature.IToken> suffix;
    
    private char symbol = Character.MIN_VALUE;
    
    private final boolean inner;
    
    private List<ClassSignature.IToken> signature;
    
    static final String SYMBOLS = "+-*";
    
    Token() {
      this(false);
    }
    
    Token(String param1String) {
      this(param1String, false);
    }
    
    Token(char param1Char) {
      this();
      this.symbol = param1Char;
    }
    
    Token(boolean param1Boolean) {
      this(null, param1Boolean);
    }
    
    Token(String param1String, boolean param1Boolean) {
      this.inner = param1Boolean;
      this.type = param1String;
    }
    
    Token setSymbol(char param1Char) {
      if (this.symbol == '\000' && "+-*".indexOf(param1Char) > -1)
        this.symbol = param1Char; 
      return this;
    }
    
    Token setType(String param1String) {
      if (this.type == null)
        this.type = param1String; 
      return this;
    }
    
    boolean hasClassBound() {
      return (this.classBound != null);
    }
    
    boolean hasInterfaceBound() {
      return (this.ifaceBound != null);
    }
    
    public ClassSignature.IToken setArray(boolean param1Boolean) {
      this.array |= param1Boolean;
      return this;
    }
    
    public ClassSignature.IToken setWildcard(char param1Char) {
      if ("+-".indexOf(param1Char) == -1)
        return this; 
      return setSymbol(param1Char);
    }
    
    private List<Token> getClassBound() {
      if (this.classBound == null)
        this.classBound = new ArrayList<Token>(); 
      return this.classBound;
    }
    
    private List<Token> getIfaceBound() {
      if (this.ifaceBound == null)
        this.ifaceBound = new ArrayList<Token>(); 
      return this.ifaceBound;
    }
    
    private List<ClassSignature.IToken> getSignature() {
      if (this.signature == null)
        this.signature = new ArrayList<ClassSignature.IToken>(); 
      return this.signature;
    }
    
    private List<ClassSignature.IToken> getSuffix() {
      if (this.suffix == null)
        this.suffix = new ArrayList<ClassSignature.IToken>(); 
      return this.suffix;
    }
    
    ClassSignature.IToken addTypeArgument(char param1Char) {
      if (this.tail != null)
        return this.tail.addTypeArgument(param1Char); 
      Token token = new Token(param1Char);
      getSignature().add(token);
      return token;
    }
    
    ClassSignature.IToken addTypeArgument(String param1String) {
      if (this.tail != null)
        return this.tail.addTypeArgument(param1String); 
      Token token = new Token(param1String);
      getSignature().add(token);
      return token;
    }
    
    ClassSignature.IToken addTypeArgument(Token param1Token) {
      if (this.tail != null)
        return this.tail.addTypeArgument(param1Token); 
      getSignature().add(param1Token);
      return param1Token;
    }
    
    ClassSignature.IToken addTypeArgument(ClassSignature.TokenHandle param1TokenHandle) {
      if (this.tail != null)
        return this.tail.addTypeArgument(param1TokenHandle); 
      ClassSignature.TokenHandle tokenHandle = param1TokenHandle.clone();
      getSignature().add(tokenHandle);
      return tokenHandle;
    }
    
    Token addBound(String param1String, boolean param1Boolean) {
      if (param1Boolean)
        return addClassBound(param1String); 
      return addInterfaceBound(param1String);
    }
    
    Token addClassBound(String param1String) {
      Token token = new Token(param1String);
      getClassBound().add(token);
      return token;
    }
    
    Token addInterfaceBound(String param1String) {
      Token token = new Token(param1String);
      getIfaceBound().add(token);
      return token;
    }
    
    Token addInnerClass(String param1String) {
      this.tail = new Token(param1String, true);
      getSuffix().add(this.tail);
      return this.tail;
    }
    
    public String toString() {
      return asType();
    }
    
    public String asBound() {
      StringBuilder stringBuilder = new StringBuilder();
      if (this.type != null)
        stringBuilder.append(this.type); 
      if (this.classBound != null)
        for (Token token : this.classBound)
          stringBuilder.append(token.asType());  
      if (this.ifaceBound != null)
        for (Token token : this.ifaceBound)
          stringBuilder.append(':').append(token.asType());  
      return stringBuilder.toString();
    }
    
    public String asType() {
      return asType(false);
    }
    
    public String asType(boolean param1Boolean) {
      StringBuilder stringBuilder = new StringBuilder();
      if (this.array)
        stringBuilder.append('['); 
      if (this.symbol != '\000')
        stringBuilder.append(this.symbol); 
      if (this.type == null)
        return stringBuilder.toString(); 
      if (!this.inner)
        stringBuilder.append('L'); 
      stringBuilder.append(this.type);
      if (!param1Boolean) {
        if (this.signature != null) {
          stringBuilder.append('<');
          for (ClassSignature.IToken iToken : this.signature)
            stringBuilder.append(iToken.asType()); 
          stringBuilder.append('>');
        } 
        if (this.suffix != null)
          for (ClassSignature.IToken iToken : this.suffix)
            stringBuilder.append('.').append(iToken.asType());  
      } 
      if (!this.inner)
        stringBuilder.append(';'); 
      return stringBuilder.toString();
    }
    
    boolean isRaw() {
      return (this.signature == null);
    }
    
    String getClassType() {
      return (this.type != null) ? this.type : "java/lang/Object";
    }
    
    public Token asToken() {
      return this;
    }
  }
  
  class TokenHandle implements IToken {
    final ClassSignature.Token token;
    
    boolean array;
    
    char wildcard;
    
    TokenHandle() {
      this(new ClassSignature.Token());
    }
    
    TokenHandle(ClassSignature.Token param1Token) {
      this.token = param1Token;
    }
    
    public ClassSignature.IToken setArray(boolean param1Boolean) {
      this.array |= param1Boolean;
      return this;
    }
    
    public ClassSignature.IToken setWildcard(char param1Char) {
      if ("+-".indexOf(param1Char) > -1)
        this.wildcard = param1Char; 
      return this;
    }
    
    public String asBound() {
      return this.token.asBound();
    }
    
    public String asType() {
      StringBuilder stringBuilder = new StringBuilder();
      if (this.wildcard > '\000')
        stringBuilder.append(this.wildcard); 
      if (this.array)
        stringBuilder.append('['); 
      return stringBuilder.append(ClassSignature.this.getTypeVar(this)).toString();
    }
    
    public ClassSignature.Token asToken() {
      return this.token;
    }
    
    public String toString() {
      return this.token.toString();
    }
    
    public TokenHandle clone() {
      return new TokenHandle(this.token);
    }
  }
  
  class SignatureParser extends SignatureVisitor {
    private FormalParamElement param;
    
    abstract class SignatureElement extends SignatureVisitor {
      public SignatureElement() {
        super(327680);
      }
    }
    
    abstract class TokenElement extends SignatureElement {
      protected ClassSignature.Token token;
      
      private boolean array;
      
      public ClassSignature.Token getToken() {
        if (this.token == null)
          this.token = new ClassSignature.Token(); 
        return this.token;
      }
      
      protected void setArray() {
        this.array = true;
      }
      
      private boolean getArray() {
        boolean bool = this.array;
        this.array = false;
        return bool;
      }
      
      public void visitClassType(String param2String) {
        getToken().setType(param2String);
      }
      
      public SignatureVisitor visitClassBound() {
        getToken();
        return new ClassSignature.SignatureParser.BoundElement(this, true);
      }
      
      public SignatureVisitor visitInterfaceBound() {
        getToken();
        return new ClassSignature.SignatureParser.BoundElement(this, false);
      }
      
      public void visitInnerClassType(String param2String) {
        this.token.addInnerClass(param2String);
      }
      
      public SignatureVisitor visitArrayType() {
        setArray();
        return this;
      }
      
      public SignatureVisitor visitTypeArgument(char param2Char) {
        return new ClassSignature.SignatureParser.TypeArgElement(this, param2Char);
      }
      
      ClassSignature.Token addTypeArgument() {
        return this.token.addTypeArgument('*').asToken();
      }
      
      ClassSignature.IToken addTypeArgument(char param2Char) {
        return this.token.addTypeArgument(param2Char).setArray(getArray());
      }
      
      ClassSignature.IToken addTypeArgument(String param2String) {
        return this.token.addTypeArgument(param2String).setArray(getArray());
      }
      
      ClassSignature.IToken addTypeArgument(ClassSignature.Token param2Token) {
        return this.token.addTypeArgument(param2Token).setArray(getArray());
      }
      
      ClassSignature.IToken addTypeArgument(ClassSignature.TokenHandle param2TokenHandle) {
        return this.token.addTypeArgument(param2TokenHandle).setArray(getArray());
      }
    }
    
    class FormalParamElement extends TokenElement {
      private final ClassSignature.TokenHandle handle;
      
      FormalParamElement(String param2String) {
        this.handle = ClassSignature.this.getType(param2String);
        this.token = this.handle.asToken();
      }
    }
    
    class TypeArgElement extends TokenElement {
      private final char wildcard;
      
      private final ClassSignature.SignatureParser.TokenElement type;
      
      TypeArgElement(ClassSignature.SignatureParser.TokenElement param2TokenElement, char param2Char) {
        this.type = param2TokenElement;
        this.wildcard = param2Char;
      }
      
      public SignatureVisitor visitArrayType() {
        this.type.setArray();
        return this;
      }
      
      public void visitBaseType(char param2Char) {
        this.token = this.type.addTypeArgument(param2Char).asToken();
      }
      
      public void visitTypeVariable(String param2String) {
        ClassSignature.TokenHandle tokenHandle = ClassSignature.this.getType(param2String);
        this.token = this.type.addTypeArgument(tokenHandle).setWildcard(this.wildcard).asToken();
      }
      
      public void visitClassType(String param2String) {
        this.token = this.type.addTypeArgument(param2String).setWildcard(this.wildcard).asToken();
      }
      
      public void visitTypeArgument() {
        this.token.addTypeArgument('*');
      }
      
      public SignatureVisitor visitTypeArgument(char param2Char) {
        return new TypeArgElement(this, param2Char);
      }
      
      public void visitEnd() {}
    }
    
    class BoundElement extends TokenElement {
      private final boolean classBound;
      
      private final ClassSignature.SignatureParser.TokenElement type;
      
      BoundElement(ClassSignature.SignatureParser.TokenElement param2TokenElement, boolean param2Boolean) {
        this.type = param2TokenElement;
        this.classBound = param2Boolean;
      }
      
      public void visitClassType(String param2String) {
        this.token = this.type.token.addBound(param2String, this.classBound);
      }
      
      public void visitTypeArgument() {
        this.token.addTypeArgument('*');
      }
      
      public SignatureVisitor visitTypeArgument(char param2Char) {
        return new ClassSignature.SignatureParser.TypeArgElement(this, param2Char);
      }
    }
    
    class SuperClassElement extends TokenElement {
      public void visitEnd() {
        ClassSignature.this.setSuperClass(this.token);
      }
    }
    
    class InterfaceElement extends TokenElement {
      public void visitEnd() {
        ClassSignature.this.addInterface(this.token);
      }
    }
    
    SignatureParser() {
      super(327680);
    }
    
    public void visitFormalTypeParameter(String param1String) {
      this.param = new FormalParamElement(param1String);
    }
    
    public SignatureVisitor visitClassBound() {
      return this.param.visitClassBound();
    }
    
    public SignatureVisitor visitInterfaceBound() {
      return this.param.visitInterfaceBound();
    }
    
    public SignatureVisitor visitSuperclass() {
      return new SuperClassElement();
    }
    
    public SignatureVisitor visitInterface() {
      return new InterfaceElement();
    }
  }
  
  class SignatureRemapper extends SignatureWriter {
    private final Set<String> localTypeVars = new HashSet<String>();
    
    public void visitFormalTypeParameter(String param1String) {
      this.localTypeVars.add(param1String);
      super.visitFormalTypeParameter(param1String);
    }
    
    public void visitTypeVariable(String param1String) {
      if (!this.localTypeVars.contains(param1String)) {
        ClassSignature.TypeVar typeVar = ClassSignature.this.getTypeVar(param1String);
        if (typeVar != null) {
          super.visitTypeVariable(typeVar.toString());
          return;
        } 
      } 
      super.visitTypeVariable(param1String);
    }
  }
  
  private final Map<TypeVar, TokenHandle> types = new LinkedHashMap<TypeVar, TokenHandle>();
  
  private Token superClass = new Token("java/lang/Object");
  
  private final List<Token> interfaces = new ArrayList<Token>();
  
  private final Deque<String> rawInterfaces = new LinkedList<String>();
  
  protected static final String OBJECT = "java/lang/Object";
  
  private ClassSignature read(String paramString) {
    if (paramString != null)
      try {
        (new SignatureReader(paramString)).accept(new SignatureParser());
      } catch (Exception exception) {
        exception.printStackTrace();
      }  
    return this;
  }
  
  protected TypeVar getTypeVar(String paramString) {
    for (TypeVar typeVar : this.types.keySet()) {
      if (typeVar.matches(paramString))
        return typeVar; 
    } 
    return null;
  }
  
  protected TokenHandle getType(String paramString) {
    for (TypeVar typeVar : this.types.keySet()) {
      if (typeVar.matches(paramString))
        return this.types.get(typeVar); 
    } 
    TokenHandle tokenHandle = new TokenHandle();
    this.types.put(new TypeVar(paramString), tokenHandle);
    return tokenHandle;
  }
  
  protected String getTypeVar(TokenHandle paramTokenHandle) {
    for (Map.Entry<TypeVar, TokenHandle> entry : this.types.entrySet()) {
      TypeVar typeVar = (TypeVar)entry.getKey();
      TokenHandle tokenHandle = (TokenHandle)entry.getValue();
      if (paramTokenHandle == tokenHandle || paramTokenHandle.asToken() == tokenHandle.asToken())
        return "T" + typeVar + ";"; 
    } 
    return paramTokenHandle.token.asType();
  }
  
  protected void addTypeVar(TypeVar paramTypeVar, TokenHandle paramTokenHandle) throws IllegalArgumentException {
    if (this.types.containsKey(paramTypeVar))
      throw new IllegalArgumentException("TypeVar " + paramTypeVar + " is already present on " + this); 
    this.types.put(paramTypeVar, paramTokenHandle);
  }
  
  protected void setSuperClass(Token paramToken) {
    this.superClass = paramToken;
  }
  
  public String getSuperClass() {
    return this.superClass.asType(true);
  }
  
  protected void addInterface(Token paramToken) {
    if (!paramToken.isRaw()) {
      String str = paramToken.asType(true);
      for (ListIterator<Token> listIterator = this.interfaces.listIterator(); listIterator.hasNext(); ) {
        Token token = listIterator.next();
        if (token.isRaw() && token.asType(true).equals(str)) {
          listIterator.set(paramToken);
          return;
        } 
      } 
    } 
    this.interfaces.add(paramToken);
  }
  
  public void addInterface(String paramString) {
    this.rawInterfaces.add(paramString);
  }
  
  protected void addRawInterface(String paramString) {
    Token token = new Token(paramString);
    String str = token.asType(true);
    for (Token token1 : this.interfaces) {
      if (token1.asType(true).equals(str))
        return; 
    } 
    this.interfaces.add(token);
  }
  
  public void merge(ClassSignature paramClassSignature) {
    try {
      HashSet<String> hashSet = new HashSet();
      for (TypeVar typeVar : this.types.keySet())
        hashSet.add(typeVar.toString()); 
      paramClassSignature.conform(hashSet);
    } catch (IllegalStateException illegalStateException) {
      illegalStateException.printStackTrace();
      return;
    } 
    for (Map.Entry<TypeVar, TokenHandle> entry : paramClassSignature.types.entrySet())
      addTypeVar((TypeVar)entry.getKey(), (TokenHandle)entry.getValue()); 
    for (Token token : paramClassSignature.interfaces)
      addInterface(token); 
  }
  
  private void conform(Set<String> paramSet) {
    for (TypeVar typeVar : this.types.keySet()) {
      String str = findUniqueName(typeVar.getOriginalName(), paramSet);
      typeVar.rename(str);
      paramSet.add(str);
    } 
  }
  
  private String findUniqueName(String paramString, Set<String> paramSet) {
    if (!paramSet.contains(paramString))
      return paramString; 
    if (paramString.length() == 1) {
      String str1 = findOffsetName(paramString.charAt(0), paramSet);
      if (str1 != null)
        return str1; 
    } 
    String str = findOffsetName('T', paramSet, "", paramString);
    if (str != null)
      return str; 
    str = findOffsetName('T', paramSet, paramString, "");
    if (str != null)
      return str; 
    str = findOffsetName('T', paramSet, "T", paramString);
    if (str != null)
      return str; 
    str = findOffsetName('T', paramSet, "", paramString + "Type");
    if (str != null)
      return str; 
    throw new IllegalStateException("Failed to conform type var: " + paramString);
  }
  
  private String findOffsetName(char paramChar, Set<String> paramSet) {
    return findOffsetName(paramChar, paramSet, "", "");
  }
  
  private String findOffsetName(char paramChar, Set<String> paramSet, String paramString1, String paramString2) {
    String str = String.format("%s%s%s", new Object[] { paramString1, Character.valueOf(paramChar), paramString2 });
    if (!paramSet.contains(str))
      return str; 
    if (paramChar > '@' && paramChar < '[') {
      int i;
      for (i = paramChar - 64; i + 65 != paramChar; i = ++i % 26) {
        str = String.format("%s%s%s", new Object[] { paramString1, Character.valueOf((char)(i + 65)), paramString2 });
        if (!paramSet.contains(str))
          return str; 
      } 
    } 
    return null;
  }
  
  public SignatureVisitor getRemapper() {
    return (SignatureVisitor)new SignatureRemapper();
  }
  
  public String toString() {
    while (this.rawInterfaces.size() > 0)
      addRawInterface(this.rawInterfaces.remove()); 
    StringBuilder stringBuilder = new StringBuilder();
    if (this.types.size() > 0) {
      boolean bool = false;
      StringBuilder stringBuilder1 = new StringBuilder();
      for (Map.Entry<TypeVar, TokenHandle> entry : this.types.entrySet()) {
        String str = ((TokenHandle)entry.getValue()).asBound();
        if (!str.isEmpty()) {
          stringBuilder1.append(entry.getKey()).append(':').append(str);
          bool = true;
        } 
      } 
      if (bool)
        stringBuilder.append('<').append(stringBuilder1).append('>'); 
    } 
    stringBuilder.append(this.superClass.asType());
    for (Token token : this.interfaces)
      stringBuilder.append(token.asType()); 
    return stringBuilder.toString();
  }
  
  public ClassSignature wake() {
    return this;
  }
  
  public static ClassSignature of(String paramString) {
    return (new ClassSignature()).read(paramString);
  }
  
  public static ClassSignature of(ClassNode paramClassNode) {
    if (paramClassNode.signature != null)
      return of(paramClassNode.signature); 
    return generate(paramClassNode);
  }
  
  public static ClassSignature ofLazy(ClassNode paramClassNode) {
    if (paramClassNode.signature != null)
      return new Lazy(paramClassNode.signature); 
    return generate(paramClassNode);
  }
  
  private static ClassSignature generate(ClassNode paramClassNode) {
    ClassSignature classSignature = new ClassSignature();
    classSignature.setSuperClass(new Token((paramClassNode.superName != null) ? paramClassNode.superName : "java/lang/Object"));
    for (String str : paramClassNode.interfaces)
      classSignature.addInterface(new Token(str)); 
    return classSignature;
  }
}
