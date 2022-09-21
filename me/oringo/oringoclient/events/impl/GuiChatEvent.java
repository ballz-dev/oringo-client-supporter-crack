package me.oringo.oringoclient.events.impl;

import me.oringo.oringoclient.events.OringoEvent;

public class GuiChatEvent extends OringoEvent {
  public int ;
  
  public int ;
  
  public char ;
  
  public int ;
  
  public GuiChatEvent(int paramInt1, int paramInt2, int paramInt3, char paramChar) {
    this. = paramInt1;
    this. = paramInt2;
    this. = paramInt3;
    this. = paramChar;
  }
  
  public static class Closed extends GuiChatEvent {
    public Closed() {
      super(0, 0, -1, false);
    }
    
    static {
    
    }
  }
  
  public static class MouseReleased extends GuiChatEvent {
    public MouseReleased(int param1Int1, int param1Int2, int param1Int3) {
      super(param1Int1, param1Int2, param1Int3, false);
    }
    
    static {
    
    }
  }
  
  public static class MouseClicked extends GuiChatEvent {
    public MouseClicked(int param1Int1, int param1Int2, int param1Int3) {
      super(param1Int1, param1Int2, param1Int3, false);
    }
    
    static {
    
    }
  }
  
  public static class KeyTyped extends GuiChatEvent {
    static {
    
    }
    
    public KeyTyped(int param1Int, char param1Char) {
      super(0, 0, param1Int, param1Char);
    }
  }
  
  public static class DrawChatEvent extends GuiChatEvent {
    public DrawChatEvent(int param1Int1, int param1Int2) {
      super(param1Int1, param1Int2, -1, false);
    }
    
    static {
    
    }
  }
}
