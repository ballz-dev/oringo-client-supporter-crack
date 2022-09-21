package me.oringo.oringoclient.events.impl;

import me.oringo.oringoclient.events.OringoEvent;

public class UpdateRenderEvent extends OringoEvent {
  public float ;
  
  public UpdateRenderEvent(float paramFloat) {
    this. = paramFloat;
  }
  
  public static class Post extends UpdateRenderEvent {
    public Post(float param1Float) {
      super(param1Float);
    }
    
    static {
    
    }
  }
  
  public static class Pre extends UpdateRenderEvent {
    public Pre(float param1Float) {
      super(param1Float);
    }
    
    static {
    
    }
  }
}
