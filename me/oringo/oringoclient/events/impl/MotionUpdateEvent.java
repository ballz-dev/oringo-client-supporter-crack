package me.oringo.oringoclient.events.impl;

import me.oringo.oringoclient.events.OringoEvent;
import me.oringo.oringoclient.utils.Rotation;
import net.minecraft.util.Vec3;
import net.minecraftforge.fml.common.eventhandler.Cancelable;

@Cancelable
public class MotionUpdateEvent extends OringoEvent {
  public double ;
  
  public boolean ;
  
  public boolean ;
  
  public double ;
  
  public double ;
  
  public float ;
  
  public float ;
  
  public boolean ;
  
  public MotionUpdateEvent setOnGround(boolean paramBoolean) {
    this. = paramBoolean;
    return this;
  }
  
  public MotionUpdateEvent setRotation(float paramFloat1, float paramFloat2) {
    this. = paramFloat2;
    this. = paramFloat1;
    return this;
  }
  
  public Rotation getRotation() {
    return new Rotation(this., this.);
  }
  
  public MotionUpdateEvent setSneaking(boolean paramBoolean) {
    this. = paramBoolean;
    return this;
  }
  
  public MotionUpdateEvent setPosition(double paramDouble1, double paramDouble2, double paramDouble3) {
    this. = paramDouble1;
    this. = paramDouble3;
    this. = paramDouble2;
    return this;
  }
  
  public MotionUpdateEvent setPosition(Vec3 paramVec3) {
    return setPosition(paramVec3.field_72450_a, paramVec3.field_72448_b, paramVec3.field_72449_c);
  }
  
  public boolean isPost() {
    return !isPre();
  }
  
  public MotionUpdateEvent setPitch(float paramFloat) {
    this. = paramFloat;
    return this;
  }
  
  public MotionUpdateEvent setX(double paramDouble) {
    this. = paramDouble;
    return this;
  }
  
  public MotionUpdateEvent setSprinting(boolean paramBoolean) {
    this. = paramBoolean;
    return this;
  }
  
  public MotionUpdateEvent setRotation(Rotation paramRotation) {
    return setRotation(paramRotation.(), paramRotation.());
  }
  
  public Vec3 getPosition() {
    return new Vec3(this., this., this.);
  }
  
  public MotionUpdateEvent setY(double paramDouble) {
    this. = paramDouble;
    return this;
  }
  
  public MotionUpdateEvent(double paramDouble1, double paramDouble2, double paramDouble3, float paramFloat1, float paramFloat2, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3) {
    this. = paramDouble1;
    this. = paramDouble2;
    this. = paramDouble3;
    this. = paramFloat1;
    this. = paramFloat2;
    this. = paramBoolean1;
    this. = paramBoolean3;
    this. = paramBoolean2;
  }
  
  public MotionUpdateEvent setYaw(float paramFloat) {
    this. = paramFloat;
    return this;
  }
  
  public MotionUpdateEvent setZ(double paramDouble) {
    this. = paramDouble;
    return this;
  }
  
  public boolean isPre() {
    return this instanceof Pre;
  }
  
  @Cancelable
  public static class Post extends MotionUpdateEvent {
    public Post(double param1Double1, double param1Double2, double param1Double3, float param1Float1, float param1Float2, boolean param1Boolean1, boolean param1Boolean2, boolean param1Boolean3) {
      super(param1Double1, param1Double2, param1Double3, param1Float1, param1Float2, param1Boolean1, param1Boolean2, param1Boolean3);
    }
    
    public Post(MotionUpdateEvent param1MotionUpdateEvent) {
      super(param1MotionUpdateEvent., param1MotionUpdateEvent., param1MotionUpdateEvent., param1MotionUpdateEvent., param1MotionUpdateEvent., param1MotionUpdateEvent., param1MotionUpdateEvent., param1MotionUpdateEvent.);
    }
    
    static {
    
    }
  }
  
  @Cancelable
  public static class Pre extends MotionUpdateEvent {
    public Pre(double param1Double1, double param1Double2, double param1Double3, float param1Float1, float param1Float2, boolean param1Boolean1, boolean param1Boolean2, boolean param1Boolean3) {
      super(param1Double1, param1Double2, param1Double3, param1Float1, param1Float2, param1Boolean1, param1Boolean2, param1Boolean3);
    }
    
    static {
    
    }
  }
}
