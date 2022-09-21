package me.oringo.oringoclient.events.impl;

import me.oringo.oringoclient.events.OringoEvent;
import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.fml.common.eventhandler.Cancelable;
import net.minecraftforge.fml.common.eventhandler.Event;

@Cancelable
public class RenderLayersEvent extends OringoEvent {
  public ModelBase ;
  
  public float ;
  
  public float ;
  
  public float ;
  
  public float ;
  
  public float ;
  
  public float ;
  
  public EntityLivingBase ;
  
  public RenderLayersEvent(EntityLivingBase paramEntityLivingBase, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, ModelBase paramModelBase) {
    this. = paramEntityLivingBase;
    this. = paramFloat1;
    this. = paramFloat2;
    this. = paramFloat3;
    this. = paramFloat4;
    this. = paramFloat5;
    this. = paramFloat6;
    this. = paramModelBase;
  }
  
  public static class Pre extends Event {
    static {
    
    }
  }
}
